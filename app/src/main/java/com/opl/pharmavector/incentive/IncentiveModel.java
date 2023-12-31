package com.opl.pharmavector.incentive;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IncentiveModel {
    @SerializedName("incentive_type")
    @Expose
    private List<IncentiveList> incentiveType;

    public List<IncentiveList> getIncentiveType() {
        return incentiveType;
    }

    public void setIncentiveType(List<IncentiveList> incentiveType) {
        this.incentiveType = incentiveType;
    }
}
