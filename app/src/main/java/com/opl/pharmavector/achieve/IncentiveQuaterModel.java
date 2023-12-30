package com.opl.pharmavector.achieve;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IncentiveQuaterModel {
    @SerializedName("quater")
    @Expose
    private List<IncentiveQuaterList> incentiveQuaterLists;

    public List<IncentiveQuaterList> getIncentiveQuaterLists() {
        return incentiveQuaterLists;
    }

    public void setIncentiveQuaterLists(List<IncentiveQuaterList> incentiveQuaterLists) {
        this.incentiveQuaterLists = incentiveQuaterLists;
    }
}
