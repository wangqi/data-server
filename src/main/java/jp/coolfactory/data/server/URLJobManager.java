package jp.coolfactory.data.server; /**
 * Created by wangqi on 6/3/2017.
 */

import jp.coolfactory.data.Constants;
import jp.coolfactory.data.Version;
import jp.coolfactory.data.db.DBUtil;
import jp.coolfactory.data.module.SQLRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@WebListener()
public class URLJobManager implements ServletContextListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(URLJobManager.class);
    private static final int DEFAULT_DRAIN_SIZE = 10;
    private static final int DEFAULT_WAIT_SECOND = 5;
    private BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
    private ExecutorService service;

    // Public constructor is required by servlet spec
    public URLJobManager() {
    }

    /**
     * Put a request into queue.
     *
     * @param req
     */
    public void submitRequest(Runnable req) {
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
                            ArrayList<Runnable> list = new ArrayList<>();
                            //If no objects avaiable, then wait
                            list.add(queue.take());
                            queue.drainTo(list, DEFAULT_DRAIN_SIZE);
                            for ( Runnable runnable : list ) {
                                runnable.run();
                            }
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

}
