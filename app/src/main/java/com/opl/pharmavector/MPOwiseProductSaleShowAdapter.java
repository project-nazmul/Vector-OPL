//MPOwiseProductSaleShowAdapter


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
public class MPOwiseProductSaleShowAdapter extends BaseAdapter {
    TextView serial;
    // TextView p_name;
    // EditText quanty;
    CheckBox check;
    Context mContext;
    ArrayList<String> product_name;
    ArrayList<String> mpo_code;
    // ArrayList<String> p_rates;
    ArrayList<Integer> qnty;
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








    MPOwiseProductSaleShowAdapter(Context con, ArrayList<String> product_name,
                                  ArrayList<Integer> qnty, ArrayList<String> value, ArrayList<String> achv, String mpo_code) {
        this.product_name = product_name;
        this.qnty = qnty;
        this.value = value;
        this.achv = achv;
        this.mContext = con;
    }



    MPOwiseProductSaleShowAdapter(Context con, ArrayList<String> product_name,
                                  ArrayList<Integer> qnty, ArrayList<String> value, ArrayList<String> achv,ArrayList<String> mpo_code) {
        this.product_name = product_name;
        this.qnty = qnty;
        this.value = value;
        this.achv = achv;
        this.mpo_code = mpo_code;

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
        final ViewHolder holder = new ViewHolder();
        //rowView = inflater.inflate(R.layout.amviewbydateproduct_show, parent, false);

        rowView = inflater.inflate(R.layout.amviewbydateproduct_show1, parent, false);


        serial = (TextView) rowView.findViewById(R.id.serial);
        holder.product_name = (TextView) rowView.findViewById(R.id.product_name);
        holder.mpo_code = (TextView) rowView.findViewById(R.id.mpo_ter);

        holder.qnty = (TextView) rowView.findViewById(R.id.amqnty2);
        holder.value = (TextView) rowView.findViewById(R.id.amvalue2);
        holder.achv = (TextView) rowView.findViewById(R.id.amachivement1);


        serial.setText(String.valueOf(position + 1));

        holder.product_name.setText(product_name.get(position));


        String number1 = qnty.get(position).toString();
        //String number = "100000000";
        double amount1 = Double.parseDouble(number1);
        DecimalFormat formatter1 = new DecimalFormat("#,##,###.##");
        String formatted3 = formatter1.format(amount1);
        holder.qnty.setText(formatted3);

        holder.qnty.setTag(position);


        holder.mpo_code.setText(mpo_code.get(position));

        holder.value.setText(value.get(position));

        Log.w("value",value.get(position));
        holder.achv.setText(achv.get(position));

        Log.w("achv",achv.get(position));


        return rowView;

    }

    private class ViewHolder {
        TextView qnty;
        TextView value;
        TextView product_name;
        TextView mpo_code;
        TextView achv;

        // int click_pos;
        // int last_position;
    }
}

