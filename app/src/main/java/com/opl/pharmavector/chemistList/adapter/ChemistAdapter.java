package com.opl.pharmavector.chemistList.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.opl.pharmavector.R;
import com.opl.pharmavector.chemistList.model.ChemistList;
import com.opl.pharmavector.doctorList.model.DoctorList;

import java.util.List;

public class ChemistAdapter extends RecyclerView.Adapter<ChemistAdapter.DoctorViewHolder> {
    public List<ChemistList> chemistLists;

    public ChemistAdapter(Context context, List<ChemistList> chemistList) {
        this.chemistLists = chemistList;
    }

    @Override
    public DoctorViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chemist_list_row, viewGroup, false);
        return new DoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DoctorViewHolder holder, int position) {
        ChemistList chemistData = chemistLists.get(position);

        holder.chemistCode.setText(chemistData.getCustCode());
        holder.chemistName.setText(chemistData.getCustName());
        holder.marketCode.setText(chemistData.getMarketCode());
        holder.marketName.setText(chemistData.getMarketName());
        holder.dopotDesc.setText(chemistData.getDepotDesc());
        holder.chemAddresss.setText(chemistData.getAddress());
    }

    @Override
    public int getItemCount() {
        return chemistLists.size();
    }

    public class DoctorViewHolder extends RecyclerView.ViewHolder {
        public TextView chemistCode, chemistName, marketCode, marketName, dopotDesc, chemAddresss;

        public DoctorViewHolder(View view) {
            super(view);
            chemistCode = view.findViewById(R.id.chemistCode);
            chemistName = view.findViewById(R.id.chemistName);
            marketCode = view.findViewById(R.id.marketCode);
            marketName = view.findViewById(R.id.marketName);
            dopotDesc = view.findViewById(R.id.dopotDesc);
            chemAddresss = view.findViewById(R.id.chemAddress);
        }
    }
}
