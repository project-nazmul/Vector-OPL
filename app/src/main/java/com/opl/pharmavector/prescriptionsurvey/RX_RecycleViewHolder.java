package com.opl.pharmavector.prescriptionsurvey;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.github.chrisbanes.photoview.PhotoView;
import com.opl.pharmavector.R;

public class RX_RecycleViewHolder extends RecyclerView.ViewHolder {
    public TextView txt_ff_code,title,txt_brandcount;
    PhotoView img_vw;
    public RX_RecycleViewHolder(View itemView) {
        super(itemView);
        txt_ff_code=(TextView)itemView.findViewById(R.id.txt_ff_code);
        title=(TextView)itemView.findViewById(R.id.title);
        txt_brandcount=(TextView)itemView.findViewById(R.id.txt_brandcount);
        img_vw=(PhotoView) itemView.findViewById(R.id.img_vw);
    }
}
