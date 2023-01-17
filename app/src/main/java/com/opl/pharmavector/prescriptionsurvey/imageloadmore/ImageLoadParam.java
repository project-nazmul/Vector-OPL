package com.opl.pharmavector.prescriptionsurvey.imageloadmore;

//ImageLoadParam 


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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageLoadParam extends AppCompatActivity {
    public RX_RecycleViewAdapter recyclerViewAdapter;
    public ArrayList<rx_model> recyclerDataArrayList;
    RecyclerView recyclerView;
    List<MovieModel> movies;
    MoviesAdapter adapter;
    ApiInterface api;
    String TAG = "ImageLoadParam - ";
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

    public String passed_mpo,passed_brandcode,pres_type,from_date,to_date,report_type,pres_sub_type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_more);
        initView();
        load_rx();
        hideKeyBoard();
        buttonEvent();
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
        fromdate.setVisibility(View.GONE);
        todate.setVisibility(View.GONE);
        actv =  findViewById(R.id.autoCompleteTextView1);
        cust = findViewById(R.id.customer);
        mpo = findViewById(R.id.mpo);
        mpo_actv = findViewById(R.id.mpo_actv);
        CardView cardview = findViewById(R.id.cardview);
        CardView cardview3 = findViewById(R.id.cardview3);
        cardview3.setVisibility(View.GONE);
        cardview.setVisibility(View.GONE);
        Bundle b = getIntent().getExtras();
        assert b != null;
        passed_mpo        = b.getString("passed_mpo");
        passed_brandcode  = b.getString("passed_brandcode");
        from_date         = b.getString("from_date");
        to_date           = b.getString("to_date");
        pres_type         = b.getString("pres_type");
        report_type       = b.getString("report_type");
        pres_sub_type     = b.getString("pres_sub_type");

        String passed_brandname = b.getString("passed_brandname");
        user_show1 = findViewById(R.id.user_show1);
        user_show1.setText("Prescription for\t" + "'" +passed_brandname +"'" + "\tby-\t" +passed_mpo);
        customerlist = new ArrayList<Customer>();
        mpoList = new ArrayList<>();
        product_code = passed_brandcode;
        mpo_code=  passed_mpo;
        recyclerView =  findViewById(R.id.recycler_view);
        recyclerDataArrayList = new ArrayList<>();
        //context = this;
        //movies = new ArrayList<>();
        //adapter = new MoviesAdapter(this, movies);
        //mpo_actv.setVisibility(View.GONE);
        api = ApiClient.getApiClient().create(ApiInterface.class);
        //setUpAdapter();
        //load(0);

    }
    private void load(int index){


        ProgressDialog ppDialog = new ProgressDialog(  ImageLoadParam.this);
        ppDialog.setMessage("Loading Products ...");
        ppDialog.setCancelable(true);
        ppDialog.show();
        Call<List<MovieModel>> call = api.loadmpoimage(index,fromdate.getText().toString().trim(),
                todate.getText().toString().trim(),product_code,pres_type,mpo_code,report_type,pres_sub_type);
        call.enqueue(new Callback<List<MovieModel>>() {
            @Override
            public void onResponse(Call<List<MovieModel>> call, Response<List<MovieModel>> response) {
                if(response.isSuccessful()){
                    ppDialog.dismiss();
                    assert response.body() != null;
                    movies.addAll(response.body());
                    adapter.notifyDataChanged();
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

        Call<List<MovieModel>> call = api.loadmpoimage(index,fromdate.getText().toString().trim(),
                todate.getText().toString().trim(),product_code, pres_type,mpo_code,report_type,pres_sub_type);
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
    private void hideKeyBoard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(PrescroptionImageSearch.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    //===========================================
    private void load_rx() {
        //Log.e("Retrived--->", from_date+"--"+to_date+"--"+product_code+"--"+"manager_code"+"--"+mpo_code);
        Call<ArrayList<rx_model>> call = api.getLoad_Rx(0,from_date,to_date,product_code,mpo_code,mpo_code);
        call.enqueue(new Callback<ArrayList<rx_model>>() {
            @Override
            public void onResponse(Call<ArrayList<rx_model>> call, Response<ArrayList<rx_model>> response) {

                if (response.isSuccessful()) {
                    recyclerDataArrayList = response.body();
                    for (int i = 0; i < recyclerDataArrayList.size(); i++) {
                        recyclerViewAdapter = new RX_RecycleViewAdapter(ImageLoadParam.this,recyclerDataArrayList);
                        LinearLayoutManager manager = new LinearLayoutManager(ImageLoadParam.this, LinearLayoutManager.VERTICAL, true);
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
