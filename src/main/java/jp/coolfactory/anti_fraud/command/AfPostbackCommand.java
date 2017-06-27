package jp.coolfactory.anti_fraud.command;

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
    private final static org.apache.log4j.Logger POSTBACK_LOGGER = org.apache.log4j.Logger.getLogger("postback_log");

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
                        jobManager.submitRequest(adRequest);
                    }
                } else {
                    POSTBACK_LOGGER.info(adRequest + "\taf_status is null or not OK.");
                }
            } else {
                LOGGER.warn("adRequest is null.");
            }
        } catch (Exception e) {
            LOGGER.warn("Failed to process stat_id", e);
            POSTBACK_LOGGER.info(adRequest + "\tFailed to send postback due to: "+e.getMessage());
            return CommandStatus.Fail;
        }
        return CommandStatus.Continue;
    }

}
