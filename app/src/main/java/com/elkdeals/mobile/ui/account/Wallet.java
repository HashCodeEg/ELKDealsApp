package com.elkdeals.mobile.ui.account;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.Utils;
import com.elkdeals.mobile.adapters.WalletAdapter;
import com.elkdeals.mobile.api.models.HistoryM;
import com.elkdeals.mobile.api.models.WalletM;
import com.elkdeals.mobile.custom.CustomLinearLayoutManager;
import com.elkdeals.mobile.custom.NestedScrollView;
import com.elkdeals.mobile.dialogFragments.BalanceTransfer;
import com.elkdeals.mobile.ui.BaseFragment;
import com.elkdeals.mobile.interfaces.OnItemClickListener;
import com.elkdeals.mobile.viewmodel.UserViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * A simple {@link Fragment} subclass.
 */
public class Wallet extends BaseFragment {


    public static final String TAG = "Wallet";
    @BindView(R.id.view_more)
    public View viewMore;
    @BindView(R.id.balance)
    public TextView balance;
    @BindView(R.id.wallet_id)
    public TextView wallet_id;
    @BindView(R.id.scrollView)
    public NestedScrollView scrollView;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    BalanceTransfer transfer_dialog;
    private CustomLinearLayoutManager manager;
    private RecyclerView.SmoothScroller smoothScroller;
    private WalletAdapter adapter;


    UserViewModel model;

    public Wallet() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return view = inflater.inflate(R.layout.fragment_wallet, container, false);
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public void initViews() {
        model = ViewModelProviders.of(this).get(UserViewModel.class);
        model.getProgress().observe(this, s -> {
            if (!TextUtils.isEmpty(s))
                activity.showProgressBar(s);
            else activity.hideDialog();
        });
        model.getMessage().observe(this, s -> activity.showMessageDialog(s));

        manager = new CustomLinearLayoutManager(activity);
        recyclerView.setLayoutManager(manager);
        smoothScroller = new LinearSmoothScroller(activity) {
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };
        adapter = new WalletAdapter(new OnItemClickListener() {
            @Override
            public void OnItemClick(Object data) {
                activity.showToast(R.string.name_activity_notifications);
            }
        });
        adapter.setOnViewLessClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(Object data) {

                Utils.showView(viewMore);
                scrollView.smoothScrollTo(0, 0);
                manager.setScrollEnabled(false);
                scrollView.setEnableScrolling(false);
            }
        });
        // adapter.addItems(Notification.getDummy());
        recyclerView.setAdapter(adapter);
        manager.setScrollEnabled(false);
        model.getWalletMMutableLiveData().observe(this, new Observer<WalletM>() {
            @Override
            public void onChanged(WalletM walletM) {
                if (walletM != null && walletM.isStatus()) {
                    balance.setText(walletM.getAmount() + "");
                    wallet_id.setText(walletM.getId() + "");
                } else {
                    balance.setText("0");
                    wallet_id.setText(R.string.have_no_wallet);
                }
            }
        });
        model.getWallet();
        model.getWalletMListMutableLiveData().observe(this, new Observer<List<HistoryM>>() {
            @Override
            public void onChanged(List<HistoryM> historyMS) {
                adapter.addItems(historyMS);
            }
        });
        model.getWalletList();

    }

    @OnClick(R.id.transfer_dialog)
    public void showTransferDialog() {
        try {
            if (TextUtils.isEmpty(balance.getText().toString())
                    || balance.getText().toString().equals(Utils.getStringRes(R.string.loading))
                    || balance.getText().toString().equals("0")) {
                activity.showToast(R.string.have_no_balance);
                return;
            }
            if (transfer_dialog != null) {
                if (transfer_dialog.isVisible())
                    transfer_dialog.dismiss();
                transfer_dialog = null;
                transfer_dialog = new BalanceTransfer();
            } else {
                transfer_dialog = new BalanceTransfer();
            }
        } catch (Exception ignored) {
            transfer_dialog = new BalanceTransfer();
        }
        transfer_dialog.show(activity.getSupportFragmentManager(), transfer_dialog.getTAG());
    }

    @Optional
    @OnClick(R.id.view_more)
    public void viewMore(View view) {
        manager.setScrollEnabled(true);
        scrollView.smoothScrollTo(0, scrollView.getHeight());
        scrollView.setEnableScrolling(false);
        if (view.getVisibility() == View.VISIBLE)
            Utils.hideView(view);
        else Utils.showView(view);
    }
}
