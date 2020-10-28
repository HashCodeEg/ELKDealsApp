
package com.elkdeals.mobile.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InvoiceDetail {

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
    @SerializedName("way")
    @Expose
    private Integer way;
    @SerializedName("startprice")
    @Expose
    private Integer startprice;
    @SerializedName("endprice")
    @Expose
    private Integer endprice;
    @SerializedName("stepprice")
    @Expose
    private Integer stepprice;
    @SerializedName("chprice")
    @Expose
    private Integer chprice;
    @SerializedName("cname")
    @Expose
    private String cname;
    @SerializedName("idnumber")
    @Expose
    private String idnumber;
    @SerializedName("datetype")
    @Expose
    private String datetype;
    @SerializedName("enddate")
    @Expose
    private Integer enddate;
    @SerializedName("did")
    @Expose
    private Integer invoiceDeleteID;

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

    public Integer getWay() {
        return way;
    }

    public void setWay(Integer way) {
        this.way = way;
    }

    public Integer getStartprice() {
        return startprice;
    }

    public void setStartprice(Integer startprice) {
        this.startprice = startprice;
    }

    public Integer getEndprice() {
        return endprice;
    }

    public void setEndprice(Integer endprice) {
        this.endprice = endprice;
    }

    public Integer getStepprice() {
        return stepprice;
    }

    public void setStepprice(Integer stepprice) {
        this.stepprice = stepprice;
    }

    public Integer getChprice() {
        return chprice;
    }

    public void setChprice(Integer chprice) {
        this.chprice = chprice;
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

    public Integer getEnddate() {
        return enddate;
    }

    public void setEnddate(Integer enddate) {
        this.enddate = enddate;
    }

    public Integer getInvoiceDeleteID() {
        return invoiceDeleteID;
    }

    public void setInvoiceDeleteID(Integer invoiceDeleteID) {
        this.invoiceDeleteID = invoiceDeleteID;
    }
}