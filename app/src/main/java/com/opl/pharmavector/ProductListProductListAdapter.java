package com.opl.pharmavector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.widget.CheckBox;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

@SuppressLint("ViewHolder")
public class ProductListProductListAdapter extends BaseAdapter implements Filterable {
    Context mContext;
    ArrayList<String> p_names;
    ArrayList<String> sl;
    ArrayList<String> p_ids;
    ArrayList<String> PROD_RATE;
    ArrayList<String> PROD_VAT;
    ArrayList<String> PPM_CODE;
    ArrayList<String> SHIFT_CODE;
    ArrayList<String> P_CODE;
    ArrayList<String> value7;
    ArrayList<String> value8;
    ArrayList<String> value9;
    ArrayList<String> value10;
    ArrayList<String> value11;
    ArrayList<String> value12;
    ArrayList<String> PPM_TYPE;
    ArrayList<String> PROD_REQ;
    ArrayList<String> prodStatus;
    static HashSet<Integer> mProductSerialList;
    public static TextView edit_qnty;
    //public static String[] quantity;
    ArrayList<String> Data_Openingstock = new ArrayList<String>();
    //ArrayList<Integer> p_quanty;
    static HashMap<Integer, String> p_quanty;
    //OnClickListener callBack;
    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    private String productname = null;
    //public static Integer total_quantity=0;
    private ArrayList<String> arraylist;
    private boolean InputState;
    private String[] valueList;
    private ArrayList<String> mStringList;
    public static int focusvalue=0;
    private ValueFilter valueFilter;
    Toast toast;
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

    ProductListProductListAdapter(Context con, ArrayList<String> sl, ArrayList<String> p_name, HashMap<Integer, String> p_quanty, ArrayList<String> value7, ArrayList<String> value8, ArrayList<String> value9, ArrayList<String> value10, ArrayList<String> value11, ArrayList<String> value12, ArrayList<String> prodStatus) {
        this.p_names = p_name;
        this.mStringList = p_name;
        this.p_quanty = p_quanty;
        this.value7 = value7;
        this.value8 = value8;
        this.value9 = value9;
        this.value10 = value10;
        this.value11 = value11;
        this.value12 = value12;
        this.sl = sl;
        this.mContext = con;
        this.prodStatus = prodStatus;
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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        rowView = convertView;

        if (rowView == null) {
            convertView = inflater.inflate(R.layout.admin_product_list_adapter, null);
            holder = new ViewHolder();
            holder.serial = (TextView) convertView.findViewById(R.id.serial);
            holder.p_name = (TextView) convertView.findViewById(R.id.product_name_admin);
            holder.ppm_error = (TextView) convertView.findViewById(R.id.ppm_error);
            holder.quantity = (TextView) convertView.findViewById(R.id.mrp_val);
            holder.p_code1 = (TextView) convertView.findViewById(R.id.p_code1);
            holder.pack_size = (TextView) convertView.findViewById(R.id.pack_size);
            holder.brand_code = (TextView) convertView.findViewById(R.id.brand_code);
            holder.tp = (TextView) convertView.findViewById(R.id.tp);
            holder.TP_VT = (TextView) convertView.findViewById(R.id.TP_VT);
            edit_qnty= (TextView) convertView.findViewById(R.id.order_qnty);
            holder.productList = (CardView) convertView.findViewById(R.id.productList);
            holder.checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.p_name.setText(mStringList.get(position));
        final int poss = Integer.parseInt(sl.get(position));
        holder.pos = position;
        int serialNo = Integer.parseInt(sl.get(position));
        final int srl = AdminProductList.nameSerialPair.get(holder.p_name.getText().toString());
        holder.serial.setText(String.valueOf(srl));
        holder.pack_size.setText(value9.get(srl-1)); // pack size
        holder.tp.setText(value10.get(srl-1)); // trade price
        holder.TP_VT.setText(value7.get(srl-1)); // vat
        holder.quantity.setText(value12.get(srl-1) ); // mrp value
        holder.p_code1.setText(value11.get(srl-1)); // product code
        holder.brand_code.setText(value8.get(srl-1)); // brand code
        if (Objects.equals(prodStatus.get(srl-1), "O")) {
            holder.productList.setCardBackgroundColor(Color.parseColor("#F8CACA"));
        }
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
        TextView p_code1;
        TextView pack_size;
        TextView brand_code;
        TextView tp;
        TextView TP_VT;
        CheckBox checkbox;
        CardView productList;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());

        if (charText.length() == 0) {

        } else {
            for (String wp : arraylist) {

            }
        }
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter {
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            int myNum = 0;

            try {
                myNum = Integer.parseInt(constraint.toString());
                System.out.println("myNum "+myNum);

                if (myNum > 0) {
                    ArrayList<String> filterList = new ArrayList<String>();

                    for (int i = 0; i < p_names.size(); i++) {
                        String productName = p_names.get(i).toLowerCase();
                        myNum = 1;
                        String Quantity = p_quanty.get(Integer.parseInt(sl.get(i)));
                        int current_qnty = Integer.parseInt(Quantity);

                        if (current_qnty >= myNum){
                            System.out.println("productsl="+Integer.parseInt(sl.get(i))+"FilterproductQuantity="+Quantity);
                            mProductSerialList.add(Integer.parseInt(sl.get(i)));
                            filterList.add(  p_names.get(i));
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
                ArrayList<String> filterList2 = new ArrayList<String>();

                try {
                    if (constraint.length() > 2) {
                        for (int i = 0; i < p_names.size(); i++) {
                            if (p_names.get(i).toLowerCase().contains(((String) constraint).toLowerCase())) {
                                int qnty = 1;
                                String productName = p_names.get(i).toLowerCase();
                                String Quantity = p_quanty.get(Integer.parseInt(sl.get(i)));
                                String p_code = value11.get(Integer.parseInt(sl.get(i)));//PRODUCT_CODE
                                String VAT = value7.get(Integer.parseInt(sl.get(i)));//TP
                                String BRAND_CODE = value8.get(Integer.parseInt(sl.get(i)));//BRAND_CODE
                                String PACK_SIZE = value9.get(Integer.parseInt(sl.get(i)));//PACK_SIZE
                                String TP = value10.get(Integer.parseInt(sl.get(i)));//TP
                                int current_qnty=Integer.parseInt(Quantity);

                                //if(current_qnty>=qnty){
                                System.out.println("productsl =  "+  Integer.parseInt(sl.get(i)) +" Filterproductcode = " + p_code
                                        +" Filterproducname = "+ productName +"  Filterpacksize = "+ PACK_SIZE + "   Brandcode = "+ BRAND_CODE
                                        +" TP VALUE = " +TP +"VAT VALUE ==  " + VAT + " mrp value ==  "+  Quantity
                                );
                                mProductSerialList.add(Integer.parseInt(sl.get(i)));
                                System.out.println("mProductSerialList =  "+mProductSerialList);
                                filterList.add(p_names.get(i));
                                System.out.println("filterList =  "+  filterList);
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
                } catch (NumberFormatException e) {
                    results.count = filterList.size();
                    results.values = filterList;
                    //return results;
                }
                return results;
            }
        }

        private String contains(CharSequence constraint) {
            return null;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mStringList = (ArrayList<String>) results.values;
            notifyDataSetChanged();
        }
    }
}




