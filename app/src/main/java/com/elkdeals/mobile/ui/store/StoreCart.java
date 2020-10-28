package com.elkdeals.mobile.ui.store;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.StoreCartManager;
import com.elkdeals.mobile.adapters.CartsAdapter;
import com.elkdeals.mobile.api.models.UserModel;
import com.elkdeals.mobile.api.models.cart_model.CartProduct;
import com.elkdeals.mobile.api.models.cart_model.CartProductAttributes;
import com.elkdeals.mobile.api.models.order_model.PostOrder;
import com.elkdeals.mobile.api.models.order_model.PostProducts;
import com.elkdeals.mobile.api.models.order_model.PostProductsAttributes;
import com.elkdeals.mobile.api.models.payment_model.PaymentMethodsInfo;
import com.elkdeals.mobile.api.models.product_model.Option;
import com.elkdeals.mobile.api.models.product_model.Value;
import com.elkdeals.mobile.api.models.user_model.UserData;
import com.elkdeals.mobile.interfaces.OnItemClickListener;
import com.elkdeals.mobile.model.Repository;
import com.elkdeals.mobile.model.RepositorySidalitac;
import com.elkdeals.mobile.ui.BaseFragment;
import com.elkdeals.mobile.ui.account.ShippingAddresses;
import com.elkdeals.mobile.viewmodel.StoreViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class StoreCart extends BaseFragment {


    public static final String TAG = "Cart";
    @BindView(R.id.recycler)
    public RecyclerView recyclerView;
    StoreViewModel storeViewModel;

    @BindView(R.id.sub_total_price)
    public TextView sub_total_price;
    @BindView(R.id.total_price)
    public TextView total_price;
    @BindView(R.id.shipping_fees)
    public TextView shippingfees_price;
    private CartsAdapter adapter;
    StoreCartManager manager= StoreCartManager.getInstance();
    private ArrayList<PaymentMethodsInfo> paymentMethodsList;

    public StoreCart() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return view = inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void initViews() {

        // Request Payment Methods
        //RequestPaymentMethods();
        storeViewModel = ViewModelProviders.of(this).get(StoreViewModel.class);
        storeViewModel.getProgress().observe(this, s -> {
            if (!TextUtils.isEmpty(s))
                activity.showProgressBar(s);
            else activity.hideDialog();
        });
        storeViewModel.getMessage().observe(this, s -> activity.showMessageDialog(s));
        ///////////////////////////////
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new CartsAdapter(this, new OnItemClickListener.onIncrementClickListener() {
            @Override
            public void OnItemClick(Object data) {
                super.OnItemClick(data);
                CartProduct cartProduct = (CartProduct) data;
                int quantity = cartProduct.getCustomersBasketProduct().getCustomersBasketQuantity();
// Check if the Quantity is less than the maximum or stock Quantity
                if (quantity >= cartProduct.getCustomersBasketProduct().getDefaultStock()) {
                    activity.showToast(R.string.reached_max_stock);
                    return;
                }
                // Increase Quantity by 1
                // Calculate Product Price with selected Quantity
                double price = Double.parseDouble(cartProduct.getCustomersBasketProduct().getProductsFinalPrice()) * (quantity + 1);

                // Set Final Price and Quantity
                cartProduct.getCustomersBasketProduct().setTotalPrice("" + price);
                cartProduct.getCustomersBasketProduct().setCustomersBasketQuantity(quantity + 1);

                manager.updateItem(cartProduct);
                adapter.addItems(manager.getCartItems());

            }
        }, new OnItemClickListener.onDecrementClickListener() {
            @Override
            public void OnItemClick(Object data) {
                super.OnItemClick(data);
                CartProduct cartProduct = (CartProduct) data;
                int quantity = cartProduct.getCustomersBasketProduct().getCustomersBasketQuantity();
// Check if the Quantity is less than the maximum or stock Quantity
                if (quantity < 2) {
                    activity.showToast(R.string.you_can_remove_product);
                    return;
                }
                // Increase Quantity by 1
                // Calculate Product Price with selected Quantity
                double price = Double.parseDouble(cartProduct.getCustomersBasketProduct().getProductsFinalPrice()) * (quantity - 1);

                // Set Final Price and Quantity
                cartProduct.getCustomersBasketProduct().setTotalPrice("" + price);
                cartProduct.getCustomersBasketProduct().setCustomersBasketQuantity(quantity - 1);

                manager.updateItem(cartProduct);
                adapter.addItems(manager.getCartItems());
            }
        }
                , new OnItemClickListener.onAddProductsClickListener() {
            @Override
            public void OnItemClick(Object data) {
                super.OnItemClick(data);
                activity.showStore();
            }
        }, new OnItemClickListener() {
            @Override
            public void OnItemClick(Object data) {
                super.OnItemClick(data);

                manager.remove((CartProduct)data);
                adapter.addItems(manager.getCartItems());
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume_() {
        super.onResume_();
        adapter.addItems(manager.getCartItems());
    }

    public void updateTotal(ArrayList<CartProduct> carts) {
        if(carts==null)return;
        double total=0,shippingfees=0;
        for(CartProduct cartProduct:carts){
            total= total+Double.parseDouble(cartProduct.getCustomersBasketProduct().getTotalPrice());
//            shippingfees= shippingfees+Double.parseDouble(cartProduct.getCustomersBasketProduct().());
        }
        sub_total_price.setText(String.valueOf(total));
        shippingfees_price.setText(String.valueOf(shippingfees));
        total_price.setText(String.valueOf(total+shippingfees));
        activity.invalidateOptionsMenu();
    }
    @Override
    public String getTAG() {
        return TAG;
    }
    @OnClick(R.id.checkout)
    public void checkOut(){
        proceedOrder();

        //call.enqueue(new Callback<OrderData>()
    }
    private void proceedOrder() {

        PostOrder orderDetails = new PostOrder();
        List<PostProducts> orderProductList = new ArrayList<>();

        ArrayList<CartProduct> checkoutItemsList = manager.getCartItems();
        for (int i=0;  i<checkoutItemsList.size();  i++) {

            PostProducts orderProduct = new PostProducts();

            // Get current Product Details
            orderProduct.setProductsId(checkoutItemsList.get(i).getCustomersBasketProduct().getProductsId());
            orderProduct.setProductsName(checkoutItemsList.get(i).getCustomersBasketProduct().getProductsName());
            orderProduct.setModel(checkoutItemsList.get(i).getCustomersBasketProduct().getProductsModel());
            orderProduct.setImage(checkoutItemsList.get(i).getCustomersBasketProduct().getProductsImage());
            orderProduct.setWeight(checkoutItemsList.get(i).getCustomersBasketProduct().getProductsWeight());
            orderProduct.setUnit(checkoutItemsList.get(i).getCustomersBasketProduct().getProductsWeightUnit());
            orderProduct.setManufacture(checkoutItemsList.get(i).getCustomersBasketProduct().getManufacturersName());
            orderProduct.setCategoriesId(checkoutItemsList.get(i).getCustomersBasketProduct().getCategoriesId());
            orderProduct.setCategoriesName(checkoutItemsList.get(i).getCustomersBasketProduct().getCategoriesName());
            orderProduct.setPrice(checkoutItemsList.get(i).getCustomersBasketProduct().getProductsPrice());
            orderProduct.setFinalPrice(checkoutItemsList.get(i).getCustomersBasketProduct().getProductsFinalPrice());
            orderProduct.setSubtotal(checkoutItemsList.get(i).getCustomersBasketProduct().getTotalPrice());
            orderProduct.setTotal(checkoutItemsList.get(i).getCustomersBasketProduct().getTotalPrice());
            orderProduct.setCustomersBasketQuantity(checkoutItemsList.get(i).getCustomersBasketProduct().getCustomersBasketQuantity());

            orderProduct.setOnSale(checkoutItemsList.get(i).getCustomersBasketProduct().getIsSaleProduct().equalsIgnoreCase("1"));


            List<PostProductsAttributes> productAttributes = new ArrayList<>();

            for (int j=0;  j<checkoutItemsList.get(i).getCustomersBasketProductAttributes().size();  j++) {
                CartProductAttributes cartProductAttributes = checkoutItemsList.get(i).getCustomersBasketProductAttributes().get(j);
                Option attributeOption = cartProductAttributes.getOption();
                Value attributeValue = cartProductAttributes.getValues().get(0);

                PostProductsAttributes attribute = new PostProductsAttributes();
                attribute.setProductsOptionsId(String.valueOf(attributeOption.getId()));
                attribute.setProductsOptions(attributeOption.getName());
                attribute.setProductsOptionsValuesId(String.valueOf(attributeValue.getId()));
                attribute.setProductsOptionsValues(attributeValue.getValue());
                attribute.setOptionsValuesPrice(attributeValue.getPrice());
                attribute.setPricePrefix(attributeValue.getPricePrefix());
                attribute.setAttributeName(attributeValue.getValue()+" "+attributeValue.getPricePrefix()+attributeValue.getPrice());

                productAttributes.add(attribute);
            }

            orderProduct.setAttributes(productAttributes);


            // Add current Product to orderProductList
            orderProductList.add(orderProduct);
        }


        UserData userData= RepositorySidalitac.getUserInfo();
        com.elkdeals.mobile.api.models.user_model.UserDetails userInfo=userData.getData().get(0);
        // Set Customer Info
        orderDetails.setCustomersId(Integer.parseInt(userInfo.getCustomersId()));
        orderDetails.setCustomersName(userInfo.getCustomersFirstname());
        orderDetails.setCustomersTelephone(userInfo.getCustomersTelephone());
        orderDetails.setCustomersEmailAddress(userInfo.getCustomersEmailAddress());



        // Set Checkout Price and Products
        orderDetails.setProductsTotal(Double.parseDouble(sub_total_price.getText().toString()));
        orderDetails.setTotalPrice(Double.parseDouble(total_price.getText().toString()));
        orderDetails.setProducts(orderProductList);
        UserModel elkUser = Repository.getUserInfo();


        PlaceOrderNow(orderDetails);

    }

    private void PlaceOrderNow(PostOrder orderDetails) {
        BaseFragment fragment= ShippingAddresses.createInstance(orderDetails);
        if (orderDetails.getTotalPrice() == 0)
        {
            activity.showToast(R.string.add_more_products);
            return;
        }
        activity.addPage(fragment);
    }

}
