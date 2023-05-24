package com.opl.pharmavector.msd_doc_support.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.opl.pharmavector.R;
import java.util.List;

public class MSDCommitmentAdapter extends RecyclerView.Adapter<MSDCommitmentAdapter.MSDCommitmentViewHolder> {
    private Context context;
    public List<MSDCommitmentModel> msdCommitmentList;

    public MSDCommitmentAdapter(Context context, List<MSDCommitmentModel> msdCommitmentList) {
        this.context = context;
        this.msdCommitmentList = msdCommitmentList;
    }

    @Override
    public MSDCommitmentViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.msd_commitment_row, viewGroup, false);
        return new MSDCommitmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MSDCommitmentViewHolder holder, int position) {
        MSDCommitmentModel msdCommitmentModel = msdCommitmentList.get(position);
        holder.req_number.setText(msdCommitmentModel.getMsdSlno());
        holder.program_date.setText(msdCommitmentModel.getProgramDate());
        holder.program_time.setText(msdCommitmentModel.getProgramTime());
        holder.institute_name.setText(msdCommitmentModel.getInstDesc());
        holder.venue_name.setText(msdCommitmentModel.getVenue());
        holder.region_name.setText(msdCommitmentModel.getRegion());
        holder.product_coverage.setText(msdCommitmentModel.getBrandName());
        holder.commitment_duration.setText(msdCommitmentModel.getCommitmentDuration());
    }

    @Override
    public int getItemCount() {
        return msdCommitmentList.size();
    }

    public class MSDCommitmentViewHolder extends RecyclerView.ViewHolder {
        public TextView req_number, program_date, program_time, institute_name, venue_name, region_name, product_coverage, commitment_duration;

        public MSDCommitmentViewHolder(View view) {
            super(view);
            req_number = view.findViewById(R.id.req_number);
            program_date = view.findViewById(R.id.program_date);
            program_time = view.findViewById(R.id.program_time);
            institute_name = view.findViewById(R.id.institute_name);
            venue_name = view.findViewById(R.id.venue_name);
            region_name = view.findViewById(R.id.region_name);
            product_coverage = view.findViewById(R.id.product_coverage);
            commitment_duration = view.findViewById(R.id.commitment_duration);
        }
    }
}
