package com.opl.pharmavector.pmdVector.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ProductModel {
    @SerializedName("customer")
    @Expose
    private List<ProductList> productList;

    public List<ProductList> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductList> productList) {
        this.productList = productList;
    }
}
