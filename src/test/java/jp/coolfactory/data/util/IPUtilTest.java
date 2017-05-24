package jp.coolfactory.data.util;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by wangqi on 24/5/2017.
 */
public class IPUtilTest {

    private String[] ips = new String[]{
            "118.110.190.136",
            "118.110.190.173",
            "118.110.190.189",
            "118.110.190.248",
            "118.110.190.62",
            "118.110.191.74",
            "118.110.191.83",
            "118.110.191.88",
            "118.110.192.156",
            "118.110.192.156",
            "118.110.192.177",
            "118.110.193.107",
            "118.110.193.122",
            "118.110.194.24",
            "118.110.195.106",
            "118.110.195.149",
            "118.110.195.213",
            "118.110.195.216",
            "118.110.195.238",
            "118.110.195.98",
            "118.110.195.98",
            "118.110.202.126",
            "118.110.202.229",
            "118.110.202.253",
            "118.110.202.253",
            "118.110.203.104",
            "118.110.203.104",
            "118.110.203.104",
            "118.110.203.28",
            "118.110.203.28",
            "118.110.204.250",
            "118.110.204.82",
            "118.110.205.20",
            "118.110.206.149",
            "118.110.206.149",
            "118.110.206.221",
            "118.110.206.43",
            "118.110.207.111",
            "118.110.207.111",
            "118.110.207.223",
            "118.110.207.239",
            "118.110.27.201",
            "118.110.65.215",
    };

    @Test
    public void formatIPPrefix() throws Exception {
        HashMap<String,Integer> map = new HashMap<>();
        for (String ip : ips ){
            String ipPrefix = IPUtil.formatIPPrefix(ip, 18);
            Integer count = map.get(ipPrefix);
            if (count == null) {
                count = 1;
                map.put(ipPrefix, count);
            } else {
                map.put(ipPrefix, count+1);
            }
        }
        System.out.println(map);
    }

}