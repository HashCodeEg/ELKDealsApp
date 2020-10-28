package com.elkdeals.mobile.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.Utils;
import com.elkdeals.mobile.api.models.order_model.OrderProducts;
import com.elkdeals.mobile.api.models.order_model.PostProductsAttributes;
import com.elkdeals.mobile.api.models.product_model.Value;
import com.elkdeals.mobile.api.network.APIClient;

import java.util.ArrayList;
import java.util.List;

/**
 * OrderedProductsListAdapter is the adapter class of RecyclerView holding List of Ordered Products in Order_Details
 **/

public class OrderedProductsListAdapter extends RecyclerView.Adapter<OrderedProductsListAdapter.MyViewHolder> {

    private Context context;
    private List<OrderProducts> orderProductsList;
    
    private ProductAttributeValuesAdapter attributesAdapter;


    public OrderedProductsListAdapter(Context context, List<OrderProducts> orderProductsList) {
        this.context = context;
        this.orderProductsList = orderProductsList;
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
        final OrderProducts product = orderProductsList.get(position);

        Utils.loadImage(holder.checkout_item_cover,APIClient.BASE_URL,product.getImage());

        holder.checkout_item_title.setText(product.getProductsName());
        holder.checkout_item_quantity.setText(String.valueOf(product.getProductsQuantity()));
        holder.checkout_item_price.setText(Utils.getStringRes(R.string.egp)+ product.getProductsPrice());
        holder.checkout_item_price_final.setText(Utils.getStringRes(R.string.egp)+ product.getFinalPrice());
        holder.checkout_item_category.setText(product.getCategoriesName());
    
    
        List<Value> selectedAttributeValues= new ArrayList<>();
        List<PostProductsAttributes> productAttributes = new ArrayList<>();
    
        productAttributes = product.getAttribute();
    
        for (int i=0;  i<productAttributes.size();  i++) {
            Value value = new Value();
            value.setValue(productAttributes.get(i).getProductsOptionsValues());
            value.setPrice(productAttributes.get(i).getOptionsValuesPrice());
            value.setPricePrefix(productAttributes.get(i).getPricePrefix());
            
            selectedAttributeValues.add(value);
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
        return orderProductsList.size();
    }



    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout checkout_item;
        private ImageView checkout_item_cover;
        private RecyclerView attributes_recycler;
        private TextView checkout_item_title, checkout_item_quantity, checkout_item_price, checkout_item_price_final, checkout_item_category;


        public MyViewHolder(final View itemView) {
            super(itemView);

            checkout_item = itemView.findViewById(R.id.checkout_item);
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

