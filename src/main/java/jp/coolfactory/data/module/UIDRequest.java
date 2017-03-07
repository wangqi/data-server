package jp.coolfactory.data.module;

import jp.coolfactory.data.db.DBUtil;
import jp.coolfactory.data.util.StringUtil;

import java.util.HashMap;

/**
 * Created by wangqi on 7/3/2017.
 */
public class UIDRequest implements SQLRequest {

    private String action = null;
    private String account_key = null;
    private String source = null;
    private String app_key = null;
    private String stat_id = null;
    private String game_user_id = null;
    private String device_id = null;
    private String created = null;
    private double revenue = 0;
    private double revenue_usd = 0;

    private String ios_ifa = null;
    private String google_aid = null;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAccount_key() {
        return account_key;
    }

    public void setAccount_key(String account_key) {
        this.account_key = account_key;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getApp_key() {
        return app_key;
    }

    public void setApp_key(String app_key) {
        this.app_key = app_key;
    }

    public String getStat_id() {
        return stat_id;
    }

    public void setStat_id(String stat_id) {
        this.stat_id = stat_id;
    }

    public String getGame_user_id() {
        return game_user_id;
    }

    public void setGame_user_id(String game_user_id) {
        this.game_user_id = game_user_id;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public double getRevenue_usd() {
        return revenue_usd;
    }

    public void setRevenue_usd(double revenue_usd) {
        this.revenue_usd = revenue_usd;
    }

    public String getIos_ifa() {
        return ios_ifa;
    }

    public void setIos_ifa(String ios_ifa) {
        this.ios_ifa = ios_ifa;
    }

    public String getGoogle_aid() {
        return google_aid;
    }

    public void setGoogle_aid(String google_aid) {
        this.google_aid = google_aid;
    }

    @Override
    public String toSQL() {
        //Get device_id from ios_ifa or google_aid
        device_id = ios_ifa;
        if ( StringUtil.isEmptyString(device_id)  ) {
            device_id = google_aid;
        }
        if ( StringUtil.isEmptyString(device_id) || StringUtil.isEmptyString(game_user_id) ) {
            //Ignore null mapping.
            return null;
        } else {
            StringBuilder buf = new StringBuilder(200);
            StringBuilder valueBuf = new StringBuilder(200);
            HashMap<String, String> map = new HashMap<>();
            valueBuf.append("(");

            buf.append("insert ignore into ").append(DBUtil.getDatabaseSchema()).append(".ad_gameuser (");
            if (StringUtil.isNotEmptyString(account_key)) {
                buf.append("account_key,");
                valueBuf.append("'{account_key}',");
                map.put("account_key", StringUtil.validSQLInput(account_key));
            }
            if (StringUtil.isNotEmptyString(source)) {
                buf.append("source,");
                valueBuf.append("'{source}',");
                map.put("source", StringUtil.validSQLInput(source));
            }
            if (StringUtil.isNotEmptyString(stat_id)) {
                buf.append("stat_id,");
                valueBuf.append("'{stat_id}',");
                map.put("stat_id", StringUtil.validSQLInput(stat_id));
            }
            if (StringUtil.isNotEmptyString(app_key)) {
                buf.append("app_key,");
                valueBuf.append("'{app_key}',");
                map.put("app_key", StringUtil.validSQLInput(app_key));
            }
            if (StringUtil.isNotEmptyString(game_user_id)) {
                buf.append("game_user_id,");
                valueBuf.append("'{game_user_id}',");
                map.put("game_user_id", StringUtil.validSQLInput(game_user_id));
            }
            if (StringUtil.isNotEmptyString(device_id)) {
                buf.append("device_id,");
                valueBuf.append("'{device_id}',");
                map.put("device_id", StringUtil.validSQLInput(ios_ifa));
            }
            if (StringUtil.isNotEmptyString(created)) {
                buf.append("created,");
                valueBuf.append("'{created}',");
                map.put("created", StringUtil.validSQLInput(created));
            }
            buf.deleteCharAt(buf.length() - 1);
            buf.append(") values ");
            valueBuf.deleteCharAt(valueBuf.length() - 1);
            valueBuf.append(')');
            String sql = StringUtil.replaceKey(buf.append('\n').append(valueBuf.toString()).toString(), map);

//            if (revenue > 0) {
//                StringBuilder updateBuf = new StringBuilder(100);
//                updateBuf.append("update ").append(DBUtil.getDatabaseSchema()).append(".ad_gameuser ");
//                updateBuf.append("set revenue=revenue+{revenue}, revenue_usd=revenue_usd+{revenue_usd} ");
//                updateBuf.append("where account_key='{account_key}' and app_key='{app_key}' and source='{source}' and game_user_id='{game_user_id}' and device_id='{device_id}'");
//                map.put("revenue", "revenue");
//                map.put("revenue_usd", String.valueOf(revenue_usd));
//                map.put("account_key", account_key);
//                map.put("app_key", app_key);
//                map.put("source", source);
//                map.put("device_id", device_id);
//                map.put("game_user_id", game_user_id);
//                sql = sql + "; \n" + StringUtil.replaceKey(updateBuf.toString(), map);
//            }
            return sql;
        }
    }
}



