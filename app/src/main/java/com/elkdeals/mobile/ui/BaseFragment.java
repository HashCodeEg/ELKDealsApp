package com.elkdeals.mobile.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.elkdeals.mobile.activities.BaseActivity;
import com.elkdeals.mobile.interfaces.HasTag;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment implements HasTag {
    public View view;
    public BaseActivity activity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (BaseActivity) activity;
    }

    public void startActivity(Class activityClass) {
        activity.startActivity(activityClass);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (this.view == null)
            this.view = view;
        ButterKnife.bind(this, view);
        view.setClickable(true);
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        initViews();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        onResume_();
    }

    @CallSuper
    public void onResume_() {

    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        Animation animation = super.onCreateAnimation(transit, enter, nextAnim);
        View view = getView();
        if (view == null)
            view = this.view;
        int preLayerType = view.getLayerType();
        if (animation == null && nextAnim != 0)
            animation = AnimationUtils.loadAnimation(getActivity(), nextAnim);
        if (animation != null) {
            View finalView = view;
            finalView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    finalView.setLayerType(preLayerType, null);

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
        return animation;
    }

    @CallSuper
    public void OnConnectionStatusChanged(boolean networkstatus){

    }
    public abstract void initViews();
}
