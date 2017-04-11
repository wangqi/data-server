package jp.coolfactory.anti_fraud.module;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * Created by wangqi on 28/11/2016.
 */
public class AfSite {

    private final static Logger LOGGER = Logger.getLogger(AfCampaign.class.getName());

    private int id;
    private Date created;
    private String name;
    private String externalId;
    private int accountId;
    private String accountName;
    /**
     * The map that stores the AfCampaign by campaign's ID
     */
    private Map<Integer, AfCampaign> _campaigns = new ConcurrentHashMap<Integer, AfCampaign>();
    /**
     * The campaign with is_default is true will be treated as default campaign
     * Note there should be one and only one default campaign for each Site.
     */
    private AfCampaign defaultCampaign = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * The External ID is the ID from third party system, like 'site_id' in TUNE's AA
     * @return
     */
    public String getExternalId() {
        return externalId;
    }

    /**
     * The External ID is the ID from third party system, like 'site_id' in TUNE's AA
     * @return
     */
    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    /**
     * Get the accountId which this site belongs to
     * @return
     */
    public int getAccountId() {
        return accountId;
    }

    /**
     * Set the accountId which this site belongs to
     * @return
     */
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * Add a campaign to this Site
     * @param campaign
     */
    public void addCampaign(AfCampaign campaign) {
        this._campaigns.put(campaign.getId(), campaign);
        if ( campaign.isDefault() ) {
            if (defaultCampaign == null) {
                this.defaultCampaign = campaign;
            } else {
                LOGGER.severe(" Site : " + this.getName() + " has more than one default campaign. Default one: " +
                        this.defaultCampaign.getName() + "; New one: " + campaign.getName());
            }
        }
    }

    /**
     * Get the given campaign by campaign id
     * @param campaignId
     * @return
     */
    public AfCampaign getCampaign(String campaignId) {
        if ( campaignId == null ) {
            LOGGER.warning("Given campaignId is null");
            return null;
        }
        try {
//            Integer id = new Integer(campaignId);
            return this._campaigns.get(campaignId);
        } catch (NumberFormatException e) {
            LOGGER.warning("campaignId : " + campaignId + " is not a number.");
        }
        return null;
    }

    /**
     * Get the default campaign for a given site.
     * @return
     */
    public AfCampaign getDefaultCampaign() {
        return this.defaultCampaign;
    }

    @Override
    public String toString() {
        return "AfSite{" +
                "id=" + id +
                ", created=" + created +
                ", name='" + name + '\'' +
                ", externalId='" + externalId + '\'' +
                ", accountId=" + accountId +
                ", accountName='" + accountName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AfSite afSite = (AfSite) o;

        if (getId() != afSite.getId()) return false;
        if (getAccountId() != afSite.getAccountId()) return false;
        if (getCreated() != null ? !getCreated().equals(afSite.getCreated()) : afSite.getCreated() != null)
            return false;
        if (getName() != null ? !getName().equals(afSite.getName()) : afSite.getName() != null) return false;
        if (getExternalId() != null ? !getExternalId().equals(afSite.getExternalId()) : afSite.getExternalId() != null)
            return false;
        return getAccountName() != null ? getAccountName().equals(afSite.getAccountName()) : afSite.getAccountName() == null;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getCreated() != null ? getCreated().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getExternalId() != null ? getExternalId().hashCode() : 0);
        result = 31 * result + getAccountId();
        result = 31 * result + (getAccountName() != null ? getAccountName().hashCode() : 0);
        return result;
    }
}
