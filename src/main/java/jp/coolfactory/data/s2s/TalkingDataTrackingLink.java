package jp.coolfactory.data.s2s;

import jp.coolfactory.data.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.HashSet;
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

    static final Logger LOGGER = LoggerFactory.getLogger(TalkingDataTrackingLink.class.getName());

    private static final HashMap<String, String[]> KEY_MAP = new HashMap<>();
    static {
        KEY_MAP.put(TuneKeyParamNames.DEVICE_IP, new String[]{"ip", "device_ip", "click_ip", "x-forwarded-for"});
        KEY_MAP.put(TuneKeyParamNames.GOOGLE_AID, new String[]{"aid", "adid", "androidid"});
        KEY_MAP.put(TuneKeyParamNames.IOS_IFA, new String[]{"idfa", "idfa_md5"});
        KEY_MAP.put(TuneKeyParamNames.USER_AGENT, new String[]{"useragent"});
        KEY_MAP.put(TuneKeyParamNames.SUB_CAMPAIGN, new String[]{"campaign"});
    }

    /**
     * Use it to construct the MAT S2S measurement URL.
     * Note, The 'publisher_id' and 'site_id' are mandatory in params
     *
     * @param params
     * @return
     */
    public String contructMATS2SLink(Map<String, String> params) {
        if ( (!params.containsKey(TuneKeyParamNames.MAT_PUB_ID) || (!params.containsKey(TuneKeyParamNames.MAT_SITE_ID))))  {
            LOGGER.error("No publisher_id or site_id found in params: ", params);
        }
        StringBuilder matS2SUrl = new StringBuilder(StringUtil.replaceKey(MAT_BASE_URL, params));
        HashSet<String> dupKeys = new HashSet<>();
        // Try to get device IP
        {
            String[] values = parseTuneParamValue(TuneKeyParamNames.DEVICE_IP, KEY_MAP, params);
            if ( values != null ) {
                String deviceIP = values[0];
                dupKeys.add(values[1]);
                matS2SUrl.append('&').append(TuneKeyParamNames.DEVICE_IP).append('=').append(deviceIP);
            }
        }
        // Try to get user_agent
        {
            String[] values = parseTuneParamValue(TuneKeyParamNames.USER_AGENT, KEY_MAP, params);
            if ( values != null ) {
                String userAgent = values[0];
                dupKeys.add(values[1]);
                matS2SUrl.append('&').append(TuneKeyParamNames.USER_AGENT).append('=').append(userAgent);
            }
        }
        // Try to get ios_ifa
        {
            String[] values = parseTuneParamValue(TuneKeyParamNames.IOS_IFA, KEY_MAP, params);
            if ( values != null ) {
                String ios_ifa = values[0];
                dupKeys.add(values[1]);
                matS2SUrl.append('&').append(TuneKeyParamNames.IOS_IFA).append('=').append(ios_ifa);
            }
        }
        // Try to get google aid
        {
            String[] values = parseTuneParamValue(TuneKeyParamNames.GOOGLE_AID, KEY_MAP, params);
            if ( values != null ) {
                String googleAid = values[0];
                dupKeys.add(values[1]);
                matS2SUrl.append('&').append(TuneKeyParamNames.DEVICE_IP).append('=').append(googleAid);
            }
        }
        // Try to get campaign
        {
            String[] values = parseTuneParamValue(TuneKeyParamNames.SUB_CAMPAIGN, KEY_MAP, params);
            if ( values != null ) {
                String campaign = values[0];
                dupKeys.add(values[1]);
                matS2SUrl.append('&').append(TuneKeyParamNames.SUB_CAMPAIGN).append('=').append(campaign);
            } else {
                // Set TalkingData's PATH as default campaign name
                String path = params.get(TuneKeyParamNames.TP_PATH);
                if (StringUtil.isNotEmptyString(path)) {
                    matS2SUrl.append('&').append(TuneKeyParamNames.SUB_CAMPAIGN).append('=').append(path);
                } else {
                    matS2SUrl.append('&').append(TuneKeyParamNames.SUB_CAMPAIGN).append('=').append("ad_track");
                }
            }
        }
        // Set sub-publisher to AD_TRACK
        matS2SUrl.append('&').append(TuneKeyParamNames.MAT_SUG_PUB).append("=ad_track");
        for ( String key : params.keySet() ) {
            if (TuneKeyParamNames.MAT_PUB_ID.equalsIgnoreCase(key)
                    || TuneKeyParamNames.MAT_SITE_ID.equalsIgnoreCase(key)
                    || TuneKeyParamNames.MAT_SUG_PUB.equalsIgnoreCase(key)
                    || TuneKeyParamNames.TP_PATH.equalsIgnoreCase(key)
                    || TuneKeyParamNames.TP_PROT.equalsIgnoreCase(key)
                    || TuneKeyParamNames.TP_HOST.equalsIgnoreCase(key)
                    || TuneKeyParamNames.TP_ACTION.equalsIgnoreCase(key)
                    || TuneKeyParamNames.TP_CHANNEL.equalsIgnoreCase(key)
                    || dupKeys.contains(key.toLowerCase())
                    ) {
                continue;
            } else {
                matS2SUrl.append('&').append(key).append('=').append(params.get(key));
            }
        }
        return matS2SUrl.toString();
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
            return false;
        }
        return true;
    }

    /**
     * Translate third-party's link to this server's format.
     *
     * @param thirdPartyLink
     * @return
     */
    @Override
    public String translateThirdPartyLink(String prot, String host, String path,
            String publisher_id, String site_id, String thirdPartyLink) {
        StringBuilder buf = new StringBuilder(200);
        try {
            URL url = new URL(thirdPartyLink);
            String tp_prot = url.getProtocol();
            String tp_host = url.getHost();
            String tp_path = url.getPath();
            String tp_queryString = url.getQuery();
            buf.append(prot).append("://").append(host);
            if ( StringUtil.isNotEmptyString(path) ) {
                if ( path.charAt(0) == '/' ) {
                    buf.append(path);
                } else {
                    buf.append('/').append(path);
                }
            }
            buf.append('?');
            if ( !tp_prot.equalsIgnoreCase("https")) {
                buf.append(TuneKeyParamNames.TP_PROT).append('=').append(tp_prot).append('&');
            }
            if ( !tp_host.equalsIgnoreCase("lnk0.com")) {
                buf.append(TuneKeyParamNames.TP_HOST).append('=').append(tp_host).append('&');
            }
            if ( StringUtil.isNotEmptyString(publisher_id) ) {
                buf.append(TuneKeyParamNames.MAT_PUB_ID).append('=').append(publisher_id).append('&');
            } else {
                return "No publisher_id";
            }
            if ( StringUtil.isNotEmptyString(site_id) ) {
                buf.append(TuneKeyParamNames.MAT_SITE_ID).append('=').append(site_id).append('&');
            } else {
                return "No site_id";
            }
            if ( StringUtil.isNotEmptyString(tp_path)) {
                buf.append(TuneKeyParamNames.TP_PATH).append('=');
                if ( tp_path.charAt(0) == '/' ) {
                    buf.append(tp_path.substring(1));
                } else {
                    buf.append(tp_path);
                }
                buf.append('&');
            }
            Map<String, String[]> params = StringUtil.url2Map(url);
            for ( String key : params.keySet() ) {
                String[] values = params.get(key);
                for ( String value : values ) {
                    buf.append(key).append('=').append(URLEncoder.encode(value, "utf-8")).append('&');
                }
            }
            if (buf.charAt(buf.length()-1) == '&') {
                buf.deleteCharAt(buf.length()-1);
            }
            return buf.toString();
        } catch ( MalformedURLException e ) {
            LOGGER.warn("The third-party URL is not in right format, ", e);
        } catch ( UnsupportedEncodingException upe) {
            LOGGER.warn("The third-party URL is not in right format, ", upe);
        }
        return buf.toString();
    }


}
