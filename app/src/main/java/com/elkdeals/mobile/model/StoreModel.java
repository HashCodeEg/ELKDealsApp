package com.elkdeals.mobile.model;

import com.elkdeals.mobile.api.models.CategoriesModel;
import com.elkdeals.mobile.api.models.StoreCartModel;
import com.elkdeals.mobile.api.models.StoreHomeResponse;
import com.elkdeals.mobile.api.models.StoreProductModel;
import com.elkdeals.mobile.api.models.TrueMsg;

import java.util.List;

import io.reactivex.Single;

public class StoreModel {
    private Repository repository = new Repository();

    public Single<List<CategoriesModel>> getCategories() {
        return repository.getCategoriesList();
    }

    public Single<List<StoreProductModel>> getStoreProducts(String categoryId) {
        return repository.getStoreProducts(categoryId);
    }

    public Single<TrueMsg> addToCart(String proid, String quantity) {
        return repository.addToCart(proid, quantity);
    }

    public Single<TrueMsg> changeQuantity(String proid, String quantity) {
        return repository.changeQuantity(proid, quantity);
    }

    public Single<List<StoreCartModel>> getCartItems() {
        return repository.getCartItems();
    }

    public Single<StoreHomeResponse> getHome() {
        return repository.getHome();
    }
}
