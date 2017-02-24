package jp.coolfactory.data.util;

/**
 * Created by wangqi on 22/2/2017.
 */
public class StringUtil {

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
}
