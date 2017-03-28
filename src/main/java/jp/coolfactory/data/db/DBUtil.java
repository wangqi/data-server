package jp.coolfactory.data.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jp.coolfactory.data.util.ConfigUtil;
import jp.coolfactory.data.util.DateUtil;
import jp.coolfactory.data.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wangqi on 25/11/2016.
 */
public class DBUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(DBUtil.class.getName());

    private static HikariDataSource db = null;

    private static String databaseSchemaName = null;

    static {
        Properties configProps = ConfigUtil.getHikariConfig();
        if (configProps == null) {
            configProps = new Properties();
            configProps.setProperty("dataSourceClassName", "db.qiku.mobi");
            configProps.setProperty("dataSource.user", "root");
            configProps.setProperty("dataSource.password", "r00t1234");
            configProps.setProperty("dataSource.databaseName", "app_data");
            LOGGER.info("AntiFraudController uses default hard code db connection config. Please check the config file");
        }
        databaseSchemaName = configProps.getProperty("dataSource.databaseName");
        HikariConfig config = new HikariConfig(configProps);
        db = new HikariDataSource(config);
        db.setInitializationFailFast(true);
        LOGGER.info(configProps.toString());
    }

    /**
     * Get the database schema name.
     * @return
     */
    public static final String getDatabaseSchema() {
        return databaseSchemaName;
    }

    /**
     * Execute a SQL in database
     * @param sql
     * @param processor
     * @param params
     */
    public static void select(String sql,
                              ResultSetProcessor processor,
                              Object... params) {
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            int cnt = 0;
            for (Object param : params) {
                ps.setObject(++cnt, param);
            }
            try (ResultSet rs = ps.executeQuery()) {
                processor.process(rs);
            } catch (SQLException e) {
                LOGGER.error("Failed to execute sql: " + sql, e);
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to execute sql: " + sql, e);
        }
    }

    public static <T> void insertBatch(String sql, PreparedStatementProcessor<T> process, ArrayList<T> list) {
        try (Connection conn = db.getConnection() ) {
            PreparedStatement ps = conn.prepareStatement(sql);
            try {
                for ( T t : list ) {
                    process.process(ps, t);
                    ps.addBatch();
                }
                ps.executeBatch();
            } catch (SQLException e) {
                LOGGER.error("Failed to execute sql: " + sql, e);
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to execute sql: " + sql, e);
        }
    }

    /**
     * Execute the SQL in batch for Statement.
     * @param process
     * @param list
     * @param <T>
     */
    public static <T> void sqlBatch(StatementProcessor<T> process, ArrayList<T> list) {
        try (Connection conn = db.getConnection() ) {
            Statement ps =  conn.createStatement();
            try {
                for ( T t : list ) {
                    String sql = process.process(t);
                    if (StringUtil.isNotEmptyString(sql) ) {
                        ps.addBatch(sql);
                    }
                }
                ps.executeBatch();
            } catch (SQLException e) {
                LOGGER.error("Failed to execute sql", e);
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to execute sql", e);
        }
    }

    /**
     * It stores the result into persistent storage. Currently it stores the data into MySQL.
     * The HashMap param needs the following values:
         action
         site_id
         external_id
         site_name
         country_code
         region_name
         device_brand
         device_carrier
         device_model
         language
         device_ip
         status
         click_date
         install_date
         ios_ifa
         ios_ifv
         os_version
         publisher_id
         publisher_name
         user_agent
         status_code
         status_desc
         eval_prop
         typedesc

     * @param record
     */
    public static final void saveLog(HashMap<String, String> record) {
        String sql = "insert into af_log_install (action,site_id,camp_id,site_name,country_code,region_name, " +
                "device_brand,device_carrier,device_model,language,device_ip,status,click_date,install_date,ios_ifa, " +
                "ios_ifv,os_version,publisher_id,publisher_name,user_agent,status_code,status_desc,eval_prop,tracking_id," +
                "postback_code, postback_desc, e_id, tz) " +
                "values (?,?,?,?,?,?," + "?,?,?,?,?,?,?,?,?," + "?,?,?,?,?,?,?,?,?," + "?,?,?,?)";

        try (Connection conn = db.getConnection() ) {

            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, "install");
            stat.setString(2, record.get("site_id"));
            stat.setString(3, record.get("camp_id"));
            stat.setString(4, record.get("site_name"));
            stat.setString(5, record.get("country_code"));
            stat.setString(6, record.get("region"));
            stat.setString(7, record.get("device_brand"));
            stat.setString(8, record.get("device_carrier"));
            stat.setString(9, record.get("device_model"));
            stat.setString(10, record.get("language"));
            stat.setString(11, record.get("device_ip"));
            stat.setString(12, record.get("status"));
            java.sql.Timestamp clickTimeStamp = null;
            String clickZonedDateString = record.get("click_date_zone");
            if ( clickZonedDateString != null ) {
                ZonedDateTime clickZonedDateTime = DateUtil.convertIOSOffset2Date(clickZonedDateString);
                clickTimeStamp = java.sql.Timestamp.from(clickZonedDateTime.toInstant());
            } else {
                clickTimeStamp = DateUtil.toSqlTimestamp(record.get("click_date"));
            }
            if ( clickTimeStamp != null ) {
                stat.setTimestamp(13, clickTimeStamp);
            } else {
                stat.setTimestamp(13, null);
            }

            java.sql.Timestamp installTimeStamp = null;
            String installZonedDateString = record.get("install_date_zone");
            if ( installZonedDateString != null ) {
                ZonedDateTime installZonedDateTime = DateUtil.convertIOSOffset2Date(installZonedDateString);
                installTimeStamp = java.sql.Timestamp.from(installZonedDateTime.toInstant());
            } else {
                installTimeStamp = DateUtil.toSqlTimestamp(record.get("install_date"));
            }
            if ( installTimeStamp != null ) {
                stat.setTimestamp(14, installTimeStamp);
            } else {
                stat.setTimestamp(14, null);
            }
            stat.setString(15, record.get("ios_ifa"));
            stat.setString(16, record.get("ios_ifv"));
            stat.setString(17, record.get("os_version"));
            stat.setString(18, record.get("publisher_id"));
            stat.setString(19, record.get("publisher_name"));
            stat.setString(20, record.get("user_agent"));
            if ( record.get("status_code") != null )
                stat.setInt(21, Integer.parseInt(record.get("status_code")));
            else
                stat.setInt(21, 0);
            stat.setString(22, record.get("status_desc"));
            if ( record.get("eval_prop") != null )
                stat.setFloat(23, Float.parseFloat(record.get("eval_prop")));
            else
                stat.setFloat(23, 0);
            stat.setString(24, record.get("tracking_id"));
            if ( record.get("postback_code") != null )
                stat.setInt(25, Integer.parseInt(record.get("postback_code")));
            else
                stat.setInt(25, 0);
            stat.setString(26, record.get("postback_desc"));
            stat.setString(27, record.get("e_id"));
            stat.setString(28, record.get("timezone"));

            stat.executeUpdate();

        } catch ( Exception e ) {
            LOGGER.error("Failed to save record into database. ", e);
        }
    }

}
