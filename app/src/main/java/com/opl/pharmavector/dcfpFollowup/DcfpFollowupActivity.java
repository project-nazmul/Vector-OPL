package com.opl.pharmavector.dcfpFollowup;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
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
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

public class DcfpFollowupActivity extends Activity implements DcrFollowupAdapter.ItemClickListener, DcfpFollowupAdapter.DcfpClickListener {
    TextView tvfromdate, tvtodate, planned_todDoc, visited_todDoc, title, p_detail_totDoc, v_detail_totDoc;
    Button backBtn, submitBtn;
    Calendar c_todate, c_fromdate;
    SimpleDateFormat dftodate, dffromdate;
    String current_todate, current_fromdate, userName, userName_2, userName_3, new_version, message_3, userRole;
    Calendar myCalendar, myCalendar1;
    private RecyclerView dcrFollowupRecycler, dcfpFollowupRecycler;
    DatePickerDialog.OnDateSetListener date_form, date_to;
    private DcrFollowupAdapter dcrFollowupAdapter;
    private DcfpFollowupAdapter dcfpFollowupAdapter;
    ArrayList<DcrFollowupModel> dcrFollowupList = new ArrayList<>();
    ArrayList<DcrFollowupModel> dcfpFollowupList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dcr_followup);

        initViews();
        calenderInit();

        if (userRole.equals("D")) { // D -> "DCFP"
            dcrSelfFollowupInfo();
        } else if (userRole.equals("T")) { // T -> "TOUR"
            tourSelfFollowupInfo();
            tourDetailFollowupInfo();
        }
        submitBtn.setOnClickListener(v -> {
            if (userRole.equals("D")) {
                dcrSelfFollowupInfo();
            } else if (userRole.equals("T")) {
                tourSelfFollowupInfo();
                tourDetailFollowupInfo();
            }
        });
        backBtn.setOnClickListener(v -> finish());
    }

    @SuppressLint("SetTextI18n")
    private void initViews() {
        Bundle b = getIntent().getExtras();
        userName = b.getString("UserName");
        userName_2 = b.getString("UserName_2");
        userName_3 = b.getString("UserName_3");
        new_version = b.getString("new_version");
        message_3 = b.getString("message_3");
        userRole = b.getString("UserRole");
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        backBtn = findViewById(R.id.backbt);
        dcrFollowupRecycler = findViewById(R.id.recyclerDcrFollowup);
        dcfpFollowupRecycler = findViewById(R.id.recyclerDcfpFollowup);
        submitBtn = findViewById(R.id.submitBtn);
        tvfromdate = findViewById(R.id.fromdate);
        tvtodate = findViewById(R.id.todate);
        title = findViewById(R.id.title);
        planned_todDoc = findViewById(R.id.planned_todDoc);
        visited_todDoc = findViewById(R.id.visited_todDoc);
        p_detail_totDoc = findViewById(R.id.p_detail_totDoc);
        v_detail_totDoc = findViewById(R.id.v_detail_totDoc);

        if (Objects.equals(userRole, "D")) {
            title.setText("Dcfp Followup");
            planned_todDoc.setText("Tot_Doc");
            visited_todDoc.setText("Tot_Doc");
            p_detail_totDoc.setText("Tot_Doc");
            v_detail_totDoc.setText("Tot_Doc");
        } else if (Objects.equals(userRole, "T")) {
            title.setText("Tour Followup");
            planned_todDoc.setText("Tot_Terri");
            visited_todDoc.setText("Tot_Terri");
            p_detail_totDoc.setText("Tot_Terri");
            v_detail_totDoc.setText("Tot_Terri");
        }
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
        tvtodate.setText(current_todate);
        c_fromdate = Calendar.getInstance();
        dffromdate = new SimpleDateFormat("01/MM/yyyy");
        current_fromdate = dffromdate.format(c_fromdate.getTime());
        tvfromdate.setText(current_fromdate);
        myCalendar = Calendar.getInstance();

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
        tvfromdate.setOnClickListener(v -> new DatePickerDialog(DcfpFollowupActivity.this, date_form, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
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
        tvtodate.setOnClickListener(v -> new DatePickerDialog(DcfpFollowupActivity.this, date_to, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar1.get(Calendar.DAY_OF_MONTH)).show());
    }

    public void dcrSelfFollowupInfo() {
        ProgressDialog dcrFollowDialog = new ProgressDialog(DcfpFollowupActivity.this);
        dcrFollowDialog.setMessage("Dcfp Followup Loading...");
        dcrFollowDialog.setTitle("Dcfp Followup");
        dcrFollowDialog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<DcrFollowupModel>> call = apiInterface.getDcrSelfFollowup(userName_3, tvtodate.getText().toString(), tvfromdate.getText().toString());
        dcrFollowupList.clear();

        call.enqueue(new Callback<List<DcrFollowupModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<DcrFollowupModel>> call, @NonNull retrofit2.Response<List<DcrFollowupModel>> response) {
                if (response.body() != null) {
                    dcrFollowupList.addAll(response.body());
                }

                if (response.isSuccessful()) {
                    dcrFollowDialog.dismiss();
                    dcrFollowupAdapter = new DcrFollowupAdapter(DcfpFollowupActivity.this, dcrFollowupList, DcfpFollowupActivity.this, userRole);
                    LinearLayoutManager manager = new LinearLayoutManager(DcfpFollowupActivity.this);
                    dcrFollowupRecycler.setLayoutManager(manager);
                    dcrFollowupRecycler.setAdapter(dcrFollowupAdapter);
                    dcrFollowupRecycler.addItemDecoration(new DividerItemDecoration(DcfpFollowupActivity.this, DividerItemDecoration.VERTICAL));
                } else {
                    dcrFollowDialog.dismiss();
                    Toast.makeText(DcfpFollowupActivity.this, "No data Available", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<DcrFollowupModel>> call, @NonNull Throwable t) {
                dcrFollowDialog.dismiss();
                dcrSelfFollowupInfo();
            }
        });
    }

    public void tourSelfFollowupInfo() {
        ProgressDialog dcrFollowDialog = new ProgressDialog(DcfpFollowupActivity.this);
        dcrFollowDialog.setMessage("Tour Followup Loading...");
        dcrFollowDialog.setTitle("Tour Followup");
        dcrFollowDialog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<DcrFollowupModel>> call = apiInterface.getTourSelfFollowup(userName_3, tvtodate.getText().toString(), tvfromdate.getText().toString());
        dcrFollowupList.clear();

        call.enqueue(new Callback<List<DcrFollowupModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<DcrFollowupModel>> call, @NonNull retrofit2.Response<List<DcrFollowupModel>> response) {
                if (response.body() != null) {
                    dcrFollowupList.addAll(response.body());
                }

                if (response.isSuccessful()) {
                    dcrFollowDialog.dismiss();
                    dcrFollowupAdapter = new DcrFollowupAdapter(DcfpFollowupActivity.this, dcrFollowupList, DcfpFollowupActivity.this, userRole);
                    LinearLayoutManager manager = new LinearLayoutManager(DcfpFollowupActivity.this);
                    dcrFollowupRecycler.setLayoutManager(manager);
                    dcrFollowupRecycler.setAdapter(dcrFollowupAdapter);
                    dcrFollowupRecycler.addItemDecoration(new DividerItemDecoration(DcfpFollowupActivity.this, DividerItemDecoration.VERTICAL));
                } else {
                    dcrFollowDialog.dismiss();
                    Toast.makeText(DcfpFollowupActivity.this, "No data Available!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<DcrFollowupModel>> call, @NonNull Throwable t) {
                dcrFollowDialog.dismiss();
                tourSelfFollowupInfo();
            }
        });
    }

    public void dcrDcfpFollowupInfo() {
        ProgressDialog dcfpFollowDialog = new ProgressDialog(DcfpFollowupActivity.this);
        dcfpFollowDialog.setMessage("Dcfp Followup Loading...");
        dcfpFollowDialog.setTitle("Dcfp Followup");
        dcfpFollowDialog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<DcrFollowupModel>> call = apiInterface.getDcrDcfpFollowup(userName_3, tvtodate.getText().toString(), tvfromdate.getText().toString());
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
                    dcfpFollowupAdapter = new DcfpFollowupAdapter(DcfpFollowupActivity.this, dcfpFollowupList, DcfpFollowupActivity.this, userRole);
                    LinearLayoutManager manager = new LinearLayoutManager(DcfpFollowupActivity.this);
                    dcfpFollowupRecycler.setLayoutManager(manager);
                    dcfpFollowupRecycler.setAdapter(dcfpFollowupAdapter);
                    dcfpFollowupRecycler.addItemDecoration(new DividerItemDecoration(DcfpFollowupActivity.this, DividerItemDecoration.VERTICAL));
                } else {
                    dcfpFollowDialog.dismiss();
                    Toast.makeText(DcfpFollowupActivity.this, "No data Available!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<DcrFollowupModel>> call, @NonNull Throwable t) {
                dcfpFollowDialog.dismiss();
                dcrDcfpFollowupInfo();
            }
        });
    }

    public void tourDetailFollowupInfo() {
        ProgressDialog dcfpFollowDialog = new ProgressDialog(DcfpFollowupActivity.this);
        dcfpFollowDialog.setMessage("Tour Followup Loading...");
        dcfpFollowDialog.setTitle("Tour Followup");
        dcfpFollowDialog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<DcrFollowupModel>> call = apiInterface.getTourRoleWiseFollowup(userName_3, tvtodate.getText().toString(), tvfromdate.getText().toString());
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
                    dcfpFollowupAdapter = new DcfpFollowupAdapter(DcfpFollowupActivity.this, dcfpFollowupList, DcfpFollowupActivity.this, userRole);
                    LinearLayoutManager manager = new LinearLayoutManager(DcfpFollowupActivity.this);
                    dcfpFollowupRecycler.setLayoutManager(manager);
                    dcfpFollowupRecycler.setAdapter(dcfpFollowupAdapter);
                    dcfpFollowupRecycler.addItemDecoration(new DividerItemDecoration(DcfpFollowupActivity.this, DividerItemDecoration.VERTICAL));
                } else {
                    dcfpFollowDialog.dismiss();
                    Toast.makeText(DcfpFollowupActivity.this, "No data Available!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<DcrFollowupModel>> call, @NonNull Throwable t) {
                dcfpFollowDialog.dismiss();
                tourDetailFollowupInfo();
            }
        });
    }

    @Override
    public void onClick(int position, DcrFollowupModel model) {
        if (Objects.equals(userRole, "D")) {
            dcrDcfpFollowupInfo();
        } else if (Objects.equals(userRole, "T")) {
            //tourDetailFollowupInfo();
        }
    }

    @Override
    public void onDcfpClick(int position, DcrFollowupModel model) {
        Intent intent = new Intent(DcfpFollowupActivity.this, AMDcfpFollowupActivity.class);
        intent.putExtra("ff_code", model.getFfCode());
        intent.putExtra("toDate", tvtodate.getText().toString());
        intent.putExtra("fromDate", tvfromdate.getText().toString());
        intent.putExtra("userName", userName_3);
        intent.putExtra("userRole", userRole);
        startActivity(intent);
    }
}