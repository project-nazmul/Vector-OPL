package com.opl.pharmavector.report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LocationReportModel {
    @SerializedName("customer")
    @Expose
    private List<LocationReportList> locationReportList;

    public List<LocationReportList> getLocationReportList() {
        return locationReportList;
    }

    public void setLocationReportList(List<LocationReportList> locationReportList) {
        this.locationReportList = locationReportList;
    }
}
