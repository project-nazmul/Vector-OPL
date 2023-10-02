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


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
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

import androidx.appcompat.app.AppCompatActivity;

import com.kosalgeek.android.photoutil.MainActivity;

public class ManagersSalesFollowup2 extends AppCompatActivity implements OnClickListener, AdapterView.OnItemSelectedListener {
    private static Activity parent;
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    //array list for spinner adapter
    private ArrayList<CategoryNew> categoriesList;
    public ProgressDialog pDialog;
    ListView productListView;
    Button submit, submitBtn;
    //private EditText current_qnty;
    EditText qnty;
    Boolean result;
    EditText inputOne, inputtwo;
    public int success;
    public String message, ord_no;
    TextView date2, ded, fromdate, todate;
    int textlength = 0;
    public TextView totqty, totval, mpo_code, mpo_name;
    //public android.widget.Spinner ordspin;
    public String userName_1, userName, active_string, act_desiredString;
    public String from_date, to_date;
    com.opl.pharmavector.JSONParser jsonParser;
    List<NameValuePair> params;
    //public String CurrenCustomer="";
    //public AutoCompleteTextView actv;
    public static ArrayList<String> p_ids;
    public static ArrayList<Integer> p_quanty;
    public static ArrayList<String> PROD_RATE;
    public static ArrayList<String> PROD_VAT;
    private ArrayList<Customer> mpodcrlist;
    private ArrayList<String> array_sort = new ArrayList<String>();
    //private String URL_PRODUCT_VIEW ="http://opsonin.com.bd/dept_order_android_v2/ViewbyDate.php";
    private final String URL_PRODUCT_VIEW = BASE_URL+"salesfollowupreport/RMSalesFollowup.php";
    private final String URL_DCR = BASE_URL+"get_product_followup.php";
    private android.widget.Spinner cust;
    public String p_code, asm_flag, sm_flag, gm_flag, am_flag;
    private ArrayList<Customer> customerlist;
    public String query_code;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.amtargetquantity);
        setContentView(R.layout.mpoachvfollowup);

        screenShortProtect();
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        productListView = (ListView) findViewById(R.id.pListView);
        Button back_btn = (Button) findViewById(R.id.backbt);
        Button view_btn = (Button) findViewById(R.id.view);
        Button submitBtn = (Button) findViewById(R.id.submitBtn_manager);
        fromdate = (TextView) findViewById(R.id.fromdate);
        todate = (TextView) findViewById(R.id.todate);
        mpo_code = (TextView) findViewById(R.id.mpo_code);
        mpo_name = (TextView) findViewById(R.id.mpo_name);
        TextView sproduct_name = (TextView) findViewById(R.id.sproduct_name);
        TextView sqnty1 = (TextView) findViewById(R.id.sqnty1);
        TextView ssellvelue = (TextView) findViewById(R.id.ssellvelue);
        TextView achivement = (TextView) findViewById(R.id.achivement_sales_admin);
        TextView gval = (TextView) findViewById(R.id.gval);
        mpo_code.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        sproduct_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        sqnty1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        ssellvelue.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        achivement.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        gval.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        cust = (android.widget.Spinner) findViewById(R.id.dcrlist);
        mpodcrlist = new ArrayList<Customer>();
        cust.setOnItemSelectedListener(this);

        final AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        back_btn.setTypeface(fontFamily);
        back_btn.setText("\uf060 "); // &#xf060
        final LinearLayout ln = (LinearLayout) findViewById(R.id.totalshow);
        totqty = (TextView) findViewById(R.id.totalsellquantity);
        totval = (TextView) findViewById(R.id.totalsellvalue);
        //totqty.setText("Quantity");
        //totval.setText("Sellvalue");
        final int listsize = productListView.getChildCount();
        final String listvalue = productListView.toString();
        Log.i("Size of ProductLIstview", "ProductLIstView SIZE: " + listsize);
        p_ids = new ArrayList<String>();
        p_quanty = new ArrayList<Integer>();
        PROD_RATE = new ArrayList<String>();
        PROD_VAT = new ArrayList<String>();
        categoriesList = new ArrayList<CategoryNew>();

        Bundle b = getIntent().getExtras();
        String userName = b.getString("UserName");
        String UserName_2 = b.getString("UserName_2");
        query_code = b.getString("query_code");
        fromdate.setText(b.getString("from_date"));
        todate.setText(b.getString("to_date"));
        Log.e("tttttttttttttt----->", b.getString("to_date"));
        asm_flag = b.getString("asm_flag");
        sm_flag = b.getString("sm_flag");
        gm_flag = b.getString("gm_flag");

        if (am_flag == null) {
            am_flag = "N";
        } else {
            am_flag = "Y";
        }

        if (asm_flag.equals("Y")) {
            mpo_code.setText("Region\nCode");
            mpo_name.setText("Region\nName");
            productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    query_code = (String) productListView.getAdapter().getItem(position);
                    Log.i("Selected Item in list", query_code);

                    Intent i = new Intent(ManagersSalesFollowup2.this, AMwiseProductSale2.class);
                    i.putExtra("sm_flag", "N");
                    i.putExtra("asm_flag", "N");
                    i.putExtra("gm_flag", "N");
                    i.putExtra("am_flag", "Y");
                    i.putExtra("UserName", query_code);
                    i.putExtra("query_code", query_code);
                    i.putExtra("UserName", query_code);
                    i.putExtra("UserName_1", query_code);
                    i.putExtra("UserName_2", query_code);
                    i.putExtra("userName", query_code);
                    i.putExtra("UserName", query_code);
                    i.putExtra("query_code", query_code);
                    i.putExtra("to_date", todate.getText().toString());
                    i.putExtra("from_date", fromdate.getText().toString());
                    //Toast.makeText(ManagersSalesFollowup2.this, "ManagerSalesFollowup2-AMwiseProductSale2", Toast.LENGTH_LONG).show();
                    startActivity(i);
                }
            });
        } else if (sm_flag.equals("Y")) {
            mpo_code.setText("Zone\nCode");
            mpo_name.setText("Zone\nName");

            productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    query_code = (String) productListView.getAdapter().getItem(position);
                    Log.i("Selected Item in list", query_code);

                    Intent i = new Intent(ManagersSalesFollowup2.this, ManagersSalesFollowup2.class);
                    i.putExtra("sm_flag", "N");
                    i.putExtra("asm_flag", "Y");
                    i.putExtra("gm_flag", "N");
                    i.putExtra("UserName", query_code);
                    i.putExtra("query_code", query_code);
                    i.putExtra("to_date", todate.getText().toString());
                    i.putExtra("from_date", fromdate.getText().toString());
                    startActivity(i);
                }
            });
        } else if (gm_flag.equals("Y")) {
            mpo_code.setText("Division\nCode");
            mpo_name.setText("Division\nName");

            productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    query_code = (String) productListView.getAdapter().getItem(position);
                    Log.i("Selected Item in list", query_code);

                    Intent i = new Intent(ManagersSalesFollowup2.this, ManagersSalesFollowup2.class);
                    i.putExtra("sm_flag", "Y");
                    i.putExtra("asm_flag", "N");
                    i.putExtra("gm_flag", "N");
                    i.putExtra("UserName", query_code);
                    i.putExtra("query_code", query_code);
                    i.putExtra("to_date", todate.getText().toString());
                    i.putExtra("from_date", fromdate.getText().toString());
                    startActivity(i);
                }
            });
        }

