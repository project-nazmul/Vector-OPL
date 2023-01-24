package com.opl.pharmavector.pmdVector.sales_4p;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.opl.pharmavector.Customer;
import com.opl.pharmavector.R;
import com.opl.pharmavector.RecyclerData;
import com.opl.pharmavector.ServiceHandler;
import com.opl.pharmavector.contact.contact_adapter;
import com.opl.pharmavector.pmdVector.DashBoardPMD;
import com.opl.pharmavector.pmdVector.adapter.BrandAdapter;
import com.opl.pharmavector.pmdVector.model.BrandList;
import com.opl.pharmavector.pmdVector.model.BrandModel;
import com.opl.pharmavector.pmdVector.model.ProductList;
import com.opl.pharmavector.pmdVector.model.ProductModel;
import com.opl.pharmavector.promomat.adapter.RecyclerTouchListener;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;

public class Activity_4p_Sales extends Activity implements MaterialSpinner.OnItemSelectedListener {
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    public ProgressDialog pDialog;
    public String json, team_type = "XX", team_name = "All", deignation_type = "XX", deignation_name = "All", place_type = "XX", place_name = "All", actv_rm_code_split, ff_name, ff_code = "XX";;
    Button back_btn, submitBtn;
    public android.widget.Spinner spin_rm;
    AutoCompleteTextView autoCompleteTextView1, autoCompleteTextView2, autoBrandName;
    private ArrayList<com.opl.pharmavector.Category> categoriesList;
    private ArrayList<Customer> customerlist;
    private ArrayList<BrandList> brandDatalist = new ArrayList<>();
    private ArrayList<ProductList> productDatalist = new ArrayList<>();
    private RecyclerView brandRecycler, recyclerView2, recyclerView3, recyclerView4;
    private RecyclerView.LayoutManager layoutManager1, layoutManager2, layoutManager3, layoutManager4;
    private ArrayList<RecyclerData> recyclerDataArrayList1, recyclerDataArrayList2, recyclerDataArrayList3, recyclerDataArrayList4;
    private contact_adapter recyclerViewAdapter1, recyclerViewAdapter2, recyclerViewAdapter3, recyclerViewAdapter4;
    ApiInterface apiInterface;
    ProgressBar progressBar;
    private String selected_number, selected_month, manager_code;
    MaterialSpinner mspinner1;
    private int monthPosition = -1;
    private BrandAdapter brandAdapter;

    private final String url_getMonth = BASE_URL + "pmd_vector/sales_4p/get_month.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4p_sales);

