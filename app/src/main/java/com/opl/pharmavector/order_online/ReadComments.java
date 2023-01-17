package com.opl.pharmavector.order_online;
import static com.nativecss.enums.RemoteContentRefreshPeriod.Never;
import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Build;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;

import com.nativecss.NativeCSS;
import com.opl.pharmavector.Contact;
import com.opl.pharmavector.Customer;
import com.opl.pharmavector.Dashboard;
import com.opl.pharmavector.DatabaseHandler;
import com.opl.pharmavector.JSONParser;
import com.opl.pharmavector.R;
import com.opl.pharmavector.Report;
import com.opl.pharmavector.ServiceHandler;
import com.opl.pharmavector.SessionManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;

public class ReadComments extends Activity implements OnItemSelectedListener {

    private Spinner spinner1, spinner2, cashcredit, cashcredit_test, credit;
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_MESSAGE_1 = "message_1";
    public static final String TAG_invoice = "invoice";
    public static final String TAG_target = "target";
    public static final String TAG_achivement = "achivement";
    public static final String TAG_MESSAGE_new_version = "new_version";
    private Button logout, achivbtn;
    private TextView user_show, newversion;
    private SessionManager session;
    public String get_ext_dt3, get_ext_dt4;
    public Button next, back;
    public static String name = null, newversion_text = null, ename = null;
    public int credit_party = 0, cash_party = 0;
    Editor editor;
    public EditText osi, op, od, dateResult, ref;
    private ArrayList<Customer> customerlist;
    private ArrayList<Customer> amtimelist;
    private ArrayList<Customer> pmtimelist;
    public Array pay_cash;
    public Array pay_cradit;
    private DatabaseHandler db;
    private String f_name, s_name;
    private Button mOffline;
    private ListView lv;
    private Contact dataModel;
    Toast toast;
    Toast toast2;
    Toast toast3;
    public String am_time_flag, pm_time_flag, next_day_time_flag;
    public String pay_cash1, userName, userName_1, UserName_2;
    public String pay_credit1;
    public String webtime1, webtime2;
    private Spinner cust;
    ProgressDialog pDialog;
    EditText mEdit;
    TextView date2, ded, note, cust_status;
    public int success;
    public String message, ord_no, invoice, target_data, achivement, searchString, select_party, select_party_new;

    private String URL_CUSOTMER = BASE_URL+"order_online/get_customer.php";
    private String URL_AM_TIME = BASE_URL+"order_am_time.php";
    private String URL_PM_TIME = BASE_URL+"order_pm_time.php";

