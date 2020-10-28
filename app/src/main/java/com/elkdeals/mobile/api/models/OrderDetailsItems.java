package com.elkdeals.mobile.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetailsItems {
    @SerializedName("id") @Expose private int id;
    @SerializedName("name") @Expose private String name;
    @SerializedName("desc") @Expose private String desc;
    @SerializedName("price") @Expose private double price;
    @SerializedName("newprice") @Expose private double newprice;
    @SerializedName("hasoffer") @Expose private Boolean hasoffer;
    @SerializedName("offerper") @Expose private int offerper;
    @SerializedName("image") @Expose private String image;
    @SerializedName("sharelink") @Expose private String sharelink;
    @SerializedName("qty") @Expose private int qty;

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getNewprice() {
        return newprice;
    }

    public void setNewprice(double newprice) {
        this.newprice = newprice;
    }

    public Boolean getHasoffer() {
        return hasoffer;
    }

    public void setHasoffer(Boolean hasoffer) {
        this.hasoffer = hasoffer;
    }

    public int getOfferper() {
        return offerper;
    }

    public void setOfferper(int offerper) {
        this.offerper = offerper;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSharelink() {
        return sharelink;
    }

    public void setSharelink(String sharelink) {
        this.sharelink = sharelink;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}

