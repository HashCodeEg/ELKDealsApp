package com.elkdeals.mobile.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.Utils;
import com.elkdeals.mobile.api.models.AucSpineerModel;
import com.elkdeals.mobile.api.models.AuctionItemModel;
import com.elkdeals.mobile.api.models.CurrentAucSerModel;
import com.elkdeals.mobile.api.models.CurrentAuctionsModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by mohamed on 1/15/17.
 * <p>
 * This Adapter To Display User Auctions
 * <p>
 * This Adapter used In (Auctions)->CurrentAuctions.class
 * <p>
 * it use item_row_current_auctions.xml Ad Row Item
 */

public class CurrentAuctionsAdapter extends BaseAdapter {

    Context context;
    int inflaterID;
    List<CurrentAuctionsModel> allCurrntAuctionsList = new ArrayList<>();
    List<AucSpineerModel> aucSpineerModels = new ArrayList<>();
    String aucType;

    /*
     *  Constructor Params
     *  @param      allCurrntAuctionsList to get all user auctions from service.
     *  @param      aucSpineerModelsList to fill spinner of all user auctions in AucDetailActivity
     *              to make it easy when user change auction.
     *  @param      aucType (IF =1)-> Normal Auction... (IF = 2) Closed Auctions "One Price".
     * */

    public CurrentAuctionsAdapter(Context context, int inflater, List<CurrentAuctionsModel> allCurrntAuctionsList, List<AucSpineerModel> aucSpineerModels, String aucType) {
        this.context = context;
        this.inflaterID = inflater;
        this.allCurrntAuctionsList = allCurrntAuctionsList;
        this.aucSpineerModels = aucSpineerModels;
        this.aucType = aucType;
    }

    public void clear() {
        this.allCurrntAuctionsList.clear();

    }

    @Override
    public int getCount() {
        return allCurrntAuctionsList.size();
    }

    @Override
    public Object getItem(int position) {
        return allCurrntAuctionsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View root = convertView;

        final ViewHolderPost holder;
        if (root == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            root = inflater.inflate(inflaterID, parent, false);
            holder = new ViewHolderPost(root);


            root.setTag(holder);
        } else {
            holder = (ViewHolderPost) root.getTag();
        }
        final CurrentAuctionsModel auction = allCurrntAuctionsList.get(position);

        setDataInViews(holder, auction);

        auction.setBidtype(2);
        sendDataToDetailActivity(holder, auction, position);

        return root;
    }


    /*
     * This Method To Set Data In UI Components.
     * @param holder To get View Component.
     * @param auction to get Model Data.
     * and this method check auctoin case(Started,Finished,Not Yet).
     * */
    private void setDataInViews(ViewHolderPost holder, CurrentAuctionsModel auc) {
        holder.prodName.setText(auc.getName() + "");
        if (auc.getAuctionkind() != 2)
            holder.highPrice.setText(context.getResources().getString(R.string.price) + " " + auc.getCurrent() + " " + context.getResources().getString(R.string.egp));
        else if (auc.getAuctionkind() == 2)
            holder.highPrice.setText("عدد الايام " + auc.getCurrent() + " يوم ");
        holder.custName.setText(auc.getCname() + "");
        holder.natIdNum.setText(auc.getIdnumber() + "");


        holder.highPrice.setTextColor(Color.BLACK);
        holder.custName.setTextColor(Color.BLACK);
        holder.natIdNum.setTextColor(Color.BLACK);

        if (auc.getStarted() && !auc.getFinished()) {
            holder.newTimer.setText(context.getResources().getString(R.string.adp_auction_still));

        }
        if (auc.getFinished()) {
            if (auc.getBidtype() == 1) {
                if (auc.isFinishedstep())
                    if (auc.isFinishedfinal())
                        holder.newTimer.setText(context.getResources().getString(R.string.adp_auction_ended));
            } else
                holder.newTimer.setText(context.getResources().getString(R.string.adp_auction_ended));
        }

        if (!auc.getStarted()) {
            holder.newTimer.setText(context.getResources().getString(R.string.adp_auction_notStarted));
        }
        Utils.loadImage(holder.imageViewProduct,"http://elkdeals.com/admin/uploads/",auc.getImage());

    }

