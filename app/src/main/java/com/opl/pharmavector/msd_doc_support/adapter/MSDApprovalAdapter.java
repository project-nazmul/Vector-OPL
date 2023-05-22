package com.opl.pharmavector.msd_doc_support.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.opl.pharmavector.R;
import java.util.List;

public class MSDApprovalAdapter extends RecyclerView.Adapter<MSDApprovalAdapter.MSDApprovalViewHolder> {
    private Context context;
    public ItemClickListener itemClickListener;
    public List<MSDApprovalModel> msdApprovalList;

    public MSDApprovalAdapter(Context context, List<MSDApprovalModel> msdApprovalModel, ItemClickListener itemClickListener) {
        this.context = context;
        this.msdApprovalList = msdApprovalModel;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public MSDApprovalViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.msd_approval_row, viewGroup, false);
        return new MSDApprovalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MSDApprovalViewHolder holder, int position) {
        MSDApprovalModel msdApprovalModel = msdApprovalList.get(position);
        holder.req_number.setText(msdApprovalModel.getMsdSlno());
        holder.program_date.setText(msdApprovalModel.getForMon());
        holder.institute_name.setText(msdApprovalModel.getInstDesc());
        holder.venue_name.setText(msdApprovalModel.getVenue());
        holder.payment_mode.setText(msdApprovalModel.getPurchType());
        holder.ppm_gift.setText(msdApprovalModel.getPurchTypePpm());
        holder.executive_code.setText(msdApprovalModel.getProposeMpo());
        holder.proposed_territory.setText(msdApprovalModel.getTerriName());
        holder.no_of_req.setText(msdApprovalModel.getNoCme());
        holder.total_cost.setText(msdApprovalModel.getTotCost());

        holder.itemView.setOnClickListener(view -> {
            itemClickListener.onClick(position, msdApprovalModel);
        });
    }

    @Override
    public int getItemCount() {
        return msdApprovalList.size();
    }

    public class MSDApprovalViewHolder extends RecyclerView.ViewHolder {
        public TextView req_number, program_date, institute_name, venue_name, payment_mode, ppm_gift, executive_code, proposed_territory, no_of_req, total_cost;

        public MSDApprovalViewHolder(View view) {
            super(view);
            req_number = view.findViewById(R.id.req_number);
            program_date = view.findViewById(R.id.program_date);
            institute_name = view.findViewById(R.id.institute_name);
            venue_name = view.findViewById(R.id.venue_name);
            payment_mode = view.findViewById(R.id.payment_mode);
            ppm_gift = view.findViewById(R.id.ppm_gift);
            executive_code = view.findViewById(R.id.executive_code);
            proposed_territory = view.findViewById(R.id.proposed_territory);
            no_of_req = view.findViewById(R.id.no_of_req);
            total_cost = view.findViewById(R.id.total_cost);
        }
    }

    public interface ItemClickListener {
        void onClick(int position, MSDApprovalModel model);
    }
}
