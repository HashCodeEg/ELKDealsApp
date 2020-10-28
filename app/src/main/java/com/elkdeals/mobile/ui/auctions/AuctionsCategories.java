package com.elkdeals.mobile.ui.auctions;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.adapters.AuctionsCategoriesAdapter;
import com.elkdeals.mobile.api.models.CategoriesModel;
import com.elkdeals.mobile.ui.BaseFragment;
import com.elkdeals.mobile.interfaces.OnItemClickListener;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class AuctionsCategories extends BaseFragment {
    public static final String TAG="categories auctions";

    String source;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    AuctionsCategoriesAdapter adapter;
    public AuctionsCategories() {
        // Required empty public constructor
        source="";
    }
    public static BaseFragment getPreviousAuctionsInstance() {
        AuctionsCategories AuctionsCategories =new AuctionsCategories();
        AuctionsCategories.source=PreviousAuctions.TAG;
        return AuctionsCategories;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return view=inflater.inflate(R.layout.fragment_categories_auctions, container, false);
    }

    @Override
    public void initViews() {
        adapter=new AuctionsCategoriesAdapter(activity, R.layout.recycler_category_auciton);
        adapter.addItems(CategoriesModel.getDummyAuctions());
        adapter.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void OnItemClick(Object data) {
                super.OnItemClick(data);
                if(data!=null)
                    showAuctions(data.toString());
            }
        } );
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
    }
    private void showAuctions(String id) {
        if(!TextUtils.isEmpty(source)){
            showAuctions(id,"0",true);
            return;
        }
        Auctions auctions=Auctions.createInstance(id);
        activity.addPage(auctions);
    }
    private void showAuctions(String id,String type,boolean finished) {
        PreviousAuctions previousAuctions=PreviousAuctions.createInstance(id,type);
        activity.addPage(previousAuctions);
    }
    @Override
    public String getTAG() {
        return TAG;
    }
}