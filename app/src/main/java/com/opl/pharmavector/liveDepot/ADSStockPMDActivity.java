package com.opl.pharmavector.liveDepot;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.opl.pharmavector.Customer;
import com.opl.pharmavector.R;
import com.opl.pharmavector.ServiceHandler;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ADSStockPMDActivity extends Activity implements AdsStockInfoAdapter.AdsStockListener {
    public int blockNumber1 = 0, blockNumber2 = 0;
    Button submitBtn, backBtn;
    AutoCompleteTextView autoProduct;
    EditText searchView;
    ProgressDialog pDialog;
    TextView prevMonth1, prevMonth2, prevMonth3, totalStock, totalCsTrans, totalDepotTrans, totalValue, totalSale, packSize, productCode;
    private ArrayList<Customer> customerList;
    RecyclerView recyclerAdsFirst, recyclerAdsSecond, recyclerAdsDetail;
    private AdsStockInfoAdapter adsStockAdapter1, adsStockAdapter2;
    private AdsStockDetailAdapter adsStockDetailAdapter;
    private ArrayList<AdsStocksInfoList> adsStocksLists1, adsStocksLists2;
    private ArrayList<AdsStockDetailsList> adsStockDetailLists;
    String Url_ads_product_list = BASE_URL + "get_product_followup.php";
    String depot_split, depot_code = "xx", depot_name, userCode, userType, userRole, p_code = "", product_name, product_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adsstock_pmd);

        initViews();
        initProductEvent();
        setUpRecyclerDetail();
        new loadADSStockProduct().execute();
        submitBtn.setOnClickListener(v -> {
            if (product_code != null) {
                getAdsStockFirstLists();
                getAdsStockSecondLists();
                getAdsStockDetailLists();
            } else {
                @SuppressLint("ShowToast") Toast toast = Toast.makeText(getBaseContext(), "Please select product first!", Toast.LENGTH_SHORT);
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initProductEvent() {
        autoProduct.setOnClickListener(v -> {
            if (!autoProduct.getText().toString().equals("")) {
                String selectCustomer = autoProduct.getText().toString();
                autoProduct.setTag(selectCustomer);
            }
        });
        autoProduct.setOnTouchListener((v, event) -> {
            autoProduct.showDropDown();
            return false;
        });

        autoProduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(final Editable s) {
                try {
                    final String inputOrder = s.toString();
                    int total_string = inputOrder.length();

                    if (inputOrder.contains("//")) {
                        String[] arr = inputOrder.split("//");
                        product_name = arr[0].trim();
                        product_code = arr[1].trim();
                        productCode.setText("P. Code: " + product_code);
                        autoProduct.setText(product_name);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            private void length() {
            }
        });
    }

    public void setUpRecyclerDetail() {
        adsStockDetailAdapter = new AdsStockDetailAdapter(adsStockDetailLists);
        LinearLayoutManager manager = new LinearLayoutManager(ADSStockPMDActivity.this);
        recyclerAdsDetail.setLayoutManager(manager);
        recyclerAdsDetail.setAdapter(adsStockDetailAdapter);
    }

    private void initViews() {
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        recyclerAdsFirst = findViewById(R.id.recyclerAdsFirst);
        recyclerAdsSecond = findViewById(R.id.recyclerAdsSecond);
        recyclerAdsDetail = findViewById(R.id.recyclerAdsDetail);
        autoProduct = findViewById(R.id.autoProductList);
        submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setTypeface(fontFamily);
        submitBtn.setText("\uf1d8");
        backBtn = findViewById(R.id.backBtn);
        backBtn.setTypeface(fontFamily);
        backBtn.setText("\uf060 ");
        Calendar calendarPrev1 = Calendar.getInstance();
        calendarPrev1.add(Calendar.MONTH, -1);
        @SuppressLint("SimpleDateFormat") String previousMonth1 = new SimpleDateFormat("MMM-yyyy").format(calendarPrev1.getTime());
        Calendar calendarPrev2 = Calendar.getInstance();
        calendarPrev2.add(Calendar.MONTH, -2);
        @SuppressLint("SimpleDateFormat") String previousMonth2 = new SimpleDateFormat("MMM-yyyy").format(calendarPrev2.getTime());
        Calendar calendarPrev3 = Calendar.getInstance();
        calendarPrev3.add(Calendar.MONTH, -3);
        @SuppressLint("SimpleDateFormat") String previousMonth3 = new SimpleDateFormat("MMM-yyyy").format(calendarPrev3.getTime());
        Log.d("month name", previousMonth1 + " :: " + previousMonth2);
        totalSale = findViewById(R.id.totalSale);
        prevMonth1 = findViewById(R.id.prevMonth1);
        prevMonth2 = findViewById(R.id.prevMonth2);
        prevMonth3 = findViewById(R.id.prevMonth3);
        totalStock = findViewById(R.id.totalStock);
        totalValue = findViewById(R.id.totalValue);
        packSize = findViewById(R.id.pack_size);
        productCode = findViewById(R.id.product_code);
        totalCsTrans = findViewById(R.id.totalCsTrans);
        totalDepotTrans = findViewById(R.id.totalDepotTrans);
        prevMonth1.setText(previousMonth1);
        prevMonth2.setText(previousMonth2);
        prevMonth3.setText(previousMonth3);
        customerList = new ArrayList<>();
        adsStocksLists1 = new ArrayList<>();
        adsStocksLists2 = new ArrayList<>();
        adsStockDetailLists = new ArrayList<>();

        Bundle b = getIntent().getExtras();
        assert b != null;
        userCode = b.getString("UserCode");
        userType = b.getString("UserName");
        userRole = b.getString("UserRole");
    }

    private void getAdsStockFirstLists() {
        ProgressDialog pDialog = new ProgressDialog(ADSStockPMDActivity.this);
        pDialog.setMessage("Loading ADS Info...");
        pDialog.setCancelable(true);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<AdsStockInfoModel> call = apiInterface.getAdsStockPMDLists(userCode, product_code);
        adsStocksLists1.clear();

        call.enqueue(new Callback<AdsStockInfoModel>() {
            @SuppressLint({"NotifyDataSetChanged", "DefaultLocale", "SetTextI18n"})
            @Override
            public void onResponse(Call<AdsStockInfoModel> call, Response<AdsStockInfoModel> response) {
                if (response.isSuccessful()) {
                    pDialog.dismiss();
                    List<AdsStocksInfoList> adsStocksList = null;

                    if (response.body() != null) {
                        adsStocksList = (response.body()).getAdsProductList();
                        adsStocksLists1.addAll(adsStocksList);
                        adsStockAdapter1 = new AdsStockInfoAdapter(adsStocksLists1, ADSStockPMDActivity.this, 1, 0);
                        LinearLayoutManager manager = new LinearLayoutManager(ADSStockPMDActivity.this);
                        recyclerAdsFirst.setLayoutManager(manager);
                        recyclerAdsFirst.setAdapter(adsStockAdapter1);

                        if (adsStocksLists1.size() > 0) {
                            packSize.setText("Pack Size: " + adsStocksLists1.get(0).getPackSize());
                        }
                    }
                    Log.d("Ads Stock: ", adsStocksLists1.toString());
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

    private void getAdsStockSecondLists() {
        ProgressDialog pDialog = new ProgressDialog(ADSStockPMDActivity.this);
        pDialog.setMessage("Loading ADS Info...");
        pDialog.setCancelable(true);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<AdsStockInfoModel> call = apiInterface.getAdsStockPMDLists(userCode, product_code);
        adsStocksLists2.clear();

        call.enqueue(new Callback<AdsStockInfoModel>() {
            @SuppressLint({"NotifyDataSetChanged", "DefaultLocale"})
            @Override
            public void onResponse(Call<AdsStockInfoModel> call, Response<AdsStockInfoModel> response) {
                if (response.isSuccessful()) {
                    pDialog.dismiss();
                    List<AdsStocksInfoList> adsStocksList = null;

                    if (response.body() != null) {
                        adsStocksList = (response.body()).getAdsProductList();
                        adsStocksLists2.addAll(adsStocksList);
                        adsStockAdapter2 = new AdsStockInfoAdapter(adsStocksLists2, ADSStockPMDActivity.this, 0, 1);
                        LinearLayoutManager manager = new LinearLayoutManager(ADSStockPMDActivity.this);
                        recyclerAdsSecond.setLayoutManager(manager);
                        recyclerAdsSecond.setAdapter(adsStockAdapter2);
                    }
                    Log.d("Ads Stock: ", adsStocksLists2.toString());
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
        ProgressDialog pDialog = new ProgressDialog(ADSStockPMDActivity.this);
        pDialog.setMessage("Loading ADS Info...");
        pDialog.setCancelable(true);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<AdsStockDetailModel> call = apiInterface.getAdsStockDetailLists(userCode, product_code);
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
                    float stockValue = 0.0F;
                    DecimalFormat formatter = new DecimalFormat("#,##,###");
                    for (int i=0; i<adsStockDetailLists.size(); i++) {
                        if (adsStockDetailLists.get(i).getDepotQnty() != null) {
                            stockValue += Float.parseFloat(adsStockDetailLists.get(i).getDepotQnty());
                        }
                    }
                    String formatStockVal = formatter.format(stockValue);
                    totalStock.setText(formatStockVal);

                    float csTransValue = 0.0F;
                    for (int i=0; i<adsStockDetailLists.size(); i++) {
                        if (adsStockDetailLists.get(i).getTrnsQnty() != null) {
                            csTransValue += Float.parseFloat(adsStockDetailLists.get(i).getTrnsQnty());
                        }
                    }
                    String formatCsTransVal = formatter.format(csTransValue);
                    totalCsTrans.setText(formatCsTransVal);

                    float depotTransValue = 0.0F;
                    for (int i=0; i<adsStockDetailLists.size(); i++) {
                        if (adsStockDetailLists.get(i).getDepotToDepotTrns() != null) {
                            depotTransValue += Float.parseFloat(adsStockDetailLists.get(i).getDepotToDepotTrns());
                        }
                    }
                    String formatDepotTransVal = formatter.format(depotTransValue);
                    totalDepotTrans.setText(formatDepotTransVal);

                    float totalValues = 0.0F;
                    totalValues = stockValue + csTransValue + depotTransValue;
                    String formatTotalVal = formatter.format(totalValues);
                    totalValue.setText(formatTotalVal);

                    float saleValue = 0.0F;
                    for (int i=0; i<adsStockDetailLists.size(); i++) {
                        if (adsStockDetailLists.get(i).getCrMonSale() != null) {
                            saleValue += Float.parseFloat(adsStockDetailLists.get(i).getCrMonSale());
                        }
                    }
                    String formatSaleVal = formatter.format(saleValue);
                    totalSale.setText(formatSaleVal);
                    Log.d("Ads Stock: ", adsStockDetailLists.toString());
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

    private void productPopulateSpinner() {
        List<String> products = new ArrayList<String>();

        for (int i = 0; i < customerList.size(); i++) {
            products.add(customerList.get(i).getName());
        }
        String[] customer = products.toArray(new String[0]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, customer);
        autoProduct.setAdapter(Adapter);
        autoProduct.setTextColor(Color.BLUE);
    }

    class loadADSStockProduct extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ADSStockPMDActivity.this);
            pDialog.setMessage("Loading Products ...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserCode");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", userName));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(Url_ads_product_list, ServiceHandler.POST, params);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    JSONArray customer = jsonObj.getJSONArray("customer");

                    for (int i = 0; i < customer.length(); i++) {
                        JSONObject catObj = (JSONObject) customer.get(i);
                        Customer customer1 = new Customer(catObj.getInt("id"), catObj.getString("name"));
                        customerList.add(customer1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            productPopulateSpinner();
        }
    }

    @Override
    public void onAdsStockClick(int position, AdsStocksInfoList model) {
        p_code = model.getPCode();
        getAdsStockDetailLists();
    }
}