package com.opl.pharmavector.prescriber;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenericTypeList {
    @SerializedName("GEN_DESC")
    @Expose
    private String genDesc;

    public String getGenDesc() {
        return genDesc;
    }

    public void setGenDesc(String genDesc) {
        this.genDesc = genDesc;
    }

    @NonNull
    @Override
    public String toString() {
        return "GenericTypeList{" +
                "genDesc='" + genDesc + '\'' +
                '}';
    }
}
