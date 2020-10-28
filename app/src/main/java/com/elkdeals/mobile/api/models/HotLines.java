package com.elkdeals.mobile.api.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mohamed on 8/1/17.
 */

@Entity
public class HotLines {

    @PrimaryKey
    @NonNull
    @SerializedName("phone")
    @Expose
    String mobile="19157";


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


}
