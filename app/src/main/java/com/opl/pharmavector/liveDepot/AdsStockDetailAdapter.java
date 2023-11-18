package com.opl.pharmavector.liveDepot;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.opl.pharmavector.R;

import java.util.List;

public class AdsStockDetailAdapter extends RecyclerView.Adapter<AdsStockDetailAdapter.AdsStockDetailVHolder> {
    public List<AdsStockDetailsList> adsStocksInfoLists;

    public AdsStockDetailAdapter(List<AdsStockDetailsList> adsStocksInfoList) {
        this.adsStocksInfoLists = adsStocksInfoList;
    }

    @Override
    public AdsStockDetailVHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_ads_detail_row, viewGroup, false);
        return new AdsStockDetailVHolder(view);
    }

    @Override
    public void onBindViewHolder(AdsStockDetailVHolder holder, int position) {
        AdsStockDetailsList adsStocksList = adsStocksInfoLists.get(position);
        holder.depot.setText(String.valueOf(adsStocksList.getDepotDesc()));
        holder.stock.setText(adsStocksList.getDepotQnty());
        holder.csTrans.setText(adsStocksList.getTrnsQnty());
        holder.depotTrans.setText(adsStocksList.getDepotToDepotTrns());
        int stock = Integer.parseInt(adsStocksList.getDepotQnty());
        int csTrans = Integer.parseInt(adsStocksList.getTrnsQnty());
        int depotTrans = Integer.parseInt(adsStocksList.getDepotToDepotTrns());
        int total = stock + csTrans + depotTrans;
        holder.total.setText(String.valueOf(total));
        holder.sale.setText(adsStocksList.getCrMonSale());
    }

    @Override
    public int getItemCount() {
        return adsStocksInfoLists.size();
    }

    public class AdsStockDetailVHolder extends RecyclerView.ViewHolder {
        public TextView depot, stock, csTrans, depotTrans, total, sale;

        public AdsStockDetailVHolder(View view) {
            super(view);
            depot = view.findViewById(R.id.depot);
            stock = view.findViewById(R.id.stock);
            csTrans = view.findViewById(R.id.csTrans);
            depotTrans = view.findViewById(R.id.depotTrans);
            total = view.findViewById(R.id.total);
            sale = view.findViewById(R.id.sale);
        }
    }
}
