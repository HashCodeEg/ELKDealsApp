package com.elkdeals.mobile.api.models;

import java.util.ArrayList;

public class Product {
    String id;
    String price;
    String description;
    String img;

    public static ArrayList<Product> getDummy() {
        ArrayList<Product> products=new ArrayList<>(50);
        for(int i=0;i<50;i++)
        {
            Product product=new Product();
            product.description="Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum";
            product.price=""+i;
            product.img="";
            product.id=i+"";
            products.add(product);
        }
        return products;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
