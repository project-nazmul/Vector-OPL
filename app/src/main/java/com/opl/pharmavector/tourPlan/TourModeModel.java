package com.opl.pharmavector.tourPlan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TourModeModel {
    @SerializedName("tour_mode")
    @Expose
    private List<TourModeList> tourModeLists;

    public List<TourModeList> getTourModeLists() {
        return tourModeLists;
    }

    public void setTourModeLists(List<TourModeList> tourModeLists) {
        this.tourModeLists = tourModeLists;
    }
}
