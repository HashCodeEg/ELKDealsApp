package com.elkdeals.mobile.api.network;


import com.elkdeals.mobile.api.models.address_model.AddressData;
import com.elkdeals.mobile.api.models.address_model.Countries;
import com.elkdeals.mobile.api.models.address_model.Zones;
import com.elkdeals.mobile.api.models.banner_model.BannerData;
import com.elkdeals.mobile.api.models.category_model.CategoryData;
import com.elkdeals.mobile.api.models.contact_model.ContactUsData;
import com.elkdeals.mobile.api.models.coupons_model.CouponsData;
import com.elkdeals.mobile.api.models.device_model.AppSettingsData;
import com.elkdeals.mobile.api.models.filter_model.get_filters.FilterData;
import com.elkdeals.mobile.api.models.language_model.LanguageData;
import com.elkdeals.mobile.api.models.messages_model.Message_data;
import com.elkdeals.mobile.api.models.news_model.all_news.NewsData;
import com.elkdeals.mobile.api.models.news_model.news_categories.NewsCategoryData;
import com.elkdeals.mobile.api.models.order_model.OrderData;
import com.elkdeals.mobile.api.models.order_model.PostOrder;
import com.elkdeals.mobile.api.models.pages_model.PagesData;
import com.elkdeals.mobile.api.models.payment_model.GetBrainTreeToken;
import com.elkdeals.mobile.api.models.payment_model.PaymentMethodsData;
import com.elkdeals.mobile.api.models.product_model.GetAllProducts;
import com.elkdeals.mobile.api.models.product_model.ProductData;
import com.elkdeals.mobile.api.models.product_model.ViewCountData;
import com.elkdeals.mobile.api.models.requests.AddOfferData;
import com.elkdeals.mobile.api.models.requests.AddRequestData;
import com.elkdeals.mobile.api.models.requests.OffersData;
import com.elkdeals.mobile.api.models.requests.RequestData;
import com.elkdeals.mobile.api.models.requests.SellerData;
import com.elkdeals.mobile.api.models.search_model.SearchData;
import com.elkdeals.mobile.api.models.shipping_model.PostTaxAndShippingData;
import com.elkdeals.mobile.api.models.shipping_model.ShippingRateData;
import com.elkdeals.mobile.api.models.user_model.UserData;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


/**
 * APIRequests contains all the Network Request Methods with relevant API Endpoints
 **/

public interface APIRequests {


    //******************** User Data ********************//

    @FormUrlEncoded
    @POST("processregistration")
    Single<UserData> processRegistration(@Field("customers_firstname") String customers_firstname,
                                         @Field("customers_lastname") String customers_lastname,
                                         @Field("email") String customers_email_address,
                                         @Field("password") String customers_password,
                                         @Field("customers_telephone") String customers_telephone,
                                         @Field("customers_picture") String customers_picture);

    @FormUrlEncoded
    @POST("processloginelk")
    Single<UserData> processLogin(@Field("email") String customers_email_address);


    //******************** Address Data ********************//

    //******************** Address Data ********************//

    @POST("getcountries")
    Call<Countries> getCountries();

    @FormUrlEncoded
    @POST("getzones")
    Call<Zones> getZones(@Field("zone_country_id") String zone_country_id);

    @FormUrlEncoded
    @POST("getalladdress")
    Single<AddressData> getAllAddress(@Field("customers_id") String customers_id);

    @FormUrlEncoded
    @POST("addseller")
    Single<SellerData> addSeller(@Field("customers_id") String customers_id,
                                 @Field("entry_firstname") String entry_firstname,
                                 @Field("entry_lastname") String entry_lastname,
                                 @Field("entry_street_address") String entry_street_address,
                                 @Field("entry_postcode") String entry_postcode,
                                 @Field("entry_city") String entry_city,
                                 @Field("entry_country_id") String entry_country_id,
                                 @Field("entry_zone_id") String entry_zone_id,
                                 @Field("entry_company") String entry_company,
                                 @Field("entry_latitude") String entry_latitude,
                                 @Field("entry_longitude") String entry_longitude);
    @FormUrlEncoded
    @POST("updateseller")
    Single<SellerData> updateSeller(@Field("customers_id") String customers_id,
                                    @Field("entry_firstname") String entry_firstname,
                                    @Field("entry_lastname") String entry_lastname,
                                    @Field("entry_street_address") String entry_street_address,
                                    @Field("entry_postcode") String entry_postcode,
                                    @Field("entry_city") String entry_city,
                                    @Field("entry_country_id") String entry_country_id,
                                    @Field("entry_zone_id") String entry_zone_id,
                                    @Field("entry_company") String entry_company,
                                    @Field("entry_zone_id") String entry_latitude,
                                    @Field("entry_company") String entry_longitude);
    @FormUrlEncoded
    @POST("deleteseller")
    Single<SellerData> deleteSeller(@Field("customers_id") String customers_id);


