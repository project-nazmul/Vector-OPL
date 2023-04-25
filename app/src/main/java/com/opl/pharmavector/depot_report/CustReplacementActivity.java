package com.opl.pharmavector.depot_report;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.opl.pharmavector.Category1;
import com.opl.pharmavector.R;
import com.opl.pharmavector.RecycleViewAdapter;
import com.opl.pharmavector.RecyclerData;
import com.opl.pharmavector.ServiceHandler;
import com.opl.pharmavector.Utils;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;

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

public class CustReplacementActivity extends Activity {
    private LinearLayoutManager layoutManager;
    private ArrayList<RecyclerData> recyclerDataArrayList;
    private RecycleViewAdapter recyclerViewAdapter;
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    ProgressDialog pDialog;
    ListView productListView;
    RecyclerView recycle1;
    private ArrayList<Category1> categoriesList1;
    public int success;
    public String message, ord_no,p_cust_code;
    public TextView totqty, totval, product_name, sqnty, velue;
    public android.widget.Spinner ordspin;
    TextView  fromdate, todate;
    public String userName_1, userName, active_string, act_desiredString, active_string2;
    public String CurrenCustomer = "";
    public AutoCompleteTextView actv;
    Button submitBtn, back_btn, view_btn;
    LinearLayout ln;
    Calendar c_todate, c_fromdate,myCalendar,myCalendar1;
    SimpleDateFormat dftodate, dffromdate;
    String current_todate, current_fromdate;
    DatePickerDialog.OnDateSetListener date_form,date_to;
    final String URL_PRODUCT_VIEW = BASE_URL+"mposalesreports/depo_report/cust_replacement_info.php";
    final String URL_CUSOTMER = BASE_URL+"mposalesreports/depo_report/cust_replacement_list.php";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.depot_cust_replacement);

        initViews();
        initCalender();
        CardView cardview1 = findViewById(R.id.cardview1);
        cardview1.setVisibility(View.GONE);
        new GetCustomer().execute();

        ordspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CurrenCustomer = ordspin.getSelectedItem().toString();
            }
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        actv.setOnTouchListener((v, event) -> {
            actv.showDropDown();
            return false;
        });

        submitBtn.setOnClickListener(v -> {
            try {
                dataload();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        back_btn.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            Thread backthred = new Thread(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    try {
                        finishActivity(v);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            backthred.start();
        });

    }
    private void initViews() {
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        actv = findViewById(R.id.autoCompleteTextView1);
        ordspin = findViewById(R.id.customer);
        back_btn = findViewById(R.id.backbt);
        submitBtn = findViewById(R.id.submitBtn);
        fromdate = findViewById(R.id.fromdate);
        todate = findViewById(R.id.todate);
        recycle1 =findViewById(R.id.recycle1);
        back_btn.setTypeface(fontFamily);
        back_btn.setText("\uf060 ");
        Bundle b = getIntent().getExtras();
        userName = b.getString("UserName");
        categoriesList1 = new ArrayList<Category1>();
        recyclerDataArrayList = new ArrayList<>();
    }

    public void finishActivity(View v) {
        finish();
    }
    private void popSpinner() {
        List<String> description = new ArrayList<String>();
        for (int i = 0; i < categoriesList1.size(); i++) {
            description.add(categoriesList1.get(i).getId());
        }
        description = Utils.removeDuplicatesFromList(description);
        //ArrayAdapter<String> dataAdapterDes = new ArrayAdapter<String>(this, R.layout.spinner_text_view, description);
        //ordspin.setAdapter(dataAdapterDes);
        String[] customer = description.toArray(new String[0]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, description);
        actv.setThreshold(0);
        actv.setAdapter(Adapter);
        actv.setTextColor(Color.BLUE);
    }

    @SuppressLint("SimpleDateFormat")
    private void initCalender() {

        c_todate = Calendar.getInstance();
        dftodate = new SimpleDateFormat("dd/MM/yyyy");
        current_todate = dftodate.format(c_todate.getTime());
        todate.setText(current_todate);
        c_fromdate = Calendar.getInstance();
        dffromdate = new SimpleDateFormat("01/MM/yyyy");
        current_fromdate = dffromdate.format(c_fromdate.getTime());
        fromdate.setText(current_fromdate);

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
                //String myFormat = "dd/MM/yyyy";
                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                fromdate.setText(sdf.format(myCalendar.getTime()));

            }

        };
        fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CustReplacementActivity.this, date_form, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
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
                //String myFormat = "dd/MM/yyyy";
                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                todate.setText(sdf.format(myCalendar.getTime()));

            }

        };
        todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(CustReplacementActivity.this, date_to, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private class GetCustomer extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CustReplacementActivity.this);
            pDialog.setTitle("Data Loading !");
            pDialog.setMessage("Loading Please Wait..");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            @SuppressLint("WrongThread") String fromdate1 = fromdate.getText().toString();
            @SuppressLint("WrongThread") String todate1 = todate.getText().toString();
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", userName));
            //params.add(new BasicNameValuePair("to_date", todate1));
            //params.add(new BasicNameValuePair("from_date", fromdate1));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_CUSOTMER, ServiceHandler.POST, params);
            Log.e("Response===> ", "> " + json);
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    JSONArray categories1 = jsonObj.getJSONArray("categories");
                    for (int i = 0; i < categories1.length(); i++) {
                        JSONObject catObj = (JSONObject) categories1.get(i);
                        Category1 cat1 = new Category1(
                                catObj.getString("sl"),
                                catObj.getString("id"),
                                catObj.getString("name"));
                        categoriesList1.add(cat1);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Toast.makeText(CustReplacementActivity.this, "Nothing To Display", Toast.LENGTH_SHORT).show();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            popSpinner();
        }
    }

    private void dataload() {
        p_cust_code = actv.getText().toString().trim();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ArrayList<RecyclerData>> call = apiInterface.getCust_Replacement(userName, p_cust_code);

        call.enqueue(new Callback<ArrayList<RecyclerData>>() {
            @Override
            public void onResponse(Call<ArrayList<RecyclerData>> call, Response<ArrayList<RecyclerData>> response) {
                if (response.isSuccessful()) {
                    Log.e("DATA-- : ",response.body().toString());
                    recyclerDataArrayList = response.body();
                    for (int i = 0; i < recyclerDataArrayList.size(); i++) {
                        recyclerViewAdapter = new RecycleViewAdapter(CustReplacementActivity.this,recyclerDataArrayList,"cust_replace");
                        LinearLayoutManager manager = new LinearLayoutManager(CustReplacementActivity.this, LinearLayoutManager.VERTICAL, true);
                        recycle1.setLayoutManager(manager);
                        recycle1.setAdapter(recyclerViewAdapter);
                    }
                }
            }
            @Override
            public void onFailure(Call<ArrayList<RecyclerData>> call, Throwable t) {
                Log.e("Data load problem--->", "Failed to Retrived Data For-- "+ t);
                Toast toast =Toast.makeText(getBaseContext(),"Failed to Retrived Data ",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}
