package com.opl.pharmavector.pmdVector.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.opl.pharmavector.R;
import com.opl.pharmavector.pmdVector.model.RegionUnitList;
import com.opl.pharmavector.pmdVector.model.RegionValList;
import java.util.List;

public class RegionUnitAdapter extends RecyclerView.Adapter<RegionUnitAdapter.RegionUnitViewHolder> {
    public List<RegionUnitList> regionUnitLists;
    private Context context;

    public RegionUnitAdapter(Context context, List<RegionUnitList> regionUnitList) {
        this.context = context;
        this.regionUnitLists = regionUnitList;
    }

    @Override
    public RegionUnitViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.region_unit_row, viewGroup, false);
        return new RegionUnitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RegionUnitViewHolder holder, int position) {
        RegionUnitList regionUnitData = regionUnitLists.get(position);

        holder.regionName.setText(regionUnitData.getRegion());
        holder.unitShare.setText(regionUnitData.getUnitShare());
        holder.unitShare1.setText(regionUnitData.getUnitShare1());
        holder.competitor1.setText(regionUnitData.getCom1());
        holder.unitShare2.setText(regionUnitData.getUnitShare2());
        holder.competitor2.setText(regionUnitData.getCom2());
        holder.unitShare3.setText(regionUnitData.getUnitShare3());
        holder.competitor3.setText(regionUnitData.getCom3());
    }

    @Override
    public int getItemCount() {
        return regionUnitLists.size();
    }

    public class RegionUnitViewHolder extends RecyclerView.ViewHolder {
        public TextView regionName, unitShare, unitShare1, competitor1, unitShare2, competitor2, unitShare3, competitor3;

        public RegionUnitViewHolder(View view) {
            super(view);
            regionName = view.findViewById(R.id.regionName);
            unitShare = view.findViewById(R.id.unitShare);
            unitShare1 = view.findViewById(R.id.unitShare1);
            competitor1 = view.findViewById(R.id.competitor1);
            unitShare2 = view.findViewById(R.id.unitShare2);
            competitor2 = view.findViewById(R.id.competitor2);
            unitShare3 = view.findViewById(R.id.unitShare3);
            competitor3 = view.findViewById(R.id.competitor3);
        }
    }

}
