package jp.coolfactory.data.module;

import jp.coolfactory.data.Constants;
import jp.coolfactory.data.db.DBUtil;
import jp.coolfactory.data.util.StringUtil;

import java.util.HashMap;

/**
 * Created by wangqi on 7/3/2017.
 */
public class UserRequest implements SQLRequest {

    private String action = null;
    private String account_key = null;
    private String source = null;
    private String stat_id = null;
    private String app_key = null;
    private String site_id = null;
    private String site_name = null;
    private String region_name = null;
    private String country_code = null;
    private int is_paid = 0;
    private double total_paid = 0;
    private String game_user_id = null;
    private String ios_ifa = null;
    private String google_aid = null;
    private String publisher_id = null;
    private String publisher_name = null;
    private String campaign_id = null;
    private String campaign_name = null;
    private String created = null;
    private String ip = null;
    private double revenue = 0;
    private double revenue_usd = 0;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
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

    public String getPublisher_id() {
        return publisher_id;
    }

    public void setPublisher_id(String publisher_id) {
        this.publisher_id = publisher_id;
    }

    public String getPublisher_name() {
        return publisher_name;
    }

    public void setPublisher_name(String publisher_name) {
        this.publisher_name = publisher_name;
    }

    public String getCampaign_id() {
        return campaign_id;
    }

    public void setCampaign_id(String campaign_id) {
        this.campaign_id = campaign_id;
    }

    public String getCampaign_name() {
        return campaign_name;
    }

