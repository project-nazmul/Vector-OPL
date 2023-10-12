package com.opl.pharmavector.productOffer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductOfferList {
    @SerializedName("sl")
    @Expose
    private Integer sl;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("P_CODE")
    @Expose
    private String pCode;
    @SerializedName("PROD_RATE")
    @Expose
    private String prodRate;
    @SerializedName("PROD_VAT")
    @Expose
    private String prodVat;
    @SerializedName("PPM_CODE")
    @Expose
    private Integer ppmCode;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("SHIFT_CODE")
    @Expose
    private Integer shiftCode;

    public Integer getSl() {
        return sl;
    }

    public void setSl(Integer sl) {
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

    public String getPCode() {
        return pCode;
    }

    public void setPCode(String pCode) {
        this.pCode = pCode;
    }

    public String getProdRate() {
        return prodRate;
    }

    public void setProdRate(String prodRate) {
        this.prodRate = prodRate;
    }

    public String getProdVat() {
        return prodVat;
    }

    public void setProdVat(String prodVat) {
        this.prodVat = prodVat;
    }

    public Integer getPpmCode() {
        return ppmCode;
    }

    public void setPpmCode(Integer ppmCode) {
        this.ppmCode = ppmCode;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getShiftCode() {
        return shiftCode;
    }

    public void setShiftCode(Integer shiftCode) {
        this.shiftCode = shiftCode;
    }
}
