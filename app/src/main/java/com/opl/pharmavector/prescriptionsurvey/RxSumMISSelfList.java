package com.opl.pharmavector.prescriptionsurvey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RxSumMISSelfList {
    @SerializedName("SL")
    @Expose
    private Integer sl;
    @SerializedName("FF_CODE")
    @Expose
    private String ffCode;
    @SerializedName("FF_NAME")
    @Expose
    private String ffName;
    @SerializedName("RG")
    @Expose
    private String rg;
    @SerializedName("SG")
    @Expose
    private String sg;
    @SerializedName("BL")
    @Expose
    private String bl;
    @SerializedName("RX")
    @Expose
    private String rx;
    @SerializedName("OP")
    @Expose
    private String op;
    @SerializedName("IND")
    @Expose
    private String ind;
    @SerializedName("SD")
    @Expose
    private String sd;
    @SerializedName("TOT_RX")
    @Expose
    private String totRx;

    public Integer getSl() {
        return sl;
    }

    public void setSl(Integer sl) {
        this.sl = sl;
    }

    public String getFfCode() {
        return ffCode;
    }

    public void setFfCode(String ffCode) {
        this.ffCode = ffCode;
    }

    public String getFfName() {
        return ffName;
    }

    public void setFfName(String ffName) {
        this.ffName = ffName;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getSg() {
        return sg;
    }

    public void setSg(String sg) {
        this.sg = sg;
    }

    public String getBl() {
        return bl;
    }

    public void setBl(String bl) {
        this.bl = bl;
    }

    public String getRx() {
        return rx;
    }

    public void setRx(String rx) {
        this.rx = rx;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getInd() {
        return ind;
    }

    public void setInd(String ind) {
        this.ind = ind;
    }

    public String getSd() {
        return sd;
    }

    public void setSd(String sd) {
        this.sd = sd;
    }

    public String getTotRx() {
        return totRx;
    }

    public void setTotRx(String totRx) {
        this.totRx = totRx;
    }
}
