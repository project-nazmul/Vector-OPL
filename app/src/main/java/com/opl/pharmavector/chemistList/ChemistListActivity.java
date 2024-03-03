package com.opl.pharmavector.chemistList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.opl.pharmavector.R;
import com.opl.pharmavector.chemistList.adapter.ChemistAdapter;
import com.opl.pharmavector.chemistList.model.ChemistList;
import com.opl.pharmavector.chemistList.model.ChemistModel;
import com.opl.pharmavector.chemistList.model.ContactModel;
import com.opl.pharmavector.doctorList.model.DoctorFFList;
import com.opl.pharmavector.doctorList.model.DoctorFFModel;
import com.opl.pharmavector.doctorList.model.DoctorList;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;
import com.opl.pharmavector.util.PreferenceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

public class ChemistListActivity extends Activity implements ChemistAdapter.ContactCallback {
    public int count;
    public Context context;
    public TextView customerCount;
    public String userName, userCode, terriCode;
    PreferenceManager preferenceManager;
    public ProgressDialog doctorFFDialog, doctorDialog;
    AutoCompleteTextView autoDoctorFFList, autoDoctorTerriList;
    Button doctorListBtn, backBtn;
    private ChemistAdapter chemistAdapter;
    private RecyclerView doctorRecycler;
    private ArrayList<DoctorFFList> doctorFFList = new ArrayList<>();
    private ArrayList<DoctorList> doctorList = new ArrayList<>();
    String selectedMpoName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemist_list);

        initViews();
        chemistFFListInfo();

        doctorListBtn.setOnClickListener(v -> {
            if (!selectedMpoName.isEmpty()) {
                chemistDetailsListInfo();
            } else {
                Toast.makeText(ChemistListActivity.this, "Please select Mpo code!", Toast.LENGTH_LONG).show();
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
        userCode = b.getString("UserCode");
        terriCode = b.getString("TerriCode");

        backBtn = findViewById(R.id.backBtn);
        backBtn.setTypeface(fontFamily);
        backBtn.setText("\uf060 ");

        customerCount = findViewById(R.id.customerCount);
        doctorListBtn = findViewById(R.id.doctorListBtn);
        doctorRecycler = findViewById(R.id.recyclerDoctorList);
        autoDoctorFFList = findViewById(R.id.autoDoctorMpoList);

        autoDoctorFFList.setOnTouchListener((v, event) -> {
            autoDoctorFFList.showDropDown();
            return false;
        });
        autoDoctorFFList.setOnClickListener(v -> {

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
                    //KeyboardUtils.hideKeyboard(DoctorListActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void populateDoctorFFList() {
        List<String> mpoCode = new ArrayList<>();
        for (int i = 0; i < doctorFFList.size(); i++) {
            mpoCode.add(doctorFFList.get(i).getMpoCode() + " - " + doctorFFList.get(i).getTerriName());
        }
        String[] mpoCodeList = mpoCode.toArray(new String[0]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<>(this, R.layout.spinner_text_view, mpoCodeList);
        autoDoctorFFList.setThreshold(1);
        autoDoctorFFList.setAdapter(Adapter);
        autoDoctorFFList.setTextColor(Color.BLUE);

        autoDoctorFFList.setOnItemClickListener((parent, view, position, id) -> {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);

            String selectedItem = (String) parent.getItemAtPosition(position);
            String[] selectedFFType = selectedItem.split("-");
            if (selectedFFType.length > 0) {
                selectedMpoName = selectedFFType[0].trim();
            }
        });
    }

    public void chemistFFListInfo() {
        doctorFFDialog = new ProgressDialog(ChemistListActivity.this);
        doctorFFDialog.setMessage("Chemist List Loading...");
        doctorFFDialog.setTitle("Chemist List Followup");
        doctorFFDialog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<DoctorFFModel> call = apiInterface.getDoctorFFList(userCode);
        doctorFFList.clear();

        call.enqueue(new Callback<DoctorFFModel>() {
            @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
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
                    Toast.makeText(ChemistListActivity.this, "No data Available", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<DoctorFFModel> call, @NonNull Throwable t) {
                doctorFFDialog.dismiss();
                //doctorFFListInfo();
            }
        });
    }

    public void chemistDetailsListInfo() {
        doctorDialog = new ProgressDialog(ChemistListActivity.this);
        doctorDialog.setMessage("Chemist Details Loading...");
        doctorDialog.setTitle("Chemist Details Followup");
        doctorDialog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ChemistModel> call = apiInterface.getChemistDetailsList(selectedMpoName);
        doctorList.clear();

        call.enqueue(new Callback<ChemistModel>() {
            @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
            @Override
            public void onResponse(@NonNull Call<ChemistModel> call, @NonNull retrofit2.Response<ChemistModel> response) {
                List<ChemistList> chemistLists = null;
                if (response.body() != null) {
                    chemistLists = response.body().getChemistLists();
                }

                if (response.isSuccessful()) {
                    doctorDialog.dismiss();
                    chemistAdapter = new ChemistAdapter(ChemistListActivity.this, chemistLists, ChemistListActivity.this);
                    LinearLayoutManager manager = new LinearLayoutManager(ChemistListActivity.this);
                    doctorRecycler.setLayoutManager(manager);
                    doctorRecycler.setAdapter(chemistAdapter);
                    doctorRecycler.addItemDecoration(new DividerItemDecoration(ChemistListActivity.this, DividerItemDecoration.VERTICAL));
                    //Log.d("company List", companyDataList.get(0).getComDesc());
                } else {
                    doctorDialog.dismiss();
                    Toast.makeText(ChemistListActivity.this, "No data Available", Toast.LENGTH_LONG).show();
                }

                if (chemistLists != null && chemistLists.size() > 0) {
                    customerCount.setText("No of Customer : " + chemistLists.size());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ChemistModel> call, @NonNull Throwable t) {
                doctorDialog.dismiss();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onContactPhone(ChemistList chemistList) {
        if (chemistList != null) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(ChemistListActivity.this);
            final View customLayout = getLayoutInflater().inflate(R.layout.dialog_contact_update, null);
            alertDialog.setView(customLayout);
            AlertDialog alert = alertDialog.create();
            alert.setCancelable(false);
            alert.setCanceledOnTouchOutside(false);

            TextView chemistCode = customLayout.findViewById(R.id.chemistCode);
            TextView chemistName = customLayout.findViewById(R.id.chemistName);
            Button contactUpdate = customLayout.findViewById(R.id.contactUpdate);
            Button contactCancel = customLayout.findViewById(R.id.contactCancel);
            EditText phoneNumber = customLayout.findViewById(R.id.phoneNumber);

            if (chemistList.getCustCode() != null) {
                chemistCode.setText("[" + chemistList.getCustCode() + "]");
            }
            if (chemistList.getCustName() != null) {
                chemistName.setText(chemistList.getCustName());
            }

            contactUpdate.setOnClickListener(v -> {
                String contactNumber = phoneNumber.getText().toString().trim();

                if (!contactNumber.equals("")) {
                    alert.dismiss();
                    updateChemistContactNo(chemistList.getCustCode(), contactNumber);
                } else {
                    alert.dismiss();
                }
            });

            contactCancel.setOnClickListener(v -> {
                alert.dismiss();
            });
            alert.show();
        }
    }

    public void updateChemistContactNo(String custCode, String contactNumber) {
        ProgressDialog contactDialog = new ProgressDialog(ChemistListActivity.this);
        contactDialog.setMessage("Chemist Contact Update...");
        contactDialog.setTitle("Chemist Contact Number");
        contactDialog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ContactModel> call = apiInterface.updateChemistNumber(terriCode, userCode, custCode, contactNumber);

        call.enqueue(new Callback<ContactModel>() {
            @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
            @Override
            public void onResponse(@NonNull Call<ContactModel> call, @NonNull retrofit2.Response<ContactModel> response) {
                if (response.isSuccessful()) {
                    contactDialog.dismiss();

                    if (response.body() != null) {
                        Toast.makeText(ChemistListActivity.this, "" + response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                    //Log.d("tourNature: ", String.valueOf(tourNatureLists));
                } else {
                    contactDialog.dismiss();
                    Toast.makeText(ChemistListActivity.this, "" + Objects.requireNonNull(response.body()).getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ContactModel> call, @NonNull Throwable t) {
                contactDialog.dismiss();
            }
        });
    }
}