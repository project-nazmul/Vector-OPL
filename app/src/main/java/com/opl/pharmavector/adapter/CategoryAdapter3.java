package com.opl.pharmavector.adapter;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Context;
import android.view.LayoutInflater;

import com.opl.pharmavector.ProductOrder;
import com.opl.pharmavector.R;
import com.opl.pharmavector.model.Category;
import com.opl.pharmavector.serverCalls.FavouriteCategoriesJsonParser3;

public class CategoryAdapter3 extends ArrayAdapter<Category> {
    private final List<Category> list;

    public CategoryAdapter3(Context context, int resource, List<Category> list) {
        super(context, resource, list);
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ViewHolder {

        protected TextView categorySerial;
        protected TextView categoryName;
        protected TextView categoryName2;
        protected EditText categoryName3;

        protected CheckBox categoryCheckBox;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        //ViewHolder viewHolder = null;
        final ViewHolder viewHolder ;

        if (convertView == null) {
            LayoutInflater inflator = LayoutInflater.from(getContext());
            convertView = inflator.inflate(R.layout.row_category, null);


            viewHolder = new ViewHolder();


            viewHolder.categorySerial = (TextView) convertView.findViewById(R.id.row_categoryname_textview_sl);
            viewHolder.categoryName = (TextView) convertView.findViewById(R.id.row_categoryname_textview);
            viewHolder.categoryName2 = (TextView) convertView.findViewById(R.id.row_categoryname_textview2);
            viewHolder.categoryName3 = (EditText) convertView.findViewById(R.id.row_categoryname_textview3);
            viewHolder.categoryCheckBox = (CheckBox) convertView.findViewById(R.id.row_category_checkbox);

            FavouriteCategoriesJsonParser3.selectedCategories3.toString().equals("");


            viewHolder.categoryCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int getPosition = (Integer) buttonView.getTag();


                    Log.e("getPosition", String.valueOf(getPosition));

                    Log.e("getPositionvalues", FavouriteCategoriesJsonParser3.selectedCategories3.toString());

                    list.get(getPosition).setSelected(buttonView.isChecked());
                    if (buttonView.isChecked()) {

                        if (!FavouriteCategoriesJsonParser3.selectedCategories3.contains(String.valueOf(list.get(getPosition).getCateogry_id()))) {
                            FavouriteCategoriesJsonParser3.selectedCategories3.add(String.valueOf(list.get(getPosition).getCateogry_id()));





                        }
                    } else {

                        if (FavouriteCategoriesJsonParser3.selectedCategories3.contains(String.valueOf(list.get(getPosition).getCateogry_id()))) {
                            FavouriteCategoriesJsonParser3.selectedCategories3.remove(String.valueOf(list.get(getPosition).getCateogry_id()));



                        }
                    }
                }
            });






            convertView.setTag(viewHolder);
            convertView.setTag(R.id.row_categoryname_textview_sl, viewHolder.categorySerial);
            convertView.setTag(R.id.row_categoryname_textview, viewHolder.categoryName);
            convertView.setTag(R.id.row_categoryname_textview2, viewHolder.categoryName2);
            convertView.setTag(R.id.row_categoryname_textview2, viewHolder.categoryName3);
            convertView.setTag(R.id.row_category_checkbox, viewHolder.categoryCheckBox);
        }


        else {

            viewHolder = (ViewHolder) convertView.getTag();
        }






        viewHolder.categoryCheckBox.setTag(position);
        viewHolder.categoryName3.setTag(position);
        viewHolder.categorySerial.setText(list.get(position).getCateogry_sl());
        viewHolder.categoryName.setText(list.get(position).getCategory_Name());
        viewHolder.categoryName2.setText(list.get(position).getCategory_Name2());
        viewHolder.categoryName3.setText(list.get(position).getCategory_Name3());

        viewHolder.categoryCheckBox.setChecked(list.get(position).isSelected());
        return convertView;
    }
}

