package com.opl.pharmavector.prescriptionsurvey;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.opl.pharmavector.Customer;
import com.opl.pharmavector.Dashboard;
import com.opl.pharmavector.ServiceHandler;
import com.opl.pharmavector.Targetvalue;
import com.opl.pharmavector.model.Patient;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.opl.pharmavector.prescriptionsurvey.Adapter;

import com.opl.pharmavector.R;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class PrescroptionImageSearch extends AppCompatActivity {
    Calendar myCalendar = Calendar.getInstance();
    Calendar myCalendar2 = Calendar.getInstance();

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private com.opl.pharmavector.prescriptionsurvey.Adapter adapter;
    private List<Patient> petsList;
    ApiInterface apiInterface;
    com.opl.pharmavector.prescriptionsurvey.Adapter.RecyclerViewClickListener listener;
    ProgressBar progressBar;
    private TextView fromdate,todate;
    private Button fab,btn_back;
    Boolean loading = true;
    private AutoCompleteTextView actv ;
    private ArrayList<Customer> customerlist;
    private Spinner cust;
    private String URL_CUSOTMER = BASE_URL+"prescription_survey/get_brand.php";
    private String product_name,product_code;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_search_main);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initViews();

        Calendar c_todate = Calendar.getInstance();
        SimpleDateFormat dftodate = new SimpleDateFormat("dd/MM/yyyy");
        String current_todate = dftodate.format(c_todate.getTime());
        todate.setText(current_todate);
        Calendar c_fromdate = Calendar.getInstance();
        SimpleDateFormat dffromdate = new SimpleDateFormat("01/MM/yyyy");
        String current_fromdate = dffromdate.format(c_fromdate.getTime());
        fromdate.setText(current_fromdate);
        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date_form = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
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
        fromdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(PrescroptionImageSearch.this, date_form, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        final Calendar myCalendar1 = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date_to = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
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
        todate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(PrescroptionImageSearch.this, date_to, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setAdapter(null);
                getDoctor();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        actv.setFocusableInTouchMode(true);
        actv.setFocusable(true);
        actv.requestFocus();
        actv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub
                if (actv.getText().toString() != "") {

                }

            }
        });
        /*---------------------23.06.2016-----start--------------------------*/
        actv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                actv.showDropDown();
                return false;
            }
        });
        actv.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(final Editable s) {
                // TODO Auto-generated method stub
                try {
                    final String inputorder = s.toString();
                    String first_split[] = inputorder.split("//");
                    product_name = first_split[0].trim();
                    product_code = first_split[1].trim();
                    actv.setText(product_name);
                    hideKeyBoard();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            private void length() {
                // TODO Auto-generated method stub

            }


        });

        listener = new Adapter.RecyclerViewClickListener() {
            @Override
            public void onRowClick(View view, final int position) {

            }
            @Override
            public void onLoveClick(View view, int position) {
            }
        };

        getDoctor();
        hideKeyBoard();
        new GetCategories().execute();
        hideKeyBoard();
    }

    private void getDoctor() {
        hideKeyBoard();
        Call<List<Patient>> call = apiInterface.getDoctorinfo(Dashboard.globalmpocode,fromdate.getText().toString().trim(),
                todate.getText().toString().trim(),product_code);
        call.enqueue(new Callback<List<Patient>>() {
            @Override
            public void onResponse(Call<List<Patient>> call, Response<List<Patient>> response) {
                progressBar.setVisibility(View.GONE);
                petsList = response.body();
                int adapterSize = petsList.size();

                if (adapterSize == 0){
                    hideKeyBoard();
                    Toast.makeText(PrescroptionImageSearch.this, "No Prescription Available" ,
                            Toast.LENGTH_SHORT).show();

                }
                else {
                    hideKeyBoard();
                    adapter = new com.opl.pharmavector.prescriptionsurvey.Adapter(petsList, PrescroptionImageSearch.this, listener);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }
            }
            @Override
            public void onFailure(Call<List<Patient>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

                hideKeyBoard();
                Toast.makeText(PrescroptionImageSearch.this, "Prescription Images are loading, Please wait" ,
                        Toast.LENGTH_SHORT).show();

                getDoctor();
                hideKeyBoard();

            }
        });


    }


    @SuppressLint("SetTextI18n")
    public void initViews() {
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        progressBar = findViewById(R.id.progress);
        fab = findViewById(R.id.fab);
        btn_back = findViewById(R.id.back);
        btn_back.setTypeface(fontFamily);
        btn_back.setText("\uf08b");
        fromdate = findViewById(R.id.fromdate);
        todate = findViewById(R.id.todate);
        actv =  findViewById(R.id.autoCompleteTextView1);
        cust = findViewById(R.id.customer);
        customerlist = new ArrayList<Customer>();
        product_name="xx";
        product_code="xx";

    }


    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setBirth();
        }
    };

    private void setBirth() {
        String myFormat = "dd/MM/yyyy";//In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        fromdate.setText(sdf.format(myCalendar.getTime()));
        SimpleDateFormat sdf2 = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf2.format(d);

    }


    class GetCategories extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... arg0) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_CUSOTMER, ServiceHandler.POST, params);
            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray customer = jsonObj.getJSONArray("customer");
                        for (int i = 0; i < customer.length(); i++) {
                            JSONObject catObj = (JSONObject) customer.get(i);
                            Customer custo = new Customer(catObj.getInt("id"), catObj.getString("name"));
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
            populateSpinner();
        }

    }
    private void populateSpinner() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < customerlist.size(); i++) {
            lables.add(customerlist.get(i).getName());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, lables);
        cust.setAdapter(spinnerAdapter);
        String[] customer = lables.toArray(new String[lables.size()]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this,R.layout.spinner_text_view, customer);
        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        actv.setAdapter(Adapter);
        actv.setTextColor(Color.BLUE);
    }
    private void hideKeyBoard(){
       // InputMethodManager imm = (InputMethodManager) context.getSystemService(PrescroptionImageSearch.INPUT_METHOD_SERVICE);
       // imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        InputMethodManager imm = (InputMethodManager) getSystemService(PrescroptionImageSearch.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }



}
