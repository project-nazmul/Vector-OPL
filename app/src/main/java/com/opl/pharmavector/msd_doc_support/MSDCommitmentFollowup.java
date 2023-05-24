package com.opl.pharmavector.msd_doc_support;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.opl.pharmavector.MonthYearPickerDialog;
import com.opl.pharmavector.R;
import com.opl.pharmavector.msd_doc_support.adapter.MSDApprovalAdapter;
import com.opl.pharmavector.msd_doc_support.adapter.MSDApprovalModel;
import com.opl.pharmavector.msd_doc_support.adapter.MSDCommitmentAdapter;
import com.opl.pharmavector.msd_doc_support.adapter.MSDCommitmentModel;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MSDCommitmentFollowup extends AppCompatActivity {
    Button showBtn, submitApproval, backBtn;
    EditText ed_date;
    RecyclerView recyclerMSDCommitment;
    MSDCommitmentAdapter msdCommitmentAdapter;
    public String userName, UserName_2, promo_type, user_flag, user_code, monthYearStr, year_val, month_val, proposed_date1, month_name_val, month_name, proposed_date2, last_date;
    ArrayList<MSDCommitmentModel> msdCommitmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msd_commitment_followup);

        initViews();
        calenderUI();
        msdCommitmentFollowup(proposed_date2);
        backBtn.setOnClickListener(v -> finish());
        showBtn.setOnClickListener(v -> msdCommitmentFollowup(proposed_date2));
    }

    private void initViews() {
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        recyclerMSDCommitment = findViewById(R.id.recyclerMSDCommitment);
        ed_date = findViewById(R.id.ed_date);
        submitApproval = findViewById(R.id.submitApproval);
        backBtn = findViewById(R.id.backBtn);
        showBtn = findViewById(R.id.showBtn);
        showBtn.setTypeface(fontFamily);
        showBtn.setText("\uf1d8");
        backBtn.setTypeface(fontFamily);
        backBtn.setText("\uf08b");
        ed_date.setFocusableInTouchMode(true);
        ed_date.setFocusable(true);
        ed_date.requestFocus();
        ed_date.setClickable(true);
        ed_date.setInputType(InputType.TYPE_NULL);
        Calendar cal = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat month_date = new SimpleDateFormat("MMM");
        String month_name = month_date.format(cal.getTime());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat year_date = new SimpleDateFormat("yyyy");
        String year_name = year_date.format(cal.getTime());
        String first_date = "JAN" + "-" + year_name.toUpperCase();
        last_date = month_name.toUpperCase() + "-" + year_name.toUpperCase();
        proposed_date2 = "01" + "-" + last_date;
        ed_date.setText(last_date);

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
                Log.d("date", proposed_date1);
                ed_date.setText(proposed_date2);
            });
            pickerDialog.show(getSupportFragmentManager(), "MonthYearPickerDialog");
        });
    }

    public void msdCommitmentFollowup(String selectMonth) {
        ProgressDialog msdCommitmentDialog = new ProgressDialog(MSDCommitmentFollowup.this);
        msdCommitmentDialog.setMessage("MSD Approval List Loading...");
        msdCommitmentDialog.setTitle("MSD Program Approval");
        msdCommitmentDialog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<MSDCommitmentModel>> call = apiInterface.getMSDCommitmentFollowup(selectMonth);
        msdCommitmentList.clear();

        call.enqueue(new Callback<List<MSDCommitmentModel>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<List<MSDCommitmentModel>> call, @NonNull retrofit2.Response<List<MSDCommitmentModel>> response) {
                if (response.body() != null) {
                    msdCommitmentList.addAll(response.body());
                    Log.d("tag", msdCommitmentList.toString());
                }

                if (msdCommitmentList.size() > 0) {
                    msdCommitmentDialog.dismiss();
                    msdCommitmentAdapter = new MSDCommitmentAdapter(MSDCommitmentFollowup.this, msdCommitmentList);
                    LinearLayoutManager manager = new LinearLayoutManager(MSDCommitmentFollowup.this);
                    recyclerMSDCommitment.setLayoutManager(manager);
                    recyclerMSDCommitment.setAdapter(msdCommitmentAdapter);
                    recyclerMSDCommitment.addItemDecoration(new DividerItemDecoration(MSDCommitmentFollowup.this, DividerItemDecoration.VERTICAL));
                } else {
                    msdCommitmentDialog.dismiss();
                    Toast.makeText(MSDCommitmentFollowup.this, "No data Available!", Toast.LENGTH_LONG).show();
                }
                msdCommitmentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<List<MSDCommitmentModel>> call, @NonNull Throwable t) {
                msdCommitmentDialog.dismiss();
                msdCommitmentFollowup(proposed_date2);
            }
        });
    }
}