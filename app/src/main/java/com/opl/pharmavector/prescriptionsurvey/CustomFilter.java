package com.opl.pharmavector.prescriptionsurvey;

import android.util.Log;
import android.widget.Filter;
import com.opl.pharmavector.model.Patient;
import com.opl.pharmavector.prescriptionsurvey.Adapter;
import java.util.ArrayList;

public class CustomFilter extends Filter {
    Adapter adapter;
    ArrayList<Patient> filterList;

    public CustomFilter(ArrayList<Patient> filterList,Adapter adapter) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    //FILTERING OCCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();

        if (constraint != null && constraint.length() > 0) {
            constraint = constraint.toString().toUpperCase();
            ArrayList<Patient> filteredPets = new ArrayList<>();
            Log.e("filterList.size()", String.valueOf(filterList.size()));

            for (int i=0; i<filterList.size(); i++) {
                if(filterList.get(i).getFirst_name().toUpperCase().contains(constraint)) {
                    filteredPets.add(filterList.get(i));
                }
            }
            results.count=filteredPets.size();
            results.values=filteredPets;
        } else {
            results.count=filterList.size();
            results.values=filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.pets = (ArrayList<Patient>) results.values;
        adapter.notifyDataSetChanged();
    }
}