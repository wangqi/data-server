package jp.coolfactory.data.module;

import jp.coolfactory.data.db.DBUtil;
import jp.coolfactory.data.util.DateUtil;
import jp.coolfactory.data.util.StringUtil;

import java.time.ZonedDateTime;
import java.util.HashMap;

/**
 * Created by wangqi on 22/2/2017.
 */
public class AdRequest {

    private String account_key;
    private String action;
    private String appKey;
    private String source;
    private String stat_id;
    private String os_version;
    private String device_id;
    private String device_type;
    private String device_brand;
    private String device_carrier;
    private String device_model;
    private String lang;
    private String plat_id;
    private String user_agent;
    private String publisher_id;
    private String publisher_name;
    private String click_ip;
    private ZonedDateTime click_time;
    private String bundle_id;
    private String install_ip;
    private ZonedDateTime install_time;
    private String agency_name;
    private String site_id;
    private String site_name;
    private String match_type;
    private String campaign_id;
    private String campaign_name;
    private String ad_url;
    private String ad_name;
    private String region_name;
    private String country_code;
    private String currency_code;
    private String existing_user;
    private ZonedDateTime imp_time;
    private String stat_click_id;
    private String stat_impression_id;
    private double payout;
    private String referral_source;
    private String referral_url;
    private double revenue;
    private double revenue_usd;
    private String status;
    private String status_code;
    private String tracking_id;
    private String ios_ifa;
    private String ios_ifv;
    private String google_aid;
    private String pub_camp_id;
    private String pub_camp_name;
    private String pub_camp_ref;
    private String pub_adset;
    private String pub_ad;
    private String pub_keyword;
    private String pub_place;
    private String pub_sub_id;
    private String pub_sub_name;
    private String adv_camp_id;
    private String adv_camp_name;
    private String adv_camp_ref;
    private String adv_adset;
    private String adv_ad;
    private String adv_keyword;
    private String adv_place;
    private String adv_sub_id;
    private String adv_sub_name;
    private String sdk;
    private String sdk_version;
    private String game_user_id;
    private boolean os_jailbroke;
    private String pub_pref_id;
    private String pub_sub1;
    private String pub_sub2;
    private String pub_sub3;
    private String pub_sub4;
    private String pub_sub5;
    private String cost_model;
    private double cost;
    private long ip_from;
    private long ip_to;
    private String city_code;
    private String metro_code;

    public String getAccount_key() {
        return account_key;
    }

