package com.elkdeals.mobile.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.Methods;
import com.elkdeals.mobile.adapters.CurrentAuctionsAdapter;
import com.elkdeals.mobile.adapters.MenuAdapter;
import com.elkdeals.mobile.api.API;
import com.elkdeals.mobile.api.Services;
import com.elkdeals.mobile.api.models.AucSpineerModel;
import com.elkdeals.mobile.api.models.CurrentAuctionsModel;
import com.elkdeals.mobile.api.models.UserModel;
import com.elkdeals.mobile.custom.ProfileImageView;
import com.elkdeals.mobile.model.Repository;
import com.elkdeals.mobile.recievers.MyBroadcastReceiver;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.elkdeals.mobile.Utils.Methods.isNetworkConnected;

public class CurrentAuctions extends BaseActivity implements AdapterView.OnItemSelectedListener {

    public static final String TAG = "CurrentAuctions";
    //-------------------------------------------------------------------------------------------------//
    @BindView(R.id.currentAuctionListView)
    ListView currentAuctionListView;                    // User Auctions ListView
    /*@BindView(R.id.userInvoicesIMGView)
    ImageView currentIMGView;                       // User Invoices ImageView (Toolbar)
    @BindView(R.id.cartIMGView)
    ImageView cartIMGView;                               // Cart ImageView (العربه) (Toolbar)
    @BindView(R.id.cartNumTextView)
    TextView cartNumTextView;                           // Num Of Auctions In Cart TextView (Toolbar)
    @BindView(R.id.homeIMGView)
    ImageView homeIMGView;                               // Home ImageView (Toolbar)
    @BindView(R.id.videoIMGView)
    ImageView videoIMGView;  */                            // Video ImageView (Toolbar)
    @BindView(R.id.auctionsTypesSpinner)
    Spinner aucTypeSpinner;                      // Auctions Types Spinner (0,1,2)
    //-------------------------------------------------------------------------------------------------//

    //-------------------------------------------------------------------------------------------------//
    List<CurrentAuctionsModel> auctionsList;                  // Auctions List  Items (Serialized Model)
    List<AucSpineerModel> getAuctionsListToDetailsActivity;   // To Fill Spinner With Auctions To Details Activity Items
    CurrentAuctionsAdapter currentAuctionAdapter;             // User Auctions Adapter
    String aucType = "-1";                                     // Auction Type
    MyBroadcastReceiver myBroadcastReceiver;

    //-------------------------------------------------------------------------------------------------//


