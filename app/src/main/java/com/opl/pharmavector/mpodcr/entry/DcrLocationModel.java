package com.opl.pharmavector.mpodcr.entry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DcrLocationModel {
    @SerializedName("tour_location")
    @Expose
    private List<DcrLocationList> dcrLocationLists;

    public List<DcrLocationList> getDcrLocationLists() {
        return dcrLocationLists;
    }

    public void setDcrLocationLists(List<DcrLocationList> dcrLocationLists) {
        this.dcrLocationLists = dcrLocationLists;
    }
}
