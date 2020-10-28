package com.elkdeals.mobile.ui.requests;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.TimeType;
import com.elkdeals.mobile.Utils.TimerCounter;
import com.elkdeals.mobile.Utils.TimerStatus;
import com.elkdeals.mobile.Utils.Utils;
import com.elkdeals.mobile.adapters.CustomExpandableListAdapter;
import com.elkdeals.mobile.adapters.RequestsCategoriesAdapter;
import com.elkdeals.mobile.api.models.address_model.AddressDetails;
import com.elkdeals.mobile.api.models.category_model.CategoryDetails;
import com.elkdeals.mobile.api.models.requests.SellerData;
import com.elkdeals.mobile.ui.BaseFragment;
import com.elkdeals.mobile.ui.account.Add_Address;
import com.elkdeals.mobile.interfaces.OnItemClickListener;
import com.elkdeals.mobile.model.RepositorySidalitac;
import com.elkdeals.mobile.viewmodel.RequestsViewModelSidalitac;
import com.elkdeals.mobile.viewmodel.StoreViewModelSidalitac;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.content.Context.LOCATION_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestsNew extends BaseFragment implements LocationListener, TimerCounter.OnTimerTick {


    boolean isSeller = false;
    public static final String TAG = "Requests Categories";
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.expandableListView)
    ExpandableListView expandableListView;
    @BindView(R.id.sell_with_us)
    TextView sell_with_us;
    LinearLayoutManager manager;
    RequestsCategoriesAdapter adapter;
    CustomExpandableListAdapter expandableAdapter;
    @BindView(R.id.divider)
    View divider;
    @BindView(R.id.confirm)
    TextView confirm;
    @BindView(R.id.reload)
    TextView reload;
    @BindView(R.id.container)
    View container;
    StoreViewModelSidalitac storeViewModel;
    RequestsViewModelSidalitac requestsViewModelSidalitac;

    TimerCounter timerCounter;
    Location location;
    LocationManager locationManager;
    public RequestsNew() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return view = inflater.inflate(R.layout.fragment_requests, container, false);
    }

    @Override
    public void initViews() {
//        ArrayList<Category> subCategories = new ArrayList<>();
//        ArrayList<Category> categories = new ArrayList<>()
        if(timerCounter==null||timerCounter.getTimerStatus()== TimerStatus.notWorking)
            timerCounter=new TimerCounter(this, TimeType.Second);
        ArrayList<CategoryDetails> parentCats = new ArrayList<>();
        ArrayList<CategoryDetails> subCats = new ArrayList<>();
        storeViewModel= ViewModelProviders.of(this).get(StoreViewModelSidalitac.class);
        requestsViewModelSidalitac= ViewModelProviders.of(this).get(RequestsViewModelSidalitac.class);
        locationManager=(LocationManager) activity.getSystemService(LOCATION_SERVICE);
        if(RepositorySidalitac.getUserInfo().getData().get(0).getIsSeller()==0){
            sell_with_us.setText(R.string.sell_with_us);
        }else sell_with_us.setVisibility(View.GONE);
        storeViewModel.getProgress().observe(this, s -> {
            if (!TextUtils.isEmpty(s))
                activity.showProgressBar(s);
            else activity.hideDialog();
        });
        storeViewModel.getMessage().observe(this, s -> activity.showMessageDialog(s));
        requestsViewModelSidalitac.getProgress().observe(this, s -> {
            if (!TextUtils.isEmpty(s))
                activity.showProgressBar(s);
            else activity.hideDialog();
        });
        requestsViewModelSidalitac.getMessage().observe(this, s -> activity.showMessageDialog(s));
        //////////////////////////////////////////////////////
        storeViewModel.getCategoriesMutuable().observe(this,
                categoriesModels -> {
            if(categoriesModels==null){
                //set error
                reload.setText(R.string.error);
                container.setVisibility(View.GONE);
                reload.setVisibility(View.VISIBLE);
                return;
            }
            if(categoriesModels.getData()==null||categoriesModels.getData().size()==0)
            {
                //set no data
                reload.setText(R.string.no_categories_found);
                container.setVisibility(View.GONE);
                reload.setVisibility(View.VISIBLE);
                return;
            }
                    reload.setVisibility(View.GONE);
                    container.setVisibility(View.VISIBLE);

                    for (int i = 0; i < categoriesModels.getData().size(); i++) {
                        if (categoriesModels.getData().get(i).getParentId().equalsIgnoreCase("0"))
                            parentCats.add(categoriesModels.getData().get(i));
                        else subCats.add(categoriesModels.getData().get(i));
                    }
                    for (int i = 0; i < parentCats.size(); i++) {
                        for (int j = 0;j < subCats.size(); j++) {
                            if(parentCats.get(i).getId().equalsIgnoreCase(subCats.get(j).getParentId())){
                                parentCats.get(i).addSubCategory(subCats.get(j));
                                subCats.remove(j);
                                j=j-1;
                            }
                        }
                    }
                    for (int i = 0; i < parentCats.size(); i++)
                        parentCats.set(i, CategoryDetails.createExpandable(parentCats.get(i)));
                    expandableAdapter=new CustomExpandableListAdapter(activity,parentCats);
                    expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                        @Override
                        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                            activity.addPage(NewRequest.createInstance((CategoryDetails) expandableAdapter.getChild(
                                    groupPosition,childPosition
                            )));
                            return true;
                        }
                    });
                    expandableAdapter.setOnCheckedItemsCountChanged(new OnItemClickListener() {
                        @Override
                        public void OnItemClick(Object data) {
                            super.OnItemClick(data);
                            int count = (int) data;
                            if (count == 0) {
                                sell_with_us.setText(R.string.cancel);
                            } else if (count == adapter.getCategoriesCount())
                                sell_with_us.setText(R.string.select_none);
                            else sell_with_us.setText(R.string.select_all);
                        }
                    });
                    adapter = new RequestsCategoriesAdapter(parentCats, true);
                    adapter.setOnCategoryClickListener(new OnItemClickListener() {
                        @Override
                        public void OnItemClick(Object data) {
                            super.OnItemClick(data);
                            activity.addPage(NewRequest.createInstance((CategoryDetails) data));
                        }
                    });
                    adapter.setOnCheckedItemsCountChanged(new OnItemClickListener() {
                        @Override
                        public void OnItemClick(Object data) {
                            super.OnItemClick(data);
                            int count = (int) data;
                            if (count == 0) {
                                sell_with_us.setText(R.string.cancel);
                            } else if (count == adapter.getCategoriesCount())
                                sell_with_us.setText(R.string.select_none);
                            else sell_with_us.setText(R.string.select_all);
                        }
                    });
                    expandableListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Log.e("eee",position+" ");
                        }
                    });
                    expandableListView.setAdapter(expandableAdapter);
                    recyclerView.setAdapter(adapter);
                });
        storeViewModel.getProgress().observe(this, s -> {
            if (!TextUtils.isEmpty(s))
                activity.showProgressBar(s);
            else activity.hideDialog();
        });
        storeViewModel.getMessage().observe(this, s -> activity.showMessageDialog(s));
        requestsViewModelSidalitac.getProgress().observe(this, s -> {
            if (!TextUtils.isEmpty(s))
                activity.showProgressBar(s);
            else activity.hideDialog();
        });
        requestsViewModelSidalitac.getMessage().observe(this, s -> activity.showMessageDialog(s));
        storeViewModel.getCategories();
        requestsViewModelSidalitac.getSellerData().observe(this, new Observer<SellerData>() {
            @Override
            public void onChanged(SellerData sellerData) {
                if(sellerData!=null)
                    setUpSell();
                else{

                    isSeller = false;
                    sell_with_us.setText(R.string.sell_with_us);
                    adapter.setShowCheckBox(false);
                    divider.setVisibility(View.GONE);
                    confirm.setVisibility(View.GONE);
                    adapter.setExpandaple(true);
                    confirm.setText(R.string.next);
                }
            }
        });
        /////////////////////////////////////////
        manager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(manager);
    }

    private void setUpSell() {

    }
    @OnClick(R.id.myrequests)
    public void getMyRequests(){
        activity.addPage(MyRequests.createInstance(RepositorySidalitac.getUserInfo().getData().get(0).getCustomersId()));
        return;
    }
    @OnClick(R.id.reload)
    public void reload() {
        initViews();
    }
    @OnClick(R.id.sell_with_us)
    public void joinUs() {
        if(sell_with_us.getText().toString().equalsIgnoreCase(Utils.getStringRes(R.string.see_requests))){
            activity.addPage(new CustomersRequests());
            return;
        }
        if (sell_with_us.getText().toString().equalsIgnoreCase(Utils.getStringRes(R.string.select_none))) {
            adapter.selectNone();
            return;
        } else if (sell_with_us.getText().toString().equalsIgnoreCase(Utils.getStringRes(R.string.select_all))) {
            adapter.selectAll();
            return;
        }
        if (isSeller) {
            isSeller = false;
            sell_with_us.setText(R.string.sell_with_us);
            adapter.setShowCheckBox(false);
            divider.setVisibility(View.GONE);
            confirm.setVisibility(View.GONE);
            adapter.setExpandaple(true);
            confirm.setText(R.string.next);
        } else {
            isSeller = true;
            sell_with_us.setText(R.string.select_all);
            confirm.setText(R.string.next);
            adapter.setShowCheckBox(true);
            divider.setVisibility(View.VISIBLE);
            confirm.setVisibility(View.VISIBLE);
            adapter.setExpandaple(false);
        }
    }

    @Override
    public void onResume_() {
        super.onResume_();
        if (Utils.getAddressDetails() != null) {
            confirm.setText(R.string.loading);
            timerCounter.startTimer();
            addAddress();
        }
    }

    @OnClick(R.id.confirm)
    public void addAddress() {
        if (confirm.getText().toString().equals(Utils.getStringRes(R.string.confirm_address))|| Utils.getAddressDetails()!=null) {
            pickLocation();
            return;
        }
        Add_Address fragment = new Add_Address();
        Bundle args = new Bundle();
        args.putBoolean("isUpdate", false);
        args.putBoolean("addAddressToUtils", true);
        fragment.setArguments(args);
        activity.addPage(fragment);
    }


    public void pickLocation() {
        if (adapter.getSelectedItems() == 0) {
            activity.showToast(R.string.selct_category_first);
            return;
        }
        if (!checkLocationPermission()) {
            return;
        }
        if (!checkIfLocationEnable()) {
            return;
        }
        Location location = getLastKnownLocation();
    }

    public boolean checkIfLocationEnable() {

        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (gps_enabled && network_enabled) {

            return true;
        }
        activity.showMessageDialog("please activate location", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickLocation();
            }
        });

        return false;
    }

    private boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                activity.showMessageDialog("Location permission", "please allow app to use your location", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityCompat.requestPermissions(activity,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                MY_PERMISSIONS_REQUEST_LOCATION);
                    }
                });


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @SuppressLint("MissingPermission")
    private Location getLastKnownLocation() {
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 400, 1, this);
        for (String provider : providers) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
            Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        if (bestLocation == null)
            onLocationChanged(bestLocation);
        return bestLocation;
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(activity,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        getLastKnownLocation();
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }


    @Override
    public void onLocationChanged(Location location) {
        if (location == null)
            return;
        this.location=location;
        if (activity.messageDialog == null || activity.messageDialog.isDismissed)
            activity.showToast("lat : " + location.getLatitude() + "/r/nlong : " + location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }


    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(this);
        Utils.setAddressDetails(null);
    }

    @Override
    public void onTimerTick(String time) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(location==null)
                    return;
                AddressDetails addressDetails= Utils.getAddressDetails();
                locationManager.removeUpdates(RequestsNew.this);
                requestsViewModelSidalitac.addSeller(RepositorySidalitac.getUserInfo().getData().get(0).getCustomersId(),
                        addressDetails.getFirstname(),addressDetails.getLastname(),addressDetails.getStreet(),addressDetails.getPostcode(),addressDetails.getCity()
                        ,addressDetails.getCountriesId()+"",addressDetails.getZoneId()+"",addressDetails.getCompany(),""+location.getLatitude(),""+location.getLongitude());
                timerCounter.cancel();
            }
        });
    }
}
