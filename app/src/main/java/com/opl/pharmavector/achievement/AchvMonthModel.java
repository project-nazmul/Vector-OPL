package com.opl.pharmavector.achievement;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AchvMonthModel {
    @SerializedName("customer")
    @Expose
    private List<AchvMonthList> achvMonthList;

    public List<AchvMonthList> getAchvMonthList() {
        return achvMonthList;
    }

    public void setAchvMonthList(List<AchvMonthList> achvMonthList) {
        this.achvMonthList = achvMonthList;
    }
}
