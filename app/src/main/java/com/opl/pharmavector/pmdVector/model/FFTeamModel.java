package com.opl.pharmavector.pmdVector.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class FFTeamModel {
    @SerializedName("team")
    @Expose
    private List<FFTeamList> team;

    public List<FFTeamList> getTeam() {
        return team;
    }

    public void setTeam(List<FFTeamList> team) {
        this.team = team;
    }
}
