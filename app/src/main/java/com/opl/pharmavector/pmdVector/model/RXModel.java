package com.opl.pharmavector.pmdVector.model;

import java.io.Serializable;

public class RXModel implements Serializable{
   public String title;
   public String rating;
   public String picture;
   public String brandcount;
   public  String type;

    public RXModel(String type) {
        this.type = type;
    }
}
