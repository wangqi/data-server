package jp.coolfactory.data.server;

import jp.coolfactory.data.Constants;
import jp.coolfactory.data.Version;
import jp.coolfactory.data.module.AdRequest;
import jp.coolfactory.data.module.URLJob;
import jp.coolfactory.data.s2s.TalkingDataTrackingLink;
import jp.coolfactory.data.s2s.TrackingLink;
import jp.coolfactory.data.s2s.TrackingLinkRecord;
import jp.coolfactory.data.s2s.TuneKeyParamNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * TalkingData uses tracking link as the following format:
 *      https://lnk0.com/El8Qh8?chn=toutiao&idfa=__IDFA__&osversion=__OS__&ip=__IP__&clicktime=__TS__&useragent=__UA__&callback={callback_param}&action=none
 * I plan to create a transit link like
 *      https://api.qiku.mobi/td/El8Qh8?chn=toutiao&idfa=__IDFA__&osversion=__OS__&ip=__IP__&clicktime=__TS__&useragent=__UA__&callback={callback_param}&action=none
 * This links refers to this servlet and it will construct the talkingdata link and refresh MAT link simultaneously.
 *
 * The TalkingData Macros are as follow:
 *   __CALLBACK__
 *   __IDFA__
 *   __IP__
 *   __MAC__
 *   __OS__
 *   __SESSIONID__
 *   __TS__
 *   __UA__
 *
 * The parameter names include:
 *    ADID
 *    aid
 *    androidid
 *    cid
 *    click_id
 *    clicktime
 *    compaign
 *    creative
 *    customerid
 *    ideaid
 *    idfa
 *    idfa_md5
 *    imei_md5
 *    ip
 *    ip
 *    kwid
 *    LBS
 *    mac
 *    mac_md5
 *    momoid
 *    osversion
 *    pid
 *    session_id
 *    td_subid
 *    traceid
 *    uid
 *    useragent
 *
 *
 *
 * At the same time, we use MAT's server side measurement-api to notify MAT get this click too.
 * The spec is at here: https://developers.tune.com/measurement-docs/measuring-clicks/
 *
 * URL:
 *    https://YOUR_PUBLISHER_ID.api-01.com/serve?
 * required parameters include:
 *    1. action=click
 *    2. publisher_id=<publisher_id>
 *    3. site_id=<site_id>
 *    4. device_ip=<device_ip> If you can’t include the device ip, please set our country code.
 *    5. country_code=<country_code> Refer to: http://www.worldatlas.com/aatlas/ctycodes.htm
 *    6. response_format=json
 *    7. google_aid – The Google Advertising Identifier or the Android ID (Android only).
 *    8. ios_ifa – The Apple Identifier for Advertisers value (iOS only). *See NOTE below if using iOS10.
 *                 Please always use the lower-cased format of the device's advertising identifiers when hashing to
 *                 ensure a 1:1 match with your TUNE data.
 *    9. user_agent
 * optional parameters include:
 *    1. campaign_id – ID of the mobile app created by TUNE and provided by advertiser
 *    2. sub_campaign - Name or ID of the campaign on in partner’s platform.
 *
 *  Example:
 *      https://1.api-01.com/serve?action=click&publisher_id=11648&site_id=2962&device_ip=123.123.123.123&ios_ifa=550e8400-e29b-41d4-a716-446655440000&response_format=json
 *  Response:
 *    {
 *      "success": true,
 *      "url": "http://itunes.apple.com/us/app/atomic-dodge-ball-lite/id550854415",
 *      "invoke_url": "myapp://specificpage?mat_click_id=9452cbd386069ba774-20140626-877",
 *      "tracking_id": "4e0146e109345e5b1955415f0472a743",
 *      "log_id": "a56f2647ef00c3a-20130203-877"
 *    }
 *
 * Created by wangqi on 08/03/2018.
 */

@WebServlet(name = "TrackingLinkServlet", urlPatterns = {"/tk"})
public class TrackingLinkServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger("track_log");

    @Override
    protected void doHead(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        StringBuilder msgBuf = new StringBuilder(200);
        try {
            String forward_ip = request.getHeader("x-forwarded-for");
            Map<String, String[]> maps = new HashMap<String, String[]>(request.getParameterMap());
            maps.put("x-forwarded-for", new String[]{forward_ip});
            TrackingLink trackingLink = new TalkingDataTrackingLink();
            TrackingLinkRecord record = trackingLink.processTracking(maps);
            URLJobManager jobManager = (URLJobManager) Version.CONTEXT.get(Constants.URL_JOB_MANAGER);
            msgBuf.append(record.isRedirect()).append("\t").append(record.getMatS2SUrl()).append("\t").append(record.getThirdPartyS2SUrl());
            // Send MAT message
            URLJob adReq = new URLJob();
            adReq.setPostback(record.getMatS2SUrl());
            adReq.setTracking(true);
            jobManager.submitRequest(adReq);
            if ( record.isRedirect() ) {
                response.sendRedirect(record.getThirdPartyS2SUrl());
            } else {
                //Notify third-party server
                adReq = new URLJob();
                adReq.setPostback(record.getThirdPartyS2SUrl());
                adReq.setTracking(true);
                jobManager.submitRequest(adReq);
            }
        } catch (Exception e) {
            msgBuf.append("\tError:").append(e.getMessage());
        } finally {
            LOGGER.info(msgBuf.toString());
        }
    }

}
