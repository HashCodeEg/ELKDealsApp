package com.elkdeals.mobile.api.models.requests;

import com.google.gson.annotations.SerializedName;

public class SellerDetails {
    @SerializedName("entry_firstname")
    private String entryFirstname;
    @SerializedName("entry_lastname")
    private String entryLastname;
    @SerializedName("entry_street_address")
    private String entryStreetAddress;
    @SerializedName("entry_suburb")
    private String entrySuburb;
    @SerializedName("entry_postcode")
    private String entryPostcode;
    @SerializedName("entry_city")
    private String entryCity;
    @SerializedName("entry_country_id")
    private Integer entryCountryId;
    @SerializedName("entry_zone_id")
    private String entryZoneId;
    @SerializedName("customers_id")
    private String customersId;
    @SerializedName("entry_gender")
    private String entryGender;
    @SerializedName("entry_company")
    private String entryCompany;
    @SerializedName("location_latitude")
    private String locationLatitude;
    @SerializedName("location_longitude")
    private String locationLongitude;


    public String getEntryFirstname() {
        return entryFirstname;
    }

    public void setEntryFirstname(String entryFirstname) {
        this.entryFirstname = entryFirstname;
    }

    public String getEntryLastname() {
        return entryLastname;
    }

    public void setEntryLastname(String entryLastname) {
        this.entryLastname = entryLastname;
    }

    public String getEntryStreetAddress() {
        return entryStreetAddress;
    }

    public void setEntryStreetAddress(String entryStreetAddress) {
        this.entryStreetAddress = entryStreetAddress;
    }

    public String getEntrySuburb() {
        return entrySuburb;
    }

    public void setEntrySuburb(String entrySuburb) {
        this.entrySuburb = entrySuburb;
    }

    public String getEntryPostcode() {
        return entryPostcode;
    }

    public void setEntryPostcode(String entryPostcode) {
        this.entryPostcode = entryPostcode;
    }

    public String getEntryCity() {
        return entryCity;
    }

    public void setEntryCity(String entryCity) {
        this.entryCity = entryCity;
    }

    public Integer getEntryCountryId() {
        return entryCountryId;
    }

    public void setEntryCountryId(Integer entryCountryId) {
        this.entryCountryId = entryCountryId;
    }

    public String getEntryZoneId() {
        return entryZoneId;
    }

    public void setEntryZoneId(String entryZoneId) {
        this.entryZoneId = entryZoneId;
    }

    public String getCustomersId() {
        return customersId;
    }

    public void setCustomersId(String customersId) {
        this.customersId = customersId;
    }

    public String getEntryGender() {
        return entryGender;
    }

    public void setEntryGender(String entryGender) {
        this.entryGender = entryGender;
    }

    public String getEntryCompany() {
        return entryCompany;
    }

    public void setEntryCompany(String entryCompany) {
        this.entryCompany = entryCompany;
    }

    public String getLocationLatitude() {
        return locationLatitude;
    }

    public void setLocationLatitude(String locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    public String getLocationLongitude() {
        return locationLongitude;
    }

    public void setLocationLongitude(String locationLongitude) {
        this.locationLongitude = locationLongitude;
    }
}
