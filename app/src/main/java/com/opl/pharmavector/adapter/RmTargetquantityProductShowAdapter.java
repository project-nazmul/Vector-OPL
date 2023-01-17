package com.opl.pharmavector.adapter;


import java.text.DecimalFormat;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.opl.pharmavector.R;

@SuppressLint("ViewHolder")
public class RmTargetquantityProductShowAdapter extends BaseAdapter {
    TextView serial;
    CheckBox check;
    Context mContext;
    ArrayList<String> product_name;
    ArrayList<String> segment_code;
    ArrayList<Integer> qnty;
    ArrayList<Float> value;
    ArrayList<Float> achv;
    ArrayList<Float> growth;
    OnClickListener callBack;
    View rowView;
    public static int last_position;


    RmTargetquantityProductShowAdapter(Context con, ArrayList<String> product_name,
                                       ArrayList<Integer> qnty, ArrayList<Float> value) {
        this.product_name = product_name;
        this.qnty = qnty;
        this.value = value;
        this.mContext = con;
    }


    RmTargetquantityProductShowAdapter(Context con, ArrayList<String> product_name,
                                       ArrayList<Integer> qnty, ArrayList<Float> value,ArrayList<Float> achv) {
        this.product_name = product_name;
        this.qnty = qnty;
        this.value = value;
        this.achv = achv;
        this.mContext = con;
    }



    public RmTargetquantityProductShowAdapter(Context con, ArrayList<String> product_name,
                                              ArrayList<Integer> qnty, ArrayList<Float> value,
                                              ArrayList<Float> achv, ArrayList<Float> growth
    ) {
        this.product_name = product_name;
        this.qnty = qnty;
        this.value = value;
        this.achv = achv;
        this.growth = growth;
        this.mContext = con;
    }


    RmTargetquantityProductShowAdapter(Context con, ArrayList<String> product_name,
                                       ArrayList<Integer> qnty, ArrayList<Float> value,
                                       ArrayList<Float> achv,ArrayList<Float> growth,
                                       ArrayList<String> segment_code
    ) {
        this.product_name = product_name;
        this.qnty = qnty;
        this.value = value;
        this.achv = achv;
        this.growth = growth;
        this.segment_code = segment_code;
        this.mContext = con;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return product_name.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return product_name.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public float values(int position) {
        return values(position);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final RmTargetquantityProductShowAdapter.ViewHolder holder = new RmTargetquantityProductShowAdapter.ViewHolder();

        rowView = inflater.inflate(R.layout.rmviewbytargetquantity_show, parent, false);
        serial = (TextView) rowView.findViewById(R.id.serial_report);


        holder.product_name = (TextView) rowView.findViewById(R.id.product_name_target_quant);
        holder.qnty = (TextView) rowView.findViewById(R.id.amqnty_target_quant);
        holder.value = (TextView) rowView.findViewById(R.id.amvalue_target_quant);
        holder.achv = (TextView) rowView.findViewById(R.id.achv_target_quant);
        holder.growth = (TextView) rowView.findViewById(R.id.growth_target_quant);
        serial.setText(String.valueOf(position + 1));
        holder.product_name.setText(product_name.get(position));
        holder.qnty.setText(qnty.get(position).toString());
        holder.qnty.setTag(position);
        String number = value.get(position).toString();
        double amount = Double.parseDouble(number);
        DecimalFormat formatter = new DecimalFormat("#,##,###");
        String formatted = formatter.format(amount);
        holder.value.setText(formatted);
        String achvnumber = achv.get(position).toString();
        holder.achv.setText(achv.get(position).toString()+"%");

        holder.growth.setText(growth.get(position)+"%");

        return rowView;

    }

    private class ViewHolder {
        TextView qnty;
        TextView value;
        TextView product_name;
        TextView achv;
        TextView growth;


    }
}


