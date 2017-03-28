package jp.coolfactory.anti_fraud.module;


import jp.coolfactory.anti_fraud.db.CheckType;
import jp.coolfactory.anti_fraud.db.Checker;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Logger;

/**
 * In database, there are ip_blacklist and ip_whitelist tables. The whilelist will be checked first. If an IP is in
 * whitelist, it will pass the filter. Otherwise, if it's in the blacklist, it will be denied. For IP that's neither in
 * blacklist and whitelist will pass the test too.
 *
 * Created by wangqi on 28/12/2016.
 */
public class AfIPFilter implements Checker {

    private final static Logger LOGGER = Logger.getLogger(AfCampaign.class.getName());

    private Set<String> whitelist = new CopyOnWriteArraySet<String>();
    private Set<String> blacklist = new CopyOnWriteArraySet<String>();

    public AfIPFilter() {
    }

    public void addToBlackList(Collection<String> ipCollection) {
        this.blacklist.addAll(ipCollection);
    }

    public void addToWhiteList(Collection<String> ipCollection) {
        this.whitelist.addAll(ipCollection);
    }

    public Set<String> getWhitelist() {
        return whitelist;
    }

    public void setWhitelist(Set<String> whitelist) {
        this.whitelist = whitelist;
    }

    public Set<String> getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(Set<String> blacklist) {
        this.blacklist = blacklist;
    }

    /**
     * Check if an IP is legitimate.
     * @param ip
     * @return
     */
    public Status check(CheckType type, String paramValue, boolean quick) {
        if ( type == CheckType.IP ) {
            if ( this.whitelist.contains(paramValue) ) {
                return Status.OK;
            } else if ( this.blacklist.contains(paramValue) ) {
                return Status.FORBIDDEN_IP;
            } else {
                return Status.OK;
            }
        } else {
            LOGGER.warning("It didn't support " + type + " checking. ");
            return Status.OK;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AfIPFilter that = (AfIPFilter) o;

        if (getWhitelist() != null ? !getWhitelist().equals(that.getWhitelist()) : that.getWhitelist() != null)
            return false;
        return getBlacklist() != null ? getBlacklist().equals(that.getBlacklist()) : that.getBlacklist() == null;
    }

    @Override
    public int hashCode() {
        int result = getWhitelist() != null ? getWhitelist().hashCode() : 0;
        result = 31 * result + (getBlacklist() != null ? getBlacklist().hashCode() : 0);
        return result;
    }
}
