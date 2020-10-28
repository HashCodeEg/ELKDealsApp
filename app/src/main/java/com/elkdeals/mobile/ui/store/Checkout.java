package com.elkdeals.mobile.ui.store;


import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elkdeals.mobile.App;
import com.elkdeals.mobile.R;
import com.elkdeals.mobile.StoreCartManager;
import com.elkdeals.mobile.Utils.Constants;
import com.elkdeals.mobile.Utils.Utils;
import com.elkdeals.mobile.adapters.CheckoutItemsAdapter;
import com.elkdeals.mobile.adapters.CouponsAdapter;
import com.elkdeals.mobile.api.models.address_model.AddressDetails;
import com.elkdeals.mobile.api.models.cart_model.CartProduct;
import com.elkdeals.mobile.api.models.cart_model.CartProductAttributes;
import com.elkdeals.mobile.api.models.coupons_model.CouponsData;
import com.elkdeals.mobile.api.models.coupons_model.CouponsInfo;
import com.elkdeals.mobile.api.models.order_model.OrderData;
import com.elkdeals.mobile.api.models.order_model.PostOrder;
import com.elkdeals.mobile.api.models.order_model.PostProducts;
import com.elkdeals.mobile.api.models.order_model.PostProductsAttributes;
import com.elkdeals.mobile.api.models.payment_model.PaymentMethodsInfo;
import com.elkdeals.mobile.api.models.product_model.Option;
import com.elkdeals.mobile.api.models.product_model.Value;
import com.elkdeals.mobile.api.models.shipping_model.ShippingService;
import com.elkdeals.mobile.api.models.user_model.UserDetails;
import com.elkdeals.mobile.api.network.APIClient;
import com.elkdeals.mobile.database.User_Cart_DB;
import com.elkdeals.mobile.model.RepositorySidalitac;
import com.elkdeals.mobile.ui.BaseFragment;
import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;


public class Checkout extends BaseFragment {