    public String cust_code_name_arr, pay_mode_new, cust_detail_new;
    public String cust_code_new = "0";
    public SimpleDateFormat timeFormat;
    protected Handler handler;
    public static String CustomerCode;
    TextView error_dt,error_payment,target,achiv,inv,ordno,succ_msg,myTextView;
    Button vieworders,updateorders;
    LinearLayout mainlayout;
    Spinner ampmspin;
    AutoCompleteTextView actv;
    Calendar myCalendar;
    @SuppressLint("CutPasteId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.readcomments_new);
        initViews();
        new GetCategories().execute();
        customerInit();

        calendarInit();
        /*
        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view,
                                  int year,
                                  int monthOfYear,
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
                error_dt.setText("");
                ded.setText(sdf.format(myCalendar.getTime()));
            }
        };
        ded.setOnClickListener(new OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ReadComments.this, date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        */


        mOffline.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Thread mysells = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        Intent i = new Intent(ReadComments.this, Dashboard.class);
                        i.putExtra("UserName", Dashboard.globalmpocode);
                        i.putExtra("new_version", Dashboard.new_version);
                        i.putExtra("UserName_2", Dashboard.globalterritorycode);
                        i.putExtra("message_3", Dashboard.message_3);
                        i.putExtra("password", Dashboard.password);
                        i.putExtra("ff_type", Dashboard.ff_type);
                        i.putExtra("vector_version", R.string.vector_version);
                        i.putExtra("emp_code", Dashboard.globalempCode);
                        i.putExtra("emp_name", Dashboard.globalempName);

                        startActivity(i);

                    }
                });
                mysells.start();

            }
        });
        vieworders.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Thread mysells = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub

                        Bundle b = getIntent().getExtras();
                        String userName = b.getString("UserName");
                        String userName_1 = b.getString("UserName_1");
                        String userName_2 = b.getString("UserName_2");
                        Intent i = new Intent(ReadComments.this, Report.class);


                        String user = myTextView.getText().toString();
                        i.putExtra("UserName", userName);
                        i.putExtra("userName_1", userName_1);
                        i.putExtra("userName_2", userName_2);
                        System.out.println("userName " + userName);
                        System.out.println("userName_1 " + userName_1);
                        startActivity(i);


                    }
                });
                mysells.start();

            }
        });


        session = new SessionManager(getApplicationContext());
        logout.setOnClickListener(new OnClickListener() {
            Bundle b = getIntent().getExtras();
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub

                        try {

/*
                            Intent i = new Intent(ReadComments.this, Dashboard.class);
                            i.putExtra("UserName", userName);
                            i.putExtra("new_version", userName);
                            i.putExtra("UserName_1", UserName_2);
                            i.putExtra("UserName_2", UserName_2);
                            i.putExtra("userName", userName);
                            i.putExtra("new_version", userName);
                            i.putExtra("userName_1", UserName_2);
                            i.putExtra("userName_2", UserName_2);
                            i.putExtra("ff_type", Dashboard.ff_type);

                            startActivity(i);
                            */

                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });

                backthred.start();


            }
        });



        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if ((ded.getText().toString().trim().equals("")) || (ded.getText().toString().trim().equals("Delivery Date")) || (ded.getText().toString().trim().equals("Please Select date"))) {

                    ded.setTextSize(14);
                    ded.setText("Please Select date");
                    ded.setTextColor(Color.RED);
                }else if ((actv.getText().toString().trim().equals("")) || (actv.getText().toString().trim().equals("Input Customer (eg. dh..)")) || (actv.getText().toString().length() < 3)) {
                    actv.setError("Customer not Assigned !");
                } else if (cust_code_new.equals("0")) {
                    actv.setError("Select valid customer !");
                }  else {
                    if (cash_party == 1) {
                        select_party_new = "CASH";
                    } else {
                        select_party_new = "CREDIT";
                    }

                    if (select_party_new.equals("Select payment mode")) {
                        error_dt.setText("Please Select payment mode by click! ");
                        error_payment.setError("Please Select payment mode by click!");
                    } else {

                        final Spinner nameSpinner =  findViewById(R.id.customer);
                        final String selected_cust = actv.getText().toString();
                        final String select_party1 = select_party_new.toString();
                        Bundle b = getIntent().getExtras();
                        String userName = b.getString("UserName");
                        String UserName_1 = b.getString("UserName_1");


                        Thread next = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Intent in = new Intent(ReadComments.this, ProductOrdernew.class);
                                Bundle extras = new Bundle();
                                extras.putString("CUST_CODE", cust_code_new);
                                extras.putString("AM_PM", ampmspin.getSelectedItem().toString());
                                extras.putString("cash_credit", select_party_new);
                                extras.putString("ORDER_DELEVERY_DATE", ded.getText().toString());
                                extras.putString("ORDER_REFERANCE_NO", ref.getText().toString());
                                Bundle b = getIntent().getExtras();
                                String userName = b.getString("UserName");
                                String UserName_1 = b.getString("UserName_1");
                                extras.putString("MPO_CODE", userName);
                                extras.putString("UserName_1", UserName_1);
                                extras.putString("CUST_CODE", cust_code_new);
                                extras.putString("AM_PM", ampmspin.getSelectedItem().toString());
                                extras.putString("cash_credit", select_party_new);
                                extras.putString("ORDER_DELEVERY_DATE", ded.getText().toString());
                                extras.putString("ORDER_REFERANCE_NO", ref.getText().toString());
                                in.putExtras(extras);
                                startActivity(in);

                            }
                        });

                        next.start();

                    }

                }


            }
        });

    }


    private void calendarInit() {

        myCalendar = Calendar.getInstance();
        ded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(ded);
            }
        });
    }

    private void showDatePicker(TextView textView) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            textView.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() +(1000*60*60*24*6) );
        datePickerDialog.show();
    }



    private void customerInit() {

        actv =  findViewById(R.id.autoCompleteTextView1);
        actv.setFocusableInTouchMode(true);
        actv.setFocusable(true);
        actv.requestFocus();
        actv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub
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


                    final String inputorder = s.toString();
                    int total_string = inputorder.length();
                    if (inputorder.indexOf(":") != -1) {
                        String cust_type = inputorder.substring(inputorder.indexOf(":") + 1);
                        String cust_type_with_note = inputorder.substring(inputorder.indexOf(":") + 0);
                        String cust_type_initial = inputorder.substring(inputorder.indexOf(":") + 0);

                        String first_split[] = inputorder.split(":");
                        cust_code_name_arr = first_split[0].trim();
                        pay_mode_new = first_split[1].trim();
                        String second_split[] = cust_code_name_arr.split("//");
                        cust_code_new = second_split[0].trim();
                        cust_detail_new = second_split[1].trim();
                        actv.setText(cust_detail_new);
                        cust_status.setText(pay_mode_new);
                        CustomerCode=cust_code_new;
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(actv.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                        if (pay_mode_new.equals("CASH")) {
                            cash_party = 1;
                            credit_party = 0;
                        } else {
                            cash_party = 0;
                            credit_party = 1;
                        }
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
    private void initViews() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        logout =  findViewById(R.id.logout);
        logout.setTypeface(fontFamily);
        logout.setText("\uf08b"); //&#xf08b
        newversion =  findViewById(R.id.newversion);
        mOffline =  findViewById(R.id.online);
        cust_status = findViewById(R.id.cust_status);
        vieworders =  findViewById(R.id.view);
        updateorders =  findViewById(R.id.update);
        achivbtn =  findViewById(R.id.achivbtn);
        target = findViewById(R.id.target);
        achiv =  findViewById(R.id.achivement);
        inv =  findViewById(R.id.invoice);
        updateorders.setTypeface(fontFamily);
        updateorders.setText("\uf044");    //ï�„ fa-edit (alias) [&#xf044;]
        vieworders.setTypeface(fontFamily);
        vieworders.setText("\uf06e"); //&#xf06e
        next =  findViewById(R.id.next);
        next.setTypeface(fontFamily);
        next.setText("\uf061");  //&#xf061
        achivbtn.setTypeface(fontFamily);
        achivbtn.setText("\uf080");
        error_dt =  findViewById(R.id.errordt);
        error_payment =  findViewById(R.id.errorpayment);
        newversion.setTypeface(fontFamily);
        newversion.setText("\uf080"); //&#xf080
        op =  findViewById(R.id.orderpage);
        cust =  findViewById(R.id.customer);
        cust.setPrompt("Select Customer");
        ref =  findViewById(R.id.reference);
        ded =  findViewById(R.id.deliverydate);
        ordno = findViewById(R.id.ordno);
        succ_msg =  findViewById(R.id.succ_msg);
        note = findViewById(R.id.note);
        mainlayout = findViewById(R.id.successmsg);
        ampmspin =  findViewById(R.id.ampm);
        cashcredit =  findViewById(R.id.cashcredit);
        credit =  findViewById(R.id.credit);
        cashcredit.setVisibility(View.GONE);
        credit.setVisibility(View.GONE);
        customerlist = new ArrayList<Customer>();
        amtimelist = new ArrayList<Customer>();
        pmtimelist = new ArrayList<Customer>();
        cust.setOnItemSelectedListener(this);
        myTextView = findViewById(R.id.user_show);
        newversion =  findViewById(R.id.newversion);
        Bundle b = getIntent().getExtras();

        name = b.getString("UserName_1");
        ename = b.getString("UserName_2");
        newversion_text = b.getString("new_version");
        newversion.setText(newversion_text);
        if (b.isEmpty()) {
            String userName = "";
            myTextView.setText(userName);
        } else {
            userName = b.getString("UserName");
            UserName_2 = b.getString("UserName_2");
            myTextView.setText(UserName_2 + "(" + userName + ")");
            String ordernumber = b.getString("Ord_NO");
            String invoice = b.getString("target");
            String tar = b.getString("invoice");
            String achivement = b.getString("achivement");

            if (ordernumber == null) {
                mainlayout.setVisibility(LinearLayout.GONE);
            } else {
                ordno.setText("Ord No." + ordernumber);
                ordno.setTextSize(10);
                succ_msg.setText("Submitted.");
                String mpo[] = ordernumber.split("-");
                myTextView.setText(Dashboard.globalterritorycode+"("+Dashboard.globalmpocode+")");
                achiv.setText(achivement);
                target.setText(tar);
                inv.setText(invoice);
            }
        }
    }
    private void populateSpinner() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < customerlist.size(); i++) {
            lables.add(customerlist.get(i).getName());
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, lables);
        cust.setAdapter(spinnerAdapter);
        String[] customer = lables.toArray(new String[lables.size()]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, customer);
        AutoCompleteTextView actv =  findViewById(R.id.autoCompleteTextView1);
        actv.setAdapter(Adapter);
        actv.setTextColor(Color.parseColor("#006199"));
    }
    class GetCategories extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ReadComments.this);
            pDialog.setMessage("Fetching Customer..");
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
            String json = jsonParser.makeServiceCall(URL_CUSOTMER, ServiceHandler.POST, params);
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
            populateSpinner();
        }

    }
    private void populateSpinnerAM() {
        am_time_flag = "0";
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < amtimelist.size(); i++) {
            lables.add(amtimelist.get(i).getName());
            am_time_flag = amtimelist.get(i).getName();
        }

        String am_time_conv = am_time_flag;
        String time = am_time_conv;// "15:30:18";
        DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        try {
            Date date = sdf.parse(time);
            timeFormat = new SimpleDateFormat("HH:mm");
            webtime1 = timeFormat.format(date);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    class GetAMTime extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            String id = userName;

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", id));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_AM_TIME, ServiceHandler.POST, params);

            //Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray customer = jsonObj.getJSONArray("customer");
                        for (int i = 0; i < customer.length(); i++) {
                            JSONObject catObj = (JSONObject) customer.get(i);
                            Customer custo = new Customer(catObj.getInt("id"), catObj.getString("name"));
                            amtimelist.add(custo);
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
            //if (pDialog.isShowing())
            //pDialog.dismiss();
            populateSpinnerAM();
        }

    }
    private void populateSpinnerPM() {
        pm_time_flag = "0";
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < pmtimelist.size(); i++) {
            lables.add(pmtimelist.get(i).getName());
            pm_time_flag = pmtimelist.get(i).getName();
        }


        String pm_time_conv = pm_time_flag;
        String time2 = pm_time_conv;// "15:30:18";
        DateFormat sdf = new SimpleDateFormat("hh:mm:ss");


        try {

            Date date2 = sdf.parse(time2);
            timeFormat = new SimpleDateFormat("HH:mm");
            webtime2 = timeFormat.format(date2);
           // System.out.println("webtime2" + webtime2);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    class GetPMTime extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            String id = userName;

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", id));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_PM_TIME, ServiceHandler.POST, params);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray customer = jsonObj.getJSONArray("customer");
                        for (int i = 0; i < customer.length(); i++) {
                            JSONObject catObj = (JSONObject) customer.get(i);
                            Customer custo = new Customer(catObj.getInt("id"), catObj.getString("name"));
                            pmtimelist.add(custo);
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
            populateSpinnerPM();
        }

    }


    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        // TODO Auto-generated method stub

    }

}
