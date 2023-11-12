package com.opl.pharmavector.prescriptionsurvey;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.opl.pharmavector.R;

import java.util.List;
import java.util.Objects;

public class RxSummaryMISAdapter extends RecyclerView.Adapter<RxSummaryMISAdapter.RxSummaryMISViewHolder> {
    private Context context;
    private String dataChecker;
    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;
    public RxSummaryMisListener rxSumMisListener;
    public List<RxSumMISSelfList> rxSumMISSelfLists;
    public List<RxSumMISSelfList> filteredCompanyList;

    public RxSummaryMISAdapter(Context context, List<RxSumMISSelfList> rxSumMISSelfList, RxSummaryMisListener sumMisListener, String data_checker) {
        this.context = context;
        this.dataChecker = data_checker;
        this.rxSumMisListener = sumMisListener;
        this.rxSumMISSelfLists = rxSumMISSelfList;
    }

    @Override
    public RxSummaryMISViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_rx_mis_row, viewGroup, false);
        return new RxSummaryMISViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RxSummaryMISViewHolder holder, int position) {
        RxSumMISSelfList rxSummaryMis = rxSumMISSelfLists.get(position);
        holder.misCode.setText(String.valueOf(rxSummaryMis.getFfCode()));
        holder.misFFName.setText(rxSummaryMis.getFfName());
        holder.misOPDRx.setText(rxSummaryMis.getOp());
        holder.misIPDRx.setText(rxSummaryMis.getInd());
        holder.misRegular.setText(rxSummaryMis.getRg());
        holder.misServiced.setText(rxSummaryMis.getSd());
        holder.misLoyality.setText(rxSummaryMis.getBl());
        holder.misGift.setText(rxSummaryMis.getSg());
        holder.misGeneration.setText(rxSummaryMis.getRx());
        holder.misTotal.setText(rxSummaryMis.getTotRx());

        holder.itemView.setOnClickListener(v -> {
            if (Objects.equals(dataChecker, "D")) {
                rxSumMisListener.onRxMisClick(position, rxSummaryMis);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rxSumMISSelfLists.size();
    }

    public class RxSummaryMISViewHolder extends RecyclerView.ViewHolder {
        public TextView misCode, misFFName, misOPDRx, misIPDRx, misRegular, misServiced, misLoyality, misGift, misGeneration, misTotal;
        public ImageView imgLogo;

        public RxSummaryMISViewHolder(View view) {
            super(view);
            misCode = view.findViewById(R.id.misCode);
            misFFName = view.findViewById(R.id.misFFName);
            misOPDRx = view.findViewById(R.id.misOPDRx);
            misIPDRx = view.findViewById(R.id.misIPDRx);
            misRegular = view.findViewById(R.id.misRegular);
            misServiced = view.findViewById(R.id.misServiced);
            misLoyality = view.findViewById(R.id.misLoyality);
            misGift = view.findViewById(R.id.misGift);
            misGeneration = view.findViewById(R.id.misGeneration);
            misTotal = view.findViewById(R.id.misTotal);
        }
    }

    public interface RxSummaryMisListener {
        void onRxMisClick(int position, RxSumMISSelfList rxModel);
    }
}