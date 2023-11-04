package com.opl.pharmavector.dcfpFollowup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorReachSelfList {
    @SerializedName("SL")
    @Expose
    private Integer sl;
    @SerializedName("FF_CODE")
    @Expose
    private String ffCode;
    @SerializedName("FF_NAME")
    @Expose
    private String ffName;
    @SerializedName("TOT_DOC")
    @Expose
    private String totDoc;
    @SerializedName("TOT_GEN_DOC")
    @Expose
    private String totGenDoc;
    @SerializedName("TOT_PROD_DOC")
    @Expose
    private String totProdDoc;
    @SerializedName("DOC_REACH")
    @Expose
    private String docReach;

    public Integer getSl() {
        return sl;
    }

    public void setSl(Integer sl) {
        this.sl = sl;
    }

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

    public String getTotDoc() {
        return totDoc;
    }

    public void setTotDoc(String totDoc) {
        this.totDoc = totDoc;
    }

    public String getTotGenDoc() {
        return totGenDoc;
    }

    public void setTotGenDoc(String totGenDoc) {
        this.totGenDoc = totGenDoc;
    }

    public String getTotProdDoc() {
        return totProdDoc;
    }

    public void setTotProdDoc(String totProdDoc) {
        this.totProdDoc = totProdDoc;
    }

    public String getDocReach() {
        return docReach;
    }

    public void setDocReach(String docReach) {
        this.docReach = docReach;
    }
}
