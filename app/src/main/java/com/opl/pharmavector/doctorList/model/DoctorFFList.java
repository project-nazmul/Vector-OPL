package com.opl.pharmavector.doctorList.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorFFList {
    @SerializedName("MPO_CODE")
    @Expose
    private String mpoCode;
    @SerializedName("TERRI_NAME")
    @Expose
    private String terriName;

    public DoctorFFList(String mpoCode, String terriName) {
        this.mpoCode = mpoCode;
        this.terriName = terriName;
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
}
