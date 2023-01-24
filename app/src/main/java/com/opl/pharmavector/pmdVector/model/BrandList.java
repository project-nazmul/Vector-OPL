package com.opl.pharmavector.pmdVector.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BrandList {
    @SerializedName("BRAND_CODE")
    @Expose
    private String brandCode;
    @SerializedName("BRAND_NAME")
    @Expose
    private String brandName;
    @SerializedName("VAL_SHARE")
    @Expose
    private String valShare;
    @SerializedName("UNITE_SHARE")
    @Expose
    private String uniteShare;
    @SerializedName("OPL_RANK")
    @Expose
    private String oplRank;
    @SerializedName("NAT_RANK")
    @Expose
    private String natRank;

    public BrandList(String brandCode, String brandName, String valShare, String uniteShare, String oplRank, String natRank) {
        this.brandCode = brandCode;
        this.brandName = brandName;
        this.valShare = valShare;
        this.uniteShare = uniteShare;
        this.oplRank = oplRank;
        this.natRank = natRank;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getValShare() {
        return valShare;
    }

    public void setValShare(String valShare) {
        this.valShare = valShare;
    }

    public String getUniteShare() {
        return uniteShare;
    }

    public void setUniteShare(String uniteShare) {
        this.uniteShare = uniteShare;
    }

    public String getOplRank() {
        return oplRank;
    }

    public void setOplRank(String oplRank) {
        this.oplRank = oplRank;
    }

    public String getNatRank() {
        return natRank;
    }

    public void setNatRank(String natRank) {
        this.natRank = natRank;
    }
}
