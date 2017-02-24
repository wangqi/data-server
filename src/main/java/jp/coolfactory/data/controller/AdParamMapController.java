package jp.coolfactory.data.controller;

import jp.coolfactory.data.db.DBUtil;
import jp.coolfactory.data.module.AdParamMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by wangqi on 22/2/2017.
 */
public class AdParamMapController implements Controller {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdParamMapController.class);

    private static final AdParamMapController instance = new AdParamMapController();

    private static final String SQL_LOAD = "select source, param_name, std_name from " +
            DBUtil.getDatabaseSchema() + ".ad_param_map";

    private static final AdParamMap AD_PARAM_MAP = new AdParamMap();

    private AdParamMapController() {
    }

    public static final AdParamMapController getInstance() {
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
        DBUtil.select(SQL_LOAD, AdParamMapController::loadDB);
    }

    /**
     * Translate the standard param name to source related name
     * @param stdParamName
     * @return
     */
    public String translateStdName(String stdParamName) {
        return AD_PARAM_MAP.getDefaultParam(stdParamName);
    }

    /**
     * Translate the name according to action.
     * @param source
     * @param stdParamName
     * @return
     */
    public String translateStdName(String source, String stdParamName) {
        return AD_PARAM_MAP.getParamName(source, stdParamName);
    }


    public static final void loadDB(ResultSet set) {
        HashMap<String,HashMap<String,String>> actionMap = new HashMap<>();
        try {
            while (set.next()) {
                String action = set.getString(1);
                HashMap<String,String> map = actionMap.get(action);
                if (map==null) {
                    map = new HashMap<>();
                    actionMap.put(action, map);
                }
                String paramName = set.getString(2);
                String stdParamName = set.getString(3);
                map.put(stdParamName, paramName);
            }
            for ( String action: actionMap.keySet() ) {
                HashMap<String,String> map = actionMap.get(action);
                AD_PARAM_MAP.putSourceParam(action, map);
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to load ad_param_map from db", e);
        }
    }
}
