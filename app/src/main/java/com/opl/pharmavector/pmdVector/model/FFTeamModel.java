package com.opl.pharmavector.pmdVector.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class FFTeamModel {
    @SerializedName("team")
    @Expose
    private List<FFTeamList> teamList;

    public List<FFTeamList> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<FFTeamList> teamList) {
        this.teamList = teamList;
    }
}
