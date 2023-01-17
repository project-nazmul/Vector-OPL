package com.opl.pharmavector.msd_doc_support.adapter;



import static android.graphics.Color.*;

import android.content.Context;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Filter;

import com.opl.pharmavector.R;
import com.opl.pharmavector.promomat.model.Promo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MSDProgramAdapter extends RecyclerView.Adapter<MSDProgramAdapter.ClubViewHolder> implements Filterable
{
    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    public List<Promo> companyList;
    public List<Promo> filteredCompanyList;
    private Context context;

    public MSDProgramAdapter(Context context, List<Promo> companyList)
    {
        this.context = context;
        this.companyList = companyList;
        this.filteredCompanyList = companyList;
    }

    @Override
    public int getItemViewType(int position)
    {
        if (position % 2 == 0)
        {
            return TYPE_ROW_COLORFUL;
        }

        return TYPE_ROW;
    }

    @Override
    public ClubViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        if (viewType == TYPE_ROW)

        {
            //View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_club, viewGroup, false);
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_msd_prog_followup, viewGroup, false);

            return new ClubViewHolder(view);
        } else
        {
            //View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_club_colorful, viewGroup, false);
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_msd_prog_followup_colorful, viewGroup, false);
            return new ClubViewHolder(view);
        }


    }

    @Override
    public void onBindViewHolder(ClubViewHolder holder, int position)
    {
        Promo company = filteredCompanyList.get(position);

        holder.serial.setText(company.serial);
        holder.mpocode.setText(company.code);
        holder.month.setText(company.month);
        holder.sample_name.setText(company.sample_name);
        holder.pack_size.setText(company.pack_size);
        holder.type.setText(company.type);
        holder.week1.setText(company.week1);
        holder.week2.setText(company.week2);
        holder.week3.setText(company.week3);


/*
        if (company.week1.equals("N")){
            holder.week1.setText("NO");
            holder.week1.setTextColor(Color.rgb(153, 0, 0));
        }else{
            holder.week1.setText("Yes");
            holder.week1.setTextColor(Color.rgb(51, 129, 16));
        }



        if (company.week2.equals("N")){
            holder.week2.setText("NO");
            holder.week2.setTextColor(Color.rgb(153, 0, 0));
        }else{
            holder.week2.setText("Yes");
            holder.week2.setTextColor(Color.rgb(51, 129, 16));
        }



        if (company.week3.equals("N")){
            holder.week3.setText("NO");
            holder.week3.setTextColor(Color.rgb(153, 0, 0));

        }else{
            holder.week3.setText("Yes");
            holder.week3.setTextColor(Color.rgb(51, 129, 16));
        }
        */

        holder.week4.setText(company.week4);
        holder.total.setText(company.total);


        holder.msd.setText(company.aci);
        holder.gm.setText(company.ari);
        holder.finance.setText(company.pop);
        holder.challan.setText(company.rad);

        if (company.osl == null || company.osl.equals(" ")){
            holder.cost.setText(company.osl);
            //Log.e("nullE-->","valus is null");
        }else{

             double amount = Double.parseDouble(company.osl);
             DecimalFormat formatter = new DecimalFormat("#,##,###");
             String formatted = formatter.format(amount);
             holder.cost.setText(formatted);
            //Log.e("nullE-->","valus is not null");
        }


       // holder.cost.setText(company.osl);
       // double amount = Double.parseDouble(company.osl);
       // DecimalFormat formatter = new DecimalFormat("#,##,###");
        //String formatted = formatter.format(amount);
        //holder.cost.setText(formatted);


    }

    @Override
    public int getItemCount()
    {
        return filteredCompanyList.size();
    }

    public class ClubViewHolder extends RecyclerView.ViewHolder
    {
        public TextView serial, mpocode, month, sample_name, pack_size, type,week1,week2,week3,week4,total,msd,gm,finance,challan,cost;
        public ImageView imgLogo;

        public ClubViewHolder(View view)
        {
            super(view);
            serial = view.findViewById(R.id.serial);
            mpocode = view.findViewById(R.id.mpocode);
            month = view.findViewById(R.id.month);
            sample_name = view.findViewById(R.id.sample_name);
            pack_size = view.findViewById(R.id.pack_size);
            type = view.findViewById(R.id.type);
            week1 = view.findViewById(R.id.week1);
            week2 = view.findViewById(R.id.week2);
            week3 = view.findViewById(R.id.week3);
            week4 = view.findViewById(R.id.week4);
            total = view.findViewById(R.id.total);


            msd = view.findViewById(R.id.msd);
            gm = view.findViewById(R.id.gm);
            finance = view.findViewById(R.id.finance);
            challan = view.findViewById(R.id.challan);
            cost = view.findViewById(R.id.cost);

        }
    }

    @Override
    public Filter getFilter()
    {
        return new Filter()
        {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence)
            {
                String charString = charSequence.toString();
                if (charString.isEmpty())
                {
                    filteredCompanyList = companyList;
                } else
                {
                    List<Promo> filteredList = new ArrayList<>();
                    for (Promo company : companyList)
                    {
                        if (company.serial.toLowerCase().contains(charString.toLowerCase()) )
                        {
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
            protected void publishResults(CharSequence charSequence, FilterResults filterResults)
            {
                filteredCompanyList = (ArrayList<Promo>) filterResults.values;
                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }
}
