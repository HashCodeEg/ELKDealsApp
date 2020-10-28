package com.elkdeals.mobile.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.api.models.product_model.Value;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by islam assem on 19/8/2018
 */
public class SpinnerAdapter extends ArrayAdapter {

    LayoutInflater inflater;
    private Context context;
    private int which;
    private ArrayList<Value> list;

    public SpinnerAdapter(Context context, int LayoutId, List<Value> items) {
        super(context, LayoutId);
        this.context = context;
        list =new ArrayList<>();
        list.addAll(items);
        inflater = LayoutInflater.from(context);
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        @SuppressLint("ViewHolder")
        View view = inflater.inflate(R.layout.spinner_item, parent, false);
        TextView textView = view.findViewById(R.id.spinner_item);
        textView.setText(list.get(position).getValue()+" : "+list.get(position).getPricePrefix()+" "+list.get(position).getPrice());
        return view;
    }

    @NonNull
    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.dropdown_item, parent, false);
        if (position == 0)
            view.setBackgroundResource(R.drawable.ic_dropdow_first);
        else if (position < list.size() - 1)
            view.setBackgroundResource(R.drawable.ic_dropdow_middle);
        else if (position == list.size() - 1)
            view.setBackgroundResource(R.drawable.ic_dropdow_last);
        TextView textView = view.findViewById(R.id.spinner_item);
        textView.setText(list.get(position).getValue()+" : "+list.get(position).getPricePrefix()+" "+list.get(position).getPrice());
        return view;
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }
}

