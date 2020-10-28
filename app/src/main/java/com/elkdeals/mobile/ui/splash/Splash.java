package com.elkdeals.mobile.ui.splash;

import android.animation.Animator;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.activities.BaseActivity;
import com.elkdeals.mobile.adapters.MenuAdapter;
import com.elkdeals.mobile.api.API;
import com.elkdeals.mobile.api.models.SplashVideo;
import com.elkdeals.mobile.model.Repository;
import com.elkdeals.mobile.ui.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Splash extends BaseActivity implements Runnable {
    @BindView(R.id.splash_image)
    ImageView splashImage;
    @BindView(R.id.fragment_container)
    FrameLayout frameLayout;
    BaseFragment chooser, login, register, confirm;
    private boolean isLogin;
    private String videoLink;

    public Splash() {
    }

    @Override
    public ArrayList<MenuAdapter.MenuItem> getItems() {
        return getAuctionsMenuItems();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        showDrawer = false;
        showToolBarUpOnLy = true;
        setTheme(R.style.AppTheme_Launcher);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                findViewById(R.id.appbar).setOutlineProvider(null);
            }
        } catch (Exception ignored) {
        }

        if(Repository.getUserInfo()!=null)
            isLogin=true;
        initViews();
    }
    @Override
    protected void onStart() {
        super.onStart();
        if(isLogin)
            return;
        //addPage(chooser);
        addPage(login);
    }


    private void getSplashVideo() {
         Call<SplashVideo> call = API.getAPIS().getSplashVideo();
        call.enqueue(new Callback<SplashVideo>() {
            @Override
            public void onResponse(Call<SplashVideo> call, Response<SplashVideo> response) {
                videoLink = "";
                if (!response.isSuccessful()) {

                } else if (response.isSuccessful()) {
                    videoLink = response.body().getVideo();
                }
            }

            @Override
            public void onFailure(Call<SplashVideo> call, Throwable t) {
                videoLink = "";
            }
        });
    }

    @Override
    public void run() {
        splashImage.animate()
                .alpha(0f)
                .setDuration(1500)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        if(isLogin)
                        {
                            home();
                            return;
                        }
                        splashImage.setVisibility(View.GONE);
                        frameLayout.setAlpha(0f);
                        frameLayout.setVisibility(View.VISIBLE);
                        frameLayout.animate().alpha(1f).setDuration(500);
                        playYoutubeVideo(videoLink,true);

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
    }
    public void login() {
        addPage(login);

    }
    public void register() {
        addPage(register);
    }
    public void confirmPhone() {
        addPage(confirm);
    }
    @Override
    public void initViews() {
        super.initViews();
        getSplashVideo();
        initToolBar();
        new Handler().postDelayed(this, 2500);
        chooser = new LoginChooser();
        login = new Login();
        register = new Register();
        confirm = new PhoneConfirmCode();
    }

    @Override
    public Toolbar initToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return toolbar;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            onBackPressed();
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void exitApp(boolean shouldEraseAndLogin) {
        finishAffinity();
    }
}
