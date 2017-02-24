package jp.coolfactory.data.controller;

import jp.coolfactory.data.module.AdApp;
import jp.coolfactory.data.util.DateUtil;
import org.junit.Before;
import org.junit.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;

import static org.junit.Assert.*;

/**
 * Created by wangqi on 22/2/2017.
 */
public class AdAppControllerTest {

    @Before
    public void setUp() {
        AdAppController.getInstance().init();
    }

    @Test
    public void parseDateString() throws Exception {
        String appKey = "battleship2cn";
        AdApp app = AdAppController.getInstance().getApAdd(appKey);
        app.setSrcTimeZone("Asia/Shanghai");
        app.setDstTimeZone("Asia/Tokyo");
        ZonedDateTime date1 = AdAppController.getInstance().parseDateString( appKey, "2017-02-22 16:33:00");
        String actual = DateUtil.formatDateTimeWithTimezone(date1);
        assertEquals("2017-02-22T17:33:00+09:00", actual);
    }
}