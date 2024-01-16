package com.opl.pharmavector.incentive;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IncentiveTypeModel {
    @SerializedName("incentive_type")
    @Expose
    private List<IncentiveTypeList> incentiveType;

    public List<IncentiveTypeList> getIncentiveType() {
        return incentiveType;
    }

    public void setIncentiveType(List<IncentiveTypeList> incentiveType) {
        this.incentiveType = incentiveType;
    }
}
