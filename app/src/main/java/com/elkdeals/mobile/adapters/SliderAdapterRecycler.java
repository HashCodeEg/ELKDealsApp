package com.elkdeals.mobile.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.Utils;
import com.elkdeals.mobile.api.models.SliderM;

import java.util.ArrayList;
import java.util.List;


public class SliderAdapterRecycler extends RecyclerView.Adapter<SliderAdapterRecycler.ViewHolder> {
    List<SliderM> mList;
    View.OnClickListener onSlideClick;
    public SliderAdapterRecycler( View.OnClickListener onSlideClick ) {
        this.onSlideClick = onSlideClick;
        this.mList = new ArrayList<>();
    }

    public void setList(List<SliderM> mList) {
        if (mList!=null)
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from (parent.getContext());
        View root = inflater.inflate(R.layout.slider_row, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SliderM m = mList.get(position);

        Utils.loadImage(holder.slideImg,holder.progressbar,"",m.getMsg(),true);
        holder.slideImg.setOnClickListener(view1 -> {
            view1.setTag(m.getMsg());
            if (onSlideClick!=null)
                onSlideClick.onClick(view1);

        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView slideImg;
        View progressbar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            slideImg = itemView.findViewById(R.id.slideImg);
            progressbar = itemView.findViewById(R.id.progressBar);

        }
    }
}
