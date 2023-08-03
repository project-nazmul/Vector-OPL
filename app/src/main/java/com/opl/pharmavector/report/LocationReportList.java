package com.opl.pharmavector.report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationReportList {
    @SerializedName("SLNO")
    @Expose
    private Integer slno;
    @SerializedName("USER_LOC")
    @Expose
    private String userLoc;

    public Integer getSlno() {
        return slno;
    }

    public void setSlno(Integer slno) {
        this.slno = slno;
    }

    public String getUserLoc() {
        return userLoc;
    }

    public void setUserLoc(String userLoc) {
        this.userLoc = userLoc;
    }
}
