package com.elkdeals.mobile.api;

import com.elkdeals.mobile.api.models.AboutWalletM;
import com.elkdeals.mobile.api.models.AucDescriptionModel;
import com.elkdeals.mobile.api.models.AucDetailsModel;
import com.elkdeals.mobile.api.models.BiddingListM;
import com.elkdeals.mobile.api.models.CategoriesModel;
import com.elkdeals.mobile.api.models.ChatModel;
import com.elkdeals.mobile.api.models.CitiesModel;
import com.elkdeals.mobile.api.models.CurrentAucClosedM;
import com.elkdeals.mobile.api.models.CurrentAuctionsDetModel;
import com.elkdeals.mobile.api.models.CurrentAuctionsModel;
import com.elkdeals.mobile.api.models.DeleteInvResponse;
import com.elkdeals.mobile.api.models.DetailsResponse;
import com.elkdeals.mobile.api.models.FAQModel;
import com.elkdeals.mobile.api.models.FinishedAuctionsModel;
import com.elkdeals.mobile.api.models.ForgotPassModel;
import com.elkdeals.mobile.api.models.HistoryM;
import com.elkdeals.mobile.api.models.HotLines;
import com.elkdeals.mobile.api.models.InformationModel;
import com.elkdeals.mobile.api.models.InvoiceAddResponse2;
import com.elkdeals.mobile.api.models.InvoiceDetail;
import com.elkdeals.mobile.api.models.InvoicesModel;
import com.elkdeals.mobile.api.models.MobileModel;
import com.elkdeals.mobile.api.models.NotificationDetModel;
import com.elkdeals.mobile.api.models.NotificationModel;
import com.elkdeals.mobile.api.models.OffersM;
import com.elkdeals.mobile.api.models.OrderDetails;
import com.elkdeals.mobile.api.models.OrderM;
import com.elkdeals.mobile.api.models.PartnersM;
import com.elkdeals.mobile.api.models.ProfileImageModel;
import com.elkdeals.mobile.api.models.PromoCodeModel;
import com.elkdeals.mobile.api.models.RegResponseModel;
import com.elkdeals.mobile.api.models.SecAucWay;
import com.elkdeals.mobile.api.models.SendPriceModel;
import com.elkdeals.mobile.api.models.SliderM;
import com.elkdeals.mobile.api.models.SplashVideo;
import com.elkdeals.mobile.api.models.StoreBuyLinks;
import com.elkdeals.mobile.api.models.StoreCartModel;
import com.elkdeals.mobile.api.models.StoreCategoriesModel;
import com.elkdeals.mobile.api.models.StoreCatesModel;
import com.elkdeals.mobile.api.models.StoreHomeResponse;
import com.elkdeals.mobile.api.models.StoreProductM;
import com.elkdeals.mobile.api.models.StoreProductModel;
import com.elkdeals.mobile.api.models.TimeLineModel;
import com.elkdeals.mobile.api.models.TrueMsg;
import com.elkdeals.mobile.api.models.UserModel;
import com.elkdeals.mobile.api.models.UserPromoCodesModel;
import com.elkdeals.mobile.api.models.WalletM;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


/**
 * Created by mohamed on 11/21/16.
 */

public interface Services {

    @GET("elkservice?action=chats")
    Single<List<ChatModel>> getChatList2(@Query("cid") String cid);

    @GET("elkservice?action=vvxx")
    Call<SplashVideo> getSplashVideo();

    @GET("elkservice?action=chats")
    Call<List<ChatModel>> getChatList(@Query("cid") String cid);

    @GET("elkservice?action=infos")
    Call<List<InformationModel>> getAllInfo();



    @FormUrlEncoded
    @POST("elkservice?action=chat")
    Call<TrueMsg> sendMessage(
            @Field("cid") String cid,
            @Field("msg") String msg,
            @Field("type") String type,
            @Field("photo") String photo
    );

    @GET("elkservice?action=clientdata")
    Single<UserModel> getUserDataRx(@Query("cid") String cid);

    @FormUrlEncoded
    @POST("elkservice?action=vcode")
    Single<RegResponseModel> askIfMobileExistsRx(
            @Field("mobile") String mobile
    );

