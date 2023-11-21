package com.opl.pharmavector.liveDepot;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.opl.pharmavector.R;

import java.util.List;

public class AdsStockInfoAdapter extends RecyclerView.Adapter<AdsStockInfoAdapter.AdsStockInfoVHolder> {
    public int blockNumber1, blockNumber2;
    public  AdsStockListener adsStockListeners;
    public List<AdsStocksInfoList> adsStocksInfoLists;

    public AdsStockInfoAdapter(List<AdsStocksInfoList> adsStocksInfoList, AdsStockListener adsStockListener, int blockNumber1, int blockNumber2) {
        this.blockNumber1 = blockNumber1;
        this.blockNumber2 = blockNumber2;
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


        if (blockNumber1 == 1) {
            holder.productName.setVisibility(View.GONE);
            holder.packSize.setVisibility(View.GONE);
            holder.freeStock.setText(adsStocksList.getTotFreeStkQnty());
            holder.freeStock.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,2.4f));
            holder.freeStock.setPadding(0, 12, 0, 0);
            holder.depotStock.setText(adsStocksList.getDepoStk());
            holder.depotStock.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,2.4f));
            holder.csStock.setText(adsStocksList.getBslStkOnly());
            holder.csStock.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,2.4f));
            holder.transit.setText(adsStocksList.getBslFgsTrnsOnly());
            holder.transit.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,2.4f));
            holder.fgsProd.setText(adsStocksList.getFgsStk());
            holder.fgsProd.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,2.4f));
            holder.curTarget.setVisibility(View.GONE);
            holder.curMonSale.setVisibility(View.GONE);
            holder.preMonSale.setVisibility(View.GONE);
            holder.pre2MonSale.setVisibility(View.GONE);
        } else if (blockNumber2 == 1) {
            holder.productName.setVisibility(View.GONE);
            holder.packSize.setVisibility(View.GONE);
            holder.freeStock.setVisibility(View.GONE);
            holder.depotStock.setVisibility(View.GONE);
            holder.csStock.setVisibility(View.GONE);
            holder.transit.setVisibility(View.GONE);
            holder.fgsProd.setVisibility(View.GONE);
            holder.curTarget.setText(adsStocksList.getTarQnty3());
            holder.curTarget.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,2f));
            holder.curTarget.setPadding(0, 12, 0, 0);
            holder.curMonSale.setText(adsStocksList.getSaleQnty3());
            holder.curMonSale.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,2f));
            holder.preMonSale.setText(adsStocksList.getSaleQnty2());
            holder.preMonSale.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,2f));
            holder.pre2MonSale.setText(adsStocksList.getSaleQnty1());
            holder.pre2MonSale.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,2f));
            holder.pre3MonSale.setVisibility(View.VISIBLE);
            holder.pre3MonSale.setText(adsStocksList.getSaleQnty0());
            holder.pre3MonSale.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,2f));
            holder.avgMonSale.setVisibility(View.VISIBLE);
            holder.avgMonSale.setText(adsStocksList.getAvg3Sale());
            holder.avgMonSale.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,2f));
        } else {
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
    }

    @Override
    public int getItemCount() {
        return adsStocksInfoLists.size();
    }

    public class AdsStockInfoVHolder extends RecyclerView.ViewHolder {
        public TextView productName, packSize, freeStock, depotStock, csStock, transit, fgsProd, curTarget, curMonSale, preMonSale, pre2MonSale, pre3MonSale, avgMonSale;

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
            pre3MonSale = view.findViewById(R.id.pre3MonSale);
            avgMonSale = view.findViewById(R.id.avgMonSale);
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