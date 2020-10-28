package com.elkdeals.mobile.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.api.models.AucDescriptionModel;
import com.elkdeals.mobile.interfaces.ItemsHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KeyValueAdapter extends RecyclerView.Adapter<KeyValueAdapter.ViewHolder> implements ItemsHolder {
    int res;
    ArrayList<AucDescriptionModel> details;

    public KeyValueAdapter(@LayoutRes int res) {
        this.res = res;
        details = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(res, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.key.setText(details.get(position).getName());
        holder.value.setText(details.get(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return details == null ? 0 : details.size();
    }

    @Override
    public void addItems(List items) {
        if (items != null && items.size() > 0 && items.get(0) instanceof AucDescriptionModel) {
            int pos = details.size();
            details.addAll(items);
            notifyItemRangeInserted(pos - 1, items.size());
        }
    }

    @Override
    public void addItems(Object item) {
        if (item instanceof AucDescriptionModel) {
            details.add((AucDescriptionModel) item);
            notifyItemInserted(details.size() - 1);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.key)
        TextView key;
        @BindView(R.id.value)
        TextView value;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
