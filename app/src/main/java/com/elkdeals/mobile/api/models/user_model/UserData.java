package com.elkdeals.mobile.api.models.user_model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class UserData {


    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("data")
    @Expose
    private List<UserDetails> data = null;
    @SerializedName("message")
    @Expose
    private String message;



    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<UserDetails> getData() {
        return data;
    }

    public void setData(List<UserDetails> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toJson() {
        Gson gson=new Gson();
        return gson.toJson(this);
    }
    public static UserData fromJson(String json){

        Gson gson=new Gson();try {
            return gson.fromJson(json, UserData.class);
        }
        catch (Exception e){
            return null;
        }
    }
}
