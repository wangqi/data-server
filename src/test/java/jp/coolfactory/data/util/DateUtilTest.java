package jp.coolfactory.data.util;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by wangqi on 24/5/2017.
 */
public class DateUtilTest {

    @Test
    public void toMilliseconds() throws Exception {
        String source = "2018-05-07 14:59:07";
        String pattern = "yyyy-MM-dd hh:mm:ss";
        DateFormat sdf = new SimpleDateFormat(pattern);
        try {
            Date date = sdf.parse(source);
            ZonedDateTime zdt = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of("Asia/Tokyo"));
            System.out.println(zdt.format(DateTimeFormatter.ofPattern(pattern)));
            long actualMillis = DateUtil.toMilliseconds(zdt);
            // 1525640347
            System.out.println(actualMillis);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}