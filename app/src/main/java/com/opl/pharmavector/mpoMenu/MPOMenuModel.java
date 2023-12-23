package com.opl.pharmavector.mpoMenu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MPOMenuModel {
    @SerializedName("dashboard")
    @Expose
    private List<MPOMenuList> mpoMenuLists;

    public List<MPOMenuList> getMpoMenuLists() {
        return mpoMenuLists;
    }

    public void setMpoMenuLists(List<MPOMenuList> mpoMenuLists) {
        this.mpoMenuLists = mpoMenuLists;
    }
}
