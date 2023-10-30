package com.opl.pharmavector.dcfpFollowup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.opl.pharmavector.R;

import java.util.List;

public class DcfpDoctorListAdapter1 extends RecyclerView.Adapter<DcfpDoctorListAdapter1.DcfpDoctorViewHolder> {
    private Context context;
    public DcfpClickListener1 dcfpClickListener;
    public List<DcfpDoctorReportList> dcfpDoctorLists;

    public DcfpDoctorListAdapter1(Context context, List<DcfpDoctorReportList> dcfpDoctorList) {
        this.context = context;
        this.dcfpDoctorLists = dcfpDoctorList;
    }

    public DcfpDoctorListAdapter1(Context context, List<DcfpDoctorReportList> dcfpDoctorList, DcfpClickListener1 dcfpClickListener) {
        this.context = context;
        this.dcfpDoctorLists = dcfpDoctorList;
        this.dcfpClickListener = dcfpClickListener;
    }

    @Override
    public DcfpDoctorViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dcfp_doctor_list_row1, viewGroup, false);
        return new DcfpDoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DcfpDoctorViewHolder holder, int position) {
        DcfpDoctorReportList dcfpDoctorModel = dcfpDoctorLists.get(position);
        holder.doc_code.setText(dcfpDoctorModel.getDocCode());
        holder.doc_name.setText(dcfpDoctorModel.getDocName());
        holder.doc_degree.setText(dcfpDoctorModel.getQual());
        holder.doc_spec.setText(dcfpDoctorModel.getSpDesc());
    }

    @Override
    public int getItemCount() {
        return dcfpDoctorLists.size();
    }

    public class DcfpDoctorViewHolder extends RecyclerView.ViewHolder {
        public TextView doc_code, doc_name, doc_degree, doc_spec, planned_eve, visited_todDoc, visited_morn, visited_eve, not_visited, visit_percentage;

        public DcfpDoctorViewHolder(View view) {
            super(view);
            doc_code = view.findViewById(R.id.doc_code);
            doc_name = view.findViewById(R.id.doc_name);
            doc_degree = view.findViewById(R.id.doc_degree);
            doc_spec = view.findViewById(R.id.doc_spec);
        }
    }

    public interface DcfpClickListener1 {
        void onDcfpClick1(int position, DcfpDoctorReportList model);
    }
}
