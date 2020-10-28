package com.elkdeals.mobile.ui.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elkdeals.mobile.App;
import com.elkdeals.mobile.R;
import com.elkdeals.mobile.StoreCartManager;
import com.elkdeals.mobile.Utils.Constants;
import com.elkdeals.mobile.adapters.ShippingServicesAdapter;
import com.elkdeals.mobile.api.models.address_model.AddressDetails;
import com.elkdeals.mobile.api.models.cart_model.CartProduct;
import com.elkdeals.mobile.api.models.product_model.ProductDetails;
import com.elkdeals.mobile.api.models.shipping_model.PostTaxAndShippingData;
import com.elkdeals.mobile.api.models.shipping_model.ShippingRateData;
import com.elkdeals.mobile.api.models.shipping_model.ShippingRateDetails;
import com.elkdeals.mobile.api.models.shipping_model.ShippingService;
import com.elkdeals.mobile.api.network.APIClient;
import com.elkdeals.mobile.ui.BaseFragment;
import com.elkdeals.mobile.ui.store.Checkout;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


public class Shipping_Methods extends BaseFragment {

    public static final String TAG="Shipping_Methods";
    View rootView;
    Boolean isUpdate = false;
    
    String tax;
    Button saveShippingMethodBtn;
    TextView free_shipping_title, local_pickup_title, flat_rate_title, ups_shipping_title;
    LinearLayout free_shipping_layout, local_pickup_layout, flat_rate_layout, ups_shipping_layout;
    RecyclerView free_shipping_services, local_pickup_services, flat_rate_services, ups_shipping_services;

    List<CartProduct> checkoutItemsList;
    List<ShippingService> freeShippingServicesList;
    List<ShippingService> localPickupServicesList;
    List<ShippingService> flatRateServicesList;
    List<ShippingService> upsShippingServicesList;

    AddressDetails shippingAddress;
    ShippingServicesAdapter freeShippingServicesAdapter;
    ShippingServicesAdapter localPickupServicesAdapter;
    ShippingServicesAdapter flatRateServicesAdapter;
    ShippingServicesAdapter upsShippingServicesAdapter;
    
    private ShippingService shippingService;
    
