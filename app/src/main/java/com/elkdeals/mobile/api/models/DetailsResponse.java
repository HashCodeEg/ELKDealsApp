package com.elkdeals.mobile.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailsResponse {
    @SerializedName("details") @Expose private DetailsM details;
    @SerializedName("keys") @Expose private List<KeyM> keys = null;

    public DetailsM getDetails() {
        return details;
    }

    public void setDetails(DetailsM details) {
        this.details = details;
    }

    public List<KeyM> getKeys() {
        return keys;
    }

    public void setKeys(List<KeyM> keys) {
        this.keys = keys;
    }

}
