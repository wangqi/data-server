package jp.coolfactory.data.common;

import jp.coolfactory.data.Constants;
import jp.coolfactory.data.Version;
import jp.coolfactory.data.module.AdRequest;
import jp.coolfactory.data.module.GameRoleRequest;
import jp.coolfactory.data.server.DBJobManager;
import jp.coolfactory.data.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An device may be mapped to multi game role in game. This object is used to track different roles under one device.
 *
 * Created by wangqi on 22/2/2017.
 */
public class AdRequestGameRoleCommand implements Handler<AdRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdRequestGameRoleCommand.class);

    public AdRequestGameRoleCommand() {
    }

    @Override
    public CommandStatus handle(AdRequest adRequest) {
        try {

            if ( Constants.ACTION_PURCHASE.equals(adRequest.getAction()) ) {
                //Update the revenue
                GameRoleRequest user = new GameRoleRequest();
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
            } else {
                /**
                 * No matter what events we have, insert a new game role into ad_gameuser table.
                 */
                GameRoleRequest user = new GameRoleRequest();
                user.setAccount_key(adRequest.getAccount_key());
                user.setAction(adRequest.getAction());
                user.setSource(adRequest.getSource());
                user.setApp_key(adRequest.getAppKey());
                user.setStat_id(adRequest.getStat_id());
                user.setCreated(DateUtil.formatDateTime(adRequest.getInstall_time()));
                user.setIos_ifa(adRequest.getIos_ifa());
                user.setGoogle_aid(adRequest.getGoogle_aid());
                user.setGame_user_id(adRequest.getGame_user_id());
                user.setRevenue(0);
                user.setRevenue_usd(0);
                user.setSite_id(adRequest.getSite_id());
                user.setSite_name(adRequest.getSite_name());
                DBJobManager manager = (DBJobManager)Version.CONTEXT.get(Constants.DB_JOB_MANAGER);
                manager.submitRequest(user);
            }
        } catch (Exception e) {
            LOGGER.warn("handle is interrupted.");
            return CommandStatus.Fail;
        }
        return CommandStatus.Continue;
    }

}
