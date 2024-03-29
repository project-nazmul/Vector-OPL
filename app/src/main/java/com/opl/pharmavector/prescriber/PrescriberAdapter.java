package com.opl.pharmavector.prescriber;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.opl.pharmavector.R;
import com.opl.pharmavector.dcfpFollowup.DcrFollowupAdapter;
import com.opl.pharmavector.dcfpFollowup.DcrFollowupModel;

import java.util.List;

public class PrescriberAdapter extends RecyclerView.Adapter<PrescriberAdapter.PrescriberViewHolder> {
    public List<TopPrescriberList> prescriberLists;
    private Context context;

    public PrescriberAdapter(Context context, List<TopPrescriberList> prescriberList) {
        this.context = context;
        this.prescriberLists = prescriberList;
    }

    @NonNull
    @Override
    public PrescriberViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.prescriber_top_row, viewGroup, false);
        return new PrescriberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PrescriberViewHolder holder, int position) {
        TopPrescriberList prescriberModel = prescriberLists.get(position);
        holder.prescriberSl.setText(String.valueOf(prescriberModel.getSlno()));
        holder.prescriberMpo.setText(prescriberModel.getMpoCode());
        holder.prescriberAm.setText(prescriberModel.getFmCode());
        holder.prescriberRm.setText(prescriberModel.getRmCode());
        holder.prescriberAsm.setText(prescriberModel.getAmCode());
        holder.prescriberDocCode.setText(prescriberModel.getDocCode());
        holder.prescriberDocName.setText(prescriberModel.getDocName());
        holder.prescriberTotalPres.setText(prescriberModel.getTotalPres());
        holder.prescriberSpecialist.setText(prescriberModel.getDocSpec());
        holder.prescriberTop.setText(prescriberModel.getTopValueShare());
        holder.prescriberTopPres.setText(prescriberModel.getTopPres());
        holder.prescriberOpl.setText(prescriberModel.getOplValueShare());
        holder.prescriberOplPres.setText(prescriberModel.getOplPres());
    }

    @Override
    public int getItemCount() {
        return prescriberLists.size();
    }

    public static class PrescriberViewHolder extends RecyclerView.ViewHolder {
        public TextView prescriberSl, prescriberMpo, prescriberAm, prescriberRm, prescriberAsm, prescriberDocCode, prescriberDocName, prescriberTotalPres, prescriberSpecialist,
                prescriberTop, prescriberOpl, prescriberTopPres, prescriberOplPres;

        public PrescriberViewHolder(View view) {
            super(view);
            prescriberSl = view.findViewById(R.id.prescriberSl);
            prescriberMpo = view.findViewById(R.id.prescriberMpo);
            prescriberAm = view.findViewById(R.id.prescriberAm);
            prescriberRm = view.findViewById(R.id.prescriberRm);
            prescriberAsm = view.findViewById(R.id.prescriberAsm);
            prescriberDocCode = view.findViewById(R.id.prescriberDocCode);
            prescriberDocName = view.findViewById(R.id.prescriberDocName);
            prescriberTotalPres = view.findViewById(R.id.prescriberTotalPres);
            prescriberSpecialist = view.findViewById(R.id.prescriberSpecialist);
            prescriberTop = view.findViewById(R.id.prescriberTop);
            prescriberTopPres = view.findViewById(R.id.prescriberTopPres);
            prescriberOpl = view.findViewById(R.id.prescriberOpl);
            prescriberOplPres = view.findViewById(R.id.prescriberOplPres);
        }
    }
}
