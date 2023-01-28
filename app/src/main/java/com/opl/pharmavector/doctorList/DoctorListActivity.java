package com.opl.pharmavector.doctorList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.opl.pharmavector.R;
import com.opl.pharmavector.doctorList.adapter.DoctorAdapter;
import com.opl.pharmavector.doctorList.model.DoctorFFList;
import com.opl.pharmavector.doctorList.model.DoctorFFModel;
import com.opl.pharmavector.doctorList.model.DoctorList;
import com.opl.pharmavector.doctorList.model.DoctorModel;
import com.opl.pharmavector.pmdVector.adapter.BrandAdapter;
import com.opl.pharmavector.pmdVector.sales_4p.Activity_4p_Sales;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;
import com.opl.pharmavector.util.KeyboardUtils;
import com.opl.pharmavector.util.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class DoctorListActivity extends Activity {
    private int count;
    private Context context;
    public String userName, userName_2, new_version, message_3;
    PreferenceManager preferenceManager;
    public ProgressDialog doctorFFDialog, doctorDialog;
    AutoCompleteTextView autoDoctorFFList, autoDoctorTerriList;
    Button doctorListBtn, backBtn;
    private DoctorAdapter doctorAdapter;
    private RecyclerView doctorRecycler;
    private ArrayList<DoctorFFList> doctorFFList = new ArrayList<>();
    private ArrayList<DoctorList> doctorList = new ArrayList<>();
    String selectedMpoName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);

        initViews();
        doctorFFListInfo();

        doctorListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selectedMpoName.isEmpty()) {
                    doctorDetailsListInfo();
                } else {
                    Toast.makeText(DoctorListActivity.this, "Please select Mpo code!", Toast.LENGTH_LONG).show();
                }
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initViews() {
        context = this;
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        preferenceManager = new PreferenceManager(this);
        count = preferenceManager.getTasbihCounter();

        Bundle b = getIntent().getExtras();
        userName = b.getString("UserName");
        userName_2 = b.getString("UserName_2");
        new_version = b.getString("new_version");
        message_3 = b.getString("message_3");

        backBtn = findViewById(R.id.backBtn);
        backBtn.setTypeface(fontFamily);
        backBtn.setText("\uf060 ");

        doctorListBtn = findViewById(R.id.doctorListBtn);
        doctorRecycler = findViewById(R.id.recyclerDoctorList);
        autoDoctorFFList = findViewById(R.id.autoDoctorMpoList);

        autoDoctorFFList.setOnTouchListener((v, event) -> {
            autoDoctorFFList.showDropDown();
            return false;
        });
        autoDoctorFFList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        autoDoctorFFList.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                autoDoctorFFList.setTextColor(Color.BLUE);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                autoDoctorFFList.setTextColor(Color.GREEN);
            }

            @Override
            public void afterTextChanged(final Editable s) {
                try {
                    final String mpoCode = s.toString();
                    //autoDoctorFFList.setText(mpoCode);
                    KeyboardUtils.hideKeyboard(DoctorListActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void populateDoctorFFList() {
        List<String> mpoCode = new ArrayList<String>();
        for (int i = 0; i < doctorFFList.size(); i++) {
            mpoCode.add(doctorFFList.get(i).getMpoCode() + " - " + doctorFFList.get(i).getTerriName());
        }
        String[] mpoCodeList = mpoCode.toArray(new String[0]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, mpoCodeList);
        autoDoctorFFList.setThreshold(2);
        autoDoctorFFList.setAdapter(Adapter);
        autoDoctorFFList.setTextColor(Color.BLUE);

        autoDoctorFFList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                String[] selectedFFType = selectedItem.split("-");
                if (selectedFFType.length > 0) {
                    selectedMpoName = selectedFFType[0].trim();
                }
            }
        });
    }

    public void doctorFFListInfo() {
        doctorFFDialog = new ProgressDialog(DoctorListActivity.this);
        doctorFFDialog.setMessage("Doctor List Loading...");
        doctorFFDialog.setTitle("Doctor List Followup");
        doctorFFDialog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<DoctorFFModel> call = apiInterface.getDoctorFFList(userName);
        doctorFFList.clear();

        call.enqueue(new Callback<DoctorFFModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<DoctorFFModel> call, @NonNull retrofit2.Response<DoctorFFModel> response) {
                List<DoctorFFList> doctorFFData = null;
                if (response.body() != null) {
                    doctorFFData = response.body().getCategoriesList();
                }

                if (response.isSuccessful()) {
                    for (int i = 0; i < (doctorFFData != null ? doctorFFData.size() : 0); i++) {
                        doctorFFList.add(new DoctorFFList(
                                doctorFFData.get(i).getMpoCode(),
                                doctorFFData.get(i).getTerriName()));
                    }
                    doctorFFDialog.dismiss();
                    populateDoctorFFList();
                    //Log.d("company List", companyDatalist.get(0).getComDesc());
                } else {
                    doctorFFDialog.dismiss();
                    Toast.makeText(DoctorListActivity.this, "No data Available", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<DoctorFFModel> call, @NonNull Throwable t) {
                doctorFFDialog.dismiss();
                doctorFFListInfo();
            }
        });
    }

    public void doctorDetailsListInfo() {
        doctorDialog = new ProgressDialog(DoctorListActivity.this);
        doctorDialog.setMessage("Doctor Details Loading...");
        doctorDialog.setTitle("Doctor Details Followup");
        doctorDialog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<DoctorModel> call = apiInterface.getDoctorDetailsList(selectedMpoName);
        doctorList.clear();

        call.enqueue(new Callback<DoctorModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<DoctorModel> call, @NonNull retrofit2.Response<DoctorModel> response) {
                List<DoctorList> doctorData = null;
                if (response.body() != null) {
                    doctorData = response.body().getDoctorList();
                }

                if (response.isSuccessful()) {
                    for (int i = 0; i < (doctorData != null ? doctorData.size() : 0); i++) {
                        doctorList.add(new DoctorList(
                                doctorData.get(i).getDocCode(),
                                doctorData.get(i).getDocName(),
                                doctorData.get(i).getDegree(),
                                doctorData.get(i).getDesig(),
                                doctorData.get(i).getSpecialization(),
                                doctorData.get(i).getMarketName(),
                                doctorData.get(i).getMarketCode(),
                                doctorData.get(i).getAddress(),
                                doctorData.get(i).getPatientPerDay()));
                    }
                    doctorDialog.dismiss();
                    doctorAdapter = new DoctorAdapter(DoctorListActivity.this, doctorList);
                    LinearLayoutManager manager = new LinearLayoutManager(DoctorListActivity.this);
                    doctorRecycler.setLayoutManager(manager);
                    doctorRecycler.setAdapter(doctorAdapter);
                    doctorRecycler.addItemDecoration(new DividerItemDecoration(DoctorListActivity.this, DividerItemDecoration.VERTICAL));
                    //Log.d("company List", companyDatalist.get(0).getComDesc());
                } else {
                    doctorDialog.dismiss();
                    Toast.makeText(DoctorListActivity.this, "No data Available", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<DoctorModel> call, @NonNull Throwable t) {
                doctorDialog.dismiss();
                doctorDetailsListInfo();
            }
        });
    }
}