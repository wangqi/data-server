package jp.coolfactory.anti_fraud.db;

import jp.coolfactory.anti_fraud.module.Status;

/**
 * It's used to check and filter the conversion data.
 *
 * Created by wangqi on 28/12/2016.
 */
public interface Checker {

    default Status check(CheckType type, String paramValue) {
        return this.check(type, paramValue, false);
    }

    Status check(CheckType type, String paramValue, boolean quick);
}
