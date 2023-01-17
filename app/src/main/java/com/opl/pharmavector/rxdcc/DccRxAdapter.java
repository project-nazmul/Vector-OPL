package com.opl.pharmavector.rxdcc;
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
public class DccRxAdapter extends BaseAdapter {
    TextView serial;
    CheckBox check;
    Context mContext;
    ArrayList<String> product_name;
    ArrayList<Integer> qnty;
    ArrayList<String> value;
    ArrayList<String> achv;
    ArrayList<String> mpo_code;
    ArrayList<String> growth;

    ArrayList<String> rtarget;
    ArrayList<String> sgtarget;
    ArrayList<String> bltarget;
    ArrayList<String> totaltarget;

    View rowView;
    public static int last_position;


    DccRxAdapter(Context con, ArrayList<String> product_name, ArrayList<Integer> qnty, ArrayList<String> value,
                  ArrayList<String> achv,ArrayList<String> mpo_code) {

        this.product_name = product_name;
        this.qnty = qnty;
        this.value = value;
        this.achv = achv;
        this.mpo_code= mpo_code;
        this.mContext = con;
    }
    DccRxAdapter(Context con, ArrayList<String> product_name, ArrayList<Integer> qnty, ArrayList<String> value,
                  ArrayList<String> achv, ArrayList<String> growth,ArrayList<String> mpo_code) {

        this.product_name = product_name;
        this.qnty = qnty;
        this.value = value;
        this.achv = achv;
        this.growth = growth;
        this.mpo_code= mpo_code;
        this.mContext = con;
    }



    public DccRxAdapter(Context con, ArrayList<String> product_name, ArrayList<Integer> qnty, ArrayList<String> value,
                         ArrayList<String> achv, ArrayList<String> growth, ArrayList<String> mpo_code,
                         ArrayList<String> rtarget,
                         ArrayList<String> sgtarget,
                         ArrayList<String> bltarget,
                         ArrayList<String> totaltarget
    ) {

        this.product_name = product_name;
        this.qnty = qnty;
        this.value = value;
        this.achv = achv;
        this.growth = growth;
        this.mpo_code= mpo_code;

        this.rtarget = rtarget;
        this.sgtarget = sgtarget;
        this.bltarget = bltarget;
        this.totaltarget= totaltarget;

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
        return totaltarget.get(position)+"-"+growth.get(position);
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
        final ViewHolder holder = new ViewHolder();
        rowView = inflater.inflate(R.layout.adapter_rx_dcc_gen_camp, parent, false);

        serial =  rowView.findViewById(R.id.serial_report);
        holder.brand_name =  rowView.findViewById(R.id.brand_name);
        holder.regular_prescription =  rowView.findViewById(R.id.regular_prescription);
        holder.special_prescription =  rowView.findViewById(R.id.special_prescription);
        holder.brandloyalty_prescription =  rowView.findViewById(R.id.brandloyalty_prescription);
        holder.total_prescription =  rowView.findViewById(R.id.total_prescription);
        holder.mpo_code =  rowView.findViewById(R.id.mpo_code);

        holder.tv_rtarget =  rowView.findViewById(R.id.rtarget);
        holder.tv_sgtarget =  rowView.findViewById(R.id.sgtarget);
        holder.tv_bltarget =  rowView.findViewById(R.id.bltarget);
        holder.tv_totaltarget =  rowView.findViewById(R.id.totaltarget);
        serial.setText(String.valueOf(position + 1));

        holder.brand_name.setText(product_name.get(position));
        holder.regular_prescription.setText(growth.get(position));
        holder.special_prescription.setText(qnty.get(position).toString());
        holder.brandloyalty_prescription.setText(value.get(position));
        holder.total_prescription.setText(achv.get(position));
        holder.mpo_code.setText( mpo_code.get(position).toString());

        holder.tv_rtarget.setText(rtarget.get(position).toString());
        holder.tv_sgtarget.setText(sgtarget.get(position).toString());
        holder.tv_bltarget.setText(bltarget.get(position).toString());
        holder.tv_totaltarget.setText(totaltarget.get(position).toString());
        return rowView;

    }

    private class ViewHolder {

        TextView brand_name;
        TextView regular_prescription;
        TextView special_prescription;
        TextView brandloyalty_prescription;
        TextView total_prescription;
        TextView mpo_code;

        TextView tv_rtarget;
        TextView tv_bltarget;
        TextView tv_sgtarget;
        TextView tv_totaltarget;

    }
}

