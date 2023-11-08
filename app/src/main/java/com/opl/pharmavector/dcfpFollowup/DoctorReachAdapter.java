package com.opl.pharmavector.dcfpFollowup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.opl.pharmavector.R;
import com.opl.pharmavector.mrd_pres_report.adapter.MRDAdapter;
import com.opl.pharmavector.promomat.model.Promo;

import java.util.List;

public class DoctorReachAdapter extends RecyclerView.Adapter<DoctorReachAdapter.DoctorReachViewHolder> {
    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;
    public List<DoctorReachSelfList> companyList;
    public List<DoctorReachSelfList> filteredCompanyList;
    private Context context;

    public DoctorReachAdapter(Context context, List<DoctorReachSelfList> companyList) {
        this.context = context;
        this.companyList = companyList;
        this.filteredCompanyList = companyList;
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return TYPE_ROW_COLORFUL;
        }
        return TYPE_ROW;
    }

    @Override
    public DoctorReachViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_doc_reach_row, viewGroup, false);
        return new DoctorReachViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DoctorReachViewHolder holder, int position) {
        DoctorReachSelfList company = filteredCompanyList.get(position);
        holder.serial.setText(String.valueOf(company.getSl()));
        holder.mpocode.setText(company.getFfCode());
        holder.location.setText(company.getFfName());
        holder.totalPres.setText(company.getTotDoc());
        holder.totalGen.setText(company.getTotGenDoc());
        holder.totalProd.setText(company.getTotProdDoc());
        holder.docReach.setText(company.getDocReach());
    }

    @Override
    public int getItemCount() {
        return filteredCompanyList.size();
    }

    public class DoctorReachViewHolder extends RecyclerView.ViewHolder {
        public TextView serial, mpocode, location, totalPres, totalGen, totalProd, docReach;
        public ImageView imgLogo;

        public DoctorReachViewHolder(View view) {
            super(view);
            serial = view.findViewById(R.id.serial);
            mpocode = view.findViewById(R.id.mpocode);
            location = view.findViewById(R.id.location);
            totalPres = view.findViewById(R.id.totalPres);
            totalGen = view.findViewById(R.id.totalGen);
            totalProd = view.findViewById(R.id.totalProd);
            docReach = view.findViewById(R.id.docReach);
        }
    }
}