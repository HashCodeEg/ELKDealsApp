
package com.elkdeals.mobile.api.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.elkdeals.mobile.Utils.Utils;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static com.elkdeals.mobile.Utils.Constants.KEY_USER;
import static com.elkdeals.mobile.Utils.Constants.KEY_USER_IMAGE;

@Entity
public class UserModel implements Parcelable {

    @PrimaryKey
    @NonNull
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("profile")
    @Expose
    private String profile="";
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("insurance")
    @Expose
    private String insurance;
    @SerializedName("nationalid")
    @Expose
    private String nationalid;
    @SerializedName("cityid")
    @Expose
    private String cityid;
    @SerializedName("cityname")
    @Expose
    private String cityname;
    @SerializedName("cityrate")
    @Expose
    private String cityrate;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("seconds")
    @Expose
    private int seconds;
    @SerializedName("imei")
    @Expose
    private String imei;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("insurancestatus")
    @Expose
    private int insurancestatus;
    @SerializedName("bids")
    @Expose
    private int bids;
    @SerializedName("discounts")
    @Expose
    private int discounts;
    @SerializedName("lastauction")
    @Expose
    private int lastauction;
    @SerializedName("extradiscount")
    @Expose
    private int extradiscounts;
    @SerializedName("codevx")
    @Expose
    private int codevx;
    @SerializedName("codereq")
    @Expose
    private boolean codereq;
    @SerializedName("offermsg")
    @Expose
    private String offermsg;
    @SerializedName("hasoffer")
    @Expose
    private boolean hasoffer;
    @SerializedName("vvlink")
    @Expose
    private String vvlink;
    @SerializedName("hasvideo")
    @Expose
    private boolean hasvideo;
    @SerializedName("hasads")
    @Expose
    private boolean showHideAds;
    @SerializedName("hasinv")
    @Expose
    private boolean hasinv;
    @SerializedName("hashcarcat")
    @Expose
    private boolean hashcarcat;
    @SerializedName("allaucprice")
    @Expose
    private int allaucprice;
    @SerializedName("exaucprice")
    @Expose
    private int exaucprice;
    @SerializedName("notnumber")
    @Expose
    private int notnumber;
    @SerializedName("hidechat")
    @Expose
    private boolean hidechat;
    @SerializedName("aucid")
    @Expose
    private int auctionId;
    @SerializedName("aucname")
    @Expose
    private String auctionName;

    @SerializedName("sending")
    @Expose
    private String sendingBidLink;
    @SerializedName("receving")
    @Expose
    private String receivingResultsLink;
    private String image;

    public UserModel(UserModel userModel,String id) {
        if (userModel == null)
        {
            this.id = id;
            return;
        }
        if (!userModel.equals(id))
        {
            UserModel.remove();
            this.id = id;
            return;
        }

        this.id = userModel.id;
        this.name = userModel.name;
        this.profile = userModel.profile;
        this.mobile = userModel.mobile;
        this.email = userModel.email;
        this.address = userModel.address;
        this.insurance = userModel.insurance;
        this.nationalid = userModel.nationalid;
        this.cityid = userModel.cityid;
        this.cityname = userModel.cityname;
        this.cityrate = userModel.cityrate;
        this.amount = userModel.amount;
        this.seconds = userModel.seconds;
        this.imei = userModel.imei;
        this.token = userModel.token;
        this.insurancestatus = userModel.insurancestatus;
        this.bids = userModel.bids;
        this.discounts = userModel.discounts;
        this.lastauction = userModel.lastauction;
        this.extradiscounts = userModel.extradiscounts;
        this.codevx = userModel.codevx;
        this.codereq = userModel.codereq;
        this.offermsg = userModel.offermsg;
        this.hasoffer = userModel.hasoffer;
        this.vvlink = userModel.vvlink;
        this.hasvideo = userModel.hasvideo;
        this.showHideAds = userModel.showHideAds;
        this.hasinv = userModel.hasinv;
        this.hashcarcat = userModel.hashcarcat;
        this.allaucprice = userModel.allaucprice;
        this.exaucprice = userModel.exaucprice;
        this.notnumber = userModel.notnumber;
        this.hidechat = userModel.hidechat;
        this.auctionId = userModel.auctionId;
        this.auctionName = userModel.auctionName;
        this.sendingBidLink = userModel.sendingBidLink;
        this.receivingResultsLink = userModel.receivingResultsLink;
        this.image = userModel.image;
    }

