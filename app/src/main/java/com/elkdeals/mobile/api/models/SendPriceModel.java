
package com.elkdeals.mobile.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendPriceModel {
    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("idnumber")
    @Expose
    private long idnumber;
    @SerializedName("lastprice")
    @Expose
    private long lastprice;
    @SerializedName("bidcounts")
    @Expose
    private long bidcounts;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(long idnumber) {
        this.idnumber = idnumber;
    }

    public long getLastprice() {
        return lastprice;
    }

    public void setLastprice(long lastprice) {
        this.lastprice = lastprice;
    }

    public long getBidcounts() {
        return bidcounts;
    }

    public void setBidcounts(long bidcounts) {
        this.bidcounts = bidcounts;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}