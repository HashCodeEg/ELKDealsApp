package com.elkdeals.mobile.model;

import com.elkdeals.mobile.api.models.AucDescriptionModel;
import com.elkdeals.mobile.api.models.AucDetailsModel;
import com.elkdeals.mobile.api.models.CategoriesModel;
import com.elkdeals.mobile.api.models.FinishedAuctionsModel;
import com.elkdeals.mobile.api.models.TimeLineModel;

import java.util.List;

import io.reactivex.Single;

public class AuctionsModel {
    private Repository repository = new Repository();

    public Single<List<CategoriesModel>> getCategories() {
        return repository.getCategoriesList();
    }

    public Single<List<TimeLineModel>> getAuctions(String categoryId) {
        return repository.getAuctions(categoryId);
    }

    public Single<List<AucDetailsModel>> getAuctionDetails(String auctionId) {
        return repository.getAuctionDetails(auctionId);
    }

    public Single<List<AucDescriptionModel>> getAuctionItemDetails(String auctionId) {
        return repository.getAuctionItemDetails(auctionId);
    }

    public Single<List<FinishedAuctionsModel>> getClosedAuctions(String categoryId, String type) {
        return repository.getClosedAuctions(categoryId, type);
    }
}
