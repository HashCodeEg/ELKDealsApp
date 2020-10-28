package com.elkdeals.mobile.dialogFragments;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.Utils;
import com.elkdeals.mobile.activities.BaseActivity;
import com.elkdeals.mobile.interfaces.HasTag;
import com.elkdeals.mobile.viewmodel.UserViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BalanceTransfer extends DialogFragment implements HasTag {
    public static final String TAG = "BalanceTransfer";
    @BindView(R.id.amount)
    EditText amount;
    @BindView(R.id.wallet_id)
    EditText walletId;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    UserViewModel model;
    private View view;

    boolean loading=false;
    public BalanceTransfer() {
    }

    @Nullable

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.customdialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window == null) return;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams windowParams = window.getAttributes();
        window.setAttributes(windowParams);

    }
    @OnClick(R.id.transfer)
    public void negative(View view) {
        if(loading)
            return;
        String walletNumber = walletId.getText().toString();
        String amountValue = amount.getText().toString();
        model.sendMoney(amountValue, walletNumber);
        loading=true;
        progressBar.setVisibility(View.VISIBLE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_transfer, container, false);
        try {
            if (getDialog().getWindow() != null) {
                getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            }
        } catch (Exception ignored) {
        }
        initViews();
        return view;
    }

    private void initViews() {
        ButterKnife.bind(this, view);
        model = ViewModelProviders.of(this).get(UserViewModel.class);
        model.getTransferModel().observe(this, trueMsg -> {
            loading=false;
            progressBar.setVisibility(View.GONE);
            if (trueMsg != null)
                if (trueMsg.isStatus())
                    ((BaseActivity) getActivity()).showMessageDialog(Utils.getStringRes(R.string.transfer_successful));
                else
                    ((BaseActivity) getActivity()).showMessageDialog(Utils.getStringRes(R.string.transfer_failed) + trueMsg.getMsg());

        });

    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
