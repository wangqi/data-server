package jp.coolfactory.anti_fraud.frequency;

/**
 * Created by wangqi on 27/12/2016.
 */
public interface FrequencyManager {

    public RecordStatus check(CacheRecord record);

}
