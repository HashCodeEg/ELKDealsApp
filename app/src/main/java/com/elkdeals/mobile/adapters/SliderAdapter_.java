package com.elkdeals.mobile.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.Utils;
import com.elkdeals.mobile.api.models.SliderM;

import java.util.List;

public class SliderAdapter_ extends BaseAdapter {
    List<SliderM> mList;
    Context context;

    public SliderAdapter_(List<SliderM> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @Override public int getCount() {
        return mList.size();
    }

    @Override public Object getItem(int i) {
        return mList.get(i);
    }

    @Override public long getItemId(int i) {
        return i;
    }

    @Override public View getView(int i, View view, ViewGroup viewGroup) {
        View root = view;
        ViewHolder holder;
        if (root == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            root = inflater.inflate(R.layout.slider_row, viewGroup, false);
            holder = new ViewHolder(root);
            root.setTag(holder);
        } else {
            holder = (ViewHolder) root.getTag();
        }

        SliderM m = mList.get(i);
        Utils.loadImage(holder.slideImg,"http://elkdeals.com/admin/uploads/",m.getMsg());
        holder.slideImg.setOnClickListener(view1 -> {
          //  DialogFragment dialogFragment = new SliderDialog(m.getMsg() + "");
           // dialogFragment.show(((AppCompatActivity) context).getFragmentManager(), "SliderDialog");
        });

        return root;
    }

    static class ViewHolder {
        ImageView slideImg;

        ViewHolder(View view) {
            slideImg = view.findViewById(R.id.slideImg);
        }
    }
}
