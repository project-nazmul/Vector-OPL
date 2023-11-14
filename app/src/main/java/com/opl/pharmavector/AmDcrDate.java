package com.opl.pharmavector;

public class AmDcrDate {
    int _id;
    String _cname;
    String _ccust;
    String _cmpo;

    public AmDcrDate(String cname, String ccust) {
        this._cname = cname;
        this._ccust = ccust;
    }

    public AmDcrDate( String cname, String ccust,String cmpo) {
        this._cname = cname;
        this._ccust = ccust;
        this._cmpo = cmpo;
    }

    public int getcid(){
        return this._id;
    }

    public String getCName(){
        return this._cname;
    }

    public String getCCUST(){
        return this._ccust;
    }

    public String getCMPO(){
        return this._cmpo;
    }
}
