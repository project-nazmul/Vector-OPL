package com.opl.pharmavector.prescriber;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopPrescriberList {
    @SerializedName("SLNO")
    @Expose
    private Integer slno;
    @SerializedName("MPO_CODE")
    @Expose
    private String mpoCode;
    @SerializedName("FM_CODE")
    @Expose
    private String fmCode;
    @SerializedName("RM_CODE")
    @Expose
    private String rmCode;
    @SerializedName("AM_CODE")
    @Expose
    private String amCode;
    @SerializedName("SM_CODE")
    @Expose
    private String smCode;
    @SerializedName("DOC_CODE")
    @Expose
    private String docCode;
    @SerializedName("DOC_NAME")
    @Expose
    private String docName;
    @SerializedName("DOC_SPEC")
    @Expose
    private String docSpec;
    @SerializedName("TOTAL_PRES")
    @Expose
    private String totalPres;
    @SerializedName("OPL_PRES")
    @Expose
    private String oplPres;
    @SerializedName("TOP_PRES")
    @Expose
    private String topPres;
    @SerializedName("OPL_VALUE_SHARE")
    @Expose
    private String oplValueShare;
    @SerializedName("TOP_VALUE_SHARE")
    @Expose
    private String topValueShare;

    public TopPrescriberList(Integer slno, String mpoCode, String fmCode, String rmCode, String amCode, String smCode, String docCode, String docName, String docSpec, String totalPres, String oplPres, String topPres, String oplValueShare, String topValueShare) {
        this.slno = slno;
        this.mpoCode = mpoCode;
        this.fmCode = fmCode;
        this.rmCode = rmCode;
        this.amCode = amCode;
        this.smCode = smCode;
        this.docCode = docCode;
        this.docName = docName;
        this.docSpec = docSpec;
        this.totalPres = totalPres;
        this.oplPres = oplPres;
        this.topPres = topPres;
        this.oplValueShare = oplValueShare;
        this.topValueShare = topValueShare;
    }

    public Integer getSlno() {
        return slno;
    }

    public void setSlno(Integer slno) {
        this.slno = slno;
    }

    public String getMpoCode() {
        return mpoCode;
    }

    public void setMpoCode(String mpoCode) {
        this.mpoCode = mpoCode;
    }

    public String getFmCode() {
        return fmCode;
    }

    public void setFmCode(String fmCode) {
        this.fmCode = fmCode;
    }

    public String getRmCode() {
        return rmCode;
    }

    public void setRmCode(String rmCode) {
        this.rmCode = rmCode;
    }

    public String getAmCode() {
        return amCode;
    }

    public void setAmCode(String amCode) {
        this.amCode = amCode;
    }

    public String getSmCode() {
        return smCode;
    }

    public void setSmCode(String smCode) {
        this.smCode = smCode;
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

    public String getDocSpec() {
        return docSpec;
    }

    public void setDocSpec(String docSpec) {
        this.docSpec = docSpec;
    }

    public String getTotalPres() {
        return totalPres;
    }

    public void setTotalPres(String totalPres) {
        this.totalPres = totalPres;
    }

    public String getOplPres() {
        return oplPres;
    }

    public void setOplPres(String oplPres) {
        this.oplPres = oplPres;
    }

    public String getTopPres() {
        return topPres;
    }

    public void setTopPres(String topPres) {
        this.topPres = topPres;
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
