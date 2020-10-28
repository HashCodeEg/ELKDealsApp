package com.elkdeals.mobile.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.Utils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by islam assem on 19/8/2018
 */
public class SpinnerNumbersAdapter extends ArrayAdapter {

    LayoutInflater inflater;
    private Context context;
    private int which;
    private ArrayList<Integer> list;
    int dropdowView,spinnerView;
    public SpinnerNumbersAdapter(Context context,@LayoutRes int dropdown, @LayoutRes int view, List<Integer> items) {
        super(context, dropdown);
        this.context = context;
        dropdowView=dropdown;
        spinnerView=view;
        list =new ArrayList<>();
        list.addAll(items);
        inflater = LayoutInflater.from(context);
    }
    public SpinnerNumbersAdapter(Context context,@LayoutRes int dropdown, @LayoutRes int view, int start,int end) {
        super(context, dropdown);
        this.context = context;
        dropdowView=dropdown;
        spinnerView=view;
        list =new ArrayList<>();
        for(int i=start;i<end+1;i++)
            list.add(i);
        inflater = LayoutInflater.from(context);
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        @SuppressLint("ViewHolder")
        View view = inflater.inflate(spinnerView, parent, false);
        TextView textView = view.findViewById(R.id.spinner_item);
        textView.setText(list.get(position)+ " "+ Utils.getStringRes(R.string.day));
        return view;
    }

    @NonNull
    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(dropdowView, parent, false);
        if (position == 0)
            view.setBackgroundResource(R.drawable.ic_dropdow_first);
        else if (position < list.size() - 1)
            view.setBackgroundResource(R.drawable.ic_dropdow_middle);
        else if (position == list.size() - 1)
            view.setBackgroundResource(R.drawable.ic_dropdow_last);
        TextView textView = view.findViewById(R.id.spinner_item);
        textView.setText(list.get(position)+ " "+ Utils.getStringRes(R.string.day));
        return view;
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }
}

