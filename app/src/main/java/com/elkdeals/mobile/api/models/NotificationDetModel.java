package com.elkdeals.mobile.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mohamed on 10/23/17.
 */

public class NotificationDetModel {
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
    @SerializedName("image")
    @Expose
    String image;
    @SerializedName("video")
    @Expose
    String video;
    @SerializedName("text")
    @Expose
    String text;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
