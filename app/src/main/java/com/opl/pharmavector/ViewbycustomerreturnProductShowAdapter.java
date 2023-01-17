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
public class ViewbycustomerreturnProductShowAdapter extends BaseAdapter {
	TextView serial;
	// TextView p_name;
	// EditText quanty;
	CheckBox check;
	Context mContext;
	ArrayList<String> product_name;
	ArrayList<String> returnpercentage;
	// ArrayList<String> p_rates;
	ArrayList<String> qnty;
	ArrayList<String> value;
	OnClickListener callBack;
	// private int pos=1;
	View rowView;

	View rowView1;
	public static int last_position;
//	public static String qnty;
	// String strHolder;
	// static ArrayList<Integer> click_pos;
	static ArrayList<String> qntyID;
	static ArrayList<String> qntyVal;

	ViewbycustomerreturnProductShowAdapter(Context con, ArrayList<String> product_name,
			ArrayList<String> qnty, ArrayList<String> value,ArrayList<String> returnpercentage) {
		this.product_name = product_name;
		this.qnty = qnty;
		this.value = value;
		this.returnpercentage = returnpercentage;
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


	public stirng returnpercentage(int position) {		return returnpercentage(position);	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final ViewHolder holder = new ViewHolder();
		rowView = inflater.inflate(R.layout.viewbycustomerproductreturn_show, parent, false);
		serial = (TextView) rowView.findViewById(R.id.serial_return);
		//returnpercentage = (TextView) rowView.findViewById(R.id.returnpercentage);


		holder.product_name = (TextView) rowView.findViewById(R.id.product_name_return);
		holder.qnty = (TextView) rowView.findViewById(R.id.qnty_return);
		holder.value = (TextView) rowView.findViewById(R.id.value_return);
		holder.returnpercentage = (TextView) rowView.findViewById(R.id.returnpercentage_return);


		serial.setText(String.valueOf(position + 1));
		holder.product_name.setText(product_name.get(position));

		holder.qnty.setText(qnty.get(position).toString());

		//holder.qnty.setTag(position);
		holder.value.setText(value.get(position).toString());
		holder.returnpercentage.setText(returnpercentage.get(position));
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
		TextView returnpercentage;
		
	
		// int click_pos;
		// int last_position;
	}
}
