package com.elkdeals.mobile.Utils;

import androidx.room.TypeConverter;

import com.elkdeals.mobile.api.models.product_model.ProductDetails;
import com.google.gson.Gson;

public class ProductDetailsConverter {
    static Gson gson = new Gson();

    @TypeConverter
    public static ProductDetails ToProductDetailsList(String data) {
        try {
            return gson.fromJson(data, ProductDetails.class);
        }
        catch (Exception e){
            return null;
        }
    }

    @TypeConverter
    public static String fromProductDetailsList(ProductDetails someObjects) {
        return gson.toJson(someObjects);
    }
}
