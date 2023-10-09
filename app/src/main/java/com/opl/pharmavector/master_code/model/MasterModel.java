package com.opl.pharmavector.master_code.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.opl.pharmavector.master_code.MasterCode;

import java.util.List;

public class MasterModel {
    @SerializedName("categories")
    @Expose
    private List<MasterCList> masterCodeList;

    public List<MasterCList> getMasterCodeList() {
        return masterCodeList;
    }

    public void setMasterCodeList(List<MasterCList> masterCodeList) {
        this.masterCodeList = masterCodeList;
    }
}
