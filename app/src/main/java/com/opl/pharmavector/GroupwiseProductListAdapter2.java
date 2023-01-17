//PcConferenceFollowupAdapter

//GroupwiseProductListAdapter2

package com.opl.pharmavector;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.opl.pharmavector.R;

@SuppressLint("ViewHolder")
public class GroupwiseProductListAdapter2 extends BaseAdapter {

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


    ArrayList<String> value8;
    ArrayList<String> value9;
    ArrayList<String> value10;
    ArrayList<String> value11;


    ArrayList<String> value12;
    ArrayList<String> value13;
    ArrayList<String> value14;
    ArrayList<String> value15;


    ArrayList<String> value16;
    ArrayList<String> value17;

    ArrayList<String> value18;

    ArrayList<String> value19;
    ArrayList<String> value20;
    View.OnClickListener callBack;
    // private int pos=1;
    View rowView;
    public static int last_position;
    static ArrayList<String> qntyID;
    static ArrayList<String> qntyVal;





    GroupwiseProductListAdapter2(Context con, ArrayList<String> product_name, ArrayList<String> qnty_3, ArrayList<String> value_doctor) {
        this.product_name = product_name;
        this.qnty = qnty_3;
        this.value = value_doctor;
        this.mContext = con;
    }


    GroupwiseProductListAdapter2(Context con, ArrayList<String> product_name, ArrayList<String> qnty_3, ArrayList<String> value_doctor,ArrayList<String> value_4) {
        this.product_name = product_name;
        this.qnty = qnty_3;
        this.value = value_doctor;
        this.value4 = value_4;
        this.mContext = con;

    }


    GroupwiseProductListAdapter2(Context con, ArrayList<String> product_name, ArrayList<String> qnty_3, ArrayList<String> value_doctor,ArrayList<String> value_4,ArrayList<String> value_5) {
        this.product_name = product_name;
        this.qnty = qnty_3;
        this.value = value_doctor;
        this.value4 = value_4;
        this.value5 = value_5;
        this.mContext = con;

    }



    GroupwiseProductListAdapter2(Context con,ArrayList<String> sl, ArrayList<String> product_name, ArrayList<String> qnty_3, ArrayList<String> value_doctor,
                                ArrayList<String> value_4,ArrayList<String> value_5,ArrayList<String> value_6,ArrayList<String> value_7) {

        this.sl = sl;
        this.product_name = product_name;
        this.qnty = qnty_3;
        this.value = value_doctor;
        this.value4 = value_4;
        this.value5 = value_5;
        this.value6 = value_6;
        this.value7 = value_7;
        this.mContext = con;


    }



    GroupwiseProductListAdapter2(Context con,ArrayList<String> sl, ArrayList<String> product_name, ArrayList<String> qnty_3, ArrayList<String> value_doctor,
                                ArrayList<String> value_4,ArrayList<String> value_5,ArrayList<String> value_6,ArrayList<String> value_7

            , ArrayList<String> value_8,ArrayList<String> value_9,ArrayList<String> value_10,ArrayList<String> value_11,
                                ArrayList<String> value_12,ArrayList<String> value_13,ArrayList<String> value_14,ArrayList<String> value_15


    ) {

        this.sl = sl;
        this.product_name = product_name;
        this.qnty = qnty_3;
        this.value = value_doctor;
        this.value4 = value_4;
        this.value5 = value_5;
        this.value6 = value_6;
        this.value7 = value_7;

        this.value8 = value_8;
        this.value9 = value_9;
        this.value10 = value_10;
        this.value11 = value_11;


        this.value12 = value_12;
        this.value13 = value_13;
        this.value14 = value_14;
        this.value15 = value_15;


        this.mContext = con;


    }





    GroupwiseProductListAdapter2(Context con,ArrayList<String> sl, ArrayList<String> product_name, ArrayList<String> qnty_3, ArrayList<String> value_doctor,
                                ArrayList<String> value_4,ArrayList<String> value_5,ArrayList<String> value_6,ArrayList<String> value_7

            , ArrayList<String> value_8,ArrayList<String> value_9,ArrayList<String> value_10,ArrayList<String> value_11,
                                ArrayList<String> value_12,ArrayList<String> value_13,ArrayList<String> value_14,ArrayList<String> value_15,
                                ArrayList<String> value_16,ArrayList<String> value_17


    ) {

        this.sl = sl;
        this.product_name = product_name;
        this.qnty = qnty_3;
        this.value = value_doctor;
        this.value4 = value_4;
        this.value5 = value_5;
        this.value6 = value_6;
        this.value7 = value_7;

        this.value8 = value_8;
        this.value9 = value_9;
        this.value10 = value_10;
        this.value11 = value_11;


        this.value12 = value_12;
        this.value13 = value_13;
        this.value14 = value_14;
        this.value15 = value_15;

        this.value16 = value_16;
        this.value17 = value_17;



        this.mContext = con;


    }






