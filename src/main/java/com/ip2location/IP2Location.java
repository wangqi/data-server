package com.ip2location;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IP2Location {
    private static final Pattern pattern = Pattern.compile("^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
    private static final Pattern pattern2 = Pattern.compile("^([0-9A-F]{1,4}:){6}(0[0-9]+\\.|.*?\\.0[0-9]+).*$", 2);
    private static final Pattern pattern3 = Pattern.compile("^[0-9]+$");
    private static final Pattern pattern4 = Pattern.compile("^(.*:)(([0-9]+\\.){3}[0-9]+)$");
    private static final Pattern pattern5 = Pattern.compile("^.*((:[0-9A-F]{1,4}){2})$");
    private static final Pattern pattern6 = Pattern.compile("^[0:]+((:[0-9A-F]{1,4}){1,2})$", 2);
    private static final BigInteger MAX_IPV4_RANGE = new BigInteger("4294967295");
    private static final BigInteger MAX_IPV6_RANGE = new BigInteger("340282366920938463463374607431768211455");
    private static final int[] COUNTRY_POSITION = new int[]{0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2};
    private static final int[] REGION_POSITION = new int[]{0, 0, 0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3};
    private static final int[] CITY_POSITION = new int[]{0, 0, 0, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4};
    private static final int[] ISP_POSITION = new int[]{0, 0, 3, 0, 5, 0, 7, 5, 7, 0, 8, 0, 9, 0, 9, 0, 9, 0, 9, 7, 9, 0, 9, 7, 9};
    private static final int[] LATITUDE_POSITION = new int[]{0, 0, 0, 0, 0, 5, 5, 0, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5};
    private static final int[] LONGITUDE_POSITION = new int[]{0, 0, 0, 0, 0, 6, 6, 0, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6};
    private static final int[] DOMAIN_POSITION = new int[]{0, 0, 0, 0, 0, 0, 0, 6, 8, 0, 9, 0, 10, 0, 10, 0, 10, 0, 10, 8, 10, 0, 10, 8, 10};
    private static final int[] ZIPCODE_POSITION = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 7, 7, 7, 0, 7, 7, 7, 0, 7, 0, 7, 7, 7, 0, 7};
    private static final int[] TIMEZONE_POSITION = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 8, 7, 8, 8, 8, 7, 8, 0, 8, 8, 8, 0, 8};
    private static final int[] NETSPEED_POSITION = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 11, 0, 11, 8, 11, 0, 11, 0, 11, 0, 11};
    private static final int[] IDDCODE_POSITION = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 12, 0, 12, 0, 12, 9, 12, 0, 12};
    private static final int[] AREACODE_POSITION = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 13, 0, 13, 0, 13, 10, 13, 0, 13};
    private static final int[] WEATHERSTATIONCODE_POSITION = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 14, 0, 14, 0, 14, 0, 14};
    private static final int[] WEATHERSTATIONNAME_POSITION = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 15, 0, 15, 0, 15, 0, 15};
    private static final int[] MCC_POSITION = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 16, 0, 16, 9, 16};
    private static final int[] MNC_POSITION = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 17, 0, 17, 10, 17};
    private static final int[] MOBILEBRAND_POSITION = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11, 18, 0, 18, 11, 18};
    private static final int[] ELEVATION_POSITION = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11, 19, 0, 19};
    private static final int[] USAGETYPE_POSITION = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12, 20};
    private MetaData _MetaData = null;
    private MappedByteBuffer _IPv4Buffer = null;
    private MappedByteBuffer _IPv6Buffer = null;
    private MappedByteBuffer _MapDataBuffer = null;
    private int[][] _IndexArrayIPv4 = new int[65536][2];
    private int[][] _IndexArrayIPv6 = new int[65536][2];
    private long _IPv4Offset = 0L;
    private long _IPv6Offset = 0L;
    private long _MapDataOffset = 0L;
    private int _IPv4ColumnSize = 0;
    private int _IPv6ColumnSize = 0;
    public boolean UseMemoryMappedFile = false;
    public String IPDatabasePath = "";
    public String IPLicensePath = "";
    private boolean gotdelay = false;
    private boolean _alreadyCheckedKey = true;
    private int COUNTRY_POSITION_OFFSET;
    private int REGION_POSITION_OFFSET;
    private int CITY_POSITION_OFFSET;
    private int ISP_POSITION_OFFSET;
    private int DOMAIN_POSITION_OFFSET;
    private int ZIPCODE_POSITION_OFFSET;
    private int LATITUDE_POSITION_OFFSET;
    private int LONGITUDE_POSITION_OFFSET;
    private int TIMEZONE_POSITION_OFFSET;
    private int NETSPEED_POSITION_OFFSET;
    private int IDDCODE_POSITION_OFFSET;
    private int AREACODE_POSITION_OFFSET;
    private int WEATHERSTATIONCODE_POSITION_OFFSET;
    private int WEATHERSTATIONNAME_POSITION_OFFSET;
    private int MCC_POSITION_OFFSET;
    private int MNC_POSITION_OFFSET;
    private int MOBILEBRAND_POSITION_OFFSET;
    private int ELEVATION_POSITION_OFFSET;
    private int USAGETYPE_POSITION_OFFSET;
    private boolean COUNTRY_ENABLED;
    private boolean REGION_ENABLED;
    private boolean CITY_ENABLED;
    private boolean ISP_ENABLED;
    private boolean LATITUDE_ENABLED;
    private boolean LONGITUDE_ENABLED;
    private boolean DOMAIN_ENABLED;
    private boolean ZIPCODE_ENABLED;
    private boolean TIMEZONE_ENABLED;
    private boolean NETSPEED_ENABLED;
    private boolean IDDCODE_ENABLED;
    private boolean AREACODE_ENABLED;
    private boolean WEATHERSTATIONCODE_ENABLED;
    private boolean WEATHERSTATIONNAME_ENABLED;
    private boolean MCC_ENABLED;
    private boolean MNC_ENABLED;
    private boolean MOBILEBRAND_ENABLED;
    private boolean ELEVATION_ENABLED;
    private boolean USAGETYPE_ENABLED;

    public IP2Location() {
    }

    private void DestroyMappedBytes() {
        if(this._IPv4Buffer != null) {
            this._IPv4Buffer = null;
        }

        if(this._IPv6Buffer != null) {
            this._IPv6Buffer = null;
        }

        if(this._MapDataBuffer != null) {
            this._MapDataBuffer = null;
        }

    }

    private void CreateMappedBytes() throws IOException {
        RandomAccessFile var1 = null;

        try {
            var1 = new RandomAccessFile(this.IPDatabasePath, "r");
            FileChannel var2 = var1.getChannel();
            this.CreateMappedBytes(var2);
        } catch (IOException var6) {
            throw var6;
        } finally {
            if(var1 != null) {
                var1.close();
                var1 = null;
            }

        }

    }

    private void CreateMappedBytes(FileChannel var1) throws IOException {
        try {
            long var2;
            if(this._IPv4Buffer == null) {
                var2 = (long)this._IPv4ColumnSize * (long)this._MetaData.getDBCount();
                this._IPv4Offset = (long)(this._MetaData.getBaseAddr() - 1);
                this._IPv4Buffer = var1.map(MapMode.READ_ONLY, this._IPv4Offset, var2);
                this._IPv4Buffer.order(ByteOrder.LITTLE_ENDIAN);
                this._MapDataOffset = this._IPv4Offset + var2;
            }

            if(!this._MetaData.getOldBIN() && this._IPv6Buffer == null) {
                var2 = (long)this._IPv6ColumnSize * (long)this._MetaData.getDBCountIPv6();
                this._IPv6Offset = (long)(this._MetaData.getBaseAddrIPv6() - 1);
                this._IPv6Buffer = var1.map(MapMode.READ_ONLY, this._IPv6Offset, var2);
                this._IPv6Buffer.order(ByteOrder.LITTLE_ENDIAN);
                this._MapDataOffset = this._IPv6Offset + var2;
            }

            if(this._MapDataBuffer == null) {
                this._MapDataBuffer = var1.map(MapMode.READ_ONLY, this._MapDataOffset, var1.size() - this._MapDataOffset);
                this._MapDataBuffer.order(ByteOrder.LITTLE_ENDIAN);
            }

        } catch (IOException var4) {
            throw var4;
        }
    }

    private boolean LoadBIN() throws IOException {
        boolean var1 = false;
        RandomAccessFile var2 = null;

        try {
            if(this.IPDatabasePath.length() > 0) {
                var2 = new RandomAccessFile(this.IPDatabasePath, "r");
                FileChannel var3 = var2.getChannel();
                MappedByteBuffer var4 = var3.map(MapMode.READ_ONLY, 0L, 64L);
                var4.order(ByteOrder.LITTLE_ENDIAN);
                this._MetaData = new MetaData();
                this._MetaData.setDBType(var4.get(0));
                this._MetaData.setDBColumn(var4.get(1));
                this._MetaData.setDBYear(var4.get(2));
                this._MetaData.setDBMonth(var4.get(3));
                this._MetaData.setDBDay(var4.get(4));
                this._MetaData.setDBCount(var4.getInt(5));
                this._MetaData.setBaseAddr(var4.getInt(9));
                this._MetaData.setDBCountIPv6(var4.getInt(13));
                this._MetaData.setBaseAddrIPv6(var4.getInt(17));
                this._MetaData.setIndexBaseAddr(var4.getInt(21));
                this._MetaData.setIndexBaseAddrIPv6(var4.getInt(25));
                if(this._MetaData.getIndexBaseAddr() > 0) {
                    this._MetaData.setIndexed(true);
                }

                if(this._MetaData.getDBCountIPv6() == 0) {
                    this._MetaData.setOldBIN(true);
                } else if(this._MetaData.getIndexBaseAddrIPv6() > 0) {
                    this._MetaData.setIndexedIPv6(true);
                }

                int var5 = this._MetaData.getDBColumn();
                this._IPv4ColumnSize = var5 << 2;
                this._IPv6ColumnSize = 16 + (var5 - 1 << 2);
                int var6 = this._MetaData.getDBType();
                this.COUNTRY_POSITION_OFFSET = COUNTRY_POSITION[var6] != 0?COUNTRY_POSITION[var6] - 1 << 2:0;
                this.REGION_POSITION_OFFSET = REGION_POSITION[var6] != 0?REGION_POSITION[var6] - 1 << 2:0;
                this.CITY_POSITION_OFFSET = CITY_POSITION[var6] != 0?CITY_POSITION[var6] - 1 << 2:0;
                this.ISP_POSITION_OFFSET = ISP_POSITION[var6] != 0?ISP_POSITION[var6] - 1 << 2:0;
                this.DOMAIN_POSITION_OFFSET = DOMAIN_POSITION[var6] != 0?DOMAIN_POSITION[var6] - 1 << 2:0;
                this.ZIPCODE_POSITION_OFFSET = ZIPCODE_POSITION[var6] != 0?ZIPCODE_POSITION[var6] - 1 << 2:0;
                this.LATITUDE_POSITION_OFFSET = LATITUDE_POSITION[var6] != 0?LATITUDE_POSITION[var6] - 1 << 2:0;
                this.LONGITUDE_POSITION_OFFSET = LONGITUDE_POSITION[var6] != 0?LONGITUDE_POSITION[var6] - 1 << 2:0;
                this.TIMEZONE_POSITION_OFFSET = TIMEZONE_POSITION[var6] != 0?TIMEZONE_POSITION[var6] - 1 << 2:0;
                this.NETSPEED_POSITION_OFFSET = NETSPEED_POSITION[var6] != 0?NETSPEED_POSITION[var6] - 1 << 2:0;
                this.IDDCODE_POSITION_OFFSET = IDDCODE_POSITION[var6] != 0?IDDCODE_POSITION[var6] - 1 << 2:0;
                this.AREACODE_POSITION_OFFSET = AREACODE_POSITION[var6] != 0?AREACODE_POSITION[var6] - 1 << 2:0;
                this.WEATHERSTATIONCODE_POSITION_OFFSET = WEATHERSTATIONCODE_POSITION[var6] != 0?WEATHERSTATIONCODE_POSITION[var6] - 1 << 2:0;
                this.WEATHERSTATIONNAME_POSITION_OFFSET = WEATHERSTATIONNAME_POSITION[var6] != 0?WEATHERSTATIONNAME_POSITION[var6] - 1 << 2:0;
                this.MCC_POSITION_OFFSET = MCC_POSITION[var6] != 0?MCC_POSITION[var6] - 1 << 2:0;
                this.MNC_POSITION_OFFSET = MNC_POSITION[var6] != 0?MNC_POSITION[var6] - 1 << 2:0;
                this.MOBILEBRAND_POSITION_OFFSET = MOBILEBRAND_POSITION[var6] != 0?MOBILEBRAND_POSITION[var6] - 1 << 2:0;
                this.ELEVATION_POSITION_OFFSET = ELEVATION_POSITION[var6] != 0?ELEVATION_POSITION[var6] - 1 << 2:0;
                this.USAGETYPE_POSITION_OFFSET = USAGETYPE_POSITION[var6] != 0?USAGETYPE_POSITION[var6] - 1 << 2:0;
                this.COUNTRY_ENABLED = COUNTRY_POSITION[var6] != 0;
                this.REGION_ENABLED = REGION_POSITION[var6] != 0;
                this.CITY_ENABLED = CITY_POSITION[var6] != 0;
                this.ISP_ENABLED = ISP_POSITION[var6] != 0;
                this.LATITUDE_ENABLED = LATITUDE_POSITION[var6] != 0;
                this.LONGITUDE_ENABLED = LONGITUDE_POSITION[var6] != 0;
                this.DOMAIN_ENABLED = DOMAIN_POSITION[var6] != 0;
                this.ZIPCODE_ENABLED = ZIPCODE_POSITION[var6] != 0;
                this.TIMEZONE_ENABLED = TIMEZONE_POSITION[var6] != 0;
                this.NETSPEED_ENABLED = NETSPEED_POSITION[var6] != 0;
                this.IDDCODE_ENABLED = IDDCODE_POSITION[var6] != 0;
                this.AREACODE_ENABLED = AREACODE_POSITION[var6] != 0;
                this.WEATHERSTATIONCODE_ENABLED = WEATHERSTATIONCODE_POSITION[var6] != 0;
                this.WEATHERSTATIONNAME_ENABLED = WEATHERSTATIONNAME_POSITION[var6] != 0;
                this.MCC_ENABLED = MCC_POSITION[var6] != 0;
                this.MNC_ENABLED = MNC_POSITION[var6] != 0;
                this.MOBILEBRAND_ENABLED = MOBILEBRAND_POSITION[var6] != 0;
                this.ELEVATION_ENABLED = ELEVATION_POSITION[var6] != 0;
                this.USAGETYPE_ENABLED = USAGETYPE_POSITION[var6] != 0;
                if(this._MetaData.getIndexed()) {
                    MappedByteBuffer var7 = var3.map(MapMode.READ_ONLY, (long)(this._MetaData.getIndexBaseAddr() - 1), (long)(this._MetaData.getBaseAddr() - this._MetaData.getIndexBaseAddr()));
                    var7.order(ByteOrder.LITTLE_ENDIAN);
                    int var8 = 0;

                    int var9;
                    for(var9 = 0; var9 < this._IndexArrayIPv4.length; ++var9) {
                        this._IndexArrayIPv4[var9][0] = var7.getInt(var8);
                        this._IndexArrayIPv4[var9][1] = var7.getInt(var8 + 4);
                        var8 += 8;
                    }

                    if(this._MetaData.getIndexedIPv6()) {
                        for(var9 = 0; var9 < this._IndexArrayIPv6.length; ++var9) {
                            this._IndexArrayIPv6[var9][0] = var7.getInt(var8);
                            this._IndexArrayIPv6[var9][1] = var7.getInt(var8 + 4);
                            var8 += 8;
                        }
                    }
                }

                if(this.UseMemoryMappedFile) {
                    this.CreateMappedBytes(var3);
                } else {
                    this.DestroyMappedBytes();
                }

                var1 = true;
            }
        } catch (IOException var13) {
            throw var13;
        } finally {
            if(var2 != null) {
                var2.close();
                var2 = null;
            }

        }

        return var1;
    }

    /** @deprecated */
    protected void finalize() throws Throwable {
        super.finalize();
    }

    public IPResult IPQuery(String var1) throws IOException {
        var1 = var1.trim();
        IPResult var2 = new IPResult(var1);
        RandomAccessFile var3 = null;
        MappedByteBuffer var4 = null;

        IPResult var5;
        try {
            if(var1 != null && var1.length() != 0) {
                boolean var6 = false;
                boolean var7 = false;
                boolean var8 = false;
                boolean var9 = false;
                int var10 = 0;
                boolean var11 = false;
                boolean var12 = false;
                int var13 = 0;
                BigInteger var14 = BigInteger.ZERO;
                long var15 = 0L;
                long var17 = 0L;
                boolean var20 = false;

                BigInteger var40;
                int var43;
                try {
                    BigInteger[] var19 = this.ip2no(var1);
                    var43 = var19[0].intValue();
                    var40 = var19[1];
                    int var42 = var19[2].intValue();
                    if(var42 == 6) {
                        String[] var21 = this.ExpandIPv6(var1, var43);
                        var2.ip_address = var21[0];
                        var43 = Integer.parseInt(var21[1]);
                    }
                } catch (UnknownHostException var37) {
                    var2.status = "INVALID_IP_ADDRESS";
                    IPResult var23 = var2;
                    return var23;
                }

                this.checkLicense();
                var2.delay = this.delay();
                long var22 = 0L;
                long var24 = 0L;
                long var26 = 0L;
                long var28 = 0L;
                BigInteger var30 = BigInteger.ZERO;
                BigInteger var31 = BigInteger.ZERO;
                IPResult var32;
                if(this._MetaData == null && !this.LoadBIN()) {
                    var2.status = "MISSING_FILE";
                    var32 = var2;
                    return var32;
                }

                int var44 = this._MetaData.getDBType();
                int var45 = this._MetaData.getDBColumn();
                if(this.UseMemoryMappedFile) {
                    if(this._IPv4Buffer == null || !this._MetaData.getOldBIN() && this._IPv6Buffer == null || this._MapDataBuffer == null) {
                        this.CreateMappedBytes();
                    }
                } else {
                    this.DestroyMappedBytes();
                    var3 = new RandomAccessFile(this.IPDatabasePath, "r");
                    if(var3 == null) {
                        var2.status = "MISSING_FILE";
                        var32 = var2;
                        return var32;
                    }
                }

                int var41;
                int var46;
                if(var43 == 4) {
                    var14 = MAX_IPV4_RANGE;
                    var24 = (long)this._MetaData.getDBCount();
                    if(this.UseMemoryMappedFile) {
                        var4 = this._IPv4Buffer;
                        var13 = var4.capacity();
                    } else {
                        var10 = this._MetaData.getBaseAddr();
                    }

                    var46 = this._IPv4ColumnSize;
                    if(this._MetaData.getIndexed()) {
                        var41 = var40.shiftRight(16).intValue();
                        var22 = (long)this._IndexArrayIPv4[var41][0];
                        var24 = (long)this._IndexArrayIPv4[var41][1];
                    }
                } else {
                    if(this._MetaData.getOldBIN()) {
                        var2.status = "IPV6_NOT_SUPPORTED";
                        var32 = var2;
                        return var32;
                    }

                    var14 = MAX_IPV6_RANGE;
                    var24 = (long)this._MetaData.getDBCountIPv6();
                    if(this.UseMemoryMappedFile) {
                        var4 = this._IPv6Buffer;
                        var13 = var4.capacity();
                    } else {
                        var10 = this._MetaData.getBaseAddrIPv6();
                    }

                    var46 = this._IPv6ColumnSize;
                    if(this._MetaData.getIndexedIPv6()) {
                        var41 = var40.shiftRight(112).intValue();
                        var22 = (long)this._IndexArrayIPv6[var41][0];
                        var24 = (long)this._IndexArrayIPv6[var41][1];
                    }
                }

                if(var40.compareTo(var14) == 0) {
                    var40 = var40.subtract(BigInteger.ONE);
                }

                while(true) {
                    if(var22 <= var24) {
                        var26 = (var22 + var24) / 2L;
                        var15 = (long)var10 + var26 * (long)var46;
                        var17 = var15 + (long)var46;
                        if(this.UseMemoryMappedFile) {
                            var20 = var17 >= (long)var13;
                        }

                        var30 = this.read32or128(var15, var43, var4, var3);
                        var31 = var20?BigInteger.ZERO:this.read32or128(var17, var43, var4, var3);
                        if(var40.compareTo(var30) < 0 || var40.compareTo(var31) >= 0) {
                            if(var40.compareTo(var30) < 0) {
                                var24 = var26 - 1L;
                            } else {
                                var22 = var26 + 1L;
                            }
                            continue;
                        }

                        if(var43 == 6) {
                            var15 += 12L;
                        }

                        if(this.COUNTRY_ENABLED) {
                            var28 = this.read32(var15 + (long)this.COUNTRY_POSITION_OFFSET, var4, var3).longValue();
                            var2.country_short = this.readStr(var28, var3);
                            var28 += 3L;
                            var2.country_long = this.readStr(var28, var3);
                        } else {
                            var2.country_short = "Not_Supported";
                            var2.country_long = "Not_Supported";
                        }

                        if(this.REGION_ENABLED) {
                            var28 = this.read32(var15 + (long)this.REGION_POSITION_OFFSET, var4, var3).longValue();
                            var2.region = this.readStr(var28, var3);
                        } else {
                            var2.region = "Not_Supported";
                        }

                        if(this.CITY_ENABLED) {
                            var28 = this.read32(var15 + (long)this.CITY_POSITION_OFFSET, var4, var3).longValue();
                            var2.city = this.readStr(var28, var3);
                        } else {
                            var2.city = "Not_Supported";
                        }

                        if(this.ISP_ENABLED) {
                            var28 = this.read32(var15 + (long)this.ISP_POSITION_OFFSET, var4, var3).longValue();
                            var2.isp = this.readStr(var28, var3);
                        } else {
                            var2.isp = "Not_Supported";
                        }

                        if(this.LATITUDE_ENABLED) {
                            var28 = var15 + (long)this.LATITUDE_POSITION_OFFSET;
                            var2.latitude = Float.parseFloat(this.setDecimalPlaces(this.readFloat(var28, var4, var3)));
                        } else {
                            var2.latitude = 0.0F;
                        }

                        if(this.LONGITUDE_ENABLED) {
                            var28 = var15 + (long)this.LONGITUDE_POSITION_OFFSET;
                            var2.longitude = Float.parseFloat(this.setDecimalPlaces(this.readFloat(var28, var4, var3)));
                        } else {
                            var2.longitude = 0.0F;
                        }

                        if(this.DOMAIN_ENABLED) {
                            var28 = this.read32(var15 + (long)this.DOMAIN_POSITION_OFFSET, var4, var3).longValue();
                            var2.domain = this.readStr(var28, var3);
                        } else {
                            var2.domain = "Not_Supported";
                        }

                        if(this.ZIPCODE_ENABLED) {
                            var28 = this.read32(var15 + (long)this.ZIPCODE_POSITION_OFFSET, var4, var3).longValue();
                            var2.zipcode = this.readStr(var28, var3);
                        } else {
                            var2.zipcode = "Not_Supported";
                        }

                        if(this.TIMEZONE_ENABLED) {
                            var28 = this.read32(var15 + (long)this.TIMEZONE_POSITION_OFFSET, var4, var3).longValue();
                            var2.timezone = this.readStr(var28, var3);
                        } else {
                            var2.timezone = "Not_Supported";
                        }

                        if(this.NETSPEED_ENABLED) {
                            var28 = this.read32(var15 + (long)this.NETSPEED_POSITION_OFFSET, var4, var3).longValue();
                            var2.netspeed = this.readStr(var28, var3);
                        } else {
                            var2.netspeed = "Not_Supported";
                        }

                        if(this.IDDCODE_ENABLED) {
                            var28 = this.read32(var15 + (long)this.IDDCODE_POSITION_OFFSET, var4, var3).longValue();
                            var2.iddcode = this.readStr(var28, var3);
                        } else {
                            var2.iddcode = "Not_Supported";
                        }

                        if(this.AREACODE_ENABLED) {
                            var28 = this.read32(var15 + (long)this.AREACODE_POSITION_OFFSET, var4, var3).longValue();
                            var2.areacode = this.readStr(var28, var3);
                        } else {
                            var2.areacode = "Not_Supported";
                        }

                        if(this.WEATHERSTATIONCODE_ENABLED) {
                            var28 = this.read32(var15 + (long)this.WEATHERSTATIONCODE_POSITION_OFFSET, var4, var3).longValue();
                            var2.weatherstationcode = this.readStr(var28, var3);
                        } else {
                            var2.weatherstationcode = "Not_Supported";
                        }

                        if(this.WEATHERSTATIONNAME_ENABLED) {
                            var28 = this.read32(var15 + (long)this.WEATHERSTATIONNAME_POSITION_OFFSET, var4, var3).longValue();
                            var2.weatherstationname = this.readStr(var28, var3);
                        } else {
                            var2.weatherstationname = "Not_Supported";
                        }

                        if(this.MCC_ENABLED) {
                            var28 = this.read32(var15 + (long)this.MCC_POSITION_OFFSET, var4, var3).longValue();
                            var2.mcc = this.readStr(var28, var3);
                        } else {
                            var2.mcc = "Not_Supported";
                        }

                        if(this.MNC_ENABLED) {
                            var28 = this.read32(var15 + (long)this.MNC_POSITION_OFFSET, var4, var3).longValue();
                            var2.mnc = this.readStr(var28, var3);
                        } else {
                            var2.mnc = "Not_Supported";
                        }

                        if(this.MOBILEBRAND_ENABLED) {
                            var28 = this.read32(var15 + (long)this.MOBILEBRAND_POSITION_OFFSET, var4, var3).longValue();
                            var2.mobilebrand = this.readStr(var28, var3);
                        } else {
                            var2.mobilebrand = "Not_Supported";
                        }

                        if(this.ELEVATION_ENABLED) {
                            var28 = this.read32(var15 + (long)this.ELEVATION_POSITION_OFFSET, var4, var3).longValue();
                            var2.elevation = this.convertFloat(this.readStr(var28, var3));
                        } else {
                            var2.elevation = 0.0F;
                        }

                        if(this.USAGETYPE_ENABLED) {
                            var28 = this.read32(var15 + (long)this.USAGETYPE_POSITION_OFFSET, var4, var3).longValue();
                            var2.usagetype = this.readStr(var28, var3);
                        } else {
                            var2.usagetype = "Not_Supported";
                        }

                        var2.status = "OK";
                    }

                    var32 = var2;
                    return var32;
                }
            }

            var2.status = "EMPTY_IP_ADDRESS";
            var5 = var2;
        } catch (IOException var38) {
            throw var38;
        } finally {
            if(var3 != null) {
                var3.close();
                var3 = null;
            }

        }

        return var5;
    }

    private String[] ExpandIPv6(String var1, int var2) {
        String var7 = var1.toUpperCase();
        String var8 = String.valueOf(var2);
        Matcher var9;
        String var10;
        String[] var11;
        StringBuffer var13;
        int var14;
        int var17;
        if(var2 == 4) {
            if(pattern4.matcher(var7).matches()) {
                var7 = var7.replaceAll("::", "0000:0000:0000:0000:0000:");
            } else {
                var9 = pattern5.matcher(var7);
                if(var9.matches()) {
                    var10 = var9.group(1);
                    var11 = var10.replaceAll("^:+", "").replaceAll(":+$", "").split(":");
                    int var12 = var11.length;
                    var13 = new StringBuffer(32);

                    for(var14 = 0; var14 < var12; ++var14) {
                        String var15 = var11[var14];
                        var13.append("0000".substring(var15.length()) + var15);
                    }

                    long var34 = (new BigInteger(var13.toString(), 16)).longValue();
                    long[] var16 = new long[]{0L, 0L, 0L, 0L};

                    for(var17 = 0; var17 < 4; ++var17) {
                        var16[var17] = var34 & 255L;
                        var34 >>= 8;
                    }

                    var7 = var7.replaceAll(var10 + "$", ":" + var16[3] + "." + var16[2] + "." + var16[1] + "." + var16[0]);
                    var7 = var7.replaceAll("::", "0000:0000:0000:0000:0000:");
                }
            }
        } else if(var2 == 6) {
            if(var7.equals("::")) {
                var7 = var7 + "0.0.0.0";
                var7 = var7.replaceAll("::", "0000:0000:0000:0000:0000:FFFF:");
                var8 = "4";
            } else {
                var9 = pattern4.matcher(var7);
                String var30;
                String[] var31;
                int var35;
                int var37;
                if(var9.matches()) {
                    var10 = var9.group(1);
                    var30 = var9.group(2);
                    var31 = var30.split("\\.");
                    int[] var32 = new int[4];
                    var14 = var32.length;

                    for(var35 = 0; var35 < var14; ++var35) {
                        var32[var35] = Integer.parseInt(var31[var35]);
                    }

                    var35 = (var32[0] << 8) + var32[1];
                    var37 = (var32[2] << 8) + var32[3];
                    String var41 = Integer.toHexString(var35);
                    String var18 = Integer.toHexString(var37);
                    StringBuffer var19 = new StringBuffer(var10.length() + 9);
                    var19.append(var10);
                    var19.append("0000".substring(var41.length()));
                    var19.append(var41);
                    var19.append(":");
                    var19.append("0000".substring(var18.length()));
                    var19.append(var18);
                    var7 = var19.toString().toUpperCase();
                    String[] var20 = var7.split("::");
                    String[] var21 = var20[0].split(":");
                    StringBuffer var22 = new StringBuffer(40);
                    StringBuffer var23 = new StringBuffer(40);
                    StringBuffer var24 = new StringBuffer(40);
                    var14 = var21.length;
                    int var25 = 0;

                    int var26;
                    for(var26 = 0; var26 < var14; ++var26) {
                        if(var21[var26].length() > 0) {
                            ++var25;
                            var22.append("0000".substring(var21[var26].length()));
                            var22.append(var21[var26]);
                            var22.append(":");
                        }
                    }

                    int var27;
                    if(var20.length > 1) {
                        String[] var46 = var20[1].split(":");
                        var14 = var46.length;

                        for(var27 = 0; var27 < var14; ++var27) {
                            if(var46[var27].length() > 0) {
                                ++var25;
                                var23.append("0000".substring(var46[var27].length()));
                                var23.append(var46[var27]);
                                var23.append(":");
                            }
                        }
                    }

                    var26 = 8 - var25;
                    if(var26 == 6) {
                        for(var27 = 1; var27 < var26; ++var27) {
                            var24.append("0000");
                            var24.append(":");
                        }

                        var24.append("FFFF:");
                        var24.append(var30);
                        var8 = "4";
                        var7 = var24.toString();
                    } else {
                        for(var27 = 0; var27 < var26; ++var27) {
                            var24.append("0000");
                            var24.append(":");
                        }

                        var22.append(var24).append(var23);
                        var7 = var22.toString().replaceAll(":$", "");
                    }
                } else {
                    Matcher var29 = pattern6.matcher(var7);
                    StringBuffer var36;
                    int var43;
                    if(var29.matches()) {
                        var30 = var29.group(1);
                        var31 = var30.replaceAll("^:+", "").replaceAll(":+$", "").split(":");
                        int var33 = var31.length;
                        var36 = new StringBuffer(32);

                        for(var35 = 0; var35 < var33; ++var35) {
                            String var38 = var31[var35];
                            var36.append("0000".substring(var38.length()) + var38);
                        }

                        long var39 = (new BigInteger(var36.toString(), 16)).longValue();
                        long[] var42 = new long[]{0L, 0L, 0L, 0L};

                        for(var43 = 0; var43 < 4; ++var43) {
                            var42[var43] = var39 & 255L;
                            var39 >>= 8;
                        }

                        var7 = var7.replaceAll(var30 + "$", ":" + var42[3] + "." + var42[2] + "." + var42[1] + "." + var42[0]);
                        var7 = var7.replaceAll("::", "0000:0000:0000:0000:0000:FFFF:");
                        var8 = "4";
                    } else {
                        var11 = var7.split("::");
                        var31 = var11[0].split(":");
                        var13 = new StringBuffer(40);
                        var36 = new StringBuffer(40);
                        StringBuffer var40 = new StringBuffer(40);
                        var37 = var31.length;
                        var17 = 0;

                        for(var43 = 0; var43 < var37; ++var43) {
                            if(var31[var43].length() > 0) {
                                ++var17;
                                var13.append("0000".substring(var31[var43].length()));
                                var13.append(var31[var43]);
                                var13.append(":");
                            }
                        }

                        int var44;
                        if(var11.length > 1) {
                            String[] var45 = var11[1].split(":");
                            var37 = var45.length;

                            for(var44 = 0; var44 < var37; ++var44) {
                                if(var45[var44].length() > 0) {
                                    ++var17;
                                    var36.append("0000".substring(var45[var44].length()));
                                    var36.append(var45[var44]);
                                    var36.append(":");
                                }
                            }
                        }

                        var43 = 8 - var17;

                        for(var44 = 0; var44 < var43; ++var44) {
                            var40.append("0000");
                            var40.append(":");
                        }

                        var13.append(var40).append(var36);
                        var7 = var13.toString().replaceAll(":$", "");
                    }
                }
            }
        }

        String[] var28 = new String[]{var7, var8};
        return var28;
    }

    private float convertFloat(String var1) {
        try {
            return Float.parseFloat(var1);
        } catch (NumberFormatException var3) {
            return 0.0F;
        }
    }

    private void reverse(byte[] var1) {
        if(var1 != null) {
            int var2 = 0;

            for(int var3 = var1.length - 1; var3 > var2; ++var2) {
                byte var4 = var1[var3];
                var1[var3] = var1[var2];
                var1[var2] = var4;
                --var3;
            }

        }
    }

    private BigInteger read32or128(long var1, int var3, MappedByteBuffer var4, RandomAccessFile var5) throws IOException {
        return var3 == 4?this.read32(var1, var4, var5):(var3 == 6?this.read128(var1, var4, var5):BigInteger.ZERO);
    }

    private BigInteger read128(long var1, MappedByteBuffer var3, RandomAccessFile var4) throws IOException {
        BigInteger var5 = BigInteger.ZERO;
        byte[] var7 = new byte[16];
        int var8;
        if(this.UseMemoryMappedFile) {
            for(var8 = 0; var8 < 16; ++var8) {
                var7[var8] = var3.get((int)var1 + var8);
            }
        } else {
            var4.seek(var1 - 1L);

            for(var8 = 0; var8 < 16; ++var8) {
                var7[var8] = var4.readByte();
            }
        }

        this.reverse(var7);
        var5 = new BigInteger(1, var7);
        return var5;
    }

    private BigInteger read32(long var1, MappedByteBuffer var3, RandomAccessFile var4) throws IOException {
        if(this.UseMemoryMappedFile) {
            return BigInteger.valueOf((long)var3.getInt((int)var1) & 4294967295L);
        } else {
            var4.seek(var1 - 1L);
            byte[] var6 = new byte[4];

            for(int var7 = 0; var7 < 4; ++var7) {
                var6[var7] = var4.readByte();
            }

            this.reverse(var6);
            return new BigInteger(1, var6);
        }
    }

    private String readStr(long var1, RandomAccessFile var3) throws IOException {
        Object var5 = null;
        int var6;
        char[] var10;
        if(this.UseMemoryMappedFile) {
            var1 -= this._MapDataOffset;
            byte var4 = this._MapDataBuffer.get((int)var1);

            try {
                var10 = new char[var4];

                for(var6 = 0; var6 < var4; ++var6) {
                    var10[var6] = (char)this._MapDataBuffer.get((int)var1 + 1 + var6);
                }
            } catch (NegativeArraySizeException var8) {
                return null;
            }
        } else {
            var3.seek(var1);
            int var9 = var3.read();

            try {
                var10 = new char[var9];

                for(var6 = 0; var6 < var9; ++var6) {
                    var10[var6] = (char)var3.read();
                }
            } catch (NegativeArraySizeException var7) {
                return null;
            }
        }

        return String.copyValueOf(var10);
    }

    private float readFloat(long var1, MappedByteBuffer var3, RandomAccessFile var4) throws IOException {
        if(this.UseMemoryMappedFile) {
            return var3.getFloat((int)var1);
        } else {
            var4.seek(var1 - 1L);
            int[] var6 = new int[4];

            for(int var7 = 0; var7 < 4; ++var7) {
                var6[var7] = var4.read();
            }

            return Float.intBitsToFloat(var6[3] << 24 | var6[2] << 16 | var6[1] << 8 | var6[0]);
        }
    }

    private String setDecimalPlaces(float var1) {
        Locale var2 = Locale.getDefault();
        NumberFormat var3 = NumberFormat.getNumberInstance(var2);
        DecimalFormat var4 = (DecimalFormat)var3;
        var4.applyPattern("###.######");
        String var5 = var4.format((double)var1).replace(',', '.');
        return var5;
    }

    private BigInteger[] ip2no(String var1) throws UnknownHostException {
        BigInteger var2 = BigInteger.ZERO;
        BigInteger var3 = BigInteger.ZERO;
        BigInteger var4 = new BigInteger("4");
        if(pattern.matcher(var1).matches()) {
            var2 = new BigInteger("4");
            var3 = new BigInteger(String.valueOf(this.ipv4no(var1)));
        } else {
            if(pattern2.matcher(var1).matches() || pattern3.matcher(var1).matches()) {
                throw new UnknownHostException();
            }

            var4 = new BigInteger("6");
            InetAddress var5 = InetAddress.getByName(var1);
            byte[] var6 = var5.getAddress();
            String var7 = "0";
            if(var5 instanceof Inet6Address) {
                var7 = "6";
            } else if(var5 instanceof Inet4Address) {
                var7 = "4";
            }

            var2 = new BigInteger(var7);
            var3 = new BigInteger(1, var6);
        }

        BigInteger[] var8 = new BigInteger[]{var2, var3, var4};
        return var8;
    }

    private long ipv4no(String var1) {
        String[] var2 = var1.split("\\.");
        long var3 = 0L;
        long var5 = 0L;

        for(int var7 = 3; var7 >= 0; --var7) {
            var5 = Long.parseLong(var2[3 - var7]);
            var3 |= var5 << (var7 << 3);
        }

        return var3;
    }

    private void checkLicense() {
        if(!this._alreadyCheckedKey) {
            if(this.IPLicensePath == null || this.IPLicensePath.length() == 0) {
                this.IPLicensePath = "license.key";
            }

            String var1 = "";
            String var2 = "";
            String var3 = "";

            try {
                BufferedReader var4 = new BufferedReader(new FileReader(this.IPLicensePath));
                if(!var4.ready()) {
                    var4.close();
                    throw new IOException();
                }

                if((var1 = var4.readLine()) != null && (var2 = var4.readLine()) != null) {
                    var3 = var4.readLine();
                }

                var4.close();
                if(var3 != null && var3.length() != 0) {
                    if(!var3.trim().equals(this.generateKey(var1 + var2))) {
                        this.gotdelay = true;
                    }
                } else if(!var2.trim().equals(this.generateKey(var1))) {
                    this.gotdelay = true;
                }

                this._alreadyCheckedKey = true;
            } catch (IOException var5) {
                this.gotdelay = true;
            }
        }

    }

    private boolean delay() {
        boolean var1 = false;
        if(this.gotdelay) {
            int var2 = 1 + (int)(Math.random() * 10.0D);

            try {
                if(var2 == 10) {
                    var1 = true;
                    Thread.sleep(5000L);
                }
            } catch (Exception var4) {
                System.out.println(var4);
            }
        }

        return var1;
    }

    private String generateKey(String var1) {
        String[] var5 = new String[2];
        String[] var6 = new String[62];
        String[] var7 = new String[200];
        StringBuffer var12 = new StringBuffer(50);

        int var9;
        try {
            if(var1.length() > 20) {
                var5[0] = var1.substring(1, 20);
            } else {
                var5[0] = var1;
            }

            var5[1] = "Hexasoft";
            int var8 = 0;

            for(var9 = 48; var9 <= 57; ++var9) {
                var6[var8] = String.valueOf((char)var9);
                ++var8;
            }

            for(var9 = 65; var9 <= 90; ++var9) {
                var6[var8] = String.valueOf((char)var9);
                ++var8;
            }

            for(var9 = 97; var9 <= 122; ++var9) {
                var6[var8] = String.valueOf((char)var9);
                ++var8;
            }

            String[] var13 = new String[]{String.valueOf(this.Asc("7")), String.valueOf(this.Asc("0")), String.valueOf(this.Asc("a")), String.valueOf(this.Asc("1")), String.valueOf(this.Asc("b")), String.valueOf(this.Asc("2")), String.valueOf(this.Asc("c")), String.valueOf(this.Asc("3")), String.valueOf(this.Asc("d")), String.valueOf(this.Asc("4")), String.valueOf(this.Asc("e")), String.valueOf(this.Asc("5")), String.valueOf(this.Asc("f")), String.valueOf(this.Asc("6")), String.valueOf(this.Asc("g"))};

            for(var9 = 0; var9 < 200; ++var9) {
                var7[var9] = var13[var9 % 15];
            }

            var8 = 0;
            boolean var14 = false;

            int var10;
            for(var10 = 0; var10 <= 1; ++var10) {
                for(int var11 = 0; var11 <= 11; ++var11) {
                    int var16 = var5[var10].length();

                    for(var9 = 0; var9 < var16; ++var9) {
                        var7[var8 % 200] = var7[var8 % 200] + String.valueOf(this.Asc(var5[var10].substring(var9, var9 + 1)));
                        ++var8;
                    }

                    var7[var8 % 200] = String.valueOf(Long.parseLong(var7[var8 % 200]) >> 1);
                    ++var8;
                }
            }

            for(var10 = 0; var10 <= 15; ++var10) {
                var12.append(var6[Integer.parseInt(var7[var10]) % 62]);
            }
        } catch (Exception var15) {
            System.out.println(var15);
        }

        if(var12.length() == 0) {
            return "error";
        } else {
            for(var9 = 0; var9 < 3; ++var9) {
                var12.insert((var9 + 1 << 2) + var9, '-');
            }

            return var12.toString().toUpperCase();
        }
    }

    private int Asc(String var1) {
        try {
            String var2 = " !\"#$%&'()*+'-./0123456789:;<=>?@";
            String var3 = "abcdefghijklmnopqrstuvwxyz";
            var2 = var2 + var3.toUpperCase();
            var2 = var2 + "[\\]^_`";
            var2 = var2 + var3;
            var2 = var2 + "{|}~";
            int var4 = var2.indexOf(var1);
            if(var4 > -1) {
                var4 += 32;
                return var4;
            }
        } catch (Exception var5) {
            System.out.println(var5);
        }

        return 0;
    }
}
