package com.opl.pharmavector;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewHolder> {
    private ArrayList<RecyclerData> DataArrayList;
    Context C ;
    String Report_flag;
    public RecycleViewAdapter(Context c, ArrayList<RecyclerData> recyclerDataArrayList){
        this.C=c;
        this.DataArrayList=recyclerDataArrayList;
    }
    public RecycleViewAdapter(Context c, ArrayList<RecyclerData> recyclerDataArrayList,String Report_flag){
        this.C=c;
        this.DataArrayList=recyclerDataArrayList;
        this.Report_flag=Report_flag;
    }
    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_row_view, parent, false);
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
        holder.txt_col5.setText(model.getCol5());
        holder.txt_col6.setText(model.getCol6());

      if(Report_flag == "cust_replace"){
           holder.txt_col5.setVisibility(View.GONE);
           holder.txt_col6.setVisibility(View.GONE);
           holder.txt_col1.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
           holder.txt_col3.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
           holder.txt_col4.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
          ViewGroup.LayoutParams txt_col1Params =  holder.txt_col1.getLayoutParams();
          txt_col1Params.width=50;
           ViewGroup.LayoutParams txt_col2Params =  holder.txt_col2.getLayoutParams();
           txt_col2Params.width=200;
          ViewGroup.LayoutParams txt_col3Params =  holder.txt_col3.getLayoutParams();
          txt_col3Params.width=50;
          ViewGroup.LayoutParams txt_col4Params =  holder.txt_col4.getLayoutParams();
          txt_col4Params.width=50;
        }
    }
    @Override
    public int getItemCount() {
        return DataArrayList.size();
    }
}
