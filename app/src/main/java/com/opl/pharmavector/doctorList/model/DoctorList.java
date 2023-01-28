package com.opl.pharmavector.doctorList.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorList {
    @SerializedName("DOC_CODE")
    @Expose
    private String docCode;
    @SerializedName("DOC_NAME")
    @Expose
    private String docName;
    @SerializedName("DEGREE")
    @Expose
    private String degree;
    @SerializedName("DESIG")
    @Expose
    private String desig;
    @SerializedName("SPECIALIZATION")
    @Expose
    private String specialization;
    @SerializedName("MARKET_NAME")
    @Expose
    private String marketName;
    @SerializedName("MARKET_CODE")
    @Expose
    private String marketCode;
    @SerializedName("ADDRESS")
    @Expose
    private String address;
    @SerializedName("PATIENT_PER_DAY")
    @Expose
    private String patientPerDay;

    public DoctorList(String docCode, String docName, String degree, String desig, String specialization, String marketName, String marketCode, String address, String patientPerDay) {
        this.docCode = docCode;
        this.docName = docName;
        this.degree = degree;
        this.desig = desig;
        this.specialization = specialization;
        this.marketName = marketName;
        this.marketCode = marketCode;
        this.address = address;
        this.patientPerDay = patientPerDay;
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

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getDesig() {
        return desig;
    }

    public void setDesig(String desig) {
        this.desig = desig;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getMarketCode() {
        return marketCode;
    }

    public void setMarketCode(String marketCode) {
        this.marketCode = marketCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPatientPerDay() {
        return patientPerDay;
    }

    public void setPatientPerDay(String patientPerDay) {
        this.patientPerDay = patientPerDay;
    }
}
