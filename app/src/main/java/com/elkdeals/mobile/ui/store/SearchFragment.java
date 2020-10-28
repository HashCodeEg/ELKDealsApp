package com.elkdeals.mobile.ui.store;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.adapters.SearchResultsAdapter;
import com.elkdeals.mobile.api.models.category_model.CategoryData;
import com.elkdeals.mobile.api.models.category_model.CategoryDetails;
import com.elkdeals.mobile.api.models.search_model.MainCategory;
import com.elkdeals.mobile.api.models.search_model.SearchData;
import com.elkdeals.mobile.api.models.search_model.SearchDetails;
import com.elkdeals.mobile.api.models.search_model.SearchResults;
import com.elkdeals.mobile.api.network.APIClient;
import com.elkdeals.mobile.ui.BaseFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;


public class SearchFragment extends BaseFragment {

    private static final String TAG = "SearchFragment";
    private static String searchText;
    private View rootView;

    private EditText search_editText;
    private RecyclerView categories_recycler, products_recycler;

    private List<SearchResults> resultsList;
    private List<CategoryDetails> subCategoriesList;

    private SearchResultsAdapter searchProductsAdapter;
    private SearchResultsAdapter searchCategoriesAdapter;

    public static SearchFragment createInstance(String searchText) {
        SearchFragment searchFragment = new SearchFragment();
        SearchFragment.searchText = searchText;
        return searchFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.search_fragment, container, false);
        return view = rootView;
    }


    //*********** Show Main Categories in the SearchList ********//
