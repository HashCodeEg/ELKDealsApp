package com.elkdeals.mobile.api.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Notification {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String message;
    private String date;
    private int read;
    private String imgUrl;
    private String link;


/*                    obj.addProperty("type", noti.getNotiType().longValue());
                    obj.addProperty("objtype", noti.getObjType());
                    obj.addProperty("objtypeid", noti.getObjId());*/

    public Notification(){
        read=1;
    }

    @Ignore
    private Notification(String imgUrl, String title, String link, String message, String date) {
        this.imgUrl = imgUrl;
        this.title = title;
        this.link = link;
        this.message = message;
        this.date = date;
    }

    public int isRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    public static List<Notification> getDummy(){
        ArrayList<Notification>notifications=new ArrayList<>();
        notifications.add(new Notification("","المزاد اوشك على الانتهاء" ,"","باقى على المزاد blablblablabalbalbblablabla","28-11-2018"));
        return notifications;
    }
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null||!obj.getClass().getName().equals(Notification.class.getName()))return false;
        Notification notification=(Notification) obj;
        return notification.id == id;
    }
}
