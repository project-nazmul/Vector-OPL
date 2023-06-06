package com.opl.pharmavector.dcfpFollowup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.opl.pharmavector.R;
import java.util.List;

public class DcfpEntrySetUpAdapter extends RecyclerView.Adapter<DcfpEntrySetUpAdapter.DcfpSetUpViewHolder> {
    public List<DcfpEntrySetUpList> entrySetUpLists;
    private Context context;
    public DcrFollowupAdapter.ItemClickListener itemClickListener;

    public DcfpEntrySetUpAdapter(Context context, List<DcfpEntrySetUpList> entrySetUpList) {
        this.context = context;
        this.entrySetUpLists = entrySetUpList;
    }

    @Override
    public DcfpSetUpViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dcr_followup_row, viewGroup, false);
        return new DcfpSetUpViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DcfpSetUpViewHolder holder, int position) {
        DcfpEntrySetUpList dcrFollowupModel = entrySetUpLists.get(position);
        holder.ff_code.setText(dcrFollowupModel.getFfCode());
        holder.ff_area.setText(dcrFollowupModel.getFfName());
        holder.planned_todDoc.setText(dcrFollowupModel.getPlanTotDoc());
        holder.planned_morn.setText(dcrFollowupModel.getPlanMor());
        holder.planned_eve.setText(dcrFollowupModel.getPlanEve());
        holder.visited_todDoc.setText(dcrFollowupModel.getVisitedTotDoc());
        holder.visited_morn.setText(dcrFollowupModel.getVisitedMor());
        holder.visited_eve.setText(dcrFollowupModel.getVisitedEve());
        holder.not_visited.setText(dcrFollowupModel.getNotVisited());
        holder.visit_percentage.setText(dcrFollowupModel.getVisitPercent());

//        holder.itemView.setOnClickListener(view -> {
//            itemClickListener.onClick(position, dcrFollowupModel);
//        });
    }

    @Override
    public int getItemCount() {
        return entrySetUpLists.size();
    }

    public class DcfpSetUpViewHolder extends RecyclerView.ViewHolder {
        public TextView ff_code, ff_area, planned_todDoc, planned_morn, planned_eve, visited_todDoc, visited_morn, visited_eve, not_visited, visit_percentage;

        public DcfpSetUpViewHolder(View view) {
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

//    public interface ItemClickListener {
//        void onClick(int position, DcrFollowupModel model);
//    }
}
