package com.opl.pharmavector.pmdVector.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class RegionValModel {
    @SerializedName("data_matrix")
    @Expose
    private List<RegionValList> regionValList;

    public List<RegionValList> getRegionValList() {
        return regionValList;
    }

    public void setRegionValList(List<RegionValList> regionValList) {
        this.regionValList = regionValList;
    }
}
