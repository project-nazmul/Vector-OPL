package com.opl.pharmavector.dcfpFollowup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DcfpDoctorReportModel {
    @SerializedName("customer")
    @Expose
    private List<DcfpDoctorReportList> dcfpDoctorLists;

    public List<DcfpDoctorReportList> getDcfpDoctorLists() {
        return dcfpDoctorLists;
    }

    public void setDcfpDoctorLists(List<DcfpDoctorReportList> dcfpDoctorLists) {
        this.dcfpDoctorLists = dcfpDoctorLists;
    }
}
