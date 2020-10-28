package com.elkdeals.mobile.ui.account;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elkdeals.mobile.App;
import com.elkdeals.mobile.R;
import com.elkdeals.mobile.adapters.AddressListAdapter;
import com.elkdeals.mobile.api.models.UserModel;
import com.elkdeals.mobile.api.models.address_model.AddressData;
import com.elkdeals.mobile.api.models.address_model.AddressDetails;
import com.elkdeals.mobile.api.models.coupons_model.CouponsInfo;
import com.elkdeals.mobile.api.models.order_model.PostOrder;
import com.elkdeals.mobile.ui.BaseFragment;
import com.elkdeals.mobile.model.Repository;
import com.elkdeals.mobile.model.RepositorySidalitac;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShippingAddresses extends BaseFragment {


    public static final String TAG = "ShippingAddresses";
    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @BindView(R.id.title)
    TextView title;
    private PostOrder orderDetails;
    private AddressListAdapter adapter;
    private double checkoutSubtotal;
    private double checkoutTotal;
    private double checkoutTax;
    private double checkoutShipping;
    private double checkoutDiscount;
    private double checkoutShippingCost;
    private List<CouponsInfo> couponsList;

    public ShippingAddresses() {
        // Required empty public constructor
    }

    public static BaseFragment createInstance(PostOrder orderDetails) {
        ShippingAddresses addresses=new ShippingAddresses();
        addresses.orderDetails=orderDetails;
        return addresses;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return view = inflater.inflate(R.layout.fragment_notifications, container, false);
    }


    @Override
    public String getTAG() {
        return TAG;
    }


    @Override
    public void initViews() {
        title.setText(R.string.addresses);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);

        RepositorySidalitac.getAdressesLiveData().observe(this, new Observer<AddressData>() {
            @Override
            public void onChanged(AddressData addressData) {
                if (addressData==null){

                    adapter= new AddressListAdapter(ShippingAddresses.this, RepositorySidalitac.getUserInfo().getData().get(0).getCustomersId(),-1,new ArrayList<>());
                    recyclerView.setAdapter(adapter);
                    showError();
                    return;
                }
                else if(addressData.getData()==null||addressData.getData().size()==0)
                {
                    adapter= new AddressListAdapter(ShippingAddresses.this, RepositorySidalitac.getUserInfo().getData().get(0).getCustomersId(),-1,new ArrayList<>());
                    recyclerView.setAdapter(adapter);
                    showEmpty();
                    return;
                }
                hidereload();
                adapter = new AddressListAdapter(ShippingAddresses.this, RepositorySidalitac.getUserInfo().getData().get(0).getCustomersId(),-1,addressData.getData());
                recyclerView.setAdapter(adapter);
            }
        });
        recyclerView.setLayoutManager(linearLayoutManager);
        if(orderDetails!=null)
        {
            view.findViewById(R.id.confirm_order_to_address).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume_() {
        super.onResume_();
        adapter= new AddressListAdapter(ShippingAddresses.this,
                RepositorySidalitac.getUserInfo().getData().get(0).getCustomersId(),-1,new ArrayList<>());
        recyclerView.setAdapter(adapter);
        RequestAllAddresses();
    }
    public void RequestAllAddresses() {
        RepositorySidalitac.RequestAllAddresses(activity);
    }

    @BindView(R.id.reloadText)
    TextView reloadText;
    @BindView(R.id.reloadImage)
    ImageView reloadImage;
    @BindView(R.id.reload)
    View reload;
    public void showError(){
        reloadText.setText("خطأ فى الاتصال,اعادة التحميل");
        view.findViewById(R.id.reload).setVisibility(View.VISIBLE);
        reloadImage.setImageResource(R.drawable.ic_logo_error);
        recyclerView.setVisibility(View.GONE);
    }
    public void showEmpty(){
        reloadText.setText("ليس لديك اى عناوين");
        view.findViewById(R.id.reload).setVisibility(View.VISIBLE);
        reloadImage.setImageResource(R.drawable.ic_empty);
        recyclerView.setVisibility(View.GONE);
    }
    public void hidereload(){
        view.findViewById(R.id.reload).setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }
    @OnClick(R.id.reload)
    public void reload(){
        hidereload();
        RepositorySidalitac.RequestAllAddresses(activity);
    }

    @OnClick(R.id.add_address)
    public void addAddress(){
        Add_Address fragment = new Add_Address();
        Bundle args = new Bundle();
        args.putBoolean("isUpdate", false);
        fragment.setArguments(args);
        activity.addPage(fragment);
    }

    boolean loading=false;

    @OnClick(R.id.confirm_order_to_address)
    public void PlaceOrderNow() {

        if(orderDetails==null)
        {
            activity.showMessageDialog("الرجاء اضافة منتجات الى السلة.");
            return;
        }
        UserModel elkUser= Repository.getUserInfo();
        AddressDetails addressDetails=adapter.getDefaultAddress();
        if(adapter==null||addressDetails==null)
        {
            activity.showMessageDialog("الرجاء اختيار او اضافة عنوان شحن");
            return;
        }
        App.getInstance().setAddress(addressDetails);
        addressDetails.setPhone(elkUser.getMobile());
        activity.addPage(new Shipping_Methods());
//        if(loading)
//            return;
//
//        // Set Shipping  Info
//        orderDetails.setDeliveryFirstname(addressDetails.getFirstname());
//        orderDetails.setDeliveryLastname(addressDetails.getLastname());
//        orderDetails.setDeliveryStreetAddress(addressDetails.getStreet());
//        orderDetails.setDeliveryPostcode("");
//        orderDetails.setDeliverySuburb(addressDetails.getSuburb());
//        orderDetails.setDeliveryCity(addressDetails.getCity());
//        orderDetails.setDeliveryZone(addressDetails.getZoneName());
//        orderDetails.setDeliveryCountry(addressDetails.getCountryName());
//        orderDetails.setDeliveryCountryId(String.valueOf(addressDetails.getCountriesId()));
//
//        // Set Billing  Info
//        orderDetails.setBillingFirstname(addressDetails.getFirstname());
//        orderDetails.setBillingLastname(addressDetails.getLastname());
//        orderDetails.setBillingStreetAddress(addressDetails.getStreet());
//        orderDetails.setBillingPostcode("");
//        orderDetails.setBillingSuburb(addressDetails.getSuburb());
//        orderDetails.setBillingCity(addressDetails.getCity());
//        orderDetails.setBillingZone(addressDetails.getZoneName());
//        orderDetails.setBillingCountry(addressDetails.getCountryName());
//        orderDetails.setBillingCountryId(String.valueOf(addressDetails.getCountriesId()));
//        orderDetails.setCustomersTelephone(elkUser.getMobile());
//        orderDetails.setLanguage_id(4);/*
//        if (couponsList.size() > 0) {
//            orderDetails.setIsCouponApplied(1);
//  **      } else {
//            orderDetails.setIsCouponApplied(0);
//        orderDetails.setCouponAmount(checkoutDiscount);
//        orderDetails.setCoupons(couponsList);
//        }*/
//
//        orderDetails.setIsCouponApplied(0);
//        orderDetails.setCoupons(new ArrayList<>());
//        // Set PaymentNonceToken and PaymentMethod
//        orderDetails.setNonce("");
//        orderDetails.setPaymentMethod("cod");
//        orderDetails.setShippingMethod("freeShipping");
//        orderDetails.setShippingCost(0);
//        orderDetails.setBilling_phone(elkUser.getMobile());
//        orderDetails.setDelivery_phone(elkUser.getMobile());
//        Log.e("post val",orderDetails.toString());
//        loading=true;
//        activity.showProgressBar();
//        Call<OrderData> call = APIClient.getInstance().addToOrder(orderDetails);
//
//        call.enqueue(new Callback<OrderData>() {
//            @Override
//            public void onResponse(Call<OrderData> call, retrofit2.Response<OrderData> response) {
//
//                activity.hideDialog();
//                loading=false;
//
//                // Check if the Response is successful
//                if (response.isSuccessful()) {
//                    if (response.body().getSuccess().equalsIgnoreCase("1")) {
//
//                        Intent notificationIntent = new Intent(getContext(), MainActivity.class);
//                        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//                        // Order has been placed Successfully
//                        com.elkdeals.mobile.fireBase.NotificationUtils utils=new NotificationUtils(activity);
//                        activity.showMessageDialog( "تم عمل الطلب بنجاح", Utils.getStringRes(R.string.thank_you), new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                activity.removeTopFragment();
//                            }
//                        }, new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                activity.removeTopFragment();
//                            }
//                        });
//
//                        // Clear User's Cart
//                       StoreCartManager.getInstance().ClearCart();
//
//                    }
//                    else if (response.body().getSuccess().equalsIgnoreCase("0")) {
//                        Snackbar.make(view, response.body().getMessage(), Snackbar.LENGTH_LONG).show();
//
//                    }
//                    else {
//                        // Unable to get Success status
//                        Snackbar.make(view, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
//                    }
//                }
//                else {
//                    loading=false;
//                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<OrderData> call, Throwable t) {
//                activity.hideDialog();
//                loading=false;
//                Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
//            }
//        });
    }
//*********** Request the Server to Generate BrainTreeToken ********//

}
