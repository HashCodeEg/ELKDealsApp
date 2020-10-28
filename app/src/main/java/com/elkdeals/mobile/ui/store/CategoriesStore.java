package com.elkdeals.mobile.ui.store;

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

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.Utils;
import com.elkdeals.mobile.adapters.CategoryListAdapterSidalitac;
import com.elkdeals.mobile.api.models.banner_model.BannerData;
import com.elkdeals.mobile.api.models.banner_model.BannerDetails;
import com.elkdeals.mobile.api.models.category_model.CategoryData;
import com.elkdeals.mobile.api.models.category_model.CategoryDetails;
import com.elkdeals.mobile.api.network.APIClient;
import com.elkdeals.mobile.ui.BaseFragment;
import com.elkdeals.mobile.interfaces.OnItemClickListener;
import com.elkdeals.mobile.viewmodel.StoreViewModelSidalitac;
import com.islam.slider.SliderLayout;
import com.islam.slider.SliderTypes.BaseSliderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.elkdeals.mobile.Utils.Utils.initSlider;


public class CategoriesStore extends BaseFragment implements TextView.OnEditorActionListener, BaseSliderView.OnSliderClickListener {
    public static final String TAG = "store categories";
    @BindView(R.id.recycler)
    RecyclerView recycler;
    CategoryListAdapterSidalitac adapter;
    StoreViewModelSidalitac storeViewModel;
    @BindView(R.id.slider)
    SliderLayout sliderShow;
    private GridLayoutManager manager;
    private CategoryDetails parentCategoryDetails;

    @BindView(R.id.search_text)
    EditText searchView;
    private List<BannerDetails> bannerImages;

    public CategoriesStore() {
        // Required empty public constructor
    }


    private static BaseFragment createInstance(CategoryDetails parentCategoryDetails) {
        CategoriesStore fragment=new CategoriesStore();
        Bundle data = new Bundle();
        data.putParcelable("cat",parentCategoryDetails);
        fragment.setArguments(data);
        fragment.parentCategoryDetails = parentCategoryDetails;
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle data = getArguments();
        if (savedInstanceState != null)
            data = savedInstanceState;
        if (data!=null){
            if (data.containsKey("cat"))
                parentCategoryDetails = data.getParcelable("cat");

        }
        // Inflate the layout for this fragment
        return view = inflater.inflate(R.layout.fragment_categories_store, container, false);
    }

