package com.opl.pharmavector.order_online;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import android.graphics.Color;
import android.text.InputType;
import android.text.method.TextKeyListener;
import android.view.Gravity;
import android.view.inputmethod.InputMethodManager;

import com.opl.pharmavector.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;
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

import static android.content.Context.INPUT_METHOD_SERVICE;

@SuppressLint("ViewHolder")
public class ProductListAdapter2 extends BaseAdapter implements Filterable {
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
    ArrayList<String> PPM_TYPE;
    ArrayList<String> PROD_REQ;
    ArrayList<String> value10;
    static HashSet<Integer> mProductSerialList;
    public static EditText edit_qnty;
    ArrayList<String> Data_Openingstock = new ArrayList<>();
    //ArrayList<Integer> p_quanty;
    static HashMap<Integer, String> p_quanty;
    //OnClickListener callBack;
    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    private String productname = null;
    //public static Integer total_quantity = 0;
    private ArrayList<String> arraylist;
    private boolean InputState;
    private String[] valueList;
    private ArrayList<String> mStringList;
    public static int focusvalue = 0;
    private ValueFilter valueFilter;
    Toast toast;

    public ProductListAdapter2(Activity activity, ArrayList<HashMap<String, String>> list, String productname, boolean state) {
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

    ProductListAdapter2(Context con, ArrayList<String> sl, ArrayList<String> p_name, HashMap<Integer, String> p_quanty, ArrayList<String> value7, ArrayList<String> value8, ArrayList<String> value9) {
        this.p_names = p_name;
        this.mStringList = p_name;
        this.p_quanty = p_quanty;
        this.value7 = value7;
        this.value8 = value8;
        this.value9 = value9;
        this.sl = sl;
        this.mContext = con;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        getFilter();
        mProductSerialList = new HashSet<Integer>();
    }

    public ProductListAdapter2(Context con, ArrayList<String> sl, ArrayList<String> p_name, HashMap<Integer, String> p_quanty, ArrayList<String> value7, ArrayList<String> value8, ArrayList<String> value9, ArrayList<String> value10) {
        this.p_names = p_name;
        this.mStringList = p_name;
        this.p_quanty = p_quanty;
        this.value7 = value7;
        this.value8 = value8;
        this.value9 = value9;
        this.value10 = value10;
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

    public Object SHIFT_CODE(int position) {
        return SHIFT_CODE.get(position);
    }

    public Object P_CODE(int position) {
        return P_CODE.get(position);
    }

    public Object PROD_VAT(int position) {
        return PROD_VAT.get(position);
    }

    public Object PROD_REQ(int position) {
        return PROD_REQ.get(position);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        rowView = convertView;

        if (rowView == null) {
            //convertView = inflater.inflate(R.layout.doc_ppm_list, null);
            convertView = inflater.inflate(R.layout.prod_list_new, null);
            holder = new ViewHolder();
            holder.serial = (TextView) convertView.findViewById(R.id.serial);
            holder.p_name = (TextView) convertView.findViewById(R.id.product_name);
            holder.pack_size = (TextView) convertView.findViewById(R.id.pack_size);
            holder.ppm_error = (TextView) convertView.findViewById(R.id.ppm_error);
            holder.quantity = (EditText) convertView.findViewById(R.id.order_qnty);
            holder.p_type = (TextView) convertView.findViewById(R.id.p_team_type);
            //edit_qnty = (EditText) convertView.findViewById(R.id.order_qnty);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.p_name.setText(mStringList.get(position));
        final int poss = Integer.parseInt(sl.get(position));
        holder.pos = position;
        int serialNo = Integer.parseInt(sl.get(position));
        int srl = ProductOrdernew.nameSerialPair.get(holder.p_name.getText().toString());
        holder.serial.setText(String.valueOf(srl));
        holder.quantity.setTag(srl);
        holder.quantity.setText(p_quanty.get(srl));
        holder.p_type.setText(value9.get(srl - 1));
        holder.pack_size.setText(value10.get(srl - 1));

        switch (value9.get(srl - 1)) {
            case "DYNAMOS": //#34438d
                holder.p_type.setTextColor(Color.parseColor("#34438d"));
                holder.p_name.setTextColor(Color.parseColor("#34438d"));
                holder.serial.setTextColor(Color.parseColor("#34438d"));
                holder.pack_size.setTextColor(Color.parseColor("#34438d"));
                break;

            case "TITAN":
                holder.p_type.setTextColor(Color.rgb(139, 0, 0));
                holder.p_name.setTextColor(Color.rgb(139, 0, 0));
                holder.serial.setTextColor(Color.rgb(139, 0, 0));
                holder.pack_size.setTextColor(Color.rgb(130, 0, 0));
                break;

            case "VERGENCE":
                holder.p_type.setTextColor(Color.rgb(153, 51, 255));
                holder.p_name.setTextColor(Color.rgb(153, 51, 255));
                holder.serial.setTextColor(Color.rgb(153, 51, 255));
                holder.pack_size.setTextColor(Color.rgb(153, 51, 255));
                break;

            case "GALLANT":
                holder.p_type.setTextColor(Color.rgb(204, 0, 0));
                holder.p_name.setTextColor(Color.rgb(204, 0, 0));
                holder.serial.setTextColor(Color.rgb(204, 0, 0));
                holder.pack_size.setTextColor(Color.rgb(204, 0, 0));
                break;

            case "EXCELON":
                holder.p_type.setTextColor(Color.rgb(0, 102, 102));
                holder.p_name.setTextColor(Color.rgb(0, 102, 102));
                holder.serial.setTextColor(Color.rgb(0, 102, 102));
                holder.pack_size.setTextColor(Color.rgb(0, 102, 102));
                break;

            default:
                holder.p_type.setTextColor(Color.parseColor("#246899"));
                holder.p_name.setTextColor(Color.parseColor("#246899"));
                holder.serial.setTextColor(Color.parseColor("#246899"));
                holder.pack_size.setTextColor(Color.parseColor("#246899"));
                break;
        }

        holder.quantity.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Log.w("giftlistontouch",value7.get(position));
                ProductOrdernew.searchview.setFocusable(false);
                holder.quantity.setFocusable(true);
                holder.quantity.setBackgroundColor(0x00008080);
                return false;
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
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    tempStr = s.toString();
                    qnty = tempStr;
                    Integer result = Integer.valueOf(qnty);
                    Integer result1 = Integer.valueOf(value7.get(position));
                }
                int testt = s.toString().length();

                if (testt > 0) {

                } else {

                }
            }

            @Override
            public void beforeTextChanged(CharSequence str, int arg1, int arg2, int arg3) {

            }

            @SuppressLint("NewApi")
            @Override
            public void afterTextChanged(Editable str) {
                try {
                    if (str.toString().isEmpty()) {
                        String zero = "0";
                        int p_serial = (Integer) holder.quantity.getTag();
                        p_quanty.put(p_serial, "0");
                        dataSet.put(holder.pos, "0");
                        float total_value = 0;
                        int total_quanty = 0;
                        int map_size = dataSet.size();
                        Set keys = dataSet.keySet();

                        for (Iterator i = keys.iterator(); i.hasNext(); ) {
                            Integer key = (Integer) i.next();
                            String value = (String) dataSet.get(key);
                            int current_quantity = Integer.parseInt(value);
                            String product_rate = "";
                        }
                        String tota = String.format("%.02f", total_value);
                        return;
                    } else {
                        int checkZero = Integer.parseInt(str.toString());

                        if (checkZero > 0) {
                            holder.quantity.setBackgroundResource(R.drawable.selected);
                            int current_quantity = 0;
                            float current_rate = 0;
                            float product_value = 0;
                            float total_value = 0;
                            int total_quanty = 0;
                            int p_serial = (Integer) holder.quantity.getTag();
                            String stock = (String) value7.get(p_serial - 1);
                            String given = str.toString();
                            int stock_qnty = Integer.parseInt(stock);
                            int given_qnty = Integer.parseInt(given);
                            String brand = (String) value8.get(p_serial - 1);

                            if (given_qnty < stock_qnty) {
                                holder.quantity.setText("10");
                                p_quanty.put(p_serial, "0");
                                dataSet.put(holder.pos, "0");
                            }
                            p_quanty.put(p_serial, str.toString());
                            dataSet.put(holder.pos, holder.quantity.getText().toString().trim());

                            int map_size = dataSet.size();
                            Set keys = dataSet.keySet();

                            for (Iterator i = keys.iterator(); i.hasNext(); ) {
                                Integer key = (Integer) i.next();
                                String value = (String) dataSet.get(8);

                                try {
                                    current_quantity = Integer.parseInt(value);
                                } catch (Exception e) {

                                }
                                String product_rate = ProductOrdernew.categoriesList.get(8).getPROD_RATE();
                                current_rate = Float.parseFloat(product_rate);
                                product_value = current_quantity * current_rate;
                                total_value = total_value + product_value;
                                total_quanty = total_quanty + current_quantity;
                            }
                        } else if (checkZero == 0) {
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
        holder.quantity.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                focusvalue = 0;
                //LinearLayout parent = (LinearLayout) v.getParent();
                String qntyfocus = holder.quantity.getText().toString().trim();
                int holderpos = holder.pos;
                set2.add(holder.pos);

                if (qntyfocus.isEmpty()) {
                    qntyfocus = "0";
                }
                int temp = Integer.parseInt(qntyfocus);

                if (temp > 0) {
                    //holder.quantity.setText(qntyfocus);
                    dataSet.put(holder.pos, holder.quantity.getText().toString().trim());
                } else {
                    //holder.quantity.setText("0");
                    dataSet.put(holder.pos, "0");
                }
            }
            else {
                focusvalue = 1;
                Editable pqnty = holder.quantity.getText();
                dataSet.put(holder.pos, "0");
                holder.quantity.setSelection(holder.quantity.getText().length());

                try {
                    int checkZero = Integer.parseInt(pqnty.toString());
                    if (checkZero == 0) {
                        holder.quantity.setText("");
                    }
                } catch (Exception e) {
                    ProductOrdernew.searchview.setFocusable(true);
                }
                holder.quantity.setBackgroundResource(R.drawable.active);
            }
        });
        return convertView;
    }

    private class ViewHolder {
        TextView serial;
        EditText quantity;
        String strHolder;
        int pos;
        TextView p_name;
        TextView value7;
        TextView msgtext;
        TextView ppm_error;
        TextView p_type;
        TextView pack_size;
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
                if (myNum > 0) {
                    ArrayList<String> filterList = new ArrayList<String>();
                    for (int i = 0; i < p_names.size(); i++) {
                        String productName = p_names.get(i).toLowerCase();
                        myNum = 1;
                        String Quantity = p_quanty.get(Integer.parseInt(sl.get(i)));
                        int current_qnty = Integer.parseInt(Quantity);

                        if (current_qnty >= myNum) {
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
            } catch (NumberFormatException nfe) {
                ArrayList<String> filterList = new ArrayList<String>();

                try {
                    if (constraint.length() > 2) {
                        for (int i = 0; i < p_names.size(); i++) {
                            if (p_names.get(i).toLowerCase().contains(((String) constraint).toLowerCase())) {
                                String productName = p_names.get(i).toLowerCase();
                                int qnty = 1;
                                String Quantity = p_quanty.get(Integer.parseInt(sl.get(i)));
                                int current_qnty = Integer.parseInt(Quantity);
                                mProductSerialList.add(Integer.parseInt(sl.get(i)));
                                filterList.add(p_names.get(i));
                            }
                        }
                        results.count = filterList.size();
                        results.values = filterList;
                    } else {
                        results.count = p_names.size();
                        results.values = p_names;
                    }
                } catch (NumberFormatException e) {
                    results.count = filterList.size();
                    results.values = filterList;
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


