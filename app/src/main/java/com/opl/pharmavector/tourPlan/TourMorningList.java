package com.opl.pharmavector.tourPlan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TourMorningList {
    @SerializedName("SL")
    @Expose
    private Integer sl;
    @SerializedName("REGION")
    @Expose
    private String region;
    @SerializedName("ENAME")
    @Expose
    private String ename;
    @SerializedName("RM_CODE")
    @Expose
    private String rmCode;

    public Integer getSl() {
        return sl;
    }

    public void setSl(Integer sl) {
        this.sl = sl;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getRmCode() {
        return rmCode;
    }

    public void setRmCode(String rmCode) {
        this.rmCode = rmCode;
    }
}
