package com.opl.pharmavector.incentive;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IncentiveList {
    @SerializedName("INCENTIVE_TYPE")
    @Expose
    private String incentiveType;
    @SerializedName("INCENTIVE_DESC")
    @Expose
    private String incentiveDesc;

    public String getIncentiveType() {
        return incentiveType;
    }

    public void setIncentiveType(String incentiveType) {
        this.incentiveType = incentiveType;
    }

    public String getIncentiveDesc() {
        return incentiveDesc;
    }

    public void setIncentiveDesc(String incentiveDesc) {
        this.incentiveDesc = incentiveDesc;
    }
}
