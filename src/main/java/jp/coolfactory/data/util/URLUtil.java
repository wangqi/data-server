package jp.coolfactory.data.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * It's used to format and encode URL
 * Created by wangqi on 31/1/2017.
 */
public class URLUtil {

    public static final String encodeURL(String url) {
        String[] parts = splitByChar(url, '?');
        if ( parts == null || parts.length!=2 ) {
            return url;
        } else {
            String schema = parts[0];
            StringBuilder buffer = new StringBuilder(200);
            buffer.append(schema).append("?");
            String[] params = splitByChar(parts[1], '&');
            if ( params == null ) {
                //Only one parameter like http://localhost/servlet?name=value
                String[] pair = splitByChar(parts[1], '=');
                if ( pair == null ) {
                    return url;
                }
                try {
                    return buffer
                            .append(pair[0])
                            .append("=")
                            .append(URLEncoder.encode(pair[1], "utf8"))
                            .toString();
                } catch (UnsupportedEncodingException e) {
                    return url;
                }
            }
            /**
             * http://global.ymtracking.com/conv?transaction_id=2ab70b976-fa8c-31a6-969915079ecce9c8afb4245eb3539a8e0025c5e499f0012&source=mat&client_ip=126.161.171.207&device_id=&idfa=A8E8A99C-C102-D490-00FF-5A9D5B3613E2&google_adv_id=&mac=&active_time=2017-01-31 01:02:26&adv_sub=tokyo
             */
            for ( String param : params ) {
                String[] pair = splitByChar(param, '=');
                if ( pair.length == 1 ) {
                    buffer.append(pair[0])
                            .append("=")
                            .append("&");
                } else if (pair.length == 2) {
                    try {
                        buffer.append(pair[0])
                                .append("=")
                                .append(URLEncoder.encode(pair[1], "utf8"))
                                .append("&");
                    } catch (UnsupportedEncodingException e) {
                        return url;
                    }
                }
            }
            buffer.deleteCharAt(buffer.length()-1);
            return buffer.toString();
        }
    }

    private static final String[] splitByChar(String str, char sep) {
        if ( str == null ) {
            return null;
        }
        int end = str.indexOf(sep);
        if ( end < 0 ) {
            return null;
        }
        ArrayList<String> parts = new ArrayList<>();
        int start = 0;
        while (end>start) {
            String part = str.substring(start, end);
            parts.add(part);
            start = end+1;
            end = str.indexOf(sep,start);
        }
        if ( start<str.length() ){
            parts.add(str.substring(start));
        }
        if ( parts.size()>0 ) {
            return parts.toArray(new String[parts.size()]);
        }
        return null;
    }
}