    @POST("getallrequest")
    Single<RequestData> getRequests();


    @FormUrlEncoded
    @POST("getrequestoffer")
    Single<OffersData> getOffers(@Field("request_id") String requestID);


    @FormUrlEncoded
    @POST("addrequest")
    Single<AddRequestData> addRequest(@Field("customers_id") String customerID, @Field("category_id") String category_id
            , @Field("title") String title, @Field("description") String description
            , @Field("new_status") String new_status, @Field("used_status") String used_status);
    @FormUrlEncoded
    @POST("addoffer")
    Single<AddOfferData> addOffer(@Field("customers_id") String customerID, @Field("request_id") String request_id
            , @Field("price") String price, @Field("deliver_time") String deliver_time
            , @Field("shipping_price") String shipping_price, @Field("product_status") String product_status);

    @FormUrlEncoded
    @POST("getcustomerrequest")
    Single<RequestData> getRequests(@Field("customers_id") String customers_id);
    @FormUrlEncoded
    @POST("addshippingaddress")
    Call<AddressData> addUserAddress(@Field("customers_id") String customers_id,
                                     @Field("entry_firstname") String entry_firstname,
                                     @Field("entry_lastname") String entry_lastname,
                                     @Field("entry_street_address") String entry_street_address,
                                     @Field("entry_postcode") String entry_postcode,
                                     @Field("entry_city") String entry_city,
                                     @Field("entry_country_id") String entry_country_id,
                                     @Field("entry_zone_id") String entry_zone_id,
                                     @Field("customers_default_address_id") String customers_default_address_id);

    @FormUrlEncoded
    @POST("updateshippingaddress")
    Call<AddressData> updateUserAddress(@Field("customers_id") String customers_id,
                                        @Field("address_id") String address_id,
                                        @Field("entry_firstname") String entry_firstname,
                                        @Field("entry_lastname") String entry_lastname,
                                        @Field("entry_street_address") String entry_street_address,
                                        @Field("entry_postcode") String entry_postcode,
                                        @Field("entry_city") String entry_city,
                                        @Field("entry_country_id") String entry_country_id,
                                        @Field("entry_zone_id") String entry_zone_id,
                                        @Field("customers_default_address_id") String customers_default_address_id);

    @FormUrlEncoded
    @POST("updatedefaultaddress")
    Call<AddressData> updateDefaultAddress(@Field("customers_id") String customers_id,
                                           @Field("address_book_id") String address_book_id);

    @FormUrlEncoded
    @POST("deleteshippingaddress")
    Call<AddressData> deleteUserAddress(@Field("customers_id") String customers_id,
                                        @Field("address_book_id") String address_book_id);



    //******************** Category Data ********************//

    @FormUrlEncoded
    @POST("allcategories")
    Single<CategoryData> getAllCategories(@Field("language_id") int language_id);



    //******************** Product Data ********************//

    @POST("getallproducts")
    Single<ProductData> getAllProducts(@Body GetAllProducts getAllProducts);


    @FormUrlEncoded
    @POST("likeproduct")
    Single<ProductData> likeProduct(@Field("liked_products_id") int liked_products_id,
                                    @Field("liked_customers_id") String liked_customers_id);

    @FormUrlEncoded
    @POST("unlikeproduct")
    Single<ProductData> unlikeProduct(@Field("liked_products_id") int liked_products_id,
                                      @Field("liked_customers_id") String liked_customers_id);


