package com.opl.pharmavector.pmdVector.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegionUnitList {
    @SerializedName("REGION")
    @Expose
    private String region;
    @SerializedName("UNIT_SHARE")
    @Expose
    private String unitShare;
    @SerializedName("COM_1")
    @Expose
    private String com1;
    @SerializedName("UNIT_SHARE_1")
    @Expose
    private String unitShare1;
    @SerializedName("COM_2")
    @Expose
    private String com2;
    @SerializedName("UNIT_SHARE_2")
    @Expose
    private String unitShare2;
    @SerializedName("COM_3")
    @Expose
    private String com3;
    @SerializedName("UNIT_SHARE_3")
    @Expose
    private String unitShare3;

    public RegionUnitList(String region, String unitShare, String com1, String unitShare1, String com2, String unitShare2, String com3, String unitShare3) {
        this.region = region;
        this.unitShare = unitShare;
        this.com1 = com1;
        this.unitShare1 = unitShare1;
        this.com2 = com2;
        this.unitShare2 = unitShare2;
        this.com3 = com3;
        this.unitShare3 = unitShare3;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getUnitShare() {
        return unitShare;
    }

    public void setUnitShare(String unitShare) {
        this.unitShare = unitShare;
    }

    public String getCom1() {
        return com1;
    }

    public void setCom1(String com1) {
        this.com1 = com1;
    }

    public String getUnitShare1() {
        return unitShare1;
    }

    public void setUnitShare1(String unitShare1) {
        this.unitShare1 = unitShare1;
    }

    public String getCom2() {
        return com2;
    }

    public void setCom2(String com2) {
        this.com2 = com2;
    }

    public String getUnitShare2() {
        return unitShare2;
    }

    public void setUnitShare2(String unitShare2) {
        this.unitShare2 = unitShare2;
    }

    public String getCom3() {
        return com3;
    }

    public void setCom3(String com3) {
        this.com3 = com3;
    }

    public String getUnitShare3() {
        return unitShare3;
    }

    public void setUnitShare3(String unitShare3) {
        this.unitShare3 = unitShare3;
    }
}
