package com.elkdeals.mobile.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.Utils;
import com.elkdeals.mobile.api.models.cart_model.CartProductAttributes;
import com.elkdeals.mobile.api.models.product_model.Attribute;
import com.elkdeals.mobile.api.models.product_model.Option;
import com.elkdeals.mobile.api.models.product_model.Value;

import java.util.ArrayList;
import java.util.List;


/**
 * ProductAttributesAdapter is the adapter class of RecyclerView holding List of Attributes in Product_Description
 **/

public class ProductAttributesAdapter extends RecyclerView.Adapter<ProductAttributesAdapter.MyViewHolder> {

    private Context context;
    private String[] attributePrices;
    private List<Value> attributeValuesList;

    private List<Attribute> productAttributesList;
    private List<CartProductAttributes> selectedAttributesList;


    public ProductAttributesAdapter(Context context, List<Attribute> productAttributesList, List<CartProductAttributes> selectedAttributesList) {
        this.context = context;
        this.productAttributesList = productAttributesList;
        this.selectedAttributesList = selectedAttributesList;

        // Set the size of AttributePrices Array
        attributePrices = new String[this.productAttributesList.size()];

        // Set 0 value at every index of AttributePrices Array
        for (int i=0;  i<attributePrices.length;  i++) {
            attributePrices[i] = "0";
        }
    }



    //********** Called to Inflate a Layout from XML and then return the Holder *********//

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_product_attributes, parent, false);

        return new MyViewHolder(itemView);
    }



    //********** Called by RecyclerView to display the Data at the specified Position *********//

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // Get the data model based on Position
        final Attribute productsAttribute = productAttributesList.get(position);

        // Get Name of Attribute
        final Option option = productsAttribute.getOption();
        // ValuesList of Attribute
        attributeValuesList = new ArrayList<>();
        attributeValuesList = productsAttribute.getValues();


        // String Array of Attribute Values
        List<String> attributeValueNames = new ArrayList<>();

        // Add Attribute Values to AttributeValueNames Array as String
        for (int i=0;  i<attributeValuesList.size();  i++) {
            attributeValueNames.add(attributeValuesList.get(i).getValue()
                                    + " ("
                                    + attributeValuesList.get(i).getPricePrefix()
                                    + Utils.getStringRes(R.string.egp)
                                    + attributeValuesList.get(i).getPrice()
                                    +")");
        }


        holder.attribute_name.setText(option.getName());
        holder.attribute_value.setText(attributeValueNames.get(0));

    }



    //********** Returns the total number of items in the data set *********//

    @Override
    public int getItemCount() {
        return productAttributesList.size();
    }



    //********** Returns the Array of Attribute Prices *********//

    public String[] getAttributePrices() {
        return attributePrices;
    }



    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

    public class MyViewHolder extends RecyclerView.ViewHolder  {
    
        LinearLayout attribute;
        TextView attribute_name, attribute_value;


        public MyViewHolder(final View itemView) {
            super(itemView);
    
            attribute =  itemView.findViewById(R.id.attribute);
            attribute_name = itemView.findViewById(R.id.attribute_name);
            attribute_value = itemView.findViewById(R.id.attribute_value);
        }
    }

}

