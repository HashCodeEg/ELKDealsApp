package com.elkdeals.mobile.ui.account;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.adapters.NotifiactionsAdapter;
import com.elkdeals.mobile.api.models.NotificationModel;
import com.elkdeals.mobile.interfaces.OnItemClickListener;
import com.elkdeals.mobile.ui.BaseFragment;
import com.elkdeals.mobile.viewmodel.UserViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class Notifications extends BaseFragment {


    public static final String TAG = "Notifications";
    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @BindView(R.id.reloadText)
    TextView reloadText;
    @BindView(R.id.reloadImage)
    ImageView reloadImage;
    @BindView(R.id.reload)
    View reload;
    UserViewModel userViewModel;
    public Notifications() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return view = inflater.inflate(R.layout.fragment_notifications, container, false);
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
        view.findViewById(R.id.add_address).setVisibility(View.INVISIBLE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(linearLayoutManager);
        NotifiactionsAdapter adapter = new NotifiactionsAdapter(new OnItemClickListener() {
            @Override
            public void OnItemClick(Object data) {
                activity.addPage(NotificationsDetails.createInstance(((NotificationModel)data).getId()+""));
            }
        });
        recyclerView.setAdapter(adapter);
        userViewModel.getUserNotifications().observe(this, new Observer<List<NotificationModel>>() {
            @Override
            public void onChanged(List<NotificationModel> notificationModelList) {
                if(notificationModelList==null){
                    showReload();
                }else if(notificationModelList.size()==0){
                    showEmpty();
                }
                else adapter.addItems(notificationModelList);
            }
        });
        userViewModel.getUserNotifications(true);
    }

    public void showEmpty(){
        reloadText.setText("لا توجد إشعارات");
        view.findViewById(R.id.reload).setVisibility(View.VISIBLE);
        reloadImage.setImageResource(R.drawable.ic_logo_error);
        recyclerView.setVisibility(View.GONE);
    }
    public void showReload(){
        reloadText.setText("خطأ فى الاتصال,اعادة التحميل");
        view.findViewById(R.id.reload).setVisibility(View.VISIBLE);
        reloadImage.setImageResource(R.drawable.ic_empty);
        recyclerView.setVisibility(View.GONE);
    }
    public void hidereload(){
        view.findViewById(R.id.reload).setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }
    @OnClick(R.id.reload)
    public void reload(){
        userViewModel.getUserNotifications(true);
        hidereload();
    }

}
