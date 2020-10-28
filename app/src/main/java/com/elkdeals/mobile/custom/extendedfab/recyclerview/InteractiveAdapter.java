package com.elkdeals.mobile.custom.extendedfab.recyclerview;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;

import com.elkdeals.mobile.custom.extendedfab.util.ViewUtil;


public abstract class InteractiveAdapter<VH extends InteractiveViewHolder, T extends InteractiveAdapter.AdapterListener>
        extends RecyclerView.Adapter<VH> {

    protected T adapterListener;

    public InteractiveAdapter(T adapterListener) {
        this();
        this.adapterListener = adapterListener;
    }

    @SuppressWarnings("WeakerAccess")
    protected InteractiveAdapter() { }

    protected View getItemView(@LayoutRes int res, ViewGroup parent) {
        return ViewUtil.getItemView(res, parent);
    }

    /**
     * An interface for any interaction this adapter carries
     */
    public interface AdapterListener {}
}
