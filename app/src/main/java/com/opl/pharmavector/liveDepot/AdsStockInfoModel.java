package com.opl.pharmavector.liveDepot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AdsStockInfoModel {
    @SerializedName("ads_product")
    @Expose
    private List<AdsStocksInfoList> adsProductList;

    public List<AdsStocksInfoList> getAdsProductList() {
        return adsProductList;
    }

    public void setAdsProductList(List<AdsStocksInfoList> adsProductList) {
        this.adsProductList = adsProductList;
    }
}
