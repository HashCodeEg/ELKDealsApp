package com.elkdeals.mobile.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.elkdeals.mobile.api.models.CategoriesModel;
import com.elkdeals.mobile.api.models.StoreCartModel;
import com.elkdeals.mobile.api.models.StoreHomeResponse;
import com.elkdeals.mobile.api.models.StoreProductModel;
import com.elkdeals.mobile.api.models.TrueMsg;
import com.elkdeals.mobile.model.StoreModel;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class StoreViewModel extends BaseViewModel {
    CompositeDisposable disposable;
    StoreModel storeModel;
    private MutableLiveData<List<CategoriesModel>> categories;
    private MutableLiveData<List<StoreProductModel>> products;
    private MutableLiveData<TrueMsg> addToCart;
    private MutableLiveData<StoreHomeResponse> homeModel;
    private MutableLiveData<List<StoreCartModel>> cartItems;

    public StoreViewModel() {
        this.storeModel = new StoreModel();
        categories = new MutableLiveData<>();
        products = new MutableLiveData<>();
        homeModel = new MutableLiveData<>();
        addToCart = new MutableLiveData<>();
        cartItems = new MutableLiveData<List<com.elkdeals.mobile.api.models.StoreCartModel>>();
        disposable = new CompositeDisposable();
    }

    public void getCategories() {
        progress.setValue("loading categories...");
        disposable.add(storeModel.getCategories().subscribe(categoriesModels -> {
            categories.setValue(categoriesModels);
            progress.setValue("");
        }, throwable -> message.setValue("Erro loading data")));
    }

    public void getProducts(String categoryId) {
        progress.setValue("loading Products...");
        disposable.add(storeModel.getStoreProducts(categoryId).subscribe(productsModelList -> {
            products.setValue(productsModelList);
            progress.setValue("");
        }, throwable -> message.setValue("Erro loading data")));
    }

    public void getStoreHome() {
        progress.setValue("Loading home page...");
        disposable.add(storeModel.getHome().subscribe(storeHomeModel -> {
            homeModel.setValue(storeHomeModel);
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
    public MutableLiveData<List<CategoriesModel>> getCategoriesMutuable() {
        return categories;
    }

    public MutableLiveData<List<StoreProductModel>> getProducts() {
        return products;
    }


    public MutableLiveData<StoreHomeResponse> getHomeModel() {
        return homeModel;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
