package com.opl.pharmavector;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.opl.pharmavector.master_code.model.MasterCList;
import com.opl.pharmavector.mpodcr.DcfpList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdminProductAdapter extends RecyclerView.Adapter<AdminProductAdapter.AdminProductHolder> {
    public List<Category> dcfpLists;
    public List<Category> filterDcfpLists;
    private Context context;
    public TextView edit_qnty;

    public AdminProductAdapter(Context context, List<Category> dcfpList) {
        this.context = context;
        this.dcfpLists = dcfpList;
        this.filterDcfpLists = dcfpList;
    }

    @Override
    public AdminProductHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.admin_product_list_adapter, viewGroup, false);
        return new AdminProductHolder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(AdminProductHolder holder, int position) {
        Category dcfpList = filterDcfpLists.get(position);
//      String v_type = dcfpList.getVisitType();
//      holder.visitType.setText(String.valueOf(v_type.charAt(0)));
        holder.serial.setText(String.valueOf(dcfpList.getsl()));
        holder.p_code1.setText(dcfpList.getId());
        holder.p_name.setText(dcfpList.getName());
        holder.pack_size.setText(dcfpList.getPROD_RATE());
        holder.pack_size.setText(dcfpList.getPROD_RATE());
        holder.brand_code.setText(dcfpList.getP_CODE());
        holder.tp.setText(dcfpList.getPROD_VAT());
        holder.TP_VT.setText(dcfpList.getPPM_CODE());
        holder.quantity.setText(dcfpList.getSHIFT_CODE());

        if (Objects.equals(dcfpList.getPROD_STAT(), "O")) {
            holder.productList.setTag(position);
            holder.productList.setCardBackgroundColor(Color.parseColor("#F8CACA"));
        }
//        if (Objects.equals(dcfpList.getVisitStat(), "Y")) {
//            //holder.layout.setBackgroundColor(Color.parseColor("#db5a6b"));
//            //holder.cardLayout.setBackgroundColor(Color.parseColor("#fedde3"));
//            holder.cardLayout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.dcfp_y_gradient));
//        } else {
//            //holder.layout.setBackgroundColor(Color.parseColor("#a4c639"));
//            holder.cardLayout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.dcfp_n_gradient));
//        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return filterDcfpLists.size();
    }

    public class AdminProductHolder extends RecyclerView.ViewHolder {
//        public TextView visitType, docCode, docName, mktDesc;
//        public LinearLayout layout;
//        public CardView cardLayout;
        TextView serial;
        TextView quantity;
        String strHolder;
        int pos;
        TextView p_name;
        TextView value7;
        TextView msgtext;
        TextView ppm_error;
        TextView p_code1;
        TextView pack_size;
        TextView brand_code;
        TextView tp;
        TextView TP_VT;
        CheckBox checkbox;
        CardView productList;

        public AdminProductHolder(View view) {
            super(view);
            serial = view.findViewById(R.id.serial);
            p_name = view.findViewById(R.id.product_name_admin);
            ppm_error = view.findViewById(R.id.ppm_error);
            quantity = view.findViewById(R.id.mrp_val);
            p_code1 = view.findViewById(R.id.p_code1);
            pack_size = view.findViewById(R.id.pack_size);
            brand_code = view.findViewById(R.id.brand_code);
            tp = view.findViewById(R.id.tp);
            TP_VT = view.findViewById(R.id.TP_VT);
            edit_qnty = view.findViewById(R.id.order_qnty);
            productList = view.findViewById(R.id.productList);
            checkbox = view.findViewById(R.id.checkbox);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void searchMasterCode(List<Category> mCodeList) {
        filterDcfpLists = mCodeList;
        notifyDataSetChanged();
    }
}
