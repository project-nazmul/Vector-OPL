package com.opl.pharmavector.doctorgift;

public class MPO {

    private String mpo_code;
    private String mpo_name;
    public MPO(String mpo_code, String mpo_name){
        this.mpo_code = mpo_code;
        this.mpo_name = mpo_name;
    }

    public String getMPOcode(){
        return mpo_code;
    }
    public void setMPOcode(String mpo_code){
        this.mpo_code = mpo_code;
    }

    public String getMPOname(){
        return mpo_name;
    }
    public void setMPOname(String mpo_name){
        this.mpo_name = mpo_name;
    }
}
