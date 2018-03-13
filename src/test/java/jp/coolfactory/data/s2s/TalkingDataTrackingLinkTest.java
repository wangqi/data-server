package jp.coolfactory.data.s2s;

import jp.coolfactory.data.util.StringUtil;
import org.junit.Test;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class TalkingDataTrackingLinkTest {

    @Test
    public void parseTuneParamValue() throws Exception {
        TalkingDataTrackingLink tdLink = new TalkingDataTrackingLink();
        String url = "http://api.qiku.mobi/td?ip=11.22.33.44";
        Map<String, String> params = StringUtil.url2MapSingle(new URL(url));
        HashMap<String, String[]> keyMap = new HashMap<>();
        keyMap.put(TuneKeyParamNames.DEVICE_IP, new String[]{"ip", "device_ip", "click_ip"});
        String[] values = tdLink.parseTuneParamValue(TuneKeyParamNames.DEVICE_IP, keyMap, params);
        assertEquals("11.22.33.44", values[0]);
        assertEquals("ip", values[1]);
    }

    @Test
    public void parseTuneParamValue2() throws Exception {
        TalkingDataTrackingLink tdLink = new TalkingDataTrackingLink();
        String url = "http://api.qiku.mobi/td?device_ip=11.22.33.44";
        Map<String, String> params = StringUtil.url2MapSingle(new URL(url));
        HashMap<String, String[]> keyMap = new HashMap<>();
        keyMap.put(TuneKeyParamNames.DEVICE_IP, new String[]{"ip"});
        String[] values = tdLink.parseTuneParamValue(TuneKeyParamNames.DEVICE_IP, keyMap, params);
        assertEquals("11.22.33.44", values[0]);
        assertEquals("device_ip", values[1]);
    }

    @Test
    public void parseTuneParamValue3() throws Exception {
        TalkingDataTrackingLink tdLink = new TalkingDataTrackingLink();
        String url = "http://api.qiku.mobi/td?click_ip=11.22.33.44";
        Map<String, String> params = StringUtil.url2MapSingle(new URL(url));
        HashMap<String, String[]> keyMap = new HashMap<>();
        keyMap.put(TuneKeyParamNames.DEVICE_IP, new String[]{"ip"});
        Object values = tdLink.parseTuneParamValue(TuneKeyParamNames.DEVICE_IP, keyMap, params);
        assertEquals(null, values);
    }

    @Test
    public void contructThirdPartyS2SLink() throws Exception {
        String url = "http://api.qiku.mobi/td?tp_prot=https&tp_host=lnk0.com&tp_path=El8Qh8&chn=toutiao&idfa=__IDFA__&osversion=__OS__&ip=__IP__&clicktime=__TS__&useragent=__UA__&callback={callback_param}&action=none&publisher_id=1234567&site_id=654321";
        TrackingLinkRecord expected = new TrackingLinkRecord();
        expected.setRedirect(true);
        expected.setMatS2SUrl("https://1234567.api-01.com/serve?action=click&publisher_id=1234567&site_id=654321&response_format=json&device_ip=__IP__&user_agent=__UA__&ios_ifa=__IDFA__&callback={callback_param}&clicktime=__TS__&osversion=__OS__");
        expected.setThirdPartyS2SUrl("https://lnk0.com/El8Qh8?t=1&idfa=__IDFA__&ip=__IP__&chn=toutiao&useragent=__UA__&callback={callback_param}&action=none&clicktime=__TS__&osversion=__OS__");

        Map<String, String[]> params = StringUtil.url2Map(new URL(url));
        TalkingDataTrackingLink tdLink = new TalkingDataTrackingLink();
        TrackingLinkRecord record = tdLink.processTracking(params);
        assertEquals(expected, record);
    }


    @Test
    public void contructThirdPartyS2SLink2() throws Exception {
        String url = "http://api.qiku.mobi/td?tp_prot=https&tp_host=lnk0.com&tp_path=El8Qh8&chn=toutiao&idfa=__IDFA__&osversion=__OS__&ip=__IP__&clicktime=__TS__&useragent=__UA__&callback={callback_param}&action=normal&publisher_id=1234567&site_id=654321";
        TrackingLinkRecord expected = new TrackingLinkRecord();
        expected.setRedirect(false);
        expected.setMatS2SUrl("https://1234567.api-01.com/serve?action=click&publisher_id=1234567&site_id=654321&response_format=json&device_ip=__IP__&user_agent=__UA__&ios_ifa=__IDFA__&callback={callback_param}&clicktime=__TS__&osversion=__OS__");
        expected.setThirdPartyS2SUrl("https://lnk0.com/El8Qh8?t=1&idfa=__IDFA__&ip=__IP__&chn=toutiao&useragent=__UA__&callback={callback_param}&action=normal&clicktime=__TS__&osversion=__OS__");

        Map<String, String[]> params = StringUtil.url2Map(new URL(url));
        TalkingDataTrackingLink tdLink = new TalkingDataTrackingLink();
        TrackingLinkRecord record = tdLink.processTracking(params);
        assertEquals(expected, record);
    }

    @Test
    public void contructThirdPartyS2SLink3() throws Exception {
        String url = "http://api.qiku.mobi/td?tp_path=El8Qh8&chn=toutiao&idfa=__IDFA__&osversion=__OS__&ip=__IP__&clicktime=__TS__&useragent=__UA__&callback={callback_param}&action=normal&publisher_id=1234567&site_id=654321";
        TrackingLinkRecord expected = new TrackingLinkRecord();
        expected.setRedirect(false);
        expected.setMatS2SUrl("https://1234567.api-01.com/serve?action=click&publisher_id=1234567&site_id=654321&response_format=json&device_ip=__IP__&user_agent=__UA__&ios_ifa=__IDFA__&callback={callback_param}&clicktime=__TS__&osversion=__OS__");
        expected.setThirdPartyS2SUrl("https://lnk0.com/El8Qh8?t=1&idfa=__IDFA__&ip=__IP__&chn=toutiao&useragent=__UA__&callback={callback_param}&action=normal&clicktime=__TS__&osversion=__OS__");

        Map<String, String[]> params = StringUtil.url2Map(new URL(url));
        TalkingDataTrackingLink tdLink = new TalkingDataTrackingLink();
        TrackingLinkRecord record = tdLink.processTracking(params);
        assertEquals(expected, record);
    }
}