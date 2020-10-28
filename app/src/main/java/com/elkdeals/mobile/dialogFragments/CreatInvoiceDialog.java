package com.elkdeals.mobile.dialogFragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.Methods;
import com.elkdeals.mobile.activities.BaseActivity;
import com.elkdeals.mobile.api.API;
import com.elkdeals.mobile.api.Services;
import com.elkdeals.mobile.api.models.InvoiceAddResponse2;
import com.elkdeals.mobile.api.models.InvoicesModel;
import com.elkdeals.mobile.ui.account.BillDetails;
import com.elkdeals.mobile.model.Repository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mohamed on 9/30/17.
 */

@SuppressLint("ValidFragment")
public class CreatInvoiceDialog extends DialogFragment {
    TextView text;
    TextView creatInvText22;
    Button creatInvBtn;
    Button subscripInAllBtn;
    String auctionID = "";
    String cateId = "";
    String price = "";
    String desc = "";

    @SuppressLint("ValidFragment")
    public CreatInvoiceDialog(String auctionID, String price, String cateId, String desc) {
        this.auctionID = auctionID;
        this.cateId = cateId;
        this.price = price;
        this.desc = desc;

    }

    public CreatInvoiceDialog() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.create_inv_dialog, null);
            text = view.findViewById(R.id.creatInvText);
            creatInvText22 = view.findViewById(R.id.creatInvText22);
            creatInvBtn = view.findViewById(R.id.creatInvBtn);
            subscripInAllBtn = view.findViewById(R.id.subscripInAllBtn);
            builder.setView(view);
            builder.setCancelable(false);

            String savedPrice = Methods.getFromSharedPref("PRICE", getActivity());
            if (savedPrice != null)
                if (!savedPrice.equals(""))
                    price = savedPrice;

            text.setText("رسم دخول المزاد " + "" + price + "جنيه");
            creatInvBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    creatInvoive(Repository.getUserInfo().getId(), auctionID + "");
                }
            });
            subscripInAllBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    creatInvoiveAll(Repository.getUserInfo().getId(), cateId);
                }
            });
            return builder.create();
        } catch (InstantiationException e) {
            return null;
        }


    }

    private void creatInvoive(String cid, String aid) {
        if(getActivity() instanceof BaseActivity)
            ((BaseActivity) getActivity()).showProgressBar("Loading...");
        Services api = API.getAPIS();
        Call<InvoiceAddResponse2> call = api.addInvTimeLine(cid, aid);
        call.enqueue(new Callback<InvoiceAddResponse2>() {
            @Override
            public void onResponse(Call<InvoiceAddResponse2> call, Response<InvoiceAddResponse2> response) {

                if(getActivity() instanceof BaseActivity)
                    ((BaseActivity) getActivity()).hideDialog();
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                } else if (response.isSuccessful()) {
                    /*DialogFragment dialogFragment = new IfUhveQDialog("PAYMENT", "", response.body().getId() + "");
                    dialogFragment.show(((Activity) getActivity()).getFragmentManager(), "IfUhveQDialog");*/
                    //startActivity(new Intent(getActivity(), ActivityForLists.class).putExtra("WHO", "AllInv"));
                    setData(response.body());
                }
            }

            @Override
            public void onFailure(Call<InvoiceAddResponse2> call, Throwable t) {
                if(getActivity() instanceof BaseActivity)
                    ((BaseActivity) getActivity()).hideDialog();
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void creatInvoiveAll(String cid, String catedid) {
        if(getActivity() instanceof BaseActivity)
            ((BaseActivity) getActivity()).showProgressBar("Loading...");
        Services api = API.getAPIS();
        Call<InvoiceAddResponse2> call = api.addAllInvTimeLine(cid, catedid);
        call.enqueue(new Callback<InvoiceAddResponse2>() {
            @Override
            public void onResponse(Call<InvoiceAddResponse2> call, Response<InvoiceAddResponse2> response) {
                if (!response.isSuccessful()) {

                } else if (response.isSuccessful()) {
                   /* DialogFragment dialogFragment = new IfUhveQDialog("PAYMENT", "", response.body().getId() + "");
                    dialogFragment.show(getFragmentManager(), "IfUhveQDialog");*/
                    //startActivity(new Intent(getActivity(), ActivityForLists.class).putExtra("WHO", "AllInv"));
                    setData(response.body());
                }
                if(getActivity() instanceof BaseActivity)
                    ((BaseActivity) getActivity()).hideDialog();
            }

            @Override
            public void onFailure(Call<InvoiceAddResponse2> call, Throwable t) {
                if(getActivity() instanceof BaseActivity)
                    ((BaseActivity) getActivity()).hideDialog();
            }
        });
    }

    private void sendInvoiceID(InvoicesModel post) {
        try{
        ((BaseActivity)getActivity()).addPage(BillDetails.createInstance(post, Repository.getUserInfo()));
        dismiss();}catch (Exception e){

        }
//        Intent intent = new Intent(getActivity(), InvoiceDetail.class);
//        Bundle bundle = new Bundle();
//
//        InvoicesModelSerialized invoicesModelSerialized = new InvoicesModelSerialized(
//                post.getId(),
//                post.getNumber(),
//                post.getDate(),
//                post.getRate(),
//                post.getTotal(),
//                post.getPtype(),
//                post.getStatus(),
//                post.getBkid(),
//                post.getAccountnumber(),
//                post.getBranch(),
//                post.getBkname(),
//                post.getDiscount()
//        );
//        bundle.putString("WHO", "ALLINV");
//        bundle.putString("invid", post.getId() + "");
//        bundle.putSerializable("OBJ", invoicesModelSerialized);
//        intent.putExtras(bundle);
//        startActivity(intent);
    }

    private void setData(InvoiceAddResponse2 response) {
        InvoicesModel model = new InvoicesModel();
        model.setAccountnumber(response.getAccountnumber());
        model.setBkid(response.getBkid());
        model.setBkname(response.getBkname());
        model.setBranch(response.getBranch());
        model.setDate("d/d/d");
        model.setDiscount((int) response.getDiscount());
        model.setId((int) response.getId());
        model.setNumber(0);
        model.setTotal((int) response.getTotal());
        model.setRate((int) response.getRate());
        model.setStatus((int) response.getStatus());
        model.setPtype((int) response.getPtype());
        sendInvoiceID(model);
    }
}
