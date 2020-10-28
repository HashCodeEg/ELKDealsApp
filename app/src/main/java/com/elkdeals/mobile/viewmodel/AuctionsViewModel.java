package com.elkdeals.mobile.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.elkdeals.mobile.api.models.AucDescriptionModel;
import com.elkdeals.mobile.api.models.AucDetailsModel;
import com.elkdeals.mobile.api.models.CategoriesModel;
import com.elkdeals.mobile.api.models.FinishedAuctionsModel;
import com.elkdeals.mobile.api.models.TimeLineModel;
import com.elkdeals.mobile.model.AuctionsModel;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class AuctionsViewModel extends BaseViewModel {
    CompositeDisposable disposable;
    AuctionsModel model;
    private MutableLiveData<List<CategoriesModel>> categories;
    private MutableLiveData<List<TimeLineModel>> auctions;
    private MutableLiveData<AucDetailsModel> auctionDetail;
    private MutableLiveData<List<AucDescriptionModel>> auctionItemDetail;
    private MutableLiveData<List<FinishedAuctionsModel>> closedAuctions;

    public AuctionsViewModel() {
        this.model = new AuctionsModel();
        categories = new MutableLiveData<>();
        auctions = new MutableLiveData<>();
        auctionDetail = new MutableLiveData<>();
        auctionItemDetail = new MutableLiveData<>();
        closedAuctions = new MutableLiveData<>();
        disposable = new CompositeDisposable();
    }

    public void getCategories() {
        progress.setValue("loading auctions...");
        disposable.add(model.getCategories().subscribe(categoriesModels -> {
            categories.setValue(categoriesModels);
            progress.setValue("");
        }, throwable -> message.setValue("Erro loading data")));
    }

    public void getAuctions(String catId) {
        progress.setValue("loading auctions...");
        disposable.add(model.getAuctions(catId).subscribe(categoriesModels -> {
            auctions.setValue(categoriesModels);
            progress.setValue("");
        }, throwable -> message.setValue("Erro loading data")));
    }

    public void getAuctionDetails(String auctionID) {
        progress.setValue("loading auction...");
        disposable.add(model.getAuctionDetails(auctionID).subscribe((aucDetailsModels -> {
            if (aucDetailsModels != null && aucDetailsModels.size() > 0)
                auctionDetail.setValue(aucDetailsModels.get(0));
            progress.setValue("");
        }), throwable -> message.setValue("Erro loading data")));
    }

    public void getAuctionItemDetails(String auctionID) {
        progress.setValue("loading auctions...");
        disposable.add(model.getAuctionItemDetails(auctionID).subscribe((categoriesModels -> {
            if (categoriesModels != null)
                auctionItemDetail.setValue(categoriesModels);
            progress.setValue("");
        }), throwable -> message.setValue("Erro loading data")));
    }

    public void getClosedAuctions(String categoryId, String type) {
        progress.setValue("loading auctions...");
        disposable.add(model.getClosedAuctions(categoryId, type).subscribe((finishedAuctionsModels -> {
            if (finishedAuctionsModels != null)
                closedAuctions.setValue(finishedAuctionsModels);
            progress.setValue("");
        }), throwable -> message.setValue("Erro loading data")));
    }

    public MutableLiveData<List<CategoriesModel>> getCategoriesMutuable() {
        return categories;
    }

    public MutableLiveData<List<TimeLineModel>> getAuctions() {
        return auctions;
    }

    public MutableLiveData<AucDetailsModel> getAuctionDetail() {
        return auctionDetail;
    }

    public MutableLiveData<List<AucDescriptionModel>> getAuctionItemDetail() {
        return auctionItemDetail;
    }

    public MutableLiveData<List<FinishedAuctionsModel>> getClosedAuctions() {
        return closedAuctions;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
