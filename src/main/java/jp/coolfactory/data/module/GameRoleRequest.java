package jp.coolfactory.data.module;

import jp.coolfactory.data.Constants;
import jp.coolfactory.data.db.DBUtil;
import jp.coolfactory.data.util.StringUtil;

import java.util.HashMap;

/**
 * Created by wangqi on 7/3/2017.
 */
public class GameRoleRequest implements SQLRequest {

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
    private String site_id = null;
    private String site_name = null;

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

    public String getSite_id() {
        return site_id;
    }

    public void setSite_id(String site_id) {
        this.site_id = site_id;
    }

    public String getSite_name() {
        return site_name;
    }

    public void setSite_name(String site_name) {
        this.site_name = site_name;
    }

    @Override
    public String toSQL() {
        //Get device_id from ios_ifa or google_aid
        device_id = ios_ifa;
        if ( StringUtil.isEmptyString(device_id)  ) {
            device_id = google_aid;
        }
        StringBuilder buf = new StringBuilder(200);
        StringBuilder valueBuf = new StringBuilder(200);
        HashMap<String, String> map = new HashMap<>();
        valueBuf.append("(");

        if ( !Constants.ACTION_PURCHASE.equals(action) ) {
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
            } else {
                //Make an empty game_user_id because it's a key in the table.
                buf.append("game_user_id,");
                valueBuf.append("'{game_user_id}',");
                map.put("game_user_id", "");
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
            if (StringUtil.isNotEmptyString(site_id)) {
                buf.append("site_id,");
                valueBuf.append("'{site_id}',");
                map.put("site_id", StringUtil.validSQLInput(site_id));
            }
            if (StringUtil.isNotEmptyString(site_name)) {
                buf.append("site_name,");
                valueBuf.append("'{site_name}',");
                map.put("site_name", StringUtil.validSQLInput(site_name));
            }
            buf.append("revenue,");
            valueBuf.append("{revenue},");
            map.put("revenue", "0.0");
            buf.append("revenue_usd,");
            valueBuf.append("{revenue_usd},");
            map.put("revenue_usd", "0.0");

            buf.deleteCharAt(buf.length() - 1);
            buf.append(") values ");
            valueBuf.deleteCharAt(valueBuf.length() - 1);
            valueBuf.append(')');
            String sql = StringUtil.replaceKey(buf.append('\n').append(valueBuf.toString()).toString(), map);

            return sql;
        } else {
            buf.append("update ").append(DBUtil.getDatabaseSchema()).append(".ad_gameuser ");
            if (revenue > 0) {
                buf.append("set revenue=revenue+{revenue}, revenue_usd=revenue_usd+{revenue_usd} ");
                buf.append("where account_key='{account_key}' and app_key='{app_key}' and source='{source}' and stat_id='{stat_id}' and game_user_id='{game_user_id}' and site_name='{site_name}'");
                map.put("revenue", String.valueOf(revenue));
                map.put("revenue_usd", String.valueOf(revenue_usd));
                map.put("account_key", account_key);
                map.put("app_key", app_key);
                map.put("source", source);
                map.put("stat_id", stat_id);
                map.put("game_user_id", game_user_id);
                map.put("site_name", site_name);
                String sql = StringUtil.replaceKey(buf.toString(), map);
                return sql;
            }
        }

        return null;
    }
}



