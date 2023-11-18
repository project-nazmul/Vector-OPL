package com.opl.pharmavector.liveDepot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdsStockDetailsList {
    @SerializedName("SL")
    @Expose
    private Integer sl;
    @SerializedName("DEPOT_CODE")
    @Expose
    private String depotCode;
    @SerializedName("DEPOT_DESC")
    @Expose
    private String depotDesc;
    @SerializedName("DEPOT_QNTY")
    @Expose
    private String depotQnty;
    @SerializedName("TRNS_QNTY")
    @Expose
    private String trnsQnty;
    @SerializedName("DEPOT_TO_DEPOT_TRNS")
    @Expose
    private String depotToDepotTrns;
    @SerializedName("CR_MON_SALE")
    @Expose
    private String crMonSale;
    @SerializedName("DD_COVER_QNTY")
    @Expose
    private String ddCoverQnty;

    public Integer getSl() {
        return sl;
    }

    public void setSl(Integer sl) {
        this.sl = sl;
    }

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

    public String getDepotQnty() {
        return depotQnty;
    }

    public void setDepotQnty(String depotQnty) {
        this.depotQnty = depotQnty;
    }

    public String getTrnsQnty() {
        return trnsQnty;
    }

    public void setTrnsQnty(String trnsQnty) {
        this.trnsQnty = trnsQnty;
    }

    public String getDepotToDepotTrns() {
        return depotToDepotTrns;
    }

    public void setDepotToDepotTrns(String depotToDepotTrns) {
        this.depotToDepotTrns = depotToDepotTrns;
    }

    public String getCrMonSale() {
        return crMonSale;
    }

    public void setCrMonSale(String crMonSale) {
        this.crMonSale = crMonSale;
    }

    public String getDdCoverQnty() {
        return ddCoverQnty;
    }

    public void setDdCoverQnty(String ddCoverQnty) {
        this.ddCoverQnty = ddCoverQnty;
    }
}
