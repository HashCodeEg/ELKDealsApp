package com.elkdeals.mobile.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.Constants;
import com.elkdeals.mobile.Utils.Methods;
import com.elkdeals.mobile.adapters.MenuAdapter;
import com.elkdeals.mobile.api.API;
import com.elkdeals.mobile.api.Services;
import com.elkdeals.mobile.api.models.SendPriceModel;
import com.elkdeals.mobile.api.models.UserModel;
import com.elkdeals.mobile.custom.ProfileImageView;
import com.elkdeals.mobile.dialogFragments.EditInformationDialog;
import com.elkdeals.mobile.model.Repository;
import com.elkdeals.mobile.recievers.ConnectivityReceiver;
import com.elkdeals.mobile.recievers.MyBroadcastReceiver;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SendPricePopUp extends BaseActivity {

    @BindView(R.id.sentPriceTextView)
    TextView sentPriceTextView;
    @BindView(R.id.ss)
    TextView ss;
    @BindView(R.id.cashTxt)
    TextView cashTxt;
    @BindView(R.id.sentPriceBtn)
    Button sentPriceBtn;
    @BindView(R.id.infoImg)
    ImageView infoImg;
    Bundle extras;
    String userID;
    int cash = 0;
    int allPrice = 0;
    String bidx = "";

    public SendPricePopUp() {
    }
    MyBroadcastReceiver myBroadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        putContentView(R.layout.activity_send_price_pop_up);
        ButterKnife.bind(this);
        extras = getIntent().getExtras();
        if (extras != null) {
            sentPriceTextView.setText(extras.getString(Constants.KEY_POST_CURRENT));
            cash = extras.getInt("KASH");
            if (cash>0){
                allPrice = cash + new  Integer(extras.getString(Constants.KEY_POST_CURRENT));
                showToast("تحذير!\n كده هاتدفع  "+allPrice+" ج "+"قبل الاستلام");
                DialogFragment dialogFragment = new EditInformationDialog();
                dialogFragment.show(getSupportFragmentManager(), "CompleteYourDataDialog");
               /* cashTxt.setVisibility(View.VISIBLE);
                cashTxt.setText(""+"تحذير!\n المبلغ الكاش الذى ستقوم بدفعه قبل الاستلام هو "+cash+" ج ");*/
            }

            if (extras.getString("TYPE").equals("2"))
                ss.setText("العدد الذى سيتم ارساله");

            sentPriceTextView.setText(extras.getString(Constants.KEY_POST_CURRENT));


            final String current = extras.getString(Constants.KEY_POST_CURRENT);
            if (extras.getString("TryBid") != null) {
                bidx = "1";
            }
            userID = Repository.getUserInfo().getId();
            final String postID = extras.getString(Constants.KEY_POST_ID);
            final Integer val = Integer.valueOf(current);
            Intent data = new Intent();
            data.putExtra("price2", "p2");
            setResult(RESULT_OK, data);
            sentPriceBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ConnectivityReceiver.isConnected()) {
                        if (bidx.equals("1")){
                            sendPrice2(postID, userID, val + "");
                            Intent data = new Intent();
                            data.putExtra("price", "p1");
                            setResult(RESULT_OK, data);
                            SendPricePopUp.this.finish();
                        }else{
                            sendPrice(postID, userID, val + "");
                            Intent data = new Intent();
                            data.putExtra("price", "p1");
                            setResult(RESULT_OK, data);
                            SendPricePopUp.this.finish();
                        }

                    } else {
                        Toast.makeText(SendPricePopUp.this, getResources().getString(R.string.no_internet_connection) + "", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
        Methods.setAppFont();
        //new RestClient(this);
        //myBroadcastReceiver = new MyBroadcastReceiver();
        //Methods.getMessageFromBroadCast(this, myBroadcastReceiver);
        //String serverMessage = Methods.getFromSharedPref(Keys.ACTV_Home, this);
        //Log.d("NAAAAMES", serverMessage + " --- " + Keys.ACTV_Home);
    }

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
        navigationView.setBackground(getResources().getDrawable(R.drawable.ic_nav_menu_requests));
        UserModel userModel = Repository.getUserInfo();
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

    @Override
    public ArrayList<MenuAdapter.MenuItem> getItems() {
        return getAuctionsMenuItems();
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            unregisterReceiver(myBroadcastReceiver);
        } catch (IllegalArgumentException e) {
        }
        hideDialog();
    }


    @Override
    public void exitApp(boolean shouldEraseAndLogin) {

    }

    public void sendPrice(final String postID, final String userID, final String val) {
        showProgressBar();
        Services api = API.getAPIS();
        Call<SendPriceModel> call = api.sendYourPrice(postID,userID,val);
        call.enqueue(new Callback<SendPriceModel>() {
            @Override
            public void onResponse(Call<SendPriceModel> call, Response<SendPriceModel> response) {
                hideDialog();
                if (!response.isSuccessful()){
                    Log.d("FRFRFR","FFFFAl 1");
                }
                if (response.isSuccessful()){
                    Log.d("FRFRFR", postID + "---" + userID + "-----" + val);
                    checkMsg(response.body().getMsg(),response.body().getName());
                }
            }

            @Override
            public void onFailure(Call<SendPriceModel> call, Throwable t) {
                Log.d("FRFRFR","FFFFAl 2");
                hideDialog();
            }
        });
    }

    public void sendPrice2(final String postID, final String userID, final String val) {
        showProgressBar();
        Services api = API.getAPIS();
        Call<SendPriceModel> call = api.sendYourPrice2(postID, userID, val);
        call.enqueue(new Callback<SendPriceModel>() {
            @Override
            public void onResponse(Call<SendPriceModel> call, Response<SendPriceModel> response) {
                if (!response.isSuccessful()) {
                    hideDialog();
                }
                if (response.isSuccessful()) {
                    hideDialog();
                    Log.d("FRFRFR", postID + "---" + userID + "-----" + val);
                    Toast.makeText(SendPricePopUp.this,"تم ارسال السعر", Toast.LENGTH_SHORT).show();
                    Gson gson = new Gson();
                    String ff = gson.toJson(response.body());
                    Log.d("FRFRFR", ff);

                }
            }

            @Override
            public void onFailure(Call<SendPriceModel> call, Throwable t) {
                hideDialog();
            }
        });
    }

    /*
    *



    * */
    private void checkMsg(String msg,String name){
        if (msg.equals("DONE"))
            TOAST("تم ارسال السعر");
        else if (msg.equals("NO_BID"))
            TOAST("لا يوجد لديك عدد مزايدات");
        else if (msg.equals("FINISH"))
            TOAST("انتهى المزاد");
        else if (msg.equals("ANOTHER"))
            TOAST("تم المزايدة من قبل "+ name);

    }

    private void TOAST(String msg){
        Toast.makeText(this,msg+ "", Toast.LENGTH_LONG).show();
    }

}
