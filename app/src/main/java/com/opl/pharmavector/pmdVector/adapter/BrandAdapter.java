package com.opl.pharmavector.pmdVector.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.opl.pharmavector.R;
import com.opl.pharmavector.pmdVector.model.BrandList;
import java.util.List;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.BrandViewHolder> {
    public List<BrandList> brandLists;
    private Context context;

    public BrandAdapter(Context context, List<BrandList> brandList) {
        this.context = context;
        this.brandLists = brandList;
    }

    @Override
    public BrandViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.brand_data_row, viewGroup, false);
        return new BrandViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BrandViewHolder holder, int position) {
        BrandList brandData = brandLists.get(position);

        holder.brandName.setText(brandData.getBrandName());
        holder.valueShare.setText(brandData.getValShare());
        holder.valueRank.setText(brandData.getOplRank());
        holder.uniteShare.setText(brandData.getUniteShare());
        holder.uniteRank.setText(brandData.getNatRank());
    }

    @Override
    public int getItemCount() {
        return brandLists.size();
    }

    public class BrandViewHolder extends RecyclerView.ViewHolder {
        public TextView brandName, valueShare, valueRank, uniteShare, uniteRank, brandwise, regionwise, week2, week3, week4, total;

        public BrandViewHolder(View view) {
            super(view);
            brandName = view.findViewById(R.id.brandName);
            valueShare = view.findViewById(R.id.valueShare);
            valueRank = view.findViewById(R.id.valueRank);
            uniteShare = view.findViewById(R.id.uniteShare);
            uniteRank = view.findViewById(R.id.uniteRank);
            brandwise = view.findViewById(R.id.brandwise);
            regionwise = view.findViewById(R.id.regionwise);
        }
    }
}
