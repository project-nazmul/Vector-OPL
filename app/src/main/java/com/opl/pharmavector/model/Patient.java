package com.opl.pharmavector.model;

import com.google.gson.annotations.SerializedName;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;

public class Patient {
    @SerializedName("id")
    private int id;
    @SerializedName("first_name")
    private String first_name;
    @SerializedName("last_name")
    private String last_name;
    @SerializedName("phone_number")
    private String phone_number;
    @SerializedName("gender")
    private String gender;
    @SerializedName("birth")
    private String birth;
    @SerializedName("picture")
    private String picture;
    @SerializedName("password")
    private String password;
    @SerializedName("email")
    private String email;
    @SerializedName("status")
    private String status;
    @SerializedName("love")
    private Boolean love;
    @SerializedName("value")
    private String value;
    @SerializedName("message")
    private String massage;
    @SerializedName("fees")
    private String fees;
    @SerializedName("schedule")
    private String schedule;
    @SerializedName("scheduleid")
    private String scheduleid;
    @SerializedName("doctorcode")
    private String doctorcode;
    @SerializedName("success")
    private int success;
    @SerializedName("message_1")
    private String message_1;
    @SerializedName("message_3")
    private String message_3;
    @SerializedName("message_4")
    private String message_4;
    @SerializedName("new_version")
    private String new_version;
    @SerializedName("serial")
    private String serial;
    @SerializedName("mpo_code")
    private String mpo_code;
    @SerializedName("month")
    private String month;
    @SerializedName("pack_size")
    private String pack_size;
    @SerializedName("sample_name")
    private String sample_name;
    @SerializedName("total")
    private String total;
    @SerializedName("type")
    private String type;
    @SerializedName("week1")
    private String week1;
    @SerializedName("week2")
    private String week2;
    @SerializedName("week3")
    private String week3;
    @SerializedName("week4")
    private String week4;
    @SerializedName("aci")
    public String aci;
    @SerializedName("ari")
    public String ari;
    @SerializedName("pop")
    public String pop;
    @SerializedName("rad")
    public String rad;
    @SerializedName("osl")
    public String osl;
    @SerializedName("oth")
    public String oth;
    @SerializedName("base")
    public String base;
    @SerializedName("oplbase")
    public String oplbase;
    @SerializedName("oplshare")
    public String oplshare;

    @SerializedName("designation")
    public String designation;

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getnew_version() {
        return new_version;
    }

    public void setnew_version(String new_version) {
        this.new_version = new_version;
    }

    public String getFF_type() {
        return message_4;
    }

    public void setFF_type(String message_4) {
        this.message_4 = message_4;
    }

    public String getFF_role() {
        return message_3;
    }

    public void setFF_role(String message_3) {
        this.message_3 = message_3;
    }

    public String getTerritory_name() {
        return message_1;
    }

    public void setTerritory_name(String message_1) {
        this.message_1 = message_1;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public Boolean getLove() {
        return love;
    }

    public void setLove(Boolean love) {
        this.love = love;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getScheduleID() {
        return scheduleid;
    }

    public void setSchedulID(String scheduleid) {
        this.scheduleid = scheduleid;
    }

    public String getDoctorCode() {
        return doctorcode;
    }

    public void setDoctorCode(String doctorcode) {
        this.doctorcode = doctorcode;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getMpocode() {
        return mpo_code;
    }

    public void setMpocode(String mpo_code) {
        this.mpo_code = mpo_code;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getSamplename() {
        return sample_name;
    }

    public void setSamplename(String sample_name) {
        this.sample_name = sample_name;
    }

    public String getPacksize() {
        return pack_size;
    }

    public void setPacksize(String pack_size) {
        this.pack_size = pack_size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getWeek1() {
        return week1;
    }

    public void setWeek1(String week1) {
        this.week1 = week1;
    }

    public String getWeek2() {
        return week2;
    }

    public void setWeek2(String week2) {
        this.week2 = week2;
    }

    public String getWeek3() {
        return week3;
    }

    public void setWeek3(String week3) {
        this.week3 = week3;
    }

    public String getWeek4() {
        return week4;
    }

    public void setWeek4(String week2) {
        this.week4 = week4;
    }

    public String getAci() {
        return aci;
    }

    public void setAci(String aci) {
        this.aci = aci;
    }

    public String getAristo() {
        return ari;
    }

    public void setAristo(String ari) {
        this.ari = ari;
    }

    public String getPopular() {
        return pop;
    }

    public void setPopular(String pop) {
        this.pop = pop;
    }

    public String getRadient() {
        return rad;
    }

    public void setRadient(String rad) {
        this.rad = rad;
    }

    public String getOsl() {
        return osl;
    }

    public void setOsl(String osl) {
        this.osl = osl;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getOplBase() {
        return oplbase;
    }

    public void setOplBase(String oplbase) {
        this.oplbase = oplbase;
    }

    public String getOplShare() {
        return oplshare;
    }

    public void setOplShare(String oplshare) {
        this.oplshare = oplshare;
    }
}
