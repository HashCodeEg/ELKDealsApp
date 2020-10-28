
package com.elkdeals.mobile.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InvoiceAddResponse2 {

    @SerializedName("status")
    @Expose
    private long status;
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("rate")
    @Expose
    private long rate;
    @SerializedName("total")
    @Expose
    private long total;
    @SerializedName("ptype")
    @Expose
    private long ptype;
    @SerializedName("discount")
    @Expose
    private long discount;
    @SerializedName("bkid")
    @Expose
    private String bkid;
    @SerializedName("accountnumber")
    @Expose
    private String accountnumber;
    @SerializedName("branch")
    @Expose
    private String branch;
    @SerializedName("bkname")
    @Expose
    private String bkname;
    @SerializedName("msg")
    @Expose
    private String msg;

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRate() {
        return rate;
    }

    public void setRate(long rate) {
        this.rate = rate;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getPtype() {
        return ptype;
    }

    public void setPtype(long ptype) {
        this.ptype = ptype;
    }

    public long getDiscount() {
        return discount;
    }

    public void setDiscount(long discount) {
        this.discount = discount;
    }

    public String getBkid() {
        return bkid;
    }

    public void setBkid(String bkid) {
        this.bkid = bkid;
    }

    public String getAccountnumber() {
        return accountnumber;
    }

    public void setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getBkname() {
        return bkname;
    }

    public void setBkname(String bkname) {
        this.bkname = bkname;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}