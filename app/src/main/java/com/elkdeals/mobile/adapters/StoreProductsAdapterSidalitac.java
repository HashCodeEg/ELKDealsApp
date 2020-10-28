package com.elkdeals.mobile.adapters;

import android.text.Html;
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
import com.elkdeals.mobile.api.models.product_model.Image;
import com.elkdeals.mobile.api.models.product_model.ProductData;
import com.elkdeals.mobile.api.models.product_model.ProductDetails;
import com.elkdeals.mobile.api.network.APIClient;
import com.elkdeals.mobile.custom.JustifiedTextView;
import com.elkdeals.mobile.interfaces.AddToCartListenre;
import com.elkdeals.mobile.interfaces.OnItemClickListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoreProductsAdapterSidalitac extends Adapter<StoreProductsAdapterSidalitac.ViewHolder> {

    ArrayList<ProductDetails> products;
    AddToCartListenre cartListener;
    OnItemClickListener onItemClickListener;
    private Boolean isGridView;

    public StoreProductsAdapterSidalitac() {
        products = new ArrayList<>();
    }

    public StoreProductsAdapterSidalitac(AddToCartListenre cartListener) {
        this.cartListener = cartListener;
        products = new ArrayList<>();
    }

    //********** Toggles the RecyclerView LayoutManager *********//

    public void toggleLayout(Boolean isGridView) {
        this.isGridView = isGridView;
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
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                isGridView ? R.layout.recycler_product : R.layout.recycler_product_list
                , parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductDetails product = products.get(position);
        holder.description.setText(Html.fromHtml(product.getProductsDescription()));
        String discount = Utils.checkDiscount(product.getProductsPrice(), product.getDiscountPrice());
        double productBasePrice= Double.parseDouble(product.getProductsPrice());
        if (!TextUtils.isEmpty(discount)) {
            product.setIsSaleProduct("1");

            // Set Discount Tag
            holder.container.setVisibility(View.VISIBLE);
            holder.discount.setText(discount);
            // Set Price info based on Discount
            holder.price_old.setVisibility(View.VISIBLE);
            holder.price_old.setText(productBasePrice+" "+ Utils.getStringRes(R.string.egp));
            holder.price.setText(String.valueOf(product.getDiscountPrice()));
        }
        else {
            product.setIsSaleProduct("0");
            holder.price.setText(productBasePrice+"");
            holder.price_old.setVisibility(View.INVISIBLE);
            holder.container.setVisibility(View.INVISIBLE);
           // productBasePrice = Double.parseDouble(productDetails.getProductsPrice());
        }
        if (TextUtils.isEmpty(product.getProductsImage())) {
            if (product.getImages() != null && product.getImages().size() > 0) {
                for (Image img : product.getImages())
                    if (!TextUtils.isEmpty(img.getImage())) {
                        Utils.loadImage(holder.img, APIClient.BASE_URL, img.getImage());
                        break;
                    }
            }
        } else Utils.loadImage(holder.img, APIClient.BASE_URL, product.getProductsImage());
        if (cartListener != null)
            holder.addToCart.setOnClickListener(v -> cartListener.AddItemToCart(product));
        if (onItemClickListener != null)
            holder.itemView.setOnClickListener(v -> onItemClickListener.OnItemClick(product));
    }

    @Override
    public int getItemCount() {
        return products == null ? 0 : products.size();
    }

    public void addItems(ProductData items) {
        if (items != null && items.getProductData() != null && items.getProductData().size() > 0) {
            int pos = products.size() - 1;
            products.addAll(items.getProductData());
            notifyItemRangeInserted(pos, items.getProductData().size());
        }
    }

    public void clear() {
        if(products!=null)
        products.clear();
        notifyDataSetChanged();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.description)
        JustifiedTextView description;
        @BindView(R.id.product_image)
        ImageView img;
        @BindView(R.id.add_to_cart)
        ImageView addToCart;
        @BindView(R.id.discount)
        TextView discount;
        @BindView(R.id.price_old)
        TextView price_old;
        @BindView(R.id.discount_frame)
        View container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