/*
    private void showCategories() {

        // Make CategoriesList Visible
        categories_recycler.setVisibility(View.VISIBLE);
        products_recycler.setVisibility(View.GONE);

        resultsList.clear();
        searchCategoriesAdapter.notifyDataSetChanged();


        for (int i = 0; i < subCategoriesList.size(); i++) {
            // Add the current Category Info to SearchResults
            SearchResults searchResult = new SearchResults();
            searchResult.setId(Integer.parseInt(subCategoriesList.get(i).getId()));
            searchResult.setName(subCategoriesList.get(i).getName());
            searchResult.setImage(subCategoriesList.get(i).getIcon());
            searchResult.setParent("Categories");

            // Add SearchResults to results List
            resultsList.add(searchResult);
        }

        // Notify the Adapter
        searchCategoriesAdapter.notifyDataSetChanged();
        if(searchCategoriesAdapter.getItemCount()==0)
            showEmpty();

    }
*/


    private void showCategories() {

        // Make CategoriesList Visible
        categories_recycler.setVisibility(View.VISIBLE);
        products_recycler.setVisibility(View.GONE);

        resultsList.clear();
        searchCategoriesAdapter.notifyDataSetChanged();

        for (int i=0;  i<subCategoriesList.size();  i++) {
            // Add the current Category Info to SearchResults
            SearchResults searchResult = new SearchResults();
            searchResult.setId(Integer.parseInt(subCategoriesList.get(i).getId()));
            searchResult.setName(subCategoriesList.get(i).getName());
            searchResult.setImage(subCategoriesList.get(i).getIcon());
            searchResult.setParent("Categories");

            // Add SearchResults to results List
            resultsList.add(searchResult);
        }


        // Notify the Adapter
        searchCategoriesAdapter.notifyDataSetChanged();

    }

    private void showCategories(List<MainCategory> categories) {

        // Make CategoriesList Visible
        categories_recycler.setVisibility(View.VISIBLE);
        products_recycler.setVisibility(View.GONE);

        resultsList.clear();
        searchCategoriesAdapter.notifyDataSetChanged();

        for (int i=0;  i<categories.size();  i++) {
            // Add the current Category Info to SearchResults
            SearchResults searchResult = new SearchResults();
            searchResult.setId((categories.get(i).getId()));
            searchResult.setName(categories.get(i).getName());
            searchResult.setImage(categories.get(i).getImage());
            searchResult.setParent("Categories");

            // Add SearchResults to results List
            resultsList.add(searchResult);
        }


        // Notify the Adapter
        searchCategoriesAdapter.notifyDataSetChanged();

    }


    //*********** Adds SearchResults returned from the Server to the resultsList ********//


    private void addResults(SearchData searchData) {

        // Get the model SearchDetails from SearchData
        SearchDetails searchResults = searchData.getProductData();


        if (searchResults.getProducts().size() > 0) {

            // Make CategoriesList Visible
            categories_recycler.setVisibility(View.GONE);
            products_recycler.setVisibility(View.VISIBLE);

            resultsList.clear();
            searchCategoriesAdapter.notifyDataSetChanged();


            for (int i=0;  i<searchResults.getProducts().size();  i++) {
                // Add the current Product Info to SearchResults
                SearchResults searchResult = new SearchResults();
                searchResult.setId(searchResults.getProducts().get(i).getProductsId());
                searchResult.setName(searchResults.getProducts().get(i).getProductsName());
                searchResult.setImage(searchResults.getProducts().get(i).getProductsImage());
                searchResult.setParent("Products");

                // Add SearchResults to results List
                resultsList.add(searchResult);
            }

            searchProductsAdapter.notifyDataSetChanged();

        }
        else  if (searchResults.getMainCategories().size()>0){
            ArrayList<MainCategory> arrayList = new ArrayList();
            arrayList.addAll(searchResults.getMainCategories());
            arrayList.addAll(searchResults.getSubCategories());
            showCategories(arrayList);
        }
        else{
            showCategories();
        }

    }

   /*
    private void addResults(SearchData searchData) {

        // Get the model SearchDetails from SearchData
        SearchDetails searchResults = searchData.getProductData();

        if (searchResults.getProducts().size() > 0) {

            // Make CategoriesList Visible
            categories_recycler.setVisibility(View.GONE);
            products_recycler.setVisibility(View.VISIBLE);

            resultsList.clear();
            searchCategoriesAdapter.notifyDataSetChanged();


            for (int i = 0; i < searchResults.getProducts().size(); i++) {
                // Add the current Product Info to SearchResults
                SearchResults searchResult = new SearchResults();
                searchResult.setId(searchResults.getProducts().get(i).getProductsId());
                searchResult.setName(searchResults.getProducts().get(i).getProductsName());
                searchResult.setImage(searchResults.getProducts().get(i).getProductsImage());
                searchResult.setParent("Products");

                // Add SearchResults to results List
                resultsList.add(searchResult);
            }

            searchProductsAdapter.notifyDataSetChanged();

        } else {
            if(searchResults.getSubCategories()!=null)
            for(SubCategory x:searchResults.getSubCategories())
            {
                CategoryDetails categoryDetails=new CategoryDetails();
                categoryDetails.setId(String.valueOf(x.getId()));
                categoryDetails.setImage(x.getImage());
                categoryDetails.setName(x.getName());
                categoryDetails.setIcon(x.getImage());
                subCategoriesList.add(categoryDetails);
            }
            if(searchResults.getMainCategories()!=null)
            for(MainCategory x:searchResults.getMainCategories())
            {
                CategoryDetails categoryDetails=new CategoryDetails();
                categoryDetails.setId(String.valueOf(x.getId()));
                categoryDetails.setImage(x.getImage());
                categoryDetails.setName(x.getName());
                categoryDetails.setIcon(x.getImage());
                subCategoriesList.add(categoryDetails);
            }
            showCategories();
        }

    }*/


    //*********** Request Search Results from the Server based on the given Query ********//

    private void RequestSearchData(String searchValue) {

        Call<SearchData> call = APIClient.getInstance()
                .getSearchData
                        (
                                searchValue, 4
                        );

        call.enqueue(new Callback<SearchData>() {
            @Override
            public void onResponse(@NotNull Call<SearchData> call, @NotNull retrofit2.Response<SearchData> response) {

                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {

                        // Search Results have been returned. Add Results to the resultsList
                        addResults(response.body());

                    } else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        Toast.makeText(getContext(),response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        showEmpty();

                    } else {
                        showReload();
                    }
                } else {
                    showReload();
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SearchData> call, Throwable t) {
                Toast.makeText(getContext(), "NetworkCallFailure : " + t, Toast.LENGTH_LONG).show();
                showReload();
            }
        });
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void initViews() {

        setHasOptionsMenu(true);


        // Binding Layout Views
        search_editText = rootView.findViewById(R.id.search_editText);
        products_recycler = rootView.findViewById(R.id.products_recycler);
        categories_recycler = rootView.findViewById(R.id.categories_recycler);


        products_recycler.setNestedScrollingEnabled(false);
        categories_recycler.setNestedScrollingEnabled(false);
        products_recycler.setVisibility(View.GONE);
        // Hide some of the Views
        categories_recycler.setVisibility(View.GONE);


        resultsList = new ArrayList<>();
        subCategoriesList = new ArrayList<>();


        // Get All CategoriesList from ApplicationContext
        //allCategoriesList = ((App) getContext().getApplicationContext()).getCategoriesList();
/*
        for (int i = 0; i < allCategoriesList.size(); i++) {
            if (!allCategoriesList.get(i).getParentId().equalsIgnoreCase("0")) {
                subCategoriesList.add(allCategoriesList.get(i));
            }
        }
*/

        // Initialize the SearchResultsAdapter for RecyclerView
        searchCategoriesAdapter = new SearchResultsAdapter(getContext(), resultsList);

        // Set the Adapter, LayoutManager and ItemDecoration to the RecyclerView
        categories_recycler.setAdapter(searchCategoriesAdapter);
        categories_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        categories_recycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));


        // Initialize the SearchResultsAdapter for RecyclerView
        searchProductsAdapter = new SearchResultsAdapter(getContext(), resultsList);

        // Set the Adapter, LayoutManager and ItemDecoration to the RecyclerView
        products_recycler.setAdapter(searchProductsAdapter);
        products_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        products_recycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        // Get All CategoriesList from ApplicationContext
        List<CategoryDetails> allCategoriesList = CategoryData.read();

        for (int i = 0; i< allCategoriesList.size(); i++) {
            if (!allCategoriesList.get(i).getParentId().equalsIgnoreCase("0")) {
                subCategoriesList.add(allCategoriesList.get(i));
            }
        }


        // Show Categories
        showCategories();


        search_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(search_editText.getText().toString())) {
                    // Show Categories
                    showCategories();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        // Set listener to be called when an action is performed on the search_editText
        search_editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!TextUtils.isEmpty(search_editText.getText().toString())) {
                        RequestSearchData(search_editText.getText().toString());
                        return true;
                    }
                }
                return false;
            }
        });
        if(TextUtils.isEmpty(searchText))
            return;
        search_editText.setText(""+ searchText);
        RequestSearchData(search_editText.getText().toString());
    }


    @OnClick(R.id.error_layout)
    public void reload() {
        initViews();
    }
    @OnClick(R.id.empty_layout)
    void empty_layout() {
        activity.onBackPressed();
    }


    private void showReload(){
        view.findViewById(R.id.error_layout).setVisibility(View.VISIBLE);
        view.findViewById(R.id.empty_layout).setVisibility(View.GONE);
        categories_recycler.setVisibility(View.GONE);
        products_recycler.setVisibility(View.GONE);
    }
    private void showEmpty(){
        view.findViewById(R.id.empty_layout).setVisibility(View.VISIBLE);
        view.findViewById(R.id.error_layout).setVisibility(View.GONE);

        categories_recycler.setVisibility(View.GONE);
        products_recycler.setVisibility(View.GONE);    }
    public void hideReload(){
        view.findViewById(R.id.empty_layout).setVisibility(View.GONE);
        view.findViewById(R.id.error_layout).setVisibility(View.GONE);

        categories_recycler.setVisibility(View.VISIBLE);
        products_recycler.setVisibility(View.VISIBLE);
    }
    @Override
    public String getTAG() {
        return TAG;
    }
}



