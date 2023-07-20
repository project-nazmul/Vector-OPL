package com.opl.pharmavector.prescriber;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenericTypeModel {
    @SerializedName("customer")
    @Expose
    private List<GenericTypeList> genericLists;

    public List<GenericTypeList> getGenericLists() {
        return genericLists;
    }

    public void setGenericLists(List<GenericTypeList> genericLists) {
        this.genericLists = genericLists;
    }
}
