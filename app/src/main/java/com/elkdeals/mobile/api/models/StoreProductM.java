package com.elkdeals.mobile.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StoreProductM {
    /*
    * obj.addProperty("id", pro.getProId());
                    obj.addProperty("", pro.getEname());
                    obj.addProperty("", pro.getAname());
                    obj.addProperty("endesc", pro.getEdesc());
                    obj.addProperty("", pro.getAdesc());
                    obj.addProperty("", pro.getImage1());
                    obj.addProperty("img2", pro.getImage2());
                    obj.addProperty("img3", pro.getImage3());
                    obj.addProperty("lot", pro.getLot());
                    obj.addProperty("video", pro.getVideo());
                    obj.addProperty("phone", pro.getPhone() == null ? "19157" : pro.getPhone());
                    obj.addProperty("lat", pro.getLatitude() + "");
                    obj.addProperty("lang", pro.getLongitude() + "");
                    obj.addProperty("price", pro.getPrice() + "");*/
    @SerializedName("id") @Expose private Integer id;
    @SerializedName("arname") @Expose private String name;
    @SerializedName("ardesc") @Expose private String desc;
    @SerializedName("price") @Expose private Double price;
    @SerializedName("newprice") @Expose private Double newprice;
    @SerializedName("hasoffer") @Expose private Boolean hasoffer;
    @SerializedName("img1") @Expose private String image;
    @SerializedName("sharelink") @Expose private String shareLink;
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

    public Boolean getHasoffer() {
        return hasoffer;
    }

    public void setHasoffer(Boolean hasoffer) {
        this.hasoffer = hasoffer;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShareLink() {
        return shareLink;
    }

    public void setShareLink(String shareLink) {
        this.shareLink = shareLink;
    }

    public int getOfferPercent() {
        return offerPercent;
    }

    public void setOfferPercent(int offerPercent) {
        this.offerPercent = offerPercent;
    }
}