    @BindView(R.id.profile_image)
    ProfileImageView profileImageView;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.username)
    TextView userName;
    @Override
    public void initViews() {
        super.initViews();
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_add_cart_requests));
        navigationView.setBackground(getResources().getDrawable(R.drawable.ic_nav_menu_requests));        UserModel userModel = Repository.getUserInfo();
        userName.setText(userModel.getName());
        email.setText(userModel.getEmail());
        String image = userModel.getImage();
        if(TextUtils.isEmpty(image))
            return;
        try{
            byte[] bvytessf = Base64.decode(image, 1);
            Bitmap bmp3 = BitmapFactory.decodeByteArray(bvytessf, 0, bvytessf.length);
            profileImageView.setImageBitmap(bmp3);
        }catch (Exception ignored){

        }
    }
    public CurrentAuctions() {
    }

    @Override
    public ArrayList<MenuAdapter.MenuItem> getItems() {
        return getAuctionsMenuItems();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        putContentView(R.layout.activity_current_auctions);
        ButterKnife.bind(this);
        Methods.setAppFont();


        if (isNetworkConnected(this)) {
            auctionsList = new ArrayList<>();
            getUserAuctions(aucType);
            //Methods.setCartNum(cartNumTextView, this);

            String[] aucTypesArray = {
                    "مزادات مركبة",
                    "مزادات بسيطة",
                    "مزادات المدة"
            };

            aucTypeSpinner.setVisibility(View.GONE);
//            aucTypeSpinner.setOnItemSelectedListener(this);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spin_item, aucTypesArray);
            aucTypeSpinner.setAdapter(adapter);

         /*
        * Fire Background Service To Get Flag
        * @var      myBroadcastReceiver To Get Message From Service
        * @method   Methods.getMessageFromBroadCast() Get Message From Service
        * */
            //new RestClient(this);
            //myBroadcastReceiver = new MyBroadcastReceiver();
            // Methods.getMessageFromBroadCast(this, myBroadcastReceiver);
            // String serverMessage = Methods.getFromSharedPref(Keys.ACTV_CurrentAuctions,this);
            // Log.d("NAAAAMES", serverMessage + " --- " + Keys.ACTV_CurrentAuctions);
        } else {
            Toast.makeText(this, this.getResources().getString(R.string.no_internet_connection) + "", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            unregisterReceiver(myBroadcastReceiver);
        } catch (IllegalArgumentException e) {
        }
    }

    // To Check IF User Make Action With Screen
    boolean flag = false;

    // To Check IF User Make Action With Screen
    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        flag = true;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


        if (isNetworkConnected(this)) {
            if (flag) {
                if (i == 0) {
                    aucType = "0";
                    getUserAuctions(aucType);
                } else if (i == 1) {
                    aucType = "1";
                    getUserAuctions(aucType);
                } else if (i == 2) {
                    aucType = "2";
                    getUserAuctions(aucType);
                }
            }
        } else {
            Toast.makeText(this, this.getResources().getString(R.string.no_internet_connection) + "", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    /*
    * This Method To Get All USer Auctions
    * @param aucType (0,1,2)
    * */
    private void getUserAuctions(final String aucType) {
        String userId = Repository.getUserInfo().getId();
        getUserAuctions(aucType, userId);
    }
    ////////////////////////////////---All User Auctions----/////////////////////////////////////////
    public void getUserAuctions(String aucType, String userId) {
        Services api = API.getAPIS();
        activity.showProgressBar("جار تحميل المنتجات");
        Call<List<CurrentAuctionsModel>> call = api.getCurrentAuction(userId, aucType);
        call.enqueue(new Callback<List<CurrentAuctionsModel>>() {
            @Override
            public void onResponse(Call<List<CurrentAuctionsModel>> call, Response<List<CurrentAuctionsModel>> response) {
                activity.hideDialog();
                if (response.isSuccessful())
                    getAllUserAucions(response);
                else {
                    showToast("حدث خطــــأ اثناء الاتصال");
                }
            }

            @Override
            public void onFailure(Call<List<CurrentAuctionsModel>> call, Throwable t) {
                activity.hideDialog();
                showToast("حدث خطــــأ اثناء الاتصال");
            }
        });
    }
    public void getAllUserAucions(Response<List<CurrentAuctionsModel>> response) {
        getAuctionsListToDetailsActivity = new ArrayList<>();
        auctionsList = new ArrayList<>();
        activity.hideDialog();
        if (response.isSuccessful()) {

            for (int i = 0; i < response.body().size(); i++) {
                auctionsList.add(response.body().get(i));
            }
            for (int i = 0; i < auctionsList.size(); i++) {
                getAuctionsListToDetailsActivity.add(new AucSpineerModel(
                        auctionsList.get(i).getId(),
                        auctionsList.get(i).getName(),
                        auctionsList.get(i).getWay(),
                        auctionsList.get(i).getCname(),
                        auctionsList.get(i).getCurrent(),
                        auctionsList.get(i).getImage()
                ));
            }

            currentAuctionAdapter = new CurrentAuctionsAdapter(CurrentAuctions.this, R.layout.item_row_auc, auctionsList, getAuctionsListToDetailsActivity, aucType);
            currentAuctionListView.setAdapter(currentAuctionAdapter);


            if (auctionsList.size() == 0) {
                Toast.makeText(CurrentAuctions.this, "لا يوجد لديك مزادات ", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void exitApp(boolean shouldEraseAndLogin) {

    }

    @Override
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        if (Methods.getFromSharedPref(Constants.ACTV_CurrentAuctions, this) != null)
//            Methods.updateInSharedPref(Constants.ACTV_CurrentAuctions, "", this);
//        startActivity(intent);
    }
}
