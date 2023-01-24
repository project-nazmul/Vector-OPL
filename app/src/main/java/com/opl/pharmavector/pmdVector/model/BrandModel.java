package com.opl.pharmavector.pmdVector.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class BrandModel {
    @SerializedName("array_data")
    @Expose
    private List<BrandList> brandList;

    public List<BrandList> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<BrandList> brandList) {
        this.brandList = brandList;
    }
}
