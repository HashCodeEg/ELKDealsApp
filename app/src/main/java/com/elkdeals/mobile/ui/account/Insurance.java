package com.elkdeals.mobile.ui.account;


import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.BitmapUtils;
import com.elkdeals.mobile.Utils.Constants;
import com.elkdeals.mobile.Utils.RealPathUtil;
import com.elkdeals.mobile.Utils.Utils;
import com.elkdeals.mobile.activities.BaseActivity;
import com.elkdeals.mobile.api.models.UserModel;
import com.elkdeals.mobile.ui.BaseFragment;
import com.elkdeals.mobile.model.Repository;
import com.elkdeals.mobile.viewmodel.UserViewModel;
import com.islam.cropper.CropImage;
import com.islam.cropper.CropImageActivity;
import com.islam.cropper.CropImageView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class Insurance extends BaseFragment {


    public static final String TAG = "Insurance";
    @BindView(R.id.note)
    TextView note;
    @BindView(R.id.cars_insurance)
    View cars_insurance;
    @BindView(R.id.other_insurance)
    View other_insurance;
    @BindView(R.id.add_bill)
    View add_bill;
    UserViewModel model;
    UserModel userModel;
    public Insurance() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return view = inflater.inflate(R.layout.fragment_insurance, container, false);
    }


    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, view);
        initViews();
    }

    @Override
    public void initViews() {

        model = ViewModelProviders.of(this).get(UserViewModel.class);
        model.getProgress().observe(this, s -> {
            if (!TextUtils.isEmpty(s))
                activity.showProgressBar(s);
            else activity.hideDialog();
        });
        model.getMessage().observe(this, s -> activity.showMessageDialog(s));
        CropImageActivity.UrlliveData = new MutableLiveData<>();
        CropImageActivity.UrlliveData.observe(this, uri -> {
            if (uri == null) {
                ((BaseActivity) getActivity()).showToast(Utils.getStringRes(R.string.no_image_selected));
                return;
            }
            File image1 = RealPathUtil.getFileFromUri(getActivity(), uri);
            File compressedImage1 = BitmapUtils.compressImage2File(getActivity(), image1);
            byte[] byteimage1 = BitmapUtils.readAll(compressedImage1);
            String encodedImage1 = Base64.encodeToString(byteimage1, Base64.DEFAULT);
            String picName = image1.getName();
            model.sendPics(encodedImage1, picName, Constants.TYPE_INSURANCE + "", "");
        });
        userModel = Repository.getUserInfo();
        if(userModel==null)
            return;
        int insuranceStatus = userModel.getInsurancestatus();

        switch (insuranceStatus) {
            case 0:
                note.setText(R.string.you_don_t_have_insurance_payments);
                break;
            case 1:
                add_bill.setVisibility(View.GONE);
                note.setText("جارى التحقق من المبلغ المدفوع الخاص بك");
                break;
            case 2:
                add_bill.setVisibility(View.GONE);
                note.setText("المبلغ التامينى المدفوع" + " " + userModel.getInsurance() + " " + "جنيه");
                break;
            case 3:
                add_bill.setVisibility(View.GONE);
                note.setText("تم استرداد المبلغ التامينى");
                break;
        }
    }
    public void reload(){

    }
    @OnClick(R.id.fawry_cars)
    public void fawaryCars(){

        if(userModel==null)
            return;
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(
                "http://elkdeals.com/fawry.jsp?cid=" +
                        userModel.getId()
                        + "&amount=2000"
        ));
        startActivity(browserIntent);
    }
    @OnClick(R.id.fawry_no_cars)
    public void fawaryOthers(){

        if(userModel==null)
            return;
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(
                "http://elkdeals.com/fawry.jsp?cid=" +
                        userModel.getId()
                        + "&amount=500"
        ));
        startActivity(browserIntent);
    }

    @OnClick(R.id.online_cars)
    public void online_cars(){
        if(userModel==null)
            return;
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(
                "http://elkdeals.com/mobilepaymentservlet?cid=" +
                        userModel.getId()
                        + "&amount=2000"
        ));
        startActivity(browserIntent);
    }
    @OnClick(R.id.online_no_cars)
    public void online_no_cars(){
        if(userModel==null)
            return;
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(
                "http://elkdeals.com/mobilepaymentservlet?cid=" +
                        userModel.getId()
                        + "&amount=500"
        ));
        startActivity(browserIntent);
    }

    @OnClick({R.id.add_bill_image_front, R.id.add_bill_text_front})
    public void addBill() {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        CropImage.activity()
                .setAutoZoomEnabled(true)
                //.setMinCropWindowSize(150,150)
                //.setInitialCropWindowRectangle(new Rect(0,0,width,0))
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setMultiTouchEnabled(true)
                .setFixAspectRatio(false)
                .setActivityTitle(Utils.getStringRes(R.string.app_name))
                .setMultiTouchEnabled(false)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(activity);

    }
}
