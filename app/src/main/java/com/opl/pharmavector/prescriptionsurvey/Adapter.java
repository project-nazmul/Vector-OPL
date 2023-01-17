package com.opl.pharmavector.prescriptionsurvey;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.PhotoView;
import com.opl.pharmavector.model.Patient;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.opl.pharmavector.R;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> implements Filterable {

    List<Patient> pets, petsFilter;
    private Context context;
    private RecyclerViewClickListener mListener;
    CustomFilter filter;
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    byte[] imageBytes = baos.toByteArray();
    public Adapter(List<Patient> pets, Context context, RecyclerViewClickListener listener) {
        this.pets = pets;
        this.petsFilter = pets;
        this.context = context;
        this.mListener = listener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_rx_list, parent, false);
        return new MyViewHolder(view, mListener);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.mName.setText("Survey Date :\t" + pets.get(position).getFirst_name().trim());
        holder.doc_qualification.setText("MPO Code :\t" + pets.get(position).getLast_name());
        holder.doc_designation.setText(pets.get(position).getPhone_number());
        holder.doc_institute.setText("Number of Brands :\t" + pets.get(position).getEmail());
        holder.doc_fees.setText(String.format("%s\t  %s", context.getString(R.string.rate), pets.get(position).getFees()));
        holder.doc_schedule.setText(pets.get(position).getSchedule());

        holder.doc_id.setText(pets.get(position).getDoctorCode());
        holder.schedule_id.setText(pets.get(position).getScheduleID());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.skipMemoryCache(false);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        String doctor_image = pets.get(position).getPicture();

        //Log.e("imagedecode==>",doctor_image);
        //doctor_image = doctor_image.replaceAll(" ", "");
        imageBytes = Base64.decode(doctor_image, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        holder.mPicture.setImageBitmap(decodedImage);


    }

    @Override
    public int getItemCount() {
        return pets.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new CustomFilter((ArrayList<Patient>) petsFilter, this);

        }
        return filter;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RecyclerViewClickListener mListener;
        private PhotoView mPicture;
        private ImageView mLove;
        private TextView mName, mType, mDate, doc_qualification, doc_designation, doc_institute, doc_fees, doc_id, doc_schedule, schedule_id;


        private LinearLayout mRowContainer;

        public MyViewHolder(View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            mPicture = itemView.findViewById(R.id.picture);
            mName = itemView.findViewById(R.id.name);
            doc_qualification = itemView.findViewById(R.id.doc_qualification);
            doc_designation = itemView.findViewById(R.id.doc_designation);
            doc_institute = itemView.findViewById(R.id.doc_institute);
            doc_fees = itemView.findViewById(R.id.doc_fees);
            doc_schedule = itemView.findViewById(R.id.doc_schedule);
            doc_id = itemView.findViewById(R.id.doc_id);
            schedule_id = itemView.findViewById(R.id.schedule_id);
            mLove = itemView.findViewById(R.id.love);
            mRowContainer = itemView.findViewById(R.id.row_container);
            mListener = listener;
            mRowContainer.setOnClickListener(this);
            mLove.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.row_container:
                    mListener.onRowClick(mRowContainer, getAdapterPosition());
                    break;

                case R.id.love:
                    mListener.onLoveClick(mLove, getAdapterPosition());
                    break;
                default:
                    break;
            }
        }
    }

    public interface RecyclerViewClickListener {
        void onRowClick(View view, int position);

        void onLoveClick(View view, int position);
    }

}
