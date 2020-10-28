package com.elkdeals.mobile.api.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Auction implements Parcelable {
    public static final Creator<Auction> CREATOR = new Creator<Auction>() {
        @Override
        public Auction createFromParcel(Parcel in) {
            return new Auction(in);
        }

        @Override
        public Auction[] newArray(int size) {
            return new Auction[size];
        }
    };
    ////////////////////////////
    @SerializedName("id")
    private
    int id;
    @SerializedName("name")
    private
    String name;
    @SerializedName("image")
    private
    String image;
    @SerializedName("bidtype")

    private
    int bidtype;
    @SerializedName("starttype")
    private
    int starttype;
    @SerializedName("datetype")
    private
    int datetype;
    @SerializedName("maxmonthes")
    private
    int maxmonthes;
    @SerializedName("maxinstallment")
    private
    int maxinstallment;
    @SerializedName("enddate")
    private
    long date;
    @SerializedName("hide")
    private
    int hide;
    @SerializedName("canbid")
    private
    int canbid;
    /////////////constructor///////////////
    @SerializedName("current")
    private
    double current;


    //////////////////// usual getters and setters////////////////////////////

    public Auction() {
    }

    protected Auction(Parcel in) {
        id = in.readInt();
        name = in.readString();
        image = in.readString();
        bidtype = in.readInt();
        starttype = in.readInt();
        datetype = in.readInt();
        maxmonthes = in.readInt();
        maxinstallment = in.readInt();
        date = in.readLong();
        hide = in.readInt();
        canbid = in.readInt();
        current = in.readDouble();
    }
/*
    private static int getBidType(BidType bidType) {
        return 0;
    }
*/
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        //todo return image
        return "http://hashcode.me/nl/media/images/product/sp0.jpg";
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getBidtype() {
        return bidtype;
    }
/*
    public void setBidtype(BidType mbidtype) {
        switch (mbidtype) {
            case Price:
                bidtype = 0;
                return;
            case Days:
                bidtype = 1;
                return;

        }
    }
*/
    public void setBidtype(int bidtype) {
        this.bidtype = bidtype;
    }

    public int getStarttype() {
        return starttype;
    }

    public void setStarttype(int starttype) {
        this.starttype = starttype;
    }

    public int getDatetype() {
        return datetype;
    }

    public void setDatetype(int datetype) {
        this.datetype = datetype;
    }

    public int getMaxmonthes() {
        return maxmonthes;
    }

    public void setMaxmonthes(int maxmonthes) {
        this.maxmonthes = maxmonthes;
    }

    public int getMaxinstallment() {
        return maxinstallment;
    }

    public void setMaxinstallment(int maxinstallment) {
        this.maxinstallment = maxinstallment;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public boolean isHidden() {

        return hide == 0;
    }

    public void setHidden(boolean hide) {
        if (hide) {
            this.hide = 0;
        } else {
            this.hide = 1;
        }
    }

    public boolean canBid() {
        return canbid == 0;
    }

    public void setCanbid(boolean canbid) {
        if (canbid) {
            this.canbid = 0;
        } else {
            this.canbid = 1;
        }
    }

    public int getHide() {
        return hide;
    }

    public void setHide(int hide) {
        this.hide = hide;
    }

    public int getCanbid() {
        return canbid;
    }

    public void setCanbid(int canbid) {
        this.canbid = canbid;
    }

    public double getCurrent() {
        return current;
    }

    public void setCurrent(double current) {
        this.current = current;
    }
/*
    ////////////////////////////////////////////////
    ////////////////////////////////////////////////
    public void setStatuse(AuctionStatus statuse) {
        switch (statuse) {
            case notStarted:
                starttype = 1;
                break;
            case started:
                starttype = 2;
                break;
        }
    }

    public AuctionStatus getStatuseEnum() {
        switch (starttype) {
            case 1:
                return AuctionStatus.notStarted;
            case 2:
                return AuctionStatus.started;
        }
        return null;
    }

    public BidType getBidTypeEnum() {
        switch (bidtype) {
            case 1:
                return Days;
            case 0:
                return Price;
        }
        return Days;
    }

    public TimeType getDateType() {
        switch (datetype) {
            case 1:
                return TimeType.Second;
            case 2:
                return TimeType.Minute;
            case 3:
                return TimeType.Hour;
            case 4:
                return TimeType.Day;
        }
        return TimeType.Second;
    }
*/
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(image);
        dest.writeInt(bidtype);
        dest.writeInt(starttype);
        dest.writeInt(datetype);
        dest.writeInt(maxmonthes);
        dest.writeInt(maxinstallment);
        dest.writeLong(date);
        dest.writeInt(hide);
        dest.writeInt(canbid);
        dest.writeDouble(current);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != Auction.class) return false;
        try {
            return ((Auction) obj).getId() == id;
        } catch (Exception ignored) {
            return false;
        }
    }
    ////////////////////////////////////////////////

/*
    public long getTimeToWaitBeforeAuction() {
        return timeToWaitBeforeAuction;
    }*/

}
