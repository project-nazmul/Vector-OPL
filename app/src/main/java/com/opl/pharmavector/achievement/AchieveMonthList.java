package com.opl.pharmavector.achievement;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AchieveMonthList {
    @SerializedName("MNYR")
    @Expose
    private String mnyr;
    @SerializedName("MNYR_DESC")
    @Expose
    private String mnyrDesc;

    public String getMnyr() {
        return mnyr;
    }

    public void setMnyr(String mnyr) {
        this.mnyr = mnyr;
    }

    public String getMnyrDesc() {
        return mnyrDesc;
    }

    public void setMnyrDesc(String mnyrDesc) {
        this.mnyrDesc = mnyrDesc;
    }
}
