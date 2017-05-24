package jp.coolfactory.anti_fraud.command;

import jp.coolfactory.anti_fraud.controller.AntiFraudController;
import jp.coolfactory.anti_fraud.frequency.CacheRecord;
import jp.coolfactory.anti_fraud.frequency.CacheRecordMaker;
import jp.coolfactory.anti_fraud.frequency.FrequencyManager;
import jp.coolfactory.anti_fraud.frequency.InternalFreqManager;
import jp.coolfactory.anti_fraud.frequency.RecordStatus;
import jp.coolfactory.anti_fraud.module.AfIPFilter;
import jp.coolfactory.anti_fraud.module.Status;
import jp.coolfactory.data.Constants;
import jp.coolfactory.data.common.CommandStatus;
import jp.coolfactory.data.common.Handler;
import jp.coolfactory.data.module.AdRequest;
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
public class AfIPSegmentCommand implements Handler<AdRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AfIPSegmentCommand.class);

    /**
     * It's used to filter IP by frequency
     */
    public final FrequencyManager InternalFreqManager = new InternalFreqManager();
    public final CacheRecordMaker cacheRecordMaker = new CacheRecordMaker() { };

    public AfIPSegmentCommand() {
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
                    CacheRecord cacheRecord = cacheRecordMaker.makeKey(adRequest);
                    RecordStatus recordStatus = InternalFreqManager.check(cacheRecord);
                    if ( recordStatus == RecordStatus.OK )  {
                        adRequest.setAf_status(Status.OK);
                    } else {
                        adRequest.setAf_status(Status.FORBIDDEN_IP_SEG);
                    }
                } else {
                    //It is already denied.
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

}
