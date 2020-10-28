package com.elkdeals.mobile.ui.splash;


import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.TimeType;
import com.elkdeals.mobile.Utils.TimerCounter;
import com.elkdeals.mobile.Utils.Utils;
import com.elkdeals.mobile.api.models.UserModel;
import com.elkdeals.mobile.model.Repository;
import com.elkdeals.mobile.ui.BaseFragment;
import com.elkdeals.mobile.viewmodel.LoginViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.elkdeals.mobile.Utils.Utils.hideSoftKeyboard;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhoneConfirmCode extends BaseFragment {
    public static final String TAG = "Confirmation Code";

    @BindView(R.id.code)
    EditText code;
    @BindView(R.id.timer)
    TextView timer;
    LoginViewModel loginViewModel;
    public PhoneConfirmCode() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return view = inflater.inflate(R.layout.fragment_phone_confirm_code, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void initViews() {
        loginViewModel= ViewModelProviders.of(this).get(LoginViewModel.class);
        loginViewModel.getProgress().observe(this, s -> {
            if (!TextUtils.isEmpty(s))
                activity.showProgressBar(s);
            else activity.hideDialog();
        });
        loginViewModel.getMessage().observe(this, s -> activity.showMessageDialog(s));
        loginViewModel.getUser().observe(this, mobileModel -> {
            if (mobileModel != null && mobileModel.getStatus())
                if (mobileModel.isNeedcode()) {
                    Log.e("needCode : ","confirm : "+mobileModel.isNeedcode()+" : "+mobileModel.getMsg());
                    startTimer();
                }else {
                    activity.home();
                }
        });

        code.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    hideSoftKeyboard(v,activity);
            }
        });
        loginViewModel.getOpenRegister().observe(this, phone -> activity.removeTopFragment());
        if(TextUtils.isEmpty(Utils.getvCode())|| Utils.getvCode().equals("0"))
            resendCode();
        else startTimer();
    }
    TimerCounter timerCounter;
    public void startTimer() {
        timer.setText("00:00:00");
        firstClick=true;
        if (timerCounter!=null)
            timerCounter.cancel();
        timerCounter  =new TimerCounter(21, TimeType.Second, time1 -> {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    timer.setText(time1);
                }
            });
        }, new TimerCounter.OnTimerFinished() {
            @Override
            public void onfinished(String time) {
                firstClick=false;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("startTimer","startTimer finished");
                        timer.setText(R.string.resend_code);
                        timer.setClickable(true);
                    }
                });
            }
        });
        timerCounter.setNumberFormat(true);
        timerCounter.startTimer();
    }
    boolean firstClick=false;
    @OnClick(R.id.timer)
    public void resendCode()
    {
        if(!firstClick){
        loginViewModel.login(Utils.phone, Utils.getImei());
            startTimer();
        }
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @OnClick(R.id.confirm)
    public void confirmPhone() {
        Log.e("vcode", Utils.getvCode()+" code");
        Log.e("mcode",code.getText().toString()+" code");
        if(code.getText().toString().equals(Utils.getvCode())){

            new UserModel(Repository.getUserInfo(), Utils.userId).save();
            activity.home();
        }else {
            code.setError("الكود غير متطابق");
        }
    }
}
