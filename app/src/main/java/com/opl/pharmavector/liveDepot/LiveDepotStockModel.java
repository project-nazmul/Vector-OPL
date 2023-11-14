package com.opl.pharmavector.liveDepot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LiveDepotStockModel {
    @SerializedName("stock_view")
    @Expose
    private List<LiveDepotStockList> depotStockLists;

    public List<LiveDepotStockList> getDepotStockLists() {
        return depotStockLists;
    }

    public void setDepotStockLists(List<LiveDepotStockList> depotStockLists) {
        this.depotStockLists = depotStockLists;
    }
}
