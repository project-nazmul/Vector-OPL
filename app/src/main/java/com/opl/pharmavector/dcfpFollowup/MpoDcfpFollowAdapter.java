package com.opl.pharmavector.dcfpFollowup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.opl.pharmavector.R;

import java.util.List;
import java.util.Objects;

public class MpoDcfpFollowAdapter extends RecyclerView.Adapter<MpoDcfpFollowAdapter.MpoDcfpViewHolder> {
    public List<MpoDcfpFollowList> mpoDcfpFollowLists;
    private Context context;

    public MpoDcfpFollowAdapter(Context context, List<MpoDcfpFollowList> mpoDcfpFollowList) {
        this.context = context;
        this.mpoDcfpFollowLists = mpoDcfpFollowList;
    }

    @Override
    public MpoDcfpViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mpo_dcfp_follow_row, viewGroup, false);
        return new MpoDcfpViewHolder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(MpoDcfpViewHolder holder, int position) {
        MpoDcfpFollowList mpoDcfpList = mpoDcfpFollowLists.get(position);
        holder.visitType.setText(mpoDcfpList.getVisitType());
        holder.docCode.setText(mpoDcfpList.getDocCode());
        holder.docName.setText(mpoDcfpList.getDocName());
        holder.mktDesc.setText(mpoDcfpList.getMktDesc());
        holder.segCode.setText(mpoDcfpList.getTsCode());

        if (Objects.equals(mpoDcfpList.getVisitStat(), "N")) {
            holder.cardLayout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.dcfp_y_gradient));
        } else {
            holder.cardLayout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.dcfp_n_gradient));
        }
    }

    @Override
    public int getItemCount() {
        return mpoDcfpFollowLists.size();
    }

    public class MpoDcfpViewHolder extends RecyclerView.ViewHolder {
        public TextView visitType, docCode, docName, mktDesc, segCode;
        public LinearLayout layout;
        public CardView cardLayout;

        public MpoDcfpViewHolder(View view) {
            super(view);
            visitType = view.findViewById(R.id.visitType);
            docCode = view.findViewById(R.id.docCode);
            docName = view.findViewById(R.id.docName);
            segCode = view.findViewById(R.id.segCode);
            mktDesc = view.findViewById(R.id.mktDesc);
            layout = view.findViewById(R.id.listLayout);
            cardLayout = view.findViewById(R.id.cardLayout);
        }
    }
}