/*
        Calendar c_todate = Calendar.getInstance();
        //System.out.println("Current time => "+c.getTime());
        SimpleDateFormat dftodate = new SimpleDateFormat("dd/MM/yyyy");
        String current_todate = dftodate.format(c_todate.getTime());
        todate.setText(current_todate);


        Calendar c_fromdate = Calendar.getInstance();
        //System.out.println("Current time => "+c.getTime());
        SimpleDateFormat dffromdate = new SimpleDateFormat("01/MM/yyyy");
        String current_fromdate = dffromdate.format(c_fromdate.getTime());
        fromdate.setText(current_fromdate);

*/
        new GetCategories().execute();

        customerlist = new ArrayList<Customer>();
        cust.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        actv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (actv.getText().toString() != "") {
                    String selectedcustomer = actv.getText().toString();
                    System.out.println("Selectedcustomer = " + selectedcustomer);
                    cust.setTag(selectedcustomer);
                }

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
                //actv.setTextColor(Color.BLACK);
            }

            @Override
            public void afterTextChanged(final Editable s) {
                // TODO Auto-generated method stub
                try {

                    //actv.setError("");
                    final String inputorder = s.toString();
                    int total_string = inputorder.length();

                    if (inputorder.indexOf("//") != -1) {

                        Log.w("product_code", "> " + "product_code : ->  " + inputorder);


                        String arr[] = inputorder.split("//");
                        String product_name = arr[0].trim();
                        String product_code = arr[1].trim();


                        p_code = product_code;

                        Log.w("product_code", "> " + "product_code : ->  " + product_code);

                        Log.w("product_code", "> " + "product_code : ->  " + product_code);


                        actv.setText(product_name);

                    } else {
                        //ded.setText("Select Date");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            private void length() {
                // TODO Auto-generated method stub

            }


        });


        // new LoadProduct().execute();

        //new ViewbyDate.GetCategories().execute();
        /*------------------------------------------------------*/
        /*--------------------------from-date range----------------------------*/
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
                //String myFormat = "dd/MM/yyyy";
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
                new DatePickerDialog(ManagersSalesFollowup2.this, date_form, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
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
                //String myFormat = "dd/MM/yyyy";
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

                new DatePickerDialog(ManagersSalesFollowup2.this, date_to, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        /*---------------------------to date range-----------------end-----------*/
        back_btn.setOnClickListener(new OnClickListener() {
            Bundle b = getIntent().getExtras();
            //UserName = b.getString("UserName");
            //UserName_1= b.getString("UserName_1");
            //UserName_2= b.getString("UserName_2");
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
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

        submitBtn.setOnClickListener(new OnClickListener() {
            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");

            @Override
            public void onClick(final View v) {
                try {
                    String fromdate1 = fromdate.getText().toString();
                    String todate1 = todate.getText().toString();
                    System.out.println("else  fromdate1 " + fromdate1);
                    System.out.println("query_code" + query_code);

                    if (fromdate1.isEmpty() || (fromdate1.equals("From Date")) || (fromdate1.equals("From Date is required"))) {
                        //fromdate.setError( "From Date is required!" );
                        fromdate.setText("From Date is required");
                        fromdate.setTextColor(Color.RED);
                    } else if (todate1.isEmpty() || (todate1.equals("To Date")) || (todate1.equals("To Date is required"))) {
                        //todate.setError( "To Date is required!" );
                        todate.setText("To Date is required");
                        todate.setTextColor(Color.RED);
                    } else {
                        System.out.println("after text change elsfromdate1eeeeeeeee" + fromdate1);
                        System.out.println("elsetodate1 " + todate1);
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
            public void onClick(View v) {

            }
        });
    }

    private void screenShortProtect() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
    }

    private void producpopulatespinner() {
        List<String> lables = new ArrayList<String>();

        for (int i = 0; i < customerlist.size(); i++) {
            lables.add(customerlist.get(i).getName());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lables);
        cust.setAdapter(spinnerAdapter);
        String[] customer = lables.toArray(new String[lables.size()]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, customer);
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
            pDialog = new ProgressDialog(ManagersSalesFollowup2.this);
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

            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    JSONArray customer = jsonObj.getJSONArray("customer");
                    for (int i = 0; i < customer.length(); i++) {
                        JSONObject catObj = (JSONObject) customer.get(i);
                        Customer custo = new Customer(catObj.getInt("id"), catObj.getString("name"));
                        customerlist.add(custo);
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
            Log.d("Changepa---ssword", categoriesList.get(i).getId());
            description.add(categoriesList.get(i).getId());
            Log.d("Changep---assword", "Login" + categoriesList.get(i).getId());
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
            //MPOwiseProductSaleShowAdapter adapter = new MPOwiseProductSaleShowAdapter(ManagersSalesFollowup2.this,lables, quanty,value,achv,mpo_code);
            MPOwiseAchvfollowupAdapter2 adapter = new MPOwiseAchvfollowupAdapter2(ManagersSalesFollowup2.this, lables,
                    quanty, value, achv, mpo_code, ff_names, growth_val, mon_growth, cum_growth);
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
            pDialog = new ProgressDialog(ManagersSalesFollowup2.this);
            pDialog.setTitle("Data Loading !");
            pDialog.setMessage("Please Wait..");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", query_code));
            params.add(new BasicNameValuePair("to_date", todate1));
            params.add(new BasicNameValuePair("from_date", fromdate1));
            params.add(new BasicNameValuePair("asm_flag", asm_flag));
            params.add(new BasicNameValuePair("sm_flag", sm_flag));
            params.add(new BasicNameValuePair("gm_flag", gm_flag));

            com.opl.pharmavector.ServiceHandler jsonParser = new com.opl.pharmavector.ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_PRODUCT_VIEW, com.opl.pharmavector.ServiceHandler.POST, params);
            Log.e("Response: ", "> " + json);

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
            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
                Toast.makeText(ManagersSalesFollowup2.this, "Nothing To Disply", Toast.LENGTH_SHORT).show();
                Toast.makeText(ManagersSalesFollowup2.this, "Please make a order first !", Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            ManagersSalesFollowup2.Spinner sp = new ManagersSalesFollowup2.Spinner();
            sp.populateSpinner();
            popSpinner();
            totqty.setText("");
            totval.setText("");
            //totqty.setText("Total target quantity="+sp.getTotalQ());
            //totval.setText("Total Sales quantity="+sp.getTotalV());
        }
    }
    /*------------- list items on click event----------------*/

    @Override
    public void onClick(View v) {

    }

    protected void onPostExecute() {

    }

    private void view() {
        Intent i = new Intent(ManagersSalesFollowup2.this, com.opl.pharmavector.Report.class);
        startActivity(i);
        finish();
    }
}







