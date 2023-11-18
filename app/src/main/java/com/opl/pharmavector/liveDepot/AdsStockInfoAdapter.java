package com.opl.pharmavector.liveDepot;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.opl.pharmavector.R;
import com.opl.pharmavector.dcfpFollowup.DcfpDoctorReportList;

import java.util.List;

public class AdsStockInfoAdapter extends RecyclerView.Adapter<AdsStockInfoAdapter.AdsStockInfoVHolder> {
    public  AdsStockListener adsStockListeners;
    public List<AdsStocksInfoList> adsStocksInfoLists;

    public AdsStockInfoAdapter(List<AdsStocksInfoList> adsStocksInfoList, AdsStockListener adsStockListener) {
        this.adsStockListeners = adsStockListener;
        this.adsStocksInfoLists = adsStocksInfoList;
    }

    @Override
    public AdsStockInfoVHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_ads_stock_row, viewGroup, false);
        return new AdsStockInfoVHolder(view);
    }

    @Override
    public void onBindViewHolder(AdsStockInfoVHolder holder, int position) {
        AdsStocksInfoList adsStocksList = adsStocksInfoLists.get(position);
        holder.productName.setText(String.valueOf(adsStocksList.getPDesc()));
        holder.packSize.setText(adsStocksList.getPackSize());
        holder.freeStock.setText(adsStocksList.getTotFreeStkQnty());
        holder.depotStock.setText(adsStocksList.getDepoStk());
        holder.csStock.setText(adsStocksList.getBslStkOnly());
        holder.transit.setText(adsStocksList.getBslFgsTrnsOnly());
        holder.fgsProd.setText(adsStocksList.getFgsStk());
        holder.curTarget.setText(adsStocksList.getTarQnty3());
        holder.curMonSale.setText(adsStocksList.getSaleQnty3());
        holder.preMonSale.setText(adsStocksList.getSaleQnty0());
        holder.pre2MonSale.setText(adsStocksList.getSaleQnty1());

        holder.itemView.setOnClickListener(v -> {
            adsStockListeners.onAdsStockClick(position, adsStocksList);
        });
    }

    @Override
    public int getItemCount() {
        return adsStocksInfoLists.size();
    }

    public class AdsStockInfoVHolder extends RecyclerView.ViewHolder {
        public TextView productName, packSize, freeStock, depotStock, csStock, transit, fgsProd, curTarget, curMonSale, preMonSale, pre2MonSale;

        public AdsStockInfoVHolder(View view) {
            super(view);
            productName = view.findViewById(R.id.productName);
            packSize = view.findViewById(R.id.packSize);
            freeStock = view.findViewById(R.id.freeStock);
            depotStock = view.findViewById(R.id.depotStock);
            csStock = view.findViewById(R.id.csStock);
            transit = view.findViewById(R.id.transit);
            fgsProd = view.findViewById(R.id.fgsProd);
            curTarget = view.findViewById(R.id.curTarget);
            curMonSale = view.findViewById(R.id.curMonSale);
            preMonSale = view.findViewById(R.id.preMonSale);
            pre2MonSale = view.findViewById(R.id.pre2MonSale);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void searchAdsProduct(List<AdsStocksInfoList> stockLists) {
        adsStocksInfoLists = stockLists;
        notifyDataSetChanged();
    }

    public interface AdsStockListener {
        void onAdsStockClick(int position, AdsStocksInfoList model);
    }
}