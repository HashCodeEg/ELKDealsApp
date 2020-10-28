package com.elkdeals.mobile.Utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.elkdeals.mobile.App;
import com.elkdeals.mobile.R;
import com.elkdeals.mobile.activities.BaseActivity;
import com.elkdeals.mobile.api.API;
import com.elkdeals.mobile.api.models.NotificationModel;
import com.elkdeals.mobile.api.models.address_model.AddressDetails;
import com.elkdeals.mobile.api.models.device_model.DeviceInfo;
import com.islam.slider.SliderLayout;
import com.islam.slider.SliderTypes.BaseSliderView;
import com.islam.slider.SliderTypes.DefaultSliderView;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import static android.content.Context.LOCATION_SERVICE;
import static com.bumptech.glide.load.DecodeFormat.PREFER_ARGB_8888;

public class Utils {
    private static final String sharedPreferencesName = "ELK";
    public static String phone;
    public static String userId;

    private static AddressDetails addressDetails;
    private static String vCode;

    public static AddressDetails getAddressDetails() {
        return addressDetails;
    }

    public static void setAddressDetails(AddressDetails addressDetails) {
        Utils.addressDetails = addressDetails;
    }
    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static String getvCode() {
        return vCode;
    }

    public static void setvCode(String vCode) {
        Utils.vCode = vCode;
    }


    public static void loadImage(ImageView target, View progressbar, String baseUrl, String url, boolean asbitmap) {
        try{

            if (target == null || TextUtils.isEmpty(url)) {
                if (progressbar != null)
                    progressbar.setVisibility(View.GONE);
                return;
            }
            if (!TextUtils.isEmpty(baseUrl)) {
                if (!url.toLowerCase().contains(baseUrl.toLowerCase()))
                    url = baseUrl.concat(url);
            }
            RequestManager requestBuilder = Glide.with(target.getContext());

            RequestBuilder<Bitmap> requestBuilderBitmap = null;
            if (asbitmap)
                requestBuilderBitmap = requestBuilder.asBitmap();

            if (requestBuilderBitmap != null) {
                requestBuilderBitmap.load(url).error(android.R.drawable.stat_notify_error)
                        .placeholder(R.drawable.none).format(PREFER_ARGB_8888).
                        listener(new RequestListener<Bitmap>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                if (progressbar != null)
                                    progressbar.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                if (progressbar != null)
                                    progressbar.setVisibility(View.GONE);
                                return false;
                            }
                        }).into(target);
            } else requestBuilder
                    .load(url).error(android.R.drawable.stat_notify_error)
                    .placeholder(R.drawable.none).format(PREFER_ARGB_8888)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                            if (progressbar != null)
                                progressbar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                            if (progressbar != null)
                                progressbar.setVisibility(View.GONE);

