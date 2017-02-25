package jp.coolfactory.data.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by wangqi on 22/2/2017.
 */
public class StringUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(StringUtil.class);

    public static final boolean isNotEmptyString(String str) {
        if(str != null && !str.isEmpty()) {
            return true;
        }
        return false;
    }

    public static final boolean isEmptyString(String str) {
        if(str == null || str.isEmpty()) {
            return true;
        }
        return false;
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
}
