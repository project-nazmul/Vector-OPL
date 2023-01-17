package com.opl.pharmavector.master_code.adapter;

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

public class PromoAdapter extends RecyclerView.Adapter<PromoAdapter.ClubViewHolder> implements Filterable
{
    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    public List<Promo> companyList;
    public List<Promo> filteredCompanyList;
    private Context context;
    private PromoAdapterListener listener;

    private ValueFilter valueFilter;

    public PromoAdapter(Context context, List<Promo> companyList)
    {
        this.context = context;
        this.companyList = companyList;
        this.filteredCompanyList = companyList;
        getFilter();
    }

    public PromoAdapter(Context context, List<Promo> companyList,PromoAdapterListener listener)
    {
        this.context = context;
        this.listener = listener;
        this.companyList = companyList;
        this.filteredCompanyList = companyList;
        getFilter();
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
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public ClubViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        if (viewType == TYPE_ROW)
        {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_master_code_row, viewGroup, false);
            return new ClubViewHolder(view);

        } else{

            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_master_code_row_colorful, viewGroup, false);
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
        holder.week4.setText(company.week4);
        holder.total.setText(company.total);

    }

    @Override
    public int getItemCount()
    {
        return filteredCompanyList.size();
    }

    public class ClubViewHolder extends RecyclerView.ViewHolder
    {
        public TextView serial, mpocode, month, sample_name, pack_size, type,week1,week2,week3,week4,total;

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


        }
    }

    /*
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
                        if (company.code.toLowerCase().contains(charString.toLowerCase()) )
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
    */


    @Override
    public Filter getFilter() {
        // TODO Auto-generated method stub
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter {

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
                    if (company.code.toLowerCase().contains(charString.toLowerCase()) )
                    {
                        filteredList.add(company);
                    }
                }

                filteredCompanyList = filteredList;
                notifyDataSetChanged();
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredCompanyList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults)
        {
            filteredCompanyList = (ArrayList<Promo>) filterResults.values;
            notifyDataSetChanged();
        }
    }

    public interface PromoAdapterListener{
        void onPromoSelected(Promo promo);
    }


}