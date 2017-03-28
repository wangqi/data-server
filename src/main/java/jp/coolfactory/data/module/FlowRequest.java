package jp.coolfactory.data.module;

import jp.coolfactory.data.Constants;
import jp.coolfactory.data.db.DBUtil;
import jp.coolfactory.data.util.DateUtil;
import jp.coolfactory.data.util.StringUtil;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by wangqi on 7/3/2017.
 */
public class FlowRequest implements SQLRequest {

    private String action = null;
    private String account_key = null;
    private String source = null;
    private String stat_id = null;
    private String app_key = null;
    private String created = null;
    private String site_id = null;
    private String site_name = null;
    private String country_code = null;
    private String region_name = null;
    private String game_user_id = null;
    //ios_ifa or google_aid
    private String uid = null;
    //It's the platform unique id
    private String plat_id = null;
    private String ip = null;
    private String publisher_id = null;
    private String publisher_name = null;
    private String device_model = null;
    private String device_type = null;
    private String os_version = null;
    private ZonedDateTime install_time = null;
    private Set<String> steps = new HashSet<>();
    private ArrayList<String> step_names = new ArrayList<>();
    private ArrayList<Integer> step_interval = new ArrayList<>();

    public FlowRequest(ZonedDateTime install_time) {
        this.install_time = install_time;
        if ( this.install_time == null ) {
            this.install_time = ZonedDateTime.now();
        }
    }

    /**
     * Add a new step into the flow
     * @param action
     * @param created
     */
    public void addStep(String action, ZonedDateTime created) {
        if ( action != null && !steps.contains(action) ) {
            steps.add(action);
            step_names.add(action);
            int seconds = (int)ChronoUnit.SECONDS.between(install_time, created);
            step_interval.add(seconds);
        }
    }

    public String getOs_version() {
        return os_version;
    }

