package com.opl.pharmavector.liveDepot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LiveStokeDepotModel {
    @SerializedName("depots")
    @Expose
    private List<LiveStockDepotList> depotLists;

    public List<LiveStockDepotList> getDepotLists() {
        return depotLists;
    }

    public void setDepotLists(List<LiveStockDepotList> depotLists) {
        this.depotLists = depotLists;
    }
}
