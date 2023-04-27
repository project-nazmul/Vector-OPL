package com.opl.pharmavector.mpodcr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.opl.pharmavector.R;

import java.util.List;

public class DcfpAdapter extends RecyclerView.Adapter<DcfpAdapter.DcfpViewHolder> {
    public List<DcfpList> dcfpLists;
    private Context context;

    public DcfpAdapter(Context context, List<DcfpList> dcfpList) {
        this.context = context;
        this.dcfpLists = dcfpList;
    }

    @Override
    public DcfpViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dcfp_list_row, viewGroup, false);
        return new DcfpViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DcfpViewHolder holder, int position) {
        DcfpList dcfpList = dcfpLists.get(position);

        holder.visitType.setText(dcfpList.getVisitType());
        holder.docCode.setText(dcfpList.getDocCode());
        holder.docName.setText(dcfpList.getDocName());
        holder.mktCode.setText(dcfpList.getMktCode());
        holder.mktDesc.setText(dcfpList.getMktDesc());
        holder.terriName.setText(dcfpList.getTerriName());
        holder.tsCode.setText(dcfpList.getTsCode());
    }

    @Override
    public int getItemCount() {
        return dcfpLists.size();
    }

    public class DcfpViewHolder extends RecyclerView.ViewHolder {
        public TextView visitType, docCode, docName, mktCode, mktDesc, terriName, tsCode;

        public DcfpViewHolder(View view) {
            super(view);
            visitType = view.findViewById(R.id.visitType);
            docCode = view.findViewById(R.id.docCode);
            docName = view.findViewById(R.id.docName);
            mktCode = view.findViewById(R.id.mktCode);
            mktDesc = view.findViewById(R.id.mktDesc);
            terriName = view.findViewById(R.id.terriName);
            tsCode = view.findViewById(R.id.tsCode);
        }
    }
}
