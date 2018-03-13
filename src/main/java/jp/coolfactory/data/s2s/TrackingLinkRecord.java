package jp.coolfactory.data.s2s;

/**
 * Wrap the tracking link object
 */
public class TrackingLinkRecord {

    private boolean isRedirect = false;

    private String matS2SUrl = null;

    private String thirdPartyS2SUrl = null;

    public boolean isRedirect() {
        return isRedirect;
    }

    public void setRedirect(boolean redirect) {
        isRedirect = redirect;
    }

    public String getMatS2SUrl() {
        return matS2SUrl;
    }

    public void setMatS2SUrl(String matS2SUrl) {
        this.matS2SUrl = matS2SUrl;
    }

    public String getThirdPartyS2SUrl() {
        return thirdPartyS2SUrl;
    }

    public void setThirdPartyS2SUrl(String thirdPartyS2SUrl) {
        this.thirdPartyS2SUrl = thirdPartyS2SUrl;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(200);
        sb.append("{\nTrackingLinkRecord:");
        sb.append("isRedirect=").append(isRedirect);
        sb.append("\n matS2SUrl=").append('"').append(matS2SUrl).append('"');
        sb.append("\n thirdPartyS2SUrl=").append('"').append(thirdPartyS2SUrl).append("\"");
        sb.append("\n}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrackingLinkRecord that = (TrackingLinkRecord) o;

        if (isRedirect() != that.isRedirect()) return false;
        if (getMatS2SUrl() != null ? !getMatS2SUrl().equals(that.getMatS2SUrl()) : that.getMatS2SUrl() != null)
            return false;
        return getThirdPartyS2SUrl() != null ? getThirdPartyS2SUrl().equals(that.getThirdPartyS2SUrl()) : that.getThirdPartyS2SUrl() == null;
    }

    @Override
    public int hashCode() {
        int result = (isRedirect() ? 1 : 0);
        result = 31 * result + (getMatS2SUrl() != null ? getMatS2SUrl().hashCode() : 0);
        result = 31 * result + (getThirdPartyS2SUrl() != null ? getThirdPartyS2SUrl().hashCode() : 0);
        return result;
    }
}
