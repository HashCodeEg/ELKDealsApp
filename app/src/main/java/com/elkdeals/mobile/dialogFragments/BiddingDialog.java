package com.elkdeals.mobile.dialogFragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.activities.BaseActivity;
import com.elkdeals.mobile.api.API;
import com.elkdeals.mobile.api.Services;
import com.elkdeals.mobile.api.models.SendPriceModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mohamed on 8/1/17.
 */

public class BiddingDialog extends DialogFragment {


    @BindView(R.id.daysOrPriceTxt)
    TextView daysOrPriceTxt;
    @BindView(R.id.aucPriceEdx)
    EditText aucPriceEdx;
    @BindView(R.id.aucPriceEdxAgain)
    EditText aucPriceEdxAgain;
    @BindView(R.id.bidBtn)
    Button bidBtn;

    String auctionId;
    String cid;
    String aucKind;
    String currentPrice;
    String BASE_URL;


    String priceOrDay;
    String priceOrDay2;
    int cPrice, nPrice, verfVal;

    public BiddingDialog() {

    }

    @SuppressLint("ValidFragment")
    public BiddingDialog(String auctionId, String currentPrice, String cid, String aucKind,String BASE_URL) {
        this.aucKind = aucKind;
        this.auctionId = auctionId;
        this.currentPrice = currentPrice;
        this.cid = cid;
        this.BASE_URL = BASE_URL;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.bidding_dialog, null);
        builder.setView(view);
        ButterKnife.bind(this, view);

        if (aucKind.equals("2")) {
            daysOrPriceTxt.setText("عدد الايام التى تريد ارسالها");
            //  aucPriceEdx.setHint("الايام السابقة "+currentPrice);
        } else {
            daysOrPriceTxt.setText("السعر الذى تريد ارساله");
            // aucPriceEdx.setHint("السعر السابق "+currentPrice);
        }


        bidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateEdx()) {
                    priceOrDay = aucPriceEdx.getText().toString();
                    cPrice = new Integer(currentPrice);
                    nPrice = new Integer(priceOrDay);
                    verfVal = new Integer(aucPriceEdxAgain.getText().toString());
                    if (nPrice > cPrice) {
                        if (verfVal == nPrice)
                            sendPrice(auctionId, cid, nPrice + "");
                        else aucPriceEdxAgain.setError("برجاء تاكيد القيمة");
                    } else aucPriceEdx.setError("برجاء اضافة قيمة اعلى من القيمة الحاليه");
                }
            }
        });

        return builder.create();
    }

    private boolean validateEdx() {

        priceOrDay = aucPriceEdx.getText().toString();
        priceOrDay2 = aucPriceEdxAgain.getText().toString();

        return chekEmpty(priceOrDay, aucPriceEdx) && chekEmpty(priceOrDay2, aucPriceEdxAgain);
    }

    private boolean chekEmpty(String txt, EditText edx) {
        if (txt != null)
            if (!txt.trim().equals("")) {
                return true;
            } else {
                edx.setError("اضف قيمة");
                return false;
            }
        else {
            edx.setError("اضف قيمة");
            return false;
        }
    }

    public void sendPrice(final String postID, final String userID, final String val) {
        BaseActivity.instance.showProgressBar();
        Services api = API.getAPIS(BASE_URL);
        Call<SendPriceModel> call = api.sendYourPriceClosedAuc(postID, userID, val);
        call.enqueue(new Callback<SendPriceModel>() {
            @Override
            public void onResponse(Call<SendPriceModel> call, Response<SendPriceModel> response) {
                BaseActivity.instance.hideDialog();
                if (response.isSuccessful()) {
                    Toast.makeText(getActivity(), "تم الارسال بنجاح", Toast.LENGTH_SHORT).show();
                    getDialog().dismiss();
                    // getActivity().recreate();
                    // return;

                }
            }

            @Override
            public void onFailure(Call<SendPriceModel> call, Throwable t) {
                BaseActivity.instance.hideDialog();
            }
        });
    }


}
