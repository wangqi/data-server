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
    public static String MAT_BASE_URL = "https://{publisher_id}.api-01.com/serve?action=click&publisher_id={publisher_id}&site_id={site_id}";

    /**
     * First, get all third-party url param names by given Tune's MAT name, which are already configured in TuneKeyParamNames interface.
     * Second, loop all possible names and get the url param values. It's a string array.
     * If found, return the array. Otherwise, return an empty array
     *
     * @param tuneKey
     * @param params
     * @return The String array which contains the param value. It may be an empty array but never be null.
     */
    public default String[] parseTuneParam(String tuneKey, Map<String,String[]> params) {
        String[] defaultValue = new String[0];
        String[] thirdPartyNames = params.get(tuneKey);
        if ( thirdPartyNames == null ) {
            return defaultValue;
        }
        for (String thirdPartyName : thirdPartyNames) {
            String[] values = params.get(thirdPartyName);
            if ( values != null && values.length>0 ) {
                return values;
            }
        }
        return defaultValue;
    }

    /**
     * First, get all third-party url param names by given Tune's MAT name, which are already configured in TuneKeyParamNames interface.
     * Second, loop all possible names and get the url param values. It's a string array.
     * If found, return the element of the array. Otherwise, return null
     *
     * @param tuneKey
     * @param params
     * @return The value for given param. Return null if not found
     */
    public default String parseTuneParamFirstValue(String tuneKey, Map<String,String[]> params) {
        String[] values = parseTuneParam(tuneKey, params);
        if ( values.length>0 ) {
            return values[0];
        } else {
            return null;
        }
    }

    /**
     * Use it to construct the MAT S2S measurement URL.
     * Note, The 'publisher_id' and 'site_id' are mandatory in params
     *
     * @param params
     * @return
     */
    public default String contructMATS2SLink(Map<String, String> params) {
        if ( (!params.containsKey(TuneKeyParamNames.MAT_PUB_ID) || (!params.containsKey(TuneKeyParamNames.MAT_SITE_ID))))  {
            LOGGER.error("No publisher_id or site_id found in params: ", params);
        }
        StringBuilder matS2SUrl = new StringBuilder(StringUtil.replaceKey(MAT_BASE_URL, params));
        for ( String key : params.keySet() ) {
            if (TuneKeyParamNames.MAT_PUB_ID.equalsIgnoreCase(key) || TuneKeyParamNames.MAT_SITE_ID.equalsIgnoreCase(key) ) {
                continue;
            } else {
                matS2SUrl.append('&').append(key).append('=').append(params.get(key));
            }
        }
        return matS2SUrl.toString();
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
                    singleValueParams.put(key, value);
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
