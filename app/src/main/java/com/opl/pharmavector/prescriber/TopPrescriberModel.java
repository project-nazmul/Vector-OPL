package com.opl.pharmavector.prescriber;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopPrescriberModel {
    @SerializedName("customer")
    @Expose
    private List<TopPrescriberList> prescriberListList;

    public List<TopPrescriberList> getPrescriberListList() {
        return prescriberListList;
    }

    public void setPrescriberListList(List<TopPrescriberList> prescriberListList) {
        this.prescriberListList = prescriberListList;
    }
}
