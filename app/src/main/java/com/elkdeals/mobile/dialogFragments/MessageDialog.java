package com.elkdeals.mobile.dialogFragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.DialogFragment;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.Utils;
import com.elkdeals.mobile.activities.BaseActivity;


public class MessageDialog extends DialogFragment {
    public boolean isDismissed;
    private View view;
    private CardView card;
    private ProgressBar progressBar;
    private NestedScrollView scrollView;
    private TextView title;
    private TextView message;
    private TextView ok;
    private TextView cancel;
    String okText,canceText;
    private String titleText;
    private String messageText;
    private View.OnClickListener onOkListener;
    private View.OnClickListener onCancelListener;
    private View divider;
    private boolean isProgress;
    private boolean exitOnDismiss=false;

    public MessageDialog() {
        titleText = null;
        messageText = null;
        onCancelListener = null;
        onOkListener = null;
        isDismissed=false;
        isProgress = false;
    }

    public static MessageDialog createMessageDialog(String titleText, String mesageText, View.OnClickListener onOkListener, View.OnClickListener onCancelListener) {
        MessageDialog dialog = new MessageDialog();
        dialog.titleText = titleText;
        dialog.messageText = mesageText;
        dialog.onOkListener = onOkListener;
        dialog.onCancelListener = onCancelListener;
        return dialog;
    }

    public static MessageDialog createMessageDialog(String titleText, String messageText, View.OnClickListener onOkListener) {
        MessageDialog dialog = new MessageDialog();
        dialog.titleText = titleText;
        dialog.messageText = messageText;
        dialog.onOkListener = onOkListener;
        return dialog;
    }

    public static MessageDialog createMessageDialog(String messageText, View.OnClickListener onOkListener) {
        MessageDialog dialog = new MessageDialog();
        dialog.messageText = messageText;
        dialog.onOkListener = onOkListener;
        return dialog;
    }
    public static MessageDialog createMessageDialog(@StringRes int messageRes, View.OnClickListener onOkListener) {
        MessageDialog dialog = new MessageDialog();
        dialog.messageText = Utils.getStringRes(messageRes);
        dialog.onOkListener = onOkListener;
        return dialog;
    }

    //not working well, todo : will be fixed
    private static MessageDialog createProgressDialog() {
        MessageDialog dialog = new MessageDialog();
        dialog.isProgress = true;
        return dialog;
    }

    public static MessageDialog createProgressDialog(String progressMessage) {
        MessageDialog dialog = new MessageDialog();
        dialog.isProgress = true;
        dialog.messageText = progressMessage;
        dialog.setCancelable(false);
        return dialog;
    }
    public MessageDialog setExitOnDismiss(boolean exitOnDismiss){
        this.exitOnDismiss=exitOnDismiss;
        return this;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.customdialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog().getWindow() != null)
            getDialog().getWindow()
                    .setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                            WindowManager.LayoutParams.WRAP_CONTENT);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog, container, false);
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
        title = view.findViewById(R.id.title);
        message = view.findViewById(R.id.message);
        ok = view.findViewById(R.id.ok);
        cancel = view.findViewById(R.id.cancel);
        divider = view.findViewById(R.id.divider);
        card = view.findViewById(R.id.dialog_card);
        progressBar = view.findViewById(R.id.progressBar);
        scrollView = view.findViewById(R.id.message_container);
        if (isProgress) {

            progressBar.setVisibility(View.VISIBLE);
            if (messageText == null) {
                scrollView.setVisibility(View.GONE);
                card.setCardBackgroundColor(getResources().getColor(R.color.transparent));
            } else {
                scrollView.setVisibility(View.VISIBLE);
                message.setText(messageText);
                card.setCardBackgroundColor(getResources().getColor(R.color.dialog_background));
            }
            return;
        }
        if (onOkListener != null) {
            ok.setVisibility(View.VISIBLE);
            ok.setOnClickListener(onOkListener);
        }
        if (onCancelListener != null) {
            cancel.setVisibility(View.VISIBLE);
            cancel.setOnClickListener(onCancelListener);
        }
        if (!TextUtils.isEmpty(titleText)) {
            title.setVisibility(View.VISIBLE);
            divider.setVisibility(View.GONE);
            title.setText(titleText);
        }
        if (!TextUtils.isEmpty(messageText)) {
            scrollView.setVisibility(View.VISIBLE);
            message.setText(messageText);
        }
        if (!TextUtils.isEmpty(okText))
            ok.setText(okText);
        if (!TextUtils.isEmpty(canceText))
            cancel.setText(canceText);
        if(exitOnDismiss)
            setCancelable(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(exitOnDismiss)
        {
            try {
                ((BaseActivity)getActivity()).exitApp(true);
            }
            catch (Exception e){
                ((BaseActivity)getActivity()).exitApp(true);
            }
        }
    }

    @Override
    public void dismiss() {
        isDismissed=true;
        if(exitOnDismiss)
        {
            try {
                ((BaseActivity)getActivity()).exitApp(true);
                return;
            }
            catch (Exception e){
                ((BaseActivity)getActivity()).exitApp(true);
                return;
            }
        }
        try {
            super.dismiss();
        } catch (Exception ignored) {
            Log.e("messageDialog", ignored.getMessage() + isProgress);
            super.dismiss();
        }
    }

    public void setOnOkListener(View.OnClickListener onOkListener) {
        this.onOkListener = onOkListener;
    }

    public void setOnCancelListener(View.OnClickListener onCancelListener) {
        this.onCancelListener = onCancelListener;
    }

    public void setTitle(String title) {
        titleText = title;
    }

    public void setMessage(String message) {
        messageText = message;
    }

    public void setOk(String ok) {
        okText = ok;
    }
    public void setCancel(String ok) {
        canceText = ok;
    }
}