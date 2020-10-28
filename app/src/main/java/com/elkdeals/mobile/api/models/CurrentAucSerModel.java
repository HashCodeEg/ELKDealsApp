package com.elkdeals.mobile.api.models;

import java.io.Serializable;

/**
 * Created by mohamed on 3/8/17.
 */

public class CurrentAucSerModel implements Serializable {
   
    private String id;
  
    private String name;
    
    private int current;
   
    private int entering;
   
    private String image;
   
    private Boolean started;
   
    private Boolean finished;
    
    private String cname;
   
    private String idnumber;
    
    private String enddate;
   
    private int enddate3;
   
    private String datetype;
    
    private int enddate2;

    private int postition;
    private int way;

    public CurrentAucSerModel(String id, int postition, String name, String image, int enddate2, String datetype,int way ) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.datetype = datetype;
        this.enddate2 = enddate2;
        this.postition = postition;
        this.way = way;
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

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getEntering() {
        return entering;
    }

    public void setEntering(int entering) {
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

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public int getEnddate3() {
        return enddate3;
    }

    public void setEnddate3(int enddate3) {
        this.enddate3 = enddate3;
    }

    public String getDatetype() {
        return datetype;
    }

    public void setDatetype(String datetype) {
        this.datetype = datetype;
    }

    public int getEnddate2() {
        return enddate2;
    }

    public void setEnddate2(int enddate2) {
        this.enddate2 = enddate2;
    }

    public int getPostition() {
        return postition;
    }

    public void setPostition(int postition) {
        this.postition = postition;
    }

    public int getWay() {
        return way;
    }

    public void setWay(int way) {
        this.way = way;
    }
}
