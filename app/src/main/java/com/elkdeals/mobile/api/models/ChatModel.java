package com.elkdeals.mobile.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mohamed on 10/12/17.
 */

public class ChatModel {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("sendertype")
    @Expose
    private Integer sendertype;
    @SerializedName("clientname")
    @Expose
    private String clientname;
    @SerializedName("uname")
    @Expose
    private String uname;
    @SerializedName("date")
    @Expose
    private String date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSendertype() {
        return sendertype;
    }

    public void setSendertype(Integer sendertype) {
        this.sendertype = sendertype;
    }

    public String getClientname() {
        return clientname;
    }

    public void setClientname(String clientname) {
        this.clientname = clientname;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
