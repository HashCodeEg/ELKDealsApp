package com.elkdeals.mobile.ui.splash;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.ui.BaseFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginChooser extends BaseFragment {


    public static final String TAG = "LoginChooser";

    public LoginChooser() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return view = inflater.inflate(R.layout.fragment_login_chooser, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void initViews() {

    }

    @OnClick(R.id.login)
    public void login() {
        ((Splash) getActivity()).login();
    }

    @OnClick(R.id.register)
    public void register() {
        ((Splash) getActivity()).register();
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
