package com.elkdeals.mobile.model;

import com.elkdeals.mobile.api.models.FAQModel;
import com.elkdeals.mobile.api.models.HotLines;

import java.util.List;

import io.reactivex.Single;

public class InfoModel {
    private Repository repository = new Repository();

    public Single<List<FAQModel>> getFAQList() {
        return repository.getFAQList();
    }
    public Single<List<HotLines>> getAllHotLiens() {
        return repository.getAllHotLiens();
    }
}
