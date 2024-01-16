package com.opl.pharmavector.incentive;

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
import com.opl.pharmavector.achieve.AchieveEarningList;
import com.opl.pharmavector.remote.ApiClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class IncentiveAdapter extends RecyclerView.Adapter<IncentiveAdapter.IncentiveViewHolder> {
    ArrayList<IncentiveDataList> incentiveLists;
    ArrayList<AchieveEarningList> checkAchieveLists = new ArrayList<>();
    Context context;
    String pmdImageUrl = ApiClient.BASE_URL+"vector_ff_image/pmd/", profileImage, userRole, designationType;

    public IncentiveAdapter(Context context, ArrayList<IncentiveDataList> incentiveList, String userRole, String designationType) {
        this.context = context;
        this.userRole = userRole;
        this.designationType = designationType;
        this.incentiveLists = incentiveList;
    }

    @NonNull
    @Override
    public IncentiveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.incentive_pmd_row, parent, false);
        return new IncentiveViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull IncentiveViewHolder holder, int position) {
        IncentiveDataList incentiveModel = incentiveLists.get(position);

        if (position > 0) {
            int presentSl = incentiveLists.get(position).getSl();
            int pastSl = incentiveLists.get(position-1).getSl();
            String presentType = incentiveLists.get(position).getIncentiveType();
            String pastType = incentiveLists.get(position-1).getIncentiveType();

            if (presentSl == pastSl && presentType.equals(pastType)) {
                holder.infoLayout.setVisibility(View.GONE);
                holder.titleLayout.setVisibility(View.GONE);
                holder.dividerLayout.setVisibility(View.GONE);
                holder.incentiveTitle.setVisibility(View.GONE);
            } else if (presentSl != pastSl && !Objects.equals(presentType, pastType)) {
                holder.infoLayout.setVisibility(View.VISIBLE);
                holder.titleLayout.setVisibility(View.VISIBLE);
                holder.dividerLayout.setVisibility(View.VISIBLE);
                holder.incentiveTitle.setVisibility(View.VISIBLE);
            } else {
                holder.infoLayout.setVisibility(View.GONE);
                holder.dividerLayout.setVisibility(View.GONE);
                holder.titleLayout.setVisibility(View.VISIBLE);
                holder.incentiveTitle.setVisibility(View.VISIBLE);
            }
        } else {
            holder.infoLayout.setVisibility(View.VISIBLE);
            holder.titleLayout.setVisibility(View.VISIBLE);
            //holder.dividerLayout.setVisibility(View.GONE);
            holder.incentiveTitle.setVisibility(View.VISIBLE);
        }
        holder.tvEmployeeCode.setText("[ "+incentiveModel.getWorkLocCode()+" ]");
        holder.tvEmployeeName.setText(incentiveModel.getEmpName());
        holder.tvFFName.setText(incentiveModel.getWorkLocation());

        switch (incentiveModel.getIncentiveType()) {
            case "PST": holder.incentiveTitle.setText("Portfolio Sales Target");
                holder.expenseTitle.setVisibility(View.GONE);
                holder.expenseValue.setVisibility(View.GONE);
                holder.growthTitle.setVisibility(View.GONE);
                holder.growthValue.setVisibility(View.GONE);
            break;
            case "PSG": holder.incentiveTitle.setText("Portfolio Sales Growth (%)");
                break;
            case "MBA": holder.incentiveTitle.setText("Major Brand Ach (%)");
                //holder.expenseTitle.setVisibility(View.GONE);
                //holder.expenseValue.setVisibility(View.GONE);
                //holder.expenseTitle1.setVisibility(View.VISIBLE);
                //holder.expenseValue1.setVisibility(View.VISIBLE);
                break;
            case "NPS": holder.incentiveTitle.setText("New Product Sales Ach (%)");
                holder.expenseTitle.setVisibility(View.GONE);
                holder.expenseValue.setVisibility(View.GONE);
                holder.growthTitle.setVisibility(View.GONE);
                holder.growthValue.setVisibility(View.GONE);
                break;
        }
//        if (incentiveModel.getRemainValue() != null) {
//            holder.remain_value.setText(incentiveModel.getRemainValue());
//        } else {
//            holder.remain_value.setText("0");
//        }
        holder.tvEmployeePhone.setText(incentiveModel.getMobilePhone());
        holder.monthValue.setText(incentiveModel.getCurrentMonth());
        holder.targetValue.setText(incentiveModel.getCurrentTarget());
        holder.salesValue.setText(incentiveModel.getCurrentSale());
        holder.achieveValue.setText(incentiveModel.getAcheivement());
        holder.expenseValue.setText(incentiveModel.getExpenseRatio());
        holder.expenseValue1.setText(incentiveModel.getExpenseRatio());
        holder.growthValue.setText(incentiveModel.getGrowth());
        profileImage = pmdImageUrl+incentiveModel.getEmpCode()+"."+"jpg" ;
        Picasso.get().load(profileImage).into(holder.imgPmdContact);
    }

    @Override
    public int getItemCount() {
        return incentiveLists.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class IncentiveViewHolder extends RecyclerView.ViewHolder {
        public TextView incentiveTitle, tvEmployeeCode, tvEmployeeName, remain_value, tvEmployeePhone, tvPhoneCall, tvPhoneSms, brandName, achievement, growth, targeted_growth, notice_title, tvFFName,
                monthTitle, targetTitle, salesTitle, AchieveTitle, expenseTitle, expenseTitle1, growthTitle, monthValue, targetValue, salesValue, achieveValue, expenseValue, growthValue, expenseValue1;
        public ImageView imgPmdContact;
        public LinearLayout hotlineLayout, achieveLayout, infoLayout, dividerLayout, titleLayout;

        public IncentiveViewHolder(View view) {
            super(view);
            incentiveTitle = (TextView)itemView.findViewById(R.id.incentiveTitle);
            tvEmployeeCode = (TextView)itemView.findViewById(R.id.tvEmployeeCode);
            tvEmployeeName = (TextView)itemView.findViewById(R.id.tvEmployeeName);
            tvEmployeePhone = (TextView)itemView.findViewById(R.id.tvEmployeePhone);
            imgPmdContact = (ImageView) itemView.findViewById(R.id.imgPmdContact);
            tvPhoneCall = (TextView) itemView.findViewById(R.id.tvPhoneCall);
            tvPhoneSms = (TextView) itemView.findViewById(R.id.tvPhoneSms);
            brandName = (TextView) itemView.findViewById(R.id.brandName);
            achievement = (TextView) itemView.findViewById(R.id.achievement);
            growth = (TextView) itemView.findViewById(R.id.growth);
            tvFFName = (TextView) itemView.findViewById(R.id.tvFFName);
            remain_value = (TextView) itemView.findViewById(R.id.remain_value);
            notice_title = (TextView) itemView.findViewById(R.id.notice_title);
            targeted_growth = (TextView) itemView.findViewById(R.id.targeted_growth);
            monthTitle = (TextView) itemView.findViewById(R.id.monthTitle);
            targetTitle = (TextView) itemView.findViewById(R.id.targetTitle);
            salesTitle = (TextView) itemView.findViewById(R.id.salesTitle);
            AchieveTitle = (TextView) itemView.findViewById(R.id.AchieveTitle);
            expenseTitle = (TextView) itemView.findViewById(R.id.expenseTitle);
            expenseTitle1 = (TextView) itemView.findViewById(R.id.expenseTitle1);
            growthTitle = (TextView) itemView.findViewById(R.id.growthTitle);
            monthValue = (TextView) itemView.findViewById(R.id.monthValue);
            targetValue = (TextView) itemView.findViewById(R.id.targetValue);
            salesValue = (TextView) itemView.findViewById(R.id.salesValue);
            achieveValue = (TextView) itemView.findViewById(R.id.achieveValue);
            expenseValue = (TextView) itemView.findViewById(R.id.expenseValue);
            expenseValue1 = (TextView) itemView.findViewById(R.id.expenseValue1);
            growthValue = (TextView) itemView.findViewById(R.id.growthValue);
            infoLayout = (LinearLayout) itemView.findViewById(R.id.infoLayout);
            hotlineLayout = (LinearLayout) itemView.findViewById(R.id.hotlineLayout);
            achieveLayout = (LinearLayout) itemView.findViewById(R.id.achieveLayout);
            dividerLayout = (LinearLayout) itemView.findViewById(R.id.dividerLayout);
            titleLayout = (LinearLayout) itemView.findViewById(R.id.titleLayout);
        }
    }
}
