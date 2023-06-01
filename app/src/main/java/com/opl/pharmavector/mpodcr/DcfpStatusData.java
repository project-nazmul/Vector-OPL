package com.opl.pharmavector.mpodcr;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DcfpStatusData {
    @SerializedName("STATUS")
    @Expose
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
