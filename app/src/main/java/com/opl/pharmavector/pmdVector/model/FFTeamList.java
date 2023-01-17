package com.opl.pharmavector.pmdVector.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FFTeamList {
    @SerializedName("team_name")
    @Expose
    private String teamName;

    @SerializedName("team_code")
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
