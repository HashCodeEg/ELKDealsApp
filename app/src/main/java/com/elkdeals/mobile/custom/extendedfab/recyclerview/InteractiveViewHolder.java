package com.elkdeals.mobile.custom.extendedfab.recyclerview;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public abstract class InteractiveViewHolder<T extends InteractiveAdapter.AdapterListener>
        extends RecyclerView.ViewHolder {

    protected T adapterListener;

    public InteractiveViewHolder(View itemView) {
        super(itemView);
    }

    public InteractiveViewHolder(View itemView, T adapterListener) {
        super(itemView);
        this.adapterListener = adapterListener;
    }
}
