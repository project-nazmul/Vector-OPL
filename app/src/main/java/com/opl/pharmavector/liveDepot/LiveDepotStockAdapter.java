package com.opl.pharmavector.liveDepot;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.opl.pharmavector.R;
import com.opl.pharmavector.master_code.model.MasterCList;
import com.opl.pharmavector.prescriptionsurvey.RxSumMISSelfList;

import java.util.List;

public class LiveDepotStockAdapter extends RecyclerView.Adapter<LiveDepotStockAdapter.LiveDepotViewHolder> {
    public List<LiveDepotStockList> depotStockLists;

    public LiveDepotStockAdapter(List<LiveDepotStockList> depotStockList) {
        this.depotStockLists = depotStockList;
    }

    @Override
    public LiveDepotViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_live_depot_row, viewGroup, false);
        return new LiveDepotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LiveDepotViewHolder holder, int position) {
        LiveDepotStockList depotStockList = depotStockLists.get(position);
        holder.serial.setText(String.valueOf(depotStockList.getSl()));
        holder.product_code.setText(depotStockList.getPCode());
        holder.product_name.setText(depotStockList.getPDesc());
        holder.pack_size.setText(depotStockList.getPackSize());
        holder.tp_vat.setText(depotStockList.getCommMrp());
        holder.stock_quantity.setText(depotStockList.getStockQnty());
        holder.stock_value.setText(depotStockList.getStockValue());
    }

    @Override
    public int getItemCount() {
        return depotStockLists.size();
    }

    public class LiveDepotViewHolder extends RecyclerView.ViewHolder {
        public TextView serial, product_code, product_name, pack_size, tp_vat, stock_quantity, stock_value;

        public LiveDepotViewHolder(View view) {
            super(view);
            serial = view.findViewById(R.id.serial);
            product_code = view.findViewById(R.id.product_code);
            product_name = view.findViewById(R.id.product_name);
            pack_size = view.findViewById(R.id.pack_size);
            tp_vat = view.findViewById(R.id.tp_vat);
            stock_quantity = view.findViewById(R.id.stock_quantity);
            stock_value = view.findViewById(R.id.stock_value);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void searchDepotProduct(List<LiveDepotStockList> stockLists) {
        depotStockLists = stockLists;
        notifyDataSetChanged();
    }
}