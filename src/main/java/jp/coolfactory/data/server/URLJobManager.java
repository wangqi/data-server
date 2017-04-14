package jp.coolfactory.data.server; /**
 * Created by wangqi on 6/3/2017.
 */

import jp.coolfactory.data.Constants;
import jp.coolfactory.data.Version;
import jp.coolfactory.data.db.DBUtil;
import jp.coolfactory.data.module.AdRequest;
import jp.coolfactory.data.module.SQLRequest;
import jp.coolfactory.data.util.StringUtil;
import jp.coolfactory.data.util.URLUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@WebListener()
public class URLJobManager implements ServletContextListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(URLJobManager.class);
    private static final Logger FRAUD_LOGGER = LoggerFactory.getLogger("fraud_log");
    private static final int DEFAULT_DRAIN_SIZE = 20;
    private BlockingQueue<AdRequest> queue = new LinkedBlockingQueue<>();
    private ExecutorService service;

    // Public constructor is required by servlet spec
    public URLJobManager() {
    }

    /**
     * Put a request into queue.
     *
     * @param req
     */
    public void submitRequest(AdRequest req) {
        try {
            if ( req != null )
                queue.put(req);
        } catch (InterruptedException e) {
            LOGGER.warn("submitRequest is interrupted.");
        }
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
      /* This method is called when the servlet context is
         initialized(when the Web application is deployed). 
         You can initialize servlet context related data here.
      */
        service = Executors.newCachedThreadPool();
        LOGGER.info("URLWorker is activated.");
        service.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (service.isShutdown()) {
                            LOGGER.info("URLWorker is shutdown.");
                            break;
                        } else {
                            ArrayList<AdRequest> list = new ArrayList<>(DEFAULT_DRAIN_SIZE);
                            //If no objects avaiable, then wait
                            list.add(queue.take());
                            queue.drainTo(list, DEFAULT_DRAIN_SIZE);
                            ArrayList<String> sqlList = new ArrayList<>(DEFAULT_DRAIN_SIZE);
                            for ( AdRequest req : list ) {
                                sendPostback(req);
                                sqlList.add(req.toPostbackSQL());
                            }
                            DBUtil.sqlBatch(sqlList);
                            //TODO Add CouldWatch here if necessary.
                            LOGGER.info("Post " + list.size() + " requests to server. ");
                        }
                    } catch (Exception e) {
                        LOGGER.warn("URLWorker worker runs into an error", e);
                    }
                }
            }
        });
        Version.CONTEXT.put(Constants.URL_JOB_MANAGER, this);
    }

    public void contextDestroyed(ServletContextEvent sce) {
      /* This method is invoked when the Servlet Context 
         (the Web application) is undeployed or 
         Application Server shuts down.
      */
        service.shutdownNow();
    }

    /**
     * Send back the postback URL
     */
    public static final int sendPostback(AdRequest req) {
        String postback = req.getPostback();
        try {
            if ( StringUtil.isNotEmptyString(postback) ) {
                LOGGER.info("Resend to postback to " + postback);
                String postbackEncoded = URLUtil.encodeURL(postback);
                LOGGER.info("Encode postback to " + postbackEncoded);
                URL url = new URL(postbackEncoded);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("User-Agent", "antifraud/1.0");
                int responseCode = conn.getResponseCode();
                StringBuffer response = new StringBuffer();
                if ( responseCode > 200 ) {
                    LOGGER.warn("Failed to send postback. Response code: " + responseCode);
                } else {
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                        if ( response.length()>100 ) {
                            response.setLength(100);
                            break;
                        }
                    }
                    in.close();
                }
                req.setPostback_code(responseCode);
                req.setPostback_desc(response.toString());
                return responseCode;
            } else {
                LOGGER.warn("postback param does not exist");
            }
        } catch (MalformedURLException e) {
            LOGGER.warn("Malformed postback url: " + postback);
        } catch (Exception e) {
            LOGGER.warn("Failed to connect to postback url: " + postback, e);
        } finally {
            FRAUD_LOGGER.info(req.toString());
        }
        return -1;
    }

}
