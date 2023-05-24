package com.opl.pharmavector.msd_doc_support.adapter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MSDCommitmentModel {
    @SerializedName("serial")
    @Expose
    private Integer serial;
    @SerializedName("MSD_SLNO")
    @Expose
    private String msdSlno;
    @SerializedName("PROGRAM_DATE")
    @Expose
    private String programDate;
    @SerializedName("PROGRAM_TIME")
    @Expose
    private String programTime;
    @SerializedName("INST_DESC")
    @Expose
    private String instDesc;
    @SerializedName("VENUE")
    @Expose
    private String venue;
    @SerializedName("TOPIC")
    @Expose
    private String topic;
    @SerializedName("REGION")
    @Expose
    private String region;
    @SerializedName("COMMITMENT_TO")
    @Expose
    private String commitmentTo;
    @SerializedName("COMMITMENT_DURATION")
    @Expose
    private String commitmentDuration;
    @SerializedName("BRAND_NAME")
    @Expose
    private String brandName;

    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    public String getMsdSlno() {
        return msdSlno;
    }

    public void setMsdSlno(String msdSlno) {
        this.msdSlno = msdSlno;
    }

    public String getProgramDate() {
        return programDate;
    }

    public void setProgramDate(String programDate) {
        this.programDate = programDate;
    }

    public String getProgramTime() {
        return programTime;
    }

    public void setProgramTime(String programTime) {
        this.programTime = programTime;
    }

    public String getInstDesc() {
        return instDesc;
    }

    public void setInstDesc(String instDesc) {
        this.instDesc = instDesc;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCommitmentTo() {
        return commitmentTo;
    }

    public void setCommitmentTo(String commitmentTo) {
        this.commitmentTo = commitmentTo;
    }

    public String getCommitmentDuration() {
        return commitmentDuration;
    }

    public void setCommitmentDuration(String commitmentDuration) {
        this.commitmentDuration = commitmentDuration;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
