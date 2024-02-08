package com.opl.pharmavector.saleReport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GroupOrderSummaryList {
    @SerializedName("SL")
    @Expose
    private Integer sl;
    @SerializedName("P_CODE")
    @Expose
    private String pCode;
    @SerializedName("P_DESC")
    @Expose
    private String pDesc;
    @SerializedName("PROD_GRP_DESC")
    @Expose
    private String prodGrpDesc;
    @SerializedName("PACK_SIZE")
    @Expose
    private String packSize;
    @SerializedName("ORD_QNTY")
    @Expose
    private String ordQnty;
    @SerializedName("ORD_VALUE")
    @Expose
    private String ordValue;

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

    public String getProdGrpDesc() {
        return prodGrpDesc;
    }

    public void setProdGrpDesc(String prodGrpDesc) {
        this.prodGrpDesc = prodGrpDesc;
    }

    public String getPackSize() {
        return packSize;
    }

    public void setPackSize(String packSize) {
        this.packSize = packSize;
    }

    public String getOrdQnty() {
        return ordQnty;
    }

    public void setOrdQnty(String ordQnty) {
        this.ordQnty = ordQnty;
    }

    public String getOrdValue() {
        return ordValue;
    }

    public void setOrdValue(String ordValue) {
        this.ordValue = ordValue;
    }
}
