package com.opl.pharmavector.liveDepot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.opl.pharmavector.Customer;
import com.opl.pharmavector.R;
import com.opl.pharmavector.ServiceHandler;
import com.opl.pharmavector.master_code.MasterCode;
import com.opl.pharmavector.master_code.adapter.MasterAdapter;
import com.opl.pharmavector.master_code.model.MasterCList;
import com.opl.pharmavector.prescriptionsurvey.RxSumMISSelfList;
import com.opl.pharmavector.prescriptionsurvey.RxSumMISSelfModel;
import com.opl.pharmavector.prescriptionsurvey.RxSummaryMISActivity;
import com.opl.pharmavector.prescriptionsurvey.RxSummaryMISAdapter;
import com.opl.pharmavector.promomat.util.FixedGridLayoutManager;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;
import com.opl.pharmavector.util.KeyboardUtils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveDepotStockActivity extends Activity {
    Button submitBtn, backBtn;
    private EditText searchView;
    RecyclerView recyclerDepotStock;
    private TextView totalStockValue;
    AutoCompleteTextView autoDepotList;
    private LiveDepotStockAdapter depotStockAdapter;
    private ArrayList<LiveStockDepotList> stockDepotLists;
    private ArrayList<LiveDepotStockList> depotStockLists;
    String depot_split, depot_code = "xx", depot_name, userCode, userType, userRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_depot_stock);

        initViews();
        searchViewEvent();
        autoCompleteEvent();
        setUpRecyclerView();
        getLiveStockDepotLists();
        submitBtn.setOnClickListener(v -> {
            getLiveDepotStockLists();
            KeyboardUtils.hideKeyboard(LiveDepotStockActivity.this);
            searchView.getText().clear();
        });
        backBtn.setOnClickListener(v -> finish());
    }

    private void searchViewEvent() {
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(searchView, InputMethodManager.SHOW_IMPLICIT);
                searchView.setFocusable(true);
                searchView.setFocusableInTouchMode(true);
                searchView.setClickable(true);
                searchView.setText("");
            }
        });
        searchView.addTextChangedListener(new TextWatcher() {
            @SuppressLint({"DefaultLocale", "NotifyDataSetChanged"})
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {
                depotProductFilter(s.toString().trim());
            }
        });
    }

    void depotProductFilter(String query) {
        List<LiveDepotStockList> depotStockList = new ArrayList<>();

        for (LiveDepotStockList stockList: depotStockLists) {
            if (stockList.getSl().toString().toUpperCase().contains(query.toUpperCase()) || stockList.getPCode().toUpperCase().contains(query.toUpperCase()) ||
                    stockList.getPDesc().toUpperCase().contains(query.toUpperCase()) || stockList.getStockValue().toUpperCase().contains(query.toUpperCase()) ||
                    stockList.getCommMrp().toUpperCase().contains(query.toUpperCase()) || stockList.getStockQnty().toUpperCase().contains(query.toUpperCase())) {
                depotStockList.add(stockList);
            }
        }
        depotStockAdapter.searchDepotProduct(depotStockList);
    }

    private void initViews() {
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setTypeface(fontFamily);
        submitBtn.setText("\uf1d8");
        backBtn = findViewById(R.id.backbt);
        backBtn.setTypeface(fontFamily);
        backBtn.setText("\uf060 ");
        searchView = findViewById(R.id.p_search);
        searchView.setVisibility(View.GONE);
        autoDepotList = findViewById(R.id.autoDepotList);
        totalStockValue = findViewById(R.id.totalStockValue);
        recyclerDepotStock = findViewById(R.id.recyclerDepotStock);

        stockDepotLists = new ArrayList<>();
        depotStockLists = new ArrayList<>();
        Bundle b = getIntent().getExtras();
        assert b != null;
        userCode = b.getString("userName");
        userType = b.getString("userCode");
        userRole = b.getString("userRole");
    }

    public void setUpRecyclerView() {
        depotStockAdapter = new LiveDepotStockAdapter(depotStockLists);
        LinearLayoutManager manager = new LinearLayoutManager(LiveDepotStockActivity.this);
        recyclerDepotStock.setLayoutManager(manager);
        recyclerDepotStock.setAdapter(depotStockAdapter);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void autoCompleteEvent() {
        autoDepotList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                autoDepotList.showDropDown();
                return false;
            }
        });
        autoDepotList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        autoDepotList.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                autoDepotList.setTextColor(Color.RED);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                autoDepotList.setTextColor(Color.GREEN);
            }

            @Override
            public void afterTextChanged(final Editable s) {
                try {
                    final String inputOrder = s.toString();
                    int total_string = inputOrder.length();

                    if (inputOrder.contains("-")) {
                        depot_split = inputOrder.substring(inputOrder.indexOf("-") + 1);
                        String[] first_split = inputOrder.split("-");
                        depot_name = first_split[1].trim();
                        depot_code = first_split[0].trim();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void populateDepotSpinner() {
        List<String> depotInfo = new ArrayList<String>();

        for (int i = 0; i < stockDepotLists.size(); i++) {
            depotInfo.add(stockDepotLists.get(i).getDepotCode() + " - " + stockDepotLists.get(i).getDepotDesc());
        }
        String[] customer = depotInfo.toArray(new String[0]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, customer);
        autoDepotList.setThreshold(2);
        autoDepotList.setAdapter(Adapter);
        autoDepotList.setTextColor(Color.BLUE);
    }

    private void getLiveStockDepotLists() {
        ProgressDialog pDialog = new ProgressDialog(LiveDepotStockActivity.this);
        pDialog.setMessage("Loading Depot List...");
        pDialog.setCancelable(true);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<LiveStokeDepotModel> call = apiInterface.getLiveStockDepotList(userCode);
        stockDepotLists.clear();

        call.enqueue(new Callback<LiveStokeDepotModel>() {
            @Override
            public void onResponse(Call<LiveStokeDepotModel> call, Response<LiveStokeDepotModel> response) {
                if (response.isSuccessful()) {
                    pDialog.dismiss();
                    List<LiveStockDepotList> liveStokeList = null;

                    if (response.body() != null) {
                        liveStokeList = (response.body()).getDepotLists();
                        stockDepotLists.addAll(liveStokeList);
                    }
                    populateDepotSpinner();
                    Log.d("LiveDepot: ", String.valueOf(stockDepotLists));
                }
            }

            @Override
            public void onFailure(Call<LiveStokeDepotModel> call, Throwable t) {
                pDialog.dismiss();
                Toast toast = Toast.makeText(getBaseContext(), "Failed to Retried Data!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private void getLiveDepotStockLists() {
        ProgressDialog pDialog = new ProgressDialog(LiveDepotStockActivity.this);
        pDialog.setMessage("Loading Depot List...");
        pDialog.setCancelable(true);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<LiveDepotStockModel> call = apiInterface.getLiveDepotStockList(depot_code);
        depotStockLists.clear();

        call.enqueue(new Callback<LiveDepotStockModel>() {
            @SuppressLint({"NotifyDataSetChanged", "DefaultLocale"})
            @Override
            public void onResponse(Call<LiveDepotStockModel> call, Response<LiveDepotStockModel> response) {
                if (response.isSuccessful()) {
                    pDialog.dismiss();
                    List<LiveDepotStockList> liveStokeList = null;

                    if (response.body() != null) {
                        liveStokeList = (response.body()).getDepotStockLists();
                        depotStockLists.addAll(liveStokeList);
                        depotStockAdapter.notifyDataSetChanged();
                    }
                    if (depotStockLists.size() > 0) {
                        searchView.setVisibility(View.VISIBLE);
                    }
                    float stockValue = 0.0F;
                    for (int i=0; i<depotStockLists.size(); i++) {
                       if (depotStockLists.get(i).getStockValue() != null) {
                           stockValue += Float.parseFloat(depotStockLists.get(i).getStockValue());
                       }
                    }
                    DecimalFormat formatter = new DecimalFormat("#,##,###.00");
                    String formatStockVal = formatter.format(stockValue);
                    totalStockValue.setText(formatStockVal);
                    Log.d("LiveDepot: ", String.valueOf(formatStockVal));
                }
            }

            @Override
            public void onFailure(Call<LiveDepotStockModel> call, Throwable t) {
                pDialog.dismiss();
                Toast toast = Toast.makeText(getBaseContext(), "Failed to Retried Data!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}