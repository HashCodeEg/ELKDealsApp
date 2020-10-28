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
import com.elkdeals.mobile.Utils.Utils;
import com.elkdeals.mobile.api.models.CategoriesModel;
import com.elkdeals.mobile.interfaces.ItemsHolder;
import com.elkdeals.mobile.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class StoreCategoriesAdapter extends RecyclerView.Adapter<StoreCategoriesAdapter.ViewHolder> implements ItemsHolder {
    private final int mRes;
    ArrayList<CategoriesModel> categories;
    OnItemClickListener onCategoryClickListener;
    private Context mContext;

    public StoreCategoriesAdapter(Context context, @LayoutRes int res) {
        mContext = context;
        mRes = res;
        categories = new ArrayList<>();
    }

    public void setOnCategoryClickListener(OnItemClickListener onCategoryClickListener) {
        this.onCategoryClickListener = onCategoryClickListener;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoriesModel model = categories.get(position);
        Utils.loadImage(holder.img, model.getImage());
        holder.title.setText(model.getName());
        if (onCategoryClickListener != null)
            holder.itemView.setOnClickListener(v -> onCategoryClickListener.OnItemClick(model));
    }

    @Override
    public void addItems(List items) {
        if (items != null && items.size() > 0 && items.get(0) instanceof CategoriesModel) {
            categories.addAll(items);
            notifyDataSetChanged();
        }
    }

    @Override
    public void addItems(Object item) {
        if (item instanceof CategoriesModel) {
            categories.add((CategoriesModel) item);
            notifyItemInserted(categories.size() - 1);
        }
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
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