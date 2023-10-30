package com.opl.pharmavector.dcfpFollowup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.opl.pharmavector.R;

import java.util.List;

public class DcfpDoctorListAdapter2 extends RecyclerView.Adapter<DcfpDoctorListAdapter2.DcfpDoctorViewHolder> {
    private Context context;
    public DcfpClickListener dcfpClickListener;
    public List<DcfpDoctorReportList> dcfpDoctorLists;

    public DcfpDoctorListAdapter2(Context context, List<DcfpDoctorReportList> dcfpDoctorList) {
        this.context = context;
        this.dcfpDoctorLists = dcfpDoctorList;
    }

    public DcfpDoctorListAdapter2(Context context, List<DcfpDoctorReportList> dcfpDoctorList, DcfpClickListener dcfpClickListener) {
        this.context = context;
        this.dcfpDoctorLists = dcfpDoctorList;
        this.dcfpClickListener = dcfpClickListener;
    }

    @Override
    public DcfpDoctorViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dcfp_doctor_list_row2, viewGroup, false);
        return new DcfpDoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DcfpDoctorViewHolder holder, int position) {
        DcfpDoctorReportList dcfpDoctorModel = dcfpDoctorLists.get(position);
        holder.doc_degree.setText(dcfpDoctorModel.getQual());
        holder.doc_spec.setText(dcfpDoctorModel.getSpDesc());

//        holder.planned_eve.setText(dcfpDoctorModel.getPlanEve());
//        holder.visited_todDoc.setText(dcfpDoctorModel.getVisitedTotDoc());
//        holder.visited_morn.setText(dcfpDoctorModel.getVisitedMor());
//        holder.visited_eve.setText(dcfpDoctorModel.getVisitedEve());
//        holder.not_visited.setText(dcfpDoctorModel.getNotVisited());
//        holder.visit_percentage.setText(dcfpDoctorModel.getVisitPercent());

        holder.itemView.setOnClickListener(view -> {
            dcfpClickListener.onDcfpClick(position, dcfpDoctorModel);
        });
    }

    @Override
    public int getItemCount() {
        return dcfpDoctorLists.size();
    }

    public class DcfpDoctorViewHolder extends RecyclerView.ViewHolder {
        public TextView doc_degree, doc_spec, planned_eve, visited_todDoc, visited_morn, visited_eve, not_visited, visit_percentage;

        public DcfpDoctorViewHolder(View view) {
            super(view);
            doc_degree = view.findViewById(R.id.doc_degree);
            doc_spec = view.findViewById(R.id.doc_spec);
            planned_eve = view.findViewById(R.id.planned_eve);
            visited_todDoc = view.findViewById(R.id.visited_todDoc);
            visited_morn = view.findViewById(R.id.visited_morn);
            visited_eve = view.findViewById(R.id.visited_eve);
            not_visited = view.findViewById(R.id.not_visited);
            visit_percentage = view.findViewById(R.id.visit_percentage);
        }
    }

    public interface DcfpClickListener {
        void onDcfpClick(int position, DcfpDoctorReportList model);
    }
}
