package com.elkdeals.mobile.ui.auctions;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.Constants;
import com.elkdeals.mobile.Utils.Methods;
import com.elkdeals.mobile.Utils.Utils;
import com.elkdeals.mobile.activities.ResultsActivitty;
import com.elkdeals.mobile.api.API;
import com.elkdeals.mobile.api.Services;
import com.elkdeals.mobile.api.models.BidderAuctionM;
import com.elkdeals.mobile.api.models.BiddingListM;
import com.elkdeals.mobile.model.Repository;
import com.elkdeals.mobile.ui.BaseFragment;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@SuppressLint("ValidFragment")
public class SecNewAucFragment extends BaseFragment {

    @BindView(R.id.productImage)
    ImageView productImage;
    @BindView(R.id.prodName)
    TextView prodName;
    @BindView(R.id.minusBtn)
    Button minusBtn;
    @BindView(R.id.valueEdx)
    EditText valueEdx;
    @BindView(R.id.plusBtn)
    Button plusBtn;
    @BindView(R.id.yearsNum)
    TextView yearsNum;
    @BindView(R.id.monthsNum)
    TextView monthsNum;
    @BindView(R.id.daysOrPriceTxt)
    TextView daysOrPriceTxt;
    @BindView(R.id.sendPRiceBtn)
    Button sendPRiceBtn;
    @BindView(R.id.resultsBtn)
    Button resultsBtn;
    @BindView(R.id.newTimer)
    TextView newTimer;
    @BindView(R.id.timeToStOrFinTextView)
    TextView timeToStOrFinTextView;
    @BindView(R.id.timerLine)
    LinearLayout timerLine;
    @BindView(R.id.monthYearsLine)
    LinearLayout monthYearsLine;


    int val;
    String cid, auctionID;

    int[] endDateForLocalTimer = {0};                   // to get end date for localTimer
    Timer localTimer = new Timer();
    Response<BiddingListM> response0;


    @SuppressLint("ValidFragment") public SecNewAucFragment(String auctionID) {
        this.auctionID = auctionID;
    }

