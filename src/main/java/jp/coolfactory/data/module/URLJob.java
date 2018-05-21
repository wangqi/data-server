package jp.coolfactory.data.module;

import jp.coolfactory.data.Version;
import jp.coolfactory.data.db.DBUtil;
import jp.coolfactory.data.util.DateUtil;
import jp.coolfactory.data.util.StringUtil;
import org.slf4j.Logger;

import java.time.ZonedDateTime;
import java.util.HashMap;

/**
 * It represends a URL job that will be processed by URLJobManager
 */
public class URLJob {

    /**
     * ==================================================================
     *  To Postback Requests Fields
     * ==================================================================
     **/
    private String postback;
    //For anti_fraud system
    private int postback_code;
    private String postback_desc;
    /**
     * I add a TrackingLink measure API, which will call the URLJobmanager to refresh MAT's server.
     * However the URLJobmanager is designed for postback. So we need a field to differentiate the postback and tracking
     * 2018-03-13
     */
    private boolean isTracking = false;

    /**
     * The following fields are used to customize URLJobManager
     */
    private String urlUserAgent = "dataserver/" + Version.VERSION;
    private String urlMethod = "GET";
    private String urlContentType = null;
    private String urlPostData = null;
    private Logger urlLogger = null;
    /**
     * ==================================================================
     *  To Postback SQL Fields
     * ==================================================================
     **/
    private String account_key;
    private String action;
    private String source;
    private String stat_id;
    private String os_version;
    private String plat_id;
    private String appKey;
    private String publisher_id;
    private String publisher_name;
    private String install_ip;
    private ZonedDateTime install_time;
    private String site_id;
    private String site_name;
    private String tracking_id;
    private String ios_ifa;
    private String google_aid;
    private long ip_from;
    private long ip_to;
    private String status;
    private String status_code;

    public Logger getUrlLogger() {
        return urlLogger;
    }

    public void setUrlLogger(Logger urlLogger) {
        this.urlLogger = urlLogger;
    }

    public String getPostback() {
        return postback;
    }

    public void setPostback(String postback) {
        this.postback = postback;
    }

    public int getPostback_code() {
        return postback_code;
    }

    public void setPostback_code(int postback_code) {
        this.postback_code = postback_code;
    }

    public String getPostback_desc() {
        return postback_desc;
    }

    public void setPostback_desc(String postback_desc) {
        this.postback_desc = postback_desc;
    }

    public boolean isTracking() {
        return isTracking;
    }

    public void setTracking(boolean tracking) {
        isTracking = tracking;
    }

    public String getUrlUserAgent() {
        return urlUserAgent;
    }

    public void setUrlUserAgent(String urlUserAgent) {
        this.urlUserAgent = urlUserAgent;
    }

    public String getUrlMethod() {
        return urlMethod;
    }

    public void setUrlMethod(String urlMethod) {
        this.urlMethod = urlMethod;
    }

    public String getUrlContentType() {
        return urlContentType;
    }

    public void setUrlContentType(String urlContentType) {
        this.urlContentType = urlContentType;
    }

    public String getUrlPostData() {
        return urlPostData;
    }

    public void setUrlPostData(String urlPostData) {
        this.urlPostData = urlPostData;
    }

    public String getAccount_key() {
        return account_key;
    }

