package jp.coolfactory.data.common;

import jp.coolfactory.data.module.AdRequest;
import jp.coolfactory.data.util.DateUtil;

/**
 * Created by wangqi on 12/4/2017.
 */
public class AdRequestCreator {

    /**
     * 2017-04-11 17:32:31	"qiku"	"install"	"85368"	"mat"	"uHslrAsmPDVaubal8KpOAvQd3DVzB1ZjsyfzFsTF6MA="	"5.1"	""	"android phone"
     * "sharp"	"ntt docomo"	"sh-02h"	"ja"	"369dfbd9-7a6a-4766-aeb3-a9749ee94e3c"	"mozilla/5.0 (linux; android 5.1.1; sh-02h build/s2030; wv) applewebkit/537.36 (khtml, like gecko) version/4.0 chrome/45.0.2454.95 mobile safari/537.36"
     * ""	""	""	"null	"null"	"49.96.23.163"	"2017-04-11T08:31:04+08:00[Asia/Shanghai]	""	"85368"	"戦艦帝国-android-jp" 	""	""
     * ""	"null"	""	"tokyo"	"jp"	"usd"	"null"	"null	""	"null"	0.0	"mat_click_id=f07c68a547572707d987c6ddfcb551c1-20160310-163748&utm_campaign=戦艦帝国-测试版-android-jp (default)&utm_content=internal&utm_medium=internal&utm_source=adcrops&utm_term=internal"
     * ""	0.0	0.0	"approved"	"0"	"null"	""	""	"83cdb92b-9abb-4dca-b160-477b6863db0f"	""	""	"null"	""	""	""	""	"null"	"null"	""	""	"null"	""	""	""	""	"null"	"null"	"android"	"3.11.1"	""	false	"null"	""	""	""	"null"	"null"	"null"	0.0	0	0	"null"	"null"	"null"	0	"null"	0.0"null"	"null"	"OK	"null"
     * @return
     * @throws Exception
     */
    public static final AdRequest createdRequest() throws Exception {
        AdRequest req = new AdRequest();
        req.setAccount_key("qiku");
        req.setAction("install");
        req.setAppKey("85368");
        req.setSource("mat");
        req.setStat_id("uHslrAsmPDVaubal8KpOAvQd3DVzB1ZjsyfzFsTF6MA=");
        req.setOs_version("5.1");
        req.setDevice_id("");
        req.setDevice_type("android phone");
        /** Line 2 */
        req.setDevice_brand("sharp");
        req.setDevice_carrier("ntt docomo");
        req.setDevice_model("sh-02h");
        req.setLang("ja");
        req.setPlat_id("369dfbd9-7a6a-4766-aeb3-a9749ee94e3c");
        req.setUser_agent("mozilla/5.0 (linux; android 5.1.1; sh-02h build/s2030; wv) applewebkit/537.36 (khtml, like gecko) version/4.0 chrome/45.0.2454.95 mobile safari/537.36");
        /** Line 3 */
        req.setPublisher_id("");
        req.setPublisher_name("");
        req.setClick_ip("");
        req.setClick_time(null);
        req.setBundle_id("");
        req.setInstall_ip("");
        req.setInstall_time(DateUtil.convertIOS2Date("2017-04-11 08:31:04", "Asia/Tokyo"));
        req.setAgency_name("");
        req.setSite_id("85368");
        req.setAf_site_id(req.getSite_id());
        req.setSite_name("戦艦帝国-android-jp");
        req.setMatch_type("");
        req.setCampaign_id("");
        /** Line 4 */
        req.setCampaign_name("");
        req.setAd_url("");
        req.setAd_name("");
        req.setRegion_name("tokyo");
        req.setCountry_code("jp");
        req.setCurrency_code("usd");
        req.setExisting_user(null);
        req.setImp_time(null);
        req.setStat_click_id(null);
        req.setStat_impression_id(null);
        req.setPayout(0.0);
        req.setReferral_source("mat_click_id=f07c68a547572707d987c6ddfcb551c1-20160310-163748&utm_campaign=戦艦帝国-测试版-android-jp (default)&utm_content=internal&utm_medium=internal&utm_source=adcrops&utm_term=internal");
        /** Line 5 */
        req.setReferral_url("");
        req.setRevenue(0.0);
        req.setRevenue_usd(0.0);
        req.setStatus("approved");
        req.setStatus_code("0");
        req.setTracking_id(null);
        req.setIos_ifa("");
        req.setIos_ifv("");
        req.setGoogle_aid("83cdb92b-9abb-4dca-b160-477b6863db0f");

        return req;
    }

}
