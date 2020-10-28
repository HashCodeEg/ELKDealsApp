package com.elkdeals.mobile.ui.auctions;


import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.StoreCartManager;
import com.elkdeals.mobile.Utils.Constants;
import com.elkdeals.mobile.Utils.Utils;
import com.elkdeals.mobile.adapters.KeyValueAdapter;
import com.elkdeals.mobile.api.models.TimeLineModel;
import com.elkdeals.mobile.ui.BaseFragment;
import com.elkdeals.mobile.viewmodel.AuctionsViewModel;
import com.islam.circleprogressview.CircleProgressView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class AuctionDetails extends BaseFragment {


    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.auction_title)
    TextView auctionTitel;
    @BindView(R.id.installments_label)
    TextView installments_label;
    @BindView(R.id.auction_title_details)
    TextView auctionTitelDetails;
    @BindView(R.id.auction_description)
    TextView auctionDescription;
    @BindView(R.id.video)
    ImageView img;
    @BindView(R.id.auction_code)
    TextView code;
    @BindView(R.id.auction_market_price)
    TextView marketPrice;
    @BindView(R.id.bid_start_label)
    TextView bidStartLabel;
    @BindView(R.id.auction_bid_start)
    TextView bidStart;
    @BindView(R.id.bid_start_price)
    TextView bidStartPrice;
    @BindView(R.id.auction_terms_paper)
    TextView termsPaper;
    @BindView(R.id.auction_installments)
    TextView installments;
    @BindView(R.id.auction_number_months)
    TextView numberOfMonths;
    @BindView(R.id.max_installment_label)
    TextView maxInstallmentsLabel;
    @BindView(R.id.auction_max_installment)
    TextView maxInstallments;
    @BindView(R.id.auction_insurance)
    TextView insurance;
    @BindView(R.id.auction_sale_restriction)
    TextView sale_restriction;
    @BindView(R.id.circleView)
    CircleProgressView circleProgressView;
    @BindView(R.id.auction_details_container)
    View detailsContainer;

    private AuctionsViewModel model;
    KeyValueAdapter adapter;
    public static final String TAG = "Auctions";
    private TimeLineModel mTimeLineModel;
    Handler uiHandler;

    public AuctionDetails() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("auction",mTimeLineModel);
    }

    public static AuctionDetails createInstance(TimeLineModel timeLineModel){
        AuctionDetails details=new AuctionDetails();
        details.mTimeLineModel=timeLineModel;
        Bundle bundle = new Bundle();
        bundle.putParcelable("auction",timeLineModel);
        details.setArguments(bundle);
        return details;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        uiHandler=new Handler();
        Bundle data = getArguments();
        if (savedInstanceState!=null)
            data = savedInstanceState;
        if (data!=null&&data.containsKey("auction"))
        mTimeLineModel = data.getParcelable("auction");
        return view=inflater.inflate(R.layout.fragment_auction_details, container, false);
    }

    @Override
    public void initViews() {
        model= ViewModelProviders.of(this).get(AuctionsViewModel.class);
        model.getProgress().observe(this, s -> {
            if (!TextUtils.isEmpty(s))
                activity.showProgressBar(s);
            else activity.hideDialog();
        });
        model.getMessage().observe(this, s -> activity.showMessageDialog(s));
        model.getAuctionDetail().observe(this,
                aucDetailsModel -> adapter.addItems(aucDetailsModel));
        model.getAuctionDetail().observe(this,
                aucDetailsModel ->
                {

                    if(aucDetailsModel.getAuctionkind()== Constants.AUCTION_KIND_MONTHS) {
                        bidStartPrice.setText(aucDetailsModel.getStartprice() + " " + Utils.getStringRes(R.string.month));
                        maxInstallmentsLabel.setText(R.string.max_number_of_installments_months);
                        installments.setText(aucDetailsModel.getMothlyinstallment()+"");
                    }
                    else {
                        bidStartPrice.setText(aucDetailsModel.getStartprice()+" "+ Utils.getStringRes(R.string.egp));
                        installments_label.setText("عدد شهور القسط");
                        installments.setText(aucDetailsModel.getMonths()+"");


                    }
                    auctionTitel.setText(aucDetailsModel.getName()+"");
                    auctionTitelDetails.setText(aucDetailsModel.getCname()+"");
                    auctionDescription.setText(aucDetailsModel.getDesc()+"");
                    code.setText(aucDetailsModel.getCode()+"");
                    marketPrice.setText(aucDetailsModel.getMarketprice()+"");
                    if(aucDetailsModel.getPercentage()<100){
                        bidStart.setText(R.string.when_subscribers_complete);
                    }
                    else bidStart.setText(aucDetailsModel.getStartdate()+"");
                    termsPaper.setText(aucDetailsModel.getEntering()+"");
                    numberOfMonths.setText(aucDetailsModel.getMonths()+"");
                    if(aucDetailsModel.getNosales()==0)
                        sale_restriction.setText(R.string.no_value);
                    else sale_restriction.setText(aucDetailsModel.getNosales()+ Utils.getStringRes(R.string.years));
                    insurance.setText(aucDetailsModel.getInsurancevalue()+"");
                    maxInstallments.setText(aucDetailsModel.getMaxmothly()+"");
                    circleProgressView.setValueAnimated(aucDetailsModel.getPercentage(),300);
                    Utils.loadImage(img,aucDetailsModel.getImage());
                });
        model.getAuctionItemDetail().observe(this,
                aucDescriptionModels ->
                        adapter.addItems(aucDescriptionModels));
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        adapter=new KeyValueAdapter(R.layout.recycler_details);
        recyclerView.setAdapter(adapter);
        auctionTitel.setText(mTimeLineModel.getName()+"");
        code.setText("7888");
        auctionTitelDetails.setText(mTimeLineModel.getCname()+"");
        numberOfMonths.setText(mTimeLineModel.getStepprice()+"");
        model.getAuctionDetails(String.valueOf(mTimeLineModel.getId()));
        model.getAuctionItemDetails(String.valueOf(mTimeLineModel.getId()));
        circleProgressView.setValue(0);
        circleProgressView.spin();
    }

    @OnClick(R.id.play)
    public void play(){
        activity.playYoutubeVideo(mTimeLineModel.getVideo());
    }

    @OnClick(R.id.start_bidding)
    public void bid(){


/*        Methods.saveInSharedPref("PRICE",mTimeLineModel.getEntering()+"",activity);
        DialogFragment dialogFragment = new CreatInvoiceDialog(
                mTimeLineModel.getId()+"",
                mTimeLineModel.getEntering()+"",
                mTimeLineModel.getCatId()+"",
                ""
        );
        dialogFragment.show(activity.getSupportFragmentManager(), "CreatInvoiceDialog");

*/
        StoreCartManager.getInstance().addAuction(mTimeLineModel);
        activity.showToast("تمت اضافة المزاد الى السلة.اذهب لعمل فاتورة.");
        activity.invalidateOptionsMenu();
        activity.onNavigationItemSelected(AuctionsCart.TAG);
    }


    @OnClick(R.id.switch_details)
    public void switchDetails(View view){
        if(recyclerView.getVisibility()!=View.VISIBLE){
        Utils.showView(recyclerView);
        Utils.hideView(detailsContainer);
        ((TextView)view).setText(R.string.view_product_specefications);
        }else{
            Utils.hideView(recyclerView);
            Utils.showView(detailsContainer);
            ((TextView)view).setText(R.string.view_auction_specefications);
        }
    }
    @Override
    public String getTAG() {
        return TAG;
    }
}
