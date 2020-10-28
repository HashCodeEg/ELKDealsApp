package com.elkdeals.mobile.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.elkdeals.mobile.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohamed on 2/19/17.
 *
 * This Adapter For Any Custom Spinner List
 */

public class SpinnerAdapterD extends BaseAdapter {

    Context context;
    int inflaterID;
    List<String> allAuctionsList = new ArrayList<>();


    public SpinnerAdapterD(Context context, int inflater, List<String> allAuctionsList) {
        this.context = context;
        this.inflaterID = inflater;
        this.allAuctionsList = allAuctionsList;
    }

    @Override
    public int getCount() {
        return allAuctionsList.size();
    }

    @Override
    public Object getItem(int position) {
        return allAuctionsList.get(position);
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
        String post = allAuctionsList.get(position);
        holder.prodName.setText(post);


        return root;
    }

    class ViewHolderPost {
        TextView prodName;

        public ViewHolderPost(View root) {
            prodName = root.findViewById(R.id.prodName);

        }
    }
}
