package jp.coolfactory.data.common;

import jp.coolfactory.data.module.AdRequest;
import jp.coolfactory.data.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
                if (StringUtil.isNotEmptyString(adRequest.getIos_ifa())) {
                    is_iOS = true;
                } else if (StringUtil.isNotEmptyString(adRequest.getGoogle_aid())) {
                    is_Android = true;
                } else {
                    //Try to guess
                    if ( StringUtil.isNotEmptyString(deviceType) ) {
                        deviceType = deviceType.toLowerCase();
                        if ( deviceType.indexOf("ios")>=0 || deviceType.indexOf("iphone")>=0 || deviceType.indexOf("ipad")>=0 || deviceType.indexOf("ipod")>=0 ) {
                            is_iOS = true;
                        } else {
                            is_Android = true;
                        }
                        LOGGER.debug("Both ios_ifa and Android is null, Try to guess via device_type: "
                                + adRequest.getDevice_type()
                                + ", result is: " + (is_iOS?"ios":"android"));
                    }
                }

                //1st, check the ios_ifa and google_aid
                if ( is_iOS ) {
                    stat_id = adRequest.getIos_ifa();
                }
                if ( is_Android ) {
                    stat_id = adRequest.getGoogle_aid();
                }

                /**
                 * If there is no idfa / aid, it's better to caculate
                 */
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
