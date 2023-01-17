package com.opl.pharmavector.contact;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.opl.pharmavector.Category;
import com.opl.pharmavector.NoticeAdapter;
import com.opl.pharmavector.R;
import com.opl.pharmavector.RecycleViewHolder;
import com.opl.pharmavector.RecyclerData;

import java.util.ArrayList;

    public class contact_adapter extends RecyclerView.Adapter<RecycleViewHolder> {

        private ArrayList<RecyclerData> DataArrayList;
        Context C;
        String Report_flag;

        public contact_adapter(Context c, ArrayList<RecyclerData> recyclerDataArrayList) {
            this.C = c;
            this.DataArrayList = recyclerDataArrayList;
        }

        @NonNull
        @Override
        public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pmd_contact_row, parent, false);
            return new RecycleViewHolder(view);
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public void onBindViewHolder(@NonNull RecycleViewHolder holder, int position) {

            RecyclerData model = DataArrayList.get(position);
            holder.txt_col1.setText(model.getCol1());
            holder.txt_col2.setText(model.getCol2());
            holder.txt_col3.setText(model.getCol3());
            holder.txt_col4.setText(model.getCol4());
            //holder.txt_col5.setText(model.getCol5());
            //holder.txt_col6.setText(model.getCol6());
        }
        @Override
        public int getItemCount() {
            return DataArrayList.size();
        }
    }

