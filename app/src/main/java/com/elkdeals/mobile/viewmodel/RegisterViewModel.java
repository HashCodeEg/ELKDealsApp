package com.elkdeals.mobile.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.Utils;
import com.elkdeals.mobile.api.models.RegResponseModel;
import com.elkdeals.mobile.api.models.UserModel;
import com.elkdeals.mobile.api.models.user_model.UserData;
import com.elkdeals.mobile.model.RegisterModel;
import com.elkdeals.mobile.model.Repository;
import com.elkdeals.mobile.model.RepositorySidalitac;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class RegisterViewModel extends com.elkdeals.mobile.viewmodel.BaseViewModel {
    CompositeDisposable disposable;
    RegisterModel registerModel = new RegisterModel();
    private MutableLiveData<RegResponseModel> user;
    private MutableLiveData<Boolean> goToLogin;
    private MutableLiveData<Boolean> success;
    private String address = "", natID = "0", cityId = "1";

    public RegisterViewModel() {
        disposable = new CompositeDisposable();
        user = new MutableLiveData<>();
        success = new MutableLiveData<>();
        goToLogin = new MutableLiveData<>();
    }

    public void register(String phone, String password, String name, String email, String imei, String token) {
        Utils.phone = phone;
        registerModel.askIfMobileExist(phone).subscribe(new SingleObserver<RegResponseModel>() {
            @Override
            public void onSubscribe(Disposable d) {
                progress.setValue(Utils.getStringRes(R.string.loading));
                disposable.add(d);
            }

            @Override
            public void onSuccess(RegResponseModel regResponseModel) {
                if (regResponseModel != null && regResponseModel.getStatus()) {
                    new UserModel(Repository.getUserInfo(),regResponseModel.getId()).save();
                    user.setValue(regResponseModel);
                    message.setValue(Utils.getStringRes(R.string.user_already_have_account));
                } else {
                    RepositorySidalitac.getInstance().registerUser(name, "", email, password, phone, "")
                            .subscribe(new SingleObserver<UserData>() {
                                @Override
                                public void onSubscribe(Disposable d) {
                                    disposable.add(d);
                                }

                                @Override
                                public void onSuccess(UserData userData) {
                                    if ("1".equalsIgnoreCase(userData.getSuccess())) {
                                        Log.e("RegisterViewModel", userData.toJson());
                                        message.setValue("تم انشاء حساب للمتجر.");

                                        registerModel.register(phone, natID, name, email, cityId, address, imei, token).subscribe(new SingleObserver<RegResponseModel>() {
                                            @Override
                                            public void onSubscribe(Disposable d) {
                                                progress.setValue(Utils.getStringRes(R.string.loading));
                                                disposable.add(d);
                                            }

                                            @Override
                                            public void onSuccess(RegResponseModel regResponseModel) {

                                                new UserModel(Repository.getUserInfo(),regResponseModel.getId()).save();
                                                user.setValue(regResponseModel);
                                                progress.setValue("");

                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                Log.e("RegisterViewModel", e.getMessage());
                                                message.setValue("Error check network connection 3: " + e.getMessage());
                                            }
                                        });

                                    } else {
                                        message.setValue("user email is already used with another store account!");
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {

                                    registerModel.register(phone, natID, name, email, cityId, address, imei, token).subscribe(new SingleObserver<RegResponseModel>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {
                                            progress.setValue(Utils.getStringRes(R.string.loading));
                                            disposable.add(d);
                                        }

                                        @Override
                                        public void onSuccess(RegResponseModel regResponseModel) {

                                            new UserModel(Repository.getUserInfo(),regResponseModel.getId()).save();
                                            user.setValue(regResponseModel);
                                            progress.setValue("");

                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            Log.e("RegisterViewModel", e.getMessage());
                                            message.setValue("Error check network connection 4: " + e.getMessage());
                                        }
                                    });
                                }
                            });
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("RegisterViewModel", e.getMessage());
                message.setValue("Error check network connection 5: " + e.getMessage());
            }
        });
    }

    public MutableLiveData<RegResponseModel> getUser() {
        return user;
    }

    public MutableLiveData<Boolean> getGoToLogin() {
        return goToLogin;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

    public void updateImei(String token, String imei, UserModel userModel) {
        registerModel.register(userModel.getMobile(), userModel.getNationalid(), userModel.getName(), userModel.getEmail(), userModel.getCityid(), userModel.getAddress(),imei, token,"yes").subscribe(new SingleObserver<RegResponseModel>() {
            @Override
            public void onSubscribe(Disposable d) {
                progress.setValue(Utils.getStringRes(R.string.loading));
                disposable.add(d);
            }

            @Override
            public void onSuccess(RegResponseModel regResponseModel) {
                user.setValue(regResponseModel);
                progress.setValue("");
            }

            @Override
            public void onError(Throwable e) {
                progress.setValue("");
                Log.e("RegisterViewModel", e.getMessage());
                message.setValue("Error check network connection 6: " + e.getMessage());
            }
        });

    }
    public void updateToken(String token,   UserModel userModel) {
        registerModel.register(userModel.getMobile(), userModel.getNationalid(), userModel.getName(), userModel.getEmail(), userModel.getCityid(), userModel.getAddress(), userModel.getImei(), token).subscribe(new SingleObserver<RegResponseModel>() {
            @Override
            public void onSubscribe(Disposable d) {
                progress.setValue(Utils.getStringRes(R.string.loading));
                disposable.add(d);
            }

            @Override
            public void onSuccess(RegResponseModel regResponseModel) {
                user.setValue(regResponseModel);
                progress.setValue("");
            }

            @Override
            public void onError(Throwable e) {
                progress.setValue("");
                Log.e("RegisterViewModel", e.getMessage());
                message.setValue("Error check network connection 6: " + e.getMessage());
            }
        });

    }

    public MutableLiveData<Boolean> getSuccess() {
        return success;
    }

    public void updateUserInfo(UserModel userModel) {
        registerModel.register(userModel.getMobile(), userModel.getNationalid(), userModel.getName(), userModel.getEmail(), userModel.getCityid(), userModel.getAddress(), userModel.getImei(), userModel.getToken()).subscribe(new SingleObserver<RegResponseModel>() {
            @Override
            public void onSubscribe(Disposable d) {
                progress.setValue(Utils.getStringRes(R.string.loading));
                disposable.add(d);
            }

            @Override
            public void onSuccess(RegResponseModel regResponseModel) {
                if(regResponseModel.getStatus()){
                user.setValue(regResponseModel);
                success.setValue(true);
                message.setValue(Utils.getStringRes(R.string.profile_updated_successfully));
                }
                else{

                    message.setValue(Utils.getStringRes(R.string.error_updating_profile)+" : " +regResponseModel.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                progress.setValue("");
                Log.e("RegisterViewModel", e.getMessage());
                message.setValue("Error check network connection 7: " + e.getMessage());
            }
        });

    }
}

