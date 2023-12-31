package com.opl.pharmavector.incentive;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IncentiveQtrList {
    @SerializedName("QTR_CODE")
    @Expose
    private String qtrCode;
    @SerializedName("QTR_DESC")
    @Expose
    private String qtrDesc;

    public String getQtrCode() {
        return qtrCode;
    }

    public void setQtrCode(String qtrCode) {
        this.qtrCode = qtrCode;
    }

    public String getQtrDesc() {
        return qtrDesc;
    }

    public void setQtrDesc(String qtrDesc) {
        this.qtrDesc = qtrDesc;
    }
}
