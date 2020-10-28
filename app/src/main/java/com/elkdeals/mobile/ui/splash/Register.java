package com.elkdeals.mobile.ui.splash;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.Utils;
import com.elkdeals.mobile.dialogFragments.ConfirmationDialog;
import com.elkdeals.mobile.ui.BaseFragment;
import com.elkdeals.mobile.viewmodel.RegisterViewModel;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class Register extends BaseFragment {

    public static final String TAG = "Register";
    @BindView(R.id.mobile)
    public EditText mobile;
    @BindView(R.id.name)
    public EditText name;
//    @BindView(R.id.password)
//    public EditText password;
    @BindView(R.id.email)
    public EditText email;

//    @BindView(R.id.toggle)
//    ImageView toggle;
    public String phone = "";
    DialogFragment dialog;
    RegisterViewModel model;

    public Register() {
        // Required empty public constructor
    }

    public static BaseFragment createInstance(String phone) {
        Register register = new Register();
        register.phone = phone;
        return register;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return view = inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void initViews() {
        if (getActivity() != null)
            model = ViewModelProviders.of(getActivity()).get(RegisterViewModel.class);
        else model = ViewModelProviders.of(activity).get(RegisterViewModel.class);
        model.getProgress().observe(this, s -> {
            if (!TextUtils.isEmpty(s))
                activity.showProgressBar(s);
            else activity.hideDialog();
        });
        model.getMessage().observe(this, s -> activity.showMessageDialog(s));
        model.getUser().observe(this, mobileModel -> {
            if (mobileModel != null && mobileModel.getStatus())
                if (mobileModel.getStatus())
                    ((Splash) getActivity()).confirmPhone();
                else {
                   activity.home();
                }
        });
        mobile.setText(phone);

//        ShowPasswordCheckBoxListener(toggle);
    }

//    boolean visible=true;
//    @OnClick(R.id.toggle)
//    public void ShowPasswordCheckBoxListener(View view) {
//        if(visible) {
//            toggle.setImageResource(R.drawable.ic_visibility_off_black_24dp);
//            password.setTransformationMethod(new PasswordTransformationMethod());  //hide the password from the edit text
//        }
//        else{
//            toggle.setImageResource(R.drawable.ic_visibility_black_24dp);
//            password.setTransformationMethod(null); //show the password from the edit text
//        }
//        visible=!visible;
//    }
    @OnClick(R.id.register)
    public void confirmPhone() {
        String phoneNumber = mobile.getText().toString();
        if (mobile == null || mobile.length() < 10) {
            activity.showToast(Utils.getStringRes(R.string.enter_valid_phone));
            return;
        }
        if (!Utils.isValidEmail(email.getText().toString())) {
            activity.showToast(Utils.getStringRes(R.string.enter_valid_email));
            return;
        }
        if (name == null || name.length() < 2) {
            activity.showToast(Utils.getStringRes(R.string.enter_name));
            return;
        }
        if (dialog != null && dialog.isVisible())
            dialog.dismiss();
        dialog = new ConfirmationDialog();
        ((ConfirmationDialog) dialog).setTitle(R.string.number_confirmation);
        ((ConfirmationDialog) dialog).setMessage(Utils.getStringRes(R.string.your_number) + " " + phoneNumber);
        ((ConfirmationDialog) dialog).setOnPositiveClick(Utils.getStringRes(R.string.confirm), v -> {
            if (dialog != null && dialog.isVisible())
                dialog.dismiss();
            model.register(phoneNumber,"123456"/*password.getText().toString()*/, name.getText().toString(), email.getText().toString(), Utils.getImei(),"");
        });
        ((ConfirmationDialog) dialog).setOnNegativeClick(Utils.getStringRes(R.string.edit), v -> {
            if (dialog != null && dialog.isVisible())
                dialog.dismiss();
        });
        dialog.show(activity.getSupportFragmentManager(), ((ConfirmationDialog) dialog).getTAG());
    }



    @Override
    public String getTAG() {
        return TAG;
    }
}
/*
er-key = ab2e8fc9f7495f99999cd977fbb53430
: consumer-secret = 2dca7424442cd93a7b2d9d5dd61dd86d
: consumer-nonce = drvp84v5h-07nxz6vxk1t5k20wxj-esc
: consumer-device-id = da4wJ-ACGsM:APA91bGZqGNEIZUH2I_r_84MpcnQyFmZbv8y-qO-CzupcEqmR4S71c_ofkLERTTXU1ihIfPBsgi5kFzrtbRS9Ova4P7IBycZQO96D3_5Oui4sQIHrg0wIMy72D4tgayXlQL2cfD6QUWQ
 --> POST http://10.0.2.2/elkdeals/index.php/app/addtoorder
 Content-Type: application/json; charset=UTF-8
 Content-Length: 1238
 consumer-key: ab2e8fc9f7495f99999cd977fbb53430
 consumer-secret: 2dca7424442cd93a7b2d9d5dd61dd86d
 consumer-nonce: drvp84v5h-07nxz6vxk1t5k20wxj-esc
 consumer-device-id: da4wJ-ACGsM:APA91bGZqGNEIZUH2I_r_84MpcnQyFmZbv8y-qO-CzupcEqmR4S71c_ofkLERTTXU1ihIfPBsgi5kFzrtbRS9Ova4P7IBycZQO96D3_5Oui4sQIHrg0wIMy72D4tgayXlQL2cfD6QUWQ
 {"billing_city":"asas","billing_country":"Egypt","billing_country_id":"63","billing_firstname":"islam","billing_lastname":"assem","billing_postcode":"","billing_street_address":"aaaa","billing_zone_id":"0","billing_phone":"010156056056","comments":"","coupon_amount":0.0,"coupons":[],"email":"","customers_id":2,"customers_name":"","customers_telephone":"010156056056","delivery_city":"asas","delivery_country":"Egypt","delivery_country_id":"63","delivery_firstname":"islam","delivery_lastname":"assem","delivery_postcode":"","delivery_street_address":"aaaa","delivery_zone_id":"0","delivery_phone":"010156056056","is_coupon_applied":0,"language_id":4,"nonce":"","payment_method":"freeShipping","products":[{"attributes":[],"categories_id":0,"customers_basket_quantity":1,"final_price":"468.0","image":"resources/assets/images/product_images/1502261147.pPOLO2-25480910_alternate2_v360x480.jpg","model":"","on_sale":false,"price":"468.0","products_id":34,"products_name":"صغير، سوليفان، أزعج، باغ","subtotal":"468.0","total":"468.0","unit":"Kilogram","weight":"0.500"}],"productsTotal":468.0,"shipping_cost":0.0,"shipping_method":"الدفع عند الاستلام","tax_zone_id":0,"totalPrice":468.0,"total_tax":0.0}
 --> END POST (1238-byte body)
 <-- 500 Internal Server Error http://10.0.2.2/elkdeals/index.php/app/addtoorder (38ms)
*/