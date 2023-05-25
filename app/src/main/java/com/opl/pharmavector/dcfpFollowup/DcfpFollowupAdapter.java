package com.opl.pharmavector.dcfpFollowup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.opl.pharmavector.R;
import java.util.List;

public class DcfpFollowupAdapter  extends RecyclerView.Adapter<DcfpFollowupAdapter.DcfpFollowupViewHolder> {
    public List<DcrFollowupModel> dcrFollowupList;
    private Context context;
    public DcfpClickListener dcfpClickListener;

    public DcfpFollowupAdapter(Context context, List<DcrFollowupModel> dcrFollowupList, DcfpClickListener dcfpClickListener) {
        this.context = context;
        this.dcrFollowupList = dcrFollowupList;
        this.dcfpClickListener = dcfpClickListener;
    }

    @Override
    public DcfpFollowupViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dcr_followup_row, viewGroup, false);
        return new DcfpFollowupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DcfpFollowupViewHolder holder, int position) {
        DcrFollowupModel dcrFollowupModel = dcrFollowupList.get(position);
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

        holder.itemView.setOnClickListener(view -> {
            dcfpClickListener.onDcfpClick(position, dcrFollowupModel);
        });
    }

    @Override
    public int getItemCount() {
        return dcrFollowupList.size();
    }

    public class DcfpFollowupViewHolder extends RecyclerView.ViewHolder {
        public TextView ff_code, ff_area, planned_todDoc, planned_morn, planned_eve, visited_todDoc, visited_morn, visited_eve, not_visited, visit_percentage;

        public DcfpFollowupViewHolder(View view) {
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

    public interface DcfpClickListener {
        void onDcfpClick(int position, DcrFollowupModel model);
    }
}
