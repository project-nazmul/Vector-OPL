package com.opl.pharmavector.dcfpFollowup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MpoDcfpFollowModel {
    @SerializedName("customer")
    @Expose
    private List<MpoDcfpFollowList> mpoDcfpFollowList;

    public List<MpoDcfpFollowList> getMpoDcfpFollowList() {
        return mpoDcfpFollowList;
    }

    public void setMpoDcfpFollowList(List<MpoDcfpFollowList> mpoDcfpFollowList) {
        this.mpoDcfpFollowList = mpoDcfpFollowList;
    }
}
