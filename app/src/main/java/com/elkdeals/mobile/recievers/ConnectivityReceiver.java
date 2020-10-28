package com.elkdeals.mobile.recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.elkdeals.mobile.App;
import com.elkdeals.mobile.Utils.Constants;
import com.elkdeals.mobile.interfaces.OnConnectionStatusChanged;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by mohamed on 2/18/18.
 */

public class ConnectivityReceiver extends BroadcastReceiver {
    private static ConnectivityReceiver receiver;
    private ArrayList<OnConnectionStatusChanged> mOnConnectionStatusChanged;

    public static BroadcastReceiver getInstance() {
        if (receiver == null)
            receiver = new ConnectivityReceiver();
        if (receiver.mOnConnectionStatusChanged == null)
            receiver.mOnConnectionStatusChanged = new ArrayList<>();
        return receiver;
    }

    public synchronized static boolean hasActiveInternetConnection() {
        try {
            HttpURLConnection urlc = (HttpURLConnection) (new URL(Constants.ConnectionTestURL).openConnection());
            urlc.setRequestProperty("User-Agent", "Test");
            urlc.setRequestProperty("Connection", "close");
            urlc.setConnectTimeout(1500);
            urlc.connect();
            return (urlc.getResponseCode() == 200);
        } catch (IOException e) {
            Log.e(Constants.LogTag, "Error checking internet connection", e);
        } catch (Exception e) {
            Log.e(Constants.LogTag, e.getMessage(), e);
        }
        return false;
    }

    public static boolean isConnected() {
        ConnectivityManager
                cm = (ConnectivityManager) App.getInstance().getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }

    public static void observeNetworkStatus(OnConnectionStatusChanged listener) {
        if (listener != null)
            ((ConnectivityReceiver) getInstance()).mOnConnectionStatusChanged.add(listener);

    }

    @Override
    public void onReceive(Context context, Intent arg1) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        notifyListeners(activeNetwork != null
                && activeNetwork.isConnectedOrConnecting());
    }

    public void notifyListeners(boolean status) {
        for (OnConnectionStatusChanged listener : ((ConnectivityReceiver) getInstance()).mOnConnectionStatusChanged)
            if (listener != null)
                try {
                    listener.OnConnectionStatusChanged(status);
                }catch (Exception e){

                }
    }
}