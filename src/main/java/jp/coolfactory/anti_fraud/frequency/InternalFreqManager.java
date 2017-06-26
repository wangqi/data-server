package jp.coolfactory.anti_fraud.frequency;


import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.logging.Logger;

/**
 * It manage the CacheRecord in a HashMap and manage the timeout manually. It's for test only.
 * In the 'check' method, it implements a very simple logic, that checks how many records under same key.
 * If the total number is less than suspicious_threshold, it returns OK.
 * If the total number is bigger than suspicious_threshold and less than forbidden threshold, it returns suspicious.
 * Otherwise, it returns forbidden status.
 *
 * Created by wangqi on 27/12/2016.
 */
public class InternalFreqManager implements FrequencyManager {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(InternalFreqManager.class);

    //The interval in seconds that we do statistic for same IP prefix/site_id records
    private int intervalSeconds = 86400;
    private int suspicious_threshold = 10;
    private int forbidden_threshold = 20;

    //The internal cache
    private HashMap<String, TreeSet<CacheRecord>> cache = new HashMap<String, TreeSet<CacheRecord>>();

    public InternalFreqManager() {
    }

    public InternalFreqManager(int intervalSeconds) {
        this.intervalSeconds = intervalSeconds;
    }

    public int getIntervalSeconds() {
        return intervalSeconds;
    }

    public void setIntervalSeconds(int intervalSeconds) {
        this.intervalSeconds = intervalSeconds;
    }

    public int getSuspicious_threshold() {
        return suspicious_threshold;
    }

    public void setSuspicious_threshold(int suspicious_threshold) {
        this.suspicious_threshold = suspicious_threshold;
    }

    public int getForbidden_threshold() {
        return forbidden_threshold;
    }

    public void setForbidden_threshold(int forbidden_threshold) {
        this.forbidden_threshold = forbidden_threshold;
    }

    /**
     * It implements a very simple logic that checks the total number of records under same key.
     *
     * @param record
     * @return
     */
    @Override
    public RecordStatus check(CacheRecord record) {
        if ( record != null ) {
            TreeSet<CacheRecord> set = cache.get(record.getKey());
            if (set == null) {
                set = new TreeSet<CacheRecord>();
                cache.put(record.getKey(), set);
            }
            ArrayList<CacheRecord> oldRecords = new ArrayList<CacheRecord>();
            for (CacheRecord that : set) {
                int diff = (int) ((record.getCreated() - that.getCreated()) / 1000);
                if (diff > this.intervalSeconds) {
                    oldRecords.add(that);
                } else {
                    return RecordStatus.OK;
                    //In an ordered Set, if the current record is less than threshold, it's no need to check further.
                }
            }
            set.removeAll(oldRecords);
            set.add(record);
            if (set.size() < suspicious_threshold) {
                return RecordStatus.OK;
            } else if (set.size() < forbidden_threshold) {
                return RecordStatus.SUSPICIOUS;
            } else {
                return RecordStatus.FORBIDDEN;
            }
        } else {
            LOGGER.warn("The CacheRecord is null.");
            return RecordStatus.OK;
        }
    }

}
