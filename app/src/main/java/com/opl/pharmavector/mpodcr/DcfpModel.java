package com.opl.pharmavector.mpodcr;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class DcfpModel {
    @SerializedName("customer")
    @Expose
    private List<DcfpList> dcfpLists;

    public List<DcfpList> getDcfpLists() {
        return dcfpLists;
    }

    public void setDcfpLists(List<DcfpList> dcfp) {
        this.dcfpLists = dcfp;
    }
}
