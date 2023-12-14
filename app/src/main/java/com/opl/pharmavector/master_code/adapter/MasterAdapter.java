package com.opl.pharmavector.master_code.adapter;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Filter;

import com.opl.pharmavector.R;
import com.opl.pharmavector.master_code.model.MasterCList;
import com.opl.pharmavector.master_code.model.MasterModel;
import com.opl.pharmavector.productOffer.ProductOfferList;
import com.opl.pharmavector.promomat.model.Promo;

import java.util.ArrayList;
import java.util.List;

public class MasterAdapter extends RecyclerView.Adapter<MasterAdapter.ClubViewHolder> implements Filterable {
    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;
    public List<MasterCList> companyList;
    public List<MasterCList> filteredCompanyList;
    private Context context;
    private MasterAdapterListener listener;
    private ValueFilter valueFilter;

    public class ClubViewHolder extends RecyclerView.ViewHolder {
        public TextView serialNo, terriCode, userRole, empCode, terriName, empName, depotName, password;

        public ClubViewHolder(View view) {
            super(view);
            serialNo = view.findViewById(R.id.serialNo);
            terriCode = view.findViewById(R.id.terriCode);
            userRole = view.findViewById(R.id.userRole);
            empCode = view.findViewById(R.id.empCode);
            terriName = view.findViewById(R.id.terriName);
            empName = view.findViewById(R.id.empName);
            depotName = view.findViewById(R.id.depotName);
            password = view.findViewById(R.id.password);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onPromoSelected(filteredCompanyList.get(getAdapterPosition()));
                }
            });
        }
    }

    public MasterAdapter(Context context, List<MasterCList> companyList) {
        this.context = context;
        this.companyList = companyList;
        this.filteredCompanyList = companyList;
        getFilter();
    }

    public MasterAdapter(Context context, List<MasterCList> companyList, MasterAdapterListener listener) {
        this.context = context;
        this.listener = listener;
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
    public long getItemId(int position) {
        return position;
    }

    @Override
    public ClubViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ROW) {
            View view = LayoutInflater.from(viewGroup.getContext()).
                    inflate(R.layout.adapter_master_code_row, viewGroup, false);
            return new ClubViewHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).
                    inflate(R.layout.adapter_master_code_row_colorful, viewGroup, false);
            return new ClubViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(ClubViewHolder holder, int position) {
        MasterCList company = filteredCompanyList.get(position);
        holder.serialNo.setText(String.valueOf(company.getSl()));
        holder.terriCode.setText(company.getMpoCode());
        holder.userRole.setText(company.getFfDesc());
        holder.empCode.setText(company.getEmpno());
        holder.terriName.setText(company.getTerriName());
        holder.empName.setText(company.getEname());
        holder.depotName.setText(company.getDepotDesc());
        holder.password.setText(company.getPassword());
    }

    @SuppressLint("NotifyDataSetChanged")
    public void searchMasterCode(List<MasterCList> mCodeList) {
        filteredCompanyList = mCodeList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return filteredCompanyList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredCompanyList = companyList;
                } else {
                    List<MasterCList> filteredList = new ArrayList<>();

                    for (MasterCList company : companyList) {
//                        if (company.code.toLowerCase().contains(charString.toLowerCase())) {
//                            filteredList.add(company);
//                        }
                    }
                    filteredCompanyList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredCompanyList;
                return filterResults;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredCompanyList = (ArrayList<MasterCList>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    private class ValueFilter extends Filter {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String charString = charSequence.toString();
            if (charString.isEmpty()) {
                filteredCompanyList = companyList;
            } else {
                List<MasterCList> filteredList = new ArrayList<>();
                for (MasterCList company : companyList) {
//                    if (company.code.toLowerCase().contains(charString.toLowerCase())) {
//                        filteredList.add(company);
//                    }
                }
                filteredCompanyList = filteredList;
                notifyDataSetChanged();
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredCompanyList;
            return filterResults;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            filteredCompanyList = (ArrayList<MasterCList>) filterResults.values;
            notifyDataSetChanged();
        }
    }

    public interface MasterAdapterListener {
        void onPromoSelected(MasterCList promo);

        void onPromoSelected(Promo promo);
    }
}