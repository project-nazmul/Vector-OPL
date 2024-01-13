package com.opl.pharmavector.incentive;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IncentiveDataModel {
    @SerializedName("incentive_data")
    @Expose
    private List<IncentiveDataList> incentiveDataLists;

    public List<IncentiveDataList> getIncentiveDataLists() {
        return incentiveDataLists;
    }

    public void setIncentiveDataLists(List<IncentiveDataList> incentiveDataLists) {
        this.incentiveDataLists = incentiveDataLists;
    }
}
