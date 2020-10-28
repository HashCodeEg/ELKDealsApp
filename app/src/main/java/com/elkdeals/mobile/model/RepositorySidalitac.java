package com.elkdeals.mobile.model;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.Constants;
import com.elkdeals.mobile.Utils.Utils;
import com.elkdeals.mobile.activities.BaseActivity;
import com.elkdeals.mobile.api.models.address_model.AddressData;
import com.elkdeals.mobile.api.models.banner_model.BannerData;
import com.elkdeals.mobile.api.models.category_model.CategoryData;
import com.elkdeals.mobile.api.models.filter_model.post_filters.PostFilterData;
import com.elkdeals.mobile.api.models.product_model.GetAllProducts;
import com.elkdeals.mobile.api.models.product_model.ProductData;
import com.elkdeals.mobile.api.models.requests.AddOfferData;
import com.elkdeals.mobile.api.models.requests.AddRequestData;
import com.elkdeals.mobile.api.models.requests.OffersData;
import com.elkdeals.mobile.api.models.requests.RequestData;
import com.elkdeals.mobile.api.models.requests.SellerData;
import com.elkdeals.mobile.api.models.user_model.UserData;
import com.elkdeals.mobile.api.network.APIClient;
import com.elkdeals.mobile.api.network.APIRequests;
import com.google.android.material.snackbar.Snackbar;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;

import static com.elkdeals.mobile.Utils.Constants.LANGUAGE_ID;

public class RepositorySidalitac  {

    String userID;
    static APIRequests services;
    static MutableLiveData<AddressData> AdressesLiveData ;
    public RepositorySidalitac(){
        services = APIClient.getInstance();
        try {
            userID = RepositorySidalitac.getUserInfo().getData().get(0).getCustomersId();
        }catch (Exception e){}
    }

    public static MutableLiveData<AddressData> getAdressesLiveData() {
        if(AdressesLiveData==null)
            AdressesLiveData=new MutableLiveData<>();
        return AdressesLiveData;
    }

    public static RepositorySidalitac getInstance() {
        return new RepositorySidalitac();
    }

    public static int getUserId() {
        try{
            UserData userdata = getUserInfo();
            if(userdata!=null)
                if(userdata.getData()!=null&&userdata.getData().size()>0)
                    return Integer.parseInt(userdata.getData().get(0).getCustomersId());
        }catch (Exception e){

        }
        return 0;
    }

