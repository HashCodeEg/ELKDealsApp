package com.elkdeals.mobile.api.models.category_model;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.List;


public class CategoryDetails extends ExpandableGroup<CategoryDetails> {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("order")
    @Expose
    private String order;
    @SerializedName("parent_id")
    @Expose
    private String parentId;
    @SerializedName("total_products")
    @Expose
    private String totalProducts;
    private boolean selected;
    ArrayList<CategoryDetails>subCategories;

    public static CategoryDetails instance(String id){
        CategoryDetails categoryDetails = CategoryData.read(id);
        if (categoryDetails == null)
            categoryDetails = new CategoryDetails();
        categoryDetails.setId(id);
        return categoryDetails;

    }

    public CategoryDetails(String title, List<CategoryDetails> items) {
        super(title, items);
        subCategories=new ArrayList<>();
        subCategories.addAll(items);
    }
    public CategoryDetails() {
        subCategories=new ArrayList<>();
    }

    public static CategoryDetails createExpandable(CategoryDetails categoryDetails) {
        CategoryDetails categoryDetails1=new CategoryDetails(categoryDetails.name,categoryDetails.subCategories);
        categoryDetails1.id=categoryDetails.id;
        categoryDetails1.image=categoryDetails.image;
        categoryDetails1.icon=categoryDetails.icon;
        categoryDetails1.name=categoryDetails.name;
        categoryDetails1.order=categoryDetails.order;
        categoryDetails1.parentId=categoryDetails.parentId;
        categoryDetails1.totalProducts=categoryDetails.totalProducts;
        return categoryDetails1;
    }


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void addSubCategory(CategoryDetails subCategoryDetails){
        subCategories.add(subCategoryDetails);
    }
    public ArrayList<CategoryDetails> getSubCategories() {

        return subCategories;
    }

    public void setSubCategories(ArrayList<CategoryDetails> subCategories) {
        this.subCategories = subCategories;
    }

    public String getId() {
        if (TextUtils.isEmpty(id))
            return "";
        return id;
    }
    public int getIdInt() {

        try
        {
            return Integer.parseInt(id);
        }catch (Exception e){

        }
        return 0;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(String totalProducts) {
        this.totalProducts = totalProducts;
    }

    @Override
    public String toString() {
        return name;
    }


}
