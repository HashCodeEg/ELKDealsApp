package com.elkdeals.mobile.adapters;


import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.activities.BaseActivity;
import com.elkdeals.mobile.interfaces.OnItemClickListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.elkdeals.mobile.App.getInstance;
import static com.elkdeals.mobile.Utils.Utils.fetchAccentColor;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    private static int  previousclickedItem = 0;
    private static String selectedTAG = "";
    OnItemClickListener onItemClickListener;
    private BaseActivity activity;
    private ArrayList<MenuItem> items;

    public MenuAdapter(@NonNull ArrayList<MenuItem> items,BaseActivity activity, OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        this.activity = activity;
        this.items = items;
        setHasStableIds(true);
        //reload();
    }

    public void setSelected(String tag) {
         selectedTAG = tag;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.nav_menu_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        if (!TextUtils.isEmpty(selectedTAG)&&selectedTAG.equals(items.get(i).tag)) {
            viewHolder.title.setTextColor(fetchAccentColor(viewHolder.itemView.getContext()));
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                viewHolder.title.setTextColor(getInstance().getResources().getColor(R.color.white, getInstance().getTheme()));
            } else
                viewHolder.title.setTextColor(getInstance().getResources().getColor(R.color.white));
        }
        if(items.get(i).title!=-1)
            viewHolder.title.setText(items.get(i).title);
        else viewHolder.title.setText(items.get(i).titleString);
        if (onItemClickListener != null)
            viewHolder.itemView.setOnClickListener(
                    v -> {
                        selectedTAG = items.get(viewHolder.getAdapterPosition()).tag;
                        notifyDataSetChanged();
                        onItemClickListener.OnItemClick(selectedTAG);
                    });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return items.size();//notifications.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.nav_item)
        TextView title;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //title.setTypeface(getRobotoMedium());
        }
    }

    public static class MenuItem {
        private String titleString;
        private int title;
        private String tag;

        public MenuItem(int title, String tag) {
            this.title = title;
            this.tag = tag;
        }
        public MenuItem(String title, String tag) {
            this.titleString = title;
            this.title=-1;
            this.tag = tag;
        }
    }
}
