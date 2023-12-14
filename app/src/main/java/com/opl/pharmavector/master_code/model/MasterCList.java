package com.opl.pharmavector.master_code.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterCList {
    @SerializedName("sl")
    @Expose
    private Integer sl;
    @SerializedName("FF_ROLL")
    @Expose
    private String ffRoll;
    @SerializedName("FF_DESC")
    @Expose
    private String ffDesc;
    @SerializedName("FF_TYPE")
    @Expose
    private String ffType;
    @SerializedName("MPO_CODE")
    @Expose
    private String mpoCode;
    @SerializedName("TERRI_NAME")
    @Expose
    private String terriName;
    @SerializedName("ENAME")
    @Expose
    private String ename;
    @SerializedName("EMPNO")
    @Expose
    private String empno;
    @SerializedName("DEPOT_DESC")
    @Expose
    private String depotDesc;
    @SerializedName("PASSWORD")
    @Expose
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFfType() {
        return ffType;
    }

    public void setFfType(String ffType) {
        this.ffType = ffType;
    }

    public String getFfDesc() {
        return ffDesc;
    }

    public void setFfDesc(String ffDesc) {
        this.ffDesc = ffDesc;
    }

    public Integer getSl() {
        return sl;
    }

    public void setSl(Integer sl) {
        this.sl = sl;
    }

    public String getFfRoll() {
        return ffRoll;
    }

    public void setFfRoll(String ffRoll) {
        this.ffRoll = ffRoll;
    }

    public String getMpoCode() {
        return mpoCode;
    }

    public void setMpoCode(String mpoCode) {
        this.mpoCode = mpoCode;
    }

    public String getTerriName() {
        return terriName;
    }

    public void setTerriName(String terriName) {
        this.terriName = terriName;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getEmpno() {
        return empno;
    }

    public void setEmpno(String empno) {
        this.empno = empno;
    }

    public String getDepotDesc() {
        return depotDesc;
    }

    public void setDepotDesc(String depotDesc) {
        this.depotDesc = depotDesc;
    }
}