    @FormUrlEncoded
    @POST("getfilters")
    Call<FilterData> getFilters(@Field("categories_id") int categories_id,
                                @Field("language_id") int language_id);


    @FormUrlEncoded
    @POST("getsearchdata")
    Call<SearchData> getSearchData(@Field("searchValue") String searchValue,
                                   @Field("language_id") int language_id);



    //******************** News Data ********************//

    @FormUrlEncoded
    @POST("getallnews")
    Single<NewsData> getAllNews(@Field("language_id") int language_id,
                                @Field("page_number") int page_number,
                                @Field("is_feature") int is_feature,
                                @Field("categories_id") String categories_id);

    @FormUrlEncoded
    @POST("allnewscategories")
    Single<NewsCategoryData> allNewsCategories(@Field("language_id") int language_id,
                                               @Field("page_number") int page_number);



    //******************** Order Data ********************//

    @POST("addtoorder")
    Single<OrderData> addToOrderr(@Body PostOrder postOrder);

    @POST("addtoorder")
    Call<OrderData> addToOrder(@Body PostOrder postOrder);

    @FormUrlEncoded
    @POST("getorders")
    Single<OrderData> getOrders(@Field("customers_id") String customers_id,
                                @Field("language_id") int language_id);

   @FormUrlEncoded
    @POST("getorders?language_id=4")
   Call<OrderData> getOrders(@Field("customers_id") String customers_id);


    @FormUrlEncoded
    @POST("getcoupon")
    Call<CouponsData> getCouponInfo(@Field("code") String code);


    @FormUrlEncoded
    @POST("getpaymentmethods")
    Single<PaymentMethodsData> getPaymentMethodss(@Field("language_id") int language_id);

    @FormUrlEncoded
    @POST("getpaymentmethods")
    Call<PaymentMethodsData> getPaymentMethods(@Field("language_id") int language_id);

    @GET("generatebraintreetoken")
    Single<GetBrainTreeToken> generateBraintreeToken();



    //******************** Banner Data ********************//

    @GET("getbanners")
    Single<BannerData> getBanners();



    //******************** Tax & Shipping Data ********************//

    @POST("getrate")
    Call<ShippingRateData> getShippingMethodsAndTax(
            @Body PostTaxAndShippingData postTaxAndShippingData);



    //******************** Contact Us Data ********************//

    @FormUrlEncoded
    @POST("contactus")
    Single<ContactUsData> contactUs(@Field("name") String name,
                                    @Field("email") String email,
                                    @Field("message") String message);



    //******************** Languages Data ********************//

    @GET("getlanguages")
    Single<LanguageData> getLanguages();



    //******************** App Settings Data ********************//

    @GET("sitesetting")
    Single<AppSettingsData> getAppSetting();


    //******************** Static Pages Data ********************//

    @FormUrlEncoded
    @POST("getallpages")
    Single<PagesData> getStaticPages(@Field("language_id") int language_id);



    //******************** Notifications Data ********************//

    @FormUrlEncoded
    @POST("registerdevices")
    Call<UserData> registerDeviceToFCM(@Field("device_id") String device_id,
                                       @Field("device_type") String device_type,
                                       @Field("ram") String ram,
                                       @Field("processor") String processor,
                                       @Field("device_os") String device_os,
                                       @Field("location") String location,
                                       @Field("device_model") String device_model,
                                       @Field("manufacturer") String manufacturer,
                                       @Field("customers_id") String customers_id);


    @FormUrlEncoded
    @POST("notify_me")
    Single<ContactUsData> notify_me(@Field("is_notify") String is_notify,
                                    @Field("device_id") String device_id);

    //********************* View Data **************************//

    @FormUrlEncoded
    @POST("viewproduct")
    Single<ViewCountData> viewproductcount(@Field("products_id") String products_id,
                                           @Field("customers_id") String coustomer_id);


    @FormUrlEncoded
    @POST("productenquiry")
    Single<ViewCountData> viewmessages(@Field("products_id") String products_id,
                                       @Field("customers_id") String customers_id,
                                       @Field("message") String messages);


    @FormUrlEncoded
    @POST("getenquiries")
    Single<Message_data> getmyMessages(@Field("customers_id") String customers_id,
                                       @Field("language_id") int language_id);


}

