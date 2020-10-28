package com.elkdeals.mobile.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.elkdeals.mobile.api.models.FAQModel;
import com.elkdeals.mobile.api.models.HotLines;
import com.elkdeals.mobile.model.InfoModel;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class BaseViewModel extends ViewModel {

    MutableLiveData<String> progress;
    MutableLiveData<String> message;
    MutableLiveData<String> toast;


    public MutableLiveData<String> getProgress() {
        return progress;
    }

    public MutableLiveData<String> getMessage() {
        return message;
    }

    public MutableLiveData<String> getToast() {
        return toast;
    }

    public CompositeDisposable disposable;
    InfoModel infoModel;
    private MutableLiveData<List<FAQModel>> faqs;
    private MutableLiveData<List<HotLines>> hotLines;
    public static String hotLine = "19157";

    public BaseViewModel() {
        progress = new MutableLiveData<>();
        message = new MutableLiveData<>();
        toast = new MutableLiveData<>();
        this.infoModel = new InfoModel();
        faqs = new MutableLiveData<>();
        hotLines = new MutableLiveData<>();
        disposable = new CompositeDisposable();
    }

    public void getFAQ() {
        progress.setValue("loading categories...");
        disposable.add(infoModel.getFAQList().subscribe(fqaModels -> {
            faqs.setValue(fqaModels);
            progress.setValue("");
        }, throwable -> message.setValue("Erro loading data")));
    }

    public void getAllHotLiens() {
        disposable.add(infoModel.getAllHotLiens().subscribe(hotLines -> {
            if (hotLines != null && hotLines.size() > 0 && hotLines.get(0) != null)
                this.hotLines.setValue(hotLines);
            if (hotLines != null && hotLines.size() > 0 && !TextUtils.isEmpty(hotLines.get(0).getMobile())) {
                hotLine = hotLines.get(0).getMobile();
            }
        }, throwable -> toast.setValue("Erro loading data")));
    }

    public MutableLiveData<List<FAQModel>> getFaqLiveData() {
        return faqs;
    }

    public MutableLiveData<List<HotLines>> getHotLines() {
        return hotLines;
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
