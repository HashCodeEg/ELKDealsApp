package com.elkdeals.mobile.dialogFragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.activities.BaseActivity;
import com.elkdeals.mobile.adapters.SpinnerNumbersAdapter;
import com.elkdeals.mobile.api.models.requests.RequestDetails;
import com.elkdeals.mobile.interfaces.HasTag;
import com.elkdeals.mobile.model.RepositorySidalitac;
import com.elkdeals.mobile.viewmodel.RequestsViewModelSidalitac;
import com.elkdeals.mobile.viewmodel.UserViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SendOffer extends DialogFragment implements HasTag {
    public static final String TAG = "BalanceTransfer";
    @BindView(R.id.price)
    EditText price;
    @BindView(R.id.shippingPrice)
    EditText shippingPrice;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.new_status)
    CheckBox new_status;
    @BindView(R.id.used_status)
    CheckBox used_status;
    UserViewModel model;
    private View view;
    private RequestDetails requestDetails;
    SpinnerNumbersAdapter adapter;
    RequestsViewModelSidalitac requestsViewModelSidalitac;
    public SendOffer() {
    }

    public static SendOffer createInstance(RequestDetails requestDetails) {
        SendOffer sendOffer=new SendOffer();
        sendOffer.requestDetails=requestDetails;
        return sendOffer;
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
        String amountValue = price.getText().toString();
        if(TextUtils.isEmpty(amountValue))
        {
            ((BaseActivity)getActivity()).showMessageDialog("يجب ادخال السعر!");
            return;
        }if(TextUtils.isEmpty(shippingPrice.getText().toString()))
        {
            ((BaseActivity)getActivity()).showMessageDialog("يجب ادخال مصاريف الشحن!");
            return;
        }
        String time=(spinner.getSelectedItemPosition()+1)+"";
        Log.e("transferadapter",time);
        String newStatus="0";
        if(new_status.isChecked())
            newStatus="1";

        Log.e("new_status",newStatus);
        requestsViewModelSidalitac.addOffer(
                RepositorySidalitac.getUserInfo().getData().get(0).getCustomersId(),
                requestDetails.getRequest_id(),
                price.getText().toString(),
                time,
                shippingPrice.getText().toString(),
                newStatus);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_send_offer, container, false);
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
        requestsViewModelSidalitac= ViewModelProviders.of(this).get(RequestsViewModelSidalitac.class);
        requestsViewModelSidalitac.getProgress().observe(this, s -> {
            if (!TextUtils.isEmpty(s))
                ((BaseActivity)getActivity()).showProgressBar(s);
            else ((BaseActivity)getActivity()).hideDialog();
        });
        requestsViewModelSidalitac.getMessage().observe(this, s -> ((BaseActivity)getActivity()).showMessageDialog(s));
        adapter=new SpinnerNumbersAdapter(getActivity(), R.layout.spinner_item, R.layout.dropdown_item_editbox,1,30);
        spinner.setAdapter(adapter);
        new_status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(used_status.isChecked()&&isChecked){
                    used_status.setChecked(false);
                }else if(!used_status.isChecked()&&!isChecked)
                {
                    ((BaseActivity)getActivity()).showToast("يجب اختيار احدى الحالات");
                    used_status.setChecked(true);
                }
            }
        });
        used_status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(new_status.isChecked()&&isChecked){
                    new_status.setChecked(false);
                }else if(!new_status.isChecked()&&!isChecked)
                {
                    ((BaseActivity)getActivity()).showToast("يجب اختيار احدى الحالات");
                    new_status.setChecked(true);
                }
            }
        });
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
