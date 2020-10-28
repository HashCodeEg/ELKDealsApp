
package com.elkdeals.mobile.api.models.category_model;

import android.text.TextUtils;

import com.elkdeals.mobile.Utils.Constants;
import com.elkdeals.mobile.Utils.Utils;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class CategoryData {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("data")
    @Expose
    private List<CategoryDetails> data = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("categories")
    @Expose
    private Integer categories;

    
    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public  List<CategoryDetails>  getData() {
        return data;
    }

    public void setData(List<CategoryDetails> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCategories() {
        return categories;
    }

    public void setCategories(Integer categories) {
        this.categories = categories;
    }


    public void save(){
        Gson g=new Gson();
        Utils.saveSharedPrefrences(Constants.CATEGORIES,g.toJson(this));
    }
    public static List<CategoryDetails> read() {
        Gson g=new Gson();
        try {
            CategoryData details=
                    g.fromJson(
                            Utils.getSharedPrefrences
                                    (Constants.CATEGORIES,"")
                            , CategoryData.class);
            if(details==null||details.getData()==null)
                return new ArrayList<>();
            return details.getData();
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    public static CategoryDetails read(String cat){
        if (TextUtils.isEmpty(cat))
            return null;
        List<CategoryDetails> cats = read();
        if (cats == null||cats.size()==0)
            return null;
        for (CategoryDetails categoryDetails : cats)
            if (cat.equalsIgnoreCase(categoryDetails.getId()))
                return categoryDetails;
        return null;
    }
}
