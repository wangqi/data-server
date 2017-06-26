package jp.coolfactory.data.util;

/**
 * Created by wangqi on 22/2/2017.
 */
public class PostbackURLGenerator {

    /**
     * appkey:
     *     digdig2android
     *     digdig2ios
     *     battleship2cn
     *     battleship-android
     *     battleship-ios
     */
    private static final String PROD_TPL_1 = "/ds/pb?action={event_name}&source=mat&app_key=";
    private static final String PROD_TPL_2 = "&site_id={site_id}&region_name={region}&sdk_version={sdk_version}&country_code={country_code}&site_name={site_name}&user_agent={conversion_user_agent}&plat_id={mat_id}&publisher_name={publisher_name}&device_id={device_id}&device_type={device_type}&device_carrier={device_carrier}&device_model={device_model}&device_brand={device_brand}&lang={language}&click_ip={session_device_ip}&click_time={click_datetime}&match_type={match_type}&ad_name={publisher_sub_ad_name}&currency_code={currency_code}&pub_camp_id={publisher_sub_campaign}&pub_camp_name={advertiser_sub_campaign_name}&imp_time={impression_datetime}&payout={payout}&referral_source={conversion_referral}&referral_url={session_referrer}&revenue={revenue}&revenue_usd={revenue_usd}&status={conversion_status}&ios_ifa={ios_ifa}&ios_ifv={ios_ifv}&google_aid={google_aid}&pub_adset={publisher_sub_adgroup_name}&pub_ad={publisher_sub_ad_name}&os_version={os_version}&install_time={conversion_datetime}&publisher_id={publisher_id}&agency_name={agency_id}&campaign_id={campaign_id}&campaign_name={campaign_name}&stat_click_id={click_transaction_id}&pub_keyword={publisher_sub_keyword}&pub_place={publisher_sub_placement_name}&adv_camp_id={publisher_sub_campaign}&adv_camp_name={publisher_sub_campaign_name}&adv_adset={publisher_sub_adgroup}&adv_ad={publisher_sub_ad_name}&adv_keyword={publisher_sub_keyword}&adv_place={publisher_sub_placement}&sdk={sdk}&game_user_id={user_id}&os_jailbroke={os_jailbroke}&pub_pref_id={publisher_ref_id}&pub_sub1={publisher_sub1}&pub_sub2={publisher_sub2}&pub_sub3={publisher_sub3}&postback=";

    private static final String DEFAULT_HOST = "https://antifraud.coolfactory.jp";

    public static void main(String[] args) {
        if (args.length<2) {
            System.out.println("[host] <appkey> <postbackurl>");
            System.exit(-1);
        }
        String appkey = "";
        String postbackUrl = "";
        String host = DEFAULT_HOST;
        if ( args.length == 2 ) {
            appkey = args[0];
            postbackUrl = args[1];
        } else if ( args.length > 1 ) {
            postbackUrl = args[1];
        }
        postbackUrl = postbackUrl.replaceAll("&", "%26");
        System.out.println("Generate Postback:");
        System.out.println(host+PROD_TPL_1 + appkey +PROD_TPL_2+postbackUrl);
    }

}
