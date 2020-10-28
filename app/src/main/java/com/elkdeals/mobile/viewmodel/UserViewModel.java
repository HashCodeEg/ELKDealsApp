package com.elkdeals.mobile.viewmodel;

import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.Constants;
import com.elkdeals.mobile.Utils.Utils;
import com.elkdeals.mobile.api.models.DeleteInvResponse;
import com.elkdeals.mobile.api.models.HistoryM;
import com.elkdeals.mobile.api.models.InvoiceDetail;
import com.elkdeals.mobile.api.models.InvoicesModel;
import com.elkdeals.mobile.api.models.NotificationDetModel;
import com.elkdeals.mobile.api.models.NotificationModel;
import com.elkdeals.mobile.api.models.ProfileImageModel;
import com.elkdeals.mobile.api.models.TrueMsg;
import com.elkdeals.mobile.api.models.WalletM;
import com.elkdeals.mobile.api.models.user_model.UserData;
import com.elkdeals.mobile.fireBase.MyFirebaseMessagingService;
import com.elkdeals.mobile.model.RepositorySidalitac;
import com.elkdeals.mobile.model.UserModel;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.elkdeals.mobile.ui.HomeActivity.shouldUpdateImei;

public class UserViewModel extends BaseViewModel {
    UserModel model;
    CompositeDisposable disposable;
    MutableLiveData<com.elkdeals.mobile.api.models.UserModel> userModel;
    MutableLiveData<com.elkdeals.mobile.api.models.ProfileImageModel> profileImage;
    MutableLiveData<UserData> userStoreModel;
    MutableLiveData<TrueMsg> transferModel;
    RepositorySidalitac repositorySidalitac;
    private MutableLiveData<List<InvoicesModel>> userInvoices;
    private MutableLiveData<List<NotificationModel>> userNotifications;
    private MutableLiveData<NotificationDetModel> userNotificationDetails;
    private MutableLiveData<List<InvoiceDetail>> userInvoiceDetails;
    private MutableLiveData<WalletM> WalletMMutableLiveData;
    private MutableLiveData<List<HistoryM>> WalletMListMutableLiveData;
    private MutableLiveData<DeleteInvResponse> deleteInvResponse;

    public UserViewModel() {
        userInvoices = new MutableLiveData<>();
        model = new UserModel();
        userModel = new MutableLiveData<>();
        profileImage = new MutableLiveData<>();
        transferModel = new MutableLiveData<>();
        WalletMMutableLiveData = new MutableLiveData<WalletM>();
        userStoreModel = new MutableLiveData<>();
        userNotifications = new MutableLiveData<>();
        userNotificationDetails = new MutableLiveData<NotificationDetModel>();
        userInvoiceDetails = new MutableLiveData<>();
        WalletMListMutableLiveData = new MutableLiveData<>();
        deleteInvResponse = new MutableLiveData<DeleteInvResponse>();
        disposable = new CompositeDisposable();
        repositorySidalitac = new RepositorySidalitac();
    }


    public void getUserInVoices() {
        progress.setValue(Utils.getStringRes(R.string.loading));
        disposable.add(model.getUserInvoices().subscribe(invoicesModels -> {
            progress.setValue("");
            userInvoices.setValue(invoicesModels);
        }, throwable ->
        {message.setValue(Utils.getStringRes(R.string.error_loadind_waallet)+
                throwable.getMessage());}));
    }

    public void getWallet() {
        progress.setValue(Utils.getStringRes(R.string.loading));
        disposable.add(model.getWallet().subscribe(walletM -> {
            progress.setValue("");
            WalletMMutableLiveData.setValue(walletM);
        }, throwable ->
        {message.setValue(Utils.getStringRes(R.string.error_loadind_wallet)+
                    throwable.getMessage());}));
    }

    public void getWalletList() {
        progress.setValue(Utils.getStringRes(R.string.loading));
        disposable.add(model.getHistory().subscribe(walletMList -> {
            progress.setValue("");
            WalletMListMutableLiveData.setValue(walletMList);
        }, throwable ->
        {message.setValue(Utils.getStringRes(R.string.error_loadind_waallet)+
                    throwable.getMessage());}));
    }

    public void getUserNotifications(boolean showMessage) {
        if (showMessage)
            progress.setValue(Utils.getStringRes(R.string.loading));
        disposable.add(model.getUserNotifications().subscribe(notificationModelList -> {
            if (showMessage)
                progress.setValue("");
            userNotifications.setValue(notificationModelList);
        }, throwable -> {
            if (showMessage)
                message.setValue(Utils.getStringRes(R.string.error_loading_notification));
        }));
    }

    public void getUserNotificationDetails(String notificationID) {
            progress.setValue(Utils.getStringRes(R.string.loading));
        disposable.add(model.getUserNotificationsDetails(notificationID).subscribe(notificationDetModel -> {
                progress.setValue("");
                userNotificationDetails.setValue(notificationDetModel);
        }, throwable -> {
            userNotificationDetails.setValue(null);
                message.setValue(Utils.getStringRes(R.string.error_loading_notification));
        }));
    }

    public void deleteUserInVoices(int id) {
        progress.setValue(Utils.getStringRes(R.string.loading));
        disposable.add(model.deleteInv(String.valueOf(id)).subscribe(deleteInvResponse -> {
            progress.setValue("");
            if (deleteInvResponse.getStatus())
                message.setValue("تم الغاء الفاتورة.");
            else message.setValue("Error Loading data : " + deleteInvResponse.getMsg());
            this.deleteInvResponse.setValue(deleteInvResponse);
        }, throwable ->
        {message.setValue(Utils.getStringRes(R.string.error_loadind_waallet)+
                throwable.getMessage());}));
    }

