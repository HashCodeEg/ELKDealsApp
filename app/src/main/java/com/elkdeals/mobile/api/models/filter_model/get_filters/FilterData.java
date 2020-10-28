package com.elkdeals.mobile.api.models.filter_model.get_filters;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class FilterData {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("filters")
    @Expose
    private List<FilterDetails> filters = new ArrayList<FilterDetails>();
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("maxPrice")
    @Expose
    private String maxPrice;


    /**
     * 
     * @return
     *     The success
     */
    public String getSuccess() {
        return success;
    }

    /**
     * 
     * @param success
     *     The success
     */
    public void setSuccess(String success) {
        this.success = success;
    }

    /**
     * 
     * @return
     *     The filters
     */
    public List<FilterDetails> getFilters() {
        if(filters!=null)
            filters=new ArrayList<>();
        return filters;
    }

    /**
     * 
     * @param filters
     *     The filters
     */
    public void setFilters(List<FilterDetails> filters) {
        this.filters = filters;
    }

    /**
     * 
     * @return
     *     The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * 
     * @param message
     *     The message
     */
    public void setMessage(String message) {
        this.message = message;
    }


    public String getMaxPrice() {
        if (TextUtils.isEmpty(maxPrice))
            maxPrice="1000";
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }


}
