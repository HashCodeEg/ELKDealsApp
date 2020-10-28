
package com.elkdeals.mobile.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentAucClosedM {

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
    @SerializedName("started")
    @Expose
    private boolean started;
    @SerializedName("finished")
    @Expose
    private boolean finished;
    @SerializedName("way")
    @Expose
    private Integer way;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("auctiontype")
    @Expose
    private Integer auctiontype;
    @SerializedName("auctionkind")
    @Expose
    private Integer auctionkind;
    @SerializedName("timertype")
    @Expose
    private Integer timertype;
    @SerializedName("datetype")
    @Expose
    private String datetype;
    @SerializedName("enddate")
    @Expose
    private Integer enddate;
    @SerializedName("msg")
    @Expose
    String msg;
    @SerializedName("hide")
    @Expose
    boolean hideBid;
    @SerializedName("max")
    @Expose
    int maxBid;
    @SerializedName("timermsg")
    @Expose
    String timerMsg;
    @SerializedName("canbid")
    @Expose
    boolean userCanBis;
    @SerializedName("finishedfinal")
    @Expose
    boolean finishedfinal;

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

    public Integer getWay() {
        return way;
    }

    public void setWay(Integer way) {
        this.way = way;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getAuctiontype() {
        return auctiontype;
    }

    public void setAuctiontype(Integer auctiontype) {
        this.auctiontype = auctiontype;
    }

    public Integer getAuctionkind() {
        return auctionkind;
    }

    public void setAuctionkind(Integer auctionkind) {
        this.auctionkind = auctionkind;
    }

    public Integer getTimertype() {
        return timertype;
    }

    public void setTimertype(Integer timertype) {
        this.timertype = timertype;
    }

    public String getDatetype() {
        return datetype;
    }

    public void setDatetype(String datetype) {
        this.datetype = datetype;
    }

    public Integer getEnddate() {
        return enddate;
    }

    public void setEnddate(Integer enddate) {
        this.enddate = enddate;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isHideBid() {
        return hideBid;
    }

    public void setHideBid(boolean hideBid) {
        this.hideBid = hideBid;
    }

    public int getMaxBid() {
        return maxBid;
    }

    public void setMaxBid(int maxBid) {
        this.maxBid = maxBid;
    }

    public String getTimerMsg() {
        return timerMsg;
    }

    public void setTimerMsg(String timerMsg) {
        this.timerMsg = timerMsg;
    }

    public boolean isUserCanBis() {
        return userCanBis;
    }

    public void setUserCanBis(boolean userCanBis) {
        this.userCanBis = userCanBis;
    }

    public boolean isFinishedfinal() {
        return finishedfinal;
    }

    public void setFinishedfinal(boolean finishedfinal) {
        this.finishedfinal = finishedfinal;
    }
}
