package com.elkdeals.mobile.ui.store;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.StoreCartManager;
import com.elkdeals.mobile.Utils.Constants;
import com.elkdeals.mobile.Utils.Utils;
import com.elkdeals.mobile.adapters.EndlessRecyclerViewScroll;
import com.elkdeals.mobile.adapters.StoreProductsAdapterSidalitac;
import com.elkdeals.mobile.api.models.cart_model.CartProduct;
import com.elkdeals.mobile.api.models.cart_model.CartProductAttributes;
import com.elkdeals.mobile.api.models.category_model.CategoryDetails;
import com.elkdeals.mobile.api.models.filter_model.get_filters.FilterData;
import com.elkdeals.mobile.api.models.filter_model.get_filters.FilterDetails;
import com.elkdeals.mobile.api.models.filter_model.post_filters.PostFilterData;
import com.elkdeals.mobile.api.models.product_model.Option;
import com.elkdeals.mobile.api.models.product_model.ProductDetails;
import com.elkdeals.mobile.api.models.product_model.Value;
import com.elkdeals.mobile.api.network.APIClient;
import com.elkdeals.mobile.dialogFragments.FilterDialog;
import com.elkdeals.mobile.interfaces.AddToCartListenre;
import com.elkdeals.mobile.interfaces.OnItemClickListener;
import com.elkdeals.mobile.ui.BaseFragment;
import com.elkdeals.mobile.viewmodel.StoreViewModelSidalitac;
import com.islam.slider.SliderLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

