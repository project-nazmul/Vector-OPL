package com.opl.pharmavector.achieve;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.text.Html;
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
import com.opl.pharmavector.remote.ApiClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class AchieveEarnAdapter extends RecyclerView.Adapter<AchieveEarnAdapter.AchieveEarnViewHolder> {
    private ArrayList<AchieveEarningList> achieveEarnLists;
    private ArrayList<AchieveEarningList> checkAchieveLists = new ArrayList<>();
    Context context;
    String pmdImageUrl = ApiClient.BASE_URL+"vector_ff_image/sales/", profileImage, userRole;
    ContactCallback contactCallback;

    public AchieveEarnAdapter(Context context, ArrayList<AchieveEarningList> achieveEarnList, String userRole, ContactCallback contactCallback) {
        this.context = context;
        this.userRole = userRole;
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
                holder.infoLayout.setVisibility(View.GONE);
                holder.achvTextLayout.setVisibility(View.GONE);
            } else {
                holder.infoLayout.setVisibility(View.VISIBLE);
                holder.achvTextLayout.setVisibility(View.VISIBLE);
            }
        } else {
            holder.infoLayout.setVisibility(View.VISIBLE);
            holder.achvTextLayout.setVisibility(View.VISIBLE);
        }
        holder.tvEmployeeCode.setText("("+achieveModel.getFfCode()+")");
        holder.tvEmployeeName.setText(achieveModel.getEmpName());
        if (achieveModel.getRemainValue() != null) {
            holder.remain_value.setText(achieveModel.getRemainValue());
        } else {
            holder.remain_value.setText("0");
        }
        holder.tvEmployeePhone.setText(achieveModel.getMobilePhone());
        holder.brandName.setText(achieveModel.getBrandName());
        holder.achievement.setText(achieveModel.getAchvPer());
        holder.growth.setText(achieveModel.getGrowth());
        holder.targeted_growth.setText(achieveModel.getGrowthTar());
        profileImage = pmdImageUrl+achieveModel.getEmpCode()+"."+"jpg" ;
        Picasso.get().load(profileImage).into(holder.imgPmdContact);

        if (Objects.equals(userRole, "MPO") && position == achieveEarnLists.size()-1) {
            holder.notice_title.setVisibility(View.VISIBLE);
            holder.notice_title.setText(Html.fromHtml("<h2><font color='red'>Important Notice</font><h2><br><p>This calculator is in beta version. It can be used for rough calculations only. Any claim based on this calculator will not be taken into consideration. Final assessment will be done in CHQ for money disbursement as per existing procedure." +
                    "<br><br>এই ক্যালকুলেটরটি বিটা সংস্করণে রয়েছে। এটি শুধুমাত্র আনুমানিক হিসাবের জন্য  ব্যবহার করা যেতে পারে। এই ক্যালকুলেটরের উপর ভিত্তি করে কোনো দাবি বিবেচনায় নেওয়া হবে না। বিদ্যমান পদ্ধতি অনুযায়ী অর্থ প্রদানের জন্য CHQ-এ মূল্যায়নই চূড়ান্ত ।</p>", Html.FROM_HTML_MODE_COMPACT));
        }

        holder.tvEmployeePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactCallback.onContactPhoneCall(achieveModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return achieveEarnLists.size();
    }

    public class AchieveEarnViewHolder extends RecyclerView.ViewHolder {
        public TextView tvEmployeeSL, tvEmployeeCode, tvEmployeeName, remain_value, tvEmployeePhone, tvPhoneCall, tvPhoneSms, brandName, achievement, growth, targeted_growth, notice_title;
        public ImageView imgPmdContact;
        public LottieAnimationView lottiePhoneCall, lottiePhoneSms;
        public LinearLayout hotlineLayout, achieveLayout, infoLayout, achvTextLayout;

        public AchieveEarnViewHolder(View view) {
            super(view);
            tvEmployeeCode = (TextView)itemView.findViewById(R.id.tvEmployeeCode);
            tvEmployeeName = (TextView)itemView.findViewById(R.id.tvEmployeeName);
            tvEmployeePhone = (TextView)itemView.findViewById(R.id.tvEmployeePhone);
            imgPmdContact = (ImageView) itemView.findViewById(R.id.imgPmdContact);
            tvPhoneCall = (TextView) itemView.findViewById(R.id.tvPhoneCall);
            tvPhoneSms = (TextView) itemView.findViewById(R.id.tvPhoneSms);
            brandName = (TextView) itemView.findViewById(R.id.brandName);
            achievement = (TextView) itemView.findViewById(R.id.achievement);
            growth = (TextView) itemView.findViewById(R.id.growth);
            remain_value = (TextView) itemView.findViewById(R.id.remain_value);
            notice_title = (TextView) itemView.findViewById(R.id.notice_title);
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
