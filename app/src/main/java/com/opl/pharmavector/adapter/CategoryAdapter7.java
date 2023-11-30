package com.opl.pharmavector.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.opl.pharmavector.R;
import com.opl.pharmavector.model.Category5;
import com.opl.pharmavector.serverCalls.FavouriteCategoriesJsonParser7;
import java.util.List;

public class CategoryAdapter7 extends ArrayAdapter<Category5> {
    private int flag = 0;
    private final List<Category5> list;

    public CategoryAdapter7(Context context, int resource, List<Category5> list, int flag) {
        super(context, resource, list);
        this.list = list;
        this.flag = flag;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ViewHolder {
        protected TextView categorySerial;
        protected TextView categoryName;
        protected TextView categoryName2;
        protected TextView categoryName3;
        protected TextView categoryName4;
        protected TextView categoryName5;
        protected TextView categoryName6;
        protected TextView categoryName7;
        protected TextView categoryName8;
        protected TextView categoryName9;
        protected TextView categoryName10;
        protected CheckBox categoryCheckBox;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //ViewHolder viewHolder = null;
        final ViewHolder viewHolder ;

        if (convertView == null) {
            LayoutInflater inflator = LayoutInflater.from(getContext());
            //convertView = inflater.inflate(R.layout.row_category_6, null);
            convertView = inflator.inflate(R.layout.row_category_doc_service_approval, null);
            viewHolder = new ViewHolder();

            viewHolder.categorySerial = (TextView) convertView.findViewById(R.id.row_categoryname_textview_sl);
            viewHolder.categoryName = (TextView) convertView.findViewById(R.id.row_categoryname_textview);
            viewHolder.categoryName2 = (TextView) convertView.findViewById(R.id.row_categoryname_textview2);
            viewHolder.categoryName3 = (TextView) convertView.findViewById(R.id.row_categoryname_textview3);

            viewHolder.categoryName4 = (TextView) convertView.findViewById(R.id.row_categoryname_textview4);
            viewHolder.categoryName5 = (TextView) convertView.findViewById(R.id.row_categoryname_textview5);
            viewHolder.categoryName6 = (TextView) convertView.findViewById(R.id.row_categoryname_textview6);
            viewHolder.categoryName7 = (TextView) convertView.findViewById(R.id.row_categoryname_textview7);

            viewHolder.categoryName8 = (TextView) convertView.findViewById(R.id.row_categoryname_textview8);
            viewHolder.categoryName9 = (TextView) convertView.findViewById(R.id.row_categoryname_textview9);
            viewHolder.categoryName10 = (TextView) convertView.findViewById(R.id.row_categoryname_textview10);
            viewHolder.categoryCheckBox = (CheckBox) convertView.findViewById(R.id.row_category_checkbox);
            if (flag == 1) {
              viewHolder.categoryCheckBox.setVisibility(View.GONE);
            }
            viewHolder.categoryCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int getPosition = (Integer) buttonView.getTag();
                    Log.e("getPosition", String.valueOf(getPosition));
                    Log.e("getPositionValues", FavouriteCategoriesJsonParser7.selectedCategories4.toString());
                    list.get(getPosition).setSelected(buttonView.isChecked());

                    if (buttonView.isChecked()) {
                        if (!FavouriteCategoriesJsonParser7.selectedCategories4.contains(String.valueOf(list.get(getPosition).getCateogry_id()))) {
                            FavouriteCategoriesJsonParser7.selectedCategories4.add(String.valueOf(list.get(getPosition).getCateogry_id()));
                        }
                    } else {
                        if (FavouriteCategoriesJsonParser7.selectedCategories4.contains(String.valueOf(list.get(getPosition).getCateogry_id()))) {
                            FavouriteCategoriesJsonParser7.selectedCategories4.remove(String.valueOf(list.get(getPosition).getCateogry_id()));
                        }
                    }
                }
            });
            convertView.setTag(viewHolder);
            convertView.setTag(R.id.row_categoryname_textview_sl, viewHolder.categorySerial);
            convertView.setTag(R.id.row_categoryname_textview, viewHolder.categoryName);
            convertView.setTag(R.id.row_categoryname_textview2, viewHolder.categoryName2);
            convertView.setTag(R.id.row_categoryname_textview3, viewHolder.categoryName3);
            convertView.setTag(R.id.row_categoryname_textview4, viewHolder.categoryName4);
            convertView.setTag(R.id.row_categoryname_textview5, viewHolder.categoryName5);
            convertView.setTag(R.id.row_categoryname_textview6, viewHolder.categoryName6);
            convertView.setTag(R.id.row_categoryname_textview7, viewHolder.categoryName7);
            convertView.setTag(R.id.row_categoryname_textview8, viewHolder.categoryName8);
            convertView.setTag(R.id.row_categoryname_textview9, viewHolder.categoryName9);
            convertView.setTag(R.id.row_categoryname_textview10, viewHolder.categoryName10);
            convertView.setTag(R.id.row_category_checkbox, viewHolder.categoryCheckBox);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.categoryCheckBox.setTag(position);
        viewHolder.categoryName3.setTag(position);
        viewHolder.categorySerial.setText(list.get(position).getCateogry_sl());
        viewHolder.categoryName.setText(list.get(position).getCategory_Name());
        viewHolder.categoryName2.setText(list.get(position).getCategory_Name2());
        viewHolder.categoryName3.setText(list.get(position).getCategory_Name3());

        viewHolder.categoryName4.setText(list.get(position).getCategory_Name4());
        viewHolder.categoryName5.setText(list.get(position).getCategory_Name5());
        viewHolder.categoryName6.setText(list.get(position).getCategory_Name6());
        viewHolder.categoryName7.setText(list.get(position).getCategory_Name7());

        viewHolder.categoryName8.setText(list.get(position).getCategory_Name8());
        viewHolder.categoryName9.setText(list.get(position).getCategory_Name9());
        viewHolder.categoryName10.setText(list.get(position).getCategory_Name10());

/*
                 genres.setCateogry_sl(MyJsonObject.getString("TERRI_NAME"));
                 genres.setCateogry_id(MyJsonObject.getString("SERVICE_NO"));
                 genres.setCategory_Name(MyJsonObject.getString("SERV_MONTH"));
                 genres.setCategory_Name2(MyJsonObject.getString("REQUEST_DT"));
                 genres.setCategory_Name3(MyJsonObject.getString("DOC_NAME"));
                 genres.setCategory_Name4(MyJsonObject.getString("SERV_DESC"));
                 genres.setCategory_Name5(MyJsonObject.getString("APPROXIMATE_COST"));
                 genres.setCategory_Name6(MyJsonObject.getString("IS_MRD_CHECKED"));
                 genres.setCategory_Name7(MyJsonObject.getString("IS_SALES_APPROVED"));
                 genres.setCategory_Name8(MyJsonObject.getString("APPROVE_DT"));
                 genres.setCategory_Name9(MyJsonObject.getString("IS_PAYMENT"));
                 genres.setCategory_Name10(MyJsonObject.getString("PAYMENT_DT"));

 */
        viewHolder.categoryCheckBox.setChecked(list.get(position).isSelected());
        return convertView;
    }
}


