package com.opl.pharmavector.achievement;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AchieveEarningList {
    @SerializedName("sl")
    @Expose
    private Integer sl;
    @SerializedName("BRAND_NAME")
    @Expose
    private String brandName;
    @SerializedName("FF_CODE")
    @Expose
    private String ffCode;
    @SerializedName("FF_NAME")
    @Expose
    private String ffName;
    @SerializedName("EMP_NAME")
    @Expose
    private String empName;
    @SerializedName("MOBILE_PHONE")
    @Expose
    private String mobilePhone;
    @SerializedName("GROWTH")
    @Expose
    private String growth;
    @SerializedName("ACHV_PER")
    @Expose
    private String achvPer;
    @SerializedName("GROWTH_TAR")
    @Expose
    private String growthTar;
    @SerializedName("EMP_CODE")
    @Expose
    private String empCode;

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public Integer getSl() {
        return sl;
    }

    public void setSl(Integer sl) {
        this.sl = sl;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
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

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getGrowth() {
        return growth;
    }

    public void setGrowth(String growth) {
        this.growth = growth;
    }

    public String getAchvPer() {
        return achvPer;
    }

    public void setAchvPer(String achvPer) {
        this.achvPer = achvPer;
    }

    public String getGrowthTar() {
        return growthTar;
    }

    public void setGrowthTar(String growthTar) {
        this.growthTar = growthTar;
    }
}
