package com.opl.pharmavector;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecycleViewHolder extends RecyclerView.ViewHolder {
    public TextView txt_col1,txt_col2,txt_col3,txt_col4,txt_col5,txt_col6,txt_col7,txt_col8;
    public RecycleViewHolder(@NonNull View itemView) {
        super(itemView);
        txt_col1=(TextView)itemView.findViewById(R.id.txt_col1);
        txt_col2=(TextView)itemView.findViewById(R.id.txt_col2);
        txt_col3=(TextView)itemView.findViewById(R.id.txt_col3);
        txt_col4=(TextView)itemView.findViewById(R.id.txt_col4);
        txt_col5=(TextView)itemView.findViewById(R.id.txt_col5);
        txt_col6=(TextView)itemView.findViewById(R.id.txt_col6);
        txt_col7=(TextView)itemView.findViewById(R.id.txt_col7);
        txt_col8=(TextView)itemView.findViewById(R.id.txt_col8);
    }
}
