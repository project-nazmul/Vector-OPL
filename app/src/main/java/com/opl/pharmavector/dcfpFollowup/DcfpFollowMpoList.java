package com.opl.pharmavector.dcfpFollowup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DcfpFollowMpoList {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("sl")
    @Expose
    private Integer sl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSl() {
        return sl;
    }

    public void setSl(Integer sl) {
        this.sl = sl;
    }
}