    // To keep track of Checked Radio Button
    private RadioButton lastChecked_RB = null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.shipping_methods, container, false);

        if (getArguments() != null) {
            if (getArguments().containsKey("isUpdate")) {
                isUpdate = getArguments().getBoolean("isUpdate", false);
            }
        }

        // Set the Title of Toolbar
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.shipping_method));

        
        // Get the Shipping Method and Tax from AppContext
        tax = ((App) getContext().getApplicationContext()).getTax();
        shippingService = ((App) getContext().getApplicationContext()).getShippingService();
        shippingAddress = ((App) getContext().getApplicationContext()).getAddress();
        
        
        // Binding Layout Views
        saveShippingMethodBtn = rootView.findViewById(R.id.save_shipping_method_btn);
        free_shipping_title = rootView.findViewById(R.id.free_shipping_title);
        local_pickup_title = rootView.findViewById(R.id.local_pickup_title);
        flat_rate_title = rootView.findViewById(R.id.flat_rate_title);
        ups_shipping_title = rootView.findViewById(R.id.ups_shipping_title);
        free_shipping_services = rootView.findViewById(R.id.free_shipping_services_list);
        local_pickup_services = rootView.findViewById(R.id.local_pickup_services_list);
        flat_rate_services = rootView.findViewById(R.id.flat_rate_services_list);
        ups_shipping_services = rootView.findViewById(R.id.ups_shipping_services_list);
        free_shipping_layout = rootView.findViewById(R.id.free_shipping_layout);
        local_pickup_layout = rootView.findViewById(R.id.local_pickup_layout);
        flat_rate_layout = rootView.findViewById(R.id.flat_rate_layout);
        ups_shipping_layout = rootView.findViewById(R.id.ups_shipping_layout);
    
    
        free_shipping_services.setNestedScrollingEnabled(false);
        local_pickup_services.setNestedScrollingEnabled(false);
        flat_rate_services.setNestedScrollingEnabled(false);
        ups_shipping_services.setNestedScrollingEnabled(false);

        // Hide some Views
        free_shipping_layout.setVisibility(View.GONE);
        local_pickup_layout.setVisibility(View.GONE);
        flat_rate_layout.setVisibility(View.GONE);
        ups_shipping_layout.setVisibility(View.GONE);

        // Get checkoutItems from Local Databases User_Cart_DB
        checkoutItemsList = StoreCartManager.getInstance().getCartItems();

        freeShippingServicesList = new ArrayList<>();
        localPickupServicesList = new ArrayList<>();
        flatRateServicesList = new ArrayList<>();
        upsShippingServicesList = new ArrayList<>();


        // Request Shipping Rates
        RequestShippingRates();


        saveShippingMethodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (shippingService != null) {

                    // Save the AddressDetails
                    App.getInstance().setTax(tax);
                    App.getInstance().setShippingService(shippingService);


                    // Check if an Address is being Edited
                    if (isUpdate) {
                        // Navigate to Checkout Fragment
                        activity.getSupportFragmentManager().popBackStack();
                    }
                    else {
                        // Navigate to Checkout Fragment
                        activity.addPage(new Checkout());
                    }

                }
            }
        });


        return view=rootView;
    }



    //*********** Handle ShippingMethods returned from the Server ********//

    private void addShippingMethods(ShippingRateDetails shippingRateDetails) {
        
        tax = shippingRateDetails.getTax();

        
        if (shippingRateDetails.getShippingMethods().getFreeShipping() != null) {
            free_shipping_layout.setVisibility(View.VISIBLE);
            free_shipping_title.setText(shippingRateDetails.getShippingMethods().getFreeShipping().getName());

            freeShippingServicesList.addAll(shippingRateDetails.getShippingMethods().getFreeShipping().getServices());
            freeShippingServicesAdapter = new ShippingServicesAdapter(getContext(), freeShippingServicesList, this);

            free_shipping_services.setAdapter(freeShippingServicesAdapter);
            free_shipping_services.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        } else {
            free_shipping_layout.setVisibility(View.GONE);
        }


        if (shippingRateDetails.getShippingMethods().getLocalPickup() != null) {
            local_pickup_layout.setVisibility(View.VISIBLE);
            local_pickup_title.setText(shippingRateDetails.getShippingMethods().getLocalPickup().getName());

            localPickupServicesList.addAll(shippingRateDetails.getShippingMethods().getLocalPickup().getServices());
            localPickupServicesAdapter = new ShippingServicesAdapter(getContext(), localPickupServicesList, this);

            local_pickup_services.setAdapter(localPickupServicesAdapter);
            local_pickup_services.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        } else {
            local_pickup_layout.setVisibility(View.GONE);
        }


        if (shippingRateDetails.getShippingMethods().getFlateRate() != null) {
            flat_rate_layout.setVisibility(View.VISIBLE);
            flat_rate_title.setText(shippingRateDetails.getShippingMethods().getFlateRate().getName());

            flatRateServicesList.addAll(shippingRateDetails.getShippingMethods().getFlateRate().getServices());
            flatRateServicesAdapter = new ShippingServicesAdapter(getContext(), flatRateServicesList, this);

            flat_rate_services.setAdapter(flatRateServicesAdapter);
            flat_rate_services.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        } else {
            flat_rate_layout.setVisibility(View.GONE);
        }


        if (shippingRateDetails.getShippingMethods().getUpsShipping() != null) {

            if (shippingRateDetails.getShippingMethods().getUpsShipping().getSuccess().equalsIgnoreCase("1")) {
                ups_shipping_layout.setVisibility(View.VISIBLE);
                ups_shipping_title.setText(shippingRateDetails.getShippingMethods().getUpsShipping().getName());

                upsShippingServicesList.addAll(shippingRateDetails.getShippingMethods().getUpsShipping().getServices());
                upsShippingServicesAdapter = new ShippingServicesAdapter(getContext(), upsShippingServicesList, this);

                ups_shipping_services.setAdapter(upsShippingServicesAdapter);
                ups_shipping_services.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                ups_shipping_services.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

            }
            else {
                ups_shipping_layout.setVisibility(View.GONE);

                final Snackbar snackbar = Snackbar.make(rootView,
                                                shippingRateDetails.getShippingMethods().getUpsShipping().getMessage(),
                                                Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction(getString(R.string.ok), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            snackbar.dismiss();
                        }
                    });
                snackbar.show();
            }

        } else {
            ups_shipping_layout.setVisibility(View.GONE);
        }


        
        if (shippingService == null) {
            if (shippingRateDetails.getShippingMethods().getFreeShipping() != null
                    &&  shippingRateDetails.getShippingMethods().getFreeShipping().getServices().size() > 0) {

                shippingService = shippingRateDetails.getShippingMethods().getFreeShipping().getServices().get(0);

            }
            else if (shippingRateDetails.getShippingMethods().getLocalPickup() != null
                    &&  shippingRateDetails.getShippingMethods().getLocalPickup().getServices().size() > 0) {

                shippingService = shippingRateDetails.getShippingMethods().getLocalPickup().getServices().get(0);

            }
            else if (shippingRateDetails.getShippingMethods().getFlateRate() != null
                    &&  shippingRateDetails.getShippingMethods().getFlateRate().getServices().size() > 0) {

                shippingService = shippingRateDetails.getShippingMethods().getFlateRate().getServices().get(0);

            }
            else if (shippingRateDetails.getShippingMethods().getUpsShipping() != null
                    &&  shippingRateDetails.getShippingMethods().getUpsShipping().getSuccess().equalsIgnoreCase("1")
                    &&  shippingRateDetails.getShippingMethods().getUpsShipping().getServices().size() > 0) {

                shippingService = shippingRateDetails.getShippingMethods().getUpsShipping().getServices().get(0);
            }
        }

    }
    
    
    //*********** Request the Server to Calculate Tax and Shipping Rates ********//
    
    private void RequestShippingRates() {

        PostTaxAndShippingData postTaxAndShippingData = new PostTaxAndShippingData();
        
        double productWeight = 0;
        String productWeightUnit = "g";
        List<ProductDetails> productsList = new ArrayList<>();
        
        
        // Get ProductWeight, WeightUnit and ProductsList
        for (int i=0;  i<checkoutItemsList.size();  i++) {
            productWeight += Double.parseDouble(checkoutItemsList.get(i).getCustomersBasketProduct().getProductsWeight());
            productWeightUnit = checkoutItemsList.get(i).getCustomersBasketProduct().getProductsWeightUnit();
            productsList.add(checkoutItemsList.get(i).getCustomersBasketProduct());
        }
        
        postTaxAndShippingData.setTitle(shippingAddress.getFirstname());
        postTaxAndShippingData.setStreetAddress(shippingAddress.getStreet());
        postTaxAndShippingData.setCity(shippingAddress.getCity());
        postTaxAndShippingData.setState(shippingAddress.getState());
        postTaxAndShippingData.setZone(shippingAddress.getZoneName());
        postTaxAndShippingData.setTaxZoneId(shippingAddress.getZoneId());
        postTaxAndShippingData.setPostcode(shippingAddress.getPostcode());
        postTaxAndShippingData.setCountry(shippingAddress.getCountryName());
        postTaxAndShippingData.setCountryID(shippingAddress.getCountriesId());
        
        postTaxAndShippingData.setProductsWeight(String.valueOf(productWeight));
        postTaxAndShippingData.setProductsWeightUnit(productWeightUnit);
        
        postTaxAndShippingData.setLanguage_id(Constants.LANGUAGE_ID);
        
        postTaxAndShippingData.setProducts(productsList);
        
        
        // Proceed API Call to get Tax and Shipping Rates
        Call<ShippingRateData> call = APIClient.getInstance()
                .getShippingMethodsAndTax
                        (
                                postTaxAndShippingData
                        );
        
        call.enqueue(new Callback<ShippingRateData>() {
            @Override
            public void onResponse(Call<ShippingRateData> call, retrofit2.Response<ShippingRateData> response) {


                activity.hideDialog();
                // Check if the Response is successful
                if (response.isSuccessful()) {
                    
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {
                        
                        addShippingMethods(response.body().getData());

                    }
                    else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        Snackbar.make(rootView, response.body().getMessage(), Snackbar.LENGTH_LONG).show();
    
                    }
                    else {
                        // Unable to get Success status
                        Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(activity, response.message(), Toast.LENGTH_SHORT).show();
                }
                
            }
            
            @Override
            public void onFailure(Call<ShippingRateData> call, Throwable t) {
                activity.hideDialog();
                Toast.makeText(activity, "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }
    
    

    public ShippingService getSelectedShippingService() {
        return shippingService;
    }
    
    
    public void setSelectedShippingService(ShippingService shippingService) {
        this.shippingService = shippingService;
    }
    
    
    public RadioButton getLastChecked_RB() {
        return lastChecked_RB;
    }
    
    public void setLastChecked_RB(RadioButton lastChecked_RB) {
        this.lastChecked_RB = lastChecked_RB;
    }


    @Override
    public void initViews() {

    }

    @Override
    public String getTAG() {
        return TAG;
    }
}