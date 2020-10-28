package com.elkdeals.mobile.fireBase;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.elkdeals.mobile.App;
import com.elkdeals.mobile.Utils.Constants;
import com.elkdeals.mobile.Utils.Methods;
import com.elkdeals.mobile.Utils.Utils;
import com.elkdeals.mobile.ui.splash.Splash;
import com.elkdeals.mobile.api.models.device_model.DeviceInfo;
import com.elkdeals.mobile.api.models.user_model.UserData;
import com.elkdeals.mobile.api.network.APIClient;
import com.elkdeals.mobile.dialogFragments.MessageDialog;
import com.elkdeals.mobile.model.Repository;
import com.elkdeals.mobile.model.RepositorySidalitac;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMessaging";

    private NotificationUtils notificationUtils;
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        logE("onStart: " + startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        logE("onCreate: ");
    }
    @Override
    public void onNewToken(String refreshedToken) {
        logE("onNewToken: "+refreshedToken);
        super.onNewToken(refreshedToken);
        // Saving reg id to shared preferences
        storeRegIdInPref(refreshedToken);

        // sending reg id to your server
        sendRegistrationToServer(refreshedToken);

        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(Config.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void sendRegistrationToServer(final String token) {
        // sending gcm token to server
        logE("sendRegistrationToServer: " + token);
    }

    private void storeRegIdInPref(String token) {
        Utils.saveSharedPrefrences(Config.SHARED_PREF,token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        logE("From: " + remoteMessage.getFrom());

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            logE("Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            logE("Data Payload: " + remoteMessage.getData().toString());

            try {

                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (Exception e) {
                logE( "Exception: " + e.getMessage());
            }
        }
    }

    private void handleNotification(String message) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        } else {
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            // If the app is in background, firebase itself handles the notification
        }
    }

    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());

        String type;
        String title = "";
        String message = "";
        String audID = "";
        String newscatID = "";
        String newsID = "";
        String logOutMsg = "";
        String userID = "";
        String newBidCount = "";

        try {

            // JSONObject data = json.getJSONObject("data");
            type = json.getString("type");
            switch (type) {
                case "1":
                    title = json.getString("title");
                    title = title.replace("_", " ");
                    message = json.getString("message");
                    message = message.replace("_", " ");
                   logE( "title: " + title);
                   logE( "message: " + message);
                    break;
                case "2":
                    audID = json.getString("id");
                    break;
                case "12":
                    audID = json.getString("id");
                    break;
                case "3":
                    newscatID = json.getString("id");
                    message = json.getString("msg");
                    message = message.replace("_", " ");
                    break;
                case "4":
                    newsID = json.getString("id");
                    message = json.getString("msg");
                    message = message.replace("_", " ");
                    break;
                case "5":
                    logOutMsg = json.getString("msg");
                    logOutMsg = logOutMsg.replace("_", " ");
                    break;
                case "6":
                    userID = json.getString("id");
                    newBidCount = json.getString("count");
                    break;
            }


            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                /*switch (type) {
                    case "2":
                        pushNotification.putExtra(Keys.KEY_POST_ID, audID);

                        break;
                    case "12":
                        pushNotification.putExtra(Keys.KEY_POST_ID, audID);

                        break;
                    case "1": {
                        pushNotification.putExtra("message", message);
                        Intent resultIntent = new Intent(getApplicationContext(), HomeActivity.class);
                        resultIntent.putExtra("message", message);
                        showNotificationMessage(getApplicationContext(), title, message, "", resultIntent);

                        break;
                    }
                    case "3": {
                        pushNotification.putExtra("msg", message);
                        Intent resultIntent = new Intent(getApplicationContext(), NotificationsList.class);
                        resultIntent.putExtra("CATEID", newscatID);
                        showNotificationMessage(getApplicationContext(), title, message, "", resultIntent);

                        break;
                    }
                    case "4": {
                        pushNotification.putExtra("msg", message);
                        Intent resultIntent = new Intent(getApplicationContext(), NotficationDetails.class);
                        resultIntent.putExtra("NOTE_ID", newsID);
                        showNotificationMessage(getApplicationContext(), title, message, "", resultIntent);
                        break;
                    }
                    case "5": {
                        pushNotification.putExtra("logOutMsg", logOutMsg);
                        Intent resultIntent = new Intent(getApplicationContext(), HomeActivity.class);
                        resultIntent.putExtra("logOutMsg", logOutMsg);
                        showNotificationMessage(getApplicationContext(), title, message, "", resultIntent);
                        break;
                    }
                    case "6": {
                        pushNotification.putExtra("count", newBidCount);
                        Intent resultIntent = new Intent(getApplicationContext(), HomeActivity.class);
                        resultIntent.putExtra("count", newBidCount);
                        showNotificationMessage(getApplicationContext(), title, message, "", resultIntent);

                        break;
                    }
                }*/

                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
                // play notification sound
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                if (type.equals("1"))
                    notificationUtils.playNotificationSound();

            } else {
                // app is in background, show the notification in notification tray

                switch (type) {
                   /* case "1": {
                        Intent resultIntent = new Intent(getApplicationContext(), HomeActivity.class);
                        resultIntent.putExtra("message", message);

                        // check for image attachment
                        if (TextUtils.isEmpty("")) {
                            showNotificationMessage(getApplicationContext(), title, message, "", resultIntent);
                        } else {
                            // image is present, show notification with image
                            showNotificationMessageWithBigImage(getApplicationContext(), title, message, "", resultIntent, "");
                        }
                        break;
                    }
                    case "3": {
                        Intent resultIntent = new Intent(getApplicationContext(), NotificationsList.class);
                        // resultIntent.putExtra("message", message);
                        resultIntent.putExtra("CATEID", newscatID);

                        if (TextUtils.isEmpty("")) {
                            showNotificationMessage(getApplicationContext(), title, message, "", resultIntent);
                        } else {
                            showNotificationMessageWithBigImage(getApplicationContext(), title, message, "", resultIntent, "");
                        }
                        break;
                    }
                    case "4": {
                        Intent resultIntent = new Intent(getApplicationContext(), NotficationDetails.class);
                        // resultIntent.putExtra("message", message);
                        resultIntent.putExtra("NOTE_ID", newsID);

                        if (TextUtils.isEmpty("")) {
                            showNotificationMessage(getApplicationContext(), title, message, "", resultIntent);
                        } else {
                            showNotificationMessageWithBigImage(getApplicationContext(), title, message, "", resultIntent, "");
                        }
                        break;
                    }*/
                    case "5": {
                        Intent resultIntent = new Intent(getApplicationContext(), Splash.class);
                        Methods.clear(this, Constants.KEY_USER);
                       // Methods.clear(this, Constants.KEY_USER_ACCOUNT_BINCODE);
                        Methods.clear(this, "VERFCODE");
                        if (TextUtils.isEmpty("")) {
                            showNotificationMessage(getApplicationContext(), title, message, "", resultIntent);
                        }
                        break;
                    }
                }

            }
        } catch (JSONException e) {
           logE( "Json Exception: " + e.getMessage());
        } catch (Exception e) {
           logE( "Exception: " + e.getMessage());
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }

    //*********** Register Device to Admin Panel with the Device's Info ********//

    public static void RegisterDeviceForFCM() {
        final Context context= App.getInstance();
        MessageDialog dialog=null;
        DeviceInfo device = Utils.getDeviceInfo(context);
        if( RepositorySidalitac.getUserInfo()==null||RepositorySidalitac.getUserInfo().getData()==null||
                RepositorySidalitac.getUserInfo().getData().size()==0){
            if(true)
                return;
            if(Repository.getUserInfo()==null)return;
            else
            APIClient.getInstance().processLogin(Repository.getUserInfo().getMobile())
            .subscribe(new SingleObserver<UserData>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onSuccess(UserData userData) {

                }

                @Override
                public void onError(Throwable e) {

                }
            });
        }
        String customer_ID = RepositorySidalitac.getUserInfo().getData().get(0).getCustomersId();
        String deviceID = FirebaseInstanceId.getInstance().getToken();
        Call<UserData> call = APIClient.getInstance()
                .registerDeviceToFCM
                        (
                                deviceID,
                                device.getDeviceType(),
                                device.getDeviceRAM(),
                                device.getDeviceProcessors(),
                                device.getDeviceAndroidOS(),
                                device.getDeviceLocation(),
                                device.getDeviceModel(),
                                device.getDeviceManufacturer(),
                                customer_ID
                        );

        logE(deviceID);
        logE(device.getDeviceType());
        logE(device.getDeviceRAM());
        logE(device.getDeviceProcessors());
        logE(device.getDeviceAndroidOS());
        logE(device.getDeviceLocation());
        logE(device.getDeviceModel());
        logE(device.getDeviceManufacturer());
        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {

                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {

                        Log.e("notification", response.body().getMessage());
//                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();

                    }
                    else {

                        Log.e("notification", response.body().getMessage());
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Log.e("notification", response.errorBody().toString());
                }
            }
            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                Toast.makeText(context, "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });

    }

    public static void logE(String message){
        if(!TextUtils.isEmpty(message))
        Log.e(TAG, message);
    }
}