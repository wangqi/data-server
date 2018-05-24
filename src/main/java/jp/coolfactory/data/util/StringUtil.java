package jp.coolfactory.data.util;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.MySQLCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by wangqi on 22/2/2017.
 */
public class StringUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(StringUtil.class);

    public static final boolean isNotEmptyString(String str) {
        if(str != null && !str.isEmpty() && !str.equals("null") ) {
            return true;
        }
        return false;
    }

    public static final boolean isEmptyString(String str) {
        if(str == null || str.isEmpty() || str.equals("null") ) {
            return true;
        }
        return false;
    }

    /**
     * Extract an attribute from JSON by using string methods.
     * @param json
     * @return
     */
    public static String extractAttrFromJson(String name, String json) {
        if (StringUtil.isEmptyString(name)) {
            return "";
        }
        if (StringUtil.isEmptyString(json)) {
            return "";
        }
        int idx = json.indexOf(name);
        if ( idx > 0 ) {
            idx = idx + name.length();
            int colon_idx = json.indexOf(':', idx);
            if ( colon_idx > 0 ) {
                // jump over "
                colon_idx += 2;
                int end_idx = json.indexOf('"', colon_idx);
                if ( end_idx > 0 && colon_idx < end_idx ) {
                    return json.substring(colon_idx, end_idx);
                }
            }
        }
        return "";
    }

    /**
     * Parse the string like '\\u3423' to char
     * @param str
     * @param enc
     * @return
     */
    public static String parseUnicodeEscaped(String str, String enc) {
        String encoding = enc;
        if ( StringUtil.isEmptyString(encoding) ) {
            encoding = "utf8";
        }
        if ( StringUtil.isEmptyString(str) ) {
            return "";
        }
        StringBuilder buf = new StringBuilder(20);
        int idx = str.indexOf("\\u");
        if ( idx > 0 ) {
            buf.append(str.substring(0, idx));
        } else if ( idx<0 ) {
            // No escaped string found
            return str;
        }
        int lastIdx = idx;
        try {
            while ( idx >= 0 ) {
                int startIdx = idx + 2;
                int endIdx = idx + 6;
                if (endIdx <= str.length() && startIdx < endIdx) {
                    int hexVal = Integer.parseInt(str.substring(startIdx, endIdx), 16);
                    buf.append((char) hexVal);
                }
                lastIdx = endIdx;
                idx = str.indexOf("\\u", endIdx);
            }
        } catch ( Exception e ) {
            LOGGER.warn("Failed to parse unicode escaped string: " + str + ", Error: " + e.getMessage());
        }
        if ( lastIdx>0 && lastIdx<str.length() ) {
            buf.append(str.substring(lastIdx));
        }
        return buf.toString();
    }

    /**
     * Generate a Base64 of SHA-256 string
     * @param string
     * @return
     */
    public static String generateHashBase64(String string) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(string.getBytes("UTF-8"));
            byte[] digest = md.digest();
            String base64 = new String(Base64.getEncoder().encode(digest));
            return base64;
        } catch (NoSuchAlgorithmException e) {
            LOGGER.warn("No SHA-256 algorithm", e);
        } catch (UnsupportedEncodingException e) {
            LOGGER.warn("UTF-8 is not supported", e);
        }
        return null;
    }

    /**
     * If the os_version has more than one dot, for example 10.2.1, then compact it to 10.2
     *
     * @param os_version
     * @return
     */
    public static String compactOsVersion(String os_version) {
        if ( StringUtil.isNotEmptyString(os_version))  {
            int lastIndex = -1;
            boolean moreThanOne = false;
            for ( int i=os_version.length()-1; i>0; i-- ) {
                char ch = os_version.charAt(i);
                if ( ch == '.' ) {
                    if ( lastIndex<0 ) {
                        lastIndex = i;
                    } else {
                        moreThanOne = true;
                        break;
                    }
                }
            }
            if ( moreThanOne && lastIndex > 0 ) {
                os_version = os_version.substring(0, lastIndex);
                return os_version;
            }
        }
        return os_version;
    }

    /**
     * It is used by URLJob and AdRequest's toString method.
     * @param value
     * @return
     */
    public static final String format_str(Object value) {
        if ( value == null ) {
            return "";
        }
        return value.toString();
    }

    /**
     * Replace the string with given {name} variables
     * O. If escaping '{', you can use '{{'. For example, 'param={{data}}' will be replaced as 'param={data}'.
     *    Note the encoding curly '}', you need to put double curly '}}' too.
     * O. If key is not found, for example, 'param={no_such_key}' with be replaced to 'param='
     * O. If the '{' is the last char, it will be removed. For example 'https://www.coolfactory.jp/test{' will be 'https://www.coolfactory.jp/test'
     * O. Empty key will be removed. For example, 'test={}' will be 'test='
     * O. Don't support the embeded key. For example: 'param={key={value}}' will be replaced to 'param='
     *
     *
     * @param string
     * @param map
     * @return
     */
    public static String replaceKey(String string, Map<String, String> map) {
        if (isEmptyString(string) ) {
            return string;
        }
        StringBuilder buf = new StringBuilder(string.length()*2);
        int i = 0;
        while ( i<string.length() ) {
            char ch = string.charAt(i);
            if ( ch == '{' ) {
                if (i + 1 < string.length()) {
                    if (string.charAt(i + 1) != '{') {
                        //Procss the key
                        int end = string.indexOf('}', i);
                        if (end > i + 1) {
                            String key = string.substring(i + 1, end);
                            String value = map.get(key);
                            if (value != null) {
                                buf.append(value);
                                i = end + 1;
                            } else {
                                i = end + 1;
                            }
                        } else if (end == i + 1) {
                            //Empty key {}
                            i = i + 2;
                        } else {
                            //Not found ending {
                            i = i + 1;
                        }
                    } else {
                        //It's an escape {{, jump two chars forward
                        buf.append(ch);
                        i = i + 2;
                    }
                } else {
                    //i is the last char but previous is '{'
                    i++;
                }
            } else if ( ch == '}') {
                //Orphan '}'
                if (i + 1 < string.length()) {
                    if (string.charAt(i + 1) == '}') {
                        buf.append(ch);
                        i+=2;
                    } else {
                        i++;
                    }
                } else {
                    i++;
                }
            } else {
                buf.append(ch);
                i++;
            }
        }
        return buf.toString();
    }

    /**
     * To prevent the SQL injection happens.
     * @param sql
     * @return
     */
    public static String validSQLInput(String sql) {
        return ESAPI.encoder().encodeForSQL(new MySQLCodec(MySQLCodec.Mode.ANSI), sql);
    }

    /**
     * Parse the parameters of a URL to HashMap
     * @param url
     * @return
     * @throws UnsupportedEncodingException
     */
    public static Map<String, String[]> url2Map(URL url) throws UnsupportedEncodingException {
        final Map<String, String[]> query_pairs = new HashMap<String, String[]>();
        final String query = url.getQuery();
        if (StringUtil.isEmptyString(query)) {
            return query_pairs;
        }
        final String[] pairs = query.split("&");
        for (String pair : pairs) {
            final int idx = pair.indexOf("=");
            final String key = idx > 0 ? URLDecoder.decode(pair.substring(0, idx), "UTF-8") : pair;
            final String value = idx > 0 && pair.length() > idx + 1 ? URLDecoder.decode(pair.substring(idx + 1), "UTF-8") : "";
            if (!query_pairs.containsKey(key)) {
                String[] values = new String[1];
                values[0] = value;
                query_pairs.put(key, values);
            } else {
                String[] oldValues = query_pairs.get(key);
                String[] newValues = new String[oldValues.length+1];
                System.arraycopy(oldValues, 0, newValues, 0, oldValues.length);
                newValues[oldValues.length] = value;
                query_pairs.put(key, newValues);
            }
        }
        return query_pairs;
    }

    /**
     * Parse the parameters of a URL to HashMap
     * @param url
     * @return
     * @throws UnsupportedEncodingException
     */
    public static Map<String, String> url2MapSingle(URL url) throws UnsupportedEncodingException {
        final Map<String, String> query_pairs = new HashMap<String, String>();
        final String query = url.getQuery();
        if (StringUtil.isEmptyString(query)) {
            return query_pairs;
        }
        final String[] pairs = query.split("&");
        for (String pair : pairs) {
            final int idx = pair.indexOf("=");
            final String key = idx > 0 ? URLDecoder.decode(pair.substring(0, idx), "UTF-8") : pair;
            final String value = idx > 0 && pair.length() > idx + 1 ? URLDecoder.decode(pair.substring(idx + 1), "UTF-8") : "";
            if (!query_pairs.containsKey(key)) {
                query_pairs.put(key, value);
            }
        }
        return query_pairs;
    }
}
