package jp.coolfactory.data.s2s;

import jp.coolfactory.data.util.StringUtil;
import org.junit.Test;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class TalkingDataTrackingLinkTest {

    @Test
    public void contructThirdPartyS2SLink() throws Exception {
        String url = "http://api.qiku.mobi/td?tp_prot=https&tp_host=lnk0.com&tp_path=El8Qh8&chn=toutiao&idfa=__IDFA__&osversion=__OS__&ip=__IP__&clicktime=__TS__&useragent=__UA__&callback={callback_param}&action=none&publisher_id=1234567&site_id=654321";
        Map<String, String[]> params = StringUtil.url2Map(new URL(url));
        TalkingDataTrackingLink tdLink = new TalkingDataTrackingLink();
        TrackingLinkRecord record = tdLink.processTracking(params);
        System.out.println(record);
    }

}