package com.elkdeals.mobile.dialogFragments;

import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.BitmapUtils;
import com.elkdeals.mobile.Utils.RealPathUtil;
import com.elkdeals.mobile.Utils.Utils;
import com.elkdeals.mobile.activities.BaseActivity;
import com.elkdeals.mobile.api.models.InvoicesModel;
import com.elkdeals.mobile.api.models.UserModel;
import com.elkdeals.mobile.interfaces.HasTag;
import com.elkdeals.mobile.viewmodel.UserViewModel;
import com.islam.cropper.CropImage;
import com.islam.cropper.CropImageActivity;
import com.islam.cropper.CropImageView;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.elkdeals.mobile.Utils.Constants.TYPE_BANK_RECIPT;

public class PaymenyDialog extends DialogFragment implements HasTag {
    public static final String TAG = "PaymenyDialog";

    private View view;
    private InvoicesModel invoicesModel;
    private UserModel userModel;
    private UserViewModel userViewModel;

    public PaymenyDialog() {
    }

    public static PaymenyDialog CreatePaymentDialog(InvoicesModel invoicesModel, UserModel userModel){
        PaymenyDialog dialog=new PaymenyDialog();
        dialog.invoicesModel=invoicesModel;
        dialog.userModel=userModel;
        return dialog;
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
        userViewModel= ViewModelProviders.of(this).get(UserViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_payment, container, false);
        try {
            if (getDialog().getWindow() != null) {
                getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            }
        } catch (Exception ignored) {
        }
        initViews();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
    }

    private void initViews() {
        ButterKnife.bind(this, view);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getProgress().observe(this, s -> {
            if (!TextUtils.isEmpty(s))
                ((BaseActivity)getActivity()).showProgressBar(s);
            else ((BaseActivity)getActivity()).hideDialog();
        });
        userViewModel.getMessage().observe(this, s -> ((BaseActivity)getActivity()).showMessageDialog(s));
        CropImageActivity.UrlliveData=new MutableLiveData<>();
        if (CropImageActivity.UrlliveData != null)
            CropImageActivity.UrlliveData.observe(this, uri -> {
                if(uri==null)
                {
                    ((BaseActivity)getActivity()).showToast(Utils.getStringRes(R.string.no_image_selected));
                    return;
                }
                File image1 = RealPathUtil.getFileFromUri(getActivity(), uri);
                if(image1==null)
                    return;
                File compressedImage1= BitmapUtils.compressImage2File(getActivity(), image1);
                byte[] byteimage1 = BitmapUtils.readAll(compressedImage1);
               String encodedImage1 = Base64.encodeToString(byteimage1, Base64.DEFAULT);
                String picName = image1.getName();
                userViewModel.sendPics(encodedImage1,picName,TYPE_BANK_RECIPT+"",invoicesModel.getId()+"");
            });
    }

    @OnClick(R.id.fawry)
    public void fawary(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(
                "http://elkdeals.com/fawryinv.jsp?cid=" + userModel.getId()+
                        "&invid=" +invoicesModel.getId()
        ));
        startActivity(browserIntent);
    }
    @OnClick(R.id.online)
    public void online(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(
                "http://elkdeals.com/paymentservlet?cid=" + userModel.getId()+
                        "&inv=" +invoicesModel.getId()
        ));
        startActivity(browserIntent);
    }
    @OnClick(R.id.attach_payment_reciept)
    public void attach_payment_reciept(){

        if(getActivity()==null)
            return;
        Log.e("dDDDDd", invoicesModel.getTotal() + "...");
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        CropImage.activity()
                .setAutoZoomEnabled(true)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setMultiTouchEnabled(true)
                .setFixAspectRatio(false)
                .setActivityTitle(Utils.getStringRes(R.string.app_name))
                .setMultiTouchEnabled(false)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(getActivity());
    }
    @OnClick(R.id.discount_with_code)
    public void discount_with_code(){

    }
    @Override
    public String getTAG() {
        return TAG;
    }


}
