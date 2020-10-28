package com.elkdeals.mobile.ui.store;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.TextView;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProviders;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.Constants;
import com.elkdeals.mobile.Utils.Methods;
import com.elkdeals.mobile.Utils.Utils;
import com.elkdeals.mobile.activities.BaseActivity;
import com.elkdeals.mobile.adapters.MenuAdapter;
import com.elkdeals.mobile.api.models.UserModel;
import com.elkdeals.mobile.custom.ProfileImageView;
import com.elkdeals.mobile.dialogFragments.EditInformationDialog;
import com.elkdeals.mobile.fireBase.Config;
import com.elkdeals.mobile.model.Repository;
import com.elkdeals.mobile.ui.splash.Splash;
import com.elkdeals.mobile.viewmodel.RegisterViewModel;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

import butterknife.BindView;

import static com.elkdeals.mobile.Utils.Constants.DATA;
import static com.elkdeals.mobile.Utils.Constants.FORCE_LOGOUT;
import static com.elkdeals.mobile.fireBase.Config.REGISTRATION_COMPLETE;

public class StoreActivity extends BaseActivity {

     String fragmentToStart;
    ViewDataBinding view;
    @BindView(R.id.profile_image)
    ProfileImageView profileImageView;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.username)
    TextView userName;
    private RegisterViewModel registerViewModel;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private UserModel userModel;

    public StoreActivity() {
        auctionsCart = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        showDrawer = true;
        showToolBarUpOnLy = false;
        super.onCreate(savedInstanceState);
        if (getIntent() != null && getIntent().getBundleExtra(DATA) != null && getIntent().getBundleExtra(DATA).getString(DATA) != null)
            fragmentToStart = getIntent().getBundleExtra(DATA).getString(DATA);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction() == null)
                    return;
                if (intent.getAction().equals(REGISTRATION_COMPLETE)) {
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
                    registerViewModel.updateToken(getToken(),  userModel);
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
        view = putContentView(R.layout.activity_main);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(mRegistrationBroadcastReceiver);
        } catch (Exception e) {

        }
    }

    @Override
    public ArrayList<MenuAdapter.MenuItem> getItems() {
        return getStoreMenuItems();
    }
    public void setUserImage(String image){
        if(TextUtils.isEmpty(image))
            return;
        try{
            byte[] bvytessf = Base64.decode(image, 1);
            Bitmap bmp3 = BitmapFactory.decodeByteArray(bvytessf, 0, bvytessf.length);
            profileImageView.setImageBitmap(bmp3);
        }catch (Exception ignored){

        }
    }
    public String getToken() {
        return Methods.getToken();
    }

    @Override
    public void initViews() {
        super.initViews();
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_add_cart_store));
        navigationView.setBackground(getResources().getDrawable(R.drawable.ic_nav_menu_store));

        addPage(getFragment(fragmentToStart),new CategoriesStore());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Repository.getUserInfo()!=null)
            userModel = Repository.getUserInfo();
        if (userModel != null) {
            setUserImage(userModel.getImage());
            userName.setText(userModel.getName());
            email.setText(userModel.getEmail());
            if (!getToken().equals(userModel.getToken())) {
                registerViewModel.updateToken(getToken(),   userModel);
            }
        }
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

}