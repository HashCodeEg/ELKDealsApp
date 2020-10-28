package com.elkdeals.mobile.adapters;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.elkdeals.mobile.R;
import com.elkdeals.mobile.activities.BaseActivity;
import com.elkdeals.mobile.api.models.category_model.CategoryDetails;
import com.elkdeals.mobile.api.models.search_model.SearchResults;
import com.elkdeals.mobile.api.network.APIClient;
import com.elkdeals.mobile.ui.store.ProductDetailsStore;
import com.elkdeals.mobile.ui.store.ProductsStore;

import java.util.List;

/**
 * SearchResultsAdapter is the adapter class of RecyclerView holding List of Search Results in SearchFragment
 **/

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.MyViewHolder> {

    Context context;
    List<SearchResults> searchResultsList;


    public SearchResultsAdapter(Context context, List<SearchResults> searchResultsList) {
        this.context = context;
        this.searchResultsList = searchResultsList;
    }



    //********** Called to Inflate a Layout from XML and then return the Holder *********//

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_search_results, parent, false);

        return new MyViewHolder(itemView);
    }



    //********** Called by RecyclerView to display the Data at the specified Position *********//

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // Get the data model based on Position
        final SearchResults searchResult = searchResultsList.get(position);

        // Get the Type of SearchItem (i.e Product, SubCategory)
        final String parent = searchResult.getParent();


        holder.search_result_title.setText(searchResult.getName());
        
        // Set Item Image on ImageView with Glide Library
        Glide
            .with(context)
            .load(APIClient.BASE_URL +searchResult.getImage())
            .into(holder.search_result_cover);


        // Handle the SearchItem Click Event
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Check the Type of SearchItem (i.e Product, Categories) to navigate to relevant Fragment
                if (parent.equalsIgnoreCase("Categories")) {
                    // Get SubCategory Info
                    Bundle categoryInfo = new Bundle();
                    categoryInfo.putInt("CategoryID", searchResult.getId());
                    categoryInfo.putString("CategoryName", searchResult.getName());

                    // Navigate to Products of selected SubCategory
                    CategoryDetails categoryDetails=new CategoryDetails();
                    categoryDetails.setId(String.valueOf(searchResult.getId()));
                    categoryDetails.setName(searchResult.getName());
                    ((BaseActivity)context).addPage(ProductsStore.createInstances(categoryDetails));



                } else if (parent.equalsIgnoreCase("Products")) {
                    // Get Product Info
                    Bundle itemInfo = new Bundle();
                    itemInfo.putInt("itemID", searchResult.getId());
                    ((BaseActivity)context).addPage(ProductDetailsStore.createInstance(searchResult.getId()));


                }
            }
        });

    }



    //********** Returns the total number of items in the data set *********//

    @Override
    public int getItemCount() {
        return searchResultsList.size();
    }



    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView search_result_title;
        ImageView search_result_cover;


        public MyViewHolder(final View itemView) {
            super(itemView);

            search_result_title = itemView.findViewById(R.id.search_result_title);
            search_result_cover = itemView.findViewById(R.id.search_result_cover);
        }
    }
}

