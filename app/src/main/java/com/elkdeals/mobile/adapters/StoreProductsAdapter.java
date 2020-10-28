package com.elkdeals.mobile.adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.Utils;
import com.elkdeals.mobile.api.models.StoreProductModel;
import com.elkdeals.mobile.interfaces.AddToCartListenre;
import com.elkdeals.mobile.interfaces.ItemsHolder;
import com.elkdeals.mobile.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoreProductsAdapter extends Adapter<StoreProductsAdapter.ViewHolder> implements ItemsHolder {

    ArrayList<StoreProductModel> products;
    AddToCartListenre cartListener;
    OnItemClickListener onItemClickListener;

    public StoreProductsAdapter() {
        products = new ArrayList<>();
    }

    public StoreProductsAdapter(AddToCartListenre cartListener) {
        this.cartListener = cartListener;
        products = new ArrayList<>();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setCartListener(AddToCartListenre cartListener) {
        this.cartListener = cartListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StoreProductModel product = products.get(position);
        holder.description.setText(product.getDescription());
        holder.price.setText(product.getLot());
        String img = product.getImg1();
        if (TextUtils.isEmpty(img))
            img = product.getImg2();
        if (TextUtils.isEmpty(img))
            img = product.getImg3();
        Utils.loadImage(holder.img, img);
        if (cartListener != null)
            holder.addToCart.setOnClickListener(v -> cartListener.AddItemToCart(product));
        if (onItemClickListener != null)
            holder.itemView.setOnClickListener(v -> onItemClickListener.OnItemClick(product));
    }

    @Override
    public int getItemCount() {
        return products == null ? 0 : products.size();
    }

    @Override
    public void addItems(List items) {
        if (items != null && items.size() > 0 && items.get(0) instanceof StoreProductModel) {
            int pos = products.size() - 1;
            products.addAll(items);
            notifyItemRangeInserted(pos, items.size());
        }
    }

    @Override
    public void addItems(Object item) {
        if (item instanceof StoreProductModel) {
            products.add((StoreProductModel) item);
            notifyItemInserted(products.size() - 1);
        }

    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.description)
        TextView description;
        @BindView(R.id.product_image)
        ImageView img;
        @BindView(R.id.add_to_cart)
        ImageView addToCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
