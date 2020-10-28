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
import com.elkdeals.mobile.StoreCartManager;
import com.elkdeals.mobile.Utils.Methods;
import com.elkdeals.mobile.adapters.AuctionsAdapter;
import com.elkdeals.mobile.api.models.TimeLineModel;
import com.elkdeals.mobile.dialogFragments.QuestionsDialog;
import com.elkdeals.mobile.ui.BaseFragment;
import com.elkdeals.mobile.interfaces.OnItemClickListener;
import com.elkdeals.mobile.viewmodel.AuctionsViewModel;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Auctions extends BaseFragment {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    public static final String TAG = "Auctions";
    private QuestionsDialog dialog;
    private String id;
    private AuctionsViewModel model;
    private AuctionsAdapter adapter;

    public Auctions() {
        // Required empty public constructor
    }

    static Auctions createInstance(String id) {
        Auctions auctions=new Auctions();
        auctions.id=id;
        return auctions;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return view=inflater.inflate(R.layout.fragment_auctions, container, false);
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
        model.getAuctions().observe(this, timeLineModels -> adapter.addItems(timeLineModels));
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        adapter=new AuctionsAdapter(new OnItemClickListener() {
            @Override
            public void OnItemClick(Object data) {
                super.OnItemClick(data);
                Methods.saveInSharedPref("PRICE",((TimeLineModel) data).getEntering()+"",activity);
              /*  DialogFragment dialogFragment = new CreatInvoiceDialog(
                        ((TimeLineModel) data).getId()+"",
                        ((TimeLineModel) data).getEntering()+"",
                        id,
                        ""
                );
                dialogFragment.show(activity.getSupportFragmentManager(), "CreatInvoiceDialog");

*/

                StoreCartManager.getInstance().addAuction((TimeLineModel) data);
                activity.showToast("تمت اضافة المزاد الى السلة.اذهب لعمل فاتورة.");
                activity.invalidateOptionsMenu();
                activity.onNavigationItemSelected(AuctionsCart.TAG);
            }
        });
        adapter.setOnDetailsClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(Object data) {
                super.OnItemClick(data);
                if(data!=null)
                showAuctionDetails((TimeLineModel) data);
            }
        });
        adapter.setOnQuestionsClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(Object data) {
                        if(dialog==null)
                            dialog=new QuestionsDialog();
                        if(dialog.isHidden())
                            dialog=new QuestionsDialog();
                        dialog.show(activity.getSupportFragmentManager(), QuestionsDialog.TAG);
                }
        });
        adapter.setOnPlayClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(Object data) {
                activity.playYoutubeVideo(data);

            }});
        recyclerView.setAdapter(adapter);
        model.getAuctions(id);
    }

    public void showAuctionDetails(TimeLineModel auction) {
        auction.setCatId(id);
        AuctionDetails auctionDetails=AuctionDetails.createInstance(auction);
        activity.addPage(auctionDetails);
    }
    @Override
    public String getTAG() {
        return TAG;
    }
}
