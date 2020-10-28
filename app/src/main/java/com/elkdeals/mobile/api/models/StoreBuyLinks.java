package com.elkdeals.mobile.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mohamed on 12/6/17.
 */

public class StoreBuyLinks {
    @SerializedName("id")
    @Expose
    int id;
    @SerializedName("label")
    @Expose
    String labe;
    @SerializedName("link")
    @Expose
    String link;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabe() {
        return labe;
    }

    public void setLabe(String labe) {
        this.labe = labe;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
