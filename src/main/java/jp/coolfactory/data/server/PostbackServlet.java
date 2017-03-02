package jp.coolfactory.data.server;

import jp.coolfactory.data.Constants;
import jp.coolfactory.data.controller.AdAppController;
import jp.coolfactory.data.controller.AdCommandController;
import jp.coolfactory.data.controller.AdParamMapController;
import jp.coolfactory.data.db.DBUtil;
import jp.coolfactory.data.module.AdApp;
import jp.coolfactory.data.module.AdRequest;
import jp.coolfactory.data.util.DateUtil;
import jp.coolfactory.data.util.StringUtil;
import jp.coolfactory.data.util.URLUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.Executors;

/**
 * It's a generic postback receiver that can process default standard action postback parameters
 * and save them into database.
 *
 * Created by wangqi on 23/11/2016.
 */
@WebServlet(name = "PostbackServlet", urlPatterns = {"/pb"})
public class PostbackServlet extends HttpServlet {

    private final static Logger LOGGER = Logger.getLogger(PostbackServlet.class.getName());
    private final static Logger ACCESS_LOGGER = Logger.getLogger("access_log");

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doRequest(request, response);
    }

    protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        //Print the access log.
        printAccessLog(request);

        HashMap<String, String> params = new HashMap<String, String>();
        String action = request.getParameter("action");
        String source = request.getParameter("source");
        if ( source == null ) {
            source = Constants.SOURCE_UNKNOWN;
        } else {
            source = source.toLowerCase();
        }
        String app_key = request.getParameter("app_key");

        AdRequest req = new AdRequest();
        ZonedDateTime created = getParamValueAsDate(request,source, "install_time");
        if ( created == null ) {
            created = ZonedDateTime.now();
        }
        req.setInstall_time(created);
        req.setAction(action);
        req.setSource(source);
        req.setAppKey(app_key);
        req.setStat_id(getParamValue(request,source, "stat_id"));
        req.setOs_version(getParamValue(request,source, "os_version"));
        req.setDevice_id(getParamValue(request,source, "device_id"));
        req.setDevice_type(getParamValue(request,source, "device_type"));
        req.setDevice_brand(getParamValue(request,source, "device_brand"));
        req.setDevice_carrier(getParamValue(request,source, "device_carrier"));
        req.setDevice_model(getParamValue(request,source, "device_model"));
        req.setLang(getParamValue(request,source, "lang"));
        req.setPlat_id(getParamValue(request,source, "plat_id"));
        req.setUser_agent(getParamValue(request,source, "user_agent"));
        req.setPublisher_id(getParamValue(request,source, "publisher_id"));
        req.setPublisher_name(getParamValue(request,source, "publisher_name"));
        req.setClick_ip(getParamValue(request,source, "click_ip"));
        req.setClick_time(getParamValueAsDate(request,source, "click_time"));
        req.setBundle_id(getParamValue(request,source, "bundle_id"));
        req.setInstall_ip(getParamValue(request,source, "ip"));
        req.setAgency_name(getParamValue(request,source, "agency_name"));
        req.setSite_id(getParamValue(request,source, "site_id"));
        req.setSite_name(getParamValue(request,source, "site_name"));
        req.setMatch_type(getParamValue(request,source, "match_type"));
        req.setCampaign_id(getParamValue(request,source, "campaign_id"));
        req.setCampaign_name(getParamValue(request,source, "campaign_name"));
        req.setAd_url(getParamValue(request,source, "ad_url"));
        req.setAd_name(getParamValue(request,source, "ad_name"));
        req.setRegion_name(getParamValue(request,source, "region_name"));
        req.setCountry_code(getParamValue(request,source, "country_code"));
        req.setCurrency_code(getParamValue(request,source, "currency_code"));
        req.setExisting_user(getParamValue(request,source, "existing_user"));
        req.setImp_time(getParamValueAsDate(request,source, "imp_time"));
        req.setStat_click_id(getParamValue(request,source, "stat_click_id"));
        req.setStat_impression_id(getParamValue(request,source, "stat_impression_id"));
        req.setPayout(getParamValueAsDouble(request,source, "payout"));
        req.setReferral_source(getParamValue(request,source, "referral_source"));
        req.setReferral_url(getParamValue(request,source, "referral_url"));
        req.setRevenue(getParamValueAsDouble(request,source, "revenue"));
        req.setRevenue_usd(getParamValueAsDouble(request,source, "revenue_usd"));
        req.setStatus(getParamValue(request,source, "status"));
        req.setStatus_code(getParamValue(request,source, "status_code"));
        req.setTracking_id(getParamValue(request,source, "tracking_id"));
        req.setIos_ifa(getParamValue(request,source, "ios_ifa"));
        req.setIos_ifv(getParamValue(request,source, "ios_ifv"));
        req.setGoogle_aid(getParamValue(request,source, "google_aid"));
        req.setPub_camp_id(getParamValue(request,source, "pub_camp_id"));
        req.setPub_camp_name(getParamValue(request,source, "pub_camp_name"));
        req.setPub_camp_ref(getParamValue(request,source, "pub_camp_ref"));
        req.setPub_adset(getParamValue(request,source, "pub_adset"));
        req.setPub_ad(getParamValue(request,source, "pub_ad"));
        req.setPub_keyword(getParamValue(request,source, "pub_keyword"));
        req.setPub_place(getParamValue(request,source, "pub_place"));
        req.setPub_sub_id(getParamValue(request,source, "pub_sub_id"));
        req.setPub_sub_name(getParamValue(request,source, "pub_sub_name"));
        req.setAdv_camp_id(getParamValue(request,source, "adv_camp_id"));
        req.setAdv_camp_name(getParamValue(request,source, "adv_camp_name"));
        req.setAdv_camp_ref(getParamValue(request,source, "adv_camp_ref"));
        req.setAdv_adset(getParamValue(request,source, "adv_adset"));
        req.setAdv_ad(getParamValue(request,source, "adv_ad"));
        req.setAdv_keyword(getParamValue(request,source, "adv_keyword"));
        req.setAdv_place(getParamValue(request,source, "adv_place"));
        req.setAdv_sub_id(getParamValue(request,source, "adv_sub_id"));
        req.setAdv_sub_name(getParamValue(request,source, "adv_sub_name"));
        req.setSdk(getParamValue(request,source, "sdk"));
        req.setSdk_version(getParamValue(request,source, "sdk_version"));
        req.setGame_user_id(getParamValue(request,source, "game_user_id"));
        req.setOs_jailbroke(getParamValueAsBool(request,source, "os_jailbroke"));
        req.setPub_pref_id(getParamValue(request,source, "pub_pref_id"));
        req.setPub_sub1(getParamValue(request,source, "pub_sub1"));
        req.setPub_sub2(getParamValue(request,source, "pub_sub2"));
        req.setPub_sub3(getParamValue(request,source, "pub_sub3"));
        req.setCost_model(getParamValue(request,source, "cost_model"));
        req.setCost(getParamValueAsDouble(request,source, "cost"));
        req.setIp_from(getParamValueAsLong(request,source, "ip_from"));
        req.setIp_to(getParamValueAsLong(request,source, "ip_to"));
        req.setCity_code(getParamValue(request,source, "city_code"));
        req.setMetro_code(getParamValue(request,source, "metro_code"));

        //Call the chain to process the request.
        AdCommandController.getInstance().handle(req);

        Writer out = response.getWriter().append("OK");
        out.close();
    }

    /**
     * Print the detail information about this access.
     * @param request
     */
    private void printAccessLog(HttpServletRequest request) {
        String method = request.getMethod();
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        String port = String.valueOf(request.getServerPort());
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        String postData = null;
        Map<String, String[]> map = request.getParameterMap();
        StringBuilder buf = new StringBuilder(200);
        for ( String name : map.keySet() ) {
            String[] values = map.get(name);
            for ( String value : values ) {
                buf.append(name).append("=").append(value).append("&");
            }
        }
        postData = buf.toString();
        String forwardIp = request.getHeader("X-Forwarded-For");
        String ip = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        buf = new StringBuilder(200);
        buf.append(method).append('\t').append(scheme).append('\t').append(serverName)
                .append('\t').append(port).append('\t').append(uri)
                .append('\t').append(queryString==null?"":queryString).append('\t').append(forwardIp==null?"":forwardIp)
                .append('\t').append(ip).append('\t').append(postData)
                .append('\t').append(userAgent);
        ACCESS_LOGGER.info(buf.toString());
    }

    /**
     * Get param value as string. Note it will lowercase the value.
     * @param request
     * @param source
     * @param stdParamName
     * @return
     */
    private String getParamValue(HttpServletRequest request, String source, String stdParamName) {
        String value = request.getParameter(AdParamMapController.getInstance().translateStdName(source, stdParamName));
        try {
            if ( value !=null )
                value = URLDecoder.decode(value, "utf-8");
        } catch (UnsupportedEncodingException e) {
        }
        if (StringUtil.isNotEmptyString(value) ) {
            value = value.toLowerCase();
        }
        return value;
    }

    /**
     * Get param value as double
     * @param request
     * @param source
     * @param stdParamName
     * @return
     */
    private double getParamValueAsDouble(HttpServletRequest request, String source, String stdParamName) {
        double value = 0;
        String temp = request.getParameter(AdParamMapController.getInstance().translateStdName(source, stdParamName));
        try {
            value = Double.parseDouble(temp);
        } catch (Exception e) {
            value = 0;
        }
        return value;
    }

    /**
     * Get param value as int
     * @param request
     * @param source
     * @param stdParamName
     * @return
     */
    private int getParamValueAsInt(HttpServletRequest request, String source, String stdParamName) {
        int value = 0;
        String temp = request.getParameter(AdParamMapController.getInstance().translateStdName(source, stdParamName));
        try {
            value = Integer.parseInt(temp);
        } catch (Exception e) {
            value = 0;
        }
        return value;
    }

    /**
     * Get param value as int
     * @param request
     * @param source
     * @param stdParamName
     * @return
     */
    private long getParamValueAsLong(HttpServletRequest request, String source, String stdParamName) {
        long value = 0;
        String temp = request.getParameter(AdParamMapController.getInstance().translateStdName(source, stdParamName));
        try {
            value = Long.parseLong(temp);
        } catch (Exception e) {
            value = 0;
        }
        return value;
    }

    /**
     * Get param value as int
     * @param request
     * @param source
     * @param stdParamName
     * @return
     */
    private boolean getParamValueAsBool(HttpServletRequest request, String source, String stdParamName) {
        boolean value = false;
        String temp = request.getParameter(AdParamMapController.getInstance().translateStdName(source, stdParamName));
        try {
            if ( StringUtil.isNotEmptyString(temp) ) {
                temp = temp.toLowerCase();
            }
            if ("1".equalsIgnoreCase(temp) || "yes".equalsIgnoreCase(temp) ||
                    "true".equalsIgnoreCase(temp) || "on".equalsIgnoreCase(temp))
                value = true;
        } catch (Exception e) {
            value = false;
        }
        return value;
    }

    /**
     * Get param value as double
     * @param request
     * @param source
     * @param stdParamName
     * @return
     */
    private ZonedDateTime getParamValueAsDate(HttpServletRequest request, String source, String stdParamName) {
        String app_key = request.getParameter("app_key");
        String temp = request.getParameter(AdParamMapController.getInstance().translateStdName(source, stdParamName));
        return AdAppController.getInstance().parseDateString(app_key, temp);
    }
}
