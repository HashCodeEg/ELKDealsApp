package com.elkdeals.mobile.ui.store;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.Utils;
import com.elkdeals.mobile.adapters.CouponsAdapter;
import com.elkdeals.mobile.adapters.OrderedProductsListAdapter;
import com.elkdeals.mobile.api.models.coupons_model.CouponsInfo;
import com.elkdeals.mobile.api.models.order_model.OrderDetails;
import com.elkdeals.mobile.api.models.order_model.OrderProducts;
import com.elkdeals.mobile.ui.BaseFragment;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;


public class Order_Details extends BaseFragment {
public static final String TAG="Order_Details";
    View rootView;
    
    OrderDetails orderDetails;

    CardView buyer_comments_card, seller_comments_card;
    RecyclerView checkout_items_recycler, checkout_coupons_recycler;
    TextView checkout_subtotal, checkout_tax, checkout_shipping, checkout_discount, checkout_total;
    TextView billing_name, billing_street, billing_address, shipping_name, shipping_street, shipping_address;
    TextView order_price, order_products_count, order_status, order_date, shipping_method, payment_method, buyer_comments, seller_comments;

    List<CouponsInfo> couponsList;
    List<OrderProducts> orderProductsList;

    CouponsAdapter couponsAdapter;
    OrderedProductsListAdapter orderedProductsAdapter;


    public static Order_Details createInstance(OrderDetails orderDetails){
        Order_Details order_details=new Order_Details();
        order_details.orderDetails=orderDetails;
        return order_details;
    }
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.order_details, container, false);
        // Binding Layout Views
        order_price = rootView.findViewById(R.id.order_price);
        order_products_count = rootView.findViewById(R.id.order_products_count);
        shipping_method = rootView.findViewById(R.id.shipping_method);
        payment_method = rootView.findViewById(R.id.payment_method);
        order_status = rootView.findViewById(R.id.order_status);
        order_date = rootView.findViewById(R.id.order_date);
        checkout_subtotal = rootView.findViewById(R.id.checkout_subtotal);
        checkout_tax = rootView.findViewById(R.id.checkout_tax);
        checkout_shipping = rootView.findViewById(R.id.checkout_shipping);
        checkout_discount = rootView.findViewById(R.id.checkout_discount);
        checkout_total = rootView.findViewById(R.id.checkout_total);
        billing_name = rootView.findViewById(R.id.billing_name);
        billing_address = rootView.findViewById(R.id.billing_address);
        billing_street = rootView.findViewById(R.id.billing_street);
        shipping_name = rootView.findViewById(R.id.shipping_name);
        shipping_address = rootView.findViewById(R.id.shipping_address);
        shipping_street = rootView.findViewById(R.id.shipping_street);
        buyer_comments = rootView.findViewById(R.id.buyer_comments);
        seller_comments = rootView.findViewById(R.id.seller_comments);
        buyer_comments_card = rootView.findViewById(R.id.buyer_comments_card);
        seller_comments_card = rootView.findViewById(R.id.seller_comments_card);
        checkout_items_recycler = rootView.findViewById(R.id.checkout_items_recycler);
        checkout_coupons_recycler = rootView.findViewById(R.id.checkout_coupons_recycler);

        checkout_items_recycler.setNestedScrollingEnabled(false);
        checkout_coupons_recycler.setNestedScrollingEnabled(false);


        // Set Order Details

        couponsList = orderDetails.getCoupons();
        orderProductsList = orderDetails.getProducts();

        double subTotal = 0;
        int noOfProducts = 0;
        for (int i=0;  i<orderProductsList.size();  i++) {
            subTotal += Double.parseDouble(orderProductsList.get(i).getFinalPrice());
            noOfProducts += orderProductsList.get(i).getProductsQuantity();
        }
        
        
        String orderPrice = Utils.getStringRes(R.string.egp) + new DecimalFormat("#0.00", DecimalFormatSymbols.getInstance(Locale.ENGLISH)).format(Double.parseDouble(orderDetails.getOrderPrice()));
        String Tax = Utils.getStringRes(R.string.egp) + new DecimalFormat("#0.00", DecimalFormatSymbols.getInstance(Locale.ENGLISH)).format(Double.parseDouble(orderDetails.getTotalTax()));
        String Shipping = Utils.getStringRes(R.string.egp) + new DecimalFormat("#0.00", DecimalFormatSymbols.getInstance(Locale.ENGLISH)).format(Double.parseDouble(orderDetails.getShippingCost()));
        String Discount = Utils.getStringRes(R.string.egp) + new DecimalFormat("#0.00", DecimalFormatSymbols.getInstance(Locale.ENGLISH)).format(Double.parseDouble(orderDetails.getCouponAmount()));
        String Subtotal = Utils.getStringRes(R.string.egp) + new DecimalFormat("#0.00", DecimalFormatSymbols.getInstance(Locale.ENGLISH)).format(subTotal);
        String Total = Utils.getStringRes(R.string.egp) + new DecimalFormat("#0.00", DecimalFormatSymbols.getInstance(Locale.ENGLISH)).format(Double.parseDouble(orderDetails.getOrderPrice()));
    
    
        order_price.setText(orderPrice);
        order_products_count.setText(String.valueOf(noOfProducts));
        shipping_method.setText(orderDetails.getShippingMethod());
        payment_method.setText(orderDetails.getPaymentMethod());
        order_status.setText(orderDetails.getOrdersStatus());
        order_date.setText(orderDetails.getDatePurchased());

        checkout_tax.setText(Tax);
        checkout_shipping.setText(Shipping);
        checkout_discount.setText(Discount);
        checkout_subtotal.setText(Subtotal);
        checkout_total.setText(Total);

        billing_name.setText(orderDetails.getBillingName());
        billing_address.setText(orderDetails.getBillingCity());
        billing_street.setText(orderDetails.getBillingStreetAddress());
        shipping_name.setText(orderDetails.getDeliveryName());
        shipping_address.setText(orderDetails.getDeliveryCity());
        shipping_street.setText(orderDetails.getDeliveryStreetAddress());

        
        if (!TextUtils.isEmpty(orderDetails.getCustomerComments())) {
            buyer_comments_card.setVisibility(View.VISIBLE);
            buyer_comments.setText(orderDetails.getCustomerComments());
        } else {
            buyer_comments_card.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(orderDetails.getAdminComments())) {
            seller_comments_card.setVisibility(View.VISIBLE);
            seller_comments.setText(orderDetails.getAdminComments());
        } else {
            seller_comments_card.setVisibility(View.GONE);
        }


        couponsAdapter = new CouponsAdapter(getContext(), couponsList, false);

        checkout_coupons_recycler.setLayoutManager(new LinearLayoutManager(activity));
        checkout_coupons_recycler.setAdapter(couponsAdapter);

        
        orderedProductsAdapter = new OrderedProductsListAdapter(getContext(), orderProductsList);

        checkout_items_recycler.setAdapter(orderedProductsAdapter);
        checkout_items_recycler.setLayoutManager(new LinearLayoutManager(activity));
        checkout_items_recycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));


        return view=rootView;

    }


    @Override
    public void initViews() {

    }

    @Override
    public String getTAG() {
        return TAG;
    }
}