    @FormUrlEncoded
    @POST("elkservice?action=getcode")
    Single<MobileModel> getSMSCodeRx(
            @Field("mobile") String mob,
            @Field("imei") String imei
    );
    @FormUrlEncoded
    @POST("elkservice?action=reg")
    Single<RegResponseModel> finishRegistrationRx(
            @Field("mobile") String mobile,
            @Field("idnumber") String idnumber,
            @Field("name") String name,
            @Field("email") String email,
            @Field("cityid") String cityID,
            @Field("address") String address,
            @Field("imei") String iemi,
            @Field("token") String token,
            @Field("logout") String logout
    );

    @GET("elkservice?action=disoffer")
    Call<String> disoffer(@Query("cid") String cid);


    @GET("elkservice?action=auctions")
    Call<List<CurrentAuctionsModel>> getHandBook(
            @Query("cid") String cid,
            @Query("type") String type
    );

    @GET("elkservice?action=auctions")
    Single<List<CurrentAuctionsModel>> getHandBookRx(
            @Query("cid") String cid,
            @Query("type") String type
    );


    @FormUrlEncoded
    @POST("elkservice?action=deleteinv")
    Single<DeleteInvResponse> deleteInvoice(
            @Field("invid") String invid
    );

    @GET("elkservice?action=invoices")
    Single<List<InvoicesModel>> getUserInvoices(
            @Query("cid") String userID
    );

    @GET("elkservice?action=fqa")
    Single<List<FAQModel>> getFQAList();

    @GET("elkservice?action=prodetails")
    Single<List<AucDescriptionModel>> getAuctionItemDetails(@Query("auid") String id);

    @GET("elkservice?action=currentauction")
    Single<List<AucDetailsModel>> getAuctionDetails(@Query("auid") String id);

    @GET("elkservice?action=currentauctionz")
    Call<List<CurrentAucClosedM>> getClosedAucDetails(@Query("auid") String id, @Query("cid") String cid);

    @GET("elkservice?action=closedauctions")
    Single<List<FinishedAuctionsModel>> getFinishedAuctions(
            @Query("cateid") String cateid,
            @Query("type") String type
    );

    @GET("elkservice?action=resultzx")
    Call<BiddingListM> getClosedAucDetails2(@Query("auid") String id, @Query("cid") String cid);

    @GET("elkservice?action=resultzx")
    Call<BiddingListM> getBiddingist(@Query("auid") String auid, @Query("cid") String cid);

    @GET("elkservice?action=resultzx")
    Single<BiddingListM> getBiddingistRx(@Query("auid") String auid, @Query("cid") String cid);

    @GET("elkservice?action=procatesx")
    Single<List<CategoriesModel>> getAuctionsX();

    /* else if (request.getParameter("action").equals("dobid")) {
                String auctionId = request.getParameter("auid");
                String clientId = request.getParameter("cid");
                String ch = request.getParameter("ch");*/
    @FormUrlEncoded
    @POST("elkservice?action=dobid")
    Call<TrueMsg> bidYesOrNo(
            @Field("auid") String auid,
            @Field("cid") String cid,
            @Field("ch") String zeroOrOne // 0 is  yes/// 1 is no
    );


    @GET("elkservice?action=allclientpromo")
    Call<List<UserPromoCodesModel>> getAllUserPromo(@Query("mobile") String mobile);

    @GET("elkservice?action=profilephoto")
    Single<ProfileImageModel> getProfilePicRx(@Query("cid") String cid);

    @GET("storeservice?action=procates")
    Single<List<StoreCategoriesModel>> getStoreCategories(@Query("catid") String catid);

    @GET("storeservice?action=cart")
    Single<List<StoreCartModel>> getCartItems(@Query("clientid") String clientid);

    @GET("elkservice?action=promocode")
    Call<PromoCodeModel> getPromCode(
            @Query("code") String code,
            @Query("mobile") String mobile
    );

