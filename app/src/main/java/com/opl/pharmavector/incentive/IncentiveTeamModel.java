package com.opl.pharmavector.incentive;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IncentiveTeamModel {
    @SerializedName("team")
    @Expose
    private List<IncentiveTeamList> incentiveTeamLists;

    public List<IncentiveTeamList> getIncentiveTeamLists() {
        return incentiveTeamLists;
    }

    public void setIncentiveTeamLists(List<IncentiveTeamList> incentiveTeamLists) {
        this.incentiveTeamLists = incentiveTeamLists;
    }
}
