package com.elkdeals.mobile.ui.account;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.adapters.BillsPayAdapter;
import com.elkdeals.mobile.api.models.InvoiceDetail;
import com.elkdeals.mobile.api.models.InvoicesModel;
import com.elkdeals.mobile.api.models.UserModel;
import com.elkdeals.mobile.dialogFragments.PaymenyDialog;
import com.elkdeals.mobile.ui.BaseFragment;
import com.elkdeals.mobile.interfaces.OnItemClickListener;
import com.elkdeals.mobile.model.Repository;
import com.elkdeals.mobile.viewmodel.UserViewModel;

import butterknife.BindView;
import butterknife.OnClick;

public class BillDetails extends BaseFragment {
    public static final String TAG = "BillDetails";
    //////////////////////////////////////////////////

    @BindView(R.id.address)
    public TextView addressTextView;
    @BindView(R.id.recycler)
    public RecyclerView recyclerView;
    @BindView(R.id.shipping_fees)
    public TextView shippingFees;
    @BindView(R.id.total_price)
    public TextView totalPrice;
    @BindView(R.id.sub_total_price)
    public TextView subTotal;
    @BindView(R.id.city)
    public TextView cityNameTextView;
    @BindView(R.id.method)
    public TextView method;
    /**/
    @BindView(R.id.discount)
    public TextView discountPrice;
    @BindView(R.id.bill_status)
    public TextView billStatus;
    @BindView(R.id.bank_account_number)
    public TextView bankAccNum;
    @BindView(R.id.bank_branch)
    public TextView bankBranch;
    @BindView(R.id.bank)
    public TextView bank;
    ///////////////////////////////////////
    private InvoicesModel invoice;
    private UserModel userinfo;
    private UserViewModel userModel;
    LinearLayoutManager manager;
    BillsPayAdapter adapter;

    public static BillDetails createInstance(InvoicesModel invoice, UserModel userinfo) {
        BillDetails details = new BillDetails();
        Bundle args = new Bundle();
        details.invoice = invoice;
        details.userinfo = userinfo;
        args.putParcelable("inv",invoice);
        args.putParcelable("user",userinfo);
        return details;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle data = getArguments();
        if (data == null)
            data = savedInstanceState;
        if (data == null)
            data = new Bundle();
        if(data.containsKey("inv"))
            invoice = data.getParcelable("inv");
        if(data.containsKey("user"))
            userinfo = data.getParcelable("user");
        // Inflate the layout for this fragment
        return view = inflater.inflate(R.layout.fragment_bill_details, container, false);
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public void initViews() {
        userModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userModel.getUserInvoiceDetails().observe(this, invoiceDetails -> adapter.addItems(invoiceDetails));

        if(invoice.getStatus()==0){
        billStatus.setText(R.string.paid);
            bankAccNum.setText(invoice.getAccountnumber() + "");
            bankBranch.setText(invoice.getBranch() + "");
            method.setText(invoice.getPtype() + "");
            bank.setText(invoice.getBkname() + "");
        }
        else {
            billStatus.setText(R.string.not_paid);
            bankAccNum.setText(R.string.not_paid);
            bankBranch.setText(R.string.not_paid);
            method.setText(R.string.not_paid);
            bank.setText(R.string.not_paid);
        }

        discountPrice.setText(invoice.getDiscount() + "");
        shippingFees.setText(invoice.getRate() + "");
        if(invoice.getTotal()-invoice.getRate()>0)
        subTotal.setText(invoice.getTotal()-invoice.getRate()+ "");
        else
            subTotal.setText("0");
        totalPrice.setText((invoice.getTotal()/*-invoice.getDiscount()+invoice.getRate()*/)+ "");
        addressTextView.setText(userinfo.getAddress() + "");
        cityNameTextView.setText(userinfo.getCityname() + "");
        manager = new LinearLayoutManager(activity);
        adapter = new BillsPayAdapter(new OnItemClickListener() {
            @Override
            public void OnItemClick(Object data) {
                super.OnItemClick(data);
                if(adapter.getItemCount()==1){
                    activity.showToast("لا يمكنك الحذف حيث انه يوجد مزاد واحد.");
                    return;
                }
                InvoiceDetail i= (InvoiceDetail) data;
                userModel.deleteUserInVoices(invoice.getId(),i.getInvoiceDeleteID());
            }
        });
        userModel.getProgress().observe(this, s -> {
            if (!TextUtils.isEmpty(s))
                activity.showProgressBar(s);
            else activity.hideDialog();
        });
        userModel.getMessage().observe(this, s ->
                {
                    if(s.contains("update"))
                    {
                        s=s.replace("update","");
                        activity.showMessageDialog(s);
                        userModel.getUserInvoices(invoice.getId()+"");
                    }
                    activity.showMessageDialog(s);
                });
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        userModel.getUserInvoices(invoice.getId()+"");
    }
    PaymenyDialog dialogFragment;
    @OnClick(R.id.bill_pay)
    public void pay(){
        try {
            if (dialogFragment != null) {
                if (dialogFragment.isVisible())
                    dialogFragment.dismiss();
                dialogFragment = null;
                dialogFragment = PaymenyDialog.CreatePaymentDialog(invoice, Repository.getUserInfo());
            } else {
                dialogFragment = PaymenyDialog.CreatePaymentDialog(invoice, Repository.getUserInfo());
            }
        } catch (Exception ignored) {
            dialogFragment = PaymenyDialog.CreatePaymentDialog(invoice, Repository.getUserInfo());
        }
        dialogFragment.show(activity.getSupportFragmentManager(), dialogFragment.getTAG());
    }
}