    @FormUrlEncoded
    @POST("elkservice?action=adddoc")
    Single<MobileModel> sendPic(
            @Field("fname") String fname,
            @Field("cid") String cid,
            @Field("type") String type,
            @Field("invid") String invid,
            @Field("doc") String image
    );
    @GET("elkservice?action=currentinv")
    Single<InvoicesModel> getCurrentInvoiceRx(
            @Query("invid") String invid
    );
    @FormUrlEncoded
    @POST("elkservice?action=paypromo")
    Call<ForgotPassModel> getpaypromo(@Field("promoid") String promoid, @Field("invid") String invid);

    @GET("elkservice?action=invauctionsnew")
    Single<List<InvoiceDetail>> getInvoiceDetails(
            @Query("invid") String invid
    );

    @GET("elkservice?action=cities")
    Call<List<CitiesModel>> getEgyptCites();

    @GET("elkservice?action=cities")
    Single<List<CitiesModel>> getEgyptCitesRx();

    @FormUrlEncoded
    @POST("elkservice?action=bid")
    Call<SendPriceModel> sendYourPrice(@Field("auid") String auid,
                                       @Field("cid") String cid,
                                       @Field("tot") String tot);

    @FormUrlEncoded
    @POST("elkservice?action=bidy")
    Call<SecAucWay> sendTot(
            @Field("auid") String auid,
            @Field("tot") String tot,
            @Field("cid") String cid
    );

    @FormUrlEncoded
    @POST("elkservice?action=bidf")
    Call<SecAucWay> sendEnsureTot(
            @Field("auid") String auid,
            @Field("tot") String tot,
            @Field("cid") String cid
    );

    @GET("elkservice?action=auctions")
    Call<List<CurrentAuctionsModel>> getCurrentAuction(@Query("cid") String cid, @Query("type") String type);

    @FormUrlEncoded
    @POST("elkservice?action=bidx")
    Call<SendPriceModel> sendYourPrice2(@Field("auid") String auid,
                                        @Field("cid") String cid,
                                        @Field("tot") String tot);

    @FormUrlEncoded
    @POST("elkservice?action=bidz")
    Call<SendPriceModel> sendYourPriceClosedAuc(
            @Field("auid") String auid,
            @Field("cid") String cid,
            @Field("tot") String tot
    );

    @FormUrlEncoded
    @POST("elkservice?action=payamount")
    Call<ForgotPassModel> getUserAmount(@Field("invid") String invid);

    @FormUrlEncoded
    @POST("elkservice?action=addinvtimeline")
    Call<InvoiceAddResponse2> addInvTimeLine(
            @Field("cid") String cid,
            @Field("aid") String aid
    );

    @FormUrlEncoded
    @POST("elkservice?action=addinvtimeline")
    Single<InvoiceAddResponse2> addInvTimeLineRx(
            @Field("cid") String cid,
            @Field("aid") String aid
    );

    @GET("elkservice?action=phones")
    Single<List<HotLines>> getAllHotLiens();

    @FormUrlEncoded
    @POST("elkservice?action=sendquestion")
    Call<ForgotPassModel> sendQuestions(
            @Field("cid") String cid,
            @Field("aid") String aid,
            @Field("msg") String msg
    );

    @GET("elkservice?action=currentauction")
    Call<List<CurrentAuctionsDetModel>> getCurrentAuctionDetails(@Query("auid") String auid, @Query("cid") String cid);


    @GET("elkservice?action=timelineau")
    Call<List<TimeLineModel>> getTimeLine(
            @Query("cid") String cid,
            @Query("catid") String catid
    );

    @GET("elkservice?action=timelineau")
    Single<List<TimeLineModel>> getTimeLineRx(
            @Query("cid") String cid,
            @Query("catid") String catid
    );

    @GET("elkservice?action=timelineauall")
    Single<List<TimeLineModel>> getTimeLineAllRx(
            @Query("cid") String cid,
            @Query("catid") String catid
    );

    @GET("elkservice?action=notifs")
    Single<List<NotificationModel>> getUserNotifications(
            @Query("cid") String cid
    );

    @GET("elkservice?action=notdetails")
    Single<NotificationDetModel> getUserNotificationsDetails(
            @Query("nid") String nid
    );

    @FormUrlEncoded
    @POST("elkservice?action=addallinvtimeline")
    Call<InvoiceAddResponse2> addAllInvTimeLine(
            @Field("cid") String cid,
            @Field("cateid") String catid
    );

