package com.opl.pharmavector.saleReport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GroupOrdSummaryModel {
    @SerializedName("order_summary")
    @Expose
    private List<GroupOrderSummaryList> groupOrdSummaryLists;

    public List<GroupOrderSummaryList> getGroupOrdSummaryLists() {
        return groupOrdSummaryLists;
    }

    public void setGroupOrdSummaryLists(List<GroupOrderSummaryList> groupOrdSummaryLists) {
        this.groupOrdSummaryLists = groupOrdSummaryLists;
    }
}
