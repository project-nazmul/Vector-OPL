package com.opl.pharmavector.personalExpense.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MotorCycleRate {
    @SerializedName("unit_exp")
    @Expose
    private String unit_exp;

    public MotorCycleRate(String unit_exp) {
        this.unit_exp = unit_exp;
    }

    public String getUnit_exp() {
        return unit_exp;
    }

    public void setUnit_exp(String unit_exp) {
        this.unit_exp = unit_exp;
    }
}
