package com.elkdeals.mobile.ui.account;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.BitmapUtils;
import com.elkdeals.mobile.Utils.Constants;
import com.elkdeals.mobile.Utils.RealPathUtil;
import com.elkdeals.mobile.Utils.Utils;
import com.elkdeals.mobile.activities.BaseActivity;
import com.elkdeals.mobile.api.models.ProfileImageModel;
import com.elkdeals.mobile.api.models.UserModel;
import com.elkdeals.mobile.custom.ProfileImageView;
import com.elkdeals.mobile.dialogFragments.EditInformationDialog;
import com.elkdeals.mobile.dialogFragments.MessageDialog;
import com.elkdeals.mobile.interfaces.OnItemClickListener;
import com.elkdeals.mobile.model.Repository;
import com.elkdeals.mobile.ui.BaseFragment;
import com.elkdeals.mobile.viewmodel.UserViewModel;
import com.islam.cropper.CropImage;
import com.islam.cropper.CropImageActivity;
import com.islam.cropper.CropImageView;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

import static com.elkdeals.mobile.Utils.Constants.FORCE_LOGOUT;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAccount extends BaseFragment implements View.OnClickListener, View.OnTouchListener {
    public static final String TAG = "my acccount";
    UserViewModel model;
    //    ////////////////////////////////
    @BindView(R.id.profile_container)
    View profileContainer;
    @BindView(R.id.camera)
    View camera;
    @BindView(R.id.profile)
    ProfileImageView profileImage;
    @BindView(R.id.profile_border)
    ImageView profile_border;
    @BindView(R.id.username)
    TextView userNameTextView;
    @BindView(R.id.name)
    TextView nameTextView;
    @BindView(R.id.email)
    TextView emailTextView;
    @BindView(R.id.user_email)
    TextView user_email;
    @BindView(R.id.mobile)
    TextView mobileTextView;
    @BindView(R.id.id)
    TextView natIDTextView;
    @BindView(R.id.credit)
    TextView credit;
    @BindView(R.id.address)
    TextView addressTextView;
    @BindView(R.id.city)
    TextView city;
    @BindView(R.id.discount)
    TextView discountTextView;
    @BindView(R.id.more_discount)
    TextView extraDiscountTextView;


    EditInformationDialog dialogFragment;
    private int previousState = View.VISIBLE;
    private boolean cancel;

    public MyAccount() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return view = inflater.inflate(R.layout.fragment_my_account, container, false);
    }

    public void viewUser(UserModel mobileModel){

        userNameTextView.setText(mobileModel.getName() + "");
        nameTextView.setText(mobileModel.getName() + "");
        emailTextView.setText(mobileModel.getEmail() + "");
        user_email.setText(mobileModel.getEmail() + "");
        mobileTextView.setText(mobileModel.getMobile() + "");
        natIDTextView.setText(mobileModel.getNationalid() + "");
        credit.setText(mobileModel.getAmount() + "");
        if(!TextUtils.isEmpty(mobileModel.getAddress()))
            addressTextView.setText(mobileModel.getAddress() + "");
        city.setText(mobileModel.getCityname() + "");
        discountTextView.setText(mobileModel.getDiscounts() + "");
        extraDiscountTextView.setText(mobileModel.getExtradiscounts() + "");
    }
    @Override
    public void initViews() {
        model = ViewModelProviders.of(this).get(UserViewModel.class);
        model.getProgress().observe(this, s -> {
            if (!TextUtils.isEmpty(s))
                activity.showProgressBar(s);
            else activity.hideDialog();
        });
        model.getMessage().observe(this, message -> {
            if (message != null && message.equals(FORCE_LOGOUT)) {
                activity.hideDialog();
                activity.messageDialog = MessageDialog.createMessageDialog("جار الخروج من البرنامج",
                        "لقد تم تسجيل الدخول من جهاز اخر يرجى اعادة تسجيل الدخول.",
                        v -> activity.exitApp(true)).setExitOnDismiss(true);
                activity.messageDialog.show(activity.getSupportFragmentManager(), FORCE_LOGOUT);
            } else activity.showMessageDialog(message);
        });
        model.getUserModel().observe(this, mobileModel -> {
            if (mobileModel == null)
                return;
            viewUser(mobileModel);
        });
        model.getProfileImage().observe(this, new Observer<ProfileImageModel>() {
            @Override
            public void onChanged(ProfileImageModel profileImageModel) {
                if (profileImageModel == null||TextUtils.isEmpty(profileImageModel.getImage()))
                {

                    return;
                }
                try {
                    camera.setVisibility(View.GONE);
                    cancel=true;
                    if(!TextUtils.isEmpty(profileImageModel.getImage()))
                    {
                        profileImage.setProfileImageForegroundColorResource(R.color.transparent);
                        byte[] bvytessf = Base64.decode(profileImageModel.getImage(), 1);
                        Bitmap bmp3 = BitmapFactory.decodeByteArray(bvytessf, 0, bvytessf.length);
                        profileImage.setImageBitmap(bmp3);
                    }
                } catch (Exception e) {

                }
                //Utils.loadImage(profileImage, API.IMAGE_BASE_URL,profileImageModel.getImage());
            }
        });


        UserModel user = Repository.getUserInfo();
        if (user!=null)
            viewUser(user);
        model.getUserData(true);
        model.getUserProfileImage();
///        profileContainer.setOnTouchListener(this);
        profileContainer.setOnClickListener(this);
        profileContainer.setOnTouchListener(this);

        CropImageActivity.UrlliveData = new MutableLiveData<>();
        CropImageActivity.UrlliveData.observe(this, uri -> {
            if (uri == null) {
                ((BaseActivity) getActivity()).showToast(Utils.getStringRes(R.string.no_image_selected));
                return;
            }
            profileImage.setImageURI(uri);
            File image1 = RealPathUtil.getFileFromUri(getActivity(), uri);
            profileImage.setProfileImageForegroundColorResource(R.color.transparent);
            File compressedImage1 = BitmapUtils.compressImage2File(getActivity(), image1);
            byte[] byteimage1 = BitmapUtils.readAll(compressedImage1);
            String encodedImage1 = Base64.encodeToString(byteimage1, Base64.DEFAULT);
            String picName = image1.getName();
            Log.e(TAG,encodedImage1);
            model.sendPics(encodedImage1, picName, Constants.TYPE_PROFILE + "", "");
        });
        //camera.setOnClickListener(this);
    }

    @OnClick(R.id.edit_account)
    public void editAccount() {
        try {
            if (dialogFragment != null) {
                if (dialogFragment.isVisible())
                    dialogFragment.dismiss();
                dialogFragment = null;
                dialogFragment =  EditInformationDialog.createInstance(new OnItemClickListener() {
                    @Override
                    public void OnItemClick(Object data) {
                        super.OnItemClick(data);
                        model.getUserData(true);
                    }
                });
            } else {
                dialogFragment =  EditInformationDialog.createInstance(new OnItemClickListener() {
                    @Override
                    public void OnItemClick(Object data) {
                        super.OnItemClick(data);
                        model.getUserData(true);
                    }
                });
            }
        } catch (Exception ignored) {
            dialogFragment =  EditInformationDialog.createInstance(new OnItemClickListener() {
                @Override
                public void OnItemClick(Object data) {
                    super.OnItemClick(data);
                    model.getUserData(true);
                }
            });
        }
        dialogFragment.show(activity.getSupportFragmentManager(), dialogFragment.getTAG());
    }

    @Override
    public void onClick(View view) {
        if (cancel)
        {
            return;
        }
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        CropImage.activity()
                .setAutoZoomEnabled(true)
                //.setMinCropWindowSize(150,150)
                //.setInitialCropWindowRectangle(new Rect(0,0,width,0))
                .setCropShape(CropImageView.CropShape.OVAL)
                .setMultiTouchEnabled(true)
                .setFixAspectRatio(true)
                .setActivityTitle(Utils.getStringRes(R.string.app_name))
                .setMultiTouchEnabled(false)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(activity);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (cancel)
        {
            return false;
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN)
            profileImage.setProfileImageForegroundColorResource(R.color.transparent);
        else if (event.getAction() == MotionEvent.ACTION_UP) {
            profileImage.setProfileImageForegroundColorResource(R.color.profile_tint);
            v.performClick();
        }
        return true;
    }

    @OnClick(R.id.insurance)
    public void showInsrance() {
        activity.addPage(new Insurance());
    }

    @OnClick(R.id.bills)
    public void showBills() {
        activity.onNavigationItemSelected(Bills.TAG);
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
