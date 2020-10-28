package com.elkdeals.mobile.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.api.models.requests.RequestDetails;
import com.elkdeals.mobile.interfaces.ItemsHolder;
import com.elkdeals.mobile.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyRequestsAdapter extends RecyclerView.Adapter<MyRequestsAdapter.ViewHolder> implements ItemsHolder {

    private boolean showDelete;
    OnItemClickListener onItemClickListener;
    private ArrayList<RequestDetails> requests;

    public MyRequestsAdapter(OnItemClickListener onItemClickListener) {
        requests = new ArrayList<>();
        this.onItemClickListener = onItemClickListener;
        showDelete=true;
    }
    public MyRequestsAdapter(OnItemClickListener onItemClickListener,boolean showDelete) {
        requests = new ArrayList<>();
        this.onItemClickListener = onItemClickListener;
        this.showDelete=showDelete;
    }

    @Override
    public void addItems(List items) {
        if (items != null && items.size() > 0 && items.get(0) instanceof RequestDetails)
            requests.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public void addItems(Object item) {
        if (item instanceof RequestDetails)
            requests.add((RequestDetails) item);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_myrequest, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        RequestDetails requestDetails=requests.get(i);
        if(requestDetails==null)
            return;
        viewHolder.request_id.setText(requestDetails.getRequest_id()+"");
        viewHolder.request_product.setText(requestDetails.getTitle()+"");
        //viewHolder.request_offers.setText(requestDetails.get+"");
        viewHolder.request_date.setText(requestDetails.getCreateAt()+"");
        if(requestDetails.getNew_status().equalsIgnoreCase("1"))
        viewHolder.new_status.setChecked(true);
        else viewHolder.new_status.setChecked(false);
        if(requestDetails.getUsed_status().equalsIgnoreCase("1"))
        viewHolder.used_status.setChecked(true);
        else viewHolder.used_status.setChecked(false);
        viewHolder.new_status.setEnabled(false);
        viewHolder.used_status.setEnabled(false);
        viewHolder.description.setText(requestDetails.getDescription());
        if(!showDelete){
            viewHolder.request_remove.setText(R.string.make_offer);
            viewHolder.request_remove.setBackgroundResource(R.drawable.edit_information_buuton);
        }
        else viewHolder.request_remove.setVisibility(View.GONE);
        if(onItemClickListener!=null&&!showDelete){
            viewHolder.request_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.OnItemClick(requestDetails);
                }
            });
        }
        else viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnItemClick(requestDetails);
            }
        });

    }

    @Override
    public int getItemCount() {
        return requests==null?0:requests.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.request_id)
        TextView request_id;
        @BindView(R.id.request_product)
        TextView request_product;
        @BindView(R.id.request_offers)
        TextView request_offers;
        @BindView(R.id.request_date)
        TextView request_date;
        @BindView(R.id.request_remove)
        TextView request_remove;
        @BindView(R.id.description)
        TextView description;
        @BindView(R.id.new_status)
        CheckBox new_status;
        @BindView(R.id.used_status)
        CheckBox used_status;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
