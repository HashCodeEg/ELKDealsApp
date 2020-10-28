package com.elkdeals.mobile.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.Constants;
import com.elkdeals.mobile.adapters.BiddingListAdapter;
import com.elkdeals.mobile.adapters.MenuAdapter;
import com.elkdeals.mobile.api.models.BidderAuctionM;
import com.elkdeals.mobile.api.models.BiddingListM;
import com.elkdeals.mobile.api.models.UserModel;
import com.elkdeals.mobile.custom.ProfileImageView;
import com.elkdeals.mobile.fireBase.Config;
import com.elkdeals.mobile.model.Repository;
import com.elkdeals.mobile.recievers.NotesReciever;
import com.elkdeals.mobile.viewmodel.PresenterImpl;

import java.util.ArrayList;

import butterknife.BindView;

public class ResultsActivitty extends BaseActivity {
    @BindView(R.id.biddingList) ListView biddingList;
    @BindView(R.id.newTimer) TextView newTimer;
    @BindView(R.id.timeToStOrFinTextView) TextView timeToStOrFinTextView;
    @BindView(R.id.bidsNum) TextView bidsNum;
    @BindView(R.id.timerLine) LinearLayout timerLine;
    @BindView(R.id.bidAgainBtn) Button bidAgainBtn;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.infoTxt) TextView infoTxt;


    String auctionId;
    Bundle bundle;
    BroadcastReceiver mRegistrationBroadcastReceiver;
    PresenterImpl presenter;


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
        if(TextUtils.isEmpty(image))
            return;
        try{
            byte[] bvytessf = Base64.decode(image, 1);
            Bitmap bmp3 = BitmapFactory.decodeByteArray(bvytessf, 0, bvytessf.length);
            profileImageView.setImageBitmap(bmp3);
        }catch (Exception ignored){

        }
    }
    public ResultsActivitty() {
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        showDrawer = true;
        showToolBarUpOnLy = false;
        super.onCreate(savedInstanceState);
        putContentView(R.layout.activity_results_activitty);
        infoTxt.setOnClickListener(view -> {
            startActivity(new Intent(this, InfoImageSliderActiivity.class));
        });
        infoTxt.setVisibility(View.GONE);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getString(Constants.KEY_POST_ID) != null) {
                auctionId = bundle.getString("" + Constants.KEY_POST_ID);
                presenter = new PresenterImpl(this, auctionId);
                presenter.loadResults();
                fireBase();
            }

        }

    }

    private void fireBase() {
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received
                    String message = intent.getStringExtra(Constants.KEY_POST_ID);
                    String message2 = intent.getStringExtra("logOutMsg");

                    if (message2 != null) {
                        if (!message2.equals("")) {
                            //todo logout
                            finishAffinity();
                        }
                    } else {
                        if (message != null)
                            if (message.equals(auctionId))
                                presenter.loadResults();
                    }

                }
            }
        };
    }


     public void getResults(BiddingListM model) {
        BiddingListAdapter adapter = new BiddingListAdapter(
                model.getBiddersList(),
                this,
                R.layout.ask_qst_dailog, model.getAuctionM().getAuctionKind() + "");
        biddingList.setAdapter(adapter);
    }

     public void showProgress() {
//        progressBar.setVisibility(View.VISIBLE);
    }

     public void hideProgress() {
        //progressBar.setVisibility(View.GONE);
    }

     public void showEmpty() {
        Toast.makeText(this, "لا توجد نتائج اﻷن ! ", Toast.LENGTH_SHORT).show();
    }

     public void getLocalTimer(int dateType, int endDate) {
        presenter.localTimer(dateType, endDate);
    }

     public void auctionIsWorking(BidderAuctionM data) {
        if (data.getTimerMsg().equals(""))
            presenter.loadResults();
        timeToStOrFinTextView.setText(
                data.getTimerMsg()
        );
        newTimer.setVisibility(View.VISIBLE);
        bidAgainBtn.setVisibility(View.VISIBLE);

        if (data.isUserCanBid()) {
            bidAgainBtn.setOnClickListener(view -> {
                presenter.cancelTimer();
                Intent intent = new Intent(this, SecNewAuction.class);
                startActivity(intent
                        .putExtra(Constants.KEY_POST_ID, data.getId() + "")
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            });
        } else {
            bidAgainBtn.setOnClickListener(view -> {
                activity.showMessageDialog("عــفـــوا",data.getCanbidmsg()
                        );
            });
        }
        if (data.isHidebids())
            bidsNum.setVisibility(View.GONE);
        else
            bidsNum.setVisibility(View.VISIBLE);
    }

     public void auctionWillStart() {
        timeToStOrFinTextView.setText("سيبدا المزاد بعد");
        newTimer.setVisibility(View.VISIBLE);
    }

     public void auctionIsFinished() {
        presenter.cancelTimer();
        timeToStOrFinTextView.setVisibility(View.GONE);
        newTimer.setText(
                this.getResources().getString(R.string.adp_auction_ended)
        );
        newTimer.setTextSize(20);
    }

     public void setTextTimer(String txt) {
        runOnUiThread(() -> {
            newTimer.setText(txt);
        });

    }

     public void executeTask() {
        presenter.loadResults();
    }

     public void setDataInVeiws(BidderAuctionM data) {

        if (data.getDatetype() != null) {
            if (data.getDatetype().equals("4")) {
                newTimer.setText(data.getEnddate() + this.getResources().getString(R.string.days));
            }
            if (data.getDatetype().equals("3")) {
                newTimer.setText(data.getEnddate() + this.getResources().getString(R.string.hours));
            }
            if (data.getDatetype().equals("2")) {
                newTimer.setText(data.getEnddate() + this.getResources().getString(R.string.minutes));
            }
            if (data.getDatetype().equals("1")) {
                newTimer.setText(data.getEnddate() + this.getResources().getString(R.string.seconds));
            }
        }
        bidsNum.setText("المزايدات المتبقية لك   " + data.getBidsNumber() + "");
    }

     public void checkIfAuctionFinished(boolean isFinished) {
        presenter.cancelTimer();
        timeToStOrFinTextView.setVisibility(View.GONE);
        newTimer.setText(
                this.getResources().getString(R.string.adp_auction_ended)
        );
        newTimer.setTextSize(20);
        bidAgainBtn.setVisibility(View.GONE);
    }


    protected void onStop() {
        super.onStop();
        presenter.cancelTimer();
    }

    @Override
    public void exitApp(boolean shouldEraseAndLogin) {

    }


    protected void onPause() {
        super.onPause();
        //mainTimerToServer.cancel();
        presenter.cancelTimer();
        try {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        } catch (IllegalArgumentException e) {
        }
    }


    @Override
    public ArrayList<MenuAdapter.MenuItem> getItems() {
        return getAuctionsMenuItems();
    }

    protected void onDestroy() {
        super.onDestroy();
        // mainTimerToServer.cancel();
        presenter.cancelTimer();
    }



    protected void onResume() {

        super.onResume();
        NotesReciever.callNotifications(mRegistrationBroadcastReceiver, this);
        presenter.loadResults();
    }
}
