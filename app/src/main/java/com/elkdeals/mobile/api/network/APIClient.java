package com.elkdeals.mobile.api.network;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.elkdeals.mobile.Utils.Utils.getMd5Hash;


/**
 * APIClient handles all the Network API Requests using Retrofit Library
 **/

public class APIClient {
    
    
    // Base URL for API Requests
    //public static final String BASE_URL = "http://hashcodegroup.com/elkdeals/";
    public static final String BASE_URL = "https://elkdeals.com/store";
    public static final String ECOMMERCE_CONSUMER_KEY = "e3312368157253966056f6af4e";
    public static final String ECOMMERCE_CONSUMER_SECRET = "61b803341572539660663e2e24";

    private static APIRequests apiRequests;
     
    
    // Singleton Instance of APIRequests
    public static APIRequests getInstance() {
        if (apiRequests == null) {
    
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    
            API_Interceptor apiInterceptor = new API_Interceptor.Builder()
                    .consumerKey(getMd5Hash(ECOMMERCE_CONSUMER_KEY))
                    .consumerSecret(getMd5Hash(ECOMMERCE_CONSUMER_SECRET))
                    .build();
            
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(apiInterceptor)
                    .addInterceptor(httpLoggingInterceptor)
                    .build();
            
            
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL+ "index.php/app/")
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            
            
            apiRequests = retrofit.create(APIRequests.class);
            
            return apiRequests;
        }
        else {
            return apiRequests;
        }
    }

}


