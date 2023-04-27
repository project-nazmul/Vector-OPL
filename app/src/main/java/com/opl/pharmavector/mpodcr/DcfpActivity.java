package com.opl.pharmavector.mpodcr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.opl.pharmavector.AmDcr;
import com.opl.pharmavector.DcrReport;
import com.opl.pharmavector.R;
import com.opl.pharmavector.amdashboard.VacantList;
import com.opl.pharmavector.amdashboard.VacantModel;
import com.opl.pharmavector.doctorList.DoctorListActivity;
import com.opl.pharmavector.doctorList.adapter.DoctorAdapter;
import com.opl.pharmavector.doctorList.model.DoctorList;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

public class DcfpActivity extends AppCompatActivity {
    TextView date2, ded, fromdate, todate;
    Button submit, back;
    String userName;
    List<DcfpList> dcfpLists;
    RecyclerView dcfpRecycler;
    DcfpAdapter dcfpAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dcfp);

        initViews();
        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date_to = new DatePickerDialog.OnDateSetListener() {
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
                todate.setTextColor(Color.BLACK);
                todate.setText("");
                todate.setText(sdf.format(myCalendar.getTime()));
            }
        };

        todate.setOnClickListener(v -> new DatePickerDialog(DcfpActivity.this, date_to, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());

        submit.setOnClickListener(v -> {
            getDcfpPreviewList();
        });

        back.setOnClickListener(v -> {
            finish();
        });
    }

    private void initViews() {
        Typeface fontFamily = Typeface.createFromAsset(getAssets(),"fonts/fontawesome.ttf");
        Bundle b = getIntent().getExtras();
        userName = b.getString("UserName");
        back = findViewById(R.id.backBtn);
        todate = findViewById(R.id.todate);
        submit = findViewById(R.id.submitBtn_2);
        dcfpRecycler = findViewById(R.id.recyclerDcfpList);
        Calendar c_todate = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dftodate = new SimpleDateFormat("dd/MM/yyyy");
        String current_todate = dftodate.format(c_todate.getTime());
        todate.setText(current_todate);
        back.setTypeface(fontFamily);
        back.setText("\uf060 ");
    }

    public void getDcfpPreviewList() {
        ProgressDialog pDialog;
        pDialog = new ProgressDialog(DcfpActivity.this);
        pDialog.setTitle("Please wait !");
        pDialog.setMessage("Loading Dcfp Preview Data..");
        pDialog.setCancelable(true);
        pDialog.show();

        Bundle b = getIntent().getExtras();
        String userName = b.getString("UserName");
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<DcfpModel> call = apiInterface.getDcfpPreviewList(userName, todate.getText().toString().trim());

        call.enqueue(new Callback<DcfpModel>() {
            @Override
            public void onResponse(@NotNull Call<DcfpModel> call, @NotNull retrofit2.Response<DcfpModel> response) {
                dcfpLists = Objects.requireNonNull(response.body()).getDcfpLists();
                Log.d("dcfp=>","response==>"+ dcfpLists.toString());

                if (response.isSuccessful()) {
//                    for (int i = 0; i < (doctorData != null ? doctorData.size() : 0); i++) {
//                        doctorList.add(new DoctorList(
//                                doctorData.get(i).getDocCode(),
//                                doctorData.get(i).getDocName(),
//                                doctorData.get(i).getDegree(),
//                                doctorData.get(i).getDesig(),
//                                doctorData.get(i).getSpecialization(),
//                                doctorData.get(i).getMarketName(),
//                                doctorData.get(i).getMarketCode(),
//                                doctorData.get(i).getAddress(),
//                                doctorData.get(i).getPatientPerDay()));
//                    }
                    pDialog.dismiss();
                    dcfpAdapter = new DcfpAdapter(DcfpActivity.this, dcfpLists);
                    LinearLayoutManager manager = new LinearLayoutManager(DcfpActivity.this);
                    dcfpRecycler.setLayoutManager(manager);
                    dcfpRecycler.setAdapter(dcfpAdapter);
                    dcfpRecycler.addItemDecoration(new DividerItemDecoration(DcfpActivity.this, DividerItemDecoration.VERTICAL));
                    //Log.d("company List", companyDatalist.get(0).getComDesc());
                } else {
                    pDialog.dismiss();
                    Toast.makeText(DcfpActivity.this, "No data Available", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DcfpModel> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(DcfpActivity.this, "Network error! Please wait while we are reconnecting", Toast.LENGTH_SHORT).show();
            }
        });
    }
}