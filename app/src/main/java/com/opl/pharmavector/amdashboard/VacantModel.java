package com.opl.pharmavector.amdashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class VacantModel {
    @SerializedName("customer")
    @Expose
    private List<VacantList> vacantLists;

    public List<VacantList> getVacantLists() {
        return vacantLists;
    }

    public void setVacantLists(List<VacantList> vacant) {
        this.vacantLists = vacant;
    }
}
