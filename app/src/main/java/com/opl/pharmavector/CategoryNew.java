package com.opl.pharmavector;

public class CategoryNew {
    private String sl;
    private String id;
    private String name;
    private String quantity;
    private String PROD_RATE;
    private String PROD_VAT;
    private int array_length;
    private String PPM_CODE;
    private String P_CODE;
    private String SHIFT_CODE;
    private String PROD_VAT_2;
    private String FF_NAME;

    public CategoryNew(String sl, String id, String name, String quantity, String PROD_RATE, String PROD_VAT, String PPM_CODE, String P_CODE, String FF_NAME){
        this.sl=sl;
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.PROD_RATE = PROD_RATE;
        this.PROD_VAT = PROD_VAT;
        this.PPM_CODE =  PPM_CODE;
        this.P_CODE =  P_CODE;
        this.FF_NAME = FF_NAME;
        //this.array_length = array_length;
    }

    public String getSl() {
        return sl;
    }

    public void setSl(String sl) {
        this.sl = sl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPROD_RATE() {
        return PROD_RATE;
    }

    public void setPROD_RATE(String PROD_RATE) {
        this.PROD_RATE = PROD_RATE;
    }

    public String getPROD_VAT() {
        return PROD_VAT;
    }

    public void setPROD_VAT(String PROD_VAT) {
        this.PROD_VAT = PROD_VAT;
    }

    public int getArray_length() {
        return array_length;
    }

    public void setArray_length(int array_length) {
        this.array_length = array_length;
    }

    public String getPPM_CODE() {
        return PPM_CODE;
    }

    public void setPPM_CODE(String PPM_CODE) {
        this.PPM_CODE = PPM_CODE;
    }

    public String getP_CODE() {
        return P_CODE;
    }

    public void setP_CODE(String p_CODE) {
        P_CODE = p_CODE;
    }

    public String getSHIFT_CODE() {
        return SHIFT_CODE;
    }

    public void setSHIFT_CODE(String SHIFT_CODE) {
        this.SHIFT_CODE = SHIFT_CODE;
    }

    public String getPROD_VAT_2() {
        return PROD_VAT_2;
    }

    public void setPROD_VAT_2(String PROD_VAT_2) {
        this.PROD_VAT_2 = PROD_VAT_2;
    }

    public String getFF_NAME() {
        return FF_NAME;
    }

    public void setFF_NAME(String FF_NAME) {
        this.FF_NAME = FF_NAME;
    }
}
