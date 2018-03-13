package jp.coolfactory.data.s2s;

import jp.coolfactory.data.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * The TrackingLink is used to parse specific attribution system's tracking link parameters
 * and construct a TUNE's S2S measurement link. The TrackingLinkServlet will use it to
 * send server-to-server tracking information.
 */
public interface TrackingLink {

    static final Logger LOGGER = LoggerFactory.getLogger(TrackingLink.class.getName());

    /**
     * It's the base part of MAT's S2S tracking url.
     */
    public static String MAT_BASE_URL = "https://{publisher_id}.api-01.com/serve?action=click&publisher_id={publisher_id}&site_id={site_id}&response_format=json";

    /**
     * Parse MAT's required parameters from param map. Because third-party system has their own parameter name, we
     * map those names to MAT's. For example, TalkingData call IP field as 'click_ip' while MAT calls it 'device_ip'.
     * We try to get the IP by 'click_ip' first, if it does not exist. We fall back to 'device_ip'.
     * If no given parameter found, it will return an null
     *
     * @param tuneKey
     * @param keyMap
     * @param params
     * @return A string array with two elements. First element is the value, second is the final third-party key name.
     */
    public default String[] parseTuneParamValue(String tuneKey, Map<String, String[]> keyMap, Map<String, String> params) {
        /**
         * Try to get device IP
         * 1). Step 1, check if the URL contains 'click_ip', which is TalkingData's parameter
         * 2). Step 2, if no click_ip, then check if the params contains 'device_ip' directly.
         */
        String[] thirdPartyKeys = keyMap.get(tuneKey);
        String[] newThirdPartyKeys = null;
        if ( thirdPartyKeys != null ) {
            newThirdPartyKeys = new String[thirdPartyKeys.length+1];
            System.arraycopy(thirdPartyKeys, 0, newThirdPartyKeys, 0, thirdPartyKeys.length);
            newThirdPartyKeys[thirdPartyKeys.length] = tuneKey;
        } else {
            newThirdPartyKeys = new String[1];
            newThirdPartyKeys[0] = tuneKey;
        }
        String[] values = new String[2];
        for ( String keyName : newThirdPartyKeys ) {
            String value = params.get(keyName.toLowerCase());
            if ( StringUtil.isNotEmptyString(value) ) {
                values[0] = value;
                values[1] = keyName.toLowerCase();
                return values;
            }
        }
        LOGGER.info("No " + tuneKey + " found for contrctMATS2SLink");
        return null;
    }

    /**
     * It's the main entry method for processing tracking links. It will parse the parameter map from HttpServletRequest
     * and generate both MAT and third-party tracking URL in string. It will also decide if the third-party URL will return
     * redirect response or not.
     *
     * @param params
     * @return
     */
    public default TrackingLinkRecord processTracking(Map<String, String[]> params ) {
        HashMap<String, String> singleValueParams = new HashMap<>();
        TrackingLinkRecord record = new TrackingLinkRecord();
        if ( params != null ) {
            for (String key : params.keySet()) {
                String[] values = params.get(key);
                if (values != null && values.length > 0) {
                    String value = values[0];
                    singleValueParams.put(key.toLowerCase(), value);
                }
            }
            // Construct MAT and third-party tracking URL
            String matS2SUrl = contructMATS2SLink(singleValueParams);
            String thirdPartyS2SUrl = contructThirdPartyS2SLink(singleValueParams);
            boolean isRedirect = isRedirected(singleValueParams);
            record.setMatS2SUrl(matS2SUrl);
            record.setThirdPartyS2SUrl(thirdPartyS2SUrl);
            record.setRedirect(isRedirect);
        } else {
            LOGGER.info("The params is null");
        }
        return record;
    }

    /**
     * Use it to construct the MAT S2S measurement URL.
     * Note, The 'publisher_id' and 'site_id' are mandatory in params
     *
     * @param params
     * @return
     */
    public String contructMATS2SLink(Map<String, String> params);

    /**
     * It's used to generate third-party tracking URL links. The sub-class will implement it.
     * @param params
     * @return
     */
    public String contructThirdPartyS2SLink(Map<String, String> params);

    /**
     * Let's the third-party params decide if it needs to do redirect.
     * @param params
     * @return
     */
    public boolean isRedirected(Map<String, String> params);

}
