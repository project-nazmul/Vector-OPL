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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dcfp_entry_setup_row, viewGroup, false);
        return new DcfpSetUpViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DcfpSetUpViewHolder holder, int position) {
        DcfpEntrySetUpList dcfpEntryModel = entrySetUpLists.get(position);
        holder.tp_week.setText(dcfpEntryModel.getTpWeek());
        holder.tp_day.setText(dcfpEntryModel.getTpDay());
        //holder.t_type.setText(dcrFollowupModel.getPlanTotDoc());

//        holder.itemView.setOnClickListener(view -> {
//            itemClickListener.onClick(position, dcrFollowupModel);
//        });
    }

    @Override
    public int getItemCount() {
        return entrySetUpLists.size();
    }

    public class DcfpSetUpViewHolder extends RecyclerView.ViewHolder {
        public TextView tp_week, tp_day, t_type;

        public DcfpSetUpViewHolder(View view) {
            super(view);
            tp_week = view.findViewById(R.id.tp_week);
            tp_day = view.findViewById(R.id.tp_day);
            t_type = view.findViewById(R.id.t_type);
        }
    }

//    public interface ItemClickListener {
//        void onClick(int position, DcrFollowupModel model);
//    }
}
