package jp.coolfactory.anti_fraud.module;

import jp.coolfactory.data.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;

/**
 * Created by wangqi on 16/1/2017.
 */
public class FraudInstallSpeedDetection implements FraudDetection {

    private final static Logger LOGGER = LoggerFactory.getLogger(FraudInstallSpeedDetection.class.getName());

    private final static int THRESHOLD = 60;

    /**
     * If the click time is too close to the install time, said it's less than 15 seconds. Then the
     * conversion is suspicious.
     *
     * The HashMap is the parameters from Postback. It should contains:
     * site_id={site_id}
     * site_name={site_name}
     * country_code={country_code}
     * device_brand={device_brand}
     * device_carrier={device_carrier}
     * device_model={device_model}
     * language={language}
     * device_ip={device_ip}
     * status={conversion_status}
     * click_date={click_date}
     * install_date={conversion_datetime}
     * ios_ifa={ios_ifa}
     * ios_ifv={ios_ifv}
     * os_version={os_version}
     * publisher_id={publisher_id}
     * publisher_name={publisher_id}
     * postback: The real postback url to send if true
     *
     * @param params
     * @return
     */
    @Override
    public Status evaluateInstall(HashMap params) {
        String click_date = (String)params.get("click_date");
        String install_date = (String)params.get("install_date");
        LocalDateTime clickDateTime = DateUtil.convertIOS2Date(click_date);
        LocalDateTime installDateTime = DateUtil.convertIOS2Date(install_date);
        if ( clickDateTime != null && install_date != null ) {
            Instant clickInstant = clickDateTime.toInstant(ZoneOffset.UTC);
            Instant installInstant = installDateTime.toInstant(ZoneOffset.UTC);
            int interval = (int)((installInstant.toEpochMilli()-clickInstant.toEpochMilli())/1000);
            if ( interval < THRESHOLD ) {
                return Status.FORBIDDEN_INTERVAL;
            } else {
                return Status.OK;
            }
        }
        return Status.OK;
    }

}
