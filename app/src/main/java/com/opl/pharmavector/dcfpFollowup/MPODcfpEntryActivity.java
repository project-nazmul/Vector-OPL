package com.opl.pharmavector.dcfpFollowup;

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

import com.opl.pharmavector.R;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MPODcfpEntryActivity extends Activity {
    TextView tvfromdate, tvtodate;
    Button backBtn, submitBtn, doctorListBtn;
    Calendar c_todate, c_fromdate;
    SimpleDateFormat dftodate, dffromdate;
    String current_todate, current_fromdate, ff_code, toDate, fromDate;
    Calendar myCalendar, myCalendar1;
    private RecyclerView dcfpSetUpRecycler;
    public String userName, userName_2, new_version, message_3;
    DatePickerDialog.OnDateSetListener date_form, date_to;
    AutoCompleteTextView autoDoctorFFList, autoDoctorTerriList;
    String selectedDocName = "";
    public ProgressDialog setUpDialog, doctorDialog;
    private ArrayList<DcfpEntrySetUpList> dcfpSetUpList = new ArrayList<>();
    private ArrayList<DcfpEntryDoctorList> dcfpDoctorList = new ArrayList<>();
    private DcfpEntrySetUpAdapter dcfpEntrySetUpAdapter;
    //private ArrayList<DcrFollowupModel> dcfpFollowupList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpo_dcfp_entry);

        initViews();
        dcfpDoctorListInfo();
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        doctorListBtn.setOnClickListener(v -> {
            if (!selectedDocName.isEmpty()) {
                dcfpSetUpListInfo();
            } else {
                Toast.makeText(MPODcfpEntryActivity.this, "Please select Doctor!", Toast.LENGTH_LONG).show();
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initViews() {
        //context = this;
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        //preferenceManager = new PreferenceManager(this);
        //count = preferenceManager.getTasbihCounter();

        Bundle b = getIntent().getExtras();
        userName = b.getString("UserName");
        userName_2 = b.getString("UserName_2");
        new_version = b.getString("new_version");
        message_3 = b.getString("message_3");

        backBtn = findViewById(R.id.backBtn);
        backBtn.setTypeface(fontFamily);
        backBtn.setText("\uf060");

        doctorListBtn = findViewById(R.id.doctorListBtn);
        dcfpSetUpRecycler = findViewById(R.id.recyclerSetUpList);
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

    private void populateDcfpDoctorList() {
        List<String> mpoCode = new ArrayList<>();
        for (int i = 0; i < dcfpDoctorList.size(); i++) {
            mpoCode.add(dcfpDoctorList.get(i).getName());
        }
        String[] mpoCodeList = mpoCode.toArray(new String[0]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<>(this, R.layout.spinner_text_view, mpoCodeList);
        autoDoctorFFList.setThreshold(2);
        autoDoctorFFList.setAdapter(Adapter);
        autoDoctorFFList.setTextColor(Color.BLUE);

        autoDoctorFFList.setOnItemClickListener((parent, view, position, id) -> {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);

            String selectedItem = (String) parent.getItemAtPosition(position);
            String[] selectedDocName = selectedItem.split("//");
            if (selectedDocName.length > 0) {
                this.selectedDocName = selectedDocName[0].trim();
            }
        });
    }

    public void dcfpDoctorListInfo() {
        setUpDialog = new ProgressDialog(MPODcfpEntryActivity.this);
        setUpDialog.setMessage("Doctor List Loading...");
        setUpDialog.setTitle("Doctor List Followup");
        setUpDialog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<DcfpEntryDoctorModel> call = apiInterface.getDcfpEntryDoctorList(userName);
        dcfpDoctorList.clear();

        call.enqueue(new Callback<DcfpEntryDoctorModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<DcfpEntryDoctorModel> call, @NonNull retrofit2.Response<DcfpEntryDoctorModel> response) {
                List<DcfpEntryDoctorList> dcfpDoctorData = null;
                if (response.body() != null) {
                    dcfpDoctorData = response.body().getDoctorLists();
                }

                if (response.isSuccessful()) {
                    for (int i = 0; i < (dcfpDoctorData != null ? dcfpDoctorData.size() : 0); i++) {
                        dcfpDoctorList.add(new DcfpEntryDoctorList(
                                dcfpDoctorData.get(i).getName()));
                    }
                    setUpDialog.dismiss();
                    populateDcfpDoctorList();
                    //Log.d("company List", companyDatalist.get(0).getComDesc());
                } else {
                    setUpDialog.dismiss();
                    Toast.makeText(MPODcfpEntryActivity.this, "No data Available", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<DcfpEntryDoctorModel> call, @NonNull Throwable t) {
                setUpDialog.dismiss();
                dcfpDoctorListInfo();
            }
        });
    }

    public void dcfpSetUpListInfo() {
        setUpDialog = new ProgressDialog(MPODcfpEntryActivity.this);
        setUpDialog.setMessage("Dcfp Entry List Loading...");
        setUpDialog.setTitle("Dcfp Entry List Followup");
        setUpDialog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<DcfpEntrySetUpModel> call = apiInterface.getDcfpEntrySetUpList();
        dcfpSetUpList.clear();

        call.enqueue(new Callback<DcfpEntrySetUpModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<DcfpEntrySetUpModel> call, @NonNull retrofit2.Response<DcfpEntrySetUpModel> response) {
                List<DcfpEntrySetUpList> dcfpSetUpData = null;
                if (response.body() != null) {
                    dcfpSetUpData = response.body().getSetUpLists();
                }

                if (response.isSuccessful()) {
                    for (int i = 0; i < (dcfpSetUpData != null ? dcfpSetUpData.size() : 0); i++) {
                        dcfpSetUpList.add(new DcfpEntrySetUpList(
                                dcfpSetUpData.get(i).getSlno(),
                                dcfpSetUpData.get(i).getTpWeek(),
                                dcfpSetUpData.get(i).getTpDay()));
                    }
                    setUpDialog.dismiss();
                    dcfpEntrySetUpAdapter = new DcfpEntrySetUpAdapter(MPODcfpEntryActivity.this, dcfpSetUpList);
                    LinearLayoutManager manager = new LinearLayoutManager(MPODcfpEntryActivity.this);
                    dcfpSetUpRecycler.setLayoutManager(manager);
                    dcfpSetUpRecycler.setAdapter(dcfpEntrySetUpAdapter);
                    //dcfpSetUpRecycler.addItemDecoration(new DividerItemDecoration(MPODcfpEntryActivity.this, DividerItemDecoration.VERTICAL));
                    //Log.d("company List", companyDatalist.get(0).getComDesc());
                } else {
                    setUpDialog.dismiss();
                    Toast.makeText(MPODcfpEntryActivity.this, "No data Available!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<DcfpEntrySetUpModel> call, @NonNull Throwable t) {
                setUpDialog.dismiss();
                dcfpDoctorListInfo();
            }
        });
    }
}
