package com.elkdeals.mobile.api.network;

import android.text.TextUtils;
import android.util.Log;

import com.elkdeals.mobile.Utils.Constants;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.elkdeals.mobile.Utils.Utils.getRandomNonce;

/**
 * Created by muneeb.vectorcoder@gmail.com on 30-Jan-18.
 */

public class API_Interceptor implements Interceptor {
    
    private static final String ECOMMERCE_CONSUMER_KEY = "consumer-key";
    private static final String ECOMMERCE_CONSUMER_SECRET = "consumer-secret";
    private static final String ECOMMERCE_CONSUMER_NONCE = "consumer-nonce";
    private static final String ECOMMERCE_CONSUMER_DEVICE_ID = "consumer-device-id";
    
    private String consumerKey;
    private String consumerSecret;
    private String consumerNonce;
    private String consumerDeviceID;
    
    
    private API_Interceptor(String consumerKey, String consumerSecret) {
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
    }
    
    
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();
        
        consumerNonce = getRandomNonce(Constants.RANDOM_NONCE);
    
        String deviceID = "";

        deviceID = FirebaseInstanceId.getInstance().getToken();
        if (deviceID != null  &&  !TextUtils.isEmpty(deviceID)) {
            consumerDeviceID = deviceID;
        }
        else {
            consumerDeviceID = getRandomNonce(Constants.RANDOM_NONCE);
        }
        
        
        Log.e("VC_Shop", ECOMMERCE_CONSUMER_KEY+" = "+consumerKey);
        Log.e("VC_Shop", ECOMMERCE_CONSUMER_SECRET+" = "+consumerSecret);
        Log.e("VC_Shop", ECOMMERCE_CONSUMER_NONCE+" = "+consumerNonce);
        Log.e("VC_Shop", ECOMMERCE_CONSUMER_DEVICE_ID+" = "+consumerDeviceID);
        
        
        HttpUrl url = originalHttpUrl.newBuilder().build();
        
        // Request customization: add request headers
        Request.Builder requestBuilder = original.newBuilder()
               .addHeader("Content-Type", "application/json")
                .addHeader(ECOMMERCE_CONSUMER_KEY, consumerKey)
                .addHeader(ECOMMERCE_CONSUMER_SECRET, consumerSecret)
                .addHeader(ECOMMERCE_CONSUMER_NONCE, consumerNonce)
                .addHeader(ECOMMERCE_CONSUMER_DEVICE_ID, consumerDeviceID)
                .url(url);
        
        
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
    

    
    
    public static final class Builder {
        
        private String consumerKey;
        private String consumerSecret;
        
        public Builder consumerKey(String consumerKey) {
            if (consumerKey == null) throw new NullPointerException("consumerKey = null");
            this.consumerKey = consumerKey;
            return this;
        }
        
        public Builder consumerSecret(String consumerSecret) {
            if (consumerSecret == null) throw new NullPointerException("consumerSecret = null");
            this.consumerSecret = consumerSecret;
            return this;
        }
        
        
        public API_Interceptor build() {
            
            if (consumerKey == null) throw new IllegalStateException("consumerKey not set");
            if (consumerSecret == null) throw new IllegalStateException("consumerSecret not set");
            
            return new API_Interceptor(consumerKey, consumerSecret);
        }
    }
    
    public String urlEncoded(String url) {
        String encodedURL = "";
        try {
            encodedURL = URLEncoder.encode(url, "UTF-8");
            Log.e("encodedURL", encodedURL);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        
        return encodedURL;
    }
}