    public void setCampaign_name(String campaign_name) {
        this.campaign_name = campaign_name;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
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

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public int getIs_paid() {
        return is_paid;
    }

    public void setIs_paid(int is_paid) {
        this.is_paid = is_paid;
    }

    public double getTotal_paid() {
        return total_paid;
    }

    public void setTotal_paid(double total_paid) {
        this.total_paid = total_paid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRequest that = (UserRequest) o;

        if (getIs_paid() != that.getIs_paid()) return false;
        if (Double.compare(that.getTotal_paid(), getTotal_paid()) != 0) return false;
        if (Double.compare(that.getRevenue(), getRevenue()) != 0) return false;
        if (Double.compare(that.getRevenue_usd(), getRevenue_usd()) != 0) return false;
        if (getAction() != null ? !getAction().equals(that.getAction()) : that.getAction() != null) return false;
        if (getAccount_key() != null ? !getAccount_key().equals(that.getAccount_key()) : that.getAccount_key() != null)
            return false;
        if (getSource() != null ? !getSource().equals(that.getSource()) : that.getSource() != null) return false;
        if (getStat_id() != null ? !getStat_id().equals(that.getStat_id()) : that.getStat_id() != null) return false;
        if (getApp_key() != null ? !getApp_key().equals(that.getApp_key()) : that.getApp_key() != null) return false;
        if (getSite_id() != null ? !getSite_id().equals(that.getSite_id()) : that.getSite_id() != null) return false;
        if (getSite_name() != null ? !getSite_name().equals(that.getSite_name()) : that.getSite_name() != null)
            return false;
        if (getRegion_name() != null ? !getRegion_name().equals(that.getRegion_name()) : that.getRegion_name() != null)
            return false;
        if (getCountry_code() != null ? !getCountry_code().equals(that.getCountry_code()) : that.getCountry_code() != null)
            return false;
        if (getGame_user_id() != null ? !getGame_user_id().equals(that.getGame_user_id()) : that.getGame_user_id() != null)
            return false;
        if (getIos_ifa() != null ? !getIos_ifa().equals(that.getIos_ifa()) : that.getIos_ifa() != null) return false;
        if (getGoogle_aid() != null ? !getGoogle_aid().equals(that.getGoogle_aid()) : that.getGoogle_aid() != null)
            return false;
        if (getPublisher_id() != null ? !getPublisher_id().equals(that.getPublisher_id()) : that.getPublisher_id() != null)
            return false;
        if (getPublisher_name() != null ? !getPublisher_name().equals(that.getPublisher_name()) : that.getPublisher_name() != null)
            return false;
        if (getCampaign_id() != null ? !getCampaign_id().equals(that.getCampaign_id()) : that.getCampaign_id() != null)
            return false;
        if (getCampaign_name() != null ? !getCampaign_name().equals(that.getCampaign_name()) : that.getCampaign_name() != null)
            return false;
        if (getCreated() != null ? !getCreated().equals(that.getCreated()) : that.getCreated() != null) return false;
        return getIp() != null ? getIp().equals(that.getIp()) : that.getIp() == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getAction() != null ? getAction().hashCode() : 0;
        result = 31 * result + (getAccount_key() != null ? getAccount_key().hashCode() : 0);
        result = 31 * result + (getSource() != null ? getSource().hashCode() : 0);
        result = 31 * result + (getStat_id() != null ? getStat_id().hashCode() : 0);
        result = 31 * result + (getApp_key() != null ? getApp_key().hashCode() : 0);
        result = 31 * result + (getSite_id() != null ? getSite_id().hashCode() : 0);
        result = 31 * result + (getSite_name() != null ? getSite_name().hashCode() : 0);
        result = 31 * result + (getRegion_name() != null ? getRegion_name().hashCode() : 0);
        result = 31 * result + (getCountry_code() != null ? getCountry_code().hashCode() : 0);
        result = 31 * result + getIs_paid();
        temp = Double.doubleToLongBits(getTotal_paid());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (getGame_user_id() != null ? getGame_user_id().hashCode() : 0);
        result = 31 * result + (getIos_ifa() != null ? getIos_ifa().hashCode() : 0);
        result = 31 * result + (getGoogle_aid() != null ? getGoogle_aid().hashCode() : 0);
        result = 31 * result + (getPublisher_id() != null ? getPublisher_id().hashCode() : 0);
        result = 31 * result + (getPublisher_name() != null ? getPublisher_name().hashCode() : 0);
        result = 31 * result + (getCampaign_id() != null ? getCampaign_id().hashCode() : 0);
        result = 31 * result + (getCampaign_name() != null ? getCampaign_name().hashCode() : 0);
        result = 31 * result + (getCreated() != null ? getCreated().hashCode() : 0);
        result = 31 * result + (getIp() != null ? getIp().hashCode() : 0);
        temp = Double.doubleToLongBits(getRevenue());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getRevenue_usd());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toSQL() {
        if ( StringUtil.isEmptyString(action) ) {
            //Action is null. Ignore it.
            return null;
        } else {
            StringBuilder buf = new StringBuilder(200);
            StringBuilder valueBuf = new StringBuilder(200);
            HashMap<String, String> map = new HashMap<>();
            valueBuf.append("(");

            if (Constants.ACTION_INSTALL.equals(action)) {
                buf.append("insert ignore into ").append(DBUtil.getDatabaseSchema()).append(".ad_register (");
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
                if (StringUtil.isNotEmptyString(publisher_id)) {
                    buf.append("publisher_id,");
                    valueBuf.append("'{publisher_id}',");
                    map.put("publisher_id", StringUtil.validSQLInput(publisher_id));
                }
                if (StringUtil.isNotEmptyString(publisher_name)) {
                    buf.append("publisher_name,");
                    valueBuf.append("'{publisher_name}',");
                    map.put("publisher_name", StringUtil.validSQLInput(publisher_name));
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
                if (StringUtil.isNotEmptyString(campaign_id)) {
                    buf.append("campaign_id,");
                    valueBuf.append("'{campaign_id}',");
                    map.put("campaign_id", StringUtil.validSQLInput(campaign_id));
                }
                if (StringUtil.isNotEmptyString(campaign_name)) {
                    buf.append("campaign_name,");
                    valueBuf.append("'{campaign_name}',");
                    map.put("campaign_name", StringUtil.validSQLInput(campaign_name));
                }
                if (StringUtil.isNotEmptyString(region_name)) {
                    buf.append("region_name,");
                    valueBuf.append("'{region_name}',");
                    map.put("region_name", StringUtil.validSQLInput(region_name));
                }
                if (StringUtil.isNotEmptyString(country_code)) {
                    buf.append("country_code,");
                    valueBuf.append("'{country_code}',");
                    map.put("country_code", StringUtil.validSQLInput(country_code));
                }
                if (StringUtil.isNotEmptyString(ios_ifa)) {
                    buf.append("ios_ifa,");
                    valueBuf.append("'{ios_ifa}',");
                    map.put("ios_ifa", StringUtil.validSQLInput(ios_ifa));
                }
                if (StringUtil.isNotEmptyString(google_aid)) {
                    buf.append("google_aid,");
                    valueBuf.append("'{google_aid}',");
                    map.put("google_aid", StringUtil.validSQLInput(google_aid));
                }
                if (StringUtil.isNotEmptyString(ip)) {
                    buf.append("ip,");
                    valueBuf.append("'{ip}',");
                    map.put("ip", StringUtil.validSQLInput(ip));
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
                return sql;
            } else if (Constants.ACTION_PURCHASE.equals(action)) {
                buf.append("update ").append(DBUtil.getDatabaseSchema()).append(".ad_register ");
                if (revenue > 0) {
                    buf.append("set is_paid={is_paid}, total_paid=total_paid+{total_paid} ");
                    buf.append("where account_key='{account_key}' and app_key='{app_key}' and source='{source}' and stat_id='{stat_id}'");
                    map.put("is_paid", "1");
                    map.put("total_paid", String.valueOf(revenue_usd));
                    map.put("account_key", account_key);
                    map.put("app_key", app_key);
                    map.put("source", source);
                    map.put("stat_id", stat_id);
                    String sql = StringUtil.replaceKey(buf.toString(), map);
                    return sql;
                }
            } else if (Constants.ACTION_LOGIN.equals(action)) {
                buf.append("update ").append(DBUtil.getDatabaseSchema()).append(".ad_register ");
                buf.append("set last_date='{last_date}' ");
                buf.append("where account_key='{account_key}' and app_key='{app_key}' and source='{source}' and stat_id='{stat_id}'");
                map.put("last_date", created);
                map.put("account_key", account_key);
                map.put("app_key", app_key);
                map.put("source", source);
                map.put("stat_id", stat_id);
                String sql = StringUtil.replaceKey(buf.toString(), map);
                return sql;
            }
            return null;
        }
    }
}



