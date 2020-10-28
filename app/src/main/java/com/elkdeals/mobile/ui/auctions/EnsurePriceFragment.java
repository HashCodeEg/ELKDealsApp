package com.elkdeals.mobile.ui.auctions;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.Constants;
import com.elkdeals.mobile.activities.ResultsActivitty;
import com.elkdeals.mobile.api.API;
import com.elkdeals.mobile.api.Services;
import com.elkdeals.mobile.api.models.SecAucWay;
import com.elkdeals.mobile.dialogFragments.AboutWalletDialog;
import com.elkdeals.mobile.ui.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("ValidFragment")
public class EnsurePriceFragment extends BaseFragment {
    @BindView(R.id.prodName)
    TextView prodName;
    @BindView(R.id.daysOrPriceValTxt)
    TextView daysOrPriceValTxt;
    @BindView(R.id.daysOrPriceTxt)
    TextView daysOrPriceTxt;
    @BindView(R.id.yearsNum)
    TextView yearsNum;
    @BindView(R.id.monthsNum)
    TextView monthsNum;
    @BindView(R.id.monthlyInstaTxt)
    TextView monthlyInstaTxt;
    @BindView(R.id.max_bid_label)
    TextView max_bid_label;
    @BindView(R.id.totalProductVal)
    TextView totalProductVal;
    @BindView(R.id.maxValeTxt)
    TextView maxValeTxt;
    @BindView(R.id.cashValue)
    TextView cashValue;
    @BindView(R.id.cashValueRewrit)
    EditText cashValueRewrit;
    @BindView(R.id.termsAndCondition)
    TextView termsAndCondition;
    @BindView(R.id.termsBox)
    CheckBox termsBox;
    @BindView(R.id.sendPRiceBtn)
    Button sendPRiceBtn;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    String cid, tot, aucId, auctionKind, auctionName, auctionImg;

    String cashS;
    int cash;

    @SuppressLint("ValidFragment")
    public EnsurePriceFragment(String cid, String tot, String aucId, String auctionKind, String auctionName, String auctionImg) {
        this.cid = cid;
        this.tot = tot;
        this.aucId = aucId;
        this.auctionKind = auctionKind;
        this.auctionName = auctionName;
        this.auctionImg = auctionImg;
    }

    @SuppressLint("ValidFragment") public EnsurePriceFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ensure_sec_new_auction, container, false);
        ButterKnife.bind(this, view);
        sendTot(cid, tot, aucId);
         sendPRiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (termsBox.isChecked()) {
                    cashS = cashValueRewrit.getText().toString();
                    if (cashS == null) {
                        cashValueRewrit.setError("اكتب قيمة الكاش");
                    } else {
                        if (!cashS.equals("")) {
                            if (cashS.equals(cashValue.getText().toString())) {
                                sendFinalPrice(cid, tot, aucId);
                            } else {
                                cashValueRewrit.setError("الرقم غير مطابق للكاش");
                            }
                        } else cashValueRewrit.setError("اكتب قيمة الكاش");
                    }
                } else {
                    Toast.makeText(getActivity(), "برجاء الموافقه على الشروط والاحكام", Toast.LENGTH_SHORT).show();
                }
            }
        });

        termsAndCondition.setOnClickListener(view1 -> {
            DialogFragment dialogFragment = new AboutWalletDialog("terms");
            dialogFragment.show(getActivity().getFragmentManager(), "AboutWalletDialog");
        });

        return view;
    }

    private void sendTot(String cid, String tot, String aucId) {
        // progressBar.setVisibility(View.VISIBLE);
        Services api = API.getAPIS();
        Call<SecAucWay> call = api.sendTot(aucId + "", tot + "", cid + "");
        call.enqueue(new Callback<SecAucWay>() {
            @Override
            public void onResponse(Call<SecAucWay> call, Response<SecAucWay> response) {
                if (!response.isSuccessful()) {
                  /*  Toast.makeText(getActivity(), "القيمة غير صحيحه", Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStack();*/
                } else if (response.isSuccessful()) {
                    if (response.body().getStatus()) {
                        setData(response.body());
                    } else {

                        activity.showMessageDialog("عــفـــوا",
                                response.body().getMsg() + ""+ "تــــم",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                        getFragmentManager().popBackStack();
                                        activity.hideDialog();
                                    }
                                },new View.OnClickListener(){
                                    @Override
                                    public void onClick(View v) {

                                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                        getFragmentManager().popBackStack();
                                        activity.hideDialog();
                                    }
                                });

                    }
                }
                //  progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<SecAucWay> call, Throwable t) {
                //progressBar.setVisibility(View.GONE);

               /* try {
                    Toast.makeText(getActivity(), "القيمة غير صحيحه", Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStack();
                }catch (NullPointerException d){}*/

            }
        });
    }

    private void setData(SecAucWay data) {
        if (auctionKind.equals("2")) {
            max_bid_label.setText("الحد الاقصى لعدد الايام");
            daysOrPriceTxt.setText("عدد الايـــــام");
        } else {daysOrPriceTxt.setText("الســــعـــر");
            max_bid_label.setText("الحد الاقصى للقسط الشهرى");
        }

        daysOrPriceValTxt.setText(data.getDays() + "");
        prodName.setText(data.getName());
        yearsNum.setText(data.getYears() + "");
        monthsNum.setText(data.getMonths() + "");
        monthlyInstaTxt.setText(data.getInstall() + "");
        totalProductVal.setText(data.getPrice() + "");
        maxValeTxt.setText(data.getMax() + "");
        cashValue.setText(data.getCash() + "");
        if (data.getCash() == 0)
            cashValueRewrit.setText("0");
    }

    private void sendFinalPrice(String cid, String tot, String aucId) {
        progressBar.setVisibility(View.VISIBLE);
        Services api = API.getAPIS();
        Call<SecAucWay> call = api.sendEnsureTot(aucId + "", tot + "", cid + "");
        call.enqueue(new Callback<SecAucWay>() {
            @Override
            public void onResponse(Call<SecAucWay> call, Response<SecAucWay> response) {
                if (!response.isSuccessful()) {
                } else if (response.isSuccessful()) {
                    if (response.body().getStatus()) {
                        Toast.makeText(getActivity(), "تم بنجاح ", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), ResultsActivitty.class)
                                .putExtra(Constants.KEY_POST_ID + "", aucId + "")
                                .putExtra("KIND", auctionKind + "")
                                .putExtra("NAME", auctionName + "")
                                .putExtra("Image", auctionImg + "")
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        );
                        getActivity().finish();
                    } else {
                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), ResultsActivitty.class)
                                .putExtra(Constants.KEY_POST_ID + "", aucId + "")
                                .putExtra("KIND", auctionKind + "")
                                .putExtra("NAME", auctionName + "")
                                .putExtra("Image", auctionImg + "")
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        );
                        getActivity().finish();
                    }
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<SecAucWay> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
    @Override
    public void initViews() {

    }

    @Override
    public String getTAG() {
        return "ensureprice";
    }

}
