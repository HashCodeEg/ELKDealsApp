
package com.elkdeals.mobile.api.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InvoicesModel implements Parcelable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("number")
    @Expose
    private long number;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("rate")
    @Expose
    private int rate;
    @SerializedName("total")
    @Expose
    private int total;
    @SerializedName("ptype")
    @Expose
    private int ptype;
    @SerializedName("status")
    @Expose
    private int status;
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
    @SerializedName("discount")
    @Expose
    private int discount;

    public InvoicesModel() {
    }
    protected InvoicesModel(Parcel in) {
        id = in.readInt();
        number = in.readLong();
        date = in.readString();
        rate = in.readInt();
        total = in.readInt();
        ptype = in.readInt();
        status = in.readInt();
        bkid = in.readString();
        accountnumber = in.readString();
        branch = in.readString();
        bkname = in.readString();
        discount = in.readInt();
    }

    public static final Creator<InvoicesModel> CREATOR = new Creator<InvoicesModel>() {
        @Override
        public InvoicesModel createFromParcel(Parcel in) {
            return new InvoicesModel(in);
        }

        @Override
        public InvoicesModel[] newArray(int size) {
            return new InvoicesModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPtype() {
        return ptype;
    }

    public void setPtype(int ptype) {
        this.ptype = ptype;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeLong(number);
        dest.writeString(date);
        dest.writeInt(rate);
        dest.writeInt(total);
        dest.writeInt(ptype);
        dest.writeInt(status);
        dest.writeString(bkid);
        dest.writeString(accountnumber);
        dest.writeString(branch);
        dest.writeString(bkname);
        dest.writeInt(discount);
    }
}

