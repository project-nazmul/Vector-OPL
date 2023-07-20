package com.opl.pharmavector.prescriber;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FieldForceModel {
    @SerializedName("customer")
    @Expose
    private List<FieldForceList> fieldForceLists;

    public List<FieldForceList> getFieldForceLists() {
        return fieldForceLists;
    }

    public void setFieldForceLists(List<FieldForceList> fieldForceLists) {
        this.fieldForceLists = fieldForceLists;
    }
}
