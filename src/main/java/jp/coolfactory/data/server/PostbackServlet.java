package jp.coolfactory.data.server;

import jp.coolfactory.data.controller.AdAppController;
import jp.coolfactory.data.controller.AdCommandController;
import jp.coolfactory.data.controller.AdParamMapController;
import jp.coolfactory.data.db.DBUtil;
import jp.coolfactory.data.module.AdApp;
import jp.coolfactory.data.module.AdRequest;
import jp.coolfactory.data.util.DateUtil;
import jp.coolfactory.data.util.URLUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashMap;
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doRequest(request, response);
    }

    protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        HashMap<String, String> params = new HashMap<String, String>();
        String action = request.getParameter("action");
        String source = request.getParameter("source");
        String app_key = request.getParameter("app_key");

        AdRequest req = new AdRequest();
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
        req.setInstall_ip(getParamValue(request,source, "install_ip"));
        req.setInstall_time(getParamValueAsDate(request,source, "install_time"));
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
        req.setRevenue_usd(getParamValue(request,source, "revenue_usd"));
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
     * Send back the postback URL
     * @param postback
     * @param params
     */
    private void sendPostback(String postback, HashMap<String, String> params ) {
        Executors.newCachedThreadPool().submit(() -> {
            try {
                if ( postback != null && postback != "" ) {
                    LOGGER.info("Resend to postback to " + postback);
                    String postbackEncoded = URLUtil.encodeURL(postback);
                    LOGGER.info("Encode postback to " + postbackEncoded);
                    URL url = new URL(postbackEncoded);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("User-Agent", "antifraud/1.0");
                    int responseCode = conn.getResponseCode();
                    StringBuffer response = new StringBuffer();
                    if ( responseCode > 200 ) {
                        LOGGER.warn("Failed to send postback. Response code: " + responseCode);
                    } else {
                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String inputLine;
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();
                    }
                    params.put("postback_code", String.valueOf(responseCode));
                    params.put("postback_desc", response.toString());
                } else {
                    LOGGER.warn("postback param does not exist");
                }
            } catch (MalformedURLException e) {
                LOGGER.warn("Malformed postback url: " + postback);
            } catch (IOException e) {
                LOGGER.warn("Failed to connect to postback url: " + postback, e);
            } finally {
                DBUtil.saveLog(params);
            }
        });
    }

    /**
     * Get param value as string
     * @param request
     * @param source
     * @param stdParamName
     * @return
     */
    private String getParamValue(HttpServletRequest request, String source, String stdParamName) {
        String value = request.getParameter(AdParamMapController.getInstance().translateStdName(source, stdParamName));
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
