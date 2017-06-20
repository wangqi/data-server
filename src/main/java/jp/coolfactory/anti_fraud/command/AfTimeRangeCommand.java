package jp.coolfactory.anti_fraud.command;

import jp.coolfactory.anti_fraud.module.Status;
import jp.coolfactory.data.Constants;
import jp.coolfactory.data.common.CommandStatus;
import jp.coolfactory.data.common.Handler;
import jp.coolfactory.data.module.AdRequest;
import jp.coolfactory.data.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;

/**
 * After digging with a lot of fraud conversions, I found a major percentage of them occurs during 01:00 to 06:00.
 * So I want to forbit this time range's conversion
 *
 * Created by wangqi on 20/6/2017.
 */
public class AfTimeRangeCommand implements Handler<AdRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AfTimeRangeCommand.class);

    public AfTimeRangeCommand() {
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
                        ZonedDateTime installDate = adRequest.getInstall_time();
                        int hour = installDate.getHour();
                        //@todo Make it configurable in future
                        if (hour >= 1 && hour <= 6) {
                            adRequest.setAf_status(Status.FORBIDDEN_TIMERANGE);
                        } else {
                            adRequest.setAf_status(Status.OK);
                        }
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
