package jp.coolfactory.anti_fraud.module;

import jp.coolfactory.anti_fraud.db.*;
import jp.coolfactory.anti_fraud.frequency.*;

import jp.coolfactory.data.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * It's used to check if an install is from real user
 * <p>
 * Created by wangqi on 24/11/2016.
 */
public class FraudCommonDetection implements FraudDetection {

    private final static Logger LOGGER = LoggerFactory.getLogger(FraudCommonDetection.class.getName());

    private static FraudDetection ourInstance = new FraudCommonDetection();

    public static FraudDetection getInstance() {
        return ourInstance;
    }

    /**
     * It's used to filter IP by frequency
     */
    public final FrequencyManager InternalFreqManager = new InternalFreqManager();
    public final CacheRecordMaker cacheRecordMaker = new CacheRecordMaker() { };


    FraudCommonDetection() {
    }

    /**
     *
         site_id={site_id}
         site_name={site_name}
         country_code={country_code}
         device_brand={device_brand}
         device_carrier={device_carrier}
         device_model={device_model}
         language={language}
         device_ip={device_ip}
         status={conversion_status}
         click_date={click_date}
         install_date={conversion_datetime}
         ios_ifa={ios_ifa}
         ios_ifv={ios_ifv}
         os_version={os_version}
         publisher_id={publisher_id}
         publisher_name={publisher_id}
     *
     * @param params
     * @return
     */
    @Override
    public final Status evaluateInstall(HashMap params) {
        //Get required parameters
        String click_date = (String)params.get("click_date");
        String install_date = (String)params.get("install_date");
        String site_id = (String)params.get("site_id");
        String site_name = (String)params.get("site_name");
        String country_code = (String)params.get("country_code");
        String device_brand = (String)params.get("device_brand");
        String device_carrier = (String)params.get("device_carrier");
        String device_model = (String)params.get("device_model");
        String language = (String)params.get("language");
        String device_ip = (String)params.get("device_ip");
        String mat_status = (String)params.get("status");
        String ios_ifa = (String)params.get("ios_ifa");
        String ios_ifv = (String)params.get("ios_ifv");
        String os_version = (String)params.get("os_version");
        String publisher_id = (String)params.get("publisher_id");
        String publisher_name = (String)params.get("publisher_name");
        //Get the campaign id from {my_campaign} macro in MAT.
        String campaign_id = (String)params.get("camp_id");
        String jailbroke = (String)params.get("jailbroke");
        //Get original postback url
        String postback = (String)params.get("postback");

        Status status = Status.OK;
        if ( "approved".equals(mat_status) ) {
            AfSite site = AntiFraudController.getInstance().getSite(site_id);

            if ( site != null ) {
                if ( LOGGER.isDebugEnabled() )
                    LOGGER.debug(" We find the site config. " + site);
                AfCampaign campaign = null;
                if ( campaign_id == null || campaign_id == "" ) {
                    campaign = site.getDefaultCampaign();
                    LOGGER.debug("Check country/carrier/device/lang/os with default campaign");
                } else {
                    campaign = site.getCampaign(campaign_id);
                    LOGGER.debug("Check country/carrier/device/lang/os/ip with campaign: " + campaign.getName());
                }
                if ( campaign != null ) {
                    /**
                     * Process the timezone first
                     */
                    String timezone = campaign.getTimezone();
                    if ( timezone == null || "".equals(timezone) ) {
                        AfAccount account = AntiFraudController.getInstance().getAccount(site.getAccountName());
                        timezone = account.getTimezone();
                    }
                    if ( timezone != null ) {
                        params.put("timezone", timezone);
                    } else {
                        params.put("timezone", "UTC");
                    }
                    String clickZonedDate = DateUtil.turnDateTimeToTimezone(click_date, timezone);
                    if ( clickZonedDate != null ) {
                        params.put("click_date_zone", clickZonedDate );
                    }
                    String installZonedDate = DateUtil.turnDateTimeToTimezone(install_date, timezone);
                    if ( installZonedDate != null ) {
                        params.put("install_date_zone", installZonedDate );
                    }


                    params.put("camp_id", String.valueOf(campaign.getId()));
                    /**
                     * Check if the jailbroke is sent and if it is true
                     */
                    if ( jailbroke != null ) {
                        LOGGER.debug("Jailbroke tag is set to: " + jailbroke);
                        int is_jailbroke = 0;
                        try {
                            is_jailbroke = Integer.parseInt(jailbroke);
                        } catch (NumberFormatException e) {
                        }
                        if ( is_jailbroke == 1 ) {
                            status = Status.FORBIDDEN_JAILBROKE;
                        }
                    }
                    if ( status == Status.OK ) {
                        status = campaign.check(CheckType.COUNTRY, country_code);
                        if ( status == Status.OK ) {
                            status = campaign.check(CheckType.CARRIER, device_carrier, false);
                            if ( status == Status.OK ) {
                                status = campaign.check(CheckType.DEVICE, device_model, false);
                                if ( status == Status.OK ) {
                                    status = campaign.check(CheckType.LANG, language, false);
                                    if ( status == Status.OK ) {
                                        status = campaign.check(CheckType.OS, os_version, false);
                                        if ( status == Status.OK ) {
                                            status = AntiFraudController.getInstance().getIPFilter().check(CheckType.IP, device_ip, false);
                                            if ( status == Status.OK ) {
                                                CacheRecord cacheRecord = cacheRecordMaker.makeKey(params);
                                                RecordStatus recordStatus = InternalFreqManager.check(cacheRecord);
                                                if ( recordStatus == RecordStatus.OK )  {
                                                    status = Status.OK;
                                                } else {
                                                    status = Status.FORBIDDEN_IP_FREQ;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    LOGGER.info("After checking, the status is: " + status);
                }
            }
        } else {
            status = Status.ALREADY_DENIED;
        }

        LOGGER.debug(click_date+"\t"+install_date+"\t"+site_id+"\t"+site_name+"\t"+country_code+"\t"+device_brand+
                "\t"+device_carrier+"\t"+device_model+"\t"+language+"\t"+device_ip+"\t"+status+"\t"+ios_ifa+"\t"+
                ios_ifv+"\t"+os_version+"\t"+publisher_id+"\t"+publisher_name+"\t" + status);

        return status;
    }

}
