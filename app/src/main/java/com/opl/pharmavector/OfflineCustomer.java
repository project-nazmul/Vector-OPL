package com.opl.pharmavector;

public class OfflineCustomer {
    int _id;
    String _cname;
    String _ccust;
    String _cmpo;

    public OfflineCustomer(int id, String cname, String ccust, String cmpo) {
        this._id = id;
        this._cname = cname;
        this._ccust = ccust;
        this._cmpo = cmpo;

    }

    public OfflineCustomer(String cname, String ccust, String cmpo) {
        this._cname = cname;
        this._ccust = ccust;
        this._cmpo = cmpo;

    }

    // getting ID
    public int getcid() {
        return this._id;
    }

    // getting first name
    public String getCName() {
        return this._cname;
    }

    // getting second name
    public String getCCUST() {
        return this._ccust;
    }

    // getting phone number
    public String getCMPO() {
        return this._cmpo;
    }


}