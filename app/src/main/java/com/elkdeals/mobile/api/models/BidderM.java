package com.elkdeals.mobile.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mohamed on 3/18/18.
 */

public class BidderM {
    @SerializedName("id")
    @Expose
    int id;
    @SerializedName("name")
    @Expose
    String name;
    @SerializedName("date")
    @Expose
    String date;
    @SerializedName("price")
    @Expose
    int price;
    @SerializedName("cid")
    @Expose
    int cid;
    @SerializedName("pricex")
    @Expose
    int pricex;

    @SerializedName("cashpricex") @Expose int cashpricex;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getPricex() {
        return pricex;
    }

    public int getCashpricex() {
        return cashpricex;
    }

    public void setCashpricex(int cashpricex) {
        this.cashpricex = cashpricex;
    }

    public void setPricex(int pricex) {
        this.pricex = pricex;
    }
}
