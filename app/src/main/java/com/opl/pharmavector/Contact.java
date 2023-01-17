package com.opl.pharmavector;

/**
 * Created by onik on 3/8/2018.
 */

public class Contact {

    //private variables
    int _id;
    String _fname;
    String _sname;
    String _phone_number;


    // Empty constructor
    public Contact(){
    }
    // constructor
    public Contact(int id, String fname, String sname){
        this._id = id;
        this._fname = fname;
        this._sname = sname;

    }
    // constructor
    public Contact(String fname, String sname){

        this._fname = fname;
        this._sname = sname;

    }

    public Contact(String fname, String sname,String tname){
        this._fname = fname;
        this._sname = sname;
        this._phone_number = tname;
    }




    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting first name
    public String getFName(){
        return this._fname;
    }

    // setting first name
    public void setFName(String fname){
        this._fname = fname;
    }

    // getting second name
    public String getSName(){
        return this._sname;
    }

    // setting first name
    public void setSName(String sname){
        this._sname = sname;
    }

    // getting phone number
    public String gettname(){
        return this._phone_number;
    }

    // setting phone number
    public void setPhoneNumber(String phone_number){
        this._phone_number = phone_number;
    }

}