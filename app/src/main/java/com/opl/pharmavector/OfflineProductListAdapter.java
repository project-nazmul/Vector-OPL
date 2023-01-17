package com.opl.pharmavector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.opl.pharmavector.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
//import android.widget.Filter.FilterResults;

@SuppressLint("ViewHolder")
public class OfflineProductListAdapter extends BaseAdapter implements Filterable {

    Context mContext;
    ArrayList<String> p_names;
    ArrayList<String> sl;
    ArrayList<String> p_ids;
    ArrayList<String> PROD_RATE;
    static HashSet<Integer> mProductSerialList;
    public static EditText edit_qnty;
    static HashMap<Integer, String> p_quanty;
    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    private ArrayList<String> mStringList;
    public static int focusvalue=0;
    private ValueFilter valueFilter;
    View rowView;
    public static int last_position;
    public static String qnty;
    public static String holder;
    LayoutInflater inflater;
    static ArrayList<Integer> qntyID = new ArrayList<Integer>();
    static ArrayList<String> qntyVal = new ArrayList<String>();
    static HashMap<Integer, String> dataSet = new HashMap<Integer, String>();
    static Set<Integer> set2 = new HashSet<Integer>();

    OfflineProductListAdapter(Context con, ArrayList<String> sl, ArrayList<String> p_name, HashMap<Integer, String> p_quanty) {

        this.p_names = p_name;
        this.mStringList = p_name;
        this.p_quanty = p_quanty;
        this.sl = sl;
        this.mContext = con;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        getFilter();
        mProductSerialList = new HashSet<Integer>();
    }

    @Override
    public int getCount() {

        return mStringList.size();
    }

    // Get the data item associated with the specified position in the data set.
    @Override
    public Object getItem(int position) {

        return mStringList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public Object sl(int position) {
        return sl.get(position);
    }

    public Object p_ids(int position) {
        return p_ids.get(position);
    }

    public Object PROD_RATE(int position) {
        return PROD_RATE.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final ViewHolder holder;

        rowView = convertView;

        if (rowView == null) {
            convertView = inflater.inflate(R.layout.product_list, null);
            holder = new ViewHolder();
            holder.serial = (TextView) convertView.findViewById(R.id.serial);
            holder.p_name = (TextView) convertView.findViewById(R.id.product_name);
            holder.quantity = (EditText) convertView.findViewById(R.id.qnty);

            edit_qnty= (EditText) convertView.findViewById(R.id.qnty);
            convertView.setTag(holder);


        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        // holder.serial.setText(sl.get(position));
        holder.p_name.setText(mStringList.get(position));
        final int poss = Integer.parseInt(sl.get(position));
        holder.pos = position;


        int srl = OfflineProductOrder.nameSerialPair.get(holder.p_name.getText()
                .toString());
        holder.serial.setText(String.valueOf(srl));

        holder.quantity.setTag(srl);


        holder.quantity.setText(p_quanty.get(srl));

        holder.quantity.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                System.out.println("------------on Touch---------");
                OfflineProductOrder.searchview.setFocusable(false);
                holder.quantity.setFocusable(true);
                return false;
                //return true;
            }
        });


