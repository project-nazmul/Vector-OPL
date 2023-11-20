package com.opl.pharmavector.tourPlan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TourClassList {
    @SerializedName("SL")
    @Expose
    private Integer sl;
    @SerializedName("TMC_CODE")
    @Expose
    private String tmcCode;
    @SerializedName("TMC_DESC")
    @Expose
    private String tmcDesc;

    public Integer getSl() {
        return sl;
    }

    public void setSl(Integer sl) {
        this.sl = sl;
    }

    public String getTmcCode() {
        return tmcCode;
    }

    public void setTmcCode(String tmcCode) {
        this.tmcCode = tmcCode;
    }

    public String getTmcDesc() {
        return tmcDesc;
    }

    public void setTmcDesc(String tmcDesc) {
        this.tmcDesc = tmcDesc;
    }
}
