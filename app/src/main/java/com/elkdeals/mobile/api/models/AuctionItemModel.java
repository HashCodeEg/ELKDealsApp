package com.elkdeals.mobile.api.models;

import java.io.Serializable;

/**
 * Created by mohamed on 3/8/17.
 */

public class AuctionItemModel implements Serializable {

    private String id;

    private String name;

    private int current;

    private String image;

    private String cname;

    private String idnumber;

    private String datetype;

    private int enddate;

    private String desc;

    private int entering;

    private int auctiontype;

    private int stepprice = 0;

    private int startPrice = 0;

    private int endprice = 0;

    private int percentage = 0;

    private int auctionprice = 0;

    private boolean registerd = false;





    public AuctionItemModel(String id, String name, int current,
                            String image, String cname, String idnumber,
                            String datetype, int enddate, String desc, int entering,
                            int startPrice,int endprice,int stepprice,int percentage ,int auctionprice,boolean registerd

    ) {
        this.id = id;
        this.name = name;
        this.current = current;
        this.image = image;
        this.cname = cname;
        this.idnumber = idnumber;
        this.datetype = datetype;
        this.enddate = enddate;
        this.desc = desc;
        this.entering = entering;
        this.startPrice = startPrice;
        this.endprice = endprice;
        this.stepprice = stepprice;
        this.percentage = percentage;
        this.auctionprice = auctionprice;
        this.registerd = registerd;

    }


    public AuctionItemModel(String id, String name, int current, String image) {
        this.id = id;
        this.name = name;
        this.current = current;
        this.image = image;
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

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getEntering() {
        return entering;
    }

    public void setEntering(int entering) {
        this.entering = entering;
    }

    public int getAuctiontype() {
        return auctiontype;
    }

    public void setAuctiontype(int auctiontype) {
        this.auctiontype = auctiontype;
    }


    public int getStepprice() {
        return stepprice;
    }

    public void setStepprice(int stepprice) {
        this.stepprice = stepprice;
    }

    public int getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(int startPrice) {
        this.startPrice = startPrice;
    }

    public int getEndprice() {
        return endprice;
    }

    public void setEndprice(int endprice) {
        this.endprice = endprice;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public int getAuctionprice() {
        return auctionprice;
    }

    public void setAuctionprice(int auctionprice) {
        this.auctionprice = auctionprice;
    }

    public boolean isRegisterd() {
        return registerd;
    }

    public void setRegisterd(boolean registerd) {
        this.registerd = registerd;
    }
}
