package com.opl.pharmavector.dcfpFollowup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.opl.pharmavector.R;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;
import com.opl.pharmavector.util.PreferenceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

public class DcfpDoctorListActivity extends Activity implements DcfpDoctorListAdapter1.DcfpClickListener1 {
    int scrollX = 0, scrollY = 0;
    private Context context;
    private TextView firstWeek, doc_d1, doc_d2, doc_d3, doc_d4, doc_d5, doc_d6, doc_d7, doc_d8, doc_d9, doc_d10, doc_d11, doc_d12, doc_d13, doc_d14, doc_d15, doc_d16, doc_d17,
            doc_d18, doc_d19, doc_d20, doc_d21, doc_d22, doc_d23, doc_d24, doc_d25, doc_d26, doc_d27, doc_d28;
    HorizontalScrollView scrollView;
    Button doctorListBtn, backBtn;
    private RecyclerView dcfpListRecycler;
    private DcfpDoctorListAdapter1 dcfpDoctorAdapter1;
    private DcfpDoctorListAdapter2 dcfpDoctorAdapter2;
    AutoCompleteTextView autoDoctorFFList;
    public String userName, userName_2, new_version, message_3, userRole, selectMpoCode;
    PreferenceManager preferenceManager;
    private List<DcfpDoctorMpoList> dcfpDocMpoLists = new ArrayList<>();
    private List<DcfpDoctorReportList> dcfpDoctorLists = new ArrayList<>();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dcfp_doctor_list);

        initViews();

        if (Objects.equals(userRole, "MPO")) {
            getDcfpDoctorListInfo(userName);
        } else {
            getDcfpDoctorMpoList();
        }
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        doctorListBtn.setOnClickListener(v -> {
            if (Objects.equals(userRole, "MPO")) {
                getDcfpDoctorListInfo(userName);
            } else {
                getDcfpDoctorListInfo(selectMpoCode);
            }
        });
        autoDoctorFFList.setOnTouchListener((v, event) -> {
            autoDoctorFFList.showDropDown();
            return false;
        });
        autoDoctorFFList.setOnClickListener(v -> {});
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initViews() {
        context = this;
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        preferenceManager = new PreferenceManager(this);

        Bundle b = getIntent().getExtras();
        userName = b.getString("UserName");
        userName_2 = b.getString("UserName_2");
        new_version = b.getString("new_version");
        message_3 = b.getString("message_3");
        userRole = b.getString("UserRole");
        backBtn = findViewById(R.id.backBtn);
        backBtn.setTypeface(fontFamily);
        backBtn.setText("\uf060 ");
        doctorListBtn = findViewById(R.id.doctorListBtn);
        dcfpListRecycler = findViewById(R.id.recyclerDcfpList);
        autoDoctorFFList = findViewById(R.id.autoDoctorMpoList);

        if (Objects.equals(userRole, "MPO")) {
            autoDoctorFFList.setText(userName);
        }
        scrollView = findViewById(R.id.scrollView);
        doc_d1 = findViewById(R.id.doc_d1);
        doc_d2 = findViewById(R.id.doc_d2);
        doc_d3 = findViewById(R.id.doc_d3);
        doc_d4 = findViewById(R.id.doc_d4);
        doc_d5 = findViewById(R.id.doc_d5);
        doc_d6 = findViewById(R.id.doc_d6);
        doc_d7 = findViewById(R.id.doc_d7);
        doc_d8 = findViewById(R.id.doc_d8);
        doc_d9 = findViewById(R.id.doc_d9);
        doc_d10 = findViewById(R.id.doc_d10);
        doc_d11 = findViewById(R.id.doc_d11);
        doc_d12 = findViewById(R.id.doc_d12);
        doc_d13 = findViewById(R.id.doc_d13);
        doc_d14 = findViewById(R.id.doc_d14);
        doc_d15 = findViewById(R.id.doc_d15);
        doc_d16 = findViewById(R.id.doc_d16);
        doc_d17 = findViewById(R.id.doc_d17);
        doc_d18 = findViewById(R.id.doc_d18);
        doc_d19 = findViewById(R.id.doc_d19);
        doc_d20 = findViewById(R.id.doc_d20);
        doc_d21 = findViewById(R.id.doc_d21);
        doc_d22 = findViewById(R.id.doc_d22);
        doc_d23 = findViewById(R.id.doc_d23);
        doc_d24 = findViewById(R.id.doc_d24);
        doc_d25 = findViewById(R.id.doc_d25);
        doc_d26 = findViewById(R.id.doc_d26);
        doc_d27 = findViewById(R.id.doc_d27);
        doc_d28 = findViewById(R.id.doc_d28);
    }

    private void populateDoctorFFList() {
        List<String> mpoCode = new ArrayList<>();
        for (int i = 0; i < dcfpDocMpoLists.size(); i++) {
            mpoCode.add(dcfpDocMpoLists.get(i).getMpoCode());
        }
        String[] mpoCodeList = mpoCode.toArray(new String[0]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<>(this, R.layout.spinner_text_view, mpoCodeList);
        autoDoctorFFList.setThreshold(1);
        autoDoctorFFList.setAdapter(Adapter);
        autoDoctorFFList.setTextColor(Color.BLUE);

        autoDoctorFFList.setOnItemClickListener((parent, view, position, id) -> {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
            selectMpoCode = (String) parent.getItemAtPosition(position);

            if (selectMpoCode != null) {
                getDcfpDoctorListInfo(selectMpoCode);
            }
        });
    }

    public void getDcfpDoctorListInfo(String mpoCode) {
        ProgressDialog dcfpDoctorDialog = new ProgressDialog(DcfpDoctorListActivity.this);
        dcfpDoctorDialog.setMessage("DCFP Doctor List Loading...");
        dcfpDoctorDialog.setTitle("DCFP Doctor List Followup");
        dcfpDoctorDialog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<DcfpDoctorReportModel> call = apiInterface.getDcfpDoctorList(mpoCode);
        dcfpDoctorLists.clear();

        call.enqueue(new Callback<DcfpDoctorReportModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<DcfpDoctorReportModel> call, @NonNull retrofit2.Response<DcfpDoctorReportModel> response) {
                if (response.body() != null) {
                    dcfpDoctorLists = response.body().getDcfpDoctorLists();
                }
                Log.d("dcfpData", dcfpDoctorLists.toString());

                if (response.isSuccessful()) {
                    LinearLayoutManager manager1 = new LinearLayoutManager(DcfpDoctorListActivity.this);
                    dcfpDoctorAdapter1 = new DcfpDoctorListAdapter1(DcfpDoctorListActivity.this, dcfpDoctorLists, DcfpDoctorListActivity.this);
                    dcfpListRecycler.setLayoutManager(manager1);
                    dcfpListRecycler.setAdapter(dcfpDoctorAdapter1);

                    int d1_count = 0, d2_count = 0, d3_count = 0, d4_count = 0, d5_count = 0, d6_count = 0, d7_count = 0, d8_count = 0, d9_count = 0, d10_count = 0, d11_count = 0,
                        d12_count = 0, d13_count = 0, d14_count = 0, d15_count = 0, d16_count = 0, d17_count = 0, d18_count = 0, d19_count = 0, d20_count = 0, d21_count = 0,
                        d22_count = 0, d23_count = 0, d24_count = 0, d25_count = 0, d26_count = 0, d27_count = 0, d28_count = 0;
                    for (int i=0; i<dcfpDoctorLists.size(); i++) {
                        if (dcfpDoctorLists.get(i).getD1() != null) {
                            d1_count++;
                        }
                        if (dcfpDoctorLists.get(i).getD2() != null) {
                            d2_count++;
                        }
                        if (dcfpDoctorLists.get(i).getD3() != null) {
                            d3_count++;
                        }
                        if (dcfpDoctorLists.get(i).getD4() != null) {
                            d4_count++;
                        }
                        if (dcfpDoctorLists.get(i).getD5() != null) {
                            d5_count++;
                        }
                        if (dcfpDoctorLists.get(i).getD6() != null) {
                            d6_count++;
                        }
                        if (dcfpDoctorLists.get(i).getD7() != null) {
                            d7_count++;
                        }
                        if (dcfpDoctorLists.get(i).getD8() != null) {
                            d8_count++;
                        }
                        if (dcfpDoctorLists.get(i).getD9() != null) {
                            d9_count++;
                        }
                        if (dcfpDoctorLists.get(i).getD10() != null) {
                            d10_count++;
                        }
                        if (dcfpDoctorLists.get(i).getD11() != null) {
                            d11_count++;
                        }
                        if (dcfpDoctorLists.get(i).getD12() != null) {
                            d12_count++;
                        }
                        if (dcfpDoctorLists.get(i).getD13() != null) {
                            d13_count++;
                        }
                        if (dcfpDoctorLists.get(i).getD14() != null) {
                            d14_count++;
                        }
                        if (dcfpDoctorLists.get(i).getD15() != null) {
                            d15_count++;
                        }
                        if (dcfpDoctorLists.get(i).getD16() != null) {
                            d16_count++;
                        }
                        if (dcfpDoctorLists.get(i).getD17() != null) {
                            d17_count++;
                        }
                        if (dcfpDoctorLists.get(i).getD18() != null) {
                            d18_count++;
                        }
                        if (dcfpDoctorLists.get(i).getD19() != null) {
                            d19_count++;
                        }
                        if (dcfpDoctorLists.get(i).getD20() != null) {
                            d20_count++;
                        }
                        if (dcfpDoctorLists.get(i).getD21() != null) {
                            d21_count++;
                        }
                        if (dcfpDoctorLists.get(i).getD22() != null) {
                            d22_count++;
                        }
                        if (dcfpDoctorLists.get(i).getD23() != null) {
                            d23_count++;
                        }
                        if (dcfpDoctorLists.get(i).getD24() != null) {
                            d24_count++;
                        }
                        if (dcfpDoctorLists.get(i).getD25() != null) {
                            d25_count++;
                        }
                        if (dcfpDoctorLists.get(i).getD26() != null) {
                            d26_count++;
                        }
                        if (dcfpDoctorLists.get(i).getD27() != null) {
                            d27_count++;
                        }
                        if (dcfpDoctorLists.get(i).getD28() != null) {
                            d28_count++;
                        }
                    }
                    doc_d1.setText(String.valueOf(d1_count));
                    doc_d2.setText(String.valueOf(d2_count));
                    doc_d3.setText(String.valueOf(d3_count));
                    doc_d4.setText(String.valueOf(d4_count));
                    doc_d5.setText(String.valueOf(d5_count));
                    doc_d6.setText(String.valueOf(d6_count));
                    doc_d7.setText(String.valueOf(d7_count));
                    doc_d8.setText(String.valueOf(d8_count));
                    doc_d9.setText(String.valueOf(d9_count));
                    doc_d10.setText(String.valueOf(d10_count));
                    doc_d11.setText(String.valueOf(d11_count));
                    doc_d12.setText(String.valueOf(d12_count));
                    doc_d13.setText(String.valueOf(d13_count));
                    doc_d14.setText(String.valueOf(d14_count));
                    doc_d15.setText(String.valueOf(d15_count));
                    doc_d16.setText(String.valueOf(d16_count));
                    doc_d17.setText(String.valueOf(d17_count));
                    doc_d18.setText(String.valueOf(d18_count));
                    doc_d19.setText(String.valueOf(d19_count));
                    doc_d20.setText(String.valueOf(d20_count));
                    doc_d21.setText(String.valueOf(d21_count));
                    doc_d22.setText(String.valueOf(d22_count));
                    doc_d23.setText(String.valueOf(d23_count));
                    doc_d24.setText(String.valueOf(d24_count));
                    doc_d25.setText(String.valueOf(d25_count));
                    doc_d26.setText(String.valueOf(d26_count));
                    doc_d27.setText(String.valueOf(d27_count));
                    doc_d28.setText(String.valueOf(d28_count));
                    dcfpDoctorDialog.dismiss();
                    Log.d("d1_count", String.valueOf(d1_count));
                } else {
                    dcfpDoctorDialog.dismiss();
                    Toast.makeText(DcfpDoctorListActivity.this, "No data Available !", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<DcfpDoctorReportModel> call, @NonNull Throwable t) {
                dcfpDoctorDialog.dismiss();
            }
        });
    }

    public void getDcfpDoctorMpoList() {
        ProgressDialog dcfpDoctorDialog = new ProgressDialog(DcfpDoctorListActivity.this);
        dcfpDoctorDialog.setMessage("DCFP MPO List Loading...");
        dcfpDoctorDialog.setTitle("DCFP MPO List Followup");
        dcfpDoctorDialog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<DcfpDoctorMpoModel> call = apiInterface.getDcfpDocMpoList(userName);
        dcfpDocMpoLists.clear();

        call.enqueue(new Callback<DcfpDoctorMpoModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<DcfpDoctorMpoModel> call, @NonNull retrofit2.Response<DcfpDoctorMpoModel> response) {
                if (response.body() != null) {
                    dcfpDocMpoLists = response.body().getDcfpDoctorMpoLists();
                }
                Log.d("dcfpMpo", dcfpDocMpoLists.toString());

                if (response.isSuccessful()) {
                    populateDoctorFFList();
                    dcfpDoctorDialog.dismiss();
                    //Log.d("d1_count", String.valueOf(d1_count));
                } else {
                    dcfpDoctorDialog.dismiss();
                    Toast.makeText(DcfpDoctorListActivity.this, "No data Available !", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<DcfpDoctorMpoModel> call, @NonNull Throwable t) {
                dcfpDoctorDialog.dismiss();
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onDcfpClick1(int position, DcfpDoctorReportList model, int d1_count) {
        scrollX = position;
        dcfpListRecycler.post(new Runnable() {
            @Override
            public void run() {
                dcfpListRecycler.scrollToPosition(position);
            }
        });
        dcfpDoctorAdapter2.notifyDataSetChanged();
    }
}