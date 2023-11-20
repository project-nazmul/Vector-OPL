package com.opl.pharmavector.tourPlan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TourModeList {
    @SerializedName("SL")
    @Expose
    private Integer sl;
    @SerializedName("TM_CODE")
    @Expose
    private String tmCode;
    @SerializedName("TM_DESC")
    @Expose
    private String tmDesc;

    public Integer getSl() {
        return sl;
    }

    public void setSl(Integer sl) {
        this.sl = sl;
    }

    public String getTmCode() {
        return tmCode;
    }

    public void setTmCode(String tmCode) {
        this.tmCode = tmCode;
    }

    public String getTmDesc() {
        return tmDesc;
    }

    public void setTmDesc(String tmDesc) {
        this.tmDesc = tmDesc;
    }
}
