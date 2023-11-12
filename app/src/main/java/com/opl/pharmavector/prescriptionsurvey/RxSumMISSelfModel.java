package com.opl.pharmavector.prescriptionsurvey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RxSumMISSelfModel {
    @SerializedName("categories")
    @Expose
    private List<RxSumMISSelfList> rxSumMISSelfLists;

    public List<RxSumMISSelfList> getRxSumMISSelfLists() {
        return rxSumMISSelfLists;
    }

    public void setRxSumMISSelfLists(List<RxSumMISSelfList> rxSumMISSelfLists) {
        this.rxSumMISSelfLists = rxSumMISSelfLists;
    }
}
