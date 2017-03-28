package jp.coolfactory.anti_fraud.frequency;

/**
 * Created by wangqi on 27/12/2016.
 */
public class CacheRecord implements Comparable{

    //It's used to store in cache. Since the IP will be turned into segment, it may be same for a bunch of records.
    private String key = null;
    //It's used to identify each record. The IP will be kept
    private String id = null;
    private long created = -1l;

    public CacheRecord(String key, String id, long created) {
        this.key = key;
        this.id = id;
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CacheRecord that = (CacheRecord) o;

        if (getCreated() != that.getCreated()) return false;
        if (getKey() != null ? !getKey().equals(that.getKey()) : that.getKey() != null) return false;
        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        int result = getKey() != null ? getKey().hashCode() : 0;
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        result = 31 * result + (int) (getCreated() ^ (getCreated() >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "CacheRecord{" +
                "key='" + key + '\'' +
                ", id='" + id + '\'' +
                ", created=" + created +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        if (this == o) return 0;
        if (o == null || getClass() != o.getClass()) return -1;

        CacheRecord that = (CacheRecord) o;

        int diff = (int)(getCreated() - that.getCreated());
        if ( diff != 0 ) return diff;
        if (getKey() != null ) {
            diff = getKey().compareTo(that.getKey());
            if ( diff != 0 ) return diff;
        }
        if ( getId() != null ) {
            diff = getId().compareTo(that.getId());
            return diff;
        }
        return -1;
    }
}
