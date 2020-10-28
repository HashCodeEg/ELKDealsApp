package com.elkdeals.mobile.api.models;

import java.io.Serializable;

/**
 * Created by mohamed on 2/19/17.
 */

public class AucSpineerModel implements Serializable {


    private static final long serialVersionUID = 1L;
    String id;
    String name;
    int way ;
    String cName;
    int current;
    String image;

    public AucSpineerModel(String id, String name, int way, String cName, int current, String image) {
        this.id = id;
        this.name = name;
        this.way = way;
        this.cName = cName;
        this.current = current;
        this.image = image;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public int getWay() {
        return way;
    }

    public void setWay(int way) {
        this.way = way;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public int getcurrent() {
        return current;
    }

    public void setcurrent(int current) {
        this.current = current;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
