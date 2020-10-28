package com.elkdeals.mobile.api.models;

import android.text.TextUtils;

import com.elkdeals.mobile.App;
import com.elkdeals.mobile.R;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.List;

public class Category  extends ExpandableGroup<Category> {
    int id;
    private String title;
    private String image;
    private int res;
    private boolean selected;
    List<Category> subCategoreis;
    public Category(String title, List<Category> subCategoreis) {
        super(title, subCategoreis);
        this.title =title;
        this.subCategoreis=subCategoreis;
        selected=false;
    }
    public static Category CreateInstance(String name, int res){
        Category category=new Category(name,new ArrayList<>());
        category.setRes(res);
        return category;
    }
    public static List<Category>getDummyStore(){
        ArrayList<Category>categories=new ArrayList<>();
        categories.add(Category.CreateInstance(App.getInstance().getString(R.string.fashion), R.drawable.fashion));
//        categories.add(Category.CreateInstance(App.getInstance().getString(R.string.automotive), R.drawable.automotive));
        categories.add(Category.CreateInstance(App.getInstance().getString(R.string.computing), R.drawable.computing));
        categories.add(Category.CreateInstance(App.getInstance().getString(R.string.home_appliances), R.drawable.home_appliances));
        categories.add(Category.CreateInstance(App.getInstance().getString(R.string.mobile), R.drawable.mobile));

        return categories;
    }
    @Override
    public String getTitle() {
        return title;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public List<Category> getSubCategoreis() {
        return subCategoreis;
    }

    public void setSubCategoreis(List<Category> subCategoreis) {
        this.subCategoreis = subCategoreis;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return TextUtils.isEmpty(title)?"": title;
    }
}
