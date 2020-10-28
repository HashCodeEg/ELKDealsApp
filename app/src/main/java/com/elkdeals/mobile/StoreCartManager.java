package com.elkdeals.mobile;

import com.elkdeals.mobile.api.models.TimeLineModel;
import com.elkdeals.mobile.api.models.cart_model.CartProduct;
import com.elkdeals.mobile.database.User_Auctions_DB;
import com.elkdeals.mobile.database.User_Cart_DB;

import java.util.ArrayList;


public class StoreCartManager {
    static StoreCartManager manager;
    private StoreCartManager(){
    }
    public static StoreCartManager getInstance(){
        if(manager==null)
            manager=new StoreCartManager();
        return manager;
    }

    public synchronized ArrayList<TimeLineModel> getAuctions() {
        User_Auctions_DB user_cart_db = new User_Auctions_DB();
        return user_cart_db.getAuctions();
    }

    public synchronized ArrayList<CartProduct> getCartItems() {
        User_Cart_DB user_cart_db = new User_Cart_DB();
        return user_cart_db.getCartItems();
    }

    public synchronized  void addCartItem(CartProduct product) {
       // dealsDatabase.insert(product);
        User_Cart_DB user_cart_db = new User_Cart_DB();
        user_cart_db.addCartItem
                (
                        product
                );
    }
    public synchronized  void addAuction(TimeLineModel timeLineModel) {
       // dealsDatabase.insert(product);
        User_Auctions_DB user_cart_db = new User_Auctions_DB();
        user_cart_db.addAuction(timeLineModel);
    }


    public synchronized void updateItem(CartProduct cartProduct) {

        User_Cart_DB user_cart_db = new User_Cart_DB();
        user_cart_db.updateCartItem(cartProduct);
    }

    public void remove(CartProduct cartProduct) {

        User_Cart_DB user_cart_db = new User_Cart_DB();
        user_cart_db.deleteCartItem(cartProduct);
    }
    public void remove(TimeLineModel timeLineModel) {

        User_Auctions_DB user_cart_db = new User_Auctions_DB();
        user_cart_db.deleteAuction(timeLineModel.getId());
    }

    public void ClearCart() {

        User_Cart_DB user_cart_db = new User_Cart_DB();
        user_cart_db.clearCart();
    }
    public void ClearAuctions() {

        User_Auctions_DB user_cart_db = new User_Auctions_DB();
        user_cart_db.clearAuctions();
    }
}
