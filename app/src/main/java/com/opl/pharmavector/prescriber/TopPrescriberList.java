package com.opl.pharmavector.prescriber;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopPrescriberList {
    @SerializedName("DOC_CODE")
    @Expose
    private String docCode;
    @SerializedName("DOC_NAME")
    @Expose
    private String docName;
    @SerializedName("BASE_QNTY")
    @Expose
    private String baseQnty;
    @SerializedName("OPL_PRES_QNTY")
    @Expose
    private String oplPresQnty;
    @SerializedName("MAX_PRES_QNTY")
    @Expose
    private String maxPresQnty;
    @SerializedName("OPL_VALUE_SHARE")
    @Expose
    private String oplValueShare;
    @SerializedName("TOP_VALUE_SHARE")
    @Expose
    private String topValueShare;

    public TopPrescriberList(String docCode, String docName, String baseQnty, String oplPresQnty, String maxPresQnty, String oplValueShare, String topValueShare) {
        this.docCode = docCode;
        this.docName = docName;
        this.baseQnty = baseQnty;
        this.oplPresQnty = oplPresQnty;
        this.maxPresQnty = maxPresQnty;
        this.oplValueShare = oplValueShare;
        this.topValueShare = topValueShare;
    }

    public String getDocCode() {
        return docCode;
    }

    public void setDocCode(String docCode) {
        this.docCode = docCode;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getBaseQnty() {
        return baseQnty;
    }

    public void setBaseQnty(String baseQnty) {
        this.baseQnty = baseQnty;
    }

    public String getOplPresQnty() {
        return oplPresQnty;
    }

    public void setOplPresQnty(String oplPresQnty) {
        this.oplPresQnty = oplPresQnty;
    }

    public String getMaxPresQnty() {
        return maxPresQnty;
    }

    public void setMaxPresQnty(String maxPresQnty) {
        this.maxPresQnty = maxPresQnty;
    }

    public String getOplValueShare() {
        return oplValueShare;
    }

    public void setOplValueShare(String oplValueShare) {
        this.oplValueShare = oplValueShare;
    }

    public String getTopValueShare() {
        return topValueShare;
    }

    public void setTopValueShare(String topValueShare) {
        this.topValueShare = topValueShare;
    }
}
