package com.elkdeals.mobile.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mohamed on 3/18/18.
 */

public class BiddingListM {
    @SerializedName("auction")
    @Expose
    BidderAuctionM auctionM;
    @SerializedName("bidder")
    @Expose
    List<BidderM> biddersList = null;

    public BidderAuctionM getAuctionM() {
        return auctionM;
    }

    public void setAuctionM(BidderAuctionM auctionM) {
        this.auctionM = auctionM;
    }

    public List<BidderM> getBiddersList() {
        return biddersList;
    }

    public void setBiddersList(List<BidderM> biddersList) {
        this.biddersList = biddersList;
    }
}
