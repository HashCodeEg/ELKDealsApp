package com.elkdeals.mobile.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mohamed on 2/21/18.
 */

public class WalletM {
    @SerializedName("status")
    @Expose
    boolean status;
    @SerializedName("msg")
    @Expose
    String msg;
    @SerializedName("code")
    @Expose
    String code;
    @SerializedName("amount")
    @Expose
    float amount;
    @SerializedName("number")
    @Expose
    int number;
    @SerializedName("id")
    @Expose
    int id;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

