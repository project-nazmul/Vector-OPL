package com.opl.pharmavector.liveDepot;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.opl.pharmavector.R;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ADSStockInfoActivity extends Activity implements AdsStockInfoAdapter.AdsStockListener {
    EditText searchView;
    TextView prevMonth1, prevMonth2;
    RecyclerView recyclerAdsStock, recyclerAdsDetail;
    private AdsStockInfoAdapter adsStockAdapter;
    private AdsStockDetailAdapter adsStockDetailAdapter;
    private ArrayList<AdsStocksInfoList> adsStocksLists;
    private ArrayList<AdsStockDetailsList> adsStockDetailLists;
    String depot_split, depot_code = "xx", depot_name, userCode, userType, userRole, p_code = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adsstock_info);

        initViews();
        searchViewEvent();
        setUpRecyclerView();
        setUpRecyclerDetail();
        getAdsStockInfoLists();
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
                adsProductFilter(s.toString().trim());
            }
        });
    }

    void adsProductFilter(String query) {
        List<AdsStocksInfoList> adsStocksInfoList = new ArrayList<>();

        for (AdsStocksInfoList stockList: adsStocksLists) {
            if (stockList.getPDesc().toString().toUpperCase().contains(query.toUpperCase())) {
                adsStocksInfoList.add(stockList);
            }
        }
        adsStockAdapter.searchAdsProduct(adsStocksInfoList);
    }

    public void setUpRecyclerView() {
        adsStockAdapter = new AdsStockInfoAdapter(adsStocksLists, ADSStockInfoActivity.this);
        LinearLayoutManager manager = new LinearLayoutManager(ADSStockInfoActivity.this);
        recyclerAdsStock.setLayoutManager(manager);
        recyclerAdsStock.setAdapter(adsStockAdapter);
    }

    public void setUpRecyclerDetail() {
        adsStockDetailAdapter = new AdsStockDetailAdapter(adsStockDetailLists);
        LinearLayoutManager manager = new LinearLayoutManager(ADSStockInfoActivity.this);
        recyclerAdsDetail.setLayoutManager(manager);
        recyclerAdsDetail.setAdapter(adsStockDetailAdapter);
    }

    private void initViews() {
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        recyclerAdsStock = findViewById(R.id.recyclerAdsStock);
        recyclerAdsDetail = findViewById(R.id.recyclerAdsDetail);
        searchView = findViewById(R.id.p_search);
        searchView.setVisibility(View.GONE);
        Calendar calendarPrev1 = Calendar.getInstance();
        calendarPrev1.add(Calendar.MONTH, -1);
        @SuppressLint("SimpleDateFormat") String previousMonth1 = new SimpleDateFormat("MMM-yyyy").format(calendarPrev1.getTime());
        Calendar calendarPrev2 = Calendar.getInstance();
        calendarPrev2.add(Calendar.MONTH, -2);
        @SuppressLint("SimpleDateFormat") String previousMonth2 = new SimpleDateFormat("MMM-yyyy").format(calendarPrev2.getTime());
        Log.d("month name", previousMonth1 + " :: " + previousMonth2);
        prevMonth1 = findViewById(R.id.prevMonth1);
        prevMonth2 = findViewById(R.id.prevMonth2);
        prevMonth1.setText(previousMonth1);
        prevMonth2.setText(previousMonth2);
        adsStocksLists = new ArrayList<>();
        adsStockDetailLists = new ArrayList<>();

        Bundle b = getIntent().getExtras();
        assert b != null;
        userCode = b.getString("userName");
        userType = b.getString("userCode");
        userRole = b.getString("userRole");
    }

    private void getAdsStockInfoLists() {
        ProgressDialog pDialog = new ProgressDialog(ADSStockInfoActivity.this);
        pDialog.setMessage("Loading ADS Info...");
        pDialog.setCancelable(true);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<AdsStockInfoModel> call = apiInterface.getAdsStockInfoLists(userCode);
        adsStocksLists.clear();

        call.enqueue(new Callback<AdsStockInfoModel>() {
            @SuppressLint({"NotifyDataSetChanged", "DefaultLocale"})
            @Override
            public void onResponse(Call<AdsStockInfoModel> call, Response<AdsStockInfoModel> response) {
                if (response.isSuccessful()) {
                    pDialog.dismiss();
                    List<AdsStocksInfoList> adsStocksList = null;

                    if (response.body() != null) {
                        adsStocksList = (response.body()).getAdsProductList();
                        adsStocksLists.addAll(adsStocksList);
                        adsStockAdapter.notifyDataSetChanged();
                    }
                    if (adsStocksLists.size() > 0) {
                        searchView.setVisibility(View.VISIBLE);
                    }

//                    float stockValue = 0.0F;
//                    for (int i=0; i<depotStockLists.size(); i++) {
//                        if (depotStockLists.get(i).getStockValue() != null) {
//                            stockValue += Float.parseFloat(depotStockLists.get(i).getStockValue());
//                        }
//                    }
//                    DecimalFormat formatter = new DecimalFormat("#,##,###.00");
//                    String formatStockVal = formatter.format(stockValue);
//                    totalStockValue.setText(formatStockVal);
                    Log.d("Ads Stock: ", adsStocksLists.toString());
                }
            }

            @Override
            public void onFailure(Call<AdsStockInfoModel> call, Throwable t) {
                pDialog.dismiss();
                Toast toast = Toast.makeText(getBaseContext(), "Failed to Retried Data!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private void getAdsStockDetailLists() {
        ProgressDialog pDialog = new ProgressDialog(ADSStockInfoActivity.this);
        pDialog.setMessage("Loading ADS Info...");
        pDialog.setCancelable(true);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<AdsStockDetailModel> call = apiInterface.getAdsStockDetailLists(p_code);
        adsStockDetailLists.clear();

        call.enqueue(new Callback<AdsStockDetailModel>() {
            @SuppressLint({"NotifyDataSetChanged", "DefaultLocale"})
            @Override
            public void onResponse(Call<AdsStockDetailModel> call, Response<AdsStockDetailModel> response) {
                if (response.isSuccessful()) {
                    pDialog.dismiss();
                    List<AdsStockDetailsList> adsStocksList = null;

                    if (response.body() != null) {
                        adsStocksList = (response.body()).getAdsProductDetail();
                        adsStockDetailLists.addAll(adsStocksList);
                        adsStockDetailAdapter.notifyDataSetChanged();
                    }
//                    float stockValue = 0.0F;
//                    for (int i=0; i<depotStockLists.size(); i++) {
//                        if (depotStockLists.get(i).getStockValue() != null) {
//                            stockValue += Float.parseFloat(depotStockLists.get(i).getStockValue());
//                        }
//                    }
//                    DecimalFormat formatter = new DecimalFormat("#,##,###.00");
//                    String formatStockVal = formatter.format(stockValue);
//                    totalStockValue.setText(formatStockVal);
                    Log.d("Ads Stock: ", adsStocksLists.toString());
                }
            }

            @Override
            public void onFailure(Call<AdsStockDetailModel> call, Throwable t) {
                pDialog.dismiss();
                Toast toast = Toast.makeText(getBaseContext(), "Failed to Retried Data!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    @Override
    public void onAdsStockClick(int position, AdsStocksInfoList model) {
        p_code = model.getPCode();
        getAdsStockDetailLists();
    }
}