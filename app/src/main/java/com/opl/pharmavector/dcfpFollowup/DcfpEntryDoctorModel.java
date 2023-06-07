package com.opl.pharmavector.dcfpFollowup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class DcfpEntryDoctorModel {
    @SerializedName("customer")
    @Expose
    private List<DcfpEntryDoctorList> doctorLists;

    public List<DcfpEntryDoctorList> getDoctorLists() {
        return doctorLists;
    }

    public void setDoctorLists(List<DcfpEntryDoctorList> doctorLists) {
        this.doctorLists = doctorLists;
    }
}

