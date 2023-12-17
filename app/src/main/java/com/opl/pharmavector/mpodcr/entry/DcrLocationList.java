package com.opl.pharmavector.mpodcr.entry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DcrLocationList {
    @SerializedName("FF_CODE")
    @Expose
    private String ffCode;
    @SerializedName("FF_NAME")
    @Expose
    private String ffName;

    public String getFfCode() {
        return ffCode;
    }

    public void setFfCode(String ffCode) {
        this.ffCode = ffCode;
    }

    public String getFfName() {
        return ffName;
    }

    public void setFfName(String ffName) {
        this.ffName = ffName;
    }
}
