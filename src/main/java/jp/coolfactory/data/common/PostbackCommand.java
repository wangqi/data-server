package jp.coolfactory.data.common;

import jp.coolfactory.data.db.DBUtil;
import jp.coolfactory.data.module.AdRequest;
import jp.coolfactory.data.util.URLUtil;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.Executors;

/**
 * Created by wangqi on 25/2/2017.
 */
public class PostbackCommand implements Handler<AdRequest> {

    private final static Logger LOGGER = Logger.getLogger(PostbackCommand.class);


    @Override
    public CommandStatus handle(AdRequest adRequest) {
        return CommandStatus.Continue;
    }

    /**
     * Send back the postback URL
     * @param postback
     * @param params
     */
    private void sendPostback(String postback, HashMap<String, String> params ) {
        Executors.newCachedThreadPool().submit(() -> {
            try {
                if ( postback != null && postback != "" ) {
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
                        }
                        in.close();
                    }
                    params.put("postback_code", String.valueOf(responseCode));
                    params.put("postback_desc", response.toString());
                } else {
                    LOGGER.warn("postback param does not exist");
                }
            } catch (MalformedURLException e) {
                LOGGER.warn("Malformed postback url: " + postback);
            } catch (IOException e) {
                LOGGER.warn("Failed to connect to postback url: " + postback, e);
            } finally {
                DBUtil.saveLog(params);
            }
        });
    }
}
