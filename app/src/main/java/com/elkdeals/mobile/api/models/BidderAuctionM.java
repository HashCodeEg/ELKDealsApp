package com.elkdeals.mobile.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mohamed on 3/18/18.
 */

public class BidderAuctionM {
    /*"started": true,
    "finished": true*/
    @SerializedName("datetype")
    @Expose
    String datetype;
    @SerializedName("enddate")
    @Expose
    int enddate;
    @SerializedName("can")
    @Expose
    int can;
    @SerializedName("min")
    @Expose
    int min;
    @SerializedName("max")
    @Expose
    int max;
    @SerializedName("bidtype")
    @Expose
    int bidtype;
    @SerializedName("current")
    @Expose
    int current;
    @SerializedName("started")
    @Expose
    boolean started;
    @SerializedName("finished")
    @Expose
    boolean finished;
    @SerializedName("finishedfinal")
    @Expose
    boolean finishedfinal;
    @SerializedName("msg")
    @Expose
    String msg;
    @SerializedName("bids")
    @Expose
    int bidsNumber;
    @SerializedName("wait")
    @Expose
    int waitTime;
    @SerializedName("hidebids")
    @Expose
    boolean hidebids;
    @SerializedName("canbid")
    @Expose
    boolean userCanBid;
    @SerializedName("timermsg")
    @Expose
    String timerMsg;
    @SerializedName("canbidmsg")
    @Expose
    String canbidmsg;

    @SerializedName("auctionkind") @Expose int auctionKind;
    @SerializedName("image") @Expose String image;
    @SerializedName("id") @Expose int id;
    @SerializedName("name") @Expose String name;
    public String getDatetype() {
        return datetype;
    }

    public void setDatetype(String datetype) {
        this.datetype = datetype;
    }

    public int getEnddate() {
        return enddate;
    }

    public void setEnddate(int enddate) {
        this.enddate = enddate;
    }

    public int getCan() {
        return can;
    }

    public void setCan(int can) {
        this.can = can;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getBidtype() {
        return bidtype;
    }

    public void setBidtype(int bidtype) {
        this.bidtype = bidtype;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public boolean isFinishedfinal() {
        return finishedfinal;
    }

    public void setFinishedfinal(boolean finishedfinal) {
        this.finishedfinal = finishedfinal;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getBidsNumber() {
        return bidsNumber;
    }

    public void setBidsNumber(int bidsNumber) {
        this.bidsNumber = bidsNumber;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public boolean isHidebids() {
        return hidebids;
    }

    public void setHidebids(boolean hidebids) {
        this.hidebids = hidebids;
    }

    public boolean isUserCanBid() {
        return userCanBid;
    }

    public void setUserCanBid(boolean userCanBid) {
        this.userCanBid = userCanBid;
    }

    public String getTimerMsg() {
        return timerMsg;
    }

    public void setTimerMsg(String timerMsg) {
        this.timerMsg = timerMsg;
    }

    public String getCanbidmsg() {
        return canbidmsg;
    }

    public void setCanbidmsg(String canbidmsg) {
        this.canbidmsg = canbidmsg;
    }

    public int getAuctionKind() {
        return auctionKind;
    }

    public void setAuctionKind(int auctionKind) {
        this.auctionKind = auctionKind;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

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
}
