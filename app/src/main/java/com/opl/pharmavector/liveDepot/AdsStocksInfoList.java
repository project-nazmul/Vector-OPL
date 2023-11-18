package com.opl.pharmavector.liveDepot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdsStocksInfoList {
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
    @SerializedName("TAR_QNTY3")
    @Expose
    private String tarQnty3;
    @SerializedName("SALE_QNTY3")
    @Expose
    private String saleQnty3;
    @SerializedName("SALE_QNTY0")
    @Expose
    private String saleQnty0;
    @SerializedName("SALE_QNTY1")
    @Expose
    private String saleQnty1;
    @SerializedName("SALE_QNTY2")
    @Expose
    private String saleQnty2;
    @SerializedName("AVG3_SALE")
    @Expose
    private String avg3Sale;
    @SerializedName("TOT_FREE_STK_QNTY")
    @Expose
    private String totFreeStkQnty;
    @SerializedName("DEPO_STK")
    @Expose
    private String depoStk;
    @SerializedName("BSL_STK_ONLY")
    @Expose
    private String bslStkOnly;
    @SerializedName("BSL_FGS_TRNS_ONLY")
    @Expose
    private String bslFgsTrnsOnly;
    @SerializedName("FGS_STK")
    @Expose
    private String fgsStk;

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

    public String getTarQnty3() {
        return tarQnty3;
    }

    public void setTarQnty3(String tarQnty3) {
        this.tarQnty3 = tarQnty3;
    }

    public String getSaleQnty3() {
        return saleQnty3;
    }

    public void setSaleQnty3(String saleQnty3) {
        this.saleQnty3 = saleQnty3;
    }

    public String getSaleQnty0() {
        return saleQnty0;
    }

    public void setSaleQnty0(String saleQnty0) {
        this.saleQnty0 = saleQnty0;
    }

    public String getSaleQnty1() {
        return saleQnty1;
    }

    public void setSaleQnty1(String saleQnty1) {
        this.saleQnty1 = saleQnty1;
    }

    public String getSaleQnty2() {
        return saleQnty2;
    }

    public void setSaleQnty2(String saleQnty2) {
        this.saleQnty2 = saleQnty2;
    }

    public String getAvg3Sale() {
        return avg3Sale;
    }

    public void setAvg3Sale(String avg3Sale) {
        this.avg3Sale = avg3Sale;
    }

    public String getTotFreeStkQnty() {
        return totFreeStkQnty;
    }

    public void setTotFreeStkQnty(String totFreeStkQnty) {
        this.totFreeStkQnty = totFreeStkQnty;
    }

    public String getDepoStk() {
        return depoStk;
    }

    public void setDepoStk(String depoStk) {
        this.depoStk = depoStk;
    }

    public String getBslStkOnly() {
        return bslStkOnly;
    }

    public void setBslStkOnly(String bslStkOnly) {
        this.bslStkOnly = bslStkOnly;
    }

    public String getBslFgsTrnsOnly() {
        return bslFgsTrnsOnly;
    }

    public void setBslFgsTrnsOnly(String bslFgsTrnsOnly) {
        this.bslFgsTrnsOnly = bslFgsTrnsOnly;
    }

    public String getFgsStk() {
        return fgsStk;
    }

    public void setFgsStk(String fgsStk) {
        this.fgsStk = fgsStk;
    }
}
