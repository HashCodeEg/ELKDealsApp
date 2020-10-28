
package com.elkdeals.mobile.api.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.elkdeals.mobile.App;
import com.elkdeals.mobile.R;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CategoriesModel implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("haspro")
    @Expose
    private boolean haspro;
    @SerializedName("image")
    @Expose
    private String image;
    private int res;

    public CategoriesModel() {

    }
    protected CategoriesModel(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        desc = in.readString();
        price = in.readString();
        total = in.readString();
        haspro = in.readByte() != 0;
        image = in.readString();
        res = in.readInt();
    }

    public static final Creator<CategoriesModel> CREATOR = new Creator<CategoriesModel>() {
        @Override
        public CategoriesModel createFromParcel(Parcel in) {
            return new CategoriesModel(in);
        }

        @Override
        public CategoriesModel[] newArray(int size) {
            return new CategoriesModel[size];
        }
    };

    public static CategoriesModel CreateInstance(String name, int res, int id){
        CategoriesModel CategoriesModel=new CategoriesModel();
        CategoriesModel.setName(name);
        CategoriesModel.setRes(res);
        CategoriesModel.setId(id);
        return CategoriesModel;
    }
    public static List<CategoriesModel> getDummyAuctions(){
        ArrayList<CategoriesModel>categories=new ArrayList<>();
        categories.add(CategoriesModel.CreateInstance(App.getInstance().getString(R.string.appliance), R.drawable.appliance,262));
        categories.add(CategoriesModel.CreateInstance(App.getInstance().getString(R.string.cards), R.drawable.cards,268));
        categories.add(CategoriesModel.CreateInstance(App.getInstance().getString(R.string.cars), R.drawable.cars,211));
        categories.add(CategoriesModel.CreateInstance(App.getInstance().getString(R.string.phones), R.drawable.phones,261));
        categories.add(CategoriesModel.CreateInstance(App.getInstance().getString(R.string.laptops), R.drawable.laptops,267));
        return categories;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public boolean isHaspro() {
        return haspro;
    }

    public void setHaspro(boolean haspro) {
        this.haspro = haspro;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public int getRes() {
        return res;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(name);
        dest.writeString(desc);
        dest.writeString(price);
        dest.writeString(total);
        dest.writeByte((byte) (haspro ? 1 : 0));
        dest.writeString(image);
        dest.writeInt(res);
    }
}
