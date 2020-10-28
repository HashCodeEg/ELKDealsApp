package com.elkdeals.mobile.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.elkdeals.mobile.Utils.Constants;
import com.elkdeals.mobile.api.models.StoreCartModel;
import com.elkdeals.mobile.api.models.banner_model.BannerData;
import com.elkdeals.mobile.api.models.category_model.CategoryData;
import com.elkdeals.mobile.api.models.filter_model.post_filters.PostFilterData;
import com.elkdeals.mobile.api.models.product_model.GetAllProducts;
import com.elkdeals.mobile.api.models.product_model.ProductData;
import com.elkdeals.mobile.api.network.APIClient;
import com.elkdeals.mobile.model.RepositorySidalitac;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class StoreViewModelSidalitac extends BaseViewModel {

    String sortBy = "Newest";
    CompositeDisposable disposable;
    RepositorySidalitac storeModel;
    private MutableLiveData<CategoryData> categories;
    private MutableLiveData<ProductData> products;
    private MutableLiveData<BannerData> homeModel;
    private MutableLiveData<List<StoreCartModel>> cartItems;

    public StoreViewModelSidalitac() {
        this.storeModel = new RepositorySidalitac();
        categories = new MutableLiveData<CategoryData>();
        products = new MutableLiveData<ProductData>();
        homeModel = new MutableLiveData<BannerData>();
        cartItems = new MutableLiveData<List<StoreCartModel>>();
        disposable = new CompositeDisposable();
    }

    public void getCategories() {
        progress.setValue("جار التحميل...");
        disposable.add(storeModel.getStoreCategories().
                subscribe(categoriesModels -> {
                    categoriesModels.save();
                    categories.setValue(categoriesModels);
                    progress.setValue("");
                }, throwable -> {
                    categories.setValue(null);
                    message.setValue("حطأ اثناء تحميل البيانات");
                }));
    }

    public void getProducts(int categoryId, int pageNumber, PostFilterData filters) {
        progress.setValue("جار التحميل...");
        disposable.add(storeModel.getCategoryProducts(categoryId, pageNumber, sortBy,filters).subscribe(productsModelList -> {
            products.setValue(productsModelList);
            progress.setValue("");
        }, throwable -> {
            message.setValue("حطأ اثناء تحميل البيانات");
            products.setValue(null);
        }));
    }
    public void getProducts(int categoryId, int pageNumber,String sortBy, PostFilterData filters) {
        progress.setValue("جار التحميل...");
        disposable.add(storeModel.getCategoryProducts(categoryId, pageNumber, sortBy,filters).subscribe(productsModelList -> {
            products.setValue(productsModelList);
            progress.setValue("");
        }, throwable -> {
            message.setValue("حطأ اثناء تحميل البيانات");
            products.setValue(null);
        }));
    }

    public void getProductDetails(int productId, int customerID) {
        GetAllProducts getAllProducts = new GetAllProducts();
        getAllProducts.setPageNumber(0);
        getAllProducts.setLanguageId(Constants.LANGUAGE_ID);
        getAllProducts.setCustomersId(customerID+"");
        progress.setValue("جار التحميل...");
        getAllProducts.setProductsId(String.valueOf(productId));
        disposable.add(APIClient.getInstance().getAllProducts(getAllProducts)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(productData -> {
            products.setValue(productData);
            progress.setValue("");
        }, throwable -> {
            progress.setValue("");
                    message.setValue("حطأ اثناء تحميل البيانات");
        }));
    }

    public void getBanners() {
        //progress.setValue("Loading home page...");
        disposable.add(storeModel.getBanners().subscribe(storeHomeModel -> {
            homeModel.setValue(storeHomeModel);
            //progress.setValue("");
        }, throwable -> {
            Log.e("error", throwable.getMessage());
        }));
    }

    /*
    public void addToCart(String proid, String quantity) {
        progress.setValue("add Product to cart...");
        disposable.add(storeModel.addToCart(proid, quantity).subscribe(trueMsg -> {
            addToCart.setValue(trueMsg);
            progress.setValue("");
        }, throwable -> message.setValue("Erro loading data")));
    }

    public void changeQuantity(String proid, String quantity) {
        progress.setValue("add Product to cart...");
        disposable.add(storeModel.changeQuantity(proid, quantity).subscribe(trueMsg -> {
            addToCart.setValue(trueMsg);
            progress.setValue("");
        }, throwable -> message.setValue("Erro loading data")));
    }

    public void getCartItems() {
        progress.setValue("loading cart items...");
        disposable.add(storeModel.getCartItems().subscribe(storeCartModels -> {
            cartItems.setValue(storeCartModels);
            progress.setValue("");
        }, throwable -> message.setValue("Erro loading data")));
    }
*/
    public MutableLiveData<CategoryData> getCategoriesMutuable() {
        return categories;
    }

    public MutableLiveData<ProductData> getProducts() {
        return products;
    }


    public MutableLiveData<List<StoreCartModel>> getCartItemsLiveData() {
        return cartItems;
    }

    public MutableLiveData<BannerData> getHomeModel() {
        return homeModel;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

}