    public void deleteUserInVoices(int id, int invAuctionID) {
        progress.setValue(Utils.getStringRes(R.string.loading));
        disposable.add(model.deleteInv(String.valueOf(id), String.valueOf(invAuctionID)).subscribe(deleteInvResponse -> {
            progress.setValue("");
            message.setValue("تم حذف العنصر من الفاتورة.");
        }, throwable -> message.setValue("Error Loading data : " + throwable.getMessage())));
    }

    public void getUserInvoices(String invID) {
        progress.setValue(Utils.getStringRes(R.string.loading));
        disposable.add(model.getUserInvoices(invID).subscribe(invoiceDetails -> {
            progress.setValue("");
            userInvoiceDetails.setValue(invoiceDetails);
        }, throwable -> message.setValue("Error Loading data")));
    }

    public void getUserProfileImage() {
         disposable.add(model.getProfileImage().subscribe(
                userModel1 -> {
                    progress.setValue("");
                    profileImage.setValue(userModel1);
                    if (!TextUtils.isEmpty(userModel1.getImage()))
                        Utils.saveSharedPrefrences(Constants.KEY_USER_IMAGE, userModel1.getImage());
                }, throwable -> {}/*message.setValue(Utils.getStringRes(R.string.error_loading_profile_image))*/));
    }

    public void getUserData(boolean forcLogout) {
        progress.setValue(Utils.getStringRes(R.string.loading));
        disposable.add(model.getUserData().subscribe(
                userModel1 -> {
                    progress.setValue("");
                    if (forcLogout&&shouldUpdateImei(userModel1.getImei()))
                        message.setValue(Constants.FORCE_LOGOUT);
                    else userModel.setValue(userModel1.save());
                }, throwable -> message.setValue("Error Loading data")));
    }

    public void registerStoreAccount(String name, String email, String password, String phone) {
        toast.setValue(Utils.getStringRes(R.string.registering_user_store_account));
        RepositorySidalitac.getInstance().registerUser(name, "", email, Utils.getMd5Hash(password), phone, "")
                .subscribe(new SingleObserver<UserData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(UserData userData) {
                        if ("0".equalsIgnoreCase(userData.getSuccess())) {
                            toast.setValue(Utils.getStringRes(R.string.error_creating_store_account) + " : " + userData.getMessage());
                            return;
                        }
                        Log.e("RegisterViewModel", userData.toJson());
                        toast.setValue("تم انشاء حساب للمتجر.");
                        userStoreModel.setValue(userData);
                        Utils.saveSharedPrefrences(Constants.KEY_USER_STORE, userData.toJson());
                        MyFirebaseMessagingService.RegisterDeviceForFCM();
                        userStoreModel.setValue(userData);
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e("RegisterViewModel", "Store Account : " + e.getMessage());
                        toast.setValue("Error check network connection 8: " + e.getMessage());
                    }
                });
    }

    public void getUserStoreData(com.elkdeals.mobile.api.models.UserModel userModel) {
         disposable.add(repositorySidalitac.processLogin(userModel.getMobile()).subscribe(
                userModel1 -> {
                    if (userModel1 == null) {
                        this.userStoreModel.setValue(null);
                        return;
                    }
                    if ("0".equalsIgnoreCase(userModel1.getSuccess())) {
                        toast.setValue(userModel1.getMessage());
                        return;
                    }
                     this.userStoreModel.setValue(userModel1);
                    Utils.saveSharedPrefrences(Constants.KEY_USER_STORE, userModel1.toJson());
                    MyFirebaseMessagingService.RegisterDeviceForFCM();
                }, throwable -> {
                    this.userStoreModel.setValue(null);
                    toast.setValue("Error Loading data : " + throwable.getMessage());
                }));
    }

    public void sendMoney(String amount, String WalletNumber) {
        progress.setValue("Transfering money...");
        disposable.add(model.sendTransfer(amount, WalletNumber).subscribe(
                trueMsg -> {
                    progress.setValue("");
                    transferModel.setValue(trueMsg);
                }, throwable -> message.setValue("Error Loading data")));
    }

    public void sendPics(String image, String fname, String type, String invid) {
        progress.setValue("sending image...");
        disposable.add(model.sendPic(image, fname, type, invid).subscribe(
                trueMsg -> {
                    if (trueMsg.getStatus())
                        message.setValue("send successfully");
                    else message.setValue("Error sending image!");
                }, throwable -> message.setValue("Error sending image!")));
    }

    public MutableLiveData<List<InvoicesModel>> getUserInvoicesLiveData() {
        return userInvoices;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

    public MutableLiveData<List<InvoiceDetail>> getUserInvoiceDetails() {
        return userInvoiceDetails;
    }


    public MutableLiveData<com.elkdeals.mobile.api.models.UserModel> getUserModel() {
        return userModel;
    }

    public MutableLiveData<ProfileImageModel> getProfileImage() {
        return profileImage;
    }

    public MutableLiveData<TrueMsg> getTransferModel() {
        return transferModel;
    }

    public MutableLiveData<DeleteInvResponse> getDeleteInvResponse() {
        return deleteInvResponse;
    }

    public MutableLiveData<List<NotificationModel>> getUserNotifications() {
        return userNotifications;
    }

    public MutableLiveData<NotificationDetModel> getUserNotificationDetailsLiveData() {
        return userNotificationDetails;
    }

    public MutableLiveData<WalletM> getWalletMMutableLiveData() {
        return WalletMMutableLiveData;
    }

    public MutableLiveData<List<HistoryM>> getWalletMListMutableLiveData() {
        return WalletMListMutableLiveData;
    }

    public MutableLiveData<UserData> getUserStoreModel() {
        return userStoreModel;
    }

}
