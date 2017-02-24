package jp.coolfactory.data.module;

/**
 * Created by wangqi on 22/2/2017.
 */
public class AdApp {

    private String appKey;

    private String appName;

    private String srcTimeZone;
    private String dstTimeZone;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getSrcTimeZone() {
        return srcTimeZone;
    }

    public void setSrcTimeZone(String srcTimeZone) {
        this.srcTimeZone = srcTimeZone;
    }

    public String getDstTimeZone() {
        return dstTimeZone;
    }

    public void setDstTimeZone(String dstTimeZone) {
        this.dstTimeZone = dstTimeZone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdApp adApp = (AdApp) o;

        if (getAppKey() != null ? !getAppKey().equals(adApp.getAppKey()) : adApp.getAppKey() != null) return false;
        if (getAppName() != null ? !getAppName().equals(adApp.getAppName()) : adApp.getAppName() != null) return false;
        if (getSrcTimeZone() != null ? !getSrcTimeZone().equals(adApp.getSrcTimeZone()) : adApp.getSrcTimeZone() != null)
            return false;
        return getDstTimeZone() != null ? getDstTimeZone().equals(adApp.getDstTimeZone()) : adApp.getDstTimeZone() == null;
    }

    @Override
    public int hashCode() {
        int result = getAppKey() != null ? getAppKey().hashCode() : 0;
        result = 31 * result + (getAppName() != null ? getAppName().hashCode() : 0);
        result = 31 * result + (getSrcTimeZone() != null ? getSrcTimeZone().hashCode() : 0);
        result = 31 * result + (getDstTimeZone() != null ? getDstTimeZone().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AdApp{" +
                "appKey='" + appKey + '\'' +
                ", appName='" + appName + '\'' +
                ", srcTimeZone='" + srcTimeZone + '\'' +
                ", dstTimeZone='" + dstTimeZone + '\'' +
                '}';
    }
}
