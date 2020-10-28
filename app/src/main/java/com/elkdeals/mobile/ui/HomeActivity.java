package com.elkdeals.mobile.ui;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.Constants;
import com.elkdeals.mobile.Utils.Methods;
import com.elkdeals.mobile.Utils.Utils;
import com.elkdeals.mobile.activities.BaseActivity;
import com.elkdeals.mobile.activities.ResultsActivitty;
import com.elkdeals.mobile.adapters.MenuAdapter;
import com.elkdeals.mobile.api.models.ProfileImageModel;
import com.elkdeals.mobile.api.models.UserModel;
import com.elkdeals.mobile.custom.ProfileImageView;
import com.elkdeals.mobile.dialogFragments.EditInformationDialog;
import com.elkdeals.mobile.dialogFragments.MessageDialog;
import com.elkdeals.mobile.fireBase.Config;
import com.elkdeals.mobile.model.Repository;
import com.elkdeals.mobile.model.RepositorySidalitac;
import com.elkdeals.mobile.ui.auctions.AuctionsActivity;
import com.elkdeals.mobile.ui.auctions.AuctionsCategories;
import com.elkdeals.mobile.ui.requests.Requests;
import com.elkdeals.mobile.ui.splash.Splash;
import com.elkdeals.mobile.ui.store.My_Orders;
import com.elkdeals.mobile.viewmodel.RegisterViewModel;
import com.elkdeals.mobile.viewmodel.UserViewModel;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.elkdeals.mobile.Utils.Constants.DATA;
import static com.elkdeals.mobile.Utils.Constants.FORCE_LOGOUT;
import static com.elkdeals.mobile.dialogFragments.QuestionsDialog.REQUEST_PHONE_CALL;
import static com.elkdeals.mobile.fireBase.Config.REGISTRATION_COMPLETE;

