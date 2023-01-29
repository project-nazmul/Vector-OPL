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
    @SerializedName("OPL_UNIT_RANK")
    @Expose
    private String oplUnitRank;
    @SerializedName("OPL_VAL_RANK")
    @Expose
    private String oplValRank;
    @SerializedName("NAT_UNIT_RANK")
    @Expose
    private String natUnitRank;
    @SerializedName("NAT_VAL_RANK")
    @Expose
    private String natValRank;

    public BrandList(String brandCode, String brandName, String valShare, String uniteShare, String oplUnitRank, String oplValRank, String natUnitRank, String natValRank) {
        this.brandCode = brandCode;
        this.brandName = brandName;
        this.valShare = valShare;
        this.uniteShare = uniteShare;
        this.oplUnitRank = oplUnitRank;
        this.oplValRank = oplValRank;
        this.natUnitRank = natUnitRank;
        this.natValRank = natValRank;
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

    public String getOplUnitRank() {
        return oplUnitRank;
    }

    public void setOplUnitRank(String oplUnitRank) {
        this.oplUnitRank = oplUnitRank;
    }

    public String getOplValRank() {
        return oplValRank;
    }

    public void setOplValRank(String oplValRank) {
        this.oplValRank = oplValRank;
    }

    public String getNatUnitRank() {
        return natUnitRank;
    }

    public void setNatUnitRank(String natUnitRank) {
        this.natUnitRank = natUnitRank;
    }

    public String getNatValRank() {
        return natValRank;
    }

    public void setNatValRank(String natValRank) {
        this.natValRank = natValRank;
    }
}