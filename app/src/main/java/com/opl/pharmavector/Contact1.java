package com.opl.pharmavector;


public class Contact1 {
    String name;
    String image;
    String phone;

    String territory;

    private boolean isSelected;

    public Contact1() {
    }

    public String getName() {

        return name;
    }

    public String getImage() {

        return image;
    }

    public String getPhone() {

        return phone;
    }

    public String getterritory() {

        return territory;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }



    public void setPhone(String phone) {
        this.phone = phone;
    }


    public void setterritory(String phone) {
        this.territory = territory;
    }





    public boolean getSelected() {

        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

}
