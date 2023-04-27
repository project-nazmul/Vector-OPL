package com.opl.pharmavector;

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
public class DcrreportvalueProductShowAdapter extends BaseAdapter {
    TextView serial;
    // TextView p_name;
    // EditText quanty;
    CheckBox check;
    Context mContext;
    ArrayList<String> product_name;
    ArrayList<String> sl;
    // ArrayList<String> p_rates;
    ArrayList<String> qnty;
    ArrayList<String> value;

    ArrayList<String> qnty_3;
    ArrayList<String> value4;
    ArrayList<String> value5;
    ArrayList<String> value6;
    ArrayList<String> value7;
    View.OnClickListener callBack;
    // private int pos=1;
    View rowView;
    public static int last_position;
    //	public static String qnty;
    // String strHolder;
    // static ArrayList<Integer> click_pos;
    static ArrayList<String> qntyID;
    static ArrayList<String> qntyVal;

    DcrreportvalueProductShowAdapter(Context con, ArrayList<String> product_name, ArrayList<String> qnty_3, ArrayList<String> value_3) {
        this.product_name = product_name;
        this.qnty = qnty_3;
        this.value = value_3;
        this.mContext = con;
    }


    DcrreportvalueProductShowAdapter(Context con, ArrayList<String> product_name, ArrayList<String> qnty_3, ArrayList<String> value_3,ArrayList<String> value_4) {
        this.product_name = product_name;
        this.qnty = qnty_3;
        this.value = value_3;
        this.value4 = value_4;
        this.mContext = con;

    }


    DcrreportvalueProductShowAdapter(Context con, ArrayList<String> product_name, ArrayList<String> qnty_3, ArrayList<String> value_3,ArrayList<String> value_4,ArrayList<String> value_5) {
        this.product_name = product_name;
        this.qnty = qnty_3;
        this.value = value_3;
        this.value4 = value_4;
        this.value5 = value_5;
        this.mContext = con;

    }



    DcrreportvalueProductShowAdapter(Context con,ArrayList<String> sl, ArrayList<String> product_name, ArrayList<String> qnty_3, ArrayList<String> value_3,
                                     ArrayList<String> value_4,ArrayList<String> value_5,ArrayList<String> value_6,ArrayList<String> value_7) {

        this.sl = sl;
        this.product_name = product_name;
        this.qnty = qnty_3;
        this.value = value_3;
        this.value4 = value_4;
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
        rowView = inflater.inflate(R.layout.viewbydatedcrreport, parent, false);

        serial = (TextView) rowView.findViewById(R.id.serial);
        holder.product_name = (TextView) rowView.findViewById(R.id.dcr_visit_to);
        holder.sl = (TextView) rowView.findViewById(R.id.serial);

        holder.qnty = (TextView) rowView.findViewById(R.id.dcr_name);

        holder.value = (TextView) rowView.findViewById(R.id.dcr_type);
        holder.value4 = (TextView) rowView.findViewById(R.id.dcr_total);
        holder.value5 = (TextView) rowView.findViewById(R.id.dcr_given);



        holder.value6 = (TextView) rowView.findViewById(R.id.dcr_remain);
        holder.value7 = (TextView) rowView.findViewById(R.id.dcr_shift);



        holder.sl.setText(sl.get(position));



        holder.product_name.setText(product_name.get(position));


        Log.w("productnamegetposition",product_name.get(position));

        String number1 = qnty.get(position).toString();



        Log.w("number1",number1);

        String amount1 = (number1);

        holder.qnty.setText(amount1);
        holder.qnty.setTag(position);

       // holder.sl.setText(sl.get(position).toString());

        holder.value.setText(value.get(position).toString());

        Log.w("value",value.get(position).toString());




        holder.value4.setText(value4.get(position).toString());


        Log.w("extra_valu4",value4.get(position).toString());


        holder.value5.setText(value5.get(position).toString());

        Log.w("extra_valu5",value5.get(position).toString());


        holder.value6.setText(value6.get(position).toString());
        Log.w("extra_valu6",value5.get(position).toString());


        holder.value7.setText(value7.get(position).toString());
        Log.w("extra_valu7",value5.get(position).toString());

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
