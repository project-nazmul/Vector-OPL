package com.opl.pharmavector.mpodcr.entry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DcrNatureList {
    @SerializedName("TN_CODE")
    @Expose
    private String tnCode;
    @SerializedName("TN_DESC")
    @Expose
    private String tnDesc;

    public String getTnCode() {
        return tnCode;
    }

    public void setTnCode(String tnCode) {
        this.tnCode = tnCode;
    }

    public String getTnDesc() {
        return tnDesc;
    }

    public void setTnDesc(String tnDesc) {
        this.tnDesc = tnDesc;
    }
}
