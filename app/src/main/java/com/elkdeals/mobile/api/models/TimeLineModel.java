
package com.elkdeals.mobile.api.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class TimeLineModel implements Parcelable {

    @PrimaryKey (autoGenerate = true)
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("current")
    @Expose
    private int current;
    @SerializedName("entering")
    @Expose
    private int entering;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("started")
    @Expose
    private Boolean started;
    @SerializedName("finished")
    @Expose
    private Boolean finished;
    @SerializedName("way")
    @Expose
    private int way;
    @SerializedName("video")
    @Expose
    private String video;
    @SerializedName("startprice")
    @Expose
    private int startprice;
    @SerializedName("registered")
    @Expose
    private Boolean registered;
    @SerializedName("endprice")
    @Expose
    private int endprice;
    @SerializedName("stepprice")
    @Expose
    private int stepprice;
    @SerializedName("auctionprice")
    @Expose
    private int auctionprice;
    @SerializedName("percentage")
    @Expose
    private int percentage;
    @SerializedName("cname")
    @Expose
    private String cname;
    @SerializedName("idnumber")
    @Expose
    private String idnumber;
    @SerializedName("datetype")
    @Expose
    private String datetype;
    @SerializedName("enddate")
    @Expose
    private int enddate;
    @SerializedName("reg")
    @Expose
    private boolean reg;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lang")
    @Expose
    private String lang;
    private String catId;


    public TimeLineModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        desc = in.readString();
        current = in.readInt();
        entering = in.readInt();
        image = in.readString();
        byte tmpStarted = in.readByte();
        started = tmpStarted == 0 ? null : tmpStarted == 1;
        byte tmpFinished = in.readByte();
        finished = tmpFinished == 0 ? null : tmpFinished == 1;
        way = in.readInt();
        video = in.readString();
        startprice = in.readInt();
        byte tmpRegistered = in.readByte();
        registered = tmpRegistered == 0 ? null : tmpRegistered == 1;
        endprice = in.readInt();
        stepprice = in.readInt();
        auctionprice = in.readInt();
        percentage = in.readInt();
        cname = in.readString();
        idnumber = in.readString();
        datetype = in.readString();
        enddate = in.readInt();
        reg = in.readByte() != 0;
        phone = in.readString();
        lat = in.readString();
        lang = in.readString();
        catId = in.readString();
    }

    public static final Creator<TimeLineModel> CREATOR = new Creator<TimeLineModel>() {
        @Override
        public TimeLineModel createFromParcel(Parcel in) {
            return new TimeLineModel(in);
        }

        @Override
        public TimeLineModel[] newArray(int size) {
            return new TimeLineModel[size];
        }
    };

    public TimeLineModel() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public int getWay() {
        return way;
    }

    public void setWay(int way) {
        this.way = way;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public int getStartprice() {
        return startprice;
    }

    public void setStartprice(int startprice) {
        this.startprice = startprice;
    }

    public Boolean getRegistered() {
        return registered;
    }

    public void setRegistered(Boolean registered) {
        this.registered = registered;
    }

    public int getEndprice() {
        return endprice;
    }

    public void setEndprice(int endprice) {
        this.endprice = endprice;
    }

    public int getStepprice() {
        return stepprice;
    }

    public void setStepprice(int stepprice) {
        this.stepprice = stepprice;
    }

    public int getAuctionprice() {
        return auctionprice;
    }

    public void setAuctionprice(int auctionprice) {
        this.auctionprice = auctionprice;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
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

    public String getDatetype() {
        return datetype;
    }

    public void setDatetype(String datetype) {
        this.datetype = datetype;
    }

    public int getEnddate() {
        return enddate;
    }

    public void setEnddate(int enddate) {
        this.enddate = enddate;
    }

    public boolean isReg() {
        return reg;
    }

    public void setReg(boolean reg) {
        this.reg = reg;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String cateId) {
        this.catId = cateId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(desc);
        dest.writeInt(current);
        dest.writeInt(entering);
        dest.writeString(image);
        dest.writeByte((byte) (started == null ? 0 : started ? 1 : 2));
        dest.writeByte((byte) (finished == null ? 0 : finished ? 1 : 2));
        dest.writeInt(way);
        dest.writeString(video);
        dest.writeInt(startprice);
        dest.writeByte((byte) (registered == null ? 0 : registered ? 1 : 2));
        dest.writeInt(endprice);
        dest.writeInt(stepprice);
        dest.writeInt(auctionprice);
        dest.writeInt(percentage);
        dest.writeString(cname);
        dest.writeString(idnumber);
        dest.writeString(datetype);
        dest.writeInt(enddate);
        dest.writeByte((byte) (reg ? 1 : 0));
        dest.writeString(phone);
        dest.writeString(lat);
        dest.writeString(lang);
        dest.writeString(catId);
    }
}
