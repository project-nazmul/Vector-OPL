
package com.opl.pharmavector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.content.Context.INPUT_METHOD_SERVICE;


@SuppressLint("ViewHolder")
public class NoticeBoardAdapter extends BaseAdapter implements Filterable {

    Context mContext;
    ArrayList<String> p_names;
    ArrayList<String> sl;
    ArrayList<String> p_ids;
    ArrayList<String> PROD_RATE;
    ArrayList<String> PROD_VAT;
    ArrayList<String> PPM_CODE;
    ArrayList<String> P_CODE;
    ArrayList<String> value7;
    ArrayList<String> value8;
    ArrayList<String> PPM_TYPE;
    ArrayList<String> PROD_REQ;
    static HashSet<Integer> mProductSerialList;
    public static TextView edit_qnty;
    ArrayList<String> Data_Openingstock = new ArrayList<String>();
    static HashMap<Integer, String> p_quanty;
    // OnClickListener callBack;
    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    private String productname = null;
    // public static Integer total_quantity=0;
    private ArrayList<String> arraylist;
    private boolean InputState;
    private String[] valueList;
    private ArrayList<String> mStringList;
    public static int focusvalue=0;
    private ValueFilter valueFilter;
    Toast toast;

    public NoticeBoardAdapter(Activity activity,
                                         ArrayList<HashMap<String, String>> list, String productname,
                                         boolean state) {
        super();
        inflater = LayoutInflater.from(mContext);
        this.activity = activity;
        this.list = list;
        this.productname = productname;
        InputState = state;
        valueList = new String[list.size()];
        this.arraylist = new ArrayList<String>();
        this.arraylist.add(productname);
    }

    View rowView;
    public static int last_position;
    public static int last_value;
    public static String qnty;
    public static String holder;
    LayoutInflater inflater;
    private Object total;
    static ArrayList<Integer> qntyID = new ArrayList<Integer>();
    static ArrayList<String> qntyVal = new ArrayList<String>();
    static HashMap<Integer, String> dataSet = new HashMap<Integer, String>();
    static Set<Integer> set2 = new HashSet<Integer>();
    public static ArrayList<String> editTxtID = new ArrayList<String>();
    static ArrayList<Category> categoriesList;



    NoticeBoardAdapter(Context con,ArrayList<String> sl, ArrayList<String> p_name,HashMap<Integer, String> p_quanty,ArrayList<String> value7,ArrayList<String> value8) {
        this.p_names = p_name;
        this.mStringList = p_name;
        this.p_quanty = p_quanty;
        this.value7 = value7;
        this.value8 = value8;
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

      //return mStringList.get(position);
        return sl.get(position);
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
    public Object PPM_CODE(int position) {
        return PPM_CODE.get(position);
    }
    public Object P_CODE(int position) { return P_CODE.get(position); }
    public Object PROD_VAT(int position) { return PROD_VAT.get(position); }
    public Object PROD_REQ(int position) { return PROD_REQ.get(position); }

    @SuppressLint({"ClickableViewAccessibility", "ResourceAsColor"})
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final ViewHolder holder;

        rowView = convertView;

        if (rowView == null) {
            convertView = inflater.inflate(R.layout.notice_board_adapter, null);
            holder = new ViewHolder();
            holder.serial = (TextView) convertView.findViewById(R.id.serial);
            holder.p_name = (TextView) convertView.findViewById(R.id.product_name);//notice_detail
            holder.ppm_error = (TextView) convertView.findViewById(R.id.notice_title);
            holder.quantity = (TextView) convertView.findViewById(R.id.order_qnty);
            edit_qnty= (TextView) convertView.findViewById(R.id.order_qnty);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.p_name.setText(mStringList.get(position));
        final int poss = Integer.parseInt(sl.get(position));
        holder.pos = position;
        int serialNo = Integer.parseInt(sl.get(position));
        int srl = NoticeBoard.nameSerialPair.get(holder.p_name.getText().toString());
        holder.serial.setText(String.valueOf(srl));
        holder.quantity.setTag(srl);
        holder.ppm_error.setText(value7.get(position));
        holder.quantity.setText(value8.get(position));
        return convertView;
    }




    private class ViewHolder {
        TextView serial;
        TextView quantity;
        String strHolder;
        int pos;
        TextView p_name;
        TextView value7;
        TextView msgtext;
        TextView ppm_error;


    }






    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());


        System.out.println("charText Text\t" + charText);

        if (charText.length() == 0) {

        } else {
            for (String wp : arraylist) {

            }
        }
        notifyDataSetChanged();
    }





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
                            Log.e("productsl---","=====================");
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
                            if (p_names.get(i).toLowerCase().contains(((String) constraint).toLowerCase()) || value7.get(i).toLowerCase().contains(((String) constraint).toLowerCase()) ) {
                               // mProductSerialList.add(Integer.parseInt(sl.get(i)));
                                filterList.add(p_names.get(i));
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



        private String contains(CharSequence constraint) {
            // TODO Auto-generated method stub
            return null;
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





