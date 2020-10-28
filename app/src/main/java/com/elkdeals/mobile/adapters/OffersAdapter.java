package com.elkdeals.mobile.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.api.models.requests.OfferDetails;
import com.elkdeals.mobile.interfaces.ItemsHolder;
import com.elkdeals.mobile.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.ViewHolder> implements ItemsHolder {

    OnItemClickListener onItemClickListener;
    private ArrayList<OfferDetails> offers;

    public OffersAdapter(OnItemClickListener onItemClickListener) {
        offers = new ArrayList<>();
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void addItems(List items) {
        offers.clear();
        if (items != null && items.size() > 0 && items.get(0) instanceof OfferDetails)
            offers.addAll(items);

        notifyDataSetChanged();
    }

    @Override
    public void addItems(Object item) {

        offers.clear();
        if (item instanceof OfferDetails)
            offers.add((OfferDetails) item);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_offer, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        OfferDetails offerDetails=offers.get(i);
        viewHolder.title.setText(offerDetails.getTitle()+"");
        viewHolder.price.setText(offerDetails.getPrice()+"");
        viewHolder.shippingtime.setText(offerDetails.getDeliver_time()+"");
        viewHolder.shipping_fees.setText(offerDetails.getShipping_price()+"");
        if(onItemClickListener!=null)
            viewHolder.acceptOffer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.OnItemClick(offerDetails);
                }
            });
    }

    @Override
    public int getItemCount() {
        return offers==null?0:offers.size();//notifications.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.product_title)
        TextView title;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.shippingtime)
        TextView shippingtime;
        @BindView(R.id.shipping_fees)
        TextView shipping_fees;
        @BindView(R.id.offer_accept)
        TextView acceptOffer;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
