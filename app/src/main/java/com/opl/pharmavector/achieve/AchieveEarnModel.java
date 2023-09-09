package com.opl.pharmavector.achieve;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AchieveEarnModel {
    @SerializedName("categories")
    @Expose
    private List<AchieveEarningList> achieveEarnList;

    public List<AchieveEarningList> getAchieveEarnList() {
        return achieveEarnList;
    }

    public void setAchieveEarnList(List<AchieveEarningList> achieveEarnList) {
        this.achieveEarnList = achieveEarnList;
    }
}
