package com.elkdeals.mobile.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.elkdeals.mobile.fireBase.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by mohamed on 8/17/16.
 */
public class Methods {

    // pattern to check if valid email or not///////////////////////////
    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%\\-+]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+");
    public static final Pattern EDIT_TEXT_PATTERN = Pattern.compile("^[\\p{L}\\d ]+(?:\\s[\\p{L}\\d ]+)*$");
    public static final Pattern USERNAME_PATTERN = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}");
    private static final int REQUEST_IMAGE_GET = 1;
    ////////////////// end ////////////////////////////////////////////
    //region Storage Permissions variables
    private static final int REQUEST_EXTERNAL_STORAGE = 3;
    static String videoUrl = "";
    //endregion
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    /// network check method
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }


    /* ================================================================= */
    public static void saveInSharedPref(String key, String value, Context context) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        prefsEditor.putString(key, value);
        prefsEditor.apply();
    }

    //----------------------------------------------------------------------------------//
    /* ================================================================= */
    public static void saveListInShared(String key, List<String> value, Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor mEdit1 = sp.edit();
        /* sKey is an array */
        mEdit1.putInt("Status_size", value.size());

        for (int i = 0; i < value.size(); i++) {
            mEdit1.putString(key, value.get(i));
        }

        mEdit1.apply();
    }

    //----------------------------------------------------------------------------------//
    public static void saveIntegerSharedPref(String key, Integer value, Context context) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        prefsEditor.putInt(key, value);
        prefsEditor.apply();
    }

    //----------------------------------------------------------------------------------//
    public static Integer getIntegerFromSharedPref(String key, Context context) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());

        Integer value = appSharedPrefs.getInt(key, 0);
        if (value != null)
            return value;
        else
            return 0;
    }

    //----------------------------------------------------------------- //
    public static String getFromSharedPref(String key, Context context) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());

        String value = appSharedPrefs.getString(key, "");
        if (value != null && !value.isEmpty())
            return value;
        else
            return null;
    } /* ============================================================== */

    //----------------------------------------------------------------- //
    public static List<String> getListFromSharedPref(String key, Context context) {
        SharedPreferences mSharedPreference1 = PreferenceManager.getDefaultSharedPreferences(context);
        List<String> dd = new ArrayList<>();

        int size = mSharedPreference1.getInt("Status_size", 0);

        for (int i = 0; i < size; i++) {
            dd.add(mSharedPreference1.getString(key, null));
        }
        return dd;
    } /* ============================================================== */

    public static void clear(Context context, String key) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        prefsEditor.remove(key);
        prefsEditor.apply();
    }

    public static void clearOneElement(String key, Context context) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        prefsEditor.remove(key);
        prefsEditor.apply();
    }

    public static void updateInSharedPref(String key, String value, Context context) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        prefsEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        prefsEditor.putString(key, value);
        prefsEditor.apply();
    }

    public static void updateIntegerInSharedPref(String key, Integer value, Context context) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        prefsEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        prefsEditor.putInt(key, value);
        prefsEditor.apply();
    }

    public static void setAppFont() {

       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                    .setDefaultFontPath("fonts/Cairo/Cairo-Regular.ttf")
                    .setFontAttrId(R.attr.fontPath)
                    .build()
            );
        }*/
        /* */
    }

    /*
        public static int getAllFromDB(DBManager dbManager) {
            Cursor cursor = dbManager.query(null, null, null, null);
            int table = 0;

            try {
                if (cursor.moveToFirst()) {
                    do {
                        table += cursor.getColumnIndex(DBManager.COL_AUC_ID);
                    } while (cursor.moveToNext());

                    if (table > 0) {
                        return table;
                    } else return 0;
                }
            } catch (SQLException e) {

            }

            return 0;
        }
    */
    public static String getKeyNameForMapUsingStringValue(String key, HashMap<String, String> map) {
        String myKey = "";
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue().equals(key)) {
                myKey = entry.getKey();
            }
        }
        return myKey;
    }

    public static String getKeyNameForMapUsingIntegerValue(String key, HashMap<String, Integer> map) {
        String myKey = "";
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue().equals(key)) {
                myKey = entry.getKey();
            }
        }
        return myKey;
    }

    public static String getStringValueMap(String key1, HashMap<String, String> map) {
        for (Map.Entry<String, String> mapEntry : map.entrySet()) {
            String key = mapEntry.getKey();
            String value = mapEntry.getValue();
            if (key1.equals(key))
                return value;
        }
        return null;
    }

    public static Integer getIntegerValueMap(String key1, HashMap<String, Integer> map) {
        for (Map.Entry<String, Integer> mapEntry : map.entrySet()) {
            String key = mapEntry.getKey();
            Integer value = mapEntry.getValue();
            if (key1.equals(key))
                return value;
        }
        return null;
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have read or write permission
        int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    public static void pickPhoto(Activity activity) {
        verifyStoragePermissions(activity);

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(intent, REQUEST_IMAGE_GET);
        }
    }

    public static void justifyListViewHeightBasedOnChildren(Context context, ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight * 40;
            listView.setLayoutParams(params);
            listView.requestLayout();
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.MATCH_PARENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * 3 * (listAdapter.getCount()));
        listView.setLayoutParams(params);
    }
    public static String getVideoID(String link) {
        String[] videoID = link.split("=");
        return videoID[1];
    }

    public static boolean checkEditTextEmpty(String txt, EditText edx) {
        if (txt != null)
            if (!txt.trim().equals("")) {
                return true;
            } else {
                edx.setError("اضف قيمة");
                return false;
            }
        else {
            edx.setError("اضف قيمة");
            return false;
        }
    }


    public static void setLineInTextView(TextView textView) {
        textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    public static String checkEmptyString(String txt) {
        if (TextUtils.isEmpty(txt))
            return "-";
        else return txt;
    }

    public static void openShareIntent(Context context, String url) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "please check out our product at: " + url);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }
    /*
     * Get FireBase Token
     * */
    public static String getToken() {
        return Utils.getSharedPrefrences(Config.SHARED_PREF,"");
    }
}