public class HomeActivity extends BaseActivity {
    ViewDataBinding view;
    @BindView(R.id.profile_image)
    ProfileImageView profileImageView;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.username)
    TextView userName;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    public static final String TAG = "Home";
    //@BindView(R.id.trial_text)
    @BindView(R.id.textView5)
    TextView trial;
    @BindView(R.id.support_phone)
    public TextView supportPhone;
    @BindView(R.id.username_)
    TextView username_;
    UserViewModel userViewModel;
    RegisterViewModel registerViewModel;

    public HomeActivity() {

    }

    @Override
    public ArrayList<MenuAdapter.MenuItem> getItems() {
        return getDefaultMenuItems();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        invalidateOptionsMenu();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        showDrawer = true;
        showToolBarUpOnLy = false;
        super.onCreate(savedInstanceState);
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction() == null)
                    return;
                if (intent.getAction().equals(REGISTRATION_COMPLETE)) {
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
                    registerViewModel.updateImei(getToken(), Utils.getImei(), Repository.getUserInfo());
                } else {
                    String message = intent.getStringExtra("message");
                    String message2 = intent.getStringExtra("logOutMsg");
                    if (message2 != null) {
                        if (!message2.equals("")) {
                            exitApp(true);

                        }
                    } else {
                        if (message != null) {
                            EditInformationDialog dialogFragment = new EditInformationDialog();
                            dialogFragment.show(getSupportFragmentManager(), "CompleteYourDataDialog");
                        }
                    }
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(REGISTRATION_COMPLETE);
        try {
            registerReceiver(mRegistrationBroadcastReceiver, intentFilter);
        } catch (Exception ignored) {
        }
        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
        view = putContentView(R.layout.activity_home);
    }

    @Override
    public void onNavigationItemSelected(String item) {
        if (My_Orders.TAG.equalsIgnoreCase(item)) {
            if (RepositorySidalitac.getUserId() == 0) {
                activity.showMessageDialog(R.string.store_blocked);
                return;
            }
        }
        super.onNavigationItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(mRegistrationBroadcastReceiver);
        } catch (Exception e) {

        }
    }

    public void setUserData(UserModel userModel) {
        if (userModel != null) {
            userName.setText(userModel.getName());
            username_.setText(userModel.getName());
            email.setText(userModel.getEmail());
            trial.setText(userModel.getAuctionName());
            if (!getToken().equals(userModel.getToken())) {
                registerViewModel.updateToken(getToken(), userModel);
            }
            try {
                if (TextUtils.isEmpty(userModel.getImage()))
                    return;
                byte[] bvytessf = Base64.decode(userModel.getImage(), 1);
                Bitmap bmp3 = BitmapFactory.decodeByteArray(bvytessf, 0, bvytessf.length);
                profileImageView.setImageBitmap(bmp3);
            } catch (Exception ignored) {

            }
        }
    }

    public void setUserImage(String image) {
        if (TextUtils.isEmpty(image))
            return;
        try {
            byte[] bvytessf = Base64.decode(image, 1);
            Bitmap bmp3 = BitmapFactory.decodeByteArray(bvytessf, 0, bvytessf.length);
            profileImageView.setImageBitmap(bmp3);
        } catch (Exception ignored) {

        }
    }

    public String getToken() {
        return Methods.getToken();
    }

    public void initViewModels() {

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getToast().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
            }
        });
        userViewModel.getMessage().observe(this, message -> {
            if (message != null && message.equals(FORCE_LOGOUT)) {
                activity.hideDialog();
                activity.messageDialog = MessageDialog.createMessageDialog("جار الخروج من البرنامج",
                        "لقد تم تسجيل الدخول من جهاز اخر يرجى اعادة تسجيل الدخول.",
                        v -> activity.exitApp(true)).setExitOnDismiss(true);
                activity.messageDialog.show(activity.getSupportFragmentManager(), FORCE_LOGOUT);
            } else activity.showMessageDialog(message);
        });
        userViewModel.getProgress().observe(this, s -> {
            if (!TextUtils.isEmpty(s))
                activity.showProgressBar(s);
            else activity.hideDialog();
        });
        userViewModel.getUserModel().observe(this, userModel1 -> {
            setUserData(userModel1);
            int serverVersionCode = userModel1.getCodevx();
            boolean isUpdateReq = userModel1.isCodereq();
            long verCode = 0;
            Log.e("OkHttp", "verCode");
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    verCode = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0).getLongVersionCode();
                } else
                    verCode = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0).versionCode;
                if (verCode < serverVersionCode && isUpdateReq) {
                    showMessageDialog("تنبيه", "برجاء تحديث التطبيق", "ألأن", "لاحقا", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String appPackageName = getPackageName();
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                            } catch (ActivityNotFoundException anfe) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                            }
                            finishAffinity();
                        }
                    }, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finishAffinity();
                        }
                    });
                }else if(shouldUpdateImei(userModel1.getImei())){
                    showMessageDialog("تاكيد الهاتف", "تاكيد تسجيل هذا الهاتف والغاء اى هاتف اخر ربما تكون مسجل عليه؟", "نعم", "لا", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            registerViewModel.updateImei(getToken(), Utils.getImei(),
                                    Repository.getUserInfo());
                            userViewModel.getUserStoreData(userModel1);
                        }
                    }, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            exitApp(true);
                        }
                    });
                }
                else userViewModel.getUserStoreData(userModel1);
            } catch (Exception e) {
                e.printStackTrace();
                userViewModel.getUserStoreData(userModel1);
            }

        });
        userViewModel.getUserStoreModel().observe(this, userModel2 -> {
            activity.hideDialog();
            if (userModel2 == null)
                return;
            if ("2".equalsIgnoreCase(userModel2.getSuccess())) {
                regiserUser();
                return;
            } else {
                userViewModel.getAllHotLiens();
            }
        });
        userViewModel.getUserNotifications().observe(this, notificationModelList -> {
            int unRead = Utils.getUnReadNotificatonCount(notificationModelList);
            activity.setCountNotification(unRead);
//                        if (notificationModelList != null&&notificationModelList.size()>0) {
//                            for(NotificationModel notificationModel:notificationModelList){
//
//                                if(notificationModel!= null);
//                            }
//                        }
        });
        userViewModel.getHotLines().observe(this, hotLines -> {
            if (!TextUtils.isEmpty(hotLines.get(0).getMobile()))
                supportPhone.setText(hotLines.get(0).getMobile());
        });
        userViewModel.getProfileImage().observe(this, new Observer<ProfileImageModel>() {
            @Override
            public void onChanged(ProfileImageModel profileImageModel) {
                setUserImage(profileImageModel.getImage());
                userViewModel.getUserData( false);
            }
        });
    }

    public static boolean shouldUpdateImei(String imei) {
        if (imei == null)
            imei = "";
        Log.e("shouldUpdateImei","old : "+imei);
        Log.e("shouldUpdateImei","new : "+Utils.getImei());
        return !imei.equalsIgnoreCase(Utils.getImei());
    }

    @Override
    public void initViews() {
        super.initViews();
        initViewModels();
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_add_cart_requests));
        navigationView.setBackground(getResources().getDrawable(R.drawable.ic_nav_menu_requests));
