package com.opl.pharmavector.mrd_pres_report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SPIReportModel {
    @SerializedName("categories")
    @Expose
    private List<SPIReportList> spiReportLists;

    public List<SPIReportList> getSpiReportLists() {
        return spiReportLists;
    }

    public void setSpiReportLists(List<SPIReportList> spiReportLists) {
        this.spiReportLists = spiReportLists;
    }
}
