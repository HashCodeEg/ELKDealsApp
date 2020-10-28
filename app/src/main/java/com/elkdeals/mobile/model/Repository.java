package com.elkdeals.mobile.model;

import android.text.TextUtils;

import com.elkdeals.mobile.Utils.Constants;
import com.elkdeals.mobile.Utils.Utils;
import com.elkdeals.mobile.api.API;
import com.elkdeals.mobile.api.Services;
import com.elkdeals.mobile.api.models.AucDescriptionModel;
import com.elkdeals.mobile.api.models.AucDetailsModel;
import com.elkdeals.mobile.api.models.CategoriesModel;
import com.elkdeals.mobile.api.models.DeleteInvResponse;
import com.elkdeals.mobile.api.models.FAQModel;
import com.elkdeals.mobile.api.models.FinishedAuctionsModel;
import com.elkdeals.mobile.api.models.HistoryM;
import com.elkdeals.mobile.api.models.HotLines;
import com.elkdeals.mobile.api.models.InvoiceDetail;
import com.elkdeals.mobile.api.models.InvoicesModel;
import com.elkdeals.mobile.api.models.MobileModel;
import com.elkdeals.mobile.api.models.NotificationDetModel;
import com.elkdeals.mobile.api.models.NotificationModel;
import com.elkdeals.mobile.api.models.ProfileImageModel;
import com.elkdeals.mobile.api.models.RegResponseModel;
import com.elkdeals.mobile.api.models.StoreCartModel;
import com.elkdeals.mobile.api.models.StoreHomeResponse;
import com.elkdeals.mobile.api.models.StoreProductModel;
import com.elkdeals.mobile.api.models.TimeLineModel;
import com.elkdeals.mobile.api.models.TrueMsg;
import com.elkdeals.mobile.api.models.UserModel;
import com.elkdeals.mobile.api.models.WalletM;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Repository {
    private Services services;
    public Repository() {
        services = API.getAPIS();
    }

    public static com.elkdeals.mobile.api.models.UserModel getUserInfo() {
        UserModel user= UserModel.fromJson(Utils.getSharedPrefrences(Constants.KEY_USER,null));
        if (user == null)
            return user;
        String image= Utils.getSharedPrefrences(Constants.KEY_USER_IMAGE,"");
        if(!TextUtils.isEmpty(image)){
            user.setImage(image);
        }
        if (TextUtils.isEmpty(user.getId())||"-1".equalsIgnoreCase(user.getId()))
            return null;
        return user;
    }

    public Single<List<FAQModel>> getFAQList() {
        return services.getFQAList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<HotLines>> getAllHotLiens() {
        return services.getAllHotLiens().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<WalletM> getWallet() {
        return services.getWallet(UserModel.fromJson(Utils.getSharedPrefrences(Constants.KEY_USER,"")).getId()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<HistoryM>> getHistory() {
        return services.getHistory(UserModel.fromJson(Utils.getSharedPrefrences(Constants.KEY_USER,"")).getId()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<TrueMsg> sendTransfer(String _amount, String _wnumber) {
        return services.sendTransfer(_amount, _wnumber, _wnumber).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    ////////////////////////////////////////////////////////
    //User
    //Start
    ////////////////////////////////////////////////////////

    Single<MobileModel> getOtpCode(String mobile, String imie) {
        return services.getSMSCodeRx(mobile, imie).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    Single<RegResponseModel> register(String mobile, String natID, String name, String email, String cityId, String address, String imei, String token,String logout) {
        return services.finishRegistrationRx(mobile, natID, name, email, cityId, address, imei, token, logout).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    Single<RegResponseModel> register(String mobile, String natID, String name, String email, String cityId, String address, String imei, String token) {
        return services.finishRegistrationRx(mobile, natID, name, email, cityId, address, imei, token, "").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<RegResponseModel> askIfMobileExist(String mobile) {
        return services.askIfMobileExistsRx(mobile).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<InvoicesModel>> getUserInvoices() {
        return services.getUserInvoices(UserModel.fromJson(Utils.getSharedPrefrences(Constants.KEY_USER,"")).getId()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Single<List<InvoiceDetail>> getUserInvoiceDetails(String invID) {
        return services.getInvoiceDetails(invID).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Single<DeleteInvResponse> deleteInv(String invID) {
        return services.deleteInvoice(invID).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Single<TrueMsg> deleteInv(String invID, String invAuctionID) {
        return services.deleteInvItem(invID,invAuctionID).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<UserModel> getUserData() {
        return services.getUserDataRx(getUserInfo().getId()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Single<ProfileImageModel> getProfileImage() {
        return services.getProfilePicRx(getUserInfo().getId()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Single<List<NotificationModel>> getUserNotifications() {
        return services.getUserNotifications(getUserInfo().getId()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Single<NotificationDetModel> getUserNotificationsDetails(String notificationID) {
        return services.getUserNotificationsDetails(notificationID).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<MobileModel> sendPic(String image, String fname, String type, String invid) {
        return services.sendPic(fname,UserModel.fromJson(Utils.getSharedPrefrences(Constants.KEY_USER,"")).getId(),type,invid,image).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    ////////////////////////////////////////////////////////
    //User
    //End
    ////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////
    //Store
    //Start
    ////////////////////////////////////////////////////////

    Single<TrueMsg> changeQuantity(String proid, String quantity) {
        return services.changeQuantity(UserModel.fromJson(Utils.getSharedPrefrences(Constants.KEY_USER,"")).getId(), proid, quantity).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    Single<List<StoreCartModel>> getCartItems() {
        return services.getCartItems(UserModel.fromJson(Utils.getSharedPrefrences(Constants.KEY_USER,"")).getId()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    Single<List<StoreProductModel>> getStoreProducts(String categoryId) {
        return services.getStoreProductsRx(categoryId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    Single<TrueMsg> addToCart(String proid, String quantity) {
        return services.addToCart(UserModel.fromJson(Utils.getSharedPrefrences(Constants.KEY_USER,"")).getId(), proid, quantity).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    Single<StoreHomeResponse> getHome() {
        return services.getHome().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    ////////////////////////////////////////////////////////
    //Store
    //End
    ////////////////////////////////////////////////////////


    ////////////////////////////////////////////////////////
    //Autions
    //Start
    ////////////////////////////////////////////////////////

    Single<List<CategoriesModel>> getCategoriesList() {
        return services.getAuctionsX().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    Single<List<CategoriesModel>> getAuctionsCategories() {
        return services.getAuctionsX().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    Single<List<TimeLineModel>> getAuctions(String categoryId) {
        return services.getTimeLineAllRx(UserModel.fromJson(Utils.getSharedPrefrences(Constants.KEY_USER,"")).getId(), categoryId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<AucDetailsModel>> getAuctionDetails(String auctionId) {
        return services.getAuctionDetails(auctionId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<AucDescriptionModel>> getAuctionItemDetails(String auctionId) {
        return services.getAuctionItemDetails(auctionId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<FinishedAuctionsModel>> getClosedAuctions(String categoryId, String type) {
        return services.getFinishedAuctions(categoryId, type).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    ////////////////////////////////////////////////////////
    //Autions
    //End
    ////////////////////////////////////////////////////////
}
