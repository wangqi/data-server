package jp.coolfactory.anti_fraud.module;

import java.io.FileReader;
import java.util.HashMap;

/**
 * It's the status object that is used to encapsulate the checking result.
 * Created by wangqi on 29/11/2016.
 */
public enum Status {

    OK(0, "approved"),
    ALREADY_DENIED(100, "Denied by third-party system."),
    FORBIDDEN_COUNTRY(100, "Country is forbidden"),
    FORBIDDEN_DEVICE(200, "Device is forbidden"),
    FORBIDDEN_LANG(300, "Language is forbidden"),
    FORBIDDEN_OS(400, "OS version is forbidden"),
    FORBIDDEN_CARRIER(500, "Carrier is forbidden"),
    FORBIDDEN_JAILBROKE(600, "iOS Jailbroke is forbidden"),
    FORBIDDEN_IP(700, "IP is in blacklist"),
    FORBIDDEN_IP_FREQ(710, "IP's freqency is suspicious"),
    FORBIDDEN_IP_PRXOY(720, "IP is proxy"),
    FORBIDDEN_IP_SEG(730, "IP segment duplicate"),
    FORBIDDEN_INTERVAL(800, "Click to install is too quick"),
    FORBIDDEN_TIMERANGE(900, "Forbid mid-night installs");

    private int status;
    private String desc;

    Status(int statusCode, String desc) {
        this.status = statusCode;
        this.desc = desc;
    }

    public int getStatus() {
        return this.status;
    }

    public String getDesc() {
        return this.desc;
    }

}
