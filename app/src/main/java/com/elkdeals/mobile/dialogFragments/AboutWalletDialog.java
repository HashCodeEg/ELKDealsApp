package com.elkdeals.mobile.dialogFragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.api.API;
import com.elkdeals.mobile.api.Services;
import com.elkdeals.mobile.api.models.AboutWalletM;
import com.elkdeals.mobile.api.models.TrueMsg;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mohamed on 8/1/17.
 */

public class AboutWalletDialog extends DialogFragment {

    @BindView(R.id.textAbout)
    TextView textAbout;

    String terms;

    public AboutWalletDialog() {

    }

    @SuppressLint("ValidFragment") public AboutWalletDialog(String terms) {
        this.terms = terms;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.wallet_about_dialog, null);
        builder.setView(view);
        ButterKnife.bind(this, view);
        if (terms == null) {
            _about();
        } else {
            if (terms.equals(""))
                _about();
            else
                _termsAndConditions();
        }

        return builder.create();
    }

    private void _about() {
        Services api = API.getAPIS();
        Call<AboutWalletM> call = api.getAboutwallet();
        call.enqueue(new Callback<AboutWalletM>() {
            @Override
            public void onResponse(Call<AboutWalletM> call, Response<AboutWalletM> response) {
                if (!response.isSuccessful()) {
                } else if (response.isSuccessful()) {
                    textAbout.setText(response.body().getAbout() + "");
                }
            }

            @Override
            public void onFailure(Call<AboutWalletM> call, Throwable t) {

            }
        });
    }

    private void _termsAndConditions() {
        Services api = API.getAPIS();
        Call<TrueMsg> call = api.termsAndConditions();
        call.enqueue(new Callback<TrueMsg>() {
            @Override
            public void onResponse(Call<TrueMsg> call, Response<TrueMsg> response) {
                if (!response.isSuccessful()) {
                } else if (response.isSuccessful()) {
                    if (response.body().isStatus())
                        textAbout.setText(response.body().getMsg() + "");
                }
            }

            @Override
            public void onFailure(Call<TrueMsg> call, Throwable t) {

            }
        });
    }


}
