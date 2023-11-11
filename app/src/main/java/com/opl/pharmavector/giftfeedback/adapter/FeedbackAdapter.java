package com.opl.pharmavector.giftfeedback.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.ImageView;
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

import com.opl.pharmavector.giftfeedback.FieldFeedbackMaster;
import com.opl.pharmavector.R;

@SuppressLint("ViewHolder")
public class FeedbackAdapter extends BaseAdapter implements Filterable {
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

    public FeedbackAdapter(Activity activity, ArrayList<HashMap<String, String>> list, String productname, boolean state) {
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

    public FeedbackAdapter(Context con, ArrayList<String> sl, ArrayList<String> p_name,  ArrayList<String> value7, ArrayList<String> value8) {
        this.p_names = p_name;
        this.mStringList = p_name;
        this.value7 = value7;
        this.value8 = value8;
        this.sl = sl;
        this.mContext = con;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        getFilter();
        mProductSerialList = new HashSet<Integer>();
    }

    public FeedbackAdapter(Context con, ArrayList<String> sl,ArrayList<String> p_name, ArrayList<String> PROD_REQ,
                           ArrayList<String> PROD_RATE, ArrayList<String> PROD_VAT, ArrayList<String> value7,ArrayList<String> value8) {
        this.p_names = p_name;
        this.mStringList = p_name;
        this.PROD_REQ = PROD_REQ;
        this.PROD_RATE = PROD_RATE;
        this.PROD_VAT = PROD_VAT;
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

    public Object P_CODE(int position) {
        return P_CODE.get(position);
    }

    public Object PROD_VAT(int position) {
        return PROD_VAT.get(position);
    }

    public Object PROD_REQ(int position) {
        return PROD_REQ.get(position);
    }

    @SuppressLint({"ClickableViewAccessibility", "ResourceAsColor", "SetTextI18n"})
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        rowView = convertView;

        if (rowView == null) {
            convertView = inflater.inflate(R.layout.adapter_vector_feedback, null);
            holder = new ViewHolder();
            holder.serial =  convertView.findViewById(R.id.serial);
            holder.p_name =  convertView.findViewById(R.id.product_name);//notice_detail
            holder.ppm_error =  convertView.findViewById(R.id.notice_title);
            holder.quantity =  convertView.findViewById(R.id.order_qnty);
            holder.topic =  convertView.findViewById(R.id.topic);
            holder.feedback_title =  convertView.findViewById(R.id.feedback_title);
            holder.feedback_details =  convertView.findViewById(R.id.feedback_details);
            holder.admin =  convertView.findViewById(R.id.admin);
            holder.imageView =  convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.p_name.setText(mStringList.get(position));
        Log.e("p_name-->",mStringList.get(position));
        final int poss = Integer.parseInt(sl.get(position));
        holder.pos = position;
        int srl = FieldFeedbackMaster.nameSerialPair.get(holder.p_name.getText().toString());
        holder.serial.setText(String.valueOf(srl));
        holder.quantity.setTag(srl);
        holder.topic.setText("Subject: " + PROD_REQ.get(position) + " " + mStringList.get(position));
        holder.feedback_title.setText(String.format("Problem:\t%s", PROD_RATE.get(position)));
        holder.feedback_details.setText(String.format("Description:\t%s", PROD_VAT.get(position)));
        holder.admin.setText(String.format("Admin Response: \t%s",value8.get(position)));
        holder.ppm_error.setText(value7.get(position));
        holder.quantity.setText(value8.get(position));

        if (Objects.equals(value8.get(position), "Solved")) {
            holder.quantity.setTextColor(Color.parseColor("#FF008000"));
        } else {
            holder.quantity.setTextColor(Color.parseColor("#FFF44336"));
        }
        return convertView;
    }

    private class ViewHolder {
        TextView serial;
        TextView quantity;
        int pos;
        TextView p_name;
        TextView ppm_error;
        TextView topic;
        TextView feedback_title;
        TextView feedback_details;
        TextView admin;
        ImageView imageView;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());

        if (charText.length() == 0) {
        } else {
            for (String wp : arraylist) {}
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
                if (myNum> 0) {
                    ArrayList<String> filterList = new ArrayList<String>();

                    for (int i = 0; i < p_names.size(); i++) {
                        String productName = p_names.get(i).toLowerCase();
                        myNum=1;
                        String Quantity = p_quanty.get(Integer.parseInt(sl.get(i)));
                        int current_qnty=Integer.parseInt(Quantity);

                        if(current_qnty>=myNum){
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
                }catch(NumberFormatException e) {
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





