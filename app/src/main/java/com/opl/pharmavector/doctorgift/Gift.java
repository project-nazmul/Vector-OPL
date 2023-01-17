package com.opl.pharmavector.doctorgift;

public class Gift {

    private String id;
    private String name;
    private String catagories;
    private String ppm_code;
    private String brand_code;

    public Gift(String id, String name, String ppm_code, String brand_code,String catagories){
        this.id = id;
        this.name = name;
        this.catagories = catagories;
        this.ppm_code = ppm_code;
        this.brand_code = brand_code;

    }

    public void setId(String id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getPpmCode(){
        return ppm_code;
    }

    public void setPpmCode(String ppm_code){
        this.ppm_code = ppm_code;
    }
    public String getCatagories(){
        return catagories;
    }

    public void setCatagories(String catagories){
        this.catagories = catagories;
    }
    public String getBrandCode(){
        return brand_code;
    }

    public void setBrandCode(String brand_code){
        this.brand_code = brand_code;
    }
}
