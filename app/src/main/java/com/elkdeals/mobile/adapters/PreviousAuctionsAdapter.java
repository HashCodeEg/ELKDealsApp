package com.elkdeals.mobile.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.Utils;
import com.elkdeals.mobile.api.API;
import com.elkdeals.mobile.api.models.FinishedAuctionsModel;
import com.elkdeals.mobile.interfaces.ItemsHolder;
import com.elkdeals.mobile.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PreviousAuctionsAdapter extends RecyclerView.Adapter<PreviousAuctionsAdapter.ViewHolder> implements ItemsHolder {
    private final OnItemClickListener onItemClickListener;
    ArrayList<FinishedAuctionsModel> auctions;

    public PreviousAuctionsAdapter(OnItemClickListener onItemClickListener) {
        auctions = new ArrayList<>();
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_previous_auction, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FinishedAuctionsModel auctionsModel = auctions.get(position);
        if (auctionsModel == null)
            return;
        holder.client.setText(auctionsModel.getCname());
        holder.installments.setText(auctionsModel.getMonths() + "");
        holder.monthly_installment.setText(auctionsModel.getInstallment() + "");
        holder.productTitle.setText(auctionsModel.getName() + "");
        Utils.loadImage(holder.image,holder.progress, API.IMAGE_BASE_URL, auctionsModel.getImage(),true);
    }

    @Override
    public int getItemCount() {
        return auctions == null ? 0 : auctions.size();
    }

    @Override
    public void addItems(List items) {
        if (items != null && items.size() > 0 && items.get(0) instanceof FinishedAuctionsModel) {
            int pos = auctions.size() - 1;
            auctions.addAll(items);
            notifyItemRangeInserted(pos, items.size());
        }
    }

    @Override
    public void addItems(Object item) {
        if (item instanceof FinishedAuctionsModel) {
            auctions.add((FinishedAuctionsModel) item);
            notifyItemInserted(auctions.size() - 1);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.client)
        TextView client;
        @BindView(R.id.installments)
        TextView installments;
        @BindView(R.id.monthly_installment)
        TextView monthly_installment;
        @BindView(R.id.product_title)
        TextView productTitle;
        @BindView(R.id.progress)
        View progress;
        @BindView(R.id.video)
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
