package com.opl.pharmavector;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.opl.pharmavector.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderListAdapter extends BaseAdapter {

	// Declare Variables
	Context mContext;
	LayoutInflater inflater;
	private List<Category> worldpopulationlist = null;
	private ArrayList<Category> arraylist;

	public OrderListAdapter(Context context,
			List<Category> worldpopulationlist) {
		mContext = context;
		this.worldpopulationlist = worldpopulationlist;
		inflater = LayoutInflater.from(mContext);
		this.arraylist = new ArrayList<Category>();
		this.arraylist.addAll(worldpopulationlist);
	}

	public class ViewHolder {
		TextView rank;
		TextView country;
		TextView population;
		ImageView flag;
	}

	@Override
	public int getCount() {
		return worldpopulationlist.size();
	}

	@Override
	public Category getItem(int position) {
		return worldpopulationlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.product_list, null);
			// Locate the TextViews in listview_item.xml
			holder.rank = (TextView) view.findViewById(R.id.serial);
			holder.country = (TextView) view.findViewById(R.id.product_name);
			holder.population = (TextView) view.findViewById(R.id.qnty);
			// Locate the ImageView in listview_item.xml
		
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		// Set the results into TextViews
		holder.rank.setText(worldpopulationlist.get(position).getId());
		holder.country.setText(worldpopulationlist.get(position).getName());

		return view;
	}

	// Filter Class
	public void filter(String charText) {
		charText = charText.toLowerCase(Locale.getDefault());
		
		
		worldpopulationlist.clear();
		if (charText.length() == 0) {
			worldpopulationlist.addAll(arraylist);
		} else {
			for (Category wp : arraylist) {
				
				if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
				if (wp.getName().contains(charText)) {
					worldpopulationlist.add(wp);
				}
			}
		}
		notifyDataSetChanged();
	}

}
}
