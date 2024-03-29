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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

public class ASMDcfpFollowupActivity extends Activity implements DcfpFollowupAdapter.DcfpClickListener {
    TextView tvfromdate, tvtodate, title, planned_todDoc, visited_todDoc, pTotDocNum, pMornNum, pEveNum, vTotDocNum, vMornNum, vEveNum, nVisitNum, vPercentNum;
    Button backBtn, submitBtn;
    Calendar c_todate, c_fromdate;
    SimpleDateFormat dftodate, dffromdate;
    String current_todate, current_fromdate, ff_code, toDate, fromDate, userRole, userName, designation;
    Calendar myCalendar, myCalendar1;
    private RecyclerView dcfpFollowupRecycler;
    DatePickerDialog.OnDateSetListener date_form, date_to;
    private DcfpFollowupAdapter dcfpFollowupAdapter;
    public ArrayList<DcrFollowupModel> dcfpFollowupList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dcfp_followup);

        initViews();
        calenderInit();
        if (!ff_code.isEmpty()) {
            if (Objects.equals(userRole, "D")) {
                dcrDcfpFollowupInfo();
            } else if (Objects.equals(userRole, "T")) {
                tourDetailFollowupInfo();
            }
        }
        backBtn.setOnClickListener(v -> finish());
        submitBtn.setOnClickListener(v -> {
            if (Objects.equals(userRole, "D")) {
                dcrDcfpFollowupInfo();
            } else if (Objects.equals(userRole, "T")) {
                tourDetailFollowupInfo();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void initViews() {
        Bundle b = getIntent().getExtras();
        toDate = b.getString("toDate");
        ff_code = b.getString("ff_code");
        fromDate = b.getString("fromDate");
        userRole = b.getString("userRole");
        userName = b.getString("userName");
        designation = b.getString("designation");
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        backBtn = findViewById(R.id.backbt);
        dcfpFollowupRecycler = findViewById(R.id.recyclerDcfpFollowup);
        submitBtn = findViewById(R.id.submitBtn);
        tvfromdate = findViewById(R.id.fromdate);
        tvtodate = findViewById(R.id.todate);
        backBtn.setTypeface(fontFamily);
        backBtn.setText("\uf060");
        title = findViewById(R.id.title);
        pTotDocNum = findViewById(R.id.pTotDocNum);
        pMornNum = findViewById(R.id.pMornNum);
        pEveNum = findViewById(R.id.pEveNum);
        vTotDocNum = findViewById(R.id.vTotDocNum);
        vMornNum = findViewById(R.id.vMornNum);
        vEveNum = findViewById(R.id.vEveNum);
        nVisitNum = findViewById(R.id.nVisitNum);
        vPercentNum = findViewById(R.id.vPercentNum);
        planned_todDoc = findViewById(R.id.planned_todDoc);
        visited_todDoc = findViewById(R.id.visited_todDoc);

        if (Objects.equals(userRole, "D")) {
            title.setText("Dcfp Followup");
            planned_todDoc.setText("Tot_Doc");
            visited_todDoc.setText("Tot_Doc");
        } else if (Objects.equals(userRole, "T")) {
            title.setText("Tour Program Followup");
            planned_todDoc.setText("Tot_Terri");
            visited_todDoc.setText("Tot_Terri");
        }
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
        tvfromdate.setOnClickListener(v -> new DatePickerDialog(ASMDcfpFollowupActivity.this, date_form, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
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
        tvtodate.setOnClickListener(v -> new DatePickerDialog(ASMDcfpFollowupActivity.this, date_to, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar1.get(Calendar.DAY_OF_MONTH)).show());
    }

    public void dcrDcfpFollowupInfo() {
        ProgressDialog dcfpFollowDialog = new ProgressDialog(ASMDcfpFollowupActivity.this);
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
                    dcfpFollowupAdapter = new DcfpFollowupAdapter(ASMDcfpFollowupActivity.this, dcfpFollowupList, ASMDcfpFollowupActivity.this, userRole);
                    LinearLayoutManager manager = new LinearLayoutManager(ASMDcfpFollowupActivity.this);
                    dcfpFollowupRecycler.setLayoutManager(manager);
                    dcfpFollowupRecycler.setAdapter(dcfpFollowupAdapter);
                    dcfpFollowupRecycler.addItemDecoration(new DividerItemDecoration(ASMDcfpFollowupActivity.this, DividerItemDecoration.VERTICAL));
                } else {
                    dcfpFollowDialog.dismiss();
                    Toast.makeText(ASMDcfpFollowupActivity.this, "No data Available!", Toast.LENGTH_LONG).show();
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
        ProgressDialog dcfpFollowDialog = new ProgressDialog(ASMDcfpFollowupActivity.this);
        dcfpFollowDialog.setMessage("Tour Followup Loading...");
        dcfpFollowDialog.setTitle("Tour Followup");
        dcfpFollowDialog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<DcrFollowupModel>> call = apiInterface.getTourChildWiseFollowup(ff_code, tvtodate.getText().toString(), tvfromdate.getText().toString());
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
                    dcfpFollowupAdapter = new DcfpFollowupAdapter(ASMDcfpFollowupActivity.this, dcfpFollowupList, ASMDcfpFollowupActivity.this, userRole);
                    LinearLayoutManager manager = new LinearLayoutManager(ASMDcfpFollowupActivity.this);
                    dcfpFollowupRecycler.setLayoutManager(manager);
                    dcfpFollowupRecycler.setAdapter(dcfpFollowupAdapter);
                    dcfpFollowupRecycler.addItemDecoration(new DividerItemDecoration(ASMDcfpFollowupActivity.this, DividerItemDecoration.VERTICAL));

                    int pTotDocCount = 0, pMornCount = 0, pEveCount = 0, vTotDocCount = 0, vMornCount = 0, vEveCount = 0, nVisitCount = 0;
                    double vPercentCount = 0.0;

                    for (int j=0; j<dcfpFollowupList.size(); j++) {
                        pTotDocCount += Integer.parseInt(dcfpFollowupList.get(j).getTotTerritory());
                        pMornCount += Integer.parseInt(dcfpFollowupList.get(j).getPlanMor());
                        pEveCount += Integer.parseInt(dcfpFollowupList.get(j).getPlanEve());
                        vTotDocCount += Integer.parseInt(dcfpFollowupList.get(j).getTotVisited());
                        vMornCount += Integer.parseInt(dcfpFollowupList.get(j).getVisitedMor());
                        vEveCount += Integer.parseInt(dcfpFollowupList.get(j).getVisitedEve());
                        nVisitCount += Integer.parseInt(dcfpFollowupList.get(j).getNotVisited());
                        vPercentCount += Double.parseDouble(dcfpFollowupList.get(j).getVisitPercent());
                    }
                    DecimalFormat formatter = new DecimalFormat("#,##,###");
                    pTotDocNum.setText(String.valueOf(formatter.format(pTotDocCount)));
                    pMornNum.setText(String.valueOf(formatter.format(pMornCount)));
                    pEveNum.setText(String.valueOf(formatter.format(pEveCount)));
                    vTotDocNum.setText(String.valueOf(formatter.format(vTotDocCount)));
                    vMornNum.setText(String.valueOf(formatter.format(vMornCount)));
                    vEveNum.setText(String.valueOf(formatter.format(vEveCount)));
                    nVisitNum.setText(String.valueOf(formatter.format(nVisitCount)));
                    vPercentNum.setText(String.valueOf(vPercentCount));
                } else {
                    dcfpFollowDialog.dismiss();
                    Toast.makeText(ASMDcfpFollowupActivity.this, "No data Available!", Toast.LENGTH_LONG).show();
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
    public void onDcfpClick(int position, DcrFollowupModel model) {
        if (Objects.equals(userRole, "D")) {
            Intent intent = new Intent(ASMDcfpFollowupActivity.this, RMDcfpFollowupActivity.class);
            intent.putExtra("ff_code", model.getFfCode());
            intent.putExtra("toDate", tvtodate.getText().toString());
            intent.putExtra("fromDate", tvfromdate.getText().toString());
            intent.putExtra("userRole", userRole);
            intent.putExtra("userName", userName);
            startActivity(intent);
        } else if (Objects.equals(userRole, "T") && !Objects.equals(designation, "RM")) {
            Intent intent = new Intent(ASMDcfpFollowupActivity.this, RMDcfpFollowupActivity.class);
            intent.putExtra("ff_code", model.getFfCode());
            intent.putExtra("toDate", tvtodate.getText().toString());
            intent.putExtra("fromDate", tvfromdate.getText().toString());
            intent.putExtra("userRole", userRole);
            intent.putExtra("userName", userName);
            intent.putExtra("designation", designation);
            startActivity(intent);
        }
    }
}