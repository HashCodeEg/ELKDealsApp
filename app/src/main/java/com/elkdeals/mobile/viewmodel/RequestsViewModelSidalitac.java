package com.elkdeals.mobile.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.Constants;
import com.elkdeals.mobile.Utils.Utils;
import com.elkdeals.mobile.api.models.requests.AddRequestData;
import com.elkdeals.mobile.api.models.requests.OfferDetails;
import com.elkdeals.mobile.api.models.requests.RequestData;
import com.elkdeals.mobile.api.models.requests.SellerData;
import com.elkdeals.mobile.api.models.user_model.UserData;
import com.elkdeals.mobile.api.models.user_model.UserDetails;
import com.elkdeals.mobile.model.RepositorySidalitac;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class RequestsViewModelSidalitac extends BaseViewModel {

    String sortBy = "Newest";
    CompositeDisposable disposable;
    RepositorySidalitac storeModel;
    private MutableLiveData<SellerData> sellerData;
    private MutableLiveData<List<OfferDetails>> offersData;
    private MutableLiveData<RequestData> requests;
    private MutableLiveData<AddRequestData> addrequest;

    public RequestsViewModelSidalitac() {
        this.storeModel = new RepositorySidalitac();
        sellerData = new MutableLiveData<>();
        addrequest = new MutableLiveData<>();
        offersData = new MutableLiveData<>();
        requests = new MutableLiveData<>();
        disposable = new CompositeDisposable();
    }
    public void addSeller(String customer_id, String entry_firstname,
                          String entry_lastname, String entry_street_address,
                          String entry_postcode, String entry_city,
                          String entry_country_id, String entry_zone_id,
                          String entry_company, String latitude, String longitude){
        progress.setValue("loading...");
        disposable.add(storeModel.addSeller(customer_id,entry_firstname,entry_lastname,
                entry_street_address,entry_postcode,entry_city,entry_country_id
                ,entry_zone_id,entry_company,latitude,longitude).subscribe(sellerData1 ->{
                    if("1".equalsIgnoreCase(sellerData1.getSuccess())){
                        message.setValue(Utils.getStringRes(R.string.seller_registered_successfully));
                        UserDetails u=RepositorySidalitac.getUserInfo().getData().get(0);
                        u.setIsSeller(1);
                        UserData userData=new UserData();
                        ArrayList<UserDetails> arrayList=new ArrayList<>();
                        arrayList.add(u);
                        userData.setData(arrayList);
                        userData.setSuccess(1+"");
                        sellerData.setValue(sellerData1);
                    }
                    else {
                        message.setValue(sellerData1.getMessage());
                        sellerData.setValue(null);
                    }
                }
                ,throwable -> {
                    sellerData.setValue(null);
                    message.setValue(Utils.getStringRes(R.string.error)+" : "+throwable.getMessage());
                }));


    }
    public void updateSeller(String customer_id, String entry_firstname,
                          String entry_lastname, String entry_street_address,
                          String entry_postcode, String entry_city,
                          String entry_country_id, String entry_zone_id,
                          String entry_company, String latitude, String longitude){
        progress.setValue("loading...");
        disposable.add(storeModel.updateSeller(customer_id,entry_firstname,entry_lastname,
                entry_street_address,entry_postcode,entry_city,entry_country_id
                ,entry_zone_id,entry_company,latitude,longitude).subscribe(sellerData1 ->{
                    if(sellerData1.getSuccess().equalsIgnoreCase("1")){
                        message.setValue(Utils.getStringRes(R.string.seller_registered_successfully));
                        UserDetails u=RepositorySidalitac.getUserInfo().getData().get(0);
                        u.setIsSeller(1);
                        UserData userData=new UserData();
                        ArrayList<UserDetails> arrayList=new ArrayList<>();
                        arrayList.add(u);
                        userData.setData(arrayList);
                        userData.setSuccess(1+"");
                        Utils.saveSharedPrefrences(Constants.KEY_USER_STORE, userData.toJson());
                    }
                    else message.setValue(sellerData1.getMessage());
                }
                ,throwable -> {
                    message.setValue(Utils.getStringRes(R.string.error)+" : "+throwable.getMessage());
                }));


    }
    public void getRequests(){
        progress.setValue("loading...");
        disposable.add(storeModel.getRequests().subscribe(sellerData1 -> {
                    progress.setValue("");
                    requests.setValue(sellerData1);
        },throwable -> {
            sellerData.setValue(null);
            message.setValue(Utils.getStringRes(R.string.error)+" : "+throwable.getMessage());}
        ));
    }
    public void getRequests(String customerID){
        progress.setValue("loading...");
        disposable.add(storeModel.getRequests(customerID).subscribe(sellerData1 -> {
                    progress.setValue("");
                    requests.setValue(sellerData1);
        },throwable -> {
            sellerData.setValue(null);
            message.setValue(Utils.getStringRes(R.string.error)+" : "+throwable.getMessage());}
        ));
    }
    public void addRequest(String customerID,String category_id,
    String title,String description,String new_status,String used_status){
        progress.setValue("loading...");
        disposable.add(storeModel.addRequest(customerID,category_id,
                title,description,new_status,used_status).subscribe(sellerData1 -> {
                    message.setValue(Utils.getStringRes(R.string.request_sent_successfully));
                    addrequest.setValue(sellerData1);
        },throwable -> {
            sellerData.setValue(null);
            message.setValue(Utils.getStringRes(R.string.error)+" : "+throwable.getMessage());}
        ));
    }
    public void getOffers(String requestID){

        progress.setValue("loading...");
        disposable.add(storeModel.getOffers(requestID).subscribe(offerssData -> {

            progress.setValue("");
            if(offerssData==null||offerssData.getData()==null)
            {
                offersData.setValue(null);
                return;
            }
            offersData.setValue(offerssData.getData());
                },throwable -> {
                    offersData.setValue(null);
                    message.setValue(Utils.getStringRes(R.string.error)+" : "+throwable.getMessage());}
        ));
    }
    public void addOffer(
            String customerID, String request_id
            ,String price, String deliver_time
            ,String shipping_price,String product_status){
        progress.setValue("loading...");
        disposable.add(storeModel.addOffer(customerID,request_id,
                price,deliver_time,shipping_price,product_status).subscribe(sellerData1 -> {
                    if(sellerData1.getSuccess().equalsIgnoreCase("1"))
                    message.setValue(Utils.getStringRes(R.string.offer_sent_successfully));
                    else message.setValue(Utils.getStringRes(R.string.offer_not_sent_successfully));
        },throwable -> {
            sellerData.setValue(null);
            message.setValue(Utils.getStringRes(R.string.error)+" : "+throwable.getMessage());}
        ));
    }
    public void deleteSeller(String customer_id){
        progress.setValue("loading...");
        disposable.add(storeModel.deleteSeller(customer_id).subscribe(sellerData1 ->{
                    if(sellerData1.getSuccess().equalsIgnoreCase("1")){
                        message.setValue(Utils.getStringRes(R.string.unregisted_seller_successfully));
                    }
                }
                ,throwable -> {
                    message.setValue(Utils.getStringRes(R.string.error)+" : "+throwable.getMessage());
                }));


    }

    public MutableLiveData<SellerData> getSellerData() {
        return sellerData;
    }
    public MutableLiveData<RequestData> getRequestsMutuable() {
        return requests;
    }

    public MutableLiveData<AddRequestData> getAddrequest() {
        return addrequest;
    }


    public MutableLiveData<List<OfferDetails>> getOffersData() {
        return offersData;
    }

    public void setOffersData(MutableLiveData<List<OfferDetails>> offersData) {
        this.offersData = offersData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

}
