package jp.coolfactory.anti_fraud.command;

import jp.coolfactory.anti_fraud.controller.AntiFraudController;
import jp.coolfactory.anti_fraud.db.CheckType;
import jp.coolfactory.anti_fraud.module.AfCampaign;
import jp.coolfactory.anti_fraud.module.AfSite;
import jp.coolfactory.anti_fraud.module.FraudDetectionFactory;
import jp.coolfactory.anti_fraud.module.Status;
import jp.coolfactory.data.Constants;
import jp.coolfactory.data.Version;
import jp.coolfactory.data.common.CommandStatus;
import jp.coolfactory.data.common.Handler;
import jp.coolfactory.data.module.AdRequest;
import jp.coolfactory.data.server.StartupListener;
import jp.coolfactory.data.server.URLJobManager;
import jp.coolfactory.data.util.PolicyCheckUtil;
import jp.coolfactory.data.util.StringUtil;
import jp.coolfactory.data.util.URLUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.Executors;

/**
 * For 'purchase' event, turn the local revenue into revenue_usd by latest currency ratio.
 *
 * API: http://api.fixer.io/latest?base=USD&symbols=CNY
 *      {"base":"USD","date":"2017-02-28","rates":{"CNY":6.868}}
 *
 * Get All: http://api.fixer.io/latest?base=USD
 *
 * Created by wangqi on 22/2/2017.
 */
public class AfPostbackCommand implements Handler<AdRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AfPostbackCommand.class);

    public AfPostbackCommand() {
    }

    @Override
    public CommandStatus handle(AdRequest adRequest) {
        try {
            if ( adRequest != null ) {
                // Only filter install action
                if ( ! Constants.ACTION_INSTALL.equals(adRequest.getAction()) ) {
                    return CommandStatus.Continue;
                }
                if ( adRequest.getAf_status() == null || adRequest.getAf_status() == Status.OK ) {
                    String postback = adRequest.getPostback();
                    if ( StringUtil.isNotEmptyString(postback) ) {
                        URLJobManager jobManager = (URLJobManager)Version.CONTEXT.get(Constants.URL_JOB_MANAGER);
                        jobManager.submitRequest(new Runnable() {
                            @Override
                            public void run() {
                                sendPostback(postback, adRequest);
                            }
                        });
                    }
                }
            } else {
                LOGGER.warn("adRequest is null.");
            }
        } catch (Exception e) {
            LOGGER.warn("Failed to process stat_id", e);
            return CommandStatus.Fail;
        }
        return CommandStatus.Continue;
    }

    /**
     * Send back the postback URL
     *
     * @param postback
     * @param request
     */
    private void sendPostback(String postback, AdRequest request ) {
        try {
            Status checkStatus = request.getAf_status();
            if ( checkStatus == Status.OK ) {
                //Resend the postback
                if ( postback != null && postback != "" ) {
                    LOGGER.info("Resend to postback to " + postback);
                    String postbackEncoded = URLUtil.encodeURL(postback);
                    LOGGER.info("Encode postback to " + postbackEncoded);
                    URL url = new URL(postbackEncoded);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("User-Agent", "antifraud/"+ Version.VERSION);
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
                    request.setPostback_code(responseCode);
                    request.setPostback_desc(response.toString());
                } else {
                    LOGGER.warn("postback param does not exist");
                }
            }
        } catch (MalformedURLException e) {
            LOGGER.warn("Malformed postback url: " + postback);
        } catch (Exception e) {
            LOGGER.warn("Failed to connect to postback url: " + postback, e);
        } finally {
            //@TODO
            //Save in the install log
//                AntiFraudController.instance.saveLog(params);
        }
    }
}
