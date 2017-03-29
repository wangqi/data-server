package jp.coolfactory.anti_fraud.command;

import jp.coolfactory.anti_fraud.controller.AntiFraudController;
import jp.coolfactory.anti_fraud.db.CheckType;
import jp.coolfactory.anti_fraud.module.AfCampaign;
import jp.coolfactory.anti_fraud.module.AfSite;
import jp.coolfactory.anti_fraud.module.Status;
import jp.coolfactory.data.Constants;
import jp.coolfactory.data.common.CommandStatus;
import jp.coolfactory.data.common.Handler;
import jp.coolfactory.data.module.AdRequest;
import jp.coolfactory.data.util.PolicyCheckUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * For 'purchase' event, turn the local revenue into revenue_usd by latest currency ratio.
 *
 * API: http://api.fixer.io/latest?base=USD&symbols=CNY
 *      {"base":"USD","date":"2017-02-28","rates":{"CNY":6.868}}
 *
 * Get All: http://api.fixer.io/latest?base=USD
 *
 * Created by wangqi on 22/2/2017.
 */
public class AfCampaignCommand implements Handler<AdRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AfCampaignCommand.class);

    public AfCampaignCommand() {
    }

    @Override
    public CommandStatus handle(AdRequest adRequest) {
        try {
            if ( adRequest != null ) {
                // Only filter install action
                if ( ! Constants.ACTION_INSTALL.equals(adRequest.getAction()) ) {
                    return CommandStatus.Continue;
                }
                if ( adRequest.getAf_status() == null || adRequest.getAf_status() == Status.OK ) {
                    String site_id = adRequest.getAf_site_id();
                    String campaign_id = adRequest.getAf_camp_id();
                    AfSite site = AntiFraudController.getInstance().getSite(site_id);
                    if ( site != null ) {
                        if (LOGGER.isDebugEnabled())
                            LOGGER.debug(" We find the site config. " + site);
                        AfCampaign campaign = null;
                        if (campaign_id == null || campaign_id == "") {
                            campaign = site.getDefaultCampaign();
                            LOGGER.debug("Check country/carrier/device/lang/os with default campaign");
                        } else {
                            campaign = site.getCampaign(campaign_id);
                            LOGGER.debug("Check country/carrier/device/lang/os/ip with campaign: " + campaign.getName());
                        }
                        if (campaign != null) {
                            Status status = adRequest.getAf_status();
                            if (status == Status.OK) {
                                status = PolicyCheckUtil.check(CheckType.COUNTRY, adRequest.getCountry_code(), campaign.getIncludeCountries(), campaign.getExcludeCountries());
                                if (status == Status.OK) {
                                    status = PolicyCheckUtil.check(CheckType.CARRIER, adRequest.getDevice_carrier(), false,
                                            campaign.getIncludeCarrier(), campaign.getExcludeCarrier());
                                    if (status == Status.OK) {
                                        status = PolicyCheckUtil.check(CheckType.DEVICE, adRequest.getDevice_model(), false,
                                                campaign.getIncludeDevices(), campaign.getExcludeDevices());
                                        if (status == Status.OK) {
                                            status = PolicyCheckUtil.check(CheckType.LANG, adRequest.getLang(), false,
                                                    campaign.getIncludeLangs(), campaign.getExcludeLangs());
                                            if (status == Status.OK) {
                                                status = PolicyCheckUtil.check(CheckType.OS, adRequest.getOs_version(), false,
                                                        campaign.getIncludeOS(), campaign.getExcludeOS());
                                            }
                                        }
                                    }
                                }
                            }
                            adRequest.setAf_status(status);
                            LOGGER.debug("checking status: " + status);
                        }
                    }
                }
            } else {
                LOGGER.warn("adRequest is null.");
            }
        } catch (Exception e) {
            LOGGER.warn("Failed to process stat_id", e);
            return CommandStatus.Fail;
        }
        return CommandStatus.Continue;
    }

}
