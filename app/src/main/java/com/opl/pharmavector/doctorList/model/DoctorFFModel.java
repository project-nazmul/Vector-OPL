package com.opl.pharmavector.doctorList.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class DoctorFFModel {
    @SerializedName("categories")
    @Expose
    private List<DoctorFFList> categoriesList;

    public List<DoctorFFList> getCategoriesList() {
        return categoriesList;
    }

    public void setCategoriesList(List<DoctorFFList> categoriesList) {
        this.categoriesList = categoriesList;
    }
}
