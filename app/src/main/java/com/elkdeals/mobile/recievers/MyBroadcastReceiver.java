package com.elkdeals.mobile.recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.elkdeals.mobile.Utils.Constants;
import com.elkdeals.mobile.Utils.Methods;

/**
 * Created by mohamed on 4/13/17.
 * <p>
 * This BroadcastReceiver class To Get Some Message From Server
 * If he found It
 * To Display IT
 * in POPUP Menu
 */

public class MyBroadcastReceiver extends BroadcastReceiver {

    /*
    * @var result To Pass Message TO All Activities
    * @var avtivityName To Get The Activity That You Wanna Display Message In
    *
    *   I Save Message Using Activity Name Key In SharedPreferences To Make It Esay To get It At
    *   Any Activity To Display Server Message
    * */

    @Override
    public void onReceive(Context context, Intent intent) {
        String result = intent.getStringExtra(Constants.EXTRA_KEY_OUT);


        String avtivityName = context.getClass().getSimpleName();
        Methods.saveInSharedPref(avtivityName, result, context);
        Log.d("HOPAAAO", context.getClass().getSimpleName());
    }
}