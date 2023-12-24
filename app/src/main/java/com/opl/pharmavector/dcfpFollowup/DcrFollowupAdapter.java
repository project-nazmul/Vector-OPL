package com.opl.pharmavector.dcfpFollowup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.opl.pharmavector.R;
import java.util.List;
import java.util.Objects;

public class DcrFollowupAdapter extends RecyclerView.Adapter<DcrFollowupAdapter.DcrFollowupViewHolder> {
    public List<DcrFollowupModel> dcrFollowupList;
    public Context context;
    public String userRole;
    public ItemClickListener itemClickListener;

    public DcrFollowupAdapter(Context context, List<DcrFollowupModel> dcrFollowupList, ItemClickListener itemClickListener, String userRole) {
        this.context = context;
        this.dcrFollowupList = dcrFollowupList;
        this.itemClickListener = itemClickListener;
        this.userRole = userRole;
    }

    @Override
    public DcrFollowupViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dcr_followup_row, viewGroup, false);
        return new DcrFollowupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DcrFollowupViewHolder holder, int position) {
        DcrFollowupModel dcrFollowupModel = dcrFollowupList.get(position);
        holder.ff_code.setText(dcrFollowupModel.getFfCode());
        holder.ff_area.setText(dcrFollowupModel.getFfName());
        holder.planned_morn.setText(dcrFollowupModel.getPlanMor());
        holder.planned_eve.setText(dcrFollowupModel.getPlanEve());
        holder.visited_morn.setText(dcrFollowupModel.getVisitedMor());
        holder.visited_eve.setText(dcrFollowupModel.getVisitedEve());
        holder.not_visited.setText(dcrFollowupModel.getNotVisited());
        holder.visit_percentage.setText(dcrFollowupModel.getVisitPercent());

        if (Objects.equals(userRole, "D")) {
            holder.planned_todDoc.setText(dcrFollowupModel.getPlanTotDoc());
            holder.visited_todDoc.setText(dcrFollowupModel.getVisitedTotDoc());
        } else if (Objects.equals(userRole, "T")) {
            holder.planned_todDoc.setText(dcrFollowupModel.getTotTerritory());
            holder.visited_todDoc.setText(dcrFollowupModel.getTotVisited());
        }
        holder.itemView.setOnClickListener(view -> {
            itemClickListener.onClick(position, dcrFollowupModel);
        });
    }

    @Override
    public int getItemCount() {
        return dcrFollowupList.size();
    }

    public class DcrFollowupViewHolder extends RecyclerView.ViewHolder {
        public TextView ff_code, ff_area, planned_todDoc, planned_morn, planned_eve, visited_todDoc, visited_morn, visited_eve, not_visited, visit_percentage;

        public DcrFollowupViewHolder(View view) {
            super(view);
            ff_code = view.findViewById(R.id.ff_code);
            ff_area = view.findViewById(R.id.ff_area);
            planned_todDoc = view.findViewById(R.id.planned_todDoc);
            planned_morn = view.findViewById(R.id.planned_morn);
            planned_eve = view.findViewById(R.id.planned_eve);
            visited_todDoc = view.findViewById(R.id.visited_todDoc);
            visited_morn = view.findViewById(R.id.visited_morn);
            visited_eve = view.findViewById(R.id.visited_eve);
            not_visited = view.findViewById(R.id.not_visited);
            visit_percentage = view.findViewById(R.id.visit_percentage);
        }
    }

    public interface ItemClickListener {
        void onClick(int position, DcrFollowupModel model);
    }
}
