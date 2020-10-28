package com.elkdeals.mobile.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Top {
    @SerializedName("id") @Expose private Integer id;
    @SerializedName("name") @Expose private String name;
    @SerializedName("desc") @Expose private String desc;
    @SerializedName("price") @Expose private Double price;
    @SerializedName("newprice") @Expose private Double newprice;
    @SerializedName("image") @Expose private String image;
    @SerializedName("sharelink") @Expose private String sharelink;
    @SerializedName("hasoffer") @Expose private boolean hasOffer;
    @SerializedName("offerper") @Expose private int offerPercent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getNewprice() {
        return newprice;
    }

    public void setNewprice(Double newprice) {
        this.newprice = newprice;
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

    public boolean isHasOffer() {
        return hasOffer;
    }

    public void setHasOffer(boolean hasOffer) {
        this.hasOffer = hasOffer;
    }

    public int getOfferPercent() {
        return offerPercent;
    }

    public void setOfferPercent(int offerPercent) {
        this.offerPercent = offerPercent;
    }
}

