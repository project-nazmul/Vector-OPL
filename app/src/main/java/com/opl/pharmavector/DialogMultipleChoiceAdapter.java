package com.opl.pharmavector;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DialogMultipleChoiceAdapter extends BaseAdapter {
    LayoutInflater mLayoutInflater;
    List<Item> mItemList;

    public DialogMultipleChoiceAdapter(Context context, List<Item> itemList) {
        mLayoutInflater = LayoutInflater.from(context);
        mItemList = itemList;
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public Item getItem(int i) {
        return mItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public List<Item> getCheckedItem() {
        List<Item> checkedItemList = new ArrayList<>();
        for (Item item : mItemList) {
            if (item.isChecked()) {
                checkedItemList.add(item);
            }
        }
        return checkedItemList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Item item = getItem(position);

        holder.tvTitle.setText(item.getTitle());
        holder.ivImage.setText(item.getTitle());

        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setChecked(!item.isChecked());
                updateItemState(holder, item.isChecked());
            }
        });
        updateItemState(holder, item.isChecked());
        return convertView;
    }

    private void updateItemState(ViewHolder holder, boolean checked) {
        holder.root.setAlpha(checked ? 1 :1);
        holder.checkbox.setChecked(checked);
    }

    private static class ViewHolder {
        View root;
        TextView tvTitle;
        TextView ivImage;
        CheckBox checkbox;

        ViewHolder(View view) {
            root = view;
            tvTitle = (TextView) view.findViewById(R.id.text);
            ivImage = (TextView) view.findViewById(R.id.image);
            checkbox = (CheckBox) view.findViewById(R.id.checkbox);
        }
    }
}