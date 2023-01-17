package com.opl.pharmavector.doctorgift;

public class Doctor {

    private String doctor_code;
    private String doctor_name;
    private String terri_name;
    public Doctor(String doctor_code, String doctor_name,String terri_name){
        this.doctor_code =doctor_code;
        this.doctor_name = doctor_name;
        this.terri_name =terri_name;
    }

    public String getDoctorCode(){
        return doctor_code;
    }
    public void setDoctorCode(String doctor_code){
        this.doctor_code = doctor_code;
    }

    public String getDoctorName(){
        return doctor_name;
    }
    public void setDoctorName(String doctor_name){
        this.doctor_name = doctor_name;
    }
    public String getDoctorTerriName(){
        return terri_name;
    }
    public void setDoctorTerriName(String terri_name){
        this.terri_name = terri_name;
    }

}
