package com.opl.pharmavector.incentive;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IncentiveQtrModel {
    @SerializedName("quater")
    @Expose
    private List<IncentiveQtrList> incentiveQtrLists;

    public List<IncentiveQtrList> getIncentiveQtrLists() {
        return incentiveQtrLists;
    }

    public void setIncentiveQtrLists(List<IncentiveQtrList> incentiveQtrList) {
        this.incentiveQtrLists = incentiveQtrList;
    }
}
