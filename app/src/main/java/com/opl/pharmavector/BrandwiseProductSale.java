package com.opl.pharmavector;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import java.net.MalformedURLException;
import java.net.URL;
import java.lang.Runnable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.opl.pharmavector.util.NetInfo;

public class BrandwiseProductSale extends Activity implements OnClickListener, AdapterView.OnItemSelectedListener {
    private static Activity parent;
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    // array list for spinner adapter
    private ArrayList<com.opl.pharmavector.Category> categoriesList;
    public ProgressDialog pDialog;
    ListView productListView;
    Button submit, submitBtn;
    // private EditText current_qnty;
    EditText qnty;
    Boolean result;
    EditText inputOne, inputtwo;
    public int success;
    public String message, ord_no;
    TextView date2, ded, fromdate, todate;
    int textlength = 0;
    public TextView totqty, totval;
    //public android.widget.Spinner ordspin;
    public String userName_1, userName, active_string, act_desiredString;
    public String from_date, to_date,g_fm_code;
    com.opl.pharmavector.JSONParser jsonParser;
    List<NameValuePair> params;
    //public String CurrenCustomer="";
    //public AutoCompleteTextView actv;
    public static ArrayList<String> p_ids;
    public static ArrayList<Integer> p_quanty;
    public static ArrayList<String> PROD_RATE;
    public static ArrayList<String> PROD_VAT;
    public static ArrayList<String> PPM_CODE;
    public static ArrayList<String> SHIFT_CODE;

    private ArrayList<Customer> mpodcrlist;
    private ArrayList<String> array_sort = new ArrayList<String>();
    //private String URL_PRODUCT_VIEW ="http://opsonin.com.bd/dept_order_android_v2/ViewbyDate.php";

    private String URL_PRODUCT_VIEW = BASE_URL+"brandwisesales/BrandwiseProductSale.php";
    private String URL_DCR = BASE_URL+"get_brand.php";
    private android.widget.Spinner cust;
    public String product_name, p_code,select_fm_code,check_flag;
    private ArrayList<Customer> customerlist;

