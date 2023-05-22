package com.opl.pharmavector.msd_doc_support.adapter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MSDApprovalModel {
    @SerializedName("serial")
    @Expose
    private Integer serial;
    @SerializedName("MSD_SLNO")
    @Expose
    private String msdSlno;
    @SerializedName("FOR_MON")
    @Expose
    private String forMon;
    @SerializedName("INST_DESC")
    @Expose
    private String instDesc;
    @SerializedName("DEPT_DESC")
    @Expose
    private String deptDesc;
    @SerializedName("VENUE")
    @Expose
    private String venue;
    @SerializedName("UPAZILLA")
    @Expose
    private String upazilla;
    @SerializedName("DIST_DESC")
    @Expose
    private String distDesc;
    @SerializedName("TERRI_NAME")
    @Expose
    private String terriName;
    @SerializedName("INTER_TYPE")
    @Expose
    private String interType;
    @SerializedName("PROPOSE_MPO")
    @Expose
    private String proposeMpo;
    @SerializedName("PURCH_TYPE")
    @Expose
    private String purchType;
    @SerializedName("PURCH_TYPE_PPM")
    @Expose
    private String purchTypePpm;
    @SerializedName("ENAME")
    @Expose
    private String ename;
    @SerializedName("NO_CME")
    @Expose
    private String noCme;

    public String getTotCost() {
        return totCost;
    }

    public void setTotCost(String totCost) {
        this.totCost = totCost;
    }

    @SerializedName("TOT_COST")
    @Expose
    private String totCost;

    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    public String getMsdSlno() {
        return msdSlno;
    }

    public void setMsdSlno(String msdSlno) {
        this.msdSlno = msdSlno;
    }

    public String getForMon() {
        return forMon;
    }

    public void setForMon(String forMon) {
        this.forMon = forMon;
    }

    public String getInstDesc() {
        return instDesc;
    }

    public void setInstDesc(String instDesc) {
        this.instDesc = instDesc;
    }

    public String getDeptDesc() {
        return deptDesc;
    }

    public void setDeptDesc(String deptDesc) {
        this.deptDesc = deptDesc;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public Object getUpazilla() {
        return upazilla;
    }

    public void setUpazilla(String upazilla) {
        this.upazilla = upazilla;
    }

    public String getDistDesc() {
        return distDesc;
    }

    public void setDistDesc(String distDesc) {
        this.distDesc = distDesc;
    }

    public String getTerriName() {
        return terriName;
    }

    public void setTerriName(String terriName) {
        this.terriName = terriName;
    }

    public String getInterType() {
        return interType;
    }

    public void setInterType(String interType) {
        this.interType = interType;
    }

    public String getProposeMpo() {
        return proposeMpo;
    }

    public void setProposeMpo(String proposeMpo) {
        this.proposeMpo = proposeMpo;
    }

    public String getPurchType() {
        return purchType;
    }

    public void setPurchType(String purchType) {
        this.purchType = purchType;
    }

    public String getPurchTypePpm() {
        return purchTypePpm;
    }

    public void setPurchTypePpm(String purchTypePpm) {
        this.purchTypePpm = purchTypePpm;
    }

    public Object getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getNoCme() {
        return noCme;
    }

    public void setNoCme(String noCme) {
        this.noCme = noCme;
    }
}
