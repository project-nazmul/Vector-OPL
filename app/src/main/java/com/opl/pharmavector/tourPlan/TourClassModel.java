package com.opl.pharmavector.tourPlan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TourClassModel {
    @SerializedName("tour_class")
    @Expose
    private List<TourClassList> tourClassLists;

    public List<TourClassList> getTourClassLists() {
        return tourClassLists;
    }

    public void setTourClassLists(List<TourClassList> tourClassLists) {
        this.tourClassLists = tourClassLists;
    }
}
