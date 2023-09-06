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
import com.opl.pharmavector.remote.ApiClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class AchieveEarnAdapter extends RecyclerView.Adapter<AchieveEarnAdapter.AchieveEarnViewHolder> {
    private ArrayList<AchieveEarningList> achieveEarnLists;
    private ArrayList<AchieveEarningList> checkAchieveLists = new ArrayList<>();
    Context context;
    String pmdImageUrl = ApiClient.BASE_URL+"vector_ff_image/sales/", profileImage;
    ContactCallback contactCallback;

    public AchieveEarnAdapter(Context context, ArrayList<AchieveEarningList> achieveEarnList, ContactCallback contactCallback) {
        this.context = context;
        this.achieveEarnLists = achieveEarnList;
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

        if (position > 0) {
            int presentSl = achieveEarnLists.get(position).getSl();
            int pastSl = achieveEarnLists.get(position-1).getSl();
            if (presentSl == pastSl) {
                //holder.hotlineLayout.setVisibility(View.GONE);
                holder.infoLayout.setVisibility(View.GONE);
                holder.achvTextLayout.setVisibility(View.GONE);
            } else {
                holder.infoLayout.setVisibility(View.VISIBLE);
                //holder.hotlineLayout.setVisibility(View.VISIBLE);
                holder.achvTextLayout.setVisibility(View.VISIBLE);
            }
        } else {
            holder.infoLayout.setVisibility(View.VISIBLE);
            //holder.hotlineLayout.setVisibility(View.VISIBLE);
            holder.achvTextLayout.setVisibility(View.VISIBLE);
        }
        holder.tvEmployeeCode.setText("("+achieveModel.getFfCode()+")");
        holder.tvEmployeeName.setText(achieveModel.getEmpName());
        //holder.tvEmpDesignation.setText(achieveModel.getCol2());
        holder.tvEmployeePhone.setText(achieveModel.getMobilePhone());
        holder.brandName.setText(achieveModel.getBrandName());
        holder.achievement.setText(achieveModel.getAchvPer());
        holder.growth.setText(achieveModel.getGrowth());
        holder.targeted_growth.setText(achieveModel.getGrowthTar());
        profileImage = pmdImageUrl+achieveModel.getEmpCode()+"."+"jpg" ;
        Picasso.get().load(profileImage).into(holder.imgPmdContact);
//
//        if (achieveModel.getCol4() != null) {
//            holder.hotlineLayout.setVisibility(View.VISIBLE);
//        } else {
//            holder.hotlineLayout.setVisibility(View.GONE);
//        }
        holder.tvEmployeePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactCallback.onContactPhoneCall(achieveModel);
            }
        });
//        holder.lottiePhoneCall.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                contactCallback.onContactPhoneCall(achieveModel);
//            }
//        });
//        holder.tvPhoneSms.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                contactCallback.onContactPhoneSms(achieveModel);
//            }
//        });
//        holder.lottiePhoneSms.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                contactCallback.onContactPhoneSms(achieveModel);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return achieveEarnLists.size();
    }

    public class AchieveEarnViewHolder extends RecyclerView.ViewHolder {
        public TextView tvEmployeeSL,tvEmployeeCode,tvEmployeeName,tvEmpDesignation,tvEmployeePhone, tvPhoneCall, tvPhoneSms, brandName, achievement, growth, targeted_growth;
        public ImageView imgPmdContact;
        public LottieAnimationView lottiePhoneCall, lottiePhoneSms;
        public LinearLayout hotlineLayout, achieveLayout, infoLayout, achvTextLayout;

        public AchieveEarnViewHolder(View view) {
            super(view);
            tvEmployeeCode = (TextView)itemView.findViewById(R.id.tvEmployeeCode);
            tvEmployeeName = (TextView)itemView.findViewById(R.id.tvEmployeeName);
            //tvEmpDesignation = (TextView)itemView.findViewById(R.id.tvEmpDesignation);
            tvEmployeePhone = (TextView)itemView.findViewById(R.id.tvEmployeePhone);
            imgPmdContact = (ImageView) itemView.findViewById(R.id.imgPmdContact);
            tvPhoneCall = (TextView) itemView.findViewById(R.id.tvPhoneCall);
            tvPhoneSms = (TextView) itemView.findViewById(R.id.tvPhoneSms);
            brandName = (TextView) itemView.findViewById(R.id.brandName);
            achievement = (TextView) itemView.findViewById(R.id.achievement);
            growth = (TextView) itemView.findViewById(R.id.growth);
            targeted_growth = (TextView) itemView.findViewById(R.id.targeted_growth);
            lottiePhoneCall = (LottieAnimationView) itemView.findViewById(R.id.lottiePhoneCall);
            lottiePhoneSms = (LottieAnimationView) itemView.findViewById(R.id.lottiePhoneSms);
            infoLayout = (LinearLayout) itemView.findViewById(R.id.infoLayout);
            hotlineLayout = (LinearLayout) itemView.findViewById(R.id.hotlineLayout);
            achieveLayout = (LinearLayout) itemView.findViewById(R.id.achieveLayout);
            achvTextLayout = (LinearLayout) itemView.findViewById(R.id.achvTextLayout);
        }
    }

    public interface ContactCallback {
        void onContactPhoneCall(AchieveEarningList achieveList);
        void onContactPhoneSms(AchieveEarningList achieveList);
    }
}