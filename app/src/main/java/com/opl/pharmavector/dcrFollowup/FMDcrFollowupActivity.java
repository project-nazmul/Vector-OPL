package com.opl.pharmavector.dcrFollowup;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.opl.pharmavector.R;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

public class FMDcrFollowupActivity extends Activity implements DcfpFollowupAdapter.DcfpClickListener {
    TextView tvfromdate, tvtodate;
    Button backBtn, submitBtn;
    Calendar c_todate, c_fromdate;
    SimpleDateFormat dftodate, dffromdate;
    String current_todate, current_fromdate, ff_code, toDate, fromDate;
    Calendar myCalendar, myCalendar1;
    private RecyclerView dcfpFollowupRecycler;
    DatePickerDialog.OnDateSetListener date_form, date_to;
    private DcfpFollowupAdapter dcfpFollowupAdapter;
    private ArrayList<DcrFollowupModel> dcfpFollowupList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dcfp_followup);

        initViews();
        calenderInit();
        if (!ff_code.isEmpty()) {
            dcrDcfpFollowupInfo();
        }
        backBtn.setOnClickListener(v -> finish());
        submitBtn.setOnClickListener(v -> dcrDcfpFollowupInfo());
    }

    private void initViews() {
        Bundle b = getIntent().getExtras();
        toDate = b.getString("toDate");
        ff_code = b.getString("ff_code");
        fromDate = b.getString("fromDate");
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        backBtn = findViewById(R.id.backbt);
        dcfpFollowupRecycler = findViewById(R.id.recyclerDcfpFollowup);
        submitBtn = findViewById(R.id.submitBtn);
        tvfromdate = findViewById(R.id.fromdate);
        tvtodate = findViewById(R.id.todate);
        backBtn.setTypeface(fontFamily);
        backBtn.setText("\uf060");
    }

    @SuppressLint("SimpleDateFormat")
    private void calenderInit() {
        tvfromdate = findViewById(R.id.fromdate);
        tvtodate = findViewById(R.id.todate);
        c_todate = Calendar.getInstance();
        dftodate = new SimpleDateFormat("dd/MM/yyyy");
        current_todate = dftodate.format(c_todate.getTime());
        //tvtodate.setText(current_todate);
        c_fromdate = Calendar.getInstance();
        dffromdate = new SimpleDateFormat("01/MM/yyyy");
        current_fromdate = dffromdate.format(c_fromdate.getTime());
        //tvfromdate.setText(current_fromdate);
        myCalendar = Calendar.getInstance();

        if (fromDate != null && toDate != null) {
            tvfromdate.setText(fromDate);
            tvtodate.setText(toDate);
        } else {
            tvfromdate.setText(current_fromdate);
            tvtodate.setText(current_todate);
        }

        date_form = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {
                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                tvfromdate.setTextColor(Color.BLACK);
                tvfromdate.setText("");
                tvfromdate.setText(sdf.format(myCalendar.getTime()));
            }
        };
        tvfromdate.setOnClickListener(v -> new DatePickerDialog(FMDcrFollowupActivity.this, date_form, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());
        myCalendar1 = Calendar.getInstance();
        date_to = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {
                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                tvtodate.setTextColor(Color.BLACK);
                tvtodate.setText("");
                tvtodate.setText(sdf.format(myCalendar.getTime()));
            }
        };
        tvtodate.setOnClickListener(v -> new DatePickerDialog(FMDcrFollowupActivity.this, date_to, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar1.get(Calendar.DAY_OF_MONTH)).show());
    }

    public void dcrDcfpFollowupInfo() {
        ProgressDialog dcfpFollowDialog = new ProgressDialog(FMDcrFollowupActivity.this);
        dcfpFollowDialog.setMessage("Dcfp Followup Loading...");
        dcfpFollowDialog.setTitle("Dcfp Followup");
        dcfpFollowDialog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<DcrFollowupModel>> call = apiInterface.getDcrDcfpFollowup(ff_code, tvtodate.getText().toString(), tvfromdate.getText().toString());
        dcfpFollowupList.clear();

        call.enqueue(new Callback<List<DcrFollowupModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<DcrFollowupModel>> call, @NonNull retrofit2.Response<List<DcrFollowupModel>> response) {
                if (response.body() != null) {
                    dcfpFollowupList.addAll(response.body());
                    Log.d("tag", dcfpFollowupList.toString());
                }

                if (dcfpFollowupList.size() > 0) {
                    dcfpFollowDialog.dismiss();
                    dcfpFollowupAdapter = new DcfpFollowupAdapter(FMDcrFollowupActivity.this, dcfpFollowupList, FMDcrFollowupActivity.this);
                    LinearLayoutManager manager = new LinearLayoutManager(FMDcrFollowupActivity.this);
                    dcfpFollowupRecycler.setLayoutManager(manager);
                    dcfpFollowupRecycler.setAdapter(dcfpFollowupAdapter);
                    dcfpFollowupRecycler.addItemDecoration(new DividerItemDecoration(FMDcrFollowupActivity.this, DividerItemDecoration.VERTICAL));
                } else {
                    dcfpFollowDialog.dismiss();
                    Toast.makeText(FMDcrFollowupActivity.this, "No data Available!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<DcrFollowupModel>> call, @NonNull Throwable t) {
                dcfpFollowDialog.dismiss();
                dcrDcfpFollowupInfo();
            }
        });
    }

    @Override
    public void onDcfpClick(int position, DcrFollowupModel model) {

    }
}