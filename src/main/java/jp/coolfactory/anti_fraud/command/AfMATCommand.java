package jp.coolfactory.anti_fraud.command;

import jp.coolfactory.anti_fraud.controller.AntiFraudController;
import jp.coolfactory.anti_fraud.db.CheckType;
import jp.coolfactory.anti_fraud.frequency.CacheRecord;
import jp.coolfactory.anti_fraud.frequency.RecordStatus;
import jp.coolfactory.anti_fraud.module.AfCampaign;
import jp.coolfactory.anti_fraud.module.AfSite;
import jp.coolfactory.anti_fraud.module.Status;
import jp.coolfactory.data.Constants;
import jp.coolfactory.data.common.CommandStatus;
import jp.coolfactory.data.common.Handler;
import jp.coolfactory.data.module.AdRequest;
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
public class AfMATCommand implements Handler<AdRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AfMATCommand.class);

    public AfMATCommand() {
    }

    @Override
    public CommandStatus handle(AdRequest adRequest) {
        try {
            if ( adRequest != null ) {
                // Only filter install action
                if ( ! Constants.ACTION_INSTALL.equals(adRequest.getAction()) ) {
                    return CommandStatus.Continue;
                }
                String mat_status = adRequest.getStatus();
                if (StringUtil.isNotEmptyString(mat_status)) {
                    if ( "approved".equals(mat_status) ) {
                        /**
                         * Check if the jailbroke is sent and if it is true
                         */
                        if (adRequest.isOs_jailbroke()) {
                            LOGGER.debug("Jailbroke tag is set to true ");
                            adRequest.setAf_status(Status.FORBIDDEN_JAILBROKE);

                        } else {
                            adRequest.setAf_status(Status.OK);
                        }
                    } else {
                        adRequest.setAf_status(Status.ALREADY_DENIED);
                        LOGGER.debug("The request is denied by third-party.");
                    }
                } else {
                    adRequest.setAf_status(Status.OK);
                }
            } else {
                LOGGER.warn("adRequest is null.");
            }
        } catch (Exception e) {
            LOGGER.warn("Failed to check the mat_status");
            return CommandStatus.Fail;
        }
        return CommandStatus.Continue;
    }

}
