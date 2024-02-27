package com.opl.pharmavector.tourPlan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TUpdateDetailList {
    @SerializedName("LOCATION_FROM")
    @Expose
    private String locationFrom;
    @SerializedName("LOCATION_TO")
    @Expose
    private String locationTo;
    @SerializedName("TN_CODE")
    @Expose
    private String tnCode;
    @SerializedName("TMC_CODE")
    @Expose
    private String tmcCode;
    @SerializedName("TM_CODE")
    @Expose
    private String tmCode;
    @SerializedName("FROM_H")
    @Expose
    private String fromH;
    @SerializedName("FROM_M")
    @Expose
    private String fromM;
    @SerializedName("FROM_AM")
    @Expose
    private String fromAm;
    @SerializedName("TO_H")
    @Expose
    private String toH;
    @SerializedName("TO_M")
    @Expose
    private String toM;
    @SerializedName("TO_AM")
    @Expose
    private String toAm;
    @SerializedName("objective")
    @Expose
    private String objective;

    public String getLocationFrom() {
        return locationFrom;
    }

    public void setLocationFrom(String locationFrom) {
        this.locationFrom = locationFrom;
    }

    public String getLocationTo() {
        return locationTo;
    }

    public void setLocationTo(String locationTo) {
        this.locationTo = locationTo;
    }

    public String getTnCode() {
        return tnCode;
    }

    public void setTnCode(String tnCode) {
        this.tnCode = tnCode;
    }

    public String getTmcCode() {
        return tmcCode;
    }

    public void setTmcCode(String tmcCode) {
        this.tmcCode = tmcCode;
    }

    public String getTmCode() {
        return tmCode;
    }

    public void setTmCode(String tmCode) {
        this.tmCode = tmCode;
    }

    public String getFromH() {
        return fromH;
    }

    public void setFromH(String fromH) {
        this.fromH = fromH;
    }

    public String getFromM() {
        return fromM;
    }

    public void setFromM(String fromM) {
        this.fromM = fromM;
    }

    public String getFromAm() {
        return fromAm;
    }

    public void setFromAm(String fromAm) {
        this.fromAm = fromAm;
    }

    public String getToH() {
        return toH;
    }

    public void setToH(String toH) {
        this.toH = toH;
    }

    public String getToM() {
        return toM;
    }

    public void setToM(String toM) {
        this.toM = toM;
    }

    public String getToAm() {
        return toAm;
    }

    public void setToAm(String toAm) {
        this.toAm = toAm;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    @Override
    public String toString() {
        return "TUpdateDetailList{" +
                "locationFrom='" + locationFrom + '\'' +
                ", locationTo='" + locationTo + '\'' +
                ", tnCode='" + tnCode + '\'' +
                ", tmcCode='" + tmcCode + '\'' +
                ", tmCode='" + tmCode + '\'' +
                ", fromH='" + fromH + '\'' +
                ", fromM='" + fromM + '\'' +
                ", fromAm='" + fromAm + '\'' +
                ", toH='" + toH + '\'' +
                ", toM='" + toM + '\'' +
                ", toAm='" + toAm + '\'' +
                ", objective='" + objective + '\'' +
                '}';
    }
}
