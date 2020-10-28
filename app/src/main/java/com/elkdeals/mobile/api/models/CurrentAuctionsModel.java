
package com.elkdeals.mobile.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentAuctionsModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("current")
    @Expose
    private Integer current;
    @SerializedName("entering")
    @Expose
    private Integer entering;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("started")
    @Expose
    private Boolean started;
    @SerializedName("finished")
    @Expose
    private Boolean finished;
    @SerializedName("cname")
    @Expose
    private String cname;
    @SerializedName("idnumber")
    @Expose
    private String idnumber;
    @SerializedName("enddate")
    @Expose
    private String enddate;
    @SerializedName("enddate3")
    @Expose
    private Integer enddate3;
    @SerializedName("datetype")
    @Expose
    private String datetype;
    @SerializedName("enddate2")
    @Expose
    private Integer enddate2;
    @SerializedName("way")
    @Expose
    private Integer way;
    @SerializedName("bidtype")
    @Expose
    int bidtype;
    @SerializedName("auctionkind")
    @Expose
    private Integer auctionkind;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("finishedstep")
    @Expose
    private boolean finishedstep;
    @SerializedName("finishedfinal")
    @Expose
    private boolean finishedfinal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Integer getEntering() {
        return entering;
    }

    public void setEntering(Integer entering) {
        this.entering = entering;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getStarted() {
        return started;
    }

    public void setStarted(Boolean started) {
        this.started = started;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
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

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public Integer getEnddate3() {
        return enddate3;
    }

    public void setEnddate3(Integer enddate3) {
        this.enddate3 = enddate3;
    }

    public String getDatetype() {
        return datetype;
    }

    public void setDatetype(String datetype) {
        this.datetype = datetype;
    }

    public Integer getEnddate2() {
        return enddate2;
    }

    public void setEnddate2(Integer enddate2) {
        this.enddate2 = enddate2;
    }

    public Integer getWay() {
        return way;
    }

    public void setWay(Integer way) {
        this.way = way;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getAuctionkind() {
        return auctionkind;
    }

    public void setAuctionkind(Integer auctionkind) {
        this.auctionkind = auctionkind;
    }

    public int getBidtype() {
        return bidtype;
    }

    public void setBidtype(int bidtype) {
        this.bidtype = bidtype;
    }

    public boolean isFinishedstep() {
        return finishedstep;
    }

    public void setFinishedstep(boolean finishedstep) {
        this.finishedstep = finishedstep;
    }

    public boolean isFinishedfinal() {
        return finishedfinal;
    }

    public void setFinishedfinal(boolean finishedfinal) {
        this.finishedfinal = finishedfinal;
    }
}