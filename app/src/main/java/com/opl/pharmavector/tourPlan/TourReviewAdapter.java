package com.opl.pharmavector.tourPlan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.opl.pharmavector.R;
import com.opl.pharmavector.saleReport.GroupOrderSummaryList;

import java.util.List;

public class TourReviewAdapter extends RecyclerView.Adapter<TourReviewAdapter.TourReviewViewHolder> {
    public Context context;
    public ItemClickListener itemClickListener;
    public List<TReviewDetailsList> reviewDetailLists;

    public TourReviewAdapter(Context context, List<TReviewDetailsList> reviewDetailList, ItemClickListener itemClickListener) {
        this.context = context;
        this.reviewDetailLists = reviewDetailList;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public TourReviewViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tour_review_raw, viewGroup, false);
        return new TourReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TourReviewViewHolder holder, int position) {
        TReviewDetailsList tourReviewModel = reviewDetailLists.get(position);
        holder.trVisitDate.setText(tourReviewModel.getVisitDt());
        holder.trMorLocCode.setText(tourReviewModel.getMorLocCode());
        holder.trMorLocName.setText(tourReviewModel.getMorLocName());
        holder.trEveLocCode.setText(tourReviewModel.getEveLocCode());
        holder.trEveLocName.setText(tourReviewModel.getEveLocName());

        holder.itemView.setOnClickListener(view -> {
            itemClickListener.onClick(position, tourReviewModel);
        });
    }

    @Override
    public int getItemCount() {
        return reviewDetailLists.size();
    }

    public class TourReviewViewHolder extends RecyclerView.ViewHolder {
        public TextView trVisitDate, trMorLocCode, trMorLocName, trEveLocCode, trEveLocName, trApproveStatus;

        public TourReviewViewHolder(View view) {
            super(view);
            trVisitDate = view.findViewById(R.id.trVisitDate);
            trMorLocCode = view.findViewById(R.id.trMorLocCode);
            trMorLocName = view.findViewById(R.id.trMorLocName);
            trEveLocCode = view.findViewById(R.id.trEveLocCode);
            trEveLocName = view.findViewById(R.id.trEveLocName);
            trApproveStatus = view.findViewById(R.id.trApproveStatus);
        }
    }

    public interface ItemClickListener {
        void onClick(int position, TReviewDetailsList reviewModel);
    }
}
