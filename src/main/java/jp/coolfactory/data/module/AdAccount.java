package jp.coolfactory.data.module;

import java.util.Date;

/**
 * Created by wangqi on 22/2/2017.
 */
public class AdAccount {

    /**
     * The Key is used as reference to this account.
     */
    private String accountKey;

    /**
     * It's the formal name of this account.
     * For example, TalkingData
     */
    private String accountName;

    /**
     * It's used to access our database's data by API
     */
    private String apiKey;

    private Date created;

    public String getAccountKey() {
        return accountKey;
    }

    public void setAccountKey(String accountKey) {
        this.accountKey = accountKey;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdAccount adAccount = (AdAccount) o;

        if (getAccountKey() != null ? !getAccountKey().equals(adAccount.getAccountKey()) : adAccount.getAccountKey() != null)
            return false;
        if (getAccountName() != null ? !getAccountName().equals(adAccount.getAccountName()) : adAccount.getAccountName() != null)
            return false;
        if (getApiKey() != null ? !getApiKey().equals(adAccount.getApiKey()) : adAccount.getApiKey() != null)
            return false;
        return getCreated() != null ? getCreated().equals(adAccount.getCreated()) : adAccount.getCreated() == null;
    }

    @Override
    public int hashCode() {
        int result = getAccountKey() != null ? getAccountKey().hashCode() : 0;
        result = 31 * result + (getAccountName() != null ? getAccountName().hashCode() : 0);
        result = 31 * result + (getApiKey() != null ? getApiKey().hashCode() : 0);
        result = 31 * result + (getCreated() != null ? getCreated().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AdAccount{" +
                "accountKey='" + accountKey + '\'' +
                ", accountName='" + accountName + '\'' +
                ", apiKey='" + apiKey + '\'' +
                ", created=" + created +
                '}';
    }
}
