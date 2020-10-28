package com.elkdeals.mobile.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HistoryM {
    @SerializedName("credit")
    @Expose
    float credit;
    @SerializedName("dibet")
    @Expose
    float dibet;
    @SerializedName("notes")
    @Expose
    String notes;
    @SerializedName("transnumber")
    @Expose
    double transnumber;
    @SerializedName("date")
    @Expose
    String date;

    public float getCredit() {
        return credit;
    }

    public void setCredit(float credit) {
        this.credit = credit;
    }

    public float getDibet() {
        return dibet;
    }

    public void setDibet(float dibet) {
        this.dibet = dibet;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public double getTransnumber() {
        return transnumber;
    }

    public void setTransnumber(double transnumber) {
        this.transnumber = transnumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
