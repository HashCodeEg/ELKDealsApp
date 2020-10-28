package com.elkdeals.mobile.api.models.cart_model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.elkdeals.mobile.Utils.CartProductAttributesConverter;
import com.elkdeals.mobile.Utils.ProductDetailsConverter;
import com.elkdeals.mobile.api.models.product_model.ProductDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@Entity
public class CartProduct {

    @SerializedName("customers_id")
    @Expose
    private int customersId;
    @PrimaryKey (autoGenerate = true)
    @NonNull
    @SerializedName("customers_basket_id")
    @Expose
    private int customersBasketId;
    @SerializedName("customers_basket_date_added")
    @Expose
    private String customersBasketDateAdded;
    @SerializedName("customers_basket_product")
    @Expose
    @TypeConverters(ProductDetailsConverter.class)
    private ProductDetails customersBasketProduct;
    @SerializedName("customers_basket_product_attributes")
    @Expose
    @TypeConverters(CartProductAttributesConverter.class)
    private List<CartProductAttributes> customersBasketProductAttributes = new ArrayList<>();

    public int getCustomersId() {
        return customersId;
    }

    public void setCustomersId(int customersId) {
        this.customersId = customersId;
    }

    public int getCustomersBasketId() {
        return customersBasketId;
    }

    public void setCustomersBasketId(int customersBasketId) {
        this.customersBasketId = customersBasketId;
    }

    public String getCustomersBasketDateAdded() {
        return customersBasketDateAdded;
    }

    public void setCustomersBasketDateAdded(String customersBasketDateAdded) {
        this.customersBasketDateAdded = customersBasketDateAdded;
    }

    public ProductDetails getCustomersBasketProduct() {
        return customersBasketProduct;
    }

    public void setCustomersBasketProduct(ProductDetails customersBasketProduct) {
        this.customersBasketProduct = customersBasketProduct;
    }

    public List<CartProductAttributes> getCustomersBasketProductAttributes() {
        return customersBasketProductAttributes;
    }

    public void setCustomersBasketProductAttributes(List<CartProductAttributes> customersBasketProductAttributes) {
        this.customersBasketProductAttributes = customersBasketProductAttributes;
    }

}
