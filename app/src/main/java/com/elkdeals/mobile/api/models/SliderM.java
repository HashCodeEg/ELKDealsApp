package com.elkdeals.mobile.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SliderM {

    @Expose@SerializedName("msg")
    private String msg;

    public String getMsg() {
         return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
