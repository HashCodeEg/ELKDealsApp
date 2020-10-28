package com.elkdeals.mobile.api.models;

import java.io.Serializable;

/**
 * Created by mohamed on 3/27/17.
 */

public class InvoicesModelSerialized implements Serializable {


    private long id;
    private long number;
    private String  date;
    private long rate;
    private long total;
    private long ptype;
    private long status;
    private long discount;
    private String  bkid;
    private String  accountnumber;
    private String  branch;
    private String  bkname;

    public InvoicesModelSerialized(
            long id, long number, String date,
            long rate, long total, long ptype,
            long status, String bkid, String accountnumber,
            String branch, String bkname,int discount) {
        this.id = id;
        this.number = number;
        this.date = date;
        this.rate = rate;
        this.total = total;
        this.ptype = ptype;
        this.status = status;
        this.bkid = bkid;
        this.accountnumber = accountnumber;
        this.branch = branch;
        this.bkname = bkname;
        this.discount = discount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
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

    public long getDiscount() {
        return discount;
    }

    public void setDiscount(long discount) {
        this.discount = discount;
    }
}
