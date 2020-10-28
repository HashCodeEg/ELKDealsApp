package com.elkdeals.mobile.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.Constants;
import com.elkdeals.mobile.Utils.Methods;
import com.elkdeals.mobile.Utils.Utils;
import com.elkdeals.mobile.adapters.MenuAdapter;
import com.elkdeals.mobile.adapters.SpinnerAdapterD;
import com.elkdeals.mobile.api.API;
import com.elkdeals.mobile.api.Services;
import com.elkdeals.mobile.api.models.AucSpineerModel;
import com.elkdeals.mobile.api.models.AuctionItemModel;
import com.elkdeals.mobile.api.models.CurrentAucSerModel;
import com.elkdeals.mobile.api.models.CurrentAuctionsDetModel;
import com.elkdeals.mobile.api.models.UserModel;
import com.elkdeals.mobile.custom.ProfileImageView;
import com.elkdeals.mobile.dialogFragments.EditInformationDialog;
import com.elkdeals.mobile.fireBase.Config;
import com.elkdeals.mobile.model.Repository;
import com.elkdeals.mobile.recievers.ConnectivityReceiver;
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

public class CurrentAuctionDetails extends BaseActivity implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.allAuctionsNamesSpinner) Spinner auctionsNamesSpinner;                         // All Auctions Names Spinner
    @BindView(R.id.itemPriceTextView) TextView itemPriceTextView;                           // Auction Price TextView
    @BindView(R.id.userNameTextView) TextView userNameTextView;                            // Customer Name TextView
    @BindView(R.id.natIDEditTextView) TextView natIDEditTextView;                           // National ID TextView
    @BindView(R.id.monthelyAmountTextView) TextView monthelyAmountTextView;                      // الخصم الشهرى//
    @BindView(R.id.amountAfterDiscTextView) TextView amountAfterDiscTextView;                     //القيمة بعد الخصم//
    @BindView(R.id.monthInstaTextView) TextView monthInstaTextView;                          //الفسط الشهرى//
    @BindView(R.id.newTimer) TextView newTimer;                                    // End Date Timer
    @BindView(R.id.minValueBtn) RelativeLayout minValue;                              // Minimum Value Btn
    @BindView(R.id.maxValBtn) RelativeLayout maxValue;                              // Maximum Value Btn
    @BindView(R.id.timeToStOrFinTextView) TextView timeToStOrFinTextView;                       // Auc Will (Start/Finish) After Time TextView
    @BindView(R.id.textMin) TextView textMin;                                     // Minimum value in TextView
    @BindView(R.id.textMax) TextView textMax;                                     // Maximum value in TextView
    @BindView(R.id.refreshDataBtn) Button refreshDataBtn;                                // Refresh Button
    @BindView(R.id.monthsNumTextView) TextView monthsNumTextView;
    @BindView(R.id.infoImg) ImageView infoImge;
    @BindView(R.id.numOfInstallementMonthsLine) LinearLayout numOfInstallementMonthsLine;
    @BindView(R.id.bidNumLine) LinearLayout bidNumLine;

    //-------------------------------------------------------------------------------------------------//
    Response<List<CurrentAuctionsDetModel>> response0;  // to get data from service
    List<AucSpineerModel> listOfSpinnerItem;            // to get spinner auctions
    CurrentAucSerModel aucSerModel;                     // to get serialized object
    AuctionItemModel itemModel;                         // to send auction details to another class if it was OnePrice Auction
    SpinnerAdapterD adapter;                             // spinner adapter for all auctions
    Bundle extras;                                      // receive activity data
    //-------------------------------------------------------------------------------------------------//
    Timer mainTimerToServer = new Timer();              // main timer to call server when auction work
    Timer localTimer = new Timer();                     // local timer to change time locally
    Timer waiteTimer = new Timer();                     // waite timer to change time locally
    Timer waiteTimerRef = new Timer();                     // waite timer to change time locally
    //-------------------------------------------------------------------------------------------------//
    String dateType = "";                               // to get date type 1,2,3,4
    String auctionID = "";                              // to get auction ID
    boolean checkIfUserInterActWithScreen;              // to chek if user interact with screen
    int[] endDateForLocalTimer = {0};                   // to get end date for localTimer
    int endDate = 0;                                    // not used just for test
    int[] count = {0};                                  // counter to stop Toast
    int waitTime = 0;
    int tempwaitTime = 0;
    int waitTimeRef = 0;
    Date lastBidDate;
    long diffBidTime = 0;
    //-------------------------------------------------------------------------------------------------//

    MyBroadcastReceiver myBroadcastReceiver;
    BroadcastReceiver mRegistrationBroadcastReceiver;
    AucSpineerModel aucModelForTry;
    String ifPrice = "";
    @BindView(R.id.currentPriceTxt)
    TextView currentPriceTxt;
    @BindView(R.id.numOfMonthTxt)
    TextView numOfMonthTxt;
    @BindView(R.id.maxMonthsTxt)
    TextView maxMonthsTxt;
    @BindView(R.id.bidCountTxt)
    TextView bidCountTxt;

    String cid;

    public CurrentAuctionDetails() {
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
        putContentView(R.layout.activity_current_auction_details);
        ButterKnife.bind(this);
        Methods.setAppFont();
        cid = Repository.getUserInfo().getId();
        getWaiteSecFromDb();
        extras = getIntent().getExtras();
        if (extras.getString(Constants.KEY_POST_ID) != null) {
            auctionID = extras.getString(Constants.KEY_POST_ID);
            getCurrentAuction(auctionID,cid);
            auctionsNamesSpinner.setOnItemSelectedListener(this);

        } else {
            aucSerModel = (CurrentAucSerModel) extras.getSerializable("AUCOBJ");
            auctionID = aucSerModel != null ? aucSerModel.getId() : null;
            dateType = aucSerModel.getDatetype();
            endDate = aucSerModel.getEnddate2();


            getCurrentAuction(auctionID,cid);

            // get all auctions in spinner
            listOfSpinnerItem =
                    (List<AucSpineerModel>) extras.getSerializable("SPINEERITEMS");
            List<String> items = new ArrayList<>();
            for (int i = 0; i < listOfSpinnerItem.size(); i++) {
                items.add(listOfSpinnerItem.get(i).getName());
            }
            auctionsNamesSpinner.setOnItemSelectedListener(this);
            adapter = new SpinnerAdapterD(this, R.layout.spinner_item, items);
            auctionsNamesSpinner.setAdapter(adapter);
            auctionsNamesSpinner.setSelection(aucSerModel.getPostition());
            //  refreshDataBtn.setVisibility(View.GONE);

        }
          /*
        * Fire Background Service To Get Flag
        * @var      myBroadcastReceiver To Get Message From Service
        * @method   Methods.getMessageFromBroadCast() Get Message From Service
        * */
        //new RestClient(this);
        //myBroadcastReceiver = new MyBroadcastReceiver();
        // Methods.getMessageFromBroadCast(this, myBroadcastReceiver);
        // String serverMessage = Methods.getFromSharedPref(Keys.ACTV_CurrentAuctionDetails, this);
        //Log.d("NAAAAMES", serverMessage + " --- " + Keys.ACTV_CurrentAuctionDetails);

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
                        if (message != null) {
                            if (message.equals(auctionID))
                                getCurrentAuction(message,cid);
                        }
                    }

                }
            }
        };
        getLastBidTimeIFExists();
        diffBidTime = subtract_milliseconds_from_date_in_java();

    }

    @Override
    public ArrayList<MenuAdapter.MenuItem> getItems() {
        return getAuctionsMenuItems();
    }

    /*
    * checkIfUserInterActWithScreen
    * */
    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        checkIfUserInterActWithScreen = true;
    }


    /*
    * WHEN ITEM SELECTED FROM SPINNER
    *
    * @var  selected to get selected object from spinner
    *
    * @Method executeTask() to get auction details from service
    * */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if (!auctionID.equals("1")) {
            AucSpineerModel selected = listOfSpinnerItem.get(i);
            auctionID = selected.getId();


            if (checkIfUserInterActWithScreen) {
                if (ConnectivityReceiver.isConnected() ) {
                    executeTask();
                } else {
                    Toast.makeText(this, this.getResources().getString(R.string.no_internet_connection) + "", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /*@Override
    public void onBackPressed() {
        //super.onBackPressed();
        //mainTimerToServer.cancel();

        localTimer.cancel();
        if (auctionID.equals("1")) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Bundle bndlanimation = ActivityOptions.makeCustomAnimation(this, R.xml.animation, R.xml.animation2).toBundle();
            startActivity(intent,bndlanimation);

        } else {
            Intent intent = new Intent(this, CurrentAuctions.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            if (Methods.getFromSharedPref(Keys.ACTV_CurrentAuctionDetails, this) != null)
                Methods.updateInSharedPref(Keys.ACTV_CurrentAuctionDetails, "", this);
            Bundle bndlanimation = ActivityOptions.makeCustomAnimation(this, R.xml.animation, R.xml.animation2).toBundle();
            startActivity(intent,bndlanimation);
        }

    }
*/
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
        try {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
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
    protected void onResume() {
        super.onResume();
        NotesReciever.callNotifications(mRegistrationBroadcastReceiver, this);
        /*new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                executeTask();
            }
        }.start();*/

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        executeTask();
    }

    /*
    * This Method To Get Data From Server Based On Time If Auction Is Started
    * @param dateType1 to get Data Type
    * @IF dateType1 ==1 call server every 5 second
    * @IF dateType1 ==2 call server every 1 minute
    * @IF dateType1 ==3 call server every 1 hour
    * @IF dateType1 ==4 call server every 1 day
    * */
    private void onGetData(String dateType1) {
        mainTimerToServer = new Timer();
        if (dateType1.equals("1")) {
            mainTimerToServer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    executeTask();
                }
            }, 5000, 5000);
        } else if (dateType1.equals("2")) {
            mainTimerToServer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    executeTask();
                }
            }, 60000, 60000);
        } else if (dateType1.equals("3")) {
            mainTimerToServer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    executeTask();
                }
            }, 3600000, 3600000);
        }

    }

    /*
    *         This Method TO GEt Current Auction Details From Service ,
    *         checkIfUserInterActWithScreen If Auction Started,Finished,NOt YEt
    * @param  aucId to use in server Method
    *
    * @IF     response.body().get(i).isStarted() && !response.body().get(i).isFinished()
    *         Auction Started And Not Finished
    *
    * @IF    !response.body().get(i).isStarted()   Auction Not Started YEt
    *         setEnabeldFalse() to disable all Buttons
    *
    * @IF     response.body().get(i).isFinished()  Auction Finished
    *         setEnabeldFalse() to disable all Buttons
    *
    * @IF     response.body().get(i).getEnddate() == 0 and auction not finished call server again to get 59 second
    *         To Get 59 sec Fire checkIfAucFinished() in this If Scope
    *
    * @method sendDataToActivity() to get Price to send it to .class
    * */
    private void getCurrentAuction(String aucId,String cid) {

        Services api = API.getAPIS();
        Call<List<CurrentAuctionsDetModel>> listCall = api.getCurrentAuctionDetails(aucId,cid);
        listCall.enqueue(new Callback<List<CurrentAuctionsDetModel>>() {
            @Override
            public void onResponse(Call<List<CurrentAuctionsDetModel>> call, Response<List<CurrentAuctionsDetModel>> response) {
                if (!response.isSuccessful()) {
                    executeTask();
                }
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().size() != 0) {
                            response0 = response;
                            for (int i = 0; i < response.body().size(); i++) {
                                Log.d("KKKK", endDate + "");
                                localTimer.cancel();

                                callLocalTimer(Integer.valueOf(response0.body().get(i).getDatetype()), response0.body().get(i).getEnddate());

                                endDate = response0.body().get(i).getEnddate();
                                dateType = response0.body().get(i).getDatetype();
                                auctionID = response0.body().get(i).getId() + "";

                                timeToStOrFinTextView.setVisibility(View.VISIBLE);
                                minValue.setEnabled(true);
                                maxValue.setEnabled(true);
                                if (auctionID.equals("1")) {
                                    aucModelForTry = new AucSpineerModel(
                                            response.body().get(i).getId() + "",
                                            response.body().get(i).getName(),
                                            0,
                                            response.body().get(i).getCname(),
                                            response.body().get(i).getCurrent(),
                                            response.body().get(i).getImage()
                                    );
                                    List<String> ss = new ArrayList<>();
                                    ss.add(aucModelForTry.getName());
                                    adapter = new SpinnerAdapterD(CurrentAuctionDetails.this, R.layout.spinner_item, ss);
                                    auctionsNamesSpinner.setAdapter(adapter);
                                }


                                // Auction Still Working
                                if (response0.body().get(i).isStarted() && !response0.body().get(i).isFinished()) {
                                    // refreshDataBtn.setVisibility(View.VISIBLE);
                                    timeToStOrFinTextView.setText(
                                            CurrentAuctionDetails.this.getResources().getString(R.string.adp_auction_will_end_in)
                                    );
                                    // Auction Maybe Finshed So Request Server Again If Finished
                                    if (response0.body().get(i).getEnddate() == 0) {
                                        checkIfAucFinished(i);  // To Check If Auction Finished ??
                                        int ddd = 3;
                                        ddd = Methods.getIntegerFromSharedPref("TRUEFALSE", CurrentAuctionDetails.this);
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

                                // Auction Not Started Yet
                                if (!response0.body().get(i).isStarted()) {
                                    // mainTimerToServer.cancel();
                                    //refreshDataBtn.setVisibility(View.VISIBLE);
                                    if (response0.body().get(i).isShowtimer()) {
                                        timeToStOrFinTextView.setText(
                                                CurrentAuctionDetails.this.getResources().getString(R.string.adp_auction_will_start_in)
                                        );
                                    } else {
                                        timeToStOrFinTextView.setText("المزاد لم يبدأ بعد");
                                    }

                                }
                                setViewData(i);
                                if (response.body().get(i).getMin() != 0 && response.body().get(i).getMax() != null) {
                                    // setViewData(i);
                                    // Auction Finished1
                                    //  refreshDataBtn.setVisibility(View.VISIBLE);
                                    if (response0.body().get(i).isFinished()) {
                                        if (response.body().get(i).isFinished()) {
                                            refreshDataBtn.setVisibility(View.GONE);
                                            setEnabeldFalse();
                                            //  mainTimerToServer.cancel();
                                            localTimer.cancel();
                                            timeToStOrFinTextView.setVisibility(View.GONE);
                                            newTimer.setText(
                                                    CurrentAuctionDetails.this.getResources().getString(R.string.adp_auction_ended)
                                            );
                                            newTimer.setTextSize(20);
                                        }

                                    }
                                }
                                if (response.body().get(i).isFinished() && response.body().get(i).isStarted()) {
                                    //refreshDataBtn.setVisibility(View.VISIBLE);
                                    setEnabeldFalse();
                                    // mainTimerToServer.cancel();
                                    localTimer.cancel();
                                    timeToStOrFinTextView.setVisibility(View.GONE);
                                    newTimer.setText(
                                            CurrentAuctionDetails.this.getResources().getString(R.string.adp_auction_ended)
                                    );
                                    newTimer.setTextSize(20);
                                    refreshDataBtn.setVisibility(View.GONE);
                                }

                                btnActions(i);
                            }
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<List<CurrentAuctionsDetModel>> call, Throwable t) {
                executeTask();
            }
        });
    }


    /*
    * This Method To Create Local Timer
    * @param dateType1 to get date Type
    * @param endDate1  to get end  date
    *
    * @IF dateType1 == 1
    * */
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
                                    newTimer.setText(endDateForLocalTimer[0] + CurrentAuctionDetails.this.getResources().getString(R.string.seconds));
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
                                    newTimer.setText(endDateForLocalTimer[0] + CurrentAuctionDetails.this.getResources().getString(R.string.minutes));
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
                                    newTimer.setText(endDateForLocalTimer[0] + CurrentAuctionDetails.this.getResources().getString(R.string.hours));
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

    /*
    *  Timer To prevent User From Clicks Fo 20 second
    *  @var  waitTime  = 20 second
    * */
    private void setWaiteTimer() {
        waiteTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new TimerTask() {
                    @Override
                    public void run() {
                        if (waitTime != 0) {
                            waitTime--;

                            if (waitTime == 0) {
                                waiteTimer.cancel();
                                waiteTimer = new Timer();
                                ifPrice = "";
                                getWaiteSecFromDb();
                            }
                        }
                    }
                });
            }
        }, 1000, 1000);
    }

    private void setWaiteTimerRef() {
        waiteTimerRef.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new TimerTask() {
                    @Override
                    public void run() {
                        if (waitTimeRef != 0) {
                            waitTimeRef--;

                            if (waitTimeRef == 0) {
                                waiteTimerRef.cancel();
                                waiteTimerRef = new Timer();
                                ifPrice = "";
                                getWaiteSecFromDb();
                            }
                        }
                    }
                });
            }
        }, 1000, 1000);
    }

    /*
    * This Method TO Check If Auction Finished OR NOT when Auction Work
    * To Get 59 Second
    * this will save 0 if auction not finished
    * this will save 1 if auction finished
    * */
    public void checkIfAucFinished(int i) {
        executeTask();
        if (response0.body().get(i).isFinished()) {
            Methods.saveIntegerSharedPref("TRUEFALSE", 1, CurrentAuctionDetails.this);
            localTimer.cancel();
            // mainTimerToServer.cancel();
        } else {
            Methods.saveIntegerSharedPref("TRUEFALSE", 0, CurrentAuctionDetails.this);
        }
    }

    /*
    * To Get Auction Data From Serivce
    * */
    private void executeTask() {
        getCurrentAuction(auctionID,cid);
    }

    /*
    * Fire Method btnActions() To send Data To .class
    * @param i to get position
    * @var count to Toast Message from Displaying more than 4 time if user decided to play
    *
    * @IF if (response0.body().get(i).isStarted() && !response0.body().get(i).isFinished())
    *       that is mean that auction not finished then send your price
    * */

    private void sendDataToActivity(String minOrMaxVal, int i, String aucType,int cashValue) {

        if (!response0.body().get(i).isStarted()) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    CurrentAuctionDetails.this.getResources().getString(R.string.adp_auction_notStarted), Toast.LENGTH_SHORT);
            count[0]++;
            if (count[0] < 4) {
                toast.show();
            }
            if (count[0] > 4) {
                toast.cancel();
            }
        } else if (response0.body().get(i).isStarted() && !response0.body().get(i).isFinished()) {
            Intent intent = new Intent(CurrentAuctionDetails.this, SendPricePopUp.class);
            Bundle bundle = new Bundle();
            bundle.putString(Constants.KEY_POST_CURRENT, minOrMaxVal + "");
            bundle.putString(Constants.KEY_POST_ID, auctionID);
            bundle.putString("TYPE", aucType);
            bundle.putInt("KASH", cashValue);
            if (auctionID.equals("1")) {
                bundle.putString("TryBid", Repository.getUserInfo().getId());
            }

            intent.putExtras(bundle);
            startActivityForResult(intent, 1);
        }
    }

    /*
    * This Method to Disable Button For Some Conditions
    * */
    private void setEnabeldFalse() {
        minValue.setEnabled(false);
        maxValue.setEnabled(false);

    }

    /*
    * @button minValue with get Minimum Value From Service
    * @button maxValue with get Maximum Value From Service
    *
    * @Method sendDataToActivity Wtih Min OR Max plus current price
    * */
    private void btnActions(final int i) {
        final boolean isUserValid = !TextUtils.isEmpty(Repository.getUserInfo().getNationalid())&&
                Repository.getUserInfo().getNationalid().length()>10;

        minValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isUserValid) {
                    getLastBidTimeIFExists();
                    diffBidTime = subtract_milliseconds_from_date_in_java();

                    if (tempwaitTime == 0) {
                        sendDataToActivity((response0.body().get(i).getCurrent() + response0.body().get(i).getMin()) + "", i, response0.body().get(i).getAuctionkind() + "",
                                response0.body().get(i).getCash()
                                );
                    } else {
                        if (diffBidTime == tempwaitTime || diffBidTime > tempwaitTime) {
                            ifPrice = "";
                            diffBidTime = 0;
                            getWaiteSecFromDb();
                        }
                        if (ifPrice.equals("1") || ifPrice.equals("")) {
                            if (diffBidTime == 0 || diffBidTime > tempwaitTime) {
                                sendDataToActivity((response0.body().get(i).getCurrent() + response0.body().get(i).getMin()) + "", i, response0.body().get(i).getAuctionkind() + "",
                                        response0.body().get(i).getCash()
                                        );
                            }
                        }
                        if (diffBidTime < tempwaitTime && diffBidTime != 0) {
                            Toast.makeText(CurrentAuctionDetails.this, "برجاء الانتظار " + (tempwaitTime - diffBidTime) + "ثانية", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    androidx.fragment.app.DialogFragment dialogFragment = new EditInformationDialog();
                    dialogFragment.show(getSupportFragmentManager(), "CompleteYourDataDialog");

                }


            }
        });

        maxValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isUserValid) {
                    getLastBidTimeIFExists();
                    diffBidTime = subtract_milliseconds_from_date_in_java();


                    if (tempwaitTime == 0) {
                        sendDataToActivity((response0.body().get(i).getCurrent() + response0.body().get(i).getMax()) + "", i, response0.body().get(i).getAuctionkind() + "",
                                response0.body().get(i).getCash()
                                );
                    } else {
                        if (diffBidTime == tempwaitTime || diffBidTime > tempwaitTime) {
                            ifPrice = "";
                            diffBidTime = 0;
                            getWaiteSecFromDb();
                        }
                        if (ifPrice.equals("1") || ifPrice.equals("")) {
                            if (diffBidTime == 0 || diffBidTime > tempwaitTime) {
                                sendDataToActivity((response0.body().get(i).getCurrent() + response0.body().get(i).getMax()) + "", i, response0.body().get(i).getAuctionkind() + "",
                                        response0.body().get(i).getCash()
                                        );
                            }
                        }
                        if (diffBidTime < tempwaitTime && diffBidTime != 0) {
                            if (waitTime != 0) {
                                Toast.makeText(CurrentAuctionDetails.this, "برجاء الانتظار " + (tempwaitTime - diffBidTime) + "ثانية", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } else {
                    androidx.fragment.app.DialogFragment dialogFragment = new EditInformationDialog();
                    dialogFragment.show(getSupportFragmentManager(), "CompleteYourDataDialog");
                }


            }
        });


    }

    /*
    * Refresh Data
    * */
    public void refreshDataBtn(View view) {
        getLastBidTimeIFExists();
        diffBidTime = subtract_milliseconds_from_date_in_java();
        if (ConnectivityReceiver.isConnected()) {

            if (tempwaitTime == 0) {
                executeTask();
                Toast.makeText(this, "تـــــــــــــــــم التحديــــــث", Toast.LENGTH_LONG).show();
                setWaiteTimerRef();
            } else {
                if (ifPrice.equals("1") || ifPrice.equals("")) {
                    if (waitTimeRef == 20) {
                        executeTask();
                        Toast.makeText(this, "تـــــــــــــــــم التحديــــــث", Toast.LENGTH_LONG).show();
                        setWaiteTimerRef();
                    } else {
                        if (waitTimeRef < 20) {
                            Toast.makeText(CurrentAuctionDetails.this, "برجاء الانتظار " + waitTimeRef + "ثانية", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                if (diffBidTime == tempwaitTime || diffBidTime > tempwaitTime) {
                    ifPrice = "";
                    diffBidTime = 0;
                    getWaiteSecFromDb();
                }
                if (diffBidTime < tempwaitTime && diffBidTime != 0) {
                    Toast.makeText(CurrentAuctionDetails.this, "برجاء الانتظار " + (tempwaitTime - diffBidTime) + "ثانية", Toast.LENGTH_SHORT).show();
                }

            }

        } else {
            Toast.makeText(this, this.getResources().getString(R.string.no_internet_connection) + "", Toast.LENGTH_SHORT).show();
        }


    }

    /*
    * This Method To set Data In Views
    * @param i to get position
    * */

    private int getWaiteSecFromDb() {
        try{
            int rate = 0;
            rate = Integer.parseInt(Repository.getUserInfo().getCityrate());
            waitTime = Repository.getUserInfo().getSeconds();
            tempwaitTime =waitTime;
            waitTimeRef = 20;
            if (rate > 0) {
                return rate;
            } else return 0;
        }catch (Exception e){

        }

        return 0;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            if (data.hasExtra("price")) {
                ifPrice = "2";

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                if (tempwaitTime != 0)
                    addBidTime("" + sdf.format(new Date()));

                getWaiteSecFromDb();

            }
            if (data.hasExtra("price2")) {
                ifPrice = "1";
            }
        }
    }

    private void setViewData(int i) {
        userNameTextView.setText(response0.body().get(i).getCname() + "");
        itemPriceTextView.setText(response0.body().get(i).getCurrent() + "");
        natIDEditTextView.setText(response0.body().get(i).getIdnumber() + "");
        monthInstaTextView.setText(response0.body().get(i).getInsta() + "");
        amountAfterDiscTextView.setText(response0.body().get(i).getNewprice() + "");
        monthelyAmountTextView.setText(response0.body().get(i).getAmount() + "");
        monthsNumTextView.setText(response0.body().get(i).getMonths() + "");//TODO ask about it
        textMin.setText(response0.body().get(i).getMin() + "");
        textMax.setText(response0.body().get(i).getMax() + "");
        bidCountTxt.setText(response0.body().get(i).getBids()+"");
        if (auctionID.equals("1")){
            bidNumLine.setVisibility(View.GONE);
        }
        if (response0.body().get(i).isShowbid())
            bidNumLine.setVisibility(View.VISIBLE);
        else bidNumLine.setVisibility(View.GONE);
        if (auctionID.equals("1")){
            bidNumLine.setVisibility(View.GONE);
        }

     //   maxMonthsTxt.setText(response0.body().get(i).getMaxmothly()+"");


        if (response0.body().get(i).getAuctionkind() == 2) {
            currentPriceTxt.setText("عدد الايام الان");
            numOfMonthTxt.setText("عدد الشهور الان");
            monthsNumTextView.setText(response0.body().get(i).getBidmonths()+"");
        }else {
            currentPriceTxt.setText("السعر الان");
            numOfMonthTxt.setText(" شهور القسط");
            monthsNumTextView.setText(response0.body().get(i).getMonths()+"");
        }


        if (response0.body().get(i).isShowtimer()) {
            if (response0.body().get(i).getDatetype() != null) {
                if (response0.body().get(i).getDatetype().equals("4")) {
                    newTimer.setText(response0.body().get(i).getEnddate() + CurrentAuctionDetails.this.getResources().getString(R.string.days));
                }
                if (response0.body().get(i).getDatetype().equals("3")) {
                    newTimer.setText(response0.body().get(i).getEnddate() + CurrentAuctionDetails.this.getResources().getString(R.string.hours));
                }
                if (response0.body().get(i).getDatetype().equals("2")) {
                    newTimer.setText(response0.body().get(i).getEnddate() + CurrentAuctionDetails.this.getResources().getString(R.string.minutes));
                }
                if (response0.body().get(i).getDatetype().equals("1")) {
                    newTimer.setText(response0.body().get(i).getEnddate() + CurrentAuctionDetails.this.getResources().getString(R.string.seconds));
                }
            }
        } else {
            newTimer.setVisibility(View.GONE);
        }

    }
}