//        View v = findViewById(R.id.toolbar_logo);
//        if (v!=null)
//            v.setVisibility(View.GONE);
//        if (getSupportActionBar()!=null)
//            getSupportActionBar().setTitle("");

        doStartUpTasks();
        setUserData(Repository.getUserInfo());
    }

    @Override
    public void exitApp(boolean shouldEraseAndLogin) {
        //activity.messageDialog.dismiss();
        Utils.clearSharedPrefrences(Constants.KEY_USER);
        Utils.clearSharedPrefrences(Constants.KEY_USER);
        Bundle args = new Bundle();
        args.putString(DATA, FORCE_LOGOUT);
        //startActivity(Splash.class,args);
        Intent intent = new Intent(this, Splash.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        finishAffinity();
        startActivity(intent);
    }

    @Override
    public void onCurrentFragmentChanged() {
        super.onCurrentFragmentChanged();
    }


    @OnClick(R.id.requests)
    public void showRequests() {
        if (trial == findViewById(R.id.textView5)) {
            showTrial();
            return;
        }
        if (RepositorySidalitac.getUserInfo() == null
                || RepositorySidalitac.getUserInfo().getData() == null
                || RepositorySidalitac.getUserInfo().getData().size() == 0
                || RepositorySidalitac.getUserInfo().getData().get(0).getIsActive() == 0) {
            activity.showMessageDialog(R.string.store_blocked);
            return;
        }
        Bundle args = new Bundle();
        args.putString(DATA, Requests.TAG);
        activity.startActivity(HomeActivity.class, args);
    }


    @OnClick(R.id.store)
    public void showStore() {
        if (RepositorySidalitac.getUserInfo() == null
                || RepositorySidalitac.getUserInfo().getData() == null
                || RepositorySidalitac.getUserInfo().getData().size() == 0
                || RepositorySidalitac.getUserInfo().getData().get(0).getIsActive() == 0) {
            activity.showMessageDialog(R.string.store_blocked);
            return;
        }
        super.showStore();
    }

    @OnClick(R.id.supportemail)
    public void sendEmail() {
        Intent send = new Intent(Intent.ACTION_SENDTO);
        String uriText = "mailto:" + Uri.encode("support@elkdeals.com") +
                "?subject=" + Uri.encode("subject") +
                "&body=" + Uri.encode("message");
        Uri uri = Uri.parse(uriText);

        send.setData(uri);
        startActivity(Intent.createChooser(send, "Send mail..."));
    }

    @OnClick(R.id.supportphonenumber_container)
    public void callHotLine() {
        try {


            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + supportPhone.getText().toString()));
                startActivity(intent);
            } else
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);

        } catch (Exception e) {

        }
    }


    @OnClick(R.id.auctions)
    public void showAuctions() {
        Bundle args = new Bundle();
        args.putString(DATA, AuctionsCategories.TAG);
        activity.startActivity(AuctionsActivity.class, args);
    }

    @OnClick({R.id.trial, R.id.trial_text})
    public void showTrial() {
        if (Repository.getUserInfo().getAuctionName() != null) {
            if (!Repository.getUserInfo().getAuctionName().equals("")) {
                Intent intent = new Intent(activity, ResultsActivitty.class);


                intent.putExtra(Constants.KEY_POST_ID, Repository.getUserInfo().getAuctionId() + "");
                startActivity(intent);

            } else activity.showToast("لا يوجد مزاد لديك");
        } else activity.showToast("لا يوجد مزاد لديك");
//        Bundle args = new Bundle();
//        args.putString(DATA, categories.TAG);
//        activity.startActivity(MainActivity.class, args);
    }

    private void doStartUpTasks() {
        userViewModel.getUserProfileImage();
        userViewModel.getUserNotifications(false);
    }

    @Override
    public void OnConnectionStatusChanged(boolean networkstatus) {
        super.OnConnectionStatusChanged(networkstatus);
        doStartUpTasks();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PHONE_CALL) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + supportPhone.getText().toString()));

                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    startActivity(intent);
                }
            }
        }
    }

    private void regiserUser() {
        UserModel userModel = Repository.getUserInfo();
        if (userModel == null) {
            activity.showMessageDialog("Error,Reading elk user info!");
            return;
        }
        userViewModel.registerStoreAccount(userModel.getName(), userModel.getEmail(),
                Utils.getRandomNonce(10), userModel.getMobile());
    }
}