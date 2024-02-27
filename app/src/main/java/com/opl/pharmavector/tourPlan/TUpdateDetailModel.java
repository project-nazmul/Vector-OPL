package com.opl.pharmavector.tourPlan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TUpdateDetailModel {
    @SerializedName("plan_details_upd")
    @Expose
    private List<TUpdateDetailList> updateDetailLists;

    public List<TUpdateDetailList> getUpdateDetailLists() {
        return updateDetailLists;
    }

    public void setUpdateDetailLists(List<TUpdateDetailList> updateDetailLists) {
        this.updateDetailLists = updateDetailLists;
    }
}
