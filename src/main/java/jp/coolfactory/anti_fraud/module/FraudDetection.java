package jp.coolfactory.anti_fraud.module;

import java.util.HashMap;

/**
 * Created by wangqi on 24/11/2016.
 */
public interface FraudDetection {
    /**
     * The HashMap is the parameters from Postback. It should contains:
         site_id={site_id}
         site_name={site_name}
         country_code={country_code}
         device_brand={device_brand}
         device_carrier={device_carrier}
         device_model={device_model}
         language={language}
         device_ip={device_ip}
         status={conversion_status}
         click_date={click_date}
         install_date={conversion_datetime}
         ios_ifa={ios_ifa}
         ios_ifv={ios_ifv}
         os_version={os_version}
         publisher_id={publisher_id}
         publisher_name={publisher_id}
         postback: The real postback url to send if true
     *
     * @param params
     * @return
     */
    Status evaluateInstall(HashMap params);


    /**
     * Try to determine if the detector is approperiate for this request.
     * @param params
     * @return True means if it should be used.
     */
    default boolean doesItApply(HashMap params) {
        return true;
    }

}
