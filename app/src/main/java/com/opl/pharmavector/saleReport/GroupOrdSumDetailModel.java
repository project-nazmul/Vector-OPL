package com.opl.pharmavector.saleReport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GroupOrdSumDetailModel {
    @SerializedName("order_summary")
    @Expose
    private List<GroupOrderSumDetailList> ordSumDetailLists;

    public List<GroupOrderSumDetailList> getOrdSumDetailLists() {
        return ordSumDetailLists;
    }

    public void setOrdSumDetailLists(List<GroupOrderSumDetailList> ordSumDetailLists) {
        this.ordSumDetailLists = ordSumDetailLists;
    }
}
