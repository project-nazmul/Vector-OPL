package com.opl.pharmavector.achievement;

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
import com.opl.pharmavector.pmdVector.ff_contact.FFContactAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AchieveEarnAdapter extends RecyclerView.Adapter<AchieveEarnAdapter.AchieveEarnViewHolder> {
    private ArrayList<AchieveEarningList> achieveEarnLists;
    Context C;
    String pmdImageUrl, profileImage;
    FFContactAdapter.FFContactCallback contactCallback;

    public AchieveEarnAdapter(Context c, ArrayList<AchieveEarningList> achieveEarnList, String pmdImageUrl) {
        this.C = c;
        this.achieveEarnLists = achieveEarnList;
        this.pmdImageUrl = pmdImageUrl;
    }

    public AchieveEarnAdapter(Context c, ArrayList<AchieveEarningList> achieveEarnList, String pmdImageUrl, FFContactAdapter.FFContactCallback contactCallback) {
        this.C = c;
        this.achieveEarnLists = achieveEarnList;
        this.pmdImageUrl = pmdImageUrl;
        this.contactCallback = contactCallback;
    }

    @NonNull
    @Override
    public AchieveEarnViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.achieve_earn_row, parent, false);
        return new AchieveEarnViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AchieveEarnViewHolder holder, int position) {
        AchieveEarningList achieveModel = achieveEarnLists.get(position);
//        holder.tvEmployeeCode.setText("("+achieveModel.getCol1()+")");
//        holder.tvEmployeeName.setText(achieveModel.getCol3());
//        holder.tvEmpDesignation.setText(achieveModel.getCol2());
//        holder.tvEmployeePhone.setText(achieveModel.getCol4());
//        profileImage = pmdImageUrl+achieveModel.getCol5()+"."+"jpg" ;
//        Picasso.get().load(profileImage).into(holder.imgPmdContact);
//
//        if (achieveModel.getCol4() != null) {
//            holder.hotlineLayout.setVisibility(View.VISIBLE);
//        } else {
//            holder.hotlineLayout.setVisibility(View.GONE);
//        }
//        holder.tvPhoneCall.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                contactCallback.onFFContactPhoneCall(achieveModel);
//            }
//        });
//        holder.lottiePhoneCall.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                contactCallback.onFFContactPhoneCall(achieveModel);
//            }
//        });
//        holder.tvPhoneSms.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                contactCallback.onFFContactPhoneSms(achieveModel);
//            }
//        });
//        holder.lottiePhoneSms.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                contactCallback.onFFContactPhoneSms(achieveModel);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return achieveEarnLists.size();
    }

    public class AchieveEarnViewHolder extends RecyclerView.ViewHolder {
        public TextView tvEmployeeSL,tvEmployeeCode,tvEmployeeName,tvEmpDesignation,tvEmployeePhone, tvPhoneCall, tvPhoneSms;
        public ImageView imgPmdContact;
        public LottieAnimationView lottiePhoneCall, lottiePhoneSms;
        public LinearLayout hotlineLayout;

        public AchieveEarnViewHolder(View view) {
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