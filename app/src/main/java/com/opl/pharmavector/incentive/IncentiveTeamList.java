package com.opl.pharmavector.incentive;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IncentiveTeamList {
    @SerializedName("TEAM_NAME")
    @Expose
    private String teamName;
    @SerializedName("TEAM_CODE")
    @Expose
    private String teamCode;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamCode() {
        return teamCode;
    }

    public void setTeamCode(String teamCode) {
        this.teamCode = teamCode;
    }
}
