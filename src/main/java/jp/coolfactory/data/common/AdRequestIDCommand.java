package jp.coolfactory.data.common;

import jp.coolfactory.data.Constants;
import jp.coolfactory.data.db.DBUtil;
import jp.coolfactory.data.module.AdRequest;
import jp.coolfactory.data.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Generate an unique ID for the request. The proirity is as follows:
 *  iOS
 *      IDFA > MAT_ID > IP + UserAgent + DeviceType + OS_Version
 *  Android
 *      Google AID > MAT_ID > IP + UserAgent + DeviceType + OS_Version
 *
 * Created by wangqi on 22/2/2017.
 */
public class AdRequestIDCommand implements Handler<AdRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdRequestIDCommand.class);

    @Override
    public CommandStatus handle(AdRequest adRequest) {
        try {
            if ( adRequest != null ) {
                boolean is_iOS = false;
                boolean is_Android = false;
                String stat_id = null;
                String deviceType = adRequest.getDevice_type();
                if (StringUtil.isNotEmptyString(deviceType)) {
                    if ( deviceType.indexOf("ios")>=0 ) {
                        is_iOS = true;
                    } else if ( deviceType.indexOf("android")>=0 ) {
                        is_Android = true;
                    }
                } else {
                    LOGGER.debug("deviceType is null, install_time: " + adRequest.getInstall_time());
                }

                //1st, check the ios_ifa and google_aid
                if ( is_iOS ) {
                    stat_id = adRequest.getIos_ifa();
                }
                if ( is_Android ) {
                    stat_id = adRequest.getGoogle_aid();
                }

                //2nd, check the source specific ID.
                if ( stat_id == null ) {
                    stat_id = adRequest.getPlat_id();
                }

                //3rd, give a default id
                if ( stat_id == null ) {
                    StringBuilder buf = new StringBuilder(200);
                    buf.append(adRequest.getUser_agent());
                    buf.append(adRequest.getDevice_type());
                    buf.append(adRequest.getInstall_ip());
                    buf.append(adRequest.getOs_version());
                    stat_id = buf.toString();
                }

                stat_id = StringUtil.generateHashBase64(stat_id);
                adRequest.setStat_id(stat_id);

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
