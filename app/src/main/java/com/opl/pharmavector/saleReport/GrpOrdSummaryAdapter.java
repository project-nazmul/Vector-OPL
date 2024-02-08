package com.opl.pharmavector.saleReport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.opl.pharmavector.R;

import java.util.List;

public class GrpOrdSummaryAdapter extends RecyclerView.Adapter<GrpOrdSummaryAdapter.GrpOrdSummaryViewHolder> {
    public Context context;
    public ItemClickListener itemClickListener;
    public List<GroupOrderSummaryList> orderSummaryLists;

    public GrpOrdSummaryAdapter(Context context, List<GroupOrderSummaryList> orderSummaryList, ItemClickListener itemClickListener) {
        this.context = context;
        this.orderSummaryLists = orderSummaryList;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public GrpOrdSummaryViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.group_ord_summary_raw, viewGroup, false);
        return new GrpOrdSummaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GrpOrdSummaryViewHolder holder, int position) {
        GroupOrderSummaryList orderSummaryModel = orderSummaryLists.get(position);
        holder.grpOrdSerial.setText(String.valueOf(orderSummaryModel.getSl()));
        holder.grpOrdProdCode.setText(orderSummaryModel.getPCode());
        holder.grpOrdProdName.setText(orderSummaryModel.getPDesc());
        holder.grpOrdProdGroup.setText(orderSummaryModel.getProdGrpDesc());
        holder.grpOrdPackSize.setText(orderSummaryModel.getPackSize());
        holder.grpOrdQuantity.setText(orderSummaryModel.getOrdQnty());
        holder.grpOrderValue.setText(orderSummaryModel.getOrdValue());

        holder.itemView.setOnClickListener(view -> {
            itemClickListener.onClick(position, orderSummaryModel);
        });
    }

    @Override
    public int getItemCount() {
        return orderSummaryLists.size();
    }

    public class GrpOrdSummaryViewHolder extends RecyclerView.ViewHolder {
        public TextView grpOrdSerial, grpOrdProdCode, grpOrdProdName, grpOrdProdGroup, grpOrdPackSize, grpOrdQuantity, grpOrderValue;

        public GrpOrdSummaryViewHolder(View view) {
            super(view);
            grpOrdSerial = view.findViewById(R.id.grpOrdSerial);
            grpOrdProdCode = view.findViewById(R.id.grpOrdProdCode);
            grpOrdProdName = view.findViewById(R.id.grpOrdProdName);
            grpOrdProdGroup = view.findViewById(R.id.grpOrdProdGroup);
            grpOrdPackSize = view.findViewById(R.id.grpOrdPackSize);
            grpOrdQuantity = view.findViewById(R.id.grpOrdQuantity);
            grpOrderValue = view.findViewById(R.id.grpOrderValue);
        }
    }

    public interface ItemClickListener {
        void onClick(int position, GroupOrderSummaryList orderModel);
    }
}
