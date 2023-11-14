package com.opl.pharmavector;

public class test {
    //private variables
    String _SL;
    String _PRODUCT_CODE;
    String _PRODUCT_QUANTITY;
    String _PRODUCT_NAME;
    String _PRODUCT_RATE;
    String _PRODUCT_VAT;

    public test() {}

    public test(String SL,String PRODUCT_CODE, String PRODUCT_QUANTITY, String PRODUCT_NAME, String PRODUCT_RATE,String PRODUCT_VAT){
        this._SL = SL;
        this._PRODUCT_CODE = PRODUCT_CODE;
        this._PRODUCT_QUANTITY = PRODUCT_QUANTITY;
        this._PRODUCT_NAME = PRODUCT_NAME;
        this._PRODUCT_RATE = PRODUCT_RATE;
        this._PRODUCT_VAT = PRODUCT_VAT;
    }

    public void setproductsl(String SL){
        this._SL = SL;
    }
    public void setcode(String PRODUCT_CODE){
        this._PRODUCT_CODE = PRODUCT_CODE;
    }
    public void setproductname(String PRODUCT_NAME){
        this._PRODUCT_NAME = PRODUCT_NAME;
    }
    public void setproductrate(String PRODUCT_RATE){
        this._PRODUCT_RATE = PRODUCT_RATE;
    }
    public void setproductquant(String PRODUCT_QUANTITY){this._PRODUCT_QUANTITY = PRODUCT_QUANTITY;}
    public void setproductvat(String PRODUCT_VAT){
        this._PRODUCT_VAT = PRODUCT_VAT;
    }
    public String getproductsl() { return this._SL; }
    public String getproductcode() { return this._PRODUCT_CODE; }
    public String getproductquant() { return this._PRODUCT_QUANTITY;}
    public String getproductname(){
        return this._PRODUCT_NAME;
    }
    public String getproductrate() { return this._PRODUCT_RATE; }
    public String getproductvat() { return this._PRODUCT_VAT; }
}

