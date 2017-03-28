package jp.coolfactory.anti_fraud.module;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wangqi on 28/11/2016.
 */
public class AfAccount {

    private int id;
    private Date created;
    private String name;
    private String Pass;
    private String email;
    private String timezone;

    /**
     * The map that stores the AfAccount by account_name (not account_id)
     */
    private Map<String, AfSite> _sites = new ConcurrentHashMap<String, AfSite>();

    /**
     * Get the account id
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Set the account id
      * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the created date
     * @return
     */
    public Date getCreated() {
        return created;
    }

    /**
     * Set the created date
     * @param created
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * Get the name
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the password
     * @return password in SHA1
     */
    public String getPass() {
        return Pass;
    }

    /**
     * Set the password
     * @param pass Password encrypted in SHA1
     */
    public void setPass(String pass) {
        Pass = pass;
    }

    /**
     * Get the email
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the email.
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get the account default timezone
     * @return
     */
    public String getTimezone() {
        return timezone;
    }

    /**
     * Set the account default timezone
     * @param timezone
     */
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    /**
     * Add a Site to this account object
     * @param site
     */
    public void addSite(AfSite site) {
        this._sites.put(site.getExternalId(), site);
    }

    /**
     * Get the Site object by its external Id
     * @param externalId
     * @return
     */
    public AfSite getSiteByExternalId(String externalId) {
        return this._sites.get(externalId);
    }

    /**
     * Get all Site objects of this account.
     * @return
     */
    public Collection<AfSite> getAllSites() {
        return Collections.unmodifiableCollection(this._sites.values());
    }

    @Override
    public String toString() {
        return "AfAccount{" +
                "id=" + id +
                ", created=" + created +
                ", name='" + name + '\'' +
                ", Pass='" + Pass + '\'' +
                ", email='" + email + '\'' +
                ", timezone='" + timezone + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AfAccount afAccount = (AfAccount) o;

        if (getId() != afAccount.getId()) return false;
        if (getCreated() != null ? !getCreated().equals(afAccount.getCreated()) : afAccount.getCreated() != null)
            return false;
        if (getName() != null ? !getName().equals(afAccount.getName()) : afAccount.getName() != null) return false;
        if (getPass() != null ? !getPass().equals(afAccount.getPass()) : afAccount.getPass() != null) return false;
        if (getEmail() != null ? !getEmail().equals(afAccount.getEmail()) : afAccount.getEmail() != null) return false;
        return getTimezone() != null ? getTimezone().equals(afAccount.getTimezone()) : afAccount.getTimezone() == null;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getCreated() != null ? getCreated().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getPass() != null ? getPass().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getTimezone() != null ? getTimezone().hashCode() : 0);
        return result;
    }
}