    public void setAccount_key(String account_key) {
        this.account_key = account_key;
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

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getDevice_brand() {
        return device_brand;
    }

    public void setDevice_brand(String device_brand) {
        this.device_brand = device_brand;
    }

    public String getDevice_carrier() {
        return device_carrier;
    }

    public void setDevice_carrier(String device_carrier) {
        this.device_carrier = device_carrier;
    }

    public String getDevice_model() {
        return device_model;
    }

    public void setDevice_model(String device_model) {
        this.device_model = device_model;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getPlat_id() {
        return plat_id;
    }

    public void setPlat_id(String plat_id) {
        this.plat_id = plat_id;
    }

    public String getUser_agent() {
        return user_agent;
    }

    public void setUser_agent(String user_agent) {
        this.user_agent = user_agent;
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

    public String getClick_ip() {
        return click_ip;
    }

    public void setClick_ip(String click_ip) {
        this.click_ip = click_ip;
    }

    public ZonedDateTime getClick_time() {
        return click_time;
    }

    public void setClick_time(ZonedDateTime click_time) {
        this.click_time = click_time;
    }

    public String getBundle_id() {
        return bundle_id;
    }

    public void setBundle_id(String bundle_id) {
        this.bundle_id = bundle_id;
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

    public String getAgency_name() {
        return agency_name;
    }

    public void setAgency_name(String agency_name) {
        this.agency_name = agency_name;
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

    public String getMatch_type() {
        return match_type;
    }

    public void setMatch_type(String match_type) {
        this.match_type = match_type;
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

    public String getAd_url() {
        return ad_url;
    }

    public void setAd_url(String ad_url) {
        this.ad_url = ad_url;
    }

    public String getAd_name() {
        return ad_name;
    }

    public void setAd_name(String ad_name) {
        this.ad_name = ad_name;
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

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

    public String getExisting_user() {
        return existing_user;
    }

    public void setExisting_user(String existing_user) {
        this.existing_user = existing_user;
    }

    public ZonedDateTime getImp_time() {
        return imp_time;
    }

    public void setImp_time(ZonedDateTime imp_time) {
        this.imp_time = imp_time;
    }

    public String getStat_click_id() {
        return stat_click_id;
    }

    public void setStat_click_id(String stat_click_id) {
        this.stat_click_id = stat_click_id;
    }

    public String getStat_impression_id() {
        return stat_impression_id;
    }

    public void setStat_impression_id(String stat_impression_id) {
        this.stat_impression_id = stat_impression_id;
    }

    public double getPayout() {
        return payout;
    }

    public void setPayout(double payout) {
        this.payout = payout;
    }

    public String getReferral_source() {
        return referral_source;
    }

    public void setReferral_source(String referral_source) {
        this.referral_source = referral_source;
    }

    public String getReferral_url() {
        return referral_url;
    }

    public void setReferral_url(String referral_url) {
        this.referral_url = referral_url;
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

    public String getIos_ifv() {
        return ios_ifv;
    }

    public void setIos_ifv(String ios_ifv) {
        this.ios_ifv = ios_ifv;
    }

    public String getGoogle_aid() {
        return google_aid;
    }

    public void setGoogle_aid(String google_aid) {
        this.google_aid = google_aid;
    }

    public String getPub_camp_id() {
        return pub_camp_id;
    }

    public void setPub_camp_id(String pub_camp_id) {
        this.pub_camp_id = pub_camp_id;
    }

    public String getPub_camp_name() {
        return pub_camp_name;
    }

    public void setPub_camp_name(String pub_camp_name) {
        this.pub_camp_name = pub_camp_name;
    }

    public String getPub_camp_ref() {
        return pub_camp_ref;
    }

    public void setPub_camp_ref(String pub_camp_ref) {
        this.pub_camp_ref = pub_camp_ref;
    }

    public String getPub_adset() {
        return pub_adset;
    }

    public void setPub_adset(String pub_adset) {
        this.pub_adset = pub_adset;
    }

    public String getPub_ad() {
        return pub_ad;
    }

    public void setPub_ad(String pub_ad) {
        this.pub_ad = pub_ad;
    }

    public String getPub_keyword() {
        return pub_keyword;
    }

    public void setPub_keyword(String pub_keyword) {
        this.pub_keyword = pub_keyword;
    }

    public String getPub_place() {
        return pub_place;
    }

    public void setPub_place(String pub_place) {
        this.pub_place = pub_place;
    }

    public String getPub_sub_id() {
        return pub_sub_id;
    }

    public void setPub_sub_id(String pub_sub_id) {
        this.pub_sub_id = pub_sub_id;
    }

    public String getPub_sub_name() {
        return pub_sub_name;
    }

    public void setPub_sub_name(String pub_sub_name) {
        this.pub_sub_name = pub_sub_name;
    }

    public String getAdv_camp_id() {
        return adv_camp_id;
    }

    public void setAdv_camp_id(String adv_camp_id) {
        this.adv_camp_id = adv_camp_id;
    }

    public String getAdv_camp_name() {
        return adv_camp_name;
    }

    public void setAdv_camp_name(String adv_camp_name) {
        this.adv_camp_name = adv_camp_name;
    }

    public String getAdv_camp_ref() {
        return adv_camp_ref;
    }

    public void setAdv_camp_ref(String adv_camp_ref) {
        this.adv_camp_ref = adv_camp_ref;
    }

    public String getAdv_adset() {
        return adv_adset;
    }

    public void setAdv_adset(String adv_adset) {
        this.adv_adset = adv_adset;
    }

    public String getAdv_ad() {
        return adv_ad;
    }

    public void setAdv_ad(String adv_ad) {
        this.adv_ad = adv_ad;
    }

    public String getAdv_keyword() {
        return adv_keyword;
    }

    public void setAdv_keyword(String adv_keyword) {
        this.adv_keyword = adv_keyword;
    }

    public String getAdv_place() {
        return adv_place;
    }

    public void setAdv_place(String adv_place) {
        this.adv_place = adv_place;
    }

    public String getAdv_sub_id() {
        return adv_sub_id;
    }

    public void setAdv_sub_id(String adv_sub_id) {
        this.adv_sub_id = adv_sub_id;
    }

    public String getAdv_sub_name() {
        return adv_sub_name;
    }

    public void setAdv_sub_name(String adv_sub_name) {
        this.adv_sub_name = adv_sub_name;
    }

    public String getSdk() {
        return sdk;
    }

    public void setSdk(String sdk) {
        this.sdk = sdk;
    }

    public String getSdk_version() {
        return sdk_version;
    }

    public void setSdk_version(String sdk_version) {
        this.sdk_version = sdk_version;
    }

    public String getGame_user_id() {
        return game_user_id;
    }

    public void setGame_user_id(String game_user_id) {
        this.game_user_id = game_user_id;
    }

    public boolean isOs_jailbroke() {
        return os_jailbroke;
    }

    public void setOs_jailbroke(boolean os_jailbroke) {
        this.os_jailbroke = os_jailbroke;
    }

    public String getPub_pref_id() {
        return pub_pref_id;
    }

    public void setPub_pref_id(String pub_pref_id) {
        this.pub_pref_id = pub_pref_id;
    }

    public String getPub_sub1() {
        return pub_sub1;
    }

    public void setPub_sub1(String pub_sub1) {
        this.pub_sub1 = pub_sub1;
    }

    public String getPub_sub2() {
        return pub_sub2;
    }

    public void setPub_sub2(String pub_sub2) {
        this.pub_sub2 = pub_sub2;
    }

    public String getPub_sub3() {
        return pub_sub3;
    }

    public void setPub_sub3(String pub_sub3) {
        this.pub_sub3 = pub_sub3;
    }

    public String getCost_model() {
        return cost_model;
    }

    public void setCost_model(String cost_model) {
        this.cost_model = cost_model;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
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

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public String getMetro_code() {
        return metro_code;
    }

    public void setMetro_code(String metro_code) {
        this.metro_code = metro_code;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPub_sub4() {
        return pub_sub4;
    }

    public void setPub_sub4(String pub_sub4) {
        this.pub_sub4 = pub_sub4;
    }

    public String getPub_sub5() {
        return pub_sub5;
    }

    public void setPub_sub5(String pub_sub5) {
        this.pub_sub5 = pub_sub5;
    }

    @Override
    public String toString() {
        return "AdRequest{" +
                "action='" + action + '\'' +
                ", appKey='" + appKey + '\'' +
                ", source='" + source + '\'' +
                ", stat_id='" + stat_id + '\'' +
                ", os_version='" + os_version + '\'' +
                ", device_id='" + device_id + '\'' +
                ", device_type='" + device_type + '\'' +
                ", device_brand='" + device_brand + '\'' +
                ", device_carrier='" + device_carrier + '\'' +
                ", device_model='" + device_model + '\'' +
                ", lang='" + lang + '\'' +
                ", plat_id='" + plat_id + '\'' +
                ", user_agent='" + user_agent + '\'' +
                ", publisher_id='" + publisher_id + '\'' +
                ", publisher_name='" + publisher_name + '\'' +
                ", click_ip='" + click_ip + '\'' +
                ", click_time=" + click_time +
                ", bundle_id='" + bundle_id + '\'' +
                ", install_ip='" + install_ip + '\'' +
                ", install_time=" + install_time +
                ", agency_name='" + agency_name + '\'' +
                ", site_id='" + site_id + '\'' +
                ", site_name='" + site_name + '\'' +
                ", match_type='" + match_type + '\'' +
                ", campaign_id='" + campaign_id + '\'' +
                ", campaign_name='" + campaign_name + '\'' +
                ", ad_url='" + ad_url + '\'' +
                ", ad_name='" + ad_name + '\'' +
                ", region_name='" + region_name + '\'' +
                ", country_code='" + country_code + '\'' +
                ", currency_code='" + currency_code + '\'' +
                ", existing_user='" + existing_user + '\'' +
                ", imp_time=" + imp_time +
                ", stat_click_id='" + stat_click_id + '\'' +
                ", stat_impression_id='" + stat_impression_id + '\'' +
                ", payout=" + payout +
                ", referral_source='" + referral_source + '\'' +
                ", referral_url='" + referral_url + '\'' +
                ", revenue=" + revenue +
                ", revenue_usd='" + revenue_usd + '\'' +
                ", status='" + status + '\'' +
                ", status_code='" + status_code + '\'' +
                ", tracking_id='" + tracking_id + '\'' +
                ", ios_ifa='" + ios_ifa + '\'' +
                ", ios_ifv='" + ios_ifv + '\'' +
                ", google_aid='" + google_aid + '\'' +
                ", pub_camp_id='" + pub_camp_id + '\'' +
                ", pub_camp_name='" + pub_camp_name + '\'' +
                ", pub_camp_ref='" + pub_camp_ref + '\'' +
                ", pub_adset='" + pub_adset + '\'' +
                ", pub_ad='" + pub_ad + '\'' +
                ", pub_keyword='" + pub_keyword + '\'' +
                ", pub_place='" + pub_place + '\'' +
                ", pub_sub_id='" + pub_sub_id + '\'' +
                ", pub_sub_name='" + pub_sub_name + '\'' +
                ", adv_camp_id='" + adv_camp_id + '\'' +
                ", adv_camp_name='" + adv_camp_name + '\'' +
                ", adv_camp_ref='" + adv_camp_ref + '\'' +
                ", adv_adset='" + adv_adset + '\'' +
                ", adv_ad='" + adv_ad + '\'' +
                ", adv_keyword='" + adv_keyword + '\'' +
                ", adv_place='" + adv_place + '\'' +
                ", adv_sub_id='" + adv_sub_id + '\'' +
                ", adv_sub_name='" + adv_sub_name + '\'' +
                ", sdk='" + sdk + '\'' +
                ", sdk_version='" + sdk_version + '\'' +
                ", game_user_id='" + game_user_id + '\'' +
                ", os_jailbroke=" + os_jailbroke +
                ", pub_pref_id='" + pub_pref_id + '\'' +
                ", pub_sub1='" + pub_sub1 + '\'' +
                ", pub_sub2='" + pub_sub2 + '\'' +
                ", pub_sub3='" + pub_sub3 + '\'' +
                ", cost_model='" + cost_model + '\'' +
                ", cost=" + cost +
                ", ip_from=" + ip_from +
                ", ip_to=" + ip_to +
                ", city_code='" + city_code + '\'' +
                ", metro_code='" + metro_code + '\'' +
                '}';
    }

    /**
     * Convert this request into a SQL insert
     * @return
     */
    public String toSQL() {
        if ( StringUtil.isEmptyString(action) ) {
            //Action is null. Ignore it.
            return null;
        } else {
            StringBuilder buf = new StringBuilder(200);
            StringBuilder valueBuf = new StringBuilder(200);
            HashMap<String,String> map = new HashMap<>();
            valueBuf.append("(");
            if ( "install".equals(action) ) {
                buf.append("insert into " + DBUtil.getDatabaseSchema() + ".ad_install (");
            } else if ( "click".equals(action) ) {
                buf.append("insert into " + DBUtil.getDatabaseSchema() + ".ad_click (");
            } else if ( "purchase".equals(action) ) {
                buf.append("insert into " + DBUtil.getDatabaseSchema() + ".ad_purchase (");
            } else {
                buf.append("insert into " + DBUtil.getDatabaseSchema() + ".ad_event (");
            }
            buf.append("action,");
            valueBuf.append("'{action}',");
            map.put("action", action);

            if (StringUtil.isNotEmptyString(account_key) ) {
                buf.append("account_key,");
                valueBuf.append("'{account_key}',");
                map.put("account_key", account_key);
            }
            if (StringUtil.isNotEmptyString(source) ) {
                buf.append("source,");
                valueBuf.append("'{source}',");
                map.put("source", source);
            }
            if (StringUtil.isNotEmptyString(stat_id) ) {
                buf.append("stat_id,");
                valueBuf.append("'{stat_id}',");
                map.put("stat_id", stat_id);
            }
            if (StringUtil.isNotEmptyString(appKey) ) {
                buf.append("appKey,");
                valueBuf.append("'{appKey}',");
                map.put("appKey", appKey);
            }
            if (StringUtil.isNotEmptyString(os_version) ) {
                buf.append("os_version,");
                valueBuf.append("'{os_version}',");
                map.put("os_version", os_version);
            }
            if (StringUtil.isNotEmptyString(device_id) ) {
                buf.append("device_id,");
                valueBuf.append("'{device_id}',");
                map.put("device_id", device_id);
            }
            if (StringUtil.isNotEmptyString(device_type) ) {
                buf.append("device_type,");
                valueBuf.append("'{device_type}',");
                map.put("device_type", device_type);
            }
            if (StringUtil.isNotEmptyString(device_brand) ) {
                buf.append("device_brand,");
                valueBuf.append("'{device_brand}',");
                map.put("device_brand", device_brand);
            }
            if (StringUtil.isNotEmptyString(device_carrier) ) {
                buf.append("device_carrier,");
                valueBuf.append("'{device_carrier}',");
                map.put("device_carrier", device_carrier);
            }
            if (StringUtil.isNotEmptyString(device_model) ) {
                buf.append("device_model,");
                valueBuf.append("'{device_model}',");
                map.put("device_model", device_model);
            }
            if (StringUtil.isNotEmptyString(lang) ) {
                buf.append("lang,");
                valueBuf.append("'{lang}',");
                map.put("lang", lang);
            }
            if (StringUtil.isNotEmptyString(plat_id) ) {
                buf.append("plat_id,");
                valueBuf.append("'{plat_id}',");
                map.put("plat_id", plat_id);
            }
            if (StringUtil.isNotEmptyString(user_agent) ) {
                buf.append("user_agent,");
                valueBuf.append("'{user_agent}',");
                map.put("user_agent", user_agent);
            }
            if (StringUtil.isNotEmptyString(publisher_id) ) {
                buf.append("publisher_id,");
                valueBuf.append("'{publisher_id}',");
                map.put("publisher_id", publisher_id);
            }
            if (StringUtil.isNotEmptyString(publisher_name) ) {
                buf.append("publisher_name,");
                valueBuf.append("'{publisher_name}',");
                map.put("publisher_name", publisher_name);
            }
            if (StringUtil.isNotEmptyString(click_ip) ) {
                buf.append("click_ip,");
                valueBuf.append("'{click_ip}',");
                map.put("click_ip", click_ip);
            }
            if (click_time!=null ) {
                String value = DateUtil.formatDateTime(click_time);
                buf.append("click_time,");
                valueBuf.append("'{click_time}',");
                map.put("click_time", value);
            }
            if (StringUtil.isNotEmptyString(bundle_id) ) {
                buf.append("bundle_id,");
                valueBuf.append("'{bundle_id}',");
                map.put("bundle_id", bundle_id);
            }
            if (StringUtil.isNotEmptyString(install_ip) ) {
                buf.append("install_ip,");
                valueBuf.append("'{install_ip}',");
                map.put("install_ip", install_ip);
            }
            if (install_time!=null ) {
                String value = DateUtil.formatDateTime(install_time);
                buf.append("install_time,");
                valueBuf.append("'{install_time}',");
                map.put("install_time", value);
            }
            if (StringUtil.isNotEmptyString(agency_name) ) {
                buf.append("agency_name,");
                valueBuf.append("'{agency_name}',");
                map.put("agency_name", agency_name);
            }
            if (StringUtil.isNotEmptyString(site_id) ) {
                buf.append("site_id,");
                valueBuf.append("'{site_id}',");
                map.put("site_id", site_id);
            }
            if (StringUtil.isNotEmptyString(site_name) ) {
                buf.append("site_name,");
                valueBuf.append("'{site_name}',");
                map.put("site_name", site_name);
            }
            if (StringUtil.isNotEmptyString(match_type) ) {
                buf.append("match_type,");
                valueBuf.append("'{match_type}',");
                map.put("match_type", match_type);
            }
            if (StringUtil.isNotEmptyString(campaign_id) ) {
                buf.append("campaign_id,");
                valueBuf.append("'{campaign_id}',");
                map.put("campaign_id", campaign_id);
            }
            if (StringUtil.isNotEmptyString(campaign_name) ) {
                buf.append("campaign_name,");
                valueBuf.append("'{campaign_name}',");
                map.put("campaign_name", campaign_name);
            }
            if (StringUtil.isNotEmptyString(ad_url) ) {
                buf.append("ad_url,");
                valueBuf.append("'{ad_url}',");
                map.put("ad_url", ad_url);
            }
            if (StringUtil.isNotEmptyString(ad_name) ) {
                buf.append("ad_name,");
                valueBuf.append("'{ad_name}',");
                map.put("ad_name", ad_name);
            }
            if (StringUtil.isNotEmptyString(region_name) ) {
                buf.append("region_name,");
                valueBuf.append("'{region_name}',");
                map.put("region_name", region_name);
            }
            if (StringUtil.isNotEmptyString(country_code) ) {
                buf.append("country_code,");
                valueBuf.append("'{country_code}',");
                map.put("country_code", country_code);
            }
            if (StringUtil.isNotEmptyString(currency_code) ) {
                buf.append("currency_code,");
                valueBuf.append("'{currency_code}',");
                map.put("currency_code", currency_code);
            }
            if (StringUtil.isNotEmptyString(existing_user) ) {
                buf.append("existing_user,");
                valueBuf.append("'{existing_user}',");
                map.put("existing_user", existing_user);
            }
            if (imp_time!=null ) {
                String value = DateUtil.formatDateTime(imp_time);
                buf.append("imp_time,");
                valueBuf.append("'{imp_time}',");
                map.put("imp_time", value);
            }
            if (StringUtil.isNotEmptyString(stat_click_id) ) {
                buf.append("stat_click_id,");
                valueBuf.append("'{stat_click_id}',");
                map.put("stat_click_id", stat_click_id);
            }
            if (StringUtil.isNotEmptyString(stat_impression_id) ) {
                buf.append("stat_impression_id,");
                valueBuf.append("'{stat_impression_id}',");
                map.put("stat_impression_id", stat_impression_id);
            }
            if (payout>0 ) {
                buf.append("payout,");
                valueBuf.append("{payout},");
                map.put("payout", String.valueOf(payout));
            }
            if (StringUtil.isNotEmptyString(referral_source) ) {
                buf.append("referral_source,");
                valueBuf.append("'{referral_source}',");
                map.put("referral_source", referral_source);
            }
            if (StringUtil.isNotEmptyString(referral_url) ) {
                buf.append("referral_url,");
                valueBuf.append("'{referral_url}',");
                map.put("referral_url", referral_url);
            }
            if (revenue>0 ) {
                buf.append("revenue,");
                valueBuf.append("{revenue},");
                map.put("revenue", String.valueOf(revenue));
            }
            if (revenue_usd>0 ) {
                buf.append("revenue_usd,");
                valueBuf.append("{revenue_usd},");
                map.put("revenue_usd", String.valueOf(revenue_usd));
            }
            if (StringUtil.isNotEmptyString(status) ) {
                buf.append("status,");
                valueBuf.append("'{status}',");
                map.put("status", status);
            }
            if (StringUtil.isNotEmptyString(status_code) ) {
                buf.append("status_code,");
                valueBuf.append("'{status_code}',");
                map.put("status_code", status_code);
            }
            if (StringUtil.isNotEmptyString(tracking_id) ) {
                buf.append("tracking_id,");
                valueBuf.append("'{tracking_id}',");
                map.put("tracking_id", tracking_id);
            }
            if (StringUtil.isNotEmptyString(ios_ifa) ) {
                buf.append("ios_ifa,");
                valueBuf.append("'{ios_ifa}',");
                map.put("ios_ifa", ios_ifa);
            }
            if (StringUtil.isNotEmptyString(ios_ifv) ) {
                buf.append("ios_ifv,");
                valueBuf.append("'{ios_ifv}',");
                map.put("ios_ifv", ios_ifv);
            }
            if (StringUtil.isNotEmptyString(google_aid) ) {
                buf.append("google_aid,");
                valueBuf.append("'{google_aid}',");
                map.put("google_aid", google_aid);
            }
            if (StringUtil.isNotEmptyString(pub_camp_id) ) {
                buf.append("pub_camp_id,");
                valueBuf.append("'{pub_camp_id}',");
                map.put("pub_camp_id", pub_camp_id);
            }
            if (StringUtil.isNotEmptyString(pub_camp_name) ) {
                buf.append("pub_camp_name,");
                valueBuf.append("'{pub_camp_name}',");
                map.put("pub_camp_name", pub_camp_name);
            }
            if (StringUtil.isNotEmptyString(pub_camp_ref) ) {
                buf.append("pub_camp_ref,");
                valueBuf.append("'{pub_camp_ref}',");
                map.put("pub_camp_ref", pub_camp_ref);
            }
            if (StringUtil.isNotEmptyString(pub_adset) ) {
                buf.append("pub_adset,");
                valueBuf.append("'{pub_adset}',");
                map.put("pub_adset", pub_adset);
            }
            if (StringUtil.isNotEmptyString(pub_ad) ) {
                buf.append("pub_ad,");
                valueBuf.append("'{pub_ad}',");
                map.put("pub_ad", pub_ad);
            }
            if (StringUtil.isNotEmptyString(pub_keyword) ) {
                buf.append("pub_keyword,");
                valueBuf.append("'{pub_keyword}',");
                map.put("pub_keyword", pub_keyword);
            }
            if (StringUtil.isNotEmptyString(pub_place) ) {
                buf.append("pub_place,");
                valueBuf.append("'{pub_place}',");
                map.put("pub_place", pub_place);
            }
            if (StringUtil.isNotEmptyString(pub_sub_id) ) {
                buf.append("pub_sub_id,");
                valueBuf.append("'{pub_sub_id}',");
                map.put("pub_sub_id", pub_sub_id);
            }
            if (StringUtil.isNotEmptyString(pub_sub_name) ) {
                buf.append("pub_sub_name,");
                valueBuf.append("'{pub_sub_name}',");
                map.put("pub_sub_name", pub_sub_name);
            }
            if (StringUtil.isNotEmptyString(adv_camp_id) ) {
                buf.append("adv_camp_id,");
                valueBuf.append("'{adv_camp_id}',");
                map.put("adv_camp_id", adv_camp_id);
            }
            if (StringUtil.isNotEmptyString(adv_camp_name) ) {
                buf.append("adv_camp_name,");
                valueBuf.append("'{adv_camp_name}',");
                map.put("adv_camp_name", adv_camp_name);
            }
            if (StringUtil.isNotEmptyString(adv_camp_ref) ) {
                buf.append("adv_camp_ref,");
                valueBuf.append("'{adv_camp_ref}',");
                map.put("adv_camp_ref", adv_camp_ref);
            }
            if (StringUtil.isNotEmptyString(adv_adset) ) {
                buf.append("adv_adset,");
                valueBuf.append("'{adv_adset}',");
                map.put("adv_adset", adv_adset);
            }
            if (StringUtil.isNotEmptyString(adv_ad) ) {
                buf.append("adv_ad,");
                valueBuf.append("'{adv_ad}',");
                map.put("adv_ad", adv_ad);
            }
            if (StringUtil.isNotEmptyString(adv_keyword) ) {
                buf.append("adv_keyword,");
                valueBuf.append("'{adv_keyword}',");
                map.put("adv_keyword", adv_keyword);
            }
            if (StringUtil.isNotEmptyString(adv_place) ) {
                buf.append("adv_place,");
                valueBuf.append("'{adv_place}',");
                map.put("adv_place", adv_place);
            }
            if (StringUtil.isNotEmptyString(adv_sub_id) ) {
                buf.append("adv_sub_id,");
                valueBuf.append("'{adv_sub_id}',");
                map.put("adv_sub_id", adv_sub_id);
            }
            if (StringUtil.isNotEmptyString(adv_sub_name) ) {
                buf.append("adv_sub_name,");
                valueBuf.append("'{adv_sub_name}',");
                map.put("adv_sub_name", adv_sub_name);
            }
            if (StringUtil.isNotEmptyString(sdk) ) {
                buf.append("sdk,");
                valueBuf.append("'{sdk}',");
                map.put("sdk", sdk);
            }
            if (StringUtil.isNotEmptyString(sdk_version) ) {
                buf.append("sdk_version,");
                valueBuf.append("'{sdk_version}',");
                map.put("sdk_version", sdk_version);
            }
            if (StringUtil.isNotEmptyString(game_user_id) ) {
                buf.append("game_user_id,");
                valueBuf.append("'{game_user_id}',");
                map.put("game_user_id", game_user_id);
            }
            if (os_jailbroke ) {
                buf.append("os_jailbroke,");
                valueBuf.append("{os_jailbroke},");
                map.put("os_jailbroke", "1");
            }
            if (StringUtil.isNotEmptyString(pub_pref_id) ) {
                buf.append("pub_pref_id,");
                valueBuf.append("'{pub_pref_id}',");
                map.put("pub_pref_id", pub_pref_id);
            }
            if (StringUtil.isNotEmptyString(pub_sub1) ) {
                buf.append("pub_sub1,");
                valueBuf.append("'{pub_sub1}',");
                map.put("pub_sub1", pub_sub1);
            }
            if (StringUtil.isNotEmptyString(pub_sub2) ) {
                buf.append("pub_sub2,");
                valueBuf.append("'{pub_sub2}',");
                map.put("pub_sub2", pub_sub2);
            }
            if (StringUtil.isNotEmptyString(pub_sub3) ) {
                buf.append("pub_sub3,");
                valueBuf.append("'{pub_sub3}',");
                map.put("pub_sub3", pub_sub3);
            }
            if (StringUtil.isNotEmptyString(pub_sub4) ) {
                buf.append("pub_sub4,");
                valueBuf.append("'{pub_sub4}',");
                map.put("pub_sub4", pub_sub4);
            }
            if (StringUtil.isNotEmptyString(pub_sub5) ) {
                buf.append("pub_sub5,");
                valueBuf.append("'{pub_sub5}',");
                map.put("pub_sub5", pub_sub5);
            }
            if (StringUtil.isNotEmptyString(cost_model) ) {
                buf.append("cost_model,");
                valueBuf.append("'{cost_model}',");
                map.put("cost_model", cost_model);
            }
            if (cost>0 ) {
                buf.append("cost,");
                valueBuf.append("{cost},");
                map.put("cost", String.valueOf(cost));
            }
            if (ip_from!=0 ) {
                buf.append("ip_from,");
                valueBuf.append("{ip_from},");
                map.put("ip_from", String.valueOf(ip_from));
            }
            if (ip_to != 0) {
                buf.append("ip_to,");
                valueBuf.append("{ip_to},");
                map.put("ip_to", String.valueOf(ip_to));
            }
            if (StringUtil.isNotEmptyString(city_code) ) {
                buf.append("city_code,");
                valueBuf.append("'{city_code}',");
                map.put("city_code", city_code);
            }
            if (StringUtil.isNotEmptyString(metro_code) ) {
                buf.append("metro_code,");
                valueBuf.append("'{metro_code}',");
                map.put("metro_code", metro_code);
            }
            buf.deleteCharAt(buf.length()-1);
            buf.append(") values ");
            valueBuf.deleteCharAt(valueBuf.length()-1);
            valueBuf.append(')');
            String sql = StringUtil.replaceKey(buf.append('\n').append(valueBuf.toString()).toString(), map);
            return sql;
        }
    }
}
