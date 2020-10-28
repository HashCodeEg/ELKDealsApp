package com.elkdeals.mobile.Utils;

import androidx.room.TypeConverter;

import com.elkdeals.mobile.api.models.cart_model.CartProductAttributes;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class CartProductAttributesConverter {
    static Gson gson = new Gson();

    @TypeConverter
    public static List<CartProductAttributes> ToCartProductAttributesList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<CartProductAttributes>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String fromCartProductAttributesList(List<CartProductAttributes> someObjects) {
        return gson.toJson(someObjects);
    }
}
