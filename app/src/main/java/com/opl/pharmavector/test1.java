package com.opl.pharmavector;

/**
 * Created by onik on 3/19/2018.
 */


public class test1 {
    //private variables
    String _SL;
    String _id;
    String _ORD_NO;
    String _P_CODE;
    String _ORD_QNTY;
    String _PROD_RATE;
    String _PROD_VAT;
    String _PRODUCT_NAME;


    public test1(){

    }
    // constructor


    public test1(String SL,String PRODUCT_CODE, String PRODUCT_QUANTITY, String PRODUCT_NAME, String PRODUCT_RATE,String PRODUCT_VAT){
        this._SL = SL;
        this._P_CODE = PRODUCT_CODE;
        this. _ORD_QNTY = PRODUCT_QUANTITY;
        this._PRODUCT_NAME = PRODUCT_NAME;
        this._PROD_RATE = PRODUCT_RATE;
        this._PROD_VAT = PRODUCT_VAT;
    }



    public test1(String PRODUCT_CODE, String PRODUCT_QUANTITY){
        this._P_CODE = PRODUCT_CODE;
        this. _ORD_QNTY  = PRODUCT_QUANTITY;

    }







    public String getproductsl(){ return this._SL;}

    public String getproductcode(){ return  this._P_CODE ;}

    public String getproductquant(){ return  this. _ORD_QNTY;}

    public String getproductordno(){
        return this._ORD_NO;
    }
    public String getproductrate(){return this._PROD_RATE;}
    public String getproductvat(){return this._PROD_VAT;}
    public String getproductname(){
        return this._PRODUCT_NAME;
    }

}