        holder.quantity.addTextChangedListener(new TextWatcher() {

            float total_value = 0;
            int total_quanty = 0;
            int current_quantity = 0;
            float current_rate = 0f;
            float product_value = 0f;

            String tempStr;

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                //	holder.quantity.setBackgroundResource(R.drawable.active);
                //	holder.serial.setBackgroundResource(R.drawable.active);
                //	holder.p_name.setBackgroundResource(R.drawable.active);

                if (s.length() > 0) {

                    tempStr = s.toString();
                    qnty = tempStr;

                }

                int testt = s.toString().length();
                if (testt> 0) {

                } else {

                    //		holder.quantity.setText("0");

                }

            }

            @Override
            public void beforeTextChanged(CharSequence str, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub


            }

            @SuppressLint("NewApi")
            @Override
            public void afterTextChanged(Editable str) {

                try {

                    //int checkZero = Integer.parseInt(str.toString());
                    if (str.toString().isEmpty()) {
                        // int checkZero=Integer.parseInt(str.toString());
                        String zero = "0";
                        // int checkZero=Integer.parseInt(zero.toString());

                        int p_serial = (Integer) holder.quantity.getTag();
                        p_quanty.put(p_serial, "0");

                        dataSet.put(holder.pos, "0");

                        float total_value = 0;
                        int total_quanty = 0;
                        int map_size = dataSet.size();
                        Set keys = dataSet.keySet();
                        for (Iterator i = keys.iterator(); i.hasNext();) {
                            Integer key = (Integer) i.next();
                            String value = (String) dataSet.get(key);
                            // System.out.println(key + " = " + value);
                            int current_quantity = Integer.parseInt(value);
                            String product_rate = OfflineProductOrder.categoriesList.get(key).getPROD_RATE();
                            float current_rate = Float.parseFloat(product_rate);
                            float product_value = current_quantity* current_rate;
                            total_value = total_value + product_value;
                            total_quanty = total_quanty + current_quantity;
                        }
                        String tota = String.format("%.02f", total_value);
                        OfflineProductOrder.totalsellquantity.setText("" + tota);

                        return;
                    }

                    else{
                        int checkZero = Integer.parseInt(str.toString());
                        if (checkZero > 0) {
                            holder.quantity.setBackgroundResource(R.drawable.selected);

                            int current_quantity = 0;
                            float current_rate = 0;
                            float product_value = 0;
                            float total_value = 0;
                            int total_quanty = 0;

                            int p_serial = (Integer) holder.quantity.getTag();
                            p_quanty.put(p_serial, str.toString());

                            dataSet.put(holder.pos, holder.quantity.getText().toString().trim());

                            int map_size = dataSet.size();
                            Set keys = dataSet.keySet();
                            for (Iterator i = keys.iterator(); i.hasNext();) {
                                Integer key = (Integer) i.next();
                                String value = (String) dataSet.get(8);
                                try {
                                    current_quantity = Integer.parseInt(value);

                                } catch (Exception e) {
                                    // TODO: handle exception
                                }
                                String product_rate = OfflineProductOrder.categoriesList
                                        .get(8).getPROD_RATE();
                                current_rate = Float.parseFloat(product_rate);
                                product_value = current_quantity * current_rate;
                                total_value = total_value + product_value;
                                total_quanty = total_quanty + current_quantity;

                            }

                        }
                        else if(checkZero==0){
                            String zero = "0";
                            int p_serial = (Integer) holder.quantity.getTag();
                            p_quanty.put(p_serial, "0");
                            dataSet.put(holder.pos, "0");
                            holder.quantity.setBackgroundColor(0x00008080);
                        }
                    }



                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        });

        holder.quantity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @SuppressLint("NewApi")
            @Override

            public void onFocusChange(View v, boolean hasFocus) {


                if (!hasFocus) {


                    focusvalue=0;
                    Log.i("focusvalue", ""+focusvalue);

                    LinearLayout parent = (LinearLayout) v.getParent();
                    String qntyfocus = holder.quantity.getText()
                            .toString().trim();
                    //holder.quantity = (EditText) parent.findViewById(R.id.qnty);
                    int holderpos = holder.pos;
                    set2.add(holder.pos);
                    if (qntyfocus.isEmpty()) {
                        qntyfocus = "0";
                    }

                    int temp = Integer.parseInt(qntyfocus);
                    if (temp > 0) {
                        holder.quantity.setText(qntyfocus);
                        dataSet.put(holder.pos, holder.quantity.getText().toString().trim());

                    } else {
                        holder.quantity.setText("0");
                        dataSet.put(holder.pos, "0");

                    }

                }
                else{
                    focusvalue=1;
                    Log.i("focusvalue", ""+focusvalue);
                    Editable pqnty=holder.quantity.getText();
                    dataSet.put(holder.pos, "0");
                    holder.quantity.setSelection(holder.quantity.getText().length());

                    try {

                        int checkZero = Integer.parseInt(pqnty.toString());
                        if(checkZero==0){

                            holder.quantity.setText("");
                        }

                    } catch (Exception e) {
                        // TODO: handle exception
                        OfflineProductOrder.searchview.setFocusable(true);
                    }


                    holder.quantity.setBackgroundResource(R.drawable.active);

                }

            }

        });

		/*------------------------------New code-----------------------*/

	/*----------------------------------------------------------------------------------------------------------*/




        return convertView;

    }

    private class ViewHolder {
        TextView serial;
        EditText quantity;
        String strHolder;
        int pos;
        TextView p_name;

    }

    // -----------------------------Search---Class-------------------------




    @Override
    public Filter getFilter() {
        // TODO Auto-generated method stub

        if (valueFilter == null) {

            valueFilter = new ValueFilter();
        }

        return valueFilter;

        // return null;
    }

    private class ValueFilter extends Filter {
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            int myNum = 0;

            try {
                myNum = Integer.parseInt(constraint.toString());
                System.out.println("myNum "+myNum);


                if (myNum> 0) {


                    ArrayList<String> filterList = new ArrayList<String>();
                    for (int i = 0; i < p_names.size(); i++) {
                        String productName = p_names.get(i).toLowerCase();

                        myNum=1;
                        String Quantity = p_quanty.get(Integer.parseInt(sl.get(i)));
                        int current_qnty=Integer.parseInt(Quantity);
                        if(current_qnty>=myNum){
                            System.out.println("productsl="+Integer.parseInt(sl.get(i))+"Filter product Quantity="+Quantity);
                            mProductSerialList.add(Integer.parseInt(sl.get(i)));
                            filterList.add(p_names.get(i));
                        }

                    }

                    results.count = filterList.size();
                    results.values = filterList;
                    return results;

                } else {
                    results.count = p_names.size();
                    results.values = p_names;
                    return results;

                }

            } catch(NumberFormatException nfe) {

                ArrayList<String> filterList = new ArrayList<String>();

                try {
                    if (constraint.length() > 2) {
                        for (int i = 0; i < p_names.size(); i++) {
                            if (p_names.get(i).toLowerCase().contains(((String) constraint).toLowerCase())) {
                                String productName = p_names.get(i).toLowerCase();
                                int qnty=1;
                                String Quantity = p_quanty.get(Integer.parseInt(sl.get(i)));
                                int current_qnty=Integer.parseInt(Quantity);

                                //if(current_qnty>=qnty){
                                System.out.println("productsl="+Integer.parseInt(sl.get(i))+"Filter product Quantity="+Quantity);
                                mProductSerialList.add(Integer.parseInt(sl.get(i)));
                                filterList.add(p_names.get(i));
                                //}
                            }



                        }

                        results.count = filterList.size();
                        results.values = filterList;
                        //return results;
                    } else {
                        results.count = p_names.size();
                        results.values = p_names;
                        //return results;
                    }




                }catch(NumberFormatException e) {
                    results.count = filterList.size();
                    results.values = filterList;
                    //return results;



                }
                return results;

            }


        }




        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {

            mStringList = (ArrayList<String>) results.values;
            notifyDataSetChanged();

        }

    }

}
