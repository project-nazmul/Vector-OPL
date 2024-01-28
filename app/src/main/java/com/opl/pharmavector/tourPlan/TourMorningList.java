package com.opl.pharmavector.tourPlan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TourMorningList {
    @SerializedName("SL")
    @Expose
    private Integer sl;
    @SerializedName("MPO_CODE")
    @Expose
    private String mpoCode;
    @SerializedName("TERRI_NAME")
    @Expose
    private String terriName;
    @SerializedName("ENAME")
    @Expose
    private String ename;

    public Integer getSl() {
        return sl;
    }

    public void setSl(Integer sl) {
        this.sl = sl;
    }

    public String getMpoCode() {
        return mpoCode;
    }

    public void setMpoCode(String mpoCode) {
        this.mpoCode = mpoCode;
    }

    public String getTerriName() {
        return terriName;
    }

    public void setTerriName(String terriName) {
        this.terriName = terriName;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }
}
