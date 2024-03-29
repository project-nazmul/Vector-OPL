package com.opl.pharmavector;

import static com.nativecss.enums.RemoteContentRefreshPeriod.Never;
import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
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

import android.content.SharedPreferences;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;

import com.nativecss.NativeCSS;
import com.opl.pharmavector.R;
import com.opl.pharmavector.personalExpense.model.MotorCycleModel;
import com.opl.pharmavector.personalExpense.model.MotorCycleRate;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;

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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import androidx.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;

public class AmPersonalExpenses extends Activity implements AdapterView.OnItemSelectedListener {
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
    private ArrayList<AmCustomer> shiftlist;
    public static String name = null, newversion_text = null, ename = null;
    public int credit_party = 0, cash_party = 0;
    SharedPreferences.Editor editor;
    public EditText osi, op, od, dateResult, ref;
    public String pass_dcr_date;
    // private ListView cust;
    private ArrayList<AmCustomer> customerlist;
    private ArrayList<AmCustomer> mpodcrlist;
    private ArrayList<MotorCycleRate> motorCycleRates = new ArrayList<>();
    public Array pay_cash;
    public Array pay_cradit;
    public String saved_da_val;
    private DatabaseHandler db;
    private String f_name, s_name;
    private Button mOffline;
    private ListView lv;
    private Contact dataModel;
    Toast toast;
    Toast toast2;
    Toast toast3;
    public String Tour_nature_code;
    public String dcr_sl_no;
    public String pay_cash1, userName, userName_1, userName_2;
    private Spinner shift_spinner;
    public stirng pay_credit1;
    public String jour_code = null;
    private Spinner cust;
    private Spinner dcrlist;
    ProgressDialog pDialog, qDialog;
    EditText ta;
    TextView date2, ded, note, s_time, e_time, da, tournature, dcr_nummber, othertaval, particul;
    public int success;
    public String message, ord_no, invoice, target_data, achivement, searchString, select_party;
    private final String URL_DCR = BASE_URL+"area_manager_api/personal_expense/get_mpo_dcr.php";
    private final String URL_JOURMODE = BASE_URL+"area_manager_api/personal_expense/get_jour_mode.php";
    private final String submit_dcr_expense = BASE_URL+"area_manager_api/personal_expense/submit_dcr_expense.php";
    private final String URL_PASS_DCR_DATE = BASE_URL+"area_manager_api/personal_expense/URL_PASS_DCR_DATE.php";
    protected Handler handler;
    TextView error_dt, error_payment;
    TextView ordno, succ_msg;
    LinearLayout mainlayout;
    Spinner ampmspin, visitstatus;
    AutoCompleteTextView actv,actv1;
    TextView myTextView;
    String UserName_2,ordernumber,tar;
    EditText diskm;
    @SuppressLint({"CutPasteId", "SetTextI18n"})

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ampersonalexpenses);

        setTitle("Personal Expenses");
        initViews();
        motorCycleRateInfo();
        new GetCategories().execute();
        new GetJourmode().execute();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.visitstatus, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        visitstatus.setPrompt("Work Status");
        visitstatus.setAdapter(adapter);
        visitstatus.setOnItemSelectedListener(this);

        visitstatus.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String da_value_on_change = saved_da_val;
                if (visitstatus.getSelectedItem().toString().equals("Half Day")) {
                    da.setText("");
                    int da_val_cov = Integer.parseInt(da_value_on_change);
                    int on_change_work_stat = da_val_cov / 2;
                    String aString = Integer.toString(on_change_work_stat);
                    da.setText(aString);
                } else if (visitstatus.getSelectedItem().toString().equals("Full Day")) {
                    da.setTextColor(Color.parseColor("#006199"));
                    da.setText(saved_da_val);
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        autocomplete_dcrdate();
        autocomplete_JourneyModeOnclick();
        Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
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
                error_dt.setText("");
                ded.setTextColor(Color.parseColor("#006199"));
                ded.setText(sdf.format(myCalendar.getTime()));
            }
        };

        ded.setOnClickListener(v -> new DatePickerDialog(AmPersonalExpenses.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());

        shift_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                final String shift_status = shift_spinner.getSelectedItem().toString();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(AmPersonalExpenses.this, "Please Select Shift !!", Toast.LENGTH_LONG).show();
            }
        });

        diskm.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (diskm.getText().toString().trim().equals("")) {
                    diskm.setText("0");
                }
                if (jour_code.trim().equals("MC")) { // && tournature ==
                    int a = Integer.parseInt(diskm.getText().toString().trim());
                    if (motorCycleRates.size() > 0) {
                        double motorRate = Double.parseDouble(motorCycleRates.get(0).getUnit_exp());
                        double result = a * motorRate;
                        String total2 = Double.toString(result);
                        ta.setText(total2);
                    }
                } else {
                    ta.setEnabled(true);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void afterTextChanged(Editable s) {}
        });

        session = new SessionManager(getApplicationContext());
        logout.setOnClickListener(v -> {
            Thread server = new Thread(() -> {
                JSONParser jsonParser = new JSONParser();
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("logout", "logout"));
                JSONObject json = jsonParser.makeHttpRequest(Login.LOGIN_URL, "POST", params);
            });
            server.start();
            logoutUser();
        });

        back.setOnClickListener(v -> {
            Thread backthred = new Thread(() -> {
                try {
                    Intent i = new Intent(AmPersonalExpenses.this,  AmDashboard.class);
                    i.putExtra("UserName", AmDashboard.globalFMCode);
                    i.putExtra("new_version", AmDashboard.new_version);
                    i.putExtra("UserName_2", AmDashboard.globalAreaCode);
                    i.putExtra("message_3", AmDashboard.message_3);
                    i.putExtra("password", AmDashboard.password);
                    i.putExtra("ff_type", AmDashboard.ff_type);
                    i.putExtra("vector_version", R.string.vector_version);
                    i.putExtra("emp_code", AmDashboard.globalempCode);
                    i.putExtra("emp_name", AmDashboard.globalempName);
                    startActivity(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            backthred.start();
        });

        next.setOnClickListener(v -> {
            Bundle b = getIntent().getExtras();
            userName = b.getString("UserName");
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
            int totalday_valid = totalday_valid1;
            int totalday_valid2 = cmonth_day + cDay;

            if ((actv.getText().toString().trim().equals("")) || (actv.getText().toString().trim().equals("DCR Date (Type date like : 01  or 11 )"))) {
                error_dt.setText("1. Select DCR DATE ");
            } else if (actv1.getText().toString().trim().equals("")) {
                error_dt.setText(" 2. Select  Journey Mode");
            } else if ((Tour_nature_code.toString().trim().equals("2")) && jour_code.toString().trim().equals("MC") && (diskm.getText().toString().trim().equals(""))) {
                error_dt.setText("3. Select Distance in KM");
            } else if ((Tour_nature_code.toString().trim().equals("3")) && jour_code.toString().trim().equals("MC") && (diskm.getText().toString().trim().equals(""))) {
                error_dt.setText("3. OUTSTATION -Select Distance in KM");
            } else if ((Tour_nature_code.toString().trim().equals("2")) && !othertaval.getText().toString().isEmpty() && particul.getText().toString().equals("")) {
                error_dt.setText("Enter Other T/A Particul (Ex: where did you spend the TA) ");
            } else if ((Tour_nature_code.toString().trim().equals("3")) && !othertaval.getText().toString().isEmpty() && particul.getText().toString().equals("")) {
                error_dt.setText("Enter Other T/A Particul (Ex: where did you spend the TA) ");
            } else {
                Thread server = new Thread(() -> {
                    JSONParser jsonParser = new JSONParser();
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("ORD_NO", dcr_sl_no));
                    params.add(new BasicNameValuePair("MPO_CODE", userName));
                    params.add(new BasicNameValuePair("JOUR_CODE", jour_code));
                    params.add(new BasicNameValuePair("AM_PM", visitstatus.getSelectedItem().toString()));
                    params.add(new BasicNameValuePair("DISKM", diskm.getText().toString()));
                    params.add(new BasicNameValuePair("TA", ta.getText().toString()));
                    params.add(new BasicNameValuePair("DA", da.getText().toString()));
                    params.add(new BasicNameValuePair("Tour_nature_code", Tour_nature_code));
                    params.add(new BasicNameValuePair("PARTICUL", particul.getText().toString()));
                    params.add(new BasicNameValuePair("OTHERVAL", othertaval.getText().toString()));
                    params.add(new BasicNameValuePair("dcr_type", shift_spinner.getSelectedItem().toString()));
                    JSONObject json = jsonParser.makeHttpRequest(submit_dcr_expense, "POST", params);

                    try {
                        success = json.getInt(TAG_SUCCESS);
                        message = json.getString(TAG_MESSAGE);
                        Log.w("please wait TRY ...." + message, json.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.w("Please wait ...." + message, json.toString());
                    }
                    Intent in = getIntent();
                    Intent inten = getIntent();
                    Bundle bundle = in.getExtras();
                    inten.getExtras();
                    String MPO_CODE = bundle.getString("MPO_CODE");
                    String userName = bundle.getString("UserName");
                    String UserName_2 = bundle.getString("UserName_2");
                    Intent sameint = new Intent(AmPersonalExpenses.this, AmPersonalExpenses.class);
                    sameint.putExtra("Ord_NO", dcr_sl_no);
                    sameint.putExtra("UserName", userName);
                    sameint.putExtra("UserName_2", UserName_2);
                    startActivity(sameint);
                    Log.w("Passed in DCR TO DCR", dcr_sl_no + "UserName" + userName + "UserName_2" + UserName_2);
                });
                server.start();
            }
            //  }
        });
    }

    private void autocomplete_JourneyModeOnclick() {
        actv1.setOnClickListener(v -> {
            if (actv1.getText().toString() != "") {
                String jourmode = actv1.getText().toString();
                cust.setTag(jourmode);
            }
        });
        actv1.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //actv.setTextColor(Color.BLACK);
            }

            @Override
            public void afterTextChanged(final Editable s) {
                try {
                    final String inputorder = s.toString();
                    int total_string = inputorder.length();
                    if (inputorder.contains(":")) {
                        String cust_type = inputorder.substring(inputorder.indexOf(":") + 1);
                        String cust_type_with_note = inputorder.substring(inputorder.indexOf(":") + 0);
                        String arr[] = cust_type_with_note.split(":");
                        cust_type_with_note = arr[1].trim();
                        String arr1[] = cust_type_with_note.split("///");
                        String jour_mode_split = arr1[0].trim();
                        //String dcr_expense_info_val=arr1[1].trim();

                        String jour_code_split[] = jour_mode_split.split("-");
                        String jour_name = jour_code_split[0].trim();
                        jour_code = jour_code_split[1].trim();
                        actv1.setText(jour_name);

                        /*
                        if ((Tour_nature_code.equals("1"))) {
                            diskm.setEnabled(false);
                        }
                        if ((Tour_nature_code.equals("2")) || (Tour_nature_code.equals("3"))) {
                            diskm.setClickable(true);
                            diskm.setEnabled(true);
                            othertaval.setEnabled(true);
                            particul.setEnabled(true);
                        }
                        */
                        if (jour_code.trim().equals("MC")) {
                            ta.setEnabled(false);
                        }
                    }  //ded.setText("Select Date");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            private void length() {}
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void autocomplete_dcrdate() {
        actv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //hideKeyBoard();
                actv.showDropDown();
                return false;
            }
        });

        actv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actv.getText().toString() != "") {
                    String selectedcustomer = actv.getText().toString();
                    cust.setTag(selectedcustomer);
                }
            }
        });
        actv.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(final Editable s) {
                try {
                    final String inputorder = s.toString();
                    int total_string = inputorder.length();
                    if (inputorder.contains(":")) {
                        String arr[] = inputorder.split(":");
                        String dcr_date_info_split = arr[0].trim();
                        String cust_type_with_note = arr[1].trim();
                        String arr1[] = cust_type_with_note.split("//");
                        Tour_nature_code = arr1[0].trim();
                        String dcr_expense_info_val = arr1[1].trim();

                        if (Tour_nature_code.equals(String.valueOf(1))) {
                            othertaval.setVisibility(View.GONE);
                            particul.setVisibility(View.GONE);
                            othertaval.setEnabled(false);
                            particul.setEnabled(false);
                            diskm.setEnabled(false);
                            ta.setEnabled(false);
                        } else if (Tour_nature_code.equals(String.valueOf(2))) {
                            Log.e("YOURtournaturecode==>", Tour_nature_code);
                            othertaval.setVisibility(View.VISIBLE);
                            particul.setVisibility(View.VISIBLE);
                            othertaval.setEnabled(true);
                            particul.setEnabled(true);
                            diskm.setClickable(true);
                            diskm.setEnabled(true);
                            ta.setEnabled(true);
                        } else if (Tour_nature_code.equals(String.valueOf(3))) {
                            othertaval.setVisibility(View.VISIBLE);
                            particul.setVisibility(View.VISIBLE);
                            othertaval.setEnabled(true);
                            particul.setEnabled(true);
                            diskm.setClickable(true);
                            diskm.setEnabled(true);
                            ta.setEnabled(true);
                        }
                        String arr0[] = dcr_date_info_split.split("//");
                        final String dcr_date = arr0[0].trim();
                        String dcr_location_split = arr0[1].trim();
                        String arr3[] = dcr_location_split.split("/");
                        dcr_sl_no = arr3[0].trim();
                        pass_dcr_date = arr3[0].trim();
                        String dcr_location_name = arr3[1].trim();
                        actv.setText(dcr_date);
                        new GetDcrType().execute();
                        //tournature.setTextColor(Color.BLUE);
                        tournature.setTextColor(Color.parseColor("#006199"));
                        tournature.setText(dcr_location_name);
                        dcr_nummber.setText(dcr_sl_no);
                        String arr4[] = dcr_expense_info_val.split("##");
                        String da_val = arr4[1].trim(); //DAVALUE
                        saved_da_val = arr4[1].trim();
                        //da.setTextColor(Color.BLUE);
                        da.setTextColor(Color.parseColor("#006199"));
                        da.setText(da_val);
                        String hq_ta_val_arr = arr4[0].trim();
                        String arr2[] = hq_ta_val_arr.split("/");
                        String hq_ta_val = arr2[1].trim();
                        //ta.setTextColor(Color.BLUE);
                        ta.setTextColor(Color.parseColor("#006199"));
                        ta.setText(hq_ta_val);

                        if (visitstatus.getSelectedItem().toString().equals("Half Day")) {
                            int da_val_cov = Integer.parseInt(da_val.toString());
                            int half_day_da = da_val_cov / 2;
                            da.setTextColor(Color.parseColor("#006199"));
                            da.setText(half_day_da);
                        } else if (visitstatus.getSelectedItem().toString().equals("Full Day")) {
                            da.setTextColor(Color.parseColor("#006199"));
                            da.setText(da_val);
                        }
                        actv1.setFocusable(true);
                        actv1.setFocusableInTouchMode(true);
                        actv1.requestFocus();
                        cashcredit = (Spinner) findViewById(R.id.cashcredit);
                        credit = (Spinner) findViewById(R.id.credit);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            private void length() {}
        });
    }

    private void initViews() {
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        logout = (Button) findViewById(R.id.logout);
        logout.setTypeface(fontFamily);
        logout.setText("\uf08b"); //&#xf08b
        user_show = findViewById(R.id.user_show);
        newversion = findViewById(R.id.newversion);
        next = (Button) findViewById(R.id.next);
        next.setTypeface(fontFamily);
        next.setText("\uf1d8");
        back = (Button) findViewById(R.id.view);
        back.setTypeface(fontFamily);
        back.setText("\uf060 ");
        error_dt = findViewById(R.id.errordt);
        error_payment = findViewById(R.id.errorpayment);
        op = (EditText) findViewById(R.id.orderpage);
        cust = (Spinner) findViewById(R.id.customer);
        dcrlist = (Spinner) findViewById(R.id.dcrlist);
        da = findViewById(R.id.da);
        othertaval = (EditText) findViewById(R.id.othertaval);
        particul = (EditText) findViewById(R.id.particul);

        particul.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start, int end, Spanned spanned, int dStart, int dEnd) {
                        if (cs.equals("")) { // for backspace
                            return cs;
                        }
                        if (cs.toString().matches("[a-zA-Z ]+")) {
                            return cs;
                        }
                        return "";
                    }
                }
        });
        diskm = findViewById(R.id.diskm);
        diskm.setClickable(true);
        diskm.setEnabled(true);
        ta = findViewById(R.id.ta);
        cust.setPrompt("Select Doctor");
        dcrlist.setPrompt("List of Dcr");
        ref = (EditText) findViewById(R.id.reference);
        ded = findViewById(R.id.tourdate);
        tournature = findViewById(R.id.tournature);
        dcr_nummber = findViewById(R.id.dcr_nummber);
        s_time = findViewById(R.id.starttime);
        e_time = findViewById(R.id.endtime);
        ordno = findViewById(R.id.ordno);
        succ_msg = findViewById(R.id.succ_msg);
        note = findViewById(R.id.note);
        mainlayout = findViewById(R.id.successmsg);
        ampmspin = findViewById(R.id.ampm);
        cashcredit = (Spinner) findViewById(R.id.cashcredit);
        credit = (Spinner) findViewById(R.id.credit);
        cashcredit.setVisibility(View.GONE);
        credit.setVisibility(View.GONE);
        customerlist = new ArrayList<AmCustomer>();
        cust.setOnItemSelectedListener(this);
        mpodcrlist = new ArrayList<AmCustomer>();
        dcrlist.setOnItemSelectedListener(this);
        shiftlist = new ArrayList<AmCustomer>();
        shift_spinner = (Spinner) findViewById(R.id.shift_spinner);
        shift_spinner.setPrompt("Select your Dcr Type");
        visitstatus = findViewById(R.id.visitstatus);
        actv =  findViewById(R.id.autoCompleteTextView1);
        actv1 =  findViewById(R.id.JourneyMode);
        myTextView = findViewById(R.id.user_show);
        newversion = findViewById(R.id.newversion);
        Bundle b = getIntent().getExtras();
        name = b.getString("UserName_1");
        ename = b.getString("UserName_2");
        newversion_text = b.getString("new_version");
        newversion.setText("DCR Expenses");

        if (b.isEmpty()) {
            String userName = "";
            myTextView.setText(userName);
        } else {
            userName = b.getString("UserName");
            UserName_2 = b.getString("UserName_2");
            myTextView.setText(UserName_2 + "(" + userName + ")");
            ordernumber = b.getString("Ord_NO");
            invoice = b.getString("target");
            tar = b.getString("invoice");
            achivement = b.getString("achivement");
            if (ordernumber == null) {
                mainlayout.setVisibility(LinearLayout.GONE);
            } else {
                ordno.setText("Submitted Expense No :  " + ordernumber);
                ordno.setTextSize(16);
                succ_msg.setText(" Personal Expense Submitted.");
                succ_msg.setTextSize(16);
                myTextView.setText(userName);
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
        ArrayAdapter<String> Adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_text_view, customer);
        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        actv.setAdapter(Adapter2);
        //actv.setTextColor(Color.BLUE);
        actv.setTextColor(Color.parseColor("#006199"));
    }

    private void populateSpinner2() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < customerlist.size(); i++) {
            lables.add(customerlist.get(i).getName());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, lables);
        cust.setAdapter(spinnerAdapter);
        String[] customer = lables.toArray(new String[lables.size()]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, customer);
        AutoCompleteTextView actv1 = (AutoCompleteTextView) findViewById(R.id.JourneyMode);
        actv1.setAdapter(Adapter);
        //actv1.setTextColor(Color.RED);
        actv1.setTextColor(Color.parseColor("#006199"));
    }

    private void populateSpinnerShift() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < shiftlist.size(); i++) {
            lables.add(shiftlist.get(i).getName());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, lables);
        shift_spinner.setAdapter(spinnerAdapter);

    }

    class GetCategories extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AmPersonalExpenses.this);
            pDialog.setMessage("Fetching Personal Expense Data..");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            //ServiceHandler jsonParser = new ServiceHandler();
            //String json = jsonParser.makeServiceCall(URL_CUSOTMER,ServiceHandler.GET);
            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            String id = userName;

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", id));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_DCR, ServiceHandler.POST, params);
            Log.e("Response: ", "> " + json);
            customerlist.clear();

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    JSONArray customer = jsonObj.getJSONArray("customer");
                    for (int i = 0; i < customer.length(); i++) {
                        JSONObject catObj = (JSONObject) customer.get(i);
                        AmCustomer custo = new AmCustomer(catObj.getInt("id"), catObj.getString("name"));
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
            populateSpinner();
            //populateSpinner2();
        }
    }

    class GetJourmode extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //pDialog = new ProgressDialog(  PersonalExpenses.this);
            //pDialog.setMessage("Fetching Customer..");
            //pDialog.setCancelable(false);
            //pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            //ServiceHandler jsonParser = new ServiceHandler();
            //String json = jsonParser.makeServiceCall(URL_CUSOTMER,ServiceHandler.GET);
            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            String id = userName;

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("id", id));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_JOURMODE, ServiceHandler.POST, params);
            Log.e("Response: ", "> " + json);
            customerlist.clear();

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    JSONArray customer = jsonObj.getJSONArray("customer");
                    for (int i = 0; i < customer.length(); i++) {
                        JSONObject catObj = (JSONObject) customer.get(i);
                        AmCustomer custo = new AmCustomer(catObj.getInt("id"), catObj.getString("name"));
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
            populateSpinner2();
            //populateSpinner2();
        }
    }

    class GetDcrType extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            //ServiceHandler jsonParser = new ServiceHandler();
            //String json = jsonParser.makeServiceCall(URL_CUSOTMER,ServiceHandler.GET);
            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            String id = userName;

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", id));
            params.add(new BasicNameValuePair("pass_dcr_date", pass_dcr_date));
            Log.e("pass_dcr_date: ", "> " + pass_dcr_date);
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_PASS_DCR_DATE, ServiceHandler.POST, params);
            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    JSONArray customer = jsonObj.getJSONArray("customer");
                    for (int i = 0; i < customer.length(); i++) {
                        JSONObject catObj = (JSONObject) customer.get(i);
                        AmCustomer custo = new AmCustomer(catObj.getInt("id"), catObj.getString("name"));
                        shiftlist.add(custo);
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
            populateSpinnerShift();
        }
    }

    public void motorCycleRateInfo() {
        qDialog = new ProgressDialog(AmPersonalExpenses.this);
        qDialog = new ProgressDialog(AmPersonalExpenses.this);
        qDialog.setMessage("Expense Rate Loading...");
        qDialog.setTitle("Expense Rate Followup");
        qDialog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<MotorCycleModel> call = apiInterface.getMotorExpenseList();
        motorCycleRates.clear();

        call.enqueue(new Callback<MotorCycleModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<MotorCycleModel> call, @NonNull retrofit2.Response<MotorCycleModel> response) {
                List<MotorCycleRate> motorCycleRate = null;
                if (response.body() != null) {
                    motorCycleRate = response.body().getExpAmount();
                }

                if (response.isSuccessful()) {
                    for (int i = 0; i < (motorCycleRate != null ? motorCycleRate.size() : 0); i++) {
                        motorCycleRates.add(new MotorCycleRate(
                                motorCycleRate.get(i).getUnit_exp()));
                    }
                    qDialog.dismiss();
                    Log.d("motorRate", motorCycleRates.get(0).getUnit_exp());
                } else {
                    qDialog.dismiss();
                    Toast.makeText(AmPersonalExpenses.this, "No data Available", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MotorCycleModel> call, @NonNull Throwable t) {
                qDialog.dismiss();
                motorCycleRateInfo();
            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {}
    private void logoutUser() {
        session.setLogin(false);
        //session.removeAttribute();
        session.invalidate();
        Intent intent = new Intent(AmPersonalExpenses.this, Login.class);
        startActivity(intent);
        finishActivity(BIND_ABOVE_CLIENT);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}
}
