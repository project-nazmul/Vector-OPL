package com.opl.pharmavector.liveDepot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LiveStockDepotList {
    @SerializedName("DEPOT_CODE")
    @Expose
    private String depotCode;
    @SerializedName("DEPOT_DESC")
    @Expose
    private String depotDesc;

    public String getDepotCode() {
        return depotCode;
    }

    public void setDepotCode(String depotCode) {
        this.depotCode = depotCode;
    }

    public String getDepotDesc() {
        return depotDesc;
    }

    public void setDepotDesc(String depotDesc) {
        this.depotDesc = depotDesc;
    }
}
