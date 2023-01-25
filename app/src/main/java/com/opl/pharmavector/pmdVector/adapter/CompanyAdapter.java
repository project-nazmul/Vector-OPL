package com.opl.pharmavector.pmdVector.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.opl.pharmavector.R;
import com.opl.pharmavector.pmdVector.model.CompanyList;
import java.util.List;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder> {
    public List<CompanyList> companyLists;
    private Context context;

    public CompanyAdapter(Context context, List<CompanyList> companyList) {
        this.context = context;
        this.companyLists = companyList;
    }

    @Override
    public CompanyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.company_data_row, viewGroup, false);
        return new CompanyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CompanyViewHolder holder, int position) {
        CompanyList companyData = companyLists.get(position);

        holder.companyName.setText(companyData.getComDesc());
        holder.brandName.setText(companyData.getBrandName());
        holder.valueShare.setText(companyData.getValShare());
        holder.valueRank.setText(companyData.getNatValRank());
        holder.uniteShare.setText(companyData.getUniteShare());
        holder.uniteRank.setText(companyData.getNatUnitRank());
    }

    @Override
    public int getItemCount() {
        return companyLists.size();
    }

    public class CompanyViewHolder extends RecyclerView.ViewHolder {
        public TextView companyName, brandName, valueShare, valueRank, uniteShare, uniteRank;

        public CompanyViewHolder(View view) {
            super(view);
            companyName = view.findViewById(R.id.companyName);
            brandName = view.findViewById(R.id.brandName);
            valueShare = view.findViewById(R.id.valueShare);
            valueRank = view.findViewById(R.id.valueRank);
            uniteShare = view.findViewById(R.id.uniteShare);
            uniteRank = view.findViewById(R.id.uniteRank);
        }
    }
}
