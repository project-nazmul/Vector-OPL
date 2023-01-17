package com.opl.pharmavector;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class NoticeAdapter extends BaseAdapter implements Filterable {

    Context context;
    ArrayList<Category> records;
    ValueFilter valueFilter;
    private LayoutInflater inflater;
    ArrayList<Category> filterrecords;

    NoticeAdapter(Context context, ArrayList<Category> records){
        this.context = context;
        this.records = records;
        this.filterrecords = records;
    }


    @Override
    public int getCount() {
        return filterrecords.size();
    }
    // Get the data item associated with the specified position in the data set.
    @Override
    public Object getItem(int position) {
        return filterrecords.get(position).getId();
    }
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @SuppressLint({"ClickableViewAccessibility", "ResourceAsColor"})
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView==null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.notice_board_adapter, null);
        }
        //convertView = inflater.inflate(R.layout.notice_board_adapter, null);
        ViewHolder holder = new ViewHolder();
        holder.serial = (TextView) convertView.findViewById(R.id.serial);
        holder.p_name = (TextView) convertView.findViewById(R.id.product_name);//notice_detail
        holder.ppm_error = (TextView) convertView.findViewById(R.id.notice_title);
        holder.quantity = (TextView) convertView.findViewById(R.id.order_qnty);
        holder.edit_qnty= (TextView) convertView.findViewById(R.id.order_qnty);
        convertView.setTag(holder);
        final int poss = Integer.parseInt(filterrecords.get(position).getId());
        holder.pos = position;
        holder.p_name.setText(filterrecords.get(position).getName());
        holder.ppm_error.setText(filterrecords.get(position).getPPM_CODE());
        holder.serial.setText(filterrecords.get(position).getId());
        holder.quantity.setText(filterrecords.get(position).getP_CODE());

        return convertView;
    }
    private class ViewHolder {
        int pos;
        TextView serial;
        TextView quantity;
        TextView p_name;
        TextView ppm_error;
        TextView edit_qnty;

    }
    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }
    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ArrayList<Category> filterList = new ArrayList<Category>();
                for (int i = 0; i < records.size(); i++) {
                    if ((records.get(i).getName().toUpperCase()).contains(constraint.toString().toUpperCase())||(records.get(i).getPROD_RATE().toUpperCase()).contains(constraint.toString().toUpperCase())){
                        //Log.e("search string  ====>", records.get(i).getName());
                        if (records.size()>0){
                            Category bean = new Category(records.get(i).getsl(), records.get(i).getId(),records.get(i).getName(),records.get(i).getPPM_CODE(),records.get(i).getPROD_RATE(), records.get(i).getPROD_VAT());
                            filterList.add(bean);
                        }else{
                            Toast.makeText(context,"Invalid Keyword",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = records.size();
                results.values = records;
            }
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filterrecords = (ArrayList<Category>) results.values;
            notifyDataSetChanged();
        }

    }

}


