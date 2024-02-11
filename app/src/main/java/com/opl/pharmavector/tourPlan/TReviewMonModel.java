package com.opl.pharmavector.tourPlan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TReviewMonModel {
    @SerializedName("tour_month")
    @Expose
    private List<TReviewMonthList> reviewMonthLists;

    public List<TReviewMonthList> getReviewMonthLists() {
        return reviewMonthLists;
    }

    public void setReviewMonthLists(List<TReviewMonthList> reviewMonthLists) {
        this.reviewMonthLists = reviewMonthLists;
    }
}
