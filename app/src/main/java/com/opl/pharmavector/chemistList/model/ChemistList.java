package com.opl.pharmavector.chemistList.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChemistList {
    @SerializedName("DEPOT_DESC")
    @Expose
    private String depotDesc;
    @SerializedName("CUST_CODE")
    @Expose
    private String custCode;
    @SerializedName("CUST_NAME")
    @Expose
    private String custName;
    @SerializedName("MARKET_CODE")
    @Expose
    private String marketCode;
    @SerializedName("MARKET_NAME")
    @Expose
    private String marketName;
    @SerializedName("ADDRESS")
    @Expose
    private String address;

    public String getDepotDesc() {
        return depotDesc;
    }

    public void setDepotDesc(String depotDesc) {
        this.depotDesc = depotDesc;
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getMarketCode() {
        return marketCode;
    }

    public void setMarketCode(String marketCode) {
        this.marketCode = marketCode;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
