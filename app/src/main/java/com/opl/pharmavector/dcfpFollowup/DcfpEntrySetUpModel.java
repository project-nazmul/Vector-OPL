package com.opl.pharmavector.dcfpFollowup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class DcfpEntrySetUpModel {
    @SerializedName("setUpLists")
    @Expose
    private List<DcfpEntrySetUpList> setUpLists;

    public List<DcfpEntrySetUpList> getSetUpLists() {
        return setUpLists;
    }

    public void setSetUpLists(List<DcfpEntrySetUpList> setUpLists) {
        this.setUpLists = setUpLists;
    }
}