                            return false;
                        }
                    })
                    .into(target);
        }
        catch (Exception e){

        }

    }

    public static void loadImage(ImageView target, String url) {
        loadImage(target, null, API.IMAGE_BASE_URL, url, false);
    }

    public static void loadImage(ImageView target, String url, boolean asBitmab) {
        loadImage(target, null, API.IMAGE_BASE_URL, url, asBitmab);
    }

    public static void loadImage(ImageView target, String baseUrl, String url) {
        loadImage(target, null, baseUrl, url, false);
    }

    public static void loadImage(ImageView target, View loader, String url) {
        loadImage(target, loader, API.IMAGE_BASE_URL, url, false);
    }


    public static String getStringRes(@StringRes int string) {
        try {
            if (BaseActivity.instance != null)
                return BaseActivity.instance.getString(string);
            else return App.getInstance().getString(string);
        } catch (Exception e) {
            return App.getInstance().getString(string);
        }
    }

    public static int getColorRes(@ColorRes int colorResourceId) {
        try {
            if (BaseActivity.instance != null)
                return ContextCompat.getColor(BaseActivity.instance, colorResourceId);
            else return ContextCompat.getColor(App.getInstance(), colorResourceId);
        } catch (Exception e) {
            return ContextCompat.getColor(App.getInstance(), colorResourceId);
        }
    }


    public static void initSlider(Context context, SliderLayout sliderLayout, String image) {
        if (!TextUtils.isEmpty(image))
            sliderLayout.addSlider(new DefaultSliderView(context).image(image, true));
    }

    public static void initSlider(Context context, SliderLayout sliderLayout, String... images) {
        if (images == null || images.length == 0)
            return;
        for (String img : images) {
            if (TextUtils.isEmpty(img))
                continue;
            sliderLayout.addSlider(new DefaultSliderView(context).image(img, true));
        }
    }

    public static void initSlider(Context context, SliderLayout sliderLayout, boolean concatToBaseUrl, String... images) {
        if (images == null || images.length == 0)
            return;
        for (String img : images) {
            if (TextUtils.isEmpty(img))
                continue;
            sliderLayout.addSlider(new DefaultSliderView(context).image(img, concatToBaseUrl));
        }
    }

    public static void initSlider(Context context, SliderLayout sliderLayout, String baseURl, String... images) {
        if (images == null || images.length == 0)
            return;
        sliderLayout.removeAllSliders();
        for (String img : images) {
            if (TextUtils.isEmpty(img))
                continue;

            Log.e("slider", baseURl.concat(img));
            sliderLayout.addSlider(new DefaultSliderView(context).image(baseURl.concat(img), false));
        }
    }

    public static void initSlider(BaseSliderView.OnSliderClickListener click, Context context, SliderLayout sliderLayout, String baseURl, String... images) {
        if (images == null || images.length == 0)
            return;
        sliderLayout.removeAllSliders();
        for (String img : images) {
            if (TextUtils.isEmpty(img))
                continue;

            Log.e("slider", baseURl.concat(img));

            sliderLayout.addSlider(new DefaultSliderView(context).setOnSliderClickListener(click).image(baseURl.concat(img), false));
        }
    }

    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    public static void saveSharedPrefrences(String key, String value) {

        //save to value with the key in SharedPreferences
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(App.getInstance()).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void clearSharedPrefrences(String key) {

        //save to value with the key in SharedPreferences
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(App.getInstance()).edit();
        editor.putString(key, "");
        editor.apply();
    }

    public static void saveSharedPrefrences(Context context, String key, String value) {

        //save to value with the key in SharedPreferences
        SharedPreferences.Editor editor
                = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getSharedPrefrences(String key, String defValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.getInstance());
        return sharedPreferences.getString(key, defValue);
    }

    public static String getSharedPrefrences(Context context, String key, String defValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key, defValue);
    }

    public static boolean getRead() {
        String s = getSharedPrefrences(Constants.ReadKey, "0");
        return !s.equals("0");

    }

    public static void setRead(boolean read) {
        if (read) saveSharedPrefrences(Constants.ReadKey, "1");
        else saveSharedPrefrences(Constants.ReadKey, "0");

    }

    public static void hideView(View view) {
        view.animate()
                .translationY(100)
                .alpha(0.0f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        view.setVisibility(View.GONE);
                    }
                });
    }

    public static void showView(View view) {
// Prepare the View for the animation
        view.setVisibility(View.VISIBLE);
        view.setAlpha(0.0f);

// Start the animation
        view.animate()
                .translationY(0)
                .alpha(1.0f)
                .setListener(null);
    }

    /*Get Device IMEI*/
    public static String getImei() {
//        String num = "";
//        int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE);
//        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
//        } else {
//            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//            num = telephonyManager.getDeviceId();
//        }
//        Log.e("imei", num);
//        //num=num+getRandomNonce(5);
//        Log.e("imei+getRandomNonce", num);
        return  UniqueDeviceID.INSTANCE.getUniqueId();
    }


    public static void hideSoftKeyboard(Activity activity) {
        try{
            hideSoftKeyboard(activity.getCurrentFocus(),activity);
        }catch (Exception ignored){

        }
    }
    public static void hideSoftKeyboard(View view,Context context) {
        try{
        InputMethodManager inputMethodManager =(InputMethodManager)context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (inputMethodManager!=null)
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }catch (Exception ignored){

        }
    }
    //*********** Returns information of the Device ********//

    @SuppressLint("MissingPermission")
    public static DeviceInfo getDeviceInfo(Context context) {

        double lat = 0;
        double lng = 0;
        //String IMEI = "";
        String NETWORK = "";
        String PROCESSORS = "";


        //String UNIQUE_ID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        PROCESSORS = String.valueOf(Runtime.getRuntime().availableProcessors());

        ActivityManager actManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        actManager.getMemoryInfo(memInfo);
        double totalRAM = Math.round(((memInfo.totalMem / 1024.0) / 1024.0) / 1024.0);


        if (CheckPermissions.is_PHONE_STATE_PermissionGranted()) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            try {
              //  IMEI = telephonyManager.getDeviceId();
                NETWORK = telephonyManager.getNetworkOperatorName();

            }  catch (Exception e) {
                e.printStackTrace();
            }
        }


        if (CheckPermissions.is_LOCATION_PermissionGranted()) {
            LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            try {
                boolean gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                boolean network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                Location location = null;
                String provider = locationManager.getBestProvider(new Criteria(), true);
                final LocationListener locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location loc) {
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {
                    }

                    @Override
                    public void onProviderEnabled(String provider) {
                    }

                    @Override
                    public void onProviderDisabled(String provider) {
                    }
                };
//                locationManager.requestLocationUpdates(provider, 1000, 0, locationListener);

                if (gps_enabled) {
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                } else if (network_enabled) {
                    location = locationManager.getLastKnownLocation(provider);
                }

                if (location != null) {
                    lat = location.getLatitude();
                    lng = location.getLongitude();
                }
                locationManager.removeUpdates(locationListener);

            } catch (Exception se) {
                se.printStackTrace();
            }
        }


        DeviceInfo device = new DeviceInfo();

        device.setDeviceID("");
        device.setDeviceType("Android");
        device.setDeviceUser(Build.USER);
        device.setDeviceModel(Build.BRAND + " " + Build.MODEL);
        device.setDeviceBrand(Build.BRAND);
        device.setDeviceSerial(Build.SERIAL);
        device.setDeviceSystemOS(System.getProperty("os.name"));
        device.setDeviceAndroidOS("Android " + Build.VERSION.RELEASE);
        device.setDeviceManufacturer(Build.MANUFACTURER);
        //device.setDeviceIMEI(IMEI);
        device.setDeviceRAM(totalRAM + "GB");
        device.setDeviceCPU(Build.UNKNOWN);
        device.setDeviceStorage(Build.UNKNOWN);
        device.setDeviceProcessors(PROCESSORS);
        device.setDeviceIP(Build.UNKNOWN);
        device.setDeviceMAC(Build.UNKNOWN);
        device.setDeviceNetwork(NETWORK);
        device.setDeviceLocation(lat + ", " + lng);
        device.setDeviceBatteryLevel(Build.UNKNOWN);
        device.setDeviceBatteryStatus(Build.UNKNOWN);

        return device;
    }


    public static void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) App.getInstance().getSystemService(
                Context.INPUT_METHOD_SERVICE
        );
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static int fetchAccentColor(Context context) {
        TypedValue typedValue = new TypedValue();

        TypedArray a = context.obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorAccent});
        int color = a.getColor(0, 0);

        a.recycle();

        return color;
    }

    public static int fetchPrimayDarkColor(Context context) {
        TypedValue typedValue = new TypedValue();

        TypedArray a = context.obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorPrimaryDark});
        int color = a.getColor(0, 0);

        a.recycle();

        return color;
    }

    public static void animateCartMenuIcon(MenuItem... menuItem) {
        if (menuItem == null) return;
        for (MenuItem item : menuItem) {
            if (item == null || item.getActionView() == null)
                continue;
            Animation animation = AnimationUtils.loadAnimation(App.getInstance(), R.anim.shake_icon);
            animation.setRepeatMode(Animation.ZORDER_TOP);
            animation.setRepeatCount(3);
            item.getActionView().startAnimation(animation);
            item.getActionView().startAnimation(null);
        }

    }


    //*********** Returns the current DataTime of Device ********//

    public static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();

        return dateFormat.format(date);
    }

    public static int getUnReadNotificatonCount(List<NotificationModel> notificationModelList) {
        if (notificationModelList == null || notificationModelList.size() == 0)
            return 0;
        int count = 0;
        for (NotificationModel model : notificationModelList)
            if (model.getStatus() == 0)
                count++;
        return count;
    }


    //*********** Checks if the Date is not Passed ********//

    public static boolean checkIsDatePassed(String givenDate) {

        boolean isPassed = false;

        Date dateGiven, dateSystem;

        Calendar calender = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate = dateFormat.format(calender.getTime());

        try {
            dateSystem = dateFormat.parse(currentDate);
            dateGiven = dateFormat.parse(givenDate);

            if (dateSystem.getTime() >= dateGiven.getTime()) {
                isPassed = true;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return isPassed;
    }


    //*********** Used to Animate the MenuIcons ********//

    public void runLayoutAnimation(final RecyclerView recyclerView) {

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) return;
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_from_right);

        recyclerView.setLayoutAnimation(controller);
        Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }


    //*********** Used to Calculate the Discount Percentage between New and Old Prices ********//

    public static String checkDiscount(String actualPrice, String discountedPrice) {

        if (discountedPrice == null) {
            discountedPrice = actualPrice;
        }

        Double oldPrice = Double.parseDouble(actualPrice);
        Double newPrice = Double.parseDouble(discountedPrice);

        double discount = (oldPrice - newPrice) / oldPrice * 100;

        return (discount > 0) ? Math.round(discount) + "% " + App.getInstance().getString(R.string.off) : null;
    }

    private static double getDiscount(String actualPrice, String discountedPrice) {

        if (discountedPrice == null) {
            discountedPrice = actualPrice;
        }

        Double oldPrice = Double.parseDouble(actualPrice);
        Double newPrice = Double.parseDouble(discountedPrice);

        double discount = (oldPrice - newPrice) / oldPrice * 100;
        return round(discount, 2);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static String getRandomNonce(int length) {

        //random max 32
        final String ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnm-";

        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder(length);

        for (int i = 0; i < length; ++i)
            randomStringBuilder.append(ALLOWED_CHARACTERS.charAt(generator.nextInt(ALLOWED_CHARACTERS.length())));

        return randomStringBuilder.toString();
    }

    //*********** Convert given String to Md5Hash ********//

    public static String getMd5Hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String md5 = number.toString(16);

            while (md5.length() < 32)
                md5 = "0" + md5;

            return md5;
        } catch (NoSuchAlgorithmException e) {
            Log.e("MD5", e.getLocalizedMessage());
            return null;
        }
    }
}
