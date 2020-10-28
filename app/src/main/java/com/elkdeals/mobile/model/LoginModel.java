package com.elkdeals.mobile.model;

import com.elkdeals.mobile.api.models.MobileModel;
import com.elkdeals.mobile.api.models.RegResponseModel;

import io.reactivex.Single;

public class LoginModel {
    private Repository repository = new Repository();

    public Single<MobileModel> login(String mobile, String imie) {
        return repository.getOtpCode(mobile, imie);
    }

    public Single<RegResponseModel> askIfMobileExist(String mobile) {
        return repository.askIfMobileExist(mobile);
    }
}
