package jp.coolfactory.data.util;

import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.util.TimeZone;

/**
 * Created by wangqi on 1/12/2016.
 */
public class DateUtil {

    private final static Logger LOGGER = Logger.getLogger(DateUtil.class.getName());

    private static final SimpleDateFormat IOS_FMT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat IOS_DATE_FMT = new SimpleDateFormat("yyyy-MM-dd");

    private static final DateTimeFormatter IOS_LOCAL_DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter IOS_LOCAL_DATETIME_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final ZoneId UTC_ZONE_ID = ZoneId.of("UTC").normalized();

    /**
     * Convert 'yyyy-MM-dd HH:mm:ss' format string to Date object
     * The time is treated as UTC
     *
     * @param iosString
     * @return
     */
    public static final LocalDateTime convertIOS2Date(String iosString) {
        try {
            LocalDateTime ldt = LocalDateTime.parse(iosString, IOS_LOCAL_DATETIME_FMT);
            return ldt;
        } catch ( Exception e ) {
            try {
                LocalDate ld = LocalDate.parse(iosString, IOS_LOCAL_DATE_FMT);
                LocalDateTime ldt = LocalDateTime.of(ld, LocalTime.MIDNIGHT);
                return ldt;
            } catch (Exception e1) {
                LOGGER.warn("Failed to parse the IOS format data: " + iosString);
            }
            return null;
        }
    }

    /**
     * Convert 'yyyy-MM-dd HH:mm:ss' format string to Date object.
     * The time is set as the given timezone.
     *
     * @param iosString
     * @return
     */
    public static final ZonedDateTime convertIOS2Date(String iosString, String timezone) {
        try {
            ZoneId zoneId = ZoneId.of(timezone);
            LocalDateTime ldt = convertIOS2Date(iosString);
            if ( ldt != null )
                return ldt.atZone(zoneId);
        } catch ( Exception e ) {
            LOGGER.warn("Failed to parse the IOS format data: " + iosString);
        }
        return null;
    }

    /**
     * Convert 'yyyy-MM-dd HH:mm:ss' format string to Date object
     * The time is treated as UTC
     *
     * @param iosString
     * @return
     */
    public static final ZonedDateTime convertIOSOffset2Date(String iosString) {
        try {
            ZonedDateTime zdt = ZonedDateTime.parse(iosString, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            return zdt;
        } catch ( Exception e ) {
            LOGGER.warn("Failed to parse the IOS with offset format data: " + iosString);
            return null;
        }
    }

    /**
     * Change a datetime from UTC to given timezone.
     * @param current
     * @param toTimeZone
     * @return
     */
    public static final ZonedDateTime changeTimeZone(LocalDateTime current, ZoneId toTimeZone) {
        return changeTimeZone(current, UTC_ZONE_ID, toTimeZone);
    }

    /**
     * Change a datatime from a timezone to another timezone
     * @param current
     * @param fromTimeZone
     * @param toTimeZone
     * @return
     */
    public static final ZonedDateTime changeTimeZone(LocalDateTime current, ZoneId fromTimeZone, ZoneId toTimeZone) {
        if ( current == null ) {
            return null;
        }
        ZonedDateTime src = ZonedDateTime.of(current, fromTimeZone);
        ZonedDateTime zdt = src.withZoneSameInstant(toTimeZone);
        return zdt.withZoneSameInstant(toTimeZone);
    }


    /**
     * Convert Date object to 'yyyy-MM-ddTHH:mm:ssZ'
     * @param date
     * @return
     */
    public static final String formatDateTimeWithTimezone(LocalDateTime date) {
        try {
            return DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(date);
        } catch ( Exception e ) {
            LOGGER.warn("Failed to format the IOS format data: " + date);
            return null;
        }
    }

    /**
     * Convert Date object to 'yyyy-MM-ddTHH:mm:ssZ'
     * @param date
     * @return
     */
    public static final String formatDateTimeWithTimezone(ZonedDateTime date) {
        try {
            return DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(date);
        } catch ( Exception e ) {
            LOGGER.warn("Failed to format the IOS format data: " + date);
            return null;
        }
    }

    /**
     * Convert Date object to 'yyyy-MM-ddTHH:mm:ssZ'
     * @param date
     * @return
     */
    public static final String formatDateTimeWithTimezone(LocalDateTime date, ZoneId timezone) {
        try {
            ZonedDateTime zonedDateTime = date.atZone(timezone);
            return DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(zonedDateTime);
        } catch ( Exception e ) {
            LOGGER.warn("Failed to format the IOS format data: " + date);
            return null;
        }
    }

    /**
     * Convert Date object to 'yyyy-MM-dd HH:mm:ss'
     * @param date
     * @return
     */
    public static final String formatDateTime(ZonedDateTime date) {
        if ( date == null ) {
            return null;
        }
        try {
            return IOS_LOCAL_DATETIME_FMT.format(date);
        } catch ( Exception e ) {
            LOGGER.warn("Failed to format the IOS format data: " + date);
            return null;
        }
    }

    /**
     * It parse a string in 'yyyy-MM-dd HH:mm:ss' format, which will be treated as UTC, to given timezone
     * and formated as 'yyyy-MM-ddTHH:mm:ssZ'
     *
      * @param dateString
     * @return
     */
    public static final String turnDateTimeToTimezone(String dateString, String timezone) {
        try {
            ZoneId zoneId = ZoneId.of(timezone);
            LocalDateTime installDate = DateUtil.convertIOS2Date(dateString);
            ZonedDateTime zonedInstallDate = DateUtil.changeTimeZone(installDate, zoneId);
            return DateUtil.formatDateTimeWithTimezone(zonedInstallDate);
        } catch (Exception e) {
            LOGGER.warn("Failed to parse the timezone or zoned date. timezone: " + timezone +
                    ", dateString: " + dateString, e);
        }
        return null;
    }

    /**
     *
      * @param dateString
     * @return
     */
    public static final java.sql.Timestamp toSqlTimestamp(String dateString) {
        try {
            LocalDateTime localDate = DateUtil.convertIOS2Date(dateString);
            if ( localDate != null ) {
                return java.sql.Timestamp.valueOf(localDate);
            }
        } catch (Exception e) {
            LOGGER.warn("Failed to convert string to sql timestamp: " + dateString, e);
        }
        return null;
    }

    /**
     * Convert ZoneDateTime to UTC epoch milliseconds
     */
    public static final long toMilliseconds(ZonedDateTime zdt) {
        int zdtOffset = TimeZone.getTimeZone(zdt.getZone().getId()).getOffset(zdt.toInstant().toEpochMilli());
        int utcOffset = TimeZone.getTimeZone("UTC").getOffset(zdt.toInstant().toEpochMilli());
        int diff = utcOffset - zdtOffset;
        long millis = zdt.toInstant().toEpochMilli() + diff;
        return millis;
    }
}
