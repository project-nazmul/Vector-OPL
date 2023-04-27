package com.opl.pharmavector.mpodcr;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DcfpList {
    @SerializedName("VISIT_TYPE")
    @Expose
    private String visitType;
    @SerializedName("DOC_CODE")
    @Expose
    private String docCode;
    @SerializedName("DOC_NAME")
    @Expose
    private String docName;
    @SerializedName("MKT_CODE")
    @Expose
    private String mktCode;
    @SerializedName("MKT_DESC")
    @Expose
    private String mktDesc;
    @SerializedName("TERRI_NAME")
    @Expose
    private String terriName;
    @SerializedName("TS_CODE")
    @Expose
    private String tsCode;

    public String getVisitType() {
        return visitType;
    }

    public void setVisitType(String visitType) {
        this.visitType = visitType;
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

    public String getMktCode() {
        return mktCode;
    }

    public void setMktCode(String mktCode) {
        this.mktCode = mktCode;
    }

    public String getMktDesc() {
        return mktDesc;
    }

    public void setMktDesc(String mktDesc) {
        this.mktDesc = mktDesc;
    }

    public String getTerriName() {
        return terriName;
    }

    public void setTerriName(String terriName) {
        this.terriName = terriName;
    }

    public String getTsCode() {
        return tsCode;
    }

    public void setTsCode(String tsCode) {
        this.tsCode = tsCode;
    }
}
