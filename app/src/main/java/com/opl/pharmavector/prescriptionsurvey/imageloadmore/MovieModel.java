package com.opl.pharmavector.prescriptionsurvey.imageloadmore;

import java.io.Serializable;

public class MovieModel implements Serializable{
    String title;
    String rating;
    String picture;
    String brandcount;
    String type;

    public MovieModel(String type) {
        this.type = type;
    }
}
