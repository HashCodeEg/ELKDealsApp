package com.elkdeals.mobile.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AucDetailsModel {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("desc")
    @Expose
    private String desc;
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
    @SerializedName("min")
    @Expose
    private Integer min;
    @SerializedName("max")
    @Expose
    private Integer max;
    @SerializedName("way")
    @Expose
    private Integer way;
    @SerializedName("startdate")
    @Expose
    private String startdate;
    @SerializedName("endregdate")
    @Expose
    private String endregdate;
    @SerializedName("marketprice")
    @Expose
    private Integer marketprice;
    @SerializedName("startprice")
    @Expose
    private Integer startprice;
    @SerializedName("inst")
    @Expose
    private Integer inst;
    @SerializedName("months")
    @Expose
    private Integer months;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("maxdiscount")
    @Expose
    private Integer maxdiscount;
    @SerializedName("maxmothly")
    @Expose
    private Integer maxmothly;
    @SerializedName("cname")
    @Expose
    private String cname;
    @SerializedName("idnumber")
    @Expose
    private String idnumber;
    @SerializedName("clientid")
    @Expose
    private String clientid;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("newprice")
    @Expose
    private Integer newprice;
    @SerializedName("insta")
    @Expose
    private Integer insta;
    @SerializedName("datetype")
    @Expose
    private String datetype;
    @SerializedName("mothlyinstallment")
    @Expose
    private String mothlyinstallment;
    @SerializedName("enddate")
    @Expose
    private Integer enddate;
    @SerializedName("auctiontype")
    @Expose
    private Integer auctiontype;
    @SerializedName("nosales")
    @Expose
    private Integer nosales;
    @SerializedName("insurancevalue")
    @Expose
    private Integer insurancevalue;
    @SerializedName("auctionkind")
    @Expose
    private Integer auctionkind;
    @SerializedName("timertype")
    @Expose
    private Integer timertype;
    @SerializedName("percentage")
    @Expose
    private Integer percentage;
    @SerializedName("bids")
    @Expose
    private Integer bids;
    @SerializedName("showbid")
    @Expose
    private boolean showbid;


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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getWay() {
        return way;
    }

    public void setWay(Integer way) {
        this.way = way;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEndregdate() {
        return endregdate;
    }

    public void setEndregdate(String endregdate) {
        this.endregdate = endregdate;
    }

    public Integer getMarketprice() {
        return marketprice;
    }

    public void setMarketprice(Integer marketprice) {
        this.marketprice = marketprice;
    }

    public Integer getStartprice() {
        return startprice;
    }

    public void setStartprice(Integer startprice) {
        this.startprice = startprice;
    }

    public Integer getInst() {
        return inst;
    }

    public void setInst(Integer inst) {
        this.inst = inst;
    }

    public Integer getMonths() {
        return months;
    }

    public void setMonths(Integer months) {
        this.months = months;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getMaxdiscount() {
        return maxdiscount;
    }

    public void setMaxdiscount(Integer maxdiscount) {
        this.maxdiscount = maxdiscount;
    }

    public Integer getMaxmothly() {
        return maxmothly;
    }

    public void setMaxmothly(Integer maxmothly) {
        this.maxmothly = maxmothly;
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

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Integer getNewprice() {
        return newprice;
    }

    public void setNewprice(Integer newprice) {
        this.newprice = newprice;
    }

    public Integer getInsta() {
        return insta;
    }

    public void setInsta(Integer insta) {
        this.insta = insta;
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

    public Integer getAuctiontype() {
        return auctiontype;
    }

    public void setAuctiontype(Integer auctiontype) {
        this.auctiontype = auctiontype;
    }

    public String getMothlyinstallment() {
        return mothlyinstallment;
    }

    public void setMothlyinstallment(String mothlyinstallment) {
        this.mothlyinstallment = mothlyinstallment;
    }

    public Integer getNosales() {
        return nosales;
    }

    public void setNosales(Integer nosales) {
        this.nosales = nosales;
    }

    public Integer getInsurancevalue() {
        return insurancevalue;
    }

    public void setInsurancevalue(Integer insurancevalue) {
        this.insurancevalue = insurancevalue;
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

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    public Integer getBids() {
        return bids;
    }

    public void setBids(Integer bids) {
        this.bids = bids;
    }

    public boolean isShowbid() {
        return showbid;
    }

    public void setShowbid(boolean showbid) {
        this.showbid = showbid;
    }
}