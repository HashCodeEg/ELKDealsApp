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
import com.elkdeals.mobile.api.models.BidderM;
import com.elkdeals.mobile.model.Repository;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mohamed on 3/18/18.
 */

public class BiddingListAdapter extends BaseAdapter {
    List<BidderM> biddingList = new ArrayList<>();
    Context context;
    int layoutId;
    String auctionKind;

    public BiddingListAdapter(List<BidderM> biddingList, Context context, int layoutId, String auctionKind) {
        this.biddingList = biddingList;
        this.context = context;
        this.layoutId = layoutId;
        this.auctionKind = auctionKind;
    }

    @Override
    public int getCount() {
        return biddingList.size();
    }

    @Override
    public Object getItem(int i) {
        return biddingList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View root = view;

        final ViewHolder holder;
        if (root == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            root = inflater.inflate(R.layout.biding_row_list, viewGroup, false);
            holder = new ViewHolder(root);


            root.setTag(holder);
        } else {
            holder = (ViewHolder) root.getTag();
        }
        BidderM post = biddingList.get(i);
        holder.bidderNameTxt.setText(post.getName() + "");
        holder.biddingDateTxt.setText(post.getDate() + "");
        holder.biddingPriceTxt.setText(post.getPrice() + "");
        holder.cashTxt.setText(post.getCashpricex() + "");

        if (auctionKind.equals("2")) {
            holder.biddingDaysPriceTxt.setText(post.getPricex() + "");
            holder.priceOrDaysTxt.setText("الايام");
            holder.daysPriceLine.setVisibility(View.VISIBLE);
        } else {
            holder.daysPriceLine.setVisibility(View.GONE);
            holder.priceOrDaysTxt.setText("السعر");
        }

        String cid = Repository.getUserInfo().getId();

        if (cid.equals(post.getCid() + "")) {
            if (i == 0) {
                int green = context.getResources().getColor(R.color.Orange);
                holder.bidRowLine.setBackgroundColor(green);
                holder.bidderNameTxt.setBackgroundColor(green);
                holder.biddingDaysPriceTxt.setBackgroundColor(green);
                holder.biddingPriceTxt.setBackgroundColor(green);
                holder.biddingDateTxt.setBackgroundColor(green);
                holder.cashTxt.setBackgroundColor(green);

                holder.bidderNameTxt.setTextColor(Color.BLACK);
                holder.biddingPriceTxt.setTextColor(Color.BLACK);
                holder.biddingDateTxt.setTextColor(Color.BLACK);
                holder.biddingDaysPriceTxt.setTextColor(Color.BLACK);
                holder.cashTxt.setTextColor(Color.BLACK);


                holder.startImg.setVisibility(View.VISIBLE);

            } else {
                int red = context.getResources().getColor(R.color.bluuuu);
                holder.bidRowLine.setBackgroundColor(red);
                holder.bidderNameTxt.setBackgroundColor(red);
                holder.biddingPriceTxt.setBackgroundColor(red);
                holder.biddingDateTxt.setBackgroundColor(red);
                holder.biddingDaysPriceTxt.setBackgroundColor(red);
                holder.cashTxt.setBackgroundColor(red);

                holder.bidderNameTxt.setTextColor(Color.WHITE);
                holder.biddingPriceTxt.setTextColor(Color.WHITE);
                holder.biddingDateTxt.setTextColor(Color.WHITE);
                holder.cashTxt.setTextColor(Color.WHITE);
                holder.biddingDaysPriceTxt.setTextColor(Color.WHITE);
                holder.startImg.setVisibility(View.GONE);

            }
        } else {
            int white = context.getResources().getColor(R.color.opacityWhite);
            holder.bidRowLine.setBackgroundColor(Color.WHITE);
            holder.bidRowLine.setBackgroundColor(white);

            holder.bidderNameTxt.setBackgroundColor(white);
            holder.biddingPriceTxt.setBackgroundColor(white);
            holder.biddingDaysPriceTxt.setBackgroundColor(white);
            holder.biddingDateTxt.setBackgroundColor(white);
            holder.cashTxt.setBackgroundColor(white);

            holder.bidderNameTxt.setTextColor(Color.BLACK);
            holder.biddingPriceTxt.setTextColor(Color.BLACK);
            holder.biddingDaysPriceTxt.setTextColor(Color.BLACK);
            holder.cashTxt.setTextColor(Color.BLACK);

            holder.startImg.setVisibility(View.GONE);
            holder.biddingDateTxt.setTextColor(Color.BLACK);
        }

        return root;
    }


    static class ViewHolder {
        @BindView(R.id.bidderNameTxt)
        TextView bidderNameTxt;
        @BindView(R.id.cashTxt)
        TextView cashTxt;
        @BindView(R.id.priceOrDaysTxt)
        TextView priceOrDaysTxt;
        @BindView(R.id.biddingDateTxt)
        TextView biddingDateTxt;
        @BindView(R.id.biddingPriceTxt)
        TextView biddingPriceTxt;
        @BindView(R.id.biddingDaysPriceTxt)
        TextView biddingDaysPriceTxt;
        @BindView(R.id.bidRowLine)
        LinearLayout bidRowLine;
        @BindView(R.id.daysPriceLine)
        LinearLayout daysPriceLine;
        @BindView(R.id.cashLine)
        LinearLayout cashLine;
        @BindView(R.id.startImg)
        ImageView startImg;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}