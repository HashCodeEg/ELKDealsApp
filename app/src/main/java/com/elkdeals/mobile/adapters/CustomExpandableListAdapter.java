package com.elkdeals.mobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.api.models.category_model.CategoryDetails;
import com.elkdeals.mobile.interfaces.OnItemClickListener;

import java.util.HashMap;
import java.util.List;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<CategoryDetails> expandable;

    private List<String> expandableListTitleE;
    private HashMap<String, List<String>> expandableListDetaiLl;
    private OnItemClickListener OnCheckedItemsCountChanged,onItemClickListener;

    public CustomExpandableListAdapter(Context context,List<CategoryDetails> expandable) {
        this.context = context;
        this.expandable = expandable;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return expandable.get(listPosition).getSubCategories().get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = getChild(listPosition, expandedListPosition).toString();
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.recycler_subcategory_title, null);
        }
        TextView expandedListTextView = convertView
                .findViewById(R.id.category_name);
        expandedListTextView.setText(expandedListText);
        //if(onItemClickListener!=null)
       // convertView.setOnClickListener(v -> onItemClickListener.OnItemClick(getChild(listPosition, expandedListPosition)));
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandable.get(listPosition).getSubCategories()
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandable.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandable.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = getGroup(listPosition).toString();
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.recycler_category_title, null);
        }
        TextView listTitleTextView = convertView
                .findViewById(R.id.category_name);
        //listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }

    public void setOnCategoryClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener=onItemClickListener;
    }

    public void setOnCheckedItemsCountChanged(OnItemClickListener OnCheckedItemsCountChanged) {
        this.OnCheckedItemsCountChanged=OnCheckedItemsCountChanged;
    }
}
