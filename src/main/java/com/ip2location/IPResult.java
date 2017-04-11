package com.ip2location;

public class IPResult {
    static final String NOT_SUPPORTED = "Not_Supported";
    String ip_address;
    String country_short;
    String country_long;
    String region;
    String city;
    String isp;
    float latitude;
    float longitude;
    String domain;
    String zipcode;
    String netspeed;
    String timezone;
    String iddcode;
    String areacode;
    String weatherstationcode;
    String weatherstationname;
    String mcc;
    String mnc;
    String mobilebrand;
    float elevation;
    String usagetype;
    String status;
    boolean delay = false;
    String version = "Version 8.0.2";

    IPResult(String var1) {
        this.ip_address = var1;
    }

    public String getCountryShort() {
        return this.country_short;
    }

    public String getCountryLong() {
        return this.country_long;
    }

    public String getRegion() {
        return this.region;
    }

    public String getCity() {
        return this.city;
    }

    public String getISP() {
        return this.isp;
    }

    public float getLatitude() {
        return this.latitude;
    }

    public float getLongitude() {
        return this.longitude;
    }

    public String getDomain() {
        return this.domain;
    }

    public String getZipCode() {
        return this.zipcode;
    }

    public String getTimeZone() {
        return this.timezone;
    }

    public String getNetSpeed() {
        return this.netspeed;
    }

    public String getIDDCode() {
        return this.iddcode;
    }

    public String getAreaCode() {
        return this.areacode;
    }

    public String getWeatherStationCode() {
        return this.weatherstationcode;
    }

    public String getWeatherStationName() {
        return this.weatherstationname;
    }

    public String getMCC() {
        return this.mcc;
    }

    public String getMNC() {
        return this.mnc;
    }

    public String getMobileBrand() {
        return this.mobilebrand;
    }

    public float getElevation() {
        return this.elevation;
    }

    public String getUsageType() {
        return this.usagetype;
    }

    public String getStatus() {
        return this.status;
    }

    public boolean getDelay() {
        return this.delay;
    }

    public String getVersion() {
        return this.version;
    }

    public String toString() {
        String var1 = System.getProperty("line.separator");
        StringBuffer var2 = new StringBuffer("IP2LocationRecord:" + var1);
        var2.append("\tIP Address = " + this.ip_address + var1);
        var2.append("\tCountry Short = " + this.country_short + var1);
        var2.append("\tCountry Long = " + this.country_long + var1);
        var2.append("\tRegion = " + this.region + var1);
        var2.append("\tCity = " + this.city + var1);
        var2.append("\tISP = " + this.isp + var1);
        var2.append("\tLatitude = " + this.latitude + var1);
        var2.append("\tLongitude = " + this.longitude + var1);
        var2.append("\tDomain = " + this.domain + var1);
        var2.append("\tZipCode = " + this.zipcode + var1);
        var2.append("\tTimeZone = " + this.timezone + var1);
        var2.append("\tNetSpeed = " + this.netspeed + var1);
        var2.append("\tIDDCode = " + this.iddcode + var1);
        var2.append("\tAreaCode = " + this.areacode + var1);
        var2.append("\tWeatherStationCode = " + this.weatherstationcode + var1);
        var2.append("\tWeatherStationName = " + this.weatherstationname + var1);
        var2.append("\tMCC = " + this.mcc + var1);
        var2.append("\tMNC = " + this.mnc + var1);
        var2.append("\tMobileBrand = " + this.mobilebrand + var1);
        var2.append("\tElevation = " + this.elevation + var1);
        var2.append("\tUsageType = " + this.usagetype + var1);
        return var2.toString();
    }
}
