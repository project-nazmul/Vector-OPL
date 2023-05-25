package com.opl.pharmavector.dcfpFollowup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DcrFollowupModel {
    @SerializedName("FF_CODE")
    @Expose
    private String ffCode;
    @SerializedName("FF_NAME")
    @Expose
    private String ffName;
    @SerializedName("PLAN_TOT_DOC")
    @Expose
    private String planTotDoc;
    @SerializedName("PLAN_MOR")
    @Expose
    private String planMor;
    @SerializedName("PLAN_EVE")
    @Expose
    private String planEve;
    @SerializedName("VISITED_TOT_DOC")
    @Expose
    private String visitedTotDoc;
    @SerializedName("VISITED_MOR")
    @Expose
    private String visitedMor;
    @SerializedName("VISITED_EVE")
    @Expose
    private String visitedEve;
    @SerializedName("NOT_VISITED")
    @Expose
    private String notVisited;
    @SerializedName("VISIT_PERCENT")
    @Expose
    private String visitPercent;

    public String getFfCode() {
        return ffCode;
    }

    public void setFfCode(String ffCode) {
        this.ffCode = ffCode;
    }

    public String getFfName() {
        return ffName;
    }

    public void setFfName(String ffName) {
        this.ffName = ffName;
    }

    public String getPlanTotDoc() {
        return planTotDoc;
    }

    public void setPlanTotDoc(String planTotDoc) {
        this.planTotDoc = planTotDoc;
    }

    public String getPlanMor() {
        return planMor;
    }

    public void setPlanMor(String planMor) {
        this.planMor = planMor;
    }

    public String getPlanEve() {
        return planEve;
    }

    public void setPlanEve(String planEve) {
        this.planEve = planEve;
    }

    public String getVisitedTotDoc() {
        return visitedTotDoc;
    }

    public void setVisitedTotDoc(String visitedTotDoc) {
        this.visitedTotDoc = visitedTotDoc;
    }

    public String getVisitedMor() {
        return visitedMor;
    }

    public void setVisitedMor(String visitedMor) {
        this.visitedMor = visitedMor;
    }

    public String getVisitedEve() {
        return visitedEve;
    }

    public void setVisitedEve(String visitedEve) {
        this.visitedEve = visitedEve;
    }

    public String getNotVisited() {
        return notVisited;
    }

    public void setNotVisited(String notVisited) {
        this.notVisited = notVisited;
    }

    public String getVisitPercent() {
        return visitPercent;
    }

    public void setVisitPercent(String visitPercent) {
        this.visitPercent = visitPercent;
    }
}
