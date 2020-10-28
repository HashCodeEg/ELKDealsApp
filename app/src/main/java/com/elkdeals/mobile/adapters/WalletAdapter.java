package com.elkdeals.mobile.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.elkdeals.mobile.R;
import com.elkdeals.mobile.api.models.HistoryM;
import com.elkdeals.mobile.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.ViewHolder>{

    public static final int VIEW_HOLDER_TRANSACTION = 100;
    public static final int VIEW_HOLDER_VIEW_LESS = 101;
    OnItemClickListener onItemClickListener, onViewLessClickListener;
    private ArrayList<HistoryM> transactions;

    public WalletAdapter(OnItemClickListener onItemClickListener) {
        transactions = new ArrayList<>();
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnViewLessClickListener(OnItemClickListener onViewLessClickListener) {
        this.onViewLessClickListener = onViewLessClickListener;
    }

    public void addItems(List<HistoryM> items) {
        if(transactions==null)
            transactions=new ArrayList<>();
        transactions.clear();
        if (items != null && items.size() > 0 && items.get(0) instanceof HistoryM)
            transactions.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return position == getItemCount() -1? VIEW_HOLDER_VIEW_LESS : VIEW_HOLDER_TRANSACTION;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        int res = R.layout.recycler_wallet;
        if (viewType == VIEW_HOLDER_VIEW_LESS)
            res = R.layout.recycler_view_less;
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(res, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (viewHolder.getItemViewType() == VIEW_HOLDER_VIEW_LESS && onViewLessClickListener != null) {
            if (viewHolder.viewLess != null) {
                viewHolder.viewLess.setText(R.string.view_less);
            }
            viewHolder.itemView.setOnClickListener(v -> onViewLessClickListener.OnItemClick(null));
        }
        else if(viewHolder.getItemViewType()==VIEW_HOLDER_TRANSACTION){
            viewHolder.date.setText(transactions.get(i).getDate()+"");
            viewHolder.transaction_number.setText(transactions.get(i).getTransnumber()+"");
            viewHolder.note.setText(transactions.get(i).getNotes()+"");
            if(transactions.get(i).getCredit()!=0){
                viewHolder.credit.setText("-"+transactions.get(i).getCredit()+"");
                viewHolder.credit.setVisibility(View.VISIBLE);
            }
            if(transactions.get(i).getDibet()!=0){
                viewHolder.debit.setText("+"+transactions.get(i).getDibet()+"");
                viewHolder.debit.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return transactions==null||transactions.size()==0?0:transactions.size()+1;//notifications.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        @BindView(R.id.view_more_button)
        public TextView viewLess;
        @Nullable
        @BindView(R.id.notes)
        public TextView note;
        @Nullable
        @BindView(R.id.date)
        public TextView date;
        @Nullable
        @BindView(R.id.transaction_number)
        public TextView transaction_number;
        @Nullable
        @BindView(R.id.transaction_credit)
        public TextView credit;
        @Nullable
        @BindView(R.id.transaction_debit)
        public TextView debit;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
