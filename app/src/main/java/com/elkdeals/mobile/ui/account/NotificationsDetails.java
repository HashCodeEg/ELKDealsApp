package com.elkdeals.mobile.ui.account;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.Utils;
import com.elkdeals.mobile.adapters.NotifiactionsAdapter;
import com.elkdeals.mobile.api.models.NotificationDetModel;
import com.elkdeals.mobile.dialogFragments.ImageDialog;
import com.elkdeals.mobile.ui.BaseFragment;
import com.elkdeals.mobile.interfaces.OnItemClickListener;
import com.elkdeals.mobile.viewmodel.UserViewModel;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsDetails extends BaseFragment {


    public static final String TAG = "NotificationsDetails";
    @BindView(R.id.details)
    TextView details;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.notesImage)
    ImageView notesImage;
    @BindView(R.id.video)
    ImageView notesVideo;
    @BindView(R.id.play)
    View play;

    @BindView(R.id.reload)
    TextView reload;
    UserViewModel userViewModel;
    private String notificationId;

    public NotificationsDetails() {
        // Required empty public constructor
    }
    public static NotificationsDetails createInstance(String notificationId) {
        if(TextUtils.isEmpty(notificationId))
            return null;
        NotificationsDetails notificationsDetails=new NotificationsDetails();
        notificationsDetails.notificationId=notificationId;
        return notificationsDetails;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return view = inflater.inflate(R.layout.fragment_notification_details, container, false);
    }


    @Override
    public String getTAG() {
        return TAG;
    }


    @Override
    public void initViews() {
        userViewModel= ViewModelProviders.of(this).get(UserViewModel.class);

        userViewModel.getProgress().observe(this, s -> {
            if (!TextUtils.isEmpty(s))
                activity.showProgressBar(s);
            else activity.hideDialog();
        });
        userViewModel.getMessage().observe(this, s -> activity.showMessageDialog(s));
        NotifiactionsAdapter adapter = new NotifiactionsAdapter(new OnItemClickListener() {
            @Override
            public void OnItemClick(Object data) {
                activity.showToast(R.string.name_activity_notifications);
            }
        });
        userViewModel.getUserNotificationDetailsLiveData().observe(this, new Observer<NotificationDetModel>() {
            @Override
            public void onChanged(NotificationDetModel notificationDetModel) {
                if(notificationDetModel==null){
                    showReload();
                }else {
                    title.setText(notificationDetModel.getTitle());
                    details.setText(notificationDetModel.getText());
                    if(notificationDetModel.getType()==1){
                    loadVideo(notificationDetModel.getVideo());
                    play.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            activity.playYoutubeVideo(notificationDetModel.getVideo());
                        }
                    });}
                    loadImage(notificationDetModel);
                }
            }
        });
        reload();
    }

    private void loadVideo(String video) {
        notesVideo.setVisibility(View.VISIBLE);
        Utils.loadImage(notesVideo,"https://img.youtube.com/vi/",video+"/hqdefault.jpg");
    }
    public void showReload(){
        reload.setText("خطأ فى الاتصال,اعادة التحميل");
        view.findViewById(R.id.reload).setVisibility(View.VISIBLE);
    }
    public void hidereload(){
        view.findViewById(R.id.reload).setVisibility(View.GONE);
        details.setVisibility(View.VISIBLE);
    }
    @OnClick(R.id.reload)
    public void reload(){
        userViewModel.getUserNotificationDetails(notificationId);
        hidereload();
    }
    public void loadImage(NotificationDetModel model) {
        try{
        if (model.getType() == 0) {
            final String img = model.getImage();
            notesImage.setVisibility(View.VISIBLE);
            final byte[] bvytessf = Base64.decode(img, 1);
            final Bitmap bmp3 = BitmapFactory.decodeByteArray(bvytessf, 0, bvytessf.length);
            notesImage.setImageBitmap(bmp3);
        }}
        catch (Exception ignored)
        {
        }
    }
    public void viewImage(NotificationDetModel model) {
        final String img = model.getImage();
        notesImage.setOnClickListener(view -> viewImageInFrag(img));
    }

    public void viewImageInFrag(String img) {
        ImageDialog newFragment = new ImageDialog(img + "");
        newFragment.setCancelable(true);
        newFragment.show(activity.getSupportFragmentManager(), ImageDialog.TAG);
    }


}