import static com.elkdeals.mobile.Utils.Utils.initSlider;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsStore extends BaseFragment implements AddToCartListenre {


    public static final String TAG = "products";

    GridLayoutManager gridLayoutManager;
    LinearLayoutManager linearLayoutManager;
    StoreCartManager manager = StoreCartManager.getInstance();
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.search_text)
    EditText search_text;
    StoreProductsAdapterSidalitac adapter;
    FilterDialog filterDialog;
    @BindView(R.id.slider)
    public SliderLayout sliderShow;
    @BindView(R.id.filterBtn)
    ToggleButton filterButton;
    @BindView(R.id.layout_toggleBtn)
    ToggleButton toggleLayoutView;

    EndlessRecyclerViewScroll endlessRecyclerViewScroll;
    @BindView(R.id.bottomBar)
    View bottomBar;
    CategoryDetails category;
    private StoreViewModelSidalitac storeViewModel;
    int pageNumber = 0;
    private String sortBy;
    private boolean isGridView;
    private boolean isFilterApplied;
    private double maxPrice;
    private List<FilterDetails> filtersList;
    private boolean isVisible = true;
    private PostFilterData filters;

    public ProductsStore() {
        // Required empty public constructor
    }

    public static ProductsStore createInstances(CategoryDetails category) {
        ProductsStore products = new ProductsStore();
        Bundle data = new Bundle();
        data.putParcelable("cat",category);
        products.setArguments(data);
        products.category = category;
        return products;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("cat", category);
        outState.putString("sortBy", sortBy);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (savedInstanceState != null){
            if (savedInstanceState.containsKey("cat"))
                category = savedInstanceState.getParcelable("cat");
            if (savedInstanceState.containsKey("sortBy"))
                sortBy = savedInstanceState.getString("sortBy");
        }
        if (getArguments() != null){
            if (getArguments().containsKey("sortBy"))
                sortBy = getArguments().getString("sortBy");
            if (getArguments().containsKey("cat"))
                category = getArguments().getParcelable("cat");
        }
        return view = inflater.inflate(R.layout.fragment_products, container, false);
    }

    @Override
    public void initViews() {
        isGridView = true;
        isFilterApplied = false;
        if (adapter == null)
            adapter = new StoreProductsAdapterSidalitac(this);
        gridLayoutManager = new GridLayoutManager(activity, 2);
        linearLayoutManager = new LinearLayoutManager(activity, RecyclerView.VERTICAL,false);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(Object data) {
                ProductDetailsStore details =
                        ProductDetailsStore.
                                createInstance(((ProductDetails) data).
                                        setCategoriesId(category.getId()));
                activity.addPage(details);
            }
        });
        setRecyclerViewLayoutManager(isGridView);
        ////////////////////////

        storeViewModel = ViewModelProviders.of(this).get(StoreViewModelSidalitac.class);
        storeViewModel.getProducts().observe(this,
                productModelList -> {
            if(productModelList==null)
            {
                showReload();
                return;
            }
            if(productModelList.getProductData()==null||productModelList.getProductData().size()==0)
            {
                showEmpty();
                return;
            }
            pageNumber=pageNumber+1;
            hideReload();
            adapter.addItems(productModelList);
        }
        );
        search_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                activity.addPage(SearchFragment.createInstance(v.getText().toString()));
                return true;
            }
        });
        storeViewModel.getProgress().observe(this, s -> {
            if (!TextUtils.isEmpty(s))
                activity.showProgressBar(s);
            else activity.hideDialog();
        });
        storeViewModel.getMessage().observe(this, s -> activity.showMessageDialog(s));
        reload();
        String[] s = new String[1];
        s[0] = category.getImage();

        filterButton.setChecked(isFilterApplied);
        toggleLayoutView.setChecked(isGridView);

        // Toggle RecyclerView's LayoutManager
        toggleLayoutView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isGridView = isChecked;
                setRecyclerViewLayoutManager(isGridView);
            }
        });

        // Handle the Click event of Filter Button
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isFilterApplied) {
                    filterButton.setChecked(true);
                    filterDialog.show();

                } else {
                    filterButton.setChecked(false);
                    filterDialog = new FilterDialog(getContext(), category.getIdInt(), filtersList, maxPrice) {
                        @Override
                        public void clearFilters() {
                            isFilterApplied = false;
                            filterButton.setChecked(false);
                            filters = null;
                            adapter.clear();
                            pageNumber=1;
                            reload();
                        }

                        @Override
                        public void applyFilters(PostFilterData postFilterData) {
                            isFilterApplied = true;
                            filterButton.setChecked(true);
                            filters = postFilterData;
                            adapter.clear();
                            pageNumber=1;
                            reload();
                        }
                    };
                    filterDialog.show();
                }
            }
        });

        initSlider(activity, sliderShow, APIClient.BASE_URL, s);
        recyclerView.addOnScrollListener(endlessRecyclerViewScroll=
                new EndlessRecyclerViewScroll(null/*bottomBar*/) {
            @Override
            public void onLoadMore(int current_page) {
                reload();
            }
        });
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public void AddItemToCart(Object item) {
        ProductDetails product = (ProductDetails) item;
        CartProduct cartProduct = new CartProduct();

        double productBasePrice, productFinalPrice, attributesPrice = 0;
        List<CartProductAttributes> selectedAttributesList = new ArrayList<>();


        // Check Discount on Product with the help of static method of Helper class
        final String discount = Utils.checkDiscount(product.getProductsPrice(), product.getDiscountPrice());

        // Get Product's Price based on Discount
        if (discount != null) {
            product.setIsSaleProduct("1");
            productBasePrice = Double.parseDouble(product.getDiscountPrice());
        } else {
            product.setIsSaleProduct("0");
            productBasePrice = Double.parseDouble(product.getProductsPrice());
        }


        // Get Default Attributes from AttributesList
        for (int i = 0; i < product.getAttributes().size(); i++) {

            CartProductAttributes productAttribute = new CartProductAttributes();

            // Get Name and First Value of current Attribute
            Option option = product.getAttributes().get(i).getOption();
            Value value = product.getAttributes().get(i).getValues().get(0);


            // Add the Attribute's Value Price to the attributePrices
            String attrPrice = value.getPricePrefix() + value.getPrice();
            attributesPrice += Double.parseDouble(attrPrice);


            // Add Value to new List
            List<Value> valuesList = new ArrayList<>();
            valuesList.add(value);


            // Set the Name and Value of Attribute
            productAttribute.setOption(option);
            productAttribute.setValues(valuesList);


            // Add current Attribute to selectedAttributesList
            selectedAttributesList.add(i, productAttribute);
        }


        // Add Attributes Price to Product's Final Price
        productFinalPrice = productBasePrice + attributesPrice;


        // Set Product's Price and Quantity
        product.setCustomersBasketQuantity(1);
        product.setProductsPrice(String.valueOf(productBasePrice));
        product.setAttributesPrice(String.valueOf(attributesPrice));
        product.setProductsFinalPrice(String.valueOf(productFinalPrice));
        product.setTotalPrice(String.valueOf(productFinalPrice));

        // Set Customer's Basket Product and selected Attributes Info
        cartProduct.setCustomersBasketProduct(product);
        cartProduct.setCustomersBasketProductAttributes(selectedAttributesList);
        manager.addCartItem(cartProduct);
        // Recreate the OptionsMenu
        activity.invalidateOptionsMenu();
    }


    @OnClick(R.id.error_layout)
    public void reload() {
        int cat=0;
        if(category!=null) {
            cat = Integer.parseInt(category.getId());
        }
        else
            category = CategoryDetails.instance(""+cat);
        if(filtersList == null)
            RequestFilters(cat);
        if (TextUtils.isEmpty(sortBy))
            storeViewModel.getProducts(cat,pageNumber,filters );
        else
            storeViewModel.getProducts(cat ,pageNumber,sortBy,filters);
        hideReload();
    }
    @OnClick(R.id.empty_layout)
    public void empty_layout() {
        activity.onBackPressed();
    }

    public void showReload(){
        view.findViewById(R.id.error_layout).setVisibility(View.VISIBLE);
        view.findViewById(R.id.empty_layout).setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
    }
    public void showEmpty(){
        view.findViewById(R.id.empty_layout).setVisibility(View.VISIBLE);
        view.findViewById(R.id.error_layout).setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
    }
    public void hideReload(){
        view.findViewById(R.id.empty_layout).setVisibility(View.GONE);
        view.findViewById(R.id.error_layout).setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }
    //*********** Switch RecyclerView's LayoutManager ********//

    public void setRecyclerViewLayoutManager(Boolean isGridView) {
        int scrollPosition = 0;

        // If a LayoutManager has already been set, get current Scroll Position
        if (recyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        }

        adapter.toggleLayout(isGridView);

        recyclerView.setLayoutManager(isGridView ? gridLayoutManager : linearLayoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.scrollToPosition(scrollPosition);
    }

    private void RequestFilters(int categories_id) {

        Call<FilterData> call = APIClient.getInstance()
                .getFilters
                        (
                                categories_id,
                                Constants.LANGUAGE_ID
                        );

        call.enqueue(new Callback<FilterData>() {
            @Override
            public void onResponse(Call<FilterData> call, retrofit2.Response<FilterData> response) {

                try {
                    if(response.body()!=null)
                    {
                        filtersList = response.body().getFilters();
                        maxPrice = Double.parseDouble(response.body().getMaxPrice());
                    }
                }catch (Exception ignored){}
//                if (response.isSuccessful()) {
//                    if (response.body().getSuccess().equalsIgnoreCase("1")) {
//
//                        filtersList = response.body().getFilters();
//                        maxPrice = Double.parseDouble(response.body().getMaxPrice());
//
//                    }
//                    else if (response.body().getSuccess().equalsIgnoreCase("0")) {
//                        if (isVisible&&view!=null)
//                            Snackbar.make(view, response.body().getMessage(), Snackbar.LENGTH_SHORT).show();
//
//                    }
//                    else {
//                        if (isVisible)
//                            Snackbar.make(view, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
//                    }
//                }
//                else {
//                    if (isVisible)
//                        Toast.makeText(getContext(), ""+response.message(), Toast.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void onFailure(Call<FilterData> call, Throwable t) {
                if (isVisible)
                    Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onItemAdded(Object item) {
        activity.invalidateOptionsMenu();
    }
}
