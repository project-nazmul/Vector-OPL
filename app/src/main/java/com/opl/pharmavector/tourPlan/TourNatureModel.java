package com.opl.pharmavector.tourPlan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TourNatureModel {
    @SerializedName("tour_nature")
    @Expose
    private List<TourNatureList> tourNatureLists;

    public List<TourNatureList> getTourNatureLists() {
        return tourNatureLists;
    }

    public void setTourNatureLists(List<TourNatureList> tourNatureLists) {
        this.tourNatureLists = tourNatureLists;
    }
}
