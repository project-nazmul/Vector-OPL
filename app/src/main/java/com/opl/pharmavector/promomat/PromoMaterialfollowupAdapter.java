package com.opl.pharmavector.promomat;

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
public class PromoMaterialfollowupAdapter extends BaseAdapter {
    TextView serial;
    // TextView p_name;
    // EditText quanty;
    CheckBox check;
    Context mContext;
    ArrayList<String> product_name;
    ArrayList<String> mpo_code;
    ArrayList<String> growth_val;
    //growth_val
    // ArrayList<String> p_rates;
    ArrayList<String> qnty;
    ArrayList<String> value;
    OnClickListener callBack;
    ArrayList<String> achv;
    // private int pos=1;
    View rowView;
    public static int last_position;
    //	public static String qnty;
    // String strHolder;
    // static ArrayList<Integer> click_pos;
    static ArrayList<String> qntyID;
    static ArrayList<String> qntyVal;


    PromoMaterialfollowupAdapter(Context con, ArrayList<String> product_name,
                                ArrayList<String> qnty, ArrayList<String> value, ArrayList<String> achv, String mpo_code) {
        this.product_name = product_name;
        this.qnty = qnty;
        this.value = value;
        this.achv = achv;
        this.mContext = con;
    }


    PromoMaterialfollowupAdapter(Context con, ArrayList<String> product_name,
                                ArrayList<String> qnty, ArrayList<String> value, ArrayList<String> achv, ArrayList<String> mpo_code) {
        this.product_name = product_name;
        this.qnty = qnty;
        this.value = value;
        this.achv = achv;
        this.mpo_code = mpo_code;

        this.mContext = con;
    }


    PromoMaterialfollowupAdapter(Context con, ArrayList<String> product_name,
                                ArrayList<String> qnty, ArrayList<String> value, ArrayList<String> achv, ArrayList<String> mpo_code,
                                ArrayList<String> growth_val) {
        this.product_name = product_name;
        this.qnty = qnty;
        this.value = value;
        this.achv = achv;
        this.mpo_code = mpo_code;
        this.growth_val = growth_val;
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
        return mpo_code.get(position);

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
        rowView = inflater.inflate(R.layout.promomaterial_sample_followup, parent, false);


        serial = (TextView) rowView.findViewById(R.id.serial);
        holder.product_name = (TextView) rowView.findViewById(R.id.product_name);
        holder.mpo_code = (TextView) rowView.findViewById(R.id.mpo_ter);
        holder.qnty = (TextView) rowView.findViewById(R.id.amqnty2);
        holder.value = (TextView) rowView.findViewById(R.id.amvalue2);
        holder.achv = (TextView) rowView.findViewById(R.id.amachivement1);
        holder.growth_val = (TextView) rowView.findViewById(R.id.growth_col);
        serial.setText(String.valueOf(position + 1));




        /*=====================TARGET==============================*/
        String number01 = product_name.get(position);
        holder.product_name.setText(number01);
        String number1 = qnty.get(position).toString();
        holder.qnty.setText(number1);
        holder.qnty.setTag(number1);
        String number = mpo_code.get(position);
        holder.mpo_code.setText(mpo_code.get(position));
        holder.growth_val.setText(growth_val.get(position));
        holder.value.setText(value.get(position));
        String number04 = achv.get(position);
        holder.achv.setText(number04);

        Log.w("achv", achv.get(position));


        return rowView;

    }

    private class ViewHolder {
        TextView qnty;
        TextView value;
        TextView product_name;
        TextView mpo_code;
        TextView growth_val;
        TextView achv;
    }
}

