package com.opl.pharmavector.prescriptionsurvey;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
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

import com.opl.pharmavector.Customer;
import com.opl.pharmavector.R;
import com.opl.pharmavector.ServiceHandler;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;
import com.opl.pharmavector.util.KeyboardUtils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ASMRxSumMISActivity extends Activity implements RxSummaryMISAdapter.RxSummaryMisListener {
    Calendar c_todate, c_fromdate;
    CardView cardViewSelf, cardViewDetail;
    SimpleDateFormat dftodate, dffromdate;
    String current_todate, current_fromdate, manager_code, manager_detail, data_checker, toDate, fromDate, ffCode, brandName, brandCode, userRole;
    String actv_brand_code_split, brand_code = "xx", brand_name, json, f_date, t_date, from_date, to_date;
    TextView fromdate, todate, txt_self, txt_detail;
    Button submitBtn, backBtn;
    RecyclerView recyclerRxSumMis, recyclerRxSumDetail;
    LinearLayoutManager linearLayoutManager;
    private ArrayList<Customer> departmentlist;
    Calendar myCalendar, myCalendar1;
    AutoCompleteTextView actv_brand_name;
    RxSummaryMISAdapter rxSumMISSelfAdapter, rxSumMISDetailAdapter;
    DatePickerDialog.OnDateSetListener date_form, date_to;
    List<RxSumMISSelfList> rxSumMISSelfLists = new ArrayList<>();
    List<RxSumMISSelfList> rxSumMISDetailLists = new ArrayList<>();
    private String URL_BRAND = BASE_URL + "prescription_survey/get_brandList.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_summary_mis);

        initViews();
        initCalender();
        autoCompleteEvents();
        new GetBrandList().execute();
        getRxSumMisSelfLists();
        getRxSumMisDetailLists();
        submitBtn.setOnClickListener(v -> {
                    getRxSumMisSelfLists();
                    getRxSumMisDetailLists();
                }
        );
        backBtn.setOnClickListener(v -> finish());
    }

    @SuppressLint("SetTextI18n")
    private void initViews() {
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setTypeface(fontFamily);
        submitBtn.setText("\uf1d8");
        backBtn = findViewById(R.id.backbt);
        backBtn.setTypeface(fontFamily);
        backBtn.setText("\uf060 ");
        fromdate = findViewById(R.id.fromdate);
        todate = findViewById(R.id.todate);
        txt_self = findViewById(R.id.txt_self);
        txt_self.setText("Self");
        txt_detail = findViewById(R.id.txt_detail);
        txt_detail.setText("Zone");
        cardViewSelf = findViewById(R.id.cardViewSelf);
        recyclerRxSumMis = findViewById(R.id.recyclerRxSumMis);
        recyclerRxSumDetail = findViewById(R.id.recyclerRxSumDetail);
        departmentlist = new ArrayList<Customer>();
        actv_brand_name = findViewById(R.id.autoCompleteTextView2);

        Bundle b = getIntent().getExtras();
        assert b != null;
        toDate = b.getString("toDate");
        ffCode = b.getString("ffCode");
        fromDate = b.getString("fromDate");
        brandName = b.getString("brandName");
        brandCode = b.getString("brandCode");
        userRole = b.getString("ffType");

        if (Objects.equals(userRole, "ASM")) {
            cardViewSelf.setVisibility(View.VISIBLE);
        } else {
            cardViewSelf.setVisibility(View.GONE);
        }

        if (brandName != null || ffCode != null) {
            actv_brand_name.setText(brandName);
            brand_code = brandCode;
        }
    }

    @SuppressLint("SimpleDateFormat")
    private void initCalender() {
        c_todate = Calendar.getInstance();
        dftodate = new SimpleDateFormat("dd/MM/yyyy");
        current_todate = dftodate.format(c_todate.getTime());
        //todate.setText(current_todate);
        c_fromdate = Calendar.getInstance();
        dffromdate = new SimpleDateFormat("01/MM/yyyy");
        current_fromdate = dffromdate.format(c_fromdate.getTime());
        //fromdate.setText(current_fromdate);

        if (fromDate != null && toDate != null) {
            fromdate.setText(fromDate);
            todate.setText(toDate);
        } else {
            fromdate.setText(current_fromdate);
            todate.setText(current_todate);
        }

        myCalendar = Calendar.getInstance();
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
                fromdate.setTextColor(Color.BLACK);
                fromdate.setText("");
                fromdate.setText(sdf.format(myCalendar.getTime()));
            }
        };
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
                todate.setTextColor(Color.BLACK);
                todate.setText("");
                todate.setText(sdf.format(myCalendar.getTime()));
            }
        };
        fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ASMRxSumMISActivity.this, date_form, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ASMRxSumMISActivity.this, date_to, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void autoCompleteEvents() {
        actv_brand_name.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                actv_brand_name.showDropDown();
                return false;
            }
        });
        actv_brand_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        actv_brand_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                actv_brand_name.setTextColor(Color.RED);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                actv_brand_name.setTextColor(Color.GREEN);
            }

            @Override
            public void afterTextChanged(final Editable s) {
                try {
                    final String inputorder = s.toString();
                    int total_string = inputorder.length();
                    if (inputorder.contains("-")) {
                        actv_brand_code_split = inputorder.substring(inputorder.indexOf("-") + 1);
                        String[] first_split = inputorder.split("-");
                        brand_name = first_split[0].trim();
                        brand_code = first_split[1].trim();
                        actv_brand_name.setText(brand_name);
                        KeyboardUtils.hideKeyboard(ASMRxSumMISActivity.this);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void populatebrandSpinner() {
        List<String> lables = new ArrayList<String>();

        for (int i = 0; i < departmentlist.size(); i++) {
            lables.add(departmentlist.get(i).getName());
        }
        String[] customer = lables.toArray(new String[0]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, customer);
        actv_brand_name.setThreshold(2);
        actv_brand_name.setAdapter(Adapter);
        actv_brand_name.setTextColor(Color.BLUE);
    }

    class GetBrandList extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... arg0) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("manager_code", manager_code));
            params.add(new BasicNameValuePair("manager_detail", manager_detail));
            ServiceHandler jsonParser = new ServiceHandler();
            json = jsonParser.makeServiceCall(URL_BRAND, ServiceHandler.POST, params);
            Log.e("myBrandList==>", json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    JSONArray customer = jsonObj.getJSONArray("customer");

                    for (int i = 0; i < customer.length(); i++) {
                        JSONObject catObj = (JSONObject) customer.get(i);
                        Customer custo = new Customer(catObj.getInt("id"), catObj.getString("name"));
                        departmentlist.add(custo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
                new GetBrandList().execute();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            populatebrandSpinner();
        }
    }

    private String monNumToNameFormat(String date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("MMM", Locale.ENGLISH);
        LocalDate ld = LocalDate.parse(date, dtf);
        String month_name = dtf2.format(ld);
        String[] splitDate = date.split("/");
        return splitDate[0] + "-" + month_name + "-" + splitDate[2];
    }

    private void getRxSumMisDetailLists() {
        data_checker = "D";
        from_date = fromdate.getText().toString();
        to_date = todate.getText().toString();
        f_date = monNumToNameFormat(from_date);
        t_date = monNumToNameFormat(to_date);
        Log.d("selectTotDay", f_date + t_date);

        ProgressDialog pDialog = new ProgressDialog(ASMRxSumMISActivity.this);
        pDialog.setMessage("Loading Rx Summary ...");
        pDialog.setCancelable(true);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<RxSumMISSelfModel> call = apiInterface.getRxSumMISDetailList(ffCode, brand_code, f_date, t_date);
        rxSumMISDetailLists.clear();

        call.enqueue(new Callback<RxSumMISSelfModel>() {
            @Override
            public void onResponse(Call<RxSumMISSelfModel> call, Response<RxSumMISSelfModel> response) {
                if (response.isSuccessful()) {
                    pDialog.dismiss();
                    List<RxSumMISSelfList> rxSumMISSelfList = null;

                    if (response.body() != null) {
                        rxSumMISSelfList = (response.body()).getRxSumMISSelfLists();
                        rxSumMISDetailLists.addAll(rxSumMISSelfList);
                    }
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ASMRxSumMISActivity.this);
                    rxSumMISDetailAdapter = new RxSummaryMISAdapter(ASMRxSumMISActivity.this, rxSumMISDetailLists, ASMRxSumMISActivity.this, data_checker);
                    recyclerRxSumDetail.setLayoutManager(linearLayoutManager);
                    recyclerRxSumDetail.setAdapter(rxSumMISDetailAdapter);
                    recyclerRxSumDetail.addItemDecoration(new DividerItemDecoration(ASMRxSumMISActivity.this, DividerItemDecoration.VERTICAL));
                    Log.d("Rx Mis: ", String.valueOf(rxSumMISSelfLists));
                }
            }

            @Override
            public void onFailure(Call<RxSumMISSelfModel> call, Throwable t) {
                pDialog.dismiss();
                Toast toast = Toast.makeText(getBaseContext(), "Failed to Retried Data!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private void getRxSumMisSelfLists() {
        data_checker = "S";
        from_date = fromdate.getText().toString();
        to_date = todate.getText().toString();
        f_date = monNumToNameFormat(from_date);
        t_date = monNumToNameFormat(to_date);
        Log.d("selectTotDay", f_date + t_date);

        ProgressDialog pDialog = new ProgressDialog(ASMRxSumMISActivity.this);
        pDialog.setMessage("Loading Rx Summary ...");
        pDialog.setCancelable(true);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<RxSumMISSelfModel> call = apiInterface.getRxSumMISSelfList(ffCode, brand_code, f_date, t_date);
        rxSumMISSelfLists.clear();

        call.enqueue(new Callback<RxSumMISSelfModel>() {
            @Override
            public void onResponse(Call<RxSumMISSelfModel> call, Response<RxSumMISSelfModel> response) {
                if (response.isSuccessful()) {
                    pDialog.dismiss();
                    List<RxSumMISSelfList> rxSumMISSelfList = null;

                    if (response.body() != null) {
                        rxSumMISSelfList = (response.body()).getRxSumMISSelfLists();
                        rxSumMISSelfLists.addAll(rxSumMISSelfList);
                    }
                    linearLayoutManager = new LinearLayoutManager(ASMRxSumMISActivity.this);
                    rxSumMISSelfAdapter = new RxSummaryMISAdapter(ASMRxSumMISActivity.this, rxSumMISSelfLists, ASMRxSumMISActivity.this, data_checker);
                    recyclerRxSumMis.setLayoutManager(linearLayoutManager);
                    recyclerRxSumMis.setAdapter(rxSumMISSelfAdapter);
                    //recyclerRxSumMis.addItemDecoration(new DividerItemDecoration(RxSummaryMISActivity.this, DividerItemDecoration.VERTICAL));
                    Log.d("Rx Mis: ", String.valueOf(rxSumMISSelfLists));
                }
            }

            @Override
            public void onFailure(Call<RxSumMISSelfModel> call, Throwable t) {
                pDialog.dismiss();
                Toast toast = Toast.makeText(getBaseContext(), "Failed to Retried Data!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    @Override
    public void onRxMisClick(int position, RxSumMISSelfList rxModel) {
        Intent intent = new Intent(ASMRxSumMISActivity.this, RMRxSumMISActivity.class);
        intent.putExtra("brandCode", brand_code);
        intent.putExtra("ffCode", rxModel.getFfCode());
        intent.putExtra("toDate", todate.getText().toString());
        intent.putExtra("fromDate", fromdate.getText().toString());
        intent.putExtra("brandName", actv_brand_name.getText().toString());
        startActivity(intent);
    }
}