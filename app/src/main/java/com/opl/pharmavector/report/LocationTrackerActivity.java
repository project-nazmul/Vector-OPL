package com.opl.pharmavector.report;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.opl.pharmavector.R;
import com.opl.pharmavector.prescriber.FieldForceList;
import com.opl.pharmavector.prescriber.FieldForceModel;
import com.opl.pharmavector.prescriber.PrescriberAdapter;
import com.opl.pharmavector.prescriber.TopPrescriberActivity;
import com.opl.pharmavector.prescriptionsurvey.PrescriptionFollowup;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;
import com.opl.pharmavector.util.PreferenceManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationTrackerActivity extends Activity {
    Calendar currentFormatDate, fromDateCalendar;
    SimpleDateFormat dateFormatDate;
    PreferenceManager preferenceManager;
    MaterialSpinner locationFieldType;
    TextView locationFromDate;
    Button locationShowBtn;
    LocationTrackerAdapter locationAdapter;
    RecyclerView recyclerLocation;
    ArrayAdapter<String> ffAdapter;
    AutoCompleteTextView locationFieldForce;
    DatePickerDialog.OnDateSetListener fromDatePicker;
    public String userName, userCode, userRole, locationFfCode, locationRmCode;
    String fieldForceValue = "Territory", manager_code, manager_detail, currentFromDate, location_date;
    List<String> fieldForceList = new ArrayList<String>();
    private List<FieldForceList> fieldForceLists = new ArrayList<>();
    private List<LocationReportList> locationReportLists = new ArrayList<>();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_tracker);

        initViews();
        initCalender();
        initFieldTypeSpinner();
        locationFieldForce.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("onTouch", String.valueOf(v.getId()));
                locationFieldForce.showDropDown();
                return false;
            }
        });
        locationFieldForce.setOnItemClickListener((parent, view, position, id) -> {
            hideSoftKeyboard(view);
        });
        locationFieldForce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        locationFieldForce.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                locationFieldForce.setTextColor(Color.BLUE);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                locationFieldForce.setTextColor(Color.BLUE);
            }

            @Override
            public void afterTextChanged(final Editable s) {
                try {
                    final String inputFieldForce = s.toString();
                    int fieldForceSize = inputFieldForce.length();

                    if (inputFieldForce.contains("-")) {
                        String[] first_split = inputFieldForce.split("-");
                        locationFfCode = first_split[0].trim();
                        locationRmCode = first_split[1].trim();
                        locationFieldForce.setText(locationFfCode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        locationShowBtn.setOnClickListener(v -> getLocationReportList());
    }

    @SuppressLint("SimpleDateFormat")
    private void initCalender() {
        currentFormatDate = Calendar.getInstance();
        dateFormatDate = new SimpleDateFormat("01/MM/yyyy");
        currentFromDate = dateFormatDate.format(currentFormatDate.getTime());
        locationFromDate.setText(currentFromDate);
        fromDateCalendar = Calendar.getInstance();

        locationFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(LocationTrackerActivity.this, fromDatePicker, fromDateCalendar.get(Calendar.YEAR), fromDateCalendar.get(Calendar.MONTH),
                        fromDateCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        fromDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                fromDateCalendar.set(Calendar.YEAR, year);
                fromDateCalendar.set(Calendar.MONTH, monthOfYear);
                fromDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {
                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                locationFromDate.setTextColor(Color.BLACK);
                locationFromDate.setText("");
                locationFromDate.setText(sdf.format(fromDateCalendar.getTime()));
            }
        };
    }

    private void hideSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    @SuppressLint("SimpleDateFormat")
    private void initViews() {
        preferenceManager = new PreferenceManager(this);
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        Bundle b = getIntent().getExtras();
        userName = b.getString("userName");
        userCode = b.getString("userCode");
        userRole = b.getString("userRole");

        locationShowBtn = findViewById(R.id.locationShowBtn);
        recyclerLocation = findViewById(R.id.recyclerLocation);
        locationFromDate = findViewById(R.id.locationFromDate);
        locationFieldType = findViewById(R.id.locationFieldType);
        locationFieldForce = findViewById(R.id.locationFieldForce);
    }

    private void initFieldTypeSpinner() {
        switch (userRole) {
            case "AD":
                locationFieldType.setItems("National", "Division", "Zone", "Region", "Area", "Territory");
                break;
            case "SM":
                locationFieldType.setItems("Zone", "Region", "Area", "Territory");
                break;
            case "ASM":
                locationFieldType.setItems("Region", "Area", "Territory");
                break;
            case "RM":
                locationFieldType.setItems("Area", "Territory");
                break;
            case "FM":
                locationFieldType.setItems("Territory");
                break;
        }
        locationFieldType.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                locationFieldForce.setText("");
                fieldForceValue = String.valueOf(item);

                if (!fieldForceValue.trim().equals("National")) {
                    getFieldForceInfo();
                } else {
                    fieldForceList.clear();
                    fieldForceLists.clear();
                    initFieldForceSpinner();
                }
            }
        });
    }

    private void getFieldForceInfo() {
        ProgressDialog fieldForceDialog = new ProgressDialog(LocationTrackerActivity.this);
        fieldForceDialog.setMessage("Field Force Loading...");
        fieldForceDialog.setTitle("Field Force Followup");
        fieldForceDialog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<FieldForceModel> call = apiInterface.getLocationFfCode(fieldForceValue, userCode, userRole);
        fieldForceLists.clear();
        Log.d("fieldForce", userCode);

        call.enqueue(new Callback<FieldForceModel>() {
            @Override
            public void onResponse(Call<FieldForceModel> call, Response<FieldForceModel> response) {
                if (response.isSuccessful()) {
                    fieldForceDialog.dismiss();
                    fieldForceLists = Objects.requireNonNull(response.body()).getFieldForceLists();
                    if (fieldForceLists.size() > 0) {
                        initFieldForceSpinner();
                    }
                    Log.d("fieldForce", String.valueOf(fieldForceLists));
                }
            }

            @Override
            public void onFailure(Call<FieldForceModel> call, Throwable t) {
                Log.d("fieldForce", "Failed to Retrieved Data -- " + t);
                fieldForceDialog.dismiss();
                Toast toast = Toast.makeText(getBaseContext(), "Failed to Retrieved Data!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    @SuppressLint("SimpleDateFormat")
    private void getLocationReportList() {
        location_date = "";
        SimpleDateFormat fromDate = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MMM/yyyy");
        Date from_date = null;

        try {
            from_date = fromDate.parse(locationFromDate.getText().toString());
            assert from_date != null;
            location_date = formatDate.format(from_date.getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Log.d("p_date", location_date);

        ProgressDialog locReportDialog = new ProgressDialog(LocationTrackerActivity.this);
        locReportDialog.setMessage("Location Tracker Loading...");
        locReportDialog.setTitle("Location Tracker Followup");
        locReportDialog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<LocationReportModel> call = apiInterface.getLocationReportList(location_date, locationFfCode);
        locationReportLists.clear();
        //Log.d("fieldForce", );

        call.enqueue(new Callback<LocationReportModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<LocationReportModel> call, Response<LocationReportModel> response) {
                if (response.isSuccessful()) {
                    locReportDialog.dismiss();
                    locationReportLists = Objects.requireNonNull(response.body()).getLocationReportList();

                    locationAdapter = new LocationTrackerAdapter(LocationTrackerActivity.this, locationReportLists);
                    LinearLayoutManager manager = new LinearLayoutManager(LocationTrackerActivity.this);
                    recyclerLocation.setLayoutManager(manager);
                    recyclerLocation.setAdapter(locationAdapter);
                    recyclerLocation.addItemDecoration(new DividerItemDecoration(LocationTrackerActivity.this, DividerItemDecoration.VERTICAL));
                    Log.d("location", String.valueOf(locationReportLists));
                }
            }

            @Override
            public void onFailure(Call<LocationReportModel> call, Throwable t) {
                Log.d("fieldForce", "Failed to Retrieved Data -- " + t);
                locReportDialog.dismiss();
                Toast toast = Toast.makeText(getBaseContext(), "Failed to Retrieved Data!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private void initFieldForceSpinner() {
        fieldForceList.clear();
        for (int i = 0; i < fieldForceLists.size(); i++) {
            fieldForceList.add(fieldForceLists.get(i).getName());
        }
        ffAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, fieldForceList);
        locationFieldForce.setThreshold(2);
        locationFieldForce.setAdapter(ffAdapter);
        locationFieldForce.setTextColor(Color.BLUE);
    }
}