    public void setAccount_key(String account_key) {
        this.account_key = account_key;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
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

    public String getOs_version() {
        return os_version;
    }

    public void setOs_version(String os_version) {
        this.os_version = os_version;
    }

    public String getPlat_id() {
        return plat_id;
    }

    public void setPlat_id(String plat_id) {
        this.plat_id = plat_id;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
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

    public String getInstall_ip() {
        return install_ip;
    }

    public void setInstall_ip(String install_ip) {
        this.install_ip = install_ip;
    }

    public ZonedDateTime getInstall_time() {
        return install_time;
    }

    public void setInstall_time(ZonedDateTime install_time) {
        this.install_time = install_time;
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

    public String getTracking_id() {
        return tracking_id;
    }

    public void setTracking_id(String tracking_id) {
        this.tracking_id = tracking_id;
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

    public long getIp_from() {
        return ip_from;
    }

    public void setIp_from(long ip_from) {
        this.ip_from = ip_from;
    }

    public long getIp_to() {
        return ip_to;
    }

    public void setIp_to(long ip_to) {
        this.ip_to = ip_to;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    /**
     * Convert this request into a SQL insert
     *
     * @return
     */
    public String toPostbackSQL() {
        if (StringUtil.isEmptyString(action)) {
            //Action is null. Ignore it.
            return null;
        } else {
            StringBuilder buf = new StringBuilder(200);
            StringBuilder valueBuf = new StringBuilder(200);
            HashMap<String, String> map = new HashMap<>();
            valueBuf.append("(");
//            if (Constants.ACTION_INSTALL.equals(action) ) {
//                buf.append("replace into ").append(DBUtil.getDatabaseSchema()).append(".ad_postback (");
//            }
            buf.append("replace into ").append(DBUtil.getDatabaseSchema()).append(".ad_postback (");
            buf.append("action,");
            valueBuf.append("'{action}',");
            map.put("action", StringUtil.validSQLInput(action));

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
            if (StringUtil.isNotEmptyString(appKey)) {
                buf.append("app_key,");
                valueBuf.append("'{app_key}',");
                map.put("app_key", StringUtil.validSQLInput(appKey));
            }
            if (StringUtil.isNotEmptyString(plat_id)) {
                buf.append("plat_id,");
                valueBuf.append("'{plat_id}',");
                map.put("plat_id", StringUtil.validSQLInput(plat_id));
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
            if (StringUtil.isNotEmptyString(install_ip)) {
                //Rename 'install_ip' to 'ip'
                buf.append("ip,");
                valueBuf.append("'{install_ip}',");
                map.put("install_ip", StringUtil.validSQLInput(install_ip));
            }
            if (install_time != null) {
                //Rename 'install_time' to 'created'
                String value = DateUtil.formatDateTime(install_time);
                buf.append("created,");
                valueBuf.append("'{install_time}',");
                map.put("install_time", value);
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
            if (StringUtil.isNotEmptyString(tracking_id)) {
                buf.append("tracking_id,");
                valueBuf.append("'{tracking_id}',");
                map.put("tracking_id", StringUtil.validSQLInput(tracking_id));
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
            if (ip_from != 0) {
                buf.append("ip_from,");
                valueBuf.append("{ip_from},");
                map.put("ip_from", String.valueOf(ip_from));
            }
            if (ip_to != 0) {
                buf.append("ip_to,");
                valueBuf.append("{ip_to},");
                map.put("ip_to", String.valueOf(ip_to));
            }
            if (postback_code != 0) {
                buf.append("postback_code,");
                valueBuf.append("{postback_code},");
                map.put("postback_code", String.valueOf(postback_code));
            }
            if (StringUtil.isNotEmptyString(postback_desc)) {
                buf.append("postback_desc,");
                valueBuf.append("'{postback_desc}',");
                map.put("postback_desc", StringUtil.validSQLInput(postback_desc));
            }
            if (StringUtil.isNotEmptyString(status)) {
                buf.append("status,");
                valueBuf.append("'{status}',");
                map.put("status", StringUtil.validSQLInput(status));
            }
            if (StringUtil.isNotEmptyString(status_code)) {
                buf.append("status_code,");
                valueBuf.append("'{status_code}',");
                map.put("status_code", StringUtil.validSQLInput(status_code));
            }
            buf.deleteCharAt(buf.length() - 1);
            buf.append(") values ");
            valueBuf.deleteCharAt(valueBuf.length() - 1);
            valueBuf.append(')');
            String sql = StringUtil.replaceKey(buf.append('\n').append(valueBuf.toString()).toString(), map);
            return sql;
        }
    }

}
