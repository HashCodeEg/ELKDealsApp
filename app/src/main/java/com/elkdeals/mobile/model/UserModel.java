package com.elkdeals.mobile.model;

import com.elkdeals.mobile.api.models.DeleteInvResponse;
import com.elkdeals.mobile.api.models.HistoryM;
import com.elkdeals.mobile.api.models.InvoiceDetail;
import com.elkdeals.mobile.api.models.InvoicesModel;
import com.elkdeals.mobile.api.models.MobileModel;
import com.elkdeals.mobile.api.models.NotificationDetModel;
import com.elkdeals.mobile.api.models.NotificationModel;
import com.elkdeals.mobile.api.models.ProfileImageModel;
import com.elkdeals.mobile.api.models.TrueMsg;
import com.elkdeals.mobile.api.models.WalletM;

import java.util.List;

import io.reactivex.Single;

public class UserModel {
    private Repository repository = new Repository();

    public Single<List<InvoicesModel>> getUserInvoices() {
        return repository.getUserInvoices();
    }

    public Single<List<InvoiceDetail>> getUserInvoices(String invID) {
        return repository.getUserInvoiceDetails(invID);
    }

    public Single<com.elkdeals.mobile.api.models.UserModel> getUserData() {
        return repository.getUserData();
    }

    public Single<ProfileImageModel> getProfileImage() {
        return repository.getProfileImage();
    }

    public Single<List<NotificationModel>> getUserNotifications() {
        return repository.getUserNotifications();
    }
    public Single<NotificationDetModel> getUserNotificationsDetails(String notificationID) {
        return repository.getUserNotificationsDetails(notificationID);
    }

    public Single<WalletM> getWallet() {
        return repository.getWallet();
    }
    public Single<List<HistoryM>> getHistory() {
        return repository.getHistory();
    }

    public Single<TrueMsg> sendTransfer(String _amount, String _wnumber) {
        return repository.sendTransfer(_amount, _wnumber);
    }
    public Single<DeleteInvResponse> deleteInv(String invId) {
        return repository.deleteInv(invId);
    }
    public Single<TrueMsg> deleteInv(String invId, String ainvAuctionID) {
        return repository.deleteInv(invId,ainvAuctionID);
    }
    public Single<MobileModel> sendPic(String image, String fname, String type, String invid){
        return repository.sendPic(image,fname,type,invid);
    }
}
