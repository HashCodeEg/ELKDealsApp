package com.elkdeals.mobile.ui.auctions;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.adapters.PreviousAuctionsAdapter;
import com.elkdeals.mobile.api.models.Auction;
import com.elkdeals.mobile.ui.BaseFragment;
import com.elkdeals.mobile.interfaces.OnItemClickListener;
import com.elkdeals.mobile.viewmodel.AuctionsViewModel;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PreviousAuctions extends BaseFragment {
    public static final String TAG="PreviousAuctions";
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    private String id;
    private String type;
    private AuctionsViewModel model;
    private PreviousAuctionsAdapter adapter;

    public PreviousAuctions() {
        // Required empty public constructor
    }

    public static PreviousAuctions createInstance(String id, String type) {
        PreviousAuctions previousAuctions=new PreviousAuctions();
        previousAuctions.id=id;
        previousAuctions.type=type;
        return previousAuctions ;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return view=inflater.inflate(R.layout.fragment_previous_auctions, container, false);
    }

    @Override
    public String getTAG() {
        return TAG;
    }


    @Override
    public void initViews() {

        model= ViewModelProviders.of(this).get(AuctionsViewModel.class);
        model.getProgress().observe(this, s -> {
            if(!TextUtils.isEmpty(s))
                activity.showProgressBar(s);
            else activity.hideDialog();
        });
        model.getMessage().observe(this, s -> activity.showMessageDialog(s));
        model.getClosedAuctions().observe(this, finishedAuctionsModels -> adapter.addItems(finishedAuctionsModels));
        ///////////////////////
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter=new PreviousAuctionsAdapter(new OnItemClickListener() {
            @Override
            public void OnItemClick(Object data) {
                activity.showToast(R.string.name_activity_notifications);
            }
        });
        for(int i=0;i<25;i++)
            adapter.addItems(new Auction());
        recyclerView.setAdapter(adapter);

        model.getClosedAuctions(id,type);
    }

}
