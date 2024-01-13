package com.opl.pharmavector.incentive;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IncentiveDataList {
    @SerializedName("sl")
    @Expose
    private Integer sl;
    @SerializedName("INCENTIVE_TYPE")
    @Expose
    private String incentiveType;
    @SerializedName("WORK_LOC_CODE")
    @Expose
    private String workLocCode;
    @SerializedName("WORK_LOCATION")
    @Expose
    private String workLocation;
    @SerializedName("EMP_CODE")
    @Expose
    private String empCode;
    @SerializedName("EMP_NAME")
    @Expose
    private String empName;
    @SerializedName("MOBILE_PHONE")
    @Expose
    private String mobilePhone;
    @SerializedName("GROWTH")
    @Expose
    private String growth;
    @SerializedName("ACHEIVEMENT")
    @Expose
    private String acheivement;
    @SerializedName("EXPENSE_RATIO")
    @Expose
    private String expenseRatio;

    public Integer getSl() {
        return sl;
    }

    public void setSl(Integer sl) {
        this.sl = sl;
    }

    public String getIncentiveType() {
        return incentiveType;
    }

    public void setIncentiveType(String incentiveType) {
        this.incentiveType = incentiveType;
    }

    public String getWorkLocCode() {
        return workLocCode;
    }

    public void setWorkLocCode(String workLocCode) {
        this.workLocCode = workLocCode;
    }

    public String getWorkLocation() {
        return workLocation;
    }

    public void setWorkLocation(String workLocation) {
        this.workLocation = workLocation;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
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

    public String getAcheivement() {
        return acheivement;
    }

    public void setAcheivement(String acheivement) {
        this.acheivement = acheivement;
    }

    public String getExpenseRatio() {
        return expenseRatio;
    }

    public void setExpenseRatio(String expenseRatio) {
        this.expenseRatio = expenseRatio;
    }
}