    private static void remove() {
        Utils.clearSharedPrefrences(KEY_USER);
        Utils.clearSharedPrefrences(KEY_USER_IMAGE);
    }

    protected UserModel(Parcel in) {
        id = in.readString();
        name = in.readString();
        profile = in.readString();
        mobile = in.readString();
        email = in.readString();
        address = in.readString();
        insurance = in.readString();
        nationalid = in.readString();
        cityid = in.readString();
        cityname = in.readString();
        cityrate = in.readString();
        amount = in.readString();
        seconds = in.readInt();
        imei = in.readString();
        token = in.readString();
        insurancestatus = in.readInt();
        bids = in.readInt();
        discounts = in.readInt();
        lastauction = in.readInt();
        extradiscounts = in.readInt();
        codevx = in.readInt();
        codereq = in.readByte() != 0;
        offermsg = in.readString();
        hasoffer = in.readByte() != 0;
        vvlink = in.readString();
        hasvideo = in.readByte() != 0;
        showHideAds = in.readByte() != 0;
        hasinv = in.readByte() != 0;
        hashcarcat = in.readByte() != 0;
        allaucprice = in.readInt();
        exaucprice = in.readInt();
        notnumber = in.readInt();
        hidechat = in.readByte() != 0;
        auctionId = in.readInt();
        auctionName = in.readString();
        sendingBidLink = in.readString();
        receivingResultsLink = in.readString();
        image = in.readString();
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public UserModel setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address==null?"لا يوجد عنوان":address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public String getNationalid() {
        return nationalid;
    }

    public void setNationalid(String nationalid) {
        this.nationalid = nationalid;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getCityrate() {
        return cityrate;
    }

    public void setCityrate(String cityrate) {
        this.cityrate = cityrate;
    }


    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getInsurancestatus() {
        return insurancestatus;
    }

    public void setInsurancestatus(int insurancestatus) {
        this.insurancestatus = insurancestatus;
    }

    public int getBids() {
        return bids;
    }

    public void setBids(int bids) {
        this.bids = bids;
    }

    public int getDiscounts() {
        return discounts;
    }

    public void setDiscounts(int discounts) {
        this.discounts = discounts;
    }

    public int getExtradiscounts() {
        return extradiscounts;
    }

    public void setExtradiscounts(int extradiscounts) {
        this.extradiscounts = extradiscounts;
    }

    public int getCodevx() {
        return codevx;
    }

    public void setCodevx(int codevx) {
        this.codevx = codevx;
    }

    public boolean isCodereq() {
        return codereq;
    }

    public void setCodereq(boolean codereq) {
        this.codereq = codereq;
    }

    public String getOffermsg() {
        return offermsg;
    }

    public void setOffermsg(String offermsg) {
        this.offermsg = offermsg;
    }

    public boolean isHasoffer() {
        return hasoffer;
    }

    public void setHasoffer(boolean hasoffer) {
        this.hasoffer = hasoffer;
    }

    public String getVvlink() {
        return vvlink;
    }

    public void setVvlink(String vvlink) {
        this.vvlink = vvlink;
    }

    public boolean isHasvideo() {
        return hasvideo;
    }

    public void setHasvideo(boolean hasvideo) {
        this.hasvideo = hasvideo;
    }

    public boolean isShowHideAds() {
        return showHideAds;
    }

    public void setShowHideAds(boolean showHideAds) {
        this.showHideAds = showHideAds;
    }

    public int getLastauction() {
        return lastauction;
    }

    public void setLastauction(int lastauction) {
        this.lastauction = lastauction;
    }

    public boolean isHasinv() {
        return hasinv;
    }

    public void setHasinv(boolean hasinv) {
        this.hasinv = hasinv;
    }

    public int getAllaucprice() {
        return allaucprice;
    }

    public void setAllaucprice(int allaucprice) {
        this.allaucprice = allaucprice;
    }

    public boolean isHashcarcat() {
        return hashcarcat;
    }

    public void setHashcarcat(boolean hashcarcat) {
        this.hashcarcat = hashcarcat;
    }

    public int getExaucprice() {
        return exaucprice;
    }

    public void setExaucprice(int exaucprice) {
        this.exaucprice = exaucprice;
    }

    public boolean isHidechat() {
        return hidechat;
    }

    public void setHidechat(boolean hidechat) {
        this.hidechat = hidechat;
    }

    public int getNotnumber() {
        return notnumber;
    }

    public void setNotnumber(int notnumber) {
        this.notnumber = notnumber;
    }

    public int getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(int auctionId) {
        this.auctionId = auctionId;
    }

    public String getAuctionName() {
        return auctionName;
    }

    public void setAuctionName(String auctionName) {
        this.auctionName = auctionName;
    }

    public String getSendingBidLink() {
        return sendingBidLink;
    }

    public void setSendingBidLink(String sendingBidLink) {
        this.sendingBidLink = sendingBidLink;
    }

    public String getReceivingResultsLink() {
        return receivingResultsLink;
    }

    public void setReceivingResultsLink(String receivingResultsLink) {
        this.receivingResultsLink = receivingResultsLink;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getProfile() {
        return profile;
    }

    private String toJson() {
        return new Gson().toJson(this);
    }
    public static UserModel fromJson(String json) {
        if (TextUtils.isEmpty(json))
            return null;
        try {
            return new Gson().fromJson(json, UserModel.class);
        }
        catch (Exception ex){
            return null;
        }

    }
    public UserModel save(){
        Utils.saveSharedPrefrences(KEY_USER,toJson());
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(profile);
        dest.writeString(mobile);
        dest.writeString(email);
        dest.writeString(address);
        dest.writeString(insurance);
        dest.writeString(nationalid);
        dest.writeString(cityid);
        dest.writeString(cityname);
        dest.writeString(cityrate);
        dest.writeString(amount);
        dest.writeInt(seconds);
        dest.writeString(imei);
        dest.writeString(token);
        dest.writeInt(insurancestatus);
        dest.writeInt(bids);
        dest.writeInt(discounts);
        dest.writeInt(lastauction);
        dest.writeInt(extradiscounts);
        dest.writeInt(codevx);
        dest.writeByte((byte) (codereq ? 1 : 0));
        dest.writeString(offermsg);
        dest.writeByte((byte) (hasoffer ? 1 : 0));
        dest.writeString(vvlink);
        dest.writeByte((byte) (hasvideo ? 1 : 0));
        dest.writeByte((byte) (showHideAds ? 1 : 0));
        dest.writeByte((byte) (hasinv ? 1 : 0));
        dest.writeByte((byte) (hashcarcat ? 1 : 0));
        dest.writeInt(allaucprice);
        dest.writeInt(exaucprice);
        dest.writeInt(notnumber);
        dest.writeByte((byte) (hidechat ? 1 : 0));
        dest.writeInt(auctionId);
        dest.writeString(auctionName);
        dest.writeString(sendingBidLink);
        dest.writeString(receivingResultsLink);
        dest.writeString(image);
    }

    @NonNull
    @Override
    public String toString() {
        return id;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return TextUtils.equals(this+"",obj+"");
    }
}