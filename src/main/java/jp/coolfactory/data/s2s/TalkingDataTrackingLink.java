package jp.coolfactory.data.s2s;

import jp.coolfactory.data.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * The TalkingData Macros are as follow:
 *   __CALLBACK__
 *   __IDFA__
 *   __IP__
 *   __MAC__
 *   __OS__
 *   __SESSIONID__
 *   __TS__
 *   __UA__
 *
 * The parameter names include:
 *    ADID
 *    aid
 *    androidid
 *    cid
 *    click_id
 *    clicktime
 *    compaign
 *    creative
 *    customerid
 *    ideaid
 *    idfa
 *    idfa_md5
 *    imei_md5
 *    ip
 *    kwid
 *    LBS
 *    mac
 *    mac_md5
 *    momoid
 *    osversion
 *    pid
 *    session_id
 *    td_subid
 *    traceid
 *    uid
 *    useragent
 *
 */
public class TalkingDataTrackingLink implements TrackingLink {

    static final Logger LOGGER = LoggerFactory.getLogger(TrackingLink.class.getName());

    private static final HashMap<String, String[]> KEY_MAP = new HashMap<>();
    static {
        KEY_MAP.put(TuneKeyParamNames.DEVICE_IP, new String[]{"ip", "device_ip", "click_ip"});
        KEY_MAP.put(TuneKeyParamNames.GOOGLE_AID, new String[]{"aid", "adid", "androidid"});
        KEY_MAP.put(TuneKeyParamNames.IOS_IFA, new String[]{"idfa", "idfa_md5"});
        KEY_MAP.put(TuneKeyParamNames.USER_AGENT, new String[]{"useragent"});
        KEY_MAP.put(TuneKeyParamNames.SUB_CAMPAIGN, new String[]{"campaign"});
    }

    /**
     * It will create a TalkingData format tracking URL link
     *
     * @param params
     * @return
     */
    @Override
    public String contructThirdPartyS2SLink(Map<String, String> params) {
        String protocol = params.get(TuneKeyParamNames.TP_PROT);
        String host = params.get(TuneKeyParamNames.TP_HOST);
        String path = params.get(TuneKeyParamNames.TP_PATH);
        if (StringUtil.isEmptyString(protocol)) {
            protocol = "https";
        }
        if (StringUtil.isEmptyString(host)) {
            host = "lnk0.com";
        }
        if (StringUtil.isEmptyString(path)) {
            LOGGER.warn("Path is empty");
            path = "/";
        } else {
            path = "/" + path;
        }
        StringBuilder urlBuf = new StringBuilder(200);
        urlBuf.append(protocol).append("://").append(host).append(path).append("?t=1");
        for (String key : params.keySet() ){
            if ( TuneKeyParamNames.MAT_PUB_ID.equalsIgnoreCase(key) ) {
                continue;
            } else if ( TuneKeyParamNames.MAT_SITE_ID.equalsIgnoreCase(key) ) {
                continue;
            } else if ( TuneKeyParamNames.TP_PROT.equalsIgnoreCase(key) ) {
                continue;
            } else if ( TuneKeyParamNames.TP_HOST.equalsIgnoreCase(key) ) {
                continue;
            } else if ( TuneKeyParamNames.TP_PATH.equalsIgnoreCase(key) ) {
                continue;
            } else {
                urlBuf.append('&').append(key).append('=').append(params.get(key));
            }
        }
        return urlBuf.toString();
    }

    /**
     * Let's the third-party params decide if it needs to do redirect.
     * For TalkingData, it uses 'action' to check if it's a S2S or click tracking. If the value is 'none', then no redirection needed.
     *
     * @param params
     * @return
     */
    @Override
    public boolean isRedirected(Map<String, String> params) {
        String value = params.get("action");
        if ( StringUtil.isNotEmptyString(value) && value.equalsIgnoreCase("none") ) {
            return true;
        }
        return false;
    }


}
