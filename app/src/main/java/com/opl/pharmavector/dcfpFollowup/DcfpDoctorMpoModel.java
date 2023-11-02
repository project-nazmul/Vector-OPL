package com.opl.pharmavector.dcfpFollowup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DcfpDoctorMpoModel {
    @SerializedName("mpo_list")
    @Expose
    private List<DcfpDoctorMpoList> dcfpDoctorMpoLists;

    public List<DcfpDoctorMpoList> getDcfpDoctorMpoLists() {
        return dcfpDoctorMpoLists;
    }

    public void setDcfpDoctorMpoLists(List<DcfpDoctorMpoList> dcfpDoctorMpoLists) {
        this.dcfpDoctorMpoLists = dcfpDoctorMpoLists;
    }
}
