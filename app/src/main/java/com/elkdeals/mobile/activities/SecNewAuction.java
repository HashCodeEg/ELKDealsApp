package com.elkdeals.mobile.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.TextView;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.Constants;
import com.elkdeals.mobile.adapters.MenuAdapter;
import com.elkdeals.mobile.api.models.UserModel;
import com.elkdeals.mobile.custom.ProfileImageView;
import com.elkdeals.mobile.model.Repository;
import com.elkdeals.mobile.ui.auctions.SecNewAucFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SecNewAuction extends BaseActivity {


    Bundle extras;
    private String auctionID;



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
        navigationView.setBackground(getResources().getDrawable(R.drawable.ic_nav_menu_requests));
        UserModel userModel = Repository.getUserInfo();
        userName.setText(userModel.getName());
        email.setText(userModel.getEmail());
        String image = userModel.getImage();
        if(TextUtils.isEmpty(image))
            return;
        try{
            byte[] bvytessf = Base64.decode(image, 1);
            Bitmap bmp3 = BitmapFactory.decodeByteArray(bvytessf, 0, bvytessf.length);
            profileImageView.setImageBitmap(bmp3);
        }catch (Exception ignored){

        }
    }
    public SecNewAuction() {
    }
    @Override
    public ArrayList<MenuAdapter.MenuItem> getItems() {
        return getAuctionsMenuItems();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        putContentView(R.layout.activity_sec_new_auction);
        ButterKnife.bind(this);

        extras = getIntent().getExtras();

        if (extras.getString(Constants.KEY_POST_ID) != null) {
            auctionID = extras.getString(Constants.KEY_POST_ID);

        }
        startSecNewWayFragment(auctionID);
    }

    private void startSecNewWayFragment(String auctionID) {
        activity.addPage(new SecNewAucFragment(auctionID));
    }

    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();


        }

    }

    @Override
    public void exitApp(boolean shouldEraseAndLogin) {

    }
}
