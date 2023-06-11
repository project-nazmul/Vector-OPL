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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;

public class DcfpEntrySetUpAdapter extends RecyclerView.Adapter<DcfpEntrySetUpAdapter.DcfpSetUpViewHolder> {
    public List<DcfpEntrySetUpList> entrySetUpLists;
    private Context context;
    private LayoutInflater inflater;
    public ItemClickListener itemClickListener;
    ArrayList<DcfpEntrySetUpList> selectedShiftList = new ArrayList<DcfpEntrySetUpList>();

    public DcfpEntrySetUpAdapter(Context context, List<DcfpEntrySetUpList> entrySetUpList, ItemClickListener itemClickListener) {
        this.inflater = LayoutInflater.from(context);
        this.entrySetUpLists = entrySetUpList;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public DcfpSetUpViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.dcfp_entry_setup_row, viewGroup, false);
        return new DcfpSetUpViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DcfpSetUpViewHolder holder, int position) {
        DcfpEntrySetUpList dcfpEntryModel = entrySetUpLists.get(position);
        holder.tp_week.setText(dcfpEntryModel.getTpWeek());
        holder.tp_day.setText(dcfpEntryModel.getTpDay());
        holder.setSelectedShift(dcfpEntryModel, position);

        holder.morningShift.setOnClickListener(v -> {
            if (selectedShiftList.size() > 0) {
                for (int i = 0; i < selectedShiftList.size(); i++) {
                    if (selectedShiftList.get(i).getTpWeek().equals(dcfpEntryModel.getTpWeek()) && selectedShiftList.get(i).getTpDay().equals(dcfpEntryModel.getTpDay())) {
                        //selectedShiftList.remove(selectedShiftList.get(i));
                        dcfpEntryModel.setShiftType("M");
                        selectedShiftList.set(i, dcfpEntryModel);
                        //selectedShiftList.add(dcfpEntryModel);
                    } else if (!selectedShiftList.contains(dcfpEntryModel)){
                        dcfpEntryModel.setShiftType("M");
                        selectedShiftList.add(dcfpEntryModel);
                    }
                }
            } else {
                dcfpEntryModel.setShiftType("M");
                selectedShiftList.add(dcfpEntryModel);
            }
            itemClickListener.onClick(position, selectedShiftList);
            Log.d("shiftList1", selectedShiftList.toString());
        });
        holder.eveningShift.setOnClickListener(v -> {
            if (selectedShiftList.size() > 0) {
                for (int i = 0; i < selectedShiftList.size(); i++) {
                    if (selectedShiftList.get(i).getTpWeek().equals(dcfpEntryModel.getTpWeek()) && selectedShiftList.get(i).getTpDay().equals(dcfpEntryModel.getTpDay())) {
                        //selectedShiftList.remove(selectedShiftList.get(i));
                        dcfpEntryModel.setShiftType("E");
                        selectedShiftList.set(i, dcfpEntryModel);
                        //selectedShiftList.add(dcfpEntryModel);
                    } else if (!selectedShiftList.contains(dcfpEntryModel)){
                        dcfpEntryModel.setShiftType("E");
                        selectedShiftList.add(dcfpEntryModel);
                    }
                }
            } else {
                dcfpEntryModel.setShiftType("E");
                selectedShiftList.add(dcfpEntryModel);
            }
            itemClickListener.onClick(position, selectedShiftList);
            Log.d("shiftList2", selectedShiftList.toString());
        });
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

    public interface ItemClickListener {
        void onClick(int position, ArrayList<DcfpEntrySetUpList> model);
    }
}
