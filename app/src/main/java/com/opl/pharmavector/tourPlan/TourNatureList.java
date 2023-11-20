package com.opl.pharmavector.tourPlan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TourNatureList {
    @SerializedName("SL")
    @Expose
    private Integer sl;
    @SerializedName("TN_CODE")
    @Expose
    private String tnCode;
    @SerializedName("TN_DESC")
    @Expose
    private String tnDesc;

    public Integer getSl() {
        return sl;
    }

    public void setSl(Integer sl) {
        this.sl = sl;
    }

    public String getTnCode() {
        return tnCode;
    }

    public void setTnCode(String tnCode) {
        this.tnCode = tnCode;
    }

    public String getTnDesc() {
        return tnDesc;
    }

    public void setTnDesc(String tnDesc) {
        this.tnDesc = tnDesc;
    }
}
