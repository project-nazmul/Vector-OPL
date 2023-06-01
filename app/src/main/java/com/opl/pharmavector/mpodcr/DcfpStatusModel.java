package com.opl.pharmavector.mpodcr;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.opl.pharmavector.Customer;
import java.util.List;

public class DcfpStatusModel {
    @SerializedName("customer")
    @Expose
    private List<DcfpStatusData> customer;

    public List<DcfpStatusData> getCustomer() {
        return customer;
    }

    public void setCustomer(List<DcfpStatusData> customer) {
        this.customer = customer;
    }
}

