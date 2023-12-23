package com.opl.pharmavector.mpoMenu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MenuRefreshList {
    @SerializedName("CHK_STATUS")
    @Expose
    private String chkStatus;

    public String getChkStatus() {
        return chkStatus;
    }

    public void setChkStatus(String chkStatus) {
        this.chkStatus = chkStatus;
    }
}