    public static final String TAG="Checkout";
    View rootView;
    boolean disableOtherCoupons = false;
    String tax;
    String selectedPaymentMethod="cod";
    String paymentNonceToken = "";
    double checkoutSubtotal, checkoutTax, checkoutShipping, checkoutShippingCost, checkoutDiscount, checkoutTotal = 0;
    CardView card_details_layout;
    NestedScrollView scroll_container;
    RecyclerView checkout_items_recycler;
    RecyclerView checkout_coupons_recycler;
    Button checkout_coupon_btn, checkout_order_btn, checkout_cancel_btn;
    ImageButton  edit_shipping_Btn, edit_shipping_method_Btn;
    EditText checkout_coupon_code, checkout_comments;
    TextView checkout_subtotal, checkout_tax, checkout_shipping, checkout_discount, checkout_total, demo_coupons_text;
    TextView  shipping_name, shipping_street, shipping_address, shipping_method;
    List<CouponsInfo> couponsList;
    List<CartProduct> checkoutItemsList;
    List<PaymentMethodsInfo> paymentMethodsList;
    UserDetails userInfo;
    AddressDetails billingAddress;
    AddressDetails shippingAddress;
    CouponsAdapter couponsAdapter;
    ShippingService shippingMethod;
    CheckoutItemsAdapter checkoutItemsAdapter;
    User_Cart_DB user_cart_db = new User_Cart_DB();

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.checkout, container, false);

        // Set the Title of Toolbar
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.checkout));

        // Get selectedShippingMethod, billingAddress and shippingAddress from ApplicationContext
        tax = App.getInstance().getTax();// ((App) getContext().getApplicationContext()).getTax();
        shippingMethod = App.getInstance().getShippingService();// ((App) getContext().getApplicationContext()).getShippingService();
        billingAddress = App.getInstance().getAddress();
        shippingAddress = App.getInstance().getAddress();

        // Get userInfo from Local Databases User_Info_DB
        userInfo = RepositorySidalitac.getUserInfo().getData().get(0);// user_info_db.getUserData(getActivity().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", null));


        // Binding Layout Views
        checkout_order_btn = rootView.findViewById(R.id.checkout_order_btn);
        checkout_cancel_btn = rootView.findViewById(R.id.checkout_cancel_btn);
        checkout_coupon_btn = rootView.findViewById(R.id.checkout_coupon_btn);
        edit_shipping_Btn = rootView.findViewById(R.id.checkout_edit_shipping);
        edit_shipping_method_Btn = rootView.findViewById(R.id.checkout_edit_shipping_method);
        shipping_method = rootView.findViewById(R.id.shipping_method);
        checkout_subtotal = rootView.findViewById(R.id.checkout_subtotal);
        checkout_tax = rootView.findViewById(R.id.checkout_tax);
        checkout_shipping = rootView.findViewById(R.id.checkout_shipping);
        checkout_discount = rootView.findViewById(R.id.checkout_discount);
        checkout_total = rootView.findViewById(R.id.checkout_total);
        shipping_name = rootView.findViewById(R.id.shipping_name);
        shipping_street = rootView.findViewById(R.id.shipping_street);
        shipping_address = rootView.findViewById(R.id.shipping_address);
        demo_coupons_text = rootView.findViewById(R.id.demo_coupons_text);
        checkout_coupon_code = rootView.findViewById(R.id.checkout_coupon_code);
        checkout_comments = rootView.findViewById(R.id.checkout_comments);
        checkout_items_recycler = rootView.findViewById(R.id.checkout_items_recycler);
        checkout_coupons_recycler = rootView.findViewById(R.id.checkout_coupons_recycler);

        card_details_layout = rootView.findViewById(R.id.card_details_layout);
        scroll_container = rootView.findViewById(R.id.scroll_container);
        card_details_layout.setVisibility(View.GONE);


        checkout_items_recycler.setNestedScrollingEnabled(false);
        checkout_coupons_recycler.setNestedScrollingEnabled(false);
    
        couponsList = new ArrayList<>();
        checkoutItemsList = new ArrayList<>();
        paymentMethodsList = new ArrayList<>();
        
        // Get checkoutItems from Local Databases User_Cart_DB
        checkoutItemsList = user_cart_db.getCartItems();
        // Initialize the CheckoutItemsAdapter for RecyclerView
        checkoutItemsAdapter = new CheckoutItemsAdapter(getContext(), checkoutItemsList);

        // Set the Adapter, LayoutManager and ItemDecoration to the RecyclerView
        checkout_items_recycler.setAdapter(checkoutItemsAdapter);
        checkout_items_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        checkout_items_recycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
    
    
    
        // Initialize the CouponsAdapter for RecyclerView
        couponsAdapter = new CouponsAdapter(getContext(), couponsList, true);
    
        // Set the Adapter, LayoutManager and ItemDecoration to the RecyclerView
        checkout_coupons_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        checkout_coupons_recycler.setAdapter(couponsAdapter);

        couponsAdapter.notifyDataSetChanged();



        checkoutTax = Double.parseDouble(tax);
        shipping_method.setText(shippingMethod.getName());
        checkoutShipping = checkoutShippingCost = Double.parseDouble(shippingMethod.getRate());
    
        // Set Billing Details
        shipping_name.setText(shippingAddress.getFirstname()+" "+shippingAddress.getLastname());
        shipping_address.setText(shippingAddress.getZoneName()+", "+shippingAddress.getCountryName());
        shipping_street.setText(shippingAddress.getStreet());

        // Set Checkout Total
        setCheckoutTotal();


        // Initialize ProgressDialog
         demo_coupons_text.setVisibility(View.GONE);

        // Handle the Click event of checkout_coupon_btn Button
        checkout_coupon_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(checkout_coupon_code.getText().toString())) {
                    GetCouponInfo(checkout_coupon_code.getText().toString());
                    activity.showProgressBar();//dialogLoader.showProgressDialog();
                }
            }
        });


        // Handle the Click event of checkout_cancel_btn Button
        checkout_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Cancel the Order and Navigate back to My_Cart Fragment
                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    getFragmentManager().popBackStack(getString(R.string.actionCart), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
            }
        });


        // Handle the Click event of checkout_order_btn Button
        checkout_order_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                activity.showProgressBar(getString(R.string.please_wait));
                proceedOrder();
            }
        });


        return view=rootView;
    }
    //*********** Returns Final Price of User's Cart ********//

    private double getProductsSubTotal() {

        double finalPrice = 0;

        for (int i=0;  i<checkoutItemsList.size();  i++) {
            // Add the Price of each Cart Product to finalPrice
            finalPrice += Double.parseDouble(checkoutItemsList.get(i).getCustomersBasketProduct().getTotalPrice());
        }

        return finalPrice;
    }



    //*********** Set Checkout's Subtotal, Tax, ShippingCost, Discount and Total Prices ********//

    private void setCheckoutTotal() {

        // Get Cart Total
        checkoutSubtotal = getProductsSubTotal();
        // Calculate Checkout Total
        checkoutTotal = checkoutSubtotal + checkoutTax + checkoutShipping - checkoutDiscount;
    
        // Set Checkout Details
        checkout_tax.setText(Constants.CURRENCY_SYMBOL + new DecimalFormat("#0.00", DecimalFormatSymbols.getInstance(Locale.ENGLISH)).format(checkoutTax));
        checkout_shipping.setText(Constants.CURRENCY_SYMBOL + new DecimalFormat("#0.00", DecimalFormatSymbols.getInstance(Locale.ENGLISH)).format(checkoutShipping));
        checkout_discount.setText(Constants.CURRENCY_SYMBOL + new DecimalFormat("#0.00", DecimalFormatSymbols.getInstance(Locale.ENGLISH)).format(checkoutDiscount));
        
        checkout_subtotal.setText(Constants.CURRENCY_SYMBOL + new DecimalFormat("#0.00", DecimalFormatSymbols.getInstance(Locale.ENGLISH)).format(checkoutSubtotal));
        checkout_total.setText(Constants.CURRENCY_SYMBOL + new DecimalFormat("#0.00", DecimalFormatSymbols.getInstance(Locale.ENGLISH)).format(checkoutTotal));

    }



    //*********** Set Order Details to proceed Checkout ********//

    private void proceedOrder() {

        PostOrder orderDetails = new PostOrder();
        List<PostProducts> orderProductList = new ArrayList<>();
    
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
    
    
        // Set Customer Info
        orderDetails.setCustomersId(Integer.parseInt(userInfo.getCustomersId()));
        orderDetails.setCustomersName(userInfo.getCustomersFirstname());
        orderDetails.setCustomersTelephone(userInfo.getCustomersTelephone());
        orderDetails.setCustomersEmailAddress(userInfo.getCustomersEmailAddress());
    
        // Set Shipping  Info
        orderDetails.setDeliveryFirstname(shippingAddress.getFirstname());
        orderDetails.setDeliveryLastname(shippingAddress.getLastname());
        orderDetails.setDeliveryStreetAddress(shippingAddress.getStreet());
        orderDetails.setDeliveryPostcode(shippingAddress.getPostcode());
        orderDetails.setDeliverySuburb(shippingAddress.getSuburb());
        orderDetails.setDeliveryCity(shippingAddress.getCity());
        orderDetails.setDeliveryZone(shippingAddress.getZoneName());
        orderDetails.setDeliveryState(shippingAddress.getZoneName());
        orderDetails.setDeliverySuburb(shippingAddress.getZoneName());
        orderDetails.setDeliveryCountry(shippingAddress.getCountryName());
        orderDetails.setDeliveryZoneId(String.valueOf(shippingAddress.getZoneId()));
        orderDetails.setDeliveryCountryId(String.valueOf(shippingAddress.getCountriesId()));
        orderDetails.setDeliverPhone(String.valueOf(shippingAddress.getPhone()));

        // Set Billing Info
        orderDetails.setBillingFirstname(billingAddress.getFirstname());
        orderDetails.setBillingLastname(billingAddress.getLastname());
        orderDetails.setBillingStreetAddress(billingAddress.getStreet());
        orderDetails.setBillingPostcode(billingAddress.getPostcode());
        orderDetails.setBillingSuburb(billingAddress.getSuburb());
        orderDetails.setBillingCity(billingAddress.getCity());
        orderDetails.setBillingZone(billingAddress.getZoneName());
        orderDetails.setBillingState(billingAddress.getZoneName());
        orderDetails.setBillingSuburb(billingAddress.getZoneName());
        orderDetails.setBillingCountry(billingAddress.getCountryName());
        orderDetails.setBillingZoneId(String.valueOf(billingAddress.getZoneId()));
        orderDetails.setBillingCountryId(String.valueOf(billingAddress.getCountriesId()));
        orderDetails.setBilling_phone(String.valueOf(billingAddress.getPhone()));

        orderDetails.setLanguage_id(Constants.LANGUAGE_ID);
        
        orderDetails.setTaxZoneId(shippingAddress.getZoneId());
        orderDetails.setTotalTax(checkoutTax);
        orderDetails.setShippingCost(checkoutShipping);
        orderDetails.setShippingMethod(shippingMethod.getName());
    
        orderDetails.setComments(checkout_comments.getText().toString().trim());
    
        if (couponsList.size() > 0) {
            orderDetails.setIsCouponApplied(1);
        } else {
            orderDetails.setIsCouponApplied(0);
        }
        orderDetails.setCouponAmount(checkoutDiscount);
        orderDetails.setCoupons(couponsList);
    
        // Set PaymentNonceToken and PaymentMethod
        orderDetails.setNonce(paymentNonceToken);
        orderDetails.setPaymentMethod(selectedPaymentMethod);
    
        // Set Checkout Price and Products
        orderDetails.setProductsTotal(checkoutSubtotal);
        orderDetails.setTotalPrice(checkoutTotal);
        orderDetails.setProducts(orderProductList);
        
        
        PlaceOrderNow(orderDetails);
        
    }

    
    //*********** Request the Server to Generate BrainTreeToken ********//

    private void GetCouponInfo(String coupon_code) {

        Call<CouponsData> call = APIClient.getInstance()
                .getCouponInfo
                        (
                                coupon_code
                        );


        call.enqueue(new Callback<CouponsData>() {
            @Override
            public void onResponse(Call<CouponsData> call, retrofit2.Response<CouponsData> response) {
                activity.hideDialog();

                // Check if the Response is successful
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {

                        final CouponsInfo couponsInfo = response.body().getData().get(0);
                        
                        if (couponsList.size() !=0 && couponsInfo.getIndividualUse().equalsIgnoreCase("1")) {
                            
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                            
                            dialog.setTitle(getString(R.string.add_coupon));
                            dialog.setMessage(getString(R.string.coupon_removes_other_coupons));
                            
                            dialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
    
                                    if (couponsInfo.getDiscountType().equalsIgnoreCase("fixed_cart")
                                            || couponsInfo.getDiscountType().equalsIgnoreCase("percent"))
                                    {
                                        if (validateCouponCart(couponsInfo))
                                                applyCoupon(couponsInfo);
                                        
                                    }
                                    else if (couponsInfo.getDiscountType().equalsIgnoreCase("fixed_product")
                                            || couponsInfo.getDiscountType().equalsIgnoreCase("percent_product"))
                                    {
                                        if (validateCouponProduct(couponsInfo))
                                            applyCoupon(couponsInfo);
                                    }
                                }
                            });
                            
                            dialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                            
                        }
                        else {
                            if (couponsInfo.getDiscountType().equalsIgnoreCase("fixed_cart")
                                    || couponsInfo.getDiscountType().equalsIgnoreCase("percent"))
                            {
                                if (validateCouponCart(couponsInfo))
                                    applyCoupon(couponsInfo);
                                
                            }
                            else if (couponsInfo.getDiscountType().equalsIgnoreCase("fixed_product")
                                    || couponsInfo.getDiscountType().equalsIgnoreCase("percent_product"))
                            {
                                if (validateCouponProduct(couponsInfo))
                                    applyCoupon(couponsInfo);
                            }
                        }

                    } else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        checkout_coupon_code.setError(response.body().getMessage());

                    } else {
                        // Unexpected Response from Server
                        Toast.makeText(getContext(), getString(R.string.unexpected_response), Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getContext(), ""+response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CouponsData> call, Throwable t) {
                activity.hideDialog();
                Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }



    //*********** Request the Server to Place User's Order ********//

    private void PlaceOrderNow(PostOrder postOrder) {

        Call<OrderData> call = APIClient.getInstance()
                .addToOrder
                        (
                                postOrder
                        );

        call.enqueue(new Callback<OrderData>() {
            @Override
            public void onResponse(Call<OrderData> call, retrofit2.Response<OrderData> response) {

                if(response!=null)
                Log.e("testAlert","response "+response.message());
                else Log.e("testAlert","response "+response);
                activity.hideDialog();
                // Check if the Response is successful
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {

                        // Clear User's Cart
                        StoreCartManager.getInstance().ClearCart();

                        // Clear User's Shipping and Billing info from AppContext
                        ((App) getContext().getApplicationContext()).setAddress(new AddressDetails());
                        ((App) getContext().getApplicationContext()).setAddress(new AddressDetails());

//
//                        // Navigate to Thank_You Fragment
//                        Fragment fragment = new Thank_You();
//                        FragmentManager fragmentManager = getFragmentManager();
//                        fragmentManager.popBackStack(getString(R.string.actionCart), FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                        fragmentManager.beginTransaction()
//                                .add(R.id.main_fragment, fragment)
//                                .commit();
//
//                        activity.recreate();
                        activity.showToast(R.string.thank_you_for_shopping);

                        //todo remember to fix it
                        BaseFragment fragment = new Thank_You();
                        activity.removeTopFragment();
                        activity.removeTopFragment();
                        activity.removeTopFragment();
                        activity.addPage(fragment);

                    }
                    else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        Snackbar.make(rootView, response.body().getMessage(), Snackbar.LENGTH_LONG).show();
    
                    }
                    else {
                        // Unable to get Success status
                        Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderData> call, Throwable t) {

                activity.hideDialog();
                Log.e("testAlert","onFailure " +t.getMessage());
                Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }
    
    
    
    //*********** Apply given Coupon to checkout ********//
    
    public void applyCoupon(CouponsInfo coupon) {
    
        double discount = 0.0;
    
        if (coupon.getDiscountType().equalsIgnoreCase("fixed_cart")) {
            discount = Double.parseDouble(coupon.getAmount());
            
        }
        else if (coupon.getDiscountType().equalsIgnoreCase("percent")) {
            discount = (checkoutSubtotal * Double.parseDouble(coupon.getAmount())) / 100;
            
        }
        else if (coupon.getDiscountType().equalsIgnoreCase("fixed_product")) {
            
            for (int i=0;  i<checkoutItemsList.size();  i++) {
                
                int productID = checkoutItemsList.get(i).getCustomersBasketProduct().getProductsId();
                int categoryID = checkoutItemsList.get(i).getCustomersBasketProduct().getCategoriesId();
                
    
                if (!checkoutItemsList.get(i).getCustomersBasketProduct().getIsSaleProduct().equalsIgnoreCase("1")  ||  !coupon.getExcludeSaleItems().equalsIgnoreCase("1")) {
                    if (!isStringExistsInList(String.valueOf(categoryID), coupon.getExcludedProductCategories())  ||  coupon.getExcludedProductCategories().size() == 0 ) {
                        if (!isStringExistsInList(String.valueOf(productID), coupon.getExcludeProductIds())  ||  coupon.getExcludeProductIds().size() == 0 ) {
                            if (isStringExistsInList(String.valueOf(categoryID), coupon.getProductCategories())  ||  coupon.getProductCategories().size() == 0 ) {
                                if (isStringExistsInList(String.valueOf(productID), coupon.getProductIds())  ||  coupon.getProductIds().size() == 0 ) {
                                    
                                    discount += (Double.parseDouble(coupon.getAmount()) * checkoutItemsList.get(i).getCustomersBasketProduct().getCustomersBasketQuantity());
                                }
                            }
                        }
                    }
                }
    
                
            }
            
        }
        else if (coupon.getDiscountType().equalsIgnoreCase("percent_product")) {
            
            for (int i=0;  i<checkoutItemsList.size();  i++) {
        
                int productID = checkoutItemsList.get(i).getCustomersBasketProduct().getProductsId();
                int categoryID = checkoutItemsList.get(i).getCustomersBasketProduct().getCategoriesId();
    
    
                if (!checkoutItemsList.get(i).getCustomersBasketProduct().getIsSaleProduct().equalsIgnoreCase("1")  ||  !coupon.getExcludeSaleItems().equalsIgnoreCase("1")) {
                    if (!isStringExistsInList(String.valueOf(categoryID), coupon.getExcludedProductCategories())  ||  coupon.getExcludedProductCategories().size() == 0 ) {
                        if (!isStringExistsInList(String.valueOf(productID), coupon.getExcludeProductIds())  ||  coupon.getExcludeProductIds().size() == 0 ) {
                            if (isStringExistsInList(String.valueOf(categoryID), coupon.getProductCategories())  ||  coupon.getProductCategories().size() == 0 ) {
                                if (isStringExistsInList(String.valueOf(productID), coupon.getProductIds())  ||  coupon.getProductIds().size() == 0 ) {
    
                                    double discountOnPrice = (Double.parseDouble(checkoutItemsList.get(i).getCustomersBasketProduct().getProductsFinalPrice()) * Double.parseDouble(coupon.getAmount())) / 100;
                                    discount += (discountOnPrice * checkoutItemsList.get(i).getCustomersBasketProduct().getCustomersBasketQuantity());
                                }
                            }
                        }
                    }
                }
        
            }
        }
        
        
        if ((checkoutDiscount+discount) >= getProductsSubTotal()) {
            showSnackBarForCoupon(getString(R.string.coupon_cannot_be_applied));
        }
        else {
            if (coupon.getIndividualUse().equalsIgnoreCase("1")) {
                couponsList.clear();
                checkoutDiscount = 0.0;
                checkoutShipping = checkoutShippingCost;
                disableOtherCoupons = true;
                setCheckoutTotal();
            }
    
            if (coupon.getFreeShipping().equalsIgnoreCase("1")) {
                checkoutShipping = 0.0;
            }
    
    
    
            checkoutDiscount += discount;
            coupon.setDiscount(String.valueOf(discount));
    
    
            couponsList.add(coupon);
            checkout_coupon_code.setText("");
            couponsAdapter.notifyDataSetChanged();
    
    
            setCheckoutTotal();
        }

    }
    
    
    
    //*********** Remove given Coupon from checkout ********//
    
    public void removeCoupon(CouponsInfo coupon) {
        
        if (coupon.getIndividualUse().equalsIgnoreCase("1")) {
            disableOtherCoupons = false;
        }
    
    
        for (int i=0;  i<couponsList.size();  i++) {
            if (coupon.getCode().equalsIgnoreCase(couponsList.get(i).getCode())) {
                couponsList.remove(i);
                couponsAdapter.notifyDataSetChanged();
            }
        }
    
        
        checkoutShipping = checkoutShippingCost;
    
        for (int i=0;  i<couponsList.size();  i++) {
            if (couponsList.get(i).getFreeShipping().equalsIgnoreCase("1")) {
                checkoutShipping = 0.0;
            }
        }
    
    
        double discount = Double.parseDouble(coupon.getDiscount());
        checkoutDiscount -= discount;
        
    
        setCheckoutTotal();

    }
    
    
    
    //*********** Validate Cart type Coupon ********//
    
    private boolean validateCouponCart(CouponsInfo coupon) {
    
        int user_used_this_coupon_counter = 0;
        
        boolean coupon_already_applied = false;
        
        boolean valid_user_email_for_coupon = false;
        boolean valid_sale_items_in_for_coupon = true;
        
        boolean valid_items_in_cart = false;
        boolean valid_category_items_in_cart = false;
        
        boolean no_excluded_item_in_cart = true;
        boolean no_excluded_category_item_in_cart = true;
        
        
        if (couponsList.size() != 0) {
            for (int i=0;  i<couponsList.size();  i++) {
                if (coupon.getCode().equalsIgnoreCase(couponsList.get(i).getCode())) {
                    coupon_already_applied = true;
                }
            }
        }
        
        
        for (int i=0;  i<coupon.getUsedBy().size();  i++) {
            if (userInfo.getCustomersId().equalsIgnoreCase(coupon.getUsedBy().get(i))) {
                user_used_this_coupon_counter += 1;
            }
        }
    
        
        if (coupon.getEmailRestrictions().size() != 0) {
            if (isStringExistsInList(userInfo.getCustomersEmailAddress(), coupon.getEmailRestrictions())) {
                valid_user_email_for_coupon = true;
            }
        }
        else {
            valid_user_email_for_coupon = true;
        }
        
    
    
        for (int i=0;  i<checkoutItemsList.size();  i++) {
    
            int productID = checkoutItemsList.get(i).getCustomersBasketProduct().getProductsId();
            int categoryID = checkoutItemsList.get(i).getCustomersBasketProduct().getCategoriesId();
            
    
            if (coupon.getExcludeSaleItems().equalsIgnoreCase("1") && checkoutItemsList.get(i).getCustomersBasketProduct().getIsSaleProduct().equalsIgnoreCase("1")) {
                valid_sale_items_in_for_coupon = false;
            }
            
    
            if (coupon.getExcludedProductCategories().size() != 0) {
                if (isStringExistsInList(String.valueOf(categoryID), coupon.getExcludedProductCategories())) {
                    no_excluded_category_item_in_cart = false;
                }
            }
    
            if (coupon.getExcludeProductIds().size() != 0) {
                if (isStringExistsInList(String.valueOf(productID), coupon.getExcludeProductIds())) {
                    no_excluded_item_in_cart = false;
                }
            }
    
            if (coupon.getProductCategories().size() != 0) {
                if (isStringExistsInList(String.valueOf(categoryID), coupon.getProductCategories())) {
                    valid_category_items_in_cart = true;
                }
            } else {
                valid_category_items_in_cart = true;
            }
    
            
            if (coupon.getProductIds().size() != 0) {
                if (isStringExistsInList(String.valueOf(productID), coupon.getProductIds())) {
                    valid_items_in_cart = true;
                }
            } else {
                valid_items_in_cart = true;
            }
            
        }
        
        
        /////////////////////////////////////////////////////
        
        if (!disableOtherCoupons) {
            if (!coupon_already_applied) {
                if (!Utils.checkIsDatePassed(coupon.getExpiryDate())) {
                    if (Integer.parseInt(coupon.getUsageCount()) <= Integer.parseInt(coupon.getUsageLimit())) {
                        if (user_used_this_coupon_counter <= Integer.parseInt(coupon.getUsageLimitPerUser())) {
                            if (valid_user_email_for_coupon) {
                                if (Double.parseDouble(coupon.getMinimumAmount()) <= checkoutTotal) {
                                    if (Double.parseDouble(coupon.getMaximumAmount()) == 0.0  ||  checkoutTotal <= Double.parseDouble(coupon.getMaximumAmount())) {
                                        if (valid_sale_items_in_for_coupon) {
                                            if (no_excluded_category_item_in_cart) {
                                                if (no_excluded_item_in_cart) {
                                                    if (valid_category_items_in_cart) {
                                                        if (valid_items_in_cart) {
                        
                                                            return true;
                        
                                                        } else {
                                                            showSnackBarForCoupon(getString(R.string.coupon_is_not_for_these_products));
                                                            return false;
                                                        }
                                                    } else {
                                                        showSnackBarForCoupon(getString(R.string.coupon_is_not_for_these_categories));
                                                        return false;
                                                    }
                                                } else {
                                                    showSnackBarForCoupon(getString(R.string.coupon_is_not_for_excluded_products));
                                                    return false;
                                                }
                                            } else {
                                                showSnackBarForCoupon(getString(R.string.coupon_is_not_for_excluded_categories));
                                                return false;
                                            }
                                        } else {
                                            showSnackBarForCoupon(getString(R.string.coupon_is_not_for_sale_items));
                                            return false;
                                        }
                                    } else {
                                        showSnackBarForCoupon(getString(R.string.coupon_max_amount_is_less_than_order_total));
                                        return false;
                                    }
                                } else {
                                    showSnackBarForCoupon(getString(R.string.coupon_min_amount_is_greater_than_order_total));
                                    return false;
                                }
                            } else {
                                showSnackBarForCoupon(getString(R.string.coupon_is_not_for_you));
                                return false;
                            }
                        } else {
                            showSnackBarForCoupon(getString(R.string.coupon_used_by_you));
                            return false;
                        }
                    } else {
                        showSnackBarForCoupon(getString(R.string.coupon_used_by_all));
                        return false;
                    }
                } else {
                    checkout_coupon_code.setError(getString(R.string.coupon_expired));
                    return false;
                }
            } else {
                showSnackBarForCoupon(getString(R.string.coupon_applied));
                return false;
            }
        } else {
            showSnackBarForCoupon(getString(R.string.coupon_cannot_used_with_existing));
            return false;
        }

    }
    
    
    
    //*********** Validate Product type Coupon ********//
    
    private boolean validateCouponProduct(CouponsInfo coupon) {
        
        int user_used_this_coupon_counter = 0;
        
        boolean coupon_already_applied = false;
        
        boolean valid_user_email_for_coupon = false;
        boolean valid_sale_items_in_for_coupon = false;
        
        boolean any_valid_item_in_cart = false;
        boolean any_valid_category_item_in_cart = false;
        
        boolean any_non_excluded_item_in_cart = false;
        boolean any_non_excluded_category_item_in_cart = false;
        
        
        if (couponsList.size() != 0) {
            for (int i=0;  i<couponsList.size();  i++) {
                if (coupon.getCode().equalsIgnoreCase(couponsList.get(i).getCode())) {
                    coupon_already_applied = true;
                }
            }
        }
        
        
        for (int i=0;  i<coupon.getUsedBy().size();  i++) {
            if (userInfo.getCustomersId().equalsIgnoreCase(coupon.getUsedBy().get(i))) {
                user_used_this_coupon_counter += 1;
            }
        }
        
        
        if (coupon.getEmailRestrictions().size() != 0) {
            if (isStringExistsInList(userInfo.getCustomersEmailAddress(), coupon.getEmailRestrictions())) {
                valid_user_email_for_coupon = true;
            }
        }
        else {
            valid_user_email_for_coupon = true;
        }
        
        
        
        for (int i=0;  i<checkoutItemsList.size();  i++) {
            
            int productID = checkoutItemsList.get(i).getCustomersBasketProduct().getProductsId();
            int categoryID = checkoutItemsList.get(i).getCustomersBasketProduct().getCategoriesId();
            
            
            if (!coupon.getExcludeSaleItems().equalsIgnoreCase("1") || !checkoutItemsList.get(i).getCustomersBasketProduct().getIsSaleProduct().equalsIgnoreCase("1")) {
                valid_sale_items_in_for_coupon = true;
            }
            
            
            if (coupon.getExcludedProductCategories().size() != 0) {
                if (isStringExistsInList(String.valueOf(categoryID), coupon.getExcludedProductCategories())) {
                    any_non_excluded_category_item_in_cart = true;
                }
            } else {
                any_non_excluded_category_item_in_cart = true;
            }
            
            if (coupon.getExcludeProductIds().size() != 0) {
                if (isStringExistsInList(String.valueOf(productID), coupon.getExcludeProductIds())) {
                    any_non_excluded_item_in_cart = true;
                }
            } else {
                any_non_excluded_item_in_cart = true;
            }
            
            if (coupon.getProductCategories().size() != 0) {
                if (isStringExistsInList(String.valueOf(categoryID), coupon.getProductCategories())) {
                    any_valid_category_item_in_cart = true;
                }
            } else {
                any_valid_category_item_in_cart = true;
            }
            
            
            if (coupon.getProductIds().size() != 0) {
                if (isStringExistsInList(String.valueOf(productID), coupon.getProductIds())) {
                    any_valid_item_in_cart = true;
                }
            } else {
                any_valid_item_in_cart = true;
            }
            
        }
        
        
        /////////////////////////////////////////////////////
        
        if (!disableOtherCoupons) {
            if (!coupon_already_applied) {
                if (!Utils.checkIsDatePassed(coupon.getExpiryDate())) {
                    if (Integer.parseInt(coupon.getUsageCount()) <= Integer.parseInt(coupon.getUsageLimit())) {
                        if (user_used_this_coupon_counter <= Integer.parseInt(coupon.getUsageLimitPerUser())) {
                            if (valid_user_email_for_coupon) {
                                if (Double.parseDouble(coupon.getMinimumAmount()) <= checkoutTotal) {
                                    if (Double.parseDouble(coupon.getMaximumAmount()) == 0.0  ||  checkoutTotal <= Double.parseDouble(coupon.getMaximumAmount())) {
                                        if (valid_sale_items_in_for_coupon) {
                                            if (any_non_excluded_category_item_in_cart) {
                                                if (any_non_excluded_item_in_cart) {
                                                    if (any_valid_category_item_in_cart) {
                                                        if (any_valid_item_in_cart) {
                        
                                                            return true;
                        
                                                        } else {
                                                            showSnackBarForCoupon(getString(R.string.coupon_is_not_for_these_products));
                                                            return false;
                                                        }
                                                    } else {
                                                        showSnackBarForCoupon(getString(R.string.coupon_is_not_for_these_categories));
                                                        return false;
                                                    }
                                                } else {
                                                    showSnackBarForCoupon(getString(R.string.coupon_is_not_for_excluded_products));
                                                    return false;
                                                }
                                            } else {
                                                showSnackBarForCoupon(getString(R.string.coupon_is_not_for_excluded_categories));
                                                return false;
                                            }
                                        } else {
                                            showSnackBarForCoupon(getString(R.string.coupon_is_not_for_sale_items));
                                            return false;
                                        }
                                    } else {
                                        showSnackBarForCoupon(getString(R.string.coupon_max_amount_is_less_than_order_total));
                                        return false;
                                    }
                                } else {
                                    showSnackBarForCoupon(getString(R.string.coupon_min_amount_is_greater_than_order_total));
                                    return false;
                                }
                            } else {
                                showSnackBarForCoupon(getString(R.string.coupon_is_not_for_you));
                                return false;
                            }
                        } else {
                            showSnackBarForCoupon(getString(R.string.coupon_used_by_you));
                            return false;
                        }
                    } else {
                        showSnackBarForCoupon(getString(R.string.coupon_used_by_all));
                        return false;
                    }
                } else {
                    checkout_coupon_code.setError(getString(R.string.coupon_expired));
                    return false;
                }
            } else {
                showSnackBarForCoupon(getString(R.string.coupon_applied));
                return false;
            }
        } else {
            showSnackBarForCoupon(getString(R.string.coupon_cannot_used_with_existing));
            return false;
        }
        
    }
    
    
    
    //*********** Show SnackBar with given Message  ********//
    
    private void showSnackBarForCoupon(String msg) {
        final Snackbar snackbar = Snackbar.make(rootView, msg, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                });
        snackbar.show();
    }
    
    
    
    //*********** Check if the given String exists in the given List ********//
    
    private boolean isStringExistsInList(String str, List<String> stringList) {
        boolean isExists = false;
    
        for (int i=0;  i<stringList.size();  i++) {
            if (stringList.get(i).equalsIgnoreCase(str)) {
                isExists = true;
            }
        }
        
        
        return isExists;
    }

    @Override
    public void initViews() {

    }

    @Override
    public String getTAG() {
        return TAG;
    }
}

