package com.elkdeals.mobile.api.models.requests;

import com.google.gson.annotations.SerializedName;

public class RequestDetails {
    @SerializedName("trequest_id")
    private String request_id;
    @SerializedName("customers_id")
    private String customers_id;
    @SerializedName("category_id")
    private String category_id;
    @SerializedName("categories_name")
    private String categories_name;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("used_status")
    private String used_status;
    @SerializedName("new_status")
    private String new_status;
    @SerializedName("created_at")
    private String createAt;

    public String getCustomers_id() {
        return customers_id;
    }

    public void setCustomers_id(String customers_id) {
        this.customers_id = customers_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
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

    public String getUsed_status() {
        return used_status;
    }

    public void setUsed_status(String used_status) {
        this.used_status = used_status;
    }

    public String getNew_status() {
        return new_status;
    }

    public void setNew_status(String new_status) {
        this.new_status = new_status;
    }

    public String getCategories_name() {
        return categories_name;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public void setCategories_name(String categories_name) {
        this.categories_name = categories_name;
    }
}
