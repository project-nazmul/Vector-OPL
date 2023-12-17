package com.opl.pharmavector.mpodcr.entry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DcrNatureModel {
    @SerializedName("tour_nature")
    @Expose
    private List<DcrNatureList> dcrNatureLists;

    public List<DcrNatureList> getDcrNatureLists() {
        return dcrNatureLists;
    }

    public void setDcrNatureLists(List<DcrNatureList> dcrNatureLists) {
        this.dcrNatureLists = dcrNatureLists;
    }
}
