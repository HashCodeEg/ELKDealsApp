package com.elkdeals.mobile.model;

import com.elkdeals.mobile.api.models.RegResponseModel;

import io.reactivex.Single;

public class RegisterModel {
    private Repository repository = new Repository();

    public Single<RegResponseModel> register(String mobile, String natID, String name, String email, String cityId, String address, String imei, String token) {
        return repository.register(mobile, natID, name, email, cityId, address, imei, token);
    }

    public Single<RegResponseModel> register(String mobile, String natID, String name, String email, String cityId, String address, String imei, String token, String logout) {
        return repository.register(mobile, natID, name, email, cityId, address, imei, token,logout);
    }

    public Single<RegResponseModel> askIfMobileExist(String mobile) {
        return repository.askIfMobileExist(mobile);
    }
}
