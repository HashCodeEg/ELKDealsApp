package com.elkdeals.mobile.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.Utils;
import com.elkdeals.mobile.api.models.InvoiceDetail;
import com.elkdeals.mobile.custom.RoundImageView;
import com.elkdeals.mobile.interfaces.ItemsHolder;
import com.elkdeals.mobile.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BillsPayAdapter extends RecyclerView.Adapter<BillsPayAdapter.ViewHolder> implements ItemsHolder {

    private ArrayList<InvoiceDetail> bills;
    private OnItemClickListener onItemClickListener;

    public BillsPayAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener=onItemClickListener;
        bills = new ArrayList<>();
    }

    @Override
    public void addItems(List items) {
        if (items != null && items.size() > 0 && items.get(0) instanceof InvoiceDetail) {
            int start = bills.size();
            bills.addAll(items);
            notifyItemRangeInserted(start - 1, items.size());
        }
    }

    @Override
    public void addItems(Object item) {
        if (item instanceof InvoiceDetail) {
            bills.add((InvoiceDetail) item);
            notifyItemInserted(bills.size() - 1);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_bill_pay, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        InvoiceDetail invoice = bills.get(i);
        if(invoice==null)
            return;
        viewHolder.auctionPrice.setText(invoice.getEndprice() + "");
        viewHolder.auctionTitle.setText(invoice.getName()+"");
        if(onItemClickListener!=null)
        viewHolder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnItemClick(invoice);
            }
        });
        Utils.loadImage(viewHolder.image,invoice.getImage(),true);

    }

    @Override
    public int getItemCount() {
        return bills.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        RoundImageView image;
        @BindView(R.id.auction_title)
        TextView auctionTitle;
        @BindView(R.id.auction_price)
        TextView auctionPrice;
        @BindView(R.id.bill_remove)
        View remove;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
