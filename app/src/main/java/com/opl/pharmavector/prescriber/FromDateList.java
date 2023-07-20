package com.opl.pharmavector.prescriber;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FromDateList {
    @SerializedName("MON")
    @Expose
    private String mon;
    @SerializedName("MNYR")
    @Expose
    private String mnyr;

    public String getMon() {
        return mon;
    }

    public void setMon(String mon) {
        this.mon = mon;
    }

    public String getMnyr() {
        return mnyr;
    }

    public void setMnyr(String mnyr) {
        this.mnyr = mnyr;
    }

    @Override
    public String toString() {
        return "FromDateList{" +
                "mon='" + mon + '\'' +
                ", mnyr='" + mnyr + '\'' +
                '}';
    }
}
