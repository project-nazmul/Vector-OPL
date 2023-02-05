package com.opl.pharmavector.personalExpense.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MotorCycleModel {
    @SerializedName("exp_amount")
    @Expose
    private List<MotorCycleRate> expAmount;

    public List<MotorCycleRate> getExpAmount() {
        return expAmount;
    }

    public void setExpAmount(List<MotorCycleRate> expAmount) {
        this.expAmount = expAmount;
    }
}
