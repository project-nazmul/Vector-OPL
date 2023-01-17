package com.opl.pharmavector;

public class Product {
    String _PRODUCT_CODE;
    String _PRODUCT_NAME;
    String _PRODUCT_RATE;
    String _PRODUCT_VAT;

    public Product(){

    }


    public Product(String PRODUCT_CODE, String PRODUCT_NAME, String PRODUCT_RATE,String PRODUCT_VAT){
        this._PRODUCT_CODE = PRODUCT_CODE;
        this._PRODUCT_NAME = PRODUCT_NAME;
        this._PRODUCT_RATE = PRODUCT_RATE;
        this._PRODUCT_VAT = PRODUCT_VAT;
    }


    public String getproductcode(){ return this._PRODUCT_CODE;}
    public String getproductname(){
        return this._PRODUCT_NAME;
    }
    public String getproductrate(){return this._PRODUCT_RATE;}
    public String getproductvat(){return this._PRODUCT_VAT;}

}










