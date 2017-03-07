package jp.coolfactory.data.common;

import jp.coolfactory.data.Constants;
import jp.coolfactory.data.Version;
import jp.coolfactory.data.module.AdRequest;
import jp.coolfactory.data.module.SQLRequest;
import jp.coolfactory.data.module.UserRequest;
import jp.coolfactory.data.server.DBJobManager;
import jp.coolfactory.data.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Register an user into ad_reguser table
 *
 * Created by wangqi on 22/2/2017.
 */
public class AdRequestUserCommand implements Handler<AdRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdRequestUserCommand.class);

    public AdRequestUserCommand() {
    }

    @Override
    public CommandStatus handle(AdRequest adRequest) {
        try {
            UserRequest user = new UserRequest();
            if ( Constants.ACTION_INSTALL.equals(adRequest.getAction()) ) {
                user.setAccount_key(adRequest.getAccount_key());
                user.setAction(adRequest.getAction());
                user.setSource(adRequest.getSource());
                user.setApp_key(adRequest.getAppKey());
                user.setStat_id(adRequest.getStat_id());
                user.setSite_id(adRequest.getSite_id());
                user.setSite_name(adRequest.getSite_name());
                user.setCampaign_id(adRequest.getCampaign_id());
                user.setCampaign_name(adRequest.getCampaign_name());
                user.setCountry_code(adRequest.getCountry_code());
                user.setRegion_name(adRequest.getRegion_name());
                user.setCreated(DateUtil.formatDateTime(adRequest.getInstall_time()));
                user.setIp(adRequest.getInstall_ip());
                user.setIos_ifa(adRequest.getIos_ifa());
                user.setGoogle_aid(adRequest.getGoogle_aid());
                user.setGame_user_id(adRequest.getGame_user_id());
                user.setRevenue(adRequest.getRevenue());
                user.setRevenue_usd(adRequest.getRevenue_usd());
            } else if ( Constants.ACTION_PURCHASE.equals(adRequest.getAction()) ) {
                user.setAccount_key(adRequest.getAccount_key());
                user.setAction(adRequest.getAction());
                user.setSource(adRequest.getSource());
                user.setApp_key(adRequest.getAppKey());
                user.setStat_id(adRequest.getStat_id());
                user.setRevenue(adRequest.getRevenue());
                user.setRevenue_usd(adRequest.getRevenue_usd());
            } else if ( Constants.ACTION_LOGIN.equals(adRequest.getAction()) ) {
                user.setAccount_key(adRequest.getAccount_key());
                user.setAction(adRequest.getAction());
                user.setSource(adRequest.getSource());
                user.setApp_key(adRequest.getAppKey());
                user.setStat_id(adRequest.getStat_id());
                user.setCreated(DateUtil.formatDateTime(adRequest.getInstall_time()));
            }
            DBJobManager manager = (DBJobManager)Version.CONTEXT.get(Constants.DB_JOB_MANAGER);
            manager.submitRequest(user);
        } catch (Exception e) {
            LOGGER.warn("handle is interrupted.");
            return CommandStatus.Fail;
        }
        return CommandStatus.Continue;
    }

}