    @SuppressLint("ValidFragment") public SecNewAucFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sec_new_way_fragment_, container, false);
        ButterKnife.bind(this, view);
        cid = Repository.getUserInfo().getId();
        AddSubValues();
        getDays();
        getCurrentAuction(auctionID, cid);
        timerLine.setVisibility(View.GONE);
        return view;
    }

    private void AddSubValues() {
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valueEdx.setError(null);
                if (!valueEdx.getText().toString().trim().equals("")) {
                    val = 0;
                    val = new Integer(valueEdx.getText().toString());
                    valueEdx.setText((val + 1) + "");
                } else valueEdx.setText("1");
            }
        });
        minusBtn.setOnClickListener(view -> {
            valueEdx.setError(null);

            if (!valueEdx.getText().toString().trim().equals("")) {
                val = 0;
                val = new Integer(valueEdx.getText().toString());
                if (val > 0)
                    valueEdx.setText((val - 1) + "");
            } else valueEdx.setText("0");
        });

    }

    int month, years, days;

    private void getDays() {
        valueEdx.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    days = Integer.parseInt(charSequence.toString());
                    month = days / 30;
                    years = month / 12;
                } catch (NumberFormatException s) {
                    days = 0;
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                monthsNum.setText(month + "");
                yearsNum.setText(years + "");
            }
        });
    }

    private void sendReqest(String auctionID, String auctionKind, String auctionName, String auctionImage) {
        sendPRiceBtn.setOnClickListener(view -> {
            String tot = valueEdx.getText().toString();
            if (TextUtils.isEmpty(tot)){
                valueEdx.setError("اكتب القيمة");
            } else {
                if (!tot.equals("0")) {
                    activity.addPage(new EnsurePriceFragment(
                            cid + "",
                            tot + "",
                            auctionID + "",
                            auctionKind + "",
                            auctionName + "",
                            auctionImage + ""
                    ));
                    //   localTimer.cancel();
                } else valueEdx.setError("اكتب القيمة");
            }
        });
    }

    public void checkIfAucFinished(int i) {
        executeTask();
        if (response0.body().getAuctionM().isFinishedfinal()) {
            Methods.saveIntegerSharedPref("TRUEFALSE", 1, getActivity());
            //localTimer.cancel();
            // mainTimerToServer.cancel();
        } else {
            Methods.saveIntegerSharedPref("TRUEFALSE", 0, getActivity());
        }
    }

    private void getCurrentAuction(String aucId, String cid) {
        Services api = API.getAPIS();
        Call<BiddingListM> listCall = api.getClosedAucDetails2(aucId, cid);
        listCall.enqueue(new Callback<BiddingListM>() {
            @Override
            public void onResponse(Call<BiddingListM> call, Response<BiddingListM> response) {
                if (!response.isSuccessful()) {
                }
                if (response.isSuccessful()) {
                    response0 = response;
                    BidderAuctionM res = response.body().getAuctionM();

                    //localTimer.cancel();
                    //callLocalTimer(Integer.valueOf(res.getDatetype()), res.getEnddate());

                    if (res.isUserCanBid()) {
                        sendPRiceBtn.setVisibility(View.VISIBLE);
                        sendReqest(
                                res.getId() + "",
                                res.getAuctionKind() + "",
                                res.getName() + "",
                                res.getImage() + ""
                        );
                    } else sendPRiceBtn.setVisibility(View.GONE);

                    timeToStOrFinTextView.setVisibility(View.VISIBLE);
                    sendPRiceBtn.setEnabled(true);

                    // Auction Still Working
                    if (res.isStarted() && !res.isFinishedfinal()) {
                        valueEdx.setEnabled(true);
                        timeToStOrFinTextView.setText(
                                res.getTimerMsg()
                        );
                        newTimer.setVisibility(View.VISIBLE);
                        // Auction Maybe Finshed So Request Server Again If Finished
                        if (res.getEnddate() == 0) {
                            //localTimer.cancel();
                            //executeTask();

                        }
                        sendPRiceBtn.setVisibility(View.VISIBLE);
                    }

                    // Auction Not Started Yet
                    if (!res.isStarted()) {
                        // resultsBtn.setVisibility(View.GONE);
                        timeToStOrFinTextView.setText("سيبدا المزاد بعد");
                        newTimer.setVisibility(View.VISIBLE);
                        sendPRiceBtn.setEnabled(false);
                        valueEdx.setEnabled(false);
                        sendPRiceBtn.setVisibility(View.GONE);
                    }
                    setViewData(res.getName(), res.getImage(),res.getAuctionKind()+"");


                    if (res.isFinishedfinal() && res.isStarted()) {
                        setEnabeldFalse();

                        if (res.getAuctionKind()==2) {
                            daysOrPriceTxt.setText(" الايام ");
                            //   aucPriceEdx.setText(res.getCurrent() + "");
                        } else {
                            daysOrPriceTxt.setText("السعر ");
                            // aucPriceEdx.setText(res.getCurrent() + "");
                        }

                        //localTimer.cancel();
                        timeToStOrFinTextView.setVisibility(View.GONE);
                        newTimer.setText(
                                "انتهى وقت المزاد المغلق"
                        );
                        newTimer.setTextSize(20);
                        try {
                            startActivity(new Intent(getActivity(), ResultsActivitty.class)
                                    .putExtra(Constants.KEY_POST_ID, res.getId() + "")
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            getActivity().finish();
                            return;
                        } catch (NullPointerException s) {
                        }

                    }

                    resultsBtn.setOnClickListener(view -> {
                        startActivity(new Intent(getActivity(), ResultsActivitty.class)
                                .putExtra(Constants.KEY_POST_ID, res.getId() + "")
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    });

                }
            }

            @Override
            public void onFailure(Call<BiddingListM> call, Throwable t) {
                executeTask();
            }
        });
    }

    private void setViewData(String auctionName, String auctionImage, String autionKind) {
        prodName.setText(auctionName);
        Utils.loadImage(productImage,"http://elkdeals.com/admin/uploads/",auctionImage);
        if (autionKind.equals("2")) {
            daysOrPriceTxt.setText(" الايام ");
            monthYearsLine.setVisibility(View.VISIBLE);

        } else {
            daysOrPriceTxt.setText("السعر ");
            monthYearsLine.setVisibility(View.GONE);
        }


        //if (response0.body().getAuctionM().isShowtimer()) {
        if (response0.body().getAuctionM().getDatetype() != null) {
            if (response0.body().getAuctionM().getDatetype().equals("4")) {
                newTimer.setText(response0.body().getAuctionM().getEnddate() + getActivity().getResources().getString(R.string.days));
            }
            if (response0.body().getAuctionM().getDatetype().equals("3")) {
                newTimer.setText(response0.body().getAuctionM().getEnddate() + getActivity().getResources().getString(R.string.hours));
            }
            if (response0.body().getAuctionM().getDatetype().equals("2")) {
                newTimer.setText(response0.body().getAuctionM().getEnddate() + getActivity().getResources().getString(R.string.minutes));
            }
            if (response0.body().getAuctionM().getDatetype().equals("1")) {
                try {
                    newTimer.setText(response0.body().getAuctionM().getEnddate() + getActivity().getResources().getString(R.string.seconds));
                } catch (NullPointerException f) {
                }
            }
        }
        // } else {
        //newTimer.setVisibility(View.GONE);
        //   }
    }

    private void callLocalTimer(int dateType1, int endDate1) {

        endDateForLocalTimer[0] = endDate1;
        localTimer = new Timer();

        switch (dateType1) {
            case 1:
                localTimer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        getActivity().runOnUiThread(new TimerTask() {
                            @Override
                            public void run() {
                                if (endDateForLocalTimer[0] != 0) {
                                    endDateForLocalTimer[0]--;
                                    newTimer.setText(endDateForLocalTimer[0] + "ثانيــة");
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
                        getActivity().runOnUiThread(new TimerTask() {
                            @Override
                            public void run() {
                                if (endDateForLocalTimer[0] != 0 && endDateForLocalTimer[0] != 3) {
                                    endDateForLocalTimer[0]--;
                                    newTimer.setText(endDateForLocalTimer[0] + "دقيـــقـة");
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
                        getActivity().runOnUiThread(new TimerTask() {
                            @Override
                            public void run() {
                                if (endDateForLocalTimer[0] != 0) {
                                    endDateForLocalTimer[0]--;
                                    newTimer.setText(endDateForLocalTimer[0] + "ســــاعة");
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

    private void setEnabeldFalse() {
        sendPRiceBtn.setEnabled(false);
        valueEdx.setEnabled(false);
        sendPRiceBtn.setVisibility(View.GONE);
        // daysOrPriceTxt.setVisibility(View.GONE);
    }

    private void executeTask() {
        getCurrentAuction(auctionID, cid);
    }

    @Override
    public void onStop() {
        super.onStop();
        // mainTimerToServer.cancel();
        //localTimer.cancel();
    }

    @Override
    public void onResume() {
        super.onResume();
        executeTask();
    }

    @Override
    public void initViews() {

    }

    @Override
    public void onPause() {
        super.onPause();
        //mainTimerToServer.cancel();
        //localTimer.cancel();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // mainTimerToServer.cancel();
        //localTimer.cancel();
    }


    @Override
    public String getTAG() {
        return "SEcNAwAucFragment";
    }
}
