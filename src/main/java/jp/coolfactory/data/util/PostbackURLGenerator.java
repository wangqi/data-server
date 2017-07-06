package jp.coolfactory.data.util;

import java.util.Scanner;

/**
 * Created by wangqi on 22/2/2017.
 */
public class PostbackURLGenerator {

    private static String[] APP_KEYS = new String[] {
            "ankora_android",
            "ankora_ios",
            "battleship-android",
            "battleship-ios",
            "battleship2cn",
            "digdig2android",
            "digdig2ios",
    };

    /**
     * appkey:
     *     digdig2android
     *     digdig2ios
     *     battleship2cn
     *     battleship-android
     *     battleship-ios
     */
    private static final String PROD_TPL_1 = "/ds/pb?action={event_name}&source=mat&app_key=";
    private static final String PROD_TPL_2 =
            "&site_id={site_id}&region_name={region}&sdk_version={sdk_version}&country_code={country_code}&" +
                    "site_name={site_name}&user_agent={conversion_user_agent}&plat_id={mat_id}&" +
                    "publisher_name={publisher_name}&device_id={device_id}&device_type={device_type}&" +
                    "device_carrier={device_carrier}&device_model={device_model}&device_brand={device_brand}&" +
                    "lang={language}&ip={device_ip}&click_ip={session_device_ip}&click_time={click_datetime}&" +
                    "match_type={match_type}&ad_name={publisher_sub_ad_name}&currency_code={currency_code}&" +
                    "pub_camp_id={publisher_sub_campaign}&pub_camp_name={advertiser_sub_campaign_name}&" +
                    "imp_time={impression_datetime}&payout={payout}&referral_source={conversion_referral}&" +
                    "referral_url={session_referrer}&revenue={revenue}&revenue_usd={revenue_usd}&" +
                    "status={conversion_status}&ios_ifa={ios_ifa}&ios_ifv={ios_ifv}&google_aid={google_aid}&" +
                    "pub_adset={publisher_sub_adgroup_name}&pub_ad={publisher_sub_ad_name}&os_version={os_version}&" +
                    "install_time={conversion_datetime}&publisher_id={publisher_id}&agency_name={agency_id}&" +
                    "campaign_id={campaign_id}&campaign_name={campaign_name}&stat_click_id={click_transaction_id}&" +
                    "pub_keyword={publisher_sub_keyword}&pub_place={publisher_sub_placement_name}&" +
                    "adv_camp_id={publisher_sub_campaign}&adv_camp_name={publisher_sub_campaign_name}&" +
                    "adv_adset={publisher_sub_adgroup}&adv_ad={publisher_sub_ad_name}&adv_keyword={publisher_sub_keyword}&" +
                    "adv_place={publisher_sub_placement}&sdk={sdk}&game_user_id={user_id}&os_jailbroke={os_jailbroke}&" +
                    "pub_pref_id={publisher_ref_id}&pub_sub1={publisher_sub1}&pub_sub2={publisher_sub2}&" +
                    "pub_sub3={publisher_sub3}&postback=";

    private static final String DEFAULT_HOST = "https://antifraud.coolfactory.jp";

    public static void main(String[] args) throws Exception {
        Scanner s = new Scanner(System.in);

        String appkey = "";
        while ( true ) {
            System.out.println("> Please select app_key (1-"+APP_KEYS.length+"): ");
            for (int i=1; i<=APP_KEYS.length; i++ ) {
                System.out.println(i+". " + APP_KEYS[i-1]);
            }
            System.out.print("$ ");
            int selectedIndex = s.nextInt();
            if (selectedIndex<=0 | selectedIndex>APP_KEYS.length) {
                System.out.println("> " + selectedIndex + " is not valid. Please select again.");
            } else {
                appkey = APP_KEYS[selectedIndex-1];
                System.out.println("> You choose [" + appkey + "] ");
                break;
            }
        }

        String postbackUrl = "";
        System.out.println("> Please input the postback URL: ");
        while ( true ) {
            postbackUrl = s.next();
            if ( postbackUrl.length() > 0 ) {
                System.out.println("Can you confirm? " + postbackUrl + " (y/n)");
                String confirmed = s.next();
                if ( "y".equals(confirmed) ) {
                    break;
                } else {
                    System.out.println("> Please input the postbacl URL again." );
                }
            }
        }
        String host = DEFAULT_HOST;
        postbackUrl = postbackUrl.replaceAll("&", "%26");
        System.out.println("Generate Postback:");
        System.out.println(host+PROD_TPL_1 + appkey +PROD_TPL_2+postbackUrl);
    }

}
