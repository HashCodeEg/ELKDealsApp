package com.elkdeals.mobile.ui.account;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.adapters.BillsAdapter;
import com.elkdeals.mobile.api.models.DeleteInvResponse;
import com.elkdeals.mobile.api.models.InvoicesModel;
import com.elkdeals.mobile.dialogFragments.PaymenyDialog;
import com.elkdeals.mobile.ui.BaseFragment;
import com.elkdeals.mobile.interfaces.OnItemClickListener;
import com.elkdeals.mobile.model.Repository;
import com.elkdeals.mobile.viewmodel.UserViewModel;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Bills extends BaseFragment {


    public static final String TAG = "Bills";
    @BindView(R.id.recycler)
    public RecyclerView recyclerView;
    public BillsAdapter adapter;
    private UserViewModel model;
    private PaymenyDialog dialogFragment;

    public Bills() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return view = inflater.inflate(R.layout.fragment_bills, container, false);
    }


    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public void initViews() {
        ///////////////////////
        if (getActivity() != null)
            model = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
        else model = ViewModelProviders.of(activity).get(UserViewModel.class);
        model.getProgress().observe(this, s -> {
            if (!TextUtils.isEmpty(s))
                activity.showProgressBar(s);
            else activity.hideDialog();
        });
        model.getMessage().observe(this, s -> activity.showMessageDialog(s));
        model.getUserInvoicesLiveData().observe(this, invoicesModels ->
                adapter.addItems(invoicesModels));
        model.getDeleteInvResponse().observe(this, new Observer<DeleteInvResponse>() {
            @Override
            public void onChanged(DeleteInvResponse deleteInvResponse) {
                model.getUserInVoices();
            }
        });
        ///////////////////////
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new BillsAdapter(new OnItemClickListener() {
            @Override
            public void OnItemClick(Object data) {
                activity.addPage(BillDetails.createInstance((InvoicesModel)data, Repository.getUserInfo()));
//                InvoicesModel invoice= (InvoicesModel) data;
//                try {
//                    if (dialogFragment != null) {
//                        if (dialogFragment.isVisible())
//                            dialogFragment.dismiss();
//                        dialogFragment = null;
//                        dialogFragment = PaymenyDialog.CreatePaymentDialog(invoice, Repository.getUserInfo());
//                    } else {
//                        dialogFragment = PaymenyDialog.CreatePaymentDialog(invoice, Repository.getUserInfo());
//                    }
//                } catch (Exception ignored) {
//                    dialogFragment = PaymenyDialog.CreatePaymentDialog(invoice, Repository.getUserInfo());
//                }
//                dialogFragment.show(activity.getSupportFragmentManager(), dialogFragment.getTAG());
            }
        }, new OnItemClickListener() {
            @Override
            public void OnItemClick(Object data) {
                model.deleteUserInVoices(((InvoicesModel)data).getId());
            }
        });
        recyclerView.setAdapter(adapter);
        model.getUserInVoices();
    }
}
