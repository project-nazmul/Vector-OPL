package com.opl.pharmavector.chemistList.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChemistModel {
    @SerializedName("chemist_data")
    @Expose
    private List<ChemistList> chemistLists;

    public List<ChemistList> getChemistLists() {
        return chemistLists;
    }

    public void setChemistLists(List<ChemistList> chemistLists) {
        this.chemistLists = chemistLists;
    }
}
