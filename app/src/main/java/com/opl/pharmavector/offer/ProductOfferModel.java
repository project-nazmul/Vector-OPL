package com.opl.pharmavector.offer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductOfferModel {
    @SerializedName("categories")
    @Expose
    private List<ProductOfferList> productOfferList;

    public List<ProductOfferList> getProductOfferList() {
        return productOfferList;
    }

    public void setProductOfferList(List<ProductOfferList> productOfferList) {
        this.productOfferList = productOfferList;
    }
}
