package com.opl.pharmavector.msd_doc_support.adapter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MSDSubmitModel {
    @SerializedName("success_1")
    @Expose
    private Integer success_1;
    @SerializedName("ord_no")
    @Expose
    private Integer ord_no;
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private Integer message;
    @SerializedName("message_1")
    @Expose
    private String message_1;
    @SerializedName("message_2")
    @Expose
    private String message_2;

    public Integer getSuccess_1() {
        return success_1;
    }

    public void setSuccess_1(Integer success_1) {
        this.success_1 = success_1;
    }

    public Integer getOrd_no() {
        return ord_no;
    }

    public void setOrd_no(Integer ord_no) {
        this.ord_no = ord_no;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Integer getMessage() {
        return message;
    }

    public void setMessage(Integer message) {
        this.message = message;
    }

    public String getMessage_1() {
        return message_1;
    }

    public void setMessage_1(String message_1) {
        this.message_1 = message_1;
    }

    public String getMessage_2() {
        return message_2;
    }

    public void setMessage_2(String message_2) {
        this.message_2 = message_2;
    }
}
