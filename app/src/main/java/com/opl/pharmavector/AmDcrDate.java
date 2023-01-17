package com.opl.pharmavector;



public class AmDcrDate {

    //private variables
    int _id;
    String _cname;
    String _ccust;
    String _cmpo;


    // Empty constructor
    public AmDcrDate(String cname, String ccust){


        this._cname = cname;
        this._ccust = ccust;


    }
    // constructor


    // constructor
    public AmDcrDate( String cname, String ccust,String cmpo){


        this._cname = cname;
        this._ccust = ccust;
        this._cmpo = cmpo;

    }

    // getting ID
    public int getcid(){
        return this._id;
    }


    // getting first name
    public String getCName(){
        return this._cname;
    }




    // getting second name
    public String getCCUST(){
        return this._ccust;
    }



    // getting phone number
    public String getCMPO(){
        return this._cmpo;
    }



}
