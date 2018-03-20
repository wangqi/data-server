package jp.coolfactory.data.s2s;

import java.util.HashSet;
import java.util.Set;

public interface TuneKeyParamNames {

    /**
     * They are used to get user info
     */
    String DEVICE_IP = "device_ip";
    String IOS_IFA = "ios_ifa";
    String GOOGLE_AID = "google_aid";
    String USER_AGENT = "user_agent";
    String SUB_CAMPAIGN = "sub_campaign";
    String CLICK_TIME = "clicktime";

    /**
     * They are used to get publisher info
     */
    String MAT_PUB_ID = "publisher_id";
    String MAT_SITE_ID = "site_id";
    String MAT_SUG_PUB = "sub_publisher";

    /**
     * They are used to contruct third-party URL
     */
    String TP_PROT = "tp_prot";
    String TP_HOST = "tp_host";
    String TP_PATH = "tp_path";
    /**
     * Note MAT also has a parameter called 'action'. It's either 'click' or 'impression'.
     * The TalkingData's 'action' is used to indicate if it is a S2S request. If it's 'none', then no redirect will be replied.
     */
    String TP_ACTION = "action";
    String TP_CHANNEL = "chn";

}
