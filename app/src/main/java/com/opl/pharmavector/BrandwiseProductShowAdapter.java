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
public class BrandwiseProductShowAdapter extends BaseAdapter {
    TextView serial;
    //TextView p_name;
    //EditText quantity;
    CheckBox check;
    Context mContext;
    ArrayList<String> product_name;
    ArrayList<String> mpo_code;
    //ArrayList<String> p_rates;
    ArrayList<Integer> qnty;
    ArrayList<String> value;
    OnClickListener callBack;
    ArrayList<String> achv;
    ArrayList<String> target_value;
    ArrayList<String> sale_value;
    ArrayList<String> growth_value;
    ArrayList<String> ffName;
    ArrayList<String> monGrowth;
    ArrayList<String> cumGrowth;
    //private int pos = 1;
    View rowView;
    public static int last_position;
    //public static String qnty;
    //String strHolder;
    //static ArrayList<Integer> click_pos;
    static ArrayList<String> qntyID;
    static ArrayList<String> qntyVal;

    BrandwiseProductShowAdapter(Context con, ArrayList<String> product_name,
                                ArrayList<Integer> qnty, ArrayList<String> value, ArrayList<String> achv, String mpo_code) {
        this.product_name = product_name;
        this.qnty = qnty;
        this.value = value;
        this.achv = achv;
        this.mContext = con;
    }

    BrandwiseProductShowAdapter(Context con, ArrayList<String> product_name,
                                ArrayList<Integer> qnty, ArrayList<String> value, ArrayList<String> achv, ArrayList<String> mpo_code) {
        this.product_name = product_name;
        this.qnty = qnty;
        this.value = value;
        this.achv = achv;
        this.mpo_code = mpo_code;
        this.mContext = con;
    }

    BrandwiseProductShowAdapter(Context con, ArrayList<String> product_name,
                                ArrayList<Integer> qnty, ArrayList<String> value, ArrayList<String> achv, ArrayList<String> mpo_code, ArrayList<String> target_value
            , ArrayList<String> sale_value) {
        this.product_name = product_name;
        this.qnty = qnty;
        this.value = value;
        this.achv = achv;
        this.mpo_code = mpo_code;
        this.sale_value = sale_value;
        this.target_value = target_value;
        this.mContext = con;
    }

    BrandwiseProductShowAdapter(Context con, ArrayList<String> product_name, ArrayList<Integer> qnty, ArrayList<String> value, ArrayList<String> achv,
                                ArrayList<String> mpo_code, ArrayList<String> target_value, ArrayList<String> sale_value, ArrayList<String> growth_value) {
        this.product_name = product_name;
        this.qnty = qnty;
        this.value = value;
        this.achv = achv;
        this.mpo_code = mpo_code;
        this.sale_value = sale_value;
        this.target_value = target_value;
        this.growth_value = growth_value;
        this.mContext = con;
    }

    BrandwiseProductShowAdapter(Context con, ArrayList<String> product_name, ArrayList<Integer> qnty, ArrayList<String> value, ArrayList<String> achv,
                                ArrayList<String> mpo_code, ArrayList<String> target_value, ArrayList<String> sale_value,
                                ArrayList<String> growth_value, ArrayList<String> ff_name, ArrayList<String> mon_growth, ArrayList<String> cum_growth) {
        this.product_name = product_name;
        this.qnty = qnty;
        this.value = value;
        this.achv = achv;
        this.mpo_code = mpo_code;
        this.sale_value = sale_value;
        this.target_value = target_value;
        this.growth_value = growth_value;
        this.ffName = ff_name;
        this.monGrowth = mon_growth;
        this.cumGrowth = cum_growth;
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
        //rowView = inflater.inflate(R.layout.amviewbydateproduct_show, parent, false);
        //rowView = inflater.inflate(R.layout.amviewbydateproduct_show1, parent, false);
        rowView = inflater.inflate(R.layout.brandwisesaleadapter, parent, false);

        serial = (TextView) rowView.findViewById(R.id.serial);
        holder.mpo_code = (TextView) rowView.findViewById(R.id.mpo_brand); //mpo code
        holder.product_name = (TextView) rowView.findViewById(R.id.brand_target_quant); //target quantity
        holder.qnty = (TextView) rowView.findViewById(R.id.amqnty_brand); //target val
        holder.value = (TextView) rowView.findViewById(R.id.salequant_brand); //sale quant
        holder.salevalue = (TextView) rowView.findViewById(R.id.salevalue_brand); //sale quant
        holder.achv = (TextView) rowView.findViewById(R.id.achivement_sale);
        //holder.growth = (TextView) rowView.findViewById(R.id.growth_sale);
        holder.terriName = (TextView) rowView.findViewById(R.id.terriName);
        holder.lmGrowth = (TextView) rowView.findViewById(R.id.lmGrowth);
        holder.monGrowth = (TextView) rowView.findViewById(R.id.monGrowth);
        holder.yearGrowth = (TextView) rowView.findViewById(R.id.yearGrowth);
        serial.setText(String.valueOf(position + 1));

        String number1 = sale_value.get(position);
        double amount1 = Double.parseDouble(number1);
        DecimalFormat formatter1 = new DecimalFormat("#,##,###.##");
        String formatted1 = formatter1.format(amount1);

        String number2 = target_value.get(position);
        double amount2 = Double.parseDouble(number2);
        DecimalFormat formatter2 = new DecimalFormat("#,##,###.##");
        String formatted2 = formatter2.format(amount2);

        String number3 = value.get(position);
        double amount3 = Double.parseDouble(number3);
        DecimalFormat formatter3 = new DecimalFormat("#,##,###.##");
        String formatted3 = formatter3.format(amount3);

        holder.mpo_code.setText(mpo_code.get(position));
        holder.product_name.setText(sale_value.get(position));
        holder.qnty.setText(formatted2);
        holder.salevalue.setText(formatted3);
        holder.value.setText(product_name.get(position));
        holder.achv.setText(achv.get(position));
        //holder.growth.setText(growth_value.get(position));
        holder.terriName.setText(ffName.get(position));
        holder.lmGrowth.setText(monGrowth.get(position));
        holder.monGrowth.setText(growth_value.get(position));
        holder.yearGrowth.setText(cumGrowth.get(position));

        Log.w("mpo_code", mpo_code.get(position));
        Log.w("product_name", product_name.get(position));
        Log.w("salequant", value.get(position));
        Log.w("achv", achv.get(position));
        Log.w("target_value", target_value.get(position));
        Log.w("sale_value", sale_value.get(position));
        Log.w("growth_value", growth_value.get(position));
        return rowView;
    }

    private class ViewHolder {
        TextView mpo_code;
        TextView product_name; //target_quantity
        TextView qnty; //targetvalue
        TextView value; //salequant
        TextView salevalue; //salevalue;
        TextView achv; //achviement
        TextView growth, terriName, lmGrowth, monGrowth, yearGrowth;
    }
}


