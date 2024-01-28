package com.opl.pharmavector.tourPlan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TourMonthModel {
    @SerializedName("tour_month")
    @Expose
    private List<TourMonthList> tourMonthLists;

    public List<TourMonthList> getTourMonthLists() {
        return tourMonthLists;
    }

    public void setTourMonthLists(List<TourMonthList> tourMonthLists) {
        this.tourMonthLists = tourMonthLists;
    }
}
