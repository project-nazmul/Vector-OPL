package com.opl.pharmavector.pmdVector.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.opl.pharmavector.R;
import com.opl.pharmavector.pmdVector.model.RegionValList;
import java.util.List;

public class RegionValAdapter extends RecyclerView.Adapter<RegionValAdapter.RegionValViewHolder> {
    public List<RegionValList> regionValLists;
    private Context context;

    public RegionValAdapter(Context context, List<RegionValList> regionValList) {
        this.context = context;
        this.regionValLists = regionValList;
    }

    @Override
    public RegionValViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.region_value_row, viewGroup, false);
        return new RegionValViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RegionValViewHolder holder, int position) {
        RegionValList regionValData = regionValLists.get(position);

        holder.regionName.setText(regionValData.getRegion());
        holder.valueShare.setText(regionValData.getValShare());
        holder.valueShare1.setText(regionValData.getValShare1());
        holder.competitor1.setText(regionValData.getCom1());
        holder.valueShare2.setText(regionValData.getValShare2());
        holder.competitor2.setText(regionValData.getCom2());
        holder.valueShare3.setText(regionValData.getValShare3());
        holder.competitor3.setText(regionValData.getCom3());
    }

    @Override
    public int getItemCount() {
        return regionValLists.size();
    }

    public class RegionValViewHolder extends RecyclerView.ViewHolder {
        public TextView regionName, valueShare, valueShare1, competitor1, valueShare2, competitor2,  valueShare3, competitor3;

        public RegionValViewHolder(View view) {
            super(view);
            regionName = view.findViewById(R.id.regionName);
            valueShare = view.findViewById(R.id.valueShare);
            valueShare1 = view.findViewById(R.id.valueShare1);
            competitor1 = view.findViewById(R.id.competitor1);
            valueShare2 = view.findViewById(R.id.valueShare2);
            competitor2 = view.findViewById(R.id.competitor2);
            valueShare3 = view.findViewById(R.id.valueShare3);
            competitor3 = view.findViewById(R.id.competitor3);
        }
    }
}
