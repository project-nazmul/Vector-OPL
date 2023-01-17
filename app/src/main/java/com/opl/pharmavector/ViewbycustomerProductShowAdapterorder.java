package com.opl.pharmavector;

import java.util.ArrayList;

import com.opl.pharmavector.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

@SuppressLint("ViewHolder")
public class ViewbycustomerProductShowAdapterorder extends BaseAdapter {
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
	// private int pos=1;
	View rowView;
	public static int last_position;
//	public static String qnty;
	// String strHolder;
	// static ArrayList<Integer> click_pos;
	static ArrayList<String> qntyID;
	static ArrayList<String> qntyVal;

	ViewbycustomerProductShowAdapterorder(Context con, ArrayList<String> product_name,
			ArrayList<String> qnty, ArrayList<String> value) {
		this.product_name = product_name;
		this.qnty = qnty;
		this.value = value;
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
		final ViewHolder holder = new ViewHolder();
		rowView = inflater.inflate(R.layout.viewbycustomerproduct_show, parent, false);
		serial = (TextView) rowView.findViewById(R.id.serial_viewbycustomer);
		holder.product_name = (TextView) rowView.findViewById(R.id.product_name_viewbycustomer);
		holder.qnty = (TextView) rowView.findViewById(R.id.qnty_viewbycustomer);
		holder.value = (TextView) rowView.findViewById(R.id.value_viewbycustomer);
	


		serial.setText(String.valueOf(position + 1));
		holder.product_name.setText(product_name.get(position));

		holder.qnty.setText(qnty.get(position).toString());

		holder.qnty.setTag(position);


		//String s =value.get(position).toString();
		//String[] split = s.split(".");
		//String firstSubString = split[0];
	//	String secondSubString = split[1];
		//String arr[] = value.get(position).toString().split(".");;
		//String test=arr[0].trim();
		//String arr1[] = cust_type_with_note.split("///");




		holder.value.setText(value.get(position).toString());
		/*
		 * float sumqty=0f; float sumsell=0f; for (int i = 0; i <
		 * p_quanty.size(); i++) { sumqty=p_quanty.get(i)+sumqty;
		 * sumsell=values.get(i)+sumsell; holder.totalqty.setTag(sumqty);
		 * holder.totalsell.setTag(sumsell); }
		 */
		

		

		return rowView;

	}

	private class ViewHolder {
		TextView qnty;
		TextView value;
		TextView product_name;
		
	
		// int click_pos;
		// int last_position;
	}
}
