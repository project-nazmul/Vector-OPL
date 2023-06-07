package com.opl.pharmavector.dcfpFollowup;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.radiobutton.MaterialRadioButton;
import com.opl.pharmavector.R;
import java.util.List;

public class DcfpEntrySetUpAdapter extends RecyclerView.Adapter<DcfpEntrySetUpAdapter.DcfpSetUpViewHolder> {
    public List<DcfpEntrySetUpList> entrySetUpLists;
    private Context context;
    private LayoutInflater inflater;
    //public DcrFollowupAdapter.ItemClickListener itemClickListener;
    //private int lastCheckItem = -1;

    public DcfpEntrySetUpAdapter(Context context, List<DcfpEntrySetUpList> entrySetUpList) {
        this.inflater = LayoutInflater.from(context);
        this.entrySetUpLists = entrySetUpList;
    }

    @Override
    public DcfpSetUpViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dcfp_entry_setup_row, viewGroup, false);
        View view = inflater.inflate(R.layout.dcfp_entry_setup_row, viewGroup, false);
        return new DcfpSetUpViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DcfpSetUpViewHolder holder, int position) {
        DcfpEntrySetUpList dcfpEntryModel = entrySetUpLists.get(position);
        holder.tp_week.setText(dcfpEntryModel.getTpWeek());
        holder.tp_day.setText(dcfpEntryModel.getTpDay());
        holder.setSelectedShift(dcfpEntryModel, position);
        //holder.t_type.setText(dcrFollowupModel.getPlanTotDoc());
//        holder.selectedShift.setSelected(position == lastCheckItem);
//        holder.selectedShift.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//               // int pos = (int) group.getTag();
////                Question que = questions.get(pos);
////                que.isAnswered = true;
////                que.checkedId = checkedId;
////                Log.e(TAG, pos + " :onCheckedChanged: " + que.toString());
//            }
//        });

//        holder.itemView.setOnClickListener(view -> {
//            itemClickListener.onClick(position, dcrFollowupModel);
//        });
    }

    @Override
    public int getItemCount() {
        if (entrySetUpLists == null) {
            return 0;
        } else {
            return entrySetUpLists.size();
        }
    }

    public class DcfpSetUpViewHolder extends RecyclerView.ViewHolder {
        public TextView tp_week, tp_day, t_type;
        public RadioGroup selectedShift;
        public RadioButton morningShift, eveningShift;

        public DcfpSetUpViewHolder(View view) {
            super(view);
            tp_week = view.findViewById(R.id.tp_week);
            tp_day = view.findViewById(R.id.tp_day);
            selectedShift = view.findViewById(R.id.radioGroupShift);
            morningShift = view.findViewById(R.id.radioBtnMorning);
            eveningShift = view.findViewById(R.id.radioBtnEvening);
        }

        public void setSelectedShift(DcfpEntrySetUpList dcfpEntryModel, int position) {
            selectedShift.setTag(position);
            if (dcfpEntryModel.isAnswered) {
                selectedShift.check(dcfpEntryModel.getCheckedId());
            } else {
                selectedShift.check(-1);
            }
            selectedShift.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int pos = (int) group.getTag();
                    DcfpEntrySetUpList que = entrySetUpLists.get(pos);
                    que.isAnswered = true;
                    que.checkedId = checkedId;
                    Log.d("shift", pos + " :onCheckedChanged: " + que.toString());
                }
            });
        }
    }

//    public interface ItemClickListener {
//        void onClick(int position, DcrFollowupModel model);
//    }
}
