package com.elkdeals.mobile.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.api.models.Request;
import com.elkdeals.mobile.interfaces.ItemsHolder;
import com.elkdeals.mobile.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.ViewHolder> implements ItemsHolder {
    public static final int ViewHolder_ADD = 100;
    public static final int ViewHolder_REQUEST = 101;
    public OnItemClickListener onRequestClickListener;
    ArrayList<Request> requests;

    public RequestsAdapter() {
        requests = new ArrayList<>();
    }

    public void setOnAddClickListener(OnItemClickListener onAddClickListener) {

    }

    public void setOnRequestClickListener(OnItemClickListener onRequestClickListener) {
        this.onRequestClickListener = onRequestClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        return position == getItemCount() ? ViewHolder_ADD : ViewHolder_REQUEST;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int res = R.layout.recycler_request;
        if (viewType == ViewHolder_ADD)
            res = R.layout.recycler_request_add;
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(res, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public void addItems(List items) {
        if (items != null && items.size() > 0 && items.get(0) instanceof Request)
            requests.addAll(items);
    }

    @Override
    public void addItems(Object item) {
        if (item instanceof Request)
            requests.add((Request) item);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
