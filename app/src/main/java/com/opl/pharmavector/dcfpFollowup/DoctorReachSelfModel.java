package com.opl.pharmavector.dcfpFollowup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DoctorReachSelfModel {
    @SerializedName("categories")
    @Expose
    private List<DoctorReachSelfList> doctorSelfLists;

    public List<DoctorReachSelfList> getDoctorSelfLists() {
        return doctorSelfLists;
    }

    public void setDoctorSelfLists(List<DoctorReachSelfList> doctorSelfLists) {
        this.doctorSelfLists = doctorSelfLists;
    }
}
