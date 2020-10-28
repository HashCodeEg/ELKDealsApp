package com.elkdeals.mobile.recievers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.activities.BaseActivity;
import com.elkdeals.mobile.fireBase.Config;
import com.elkdeals.mobile.fireBase.NotificationUtils;
import com.elkdeals.mobile.ui.account.MyAccount;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by mohamed on 5/20/17.
 */

public class NotesReciever {

    /*This Method  to get notifications and put it PopUp */
    public static void recieveNotes(Activity context, Intent intent) {
        if(TextUtils.isEmpty(intent.getAction()))
            return;
        // checking for type intent filter
        if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
            // gcm successfully registered
            // now subscribe to `global` topic to receive app wide notifications
            FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
            getFirebaseRegId(context);
        } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
            // new push notification is received
            String message = intent.getStringExtra("message");
            String logOutMessage = intent.getStringExtra("logOutMsg");

            if (logOutMessage != null) {
                if (!logOutMessage.equals("")) {
                    BaseActivity.instance.exitApp(true);
                }
            } else {
                if (message != null) {
                    BaseActivity.instance.addPage(new MyAccount());
                    BaseActivity.instance.showToast(R.string.complete_registeration);
                }
            }
        }
    }
    public static void callNotifications(BroadcastReceiver mRegistrationBroadcastReceiver, Context context) {
        try{
        BaseActivity.instance.registerReceiver(mRegistrationBroadcastReceiver,new IntentFilter(Config.PUSH_NOTIFICATION));
        BaseActivity.instance.registerReceiver(mRegistrationBroadcastReceiver,new IntentFilter(Config.REGISTRATION_COMPLETE));
        NotificationUtils.clearNotifications(context);}
        catch (Exception ignored){

        }
    }
    public static void removeNotifications(BroadcastReceiver mRegistrationBroadcastReceiver, Context context) {
        try{
        BaseActivity.instance.unregisterReceiver(mRegistrationBroadcastReceiver);
        NotificationUtils.clearNotifications(context);}
        catch (Exception ignored){

        }
    }

    private static String getFirebaseRegId(Context context) {
        SharedPreferences pref = context.getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
        Log.d("FGFGFGDG", "Firebase reg id: " + regId);
        return regId;
    }
}
