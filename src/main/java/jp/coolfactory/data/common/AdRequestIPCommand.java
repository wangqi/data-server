package jp.coolfactory.data.common;

import com.ip2location.IP2Location;
import com.ip2location.IPResult;
import com.ip2proxy.IP2Proxy;
import com.ip2proxy.ProxyResult;
import jp.coolfactory.data.Constants;
import jp.coolfactory.data.Version;
import jp.coolfactory.data.module.AdRequest;
import jp.coolfactory.data.module.UserRequest;
import jp.coolfactory.data.server.DBJobManager;
import jp.coolfactory.data.util.ConfigUtil;
import jp.coolfactory.data.util.DateUtil;
import jp.coolfactory.data.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Register an user into ad_reguser table
 *
 * Created by wangqi on 22/2/2017.
 */
public class AdRequestIPCommand implements Handler<AdRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdRequestIPCommand.class);

    private IP2Location ipLocation = new IP2Location();
    private IP2Proxy ipProxy = new IP2Proxy();

    public AdRequestIPCommand() {
        try {
            ipLocation.IPDatabasePath = ConfigUtil.getIPv4File();
            ipProxy.Open(ConfigUtil.getIPv4ProxyFile(), IP2Proxy.IOModes.IP2PROXY_FILE_IO);
        } catch (Exception e) {
            LOGGER.warn("Failed to load IP util.", e);
        }
    }

    @Override
    public CommandStatus handle(AdRequest adRequest) {
        try {
            String installIP = adRequest.getInstall_ip();
            IPResult result = ipLocation.IPQuery(installIP);
            if ( result != null ) {
                String countryCode = result.getCountryShort();
//                String countryName = result.getCountryLong();
                String regionName = result.getRegion();
                String cityName = result.getCity();
//                String isp = result.getISP();
//                String domain = result.getDomain();
                adRequest.setCountry_code(countryCode);
                adRequest.setRegion_name(regionName);
                adRequest.setCity_code(cityName);
                adRequest.setIp_from(result.getIp_from());
                adRequest.setIp_to(result.getIp_to());
            } else {
                LOGGER.warn("Didn't find ip data for " + adRequest.getInstall_ip());
            }
            ProxyResult proxyResult = ipProxy.GetAll(installIP);
            int isProxy = proxyResult.Is_Proxy;
            String proxyType = proxyResult.Proxy_Type;
            adRequest.setIs_proxy(isProxy);
            if (StringUtil.isNotEmptyString(proxyType)) {
                if ( ! "-".equals(proxyType) ) {
                    adRequest.setProxy_type(proxyType);
                }
            }
        } catch (Exception e) {
            LOGGER.warn("handle is interrupted.", e);
            return CommandStatus.Fail;
        }
        return CommandStatus.Continue;
    }

}
