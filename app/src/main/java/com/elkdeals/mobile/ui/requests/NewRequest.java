package com.elkdeals.mobile.ui.requests;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.api.models.category_model.CategoryDetails;
import com.elkdeals.mobile.api.models.requests.AddRequestData;
import com.elkdeals.mobile.api.models.requests.RequestDetails;
import com.elkdeals.mobile.ui.BaseFragment;
import com.elkdeals.mobile.model.RepositorySidalitac;
import com.elkdeals.mobile.viewmodel.RequestsViewModelSidalitac;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewRequest extends BaseFragment {
    public static final String TAG = "new Request";
    @BindView(R.id.check_new)
    CheckBox newCheck;
    @BindView(R.id.check_old)
    CheckBox usedCheck;
    @BindView(R.id.title)
    EditText title;
    @BindView(R.id.description)
    EditText description;
    @BindView(R.id.request_category)
    TextView request_category;
    RequestsViewModelSidalitac requestsViewModelSidalitac;
    CategoryDetails categoryDetails;
    RequestDetails r;
    public NewRequest() {
        // Required empty public constructor
    }

    public static BaseFragment createInstance(CategoryDetails categoryDetails) {
        NewRequest newRequest=new NewRequest();
        newRequest.categoryDetails=categoryDetails;
        return newRequest;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return view = inflater.inflate(R.layout.fragment_new_request, container, false);
    }

    @Override
    public void initViews() {
        requestsViewModelSidalitac= ViewModelProviders.of(this).get(RequestsViewModelSidalitac.class);
        requestsViewModelSidalitac.getProgress().observe(this, s -> {
            if (!TextUtils.isEmpty(s))
                activity.showProgressBar(s);
            else activity.hideDialog();
        });
        requestsViewModelSidalitac.getMessage().observe(this, s -> activity.showMessageDialog(s));
        requestsViewModelSidalitac.getAddrequest().observe(this, new Observer<AddRequestData>() {
            @Override
            public void onChanged(AddRequestData addRequestData) {
                r=addRequestData.getData();
            }
        });
        request_category.setText(categoryDetails.getName());

    }

    @OnClick({R.id.request})
    public void showRequests() {
        if(!newCheck.isChecked()&&!usedCheck.isChecked())
        {
            activity.showMessageDialog(R.string.select_status_new_old);
            return;
        }
        if(TextUtils.isEmpty(title.getText().toString()))
        {
            activity.showMessageDialog(R.string.enter_title);
            return;
        }
        if(TextUtils.isEmpty(description.getText().toString()))
        {
            activity.showMessageDialog(R.string.enter_description);
            return;
        }
        String customerID= RepositorySidalitac.getUserInfo().getData().get(0).getCustomersId();
        String titlee=title.getText().toString();
        String descriptionn=description.getText().toString();
        String newStatus="0";
        String oldStatus="0";
        if(newCheck.isChecked())
            newStatus="1";
        if(usedCheck.isChecked())
            oldStatus="1";

        requestsViewModelSidalitac.addRequest(customerID,categoryDetails.getId(),titlee,descriptionn,newStatus,oldStatus);
    }

    @OnClick({R.id.offers})
    public void showOffers() {
        if(r==null)
        activity.addPage(MyRequests.createInstance(RepositorySidalitac.getUserInfo().getData().get(0).getCustomersId()));
        else activity.addPage(Offers.createInstance(r));
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
