package com.elkdeals.mobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.api.models.CategoriesModel;
import com.elkdeals.mobile.interfaces.ItemsHolder;
import com.elkdeals.mobile.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class AuctionsCategoriesAdapter extends RecyclerView.Adapter<AuctionsCategoriesAdapter.ViewHolder> implements ItemsHolder {
    private final int mRes;
    ArrayList<CategoriesModel> categories;
    private Context mContext;
    private OnItemClickListener onItemClickListener;

    public AuctionsCategoriesAdapter(Context context, @LayoutRes int res) {
        mContext = context;
        mRes = res;
        categories = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return categories == null ? 0 : categories.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(mRes, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        CategoriesModel categoriesModel = categories.get(position);
        if (categoriesModel == null)
            return;
        viewHolder.img.setImageResource(categoriesModel.getRes());
        viewHolder.title.setText(categoriesModel.getName());
        if (onItemClickListener != null)
            viewHolder.itemView.setOnClickListener(
                    v -> onItemClickListener.OnItemClick(String.valueOf(categoriesModel.getId())));
    }

    @Override
    public void addItems(List items) {
        if (items != null && items.size() > 0 && items.get(0) instanceof CategoriesModel)
            categories.addAll(items);
    }

    @Override
    public void addItems(Object item) {
        if (item instanceof CategoriesModel)
            categories.add((CategoriesModel) item);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView img;
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            img = itemView.findViewById(R.id.category_image);
            title = itemView.findViewById(R.id.category_name);
        }
    }
}