        initViews();
        //setUpRecyclerView();
        //initPlaceSpinner();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //brandRecycler.setAdapter(null);
                //getContact();
                brandWiseDataInfo();
            }
        });

        brandRecycler.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), brandRecycler, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                selected_number = recyclerDataArrayList1.get(position).getCol4();
                selected_month = recyclerDataArrayList1.get(position).getCol3();
                if (selected_number != null && !selected_number.isEmpty() && !selected_number.equals("null")) {
                    //ff_contact_activity.ViewDialog alert = new ff_contact_activity.ViewDialog();
                    //alert.showDialog();
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void initViews() {
        productBrandDataInfo();
        manager_code = DashBoardPMD.pmd_loccode;
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        back_btn = findViewById(R.id.backbt);
        submitBtn = findViewById(R.id.submitBtn);
        autoBrandName = findViewById(R.id.autoBrandName);
        submitBtn.setTypeface(fontFamily);
        back_btn.setTypeface(fontFamily);
        back_btn.setText("\uf060 ");

        mspinner1 = findViewById(R.id.mspinner1);
        customerlist = new ArrayList<Customer>();
        //autoCompleteTextView2 = findViewById(R.id.autoCompleteTextView2);
        categoriesList = new ArrayList<com.opl.pharmavector.Category>();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        brandRecycler = findViewById(R.id.brandRecycler);
        //layoutManager1 = new LinearLayoutManager(this);
        //brandRecycler.setLayoutManager(layoutManager1);
        recyclerDataArrayList1 = new ArrayList<>();
        new GetMonth().execute();
    }

    private void populateProductSpinner() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < productDatalist.size(); i++) {
            lables.add(productDatalist.get(i).getName());
        }
        String[] customer = lables.toArray(new String[0]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, customer);
        autoBrandName.setThreshold(2);
        autoBrandName.setAdapter(Adapter);
        autoBrandName.setTextColor(Color.BLUE);
    }

    public void finishActivity(View v) {
        finish();
    }

    private void initPlaceSpinner() {
        MaterialSpinner mspinner2 = findViewById(R.id.mspinner2);
        if (monthPosition > -1) {
            int cyclePosition = Integer.parseInt(customerlist.get(monthPosition).getCycle_n());
            List<String> cycle = new ArrayList<String>();
            for (int i = 1; i <= cyclePosition; i++) {
                cycle.add(String.valueOf(i));
            }
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, cycle);
            mspinner2.setAdapter(spinnerAdapter);
        }
        //mspinner2.setItems("1", "2", "3", "4", "5");
    }

    private void populateMonthAuto() {
        List<String> labels = new ArrayList<String>();
        for (int i = 0; i < customerlist.size(); i++) {
            labels.add(customerlist.get(i).getName());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, labels);
        mspinner1.setAdapter(spinnerAdapter);
        String[] customer = labels.toArray(new String[0]);
        mspinner1.setOnItemSelectedListener(Activity_4p_Sales.this);
        //ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, customer);
        //autoCompleteTextView2.setThreshold(2);
        //autoCompleteTextView2.setAdapter(Adapter);
        //autoCompleteTextView2.setTextColor(Color.BLUE);
    }

    @Override
    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
        monthPosition = position;
        initPlaceSpinner();
        Log.d("select month", String.valueOf(position));
    }

    public void setUpRecyclerView() {
        brandAdapter = new BrandAdapter(Activity_4p_Sales.this, brandDatalist);
        LinearLayoutManager manager = new LinearLayoutManager(Activity_4p_Sales.this);
        //manager.setTotalColumnCount(1);
        brandRecycler.setLayoutManager(manager);
        brandRecycler.setAdapter(brandAdapter);
        brandRecycler.addItemDecoration(new DividerItemDecoration(Activity_4p_Sales.this, DividerItemDecoration.VERTICAL));
    }

    @SuppressLint("StaticFieldLeak")
    class GetMonth extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            json = jsonParser.makeServiceCall(url_getMonth, ServiceHandler.POST, null);
            customerlist.clear();

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    JSONArray jsonArray = jsonObj.getJSONArray("customer");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject catObj = (JSONObject) jsonArray.get(i);
                        Customer customer = new Customer(catObj.getString("name"), catObj.getString("name2"), catObj.getString("cycle_n"));
                        customerlist.add(customer);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.d("JSON Data", "Didn't receive any data from server!");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            populateMonthAuto();
        }
    }

    public void brandWiseDataInfo() {
        pDialog = new ProgressDialog(Activity_4p_Sales.this);
        pDialog.setMessage("Brand Loading...");
        pDialog.setTitle("Brand Followup");
        pDialog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<BrandModel> call = apiInterface.getBrandWiseList(customerlist.get(monthPosition).getName());
        brandDatalist.clear();

        call.enqueue(new Callback<BrandModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<BrandModel> call, @NonNull retrofit2.Response<BrandModel> response) {
                List<BrandList> brandData = null;
                if (response.body() != null) {
                    brandData = response.body().getBrandList();
                }

                if (response.isSuccessful()) {
                    for (int i = 0; i < (brandData != null ? brandData.size() : 0); i++) {
                        brandDatalist.add(new BrandList(brandData.get(i).getBrandCode(),
                                brandData.get(i).getBrandName(),
                                brandData.get(i).getValShare(),
                                brandData.get(i).getUniteShare(),
                                brandData.get(i).getOplRank(),
                                brandData.get(i).getNatRank()));
                    }
                    setUpRecyclerView();
                    Log.d("brand List", brandDatalist.get(0).getBrandName());
                    //brandAdapter.notifyDataSetChanged();
                    pDialog.dismiss();
                } else {
                    pDialog.dismiss();
                    Toast.makeText(Activity_4p_Sales.this, "No data Available", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<BrandModel> call, @NonNull Throwable t) {
                pDialog.dismiss();
                brandWiseDataInfo();
            }
        });
    }

    public void productBrandDataInfo() {
        pDialog = new ProgressDialog(Activity_4p_Sales.this);
        pDialog.setMessage("Product Loading...");
        pDialog.setTitle("Product Followup");
        pDialog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ProductModel> call = apiInterface.getProductBrandList(manager_code);
        productDatalist.clear();

        call.enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(@NonNull Call<ProductModel> call, @NonNull retrofit2.Response<ProductModel> response) {
                List<ProductList> productData = null;
                if (response.body() != null) {
                    productData = response.body().getProductList();
                }

                if (response.isSuccessful()) {
                    for (int i = 0; i < (productData != null ? productData.size() : 0); i++) {
                        productDatalist.add(new ProductList(productData.get(i).getName(),
                                productData.get(i).getId()));
                    }
                    populateProductSpinner();
                    //Log.d("product List", productDatalist.get(0).getName());
                    //brandAdapter.notifyDataSetChanged();
                    pDialog.dismiss();
                } else {
                    pDialog.dismiss();
                    Toast.makeText(Activity_4p_Sales.this, "No data Available", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProductModel> call, @NonNull Throwable t) {
                pDialog.dismiss();
                brandWiseDataInfo();
            }
        });
    }
}