package com.elkdeals.mobile.ui.store;


import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.StoreCartManager;
import com.elkdeals.mobile.Utils.Utils;
import com.elkdeals.mobile.adapters.SpinnerAdapter;
import com.elkdeals.mobile.api.models.cart_model.CartProduct;
import com.elkdeals.mobile.api.models.cart_model.CartProductAttributes;
import com.elkdeals.mobile.api.models.product_model.Attribute;
import com.elkdeals.mobile.api.models.product_model.Option;
import com.elkdeals.mobile.api.models.product_model.ProductData;
import com.elkdeals.mobile.api.models.product_model.ProductDetails;
import com.elkdeals.mobile.api.models.product_model.Value;
import com.elkdeals.mobile.api.network.APIClient;
import com.elkdeals.mobile.custom.FlowLayout;
import com.elkdeals.mobile.custom.JustifiedTextView;
import com.elkdeals.mobile.model.RepositorySidalitac;
import com.elkdeals.mobile.ui.BaseFragment;
import com.elkdeals.mobile.viewmodel.StoreViewModelSidalitac;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.islam.slider.SliderLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.OnClick;

import static com.elkdeals.mobile.Utils.Utils.initSlider;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailsStore extends BaseFragment {
    @BindView(R.id.options)
    FlowLayout options;
    @BindView(R.id.slider)
    SliderLayout sliderShow;
    public static final String TAG = "Product Details";
    @Nullable
    private ProductDetails productDetails;
    @BindView(R.id.title)
    public TextView title;
    @BindView(R.id.description)
    public JustifiedTextView description;
    @BindView(R.id.brand)
    public TextView brand;
    @BindView(R.id.seller)
    public TextView seller;
    @BindView(R.id.availability)
    public TextView availability;
    @BindView(R.id.price)
    public TextView price;
    @BindView(R.id.categories_details)
    public TextView categories_details;
    @BindView(R.id.discount_frame)
    public View container;
    @BindView(R.id.discount)
    public TextView discountText;
    @BindView(R.id.price_old)
    public TextView price_old;

    @BindView(R.id.search_text)
    EditText search_text;
    private StoreViewModelSidalitac storeViewModel;
    private double productBasePrice;
    private double attributesPrice=0.0;
    private double productFinalPrice;
    ArrayList<SpinnerAdapter>adapters=new ArrayList<>();
    ArrayList<Spinner>spinners=new ArrayList<>();
    private int productId;

    public ProductDetailsStore() {
        // Required empty public constructor
    }

    public static ProductDetailsStore createInstance(ProductDetails productDetails) {
        Log.e(TAG,new Gson().toJson(productDetails));
        ProductDetailsStore productDetailsSidalitac = new ProductDetailsStore();
        productDetailsSidalitac.productDetails = productDetails;
        productDetailsSidalitac.productId = productDetails.getProductsId();
        return productDetailsSidalitac;
    }

    public static ProductDetailsStore createInstance(int productId) {
        ProductDetailsStore productDetailsSidalitac = new ProductDetailsStore();
        productDetailsSidalitac.productId = productId;
        return productDetailsSidalitac;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("itemID",productId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(savedInstanceState!=null)
            if(savedInstanceState.containsKey("itemID"))
            productId = savedInstanceState.getInt("itemID");
        // Inflate the layout for this fragment
        return view = inflater.inflate(R.layout.fragment_product_details, container, false);
    }

    @Override
    public void initViews() {
        storeViewModel = ViewModelProviders.of(this).get(StoreViewModelSidalitac.class);
        storeViewModel.getProgress().observe(this, s -> {
            if (!TextUtils.isEmpty(s))
                activity.showProgressBar(s);
            else activity.hideDialog();
        });

        search_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                activity.addPage(SearchFragment.createInstance(v.getText().toString()));
                return true;
            }
        });
        storeViewModel.getMessage().observe(this, s -> activity.showMessageDialog(s));
        storeViewModel.getProducts().observe(this, new Observer<ProductData>() {
            @Override
            public void onChanged(ProductData productData) {
                if(productData!=null){
                    if ("1".equalsIgnoreCase(productData.getSuccess()))
                    {
                        if (productData.getProductData()!=null&&productData.getProductData().size()>0)
                            showProduct(productData.getProductData().get(0));
                    }else activity.showMessageDialog(productData.getMessage());
                }
                else{
                    activity.showMessageDialog("خطأ فى الاتصال", "فشل تحمبل بيانات المنتج.اعادة المحاولة؟",
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    storeViewModel.getProductDetails(productId, RepositorySidalitac.getUserId());                                }
                            }, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    activity.onBackPressed();
                                }
                            });
                }
            }
        });
        if(productDetails!=null)
            showProduct(productDetails);
        if(productId==0&&productDetails==null)
            getActivity().onBackPressed();
        else storeViewModel.getProductDetails(productId, RepositorySidalitac.getUserId());
    }

    public void showProduct(@NonNull ProductDetails productDetails){
        this.productDetails=productDetails;
        description.setText(Html.fromHtml(productDetails.getProductsDescription()));
        brand.setText(productDetails.getManufacturersName());
        if (TextUtils.isEmpty(brand.getText()))
            brand.setVisibility(View.GONE);
        categories_details.setText(productDetails.getCategoriesNameWithPatent(categories_details.getText().toString()));
        seller.setText(productDetails.getManufacturersName());
        title.setText(productDetails.getProductsName());
        availability.setText(productDetails.getDefaultStock()+"");
        // Check Discount on Product with the help of static method of Helper class
        String discount = Utils.checkDiscount(productDetails.getProductsPrice(), productDetails.getDiscountPrice());

        productBasePrice = Double.parseDouble(productDetails.getProductsPrice());
        if (discount != null) {
            productDetails.setIsSaleProduct("1");
            price.setText(productDetails.getDiscountPrice());
            container.setVisibility(View.VISIBLE);
            discountText.setText(discount);
            price_old.setVisibility(View.VISIBLE);
            price_old.setText(productBasePrice+"");

        }
        else {
            productDetails.setIsSaleProduct("0");
            price.setText(productBasePrice+"");
            price_old.setVisibility(View.GONE);
            container.setVisibility(View.GONE);
        }
        String[] sliders = getSliderUrls(productDetails);
        if(sliders!=null)
            initSlider(activity,sliderShow, APIClient.BASE_URL,sliders);
        else {
            String[] s=new String[1];
            s[0]=productDetails.getProductsImage();
            initSlider(activity,sliderShow, APIClient.BASE_URL,s);
        }

        if (productDetails.getAttributes()!=null)
            for(int i=0;i<productDetails.getAttributes().size();i++){
                Attribute attribute=productDetails.getAttributes().get(i);
                if(attribute==null)
                    continue;

                SpinnerAdapter adapter=new SpinnerAdapter(activity, R.layout.spinner_item, attribute.getValues());
                Spinner spinner=new Spinner(activity);
                adapters.add(adapter);
                spinners.add(spinner);
                spinner.setAdapter(adapter);
                spinner.setPopupBackgroundResource(R.drawable.none);
                spinner.setBackgroundResource(R.drawable.none);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Value value= (Value) adapter.getItem(position);
                        if(value==null)return;
                        if(value.getPricePrefix().equals("+"))
                            attributesPrice=attributesPrice+Double.valueOf(value.getPrice());
                        else attributesPrice=attributesPrice-Double.valueOf(value.getPrice());
                        updateProductPrice();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                options.addView(spinner);
            }
    }
    private String[] getSliderUrls(ProductDetails productData) {

        if (productData == null || productData.getImages() == null || productData.getImages().size() == 0)
            return null;
        String[] items = new String[productData.getImages().size()];
        for (int i = 0; i < productData.getImages().size();i++) {
            items[i] = productData.getImages().get(i).getImage();
        }
        return items;
    }
    //*********** Update Product's final Price based on selected Attributes ********//

    public void updateProductPrice() {
        // Calculate and Set Product's total Price
        productFinalPrice = productBasePrice + attributesPrice;
        price.setText(Utils.getStringRes(R.string.egp)+ new DecimalFormat("#0.00").format(productFinalPrice));

    }
    @OnClick(R.id.add_to_cart)
    public void addToCart() {
        if(productDetails == null)
            return;
        //this.AddItemToCart(storeProductModel);
        if (productDetails.getDefaultStock() > 0) {

            CartProduct cartProduct = new CartProduct();
            // Set Product's Price, Quantity and selected Attributes Info
            productDetails.setCustomersBasketQuantity(1);
            productDetails.setProductsPrice(String.valueOf(productBasePrice));
            productDetails.setAttributesPrice(String.valueOf(attributesPrice));
            productDetails.setProductsFinalPrice(price.getText().toString());
            productDetails.setTotalPrice(String.valueOf(productDetails.getProductsFinalPrice()));
            cartProduct.setCustomersBasketProduct(productDetails);
            List<Attribute> attributesList = productDetails.getAttributes();

            List<CartProductAttributes> selectedAttributesList=new ArrayList<>();
            if (attributesList.size() > 0) {
                for (int i = 0; i < attributesList.size(); i++) {

                    CartProductAttributes productAttribute = new CartProductAttributes();

                    // Get Name and First Value of current Attribute
                    Option option = attributesList.get(i).getOption();
                    Value value = attributesList.get(i).getValues().get(0);

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
                cartProduct.setCustomersBasketProductAttributes(selectedAttributesList);

            }
            StoreCartManager.getInstance().addCartItem(cartProduct);
            // Recreate the OptionsMenu
            activity.invalidateOptionsMenu();

            Snackbar.make(view, Utils.getStringRes(R.string.item_added_to_cart), Snackbar.LENGTH_SHORT).show();
        }
        else activity.showToast("هذا المنتج غير متوفر حاليا!");
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
