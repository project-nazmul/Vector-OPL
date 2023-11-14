package com.opl.pharmavector.liveDepot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LiveDepotStockList {
    @SerializedName("SL")
    @Expose
    private Integer sl;
    @SerializedName("P_CODE")
    @Expose
    private String pCode;
    @SerializedName("P_DESC")
    @Expose
    private String pDesc;
    @SerializedName("PACK_SIZE")
    @Expose
    private String packSize;
    @SerializedName("COMM_MRP")
    @Expose
    private String commMrp;
    @SerializedName("STOCK_QNTY")
    @Expose
    private String stockQnty;
    @SerializedName("STOCK_VALUE")
    @Expose
    private String stockValue;

    public Integer getSl() {
        return sl;
    }

    public void setSl(Integer sl) {
        this.sl = sl;
    }

    public String getPCode() {
        return pCode;
    }

    public void setPCode(String pCode) {
        this.pCode = pCode;
    }

    public String getPDesc() {
        return pDesc;
    }

    public void setPDesc(String pDesc) {
        this.pDesc = pDesc;
    }

    public String getPackSize() {
        return packSize;
    }

    public void setPackSize(String packSize) {
        this.packSize = packSize;
    }

    public String getCommMrp() {
        return commMrp;
    }

    public void setCommMrp(String commMrp) {
        this.commMrp = commMrp;
    }

    public String getStockQnty() {
        return stockQnty;
    }

    public void setStockQnty(String stockQnty) {
        this.stockQnty = stockQnty;
    }

    public String getStockValue() {
        return stockValue;
    }

    public void setStockValue(String stockValue) {
        this.stockValue = stockValue;
    }
}
