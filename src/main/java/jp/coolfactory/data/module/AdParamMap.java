package jp.coolfactory.data.module;

import com.google.common.collect.ImmutableMap;
import jp.coolfactory.data.util.StringUtil;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Because every statistic channel may have their unique name for a parameter. For example, MAT's 'created' is equals to
 * Appsflyer's 'install_time'. They are same parameter with different names. To fix this problem, I use a map to translate
 * third-party names to standard names in our system.
 *
 *
 * Created by wangqi on 22/2/2017.
 */
public class AdParamMap {

    private ImmutableMap<String, String> defaultSourceMap = ImmutableMap.of();
    private ConcurrentHashMap<String, ImmutableMap<String, String> > sourceMap = new ConcurrentHashMap<>();

    /**
     * Add an paramName to stdParamName mappming into cache.
     * If the source is empty, use the default source map.
     * @param source
     * @param map
     */
    public void putSourceParam(String source, HashMap<String,String> map) {
        if ( StringUtil.isEmptyString(source) ) {
            defaultSourceMap = ImmutableMap.copyOf(map);
        } else {
            ImmutableMap<String, String> im_map = sourceMap.get(source);
            if ( im_map == null ) {
                im_map = ImmutableMap.copyOf(map);
                sourceMap.put(source, im_map);
            }
        }
    }

    /**
     * Return the default paramName according to stdParamName.
     * @param stdParamName
     * @return
     */
    public String getDefaultParam(String stdParamName) {
        return defaultSourceMap.get(stdParamName);
    }

    /**
     * Find the standard param name's real name in given source. For example, the standard 'install_time'
     * is called 'created' in MAT.
     *
     * @param source
     * @param stdParamName
     * @return
     */
    public String getParamName(String source, String stdParamName) {
        String paramName = null;
        if ( StringUtil.isNotEmptyString(source) ) {
            ImmutableMap<String, String> im_map = sourceMap.get(source);
            if ( im_map != null ) {
                paramName = im_map.get(stdParamName);
            }
        }
        if ( paramName == null ) {
            paramName = defaultSourceMap.get(stdParamName);
        }
        return paramName;
    }
}
