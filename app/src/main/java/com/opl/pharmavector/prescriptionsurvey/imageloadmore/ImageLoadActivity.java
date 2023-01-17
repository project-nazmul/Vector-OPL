package com.opl.pharmavector.prescriptionsurvey.imageloadmore;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.opl.pharmavector.ASMBrandwiseProductSale;
import com.opl.pharmavector.Customer;
import com.opl.pharmavector.Dashboard;
import com.opl.pharmavector.JSONParser;
import com.opl.pharmavector.R;
import com.opl.pharmavector.ServiceHandler;
import com.opl.pharmavector.pmdVector.DashBoardPMD;
import com.opl.pharmavector.prescriptionsurvey.PrescroptionImageSearch;
import com.opl.pharmavector.prescriptionsurvey.RX_RecycleViewAdapter;
import com.opl.pharmavector.prescriptionsurvey.rx_model;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;
import com.opl.pharmavector.util.KeyboardUtils;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageLoadActivity extends AppCompatActivity {
    private RX_RecycleViewAdapter recyclerViewAdapter;
    RecyclerView recyclerView;
    List<MovieModel> movies;
    MoviesAdapter adapter;
    ApiInterface api;
    String TAG = "ImageLoadActivity - ";
    Context context;
    private TextView fromdate,todate,user_show1;
    private Button fab,btn_back;
    private AutoCompleteTextView actv,mpo_actv ;
    private ArrayList<Customer> customerlist;
    private ArrayList<Customer> mpoList;

    private Spinner cust,mpo;

    private String product_name,product_code,user_code,actv_mpo_actv_split,mpo_code,emp_name;
    Calendar c_todate,c_fromdate;
    SimpleDateFormat dftodate,dffromdate;
    public Calendar myCalendar,myCalendar1;
    public DatePickerDialog.OnDateSetListener date_form,date_to;
    public String manager_code,manager_detail,manager_flag;
    private String URL_LIST = BASE_URL+"prescription_survey/get_mpoList.php";
    private String URL_CUSOTMER = BASE_URL+"prescription_survey/get_brand.php";
    private ArrayList<rx_model> recyclerDataArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_more);
        initView();
        caclenderEvent();
        brandSelectEvent();
        new GetCategories().execute();
        buttonEvent();
        setUpAdapter();
        load(0);


    }

    private void setUpAdapter() {
        adapter.setLoadMoreListener(new MoviesAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        int index = movies.size() - 1;
                        loadMore(index);
                    }
                });

            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        RecyclerView.ItemDecoration dividerItemDecoration = new VerticalLineDecorator(ContextCompat.getDrawable(context, R.drawable.recycler_view_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);
        api = ApiClient.getApiClient().create(ApiInterface.class);
    }

    private void buttonEvent() {

        //recyclerAdapter.clear();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 //movies.clear();
                 //setUpAdapter();
                 load_rx();
                //recyclerView.setAdapter(adapter);

            }
        });


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        finish();
                    }
                });

                backthred.start();
            }
        });
    }

    private void initView() {
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        fab = findViewById(R.id.fab);
        btn_back = findViewById(R.id.back);
        btn_back.setTypeface(fontFamily);
        btn_back.setText("\uf08b");
        fromdate = findViewById(R.id.fromdate);
        todate = findViewById(R.id.todate);
        actv =  findViewById(R.id.autoCompleteTextView1);
        cust = findViewById(R.id.customer);
        mpo = findViewById(R.id.mpo);
        mpo_actv = findViewById(R.id.mpo_actv);
        recyclerDataArrayList = new ArrayList<>();


        Bundle b = getIntent().getExtras();
        assert b != null;
        manager_code = b.getString("manager_code");
        manager_detail= b.getString("manager_detail");
        manager_flag= b.getString("manager_flag");
        user_show1 = findViewById(R.id.user_show1);
        user_show1.setText( manager_code + " [ "+ manager_detail+" ] "  );
        customerlist = new ArrayList<Customer>();
        mpoList = new ArrayList<>();

        product_code = "xx";
        mpo_code="xx";
        recyclerView =  findViewById(R.id.recycler_view);
        context = this;
        movies = new ArrayList<>();
        adapter = new MoviesAdapter(this, movies);
        if (manager_flag.equals("MPO")){
            mpo_actv.setVisibility(View.GONE);
        }else{
            mpo_actv.setVisibility(View.VISIBLE);
            new GetList().execute();
        }
    }


    private void brandSelectEvent() {

        actv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
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
                actv.setTextColor(Color.parseColor("#006199"));
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
                actv.setTextColor(Color.parseColor("#006199"));

            }

            @Override
            public void afterTextChanged(final Editable s) {
                // TODO Auto-generated method stub
                try {
                    final String inputorder = s.toString();
                    int total_string = inputorder.length();
                    if (inputorder.indexOf("//") != -1)
                    {
                        String cust_type = inputorder.substring(inputorder.indexOf("//") + 1);
                        String cust_type_with_note = inputorder.substring(inputorder.indexOf("//") + 0);
                        String cust_type_initial = inputorder.substring(inputorder.indexOf("//") + 0);
                        String first_split[] = inputorder.split("//");
                        product_name = first_split[0].trim();
                        product_code = first_split[1].trim();
                        Log.e("product_code==>",product_code);
                        actv.setText(product_name);
                        hideKeyBoard();



                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            private void length() {
                // TODO Auto-generated method stub

            }


        });



        mpo_actv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // hideKeyBoard();
                mpo_actv.showDropDown();
                return false;
            }
        });
        mpo_actv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            }
        });
        mpo_actv.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                mpo_actv.setTextColor(Color.parseColor("#006199"));
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
                mpo_actv.setTextColor(Color.parseColor("#006199"));
            }

            @Override
            public void afterTextChanged(final Editable s) {
                // TODO Auto-generated method stub
                try {
                    final String inputorder = s.toString();
                    int total_string = inputorder.length();
                    if (inputorder.contains("-")) {
                        actv_mpo_actv_split = inputorder.substring(inputorder.indexOf("-") + 1);
                        String[] first_split = inputorder.split("-");
                        mpo_code = first_split[0].trim();
                        emp_name  = first_split[1].trim();
                        mpo_actv.setText(mpo_code);
                        KeyboardUtils.hideKeyboard(ImageLoadActivity.this);

                    } else {

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            private void length() {
                // TODO Auto-generated method stub

            }

        });
    }

    @SuppressLint("SimpleDateFormat")
    private void caclenderEvent() {

        c_todate = Calendar.getInstance();
        dftodate = new SimpleDateFormat("dd/MM/yyyy");
        String current_todate = dftodate.format(c_todate.getTime());
        todate.setText(current_todate);
        c_fromdate = Calendar.getInstance();
        dffromdate = new SimpleDateFormat("01/MM/yyyy");
        String current_fromdate = dffromdate.format(c_fromdate.getTime());
        fromdate.setText(current_todate);


        myCalendar = Calendar.getInstance();


        date_form = new DatePickerDialog.OnDateSetListener() {
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
                new DatePickerDialog(ImageLoadActivity.this, date_form, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        myCalendar1 = Calendar.getInstance();

        date_to = new DatePickerDialog.OnDateSetListener() {
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

                new DatePickerDialog(ImageLoadActivity.this, date_to, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void load(int index){
        Log.e("LoadImage------------->",product_code+manager_code+mpo_code+index);

        ProgressDialog ppDialog = new ProgressDialog(  ImageLoadActivity.this);
        ppDialog.setMessage("Loading Products ...");
        ppDialog.setCancelable(true);
        ppDialog.show();
        Call<List<MovieModel>> call = api.getMovies(index,fromdate.getText().toString().trim(),
                todate.getText().toString().trim(),product_code,manager_code,mpo_code);
        call.enqueue(new Callback<List<MovieModel>>() {
            @Override
            public void onResponse(Call<List<MovieModel>> call, Response<List<MovieModel>> response) {
                if(response.isSuccessful()){
                    ppDialog.dismiss();
                    assert response.body() != null;
                    Log.e("ttt========", String.valueOf(response.body()));
                    //movies.addAll(response.body());

                    //adapter.notifyDataChanged();
                }
            }

            @Override
            public void onFailure(Call<List<MovieModel>> call, Throwable t) {
                Log.e(TAG," Response Error "+t.getMessage());
            }
        });
    }

    private void loadMore(int index){

        movies.add(new MovieModel("load"));
        adapter.notifyItemInserted(movies.size()-1);

        Call<List<MovieModel>> call = api.getMovies(index,fromdate.getText().toString().trim(),
                todate.getText().toString().trim(),product_code, manager_code,mpo_code);
        call.enqueue(new Callback<List<MovieModel>>() {
            @Override
            public void onResponse(Call<List<MovieModel>> call, Response<List<MovieModel>> response) {
                if(response.isSuccessful()){
                    //remove loading view
                    movies.remove(movies.size()-1);
                    List<MovieModel> result = response.body();
                    if(result.size()>0){
                        //add loaded data
                        movies.addAll(result);
                    }else{//result size 0 means there is no more data available at server
                        adapter.setMoreDataAvailable(false);
                        //telling adapter to stop calling load more as no more server data available
                        Toast.makeText(context,"No More Data Available",Toast.LENGTH_LONG).show();
                        adapter.loadDestroy();
                    }
                    adapter.notifyDataChanged();
                    //should call the custom method adapter.notifyDataChanged here to get the correct loading status
                }else{
                    adapter.loadDestroy();
                    loadMore(index+2);
                }
            }

            @Override
            public void onFailure(Call<List<MovieModel>> call, Throwable t) {
                adapter.loadDestroy();
                loadMore(index+2);
            }
        });
    }


    class GetCategories extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... arg0) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("manager_code", manager_code));
            params.add(new BasicNameValuePair("manager_detail", manager_detail));

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
        actv.setTextColor(Color.parseColor("#006199"));
    }




    private void populateSpinnerRM() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < mpoList.size(); i++) {
            lables.add(mpoList.get(i).getName());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, lables);
        mpo.setAdapter(spinnerAdapter);
        String[] customer = lables.toArray(new String[lables.size()]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, customer);
        mpo_actv.setThreshold(0);
        mpo_actv.setAdapter(Adapter);
        mpo_actv.setTextColor(Color.parseColor("#006199"));
    }


    class GetList extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... arg0) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("manager_code", manager_code));
            params.add(new BasicNameValuePair("manager_flag", manager_flag));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_LIST, ServiceHandler.POST, params);
            mpoList.clear();
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    JSONArray customer = jsonObj.getJSONArray("customer");
                    for (int i = 0; i < customer.length(); i++) {
                        JSONObject catObj = (JSONObject) customer.get(i);
                        Customer custo = new Customer(catObj.getInt("id"), catObj.getString("name"));
                        mpoList.add(custo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            populateSpinnerRM();
        }

    }

    private void hideKeyBoard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(PrescroptionImageSearch.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

   //===========================================
    private void load_rx() {
        Call<ArrayList<rx_model>> call = api.getLoad_Rx(0,fromdate.getText().toString().trim(),
                todate.getText().toString().trim(),product_code,manager_code,mpo_code);
        Log.e("Authentication", "Retrived Data ");
        call.enqueue(new Callback<ArrayList<rx_model>>() {
            @Override
            public void onResponse(Call<ArrayList<rx_model>> call, Response<ArrayList<rx_model>> response) {

                if (response.isSuccessful()) {
                    recyclerDataArrayList = response.body();
                    for (int i = 0; i < recyclerDataArrayList.size(); i++) {
                        recyclerViewAdapter = new RX_RecycleViewAdapter(ImageLoadActivity.this,recyclerDataArrayList);
                        LinearLayoutManager manager = new LinearLayoutManager(ImageLoadActivity.this, LinearLayoutManager.VERTICAL, true);
                        recyclerView.setLayoutManager(manager);
                        recyclerView.setAdapter(recyclerViewAdapter);
                    }
                }

            }
            @Override
            public void onFailure(Call<ArrayList<rx_model>> call, Throwable t) {
                Log.e("Authentication", "Failed to Retrived Data For-- "+ t);
                Toast toast =Toast.makeText(getBaseContext(),"Failed to Retrived Data ",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
    //=========================================
}
