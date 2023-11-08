package com.opl.pharmavector.dcfpFollowup;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.opl.pharmavector.R;

import java.util.List;
import java.util.Objects;

public class DcfpDoctorListAdapter1 extends RecyclerView.Adapter<DcfpDoctorListAdapter1.DcfpDoctorViewHolder> {
    int d1_count = 0;
    private Context context;
    public DcfpClickListener1 dcfpClickListener;
    public List<DcfpDocTotDateList> dcfpTotDayLists;
    public List<DcfpDoctorReportList> dcfpDoctorLists;

    public DcfpDoctorListAdapter1(Context context, List<DcfpDoctorReportList> dcfpDoctorList) {
        this.context = context;
        this.dcfpDoctorLists = dcfpDoctorList;
    }

    public DcfpDoctorListAdapter1(Context context, List<DcfpDoctorReportList> dcfpDoctorList, List<DcfpDocTotDateList> dcfpTotDayList, DcfpClickListener1 dcfpClickListener) {
        this.context = context;
        this.dcfpDoctorLists = dcfpDoctorList;
        this.dcfpTotDayLists = dcfpTotDayList;
        this.dcfpClickListener = dcfpClickListener;
    }

    @Override
    public DcfpDoctorViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dcfp_doctor_list_row1, viewGroup, false);
        return new DcfpDoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DcfpDoctorViewHolder holder, int position) {
        DcfpDoctorReportList dcfpDoctorModel = dcfpDoctorLists.get(position);
        holder.total.setText(dcfpDoctorModel.getTotal());
        holder.doc_code.setText(dcfpDoctorModel.getDocCode());
        holder.doc_name.setText(dcfpDoctorModel.getDocName());
        holder.doc_degree.setText(dcfpDoctorModel.getQual());
        holder.doc_spec.setText(dcfpDoctorModel.getSpDesc());
        holder.doc_name1.setText(dcfpDoctorModel.getDocName());

        if (dcfpDoctorModel.getD1() != null) {
            holder.doc_d1.setText(dcfpDoctorModel.getD1());
            holder.doc_d1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_check, 0, 0, 0);
        } else {
            holder.doc_d1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_dot, 0, 0, 0);
            holder.doc_d1.setPadding(14, 0, 0, 0);
        }
        if (dcfpDoctorModel.getD2() != null) {
            holder.doc_d2.setText(dcfpDoctorModel.getD2());
            holder.doc_d2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_check, 0, 0, 0);
        } else {
            holder.doc_d2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_dot, 0, 0, 0);
            holder.doc_d2.setPadding(14, 0, 0, 0);
        }
        if (dcfpDoctorModel.getD3() != null) {
            holder.doc_d3.setText(dcfpDoctorModel.getD3());
            holder.doc_d3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_check, 0, 0, 0);
        } else {
            holder.doc_d3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_dot, 0, 0, 0);
            holder.doc_d3.setPadding(14, 0, 0, 0);
        }
        if (dcfpDoctorModel.getD4() != null) {
            holder.doc_d4.setText(dcfpDoctorModel.getD4());
            holder.doc_d4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_check, 0, 0, 0);
        } else {
            holder.doc_d4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_dot, 0, 0, 0);
            holder.doc_d4.setPadding(14, 0, 0, 0);
        }
        if (dcfpDoctorModel.getD5() != null) {
            holder.doc_d5.setText(dcfpDoctorModel.getD5());
            holder.doc_d5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_check, 0, 0, 0);
        } else {
            holder.doc_d5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_dot, 0, 0, 0);
            holder.doc_d5.setPadding(14, 0, 0, 0);
        }
        if (dcfpDoctorModel.getD6() != null) {
            holder.doc_d6.setText(dcfpDoctorModel.getD6());
            holder.doc_d6.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_check, 0, 0, 0);
        } else {
            holder.doc_d6.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_dot, 0, 0, 0);
            holder.doc_d6.setPadding(14, 0, 0, 0);
        }
        if (dcfpDoctorModel.getD7() != null) {
            holder.doc_d7.setText(dcfpDoctorModel.getD7());
            holder.doc_d7.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_check, 0, 0, 0);
        } else {
            holder.doc_d7.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_dot, 0, 0, 0);
            holder.doc_d7.setPadding(14, 0, 0, 0);
        }
        if (dcfpDoctorModel.getD8() != null) {
            holder.doc_d8.setText(dcfpDoctorModel.getD8());
            holder.doc_d8.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_check, 0, 0, 0);
        } else {
            holder.doc_d8.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_dot, 0, 0, 0);
            holder.doc_d8.setPadding(14, 0, 0, 0);
        }
        if (dcfpDoctorModel.getD9() != null) {
            holder.doc_d9.setText(dcfpDoctorModel.getD9());
            holder.doc_d9.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_check, 0, 0, 0);
        } else {
            holder.doc_d9.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_dot, 0, 0, 0);
            holder.doc_d9.setPadding(14, 0, 0, 0);
        }
        if (dcfpDoctorModel.getD10() != null) {
            holder.doc_d10.setText(dcfpDoctorModel.getD10());
            holder.doc_d10.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_check, 0, 0, 0);
        } else {
            holder.doc_d10.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_dot, 0, 0, 0);
            holder.doc_d10.setPadding(14, 0, 0, 0);
        }
        if (dcfpDoctorModel.getD11() != null) {
            holder.doc_d11.setText(dcfpDoctorModel.getD11());
            holder.doc_d11.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_check, 0, 0, 0);
        } else {
            holder.doc_d11.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_dot, 0, 0, 0);
            holder.doc_d11.setPadding(14, 0, 0, 0);
        }
        if (dcfpDoctorModel.getD12() != null) {
            holder.doc_d12.setText(dcfpDoctorModel.getD12());
            holder.doc_d12.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_check, 0, 0, 0);
        } else {
            holder.doc_d12.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_dot, 0, 0, 0);
            holder.doc_d12.setPadding(14, 0, 0, 0);
        }
        if (dcfpDoctorModel.getD13() != null) {
            holder.doc_d13.setText(dcfpDoctorModel.getD13());
            holder.doc_d13.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_check, 0, 0, 0);
        } else {
            holder.doc_d13.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_dot, 0, 0, 0);
            holder.doc_d13.setPadding(14, 0, 0, 0);
        }
        if (dcfpDoctorModel.getD14() != null) {
            holder.doc_d14.setText(dcfpDoctorModel.getD14());
            holder.doc_d14.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_check, 0, 0, 0);
        } else {
            holder.doc_d14.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_dot, 0, 0, 0);
            holder.doc_d14.setPadding(14, 0, 0, 0);
        }
        if (dcfpDoctorModel.getD15() != null) {
            holder.doc_d15.setText(dcfpDoctorModel.getD15());
            holder.doc_d15.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_check, 0, 0, 0);
        } else {
            holder.doc_d15.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_dot, 0, 0, 0);
            holder.doc_d15.setPadding(14, 0, 0, 0);
        }
        if (dcfpDoctorModel.getD16() != null) {
            holder.doc_d16.setText(dcfpDoctorModel.getD16());
            holder.doc_d16.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_check, 0, 0, 0);
        } else {
            holder.doc_d16.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_dot, 0, 0, 0);
            holder.doc_d16.setPadding(14, 0, 0, 0);
        }
        if (dcfpDoctorModel.getD17() != null) {
            holder.doc_d17.setText(dcfpDoctorModel.getD17());
            holder.doc_d17.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_check, 0, 0, 0);
        } else {
            holder.doc_d17.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_dot, 0, 0, 0);
            holder.doc_d17.setPadding(14, 0, 0, 0);
        }
        if (dcfpDoctorModel.getD18() != null) {
            holder.doc_d18.setText(dcfpDoctorModel.getD18());
            holder.doc_d18.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_check, 0, 0, 0);
        } else {
            holder.doc_d18.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_dot, 0, 0, 0);
            holder.doc_d18.setPadding(14, 0, 0, 0);
        }
        if (dcfpDoctorModel.getD19() != null) {
            holder.doc_d19.setText(dcfpDoctorModel.getD19());
            holder.doc_d19.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_check, 0, 0, 0);
        } else {
            holder.doc_d19.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_dot, 0, 0, 0);
            holder.doc_d19.setPadding(14, 0, 0, 0);
        }
        if (dcfpDoctorModel.getD20() != null) {
            holder.doc_d20.setText(dcfpDoctorModel.getD20());
            holder.doc_d20.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_check, 0, 0, 0);
        } else {
            holder.doc_d20.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_dot, 0, 0, 0);
            holder.doc_d20.setPadding(14, 0, 0, 0);
        }
        if (dcfpDoctorModel.getD21() != null) {
            holder.doc_d21.setText(dcfpDoctorModel.getD21());
            holder.doc_d21.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_check, 0, 0, 0);
        } else {
            holder.doc_d21.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_dot, 0, 0, 0);
            holder.doc_d21.setPadding(14, 0, 0, 0);
        }
        if (dcfpDoctorModel.getD22() != null) {
            holder.doc_d22.setText(dcfpDoctorModel.getD22());
            holder.doc_d22.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_check, 0, 0, 0);
        } else {
            holder.doc_d22.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_dot, 0, 0, 0);
            holder.doc_d22.setPadding(14, 0, 0, 0);
        }
        if (dcfpDoctorModel.getD23() != null) {
            holder.doc_d23.setText(dcfpDoctorModel.getD23());
            holder.doc_d23.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_check, 0, 0, 0);
        } else {
            holder.doc_d23.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_dot, 0, 0, 0);
            holder.doc_d23.setPadding(14, 0, 0, 0);
        }
        if (dcfpDoctorModel.getD24() != null) {
            holder.doc_d24.setText(dcfpDoctorModel.getD24());
            holder.doc_d24.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_check, 0, 0, 0);
        } else {
            holder.doc_d24.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_dot, 0, 0, 0);
            holder.doc_d24.setPadding(14, 0, 0, 0);
        }
        if (dcfpDoctorModel.getD25() != null) {
            holder.doc_d25.setText(dcfpDoctorModel.getD25());
            holder.doc_d25.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_check, 0, 0, 0);
        } else {
            holder.doc_d25.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_dot, 0, 0, 0);
            holder.doc_d25.setPadding(14, 0, 0, 0);
        }
        if (dcfpDoctorModel.getD26() != null) {
            holder.doc_d26.setText(dcfpDoctorModel.getD26());
            holder.doc_d26.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_check, 0, 0, 0);
        } else {
            holder.doc_d26.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_dot, 0, 0, 0);
            holder.doc_d26.setPadding(14, 0, 0, 0);
        }
        if (dcfpDoctorModel.getD27() != null) {
            holder.doc_d27.setText(dcfpDoctorModel.getD27());
            holder.doc_d27.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_check, 0, 0, 0);
        } else {
            holder.doc_d27.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_dot, 0, 0, 0);
            holder.doc_d27.setPadding(14, 0, 0, 0);
        }
        if (dcfpDoctorModel.getD28() != null) {
            holder.doc_d28.setText(dcfpDoctorModel.getD28());
            holder.doc_d28.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_check, 0, 0, 0);
        } else {
            holder.doc_d28.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_dot, 0, 0, 0);
            holder.doc_d28.setPadding(14, 0, 0, 0);
        }

        refreshTextViewBg(holder);
        if (dcfpTotDayLists.size() > 0) {
            if (Objects.equals(dcfpTotDayLists.get(0).getTotDay(), "1")) {
                holder.doc_d1.setBackgroundResource(R.color.yello);
            } else if (Objects.equals(dcfpTotDayLists.get(0).getTotDay(), "2")) {
                holder.doc_d2.setBackgroundResource(R.color.yello);
            } else if (Objects.equals(dcfpTotDayLists.get(0).getTotDay(), "3")) {
                holder.doc_d3.setBackgroundResource(R.color.yello);
            } else if (Objects.equals(dcfpTotDayLists.get(0).getTotDay(), "4")) {
                holder.doc_d4.setBackgroundResource(R.color.yello);
            } else if (Objects.equals(dcfpTotDayLists.get(0).getTotDay(), "5")) {
                holder.doc_d5.setBackgroundResource(R.color.yello);
            } else if (Objects.equals(dcfpTotDayLists.get(0).getTotDay(), "6")) {
                holder.doc_d6.setBackgroundResource(R.color.yello);
            } else if (Objects.equals(dcfpTotDayLists.get(0).getTotDay(), "7")) {
                holder.doc_d7.setBackgroundResource(R.color.yello);
            } else if (Objects.equals(dcfpTotDayLists.get(0).getTotDay(), "8")) {
                holder.doc_d8.setBackgroundResource(R.color.yello);
            } else if (Objects.equals(dcfpTotDayLists.get(0).getTotDay(), "9")) {
                holder.doc_d9.setBackgroundResource(R.color.yello);
            } else if (Objects.equals(dcfpTotDayLists.get(0).getTotDay(), "10")) {
                holder.doc_d10.setBackgroundResource(R.color.yello);
            } else if (Objects.equals(dcfpTotDayLists.get(0).getTotDay(), "11")) {
                holder.doc_d11.setBackgroundResource(R.color.yello);
            } else if (Objects.equals(dcfpTotDayLists.get(0).getTotDay(), "12")) {
                holder.doc_d12.setBackgroundResource(R.color.yello);
            } else if (Objects.equals(dcfpTotDayLists.get(0).getTotDay(), "13")) {
                holder.doc_d13.setBackgroundResource(R.color.yello);
            } else if (Objects.equals(dcfpTotDayLists.get(0).getTotDay(), "14")) {
                holder.doc_d14.setBackgroundResource(R.color.yello);
            } else if (Objects.equals(dcfpTotDayLists.get(0).getTotDay(), "15")) {
                holder.doc_d15.setBackgroundResource(R.color.yello);
            } else if (Objects.equals(dcfpTotDayLists.get(0).getTotDay(), "16")) {
                holder.doc_d16.setBackgroundResource(R.color.yello);
            } else if (Objects.equals(dcfpTotDayLists.get(0).getTotDay(), "17")) {
                holder.doc_d17.setBackgroundResource(R.color.yello);
            } else if (Objects.equals(dcfpTotDayLists.get(0).getTotDay(), "18")) {
                holder.doc_d18.setBackgroundResource(R.color.yello);
            } else if (Objects.equals(dcfpTotDayLists.get(0).getTotDay(), "19")) {
                holder.doc_d19.setBackgroundResource(R.color.yello);
            } else if (Objects.equals(dcfpTotDayLists.get(0).getTotDay(), "20")) {
                holder.doc_d20.setBackgroundResource(R.color.yello);
            } else if (Objects.equals(dcfpTotDayLists.get(0).getTotDay(), "21")) {
                holder.doc_d21.setBackgroundResource(R.color.yello);
            } else if (Objects.equals(dcfpTotDayLists.get(0).getTotDay(), "22")) {
                holder.doc_d22.setBackgroundResource(R.color.yello);
            } else if (Objects.equals(dcfpTotDayLists.get(0).getTotDay(), "23")) {
                holder.doc_d23.setBackgroundResource(R.color.yello);
            } else if (Objects.equals(dcfpTotDayLists.get(0).getTotDay(), "24")) {
                holder.doc_d24.setBackgroundResource(R.color.yello);
            } else if (Objects.equals(dcfpTotDayLists.get(0).getTotDay(), "25")) {
                holder.doc_d25.setBackgroundResource(R.color.yello);
            } else if (Objects.equals(dcfpTotDayLists.get(0).getTotDay(), "26")) {
                holder.doc_d26.setBackgroundResource(R.color.yello);
            } else if (Objects.equals(dcfpTotDayLists.get(0).getTotDay(), "27")) {
                holder.doc_d27.setBackgroundResource(R.color.yello);
            } else if (Objects.equals(dcfpTotDayLists.get(0).getTotDay(), "28")) {
                holder.doc_d28.setBackgroundResource(R.color.yello);
            }
        }
    }

    private void refreshTextViewBg(DcfpDoctorViewHolder holder) {
        holder.doc_d1.setBackgroundColor(Color.parseColor("#FFDDDDDD"));
        holder.doc_d2.setBackgroundColor(Color.parseColor("#FFDDDDDD"));
        holder.doc_d3.setBackgroundColor(Color.parseColor("#FFDDDDDD"));
        holder.doc_d4.setBackgroundColor(Color.parseColor("#FFDDDDDD"));
        holder.doc_d5.setBackgroundColor(Color.parseColor("#FFDDDDDD"));
        holder.doc_d6.setBackgroundColor(Color.parseColor("#FFDDDDDD"));
        holder.doc_d7.setBackgroundColor(Color.parseColor("#FFDDDDDD"));
        holder.doc_d8.setBackgroundColor(Color.parseColor("#FFDDDDDD"));
        holder.doc_d9.setBackgroundColor(Color.parseColor("#FFDDDDDD"));
        holder.doc_d10.setBackgroundColor(Color.parseColor("#FFDDDDDD"));
        holder.doc_d11.setBackgroundColor(Color.parseColor("#FFDDDDDD"));
        holder.doc_d12.setBackgroundColor(Color.parseColor("#FFDDDDDD"));
        holder.doc_d13.setBackgroundColor(Color.parseColor("#FFDDDDDD"));
        holder.doc_d14.setBackgroundColor(Color.parseColor("#FFDDDDDD"));
        holder.doc_d15.setBackgroundColor(Color.parseColor("#FFDDDDDD"));
        holder.doc_d16.setBackgroundColor(Color.parseColor("#FFDDDDDD"));
        holder.doc_d17.setBackgroundColor(Color.parseColor("#FFDDDDDD"));
        holder.doc_d18.setBackgroundColor(Color.parseColor("#FFDDDDDD"));
        holder.doc_d19.setBackgroundColor(Color.parseColor("#FFDDDDDD"));
        holder.doc_d20.setBackgroundColor(Color.parseColor("#FFDDDDDD"));
        holder.doc_d21.setBackgroundColor(Color.parseColor("#FFDDDDDD"));
        holder.doc_d22.setBackgroundColor(Color.parseColor("#FFDDDDDD"));
        holder.doc_d23.setBackgroundColor(Color.parseColor("#FFDDDDDD"));
        holder.doc_d24.setBackgroundColor(Color.parseColor("#FFDDDDDD"));
        holder.doc_d25.setBackgroundColor(Color.parseColor("#FFDDDDDD"));
        holder.doc_d26.setBackgroundColor(Color.parseColor("#FFDDDDDD"));
        holder.doc_d27.setBackgroundColor(Color.parseColor("#FFDDDDDD"));
        holder.doc_d28.setBackgroundColor(Color.parseColor("#FFDDDDDD"));
    }

    @Override
    public int getItemCount() {
        return dcfpDoctorLists.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class DcfpDoctorViewHolder extends RecyclerView.ViewHolder {
        public TextView doc_code, doc_name, doc_degree, doc_spec, doc_name1, doc_d1, doc_d2, doc_d3, doc_d4, doc_d5, doc_d6, doc_d7, doc_d8, doc_d9, doc_d10, doc_d11, doc_d12,
                doc_d13, doc_d14, doc_d15, doc_d16, doc_d17, doc_d18, doc_d19, doc_d20, doc_d21, doc_d22, doc_d23, doc_d24, doc_d25, doc_d26, doc_d27, doc_d28, total;
        LinearLayout layout_summary;

        public DcfpDoctorViewHolder(View view) {
            super(view);
            doc_code = view.findViewById(R.id.doc_code);
            doc_name = view.findViewById(R.id.doc_name);
            doc_degree = view.findViewById(R.id.doc_degree);
            doc_spec = view.findViewById(R.id.doc_spec);
            doc_name1 = view.findViewById(R.id.doc_name1);
            doc_d1 = view.findViewById(R.id.doc_d1);
            doc_d2 = view.findViewById(R.id.doc_d2);
            doc_d3 = view.findViewById(R.id.doc_d3);
            doc_d4 = view.findViewById(R.id.doc_d4);
            doc_d5 = view.findViewById(R.id.doc_d5);
            doc_d6 = view.findViewById(R.id.doc_d6);
            doc_d7 = view.findViewById(R.id.doc_d7);
            doc_d8 = view.findViewById(R.id.doc_d8);
            doc_d9 = view.findViewById(R.id.doc_d9);
            doc_d10 = view.findViewById(R.id.doc_d10);
            doc_d11 = view.findViewById(R.id.doc_d11);
            doc_d12 = view.findViewById(R.id.doc_d12);
            doc_d13 = view.findViewById(R.id.doc_d13);
            doc_d14 = view.findViewById(R.id.doc_d14);
            doc_d15 = view.findViewById(R.id.doc_d15);
            doc_d16 = view.findViewById(R.id.doc_d16);
            doc_d17 = view.findViewById(R.id.doc_d17);
            doc_d18 = view.findViewById(R.id.doc_d18);
            doc_d19 = view.findViewById(R.id.doc_d19);
            doc_d20 = view.findViewById(R.id.doc_d20);
            doc_d21 = view.findViewById(R.id.doc_d21);
            doc_d22 = view.findViewById(R.id.doc_d22);
            doc_d23 = view.findViewById(R.id.doc_d23);
            doc_d24 = view.findViewById(R.id.doc_d24);
            doc_d25 = view.findViewById(R.id.doc_d25);
            doc_d26 = view.findViewById(R.id.doc_d26);
            doc_d27 = view.findViewById(R.id.doc_d27);
            doc_d28 = view.findViewById(R.id.doc_d28);
            total = view.findViewById(R.id.total);
            layout_summary = view.findViewById(R.id.layout_summary);
        }
    }

    public interface DcfpClickListener1 {
        void onDcfpClick1(int position, DcfpDoctorReportList model, int d1_count);
    }
}
