package com.opl.pharmavector.promomat.model;

import com.google.gson.annotations.SerializedName;
import com.opl.pharmavector.R;

public class Promo {
    @SerializedName("serial")
    public String serial;

    @SerializedName("code")
    public String code;

    @SerializedName("month")
    public String month;

    @SerializedName("sample_name")
    public String sample_name;

    @SerializedName("pack_size")
    public String pack_size;

    @SerializedName("type")
    public String type;

    @SerializedName("week1")
    public String week1;

    @SerializedName("week2")
    public String week2;

    @SerializedName("week3")
    public String week3;

    @SerializedName("week4")
    public String week4;

    @SerializedName("total")
    public String total;

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

    public Promo(String serial, String code, String month, String sample_name, String pack_size, String type, String week1, String week2, String week3, String week4, String total, String aci) {
        this.serial = serial;
        this.code = code;
        this.month = month;
        this.sample_name = sample_name;
        this.pack_size = pack_size;
        this.type = type;
        this.week1 = week1;
        this.week2 = week2;
        this.week3 = week3;
        this.week4 = week4;
        this.total = total;
        this.aci = aci;
    }

    public Promo(String serial, String code, String month, String sample_name, String pack_size, String type,
                 String week1, String week2, String week3, String week4, String total)
    {
        this.serial = serial;
        this.code = code;
        this.month = month;
        this.sample_name = sample_name;
        this.pack_size = pack_size;
        this.type = type;
        this.week1 = week1;
        this.week2 = week2;
        this.week3 = week3;
        this.week4 = week4;
        this.total = total;
    }


    public Promo(String serial, String code, String month, String sample_name, String pack_size, String type,
                 String week1, String week2, String week3, String week4, String total,
                 String aci,String ari,String pop,String rad,String osl)
    {
        this.serial = serial;
        this.code = code;
        this.month = month;
        this.sample_name = sample_name;
        this.pack_size = pack_size;
        this.type = type;
        this.week1 = week1;
        this.week2 = week2;
        this.week3 = week3;
        this.week4 = week4;
        this.total = total;
        this.aci = aci;
        this.ari =ari;
        this.pop = pop;
        this.rad = rad;
        this.osl = osl;
    }


    public Promo(String serial, String code, String month, String sample_name, String pack_size, String type,
                 String week1, String week2, String week3, String week4, String total,
                 String aci,String ari,String pop,String rad,String osl,String base, String oplbase, String oplshare)
    {
        this.serial = serial;
        this.code = code;
        this.month = month;
        this.sample_name = sample_name;
        this.pack_size = pack_size;
        this.type = type;
        this.week1 = week1;
        this.week2 = week2;
        this.week3 = week3;
        this.week4 = week4;
        this.total = total;
        this.aci = aci;
        this.ari =ari;
        this.pop = pop;
        this.rad = rad;
        this.osl = osl;
        this.base = base;
        this.oplbase = oplbase;
        this.oplshare = oplshare;

    }








    public String getSerial() {
        return serial;
    }
    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }



    public String getMonth() {
        return month;
    }
    public void setMonth(String month) {
        this.month = month;
    }


    public String getSample_name() {
        return sample_name;
    }
    public void setSample_name(String sample_name) {
        this.sample_name = sample_name;
    }

    public String getWeek3() {
        return week3;
    }
    public void setWeek3(String week3) {
        this.week3 = week3;
    }

    public String getWeek2() {
        return week2;
    }
    public void setWeek2(String week2) {
        this.week2 = week2;
    }


    public String getWeek1() {
        return week1;
    }
    public void setWeek1(String week1) {
        this.week1 = week1;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
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

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
