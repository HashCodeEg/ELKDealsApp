package com.elkdeals.mobile.ui.requests;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.adapters.MyRequestsAdapter;
import com.elkdeals.mobile.api.models.requests.RequestData;
import com.elkdeals.mobile.api.models.requests.RequestDetails;
import com.elkdeals.mobile.dialogFragments.SendOffer;
import com.elkdeals.mobile.ui.BaseFragment;
import com.elkdeals.mobile.interfaces.OnItemClickListener;
import com.elkdeals.mobile.model.RepositorySidalitac;
import com.elkdeals.mobile.viewmodel.RequestsViewModelSidalitac;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomersRequests extends BaseFragment {


    public static final String TAG = "CustomersRequests";
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    private RequestsViewModelSidalitac storeViewModel;
    private MyRequestsAdapter adapter;

    public CustomersRequests() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return view=inflater.inflate(R.layout.fragment_myrequests, container, false);
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

        storeViewModel= ViewModelProviders.of(this).get(RequestsViewModelSidalitac.class);

        storeViewModel.getProgress().observe(this, s -> {
            if (!TextUtils.isEmpty(s))
                activity.showProgressBar(s);
            else activity.hideDialog();
        });
        storeViewModel.getMessage().observe(this, s -> activity.showMessageDialog(s));
        storeViewModel.getRequestsMutuable().observe(this, new Observer<RequestData>() {
            @Override
            public void onChanged(RequestData sellerData) {
                adapter= new MyRequestsAdapter(new OnItemClickListener(){
                    @Override
                    public void OnItemClick(Object data) {
                        super.OnItemClick(data);
                        SendOffer sendOffer= SendOffer.createInstance((RequestDetails) data);
                        sendOffer.show(activity.getSupportFragmentManager(), SendOffer.TAG);
                    }
                },false);
                if(sellerData==null)
                {
                    recyclerView.setAdapter(adapter);
                    showReload();
                    return;
                }
                hidereload();
                for(int i=0;i<sellerData.getData().size();i++){
                    if(RepositorySidalitac.getUserInfo().getData().get(0).getCustomersId()
                            .equalsIgnoreCase(sellerData.getData().get(i).getCustomers_id()))
                    sellerData.getData().remove(i--);
                }
                adapter.addItems(sellerData.getData());
                recyclerView.setAdapter(adapter);
            }
        });
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter= new MyRequestsAdapter(new OnItemClickListener(){
            @Override
            public void OnItemClick(Object data) {
                super.OnItemClick(data);
            }
        });
        recyclerView.setAdapter(adapter);
        storeViewModel.getRequests();
    }

    @OnClick(R.id.reload)
    public void reload() {
        initViews();
    }


    public void showReload(){
        view.findViewById(R.id.reload).setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }
    public void hidereload(){
        view.findViewById(R.id.reload).setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }
}
