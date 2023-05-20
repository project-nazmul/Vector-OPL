package com.opl.pharmavector.dcrFollowup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.opl.pharmavector.R;
import com.opl.pharmavector.doctorList.model.DoctorList;
import java.util.List;

public class DcrFollowupAdapter extends RecyclerView.Adapter<DcrFollowupAdapter.DcrFollowupViewHolder> {
    public List<DoctorList> doctorLists;
    private Context context;

    public DcrFollowupAdapter(Context context, List<DoctorList> doctorList) {
        this.context = context;
        this.doctorLists = doctorList;
    }

    @Override
    public DcrFollowupViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dcr_followup_row, viewGroup, false);
        return new DcrFollowupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DcrFollowupViewHolder holder, int position) {
        DoctorList doctorData = doctorLists.get(position);
        holder.ff_code.setText(doctorData.getDocCode());
        holder.ff_area.setText(doctorData.getDocName());
        holder.planned_todDoc.setText(doctorData.getMarketCode());
        holder.planned_morn.setText(doctorData.getMarketName());
        holder.planned_eve.setText(doctorData.getDegree());
        holder.visited_todDoc.setText(doctorData.getSpecialization());
        holder.visited_morn.setText(doctorData.getDesig());
        holder.visited_eve.setText(doctorData.getPatientPerDay());
        holder.not_visited.setText(doctorData.getAddress());
        holder.visit_percentage.setText(doctorData.getAddress());
    }

    @Override
    public int getItemCount() {
        return doctorLists.size();
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
}
