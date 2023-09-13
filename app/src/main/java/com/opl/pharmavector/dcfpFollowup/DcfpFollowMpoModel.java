package com.opl.pharmavector.dcfpFollowup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DcfpFollowMpoModel {
    @SerializedName("customer")
    @Expose
    private List<DcfpFollowMpoList> dcfpFollowMpoList;

    public List<DcfpFollowMpoList> getDcfpFollowMpoList() {
        return dcfpFollowMpoList;
    }

    public void setDcfpFollowMpoList(List<DcfpFollowMpoList> dcfpFollowMpoList) {
        this.dcfpFollowMpoList = dcfpFollowMpoList;
    }
}
