package com.elkdeals.mobile.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mohamed on 12/6/17.
 */

public class StoreProductModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("enname")
    @Expose
    private String enname;
    @SerializedName("arname")
    @Expose
    private String arname;
    @SerializedName("endesc")
    @Expose
    private String endesc;
    @SerializedName("ardesc")
    @Expose
    private String ardesc;
    @SerializedName("img1")
    @Expose
    private String img1;
    @SerializedName("img2")
    @Expose
    private String img2;
    @SerializedName("img3")
    @Expose
    private String img3;
    @SerializedName("lot")
    @Expose
    private String lot;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lang")
    @Expose
    private String lang;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEnname() {
        return enname;
    }

    public void setEnname(String enname) {
        this.enname = enname;
    }

    public String getArname() {
        return arname;
    }

    public void setArname(String arname) {
        this.arname = arname;
    }

    public String getEndesc() {
        return endesc;
    }

    public void setEndesc(String endesc) {
        this.endesc = endesc;
    }

    public String getArdesc() {
        return ardesc;
    }

    public void setArdesc(String ardesc) {
        this.ardesc = ardesc;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getDescription() {
        return getEndesc();
    }

    public String getName() {
        return getArname();
    }
}

