package com.opl.pharmavector.saleReport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.opl.pharmavector.R;

import java.util.List;

public class GrpOrdSumDetailAdapter extends RecyclerView.Adapter<GrpOrdSumDetailAdapter.GrpOrdSumDetailViewHolder> {
    public Context context;
    public ItemClickListener itemClickListener;
    public List<GroupOrderSumDetailList> orderSumDetailLists;

    public GrpOrdSumDetailAdapter(Context context, List<GroupOrderSumDetailList> orderSumDetailList, ItemClickListener itemClickListener) {
        this.context = context;
        this.orderSumDetailLists = orderSumDetailList;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public GrpOrdSumDetailViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.group_ord_detail_raw, viewGroup, false);
        return new GrpOrdSumDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GrpOrdSumDetailViewHolder holder, int position) {
        GroupOrderSumDetailList ordSumDetailsModel = orderSumDetailLists.get(position);
        holder.grpOrdSerial.setText(String.valueOf(ordSumDetailsModel.getSl()));
        holder.grpOrdFfCode.setText(ordSumDetailsModel.getFfCode());
        holder.grpOrdFfName.setText(ordSumDetailsModel.getFfName());
        holder.grpOrdQuantity.setText(ordSumDetailsModel.getOrdQnty());
        holder.grpOrderValue.setText(ordSumDetailsModel.getOrdValue());

        holder.itemView.setOnClickListener(view -> {
            itemClickListener.onClick(position, ordSumDetailsModel);
        });
    }

    @Override
    public int getItemCount() {
        return orderSumDetailLists.size();
    }

    public class GrpOrdSumDetailViewHolder extends RecyclerView.ViewHolder {
        public TextView grpOrdSerial, grpOrdFfCode, grpOrdFfName, grpOrdProdGroup, grpOrdPackSize, grpOrdQuantity, grpOrderValue;

        public GrpOrdSumDetailViewHolder(View view) {
            super(view);
            grpOrdSerial = view.findViewById(R.id.grpOrdSerial);
            grpOrdFfCode = view.findViewById(R.id.grpOrdFfCode);
            grpOrdFfName = view.findViewById(R.id.grpOrdFfName);
            grpOrdQuantity = view.findViewById(R.id.grpOrdQuantity);
            grpOrderValue = view.findViewById(R.id.grpOrderValue);
        }
    }

    public interface ItemClickListener {
        void onClick(int position, GroupOrderSumDetailList orderModel);
    }
}
