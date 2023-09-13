package com.opl.pharmavector.dcfpFollowup;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.opl.pharmavector.R;
import com.opl.pharmavector.achieve.AchieveMonthList;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;
import com.opl.pharmavector.util.KeyboardUtils;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

public class MpoDcfpFollowActivity extends Activity {
    TextView date2, ded, fromdate, todate;
    Button submit, back;
    String userName, areaCode, areaName, segmentCode ="XX", segmentName, dcfpMpoCode, dcfpMpoName, mpoSegmentInfo;
    List<MpoDcfpFollowList> mpoDcfpFollowLists;
    List<DcfpFollowMpoList> mpoDcfpSegmentLists;
    List<DcfpFollowMpoList> dcfpFollowMpoLists;
    RecyclerView recyclerMpoDcfp;
    MpoDcfpFollowAdapter mpoDcfpFollowAdapter;
    AutoCompleteTextView autoDcfpMpoFollow;
    MaterialSpinner segmentSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpo_dcfp_follow);

        initViews();
        getDcfpFollowMpoList();
        autoDcfpFollowEvents();
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
        todate.setOnClickListener(v -> new DatePickerDialog(MpoDcfpFollowActivity.this, date_to, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());

        submit.setOnClickListener(v -> {
            getDcfpMpoFollowupList();
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
        submit = findViewById(R.id.submitBtn);
        segmentSpinner = findViewById(R.id.segmentSpinner);
        segmentSpinner.setText(segmentCode);
        recyclerMpoDcfp = findViewById(R.id.recyclerMpoDcfp);
        autoDcfpMpoFollow = findViewById(R.id.autoDcfpMpoList);
        Calendar c_todate = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dftodate = new SimpleDateFormat("dd/MM/yyyy");
        String current_todate = dftodate.format(c_todate.getTime());
        todate.setText(current_todate);
        back.setTypeface(fontFamily);
        back.setText("\uf060 ");

        Bundle bundle = getIntent().getExtras();
        areaCode = b.getString("UserName");
        areaName = b.getString("UserName_2");
    }

    private void initMpoSegmentSpinner(List<DcfpFollowMpoList> mpoSegmentLists) {
        ArrayList<String> mpoSegmentList = new ArrayList<>();
        if (mpoSegmentLists.size() > 0) {
            for (DcfpFollowMpoList segmentName : mpoSegmentLists) {
                mpoSegmentList.add(segmentName.getName());
            }
        }
        segmentSpinner.setItems(mpoSegmentList);
        if (Objects.equals(segmentCode, "XX")) {
            segmentSpinner.setText(segmentCode);
        } else {
            segmentCode = "XX";
            segmentSpinner.setText(segmentCode);
        }

        segmentSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                mpoSegmentInfo = String.valueOf(item).trim();
                String[] first_split = mpoSegmentInfo.split("-");
                segmentName = first_split[0].trim();
                segmentCode = first_split[1].trim();
                //autoDcfpMpoFollow.setText(mpoAreaCode);
                Log.d("segment name", segmentName);
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void autoDcfpFollowEvents() {
        autoDcfpMpoFollow.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                autoDcfpMpoFollow.showDropDown();
                return false;
            }
        });
        autoDcfpMpoFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        autoDcfpMpoFollow.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                autoDcfpMpoFollow.setTextColor(Color.BLUE);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                autoDcfpMpoFollow.setTextColor(Color.BLUE);
            }

            @Override
            public void afterTextChanged(final Editable s) {
                try {
                    final String mpoAreaCode = s.toString();

                    if (mpoAreaCode.contains("-")) {
                        String tempMpoCode = mpoAreaCode.substring(mpoAreaCode.indexOf("-") + 1);
                        String[] first_split = mpoAreaCode.split("-");
                        dcfpMpoName = first_split[0].trim();
                        dcfpMpoCode = first_split[1].trim();
                        getDcfpMpoSegmentList();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void populateMpoFollowList(List<DcfpFollowMpoList> dcfpFollowMpoLists) {
        List<String> dcfpMpoList = new ArrayList<String>();
        for (int i = 0; i < dcfpFollowMpoLists.size(); i++) {
            dcfpMpoList.add(dcfpFollowMpoLists.get(i).getName());
        }
        String[] mpoFollowList = dcfpMpoList.toArray(new String[0]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, mpoFollowList);
        autoDcfpMpoFollow.setThreshold(2);
        autoDcfpMpoFollow.setAdapter(Adapter);
        autoDcfpMpoFollow.setTextColor(Color.BLUE);
    }

    public void getDcfpMpoSegmentList() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<DcfpFollowMpoModel> call = apiInterface.getDcfpMpoSegmentList(dcfpMpoCode);

        call.enqueue(new Callback<DcfpFollowMpoModel>() {
            @Override
            public void onResponse(@NotNull Call<DcfpFollowMpoModel> call, @NotNull retrofit2.Response<DcfpFollowMpoModel> response) {
                mpoDcfpSegmentLists = Objects.requireNonNull(response.body()).getDcfpFollowMpoList();
                Log.d("dcfp=>","response==>"+ dcfpFollowMpoLists.toString());

                if (response.isSuccessful()) {
                    if (dcfpFollowMpoLists.size() > 0) {
                        initMpoSegmentSpinner(mpoDcfpSegmentLists);
                    }
                } else {
                    Toast.makeText(MpoDcfpFollowActivity.this, "No data Available!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DcfpFollowMpoModel> call, Throwable t) {
                Toast.makeText(MpoDcfpFollowActivity.this, "Network error! Please wait while we are reconnecting", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getDcfpFollowMpoList() {
        ProgressDialog pDialog;
        pDialog = new ProgressDialog(MpoDcfpFollowActivity.this);
        pDialog.setTitle("Please wait !");
        pDialog.setMessage("Loading Mpo List..");
        pDialog.setCancelable(true);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<DcfpFollowMpoModel> call = apiInterface.getDcfpFollowMpoList(areaCode);

        call.enqueue(new Callback<DcfpFollowMpoModel>() {
            @Override
            public void onResponse(@NotNull Call<DcfpFollowMpoModel> call, @NotNull retrofit2.Response<DcfpFollowMpoModel> response) {
                dcfpFollowMpoLists = Objects.requireNonNull(response.body()).getDcfpFollowMpoList();
                Log.d("dcfp=>","response==>"+ dcfpFollowMpoLists.toString());

                if (response.isSuccessful()) {
                    pDialog.dismiss();

                    if (dcfpFollowMpoLists.size() > 0) {
                        populateMpoFollowList(dcfpFollowMpoLists);
                    }
                } else {
                    pDialog.dismiss();
                    Toast.makeText(MpoDcfpFollowActivity.this, "No data Available!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DcfpFollowMpoModel> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(MpoDcfpFollowActivity.this, "Network error! Please wait while we are reconnecting", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getDcfpMpoFollowupList() {
        ProgressDialog pDialog;
        pDialog = new ProgressDialog(MpoDcfpFollowActivity.this);
        pDialog.setTitle("Please wait !");
        pDialog.setMessage("Loading Dcfp Follow-up Data..");
        pDialog.setCancelable(true);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<MpoDcfpFollowModel> call = apiInterface.getDcfpMpoFollowupList(dcfpMpoCode, todate.getText().toString().trim(), segmentCode);

        call.enqueue(new Callback<MpoDcfpFollowModel>() {
            @Override
            public void onResponse(@NotNull Call<MpoDcfpFollowModel> call, @NotNull retrofit2.Response<MpoDcfpFollowModel> response) {
                mpoDcfpFollowLists = Objects.requireNonNull(response.body()).getMpoDcfpFollowList();
                Log.d("dcfp=>","response==>"+ mpoDcfpFollowLists.toString());

                if (response.isSuccessful()) {
                    pDialog.dismiss();
                    mpoDcfpFollowAdapter = new MpoDcfpFollowAdapter(MpoDcfpFollowActivity.this, mpoDcfpFollowLists);
                    LinearLayoutManager manager = new LinearLayoutManager(MpoDcfpFollowActivity.this);
                    recyclerMpoDcfp.setLayoutManager(manager);
                    recyclerMpoDcfp.setAdapter(mpoDcfpFollowAdapter);
                    recyclerMpoDcfp.addItemDecoration(new DividerItemDecoration(MpoDcfpFollowActivity.this, DividerItemDecoration.VERTICAL));
                } else {
                    pDialog.dismiss();
                    Toast.makeText(MpoDcfpFollowActivity.this, "No data Available!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MpoDcfpFollowModel> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(MpoDcfpFollowActivity.this, "Network error! Please wait while we are reconnecting", Toast.LENGTH_SHORT).show();
            }
        });
    }
}