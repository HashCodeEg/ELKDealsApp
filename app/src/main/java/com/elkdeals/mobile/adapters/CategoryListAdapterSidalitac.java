package com.elkdeals.mobile.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.Utils;
import com.elkdeals.mobile.api.models.category_model.CategoryDetails;
import com.elkdeals.mobile.api.network.APIClient;
import com.elkdeals.mobile.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;


/**
 * CategoryListAdapter is the adapter class of RecyclerView holding List of Categories in MainCategories
 **/

public class CategoryListAdapterSidalitac extends RecyclerView.Adapter<CategoryListAdapterSidalitac.MyViewHolder> {

    boolean isSubCategory;

    Context context;
    List<CategoryDetails> categoryList,filteredList;
    private OnItemClickListener onCategoryClickListener;

    public CategoryListAdapterSidalitac(Context context, List<CategoryDetails> categoriesList, boolean isSubCategory) {
        this.context = context;
        this.isSubCategory = isSubCategory;
        this.categoryList = categoriesList;
        filteredList=categoriesList;
    }

    public void setOnCategoryClickListener(OnItemClickListener onCategoryClickListener) {
        this.onCategoryClickListener = onCategoryClickListener;
    }


//********** Called to Inflate a Layout from XML and then return the Holder *********//

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_category_store, parent, false);

        return new MyViewHolder(itemView);
    }

/////////////////////////////////////////

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredList = categoryList;
                } else {
                    List<CategoryDetails> filteredListt = new ArrayList<>();
                    for (CategoryDetails row :categoryList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getName().contains(charSequence)) {
                            filteredListt.add(row);
                        }
                    }

                    filteredList = filteredListt;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredList = (ArrayList<CategoryDetails>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    //********** Called by RecyclerView to display the Data at the specified Position *********//

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // Get the data model based on Position
        final CategoryDetails categoryDetails = filteredList.get(position);

        Utils.loadImage(holder.category_image,APIClient.BASE_URL ,categoryDetails.getImage());
        holder.progressBar.setVisibility(View.GONE);
        holder.category_title.setText(categoryDetails.getName());
        if(onCategoryClickListener!=null)
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCategoryClickListener.OnItemClick(categoryDetails);
                }
            });
    }



    //********** Returns the total number of items in the data set *********//

    @Override
    public int getItemCount() {
        return filteredList==null?0:filteredList.size();
    }



    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

    public class MyViewHolder extends RecyclerView.ViewHolder  {
    
        RelativeLayout category_card;
        ImageView category_image;
        TextView category_title;
        ProgressBar progressBar;


        public MyViewHolder(final View itemView) {
            super(itemView);
            category_image =  itemView.findViewById(R.id.category_image);
            category_title =  itemView.findViewById(R.id.category_name);
            progressBar = itemView.findViewById(R.id.progress);
        }
    }
}

