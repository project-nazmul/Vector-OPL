package com.opl.pharmavector.pmdVector.ff_contact;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import com.opl.pharmavector.R;
import com.opl.pharmavector.RecyclerData;
import com.opl.pharmavector.contact.ContactAdapter;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class FFContactAdapter extends RecyclerView.Adapter<FFContactAdapter.FFContactViewHolder> {
    private ArrayList<RecyclerData> DataArrayList;
    Context C;
    String pmdImageUrl, profileImage;
    FFContactCallback contactCallback;

    public FFContactAdapter(Context c, ArrayList<RecyclerData> recyclerDataArrayList, String pmdImageUrl) {
        this.C = c;
        this.DataArrayList = recyclerDataArrayList;
        this.pmdImageUrl = pmdImageUrl;
    }

    public FFContactAdapter(Context c, ArrayList<RecyclerData> recyclerDataArrayList, String pmdImageUrl, FFContactCallback contactCallback) {
        this.C = c;
        this.DataArrayList = recyclerDataArrayList;
        this.pmdImageUrl = pmdImageUrl;
        this.contactCallback = contactCallback;
    }

    @NonNull
    @Override
    public FFContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pmd_sales_contact_row, parent, false);
        return new FFContactViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FFContactViewHolder holder, int position) {
        RecyclerData model = DataArrayList.get(position);
        holder.tvEmployeeCode.setText("("+model.getCol1()+")");
        holder.tvEmployeeName.setText(model.getCol3());
        holder.tvEmpDesignation.setText(model.getCol2());
        holder.tvEmployeePhone.setText(model.getCol4());
        profileImage = pmdImageUrl+model.getCol5()+"."+"jpg" ;
        Picasso.get().load(profileImage).into(holder.imgPmdContact);

        if (model.getCol4() != null) {
            holder.hotlineLayout.setVisibility(View.VISIBLE);
        } else {
            holder.hotlineLayout.setVisibility(View.GONE);
        }
        holder.tvPhoneCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactCallback.onFFContactPhoneCall(model);
            }
        });
        holder.lottiePhoneCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactCallback.onFFContactPhoneCall(model);
            }
        });
        holder.tvPhoneSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactCallback.onFFContactPhoneSms(model);
            }
        });
        holder.lottiePhoneSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactCallback.onFFContactPhoneSms(model);
            }
        });
    }

    @Override
    public int getItemCount() {
        return DataArrayList.size();
    }

    public class FFContactViewHolder extends RecyclerView.ViewHolder {
        public TextView tvEmployeeSL,tvEmployeeCode,tvEmployeeName,tvEmpDesignation,tvEmployeePhone, tvPhoneCall, tvPhoneSms;
        public ImageView imgPmdContact;
        public LottieAnimationView lottiePhoneCall, lottiePhoneSms;
        public LinearLayout hotlineLayout;

        public FFContactViewHolder(View view) {
            super(view);
            tvEmployeeCode = (TextView)itemView.findViewById(R.id.tvEmployeeCode);
            tvEmployeeName = (TextView)itemView.findViewById(R.id.tvEmployeeName);
            tvEmpDesignation = (TextView)itemView.findViewById(R.id.tvEmpDesignation);
            tvEmployeePhone = (TextView)itemView.findViewById(R.id.tvEmployeePhone);
            imgPmdContact = (ImageView) itemView.findViewById(R.id.imgPmdContact);
            tvPhoneCall = (TextView) itemView.findViewById(R.id.tvPhoneCall);
            tvPhoneSms = (TextView) itemView.findViewById(R.id.tvPhoneSms);
            lottiePhoneCall = (LottieAnimationView) itemView.findViewById(R.id.lottiePhoneCall);
            lottiePhoneSms = (LottieAnimationView) itemView.findViewById(R.id.lottiePhoneSms);
            hotlineLayout = (LinearLayout) itemView.findViewById(R.id.hotlineLayout);
        }
    }

    public interface FFContactCallback {
        void onFFContactPhoneCall(RecyclerData recyclerData);
        void onFFContactPhoneSms(RecyclerData recyclerData);
    }
}

