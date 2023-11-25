package com.opl.pharmavector.mrd_pres_report.adapter;

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
import com.opl.pharmavector.promomat.model.Promo;

import java.util.ArrayList;
import java.util.List;

public class MRDDocAdapter extends RecyclerView.Adapter<MRDDocAdapter.ClubViewHolder> implements Filterable {
    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    public List<Promo> companyList;
    public List<Promo> filteredCompanyList;
    private Context context;
    private int recyclerNo;

    public MRDDocAdapter(Context context, List<Promo> companyList, int recyclerNo) {
        this.context = context;
        this.recyclerNo = recyclerNo;
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
    public ClubViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_mrd_doc_report, viewGroup, false);
        return new ClubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ClubViewHolder holder, int position) {
            Promo company = filteredCompanyList.get(position);
            holder.serial.setText(company.serial);
            holder.mpocode.setText(company.code);
            holder.month.setText(company.month);
            holder.spec.setText(company.phySpec);
            holder.sample_name.setText(company.sample_name);
            holder.pack_size.setText(company.pack_size);
            holder.type.setText(company.type);
            holder.week1.setText(company.week1);
            holder.week1.setVisibility(View.GONE);
            holder.week2.setVisibility(View.GONE);
            holder.week3.setVisibility(View.GONE);
            holder.week4.setVisibility(View.GONE);
            holder.total.setVisibility(View.GONE);
            holder.aci.setVisibility(View.GONE);
            holder.ari.setVisibility(View.GONE);
            holder.pop.setVisibility(View.GONE);
            holder.rad.setVisibility(View.GONE);
            holder.osl.setVisibility(View.GONE);
            holder.base.setVisibility(View.GONE);
            holder.oplbase.setVisibility(View.GONE);
            holder.oplshare.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount()
    {
        return filteredCompanyList.size();
    }

    public class ClubViewHolder extends RecyclerView.ViewHolder {
        public TextView serial, mpocode, month, sample_name, pack_size, type, week1, week2, week3, week4, total, aci, ari,
                pop, rad, osl, oth, base, oplbase, oplshare, spec;
        public ImageView imgLogo;

        public ClubViewHolder(View view) {
            super(view);
            serial = view.findViewById(R.id.serial);
            mpocode = view.findViewById(R.id.mpocode);
            month = view.findViewById(R.id.month);
            spec = view.findViewById(R.id.spec);
            sample_name = view.findViewById(R.id.sample_name);
            pack_size = view.findViewById(R.id.pack_size);
            type = view.findViewById(R.id.type);
            week1 = view.findViewById(R.id.week1);
            week2 = view.findViewById(R.id.week2);
            week3 = view.findViewById(R.id.week3);
            week4 = view.findViewById(R.id.week4);
            total = view.findViewById(R.id.total);
            aci = view.findViewById(R.id.aci);
            ari = view.findViewById(R.id.ari);
            pop = view.findViewById(R.id.pop);
            rad = view.findViewById(R.id.rad);
            osl = view.findViewById(R.id.osl);
            base = view.findViewById(R.id.base);
            oplbase = view.findViewById(R.id.oplbase);
            oplshare = view.findViewById(R.id.oplshare);
        }
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
                    List<Promo> filteredList = new ArrayList<>();

                    for (Promo company : companyList) {
                        if (company.serial.toLowerCase().contains(charString.toLowerCase()) ) {
                            filteredList.add(company);
                        }
                    }
                    filteredCompanyList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredCompanyList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredCompanyList = (ArrayList<Promo>) filterResults.values;
                //refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }
}
