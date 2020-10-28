package com.elkdeals.mobile.dialogFragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.api.API;
import com.elkdeals.mobile.api.Services;
import com.elkdeals.mobile.api.models.TrueMsg;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mohamed on 8/1/17.
 */

public class RateAuctiontDialog extends androidx.fragment.app.DialogFragment {

    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.commentEdx)
    EditText commentEdx;
    @BindView(R.id.addComment)
    Button addComment;

    String rate, comment, auid, cid;

    @SuppressLint("ValidFragment")
    public RateAuctiontDialog( String auid, String cid) {
        this.auid = auid;
        this.cid = cid;
    }

    public RateAuctiontDialog() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.rate_auc_dialog, null);
        builder.setView(view);
        ButterKnife.bind(this, view);
        getData();
        return builder.create();
    }

    private void getData() {

        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comment = commentEdx.getText().toString();
                rate = (int) ratingBar.getRating() + "";
                if (comment == null)
                    comment = "no comment";
                if (rate == null)
                    rate = "0";

                sendRate(auid, cid, comment, rate);
            }
        });
    }

    private void sendRate(String auid, String cid, String comment, String rate) {
        Services api = API.getAPIS();
        Call<TrueMsg> call = api.sendRate(auid, cid, comment, rate);
        call.enqueue(new Callback<TrueMsg>() {
            @Override
            public void onResponse(Call<TrueMsg> call, Response<TrueMsg> response) {
                getDialog().dismiss();
                if (!response.isSuccessful()) {
                } else if (response.isSuccessful()) {
                    if (response.body().isStatus())
                        Toast.makeText(getActivity(), "شكرا لتقييمك! ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TrueMsg> call, Throwable t) {
                getDialog().dismiss();
            }
        });
    }
}
