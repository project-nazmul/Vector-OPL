package com.opl.pharmavector.doctorgift;

        import android.content.Context;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.CheckBox;
        import android.widget.CompoundButton;
        import android.widget.EditText;
        import android.widget.Filter;
        import android.widget.Filterable;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.opl.pharmavector.ChemistGiftListAdapter;
        import com.opl.pharmavector.R;

        import java.util.ArrayList;


public class CustomAdapterApproval extends BaseAdapter {
    Context context;
    ArrayList<DataHolder> records;
    private LayoutInflater inflater;
    public static ArrayList<String> selectedApprovalList = new ArrayList<>();
    CustomAdapterApproval(Context context, ArrayList<DataHolder> records){
        this.context = context;
        this.records = records;
    }
    @Override
    public int getCount() {
        return records.size();
    }
    @Override
    public Object getItem(int i) {
        return records.get(i).getvalue1();
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        selectedApprovalList.clear();
        final ViewHolder holder;
        if (view==null) {
            holder = new ViewHolder();
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_item_gift_approval, null);
            holder.txt_value1 = (TextView) view.findViewById(R.id.sl_0);
            holder.txt_value2 = (TextView) view.findViewById(R.id.con_month);
            holder.txt_value3 = (TextView) view.findViewById(R.id.territory_code);
            holder.txt_value4 = (TextView) view.findViewById(R.id.gift_name);
            holder.txt_value5 = (TextView) view.findViewById(R.id.doctor_name);
            holder.txt_value6 = (TextView) view.findViewById(R.id.doctor_code);
            holder.chk1        = (CheckBox) view.findViewById(R.id.chk_checkbox);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        holder.txt_value1.setText(records.get(i).getvalue1());
        holder.txt_value2.setText(records.get(i).getvalue2());
        holder.txt_value3.setText(records.get(i).getvalue3());
        holder.txt_value4.setText(records.get(i).getvalue4());
        holder.txt_value5.setText(records.get(i).getvalue5());
        holder.txt_value6.setText(records.get(i).getvalue6());
        holder.chk1.setChecked(records.get(i).getSelected());
        holder.chk1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer pos = (Integer) holder.chk1.getTag();
                 if (records.get(i).getSelected()) {
                    records.get(i).setSelected(false);
                    selectedApprovalList.remove(records.get(i).getvalue7());
                    Log.e("selectedApprovalList-->", String.valueOf(selectedApprovalList));
                } else {
                    records.get(i).setSelected(true);
                    selectedApprovalList.add(records.get(i).getvalue7());
                    Log.e("selectedApprovalList", String.valueOf(selectedApprovalList));
                }
            }
        });

        return view;
    }

    class ViewHolder{
        TextView txt_value1,txt_value2,txt_value3,txt_value4,txt_value5,txt_value6,txt_value7,txt_value8,txt_value9,txt_value10,txt_value11,txt_value12;
        CheckBox chk1;
    }
}
