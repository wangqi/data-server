package com.ip2location;

class MetaData {
    private int _BaseAddr = 0;
    private int _DBCount = 0;
    private int _DBColumn = 0;
    private int _DBType = 0;
    private int _DBDay = 1;
    private int _DBMonth = 1;
    private int _DBYear = 1;
    private int _BaseAddrIPv6 = 0;
    private int _DBCountIPv6 = 0;
    private boolean _OldBIN = false;
    private boolean _Indexed = false;
    private boolean _IndexedIPv6 = false;
    private int _IndexBaseAddr = 0;
    private int _IndexBaseAddrIPv6 = 0;

    MetaData() {
    }

    int getBaseAddr() {
        return this._BaseAddr;
    }

    int getDBCount() {
        return this._DBCount;
    }

    int getDBColumn() {
        return this._DBColumn;
    }

    int getDBType() {
        return this._DBType;
    }

    int getDBDay() {
        return this._DBDay;
    }

    int getDBMonth() {
        return this._DBMonth;
    }

    int getDBYear() {
        return this._DBYear;
    }

    int getBaseAddrIPv6() {
        return this._BaseAddrIPv6;
    }

    int getDBCountIPv6() {
        return this._DBCountIPv6;
    }

    boolean getOldBIN() {
        return this._OldBIN;
    }

    boolean getIndexed() {
        return this._Indexed;
    }

    boolean getIndexedIPv6() {
        return this._IndexedIPv6;
    }

    int getIndexBaseAddr() {
        return this._IndexBaseAddr;
    }

    int getIndexBaseAddrIPv6() {
        return this._IndexBaseAddrIPv6;
    }

    void setBaseAddr(int var1) {
        this._BaseAddr = var1;
    }

    void setDBCount(int var1) {
        this._DBCount = var1;
    }

    void setDBColumn(int var1) {
        this._DBColumn = var1;
    }

    void setDBType(int var1) {
        this._DBType = var1;
    }

    void setDBDay(int var1) {
        this._DBDay = var1;
    }

    void setDBMonth(int var1) {
        this._DBMonth = var1;
    }

    void setDBYear(int var1) {
        this._DBYear = var1;
    }

    void setBaseAddrIPv6(int var1) {
        this._BaseAddrIPv6 = var1;
    }

    void setDBCountIPv6(int var1) {
        this._DBCountIPv6 = var1;
    }

    void setOldBIN(boolean var1) {
        this._OldBIN = var1;
    }

    void setIndexed(boolean var1) {
        this._Indexed = var1;
    }

    void setIndexedIPv6(boolean var1) {
        this._IndexedIPv6 = var1;
    }

    void setIndexBaseAddr(int var1) {
        this._IndexBaseAddr = var1;
    }

    void setIndexBaseAddrIPv6(int var1) {
        this._IndexBaseAddrIPv6 = var1;
    }
}
