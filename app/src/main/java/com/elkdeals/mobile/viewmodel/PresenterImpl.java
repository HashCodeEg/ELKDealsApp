package com.elkdeals.mobile.viewmodel;

import android.util.Log;

import com.elkdeals.mobile.activities.ResultsActivitty;
import com.elkdeals.mobile.api.API;
import com.elkdeals.mobile.api.Services;
import com.elkdeals.mobile.api.models.BidderAuctionM;
import com.elkdeals.mobile.api.models.BiddingListM;
import com.elkdeals.mobile.model.Repository;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterImpl{
    Timer localTimer;
    ResultsActivitty view;
    String auctionId;
    String userId;
    public PresenterImpl(ResultsActivitty view, String auctionId) {
        this.view = view;
        this.auctionId = auctionId;
        this.userId = Repository.getUserInfo().getId();
        this.localTimer = new Timer();
    }

     public void loadResults() {
        view.showProgress();
         Services api= API.getAPIS();
         api.getBiddingist(auctionId,userId).enqueue(new Callback<BiddingListM>() {
             public void onResponse(Call<BiddingListM> call, Response<BiddingListM> response) {
                view.hideProgress();
                if (!response.isSuccessful()) {
                } else if (response.isSuccessful()) {
                    if (response.body().getBiddersList().size() == 0)
                        view.showEmpty();

                    localTimer.cancel();
                    view.getResults(response.body());
                    BidderAuctionM m = response.body().getAuctionM();
                    view.getLocalTimer(Integer.parseInt(m.getDatetype()), m.getEnddate());
                    if (m.isStarted() && !m.isFinishedfinal()) {
                        view.auctionIsWorking(m);
                        if (m.getEnddate() == 0) {
                            auctionFinished();
                            view.executeTask();
                        }
                    }

                    view.setDataInVeiws(m);

                    if (!m.isStarted())
                        view.auctionWillStart();
                    if (m.isFinishedfinal() && m.isStarted())
                        view.auctionIsFinished();

                }
            }

             public void onFailure(Call<BiddingListM> call, Throwable t) {
                view.hideProgress();
                view.executeTask();
            }
        });
    }


     public void localTimer(int dateType1, final int endDate1) {
        localTimer = new Timer();
        int[] endDateForLocalTimer = {endDate1};
        switch (dateType1) {
            case 1:
                localTimer.scheduleAtFixedRate(new TimerTask() {
                    
                    public void run() {
                        if (endDateForLocalTimer[0] != 0) {
                            endDateForLocalTimer[0]--;
                            view.setTextTimer(endDateForLocalTimer[0] + "ثانيه");
                            Log.d("SSSSSSSS", endDateForLocalTimer[0] + "");
                        }
                        if (endDateForLocalTimer[0] == 0) {
                            fromWillToWorking();
                            view.executeTask();
                            auctionFinished();
                        }
                    }
                }, 1000, 1000);
                break;
            case 2:
                localTimer.scheduleAtFixedRate(new TimerTask() {
                    
                    public void run() {

                        if (endDateForLocalTimer[0] != 0 && endDateForLocalTimer[0] != 3) {
                            endDateForLocalTimer[0]--;
                            view.setTextTimer(endDateForLocalTimer[0] + "دقيقة");
                        }
                       // if (endDateForLocalTimer[0] == 3) {
                           // fromWillToWorking();
                            view.executeTask();
                        //}

                    }
                }, 60000, 60000);
                break;
            case 3:
                localTimer.scheduleAtFixedRate(new TimerTask() {
                    
                    public void run() {

                        if (endDateForLocalTimer[0] != 0) {
                            endDateForLocalTimer[0]--;
                            view.setTextTimer(endDateForLocalTimer[0] + "ساعه");
                        }
                        if (endDateForLocalTimer[0] == 3) {
                           // fromWillToWorking();
                            view.executeTask();
                        }

                    }
                }, 3600000, 3600000);
                break;
        }


    }

    public void auctionFinished() {
        Services api= API.getAPIS();
        api.getBiddingist(auctionId,userId).enqueue(new Callback<BiddingListM>() {
             public void onResponse(Call<BiddingListM> call, Response<BiddingListM> response) {
                view.hideProgress();
                if (!response.isSuccessful()) {
                } else if (response.isSuccessful()) {

                    BidderAuctionM m = response.body().getAuctionM();
                    if (m.isStarted() && m.isFinishedfinal())
                        view.checkIfAuctionFinished(true);
                }
            }

             public void onFailure(Call<BiddingListM> call, Throwable t) {
            }
        });
    }

    public void fromWillToWorking() {
        Services api= API.getAPIS();
        api.getBiddingist(auctionId,userId).enqueue(new Callback<BiddingListM>() {
             public void onResponse(Call<BiddingListM> call, Response<BiddingListM> response) {
                view.hideProgress();
                if (!response.isSuccessful()) {
                } else if (response.isSuccessful()) {

                    BidderAuctionM m = response.body().getAuctionM();
                    if (m.isStarted() && !m.isFinishedfinal()) {
                        view.executeTask();
                    }
                }
            }

             public void onFailure(Call<BiddingListM> call, Throwable t) {
            }
        });
    }

    public void cancelTimer() {
        localTimer.cancel();
    }
}