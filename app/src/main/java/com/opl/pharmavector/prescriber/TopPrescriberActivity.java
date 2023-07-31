package com.opl.pharmavector.prescriber;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.opl.pharmavector.R;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;
import com.opl.pharmavector.util.PreferenceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopPrescriberActivity extends Activity {
    Button showBtn;
    TextView genericName;
    MaterialSpinner fieldType;
    AutoCompleteTextView fieldForce, genericType;
    PreferenceManager preferenceManager;
    RecyclerView recyclerPrescriber;
    private PrescriberAdapter prescriberAdapter;
    String fieldForceValue ="Territory", manager_code, manager_detail;
    private List<FromDateList> fromDateLists = new ArrayList<>();
    private List<FromDateList> toDateLists = new ArrayList<>();
    private List<FieldForceList> fieldForceLists = new ArrayList<>();
    private List<GenericTypeList> genericTypeLists = new ArrayList<>();
    private List<TopPrescriberList> topPrescriberLists = new ArrayList<>();
    public String userName, userCode, userRole, new_version, message_3, message, ffCode, rmCode, rmName, managerCode, genCode, genName;
    public String fromDate, fromDateFormat, toDate, toDateFormat, globalUserName, globalUserCode;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_prescriber);

        initViews();
        getFromDateInfo();
        getToDateInfo();
        if (!"MPO".equals(userRole)) {
            fieldType.setVisibility(View.VISIBLE);
            initFieldTypeSpinner();
        } else {
            fieldType.setVisibility(View.GONE);
            fieldForce.setText(userCode);
        }
        showBtn.setOnClickListener(v -> {
            hideSoftKeyboard(v);
            if (userRole.equals("MPO")) {
                ffCode = userCode;
                prescriberDetailsInfo();
            } else {
                prescriberDetailsInfo();
            }
        });
        fieldForce.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                fieldForce.showDropDown();
                return false;
            }
        });
        genericType.setOnItemClickListener((parent, view, position, id) -> {
            hideSoftKeyboard(view);
        });
        fieldForce.setOnItemClickListener((parent, view, position, id) -> {
            hideSoftKeyboard(view);
        });
        fieldForce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        fieldForce.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                fieldForce.setTextColor(Color.BLUE);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                fieldForce.setTextColor(Color.BLUE);
            }

            @Override
            public void afterTextChanged(final Editable s) {
                try {
                    final String inputFieldForce = s.toString();
                    int fieldForceSize = inputFieldForce.length();

                    if (inputFieldForce.contains("-")) {
                        //ffCode = inputFieldForce.substring(inputFieldForce.indexOf("-") + 1);
                        String[] first_split = inputFieldForce.split("-");
                        ffCode = first_split[0].trim();
                        rmName = first_split[1].trim();
                        fieldForce.setText(ffCode);
                        //KeyboardUtils.hideKeyboard(TopPrescriberActivity.this);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        genericType.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                genericType.showDropDown();
                return false;
            }
        });
        genericType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        genericType.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                genericType.setTextColor(Color.BLUE);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                genericType.setTextColor(Color.BLUE);
            }

            @Override
            public void afterTextChanged(final Editable s) {
                try {
                    final String inputFieldForce = s.toString();
                    int fieldForceSize = inputFieldForce.length();

                    if (inputFieldForce.contains("-")) {
                        String ffValue = inputFieldForce.substring(inputFieldForce.indexOf("-") + 1);
                        String[] first_split = inputFieldForce.split("-");
                        genName = first_split[0].trim();
                        genCode = first_split[1].trim();
                        genericName.setText(genName);
                        //genericType.setText(inputFieldForce);
                        //KeyboardUtils.hideKeyboard(TopPrescriberActivity.this);
                        Log.d("genericType", "ffValue:: "+ ffValue + " :: " + inputFieldForce);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void hideSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    private void initViews() {
        preferenceManager = new PreferenceManager(this);
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        Bundle b = getIntent().getExtras();
        userName = b.getString("UserName");
        userCode = b.getString("UserCode");
        userRole = b.getString("UserRole");
        //manager_code = b.getString("manager_code");
        //manager_detail = b.getString("manager_detail");
        new_version = b.getString("new_version");
        message_3 = b.getString("message_3");
        globalUserCode = preferenceManager.getAdmin_Code();
        globalUserName = preferenceManager.getAdmin_Name();
        showBtn = findViewById(R.id.showBtn);
        fieldType = findViewById(R.id.fieldType);
        fieldForce = findViewById(R.id.fieldForce);
        genericType = findViewById(R.id.genericType);
        genericName = findViewById(R.id.genericName);
        recyclerPrescriber = findViewById(R.id.recyclerPrescriber);
    }

    private void initFromDateList(List<FromDateList> fromDateLists) {
        MaterialSpinner mFromDate = findViewById(R.id.fromDate);
        ArrayList<String> fromDateList = new ArrayList<>();

        if (fromDateLists.size() > 0) {
            for (FromDateList fromDate: fromDateLists) {
                fromDateList.add(fromDate.getMon());
            }
        }
        mFromDate.setItems(fromDateList);

        mFromDate.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                fromDate = String.valueOf(item).trim();

                for (int i = 0; i < fromDateLists.size(); i++) {
                    if (fromDateLists.get(i).getMon().contains(fromDate)) {
                        fromDateFormat = fromDateLists.get(i).getMnyr();
                        getToDateInfo();
                    }
                }
                Log.d("fromDate", fromDateFormat);
            }
        });
    }

    private void initToDateList(List<FromDateList> toDateLists) {
        MaterialSpinner mToDate = findViewById(R.id.toDate);
        ArrayList<String> toDateList = new ArrayList<>();

        if (toDateLists.size() > 0) {
            for (FromDateList toDate: toDateLists) {
                toDateList.add(toDate.getMon());
            }
        }
        mToDate.setItems(toDateList);

        mToDate.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                toDate = String.valueOf(item).trim();
                for (int i = 0; i < toDateLists.size(); i++) {
                    if (toDateLists.get(i).getMon().contains(toDate)) {
                        toDateFormat = toDateLists.get(i).getMnyr();
                        getGenericTypeInfo();
                    }
                }
                Log.d("toDate", fromDateFormat);
            }
        });
    }

    private void initFieldForceSpinner() {
        List<String> fieldForceList = new ArrayList<String>();

        for (int i = 0; i < fieldForceLists.size(); i++) {
            fieldForceList.add(fieldForceLists.get(i).getName());
        }
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, fieldForceList);
        fieldForce.setThreshold(2);
        fieldForce.setAdapter(Adapter);
        fieldForce.setTextColor(Color.BLUE);
    }

    private void initGenericTypeSpinner() {
        List<String> genericTypeList = new ArrayList<String>();

        for (int i = 0; i < genericTypeLists.size(); i++) {
            genericTypeList.add(genericTypeLists.get(i).getGenDesc());
        }
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, genericTypeList);
        genericType.setThreshold(2);
        genericType.setAdapter(Adapter);
        genericType.setTextColor(Color.BLUE);
    }

    private void initFieldTypeSpinner() {
        switch (userRole) {
            case "AD":
                fieldType.setItems("National", "Division", "Zone", "Region", "Area", "Territory");
                break;
            case "SM":
                fieldType.setItems("Zone", "Region", "Area", "Territory");
                break;
            case "ASM":
                fieldType.setItems("Region", "Area", "Territory");
                break;
            case "RM":
                fieldType.setItems("Area", "Territory");
                break;
            case "FM":
                fieldType.setItems("Territory");
                break;
        }

        fieldType.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                fieldForceValue = String.valueOf(item);
                if (!fieldForceValue.trim().equals("National")) {
                    getFieldForceInfo();
                }
//                if (fieldForceValue.trim().equals("National")) {
//                    passed_manager_code = manager_code;
//                    brand_code="xx";
//                    actv_rm.setText("");
//                    actv_brand_name.setText("");
//                    customerlist.clear();
//                    categoriesList.clear();
//
//                    new PrescriptionFollowup.GetCategories().execute();
//                    postPrescriptionCount();
//                    postSubTotal();
//                } else {
//                    actv_rm.setText("");
//                    actv_brand_name.setText("");
//                    customerlist.clear();
//                    new PrescriptionFollowup.GetList().execute();
//                }
            }
        });
    }

    private void getFromDateInfo() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<FromDateModel> call = apiInterface.getPrescriberFromDate(userCode);
        fromDateLists.clear();
        Log.d("fromDate", userCode);

        call.enqueue(new Callback<FromDateModel>() {
            @Override
            public void onResponse(Call<FromDateModel> call, Response<FromDateModel> response) {
                if (response.isSuccessful()) {
                    fromDateLists = Objects.requireNonNull(response.body()).getFromDateLists();
                    if (fromDateLists.size() > 0) {
                        initFromDateList(fromDateLists);
                    }
                    Log.d("fromDate", String.valueOf(fromDateLists));
                }
            }

            @Override
            public void onFailure(Call<FromDateModel> call, Throwable t) {
                Log.d("fromDate", "Failed to Retrieved Data -- " + t);
                Toast toast = Toast.makeText(getBaseContext(), "Failed to Retrieved Data!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private void getToDateInfo() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<FromDateModel> call = apiInterface.getPrescriberToDate(userCode, fromDateFormat);
        toDateLists.clear();
        Log.d("toDate", userCode);

        call.enqueue(new Callback<FromDateModel>() {
            @Override
            public void onResponse(Call<FromDateModel> call, Response<FromDateModel> response) {
                if (response.isSuccessful()) {
                    toDateLists = Objects.requireNonNull(response.body()).getFromDateLists();
                    if (toDateLists.size() > 0) {
                        initToDateList(toDateLists);
                    }
                    Log.d("toDate", String.valueOf(toDateLists));
                }
            }

            @Override
            public void onFailure(Call<FromDateModel> call, Throwable t) {
                Log.d("toDate", "Failed to Retrieved Data -- " + t);
                Toast toast = Toast.makeText(getBaseContext(), "Failed to Retrieved Data!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private void getFieldForceInfo() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<FieldForceModel> call = apiInterface.getFieldForceCode(fieldForceValue, userCode, userRole);
        fieldForceLists.clear();
        Log.d("fieldForce", userCode);

        call.enqueue(new Callback<FieldForceModel>() {
            @Override
            public void onResponse(Call<FieldForceModel> call, Response<FieldForceModel> response) {
                if (response.isSuccessful()) {
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
                Toast toast = Toast.makeText(getBaseContext(), "Failed to Retrieved Data!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private void getGenericTypeInfo() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<GenericTypeModel> call = apiInterface.getGenericTypeList(userCode, fromDateFormat, toDateFormat);
        genericTypeLists.clear();
        Log.d("genericType", userCode + fromDateFormat + toDateFormat);

        call.enqueue(new Callback<GenericTypeModel>() {
            @Override
            public void onResponse(Call<GenericTypeModel> call, Response<GenericTypeModel> response) {
                if (response.isSuccessful()) {
                    genericTypeLists = Objects.requireNonNull(response.body()).getGenericLists();
                    if (genericTypeLists.size() > 0) {
                        initGenericTypeSpinner();
                    }
                    Log.d("genericType", String.valueOf(genericTypeLists));
                }
            }

            @Override
            public void onFailure(Call<GenericTypeModel> call, Throwable t) {
                Log.d("genericType", "Failed to Retrieved Data -- " + t);
                Toast toast = Toast.makeText(getBaseContext(), "Failed to Retrieved Data!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    public void prescriberDetailsInfo() {
        ProgressDialog prescriberDialog = new ProgressDialog(TopPrescriberActivity.this);
        prescriberDialog.setMessage("Prescriber Details Loading...");
        prescriberDialog.setTitle("Prescriber Details Followup");
        prescriberDialog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<TopPrescriberModel> call = apiInterface.getPrescriberDetailList(userCode, fromDateFormat, toDateFormat, ffCode, genCode);
        Log.d("prescriber", userCode + fromDateFormat + toDateFormat + ffCode + genCode);
        topPrescriberLists.clear();

        call.enqueue(new Callback<TopPrescriberModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<TopPrescriberModel> call, @NonNull retrofit2.Response<TopPrescriberModel> response) {
                List<TopPrescriberList> prescriberData = null;
                if (response.body() != null) {
                    prescriberData = response.body().getPrescriberListList();
                }

                if (response.isSuccessful()) {
                    for (int i = 0; i < (prescriberData != null ? prescriberData.size() : 0); i++) {
                        topPrescriberLists.add(new TopPrescriberList(
                                prescriberData.get(i).getSlno(),
                                prescriberData.get(i).getMpoCode(),
                                prescriberData.get(i).getFmCode(),
                                prescriberData.get(i).getRmCode(),
                                prescriberData.get(i).getAmCode(),
                                prescriberData.get(i).getSmCode(),
                                prescriberData.get(i).getDocCode(),
                                prescriberData.get(i).getDocName(),
                                prescriberData.get(i).getDocSpec(),
                                prescriberData.get(i).getTotalPres(),
                                prescriberData.get(i).getOplPres(),
                                prescriberData.get(i).getTopPres(),
                                prescriberData.get(i).getOplValueShare(),
                                prescriberData.get(i).getTopValueShare()));
                    }
                    prescriberDialog.dismiss();
                    prescriberAdapter = new PrescriberAdapter(TopPrescriberActivity.this, topPrescriberLists);
                    LinearLayoutManager manager = new LinearLayoutManager(TopPrescriberActivity.this);
                    recyclerPrescriber.setLayoutManager(manager);
                    recyclerPrescriber.setAdapter(prescriberAdapter);
                    recyclerPrescriber.addItemDecoration(new DividerItemDecoration(TopPrescriberActivity.this, DividerItemDecoration.VERTICAL));
                    Log.d("prescriber", String.valueOf(topPrescriberLists));
                } else {
                    prescriberDialog.dismiss();
                    Toast.makeText(TopPrescriberActivity.this, "No data Available!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<TopPrescriberModel> call, @NonNull Throwable t) {
                prescriberDialog.dismiss();
            }
        });
    }
}