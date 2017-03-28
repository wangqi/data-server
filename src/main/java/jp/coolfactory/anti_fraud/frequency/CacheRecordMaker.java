package jp.coolfactory.anti_fraud.frequency;

import jp.coolfactory.data.util.DateUtil;
import jp.coolfactory.data.util.IPUtil;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;

/**
 * It's used to generate the key to store in frequency for a request.
 * Created by wangqi on 27/12/2016.
 */
public interface CacheRecordMaker {

    /**
     * The params should contain the following parameters:
     *   af_campaign_id, site_id, site_name, country_code, region, device_brand, device_carrier, device_model,
     *   language, device_ip, status, ios_ifa, ios_ifv, google_aid, os_version, publisher_id, publisher_name,
     *   tracking_id, click_date, install_date, jailbroke
     *
     * @param params
     * @return
     */
    default CacheRecord makeKey(HashMap<String, String> params) {
        String site_id = params.get("site_id");
        if ( site_id == null ) {
            return null;
        }
        String device_ip = params.get("device_ip");
        String device_ip_segment = device_ip;
        if ( device_ip == null ) {
            return null;
        } else {
            device_ip_segment = IPUtil.formatIPPrefix(device_ip, 32);
        }
        String key = site_id + "_" + device_ip_segment;
        String id = site_id + "_" + device_ip;
        String createdStr = params.get("install_date");
        if ( createdStr == null ) {
            return null;
        }
        LocalDateTime createdDate = DateUtil.convertIOS2Date(createdStr);
        if ( createdDate == null ) {
            createdDate = LocalDateTime.now();
        }
        CacheRecord record = new CacheRecord(key, id, createdDate.toInstant(ZoneOffset.UTC).toEpochMilli());
        return record;
    }

}