    @Override
    public void initViews() {
        //adapter.addItems(Category.getDummyStore());
        manager = new GridLayoutManager(activity, 2);
        recycler.setLayoutManager(manager);
        storeViewModel = ViewModelProviders.of(this).get(StoreViewModelSidalitac.class);
        ///////////////////////////////////////////////////////

        ///////////////////////////////////////////////////////////
        storeViewModel.getCategoriesMutuable().observe(this,
                categoriesModels -> {
                    if(categoriesModels==null){
                        showReload();
                        return;
                    }
                    else if(categoriesModels.getData()==null||categoriesModels.getData().size()==0){
                        showEmpty();
                        return;
                    }
            hideReload();
            adapter = new CategoryListAdapterSidalitac(activity,getCategories(categoriesModels.getData()), false);
                    adapter.setOnCategoryClickListener(new OnItemClickListener() {
                        @Override
                        public void OnItemClick(Object data) {
                            super.OnItemClick((data));

                            if(parentCategoryDetails!=null)
                                activity.addPage(ProductsStore.createInstances((CategoryDetails)(data)));
                            else activity.addPage(CategoriesStore.createInstance((CategoryDetails)(data)));
                        }
                    });
                    recycler.setAdapter(adapter);
                    recycler.setLayoutManager(manager);
                });
        storeViewModel.getHomeModel().observe(this,
                storeHomeResponse ->
                        initSlider(CategoriesStore.this,activity,
                                sliderShow, APIClient.BASE_URL, getSliderUrls(storeHomeResponse)));
        storeViewModel.getProgress().observe(this, s -> {
            if (!TextUtils.isEmpty(s))
                activity.showProgressBar(s);
            else activity.hideDialog();
        });
        storeViewModel.getMessage().observe(this, s -> activity.showMessageDialog(s));
        storeViewModel.getCategories();
        storeViewModel.getBanners();
        searchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                activity.addPage(SearchFragment.createInstance(v.getText().toString()));
                if(adapter==null)return true;
                adapter.getFilter().filter(v.getText());
                adapter.notifyDataSetChanged();
                return true;
            }
        });
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(adapter==null)return;
                adapter.getFilter().filter(s.toString());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    public ArrayList<CategoryDetails> getCategories(List<CategoryDetails> data) {
        ArrayList<CategoryDetails>categoryDetails=new ArrayList<>();
        String parentCategory="0";
        if(parentCategoryDetails!=null)
            parentCategory=parentCategoryDetails.getId();
        for(int i=0;i< data.size();i++){
            if(data.get(i).getParentId().equalsIgnoreCase(parentCategory))
                categoryDetails.add(data.get(i));
        }
        if(categoryDetails.size()==0){
            showEmpty();
        }
        return categoryDetails;
    }
    private String[] getSliderUrls(BannerData bannerData) {
        if (bannerData == null || bannerData.getData() == null || bannerData.getData().size() == 0)
            return null;
        bannerImages=bannerData.getData();
        String[] items = new String[bannerData.getData().size()];
        for (int i = 0; i < bannerData.getData().size(); i++)
            items[i] = bannerData.getData().get(i).getImage();
        return items;
    }


    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            Utils.hideSoftKeyboard(v);
            return true;
        }
        return false;
    }
    @OnClick(R.id.error_layout)
    public void reload() {
        initViews();
    }
    @OnClick(R.id.empty_layout)
    public void empty_layout() {
        activity.onBackPressed();
    }


    public void showReload(){
        view.findViewById(R.id.error_layout).setVisibility(View.VISIBLE);
        view.findViewById(R.id.empty_layout).setVisibility(View.GONE);
        recycler.setVisibility(View.GONE);
    }
    public void showEmpty(){
        view.findViewById(R.id.empty_layout).setVisibility(View.VISIBLE);
        view.findViewById(R.id.error_layout).setVisibility(View.GONE);
        recycler.setVisibility(View.GONE);
    }
    public void hideReload(){
        view.findViewById(R.id.empty_layout).setVisibility(View.GONE);
        view.findViewById(R.id.error_layout).setVisibility(View.GONE);
        recycler.setVisibility(View.VISIBLE);
    }

    public void onSliderClick(BaseSliderView slider) {

        if(bannerImages == null)
            return;
        int position = sliderShow.getCurrentPosition();
        String url = bannerImages.get(position).getUrl();
        String type = bannerImages.get(position).getType();

        if (type.equalsIgnoreCase("product")) {
            if (!url.isEmpty()) {
                // Get Product Info
                Bundle bundle = new Bundle();
                bundle.putInt("itemID", Integer.parseInt(url));
                ProductDetailsStore details = ProductDetailsStore.createInstance(Integer.parseInt(url));
                activity.addPage(details);
            }

        }
        else if (type.equalsIgnoreCase("category")) {
            List<CategoryDetails> allCategoriesList = CategoryData.read();
            if (allCategoriesList == null)
                return;
            if (!url.isEmpty()) {
                int categoryID = 0;
                String categoryName = "";

                for (int i=0;  i< allCategoriesList.size();  i++) {
                    if (url.equalsIgnoreCase(allCategoriesList.get(i).getId())) {
                        activity.addPage(ProductsStore.createInstances(allCategoriesList.get(i)));
                        return;
                    }
                }

            }

        }
        else if (type.equalsIgnoreCase("deals")) {
            Bundle bundle = new Bundle();
            bundle.putString("sortBy", "special");


            // Navigate to Products Fragment
            ProductsStore fragment = new ProductsStore();
            fragment.setArguments(bundle);
            activity.addPage(fragment);

        }
        else if (type.equalsIgnoreCase("top seller")) {
            Bundle bundle = new Bundle();
            bundle.putString("sortBy", "top seller");


            // Navigate to Products Fragment
            ProductsStore fragment = new ProductsStore();
            fragment.setArguments(bundle);
            activity.addPage(fragment);

        }
        else if (type.equalsIgnoreCase("most liked")) {
            Bundle bundle = new Bundle();
            bundle.putString("sortBy", "most liked");

            // Navigate to Products Fragment
            ProductsStore fragment = new ProductsStore();
            fragment.setArguments(bundle);
            activity.addPage(fragment);

        }

    }

}
