package jp.coolfactory.data.util;

import jp.coolfactory.anti_fraud.db.CheckType;
import jp.coolfactory.anti_fraud.module.Status;
import org.apache.log4j.Logger;

import java.util.Set;

/**
 * Created by wangqi on 29/3/2017.
 */
public class PolicyCheckUtil {

    private final static Logger LOGGER = Logger.getLogger(PolicyCheckUtil.class.getName());

    /**
     * The 'quick' is true by default.
     *
     * @param type
     * @param paramValue If the paramValue is null or empty string, it will pass the test.
     * @return
     */
    public static final Status check(CheckType type, String paramValue,
                                     Set<String> includes, Set<String> excludes ) {
        return check(type, paramValue, true, includes, excludes);
    }

    /**
     * If the 'quick' is false, it will use prefix-matching to compare the paramValue and pattern in database.
     *
     * @param type
     * @param paramValue If the paramValue is null or empty string, it will pass the test.
     * @param quick
     * @return
     */
    public static final Status check(CheckType type, String paramValue, boolean quick,
                                     Set<String> includes, Set<String> excludes ) {
        Status status = Status.OK;
        /**
         * If the given paramValue is empty, it will pass the test.
         */
        if (paramValue == null || paramValue == "") {
            return status;
        }
        String value = paramValue.toLowerCase();
        if (quick) {
            if (includes.contains(value)) {
                status = Status.OK;
                if (LOGGER.isDebugEnabled())
                    LOGGER.debug("Value " + value + " is in include policy for type: " + type);
            } else if (excludes.contains(value)) {
                status = getDeniedStatus(type);
                if (LOGGER.isDebugEnabled())
                    LOGGER.debug("Value " + value + " is in exclude policy for type: " + type);
            } else {
                if (includes.size() > 0) {
                    if (LOGGER.isDebugEnabled())
                        LOGGER.debug("Value " + value + " is not in either include or exclude policy for type: " + type);
                    status = getDeniedStatus(type);
                }
            }
        } else {
            //Slow methods use the prefix matching
            boolean found = false;
            for (String pattern : includes) {
                if (value.startsWith(pattern)) {
                    found = true;
                    break;
                }
            }
            if (found) {
                status = Status.OK;
                if (LOGGER.isDebugEnabled())
                    LOGGER.debug("Value " + value + " is in prefix matching include policy for type: " + type);
            } else {
                for (String pattern : excludes) {
                    if (value.startsWith(pattern)) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    status = getDeniedStatus(type);
                    if (LOGGER.isDebugEnabled())
                        LOGGER.debug("Value " + value + " is in prefix matching exclude policy for type: " + type);
                }
            }
            if (!found && includes.size() > 0) {
                if (LOGGER.isDebugEnabled())
                    LOGGER.debug("Value " + value + " is not in either prefix matching include or prefix matching " +
                            "exclude policy for type: " + type);
                status = getDeniedStatus(type);
            }
        }

        return status;
    }

    public static final Status getDeniedStatus(CheckType type) {
        switch (type) {
            case COUNTRY:
                return Status.FORBIDDEN_COUNTRY;
            case DEVICE:
                return Status.FORBIDDEN_DEVICE;
            case LANG:
                return Status.FORBIDDEN_LANG;
            case OS:
                return Status.FORBIDDEN_OS;
            case CARRIER:
                return Status.FORBIDDEN_CARRIER;
            case IP:
                return Status.FORBIDDEN_IP;
            case JAILBROKE:
                return Status.FORBIDDEN_JAILBROKE;
            case DOWNLOAD_INTERVAL:
                return Status.FORBIDDEN_INTERVAL;
            default:
                return null;
        }
    }
}
