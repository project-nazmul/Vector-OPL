package com.opl.pharmavector.doctorgift;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.opl.pharmavector.ChemistGiftListAdapter;
import com.opl.pharmavector.R;

import java.util.ArrayList;


public class CustomAdapterFollowup extends BaseAdapter {
    Context context;
    ArrayList<DataHolder> records;
    private LayoutInflater inflater;
    CustomAdapterFollowup(Context context, ArrayList<DataHolder> records){
        this.context = context;
        this.records = records;
    }

    @Override
    public int getCount() {
        return records.size();
    }

    @Override
    public Object getItem(int i) {
        return records.get(i).getvalue1();
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (view==null) {
            holder = new ViewHolder();
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_item_followup, null);
            holder.txt_value1 = (TextView) view.findViewById(R.id.sl_0);
            holder.txt_value2 = (TextView) view.findViewById(R.id.con_month);
            holder.txt_value3 = (TextView) view.findViewById(R.id.territory_code);
            holder.txt_value4 = (TextView) view.findViewById(R.id.gift_name);
            holder.txt_value5 = (TextView) view.findViewById(R.id.doctor_name);
            holder.txt_value6 = (TextView) view.findViewById(R.id.doctor_code);
            holder.txt_value7 = (TextView) view.findViewById(R.id.am_status);
            holder.txt_value8 = (TextView) view.findViewById(R.id.rm_status);
            holder.txt_value9 = (TextView) view.findViewById(R.id.asm_status);
            holder.txt_value10 = (TextView) view.findViewById(R.id.sm_status);
            holder.txt_value11 = (TextView) view.findViewById(R.id.gm_status);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        holder.txt_value1.setText(records.get(i).getvalue1());
        holder.txt_value2.setText(records.get(i).getvalue2());
        holder.txt_value3.setText(records.get(i).getvalue3());
        holder.txt_value4.setText(records.get(i).getvalue4());
        holder.txt_value5.setText(records.get(i).getvalue5());
        holder.txt_value6.setText(records.get(i).getvalue6());
        holder.txt_value7.setText(records.get(i).getvalue7());
        holder.txt_value8.setText(records.get(i).getvalue8());
        holder.txt_value9.setText(records.get(i).getvalue9());
        holder.txt_value10.setText(records.get(i).getvalue10());
        holder.txt_value11.setText(records.get(i).getvalue11());
        return view;
    }

    class ViewHolder{
        TextView txt_value1,txt_value2,txt_value3,txt_value4,txt_value5,txt_value6,txt_value7,txt_value8,txt_value9,txt_value10,txt_value11,txt_value12;
    }
}
