package com.elkdeals.mobile.dialogFragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.Utils.Utils;
import com.elkdeals.mobile.activities.BaseActivity;
import com.elkdeals.mobile.api.API;
import com.elkdeals.mobile.api.models.CitiesModel;
import com.elkdeals.mobile.api.models.UserModel;
import com.elkdeals.mobile.interfaces.HasTag;
import com.elkdeals.mobile.interfaces.OnItemClickListener;
import com.elkdeals.mobile.model.Repository;
import com.elkdeals.mobile.recievers.ConnectivityReceiver;
import com.elkdeals.mobile.viewmodel.RegisterViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditInformationDialog extends DialogFragment implements HasTag {
    public static final String TAG = "EditInformationDialog";
    public static final int REQUEST_PHONE_CALL = 100;
    @BindView(R.id.name)
    EditText nameTextView;
    @BindView(R.id.email)
    EditText emailTextView;
    @BindView(R.id.mobile)
    EditText mobileTextView;
    @BindView(R.id.id_number)
    EditText natIDTextView;
    @BindView(R.id.city)
    EditText city;
    @BindView(R.id.address)
    EditText address;
    private View view;
    private RegisterViewModel registerViewModel;
    private UserModel userInfo;
    private ArrayAdapter<String> cityAdapter;
    private List<CitiesModel> cityList;
    private List<String> cityNames;
    private String selectedCountryID;
    OnItemClickListener onCompleteClickListener;
    public EditInformationDialog() {
    }

    public static EditInformationDialog createInstance(OnItemClickListener onCompleteClickListener) {
        EditInformationDialog dialog=new EditInformationDialog();
        dialog.onCompleteClickListener=onCompleteClickListener;
        return dialog;
    }

    @Nullable
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.customdialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window == null) return;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams windowParams = window.getAttributes();
        //windowParams.dimAmount = 0.00f;
        //windowParams.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(windowParams);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_edit_information, container, false);
        try {
            if (getDialog().getWindow() != null) {
                getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            }
        } catch (Exception ignored) {
        }
        initViews();
        return view;
    }


    @SuppressLint("ClickableViewAccessibility")
    private void initViews() {
        ButterKnife.bind(this, view);
        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
        registerViewModel.getProgress().observe(this, s -> {
            if(! (getActivity() instanceof BaseActivity))
                return;
            if (!TextUtils.isEmpty(s))
                ((BaseActivity)getActivity()).showProgressBar(s);
            else ((BaseActivity)getActivity()).hideDialog();
        });
        registerViewModel.getSuccess().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    if(onCompleteClickListener!=null)
                        onCompleteClickListener.OnItemClick(null);
                    dismiss();
                }
            }
        });
        registerViewModel.getMessage().observe(this, s ->{
        if(! (getActivity() instanceof BaseActivity))
            return;
            ((BaseActivity)getActivity()).showMessageDialog(s);
        });
        userInfo= Repository.getUserInfo();
        this.nameTextView.setText(userInfo.getName());
        this.city.setText(userInfo.getCityname());
        this.emailTextView.setText(userInfo.getEmail());
        this.address.setText(userInfo.getAddress());
        this.natIDTextView.setText(userInfo.getNationalid());
        this.mobileTextView.setText(userInfo.getMobile());
        this.nameTextView.setEnabled(false);
        this.mobileTextView.setEnabled(false);
        this.city.setEnabled(true);
        this.address.setEnabled(true);
        this.natIDTextView.setEnabled(false);
        RequestCities();
        // Handle Touch event of input_country EditText
        city.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if(cityNames==null||cityList==null)
                    {
                        ((BaseActivity)getActivity()).showToast(R.string.cities_not_loaded_yet);
                        RequestCities();
                    }
                    cityAdapter = new ArrayAdapter<String>(getContext(), R.layout.recycler_textview);
                    cityAdapter.addAll(cityNames);

                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                    View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_list_search, null);
                    dialog.setView(dialogView);
                    dialog.setCancelable(false);

                    Button dialog_button = dialogView.findViewById(R.id.dialog_button);
                    EditText dialog_input = dialogView.findViewById(R.id.dialog_input);
                    TextView dialog_title = dialogView.findViewById(R.id.dialog_title);
                    ListView dialog_list = dialogView.findViewById(R.id.dialog_list);

                    dialog_title.setText(getString(R.string.country));
                    dialog_list.setVerticalScrollBarEnabled(true);
                    dialog_list.setAdapter(cityAdapter);

                    dialog_input.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                        @Override
                        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                            // Filter CountryAdapter
                            cityAdapter.getFilter().filter(charSequence);
                        }
                        @Override
                        public void afterTextChanged(Editable s) {}
                    });


                    final AlertDialog alertDialog = dialog.create();

                    dialog_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });

                    alertDialog.show();



                    dialog_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            alertDialog.dismiss();
                            final String selectedItem = cityAdapter.getItem(position);

                            String countryID = "";
                            city.setText(selectedItem);
                            for (int i=0;  i<cityList.size();  i++) {
                                if (cityList.get(i).getName().equalsIgnoreCase(selectedItem)) {
                                    // Get the ID of selected Country
                                    countryID = cityList.get(i).getId();
                                    break;
                                }
                            }
                            selectedCountryID = countryID;
                        }
                    });
                }

                return false;
            }
        });

    }
    public void RequestCities() {

        Call<List<CitiesModel>>citiesModelCall= API.getAPIS().getEgyptCites();
        citiesModelCall.enqueue(new Callback<List<CitiesModel>>() {
            @Override
            public void onResponse(Call<List<CitiesModel>> call, Response<List<CitiesModel>> response) {
                if (!response.isSuccessful()) {
                }
                if (response.isSuccessful()&&response.body()!=null) {
                    cityList=new ArrayList<>();
                    cityNames=new ArrayList<>();
                    for (int i = 0; i < response.body().size(); i++) {
                        cityList.add(response.body().get(i));
                        cityNames.add(cityList.get(i).getName());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CitiesModel>> call, Throwable t) {

            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PHONE_CALL) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+19157"));

                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    startActivity(intent);
                }
            }
        }
    }

    @OnClick(R.id.dialog_container)
    @Override
    public void dismiss() {
        super.dismiss();
    }

    @OnClick(R.id.save)
    public void save() {
        /*
        String name=this.nameTextView.getEditableText().toString();
        String city=this.city.getEditableText().toString();
        String email=this.emailTextView.getEditableText().toString();
        String address=this.address.getEditableText().toString();
        String address=this.address.getEditableText().toString();*/
        boolean shouldReturn = false;
        if (TextUtils.isEmpty(nameTextView.getText())){
            nameTextView.setError(Utils.getStringRes(R.string.enter_name));
            shouldReturn = true;
        }
        if (TextUtils.isEmpty(emailTextView.getText())){
            emailTextView.setError(Utils.getStringRes(R.string.enter_email));
            shouldReturn = true;
        }
        if (TextUtils.isEmpty(natIDTextView.getText())){
            natIDTextView.setError(Utils.getStringRes(R.string.enter_nat_id));
            shouldReturn = true;
        }
        if (!TextUtils.isEmpty(natIDTextView.getText())&&natIDTextView.getText().toString().length()!=14){
            natIDTextView.setError(Utils.getStringRes(R.string.enter_valid_nat_id));
            shouldReturn = true;
        }
        if (TextUtils.isEmpty(mobileTextView.getText())){
            mobileTextView.setError(Utils.getStringRes(R.string.enter_phone));
            shouldReturn = true;
        }
        if (TextUtils.isEmpty(address.getText())){
            address.setError(Utils.getStringRes(R.string.enter_address));
            shouldReturn = true;
        }
        if(shouldReturn)
            return;
        if (ConnectivityReceiver.isConnected()) {
            userInfo.setName(nameTextView.getEditableText().toString());
            userInfo.setEmail(emailTextView.getEditableText().toString());
            userInfo.setAddress(address.getEditableText().toString());
            if(TextUtils.isEmpty(selectedCountryID))
                selectedCountryID=userInfo.getCityid();
            Log.e(TAG,"selectedCountryID : "+selectedCountryID);
            userInfo.setCityid(selectedCountryID);
            userInfo.setNationalid(natIDTextView.getText().toString());
            userInfo.setMobile(mobileTextView.getText().toString());
            userInfo.setAddress(address.getText().toString());
            registerViewModel.updateUserInfo(userInfo);
        } else {
            ((BaseActivity)getActivity()).showMessageDialog(R.string.no_internet_connection);
        }
    }
    @Override
    public String getTAG() {
        return TAG;
    }

}
