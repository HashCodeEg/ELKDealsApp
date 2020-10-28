package com.elkdeals.mobile.dialogFragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
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
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import com.elkdeals.mobile.R;

import com.elkdeals.mobile.ui.account.FAQ;
import com.elkdeals.mobile.interfaces.HasTag;
import com.elkdeals.mobile.ui.auctions.AuctionsActivity;
import com.elkdeals.mobile.viewmodel.InfoViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.elkdeals.mobile.Utils.Constants.DATA;

public class QuestionsDialog extends DialogFragment implements HasTag {
    public static final String TAG = "QuestionsDialog";
    public static final int REQUEST_PHONE_CALL = 100;
    private View view;
    @BindView(R.id.call)
    TextView call;
    public QuestionsDialog() {
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_questions, container, false);
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
        call.setText(InfoViewModel.hotLine);
    }

    @OnClick(R.id.faq)
    public void faqq() {
        try {
            //todo use home instead
            Bundle args = new Bundle();
            args.putString(DATA, FAQ.TAG);
            Intent intent = new Intent(getActivity(), AuctionsActivity.class);
            intent.putExtra(DATA, args);
            startActivity(intent);
        } catch (Exception e) {
            Log.e("callus", e.getMessage());
            dismiss();
        }
    }

    @OnClick(R.id.website)
    public void websitee() {

        try {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://elkdeals.com/"));
            startActivity(browserIntent);
        } catch (Exception e) {
            Log.e("callus", e.getMessage());

            dismiss();
        }
    }

    @OnClick(R.id.call)
    public void callUss(TextView view) {
        try {
            if (getContext() == null) return;

            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+call.getText()));
                startActivity(intent);
            } else if (getActivity() != null)
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);

            dismiss();
        } catch (Exception e) {
            Log.e("callus", e.getMessage());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PHONE_CALL) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" +call.getText()));

                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    startActivity(intent);
                }
            }
        }
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
