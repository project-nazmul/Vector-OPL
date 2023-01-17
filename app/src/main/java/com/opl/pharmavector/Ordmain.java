package com.opl.pharmavector;

public class Ordmain {

    int _id;
    String _ordno;
    String _custcode;
    String _mpocode;
    String _orddate;
    String _enterdate;
    String _delidate;
    String _delitime;
    String _shiftstat;
    String _paymode;
    // Empty constructor

    public Ordmain( int id,String ordno, String custcode,String mpocode,String orddate, String enterdate,String delidate,String deltime,String shiftstat,String paymode){
        this._id = id;
        this._ordno = ordno;
        this._custcode=custcode;
        this._mpocode=mpocode;
        this._orddate = orddate;
        this._enterdate=enterdate;
        this._delidate=delidate;
        this._delitime = deltime;
        this._shiftstat=shiftstat;
        this._paymode=paymode;
    }

    public Ordmain( String ordno, String custcode,String mpocode,String orddate,String delidate,String deltime,String shiftstat,String paymode){
        this._ordno = ordno;
        this._custcode=custcode;
        this._mpocode=mpocode;
        this._orddate = orddate;
        this._delidate=delidate;
        this._delitime = deltime;
        this._shiftstat=shiftstat;
        this._paymode=paymode;
    }

    public int getordid() {
        return  this._id;
    }
    public String getordno(){
        return this._ordno;
    }
    public String getcustcode(){
        return this._custcode;
    }
    public String getmpocode(){

        return this._mpocode;
    }
    public String getdelidate(){
        return this._delidate;
    }

    public String getdelitime(){
        return this._delitime;
    }

    public String getshiftstat(){
        return this._shiftstat;
    }

    public String getpaymode(){
        return this._paymode;
    }

}













