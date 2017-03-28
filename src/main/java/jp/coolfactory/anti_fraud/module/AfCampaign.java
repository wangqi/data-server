package jp.coolfactory.anti_fraud.module;

import jp.coolfactory.anti_fraud.db.CheckType;
import jp.coolfactory.anti_fraud.db.Checker;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * For a given site, there may be more than one campaigns. Each campaign is mapped to different countries, devices, languages,
 * or partners in third parties.
 * Created by wangqi on 28/11/2016.
 */
public class AfCampaign implements Checker {

    private final static Logger LOGGER = Logger.getLogger(AfCampaign.class.getName());

    private int id;
    private String name;
    private Date created;
    private int siteId;
    private String siteName;
    private boolean isDefault = false;
    private String timezone;
    private Set<String> includeCountries = new CopyOnWriteArraySet<String>();
    private Set<String> excludeCountries = new CopyOnWriteArraySet<String>();
    private Set<String> includeDevices = new CopyOnWriteArraySet<String>();
    private Set<String> excludeDevices = new CopyOnWriteArraySet<String>();
    private Set<String> includeLangs = new CopyOnWriteArraySet<String>();
    private Set<String> excludeLangs = new CopyOnWriteArraySet<String>();
    private Set<String> includeOS = new CopyOnWriteArraySet<String>();
    private Set<String> excludeOS = new CopyOnWriteArraySet<String>();
    private Set<String> includeCarrier = new CopyOnWriteArraySet<String>();
    private Set<String> excludeCarrier = new CopyOnWriteArraySet<String>();

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

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int site_id) {
        this.siteId = site_id;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public Set<String> getIncludeCountries() {
        return includeCountries;
    }

    public void setIncludeCountries(Set<String> includeCountries) {
        this.includeCountries = includeCountries;
    }

    public Set<String> getExcludeCountries() {
        return excludeCountries;
    }

    public void setExcludeCountries(Set<String> excludeCountries) {
        this.excludeCountries = excludeCountries;
    }

    public Set<String> getIncludeDevices() {
        return includeDevices;
    }

    public void setIncludeDevices(Set<String> includeDevices) {
        this.includeDevices = includeDevices;
    }

    public Set<String> getExcludeDevices() {
        return excludeDevices;
    }

    public void setExcludeDevices(Set<String> excludeDevices) {
        this.excludeDevices = excludeDevices;
    }

    public Set<String> getIncludeLangs() {
        return includeLangs;
    }

    public void setIncludeLangs(Set<String> includeLangs) {
        this.includeLangs = includeLangs;
    }

    public Set<String> getExcludeLangs() {
        return excludeLangs;
    }

    public void setExcludeLangs(Set<String> excludeLangs) {
        this.excludeLangs = excludeLangs;
    }

    public Set<String> getIncludeOS() {
        return includeOS;
    }

    public void setIncludeOS(Set<String> includeOS) {
        this.includeOS = includeOS;
    }

    public Set<String> getExcludeOS() {
        return excludeOS;
    }

    public Set<String> getIncludeCarrier() {
        return includeCarrier;
    }

    public void setIncludeCarrier(Set<String> includeCarrier) {
        this.includeCarrier = includeCarrier;
    }

    public Set<String> getExcludeCarrier() {
        return excludeCarrier;
    }

    public void setExcludeCarrier(Set<String> excludeCarrier) {
        this.excludeCarrier = excludeCarrier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public void setExcludeOS(Set<String> excludeOS) {
        this.excludeOS = excludeOS;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    /**
     * Check if the given value is allowed. It will return a Status object.
     * Note, the include policy's priority is higher than exclude policy.
     * It uses the 'quick' matching, which compare the paramValue and patterns directly.
     * <p>
     * If the value is in 'INCLUDE' policy, it will be passed
     * If the value is in 'EXCLUDE' policy, it will be denied
     * If it's not found in either INCLUDE or EXCLUDE, and INCLUDE is not empty, deny it.
     *
     * @param type TYPE object that indicate the detail type.
     * @return
     */
    @Override
    public Status check(CheckType type, String paramValue) {
        return check(type, paramValue, true);
    }

    /**
     * If the 'quick' is false, it will use prefix-matching to compare the paramValue and pattern in database.
     *
     * @param type
     * @param paramValue If the paramValue is null or empty string, it will pass the test.
     * @param quick
     * @return
     */
    @Override
    public Status check(CheckType type, String paramValue, boolean quick) {
        Status status = Status.OK;
        /**
         * If the given paramValue is empty, it will pass the test.
         */
        if (paramValue == null || paramValue == "") {
            return status;
        }
        String value = paramValue.toLowerCase();
        Set<String> includes = this.getIncludePolicy(type);
        Set<String> excludes = this.getExcludePolicy(type);
        if (quick) {
            if (includes.contains(value)) {
                status = Status.OK;
                if (LOGGER.isLoggable(Level.FINE))
                    LOGGER.fine("Value " + value + " is in include policy for type: " + type);
            } else if (excludes.contains(value)) {
                status = this.getDeniedStatus(type);
                if (LOGGER.isLoggable(Level.FINE))
                    LOGGER.fine("Value " + value + " is in exclude policy for type: " + type);
            } else {
                if (includes.size() > 0) {
                    if (LOGGER.isLoggable(Level.FINE))
                        LOGGER.fine("Value " + value + " is not in either include or exclude policy for type: " + type);
                    status = this.getDeniedStatus(type);
                }
            }
        } else {
            //Slow methods use the prefix matching
            boolean found = false;
            for (String pattern : includes) {
                if (value.startsWith(pattern)) {
                    found = true;
                    break;
                }
            }
            if (found) {
                status = Status.OK;
                if (LOGGER.isLoggable(Level.FINE))
                    LOGGER.fine("Value " + value + " is in prefix matching include policy for type: " + type);
            } else {
                for (String pattern : excludes) {
                    if (value.startsWith(pattern)) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    status = this.getDeniedStatus(type);
                    if (LOGGER.isLoggable(Level.FINE))
                        LOGGER.fine("Value " + value + " is in prefix matching exclude policy for type: " + type);
                }
            }
            if (!found && includes.size() > 0) {
                if (LOGGER.isLoggable(Level.FINE))
                    LOGGER.fine("Value " + value + " is not in either prefix matching include or prefix matching " +
                            "exclude policy for type: " + type);
                status = this.getDeniedStatus(type);
            }
        }

        return status;
    }

    @Override
    public String toString() {
        return "AfCampaign{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", created=" + created +
                ", siteId=" + siteId +
                ", siteName='" + siteName + '\'' +
                ", isDefault=" + isDefault +
                ", timezone='" + timezone + '\'' +
                ", includeCountries=" + includeCountries +
                ", excludeCountries=" + excludeCountries +
                ", includeDevices=" + includeDevices +
                ", excludeDevices=" + excludeDevices +
                ", includeLangs=" + includeLangs +
                ", excludeLangs=" + excludeLangs +
                ", includeOS=" + includeOS +
                ", excludeOS=" + excludeOS +
                ", includeCarrier=" + includeCarrier +
                ", excludeCarrier=" + excludeCarrier +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AfCampaign that = (AfCampaign) o;

        if (getId() != that.getId()) return false;
        if (getSiteId() != that.getSiteId()) return false;
        if (isDefault() != that.isDefault()) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getCreated() != null ? !getCreated().equals(that.getCreated()) : that.getCreated() != null) return false;
        if (getSiteName() != null ? !getSiteName().equals(that.getSiteName()) : that.getSiteName() != null)
            return false;
        if (getTimezone() != null ? !getTimezone().equals(that.getTimezone()) : that.getTimezone() != null)
            return false;
        if (getIncludeCountries() != null ? !getIncludeCountries().equals(that.getIncludeCountries()) : that.getIncludeCountries() != null)
            return false;
        if (getExcludeCountries() != null ? !getExcludeCountries().equals(that.getExcludeCountries()) : that.getExcludeCountries() != null)
            return false;
        if (getIncludeDevices() != null ? !getIncludeDevices().equals(that.getIncludeDevices()) : that.getIncludeDevices() != null)
            return false;
        if (getExcludeDevices() != null ? !getExcludeDevices().equals(that.getExcludeDevices()) : that.getExcludeDevices() != null)
            return false;
        if (getIncludeLangs() != null ? !getIncludeLangs().equals(that.getIncludeLangs()) : that.getIncludeLangs() != null)
            return false;
        if (getExcludeLangs() != null ? !getExcludeLangs().equals(that.getExcludeLangs()) : that.getExcludeLangs() != null)
            return false;
        if (getIncludeOS() != null ? !getIncludeOS().equals(that.getIncludeOS()) : that.getIncludeOS() != null)
            return false;
        if (getExcludeOS() != null ? !getExcludeOS().equals(that.getExcludeOS()) : that.getExcludeOS() != null)
            return false;
        if (getIncludeCarrier() != null ? !getIncludeCarrier().equals(that.getIncludeCarrier()) : that.getIncludeCarrier() != null)
            return false;
        return getExcludeCarrier() != null ? getExcludeCarrier().equals(that.getExcludeCarrier()) : that.getExcludeCarrier() == null;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getCreated() != null ? getCreated().hashCode() : 0);
        result = 31 * result + getSiteId();
        result = 31 * result + (getSiteName() != null ? getSiteName().hashCode() : 0);
        result = 31 * result + (isDefault() ? 1 : 0);
        result = 31 * result + (getTimezone() != null ? getTimezone().hashCode() : 0);
        result = 31 * result + (getIncludeCountries() != null ? getIncludeCountries().hashCode() : 0);
        result = 31 * result + (getExcludeCountries() != null ? getExcludeCountries().hashCode() : 0);
        result = 31 * result + (getIncludeDevices() != null ? getIncludeDevices().hashCode() : 0);
        result = 31 * result + (getExcludeDevices() != null ? getExcludeDevices().hashCode() : 0);
        result = 31 * result + (getIncludeLangs() != null ? getIncludeLangs().hashCode() : 0);
        result = 31 * result + (getExcludeLangs() != null ? getExcludeLangs().hashCode() : 0);
        result = 31 * result + (getIncludeOS() != null ? getIncludeOS().hashCode() : 0);
        result = 31 * result + (getExcludeOS() != null ? getExcludeOS().hashCode() : 0);
        result = 31 * result + (getIncludeCarrier() != null ? getIncludeCarrier().hashCode() : 0);
        result = 31 * result + (getExcludeCarrier() != null ? getExcludeCarrier().hashCode() : 0);
        return result;
    }

    /**
     * Parse the '|' separated string as an country code list. All countries will be added into internal Set.
     *
     * @param isInclude Is the valueList for INCLUDE or EXCLUDE. True is for include.
     * @param valueList The '|' separated country code lists.
     * @return The internal Set, which is used for the test purpose only. Do not change its value.
     */
    public final Set<String> parseStringValue(boolean isInclude, String valueList, CheckType type) {
        if (valueList == null || valueList == "") {
            return Collections.emptySet();
        }
        String[] values = valueList.toLowerCase().split("\\|");
        switch (type) {
            case COUNTRY:
                if (isInclude) {
                    this.includeCountries.addAll(Arrays.asList(values));
                    return Collections.unmodifiableSet(this.includeCountries);
                } else {
                    this.excludeCountries.addAll(Arrays.asList(values));
                    return Collections.unmodifiableSet(this.excludeCountries);
                }
            case DEVICE:
                if (isInclude) {
                    this.includeDevices.addAll(Arrays.asList(values));
                    return Collections.unmodifiableSet(this.includeDevices);
                } else {
                    this.excludeDevices.addAll(Arrays.asList(values));
                    return Collections.unmodifiableSet(this.excludeDevices);
                }
            case LANG:
                if (isInclude) {
                    this.includeLangs.addAll(Arrays.asList(values));
                    return Collections.unmodifiableSet(this.includeLangs);
                } else {
                    this.excludeLangs.addAll(Arrays.asList(values));
                    return Collections.unmodifiableSet(this.excludeLangs);
                }
            case OS:
                if (isInclude) {
                    this.includeOS.addAll(Arrays.asList(values));
                    return Collections.unmodifiableSet(this.includeOS);
                } else {
                    this.excludeOS.addAll(Arrays.asList(values));
                    return Collections.unmodifiableSet(this.excludeOS);
                }
            case CARRIER:
                if (isInclude) {
                    this.includeCarrier.addAll(Arrays.asList(values));
                    return Collections.unmodifiableSet(this.includeCarrier);
                } else {
                    this.excludeCarrier.addAll(Arrays.asList(values));
                    return Collections.unmodifiableSet(this.excludeCarrier);
                }
            default:
                LOGGER.warning("The type: " + type + " for parseStringValue method is illegal.");
                break;
        }
        return Collections.emptySet();
    }


    /// -----------------------------------------


    private Set<String> getIncludePolicy(CheckType type) {
        switch (type) {
            case COUNTRY:
                return this.includeCountries;
            case DEVICE:
                return this.includeDevices;
            case LANG:
                return this.includeLangs;
            case OS:
                return this.includeOS;
            case CARRIER:
                return this.includeCarrier;
            default:
                return null;
        }
    }

    private Set<String> getExcludePolicy(CheckType type) {
        switch (type) {
            case COUNTRY:
                return this.excludeCountries;
            case DEVICE:
                return this.excludeDevices;
            case LANG:
                return this.excludeLangs;
            case OS:
                return this.excludeOS;
            case CARRIER:
                return this.excludeCarrier;
            default:
                return null;
        }
    }

    private Status getDeniedStatus(CheckType type) {
        switch (type) {
            case COUNTRY:
                return Status.FORBIDDEN_COUNTRY;
            case DEVICE:
                return Status.FORBIDDEN_DEVICE;
            case LANG:
                return Status.FORBIDDEN_LANG;
            case OS:
                return Status.FORBIDDEN_OS;
            case CARRIER:
                return Status.FORBIDDEN_CARRIER;
            default:
                return null;
        }
    }
}
