package com.opl.pharmavector.chemistList.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.opl.pharmavector.R;
import com.opl.pharmavector.achieve.AchieveEarnAdapter;
import com.opl.pharmavector.achieve.AchieveEarningList;
import com.opl.pharmavector.chemistList.model.ChemistList;
import com.opl.pharmavector.doctorList.model.DoctorList;

import java.util.List;

public class ChemistAdapter extends RecyclerView.Adapter<ChemistAdapter.DoctorViewHolder> {
    public List<ChemistList> chemistLists;
    public ContactCallback contactCallback;

    public ChemistAdapter(Context context, List<ChemistList> chemistList, ContactCallback contactCallback) {
        this.chemistLists = chemistList;
        this.contactCallback = contactCallback;
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
        holder.chemAddress.setText(chemistData.getAddress());
        holder.phoneNumber.setText(chemistData.getMobileNumber());

        holder.contactUpdate.setOnClickListener(v -> {
            contactCallback.onContactPhone(chemistData);
        });
    }

    @Override
    public int getItemCount() {
        return chemistLists.size();
    }

    public class DoctorViewHolder extends RecyclerView.ViewHolder {
        public TextView chemistCode, chemistName, marketCode, marketName, dopotDesc, chemAddress, phoneNumber;
        public Button contactUpdate;

        public DoctorViewHolder(View view) {
            super(view);
            chemistCode = view.findViewById(R.id.chemistCode);
            chemistName = view.findViewById(R.id.chemistName);
            marketCode = view.findViewById(R.id.marketCode);
            marketName = view.findViewById(R.id.marketName);
            dopotDesc = view.findViewById(R.id.dopotDesc);
            chemAddress = view.findViewById(R.id.chemAddress);
            phoneNumber = view.findViewById(R.id.phoneNumber);
            contactUpdate = view.findViewById(R.id.contactUpdate);
        }
    }

    public interface ContactCallback {
        void onContactPhone(ChemistList chemistList);
    }
}
