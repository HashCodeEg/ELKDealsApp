package com.elkdeals.mobile.ui.store;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.adapters.OrdersListAdapter;
import com.elkdeals.mobile.api.models.order_model.OrderData;
import com.elkdeals.mobile.api.models.order_model.OrderDetails;
import com.elkdeals.mobile.api.network.APIClient;
import com.elkdeals.mobile.ui.BaseFragment;
import com.elkdeals.mobile.model.RepositorySidalitac;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;


public class My_Orders extends BaseFragment {

    public static final String TAG = "My_Orders";
    View rootView;
    String customerID;
    
    TextView emptyRecord;
    FrameLayout banner_adView;
    RecyclerView orders_recycler;

    OrdersListAdapter ordersListAdapter;

    List<OrderDetails> ordersList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_my_orders, container, false);
        return view=rootView;
    }
    //*********** Adds Orders returned from the Server to the OrdersList ********//
    private void addOrders(OrderData orderData) {
        // Add Orders to ordersList from the List of OrderData
        ordersList = orderData.getData();
        // Initialize the OrdersListAdapter for RecyclerView
        ordersListAdapter = new OrdersListAdapter(getContext(), customerID, ordersList);
        // Set the Adapter and LayoutManager to the RecyclerView
        orders_recycler.setAdapter(ordersListAdapter);
        orders_recycler.setLayoutManager(new LinearLayoutManager(activity));
        ordersListAdapter.notifyDataSetChanged();
    }
    //*********** Request User's Orders from the Server ********//
    public void RequestMyOrders() {
activity.showProgressBar("loading...");
        Call<OrderData> call = APIClient.getInstance().getOrders(customerID);
        call.enqueue(new Callback<OrderData>() {
            @Override
            public void onResponse(Call<OrderData> call, retrofit2.Response<OrderData> response) {
                Log.e("order", new Gson().toJson(response.body()));
                activity.hideDialog();
                if(response.body()==null||
                        response.body().getSuccess().equalsIgnoreCase("0")){
                    showReload();
                    return;
                }
                hideReload();
                // Check if the Response is successful
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {
                        // Orders have been returned. Add Orders to the ordersList
                        addOrders(response.body());
                    }
                    else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        emptyRecord.setVisibility(View.VISIBLE);
                        Snackbar.make(rootView, response.body().getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                    else {
                        // Unable to get Success status
                        emptyRecord.setVisibility(View.VISIBLE);
                        Snackbar.make(rootView, getString(R.string.error), Snackbar.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<OrderData> call, Throwable t) {
                activity.showMessageDialog("NetworkCallFailure : "+t);
                emptyRecord.setVisibility(View.GONE);
                showReload();
            }
        });
    }
    @Override
    public void initViews() {
        customerID= RepositorySidalitac.getUserInfo().getData().get(0).getCustomersId();
        // Binding Layout Views
        emptyRecord = rootView.findViewById(R.id.empty_record);
        orders_recycler = rootView.findViewById(R.id.orders_recycler);
        // Hide some of the Views
        emptyRecord.setVisibility(View.GONE);
        // Request for User's Orders
        RequestMyOrders();
    }

    @OnClick(R.id.reload)
    public void reload() {
        initViews();
    }


    public void showReload(){
        view.findViewById(R.id.reload).setVisibility(View.VISIBLE);
        orders_recycler.setVisibility(View.GONE);
    }
    public void hideReload(){
        view.findViewById(R.id.reload).setVisibility(View.GONE);
        orders_recycler.setVisibility(View.VISIBLE);
    }
    @Override
    public String getTAG() {
        return TAG;
    }
}

