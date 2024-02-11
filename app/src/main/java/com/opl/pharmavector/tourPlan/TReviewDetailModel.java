package com.opl.pharmavector.tourPlan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TReviewDetailModel {
    @SerializedName("plan_details")
    @Expose
    private List<TReviewDetailsList> reviewDetailList;

    public List<TReviewDetailsList> getReviewDetailList() {
        return reviewDetailList;
    }

    public void setReviewDetailList(List<TReviewDetailsList> reviewDetailList) {
        this.reviewDetailList = reviewDetailList;
    }
}
