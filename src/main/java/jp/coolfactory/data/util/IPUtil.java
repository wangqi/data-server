package jp.coolfactory.data.util;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * It's used to format a IP address to IP segment.
 *
 * Created by wangqi on 22/12/2016.
 */
public class IPUtil {

    private final static Logger LOGGER = Logger.getLogger(IPUtil.class.getName());

    private static final int DEFAULT_IP_PREFIX_LEN = 24;

    private static final int NBITS = 32;

    private static final String IP_ADDRESS = "(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})";

    private static final Pattern IP_PATTERN = Pattern.compile(IP_ADDRESS);

    private static final int[] IP_MASKS = new int[32];
    static {
        /**
         * Calculate the IP netmask prefix first.
         */
        for (int i=1; i<=32; i++) {
            for (int j = 0; j < i; j++) {
                IP_MASKS[i-1] |= (1 << 31 - j);
            }
        }
    }

    /**
     * Format an IP address with only prefix left. The prefix may not be the network segment but
     * it's what we think can represent an user.
     *
     * @param ipAddress
     * @return
     */
    public static final String formatIPPrefix(String ipAddress) {
       return formatIPPrefix(ipAddress, DEFAULT_IP_PREFIX_LEN);
    }

    /**
     * Format an IP address with given prefixLen. If the ipAddress is null, it will return null instead of empty string.
     * If the ipAddress is not valid, it will return the same value back.
     * @param ipAddress
     * @param prefixLen
     * @return
     */
    public static final String formatIPPrefix(String ipAddress, int prefixLen) {
        if ( ipAddress == null ) {
            return ipAddress;
        }
        Matcher matcher = IP_PATTERN.matcher(ipAddress);
        if (matcher.matches()) {
            int groupCount = matcher.groupCount();
            if ( groupCount != 4 ) {
                LOGGER.warning("IP is not valid: " + ipAddress);
                return ipAddress;
            }
            int[] ipParts = new int[4];
            for ( int i=0; i<ipParts.length; i++ ) {
                ipParts[i] = Integer.parseInt(matcher.group(i+1));
            }
            int prefixMask = 0;
            if ( prefixLen >= 0 && prefixMask < IP_MASKS.length ) {
                prefixMask = IP_MASKS[prefixLen-1];
            }
            StringBuilder buf = new StringBuilder(32);
            for ( int i=0; i<ipParts.length-1; i++ ){
                buf.append( ipParts[i]  & (prefixMask>>(NBITS-8*(i+1))) ).append('.');
            }
            buf.append(ipParts[3] & 256);
            return buf.toString();
        } else {
            LOGGER.warning("ipAddress is not matched." + ipAddress);
            return ipAddress;
        }

    }

    /**
     * Convert an IP address to long number.
     * @param ipAddress
     * @return
     */
    public static final long ip2long(String ipAddress) {
        long ipValue = 0l;
        if ( ipAddress == null ) {
            return ipValue;
        }
        Matcher matcher = IP_PATTERN.matcher(ipAddress);
        if (matcher.matches()) {
            int groupCount = matcher.groupCount();
            if ( groupCount != 4 ) {
                LOGGER.warning("IP is not valid: " + ipAddress);
                return ipValue;
            }
            int[] ipParts = new int[4];
            for ( int i=0; i<ipParts.length; i++ ) {
                ipParts[i] = Integer.parseInt(matcher.group(i+1));
                ipValue <<= 8;
                ipValue |= ipParts[i];
            }
            ipValue &= 0xffffffff;
        } else {
            LOGGER.warning("ipAddress is not matched." + ipAddress);
        }
        return ipValue;
    }

}
