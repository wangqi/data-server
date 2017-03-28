package jp.coolfactory.data.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by wangqi on 24/2/2017.
 */
public class StringUtilTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void dictFormat0() throws Exception {
        String stat_id = "0123456";
        String app_key = "appKey";
        String test = "https://www.coolfactory.jp/test?param=";
        HashMap<String, String> maps = new HashMap<>();
        maps.put("stat_id", stat_id);
        maps.put("app_key", app_key);
        String actual = StringUtil.replaceKey(test, maps);
        String expect = "https://www.coolfactory.jp/test?param=";
        assertEquals(expect, actual);
    }

    @Test
    public void dictFormat1() throws Exception {
        String stat_id = "0123456";
        String app_key = "appKey";
        String test = "https://www.coolfactory.jp/test?stat_id={stat_id}&app_key={app_key}=data={{data}}";
        HashMap<String, String> maps = new HashMap<>();
        maps.put("stat_id", stat_id);
        maps.put("app_key", app_key);
        String actual = StringUtil.replaceKey(test, maps);
        String expect = "https://www.coolfactory.jp/test?stat_id=0123456&app_key=appKey=data={data}";
        assertEquals(expect, actual);
    }

    @Test
    public void dictFormat2() throws Exception {
        String stat_id = "0123456";
        String app_key = "appKey";
        String test = "https://www.coolfactory.jp/test{";
        HashMap<String, String> maps = new HashMap<>();
        maps.put("stat_id", stat_id);
        maps.put("app_key", app_key);
        String actual = StringUtil.replaceKey(test, maps);
        String expect = "https://www.coolfactory.jp/test";
        assertEquals(expect, actual);
    }

    @Test
    public void dictFormat3() throws Exception {
        String stat_id = "0123456";
        String app_key = "appKey";
        String test = "https://www.coolfactory.jp/test={}";
        HashMap<String, String> maps = new HashMap<>();
        maps.put("stat_id", stat_id);
        maps.put("app_key", app_key);
        String actual = StringUtil.replaceKey(test, maps);
        String expect = "https://www.coolfactory.jp/test=";
        assertEquals(expect, actual);
    }

    @Test
    public void dictFormat4() throws Exception {
        String stat_id = "app_key";
        String app_key = "appKey";
        String test = "https://www.coolfactory.jp/test={app_key={stat_id}}";
        HashMap<String, String> maps = new HashMap<>();
        maps.put("stat_id", stat_id);
        maps.put("app_key", app_key);
        String actual = StringUtil.replaceKey(test, maps);
        String expect = "https://www.coolfactory.jp/test=";
        assertEquals(expect, actual);
    }

    @Test
    public void dictFormat5() throws Exception {
        String stat_id = "0123456";
        String app_key = "appKey";
        String test = "https://www.coolfactory.jp/test?param=}";
        HashMap<String, String> maps = new HashMap<>();
        maps.put("stat_id", stat_id);
        maps.put("app_key", app_key);
        String actual = StringUtil.replaceKey(test, maps);
        String expect = "https://www.coolfactory.jp/test?param=";
        assertEquals(expect, actual);
    }

    @Test
    public void dictFormat6() throws Exception {
        String stat_id = "0123456";
        String app_key = "appKey";
        String test = "https://www.coolfactory.jp/test?param=}}";
        HashMap<String, String> maps = new HashMap<>();
        maps.put("stat_id", stat_id);
        maps.put("app_key", app_key);
        String actual = StringUtil.replaceKey(test, maps);
        String expect = "https://www.coolfactory.jp/test?param=}";
        assertEquals(expect, actual);
    }

    @Test
    public void compactOsVersion() throws Exception {
        String os_version = "10.2";
        String expected = "10.2";
        String actual = StringUtil.compactOsVersion(os_version);
        assertEquals(expected, actual);
    }

    @Test
    public void compactOsVersion1() throws Exception {
        String os_version = "10.2.1";
        String expected = "10.2";
        String actual = StringUtil.compactOsVersion(os_version);
        assertEquals(expected, actual);
    }

    @Test
    public void compactOsVersion2() throws Exception {
        String os_version = "10.2.1.0";
        String expected = "10.2.1";
        String actual = StringUtil.compactOsVersion(os_version);
        assertEquals(expected, actual);
    }

    @Test
    public void compactOsVersion3() throws Exception {
        String os_version = null;
        String expected = null;
        String actual = StringUtil.compactOsVersion(os_version);
        assertEquals(expected, actual);
    }

    @Test
    public void compactOsVersion4() throws Exception {
        String os_version = "";
        String expected = "";
        String actual = StringUtil.compactOsVersion(os_version);
        assertEquals(expected, actual);
    }

    @Test
    public void generateHashBase64() throws Exception {
        String ios_ifa = "7d9af3fa-7326-49c9-8173-0b39e9496772";
        String google_aid = "fca5ba73-27b8-4066-8fd8-60f0d79f6778";
        String ua_devicemodel_lang_os_ip = "Mozilla/5.0 (iPhone; CPU iPhone OS 8_2 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Mobile/12D508" +
                "iPhone4,1" + "ja" + "4.0.10" + "126.210.48.225";
        String base64_ifa = StringUtil.generateHashBase64(ios_ifa);
        System.out.println("base64_idfa: " + base64_ifa + ", len: " + base64_ifa.length());
        String base64_aid = StringUtil.generateHashBase64(google_aid);
        System.out.println("base64_aid: " + base64_aid + ", len: " + base64_aid.length());
        String base64_ua_devicemodel_lang_os_ip = StringUtil.generateHashBase64(ua_devicemodel_lang_os_ip);
        System.out.println("base64_ua_devicemodel_lang_os_ip: " + base64_ua_devicemodel_lang_os_ip + ", len: " + base64_ua_devicemodel_lang_os_ip.length());

        assertEquals(base64_aid.length(), base64_ifa.length());
        assertEquals(base64_aid.length(), base64_ua_devicemodel_lang_os_ip.length());
    }
}