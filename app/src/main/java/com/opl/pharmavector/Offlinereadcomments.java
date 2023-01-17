package com.opl.pharmavector;

import static com.nativecss.enums.RemoteContentRefreshPeriod.Never;
import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Color;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;

import com.nativecss.NativeCSS;
import com.opl.pharmavector.R;
import com.opl.pharmavector.prescriptionsurvey.PrescroptionImageSearch;
import com.opl.pharmavector.util.NetInfo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class Offlinereadcomments extends Activity implements OnItemSelectedListener {
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
    public Button next, back;
    public static String name = null, newversion_text = null;
    public int credit_party = 0, cash_party = 0;
    Editor editor;
    public EditText osi, op, od, dateResult, ref;
    // private ListView cust;
    private ArrayList<Customer> customerlist;
    Spinner onik;
    private DatabaseHandler db;
    Toast toast;
    private Spinner cust;
    ProgressDialog pDialog;
    EditText mEdit;
    TextView date2, ded, cust_status;
    public int success;
    public String message, ord_no, invoice, target_data, achivement, searchString, select_party, select_party_new;
    private String URL_CUSOTMER = BASE_URL+"get_customer.php";
    private String URL_Achievement = BASE_URL+"Achievement.php";
    private Button offline;
    public String cust_code_name_arr, pay_mode_new, cust_code_new, cust_detail_new;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offlinereadcomments);
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        logout = (Button) findViewById(R.id.logout);
        logout.setTypeface(fontFamily);
        logout.setText("\uf08b"); //&#xf08b
        user_show = (TextView) findViewById(R.id.user_show);
        newversion = (TextView) findViewById(R.id.newversion);
        Button vieworders = (Button) findViewById(R.id.view);
        Button updateorders = (Button) findViewById(R.id.update);
        achivbtn = (Button) findViewById(R.id.achivbtn);
        offline = (Button) findViewById(R.id.offline);
        TextView target = (TextView) findViewById(R.id.target);
        TextView achiv = (TextView) findViewById(R.id.achivement);
        TextView inv = (TextView) findViewById(R.id.invoice);
        target.setTypeface(fontFamily);
        target.setText("\uf080"); //&#xf080
        achiv.setTypeface(fontFamily);
        achiv.setText("\uf080");
        inv.setTypeface(fontFamily);
        inv.setText("\uf080");
        updateorders.setTypeface(fontFamily);
        updateorders.setText("\uf044");    //ï�„ fa-edit (alias) [&#xf044;]
        vieworders.setTypeface(fontFamily);
        vieworders.setText("\uf06e"); //&#xf06e
        next = (Button) findViewById(R.id.next);
        next.setTypeface(fontFamily);
        next.setText("\uf061");  //&#xf061
        achivbtn.setTypeface(fontFamily);
        achivbtn.setText("\uf080");
        offline.setTypeface(fontFamily);
        final TextView error_dt = (TextView) findViewById(R.id.errordt);
        final TextView error_payment = (TextView) findViewById(R.id.errorpayment);
        newversion.setTypeface(fontFamily);
        op = (EditText) findViewById(R.id.orderpage);
        cust = (Spinner) findViewById(R.id.customer);
        cust.setPrompt("Select Customer");
        ref = (EditText) findViewById(R.id.reference);
        ded = (TextView) findViewById(R.id.deliverydate);
        TextView ordno = (TextView) findViewById(R.id.ordno);
        TextView succ_msg = (TextView) findViewById(R.id.succ_msg);
        LinearLayout mainlayout = (LinearLayout) findViewById(R.id.successmsg);
        cust_status = (TextView) findViewById(R.id.cust_status);
        db = new DatabaseHandler(this);
        toast = Toast.makeText(Offlinereadcomments.this, "Button is clicked for offline database", Toast.LENGTH_LONG);
        final Spinner ampmspin = (Spinner) findViewById(R.id.ampm);
        cashcredit = (Spinner) findViewById(R.id.cashcredit);
        credit = (Spinner) findViewById(R.id.credit);
        cashcredit.setVisibility(View.GONE);
        credit.setVisibility(View.GONE);
        ArrayList<String> listname = db.getALLCUSTOMERS();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listname);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        onik = (Spinner) findViewById(R.id.onik);
        onik.setAdapter(adapter);
        customerlist = new ArrayList<Customer>();
        cust.setOnItemSelectedListener(this);
        final AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        actv.setAdapter(new ArrayAdapter<String>(Offlinereadcomments.this, R.layout.spinner_item, listname));
        actv.setThreshold(1);

        actv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                actv.showDropDown();
                return false;
            }
        });

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
                        hideKeyBoard();
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

        final TextView myTextView = (TextView) findViewById(R.id.user_show);
        final TextView newversion = (TextView) findViewById(R.id.newversion);
        Bundle b = getIntent().getExtras();
        name = b.getString("UserName");
        newversion_text = b.getString("new_version");
        newversion.setText(newversion_text);
        if (b.isEmpty()) {
            String userName = "";
            myTextView.setText(userName);

        } else {
            String userName = b.getString("UserName");
            ArrayList<String> userName_1 = db.getterritoryname();
            String username_2 = userName_1.toString();
            myTextView.setText(username_2 + "(" + userName + ")");
            String ordernumber = b.getString("Ord_NO");
            String invoice = b.getString("target");
            String tar = b.getString("invoice");
            String achivement = b.getString("achivement");
            if (ordernumber == null) {
                mainlayout.setVisibility(LinearLayout.GONE);
            } else {
                String username_3 = userName_1.toString();
                myTextView.setText(username_3 + "(" + userName + ")");
                ordno.setText("Ord No." + ordernumber);
                succ_msg.setText("Submitted");
                achiv.setText(achivement);
                target.setText(tar);
                inv.setText(invoice);
            }
        }

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

        ded.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Offlinereadcomments.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
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
                        //Intent sellview=new Intent(ReadComments.this, Sellview.class);
                        Bundle b = getIntent().getExtras();
                        String userName = b.getString("UserName");
                        String userName_1 = b.getString("UserName_1");
                        String userName_2 = b.getString("UserName_2");
                        Intent i = new Intent(Offlinereadcomments.this, Report.class);
                        String user = myTextView.getText().toString();
                        i.putExtra("UserName", userName);
                        i.putExtra("userName_1", userName_1);
                        i.putExtra("userName_2", userName_2);
                        startActivity(i);
                    }
                });
                mysells.start();

            }
        });
        achivbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetInfo.isOnline(getBaseContext())) {

                    final JSONParser jsonParser = new JSONParser();
                    final List<NameValuePair> params = new ArrayList<NameValuePair>();
                    Bundle b = getIntent().getExtras();
                    String userName = b.getString("UserName");
                    params.add(new BasicNameValuePair("id", userName));
                    Thread server = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            JSONObject json = jsonParser.makeHttpRequest(URL_Achievement, "POST", params);
                            try {
                                success = json.getInt(TAG_SUCCESS);
                                message = json.getString(TAG_MESSAGE);
                                invoice = json.getString(TAG_invoice);
                                target_data = json.getString(TAG_target);
                                achivement = json.getString(TAG_achivement);
                                if (success == 1) {

                                } else {
                                    SaveToDataBase();
                                }

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            Intent in = getIntent();
                            Intent inten = getIntent();
                            Bundle bundle = in.getExtras();
                            inten.getExtras();
                            String MPO_CODE = bundle.getString("MPO_CODE");
                            String userName_1 = bundle.getString("userName_1");
                            Intent sameint = new Intent(Offlinereadcomments.this, Achievement.class);
                            sameint.putExtra("UserName", MPO_CODE);
                            sameint.putExtra("Ord_NO", message);
                            sameint.putExtra("invoice", invoice);
                            sameint.putExtra("target", target_data);
                            sameint.putExtra("achivement", achivement);
                            sameint.putExtra("userName_1", name);
                            startActivity(sameint);
                        }

                        private void SaveToDataBase() {
                            // TODO Auto-generated method stub
                        }

                    });
                    server.start();
                } else {
                    Toast.makeText(getBaseContext(), "Connect online",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        offline.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Thread mysells = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        db = new DatabaseHandler(Offlinereadcomments.this);
                        Bundle b = getIntent().getExtras();
                        String userName = b.getString("UserName");
                        Intent i = new Intent(Offlinereadcomments.this, Offlinereport.class);
                        String user = myTextView.getText().toString();
                        i.putExtra("UserName", userName);
                        startActivity(i);

                    }
                });
                mysells.start();

            }
        });
        updateorders.setOnClickListener(new OnClickListener() {
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
                        Intent i = new Intent(Offlinereadcomments.this, Updateview.class);
                        i.putExtra("UserName", userName);
                        i.putExtra("userName_1", userName_1);
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
                            Bundle b = getIntent().getExtras();
                            String userName = b.getString("UserName");
                            String UserName_1 = b.getString("userName_1");
                            String UserName_2 = b.getString("userName_2");
                            Intent i = new Intent(Offlinereadcomments.this, Dashboard.class);
                            i.putExtra("UserName", userName);
                            i.putExtra("new_version", userName);
                            i.putExtra("UserName_1", UserName_1);
                            i.putExtra("UserName_2", UserName_2);
                            i.putExtra("userName", userName);
                            i.putExtra("new_version", userName);
                            i.putExtra("userName_1", UserName_1);
                            i.putExtra("userName_2", UserName_2);
                            startActivity(i);
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

                if ((ded.getText().toString().trim().equals("")) || (ded.getText().toString().trim().equals("Delivery Date")) || (ded.getText().toString().trim().equals("Please Select date"))) {

                    ded.setTextSize(14);
                    ded.setText("Please Select date");
                    ded.setTextColor(Color.RED);
                } else if ((actv.getText().toString().trim().equals("")) || (actv.getText().toString().trim().equals("Input Customer (eg. dh..)")) || (actv.getText().toString().length() < 20)) {
                    actv.setError("Customer not Assigned !");
                } else if (totalday_given > totalday_valid) {
                    ded.setError("Delivery Date  is not more than 6 days");
                    error_dt.setText("Delivery Date  is not more than next 6 days! ");
                } else if (totalday_given < totalday_valid1) {
                    ded.setError("Delivery Date  is not more than 6 days");
                    error_dt.setText("Delivery Date  is not less current date! ");
                } else {

                    if (cash_party == 1) {
                        select_party_new = "CASH";
                    } else {
                        select_party_new = "CREDIT";
                    }

                    if (select_party_new.equals("Select payment mode")) {
                        error_dt.setText("Please Select payment mode by click! ");
                        error_payment.setError("Please Select payment mode by click!");
                    } else {
                        Bundle b = getIntent().getExtras();
                        Thread next = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Intent in = new Intent(Offlinereadcomments.this, OfflineProductOrder.class);
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

    public void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(PrescroptionImageSearch.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
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
