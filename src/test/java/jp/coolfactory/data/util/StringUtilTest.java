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
}