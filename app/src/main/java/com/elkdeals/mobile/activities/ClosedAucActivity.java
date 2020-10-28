package com.elkdeals.mobile.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.Constants;
import com.elkdeals.mobile.Utils.Methods;
import com.elkdeals.mobile.Utils.Utils;
import com.elkdeals.mobile.adapters.MenuAdapter;
import com.elkdeals.mobile.api.API;
import com.elkdeals.mobile.api.Services;
import com.elkdeals.mobile.api.models.CurrentAucClosedM;
import com.elkdeals.mobile.api.models.CurrentAucSerModel;
import com.elkdeals.mobile.api.models.SendPriceModel;
import com.elkdeals.mobile.api.models.UserModel;
import com.elkdeals.mobile.custom.ProfileImageView;
import com.elkdeals.mobile.dialogFragments.RateAuctiontDialog;
import com.elkdeals.mobile.model.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClosedAucActivity extends BaseActivity {
    @BindView(R.id.productImage) ImageView productImage;
    @BindView(R.id.prodName) TextView prodName;
    @BindView(R.id.newTimer) TextView newTimer;
    @BindView(R.id.timeToStOrFinTextView) TextView timeToStOrFinTextView;
    @BindView(R.id.timerLine) LinearLayout timerLine;
    @BindView(R.id.daysOrPriceTxt) TextView daysOrPriceTxt;
    @BindView(R.id.aucPriceEdx) EditText aucPriceEdx;
    @BindView(R.id.bidBtn) Button bidBtn;
    @BindView(R.id.resultsBtn) Button resultsBtn;
    @BindView(R.id.msgTxt) TextView msgTxt;
    @BindView(R.id.rateUs) Button rateUs;

    Response<List<CurrentAucClosedM>> response0;  // to get data from service

    private String auctionID, cid;
    String dateType = "";                               // to get date type 1,2,3,4
    int[] endDateForLocalTimer = {0};                   // to get end date for localTimer
    int endDate = 0;                                    // not used just for test
    Timer localTimer = new Timer();
    Bundle extras;
    CurrentAucSerModel aucSerModel;
    String BASE_URL;
    String BASE_URL_SEND;

    public ClosedAucActivity() {
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
    @Override
    public ArrayList<MenuAdapter.MenuItem> getItems() {
        return getAuctionsMenuItems();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        putContentView(R.layout.activity_closed_auc);
        ButterKnife.bind(this);

        Methods.setAppFont();
        getBaseUrl();
        cid = Repository.getUserInfo().getId();

        extras = getIntent().getExtras();
        if (extras.getString(Constants.KEY_POST_ID) != null) {
            auctionID = extras.getString(Constants.KEY_POST_ID);
            //   dateType = extras.getString("dateType");
            // endDate = new Integer(extras.getString("endDate"));
            Log.d("DDDDdd", dateType);
            Log.d("DDDDddvv", "" + endDate);
            getCurrentAuction(auctionID, cid);
        } else {
            aucSerModel = (CurrentAucSerModel) extras.getSerializable("AUCOBJ");
            auctionID = aucSerModel != null ? aucSerModel.getId() : null;
            dateType = aucSerModel.getDatetype();
            endDate = aucSerModel.getEnddate2();
            getCurrentAuction(auctionID, cid);

        }
        rateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment = new RateAuctiontDialog(auctionID, cid);
                dialogFragment.show(getSupportFragmentManager(), "RateAuctiontDialog");
            }
        });
    }


    private void getCurrentAuction(String aucId, String cid) {
        Services api = API.getAPIS();
        Call<List<CurrentAucClosedM>> listCall = api.getClosedAucDetails(aucId, cid);
        listCall.enqueue(new Callback<List<CurrentAucClosedM>>() {
            @Override
            public void onResponse(Call<List<CurrentAucClosedM>> call, Response<List<CurrentAucClosedM>> response) {
                if (!response.isSuccessful()) {
                    executeTask();
                }
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().size() != 0) {

                            response0 = response;
                            for (int i = 0; i < response.body().size(); i++) {
                                CurrentAucClosedM res = response.body().get(i);
                                Log.d("KKKK", endDate + "");
                                localTimer.cancel();

                                callLocalTimer(Integer.valueOf(res.getDatetype()), res.getEnddate());

                                endDate = res.getEnddate();
                                dateType = res.getDatetype();
                                auctionID = res.getId() + "";

                                timeToStOrFinTextView.setVisibility(View.VISIBLE);
                                bidBtn.setEnabled(true);

                                // Auction Still Working
                                if (res.isStarted() && !res.isFinished()) {
                                    // refreshDataBtn.setVisibility(View.VISIBLE);
                                    timeToStOrFinTextView.setText(
                                            "سينتهى المزاد المغلق بعد"
                                    );
                                    newTimer.setVisibility(View.VISIBLE);
                                    // Auction Maybe Finshed So Request Server Again If Finished
                                    if (res.getEnddate() == 0) {
                                        checkIfAucFinished(i);  // To Check If Auction Finished ??
                                        int ddd = 3;
                                        ddd = Methods.getIntegerFromSharedPref("TRUEFALSE", ClosedAucActivity.this);
                                        // Auction Finished
                                        if (ddd == 1) {
                                            newTimer.setTextSize(20);
                                            //mainTimerToServer.cancel();
                                            localTimer.cancel();
                                        } else if (ddd == 0) {
                                            executeTask();   // Not Finished
                                        }
                                    }
                                    aucPriceEdx.setHint("");
                                    aucPriceEdx.setEnabled(true);
                                    resultsBtn.setVisibility(View.VISIBLE);
                                    bidBtn.setVisibility(View.VISIBLE);
                                }

                                // Auction Not Started Yet
                                if (!res.isStarted()) {
                                    // resultsBtn.setVisibility(View.GONE);
                                    timeToStOrFinTextView.setText("سيبدا المزاد بعد");
                                    newTimer.setVisibility(View.VISIBLE);
                                    aucPriceEdx.setEnabled(false);
                                    aucPriceEdx.setHint("المزاد لم يبدا");
                                    bidBtn.setEnabled(false);
                                    // resultsBtn.setVisibility(View.GONE);
                                }
                                setViewData(i);


                                if (res.isFinished() && res.isStarted()) {
                                    setEnabeldFalse();

                                    if (res.getAuctionkind() == 2) {
                                        daysOrPriceTxt.setText("عدد الايام ");
                                        //   aucPriceEdx.setText(res.getCurrent() + "");
                                    } else {
                                        daysOrPriceTxt.setText("السعر ");
                                        // aucPriceEdx.setText(res.getCurrent() + "");
                                    }

                                    localTimer.cancel();
                                    timeToStOrFinTextView.setVisibility(View.GONE);
                                    newTimer.setText(
                                            "انتهى وقت المزاد المغلق"
                                    );
                                    newTimer.setTextSize(20);

                                    startActivity(new Intent(ClosedAucActivity.this, BiddingListActivity.class)
                                            .putExtra(Constants.KEY_POST_ID, auctionID)
                                            .putExtra("CURRENT", response0.body().get(i).getCurrent() + "")
                                            .putExtra("KIND", response0.body().get(i).getAuctionkind() + "").
                                                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                    finish();
                                    return;
                                }

                                btnActions(i);
                                if (res.isHideBid()) {
                                    aucPriceEdx.setEnabled(false);
                                    bidBtn.setEnabled(false);
                                    bidBtn.setVisibility(View.GONE);
                                }

                            }
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<List<CurrentAucClosedM>> call, Throwable t) {
                executeTask();
            }
        });
    }

    private void setViewData(int i) {

        prodName.setText(response0.body().get(i).getName());
        msgTxt.setText(response0.body().get(i).getMsg());
        Utils.loadImage(productImage,"http://elkdeals.com/admin/uploads/",response0.body().get(i).getImage());
        if (response0.body().get(i).getAuctionkind() == 2)
            daysOrPriceTxt.setText("عدد الايام ");
        else
            daysOrPriceTxt.setText("السعر ");


        //if (response0.body().get(i).isShowtimer()) {
        if (response0.body().get(i).getDatetype() != null) {
            if (response0.body().get(i).getDatetype().equals("4")) {
                newTimer.setText(response0.body().get(i).getEnddate() + ClosedAucActivity.this.getResources().getString(R.string.days));
            }
            if (response0.body().get(i).getDatetype().equals("3")) {
                newTimer.setText(response0.body().get(i).getEnddate() + ClosedAucActivity.this.getResources().getString(R.string.hours));
            }
            if (response0.body().get(i).getDatetype().equals("2")) {
                newTimer.setText(response0.body().get(i).getEnddate() + ClosedAucActivity.this.getResources().getString(R.string.minutes));
            }
            if (response0.body().get(i).getDatetype().equals("1")) {
                newTimer.setText(response0.body().get(i).getEnddate() + ClosedAucActivity.this.getResources().getString(R.string.seconds));
            }
        }
        // } else {
        //newTimer.setVisibility(View.GONE);
        //   }

    }

    private void btnActions(final int i) {
        bidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateEdx()) {
                    currentValue = aucPriceEdx.getText().toString();
                    int price = new Integer(currentValue);
                    int nPrice = new Integer(response0.body().get(i).getMaxBid());
                    if (price <= nPrice)
                        sendPrice(auctionID, cid, price + "", response0.body().get(i).getAuctionkind() + "");
                    else aucPriceEdx.setError("اضف قيمة اقل");
                }

            }
        });
        resultsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ClosedAucActivity.this, BiddingListActivity.class)
                        .putExtra(Constants.KEY_POST_ID, auctionID)
                        .putExtra("CURRENT", response0.body().get(i).getCurrent() + "")
                        .putExtra("KIND", response0.body().get(i).getAuctionkind() + "").
                                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

            }
        });
    }

    String currentValue = "";

    private boolean validateEdx() {

        currentValue = aucPriceEdx.getText().toString();


        return Methods.checkEditTextEmpty(currentValue, aucPriceEdx);
    }

    public void sendPrice(final String postID, final String userID, final String val, final String auctionKind) {

        BaseActivity.instance.showProgressBar();
        String url  = "http://"+BASE_URL_SEND+"/elk/";
        Services api = API.getAPIS(url);
        Call<SendPriceModel> call = api.sendYourPriceClosedAuc(postID, userID, val);
        call.enqueue(new Callback<SendPriceModel>() {
            @Override
            public void onResponse(Call<SendPriceModel> call, Response<SendPriceModel> response) {
                BaseActivity.instance.hideDialog();

                if (response.isSuccessful()) {
                    //  Methods.saveIntegerSharedPref("MinValue", new Integer(val), ClosedAucActivity.this);
                    addMinBid(cid, auctionID, val);
                    Toast.makeText(ClosedAucActivity.this, "تم الارسال بنجاح", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ClosedAucActivity.this, BiddingListActivity.class)
                            .putExtra(Constants.KEY_POST_ID + "", auctionID + "")
                            .putExtra("KIND", auctionKind + "")
                            .putExtra("CURRENT", val + "").addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    );
                    /*finish();
                    return;*/
                    /*bidBtn.setEnabled(false);
                    aucPriceEdx.setEnabled(false)*/

                }
            }

            @Override
            public void onFailure(Call<SendPriceModel> call, Throwable t) {
                BaseActivity.instance.hideDialog();
            }
        });
    }

    private void addMinBid(String cid, String auctionID, String value) {
        Utils.saveSharedPrefrences(Constants.TABLE_Minimum_Bid+auctionID,value);
    }


    public void checkIfAucFinished(int i) {
        executeTask();
        if (response0.body().get(i).isFinished()) {
            Methods.saveIntegerSharedPref("TRUEFALSE", 1, ClosedAucActivity.this);
            localTimer.cancel();
            // mainTimerToServer.cancel();
        } else {
            Methods.saveIntegerSharedPref("TRUEFALSE", 0, ClosedAucActivity.this);
        }
    }

    private void callLocalTimer(int dateType1, int endDate1) {

        endDateForLocalTimer[0] = endDate1;
        localTimer = new Timer();

        switch (dateType1) {
            case 1:
                localTimer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new TimerTask() {
                            @Override
                            public void run() {
                                if (endDateForLocalTimer[0] != 0) {
                                    endDateForLocalTimer[0]--;
                                    newTimer.setText(endDateForLocalTimer[0] + ClosedAucActivity.this.getResources().getString(R.string.seconds));
                                }
                                if (endDateForLocalTimer[0] == 0) {
                                    executeTask();
                                }
                            }
                        });
                    }
                }, 1000, 1000);
                break;
            case 2:
                localTimer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new TimerTask() {
                            @Override
                            public void run() {
                                if (endDateForLocalTimer[0] != 0 && endDateForLocalTimer[0] != 3) {
                                    endDateForLocalTimer[0]--;
                                    newTimer.setText(endDateForLocalTimer[0] + ClosedAucActivity.this.getResources().getString(R.string.minutes));
                                }
                                if (endDateForLocalTimer[0] == 3) {
                                    executeTask();
                                }
                            }
                        });
                    }
                }, 60000, 60000);
                break;
            case 3:
                localTimer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new TimerTask() {
                            @Override
                            public void run() {
                                if (endDateForLocalTimer[0] != 0) {
                                    endDateForLocalTimer[0]--;
                                    newTimer.setText(endDateForLocalTimer[0] + ClosedAucActivity.this.getResources().getString(R.string.hours));
                                }
                                if (endDateForLocalTimer[0] == 3) {
                                    executeTask();
                                }
                            }
                        });
                    }
                }, 3600000, 3600000);
                break;
        }
    }

    private void executeTask() {
        getCurrentAuction(auctionID, cid);
    }

    private void setEnabeldFalse() {
        bidBtn.setEnabled(false);
        aucPriceEdx.setEnabled(false);
        bidBtn.setVisibility(View.GONE);
        // daysOrPriceTxt.setVisibility(View.GONE);
    }
    private void getBaseUrl() {
        BASE_URL = Repository.getUserInfo().getReceivingResultsLink();
        BASE_URL_SEND = Repository.getUserInfo().getSendingBidLink();
    }
    @Override
    protected void onStop() {
        super.onStop();
        // mainTimerToServer.cancel();
        localTimer.cancel();
    }

    @Override
    public void exitApp(boolean shouldEraseAndLogin) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        //mainTimerToServer.cancel();
        localTimer.cancel();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        executeTask();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // mainTimerToServer.cancel();
        localTimer.cancel();
    }
}
