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

    /**
     * They are used to get publisher info
     */
    String MAT_PUB_ID = "publisher_id";
    String MAT_SITE_ID = "site_id";

    /**
     * They are used to contruct third-party URL
     */
    String TP_PROT = "tp_prot";
    String TP_HOST = "tp_host";
    String TP_PATH = "tp_path";

}
