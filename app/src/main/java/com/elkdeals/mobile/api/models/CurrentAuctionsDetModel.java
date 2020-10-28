
package com.elkdeals.mobile.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentAuctionsDetModel {

    @SerializedName("startdate")
    @Expose
    private String startdate;
    @SerializedName("endregdate")
    @Expose
    private String endregdate;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("marketprice")
    @Expose
    private Integer marketprice;
    @SerializedName("startprice")
    @Expose
    private Integer startprice;
    @SerializedName("inst")
    @Expose
    private Integer inst;
    @SerializedName("maxdiscount")
    @Expose
    private Integer maxdiscount;
    @SerializedName("maxmothly")
    @Expose
    private Integer maxmothly;
    @SerializedName("mothlyinstallment")
    @Expose
    private Integer mothlyinstallment;
    @SerializedName("auctiontype")
    @Expose
    private Integer auctiontype;
    @SerializedName("nosales")
    @Expose
    private Integer nosales;
    @SerializedName("insurancevalue")
    @Expose
    private Integer insurancevalue;
    @SerializedName("id")
    @Expose
    private String id;
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
    private boolean started = false;
    @SerializedName("finished")
    @Expose
    private boolean finished = false;
    @SerializedName("min")
    @Expose
    private Integer min;
    @SerializedName("max")
    @Expose
    private Integer max;
    @SerializedName("cname")
    @Expose
    private String cname;
    @SerializedName("clientid")
    @Expose
    private String clientid;
    @SerializedName("idnumber")
    @Expose
    private String idnumber;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("newprice")
    @Expose
    private Integer newprice;
    @SerializedName("insta")
    @Expose
    private Integer insta;
    @SerializedName("datetype")
    @Expose
    private String datetype;
    @SerializedName("enddate")
    @Expose
    private Integer enddate;
    @SerializedName("months")
    @Expose
    private Integer months;
    @SerializedName("auctionkind")
    @Expose
    private Integer auctionkind;
    @SerializedName("showtimer")
    @Expose
    private boolean showtimer;
    @SerializedName("cash")
    @Expose
    private Integer cash;
    @SerializedName("bidmonths")
    @Expose
    private Integer bidmonths;
    @SerializedName("bids")
    @Expose
    private Integer bids;
    @SerializedName("showbid")
    @Expose
    private boolean showbid;


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

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
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

    public Integer getMonths() {
        return months;
    }

    public void setMonths(Integer months) {
        this.months = months;
    }

    public boolean isShowtimer() {
        return showtimer;
    }

    public void setShowtimer(boolean showtimer) {
        this.showtimer = showtimer;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Integer getMothlyinstallment() {
        return mothlyinstallment;
    }

    public void setMothlyinstallment(Integer mothlyinstallment) {
        this.mothlyinstallment = mothlyinstallment;
    }

    public Integer getAuctiontype() {
        return auctiontype;
    }

    public void setAuctiontype(Integer auctiontype) {
        this.auctiontype = auctiontype;
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

    public Integer getCash() {
        return cash;
    }

    public void setCash(Integer cash) {
        this.cash = cash;
    }

    public Integer getBidmonths() {
        return bidmonths;
    }

    public void setBidmonths(Integer bidmonths) {
        this.bidmonths = bidmonths;
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