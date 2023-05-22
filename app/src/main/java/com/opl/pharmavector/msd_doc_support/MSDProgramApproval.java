package com.opl.pharmavector.msd_doc_support;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.opl.pharmavector.MonthYearPickerDialog;
import com.opl.pharmavector.R;
import com.opl.pharmavector.dcrFollowup.DcfpFollowupAdapter;
import com.opl.pharmavector.msd_doc_support.adapter.MSDApprovalAdapter;
import com.opl.pharmavector.msd_doc_support.adapter.MSDApprovalModel;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MSDProgramApproval extends AppCompatActivity implements MSDApprovalAdapter.ItemClickListener {
    Button back_btn;
    EditText ed_date;
    RecyclerView recyclerMSDApproval;
    MSDApprovalAdapter msdApprovalAdapter;
    public String userName, UserName_2, promo_type, user_flag, user_code, monthYearStr, year_val, month_val, proposed_date1, month_name_val, month_name, proposed_date2;
    ArrayList<MSDApprovalModel> msdApprovalList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msd_approval);

        initViews();
        calenderUI();
        msdProgramApprovalList();
        back_btn.setOnClickListener(v -> finish());
    }

    private void initViews() {
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        recyclerMSDApproval = findViewById(R.id.recyclerMSDApproval);
        ed_date = findViewById(R.id.ed_date);
        back_btn = findViewById(R.id.backbt);
        ed_date.setFocusableInTouchMode(true);
        ed_date.setFocusable(true);
        ed_date.requestFocus();
        ed_date.setClickable(true);
        ed_date.setInputType(InputType.TYPE_NULL);
        back_btn.setTypeface(fontFamily);
        back_btn.setText("\uf060");

        Bundle b = getIntent().getExtras();
        userName = b.getString("userName");
        UserName_2 = b.getString("UserName_2");
        promo_type = b.getString("promo_type");
        user_flag = b.getString("user_flag");
        user_code = b.getString("user_code");
    }

    private void calenderUI() {
        ed_date.setOnClickListener(v -> {
            MonthYearPickerDialog pickerDialog = new MonthYearPickerDialog();
            pickerDialog.setListener((datePicker, year, month, i2) -> {
                monthYearStr = year + "-" + (month + 1) + "-" + i2;
                String test = monthYearStr;
                year_val = test.substring(0, 4);
                month_val = String.valueOf(month);
                proposed_date1 = "01" + "/" + (month) + "/" + year;
                if (month_val.equals(String.valueOf(1))) {
                    month_name_val = "January";
                    month_name ="JAN";
                } else if (month_val.equals(String.valueOf(2))) {
                    month_name_val = "Feb";
                    month_name ="FEB";
                } else if (month_val.equals(String.valueOf(3))) {
                    month_name_val = "March";
                    month_name ="MAR";
                } else if (month_val.equals(String.valueOf(4))) {
                    month_name_val = "April";
                    month_name ="APR";
                } else if (month_val.equals(String.valueOf(5))) {
                    month_name_val = "May";
                    month_name ="MAY";
                } else if (month_val.equals(String.valueOf(6))) {
                    month_name_val = "June";
                    month_name ="JUN";
                } else if (month_val.equals(String.valueOf(7))) {
                    month_name_val = "July";
                    month_name ="JUL";
                } else if (month_val.equals(String.valueOf(8))) {
                    month_name_val = "August";
                    month_name ="AUG";
                } else if (month_val.equals(String.valueOf(9))) {
                    month_name_val = "September";
                    month_name ="SEP";
                } else if (month_val.equals(String.valueOf(10))) {
                    month_name_val = "October";
                    month_name ="OCT";
                } else if (month_val.equals(String.valueOf(11))) {
                    month_name_val = "November";
                    month_name ="NOV";
                } else if (month_val.equals(String.valueOf(12))) {
                    month_name_val = "December";
                    month_name ="DEC";
                }
                proposed_date2 = "01" + "-" + month_name + "-" + year;
                //Log.e("proposed_date1", proposed_date1);
                Log.e("proposed_date1", proposed_date2);
                ed_date.setText(proposed_date2);
                //prepareMPOPromo();
            });
            pickerDialog.show(getSupportFragmentManager(), "MonthYearPickerDialog");
        });
    }

    public void msdProgramApprovalList() {
        ProgressDialog msdApprovalDialog = new ProgressDialog(MSDProgramApproval.this);
        msdApprovalDialog.setMessage("MSD Approval List Loading...");
        msdApprovalDialog.setTitle("MSD Program Approval");
        msdApprovalDialog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<MSDApprovalModel>> call = apiInterface.getMSDApprovalList(userName);
        msdApprovalList.clear();

        call.enqueue(new Callback<List<MSDApprovalModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<MSDApprovalModel>> call, @NonNull retrofit2.Response<List<MSDApprovalModel>> response) {
                if (response.body() != null) {
                    msdApprovalList.addAll(response.body());
                    Log.d("tag", msdApprovalList.toString());
                }

                if (msdApprovalList.size() > 0) {
                    msdApprovalDialog.dismiss();
                    msdApprovalAdapter = new MSDApprovalAdapter(MSDProgramApproval.this, msdApprovalList, MSDProgramApproval.this);
                    LinearLayoutManager manager = new LinearLayoutManager(MSDProgramApproval.this);
                    recyclerMSDApproval.setLayoutManager(manager);
                    recyclerMSDApproval.setAdapter(msdApprovalAdapter);
                    recyclerMSDApproval.addItemDecoration(new DividerItemDecoration(MSDProgramApproval.this, DividerItemDecoration.VERTICAL));
                } else {
                    msdApprovalDialog.dismiss();
                    Toast.makeText(MSDProgramApproval.this, "No data Available!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<MSDApprovalModel>> call, @NonNull Throwable t) {
                msdApprovalDialog.dismiss();
                msdProgramApprovalList();
            }
        });
    }

    @Override
    public void onClick(int position, MSDApprovalModel model) {

    }
}