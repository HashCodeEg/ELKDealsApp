package com.elkdeals.mobile.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mohamed on 4/8/17.
 */

public class ProfileImageModel {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("image")
    @Expose
    private String image;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
