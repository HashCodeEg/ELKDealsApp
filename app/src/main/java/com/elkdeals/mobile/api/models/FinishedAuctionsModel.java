
package com.elkdeals.mobile.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FinishedAuctionsModel {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("current")
    @Expose
    private Integer current;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("cname")
    @Expose
    private String cname;
    @SerializedName("idnumber")
    @Expose
    private String idnumber;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("netprice")
    @Expose
    private String netprice;
    @SerializedName("auctionprice")
    @Expose
    private String auctionprice;
    @SerializedName("months")
    @Expose
    private String months;
    @SerializedName("installment")
    @Expose
    private String installment;
    @SerializedName("enddate")
    @Expose
    private String enddate;

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

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getNetprice() {
        return netprice;
    }

    public void setNetprice(String netprice) {
        this.netprice = netprice;
    }

    public String getAuctionprice() {
        return auctionprice;
    }

    public void setAuctionprice(String auctionprice) {
        this.auctionprice = auctionprice;
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    public String getInstallment() {
        return installment;
    }

    public void setInstallment(String installment) {
        this.installment = installment;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

}