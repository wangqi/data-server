package jp.coolfactory.anti_fraud.command;

import com.sun.tools.internal.jxc.ap.Const;
import com.sun.tools.javac.code.Attribute;
import jp.coolfactory.anti_fraud.controller.AntiFraudController;
import jp.coolfactory.anti_fraud.module.AfSite;
import jp.coolfactory.anti_fraud.module.Status;
import jp.coolfactory.data.Constants;
import jp.coolfactory.data.Version;
import jp.coolfactory.data.common.CommandStatus;
import jp.coolfactory.data.common.Handler;
import jp.coolfactory.data.module.AdRequest;
import jp.coolfactory.data.server.URLJobManager;
import jp.coolfactory.data.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * Google Adwords has an S2S API to send mobile app's install data.
 * Please refer to : https://developers.google.com/app-conversion-tracking/api/
 *
 *
 *
 * Created by wangqi on 30/03/2018.
 */
public class AfGoogleAdwordsPostbackCommand implements Handler<AdRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AfGoogleAdwordsPostbackCommand.class);
    private static final Logger GOOGLE_LOGGER = LoggerFactory.getLogger("google_log");

    /**
     * https://adwords.google.com/um/AcctPref/Home?__c=7133715996&__u=4474841196&__o=cues&authuser=1#apidevelopercenter.
     * It's for Basic access.
     */
    private static final String MCC_DEVELOPER_TOKEN = "sxHPIzagxN5RvQ3L-zXGAw";

    private static final String REQ_URL = "https://www.googleadservices.com/pagead/conversion/app/1.0";

    private static final String REQ_USER_AGENT = "CoolFactory " + Version.VERSION + " (Java 1.8; en_US; Proxy)";

    private static final HashMap<String, String> EVENT_TYPE_MAP = new HashMap<>();
    static {
        EVENT_TYPE_MAP.put(Constants.ACTION_INSTALL, "first_open");
        EVENT_TYPE_MAP.put(Constants.ACTION_LOGIN, "session_start");
        EVENT_TYPE_MAP.put(Constants.ACTION_PURCHASE, "in_app_purchase");
    }


//    private final static org.apache.log4j.Logger POSTBACK_LOGGER = org.apache.log4j.Logger.getLogger("postback_log");

    public AfGoogleAdwordsPostbackCommand() {
    }

    @Override
    public CommandStatus handle(AdRequest adRequest) {
        try {
            if ( adRequest != null ) {
                // For Adwords, only send the Install and Purchase event.
                if ( Constants.ACTION_INSTALL.equals(adRequest.getAction()) ||
                        Constants.ACTION_PURCHASE.equals(adRequest.getAction())
                        ) {
                    if ( adRequest.getAf_status() == null || adRequest.getAf_status() == Status.OK ) {
                        AfSite site = AntiFraudController.getInstance().getSite(adRequest.getAf_site_id());
                        if ( site == null ) {
                            LOGGER.warn("AfSite is null for : " + adRequest.getAf_site_id());
                            return CommandStatus.Continue;
                        } else {
                            if ( StringUtil.isEmptyString(site.getAdwordsLinkId()) ) {
                                LOGGER.warn("AfSite " + adRequest.getSite_name() + " don't have adword_link_id.");
                                return CommandStatus.Continue;
                            } else {
                                HashMap<String, String> postParams = new HashMap<>();
                                postParams.put("dev_token", MCC_DEVELOPER_TOKEN);
                                postParams.put("link_id", site.getAdwordsLinkId());
                                postParams.put("app_event_type", EVENT_TYPE_MAP.get(adRequest.getAction()));
                                boolean is_ios = adRequest.getSite_name().toLowerCase().indexOf("ios")>0;
                                boolean is_android = adRequest.getSite_name().toLowerCase().indexOf("android")>0;
                                String ios_ifa = adRequest.getIos_ifa();
                                String google_aid = adRequest.getGoogle_aid();
                                if ( is_ios && StringUtil.isNotEmptyString(ios_ifa) ) {
                                    postParams.put("rdid", ios_ifa);
                                    postParams.put("id_type", "idfa");
                                    postParams.put("lat", "1");
                                } else if ( is_android && StringUtil.isNotEmptyString(google_aid) ) {
                                    postParams.put("rdid", ios_ifa);
                                    postParams.put("id_type", "advertisingid");
                                    postParams.put("lat", "1");
                                } else {
                                    postParams.put("lat", "0");
                                }
                                postParams.put("app_version", "1.0.0");
                                postParams.put("os_version", adRequest.getOs_version());
                                postParams.put("sdk_version", adRequest.getSdk_version());
                                float click_time = (float)(adRequest.getClick_time().to/1000.0);
                                postParams.put("timestamp", String.valueOf(click_time));
                                if ( Constants.ACTION_PURCHASE.equals(adRequest.getAction())) {
                                    postParams.put("value", String.valueOf(adRequest.getRevenue()));
                                    postParams.put("currency_code", String.valueOf(adRequest.getCurrency_code()));
                                }

                                String postback = adRequest.getPostback();
                                if ( StringUtil.isNotEmptyString(postback) ) {
                                    URLJobManager jobManager = (URLJobManager)Version.CONTEXT.get(Constants.URL_JOB_MANAGER);
                                    jobManager.submitRequest(adRequest);
                                }
                            }
                        }
                    } else {
                        LOGGER.info(adRequest + "\taf_status is null or not OK.");
                    }
                } else {
                    return CommandStatus.Continue;
                }
            } else {
                LOGGER.warn("adRequest is null.");
            }
        } catch (Exception e) {
            LOGGER.warn("Failed to process stat_id", e);
            LOGGER.info(adRequest + "\tFailed to send postback due to: "+e.getMessage());
            return CommandStatus.Fail;
        }
        return CommandStatus.Continue;
    }

}
