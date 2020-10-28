package com.elkdeals.mobile.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.Constants;
import com.elkdeals.mobile.Utils.Methods;
import com.elkdeals.mobile.Utils.Utils;
import com.elkdeals.mobile.adapters.BiddingListAdapter;
import com.elkdeals.mobile.adapters.MenuAdapter;
import com.elkdeals.mobile.api.API;
import com.elkdeals.mobile.api.Services;
import com.elkdeals.mobile.api.models.BidderAuctionM;
import com.elkdeals.mobile.api.models.BidderM;
import com.elkdeals.mobile.api.models.BiddingListM;
import com.elkdeals.mobile.api.models.SendPriceModel;
import com.elkdeals.mobile.api.models.TrueMsg;
import com.elkdeals.mobile.api.models.UserModel;
import com.elkdeals.mobile.custom.ProfileImageView;
import com.elkdeals.mobile.dialogFragments.BiddingDialog;
import com.elkdeals.mobile.dialogFragments.RateAuctiontDialog;
import com.elkdeals.mobile.fireBase.Config;
import com.elkdeals.mobile.model.Repository;
import com.elkdeals.mobile.recievers.MyBroadcastReceiver;
import com.elkdeals.mobile.recievers.NotesReciever;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BiddingListActivity extends BaseActivity {
    @BindView(R.id.newTimer) TextView newTimer;
    @BindView(R.id.timeToStOrFinTextView) TextView timeToStOrFinTextView;
    @BindView(R.id.timerLine) LinearLayout timerLine;
    @BindView(R.id.biddingList) ListView biddingList;
    @BindView(R.id.noBtn) Button noBtn;
    @BindView(R.id.yesBtn) Button yesBtn;
    @BindView(R.id.yesNoLine) LinearLayout yesNoLine;
    @BindView(R.id.plusBtn) Button plusBtn;
    @BindView(R.id.valueEdx) EditText valueEdx;
    @BindView(R.id.minusBtn) Button minusBtn;
    @BindView(R.id.addPriceBtn) Button addPriceBtn;
    @BindView(R.id.minMaxLine) LinearLayout minMaxLine;
    @BindView(R.id.msgTxt) TextView msgTxt;
    @BindView(R.id.bidsNum) TextView bidsNum;
    @BindView(R.id.rateUs) Button rateUs;

    private String auctionID, cid;
    String auctionKind;                               // to get date type 1,2,3,4
    int[] endDateForLocalTimer = {0};                   // to get end date for localTimer
    Timer localTimer = new Timer();
    Bundle extras;
    Context context;
    MyBroadcastReceiver myBroadcastReceiver;
    BroadcastReceiver mRegistrationBroadcastReceiver;
    int waitTime = 0;
    int tempwaitTime = 0;
    Date lastBidDate;
    long diffBidTime = 0;
    boolean click = false;
    boolean clickA = false;
    String BASE_URL;
    String BASE_URL_SEND;

    public BiddingListActivity() {
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        putContentView(R.layout.activity_bidding_list);
        ButterKnife.bind(this);
        getBaseUrl();
        context = BiddingListActivity.this;
        cid = Repository.getUserInfo().getId();
        extras = getIntent().getExtras();
        auctionID = extras.getString(Constants.KEY_POST_ID);
        // current = extras.getString("CURRENT");
        auctionKind = extras.getString("KIND");
        fireBase();
        getBiddingList(auctionID);
        makeItFalse();
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

                            finishAffinity();
                        }
                    } else {
                        if (message != null)
                            if (message.equals(auctionID))
                                getBiddingListWithFireBase(message);
                    }

                }
            }
        };
    }

    String msg = "";
    String dayOrPrice = "";
    int current2;
    int val;
    int min = 0;
    int max;

    private void buttonsActions(final Response<BiddingListM> response) {
        final BiddingListM model = response.body();
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMessageDialog("تنبيه"
                        , "فى حالة التاكيد لن تتمكن من ارسال اى قيمة اخرى على هذا المزاد"
                        , new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sayNoForBidding(auctionID, cid, "1");

                            }
                        });
            }
        });
        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (model.getAuctionM().getCan() == 0) {
                    sayYesForBidding(
                            "" + auctionID,
                            cid + "",
                            model.getAuctionM().getCurrent() + "",
                            auctionKind + ""

                    );
                }

            }
        });


        if (auctionKind.equals("2")) {
            msg = "عدد الايام الذى سيتم ارساله ";
            dayOrPrice = " يوم ";
        } else {
            msg = "السعر الذى سيتم ارساله ";
            dayOrPrice = " جنيه ";
        }


        current2 = model.getAuctionM().getCurrent();
        getMinBid();
        max = (model.getAuctionM().getMax());
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valueEdx.setError(null);
                clickA = true;
                if (!valueEdx.getText().toString().trim().equals("")) {
                    val = 0;
                    val = new Integer(valueEdx.getText().toString());
                    if (val < (current2 + max))
                        valueEdx.setText((val + 1) + "");
                    else
                        Toast.makeText(BiddingListActivity.this, "الرجاء الالتزام بالحد الادنى والاقصى للمزايدة", Toast.LENGTH_SHORT).show();
                } else valueEdx.setText("1");
            }
        });
        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valueEdx.setError(null);
                clickA = true;
                if (!valueEdx.getText().toString().trim().equals("")) {
                    val = 0;
                    val = new Integer(valueEdx.getText().toString());
                    if (val > 0)
                        valueEdx.setText((val - 1) + "");
                    else
                        Toast.makeText(BiddingListActivity.this, "الرجاء الالتزام بالحد الادنى والاقصى للمزايدة", Toast.LENGTH_SHORT).show();
                } else valueEdx.setText("0");
            }
        });


        addPriceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPrices(model);
            }
        });

        rateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                androidx.fragment.app.DialogFragment dialogFragment = new RateAuctiontDialog(auctionID, cid);
                dialogFragment.show(getSupportFragmentManager(), "RateAuctiontDialog");
            }
        });
        valueEdx.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (i == EditorInfo.IME_ACTION_DONE)) {
                    click = false;
                    clickA = false;
                    checkPrices(model);
                }
                return false;
            }
        });
    }

    private void checkPrices(final BiddingListM model) {
        if (getBidsNumber(model) > 0) {
            getLastBidTimeIFExists();
            diffBidTime = subtract_milliseconds_from_date_in_java();

            if (tempwaitTime == 0) {
                firePrice(model);
            } else {
                if (diffBidTime == tempwaitTime || diffBidTime > tempwaitTime) {
                    diffBidTime = 0;
                    getWaiteSecFromDb(model);
                }
                if (diffBidTime == 0 || diffBidTime > tempwaitTime) {
                    firePrice(model);
                }

                if (diffBidTime < tempwaitTime && diffBidTime != 0) {
                    if (waitTime != 0) {
                        Toast.makeText(context, "برجاء الانتظار " + (tempwaitTime - diffBidTime) + "ثانية", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        } else {
            Toast.makeText(context, "لقد استهلكت كل عدد مزايداتك", Toast.LENGTH_SHORT).show();
        }
    }

    private void firePrice(final BiddingListM model) {
        click = false;
        clickA = false;
        if (!valueEdx.getText().toString().trim().equals("")) {
            val = new Integer(valueEdx.getText().toString());
            if ((val <= (current2 + max)) && (val > min)) {
                showMessageDialog("تنبيه"
                        , msg + (val) + dayOrPrice
                        , new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                sendPrice(auctionID, cid, val + "");
                                getWaiteSecFromDb(model);
                            }
                        });
            } else
                valueEdx.setError("الرجاء الالتزام بالحد الادنى والاقصى للمزايدة");
        } else valueEdx.setError("اضف قيمة");
    }

    public void sendPrice(final String postID, final String userID, final String val) {

        showProgressBar();
        String url = "http://" + BASE_URL_SEND + "/elk/";
        Services api = API.getAPIS(url);
        Call<SendPriceModel> call = api.sendYourPriceClosedAuc(postID, userID, val);
        call.enqueue(new Callback<SendPriceModel>() {
            @Override
            public void onResponse(Call<SendPriceModel> call, Response<SendPriceModel> response) {
                hideDialog();
                if (response.isSuccessful()) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    addBidTime("" + sdf.format(new Date()));
                    addMinBid(cid, auctionID, val);
                    Toast.makeText(context, "تم الارسال بنجاح", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SendPriceModel> call, Throwable t) {
                hideDialog();
            }
        });
    }

    private void sayNoForBidding(final String auctionID, String cid, String yesOrNo) {
        showProgressBar();
        Services api = API.getAPIS();
        Call<TrueMsg> call = api.bidYesOrNo(auctionID, cid, yesOrNo);
        call.enqueue(new Callback<TrueMsg>() {
            @Override
            public void onResponse(Call<TrueMsg> call, Response<TrueMsg> response) {
                hideDialog();
                 if (response.isSuccessful()) {
                    Toast.makeText(BiddingListActivity.this, "تم ارسال الطلب", Toast.LENGTH_SHORT).show();
                    recreate();
                }
            }

            @Override
            public void onFailure(Call<TrueMsg> call, Throwable t) {
                hideDialog();
            }
        });
    }

    private void sayYesForBidding(final String auctionID, final String cid, String current, String auctionKind) {
        getMinBid();
        String url = "http://" + BASE_URL_SEND + "/elk/";

        DialogFragment dialogFragment = new BiddingDialog(
                auctionID + "",
                min + "",
                cid + "",
                auctionKind + "",
                url + ""
        );
        dialogFragment.show(getSupportFragmentManager(), "BiddingDialog");
    }

    private void getBiddingList(final String auctionID) {
        Services api = API.getAPIS();
        Call<BiddingListM> call = api.getBiddingist(auctionID + "", cid + "");
        call.enqueue(new Callback<BiddingListM>() {
            @Override
            public void onResponse(Call<BiddingListM> call, Response<BiddingListM> response) {
                //  ProgressManager.getInstance().stopLoading();
                if (!response.isSuccessful()) {
                } else if (response.isSuccessful()) {
                    List<BidderM> biddingListMS = new ArrayList<>();
                    final BiddingListM res = response.body();
                    if (res.getBiddersList().size() == 0) {
                        Toast.makeText(BiddingListActivity.this, "لا توجد نتائج", Toast.LENGTH_SHORT).show();
                        // yesNoLine.setVisibility(View.GONE);
                    }

                    localTimer.cancel();
                    callLocalTimer(new Integer(res.getAuctionM().getDatetype()), res.getAuctionM().getEnddate());

                    timeToStOrFinTextView.setVisibility(View.VISIBLE);
                    if (!click && !clickA)
                        valueEdx.setText(res.getAuctionM().getCurrent() + "");
                    biddingListMS = res.getBiddersList();
                    BiddingListAdapter adapter = new BiddingListAdapter(
                            biddingListMS,
                            BiddingListActivity.this,
                            R.layout.ask_qst_dailog, auctionKind);

                    biddingList.setAdapter(adapter);
                    addBidTimeToDB(cid, auctionID, res.getAuctionM().getWaitTime() + "");
                    if (res.getAuctionM().isStarted() && !res.getAuctionM().isFinishedfinal()) {
                        // refreshDataBtn.setVisibility(View.VISIBLE);
                        timeToStOrFinTextView.setText(
                                context.getResources().getString(R.string.adp_auction_will_end_in)
                        );
                        newTimer.setVisibility(View.VISIBLE);
                        msgTxt.setVisibility(View.VISIBLE);
                        if (res.getAuctionM().isHidebids())
                            bidsNum.setVisibility(View.GONE);
                        else
                            bidsNum.setVisibility(View.VISIBLE);
                        if (res.getAuctionM().getCan() == 0) {
                            if (res.getAuctionM().isFinished() && !res.getAuctionM().isFinishedfinal()) {
                                showOrHide(res.getAuctionM());
                            } else {
                                yesNoLine.setVisibility(View.GONE);
                                minMaxLine.setVisibility(View.GONE);
                            }
                        } else {
                            yesNoLine.setVisibility(View.GONE);
                            minMaxLine.setVisibility(View.GONE);
                        }
                        // Auction Maybe Finshed So Request Server Again If Finished
                        if (res.getAuctionM().getEnddate() == 0) {
                            checkIfAucFinished(response);  // To Check If Auction Finished ??
                            int ddd = 3;
                            ddd = Methods.getIntegerFromSharedPref("TRUEFALSE", context);
                            // Auction Finished
                            if (ddd == 1) {
                                newTimer.setTextSize(20);
                                //mainTimerToServer.cancel();
                                localTimer.cancel();
                            } else if (ddd == 0) {
                                executeTask();   // Not Finished
                            }
                        }
                    }
                    setViewData(res);

                    // Auction Not Started Yet
                    if (!res.getAuctionM().isStarted()) {
                        timeToStOrFinTextView.setText("سيبدا المزاد بعد");
                        newTimer.setVisibility(View.VISIBLE);
                        yesNoLine.setVisibility(View.GONE);
                        minMaxLine.setVisibility(View.GONE);
                        msgTxt.setVisibility(View.GONE);
                        addMinBid(cid, auctionID, "0");
                    }
                    if (res.getAuctionM().isFinishedfinal() && res.getAuctionM().isStarted()) {
                        //refreshDataBtn.setVisibility(View.VISIBLE);
                        // mainTimerToServer.cancel();
                        localTimer.cancel();
                        timeToStOrFinTextView.setVisibility(View.GONE);
                        newTimer.setText(
                                context.getResources().getString(R.string.adp_auction_ended)
                        );
                        newTimer.setTextSize(20);
                        yesNoLine.setVisibility(View.GONE);
                        minMaxLine.setVisibility(View.GONE);
                        msgTxt.setVisibility(View.GONE);
                        rateUs.setVisibility(View.VISIBLE);
                        addMinBid(cid, auctionID, "0");
                    }

                    buttonsActions(response);
                    if (res.getAuctionM().getCan() == 1) {
                        yesNoLine.setVisibility(View.GONE);
                        minMaxLine.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<BiddingListM> call, Throwable t) {
                //  ProgressManager.getInstance().stopLoading();
            }
        });
    }

    private void getBiddingListWithFireBase(final String auctionID) {
        String url = "http://" + BASE_URL + "/elk/";
        Services api = API.getAPIS(url);
        Call<BiddingListM> call = api.getBiddingist(auctionID + "", cid + "");
        call.enqueue(new Callback<BiddingListM>() {
            @Override
            public void onResponse(Call<BiddingListM> call, Response<BiddingListM> response) {
                //  ProgressManager.getInstance().stopLoading();
                if (!response.isSuccessful()) {
                } else if (response.isSuccessful()) {
                    List<BidderM> biddingListMS = new ArrayList<>();
                    final BiddingListM res = response.body();
                    if (res.getBiddersList().size() == 0) {
                        Toast.makeText(BiddingListActivity.this, "لا توجد نتائج", Toast.LENGTH_SHORT).show();
                        // yesNoLine.setVisibility(View.GONE);
                    }

                    localTimer.cancel();
                    callLocalTimer(new Integer(res.getAuctionM().getDatetype()), res.getAuctionM().getEnddate());

                    timeToStOrFinTextView.setVisibility(View.VISIBLE);
                    if (!click && !clickA)
                        valueEdx.setText(res.getAuctionM().getCurrent() + "");
                    biddingListMS = res.getBiddersList();
                    BiddingListAdapter adapter = new BiddingListAdapter(
                            biddingListMS,
                            BiddingListActivity.this,
                            R.layout.ask_qst_dailog, auctionKind);

                    biddingList.setAdapter(adapter);
                    addBidTimeToDB(cid, auctionID, res.getAuctionM().getWaitTime() + "");
                    if (res.getAuctionM().isStarted() && !res.getAuctionM().isFinishedfinal()) {
                        // refreshDataBtn.setVisibility(View.VISIBLE);
                        timeToStOrFinTextView.setText(
                                context.getResources().getString(R.string.adp_auction_will_end_in)
                        );
                        newTimer.setVisibility(View.VISIBLE);
                        msgTxt.setVisibility(View.VISIBLE);
                        if (res.getAuctionM().isHidebids())
                            bidsNum.setVisibility(View.GONE);
                        else
                            bidsNum.setVisibility(View.VISIBLE);
                        if (res.getAuctionM().getCan() == 0) {
                            if (res.getAuctionM().isFinished() && !res.getAuctionM().isFinishedfinal()) {
                                showOrHide(res.getAuctionM());
                            } else {
                                yesNoLine.setVisibility(View.GONE);
                                minMaxLine.setVisibility(View.GONE);
                            }
                        } else {
                            yesNoLine.setVisibility(View.GONE);
                            minMaxLine.setVisibility(View.GONE);
                        }
                        // Auction Maybe Finshed So Request Server Again If Finished
                        if (res.getAuctionM().getEnddate() == 0) {
                            checkIfAucFinished(response);  // To Check If Auction Finished ??
                            int ddd = 3;
                            ddd = Methods.getIntegerFromSharedPref("TRUEFALSE", context);
                            // Auction Finished
                            if (ddd == 1) {
                                newTimer.setTextSize(20);
                                //mainTimerToServer.cancel();
                                localTimer.cancel();
                            } else if (ddd == 0) {
                                executeTask();   // Not Finished
                            }
                        }
                    }
                    setViewData(res);

                    // Auction Not Started Yet
                    if (!res.getAuctionM().isStarted()) {
                        timeToStOrFinTextView.setText("سيبدا المزاد بعد");
                        newTimer.setVisibility(View.VISIBLE);
                        yesNoLine.setVisibility(View.GONE);
                        minMaxLine.setVisibility(View.GONE);
                        msgTxt.setVisibility(View.GONE);
                        addMinBid(cid, auctionID, "0");
                    }
                    if (res.getAuctionM().isFinishedfinal() && res.getAuctionM().isStarted()) {
                        //refreshDataBtn.setVisibility(View.VISIBLE);
                        // mainTimerToServer.cancel();
                        localTimer.cancel();
                        timeToStOrFinTextView.setVisibility(View.GONE);
                        newTimer.setText(
                                context.getResources().getString(R.string.adp_auction_ended)
                        );
                        newTimer.setTextSize(20);
                        yesNoLine.setVisibility(View.GONE);
                        minMaxLine.setVisibility(View.GONE);
                        msgTxt.setVisibility(View.GONE);
                        rateUs.setVisibility(View.VISIBLE);
                        addMinBid(cid, auctionID, "0");
                    }

                    buttonsActions(response);
                    if (res.getAuctionM().getCan() == 1) {
                        yesNoLine.setVisibility(View.GONE);
                        minMaxLine.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<BiddingListM> call, Throwable t) {
                //  ProgressManager.getInstance().stopLoading();
            }
        });
    }

    private void showOrHide(BidderAuctionM res) {
        if (res.getBidtype() == 1) {
            yesNoLine.setVisibility(View.VISIBLE);
            minMaxLine.setVisibility(View.GONE);
        } else if (res.getBidtype() == 0) {
            minMaxLine.setVisibility(View.VISIBLE);
            yesNoLine.setVisibility(View.GONE);
        }
    }

    public void checkIfAucFinished(Response<BiddingListM> response) {
        executeTask();
        if (response.body().getAuctionM().isFinishedfinal()) {
            Methods.saveIntegerSharedPref("TRUEFALSE", 1, context);
            localTimer.cancel();
            // mainTimerToServer.cancel();
        } else {
            Methods.saveIntegerSharedPref("TRUEFALSE", 0, context);
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
                                    newTimer.setText(endDateForLocalTimer[0] + BiddingListActivity.this.getResources().getString(R.string.seconds));
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
                                    newTimer.setText(endDateForLocalTimer[0] + BiddingListActivity.this.getResources().getString(R.string.minutes));
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
                                    newTimer.setText(endDateForLocalTimer[0] + BiddingListActivity.this.getResources().getString(R.string.hours));
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

    private void setViewData(BiddingListM model) {
        msgTxt.setText(model.getAuctionM().getMsg());
        bidsNum.setText("المزايدات المتبقية لك   " + model.getAuctionM().getBidsNumber() + "");
        if (model.getAuctionM().getDatetype() != null) {
            if (model.getAuctionM().getDatetype().equals("4")) {
                newTimer.setText(model.getAuctionM().getEnddate() + this.getResources().getString(R.string.days));
            }
            if (model.getAuctionM().getDatetype().equals("3")) {
                newTimer.setText(model.getAuctionM().getEnddate() + this.getResources().getString(R.string.hours));
            }
            if (model.getAuctionM().getDatetype().equals("2")) {
                newTimer.setText(model.getAuctionM().getEnddate() + this.getResources().getString(R.string.minutes));
            }
            if (model.getAuctionM().getDatetype().equals("1")) {
                newTimer.setText(model.getAuctionM().getEnddate() + this.getResources().getString(R.string.seconds));
            }
        }
        getBidsNumber(model);
        getWaitTime(model);

    }

    private int getBidsNumber(BiddingListM model) {
        return model.getAuctionM().getBidsNumber();
    }

    private int getWaitTime(BiddingListM model) {
        return model.getAuctionM().getWaitTime();
    }

    private void addBidTime(String date) {
        Utils.saveSharedPrefrences(Constants.LastBidTime+auctionID,date);
    }

    public long subtract_milliseconds_from_date_in_java() {
        Date date1 = new Date();
        Log.d("DADADAD", "" + Math.abs(getDateDiff(date1, lastBidDate, TimeUnit.MILLISECONDS) / 1000));
        return Math.abs(getDateDiff(date1, lastBidDate, TimeUnit.MILLISECONDS) / 1000);
    }

    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        try {
            long diffInMillies = date2.getTime() - date1.getTime();
            return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
        } catch (NullPointerException c) {
            c.printStackTrace();
        }
        return 0;
    }

    private int getLastBidTimeIFExists() {
        String string = Utils.getSharedPrefrences(Constants.LastBidTime+auctionID,null);
        if(TextUtils.isEmpty(string))
            return 0;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            lastBidDate = format.parse(string);
            Log.d("BID_DATE", lastBidDate + "");
            return 1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void getWaiteSecFromDb(BiddingListM m) {
        /*waitTime = getWaitTime(m);
        tempwaitTime = getWaitTime(m);*/
        getBidTimeFromDB();
    }

    private void addMinBid(String cid, String auctionID, String value) {
        Utils.saveSharedPrefrences(Constants.TABLE_Minimum_Bid+auctionID,value);
    }

    private int getMinBid() {

        int temp = Integer.parseInt(Utils.getSharedPrefrences(Constants.TABLE_Minimum_Bid+auctionID,"-1"));
        if(temp!=-1){
            min=temp;
            return 1;
        }
        return 0;
    }

    private void addBidTimeToDB(String cid, String auctionID, String value) {
        Utils.saveSharedPrefrences(Constants.Bid_Time_Value+auctionID,value);
    }

    private int getBidTimeFromDB() {

        waitTime = 0;
        tempwaitTime = 0;
        String time = Utils.getSharedPrefrences(Constants.Bid_Time_Value+auctionID,null);
        if(!TextUtils.isEmpty(time)){
            waitTime = Integer.valueOf(time);
            tempwaitTime = Integer.valueOf(time);
            return 1;
        }
        return 0;
    }

    private void makeItFalse() {

        valueEdx.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                Rect r = new Rect();
                valueEdx.getWindowVisibleDisplayFrame(r);
                int screenHeight = valueEdx.getRootView().getHeight();

                // r.bottom is the position above soft keypad or device button.
                // if keypad is shown, the r.bottom is smaller than that before.
                int keypadHeight = screenHeight - r.bottom;


                // 0.15 ratio is perhaps enough to determine keypad height.
                // keyboard is opened
                // keyboard is closed
                click = keypadHeight > screenHeight * 0.15;
            }
        });

    }

    private void executeTask() {
        getBiddingList(auctionID);
    }


    private void getBaseUrl() {
        BASE_URL = Repository.getUserInfo().getReceivingResultsLink();
        BASE_URL_SEND = Repository.getUserInfo().getSendingBidLink();
    }

    @Override
    protected void onStop() {
        super.onStop();
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
        try {
            LocalBroadcastManager.getInstance(this).
                    unregisterReceiver(mRegistrationBroadcastReceiver);
            unregisterReceiver(myBroadcastReceiver);
        } catch (IllegalArgumentException e) {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // mainTimerToServer.cancel();
        localTimer.cancel();
    }

    @Override
    public ArrayList<MenuAdapter.MenuItem> getItems() {
        return getAuctionsMenuItems();
    }

    @Override
    protected void onResume() {
        super.onResume();
        NotesReciever.callNotifications(mRegistrationBroadcastReceiver, this);
    }
}
