package jp.coolfactory.data.controller;

import com.google.common.collect.ImmutableMap;
import jp.coolfactory.data.db.DBUtil;
import jp.coolfactory.data.module.AdApp;
import jp.coolfactory.data.module.AdParamMap;
import jp.coolfactory.data.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wangqi on 22/2/2017.
 */
public class AdAppController implements Controller {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdAppController.class);

    private static final AdAppController instance = new AdAppController();

    private static final String SQL_LOAD = "select app_key, app_name, src_timezone, dst_timezone from " +
            DBUtil.getDatabaseSchema() + ".ad_app";

    private static ConcurrentHashMap<String, AdApp> AD_APP_MAP = new ConcurrentHashMap<>();

    private AdAppController() {
    }

    public static final AdAppController getInstance() {
        return instance;
    }

    /**
     * Initialize the AdParamMap here.
     */
    public void init() {
        reload();
    }

    /**
     * Reload the setting from database if necessary.
     */
    public void reload() {
        DBUtil.select(SQL_LOAD, AdAppController::loadDB);
    }

    /**
     * Get the App object.
     * @param appKey
     * @return
     */
    public AdApp getApAdd(String appKey) {
        if ( appKey != null ) {
            return AD_APP_MAP.get(appKey);
        } else {
            return null;
        }
    }

    /**
     * Get the app specific timezone, or UTC+8 as default.
     * @param appKey
     * @return
     */
    public ZoneId getSrcZoneId(String appKey) {
        AdApp app = getApAdd(appKey);
        try {
            if ( app != null ) {
                return ZoneId.of(app.getSrcTimeZone());
            }
        } catch (Exception e) {
            LOGGER.warn("Failed to get src_timezone for App: " + app, e);
        }
        return ZoneId.of("Asia/Shanghai");
    }

    /**
     * Get the app specific timezone, or UTC+8 as default.
     * @param appKey
     * @return
     */
    public ZoneId getDstZoneId(String appKey) {
        AdApp app = getApAdd(appKey);
        try {
            if ( app != null ) {
                return ZoneId.of(app.getDstTimeZone());
            }
        } catch (Exception e) {
            LOGGER.warn("Failed to get dst_timezone for App: " + app, e);
        }
        return ZoneId.of("Asia/Shanghai");
    }

    /**
     * Parse the date by app specific timezone.
     * @param appKey
     * @param value
     * @return
     */
    public ZonedDateTime parseDateString(String appKey, String value) {
        ZonedDateTime zonedDateTime = null;
        ZoneId srcZoneId = getSrcZoneId(appKey);
        ZoneId dstZoneId = getDstZoneId(appKey);
        try {
            LocalDateTime dateTime = DateUtil.convertIOS2Date(value);
            if ( srcZoneId != dstZoneId ) {
                //Change the date to dst timezone
                zonedDateTime = DateUtil.changeTimeZone(dateTime, srcZoneId, dstZoneId);
            }
        } catch (Exception e) {
            LOGGER.warn("Failed to parse date string: "+value + ", appkey:"+appKey, e);
        }
        return zonedDateTime;
    }

    public static final void loadDB(ResultSet set) {
        HashMap<String,AdApp> tempMap = new HashMap<>();
        try {
            while (set.next()) {
                String app_key = set.getString(1);
                String app_name = set.getString(2);
                String src_timezone = set.getString(3);
                String dst_timezone = set.getString(4);
                AdApp app = new AdApp();
                app.setAppKey(app_key);
                app.setAppName(app_name);
                app.setSrcTimeZone(src_timezone);
                app.setDstTimeZone(dst_timezone);
                tempMap.put(app_key, app);
            }
            //Refresh the old map by copy-on-write method
            AD_APP_MAP = new ConcurrentHashMap<>(tempMap);
        } catch (SQLException e) {
            LOGGER.error("Failed to load ad_param_map from db", e);
        }
    }
}