    /*
     * This Method To Send Data To AucDetailsActivity.
     *
     * @param  holder to get View Component.
     * @param  auction to get Model Data.
     * @param  position to get Auction Position In ListView .
     *
     * @var    auctionSerializedModel to send all current auction details in on serialized Object to DetailsActivity.class.
     * @var    itemModel to send all current auction details in on serialized Object to .class.
     * @var    aucSpineerModels this to get All Auctions For Spinner In DetailsActivity.class.
     * @IF     if (auction.getWay() == 1) Auction Will Be ClosedAuction (One Price).
     * @ELSE   Auction Will Be Normal.
     *
     * @Intent intent  ---> CurrentAuctionDetails.class.
     * @Intent intent2 ---> .class.
     * */
    private void sendDataToDetailActivity(ViewHolderPost holder, final CurrentAuctionsModel auction, final int position) {
        final CurrentAucSerModel[] auctionSerializedModel = new CurrentAucSerModel[1];
        final AuctionItemModel[] itemModel = new AuctionItemModel[1];

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*
                if (ConnectivityReceiver.isConnected()) {
                    Intent intent = new Intent(context, ClosedAucActivity.class);
                    Intent intent2 = new Intent(context, CurrentAuctionDetails.class);
                    Bundle extras = new Bundle();

                    auctionSerializedModel[0] = new CurrentAucSerModel(
                            auction.getId(),
                            position,
                            auction.getName(),
                            auction.getImage(),
                            auction.getEnddate2(),
                            auction.getDatetype(),
                            auction.getWay()
                    );

                    itemModel[0] = new AuctionItemModel(
                            auction.getId() + "",
                            auction.getName(),
                            auction.getCurrent(),
                            auction.getImage()
                    );
                    extras.putSerializable("AUCOBJ", auctionSerializedModel[0]);
                    extras.putSerializable("OBJ", itemModel[0]);
                    extras.putSerializable("SPINEERITEMS", (Serializable) aucSpineerModels);
                    intent.putExtras(extras);
                    intent2.putExtras(extras);
                    //intent3.putExtras(extras);
                    if (auction.getBidtype() == 1) {
                        if (!auction.isFinishedstep())
                            context.startActivity(intent);
                        else {
                            context.startActivity(new Intent(context, BiddingListActivity.class)
                                    .putExtra(Constants.KEY_POST_ID, auction.getId() + "")
                                    .putExtra("CURRENT", auction.getCurrent() + "")
                                    .putExtra("KIND", auction.getAuctionkind() + "").
                                            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        }
                    } else if (auction.getBidtype() == 0) {
                        context.startActivity(intent2);
                    } else if (auction.getBidtype() == 2) {
                        context.startActivity(new Intent(context, ResultsActivitty.class)
                                .putExtra(Constants.KEY_POST_ID, auction.getId() + "")
                                .putExtra("CURRENT", auction.getCurrent() + "")
                                .putExtra("KIND", auction.getAuctionkind() + "")
                                .putExtra("NAME", auction.getName() + "")
                                .putExtra("Image", auction.getImage() + "").
                                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.no_internet_connection) + "", Toast.LENGTH_SHORT).show();
                }
*/
            }
        });
    }


    /*
     * ViewHolder Class To Get View Components.
     * */
    class ViewHolderPost {
        TextView prodName;
        TextView highPrice;
        TextView custName;
        TextView natIdNum;
        TextView newTimer;
        ImageView imageViewProduct;
        LinearLayout cardView;

        public ViewHolderPost(View root) {

            prodName = root.findViewById(R.id.itemNameTextView);
            highPrice = root.findViewById(R.id.itemPriceTextView);
            custName = root.findViewById(R.id.userNameTextView);
            natIdNum = root.findViewById(R.id.natIDEditTextView);
            newTimer = root.findViewById(R.id.newTimer);
            imageViewProduct = root.findViewById(R.id.productImage);
            cardView = root.findViewById(R.id.catCardView);
        }
    }
}
