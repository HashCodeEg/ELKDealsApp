package com.elkdeals.mobile.api.models.requests;

import com.google.gson.annotations.SerializedName;

public class OfferDetails {
    @SerializedName("request_id")
    private String request_id;
    @SerializedName("trequest_id")
    private Integer trequestId;
    @SerializedName("customers_id")
    private String customers_id;
    @SerializedName("price")
    private String price;
    @SerializedName("deliver_time")
    private String deliver_time;
    @SerializedName("shipping_price")
    private String shipping_price;
    @SerializedName("product_status")
    private String product_status;
    @SerializedName("created_at")
    private String created_at;

    @SerializedName("offer_id")
    private Integer offerId;
    @SerializedName("customer_id")
    private Integer customerId;
    @SerializedName("category_id")
    private Integer categoryId;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("new_status")
    private Integer newStatus;
    @SerializedName("used_status")
    private Integer usedStatus;
    public String getCustomers_id() {
        return customers_id;
    }


    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public Integer getTrequestId() {
        return trequestId;
    }

    public void setTrequestId(Integer trequestId) {
        this.trequestId = trequestId;
    }

    public void setCustomers_id(String customers_id) {
        this.customers_id = customers_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDeliver_time() {
        return deliver_time;
    }

    public void setDeliver_time(String deliver_time) {
        this.deliver_time = deliver_time;
    }

    public String getShipping_price() {
        return shipping_price;
    }

    public void setShipping_price(String shipping_price) {
        this.shipping_price = shipping_price;
    }

    public String getProduct_status() {
        return product_status;
    }

    public void setProduct_status(String product_status) {
        this.product_status = product_status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Integer getOfferId() {
        return offerId;
    }

    public void setOfferId(Integer offerId) {
        this.offerId = offerId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(Integer newStatus) {
        this.newStatus = newStatus;
    }

    public Integer getUsedStatus() {
        return usedStatus;
    }

    public void setUsedStatus(Integer usedStatus) {
        this.usedStatus = usedStatus;
    }
}
