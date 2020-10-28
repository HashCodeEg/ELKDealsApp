package com.elkdeals.mobile;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Build;

import androidx.multidex.MultiDex;

import com.crashlytics.android.Crashlytics;
import com.elkdeals.mobile.Utils.Constants;
import com.elkdeals.mobile.Utils.LocaleHelper;
import com.elkdeals.mobile.api.API;
import com.elkdeals.mobile.api.models.address_model.AddressDetails;
import com.elkdeals.mobile.api.models.shipping_model.ShippingService;
import com.elkdeals.mobile.database.DB_Handler;
import com.elkdeals.mobile.database.DB_Manager;
import com.elkdeals.mobile.recievers.ConnectivityReceiver;
import com.google.firebase.FirebaseApp;
import com.islam.slider.SliderTypes.BaseSliderView;

import io.fabric.sdk.android.Fabric;

public class App extends Application {
    static App mApp;

    AddressDetails address;
    private ShippingService shippingService;
    private String tax;

    public App() {
        mApp = this;
    }

    public static synchronized App getInstance() {
        return mApp;
    }

    public AddressDetails getAddress() {
        return address;
    }

    public void setAddress(AddressDetails address) {
        this.address = address;
    }

    @Override
    public void onCreate() {
  /*      DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);
*/
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        FirebaseApp.initializeApp(this);
        //AndroidNetworking.initialize(getApplicationContext());
        //AuctionsDatabase.setInstance(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(ConnectivityReceiver.getInstance(), new IntentFilter(Constants.InternetChangeFilter));
        }
        BaseSliderView.setBaseUrl(API.IMAGE_BASE_URL);
        // initialize DB_Handler and DB_Manager
        DB_Handler db_handler = new DB_Handler();
        DB_Manager.initializeInstance(db_handler);

// Initialize Places.
        //Places.initialize(getApplicationContext(), Constants.apiKey);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if (ConnectivityReceiver.getInstance() != null)
                    unregisterReceiver(ConnectivityReceiver.getInstance());
            }
        } catch (Exception ignored) {

        }

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
        MultiDex.install(this);

    }

    public ShippingService getShippingService() {
        return shippingService;
    }

    public void setShippingService(ShippingService shippingService) {
        this.shippingService=shippingService;
    }

    public void setTax(String tax) {
        this.tax=tax;

    }

    public String getTax() {
        return tax;
    }
}
