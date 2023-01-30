package com.opl.pharmavector.pmdVector.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class RegionUnitModel {
    @SerializedName("data_matrix")
    @Expose
    private List<RegionUnitList> regionUnitList;

    public List<RegionUnitList> getRegionUnitList() {
        return regionUnitList;
    }

    public void setRegionUnitList(List<RegionUnitList> regionUnitList) {
        this.regionUnitList = regionUnitList;
    }
}
