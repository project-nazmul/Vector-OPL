package com.opl.pharmavector.doctorList.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class DoctorModel {
    @SerializedName("array_data")
    @Expose
    private List<DoctorList> doctorList;

    public List<DoctorList> getDoctorList() {
        return doctorList;
    }

    public void setDoctorList(List<DoctorList> doctorList) {
        this.doctorList = doctorList;
    }
}
