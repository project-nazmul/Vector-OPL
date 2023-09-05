//MPOwiseAchvfollowupAdapter
package com.opl.pharmavector;

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

@SuppressLint("ViewHolder")
public class MPOwiseAchvfollowupAdapter extends BaseAdapter {
    TextView serial;
    //TextView p_name;
    //EditText quanty;
    CheckBox check;
    Context mContext;
    ArrayList<String> product_name;
    ArrayList<String> mpo_code;
    ArrayList<String> mpo_name;
    ArrayList<String> growth_val;
    //growth_val
    //ArrayList<String> p_rates;
    ArrayList<Integer> qnty;
    ArrayList<String> value;
    OnClickListener callBack;
    ArrayList<String> achv;
    //private int pos=1;
    View rowView;
    public static int last_position;
    //public static String qnty;
    //String strHolder;
    //static ArrayList<Integer> click_pos;
    static ArrayList<String> qntyID;
    static ArrayList<String> qntyVal;

    MPOwiseAchvfollowupAdapter(Context con, ArrayList<String> product_name, ArrayList<Integer> qnty, ArrayList<String> value, ArrayList<String> achv, String mpo_code) {
        this.product_name = product_name;
        this.qnty = qnty;
        this.value = value;
        this.achv = achv;
        this.mContext = con;
    }

    MPOwiseAchvfollowupAdapter(Context con, ArrayList<String> product_name, ArrayList<Integer> qnty, ArrayList<String> value, ArrayList<String> achv, ArrayList<String> mpo_code) {
        this.product_name = product_name;
        this.qnty = qnty;
        this.value = value;
        this.achv = achv;
        this.mpo_code = mpo_code;
        this.mContext = con;
    }

    MPOwiseAchvfollowupAdapter(Context con, ArrayList<String> product_name,
                               ArrayList<Integer> qnty, ArrayList<String> value,
                               ArrayList<String> achv, ArrayList<String> mpo_code, ArrayList<String> growth_val) {
        this.product_name = product_name;
        this.qnty = qnty;
        this.value = value;
        this.achv = achv;
        this.mpo_code = mpo_code;
        this.growth_val = growth_val;
        this.mContext = con;
    }

    MPOwiseAchvfollowupAdapter(Context con, ArrayList<String> product_name,
                               ArrayList<Integer> qnty, ArrayList<String> value,
                               ArrayList<String> achv, ArrayList<String> mpo_code,
                               ArrayList<String> mpo_name, ArrayList<String> growth_val) {
        this.product_name = product_name;
        this.qnty = qnty;
        this.value = value;
        this.achv = achv;
        this.mpo_code = mpo_code;
        this.mpo_name = mpo_name;
        this.growth_val = growth_val;
        this.mContext = con;
    }

    @Override
    public int getCount() {
        return product_name.size();
    }

    @Override
    public Object getItem(int position) {
        return mpo_code.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public float values(int position) {
        return values(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final ViewHolder holder = new ViewHolder();
        //rowView = inflater.inflate(R.layout.amviewbydateproduct_show1, parent, false);
        rowView = inflater.inflate(R.layout.amviewbydateproduct_show3, parent, false);
        serial = (TextView) rowView.findViewById(R.id.serial);
        holder.product_name = (TextView) rowView.findViewById(R.id.product_name);
        holder.mpo_code = (TextView) rowView.findViewById(R.id.mpo_ter);
        //holder.mpo_name = (TextView) rowView.findViewById(R.id.mpo_name);
        holder.qnty = (TextView) rowView.findViewById(R.id.amqnty2);
        holder.value = (TextView) rowView.findViewById(R.id.amvalue2);
        holder.achv = (TextView) rowView.findViewById(R.id.amachivement1);
        holder.growth_val = (TextView) rowView.findViewById(R.id.growth_col);
        serial.setText(String.valueOf(position + 1));
        Log.w("productname---adap--", product_name.get(position));

        /*=====================TARGET==============================*/
        String number01 = product_name.get(position);
        double amount = Double.parseDouble(number01);
        DecimalFormat formatter = new DecimalFormat("#,##,###.##");
        String formatted = formatter.format(amount);
        Log.w("saletarget---ADAPTER---", formatted);
        holder.product_name.setText(formatted);

        /*=====================TARGET==============================*/
        String number1 = qnty.get(position).toString();
        double amount1 = Double.parseDouble(number1);
        DecimalFormat formatter1 = new DecimalFormat("#,##,###.##");
        String formatted3 = formatter1.format(amount1);
        holder.qnty.setText(formatted3);
        holder.qnty.setTag(position);
        Log.w("qnty", formatted3);
        String number = mpo_code.get(position);
        Log.w("qnty", number);
        holder.mpo_code.setText(mpo_code.get(position));
        //holder.mpo_name.setText(mpo_name.get(position));
        holder.growth_val.setText(growth_val.get(position));
        //String valu_format = value.get(position).toString();
        //double val_for=Double.parseDouble(valu_format);
        //DecimalFormat formatter2 = new DecimalFormat("#,##,###.##");
        //String formatted4 = formatter2.format(val_for);
        Log.w("value", value.get(position));
        holder.value.setText(value.get(position));
        //holder.value.setText(formatted4);
        String number04 = achv.get(position);
        double amount4 = Double.parseDouble(number04);
        DecimalFormat formatter4 = new DecimalFormat("#,##,###.##");
        String formatted4 = formatter4.format(amount4);
        Log.w("sale_target", formatted);
        holder.achv.setText(formatted4);
        Log.w("achv", achv.get(position));
        return rowView;
    }

    private class ViewHolder {
        TextView qnty;
        TextView value;
        TextView product_name;
        TextView mpo_code;
        TextView mpo_name;
        TextView growth_val;
        TextView achv;
        //int click_pos;
        //int last_position;
    }
}

