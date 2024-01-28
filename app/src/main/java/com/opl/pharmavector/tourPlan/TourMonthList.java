package com.opl.pharmavector.tourPlan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TourMonthList {
    @SerializedName("SL")
    @Expose
    private Integer sl;
    @SerializedName("CAL_DAY")
    @Expose
    private String calDay;
    @SerializedName("CAL_DAY_DESC")
    @Expose
    private String calDayDesc;
    @SerializedName("CAL_DAY_CHR")
    @Expose
    private String calDayChr;
    @SerializedName("HOLIDAY")
    @Expose
    private String holiday;

    public Integer getSl() {
        return sl;
    }

    public void setSl(Integer sl) {
        this.sl = sl;
    }

    public String getCalDay() {
        return calDay;
    }

    public void setCalDay(String calDay) {
        this.calDay = calDay;
    }

    public String getCalDayDesc() {
        return calDayDesc;
    }

    public void setCalDayDesc(String calDayDesc) {
        this.calDayDesc = calDayDesc;
    }

    public String getCalDayChr() {
        return calDayChr;
    }

    public void setCalDayChr(String calDayChr) {
        this.calDayChr = calDayChr;
    }

    public String getHoliday() {
        return holiday;
    }

    public void setHoliday(String holiday) {
        this.holiday = holiday;
    }
}
