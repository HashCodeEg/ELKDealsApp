package com.elkdeals.mobile.ui.requests;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.Utils;
import com.elkdeals.mobile.adapters.OffersAdapter;
import com.elkdeals.mobile.api.models.Notification;
import com.elkdeals.mobile.api.models.UserModel;
import com.elkdeals.mobile.api.models.order_model.PostOrder;
import com.elkdeals.mobile.api.models.order_model.PostProducts;
import com.elkdeals.mobile.api.models.requests.OfferDetails;
import com.elkdeals.mobile.api.models.requests.RequestDetails;
import com.elkdeals.mobile.api.models.user_model.UserData;
import com.elkdeals.mobile.ui.BaseFragment;
import com.elkdeals.mobile.ui.account.ShippingAddresses;
import com.elkdeals.mobile.interfaces.OnItemClickListener;
import com.elkdeals.mobile.model.Repository;
import com.elkdeals.mobile.model.RepositorySidalitac;
import com.elkdeals.mobile.viewmodel.RequestsViewModelSidalitac;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class Offers extends BaseFragment {


    public static final String TAG = "Offers";
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    RequestsViewModelSidalitac requestsViewModelSidalitac;
    private RequestDetails requestDetails;
    private OffersAdapter adapter;

    public Offers() {
        // Required empty public constructor
    }

    public static Offers createInstance(RequestDetails requestDetails) {
        Offers offers = new Offers();
        offers.requestDetails = requestDetails;
        return offers;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return view = inflater.inflate(R.layout.fragment_offers, container, false);
    }


    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public void initViews() {
        requestsViewModelSidalitac = ViewModelProviders.of(this).get(RequestsViewModelSidalitac.class);

        requestsViewModelSidalitac.getProgress().observe(this, s -> {
            if (!TextUtils.isEmpty(s))
                activity.showProgressBar(s);
            else activity.hideDialog();
        });
        requestsViewModelSidalitac.getMessage().observe(this, s -> activity.showMessageDialog(s));

        requestsViewModelSidalitac.getOffers(requestDetails.getRequest_id());
        requestsViewModelSidalitac.getOffersData().observe(this, new Observer<List<OfferDetails>>() {
            @Override
            public void onChanged(List<OfferDetails> offerDetails) {
                if (offerDetails == null||offerDetails.size()==0) {
                    showReload();
                } else {
                    adapter.addItems(offerDetails);
                    hideReload();
                }
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new OffersAdapter(new OnItemClickListener() {
            @Override
            public void OnItemClick(Object data) {
                activity.showToast(R.string.name_activity_notifications);
                activity.showMessageDialog(Utils.getStringRes(R.string.accept_offer_confirmation),
                        Utils.getStringRes(R.string.accept_offer_confirmation_message), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                acceptOffer((OfferDetails) data);
                            }
                        });
            }
        });
        adapter.addItems(Notification.getDummy());
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.reload)
    public void reload() {
        initViews();
    }


    public void showReload() {
        view.findViewById(R.id.reload).setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    public void hideReload() {
        view.findViewById(R.id.reload).setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    public void acceptOffer(OfferDetails offerDetails) {
        PostOrder orderDetails = new PostOrder();
        List<PostProducts> orderProductList = new ArrayList<>();
        PostProducts orderProduct = new PostProducts();
        orderProduct.setFinalPrice(offerDetails.getPrice());
        orderProduct.setProductsName(offerDetails.getTitle());
        orderProduct.setCategoriesId(offerDetails.getCategoryId());
        //orderProduct.setCategoriesName(offerDetails.get());
        orderProduct.setCustomersBasketQuantity(1);
        orderProduct.setPrice(offerDetails.getPrice());
        orderProductList.add(orderProduct);
        UserData userData = RepositorySidalitac.getUserInfo();
        com.elkdeals.mobile.api.models.user_model.UserDetails userInfo = userData.getData().get(0);
        // Set Customer Info
        orderDetails.setCustomersId(Integer.parseInt(userInfo.getCustomersId()));
        orderDetails.setCustomersName(userInfo.getCustomersFirstname());
        orderDetails.setCustomersTelephone(userInfo.getCustomersTelephone());
        orderDetails.setCustomersEmailAddress(userInfo.getCustomersEmailAddress());
        // Set Checkout Price and Products
        orderDetails.setProductsTotal(Double.parseDouble(offerDetails.getPrice()));
        orderDetails.setShippingCost(Double.parseDouble(offerDetails.getShipping_price()));
        orderDetails.setTotalPrice(Double.parseDouble(offerDetails.getPrice()));
        orderDetails.setProducts(orderProductList);
        UserModel elkUser = Repository.getUserInfo();
        PlaceOrderNow(orderDetails);
    }

    private void PlaceOrderNow(PostOrder orderDetails) {
        BaseFragment fragment = ShippingAddresses.createInstance(orderDetails);
        activity.addPage(fragment);
    }
}
