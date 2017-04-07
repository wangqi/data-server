package jp.coolfactory.data.common;

import jp.coolfactory.data.Constants;
import jp.coolfactory.data.Version;
import jp.coolfactory.data.module.AdRequest;
import jp.coolfactory.data.server.DBJobManager;
import jp.coolfactory.data.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Save the AdRequest to database.
 *
 * Created by wangqi on 22/2/2017.
 */
public class AdRequestLogCommand implements Handler<AdRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdRequestLogCommand.class);
    private static final Logger install_loggger = LoggerFactory.getLogger("install_log");
    private static final Logger click_loggger = LoggerFactory.getLogger("click_log");
    private static final Logger event_loggger = LoggerFactory.getLogger("event_log");
    private static final Logger purchase_loggger = LoggerFactory.getLogger("purchase_log");

    @Override
    public CommandStatus handle(AdRequest adRequest) {
        try {
            String action = adRequest.getAction();
            if (StringUtil.isNotEmptyString(action)) {
                if ( Constants.ACTION_INSTALL.equals(action)) {
                    install_loggger.info(adRequest.toString());
                } else if ( Constants.ACTION_CLICK.equals(action)) {
                    click_loggger.info(adRequest.toString());
                } else if ( Constants.ACTION_PURCHASE.equals(action)) {
                    purchase_loggger.info(adRequest.toString());
                } else {
                    event_loggger.info(adRequest.toString());
                }
            }
        } catch (Exception e) {
            LOGGER.warn("handle is interrupted.");
            return CommandStatus.Fail;
        }
        return CommandStatus.Continue;
    }

}
