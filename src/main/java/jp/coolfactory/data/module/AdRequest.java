package jp.coolfactory.data.module;

import com.google.common.base.MoreObjects;
import jp.coolfactory.anti_fraud.module.Status;
import jp.coolfactory.data.Constants;
import jp.coolfactory.data.db.DBUtil;
import jp.coolfactory.data.util.DateUtil;
import jp.coolfactory.data.util.StringUtil;

import java.time.ZonedDateTime;
import java.util.HashMap;

/**
 * Created by wangqi on 22/2/2017.
 */
public class AdRequest implements SQLRequest {

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
    private String order_id;
    //For anti_fraud system
    private int postback_code;
    private String postback_desc;
    // 0-1 probability of true positive judgement
    private float eval_prop;
    private String af_site_id;
    private String af_camp_id;
    private Status af_status;
    private String postback;

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

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getAf_site_id() {
        return af_site_id;
    }

    public void setAf_site_id(String af_site_id) {
        this.af_site_id = af_site_id;
    }

    public String getAf_camp_id() {
        return af_camp_id;
    }

    public void setAf_camp_id(String af_camp_id) {
        this.af_camp_id = af_camp_id;
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

    public float getEval_prop() {
        return eval_prop;
    }

    public void setEval_prop(float eval_prop) {
        this.eval_prop = eval_prop;
    }

    public Status getAf_status() {
        return af_status;
    }

    public void setAf_status(Status af_status) {
        if ( af_status != null ) {
            this.af_status = af_status;
            this.status = af_status.getDesc();
            this.status_code = String.valueOf(af_status.getStatus());
        }
    }

    public String getPostback() {
        return postback;
    }

    public void setPostback(String postback) {
        this.postback = postback;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdRequest adRequest = (AdRequest) o;

        if (getAccount_key() != null ? !getAccount_key().equals(adRequest.getAccount_key()) : adRequest.getAccount_key() != null)
            return false;
        if (getAction() != null ? !getAction().equals(adRequest.getAction()) : adRequest.getAction() != null)
            return false;
        if (getAppKey() != null ? !getAppKey().equals(adRequest.getAppKey()) : adRequest.getAppKey() != null)
            return false;
        if (getSource() != null ? !getSource().equals(adRequest.getSource()) : adRequest.getSource() != null)
            return false;
        if (getStat_id() != null ? !getStat_id().equals(adRequest.getStat_id()) : adRequest.getStat_id() != null)
            return false;
        if (getPlat_id() != null ? !getPlat_id().equals(adRequest.getPlat_id()) : adRequest.getPlat_id() != null)
            return false;
        if (getInstall_ip() != null ? !getInstall_ip().equals(adRequest.getInstall_ip()) : adRequest.getInstall_ip() != null)
            return false;
        if (getInstall_time() != null ? !getInstall_time().equals(adRequest.getInstall_time()) : adRequest.getInstall_time() != null)
            return false;
        if (getSite_id() != null ? !getSite_id().equals(adRequest.getSite_id()) : adRequest.getSite_id() != null)
            return false;
        if (getSite_name() != null ? !getSite_name().equals(adRequest.getSite_name()) : adRequest.getSite_name() != null)
            return false;
        if (getIos_ifa() != null ? !getIos_ifa().equals(adRequest.getIos_ifa()) : adRequest.getIos_ifa() != null)
            return false;
        if (getIos_ifv() != null ? !getIos_ifv().equals(adRequest.getIos_ifv()) : adRequest.getIos_ifv() != null)
            return false;
        return getGoogle_aid() != null ? getGoogle_aid().equals(adRequest.getGoogle_aid()) : adRequest.getGoogle_aid() == null;
    }

    @Override
    public int hashCode() {
        int result = getAccount_key() != null ? getAccount_key().hashCode() : 0;
        result = 31 * result + (getAction() != null ? getAction().hashCode() : 0);
        result = 31 * result + (getAppKey() != null ? getAppKey().hashCode() : 0);
        result = 31 * result + (getSource() != null ? getSource().hashCode() : 0);
        result = 31 * result + (getStat_id() != null ? getStat_id().hashCode() : 0);
        result = 31 * result + (getPlat_id() != null ? getPlat_id().hashCode() : 0);
        result = 31 * result + (getInstall_ip() != null ? getInstall_ip().hashCode() : 0);
        result = 31 * result + (getInstall_time() != null ? getInstall_time().hashCode() : 0);
        result = 31 * result + (getSite_id() != null ? getSite_id().hashCode() : 0);
        result = 31 * result + (getSite_name() != null ? getSite_name().hashCode() : 0);
        result = 31 * result + (getIos_ifa() != null ? getIos_ifa().hashCode() : 0);
        result = 31 * result + (getIos_ifv() != null ? getIos_ifv().hashCode() : 0);
        result = 31 * result + (getGoogle_aid() != null ? getGoogle_aid().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(200);
        sb.append("").append('"').append(account_key).append('"');
        sb.append("\t").append('"').append(action).append('"');
        sb.append("\t").append('"').append(appKey).append('"');
        sb.append("\t").append('"').append(source).append('"');
        sb.append("\t").append('"').append(stat_id).append('"');
        sb.append("\t").append('"').append(os_version).append('"');
        sb.append("\t").append('"').append(device_id).append('"');
        sb.append("\t").append('"').append(device_type).append('"');
        sb.append("\t").append('"').append(device_brand).append('"');
        sb.append("\t").append('"').append(device_carrier).append('"');
        sb.append("\t").append('"').append(device_model).append('"');
        sb.append("\t").append('"').append(lang).append('"');
        sb.append("\t").append('"').append(plat_id).append('"');
        sb.append("\t").append('"').append(user_agent).append('"');
        sb.append("\t").append('"').append(publisher_id).append('"');
        sb.append("\t").append('"').append(publisher_name).append('"');
        sb.append("\t").append('"').append(click_ip).append('"');
        if ( click_time != null ) {
            sb.append("\t").append('"').append(DateUtil.formatDateTime(click_time)).append('"');
        } else {
            sb.append("\t").append("\"\"");
        }
        sb.append("\t").append('"').append(bundle_id).append('"');
        sb.append("\t").append('"').append(install_ip).append('"');
        if ( install_time != null ) {
            sb.append("\t").append('"').append(DateUtil.formatDateTime(install_time)).append('"');
        } else {
            sb.append("\t").append("\"\"");
        }
        sb.append("\t").append('"').append(agency_name).append('"');
        sb.append("\t").append('"').append(site_id).append('"');
        sb.append("\t").append('"').append(site_name).append('"');
        sb.append("\t").append('"').append(match_type).append('"');
        sb.append("\t").append('"').append(campaign_id).append('"');
        sb.append("\t").append('"').append(campaign_name).append('"');
        sb.append("\t").append('"').append(ad_url).append('"');
        sb.append("\t").append('"').append(ad_name).append('"');
        sb.append("\t").append('"').append(region_name).append('"');
        sb.append("\t").append('"').append(country_code).append('"');
        sb.append("\t").append('"').append(currency_code).append('"');
        sb.append("\t").append('"').append(existing_user).append('"');
        if ( imp_time != null ) {
            sb.append("\t").append('"').append(DateUtil.formatDateTime(imp_time)).append('"');
        } else {
            sb.append("\t").append("\"\"");
        }
        sb.append("\t").append('"').append(stat_click_id).append('"');
        sb.append("\t").append('"').append(stat_impression_id).append('"');
        sb.append("\t").append(payout);
        sb.append("\t").append('"').append(referral_source).append('"');
        sb.append("\t").append('"').append(referral_url).append('"');
        sb.append("\t").append(revenue);
        sb.append("\t").append(revenue_usd);
        sb.append("\t").append('"').append(status).append('"');
        sb.append("\t").append('"').append(status_code).append('"');
        sb.append("\t").append('"').append(tracking_id).append('"');
        sb.append("\t").append('"').append(ios_ifa).append('"');
        sb.append("\t").append('"').append(ios_ifv).append('"');
        sb.append("\t").append('"').append(google_aid).append('"');
        sb.append("\t").append('"').append(pub_camp_id).append('"');
        sb.append("\t").append('"').append(pub_camp_name).append('"');
        sb.append("\t").append('"').append(pub_camp_ref).append('"');
        sb.append("\t").append('"').append(pub_adset).append('"');
        sb.append("\t").append('"').append(pub_ad).append('"');
        sb.append("\t").append('"').append(pub_keyword).append('"');
        sb.append("\t").append('"').append(pub_place).append('"');
        sb.append("\t").append('"').append(pub_sub_id).append('"');
        sb.append("\t").append('"').append(pub_sub_name).append('"');
        sb.append("\t").append('"').append(adv_camp_id).append('"');
        sb.append("\t").append('"').append(adv_camp_name).append('"');
        sb.append("\t").append('"').append(adv_camp_ref).append('"');
        sb.append("\t").append('"').append(adv_adset).append('"');
        sb.append("\t").append('"').append(adv_ad).append('"');
        sb.append("\t").append('"').append(adv_keyword).append('"');
        sb.append("\t").append('"').append(adv_place).append('"');
        sb.append("\t").append('"').append(adv_sub_id).append('"');
        sb.append("\t").append('"').append(adv_sub_name).append('"');
        sb.append("\t").append('"').append(sdk).append('"');
        sb.append("\t").append('"').append(sdk_version).append('"');
        sb.append("\t").append('"').append(game_user_id).append('"');
        sb.append("\t").append(os_jailbroke);
        sb.append("\t").append('"').append(pub_pref_id).append('"');
        sb.append("\t").append('"').append(pub_sub1).append('"');
        sb.append("\t").append('"').append(pub_sub2).append('"');
        sb.append("\t").append('"').append(pub_sub3).append('"');
        sb.append("\t").append('"').append(pub_sub4).append('"');
        sb.append("\t").append('"').append(pub_sub5).append('"');
        sb.append("\t").append('"').append(cost_model).append('"');
        sb.append("\t").append(cost);
        sb.append("\t").append(ip_from);
        sb.append("\t").append(ip_to);
        sb.append("\t").append('"').append(city_code).append('"');
        sb.append("\t").append('"').append(metro_code).append('"');
        sb.append("\t").append('"').append(order_id).append('"');
        sb.append("\t").append(postback_code);
        sb.append("\t").append('"').append(postback_desc).append('"');
        sb.append("\t").append(eval_prop);
        sb.append("\t").append('"').append(af_site_id).append('"');
        sb.append("\t").append('"').append(af_camp_id).append('"');
        sb.append("\t").append('"').append(af_status).append('"');
        sb.append("\t").append('"').append(postback).append('"');
        return sb.toString();
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
            if (Constants.ACTION_INSTALL.equals(action) ) {
                buf.append("replace into ").append(DBUtil.getDatabaseSchema()).append(".ad_install (");
            } else if ( Constants.ACTION_CLICK.equals(action) ) {
                buf.append("replace into ").append(DBUtil.getDatabaseSchema()).append(".ad_click (");
            } else if ( Constants.ACTION_PURCHASE.equals(action) ) {
                buf.append("replace into ").append(DBUtil.getDatabaseSchema()).append(".ad_purchase (");
            } else {
                buf.append("replace into ").append(DBUtil.getDatabaseSchema()).append(".ad_event (");
            }
            buf.append("action,");
            valueBuf.append("'{action}',");
            map.put("action", StringUtil.validSQLInput(action));

            if (StringUtil.isNotEmptyString(account_key) ) {
                buf.append("account_key,");
                valueBuf.append("'{account_key}',");
                map.put("account_key", StringUtil.validSQLInput(account_key));
            }
            if (StringUtil.isNotEmptyString(source) ) {
                buf.append("source,");
                valueBuf.append("'{source}',");
                map.put("source", StringUtil.validSQLInput(source));
            }
            if (StringUtil.isNotEmptyString(stat_id) ) {
                buf.append("stat_id,");
                valueBuf.append("'{stat_id}',");
                map.put("stat_id", StringUtil.validSQLInput(stat_id));
            }
            if (StringUtil.isNotEmptyString(appKey) ) {
                buf.append("app_key,");
                valueBuf.append("'{app_key}',");
                map.put("app_key", StringUtil.validSQLInput(appKey));
            }
            if (StringUtil.isNotEmptyString(os_version) ) {
                buf.append("os_version,");
                valueBuf.append("'{os_version}',");
                map.put("os_version", StringUtil.validSQLInput(os_version));
            }
            if (StringUtil.isNotEmptyString(device_id) ) {
                buf.append("device_id,");
                valueBuf.append("'{device_id}',");
                map.put("device_id", StringUtil.validSQLInput(device_id));
            }
            if (StringUtil.isNotEmptyString(device_type) ) {
                buf.append("device_type,");
                valueBuf.append("'{device_type}',");
                map.put("device_type", StringUtil.validSQLInput(device_type));
            }
            if (StringUtil.isNotEmptyString(device_brand) ) {
                buf.append("device_brand,");
                valueBuf.append("'{device_brand}',");
                map.put("device_brand", StringUtil.validSQLInput(device_brand));
            }
            if (StringUtil.isNotEmptyString(device_carrier) ) {
                buf.append("device_carrier,");
                valueBuf.append("'{device_carrier}',");
                map.put("device_carrier", StringUtil.validSQLInput(device_carrier));
            }
            if (StringUtil.isNotEmptyString(device_model) ) {
                buf.append("device_model,");
                valueBuf.append("'{device_model}',");
                map.put("device_model", StringUtil.validSQLInput(device_model));
            }
            if (StringUtil.isNotEmptyString(lang) ) {
                buf.append("lang,");
                valueBuf.append("'{lang}',");
                map.put("lang", StringUtil.validSQLInput(lang));
            }
            if (StringUtil.isNotEmptyString(plat_id) ) {
                buf.append("plat_id,");
                valueBuf.append("'{plat_id}',");
                map.put("plat_id", StringUtil.validSQLInput(plat_id));
            }
            if (StringUtil.isNotEmptyString(user_agent) ) {
                buf.append("user_agent,");
                valueBuf.append("'{user_agent}',");
                map.put("user_agent", StringUtil.validSQLInput(user_agent));
            }
            if (StringUtil.isNotEmptyString(publisher_id) ) {
                buf.append("publisher_id,");
                valueBuf.append("'{publisher_id}',");
                map.put("publisher_id", StringUtil.validSQLInput(publisher_id));
            }
            if (StringUtil.isNotEmptyString(publisher_name) ) {
                buf.append("publisher_name,");
                valueBuf.append("'{publisher_name}',");
                map.put("publisher_name", StringUtil.validSQLInput(publisher_name));
            }
            if (StringUtil.isNotEmptyString(click_ip) ) {
                buf.append("click_ip,");
                valueBuf.append("'{click_ip}',");
                map.put("click_ip", StringUtil.validSQLInput(click_ip));
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
                map.put("bundle_id", StringUtil.validSQLInput(bundle_id));
            }
            if (StringUtil.isNotEmptyString(install_ip) ) {
                //Rename 'install_ip' to 'ip'
                buf.append("ip,");
                valueBuf.append("'{install_ip}',");
                map.put("install_ip", StringUtil.validSQLInput(install_ip));
            }
            if (install_time!=null ) {
                //Rename 'install_time' to 'created'
                String value = DateUtil.formatDateTime(install_time);
                buf.append("created,");
                valueBuf.append("'{install_time}',");
                map.put("install_time", value);
            }
            if (StringUtil.isNotEmptyString(agency_name) ) {
                buf.append("agency_name,");
                valueBuf.append("'{agency_name}',");
                map.put("agency_name", StringUtil.validSQLInput(agency_name));
            }
            if (StringUtil.isNotEmptyString(site_id) ) {
                buf.append("site_id,");
                valueBuf.append("'{site_id}',");
                map.put("site_id", StringUtil.validSQLInput(site_id));
            }
            if (StringUtil.isNotEmptyString(site_name) ) {
                buf.append("site_name,");
                valueBuf.append("'{site_name}',");
                map.put("site_name", StringUtil.validSQLInput(site_name));
            }
            if (StringUtil.isNotEmptyString(match_type) ) {
                buf.append("match_type,");
                valueBuf.append("'{match_type}',");
                map.put("match_type", StringUtil.validSQLInput(match_type));
            }
            if (StringUtil.isNotEmptyString(campaign_id) ) {
                buf.append("campaign_id,");
                valueBuf.append("'{campaign_id}',");
                map.put("campaign_id", StringUtil.validSQLInput(campaign_id));
            }
            if (StringUtil.isNotEmptyString(campaign_name) ) {
                buf.append("campaign_name,");
                valueBuf.append("'{campaign_name}',");
                map.put("campaign_name", StringUtil.validSQLInput(campaign_name));
            }
            if (StringUtil.isNotEmptyString(ad_url) ) {
                buf.append("ad_url,");
                valueBuf.append("'{ad_url}',");
                map.put("ad_url", StringUtil.validSQLInput(ad_url));
            }
            if (StringUtil.isNotEmptyString(ad_name) ) {
                buf.append("ad_name,");
                valueBuf.append("'{ad_name}',");
                map.put("ad_name", StringUtil.validSQLInput(ad_name));
            }
            if (StringUtil.isNotEmptyString(region_name) ) {
                buf.append("region_name,");
                valueBuf.append("'{region_name}',");
                map.put("region_name", StringUtil.validSQLInput(region_name));
            }
            if (StringUtil.isNotEmptyString(country_code) ) {
                buf.append("country_code,");
                valueBuf.append("'{country_code}',");
                map.put("country_code", StringUtil.validSQLInput(country_code));
            }
            if (StringUtil.isNotEmptyString(currency_code) ) {
                buf.append("currency_code,");
                valueBuf.append("'{currency_code}',");
                map.put("currency_code", StringUtil.validSQLInput(currency_code));
            }
            if (StringUtil.isNotEmptyString(existing_user) ) {
                buf.append("existing_user,");
                valueBuf.append("'{existing_user}',");
                map.put("existing_user", StringUtil.validSQLInput(existing_user));
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
                map.put("stat_click_id", StringUtil.validSQLInput(stat_click_id));
            }
            if (StringUtil.isNotEmptyString(stat_impression_id) ) {
                buf.append("stat_impression_id,");
                valueBuf.append("'{stat_impression_id}',");
                map.put("stat_impression_id", StringUtil.validSQLInput(stat_impression_id));
            }
            if (payout>0 ) {
                buf.append("payout,");
                valueBuf.append("{payout},");
                map.put("payout", String.valueOf(payout));
            }
            if (StringUtil.isNotEmptyString(referral_source) ) {
                buf.append("referral_source,");
                valueBuf.append("'{referral_source}',");
                map.put("referral_source", StringUtil.validSQLInput(referral_source));
            }
            if (StringUtil.isNotEmptyString(referral_url) ) {
                buf.append("referral_url,");
                valueBuf.append("'{referral_url}',");
                map.put("referral_url", StringUtil.validSQLInput(referral_url));
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
                map.put("status", StringUtil.validSQLInput(status));
            }
            if (StringUtil.isNotEmptyString(status_code) ) {
                buf.append("status_code,");
                valueBuf.append("'{status_code}',");
                map.put("status_code", StringUtil.validSQLInput(status_code));
            }
            if (StringUtil.isNotEmptyString(tracking_id) ) {
                buf.append("tracking_id,");
                valueBuf.append("'{tracking_id}',");
                map.put("tracking_id", StringUtil.validSQLInput(tracking_id));
            }
            if (StringUtil.isNotEmptyString(ios_ifa) ) {
                buf.append("ios_ifa,");
                valueBuf.append("'{ios_ifa}',");
                map.put("ios_ifa", StringUtil.validSQLInput(ios_ifa));
            }
            if (StringUtil.isNotEmptyString(ios_ifv) ) {
                buf.append("ios_ifv,");
                valueBuf.append("'{ios_ifv}',");
                map.put("ios_ifv", StringUtil.validSQLInput(ios_ifv));
            }
            if (StringUtil.isNotEmptyString(google_aid) ) {
                buf.append("google_aid,");
                valueBuf.append("'{google_aid}',");
                map.put("google_aid", StringUtil.validSQLInput(google_aid));
            }
            if (StringUtil.isNotEmptyString(pub_camp_id) ) {
                buf.append("pub_camp_id,");
                valueBuf.append("'{pub_camp_id}',");
                map.put("pub_camp_id", StringUtil.validSQLInput(pub_camp_id));
            }
            if (StringUtil.isNotEmptyString(pub_camp_name) ) {
                buf.append("pub_camp_name,");
                valueBuf.append("'{pub_camp_name}',");
                map.put("pub_camp_name", StringUtil.validSQLInput(pub_camp_name));
            }
            if (StringUtil.isNotEmptyString(pub_camp_ref) ) {
                buf.append("pub_camp_ref,");
                valueBuf.append("'{pub_camp_ref}',");
                map.put("pub_camp_ref", StringUtil.validSQLInput(pub_camp_ref));
            }
            if (StringUtil.isNotEmptyString(pub_adset) ) {
                buf.append("pub_adset,");
                valueBuf.append("'{pub_adset}',");
                map.put("pub_adset", StringUtil.validSQLInput(pub_adset));
            }
            if (StringUtil.isNotEmptyString(pub_ad) ) {
                buf.append("pub_ad,");
                valueBuf.append("'{pub_ad}',");
                map.put("pub_ad", StringUtil.validSQLInput(pub_ad));
            }
            if (StringUtil.isNotEmptyString(pub_keyword) ) {
                buf.append("pub_keyword,");
                valueBuf.append("'{pub_keyword}',");
                map.put("pub_keyword", StringUtil.validSQLInput(pub_keyword));
            }
            if (StringUtil.isNotEmptyString(pub_place) ) {
                buf.append("pub_place,");
                valueBuf.append("'{pub_place}',");
                map.put("pub_place", StringUtil.validSQLInput(pub_place));
            }
            if (StringUtil.isNotEmptyString(pub_sub_id) ) {
                buf.append("pub_sub_id,");
                valueBuf.append("'{pub_sub_id}',");
                map.put("pub_sub_id", StringUtil.validSQLInput(pub_sub_id));
            }
            if (StringUtil.isNotEmptyString(pub_sub_name) ) {
                buf.append("pub_sub_name,");
                valueBuf.append("'{pub_sub_name}',");
                map.put("pub_sub_name", StringUtil.validSQLInput(pub_sub_name));
            }
            if (StringUtil.isNotEmptyString(adv_camp_id) ) {
                buf.append("adv_camp_id,");
                valueBuf.append("'{adv_camp_id}',");
                map.put("adv_camp_id", StringUtil.validSQLInput(adv_camp_id));
            }
            if (StringUtil.isNotEmptyString(adv_camp_name) ) {
                buf.append("adv_camp_name,");
                valueBuf.append("'{adv_camp_name}',");
                map.put("adv_camp_name", StringUtil.validSQLInput(adv_camp_name));
            }
            if (StringUtil.isNotEmptyString(adv_camp_ref) ) {
                buf.append("adv_camp_ref,");
                valueBuf.append("'{adv_camp_ref}',");
                map.put("adv_camp_ref", StringUtil.validSQLInput(adv_camp_ref));
            }
            if (StringUtil.isNotEmptyString(adv_adset) ) {
                buf.append("adv_adset,");
                valueBuf.append("'{adv_adset}',");
                map.put("adv_adset", StringUtil.validSQLInput(adv_adset));
            }
            if (StringUtil.isNotEmptyString(adv_ad) ) {
                buf.append("adv_ad,");
                valueBuf.append("'{adv_ad}',");
                map.put("adv_ad", StringUtil.validSQLInput(adv_ad));
            }
            if (StringUtil.isNotEmptyString(adv_keyword) ) {
                buf.append("adv_keyword,");
                valueBuf.append("'{adv_keyword}',");
                map.put("adv_keyword", StringUtil.validSQLInput(adv_keyword));
            }
            if (StringUtil.isNotEmptyString(adv_place) ) {
                buf.append("adv_place,");
                valueBuf.append("'{adv_place}',");
                map.put("adv_place", StringUtil.validSQLInput(adv_place));
            }
            if (StringUtil.isNotEmptyString(adv_sub_id) ) {
                buf.append("adv_sub_id,");
                valueBuf.append("'{adv_sub_id}',");
                map.put("adv_sub_id", StringUtil.validSQLInput(adv_sub_id));
            }
            if (StringUtil.isNotEmptyString(adv_sub_name) ) {
                buf.append("adv_sub_name,");
                valueBuf.append("'{adv_sub_name}',");
                map.put("adv_sub_name", StringUtil.validSQLInput(adv_sub_name));
            }
            if (StringUtil.isNotEmptyString(sdk) ) {
                buf.append("sdk,");
                valueBuf.append("'{sdk}',");
                map.put("sdk", StringUtil.validSQLInput(sdk));
            }
            if (StringUtil.isNotEmptyString(sdk_version) ) {
                buf.append("sdk_version,");
                valueBuf.append("'{sdk_version}',");
                map.put("sdk_version", StringUtil.validSQLInput(sdk_version));
            }
            if (StringUtil.isNotEmptyString(game_user_id) ) {
                buf.append("game_user_id,");
                valueBuf.append("'{game_user_id}',");
                map.put("game_user_id", StringUtil.validSQLInput(game_user_id));
            }
            if (os_jailbroke ) {
                buf.append("os_jailbroke,");
                valueBuf.append("{os_jailbroke},");
                map.put("os_jailbroke", "1");
            }
            if (StringUtil.isNotEmptyString(pub_pref_id) ) {
                buf.append("pub_pref_id,");
                valueBuf.append("'{pub_pref_id}',");
                map.put("pub_pref_id", StringUtil.validSQLInput(pub_pref_id));
            }
            if (StringUtil.isNotEmptyString(pub_sub1) ) {
                buf.append("pub_sub1,");
                valueBuf.append("'{pub_sub1}',");
                map.put("pub_sub1", StringUtil.validSQLInput(pub_sub1));
            }
            if (StringUtil.isNotEmptyString(pub_sub2) ) {
                buf.append("pub_sub2,");
                valueBuf.append("'{pub_sub2}',");
                map.put("pub_sub2", StringUtil.validSQLInput(pub_sub2));
            }
            if (StringUtil.isNotEmptyString(pub_sub3) ) {
                buf.append("pub_sub3,");
                valueBuf.append("'{pub_sub3}',");
                map.put("pub_sub3", StringUtil.validSQLInput(pub_sub3));
            }
            if (StringUtil.isNotEmptyString(pub_sub4) ) {
                buf.append("pub_sub4,");
                valueBuf.append("'{pub_sub4}',");
                map.put("pub_sub4", StringUtil.validSQLInput(pub_sub4));
            }
            if (StringUtil.isNotEmptyString(pub_sub5) ) {
                buf.append("pub_sub5,");
                valueBuf.append("'{pub_sub5}',");
                map.put("pub_sub5", StringUtil.validSQLInput(pub_sub5));
            }
            if (StringUtil.isNotEmptyString(cost_model) ) {
                buf.append("cost_model,");
                valueBuf.append("'{cost_model}',");
                map.put("cost_model", StringUtil.validSQLInput(cost_model));
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
                map.put("city_code", StringUtil.validSQLInput(city_code));
            }
            if (StringUtil.isNotEmptyString(metro_code) ) {
                buf.append("metro_code,");
                valueBuf.append("'{metro_code}',");
                map.put("metro_code", StringUtil.validSQLInput(metro_code));
            }
            if (StringUtil.isNotEmptyString(order_id) ) {
                buf.append("order_id,");
                valueBuf.append("'{order_id}',");
                map.put("order_id", StringUtil.validSQLInput(order_id));
            }
            if (postback_code != 0 ) {
                buf.append("postback_code,");
                valueBuf.append("{postback_code},");
                map.put("postback_code", String.valueOf(postback_code));
            }
            if (StringUtil.isNotEmptyString(postback_desc) ) {
                buf.append("postback_desc,");
                valueBuf.append("'{postback_desc}',");
                map.put("postback_desc", StringUtil.validSQLInput(postback_desc));
            }
            if (eval_prop > 0 ) {
                buf.append("eval_prop,");
                valueBuf.append("{eval_prop},");
                map.put("eval_prop", String.valueOf(eval_prop));
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
