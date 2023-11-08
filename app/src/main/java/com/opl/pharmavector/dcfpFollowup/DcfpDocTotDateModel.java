package com.opl.pharmavector.dcfpFollowup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DcfpDocTotDateModel {
    @SerializedName("categories")
    @Expose
    private List<DcfpDocTotDateList> dcfpTotDateLists;

    public List<DcfpDocTotDateList> getDcfpTotDateLists() {
        return dcfpTotDateLists;
    }

    public void setDcfpTotDateLists(List<DcfpDocTotDateList> dcfpTotDateLists) {
        this.dcfpTotDateLists = dcfpTotDateLists;
    }
}