    GroupwiseProductListAdapter2(Context con,ArrayList<String> sl, ArrayList<String> product_name, ArrayList<String> qnty_3, ArrayList<String> value_doctor,
                                ArrayList<String> value_4,ArrayList<String> value_5,ArrayList<String> value_6,ArrayList<String> value_7

            , ArrayList<String> value_8,ArrayList<String> value_9,ArrayList<String> value_10,ArrayList<String> value_11,
                                ArrayList<String> value_12,ArrayList<String> value_13,ArrayList<String> value_14,ArrayList<String> value_15,
                                ArrayList<String> value_16,ArrayList<String> value_17,ArrayList<String> value_18


    ) {

        this.sl = sl;
        this.product_name = product_name;
        this.qnty = qnty_3;
        this.value = value_doctor;
        this.value4 = value_4;
        this.value5 = value_5;
        this.value6 = value_6;
        this.value7 = value_7;

        this.value8 = value_8;
        this.value9 = value_9;
        this.value10 = value_10;
        this.value11 = value_11;


        this.value12 = value_12;
        this.value13 = value_13;
        this.value14 = value_14;
        this.value15 = value_15;

        this.value16 = value_16;
        this.value17 = value_17;
        this.value18 = value_18;
        this.mContext = con;

    }








    GroupwiseProductListAdapter2(Context con,ArrayList<String> sl, ArrayList<String> product_name, ArrayList<String> qnty_3, ArrayList<String> value_doctor,
                                ArrayList<String> value_4,ArrayList<String> value_5,ArrayList<String> value_6,ArrayList<String> value_7

            , ArrayList<String> value_8,ArrayList<String> value_9,ArrayList<String> value_10,ArrayList<String> value_11,
                                ArrayList<String> value_12,ArrayList<String> value_13,ArrayList<String> value_14,ArrayList<String> value_15,
                                ArrayList<String> value_16,ArrayList<String> value_17,ArrayList<String> value_18,
                                ArrayList<String> value_19,ArrayList<String> value_20


    ) {

        this.sl = sl;
        this.product_name = product_name;
        this.qnty = qnty_3;
        this.value = value_doctor;
        this.value4 = value_4;
        this.value5 = value_5;
        this.value6 = value_6;
        this.value7 = value_7;

        this.value8 = value_8;
        this.value9 = value_9;
        this.value10 = value_10;
        this.value11 = value_11;


        this.value12 = value_12;
        this.value13 = value_13;
        this.value14 = value_14;
        this.value15 = value_15;

        this.value16 = value_16;
        this.value17 = value_17;
        this.value18 = value_18;
        this.value19 = value_19;
        this.value20 = value_20;
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
        return value16.get(position);
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
        //  rowView = inflater.inflate(R.layout.rmdcrfollowupadapter, parent, false);

        //rowView = inflater.inflate(R.layout.pcconferencefollowupadapter, parent, false);

        rowView = inflater.inflate(R.layout.grp_wise_prd_ord2_adapter, parent, false);






        serial = (TextView) rowView.findViewById(R.id.serial_1);
        holder.product_name = (TextView) rowView.findViewById(R.id.pc_product_name);


        holder.sl = (TextView) rowView.findViewById(R.id.serial_1);
        holder.qnty = (TextView) rowView.findViewById(R.id.qnty_31);
        holder.value = (TextView) rowView.findViewById(R.id.value_doctor);
        holder.value4 = (TextView) rowView.findViewById(R.id.value_41);
        holder.value5 = (TextView) rowView.findViewById(R.id.value_51);
        holder.value6 = (TextView) rowView.findViewById(R.id.value_61);
        holder.value7 = (TextView) rowView.findViewById(R.id.value_71);
        holder.value8 = (TextView) rowView.findViewById(R.id.value_81);
        holder.value9 = (TextView) rowView.findViewById(R.id.value_91);
        holder.value10 = (TextView) rowView.findViewById(R.id.value_101);
        holder.value11 = (TextView) rowView.findViewById(R.id.value_111);
        holder.value12 = (TextView) rowView.findViewById(R.id.value_121);
        holder.value13 = (TextView) rowView.findViewById(R.id.value_131);
        holder.value14 = (TextView) rowView.findViewById(R.id.value_14);
        holder.value19 = (TextView) rowView.findViewById(R.id.value_dis);
        holder.value20 = (TextView) rowView.findViewById(R.id.value_adj);




        holder.value_mpo = (TextView) rowView.findViewById(R.id.mpo_value_pc);


        holder.sl.setText(sl.get(position));



        holder.product_name.setText(product_name.get(position));


        Log.w("productnamegetposition",product_name.get(position));

        String number1 = qnty.get(position).toString();



        Log.w("number1",number1);

        String amount1 = (number1);

        holder.qnty.setText(amount1);
        holder.qnty.setTextColor(Color.rgb(213,0,249));

        holder.qnty.setTag(position);

        // holder.sl.setText(sl.get(position).toString());

        holder.value.setText(value.get(position).toString());
        holder.value.setTextColor(Color.rgb(213,0,249));
        holder.value4.setText(value4.get(position).toString());
        holder.value5.setText(value5.get(position).toString());
        holder.value6.setText(value6.get(position).toString());
        holder.value7.setText(value7.get(position).toString());
        holder.value8.setText(value8.get(position).toString());
        holder.value11.setText(value11.get(position).toString());
        Log.w("extra_valu11",value11.get(position).toString());





        Log.w("extra_valu8",value8.get(position).toString());



        String number = value9.get(position).toString();
		//String number = "100000000";
		double amount = Double.parseDouble(number);
		DecimalFormat formatter = new DecimalFormat("#,##,###");
		String formatted = formatter.format(amount);
        holder.value9.setText(formatted);



       // holder.value9.setText(value9.get(position).toString());

/*
String number = value9.get(position).toString();
		//String number = "100000000";
		double amount = Double.parseDouble(number);
		DecimalFormat formatter = new DecimalFormat("#,##,###");
		String formatted = formatter.format(amount);
		holder.value.setText(formatted);
 */












        holder.value12.setText(value12.get(position).toString());
        Log.w("extra_valu12",value12.get(position).toString());
        holder.value12.setTextColor(Color.rgb(213,0,249));

        holder.value13.setText(value13.get(position).toString());



        holder.value13.setTextColor(Color.rgb(213,0,249));
        Log.w("extra_valu13",value13.get(position).toString());



        holder.value14.setText(value15.get(position).toString());
        Log.w("extra_valu14",value15.get(position).toString());



        holder.value_mpo.setText(value18.get(position).toString());
        Log.w("mpocode",value18.get(position).toString());




        holder.value19.setText(value19.get(position).toString());

        holder.value20.setText(value20.get(position).toString());



        if (value4.get(position).toString().equals("GG") ||value4.get(position).toString().equals("CC") || value4.get(position).toString().equals("II")
                || value4.get(position).toString().equals("VV")  || value4.get(position).toString().equals("ZZ")){


            holder.sl.setTextColor(Color.rgb(237,237,237));
            holder.product_name.setTextColor(Color.rgb(237,237,237));
            holder.value_mpo.setTextColor(Color.rgb(237,237,237));


            holder.value4.setTextColor(Color.rgb(237,237,237));
            holder.value5.setTextColor(Color.rgb(237,237,237));
            holder.value6.setTextColor(Color.rgb(237,237,237));
            holder.value7.setTextColor(Color.rgb(237,237,237));
            holder.value8.setTextColor(Color.rgb(237,237,237));
            holder.value10.setTextColor(Color.rgb(237,237,237));
            holder.value11.setTextColor(Color.rgb(237,237,237));

            //  holder.qnty.setTextColor(Color.rgb(237,237,237));

            holder.value12.setTextColor(Color.rgb(237,237,237));
            holder.value13.setTextColor(Color.rgb(237,237,237));
            holder.value14.setTextColor(Color.rgb(237,237,237));
            holder.value19.setTextColor(Color.rgb(237,237,237));
            holder.value20.setTextColor(Color.rgb(237,237,237));
        }



















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


        TextView value8;
        TextView value9;
        TextView value10;
        TextView value11;

        TextView value12;
        TextView value13;
        TextView value14;


        TextView value19;
        TextView value20;

        TextView value_mpo;

    }
}


