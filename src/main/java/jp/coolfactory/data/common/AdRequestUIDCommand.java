package jp.coolfactory.data.common;

import jp.coolfactory.data.Constants;
import jp.coolfactory.data.Version;
import jp.coolfactory.data.module.AdRequest;
import jp.coolfactory.data.module.UIDRequest;
import jp.coolfactory.data.server.DBJobManager;
import jp.coolfactory.data.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Register an user into ad_reguser table
 *
 * Created by wangqi on 22/2/2017.
 */
public class AdRequestUIDCommand implements Handler<AdRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdRequestUIDCommand.class);

    public AdRequestUIDCommand() {
    }

    @Override
    public CommandStatus handle(AdRequest adRequest) {
        try {
            UIDRequest user = new UIDRequest();
            user.setAccount_key(adRequest.getAccount_key());
            user.setAction(adRequest.getAction());
            user.setSource(adRequest.getSource());
            user.setApp_key(adRequest.getAppKey());
            user.setStat_id(adRequest.getStat_id());
            user.setCreated(DateUtil.formatDateTime(adRequest.getInstall_time()));
            user.setIos_ifa(adRequest.getIos_ifa());
            user.setGoogle_aid(adRequest.getGoogle_aid());
            user.setGame_user_id(adRequest.getGame_user_id());
            user.setRevenue(adRequest.getRevenue());
            user.setRevenue_usd(adRequest.getRevenue_usd());
            user.setSite_id(adRequest.getSite_id());
            user.setSite_name(adRequest.getSite_name());
            DBJobManager manager = (DBJobManager)Version.CONTEXT.get(Constants.DB_JOB_MANAGER);
            manager.submitRequest(user);
        } catch (Exception e) {
            LOGGER.warn("handle is interrupted.");
            return CommandStatus.Fail;
        }
        return CommandStatus.Continue;
    }

}
