package com.opl.pharmavector.dcfpFollowup;

import androidx.annotation.NonNull;

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
    @SerializedName("TP_TYPE")
    @Expose
    private String tpType;
    @SerializedName("UPD_STAT")
    @Expose
    private String updStat;
    @SerializedName("TP_DAY_DESC")
    @Expose
    private String dayDesc;
    public int checkedId = -1;
    public boolean isAnswered;
    public String shiftType;

    public String getDayDesc() {
        return dayDesc;
    }

    public void setDayDesc(String dayDesc) {
        this.dayDesc = dayDesc;
    }

    public String getTpType() {
        return tpType;
    }

    public void setTpType(String tpType) {
        this.tpType = tpType;
    }

    public String getShiftType() {
        return shiftType;
    }

    public void setShiftType(String shiftType) {
        this.shiftType = shiftType;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }

    public int getCheckedId() {
        return checkedId;
    }

    public void setCheckedId(int checkedId) {
        this.checkedId = checkedId;
    }

    public DcfpEntrySetUpList(String slno, String tpWeek, String tpDay, String tpType) {
        this.slno = slno;
        this.tpWeek = tpWeek;
        this.tpDay = tpDay;
        this.tpType = tpType;
    }

    public DcfpEntrySetUpList(String slno, String tpWeek, String tpDay, String tpType, String updStat, String dayDesc) {
        this.slno = slno;
        this.tpWeek = tpWeek;
        this.tpDay = tpDay;
        this.tpType = tpType;
        this.updStat = updStat;
        this.dayDesc = dayDesc;
    }

    public String getUpdStat() {
        return updStat;
    }

    public void setUpdStat(String updStat) {
        this.updStat = updStat;
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

    @Override
    public String toString() {
        return "DcfpEntrySetUpList{" +
                "slno='" + slno + '\'' +
                ", tpWeek='" + tpWeek + '\'' +
                ", tpDay='" + tpDay + '\'' +
                ", tpType='" + tpType + '\'' +
                ", checkedId=" + checkedId +
                ", isAnswered=" + isAnswered +
                ", shiftType='" + shiftType + '\'' +
                '}';
    }
}
