package com.elkdeals.mobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.Utils;
import com.elkdeals.mobile.activities.BaseActivity;
import com.elkdeals.mobile.api.models.order_model.OrderDetails;
import com.elkdeals.mobile.ui.store.Order_Details;

import java.util.List;


/**
 * OrdersListAdapter is the adapter class of RecyclerView holding List of Orders in My_Orders
 **/

public class OrdersListAdapter extends RecyclerView.Adapter<OrdersListAdapter.MyViewHolder> {
    Context context;
    String customerID;
    List<OrderDetails> ordersList;


    public OrdersListAdapter(Context context, String customerID, List<OrderDetails> ordersList) {
        this.context = context;
        this.customerID = customerID;
        this.ordersList = ordersList;
    }



    //********** Called to Inflate a Layout from XML and then return the Holder *********//

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_card_orders, parent, false);

        return new MyViewHolder(itemView);
    }



    //********** Called by RecyclerView to display the Data at the specified Position *********//

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        
        // Get the data model based on Position
        final OrderDetails orderDetails = ordersList.get(position);
    
        int noOfProducts = 0;
        for (int i=0;  i<orderDetails.getProducts().size();  i++) {
            // Count no of Products
            noOfProducts += orderDetails.getProducts().get(i).getProductsQuantity();

        }

        holder.order_id.setText(String.valueOf(orderDetails.getOrdersId()));
        holder.order_status.setText(orderDetails.getOrdersStatus());
        holder.order_price.setText(Utils.getStringRes(R.string.egp)+ orderDetails.getOrderPrice());
        holder.order_date.setText(orderDetails.getDatePurchased());
        holder.order_product_count.setText(String.valueOf(noOfProducts));
    

        // Check Order's status
        if (orderDetails.getOrdersStatus().equalsIgnoreCase("Pending")) {
            holder.order_status.setTextColor(Utils.getColorRes( R.color.colorAccentAuctions));
        } else if (orderDetails.getOrdersStatus().equalsIgnoreCase("Completed")) {
            holder.order_status.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDarkAuctions));
        } else {
            holder.order_status.setTextColor(ContextCompat.getColor(context, R.color.red));
        }
    
        
    
        holder.order_view_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseActivity)context).addPage(Order_Details.createInstance(orderDetails));
            }
        });

    }



    //********** Returns the total number of items in the data set *********//

    @Override
    public int getItemCount() {
        return ordersList.size();
    }



    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView order_view_btn;
        private TextView order_id, order_product_count, order_status, order_price, order_date;


        public MyViewHolder(final View itemView) {
            super(itemView);
    
            order_view_btn = itemView.findViewById(R.id.order_view_btn);
            order_id = itemView.findViewById(R.id.order_id);
            order_product_count = itemView.findViewById(R.id.order_products_count);
            order_status = itemView.findViewById(R.id.order_status);
            order_price = itemView.findViewById(R.id.order_price);
            order_date = itemView.findViewById(R.id.order_date);
        }
    }
}

