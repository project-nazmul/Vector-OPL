package com.opl.pharmavector;

import java.util.ArrayList;

import com.opl.pharmavector.R;
//import com.opl.salematrix.ProductListAdapter.ValueFilter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

@SuppressLint("ViewHolder")



public class ProductUpdateAdapter extends BaseAdapter {
	TextView serial;
	// TextView p_name;
	// EditText quanty;
	CheckBox check;
	Context mContext;
	//ArrayList<String> product_name;
	// ArrayList<String> p_rates;
	public static ArrayList<Integer> qnty;
	public static ArrayList<String> product_name;
	public static ArrayList<String> p_ids;
	ArrayList<Float> value;
	OnClickListener callBack;
	View rowView;
	public static int last_position;
	static ArrayList<Integer> qntyID;
	static ArrayList<String> qntyVal;

	//ProductUpdateAdapter(Context con, ArrayList<String> product_name,ArrayList<Integer> qnty, ArrayList<Float> value) {
	ProductUpdateAdapter(Context con, ArrayList<String> product_name,ArrayList<Integer> qnty,ArrayList<String> p_ids) {
		
		
		this.product_name = product_name;
		this.p_ids = p_ids;
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
	
	public Object p_ids(int position) {
		return p_ids.get(position);
	}
	
	
	
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) mContext	.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final ViewHolder holder = new ViewHolder();
		rowView = inflater.inflate(R.layout.product_update, parent, false);
		serial = (TextView) rowView.findViewById(R.id.serial);
		holder.product_name = (TextView) rowView.findViewById(R.id.product_name);
		holder.qnty = (TextView) rowView.findViewById(R.id.qnty1);
		holder.p_ids = (TextView) rowView.findViewById(R.id.p_id);
		//holder.value = (TextView) rowView.findViewById(R.id.value);
		serial.setText(String.valueOf(position + 1));
		holder.product_name.setText(product_name.get(position));
		holder.p_ids.setText(p_ids.get(position));
		holder.qnty.setText(qnty.get(position).toString());
		holder.qnty.setTag(position);
		
		
		holder.qnty.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
				try {
					
					int checkZero = Integer.parseInt(s.toString());
					if (checkZero>0) {
						holder.qnty.setBackgroundResource(R.drawable.selected);
					}
					else {
						holder.qnty.setBackgroundColor(0x00008080);
					}
					
					
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
		});
		
		
		
		
		
		return rowView;

	}

	
	private class ViewHolder {
		TextView qnty;
		TextView value;
		TextView product_name;
		TextView p_ids;
	
		// int click_pos;
		// int last_position;
	}
	
	
}
