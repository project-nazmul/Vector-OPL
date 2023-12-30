package com.opl.pharmavector.achieve;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AchvIncentiveModel {
    @SerializedName("incentive_type")
    @Expose
    private List<AchieveIncentiveList> incentiveType;

    public List<AchieveIncentiveList> getIncentiveType() {
        return incentiveType;
    }

    public void setIncentiveType(List<AchieveIncentiveList> incentiveType) {
        this.incentiveType = incentiveType;
    }
}
