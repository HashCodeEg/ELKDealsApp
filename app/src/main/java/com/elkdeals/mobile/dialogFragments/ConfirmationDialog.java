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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.DialogFragment;

import com.elkdeals.mobile.App;
import com.elkdeals.mobile.R;
import com.elkdeals.mobile.interfaces.HasTag;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfirmationDialog extends DialogFragment implements HasTag {
    public static final String TAG = "Confirmation Dialog";
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.message)
    TextView message;
    @BindView(R.id.positive)
    TextView positive;
    @BindView(R.id.negative)
    TextView negative;
    private String mTitle, mMessage, mPositive, mNegative;
    private View view;
    private View.OnClickListener onPositiveClick, onNegativeClick;

    public ConfirmationDialog() {
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
        //windowParams.dimAmount = 0.00f;
        //windowParams.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(windowParams);

    }

    @OnClick(R.id.negative)
    public void negative(View view) {
        try {
            Log.e("negative", "click on negative");
            if (onNegativeClick == null)
                dismiss();
            else onNegativeClick.onClick(view);
        } catch (Exception ignored) {
        }
    }

    @OnClick(R.id.positive)
    public void positive(View view) {
        try {
            Log.e("positive", "click on positive");
            if (onPositiveClick == null)
                dismiss();
            else onPositiveClick.onClick(view);
        } catch (Exception ignored) {
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_confirm, container, false);
        try {
            if (getDialog().getWindow() != null) {
                getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            }
        } catch (Exception ignored) {
        }
        initViews();
        return view;
    }

    public void setTitle(String Title) {
        this.mTitle = Title;
    }

    public void setTitle(@StringRes int Title) {
        this.mTitle = App.getInstance().getString(Title);
    }

    public void setMessage(String Message) {
        this.mMessage = Message;
    }

    public void setOnPositiveClick(String confirm, View.OnClickListener onPositiveClick) {
        this.onPositiveClick = onPositiveClick;
    }

    public void setOnNegativeClick(String edit, View.OnClickListener onNegativeClick) {
        this.onNegativeClick = onNegativeClick;
    }

    private void initViews() {
        ButterKnife.bind(this, view);
        if (TextUtils.isEmpty(mTitle))
            title.setText(R.string.name);
        else title.setText(mTitle);
        if (TextUtils.isEmpty(mMessage))
            message.setText(R.string.message);
        else message.setText(mMessage);
        if (TextUtils.isEmpty(mPositive))
            positive.setText(R.string.ok);
        else positive.setText(mPositive);
        if (TextUtils.isEmpty(mNegative))
            negative.setText(R.string.cancel);
        else negative.setText(mNegative);
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
