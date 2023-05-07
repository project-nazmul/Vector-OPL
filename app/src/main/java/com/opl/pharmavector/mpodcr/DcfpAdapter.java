package com.opl.pharmavector.mpodcr;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
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

public class DcfpAdapter extends RecyclerView.Adapter<DcfpAdapter.DcfpViewHolder> {
    public List<DcfpList> dcfpLists;
    private Context context;

    public DcfpAdapter(Context context, List<DcfpList> dcfpList) {
        this.context = context;
        this.dcfpLists = dcfpList;
    }

    @Override
    public DcfpViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dcfp_list_row, viewGroup, false);
        return new DcfpViewHolder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(DcfpViewHolder holder, int position) {
        DcfpList dcfpList = dcfpLists.get(position);

//      String v_type = dcfpList.getVisitType();
//      holder.visitType.setText(String.valueOf(v_type.charAt(0)));
        holder.visitType.setText(dcfpList.getVisitType());
        holder.docCode.setText(dcfpList.getDocCode());
        holder.docName.setText(dcfpList.getDocName());
        holder.mktDesc.setText(dcfpList.getMktDesc());

        if (Objects.equals(dcfpList.getVisitStat(), "Y")) {
            //holder.layout.setBackgroundColor(Color.parseColor("#db5a6b"));
            //holder.cardLayout.setBackgroundColor(Color.parseColor("#fedde3"));
            holder.cardLayout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.dcfp_y_gradient));
        } else {
            //holder.layout.setBackgroundColor(Color.parseColor("#a4c639"));
            holder.cardLayout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.dcfp_n_gradient));
        }
    }

    @Override
    public int getItemCount() {
        return dcfpLists.size();
    }

    public class DcfpViewHolder extends RecyclerView.ViewHolder {
        public TextView visitType, docCode, docName, mktDesc;
        public LinearLayout layout;
        public CardView cardLayout;

        public DcfpViewHolder(View view) {
            super(view);
            visitType = view.findViewById(R.id.visitType);
            docCode = view.findViewById(R.id.docCode);
            docName = view.findViewById(R.id.docName);
            mktDesc = view.findViewById(R.id.mktDesc);
            layout = view.findViewById(R.id.listLayout);
            cardLayout = view.findViewById(R.id.cardLayout);
        }
    }
}
