package com.opl.pharmavector.mpoMenu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MenuRefreshModel {
    @SerializedName("categories")
    @Expose
    private List<MenuRefreshList> menuRefreshLists;

    public List<MenuRefreshList> getMenuRefreshLists() {
        return menuRefreshLists;
    }

    public void setMenuRefreshLists(List<MenuRefreshList> menuRefreshLists) {
        this.menuRefreshLists = menuRefreshLists;
    }
}
