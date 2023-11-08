package com.opl.pharmavector.dcfpFollowup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DcfpDocTotDateList {
    @SerializedName("TP_DAY")
    @Expose
    private String tpDay;
    @SerializedName("TP_WEEK")
    @Expose
    private String tpWeek;
    @SerializedName("TOT_DAY")
    @Expose
    private String totDay;

    public String getTpDay() {
        return tpDay;
    }

    public void setTpDay(String tpDay) {
        this.tpDay = tpDay;
    }

    public String getTpWeek() {
        return tpWeek;
    }

    public void setTpWeek(String tpWeek) {
        this.tpWeek = tpWeek;
    }

    public String getTotDay() {
        return totDay;
    }

    public void setTotDay(String totDay) {
        this.totDay = totDay;
    }
}
