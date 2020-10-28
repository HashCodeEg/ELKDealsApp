package com.elkdeals.mobile.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {
    public static final String IMAGE_BASE_URL = "http://www.elkdeals.com/admin/uploads/";
    private static final File cacheDir = new File(System.getProperty("java.io.tmpdir"), UUID.randomUUID().toString());
    private static final Cache cache = new Cache(cacheDir, 1024);
    private static final Gson gson = new GsonBuilder()
            .setLenient()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create();

    private static Retrofit retrofitInstance;

    static HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);
    private static final OkHttpClient.Builder okHttpClient = new OkHttpClient().newBuilder().
            connectTimeout(20, TimeUnit.SECONDS).
            cache(cache).
            retryOnConnectionFailure(true).
            connectionPool(new ConnectionPool(15, 500000, TimeUnit.MILLISECONDS)).
            readTimeout(60, TimeUnit.SECONDS).
            addInterceptor(httpLoggingInterceptor);

    private static Retrofit getInstance() {
        if (retrofitInstance == null) {
            String baseUrl = "http://149.56.235.74:8080/elkservice/";
            retrofitInstance = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient.build())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

        }
        return retrofitInstance;
    }
    private static Retrofit getInstance(String baseUrl) {
        if (retrofitInstance == null) {
            retrofitInstance = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    //.client(okHttpClient.build())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

        }
        return retrofitInstance;
    }

    public static Services getAPIS() {
        return getInstance().create(Services.class);
    }
    public static Services getAPIS(String url) {
        return getInstance(url).create(Services.class);
    }
}
