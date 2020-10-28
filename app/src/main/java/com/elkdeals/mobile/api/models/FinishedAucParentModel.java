package com.elkdeals.mobile.api.models;

import java.io.Serializable;

/**
 * Created by mohamed on 4/11/17.
 */

public class FinishedAucParentModel implements Serializable {

    String name;
    String image;

    public FinishedAucParentModel(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
