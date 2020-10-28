package com.elkdeals.mobile.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.activities.BaseActivity;
import com.elkdeals.mobile.api.models.address_model.AddressDetails;
import com.elkdeals.mobile.ui.account.ShippingAddresses;
import com.elkdeals.mobile.ui.account.Add_Address;
import com.elkdeals.mobile.model.RepositorySidalitac;

import java.util.List;


/**
 * AddressListAdapter is the adapter class of RecyclerView holding List of Addresses in My_Addresses
 **/

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.MyViewHolder> {

    BaseActivity activity;
    ShippingAddresses fragment;
    String customerID;
    List<AddressDetails> addressList;

    private int selectedPosition;

    // To keep track of Checked Radio Button
    private RadioButton lastChecked_RB = null;


    public AddressListAdapter(ShippingAddresses fragment, String customerID, int defaultAddressPosition, List<AddressDetails> addressList) {
        this.activity = fragment.activity;
        this.fragment = fragment;
        this.customerID = customerID;
        this.addressList = addressList;
        this.selectedPosition = defaultAddressPosition;
    }



    //********** Called to Inflate a Layout from XML and then return the Holder *********//

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_card_addresses, parent, false);

        return new MyViewHolder(itemView);
    }



    //********** Called by RecyclerView to display the Data at the specified Position *********//

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // Get the data model based on Position
        final AddressDetails addressDetails = addressList.get(position);

        final String addressID = String.valueOf(addressDetails.getAddressId());

        holder.address_title.setText(addressDetails.getFirstname() +" "+ addressDetails.getLastname());
        holder.address_details.setText(addressDetails.getStreet() +", "+ addressDetails.getCity() +", "+ addressDetails.getCountryName());
        

        // Toggle the Default Address RadioButton with Position
        if (position == selectedPosition||addressDetails.getDefaultAddress()==addressDetails.getAddressId()) {
            holder.makeDefault_rb.setChecked(true);
            lastChecked_RB = holder.makeDefault_rb;
        } else {
            holder.makeDefault_rb.setChecked(false);
        }


        // Check the Clicked RadioButton
        holder.makeDefault_rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (lastChecked_RB != null) {
                    lastChecked_RB.setChecked(false);
                }

                // Request the Server to Change Default Address
                RepositorySidalitac.MakeAddressDefault( addressID, activity);
                lastChecked_RB = holder.makeDefault_rb;
                selectedPosition = position;
                fragment.RequestAllAddresses();
            }
        });


        // Edit relevant Address
        holder.edit_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set the current Address Info to Bundle
                Bundle addressInfo = new Bundle();
                addressInfo.putBoolean("isUpdate", true);
                addressInfo.putString("addressID", addressID);
                addressInfo.putString("addressFirstname", addressDetails.getFirstname());
                addressInfo.putString("addressLastname", addressDetails.getLastname());
                addressInfo.putString("addressCountryName", addressDetails.getCountryName());
                addressInfo.putString("addressCountryID", ""+addressDetails.getCountriesId());
                addressInfo.putString("addressZoneName", addressDetails.getZoneName());
                addressInfo.putString("addressZoneID", ""+addressDetails.getZoneId());
                addressInfo.putString("addressState", addressDetails.getState());
                addressInfo.putString("addressCity", addressDetails.getCity());
                addressInfo.putString("addressStreet", addressDetails.getStreet());
                addressInfo.putString("addressPostCode", addressDetails.getPostcode());
                addressInfo.putString("addressSuburb", addressDetails.getSuburb());

                // Navigate to Add_Address Fragment with arguments to Edit Address
                Add_Address fragment = new Add_Address();
                fragment.setArguments(addressInfo);
                activity.addPage(fragment);
            }
        });

    }



    //********** Returns the total number of items in the data set *********//

    @Override
    public int getItemCount() {
        return addressList==null?0:addressList.size();
    }

    public AddressDetails getDefaultAddress() {
        if(getItemCount()==0)
            return null;
//        if (getItemCount() == 1)
//            return addressList.get(0);
        for(AddressDetails addressDetails:addressList)
            if(addressDetails.getDefaultAddress()==addressDetails.getAddressId())
                return addressDetails;
        return null;
    }


    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageButton edit_address;
        RadioButton makeDefault_rb;
        TextView address_title, address_details;


        public MyViewHolder(final View itemView) {
            super(itemView);

            address_title = itemView.findViewById(R.id.address_title);
            address_details = itemView.findViewById(R.id.address_details);
            edit_address = itemView.findViewById(R.id.edit_address);
            makeDefault_rb = itemView.findViewById(R.id.default_address_rb);
        }
    }
}