    AutoCompleteTextView actv;
    Button back_btn, view_btn;
    LinearLayout ln;
    Calendar c_todate, c_fromdate;
    SimpleDateFormat dftodate, dffromdate;
    String current_todate, current_fromdate;
    Calendar myCalendar, myCalendar1;
    DatePickerDialog.OnDateSetListener date_form, date_to;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.brandwisesale);

        initViews();
        calenderInit();

        cust.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        if (p_code != null && product_name != null && !p_code.equals("null") && !product_name.equals("null")) {
            actv.setText(product_name);
            actv.setSelection(actv.getText().length());
            new GetBrandSale().execute();
        } else {
            actv.setFocusable(true);
            actv.setSelection(actv.getText().length());
        }

        actv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actv.getText().toString() != "") {
                    String selectedcustomer = actv.getText().toString();
                    cust.setTag(selectedcustomer);
                }
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
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                //actv.setTextColor(Color.BLACK);
            }

            @Override
            public void afterTextChanged(final Editable s) {
                try {
                    //actv.setError("");
                    final String inputorder = s.toString();
                    int total_string = inputorder.length();

                    if (inputorder.indexOf("//") != -1) {
                        String arr[] = inputorder.split("//");
                        product_name = arr[0].trim();
                        String product_code = arr[1].trim();
                        p_code = product_code;
                        actv.setText(product_name);
                    } else {
                        // ded.setText("Select Date");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            private void length() {

            }
        });

        new LoadProduct().execute();
        back_btn.setOnClickListener(new OnClickListener() {
            Bundle b = getIntent().getExtras();
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
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

        submitBtn.setOnClickListener(new OnClickListener() {
            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            @Override
            public void onClick(final View v) {
                if ((actv.getText().toString().trim().equals(""))) {
                    actv.setError("Select a Brand");
                } else {
                    try {
                        String fromdate1 = fromdate.getText().toString();
                        String todate1 = todate.getText().toString();
                        if (fromdate1.isEmpty() || (fromdate1.equals("From Date")) || (fromdate1.equals("From Date is required"))) {
                            fromdate.setText("From Date is required");
                            fromdate.setTextColor(Color.RED);
                        } else if (todate1.isEmpty() || (todate1.equals("To Date")) || (todate1.equals("To Date is required"))) {
                            todate.setText("To Date is required");
                            todate.setTextColor(Color.RED);
                        } else {
                            categoriesList.clear();
                            new GetCategories().execute();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            }
        });

        ln.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

            }
        });

        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                select_fm_code = (String) productListView.getAdapter().getItem(arg2);
                if (select_fm_code.toString().trim().equals("TOTAL")){
                    Log.e("please select a MPO",select_fm_code);
                } else {
                    new callserver().execute();
                }
            }
        });
    }

    private void calenderInit() {
        Bundle b = getIntent().getExtras();
        String toDate = b.getString("to_date");
        String fromDate = b.getString("from_date");
        c_todate = Calendar.getInstance();
        dftodate = new SimpleDateFormat("dd/MM/yyyy");
        current_todate = dftodate.format(c_todate.getTime());
        todate.setText(toDate);
        c_fromdate = Calendar.getInstance();
        dffromdate = new SimpleDateFormat("01/MM/yyyy");
        current_fromdate = dffromdate.format(c_fromdate.getTime());
        fromdate.setText(fromDate);
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

        fromdate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(BrandwiseProductSale.this, date_form, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
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
        todate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(BrandwiseProductSale.this, date_to, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void initViews() {
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        productListView =  findViewById(R.id.pListView);
        back_btn = findViewById(R.id.backbt);
        view_btn = findViewById(R.id.view);
        submitBtn = findViewById(R.id.submitBtn);
        fromdate = findViewById(R.id.fromdate);
        todate = findViewById(R.id.todate);
        cust = findViewById(R.id.dcrlist);
        mpodcrlist = new ArrayList<Customer>();
        cust.setOnItemSelectedListener(this);
        actv = findViewById(R.id.autoCompleteTextView1);
        actv.setHint("Type Brand Name");
        back_btn.setTypeface(fontFamily);
        back_btn.setText("\uf060 ");// &#xf060
        ln = (LinearLayout) findViewById(R.id.totalshow);
        totqty = findViewById(R.id.totalsellquantity);
        totval = findViewById(R.id.totalsellvalue);
        p_ids = new ArrayList<String>();
        p_quanty = new ArrayList<Integer>();
        PROD_RATE = new ArrayList<String>();
        PROD_VAT = new ArrayList<String>();
        PPM_CODE = new ArrayList<String>();
        SHIFT_CODE = new ArrayList<String>();
        categoriesList = new ArrayList<com.opl.pharmavector.Category>();
        Bundle b = getIntent().getExtras();
        String userName = b.getString("UserName");
        p_code = b.getString("p_code");
        product_name = b.getString("product_name");
        g_fm_code= userName;
        submitBtn.setTextSize(10);
        customerlist = new ArrayList<Customer>();
    }


    private void producpopulatespinner() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < customerlist.size(); i++) {
            lables.add(customerlist.get(i).getName());


        }


        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,  R.layout.spinner_text_view, lables);
        cust.setAdapter(spinnerAdapter);
        String[] customer = lables.toArray(new String[lables.size()]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, customer);
        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);

        actv.setAdapter(Adapter);
        actv.setTextColor(Color.BLUE);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    class LoadProduct extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(BrandwiseProductSale.this);
            pDialog.setMessage("Loading Products ...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            String id = userName;
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", id));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_DCR, ServiceHandler.POST, params);

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
            if (pDialog.isShowing())
                pDialog.dismiss();
            producpopulatespinner();

        }

    }


    private void popSpinner() {
        List<String> description = new ArrayList<String>();
        for (int i = 0; i < categoriesList.size(); i++) {
            description.add(categoriesList.get(i).getId());
        }


    }


    public void finishActivity(View v) {
        finish();
    }


    class Spinner {
        private String TotalQ;
        private String TotalV;

        private void populateSpinner() {
            ArrayList<String> lables = new ArrayList<String>();
            ArrayList<Integer> quanty = new ArrayList<Integer>();
            ArrayList<String> value = new ArrayList<String>();
            ArrayList<String> achv = new ArrayList<String>();
            ArrayList<String> mpo_code = new ArrayList<String>();
            ArrayList<String> sale_value = new ArrayList<String>();
            ArrayList<String> target_value = new ArrayList<String>();
            ArrayList<String> growth_value = new ArrayList<String>();
            float achievment;
            String prod_rate, prod_vat, ppm_code, shift_code, growth_code;
            String mpo, quantity;

            for (int i = 0; i < categoriesList.size(); i++) {

                lables.add(categoriesList.get(i).getName());
                p_ids.add(categoriesList.get(i).getId());
                quanty.add(categoriesList.get(i).getQuantity());
                mpo = String.valueOf(categoriesList.get(i).getId());
                quantity = categoriesList.get(i).getP_CODE();
                prod_rate = String.valueOf((categoriesList.get(i).getPROD_RATE()));
                prod_vat = String.valueOf((categoriesList.get(i).getPROD_VAT()));
                ppm_code = String.valueOf((categoriesList.get(i).getPPM_CODE()));
                shift_code = String.valueOf((categoriesList.get(i).getP_CODE()));
                growth_code = String.valueOf((categoriesList.get(i).getSHIFT_CODE()));
                value.add(prod_rate);
                achv.add(prod_vat);
                mpo_code.add(mpo);
                sale_value.add(ppm_code);
                target_value.add(shift_code);
                growth_value.add(growth_code);
            }


            BrandwiseProductShowAdapter adapter = new BrandwiseProductShowAdapter(BrandwiseProductSale.this, lables, quanty,
                    value, achv, mpo_code, sale_value, target_value, growth_value);

            productListView.setAdapter(adapter);
        }

        private float round(float x, int i) {
            // TODO Auto-generated method stub
            return 0;
        }

        public String getTotalQ() {
            return TotalQ;
        }

        public String getTotalV() {
            return TotalV;
        }
    }

    private class GetCategories extends AsyncTask<Void, Void, Void> {
        String fromdate1 = fromdate.getText().toString();
        String todate1 = todate.getText().toString();
        Bundle b = getIntent().getExtras();
        String userName = b.getString("UserName");

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(BrandwiseProductSale.this);
            pDialog.setTitle("Data Loading !");
            pDialog.setMessage("Please Wait..");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            String UserName = b.getString("UserName");
            String id = userName;
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", id));
            params.add(new BasicNameValuePair("to_date", todate1));
            params.add(new BasicNameValuePair("p_code", p_code));
            params.add(new BasicNameValuePair("from_date", fromdate1));

            com.opl.pharmavector.ServiceHandler jsonParser = new com.opl.pharmavector.ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_PRODUCT_VIEW, com.opl.pharmavector.ServiceHandler.POST, params);
            Log.e("Response: ", "> " + json);
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj.getJSONArray("categories");
                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            com.opl.pharmavector.Category cat = new com.opl.pharmavector.Category(
                                    catObj.getString("sl"),
                                    catObj.getString("id"),
                                    catObj.getString("name"),
                                    catObj.getInt("quantity"),
                                    catObj.getString("PROD_RATE"),
                                    catObj.getString("PROD_VAT"),
                                    catObj.getString("PPM_CODE"),
                                    catObj.getString("P_CODE"),
                                    catObj.getString("SHIFT_CODE")
                            );
                            categoriesList.add(cat);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Toast.makeText(BrandwiseProductSale.this, "Nothing To Disply", Toast.LENGTH_SHORT).show();
                Toast.makeText(BrandwiseProductSale.this, "Please make a order first !", Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            BrandwiseProductSale.Spinner sp = new BrandwiseProductSale.Spinner();
            sp.populateSpinner();
            popSpinner();
            totqty.setText("");
            totval.setText("");
        }
    }

    private class GetBrandSale extends AsyncTask<Void, Void, Void> {

        String fromdate1 = fromdate.getText().toString();
        String todate1 = todate.getText().toString();
        Bundle b = getIntent().getExtras();
        String userName = b.getString("UserName");

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(BrandwiseProductSale.this);
            pDialog.setTitle("Data Loading !");
            pDialog.setMessage("Please Wait..");
            pDialog.setCancelable(false);

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            String UserName = b.getString("UserName");
            String id = userName;
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", id));
            params.add(new BasicNameValuePair("to_date", todate.getText().toString()));
            params.add(new BasicNameValuePair("p_code", p_code));
            params.add(new BasicNameValuePair("from_date", fromdate.getText().toString()));
            com.opl.pharmavector.ServiceHandler jsonParser = new com.opl.pharmavector.ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_PRODUCT_VIEW, com.opl.pharmavector.ServiceHandler.POST, params);
            Log.d("GetBrandSale: 2023", json);
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj.getJSONArray("categories");
                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            com.opl.pharmavector.Category cat = new com.opl.pharmavector.Category(
                                    catObj.getString("sl"),
                                    catObj.getString("id"),
                                    catObj.getString("name"),
                                    catObj.getInt("quantity"),
                                    catObj.getString("PROD_RATE"),
                                    catObj.getString("PROD_VAT"),
                                    catObj.getString("PPM_CODE"),
                                    catObj.getString("P_CODE"),
                                    catObj.getString("SHIFT_CODE")
                            );
                            categoriesList.add(cat);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
                Toast.makeText(BrandwiseProductSale.this, "Nothing To Disply", Toast.LENGTH_SHORT).show();
                Toast.makeText(BrandwiseProductSale.this, "Please make a order first !", Toast.LENGTH_LONG).show();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            BrandwiseProductSale.Spinner sp = new BrandwiseProductSale.Spinner();
            sp.populateSpinner();
            popSpinner();
            totqty.setText("");
            totval.setText("");
        }
    }

    @SuppressLint("StaticFieldLeak")
    class callserver extends AsyncTask<Void, Void, Void> {
        final JSONParser jsonParser = new JSONParser();
        final List<NameValuePair> params = new ArrayList<NameValuePair>();
        private ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(BrandwiseProductSale.this, "", "Wait....", true);
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            String URL_DOC_ADDRESS = BASE_URL+"productwisesales/check_flag.php";
            params.add(new BasicNameValuePair("select_fm_code", select_fm_code));
            JSONObject json = jsonParser.makeHttpRequest(URL_DOC_ADDRESS, "POST", params);
            if (json != null) {
                try {
                    check_flag = json.getString("PROD_GRP");
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            Thread backthred = new Thread(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    try {
                        if (!NetInfo.isOnline(getBaseContext())) {
                        } else{
                            new Handler(Looper.getMainLooper()).post(new Runnable(){
                                @Override
                                public void run() {
                                    if (check_flag.equals("G")){
                                        Intent i = new Intent(BrandwiseProductSale.this, SegmentSale.class);
                                        i.putExtra("mpo_code", select_fm_code);
                                        i.putExtra("fm_code", g_fm_code);
                                        i.putExtra("from_date", fromdate.getText().toString());
                                        i.putExtra("to_date", todate.getText().toString());
                                        i.putExtra("p_code", p_code);
                                        i.putExtra("report_flag", "B");
                                        startActivity(i);
                                    } else {

                                    }
                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            backthred.start();
        }
    }

    @Override
    public void onClick(View v) {

    }

    protected void onPostExecute() {

    }

    private void view() {
        Intent i = new Intent(BrandwiseProductSale.this, com.opl.pharmavector.Report.class);
        startActivity(i);
        finish();
    }
}

