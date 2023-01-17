
package com.opl.pharmavector.doctorservice;

import java.util.ArrayList;

import com.opl.pharmavector.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

@SuppressLint("ViewHolder")
public class DocServiceStatus extends BaseAdapter {

    TextView serial;
    CheckBox check;
    Context mContext;
    ArrayList<String> product_name;
    ArrayList<String> sl;
    ArrayList<String> qnty;
    ArrayList<String> value;
    ArrayList<String> item_name;
    ArrayList<String> value4;
    ArrayList<String> value5;
    ArrayList<String> value6;
    ArrayList<String> value7;
    View.OnClickListener callBack;
    View rowView;
    public static int last_position;
    static ArrayList<String> qntyID;
    static ArrayList<String> qntyVal;


    DocServiceStatus(Context con, ArrayList<String> product_name, ArrayList<String> item_name, ArrayList<String> type) {
        this.product_name = product_name;
        this.qnty = item_name;
        this.value = type;
        this.mContext = con;
    }


    DocServiceStatus(Context con, ArrayList<String> product_name, ArrayList<String> item_name, ArrayList<String> type, ArrayList<String> quant) {
        this.product_name = product_name;
        this.qnty = item_name;
        this.value = type;
        this.value4 = quant;
        this.mContext = con;

    }


    DocServiceStatus(Context con, ArrayList<String> product_name, ArrayList<String> item_name, ArrayList<String> type, ArrayList<String> quant, ArrayList<String> value_5) {
        this.product_name = product_name;
        this.qnty = item_name;
        this.value = type;
        this.value4 = quant;
        this.value5 = value_5;
        this.mContext = con;

    }


    //DcrreportvalueProductShowAdapter

    DocServiceStatus(Context con, ArrayList<String> sl, ArrayList<String> product_name, ArrayList<String> item_name, ArrayList<String> type,
                     ArrayList<String> quant, ArrayList<String> value_5, ArrayList<String> value_6, ArrayList<String> value_7) {

        this.sl = sl;
        this.product_name = product_name;
        this.qnty = item_name;
        this.value = type;
        this.value4 = quant;
        this.value5 = value_5;
        this.value6 = value_6;
        this.value7 = value_7;
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

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final ViewHolder holder = new ViewHolder();

        //  rowView = inflater.inflate(R.layout.viewmporeportforam, parent, false);

        //rowView = inflater.inflate(R.layout.amviewdoctorreport, parent, false);
        rowView = inflater.inflate(R.layout.docserviceadapter, parent, false);


        serial = (TextView) rowView.findViewById(R.id.serial);
        holder.product_name = (TextView) rowView.findViewById(R.id.product_name);
        holder.sl = (TextView) rowView.findViewById(R.id.serial);

        holder.qnty = (TextView) rowView.findViewById(R.id.item_name);

        holder.value = (TextView) rowView.findViewById(R.id.type);
        holder.value4 = (TextView) rowView.findViewById(R.id.quant);
        holder.value5 = (TextView) rowView.findViewById(R.id.value_5);


        holder.value6 = (TextView) rowView.findViewById(R.id.value_6);
        holder.value7 = (TextView) rowView.findViewById(R.id.value_7);

        // String test=Sl.get(position);
        // serial.setText(String.valueOf(position + 1));


        //String sl = sl.get(position).toString();
        // serial.setText(String.valueOf(sl));

        holder.sl.setText(sl.get(position));


        holder.product_name.setText(product_name.get(position));


        Log.w("productnamegetposition", product_name.get(position));

        String number1 = qnty.get(position).toString();

        Log.w("number1", number1);

        String amount1 = (number1);

        holder.qnty.setText(amount1);
        holder.qnty.setTag(position);

        // holder.sl.setText(sl.get(position).toString());

        holder.value.setText(value.get(position).toString());
        Log.w("value", value.get(position).toString());


        holder.value4.setText(value4.get(position).toString());
        Log.w("extra_valu4", value4.get(position).toString());


        holder.value5.setText(value5.get(position).toString());
        Log.w("extra_valu5", value5.get(position).toString());


        holder.value6.setText(value6.get(position).toString());
        Log.w("extra_valu6", value5.get(position).toString());


        holder.value7.setText(value7.get(position).toString());
        Log.w("extra_valu7", value5.get(position).toString());

        return rowView;

    }

    private class ViewHolder {
        TextView sl;
        TextView qnty;
        TextView value;
        TextView product_name;
        TextView value4;
        TextView value5;
        TextView value6;
        TextView value7;

    }
}


