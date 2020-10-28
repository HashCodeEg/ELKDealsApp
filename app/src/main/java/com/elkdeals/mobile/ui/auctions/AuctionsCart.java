package com.elkdeals.mobile.ui.auctions;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.StoreCartManager;
import com.elkdeals.mobile.adapters.AuctionsCartAdapter;
import com.elkdeals.mobile.api.API;
import com.elkdeals.mobile.api.models.InvoiceAddResponse2;
import com.elkdeals.mobile.api.models.TimeLineModel;
import com.elkdeals.mobile.dialogFragments.QuestionsDialog;
import com.elkdeals.mobile.ui.BaseFragment;
import com.elkdeals.mobile.ui.account.Bills;
import com.elkdeals.mobile.interfaces.OnItemClickListener;
import com.elkdeals.mobile.model.Repository;
import com.elkdeals.mobile.viewmodel.StoreViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class AuctionsCart extends BaseFragment {


    public static final String TAG = "AuctionsCart";
    @BindView(R.id.recycler)
    public RecyclerView recyclerView;
    @BindView(R.id.sub_total_price)
    public TextView sub_total_price;
    @BindView(R.id.total_price)
    public TextView total_price;
    @BindView(R.id.shipping_fees)
    public TextView shippingfees_price;
    @BindView(R.id.checkout)
    public TextView checkout;
    StoreViewModel storeViewModel;
    private AuctionsCartAdapter adapter;
StoreCartManager manager;

    QuestionsDialog dialog;
    public AuctionsCart() {
        manager= StoreCartManager.getInstance();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return view = inflater.inflate(R.layout.fragment_cart_auctions, container, false);
    }

    @Override
    public void onResume_() {
        super.onResume_();
        adapter.addItems(manager.getAuctions());
    }

    @Override
    public void initViews() {
        storeViewModel = ViewModelProviders.of(this).get(StoreViewModel.class);
        storeViewModel.getProgress().observe(this, s -> {
            if (!TextUtils.isEmpty(s))
                activity.showProgressBar(s);
            else activity.hideDialog();
        });
        storeViewModel.getMessage().observe(this, s -> activity.showMessageDialog(s));
        ///////////////////////////////
        view.findViewById(R.id.details_container).setVisibility(View.GONE);
        checkout.setText(R.string.create_inv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new AuctionsCartAdapter(new OnItemClickListener() {
            @Override
            public void OnItemClick(Object data) {
                super.OnItemClick(data);
                manager.remove((TimeLineModel) data);
                adapter.addItems(manager.getAuctions());
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
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createInve();
            }
        });
    }

    public void createInve(){
        ArrayList<TimeLineModel> auctions=manager.getAuctions();
        if(auctions==null||auctions.size()==0)
        {
            activity.showToast(R.string.create_auctions_first);
            return;
        }
        createInve(auctions);
    }
Disposable disposable;
    int startIndex=0;
    private void createInve(ArrayList<TimeLineModel> auctions) {
        if(startIndex>=auctions.size()){
            adapter.addItems(manager.getAuctions());
            activity.invalidateOptionsMenu();
            activity.showToast("اذهب للفواتير واختار الفاتورة الل عاوز تدفعها!");
            activity.addPage(new Bills());
            return;
        }
        TimeLineModel timeLineModel=auctions.get(startIndex);

        API.getAPIS().addInvTimeLineRx(Repository.getUserInfo().getId(), String.valueOf(timeLineModel.getId()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<InvoiceAddResponse2>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable=d;
                activity.showProgressBar("جارى عمل فاتورة : "+timeLineModel.getName());
            }

            @Override
            public void onSuccess(InvoiceAddResponse2 invoiceAddResponse2) {
                disposable.dispose();
                activity.showToast("تم عمل فاتورة : "+timeLineModel.getName());
                manager.remove(timeLineModel);
                adapter.addItems(manager.getAuctions());
                activity.invalidateOptionsMenu();
                activity.hideDialog();
                startIndex=startIndex+1;
                createInve(auctions);
            }

            @Override
            public void onError(Throwable e) {
                activity.showToast("فشل عمل فاتورة : "+timeLineModel.getName());
                adapter.addItems(manager.getAuctions());
                disposable.dispose();
                activity.invalidateOptionsMenu();
                activity.hideDialog();
                startIndex=startIndex+1;
                createInve(auctions);
            }
        });
    }

    public void showAuctionDetails(TimeLineModel auction) {
        AuctionDetails auctionDetails=AuctionDetails.createInstance(auction);
        activity.addPage(auctionDetails);
    }
    @Override
    public String getTAG() {
        return TAG;
    }
}
