package com.opl.pharmavector.contact;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.opl.pharmavector.R;
import com.opl.pharmavector.RecyclerData;
import com.opl.pharmavector.master_code.model.MasterCList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private List<RecyclerData> DataArrayList;
    Context C;
    String pmdImageUrl, profileImage;
    ContactCallback contactCallback;

    public ContactAdapter(Context c, List<RecyclerData> recyclerDataArrayList, String pmdImageUrl) {
        this.C = c;
        this.DataArrayList = recyclerDataArrayList;
        this.pmdImageUrl = pmdImageUrl;
    }

    public ContactAdapter(Context c, List<RecyclerData> recyclerDataArrayList, String pmdImageUrl, ContactCallback contactCallback) {
        this.C = c;
        this.DataArrayList = recyclerDataArrayList;
        this.pmdImageUrl = pmdImageUrl;
        this.contactCallback = contactCallback;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pmd_contact_row, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        RecyclerData model = DataArrayList.get(position);
        holder.tvEmployeeSL.setText(model.getCol1());
        holder.tvEmployeeCode.setText(model.getCol7());
        holder.tvEmployeeName.setText(model.getCol3());
        holder.tvEmpDesignation.setText(model.getCol2());
        holder.tvEmployeePhone.setText(model.getCol4());
        profileImage = pmdImageUrl+model.getCol7()+"."+"jpg" ;
        Picasso.get().load(profileImage).into(holder.imgPmdContact);

        holder.tvPhoneCall.setOnClickListener(v -> contactCallback.onContactPhoneCall(model));
        holder.lottiePhoneCall.setOnClickListener(v -> contactCallback.onContactPhoneCall(model));
        holder.tvPhoneSms.setOnClickListener(v -> contactCallback.onContactPhoneSms(model));
        holder.lottiePhoneSms.setOnClickListener(v -> contactCallback.onContactPhoneSms(model));
    }

    @Override
    public int getItemCount() {
        return DataArrayList.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        public TextView tvEmployeeSL,tvEmployeeCode,tvEmployeeName,tvEmpDesignation,tvEmployeePhone, tvPhoneCall, tvPhoneSms;
        public ImageView imgPmdContact;
        public LottieAnimationView lottiePhoneCall, lottiePhoneSms;

        public ContactViewHolder(View view) {
            super(view);
            tvEmployeeSL = (TextView)itemView.findViewById(R.id.tvEmployeeSL);
            tvEmployeeCode = (TextView)itemView.findViewById(R.id.tvEmployeeCode);
            tvEmployeeName = (TextView)itemView.findViewById(R.id.tvEmployeeName);
            tvEmpDesignation = (TextView)itemView.findViewById(R.id.tvEmpDesignation);
            tvEmployeePhone = (TextView)itemView.findViewById(R.id.tvEmployeePhone);
            imgPmdContact = (ImageView) itemView.findViewById(R.id.imgPmdContact);
            tvPhoneCall = (TextView) itemView.findViewById(R.id.tvPhoneCall);
            tvPhoneSms = (TextView) itemView.findViewById(R.id.tvPhoneSms);
            lottiePhoneCall = (LottieAnimationView) itemView.findViewById(R.id.lottiePhoneCall);
            lottiePhoneSms = (LottieAnimationView) itemView.findViewById(R.id.lottiePhoneSms);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void searchPmdContactList(List<RecyclerData> mCodeList) {
        DataArrayList = mCodeList;
        notifyDataSetChanged();
    }

    public interface ContactCallback {
        void onContactPhoneCall(RecyclerData recyclerData);
        void onContactPhoneSms(RecyclerData recyclerData);
    }
}

