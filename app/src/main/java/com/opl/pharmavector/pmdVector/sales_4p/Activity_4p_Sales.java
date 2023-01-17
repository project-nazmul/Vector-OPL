package com.opl.pharmavector.pmdVector.sales_4p;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.TextView;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.opl.pharmavector.Customer;
import com.opl.pharmavector.R;
import com.opl.pharmavector.RecyclerData;
import com.opl.pharmavector.ServiceHandler;
import com.opl.pharmavector.contact.contact_adapter;
import com.opl.pharmavector.pmdVector.ff_contact.ff_contact_activity;
import com.opl.pharmavector.promomat.adapter.RecyclerTouchListener;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Activity_4p_Sales extends Activity {
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    public ProgressDialog pDialog;
    public String json,team_type="XX", team_name="All",deignation_type="XX", deignation_name="All", place_type="XX", place_name="All", actv_rm_code_split,ff_name, ff_code = "XX";;
    Button back_btn,submitBtn ;
    public android.widget.Spinner spin_rm;
    AutoCompleteTextView autoCompleteTextView1,autoCompleteTextView2;
    private ArrayList<com.opl.pharmavector.Category> categoriesList;
    private ArrayList<Customer> customerlist;
    private ArrayList<Customer> departmentlist;
    private RecyclerView recyclerView1,recyclerView2,recyclerView3,recyclerView4;
    private RecyclerView.LayoutManager layoutManager1,layoutManager2,layoutManager3,layoutManager4;
    private ArrayList<RecyclerData> recyclerDataArrayList1,recyclerDataArrayList2,recyclerDataArrayList3,recyclerDataArrayList4;
    private contact_adapter recyclerViewAdapter1,recyclerViewAdapter2,recyclerViewAdapter3,recyclerViewAdapter4;
    ApiInterface apiInterface;
    ProgressBar progressBar;
    private String selected_number,selected_person;
    MaterialSpinner mspinner1;

    private final String url_getMonth = BASE_URL+"pmd_vector/sales_4p/get_month.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4p_sales);
        initViews();
        initplaceSpinner();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView1.setAdapter(null);
                //getContact();

            }
        });

        recyclerView1.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView1, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                selected_number = recyclerDataArrayList1.get(position).getCol4();
                selected_person = recyclerDataArrayList1.get(position).getCol3();
                if (selected_number != null && !selected_number.isEmpty() && !selected_number.equals("null")){
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
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        back_btn = findViewById(R.id.backbt);
        submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setTypeface(fontFamily);
        back_btn.setTypeface(fontFamily);
        back_btn.setText("\uf060 ");

        mspinner1 = findViewById(R.id.mspinner1);
        customerlist = new ArrayList<Customer>();
        //autoCompleteTextView2 = findViewById(R.id.autoCompleteTextView2);
        categoriesList = new ArrayList<com.opl.pharmavector.Category>();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        recyclerView1 = findViewById(R.id.recyclerView1);
        layoutManager1 = new LinearLayoutManager(this);
        recyclerView1.setLayoutManager(layoutManager1);
        recyclerDataArrayList1 = new ArrayList<>();
        new GetMonth().execute();
    }
    public void finishActivity(View v) {
        finish();
    }

    private void initplaceSpinner() {
        MaterialSpinner mspinner2 = findViewById(R.id.mspinner2);
        mspinner2.setItems("1", "2", "3", "4", "5");
    }
    private void populateMonthAuto() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < customerlist.size(); i++) {
            lables.add(customerlist.get(i).getName());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, lables);
        mspinner1.setAdapter(spinnerAdapter);
        String[] customer = lables.toArray(new String[lables.size()]);
        //ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, customer);
        //autoCompleteTextView2.setThreshold(2);
        //autoCompleteTextView2.setAdapter(Adapter);
        //autoCompleteTextView2.setTextColor(Color.BLUE);
    }
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
                    if (jsonObj != null) {
                        JSONArray customer = jsonObj.getJSONArray("customer");
                        for (int i = 0; i < customer.length(); i++) {
                            JSONObject catObj = (JSONObject) customer.get(i);
                            Customer custo = new Customer(catObj.getString("name"), catObj.getString("name2"));
                            customerlist.add(custo);
                        }
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
            populateMonthAuto();
        }

    }

}