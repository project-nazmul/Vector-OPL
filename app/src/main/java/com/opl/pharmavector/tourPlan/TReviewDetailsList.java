package com.opl.pharmavector.tourPlan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TReviewDetailsList {
    @SerializedName("VISIT_DT")
    @Expose
    private String visitDt;
    @SerializedName("MOR_LOC_CODE")
    @Expose
    private String morLocCode;
    @SerializedName("MOR_LOC_NAME")
    @Expose
    private String morLocName;
    @SerializedName("EVE_LOC_CODE")
    @Expose
    private String eveLocCode;
    @SerializedName("EVE_LOC_NAME")
    @Expose
    private String eveLocName;
    @SerializedName("APPROVED_STAT")
    @Expose
    private String approvedStat;

    public String getVisitDt() {
        return visitDt;
    }

    public void setVisitDt(String visitDt) {
        this.visitDt = visitDt;
    }

    public String getMorLocCode() {
        return morLocCode;
    }

    public void setMorLocCode(String morLocCode) {
        this.morLocCode = morLocCode;
    }

    public String getMorLocName() {
        return morLocName;
    }

    public void setMorLocName(String morLocName) {
        this.morLocName = morLocName;
    }

    public String getEveLocCode() {
        return eveLocCode;
    }

    public void setEveLocCode(String eveLocCode) {
        this.eveLocCode = eveLocCode;
    }

    public String getEveLocName() {
        return eveLocName;
    }

    public void setEveLocName(String eveLocName) {
        this.eveLocName = eveLocName;
    }

    public String getApprovedStat() {
        return approvedStat;
    }

    public void setApprovedStat(String approvedStat) {
        this.approvedStat = approvedStat;
    }
}
