package com.opl.pharmavector;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.opl.pharmavector.prescriptionsurvey.PrescroptionImageSearch;

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

public class Offlineupdateview extends Activity implements AdapterView.OnItemSelectedListener {
    private Button logout;
    private Spinner spinner1, spinner2, cashcredit, cashcredit_test, credit, onik;
    private TextView user_show;
    private SessionManager session;
    public Button next, back;
    SharedPreferences.Editor editor;
    public EditText osi, op, od, dateResult, ref;
    // private ListView cust;
    private ArrayList<Customer> customerlist;
    private Spinner cust;
    ProgressDialog pDialog;
    EditText mEdit;
    TextView date2, ded, cash_credt;
    public static String date = null, test;
    public int credit_party = 0, cash_party = 0;
    public String userName_1, userName, UserName, deli_tdate, deli_time, select_party;
    private String URL_CUSOTMER = BASE_URL+"get_ordno.php";
    protected Handler handler;
    private DatabaseHandler db;
    public String CUST_CODE1, pay_mode;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offlineupdateview);
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        logout = (Button) findViewById(R.id.logout);
        logout.setTypeface(fontFamily);
        logout.setText("\uf08b"); //&#xf08b
        user_show = (TextView) findViewById(R.id.user_show);
        Button vieworders = (Button) findViewById(R.id.view);
        Button updateorders = (Button) findViewById(R.id.update);
        vieworders.setTypeface(fontFamily);
        vieworders.setText("\uf060 "); //&#xf06e
        next = (Button) findViewById(R.id.next);
        next.setTypeface(fontFamily);
        next.setText("\uf061");  //&#xf061
        final TextView error_dt = (TextView) findViewById(R.id.errordt);
        final TextView error_payment = (TextView) findViewById(R.id.errorpayment);
        op = (EditText) findViewById(R.id.orderpage);
        cust = (Spinner) findViewById(R.id.customer);
        onik = (Spinner) findViewById(R.id.onik);
        cust.setPrompt("Select Customer");
        ref = (EditText) findViewById(R.id.reference);
        ded = (TextView) findViewById(R.id.deliverydate);

        cash_credt = (TextView) findViewById(R.id.cash_credt);
        final Spinner ampmspin = (Spinner) findViewById(R.id.ampm);
        cashcredit = (Spinner) findViewById(R.id.cashcredit);
        credit = (Spinner) findViewById(R.id.credit);
        cashcredit.setVisibility(View.GONE);
        credit.setVisibility(View.GONE);
        /*==================================================    Get Order details in spinner ======================================================*/
        customerlist = new ArrayList<Customer>();
        cust.setOnItemSelectedListener(this);
        db = new DatabaseHandler(Offlineupdateview.this);
        ArrayList<String> listname = db.getOfflineOrdDtl();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listname);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        onik = (Spinner) findViewById(R.id.onik);
        onik.setAdapter(adapter);
        final AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        actv.setAdapter(new ArrayAdapter<String>(Offlineupdateview.this,
                R.layout.onik, listname));
        actv.setThreshold(1);

        actv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                actv.showDropDown();
                return false;
            }
        });
        actv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (actv.getText().toString() != "") {
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

            }

            @Override
            public void afterTextChanged(final Editable s) {
                // TODO Auto-generated method stub
                try {
                    final String inputorder = s.toString();
                    int total_string = inputorder.length();

                    if (total_string > 0) {
                        String split1[] = inputorder.split("///");
                        pay_mode = split1[1];

                        String result2 = split1[0];
                        String split2[] = result2.split("#");
                        String asubstring = split2[1];
                        String ord_cust = split2[0];
                        String split3[] = ord_cust.split("//");
                        String CUST_CODE = split3[1];
                        String ord = split3[0];
                        CUST_CODE1 = CUST_CODE;
                        actv.setText(ord);
                        ded.setText(asubstring);
                        cash_credt.setText(pay_mode);
                        hideKeyBoard();
                    } else {
                        ded.setText("Select Date");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            private void hideKeyBoard() {
                InputMethodManager imm = (InputMethodManager) getSystemService(PrescroptionImageSearch.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }

            private void length() {
                // TODO Auto-generated method stub

            }


        });

        final TextView myTextView = (TextView) findViewById(R.id.user_show);
        Bundle b = getIntent().getExtras();
        String UserName = b.getString("UserName");
        String UserName_1 = b.getString("userName_1");
        ArrayList<String> userName_2 = db.getterritoryname();
        myTextView.setText(userName_2 + "(" + UserName + ")");
        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

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
                ded.setText(sdf.format(myCalendar.getTime()));
            }

        };

        ded.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(Offlineupdateview.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        vieworders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Thread mysells = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Bundle b = getIntent().getExtras();
                        String UserName = b.getString("UserName");
                        Intent i = new Intent(Offlineupdateview.this, Offlinereport.class);
                        String user = myTextView.getText().toString();
                        i.putExtra("UserName", UserName);
                        startActivity(i);
                    }
                });
                mysells.start();

            }
        });

        session = new SessionManager(getApplicationContext());
        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Thread server = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        JSONParser jsonParser = new JSONParser();
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("logout", "logout"));
                        JSONObject json = jsonParser.makeHttpRequest(Login.LOGIN_URL, "POST", params);

                    }
                });

                server.start();
                logoutUser();
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                Calendar c = Calendar.getInstance();
                int cYear = c.get(Calendar.YEAR);
                int cMonth = c.get(Calendar.MONTH) + 1;
                int cDay = c.get(Calendar.DAY_OF_MONTH);
                int gyear = myCalendar.get(Calendar.YEAR);
                int gmonth = myCalendar.get(Calendar.MONTH) + 1;
                if (gyear > cYear) {
                    gmonth = myCalendar.get(Calendar.MONTH) + 13;
                }
                int gday = myCalendar.get(Calendar.DAY_OF_MONTH);
                int gmonth_day = gmonth * 30;
                int totalday_given = gmonth_day + gday;
                int cmonth_day = cMonth * 30;
                int totalday_valid1 = cmonth_day + cDay;
                int totalday_valid = totalday_valid1 + 6;

                if (
                        (ded.getText().toString().trim().equals(""))
                                || (ded.getText().toString().trim().equals("Delivery Date"))
                                || (actv.getText().toString().trim().equals(""))
                                || (actv.getText().toString().trim().equals("Input Order (eg. dh..)"))
                ) {
                    ded.setError("Delivery Date  is required!");
                    actv.setError("Order number is  not Assigned !");
                } else if (totalday_given > totalday_valid) {
                    ded.setError("Delivery Date  is not more than 6 days");
                    error_dt.setText("Delivery Date  is not more than next 6 days! ");
                } else if (totalday_given < totalday_valid1) {
                    ded.setError("Delivery Date  is not more than 6 days");
                    error_dt.setText("Delivery Date  is not less current date! ");
                } else {
                    final String selected_ord = actv.getText().toString();
                    Bundle b = getIntent().getExtras();
                    String userName = b.getString("UserName");
                    String UserName_1 = b.getString("UserName_1");
                    Thread next = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Intent in = new Intent(Offlineupdateview.this, Offlineordupdate.class);
                            Bundle extras = new Bundle();
                            extras.putString("selected_ord", selected_ord);
                            extras.putString("CUST_CODE", CUST_CODE1);

                            extras.putString("AM_PM", ampmspin.getSelectedItem().toString());
                            extras.putString("cash_credit", pay_mode);
                            extras.putString("ORDER_DELEVERY_DATE", ded.getText().toString());
                            extras.putString("ORDER_REFERANCE_NO", ref.getText().toString());
                            Bundle b = getIntent().getExtras();
                            String userName = b.getString("UserName");
                            String UserName_1 = b.getString("UserName_1");
                            extras.putString("MPO_CODE", userName);
                            extras.putString("UserName_1", UserName_1);
                            in.putExtras(extras);
                            startActivity(in);
                        }
                    });
                    next.start();
                }
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menus, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                startActivity(new Intent(this, setting.class));
                return true;
            case R.id.help:
                startActivity(new Intent(this, Help.class));
                return true;
            case R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void populateSpinner() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < customerlist.size(); i++) {
            lables.add(customerlist.get(i).getName());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);
        cust.setAdapter(spinnerAdapter);
        String[] customer = lables.toArray(new String[lables.size()]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, customer);
        AutoCompleteTextView actv = findViewById(R.id.autoCompleteTextView1);
        actv.setThreshold(2);
        actv.setAdapter(Adapter);
        actv.setTextColor(Color.BLUE);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    private void logoutUser() {
        session.setLogin(false);
        session.invalidate();
        Intent intent = new Intent(Offlineupdateview.this, Login.class);
        startActivity(intent);
        finishActivity(BIND_ABOVE_CLIENT);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        // TODO Auto-generated method stub

    }

}
