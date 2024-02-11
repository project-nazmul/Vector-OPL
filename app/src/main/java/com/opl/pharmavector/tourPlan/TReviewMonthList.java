package com.opl.pharmavector.tourPlan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TReviewMonthList {
    @SerializedName("MNYR_DESC")
    @Expose
    private String mnyrDesc;
    @SerializedName("MNYR")
    @Expose
    private String mnyr;

    public String getMnyrDesc() {
        return mnyrDesc;
    }

    public void setMnyrDesc(String mnyrDesc) {
        this.mnyrDesc = mnyrDesc;
    }

    public String getMnyr() {
        return mnyr;
    }

    public void setMnyr(String mnyr) {
        this.mnyr = mnyr;
    }
}
