package com.opl.pharmavector.dcfpFollowup;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

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
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.opl.pharmavector.JSONParser;
import com.opl.pharmavector.R;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;

public class MPODcfpEntryActivity extends Activity implements DcfpEntrySetUpAdapter.ItemClickListener {
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    TextView tvfromdate, tvtodate, entryCounter;
    Button backBtn, submitBtn, showBtn;
    Calendar c_todate, c_fromdate;
    SimpleDateFormat dftodate, dffromdate;
    String current_todate, current_fromdate, ff_code, toDate, fromDate;
    Calendar myCalendar, myCalendar1;
    private RecyclerView dcfpSetUpRecycler;
    public String userName, userName_2, new_version, message_3, message;
    DatePickerDialog.OnDateSetListener date_form, date_to;
    AutoCompleteTextView autoDoctorFFList, autoDoctorTerriList;
    String selectedDocName = "";
    String selectedDocCode = "";
    public int success;
    Toast toast1, toast2;
    JSONParser jsonParser = new JSONParser();
    List<NameValuePair> params = new ArrayList<NameValuePair>();
    public ProgressDialog setUpDialog, doctorDialog;
    private ArrayList<DcfpEntrySetUpList> dcfpSetUpList = new ArrayList<>();
    private ArrayList<DcfpEntryDoctorList> dcfpDoctorList = new ArrayList<>();
    private ArrayList<DcfpEntrySetUpList> selectedItemList = new ArrayList<>();
    private DcfpEntrySetUpAdapter dcfpEntrySetUpAdapter;
    public static String DCFP_SUBMIT_URL = BASE_URL+"dcfp/submit_dcfp.php";

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
        showBtn.setOnClickListener(v -> {
            if (!selectedDocName.isEmpty()) {
                dcfpSetUpListInfo();
            } else {
                Toast.makeText(MPODcfpEntryActivity.this, "Please select Doctor!", Toast.LENGTH_LONG).show();
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initViews() {
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        Bundle b = getIntent().getExtras();
        userName = b.getString("UserName");
        userName_2 = b.getString("UserName_2");
        new_version = b.getString("new_version");
        message_3 = b.getString("message_3");
        backBtn = findViewById(R.id.backBtn);
        backBtn.setTypeface(fontFamily);
        backBtn.setText("\uf060");
        submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setTypeface(fontFamily);
        submitBtn.setText("\uf1d8"); // &#xf1d8
        showBtn = findViewById(R.id.showBtn);
        entryCounter = findViewById(R.id.entryCounter);
        dcfpSetUpRecycler = findViewById(R.id.recyclerSetUpList);
        autoDoctorFFList = findViewById(R.id.autoDoctorMpoList);
        toast1 = Toast.makeText(MPODcfpEntryActivity.this, "Order submit Successfully!", Toast.LENGTH_SHORT);
        toast2 = Toast.makeText(MPODcfpEntryActivity.this, "Error, please try again!", Toast.LENGTH_SHORT);

        submitBtn.setOnClickListener(v -> {
            if (selectedItemList.size() > 0) {
                for (int i = 0; i < selectedItemList.size(); i++) {
                        params.add(new BasicNameValuePair("TP_WEEK" + String.valueOf(i+1), selectedItemList.get(i).getTpWeek()));
                        params.add(new BasicNameValuePair("TP_DAY" + String.valueOf(i+1), selectedItemList.get(i).getTpDay()));
                        params.add(new BasicNameValuePair("TP_TYPE" + String.valueOf(i+1), selectedItemList.get(i).getTpType()));
                    }
                params.add(new BasicNameValuePair("id", userName));
                params.add(new BasicNameValuePair("TOTAL_REC", String.valueOf(selectedItemList.size())));
                params.add(new BasicNameValuePair("DOC_CODE", selectedDocCode));

                final ProgressDialog progress = ProgressDialog.show(this, "Submit Data", "Please Wait..", true);
                Thread server = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject json = jsonParser.makeHttpRequest(DCFP_SUBMIT_URL, "POST", params);
                        progress.dismiss();
                        Log.d("shiftParam", params.toString());

                        try {
                            success = json.getInt(TAG_SUCCESS);
                            message = json.getString(TAG_MESSAGE);

                            if (success == 1) {
                                selectedItemList.clear();
                                runOnUiThread(() -> {
                                    Toast.makeText(MPODcfpEntryActivity.this, message, Toast.LENGTH_SHORT).show();
                                    dcfpSetUpListInfo();
                                });
                            } else {
                                runOnUiThread(() -> {
                                    Toast.makeText(MPODcfpEntryActivity.this, message, Toast.LENGTH_SHORT).show();
                                    //entryCounter.setText("-");
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                server.start();
            } else {
                Toast.makeText(MPODcfpEntryActivity.this, "This plan already in the List, please select another dcfp plan!", Toast.LENGTH_LONG).show();
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
            String[] selectedDocInfo = selectedItem.split("//");
            if (selectedDocInfo.length > 0) {
                selectedDocName = selectedDocInfo[0].trim();
                selectedDocCode = selectedDocInfo[1].trim();
            }
            if (!selectedDocName.isEmpty()) {
                dcfpSetUpListInfo();
            } else {
                Toast.makeText(MPODcfpEntryActivity.this, "Please select Doctor!", Toast.LENGTH_LONG).show();
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
                } else {
                    setUpDialog.dismiss();
                    Toast.makeText(MPODcfpEntryActivity.this, "No data Available!", Toast.LENGTH_LONG).show();
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
        Call<DcfpEntrySetUpModel> call = apiInterface.getDcfpEntrySetUpList(userName, selectedDocCode);
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
                                dcfpSetUpData.get(i).getTpDay(),
                                dcfpSetUpData.get(i).getTpType(),
                                dcfpSetUpData.get(i).getUpdStat()));
                    }
                    setUpDialog.dismiss();
                    dcfpEntrySetUpAdapter = new DcfpEntrySetUpAdapter(MPODcfpEntryActivity.this, dcfpSetUpList, MPODcfpEntryActivity.this);
                    LinearLayoutManager manager = new LinearLayoutManager(MPODcfpEntryActivity.this);
                    dcfpSetUpRecycler.setLayoutManager(manager);
                    dcfpSetUpRecycler.setAdapter(dcfpEntrySetUpAdapter);
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(int position, ArrayList<DcfpEntrySetUpList> model) {
        List<DcfpEntrySetUpList> itemList = model.stream().distinct().collect(Collectors.toList());
        selectedItemList = (ArrayList<DcfpEntrySetUpList>) itemList;
        entryCounter.setText("Total Count: " + itemList.size());
        Log.d("shiftFinal", model.toString() + model.size());
        Log.d("shiftFinal", itemList.toString() + itemList.size());
    }
}
