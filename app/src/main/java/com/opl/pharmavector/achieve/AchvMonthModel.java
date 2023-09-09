package com.opl.pharmavector.achieve;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AchvMonthModel {
    @SerializedName("customer")
    @Expose
    private List<AchieveMonthList> achvMonthList;

    public List<AchieveMonthList> getAchvMonthList() {
        return achvMonthList;
    }

    public void setAchvMonthList(List<AchieveMonthList> achvMonthList) {
        this.achvMonthList = achvMonthList;
    }
}
