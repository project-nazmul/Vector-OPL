package com.opl.pharmavector.prescriber;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FromDateModel {
    @SerializedName("customer")
    @Expose
    private List<FromDateList> fromDateLists;

    public List<FromDateList> getFromDateLists() {
        return fromDateLists;
    }

    public void setFromDateLists(List<FromDateList> fromDateLists) {
        this.fromDateLists = fromDateLists;
    }
}
