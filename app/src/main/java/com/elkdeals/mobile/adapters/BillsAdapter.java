package com.elkdeals.mobile.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.api.models.InvoicesModel;
import com.elkdeals.mobile.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BillsAdapter extends RecyclerView.Adapter<BillsAdapter.ViewHolder>  {

    private ArrayList<InvoicesModel> bills;
    private OnItemClickListener onItemClickListener,onRemoveClickLisetener;

    public BillsAdapter(OnItemClickListener onItemClickListener,OnItemClickListener onRemoveClickLisetener) {
        bills = new ArrayList<>();
        this.onItemClickListener = onItemClickListener;
        this.onRemoveClickLisetener= onRemoveClickLisetener;
    }

    public void addItems(List items) {
        bills.clear();
        if (items != null && items.size() > 0 && items.get(0) instanceof InvoicesModel) {
            bills.addAll(items);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.recycler_bill, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        InvoicesModel invoice = bills.get(i);
        viewHolder.discountText.setText(invoice.getDiscount() + "");
        viewHolder.invoiceDate.setText(invoice.getDate());
        viewHolder.invoiceNum.setText(invoice.getNumber() + "");
        viewHolder.invoiceDelivPrice.setText(invoice.getRate() + "");
        //viewHolder.productsTitle.setText(invoice.get() + "");
        viewHolder.total.setText(invoice.getTotal() + "");
//        if(invoice.getStatus()==0){
//            viewHolder.completeInvoiceBtn.setText(R.string.paid);
//            viewHolder.completeInvoiceBtn.setBackgroundResource(R.drawable.bill_paid);
//            viewHolder.invoiceStatus.setText(R.string.paid);
//            //viewHolder.completeInvoiceBtn.setBackgroundResource(R.string.paid);
//            viewHolder.billRemove.setVisibility(View.INVISIBLE);
//            return;
//        }else{
//            viewHolder.completeInvoiceBtn.setText(R.string.pay_now);
//            viewHolder.completeInvoiceBtn.setBackgroundResource(R.drawable.bill_pay);
//            viewHolder.invoiceStatus.setText(R.string.not_paid);
//            //viewHolder.completeInvoiceBtn.setBackgroundResource(R.string.paid);
//            viewHolder.billRemove.setVisibility(View.INVISIBLE);
//        }

            viewHolder.billRemove.setVisibility(View.INVISIBLE);

        if (invoice.getStatus() == 1) {
            viewHolder.invoiceStatus.setText("غير مدفوعة");
            viewHolder.completeInvoiceBtn.setText("ادفع الفاتورة");
//            viewHolder.deleteInvoiceBtn.setVisibility(View.VISIBLE);
            viewHolder.completeInvoiceBtn.setVisibility(View.VISIBLE);
        } else if (invoice.getStatus() == 2) {
            viewHolder.invoiceStatus.setText("الفاتورة مدفوعه");
//            viewHolder.deleteInvoiceBtn.setVisibility(View.GONE);
            viewHolder.completeInvoiceBtn.setVisibility(View.GONE);
        } else if (invoice.getStatus() == 3) {
            viewHolder.invoiceStatus.setText("تم الشحن");
//            viewHolder.deleteInvoiceBtn.setVisibility(View.GONE);
            viewHolder.completeInvoiceBtn.setVisibility(View.GONE);
        } else if (invoice.getStatus() == 4) {
            viewHolder.invoiceStatus.setText("تم التوصيل");
//            viewHolder.deleteInvoiceBtn.setVisibility(View.GONE);
            viewHolder.completeInvoiceBtn.setVisibility(View.GONE);
        } else if (invoice.getStatus() == 6) {
            viewHolder.invoiceStatus.setText("فى انتظار التاكيد");
//            viewHolder.deleteInvoiceBtn.setVisibility(View.GONE);
            viewHolder.completeInvoiceBtn.setVisibility(View.GONE);
        } else if (invoice.getStatus() == 5) {
            viewHolder.invoiceStatus.setText("تم الغاء الفاتورة");
//            viewHolder.deleteInvoiceBtn.setVisibility(View.GONE);
            viewHolder.completeInvoiceBtn.setVisibility(View.GONE);
        }else if (invoice.getStatus() == 8) {
            viewHolder.invoiceStatus.setText("فى انتظار الشحن");
//            viewHolder.deleteInvoiceBtn.setVisibility(View.GONE);
            viewHolder.completeInvoiceBtn.setVisibility(View.GONE);
        }else if (invoice.getStatus() == 10) {
            viewHolder.invoiceStatus.setText("مرتجع");
//            viewHolder.deleteInvoiceBtn.setVisibility(View.GONE);
            viewHolder.completeInvoiceBtn.setVisibility(View.GONE);
        }

        if(onItemClickListener!=null)
            viewHolder.completeInvoiceBtn.setOnClickListener(v -> onItemClickListener.OnItemClick(invoice));
        //if(onRemoveClickLisetener!=null)
        //    viewHolder.billRemove.setOnClickListener(v -> onRemoveClickLisetener.OnItemClick(invoice));

    }

    @Override
    public int getItemCount() {
        return bills.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.bill_pay)
        TextView completeInvoiceBtn;
        @BindView(R.id.bill_delivery)
        TextView invoiceDelivPrice;
        @BindView(R.id.bill_state)
        TextView invoiceStatus;
        @BindView(R.id.bill_discount)
        TextView discountText;
        @BindView(R.id.bill_total)
        TextView total;
        @BindView(R.id.bill_date)
        TextView invoiceDate;
        @BindView(R.id.bill_id)
        TextView invoiceNum;
        @BindView(R.id.product_title)
        TextView productsTitle;
        @BindView(R.id.bill_remove)
        View billRemove;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
