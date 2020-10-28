package com.elkdeals.mobile.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mohamed on 10/23/17.
 */

public class NotificationModel {

    @SerializedName("id")
    @Expose
    Integer id;
    @SerializedName("title")
    @Expose
    String title;
    @SerializedName("date")
    @Expose
    String date;
    @SerializedName("type")
    @Expose
    Integer type;
    @SerializedName("status")
    @Expose
    Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
