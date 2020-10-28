package com.elkdeals.mobile.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StoreCartModel {

    @SerializedName("id") @Expose double id;
    @SerializedName("name") @Expose String name;
    @SerializedName("desc") @Expose String description;
    @SerializedName("price") @Expose double price;
    @SerializedName("hasoffer") @Expose boolean hasOffer;
    @SerializedName("offerper") @Expose double offerPercent;
    @SerializedName("image") @Expose String image;
    @SerializedName("sharelink") @Expose String shareLink;
    @SerializedName("qty") @Expose double quantity;

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isHasOffer() {
        return hasOffer;
    }

    public void setHasOffer(boolean hasOffer) {
        this.hasOffer = hasOffer;
    }

    public double getOfferPercent() {
        return offerPercent;
    }

    public void setOfferPercent(double offerPercent) {
        this.offerPercent = offerPercent;
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

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
