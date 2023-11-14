package com.opl.pharmavector;

public class Territory {
    String _territory_name;

    public Territory() {}

    public Territory(String tname){
        this._territory_name = tname;
    }

    public String gettname(){
        return this._territory_name;
    }

    public void settname(String phone_number){
        this._territory_name = phone_number;
    }
}