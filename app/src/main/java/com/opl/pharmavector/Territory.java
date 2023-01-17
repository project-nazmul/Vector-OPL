package com.opl.pharmavector;


/**
 * Created by onik on 3/8/2018.
 */

public class Territory {



    String _territory_name;


    // Empty constructor
    public Territory(){
    }
    // constructor


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