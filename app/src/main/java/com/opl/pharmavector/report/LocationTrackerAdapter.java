package com.opl.pharmavector.report;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.opl.pharmavector.R;
import com.opl.pharmavector.prescriber.PrescriberAdapter;
import com.opl.pharmavector.prescriber.TopPrescriberList;

import java.util.List;

public class LocationTrackerAdapter extends RecyclerView.Adapter<LocationTrackerAdapter.LocationViewHolder> {
    public List<LocationReportList> locationReportLists;

    public LocationTrackerAdapter(Context context, List<LocationReportList> locationReportList) {
        this.locationReportLists = locationReportList;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.location_tracker_row, viewGroup, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LocationViewHolder holder, int position) {
        LocationReportList locationModel = locationReportLists.get(position);
        holder.locationSl.setText(String.valueOf(locationModel.getSlno()));
        holder.locationName.setText(locationModel.getUserLoc());
    }

    @Override
    public int getItemCount() {
        return locationReportLists.size();
    }

    public static class LocationViewHolder extends RecyclerView.ViewHolder {
        public TextView locationSl, locationName;

        public LocationViewHolder(View view) {
            super(view);
            locationSl = view.findViewById(R.id.locationSl);
            locationName = view.findViewById(R.id.locationName);
        }
    }
}
