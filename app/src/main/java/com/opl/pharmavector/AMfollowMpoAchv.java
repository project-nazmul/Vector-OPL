package com.opl.pharmavector;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
import java.util.StringJoiner;

public class AMfollowMpoAchv extends AppCompatActivity implements OnClickListener, AdapterView.OnItemSelectedListener {
    private static Activity parent;
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    private ArrayList<CategoryNew> categoriesList;
    public ProgressDialog pDialog;
    ListView productListView;
    Button submitBtn;
    public int success;
    public String message, ord_no;
    TextView tvfromdate, tvtodate, mpo_code, mpo_name;
    public TextView totqty, totval;
    public String userName_1, userName, active_string, act_desiredString;
    public String from_date, to_date;
    com.opl.pharmavector.JSONParser jsonParser;
    public static ArrayList<String> p_ids;
    public static ArrayList<Integer> p_quanty;
    public static ArrayList<String> PROD_RATE;
    public static ArrayList<String> PROD_VAT;
    private ArrayList<Customer> mpodcrlist;
    private final ArrayList<String> array_sort = new ArrayList<String>();
    private final String URL_PRODUCT_VIEW = BASE_URL+"salesfollowupreport/AMfollowMpoAchv.php";
    private android.widget.Spinner cust;
    public String p_code;
    private ArrayList<Customer> customerlist;
    Button back_btn, view_btn;
    LinearLayout ln;
    Calendar c_todate, c_fromdate;
    SimpleDateFormat dftodate, dffromdate;
    String current_todate, current_fromdate, UserName, fromdate, todate;
    Calendar myCalendar, myCalendar1;
    DatePickerDialog.OnDateSetListener date_form, date_to;AutoCompleteTextView actv;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mpoachvfollowup);

        userName ="xx";
        initViews();
        initCalender();

        new GetCategories().execute();
        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                String rm_code = (String) productListView.getAdapter().getItem(arg2);
                Intent i = new Intent(AMfollowMpoAchv.this, AMfollowMpoAchv2.class);
                i.putExtra("UserName", rm_code);
                i.putExtra("to_date", tvtodate.getText().toString());
                i.putExtra("from_date", tvfromdate.getText().toString());
                startActivity(i);
            }
        });
        submitBtn.setOnClickListener(new OnClickListener() {
            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");

            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(final View v) {
                try {
                    String fromdate1 = tvfromdate.getText().toString();
                    String todate1 = tvtodate.getText().toString();
                    if (fromdate1.isEmpty() || (fromdate1.equals("From Date")) || (fromdate1.equals("From Date is required"))) {
                        tvfromdate.setText("From Date is required");
                        tvfromdate.setTextColor(Color.RED);
                    } else if (todate1.isEmpty() || (todate1.equals("To Date")) || (todate1.equals("To Date is required"))) {
                        tvtodate.setText("To Date is required");
                        tvtodate.setTextColor(Color.RED);
                    } else {
                        categoriesList.clear();
                        new GetCategories().execute();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        ln.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {}
        });
    }

    @SuppressLint("SimpleDateFormat")
    private void initCalender() {
        Bundle b = getIntent().getExtras();
        userName = b.getString("UserName");
        fromdate = b.getString("from_date");
        todate = b.getString("to_date");
        c_todate = Calendar.getInstance();
        dftodate = new SimpleDateFormat("dd/MM/yyyy");
        current_todate = dftodate.format(c_todate.getTime());
        //todate.setText(current_todate);
        c_fromdate = Calendar.getInstance();
        dffromdate = new SimpleDateFormat("01/MM/yyyy");
        current_fromdate = dffromdate.format(c_fromdate.getTime());
        //fromdate.setText(current_fromdate);
        customerlist = new ArrayList<Customer>();
        cust.setOnItemSelectedListener(this);
        myCalendar = Calendar.getInstance();

        if (fromdate != null && todate != null) {
            tvfromdate.setText(fromdate);
            tvtodate.setText(todate);
        } else {
            tvfromdate.setText(current_fromdate);
            tvtodate.setText(current_todate);
        }

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
                tvfromdate.setTextColor(Color.BLACK);
                tvfromdate.setText("");
                tvfromdate.setText(sdf.format(myCalendar.getTime()));
            }
        };
        tvfromdate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AMfollowMpoAchv.this, date_form, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

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
                tvtodate.setTextColor(Color.BLACK);
                tvtodate.setText("");
                tvtodate.setText(sdf.format(myCalendar.getTime()));
            }
        };
        tvtodate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AMfollowMpoAchv.this, date_to, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        back_btn.setOnClickListener(new OnClickListener() {
            Bundle b = getIntent().getExtras();
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                backthred.start();
            }
        });
    }

    private void initViews() {
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        productListView = findViewById(R.id.pListView);
        back_btn = findViewById(R.id.backbt);
        view_btn = findViewById(R.id.view);
        submitBtn = findViewById(R.id.submitBtn_manager);
        tvfromdate = findViewById(R.id.fromdate);
        tvtodate = findViewById(R.id.todate);
        cust = findViewById(R.id.dcrlist);
        mpodcrlist = new ArrayList<Customer>();
        cust.setOnItemSelectedListener(this);
        actv = findViewById(R.id.autoCompleteTextView1);
        back_btn.setTypeface(fontFamily);
        back_btn.setText("\uf060 "); //&#xf060
        ln = findViewById(R.id.totalshow);
        totqty = findViewById(R.id.totalsellquantity);
        totval = findViewById(R.id.totalsellvalue);
        mpo_code = findViewById(R.id.mpo_code);
        mpo_name = findViewById(R.id.mpo_name);
        mpo_code.setText("Territory\nCode");
        mpo_name.setText("Territory\nName");
        p_ids = new ArrayList<String>();
        p_quanty = new ArrayList<Integer>();
        PROD_RATE = new ArrayList<String>();
        PROD_VAT = new ArrayList<String>();
        categoriesList = new ArrayList<>();
        Bundle b = getIntent().getExtras();
        userName = b.getString("UserName");
        //fromdate.setText(b.getString("from_date"));
        //todate.setText(b.getString("to_date"));
        Log.e("userName==>",userName);

        if (userName.equals("xx")){
            userName = AmDashboard.globalAreaCode;
            Log.e("userName==>",userName);
        }
    }

    private void popSpinner() {
        List<String> description = new ArrayList<String>();
        for (int i = 0; i < categoriesList.size(); i++) {
            description.add(categoriesList.get(i).getId());
        }
    }

    class Spinner {
        private String TotalQ;
        private String TotalV;

        private void populateSpinner() {
            ArrayList<String> lables = new ArrayList<String>();
            ArrayList<String> quanty = new ArrayList<String>();
            ArrayList<String> value = new ArrayList<String>();
            ArrayList<String> achv = new ArrayList<String>();
            ArrayList<String> mpo_code = new ArrayList<String>();
            ArrayList<String> ff_names = new ArrayList<String>();
            ArrayList<String> growth_val = new ArrayList<String>();
            ArrayList<String> mon_growth = new ArrayList<String>();
            ArrayList<String> cum_growth = new ArrayList<String>();
            String quantity = "", monGrowth, cumGrowth;
            float achievment;
            String prod_rate, prod_vat, sellvalue;
            String mpo, growth, ff_name;

            for (int i = 0; i < categoriesList.size(); i++) {
                lables.add(categoriesList.get(i).getName());
                p_ids.add(categoriesList.get(i).getId());
                quanty.add(categoriesList.get(i).getQuantity());
                quantity = categoriesList.get(i).getQuantity();
                prod_rate = String.valueOf((categoriesList.get(i).getPROD_RATE()));
                prod_vat = String.valueOf((categoriesList.get(i).getPROD_VAT()));
                mpo = String.valueOf(categoriesList.get(i).getPPM_CODE());
                ff_name = String.valueOf(categoriesList.get(i).getFF_NAME());
                growth = String.valueOf(categoriesList.get(i).getP_CODE());
                monGrowth = String.valueOf(categoriesList.get(i).getMON_GROWTH());
                cumGrowth = String.valueOf(categoriesList.get(i).getCUM_GROWTH());
                value.add(prod_rate);
                achv.add(prod_vat);
                mpo_code.add(mpo);
                ff_names.add(ff_name);
                growth_val.add(growth);
                mon_growth.add(monGrowth);
                cum_growth.add(cumGrowth);
            }
            MPOwiseAchvfollowupAdapter2 adapter = new MPOwiseAchvfollowupAdapter2(AMfollowMpoAchv.this, lables, quanty, value, achv, mpo_code, ff_names, growth_val, mon_growth, cum_growth);
            productListView.setAdapter(adapter);
        }

        private float round(float x, int i) {return 0;}

        public String getTotalQ() {
            return TotalQ;
        }

        public String getTotalV() {
            return TotalV;
        }
    }

    private class GetCategories extends AsyncTask<Void, Void, Void> {
        String fromdate1 = tvfromdate.getText().toString();
        String todate1 = tvtodate.getText().toString();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AMfollowMpoAchv.this);
            pDialog.setTitle("Data Loading !");
            pDialog.setMessage("Please Wait..");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", userName));
            params.add(new BasicNameValuePair("to_date", todate1));
            params.add(new BasicNameValuePair("p_code", p_code));
            params.add(new BasicNameValuePair("from_date", fromdate1));
            com.opl.pharmavector.ServiceHandler jsonParser = new com.opl.pharmavector.ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_PRODUCT_VIEW, com.opl.pharmavector.ServiceHandler.POST, params);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    JSONArray categories = jsonObj.getJSONArray("categories");
                    for (int i = 0; i < categories.length(); i++) {
                        JSONObject catObj = (JSONObject) categories.get(i);
                        CategoryNew cat = new CategoryNew(
                                catObj.getString("sl"),
                                catObj.getString("id"),
                                catObj.getString("name"),
                                catObj.getString("quantity"),
                                catObj.getString("PROD_RATE"),
                                catObj.getString("PROD_VAT"),
                                catObj.getString("PPM_CODE"),
                                catObj.getString("P_CODE"),
                                catObj.getString("FF_NAME"),
                                catObj.getString("MON_GROWTH"),
                                catObj.getString("CUM_GROWTH")
                        );
                        categoriesList.add(cat);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            AMfollowMpoAchv.Spinner sp = new AMfollowMpoAchv.Spinner();
            sp.populateSpinner();
            popSpinner();
            totqty.setText("");
            totval.setText("");
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    public void finishActivity(View v) {
        finish();
    }

    @Override
    public void onClick(View v) {}

    protected void onPostExecute() {}
}






