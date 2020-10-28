package com.elkdeals.mobile.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.Constants;
import com.elkdeals.mobile.Utils.Utils;
import com.elkdeals.mobile.adapters.MenuAdapter;

import java.util.ArrayList;


public class ImageActivity extends BaseActivity {
    String img;
     ImageView imageView;
    public ImageActivity() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString(Constants.StringExtra,img);

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.StringExtra,img);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_Launcher);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        String img = "";
        if (savedInstanceState != null)
            img = savedInstanceState.getString(Constants.StringExtra);
        if (getIntent()!=null&&TextUtils.isEmpty(img))
            img = getIntent().getStringExtra(Constants.StringExtra);
        if (!TextUtils.isEmpty(img))
            this.img = img;
        setContentView(R.layout.activity_image);
        imageView = findViewById(R.id.image);
        initViews();
    }
    @Override
    public ArrayList<MenuAdapter.MenuItem> getItems() {
        return getAuctionsMenuItems();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void initViews() {
        super.initViews();
        Utils.loadImage(imageView,findViewById(R.id.progressBar),"",img,false);
    }

    @Override
    public void exitApp(boolean shouldEraseAndLogin) {

    }

    public static void showImage(Context context,String image){
        if (TextUtils.isEmpty(image))
            return;
        Intent intent = new Intent(context, ImageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.StringExtra,image);
        intent.putExtra(Constants.StringExtra,image);
        intent.putExtra(Constants.DATA,bundle);
        context.startActivity(intent);
    }
}
