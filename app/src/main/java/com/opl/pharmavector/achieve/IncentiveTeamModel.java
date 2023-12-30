package com.opl.pharmavector.achieve;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IncentiveTeamModel {
    @SerializedName("team")
    @Expose
    private List<IncentiveTeamLists> incentiveTeamLists;

    public List<IncentiveTeamLists> getIncentiveTeamLists() {
        return incentiveTeamLists;
    }

    public void setIncentiveTeamLists(List<IncentiveTeamLists> incentiveTeamLists) {
        this.incentiveTeamLists = incentiveTeamLists;
    }
}
