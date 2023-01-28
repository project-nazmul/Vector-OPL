package com.opl.pharmavector.doctorList.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.opl.pharmavector.R;
import com.opl.pharmavector.doctorList.model.DoctorList;
import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder> {
    public List<DoctorList> doctorLists;
    private Context context;

    public DoctorAdapter(Context context, List<DoctorList> doctorList) {
        this.context = context;
        this.doctorLists = doctorList;
    }

    @Override
    public DoctorViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.doctor_list_row, viewGroup, false);
        return new DoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DoctorViewHolder holder, int position) {
        DoctorList doctorData = doctorLists.get(position);

        holder.doctorCode.setText(doctorData.getDocCode());
        holder.doctorName.setText(doctorData.getDocName());
        holder.marketCode.setText(doctorData.getMarketCode());
        holder.marketName.setText(doctorData.getMarketName());
        holder.doctorDegree.setText(doctorData.getDegree());
        holder.drSpecialization.setText(doctorData.getSpecialization());
        holder.drDesignation.setText(doctorData.getDesig());
        holder.doctorNop.setText(doctorData.getPatientPerDay());
        holder.drAddress.setText(doctorData.getAddress());
    }

    @Override
    public int getItemCount() {
        return doctorLists.size();
    }

    public class DoctorViewHolder extends RecyclerView.ViewHolder {
        public TextView doctorCode, doctorName, marketCode, marketName, doctorDegree, drSpecialization, drDesignation, doctorNop, drAddress;

        public DoctorViewHolder(View view) {
            super(view);
            doctorCode = view.findViewById(R.id.doctorCode);
            doctorName = view.findViewById(R.id.doctorName);
            marketCode = view.findViewById(R.id.marketCode);
            marketName = view.findViewById(R.id.marketName);
            doctorDegree = view.findViewById(R.id.doctorDegree);
            drSpecialization = view.findViewById(R.id.drSpecialization);
            drDesignation = view.findViewById(R.id.drDesignation);
            doctorNop = view.findViewById(R.id.doctorNop);
            drAddress = view.findViewById(R.id.drAddress);
        }
    }
}
