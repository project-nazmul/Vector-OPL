package com.opl.pharmavector.util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PopUpUrlModel {
    @SerializedName("FLAG")
    @Expose
    private String flag;
    @SerializedName("TITLE")
    @Expose
    private String title;
    @SerializedName("MESSAGE")
    @Expose
    private String message;
    @SerializedName("IMAGE_URL")
    @Expose
    private String imageUrl;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
