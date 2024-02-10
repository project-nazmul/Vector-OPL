package com.opl.pharmavector.saleReport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GroupOrderSumDetailList {
    @SerializedName("SL")
    @Expose
    private Integer sl;
    @SerializedName("FF_CODE")
    @Expose
    private String ffCode;
    @SerializedName("FF_NAME")
    @Expose
    private String ffName;
    @SerializedName("ORD_QNTY")
    @Expose
    private String ordQnty;
    @SerializedName("ORD_VALUE")
    @Expose
    private String ordValue;

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

    public String getOrdQnty() {
        return ordQnty;
    }

    public void setOrdQnty(String ordQnty) {
        this.ordQnty = ordQnty;
    }

    public String getOrdValue() {
        return ordValue;
    }

    public void setOrdValue(String ordValue) {
        this.ordValue = ordValue;
    }
}
