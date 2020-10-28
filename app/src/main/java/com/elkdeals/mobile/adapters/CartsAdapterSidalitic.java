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
import com.elkdeals.mobile.api.models.cart_model.CartProduct;
import com.elkdeals.mobile.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartsAdapterSidalitic extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int ADD_PRODUCTS_VIEW_HOLDER = 100;
    private static final int CART_VIEW_HOLDER = 200;
    ArrayList<CartProduct> carts;
    OnItemClickListener onIncrementClickListener, onDecrementClickListener, onAddProductsClickListener;

    public CartsAdapterSidalitic(OnItemClickListener.onIncrementClickListener onIncrementClickListener, OnItemClickListener.onDecrementClickListener onDecrementClickListener, OnItemClickListener.onAddProductsClickListener onAddProductsClickListener) {
        this.onIncrementClickListener = onIncrementClickListener;
        this.onDecrementClickListener = onDecrementClickListener;
        this.onAddProductsClickListener = onAddProductsClickListener;
        carts = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (inflater == null) return null;
        if (viewType == ADD_PRODUCTS_VIEW_HOLDER)
            return new AddProductsViewHolder(inflater.inflate(R.layout.add_products_button, parent, false));
        else return new CartViewHolder(inflater.inflate(R.layout.recycler_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == CART_VIEW_HOLDER) {
            bindCart((CartViewHolder) holder, position);
        } else if (holder.getItemViewType() == ADD_PRODUCTS_VIEW_HOLDER)
            if (onAddProductsClickListener != null) {
                holder.itemView.setOnClickListener(v -> onAddProductsClickListener.OnItemClick(null));
            }

    }

    private void bindCart(CartViewHolder holder, int position) {
        CartProduct cart = carts.get(position);
        if (cart == null)
            return;
        holder.productTitle.setText(cart.getCustomersBasketProduct().getProductsName());
        holder.seller.setText("");
        holder.quantity.setText(cart.getCustomersBasketProduct().getCustomersBasketQuantity() + "");
        //holder.note.setText(cart.getDescription() + "");
        holder.price_unit.setText(cart.getCustomersBasketProduct().getTotalPrice() + "");
        Utils.loadImage(holder.img, cart.getCustomersBasketProduct().getProductsImage());
        if (onIncrementClickListener != null)
            holder.increment.setOnClickListener(v -> onIncrementClickListener.OnItemClick(cart));
        if (onDecrementClickListener != null)
            holder.decrement.setOnClickListener(v -> onDecrementClickListener.OnItemClick(cart));

    }

    @Override
    public int getItemCount() {
        return carts == null ? 1 : carts.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1)
            return ADD_PRODUCTS_VIEW_HOLDER;
        else return CART_VIEW_HOLDER;
    }

    public void addItems(List items) {
        if (items != null && items.size() > 0 && items.get(0) instanceof CartProduct) {
            carts.clear();
            int pos = carts.size() - 1;
            if(pos<0)pos=0;
            carts.addAll(items);
            notifyItemRangeInserted(pos, items.size());
        }

    }


    public static class CartViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.product_title)
        TextView productTitle;
        @BindView(R.id.seller)
        TextView seller;
        @BindView(R.id.quantity)
        TextView quantity;
        @BindView(R.id.quantity_button)
        TextView quantity_;
        @BindView(R.id.note)
        TextView note;
        @BindView(R.id.price_unit)
        TextView price_unit;
        @BindView(R.id.cart_item_image)
        ImageView img;
        @BindView(R.id.increment)
        ImageView increment;
        @BindView(R.id.decrement)
        ImageView decrement;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class AddProductsViewHolder extends RecyclerView.ViewHolder {
        public AddProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
