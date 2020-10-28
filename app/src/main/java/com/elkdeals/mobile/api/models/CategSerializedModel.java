package com.elkdeals.mobile.api.models;

import java.io.Serializable;

/**
 * Created by mohamed on 5/6/17.
 */

public class CategSerializedModel implements Serializable {
    String name;
    int id;

    public CategSerializedModel(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
