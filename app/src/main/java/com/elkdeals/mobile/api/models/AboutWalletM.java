package com.elkdeals.mobile.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mohamed on 2/22/18.
 */

public class AboutWalletM {
    @SerializedName("about")
    @Expose
    String about;

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