    public void setOs_version(String os_version) {
        this.os_version = os_version;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public ZonedDateTime getInstall_time() {
        return install_time;
    }

    public void setInstall_time(ZonedDateTime install_time) {
        this.install_time = install_time;
    }

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

    public String getStat_id() {
        return stat_id;
    }

    public void setStat_id(String stat_id) {
        this.stat_id = stat_id;
    }

    public String getApp_key() {
        return app_key;
    }

    public void setApp_key(String app_key) {
        this.app_key = app_key;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
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

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public String getGame_user_id() {
        return game_user_id;
    }

    public void setGame_user_id(String game_user_id) {
        this.game_user_id = game_user_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPlat_id() {
        return plat_id;
    }

    public void setPlat_id(String plat_id) {
        this.plat_id = plat_id;
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

    public String getDevice_model() {
        return device_model;
    }

    public void setDevice_model(String device_model) {
        this.device_model = device_model;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FlowRequest that = (FlowRequest) o;

        if (getAction() != null ? !getAction().equals(that.getAction()) : that.getAction() != null) return false;
        if (getAccount_key() != null ? !getAccount_key().equals(that.getAccount_key()) : that.getAccount_key() != null)
            return false;
        if (getSource() != null ? !getSource().equals(that.getSource()) : that.getSource() != null) return false;
        if (getStat_id() != null ? !getStat_id().equals(that.getStat_id()) : that.getStat_id() != null) return false;
        if (getApp_key() != null ? !getApp_key().equals(that.getApp_key()) : that.getApp_key() != null) return false;
        if (getCreated() != null ? !getCreated().equals(that.getCreated()) : that.getCreated() != null) return false;
        if (getSite_id() != null ? !getSite_id().equals(that.getSite_id()) : that.getSite_id() != null) return false;
        if (getSite_name() != null ? !getSite_name().equals(that.getSite_name()) : that.getSite_name() != null)
            return false;
        if (getCountry_code() != null ? !getCountry_code().equals(that.getCountry_code()) : that.getCountry_code() != null)
            return false;
        if (getRegion_name() != null ? !getRegion_name().equals(that.getRegion_name()) : that.getRegion_name() != null)
            return false;
        if (getGame_user_id() != null ? !getGame_user_id().equals(that.getGame_user_id()) : that.getGame_user_id() != null)
            return false;
        if (getUid() != null ? !getUid().equals(that.getUid()) : that.getUid() != null) return false;
        if (getPlat_id() != null ? !getPlat_id().equals(that.getPlat_id()) : that.getPlat_id() != null) return false;
        if (getIp() != null ? !getIp().equals(that.getIp()) : that.getIp() != null) return false;
        if (getPublisher_id() != null ? !getPublisher_id().equals(that.getPublisher_id()) : that.getPublisher_id() != null)
            return false;
        if (getPublisher_name() != null ? !getPublisher_name().equals(that.getPublisher_name()) : that.getPublisher_name() != null)
            return false;
        if (getDevice_model() != null ? !getDevice_model().equals(that.getDevice_model()) : that.getDevice_model() != null)
            return false;
        if (getDevice_type() != null ? !getDevice_type().equals(that.getDevice_type()) : that.getDevice_type() != null)
            return false;
        if (getOs_version() != null ? !getOs_version().equals(that.getOs_version()) : that.getOs_version() != null)
            return false;
        if (getInstall_time() != null ? !getInstall_time().equals(that.getInstall_time()) : that.getInstall_time() != null)
            return false;
        if (steps != null ? !steps.equals(that.steps) : that.steps != null) return false;
        if (step_names != null ? !step_names.equals(that.step_names) : that.step_names != null) return false;
        return step_interval != null ? step_interval.equals(that.step_interval) : that.step_interval == null;
    }

    @Override
    public int hashCode() {
        int result = getAction() != null ? getAction().hashCode() : 0;
        result = 31 * result + (getAccount_key() != null ? getAccount_key().hashCode() : 0);
        result = 31 * result + (getSource() != null ? getSource().hashCode() : 0);
        result = 31 * result + (getStat_id() != null ? getStat_id().hashCode() : 0);
        result = 31 * result + (getApp_key() != null ? getApp_key().hashCode() : 0);
        result = 31 * result + (getCreated() != null ? getCreated().hashCode() : 0);
        result = 31 * result + (getSite_id() != null ? getSite_id().hashCode() : 0);
        result = 31 * result + (getSite_name() != null ? getSite_name().hashCode() : 0);
        result = 31 * result + (getCountry_code() != null ? getCountry_code().hashCode() : 0);
        result = 31 * result + (getRegion_name() != null ? getRegion_name().hashCode() : 0);
        result = 31 * result + (getGame_user_id() != null ? getGame_user_id().hashCode() : 0);
        result = 31 * result + (getUid() != null ? getUid().hashCode() : 0);
        result = 31 * result + (getPlat_id() != null ? getPlat_id().hashCode() : 0);
        result = 31 * result + (getIp() != null ? getIp().hashCode() : 0);
        result = 31 * result + (getPublisher_id() != null ? getPublisher_id().hashCode() : 0);
        result = 31 * result + (getPublisher_name() != null ? getPublisher_name().hashCode() : 0);
        result = 31 * result + (getDevice_model() != null ? getDevice_model().hashCode() : 0);
        result = 31 * result + (getDevice_type() != null ? getDevice_type().hashCode() : 0);
        result = 31 * result + (getOs_version() != null ? getOs_version().hashCode() : 0);
        result = 31 * result + (getInstall_time() != null ? getInstall_time().hashCode() : 0);
        result = 31 * result + (steps != null ? steps.hashCode() : 0);
        result = 31 * result + (step_names != null ? step_names.hashCode() : 0);
        result = 31 * result + (step_interval != null ? step_interval.hashCode() : 0);
        return result;
    }

    @Override
    public String toSQL() {
        StringBuilder buf = new StringBuilder(200);
        StringBuilder valueBuf = new StringBuilder(200);
        HashMap<String, String> map = new HashMap<>();
        valueBuf.append("(");

        buf.append("insert ignore into ").append(DBUtil.getDatabaseSchema()).append(".ad_flow (");
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
        if (StringUtil.isNotEmptyString(country_code)) {
            buf.append("country_code,");
            valueBuf.append("'{country_code}',");
            map.put("country_code", StringUtil.validSQLInput(country_code));
        }
        if (StringUtil.isNotEmptyString(region_name)) {
            buf.append("region_name,");
            valueBuf.append("'{region_name}',");
            map.put("region_name", StringUtil.validSQLInput(region_name));
        }
        if (StringUtil.isNotEmptyString(game_user_id)) {
            buf.append("game_user_id,");
            valueBuf.append("'{game_user_id}',");
            map.put("game_user_id", StringUtil.validSQLInput(game_user_id));
        }
        if (StringUtil.isNotEmptyString(plat_id)) {
            buf.append("plat_id,");
            valueBuf.append("'{plat_id}',");
            map.put("plat_id", StringUtil.validSQLInput(plat_id));
        }
        if (StringUtil.isNotEmptyString(uid)) {
            buf.append("uid,");
            valueBuf.append("'{uid}',");
            map.put("uid", StringUtil.validSQLInput(uid));
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
        if (StringUtil.isNotEmptyString(device_model)) {
            buf.append("device_model,");
            valueBuf.append("'{device_model}',");
            map.put("device_model", StringUtil.validSQLInput(device_model));
        }
        if (StringUtil.isNotEmptyString(device_type)) {
            buf.append("device_type,");
            valueBuf.append("'{device_type}',");
            map.put("device_type", StringUtil.validSQLInput(device_type));
        }
        if (StringUtil.isNotEmptyString(ip)) {
            buf.append("ip,");
            valueBuf.append("'{ip}',");
            map.put("ip", StringUtil.validSQLInput(ip));
        }
        if (StringUtil.isNotEmptyString(os_version)) {
            buf.append("os_version,");
            valueBuf.append("'{os_version}',");
            map.put("os_version", StringUtil.validSQLInput(os_version));
        }
        if ( install_time != null ) {
            buf.append("install_time,");
            valueBuf.append("'{install_time}',");
            map.put("install_time", DateUtil.formatDateTime(install_time));
        }
        buf.deleteCharAt(buf.length() - 1);
        buf.append(") values ");
        valueBuf.deleteCharAt(valueBuf.length() - 1);
        valueBuf.append(')');
        String sql = StringUtil.replaceKey(buf.append('\n').append(valueBuf.toString()).toString(), map);
        return sql;
    }
}



