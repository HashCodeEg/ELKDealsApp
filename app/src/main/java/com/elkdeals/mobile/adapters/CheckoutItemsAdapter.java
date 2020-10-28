package com.elkdeals.mobile.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.Constants;
import com.elkdeals.mobile.api.models.cart_model.CartProduct;
import com.elkdeals.mobile.api.models.cart_model.CartProductAttributes;
import com.elkdeals.mobile.api.models.product_model.Value;
import com.elkdeals.mobile.api.network.APIClient;

import java.util.ArrayList;
import java.util.List;

/**
 * CheckoutItemsAdapter is the adapter class of RecyclerView holding List of Products to be Ordered in Checkout
 **/

public class CheckoutItemsAdapter extends RecyclerView.Adapter<CheckoutItemsAdapter.MyViewHolder> {

    private Context context;
    private List<CartProduct> checkoutItemsList;
    
    private ProductAttributeValuesAdapter attributesAdapter;


    public CheckoutItemsAdapter(Context context, List<CartProduct> checkoutItemsList) {
        this.context = context;
        this.checkoutItemsList = checkoutItemsList;
    }



    //********** Called to Inflate a Layout from XML and then return the Holder *********//

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_checkout_items, parent, false);

        return new MyViewHolder(itemView);
    }



    //********** Called by RecyclerView to display the Data at the specified Position *********//

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        // Get the data model based on Position
        final CartProduct cartProduct = checkoutItemsList.get(position);

        // Set Product Image on ImageView with Glide Library
        Glide.with(context).load(APIClient.BASE_URL+ cartProduct.getCustomersBasketProduct().getProductsImage()).into(holder.checkout_item_cover);

        holder.checkout_item_title.setText(cartProduct.getCustomersBasketProduct().getProductsName());
        holder.checkout_item_quantity.setText(String.valueOf(cartProduct.getCustomersBasketProduct().getCustomersBasketQuantity()));
        holder.checkout_item_price.setText(Constants.CURRENCY_SYMBOL + cartProduct.getCustomersBasketProduct().getProductsPrice());
        holder.checkout_item_price_final.setText(Constants.CURRENCY_SYMBOL + cartProduct.getCustomersBasketProduct().getTotalPrice());
        holder.checkout_item_category.setText(cartProduct.getCustomersBasketProduct().getCategoriesName());
    
    
        List<Value> selectedAttributeValues= new ArrayList<>();
        List<CartProductAttributes> productAttributes= new ArrayList<>();
    
        productAttributes = cartProduct.getCustomersBasketProductAttributes();
    
        for (int i=0;  i<productAttributes.size();  i++) {
            selectedAttributeValues.add(productAttributes.get(i).getValues().get(0));
        }
    
        // Initialize the ProductAttributeValuesAdapter for RecyclerView
        attributesAdapter = new ProductAttributeValuesAdapter(context, selectedAttributeValues);
    
        // Set the Adapter and LayoutManager to the RecyclerView
        holder.attributes_recycler.setAdapter(attributesAdapter);
        holder.attributes_recycler.setLayoutManager(new LinearLayoutManager(context));
    
        attributesAdapter.notifyDataSetChanged();
        

    }



    //********** Returns the total number of items in the data set *********//

    @Override
    public int getItemCount() {
        return checkoutItemsList.size();
    }



    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

    public static class MyViewHolder extends RecyclerView.ViewHolder {
    
        private ImageView checkout_item_cover;
        private RecyclerView attributes_recycler;
        private TextView checkout_item_title, checkout_item_quantity, checkout_item_price, checkout_item_price_final, checkout_item_category;


        public MyViewHolder(final View itemView) {
            super(itemView);

            checkout_item_cover = itemView.findViewById(R.id.checkout_item_cover);
            checkout_item_title = itemView.findViewById(R.id.checkout_item_title);
            checkout_item_quantity = itemView.findViewById(R.id.checkout_item_quantity);
            checkout_item_price = itemView.findViewById(R.id.checkout_item_price);
            checkout_item_price_final = itemView.findViewById(R.id.checkout_item_price_final);
            checkout_item_category = itemView.findViewById(R.id.checkout_item_category);
    
            attributes_recycler = itemView.findViewById(R.id.order_item_attributes_recycler);
        }
    }

}

