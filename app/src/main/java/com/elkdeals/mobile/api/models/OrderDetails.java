package com.elkdeals.mobile.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetails {
    @SerializedName("data") @Expose private OrderDetailsData detailsData;
    @SerializedName("list") @Expose private List<OrderDetailsItems> orderDetailsItems;

    public OrderDetailsData getDetailsData() {
        return detailsData;
    }

    public void setDetailsData(OrderDetailsData detailsData) {
        this.detailsData = detailsData;
    }

    public List<OrderDetailsItems> getOrderDetailsItems() {
        return orderDetailsItems;
    }

    public void setOrderDetailsItems(List<OrderDetailsItems> orderDetailsItems) {
        this.orderDetailsItems = orderDetailsItems;
    }
}

