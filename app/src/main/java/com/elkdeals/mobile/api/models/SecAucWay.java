package com.elkdeals.mobile.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SecAucWay {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("days")
    @Expose
    private Integer days;
    @SerializedName("months")
    @Expose
    private Integer months;
    @SerializedName("years")
    @Expose
    private Integer years;
    @SerializedName("install")
    @Expose
    private Integer install;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("max")
    @Expose
    private Integer max;
    @SerializedName("cash")
    @Expose
    private Integer cash;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getMonths() {
        return months;
    }

    public void setMonths(Integer months) {
        this.months = months;
    }

    public Integer getYears() {
        return years;
    }

    public void setYears(Integer years) {
        this.years = years;
    }

    public Integer getInstall() {
        return install;
    }

    public void setInstall(Integer install) {
        this.install = install;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getCash() {
        return cash;
    }

    public void setCash(Integer cash) {
        this.cash = cash;
    }


}
