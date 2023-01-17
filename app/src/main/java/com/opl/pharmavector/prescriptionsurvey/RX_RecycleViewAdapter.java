package com.opl.pharmavector.prescriptionsurvey;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.opl.pharmavector.R;

import java.util.ArrayList;

public class RX_RecycleViewAdapter extends RecyclerView.Adapter<RX_RecycleViewHolder>{
    private ArrayList<rx_model> DataArrayList;
    Context C ;
    public RX_RecycleViewAdapter(Context c, ArrayList<rx_model> recyclerDataArrayList){
        this.C=c;
        this.DataArrayList=recyclerDataArrayList;
    }
    @NonNull
    @Override
    public RX_RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_rx_image, parent, false);
        return new RX_RecycleViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RX_RecycleViewHolder holder, int position) {
        rx_model model = DataArrayList.get(position);
        holder.txt_ff_code.setText(model.getFF_Code());
        holder.txt_brandcount.setText(model.getBrandcount());
        holder.title.setText(model.getTitle());

        String doctor_image = (model.img_path);
        doctor_image = doctor_image.replaceAll(" ", "");

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.skipMemoryCache(true);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        requestOptions.error(R.drawable.ic_imageload);
        Glide.with(C)
                .load(doctor_image)
                .apply(requestOptions)
                .into(holder.img_vw);

    }
    @Override
    public int getItemCount() {
        return DataArrayList.size();
    }
}
