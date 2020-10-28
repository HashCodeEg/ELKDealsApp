package com.elkdeals.mobile.viewmodel;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.elkdeals.mobile.App;
import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.Utils;
import com.elkdeals.mobile.activities.BaseActivity;
import com.elkdeals.mobile.api.models.MobileModel;
import com.elkdeals.mobile.model.LoginModel;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class LoginViewModel extends BaseViewModel {
    CompositeDisposable disposable;
    LoginModel loginModel = new LoginModel();
    private MutableLiveData<MobileModel> user;
    private MutableLiveData<String> openRegister;

    public LoginViewModel() {
        disposable = new CompositeDisposable();
        user = new MutableLiveData<>();
        openRegister = new MutableLiveData<>();
    }

    public void login(String phone, String imie) {
        Utils.phone=phone;
        disposable.add(loginModel.askIfMobileExist(phone).subscribe(regResponseModel -> {
            if (regResponseModel.getStatus())
            {
                Utils.userId = regResponseModel.getId();
                 loginifExist(phone, imie);
            }
            else openRegister.setValue(phone);
        }, throwable -> {
            if (throwable.getCause()!=null)
            Toast.makeText(App.getInstance(),"Error :"+throwable.getCause().getMessage(),Toast.LENGTH_LONG).show();
            else
            Toast.makeText(App.getInstance(),"Error :"+throwable.toString(),Toast.LENGTH_LONG).show();
            message.setValue("Error check network connection 1: "+ throwable.getMessage());
        }));

    }

    private void loginifExist(String phone, String imie) {
        //loginModel.
        loginModel.login(phone, imie).subscribe(new SingleObserver<MobileModel>() {
            @Override
            public void onSubscribe(Disposable d) {
                progress.setValue(Utils.getStringRes(R.string.loading));
                disposable.add(d);
            }

            @Override
            public void onSuccess(MobileModel mobileModel) {
                user.setValue(mobileModel);
                progress.setValue("");
                Utils.setvCode(mobileModel.getCode());
            }
            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                BaseActivity.instance.showToast(e.getMessage());
                message.setValue("Error check network connection 2: "+ e.getMessage());
            }
        });

    }

    public MutableLiveData<String> getOpenRegister() {
        return openRegister;
    }

    public MutableLiveData<MobileModel> getUser() {
        return user;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
