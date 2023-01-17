package com.opl.pharmavector.model;

public class Category {


    private String cateogry_sl;
    private String cateogry_id;
    private String category_Name;
    private String category_Name2;
    private String category_Name3;
    private boolean isSelected;

    public String getCateogry_id() {
        return cateogry_id;
    }
    public void setCateogry_id(String cateogry_id) {
        this.cateogry_id = cateogry_id;
    }



    public String getCateogry_sl() {
        return cateogry_sl;
    }
    public void setCateogry_sl(String cateogry_sl) {
        this.cateogry_sl = cateogry_sl;
    }

    public String getCategory_Name() {
        return category_Name;
    }
    public void setCategory_Name(String category_Name) {
        this.category_Name = category_Name;
    }


    public String getCategory_Name2() {
        return category_Name2;
    }
    public void setCategory_Name2(String category_Name2) {
        this.category_Name2 = category_Name2;
    }


    public String getCategory_Name3() {
        return category_Name3;
    }
    public void setCategory_Name3(String category_Name3) {
        this.category_Name3 = category_Name3;
    }


    public boolean isSelected() {
        return isSelected;
    }
    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}