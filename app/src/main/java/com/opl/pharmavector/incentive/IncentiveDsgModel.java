package com.opl.pharmavector.incentive;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IncentiveDsgModel {
    @SerializedName("designation")
    @Expose
    private List<IncentiveDsgList> incentiveDsgLists;

    public List<IncentiveDsgList> getIncentiveDsgLists() {
        return incentiveDsgLists;
    }

    public void setIncentiveDsgLists(List<IncentiveDsgList> incentiveDsgLists) {
        this.incentiveDsgLists = incentiveDsgLists;
    }
}
