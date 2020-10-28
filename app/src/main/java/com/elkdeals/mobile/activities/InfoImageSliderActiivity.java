package com.elkdeals.mobile.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.adapters.MenuAdapter;
import com.elkdeals.mobile.adapters.SliderAdapterRecycler;
import com.elkdeals.mobile.api.API;
import com.elkdeals.mobile.api.Services;
import com.elkdeals.mobile.api.models.SliderM;
import com.elkdeals.mobile.api.models.UserModel;
import com.elkdeals.mobile.custom.ProfileImageView;
import com.elkdeals.mobile.model.Repository;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.elkdeals.mobile.activities.ImageActivity.showImage;

public class InfoImageSliderActiivity extends BaseActivity {
    @BindView(R.id.imageSliderList)
    RecyclerView imageSliderList;
    SliderAdapterRecycler adapterRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        putContentView(R.layout.activity_info_image_slider_actiivity);
        ButterKnife.bind(this);
        getSlider();
    }

    @BindView(R.id.profile_image)
    ProfileImageView profileImageView;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.username)
    TextView userName;
    @Override
    public void initViews() {
        super.initViews();
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_add_cart_requests));
        navigationView.setBackground(getResources().getDrawable(R.drawable.ic_nav_menu_requests));        UserModel userModel = Repository.getUserInfo();
        userName.setText(userModel.getName());
        email.setText(userModel.getEmail());
        String image = userModel.getImage();
        imageSliderList.setAdapter(adapterRecycler = new SliderAdapterRecycler(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImage(InfoImageSliderActiivity.this,""+v.getTag());
            }
        }));
        imageSliderList.setLayoutManager(new LinearLayoutManager(this));
        if(TextUtils.isEmpty(image))
            return;
        try{
            byte[] bvytessf = Base64.decode(image, 1);
            Bitmap bmp3 = BitmapFactory.decodeByteArray(bvytessf, 0, bvytessf.length);
            profileImageView.setImageBitmap(bmp3);
        }catch (Exception ignored){

        }
    }
    public InfoImageSliderActiivity() {
    }
    @Override
    public void exitApp(boolean shouldEraseAndLogin) {

    }
    @Override
    public ArrayList<MenuAdapter.MenuItem> getItems() {
        return getDefaultMenuItems();
    }



    private void getSlider() {
        Services api = API.getAPIS();
        Call<List<SliderM>> call = api.getSlider();
        call.enqueue(new Callback<List<SliderM>>() {
            @Override public void onResponse(Call<List<SliderM>> call, Response<List<SliderM>> response) {
                if (!response.isSuccessful()) {
                } else if (response.isSuccessful()) {
                    if (response.body().size()==0)
                        Toast.makeText(InfoImageSliderActiivity.this, "عفوا", Toast.LENGTH_SHORT).show();
                    runOnUiThread(()->adapterRecycler.setList( response.body()));
                }
            }

            @Override public void onFailure(Call<List<SliderM>> call, Throwable t) {

            }
        });

    }
}
