package jp.coolfactory.data.util;

import jp.coolfactory.data.db.DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Manage the config utitilies.
 *
 * Created by wangqi on 17/1/2017.
 */
public class ConfigUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(ConfigUtil.class.getName());

    private static final String MODE_TEST = "test";
    private static final String MODE_PROD = "prod";

    private static final String MODE = System.getProperty("mode", MODE_PROD);

    /**
     * Get the database name for anti_fraud system
     * @return
     */
    public static final String getAntiFraudDatabaseSchema() {
//        return "anti_fraud";
        return DBUtil.getDatabaseSchema();
    }

    /**
     * Get the datasource Hikari
     * @return
     */
    public static final Properties getHikariConfig() {
        try {
            Properties configProps = new Properties();
            InputStream is ;
            if (MODE_TEST.equals(MODE)) {
                is = ConfigUtil.class.getResourceAsStream("/hikari_test.properties");
                LOGGER.info("AntiFraudController uses test db connection");
            } else {
                is = ConfigUtil.class.getResourceAsStream("/hikari_"+MODE+".properties");
                LOGGER.info("AntiFraudController uses "+MODE+" db connection");
                if ( is == null ) {
                    is = ConfigUtil.class.getResourceAsStream("/hikari_prod.properties");
                    LOGGER.info("AntiFraudController uses prod db connection");
                }
            }
            if (is != null) {
                configProps.load(is);
            }
            return configProps;
        } catch (IOException e) {
            LOGGER.warn("Failed to load inputstream from db config file", e);
        }
        return null;
    }

    /**
     * Get the IP2Location file path.
     * @return
     */
    public static final String getIPCSVFile() {
        String ipv4 = System.getProperty("ipv4");
        File file = null;
        if ( ipv4 != null ) {
            file = new File(ipv4);
        }
        if ( file.exists() ) {
            return file.getAbsolutePath();
        }
        LOGGER.warn("Didn't find the ipv4 file specified in system property -Dipv4: " + ipv4 );
        String workingDir = System.getProperty("user.dir");
        file = new File(workingDir, "ipv4.csv");
        if ( file.exists() ) {
            return file.getAbsolutePath();
        }
        LOGGER.warn("Didn't find the ipv4.csv in working dir: " + file.getAbsolutePath());
        return null;
    }

}
