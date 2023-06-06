package com.opl.pharmavector.dcfpFollowup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DcfpEntrySetUpList {
    @SerializedName("SLNO")
    @Expose
    private String slno;
    @SerializedName("TP_WEEK")
    @Expose
    private String tpWeek;
    @SerializedName("TP_DAY")
    @Expose
    private String tpDay;

    public DcfpEntrySetUpList(String slno, String tpWeek, String tpDay) {
        this.slno = slno;
        this.tpWeek = tpWeek;
        this.tpDay = tpDay;
    }

    public String getSlno() {
        return slno;
    }

    public void setSlno(String slno) {
        this.slno = slno;
    }

    public String getTpWeek() {
        return tpWeek;
    }

    public void setTpWeek(String tpWeek) {
        this.tpWeek = tpWeek;
    }

    public String getTpDay() {
        return tpDay;
    }

    public void setTpDay(String tpDay) {
        this.tpDay = tpDay;
    }
}