    public Single<UserData> registerUser(String customers_firstname, String customers_lastname,
                                         String customers_email_address, String customers_password,
                                         String customers_telephone, String customers_picture){
        return services.processRegistration(customers_firstname,customers_lastname,customers_email_address
                ,customers_password,customers_telephone,customers_picture).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Single<CategoryData> getStoreCategories(){
        return services.getAllCategories(LANGUAGE_ID).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Single<BannerData> getBanners(){
        return services.getBanners().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<UserData> processLogin(String mobile){
        return services.processLogin(mobile).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Single<RequestData> getRequests(String customerID){
        return services.getRequests(customerID).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<AddRequestData> addRequest(String customerID, String category_id, String title, String description, String new_status, String used_status) {
        return services.addRequest(customerID,category_id,
                title,description,new_status,used_status).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Single<AddOfferData> addOffer(
            String customerID, String request_id
            ,String price, String deliver_time
            ,String shipping_price,String product_status
    ) {
        return services.addOffer(customerID,request_id,
                price,deliver_time,shipping_price,product_status).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Single<RequestData> getRequests(){
        return services.getRequests().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    //*********** Request Products of given Category from the Server based on PageNo. ********//
    public Single<ProductData> getCategoryProducts(int categoryID, int pageNumber, String sortBy, PostFilterData filters) {

        GetAllProducts getAllProducts = new GetAllProducts();
        getAllProducts.setPageNumber(pageNumber);
        getAllProducts.setLanguageId(LANGUAGE_ID);
        getAllProducts.setCustomersId(userID);
        if(filters!=null) {
            getAllProducts.setFilters(filters.getFilters());
            getAllProducts.setPrice(filters.getPrice());
        }
        getAllProducts.setCategoriesId(String.valueOf(categoryID));
        getAllProducts.setType(sortBy);
        return services.getAllProducts(getAllProducts).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<OffersData> getOffers(String requestID){

        return services.getOffers(requestID).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public static UserData getUserInfo() {
        return UserData.fromJson(Utils.getSharedPrefrences(Constants.KEY_USER_STORE,""));
    }

    public Single<SellerData> addSeller(String customer_id, String entry_firstname,
                                        String entry_lastname, String entry_street_address,
                                        String entry_postcode, String entry_city,
                                        String entry_country_id, String entry_zone_id,
                                        String entry_company, String latitude, String longitude){
        return services.addSeller(customer_id,entry_firstname,entry_lastname,
                entry_street_address,entry_postcode,entry_city,entry_country_id
                ,entry_zone_id,entry_company,latitude,longitude).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }    public Single<SellerData> updateSeller(String customer_id, String entry_firstname,
                                                String entry_lastname, String entry_street_address,
                                                String entry_postcode, String entry_city,
                                                String entry_country_id, String entry_zone_id,
                                                String entry_company, String latitude, String longitude){
        return services.updateSeller(customer_id,entry_firstname,entry_lastname,
                entry_street_address,entry_postcode,entry_city,entry_country_id
                ,entry_zone_id,entry_company,latitude,longitude).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Single<SellerData> deleteSeller(String customer_id) {
        return services.deleteSeller(customer_id);
    }
    //*********** Request to Delete the given User's Address ********//

    public static void DeleteAddress(final String addressID, BaseActivity activity) {

        String customerID=getUserInfo().getData().get(0).getCustomersId();
        activity.showProgressBar("loading...");
        Call<AddressData> call = APIClient.getInstance()
                .deleteUserAddress
                        (
                                customerID,
                                addressID
                        );

        call.enqueue(new Callback<AddressData>() {
            @Override
            public void onResponse(Call<AddressData> call, retrofit2.Response<AddressData> response) {

                if (response.isSuccessful()) {

                    if (response.body().getSuccess().equalsIgnoreCase("1")) {

                        // Address has been Deleted. Show the Message to the User
                        Snackbar.make(activity.getWindow().getDecorView(), response.body().getMessage(), Snackbar.LENGTH_SHORT).show();

                    }
                    else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        Snackbar.make(activity.getWindow().getDecorView(), response.body().getMessage(), Snackbar.LENGTH_LONG).show();

                    }
                    else {
                        // Unable to get Success status
                        Snackbar.make(activity.getWindow().getDecorView(), Utils.getStringRes(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(activity, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddressData> call, Throwable t) {
                Toast.makeText(activity, "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }


    //*********** Request User's all Addresses from the Server ********//

    public static void RequestAllAddresses(BaseActivity activity) {
        final Disposable[] d = new Disposable[1];
        if(AdressesLiveData==null)
            AdressesLiveData=new MutableLiveData<>();
        APIClient.getInstance()
                .getAllAddress
                        (
                                getUserInfo().getData().get(0).getCustomersId()
                        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<AddressData>() {
            @Override
            public void onSubscribe(Disposable dd) {
                activity.showProgressBar("جار التحميل...");
                d[0] =dd;
            }

            @Override
            public void onSuccess(AddressData addressData) {
                AdressesLiveData.setValue(addressData);
                activity.hideDialog();
                d[0].dispose();
            }

            @Override
            public void onError(Throwable e) {
                activity.hideDialog();
                AdressesLiveData.setValue(null);
                activity.showToast( "NetworkCallFailure : "+e.getMessage());
                d[0].dispose();
            }
        });
    }


    //*********** Request for Changing the Address to User's Default Address ********//

    public static void MakeAddressDefault(final String addressID, BaseActivity activity) {
        String customerID=getUserInfo().getData().get(0).getCustomersId();
        activity.showProgressBar("loading...");
        Call<AddressData> call = APIClient.getInstance()
                .updateDefaultAddress
                        (
                                customerID,
                                addressID
                        );

        call.enqueue(new Callback<AddressData>() {
            @Override
            public void onResponse(Call<AddressData> call, retrofit2.Response<AddressData> response) {

                activity.hideDialog();

                // Check if the Response is successful
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {

                    }
                    else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        Snackbar.make(activity.getWindow().getDecorView(), response.body().getMessage(), Snackbar.LENGTH_LONG).show();

                    }
                    else {

                        // Unable to get Success status
                        Snackbar.make(activity.getWindow().getDecorView(), Utils.getStringRes(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }
                }
                else {
                }
            }

            @Override
            public void onFailure(Call<AddressData> call, Throwable t) {
                activity.hideDialog();
                activity.showToast("NetworkCallFailure");
            }
        });

    }

}
