package com.opl.pharmavector.pmdVector.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegionValList {
    @SerializedName("REGION")
    @Expose
    private String region;
    @SerializedName("VAL_SHARE")
    @Expose
    private String valShare;
    @SerializedName("COM_1")
    @Expose
    private String com1;
    @SerializedName("VAL_SHARE_1")
    @Expose
    private String valShare1;
    @SerializedName("COM_2")
    @Expose
    private String com2;
    @SerializedName("VAL_SHARE_2")
    @Expose
    private String valShare2;
    @SerializedName("COM_3")
    @Expose
    private String com3;
    @SerializedName("VAL_SHARE_3")
    @Expose
    private String valShare3;

    public RegionValList(String region, String valShare, String com1, String valShare1, String com2, String valShare2, String com3, String valShare3) {
        this.region = region;
        this.valShare = valShare;
        this.com1 = com1;
        this.valShare1 = valShare1;
        this.com2 = com2;
        this.valShare2 = valShare2;
        this.com3 = com3;
        this.valShare3 = valShare3;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getValShare() {
        return valShare;
    }

    public void setValShare(String valShare) {
        this.valShare = valShare;
    }

    public String getCom1() {
        return com1;
    }

    public void setCom1(String com1) {
        this.com1 = com1;
    }

    public String getValShare1() {
        return valShare1;
    }

    public void setValShare1(String valShare1) {
        this.valShare1 = valShare1;
    }

    public String getCom2() {
        return com2;
    }

    public void setCom2(String com2) {
        this.com2 = com2;
    }

    public String getValShare2() {
        return valShare2;
    }

    public void setValShare2(String valShare2) {
        this.valShare2 = valShare2;
    }

    public String getCom3() {
        return com3;
    }

    public void setCom3(String com3) {
        this.com3 = com3;
    }

    public String getValShare3() {
        return valShare3;
    }

    public void setValShare3(String valShare3) {
        this.valShare3 = valShare3;
    }
}
