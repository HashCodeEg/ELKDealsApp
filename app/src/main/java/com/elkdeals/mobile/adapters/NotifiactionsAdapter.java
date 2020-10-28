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
import com.elkdeals.mobile.api.models.NotificationModel;
import com.elkdeals.mobile.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotifiactionsAdapter extends RecyclerView.Adapter<NotifiactionsAdapter.ViewHolder> {

    OnItemClickListener onItemClickListener;
    private ArrayList<NotificationModel> notifications;

    public NotifiactionsAdapter(OnItemClickListener onItemClickListener) {
        notifications = new ArrayList<>();
        this.onItemClickListener = onItemClickListener;
    }

    public void addItems(List<NotificationModel> items) {
        boolean noUpdate = false;
        int oldsize = notifications.size();
        if (items != null) {
            Collections.reverse(items);
            for (int i = items.size() - 1; i >= 0; i--) {
                NotificationModel notification = items.get(i);
                if (notifications.contains(notification)) {
                    notifications.set(notifications.indexOf(notification), notification);
                    noUpdate = true;
                } else {
                    notifications.add(0, notification);
                }
            }
        }
        if (noUpdate)
            notifyDataSetChanged();
        else if (notifications.size() - oldsize == 1)
            notifyItemInserted(0);
        else notifyItemRangeInserted(0, notifications.size() - oldsize);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_notification, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        NotificationModel model=notifications.get(i);
        if(model.getStatus()==1) {
            viewHolder.itemView.setBackgroundColor(Utils.getColorRes(R.color.gray));
            viewHolder.imag.setVisibility(View.INVISIBLE);
        }
        viewHolder.title.setText(model.getTitle());
        viewHolder.date.setText(model.getDate());
        if (onItemClickListener != null)
            viewHolder.itemView.setOnClickListener(v -> onItemClickListener.OnItemClick(model));
        //Utils.loadImage(viewHolder.imag,notifications.get(i).getImgUrl());
    }

    @Override
    public int getItemCount() {
        return notifications==null?0:notifications.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.notification_title)
        TextView title;
        @BindView(R.id.notification_date)
        TextView date;
        @BindView(R.id.notification_read)
        ImageView imag;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
