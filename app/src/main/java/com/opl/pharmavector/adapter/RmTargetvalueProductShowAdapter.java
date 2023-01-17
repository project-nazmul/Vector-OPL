package com.opl.pharmavector.adapter;
//RmTargetvalueProductShowAdapter



import java.text.DecimalFormat;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.opl.pharmavector.R;

@SuppressLint("ViewHolder")
public class RmTargetvalueProductShowAdapter extends BaseAdapter {
    TextView serial;
    // TextView p_name;
    // EditText quanty;
    CheckBox check;
    Context mContext;
    ArrayList<String> product_name;
    // ArrayList<String> p_rates;
    ArrayList<String> qnty;
    ArrayList<String> value;
    OnClickListener callBack;
    ArrayList<Float> achv;
    ArrayList<Float> growth;
    // private int pos=1;
    View rowView;
    public static int last_position;
    //	public static String qnty;
    // String strHolder;
    // static ArrayList<Integer> click_pos;
    static ArrayList<String> qntyID;
    static ArrayList<String> qntyVal;

    RmTargetvalueProductShowAdapter(Context con, ArrayList<String> product_name,
                                    ArrayList<String> qnty, ArrayList<String> value) {
        this.product_name = product_name;
        this.qnty = qnty;
        this.value = value;
        this.mContext = con;
    }






    RmTargetvalueProductShowAdapter(Context con, ArrayList<String> product_name,
                                    ArrayList<String> qnty, ArrayList<String> value,ArrayList<Float> achv) {
        this.product_name = product_name;
        this.qnty = qnty;
        this.value = value;
        this.achv = achv;
        this.mContext = con;
    }


    public RmTargetvalueProductShowAdapter(Context con, ArrayList<String> product_name,
                                           ArrayList<String> qnty, ArrayList<String> value,
                                           ArrayList<Float> achv, ArrayList<Float> growth) {
        this.product_name = product_name;
        this.qnty = qnty;
        this.value = value;
        this.achv = achv;
        this.growth = growth;
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
        final RmTargetvalueProductShowAdapter.ViewHolder holder = new RmTargetvalueProductShowAdapter.ViewHolder();

        rowView = inflater.inflate(R.layout.amviewbydateproduct_show, parent, false);
        serial = (TextView) rowView.findViewById(R.id.serial);
        holder.product_name = (TextView) rowView.findViewById(R.id.product_name_target_value);
        holder.qnty = (TextView) rowView.findViewById(R.id.amqnty1_target_value);
        holder.value = (TextView) rowView.findViewById(R.id.amvalue1_target_value);
        holder.achv = (TextView) rowView.findViewById(R.id.amachivement_target_value);
        holder.growth = (TextView) rowView.findViewById(R.id.growth_target_value);

        serial.setText(String.valueOf(position + 1));
        holder.product_name.setText(product_name.get(position));


        String number1 = qnty.get(position).toString();
        //String number = "100000000";
        double amount1 = Double.parseDouble(number1);
        DecimalFormat formatter1 = new DecimalFormat("#,##,###.##");
        String formatted3 = formatter1.format(amount1);
        holder.qnty.setText(formatted3);

        //holder.qnty.setText(qnty.get(position).toString());

        holder.qnty.setTag(position);

        holder.value.setText(value.get(position).toString());

        //   Log.w("adapterachievment",achv.get(position).toString());

        holder.achv.setText(achv.get(position).toString()+"%");

        holder.growth.setText(growth.get(position).toString()+"%");






        return rowView;

    }

    private class ViewHolder {
        TextView qnty;
        TextView value;
        TextView product_name;
        TextView achv;
        TextView growth;

        // int click_pos;
        // int last_position;
    }
}



