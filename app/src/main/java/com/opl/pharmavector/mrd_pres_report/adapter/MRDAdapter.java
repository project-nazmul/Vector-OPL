package com.opl.pharmavector.mrd_pres_report.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Filter;

import com.opl.pharmavector.R;
import com.opl.pharmavector.mrd_pres_report.MRDPresReport;
import com.opl.pharmavector.mrd_pres_report.SPIReportList;
import com.opl.pharmavector.promomat.model.Promo;

import java.util.ArrayList;
import java.util.List;

public class MRDAdapter extends RecyclerView.Adapter<MRDAdapter.ClubViewHolder> implements Filterable {
    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;
    public List<SPIReportList> companyList;
    public List<SPIReportList> filteredCompanyList;
    private Context context;

    public MRDAdapter(Context context, List<SPIReportList> companyList) {
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
    public ClubViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_mrd_pres_report, viewGroup, false);
        return new ClubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ClubViewHolder holder, int position) {
        SPIReportList company = filteredCompanyList.get(position);
        holder.serial.setText(String.valueOf(company.getSl()));
        holder.mpocode.setText(company.getUserCode());
        holder.base.setText(company.getBase());
        holder.oplbase.setText(company.getOplBase());
        holder.oplshare.setText(company.getOplShare());
        holder.opsonin.setText(company.getOpsonin());
        holder.square.setText(company.getSquare());
        holder.incepta.setText(company.getIncepta());
        holder.beximco.setText(company.getBeximco());
        holder.drugs.setText(company.getDrug());
        holder.healthcare.setText(company.getHealthcare());
        holder.reneta.setText(company.getRenata());
        holder.sKF.setText(company.getSkf());
        holder.acme.setText(company.getAcme());
        holder.aci.setText(company.getAci());
        holder.aristopharma.setText(company.getAristopharma());
        holder.popular.setText(company.getPopular());
        holder.radient.setText(company.getRadiant());
        holder.osl.setText(company.getOsl());
    }

    @Override
    public int getItemCount()
    {
        return filteredCompanyList.size();
    }

    public class ClubViewHolder extends RecyclerView.ViewHolder {
        public TextView serial, mpocode, opsonin, square, incepta, beximco,drugs,healthcare,reneta,sKF,acme,aci,aristopharma,popular,radient,osl,oth,base,oplbase,oplshare;
        public ImageView imgLogo;

        public ClubViewHolder(View view) {
            super(view);
            serial = view.findViewById(R.id.serial);
            mpocode = view.findViewById(R.id.mpocode);
            opsonin = view.findViewById(R.id.opsonin);
            square = view.findViewById(R.id.square);
            incepta = view.findViewById(R.id.incepta);
            beximco = view.findViewById(R.id.beximco);
            drugs = view.findViewById(R.id.drugs);
            healthcare = view.findViewById(R.id.healthcare);
            reneta = view.findViewById(R.id.reneta);
            sKF = view.findViewById(R.id.sKF);
            acme = view.findViewById(R.id.acme);
            aci = view.findViewById(R.id.aci);
            aristopharma = view.findViewById(R.id.aristopharma);
            popular = view.findViewById(R.id.popular);
            radient = view.findViewById(R.id.radient);
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
                    List<SPIReportList> filteredList = new ArrayList<>();
                    for (SPIReportList company : companyList) {
                        if (company.getSl().toString().toLowerCase().contains(charString.toLowerCase()) ) {
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
                filteredCompanyList = (ArrayList<SPIReportList>) filterResults.values;
                //refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }
}