package com.elkdeals.mobile.ui.splash;


import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.Utils;
import com.elkdeals.mobile.api.models.UserModel;
import com.elkdeals.mobile.dialogFragments.ConfirmationDialog;
import com.elkdeals.mobile.model.Repository;
import com.elkdeals.mobile.ui.BaseFragment;
import com.elkdeals.mobile.viewmodel.LoginViewModel;

import butterknife.BindView;
import butterknife.OnClick;

import static com.elkdeals.mobile.Utils.Utils.hideSoftKeyboard;

/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends BaseFragment {
    public final static String TAG = "Login";
    @BindView(R.id.mobile)
    public EditText phone;
    LoginViewModel model;
    private ConfirmationDialog dialog;

    public Login() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return view = inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public void initViews() {
        model = ViewModelProviders.of(this).get(LoginViewModel.class);
        model.getProgress().observe(this, s -> {
            if (!TextUtils.isEmpty(s))
                activity.showProgressBar(s);
            else activity.hideDialog();
        });
        model.getMessage().observe(this, s -> activity.showMessageDialog(s));
        model.getUser().observe(this, mobileModel -> {
            if (mobileModel != null && mobileModel.getStatus()){
                Log.e("needCode",mobileModel.isNeedcode()+" : "+mobileModel.getMsg());
                Log.e("needCode","code : "+mobileModel.getCode());
                if (mobileModel.isNeedcode())
                    ((Splash) getActivity()).confirmPhone();
                else {
                    new UserModel(Repository.getUserInfo(), Utils.userId).save();
                    activity.home();
                }
            }
        });
        model.getOpenRegister().observe(this, phone -> activity.addPage(Register.createInstance(phone)));
        phone.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                login();
                return false;
            }
        });
        phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                hideSoftKeyboard(v,activity);
            }
        });
    }

    @OnClick(R.id.login)
    public void login() {
        if (phone.getText().toString().length() < 10) {
            activity.showToast(Utils.getStringRes(R.string.enter_valid_phone));
            return;
        }
        if (dialog != null && dialog.isVisible())
            dialog.dismiss();
        dialog = new ConfirmationDialog();
        dialog.setTitle(R.string.number_confirmation);
        dialog.setMessage(Utils.getStringRes(R.string.your_number) + " " + phone.getText().toString());
        dialog.setOnPositiveClick(Utils.getStringRes(R.string.confirm), v -> {
            if (dialog != null && dialog.isVisible())
                dialog.dismiss();
            model.login(phone.getText().toString(), Utils.getImei());
        });
        dialog.setOnNegativeClick(Utils.getStringRes(R.string.edit), v -> {
            if (dialog != null && dialog.isVisible())
                dialog.dismiss();
        });
        dialog.show(activity.getSupportFragmentManager(), dialog.getTAG());
    }

}
