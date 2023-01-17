package com.opl.pharmavector;
public class Orditem {
    int _ordid;
    String _ordno;
    String _prodtcode;
    String _prodrate;
    String _ordquant;
    String _enterdate;
    String _prodvat;

    public Orditem( int ordid,String ordno, String prodtcode,String prodrate,String ordquant, String enterdate,String prodvat) {
        this._ordid = ordid;
        this._ordno = ordno;
        this._prodtcode = prodtcode;
        this._prodrate = prodrate;
        this._ordquant = ordquant;
        this._enterdate = enterdate;
        this._prodvat = prodvat;
    }
    public Orditem( String ordno, String prodtcode,String prodrate,String ordquant,String prodvat) {
        this._ordno = ordno;
        this._prodtcode = prodtcode;
        this._prodrate = prodrate;
        this._ordquant = ordquant;
        this._prodvat = prodvat;

    }





    public int getitemordid() {return  this._ordid;}

    public String getitemordno(){return this._ordno;}

    public String prodcode(){return  this._prodtcode;}
    public String prodrate(){return   this._prodrate;}
    public String ordquant(){return this._ordquant;}
    public String prodvat(){return this._prodvat;}




}