    @GET("storeservice?action=products")
    Single<List<StoreProductModel>> getStoreProductsRx(@Query("catid") String catid);

    @GET("storeservice?action=prodetails")
    Call<List<AucDescriptionModel>> getStoreProductsDet(@Query("proid") String proid);

    @GET("elkservice?action=cateschildern")
    Call<List<StoreCatesModel>> getcateschildern(
            @Query("cateid") String id
    );

    @GET("elkservice?action=cateschildern")
    Single<List<StoreCatesModel>> getcateschildernRx(
            @Query("cateid") String id
    );

    @GET("storeservice?action=prolinks")
    Call<List<StoreBuyLinks>> getProLinks(
            @Query("proid") String proid
    );

    //@FormUrlEncoded
    @GET("elkservice?action=deleteinvrow")
    Single<TrueMsg> deleteInvItem(
            @Query("invid") String invid,
            @Query("invauid") String invauid
    );

    @FormUrlEncoded
    @POST("elkservice?action=sendelk")
    Single<TrueMsg> sendTransfer(
            @Field("amount") String _amount,
            @Field("wid") String _wid,
            @Field("wnumber") String _wnumber
    );

    @GET("elkservice?action=wallet")
    Single<WalletM> getWallet(
            @Query("cid") String _cid
    );

    @GET("elkservice?action=aboutwallet")
    Call<AboutWalletM> getAboutwallet(
    );

    @GET("elkservice?action=treams")
    Call<TrueMsg> termsAndConditions(
    );

    @GET("elkservice?action=wallettrans")
    Single<List<HistoryM>> getHistory(
            @Query("wid") String _wid
    );

    @GET("elkservice?action=wallettrans")
    Single<List<HistoryM>> getHistoryRx(
            @Query("wid") String _wid
    );

    @GET("elkservice?action=partners")
    Single<List<PartnersM>> getPartnersRx(
    );

    @GET("elkservice?action=rate")
    Call<TrueMsg> sendRate(
            @Query("auid") String auid,
            @Query("cid") String cid,
            @Query("comment") String comment,
            @Query("rate") String rate
    );

    @GET("storeservice?action=products")
    Single<List<StoreProductM>> getProducts(@Query("catid") String _prodId);

    @GET("storeservice?action=cart")
    Single<List<StoreCartModel>> getClientCart(@Query("clientid") String _clientId);

    @GET("storeservice?action=search")
    Single<List<StoreProductM>> getSearchResult(@Query("key") String _key);

    @GET("storeservice?action=offers")
    Single<List<OffersM>> getOffers();

    @GET("storeservice?action=prodetails")
    Single<DetailsResponse> getDetails(@Query("proid") String _prodId);

    @GET("storeservice?action=home")
    Single<StoreHomeResponse> getHome();

    @GET("storeservice?action=orders")
    Single<List<OrderM>> getOrders(@Query("cid") String cid);

    @GET("storeservice?action=orderdetails")
    Single<OrderDetails> getOrderDetails(@Query("orderid") String cartId);

    @FormUrlEncoded
    @POST("storeservice?action=addtocart")
    Single<TrueMsg> addToCart(
            @Field("cid") String clientid,
            @Field("proid") String proid,
            @Field("qty") String quantity
    );


    @FormUrlEncoded
    @POST("storeservice?action=removeprocart")
    Single<TrueMsg> removeFromCart(
            @Field("cid") String clientid,
            @Field("proid") String proid
    );

    @FormUrlEncoded
    @POST("storeservice?action=chqty")
    Single<TrueMsg> changeQuantity(
            @Field("cid") String clientid,
            @Field("proid") String proid,
            @Field("qty") String qty
    );

    @FormUrlEncoded
    @POST("storeservice?action=processcart")
    Single<TrueMsg> processCart(
            @Field("cid") String clientId,
            @Field("paytype") String payType,
            @Field("address") String address
    );

    @FormUrlEncoded
    @POST("services?action=rateorder")
    Single<TrueMsg> sendProdRate(@Field("orderid") String orderId, @Field("rate") String rate);

    @GET("elkservice?action=slide")
    Call<List<SliderM>> getSlider();
}
