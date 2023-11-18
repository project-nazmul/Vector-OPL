package com.opl.pharmavector.liveDepot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AdsStockDetailModel {
    @SerializedName("ads_product_detail")
    @Expose
    private List<AdsStockDetailsList> adsProductDetail;

    public List<AdsStockDetailsList> getAdsProductDetail() {
        return adsProductDetail;
    }

    public void setAdsProductDetail(List<AdsStockDetailsList> adsProductDetail) {
        this.adsProductDetail = adsProductDetail;
    }
}
