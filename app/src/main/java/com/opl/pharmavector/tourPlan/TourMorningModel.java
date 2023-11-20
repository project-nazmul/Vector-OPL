package com.opl.pharmavector.tourPlan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TourMorningModel {
    @SerializedName("tour_mode")
    @Expose
    private List<TourMorningList> tourMorningLists;

    public List<TourMorningList> getTourMorningLists() {
        return tourMorningLists;
    }

    public void setTourMorningLists(List<TourMorningList> tourMorningLists) {
        this.tourMorningLists = tourMorningLists;
    }
}
