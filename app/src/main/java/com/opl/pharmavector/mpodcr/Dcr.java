package com.opl.pharmavector.mpodcr;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.opl.pharmavector.ChemistGiftOrder;
import com.opl.pharmavector.Contact;
import com.opl.pharmavector.Customer;
import com.opl.pharmavector.Dashboard;
import com.opl.pharmavector.DatabaseHandler;
import com.opl.pharmavector.DcrDate;
import com.opl.pharmavector.DialogMultipleChoice;
import com.opl.pharmavector.model.Patient;
import com.opl.pharmavector.mpodcr.GiftOrder;
import com.opl.pharmavector.JSONParser;
import com.opl.pharmavector.Login;
import com.opl.pharmavector.MultiSelectionSpinner;
import com.opl.pharmavector.R;
import com.opl.pharmavector.ServiceHandler;
import com.opl.pharmavector.SessionManager;
import com.opl.pharmavector.geolocation.DoctorChamberLocate;
import com.opl.pharmavector.prescriptionsurvey.PrescriptionFollowup2;
import com.opl.pharmavector.prescriptionsurvey.PrescriptionSurveyReport;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;
import com.opl.pharmavector.stirng;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;
import static com.opl.pharmavector.remote.ApiClient.BASE_URL;
import static com.opl.pharmavector.util.KeyboardUtils.hideKeyboard;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import retrofit2.Call;
import retrofit2.Callback;

public class Dcr extends Activity implements OnItemSelectedListener {
    private Spinner spinner1, spinner2, cashcredit, cashcredit_test, credit;
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_MESSAGE_1 = "message_1";
    public static final String TAG_invoice = "invoice";
    public static final String TAG_target = "target";
    public static final String TAG_achivement = "achivement";
    public static final String TAG_MESSAGE_new_version = "new_version";
    public String yes_no_val = "";
    private Button logout;
    private SessionManager session;
    public Button next, back, dcr_submit, chemist_ppm;
    public static String name = null, newversion_text = null, ename = null;
    public int credit_party = 0, cash_party = 0;
    public EditText osi, op, od, dateResult, ref, date_ext;
    private ArrayList<Customer> customerlist;
    private ArrayList<Customer> visitorlist;
    private ArrayList<Customer> chemistlist;
    private ArrayList<Customer> shiftlist;
    private ArrayList<Customer> dateextendlist;
    public Array pay_cash;
    public Array pay_cradit;
    private DatabaseHandler db;
    public String dcr_code = "";
    public String dt_code;
    public String com_ana_val;
    public String pay_cash1, userName, userName_1, userName_2, UserName_2;
    public Spinner location;
    public stirng pay_credit1;
    public String location_code;
    public String loc_code;
    private Spinner cust, visitor, chemist, shift_spinner, dcr_date_extend;
    ProgressDialog pDialog, pDialog2;
    TextView  ded, note, s_time, e_time, remarks, comp_ana,user_show;
    public int success;
    public String message, ord_no, invoice, target_data, achivement, searchString, select_party;
    ArrayList<HashMap<String, String>> dcrdatelist;
    ArrayList<HashMap<String, String>> dcrdaterange;
    public String shift_status = "AM";
    public String get_ext_dt;
    public  String doc_code,doc_name;
    private final String URL_CUSOTMER = BASE_URL+"mpodcr/get_doctor_gps.php";
    private final String URL_EMP = BASE_URL+"mpodcr/getemp.php";
    private final String URL_CHEMIST = BASE_URL+"mpodcr/get_onlychemist.php";
    private final String URL_DCCCHEMIST = BASE_URL+"mpodcr/get_dccchemist.php";
    private final String submit_url = BASE_URL+"mpodcr/dcrsubmit_1.php";
    private final String get_dcr_date = BASE_URL+"mpodcr/get_dcr.php";
    private final String URL_SHIFT = BASE_URL+"mpodcr/getshift.php";
    private final String date_range_permission = BASE_URL+"mpodcr/date_range_permission.php";
    private final String check_dcr_status = BASE_URL+"mpodcr/check_dcr_status.php";
    Typeface fontFamily;
    TextView error_dt, error_payment, ordno, succ_msg;
    Date today;
    protected Handler handler;
    DialogMultipleChoice mDialogMultipleChoice;
    LinearLayout mainlayout;
    private Spinner dcr_spinner, chemordoc, ampmspin, yes_no;
    private AutoCompleteTextView doccode, visitorcode, marketcode;
    private String dat_val_ext, ordernumber, UserName_3;
    MultiSelectionSpinner spinner;
    private AutoCompleteTextView actv, actv2, actv3;
    private TextView myTextView, newversion;
    private Calendar cal, myCalendar;
    DateFormat df;
    String date_str;
    private DatePickerDialog.OnDateSetListener date;
    public double mpo_current_lang,mpo_current_lat;

    @SuppressLint({"CutPasteId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dcr);

        initViews();
        new GetDcrDateOffline().execute();
        spinnerViews();
        autoCompleteTextViews();

        mDialogMultipleChoice = new DialogMultipleChoice(this);
        findViewById(R.id.show_multiple_dialog).setOnClickListener(view -> mDialogMultipleChoice.show(s_time));
        btnVisibleInit();
        new GeTShift().execute();
        new GeTDateExtend().execute();
        new GetEmp().execute();

        dat_val_ext = date_ext.getText().toString().trim();
        multiSpinnerinitViews();
        docCustomerVisitorAutocomplete();

        remarks.setOnClickListener(v -> {
            if (actv2.getText().toString() != "") {
                final String remarksvalue = remarks.getText().toString();
            }
        });
        comp_ana.setOnClickListener(v -> {
            if (actv2.getText().toString() != "") {
                final String comp_anavalue = comp_ana.getText().toString();
            }
        });
        initCalender();
        postDcrCheck();

        dcr_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dcr_code = dcr_spinner.getSelectedItem().toString();
                dt_code = dcr_code.substring(0, 1);
                doccode.setCursorVisible(false);
                doccode.setPressed(false);
                doccode.setClickable(false);
                doccode.setTag(doccode.getKeyListener());
                doccode.setKeyListener(null);
                marketcode.setCursorVisible(false);
                marketcode.setPressed(false);
                marketcode.setClickable(false);
                marketcode.setTag(marketcode.getKeyListener());
                marketcode.setKeyListener(null);
                actv.setFocusable(false);

                if (dcr_code.equals("Regular")) {
                    dcr_submit.setVisibility(View.GONE);
                    next.setVisibility(View.VISIBLE);
                    next.setClickable(true);
                    next.setPressed(true);
                    yes_no.setVisibility(View.GONE);
                    chemordoc.setVisibility(View.VISIBLE);

                    if (db.checkOrdNo()) {
                        location.setVisibility(View.GONE);
                        shift_spinner.setFocusable(true);
                        shift_spinner.setFocusableInTouchMode(true);
                        shift_spinner.requestFocus();
                    } else {
                        location.setVisibility(View.VISIBLE);
                        location.setFocusable(true);
                        location.setFocusableInTouchMode(true);
                        location.requestFocus();
                    }
                    actv.setEnabled(true);
                    error_dt.setText("");
                    actv.setVisibility(View.VISIBLE);
                    actv3.setVisibility(View.VISIBLE);

                } else if (dcr_code.equals("Journey") || dcr_code.equals("Conference") || dcr_code.equals("Training") || dcr_code.equals("Others") || dcr_code.equals("Meeting")
                        || dcr_code.equals("Holiday") || dcr_code.equals("Leave")) {
                    if (db.checkOrdNo()) {
                        location.setVisibility(View.GONE);
                        shift_spinner.setFocusable(true);
                        shift_spinner.setFocusableInTouchMode(true);
                        shift_spinner.requestFocus();
                    } else {
                        location.setVisibility(View.VISIBLE);
                        location.setFocusable(true);
                        location.setFocusableInTouchMode(true);
                        location.requestFocus();
                    }
                    chemordoc.setVisibility(View.GONE);
                    yes_no.setVisibility(View.GONE);
                    dcr_submit.setVisibility(View.VISIBLE);
                    dcr_submit.setPressed(true);
                    dcr_submit.setClickable(true);
                    dcr_submit.setEnabled(true);
                    next.setClickable(false);
                    next.setPressed(false);
                    error_dt.setText("");
                    actv.setVisibility(View.GONE);
                    actv3.setVisibility(View.GONE);
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        location.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                location_code = location.getSelectedItem().toString();
                loc_code = location_code.substring(0, 1);
                final String check = String.valueOf(loc_code);

                if (loc_code.equals("S")) {
                    location.setFocusable(true);
                    location.setFocusableInTouchMode(true);
                    location.requestFocus();
                } else {
                    shift_spinner.setFocusable(true);
                    shift_spinner.setFocusableInTouchMode(true);
                    shift_spinner.requestFocus();
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        s_time.setOnClickListener(v -> {
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;

            mTimePicker = new TimePickerDialog(Dcr.this, (timePicker, selectedHour, selectedMinute) -> s_time.setText(selectedHour + ":" + selectedMinute), hour, minute, true); //Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();

            e_time.setFocusable(true);
            e_time.setFocusableInTouchMode(true);
            e_time.requestFocus();
        });

        e_time.setOnClickListener(v -> {
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(Dcr.this, (timePicker, selectedHour, selectedMinute) -> e_time.setText(selectedHour + ":" + selectedMinute), hour, minute, true); //Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
            String check_shift = s_time.getText().toString();
            String[] arr = check_shift.split(":");
            String day_shift = arr[0].trim();
            int shift_check = Integer.parseInt(day_shift);
        });

        session = new SessionManager(getApplicationContext());
        shift_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
                shift_status = shift_spinner.getSelectedItem().toString();

                if (dcr_code.equals("Regular")){
                    if (shift_spinner.getSelectedItem().toString().equals("AM") || shift_spinner.getSelectedItem().toString().equals("PM")) {
                        yes_no.setVisibility(View.GONE);
                        actv.setEnabled(true);
                        error_dt.setText("");
                        actv.setVisibility(View.VISIBLE);
                        actv3.setVisibility(View.VISIBLE);
                        chemordoc.setVisibility(View.VISIBLE);
                        chemordoc.setFocusable(true);
                        chemordoc.setFocusableInTouchMode(true);
                        chemordoc.setFocusable(true);
                    }
                } else if (dcr_code.equals("Journey") || dcr_code.equals("Conference") || dcr_code.equals("Training") || dcr_code.equals("Others") || dcr_code.equals("Meeting")
                        || dcr_code.equals("Holiday") || dcr_code.equals("Leave")){
                    chemordoc.setVisibility(View.GONE);
                    yes_no.setVisibility(View.GONE);
                    dcr_submit.setPressed(true);
                    dcr_submit.setClickable(true);
                    dcr_submit.setEnabled(true);
                    next.setClickable(false);
                    next.setPressed(false);
                    error_dt.setText("");
                    actv.setVisibility(View.GONE);
                    actv3.setVisibility(View.GONE);
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(Dcr.this, "Please Select Shift !!", Toast.LENGTH_LONG).show();
            }
        });

        spinner.setOnItemSelectedListener(
                new OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                        s_time.setFocusableInTouchMode(true);
                        s_time.setFocusable(true);
                        s_time.setFocusableInTouchMode(true);
                        s_time.requestFocus();
                    }
                });

        chemordoc.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (chemordoc.getSelectedItem().toString().equals("Doctor")) {
                    new GetCategories().execute();
                    chemist_ppm.setVisibility(View.GONE);
                    next.setVisibility(View.VISIBLE);
                    next.setEnabled(true);
                    next.setPressed(true);
                    next.setClickable(true);
                    yes_no.setVisibility(View.VISIBLE);
                    yes_no.setFocusable(true);
                    yes_no.setFocusableInTouchMode(true);
                    yes_no.requestFocus();
                    actv.setVisibility(View.VISIBLE);
                    actv3.setVisibility(View.GONE);
                    dcr_submit.setVisibility(View.INVISIBLE);
                    chemist_ppm.setEnabled(false);
                    chemist_ppm.setPressed(false);
                    chemist_ppm.setClickable(false);
                } else if (chemordoc.getSelectedItem().toString().equals("Chemist")) {
                    new GetChemist().execute();
                    yes_no.setVisibility(View.GONE);
                    actv.setVisibility(View.GONE);
                    actv3.setVisibility(View.VISIBLE);
                    actv3.setFocusable(true);
                    actv3.setFocusableInTouchMode(true);
                    actv3.requestFocus();
                    actv3.setText("");
                    dcr_submit.setVisibility(View.VISIBLE);
                    chemist_ppm.setEnabled(true);
                    chemist_ppm.setPressed(true);
                    chemist_ppm.setClickable(true);
                    chemist_ppm.setVisibility(View.GONE);
                    chemist_ppm.setEnabled(false);
                    chemist_ppm.setPressed(false);
                    chemist_ppm.setClickable(false);
                    next.setVisibility(View.GONE);
                    next.setEnabled(false);
                    next.setPressed(false);
                    next.setClickable(false);
                } else if (chemordoc.getSelectedItem().toString().equals("Doctor cum Chemist (DCC)")) {
                    new GetDCCChemist().execute();
                    yes_no.setVisibility(View.GONE);
                    actv.setVisibility(View.GONE);
                    actv3.setVisibility(View.VISIBLE);
                    actv3.setFocusable(true);
                    actv3.setFocusableInTouchMode(true);
                    actv3.requestFocus();
                    actv3.setText("");
                    chemist_ppm.setVisibility(View.VISIBLE);
                    chemist_ppm.setEnabled(true);
                    chemist_ppm.setPressed(true);
                    chemist_ppm.setClickable(true);
                    dcr_submit.setVisibility(View.INVISIBLE);
                    chemist_ppm.setEnabled(false);
                    chemist_ppm.setPressed(false);
                    chemist_ppm.setClickable(false);
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        yes_no.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                yes_no_val = yes_no.getSelectedItem().toString();
                if (yes_no.getSelectedItem().toString().equals("Yes")) {
                    dcr_submit.setPressed(false);
                    dcr_submit.setClickable(false);
                    next.setVisibility(View.VISIBLE);
                    next.setEnabled(true);
                    next.setPressed(true);
                    next.setClickable(true);
                    back.setEnabled(true);
                    back.setPressed(true);
                    back.setClickable(true);
                    actv.setFocusable(true);
                    actv.setFocusableInTouchMode(true);
                    actv.requestFocus();
                }
                else if (yes_no.getSelectedItem().toString().equals("No")) {
                    dcr_submit.setVisibility(View.VISIBLE);
                    back.setEnabled(true);
                    back.setPressed(true);
                    back.setClickable(true);
                    next.setVisibility(View.GONE);
                    next.setClickable(false);
                    next.setPressed(false);
                    next.setEnabled(false);
                    dcr_submit.setEnabled(true);
                    dcr_submit.setPressed(true);
                    dcr_submit.setClickable(true);
                    actv.setEnabled(true);
                    actv.setPressed(true);
                    actv.setClickable(true);
                    actv.setFocusable(true);
                    actv.setFocusableInTouchMode(true);
                    actv.requestFocus();
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        back.setOnClickListener(v -> {
            Thread mysells = new Thread(new Runnable() {
                @Override
                public void run() {
                    // toast.show();
                    /*
                    Intent i = new Intent(Dcr.this, Dashboard.class);
                    String user = myTextView.getText().toString();
                    i.putExtra("UserName", userName);
                    i.putExtra("UserName_2", UserName_2);
                    i.putExtra("ff_type", Dashboard.ff_type);
                    startActivity(i);
                    */
                    logoutUser();
                }
            });
            mysells.start();
        });

        logout.setOnClickListener(v -> {
            //finish();
            //server.start();
            logoutUser();
        });

        chemist_ppm.setOnClickListener(v -> {
            Bundle f = getIntent().getExtras();
            userName = f.getString("UserName");
            String str = ded.getText().toString();
            String date_1 = str.replaceAll("[^\\d.-]", "");
            final String ord_no = userName + "-" + date_1;

            Calendar c = Calendar.getInstance();
            int cYear = c.get(Calendar.YEAR);
            int cMonth = c.get(Calendar.MONTH) + 1;
            int cDay = c.get(Calendar.DAY_OF_MONTH);
            int gyear = myCalendar.get(Calendar.YEAR);
            //int max_date=cDay+2;

            int gmonth = myCalendar.get(Calendar.MONTH) + 1;
            if (gyear > cYear) {
                gmonth = myCalendar.get(Calendar.MONTH) + 13;
            }

            int gday = myCalendar.get(Calendar.DAY_OF_MONTH);
            int gmonth_day = gmonth * 30;
            int totalday_given = gmonth_day + gday;
            int cmonth_day = cMonth * 30;
            int totalday_valid1 = cmonth_day + cDay;
            int totalday_valid = totalday_valid1 + 0;
            int totalday_valid2 = cmonth_day + cDay - 7;
            int total_back_day = Integer.parseInt(get_ext_dt);
            int rcYear = c.get(Calendar.YEAR);
            int rcMonth = c.get(Calendar.MONTH) + 1;
            int rcDay = c.get(Calendar.DAY_OF_MONTH);
            int rcYear_day = rcYear * 365;
            int rcMonth_day = rcMonth * 30;
            int rctotal_day_today = rcYear_day + rcMonth_day + rcDay;
            int total_valid_back_day = rctotal_day_today - total_back_day;
            int rgyear = myCalendar.get(Calendar.YEAR);
            int rgmonth = myCalendar.get(Calendar.MONTH) + 1;
            int rgday = myCalendar.get(Calendar.DAY_OF_MONTH);
            int rgyear_day = rgyear * 365;
            int rgmonth_day = rgmonth * 30;
            int rgtotal_day_given = rgyear_day + rgmonth_day + rgday;

            if ((ded.getText().toString().trim().equals("")) || (ded.getText().toString().trim().equals("Reference Date")) || (ded.getText().toString().trim().equals("Please Select date"))) {
                ded.setTextSize(14);
                ded.setText("Please Select date");
                ded.setTextColor(Color.RED);
            } else if ((actv.getText().toString().trim().equals("")) || (actv.getText().toString().trim().equals("Input Customer (eg. dh..)"))) {
                actv.setError("Doctor  not Assigned !");
                actv.setText("Please insert  Doctor Name ");
                actv.setTextColor(Color.RED);
            } else if (rgtotal_day_given > rctotal_day_today) {
                error_dt.setText("Delivery Date  is not greater than current date!");
            } else if (rgtotal_day_given < total_valid_back_day) {
                ded.setError("Delivery Date  is not less " + total_back_day + "  than days from current date");
                error_dt.setText("Delivery Date  is not less " + total_back_day + " than  days from current date");
            }
            final Spinner nameSpinner =  findViewById(R.id.customer);
            final String selected_cust = actv.getText().toString();
            Bundle b = getIntent().getExtras();
            final String userName = b.getString("UserName");
            String UserName_1 = b.getString("UserName_1");

            Thread next = new Thread(new Runnable() {
                @Override
                public void run() {
                    Intent in = new Intent(Dcr.this, ChemistGiftOrder.class);
                    Bundle extras = new Bundle();
                    String str = ded.getText().toString();
                    String date_1 = str.replaceAll("[^\\d.-]", "");
                    final String generated_ord_no = userName + "-" + date_1;
                    extras.putString("MPO_CODE", userName);
                    extras.putString("CUST_CODE", userName);
                    extras.putString("AM_PM", shift_spinner.getSelectedItem().toString());
                    extras.putString("ORDER_DELEVERY_DATE", ded.getText().toString());
                    extras.putString("ORDER_REFERANCE_NO", ref.getText().toString());
                    extras.putString("ord_no", generated_ord_no);
                    extras.putString("doc_code", doc_code);
                    extras.putString("end_time", e_time.getText().toString());
                    extras.putString("start_time", s_time.getText().toString());
                    extras.putString("Type", dt_code);
                    extras.putString("location code", loc_code);
                    extras.putString("VISITOR_CODE", visitorcode.getText().toString());
                    extras.putString("VISIT_DATE", ded.getText().toString());
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
        });

        dcr_submit.setOnClickListener(v -> {
            Bundle b = getIntent().getExtras();
            userName = b.getString("UserName");
            String str = ded.getText().toString();
            String date_1 = str.replaceAll("[^\\d.-]", "");
            final String ord_no = userName + "-" + date_1;
            Calendar c = Calendar.getInstance();
            int cYear = c.get(Calendar.YEAR);
            int cMonth = c.get(Calendar.MONTH) + 1;
            int cDay = c.get(Calendar.DAY_OF_MONTH);
            int gyear = myCalendar.get(Calendar.YEAR);
            //int max_date=cDay+2;
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
            int myNum = Integer.parseInt(get_ext_dt);
            int totalday_valid2 = cmonth_day + cDay - myNum;
            int total_back_day = Integer.parseInt(get_ext_dt);
            int rcYear = c.get(Calendar.YEAR);
            int rcMonth = c.get(Calendar.MONTH) + 1;
            int rcDay = c.get(Calendar.DAY_OF_MONTH);
            int rcYear_day = rcYear * 365;
            int rcMonth_day = rcMonth * 30;
            int rctotal_day_today = rcYear_day + rcMonth_day + rcDay;
            int total_valid_back_day = rctotal_day_today - total_back_day;
            int rgyear = myCalendar.get(Calendar.YEAR);
            int rgmonth = myCalendar.get(Calendar.MONTH) + 1;
            int rgday = myCalendar.get(Calendar.DAY_OF_MONTH);
            int rgyear_day = rgyear * 365;
            int rgmonth_day = rgmonth * 30;
            int rgtotal_day_given = rgyear_day + rgmonth_day + rgday;

            if ((ded.getText().toString().trim().equals("")) || (ded.getText().toString().trim().equals("Reference Date")) || (ded.getText().toString().trim().equals("Please Select date"))) {
                ded.setTextSize(14);
                ded.setText("Please Select date");
                ded.setTextColor(Color.RED);
            } else if (rgtotal_day_given > rctotal_day_today) {
                error_dt.setText("Delivery Date  is not greater than current date!");
            } else if (rgtotal_day_given < total_valid_back_day) {
                ded.setError("Delivery Date  is not less " + total_back_day + "  than days from current date");
                error_dt.setText("Delivery Date  is not less " + total_back_day + " than  days from current date");
            } else if (dcr_spinner.getSelectedItem().toString().equals("Select Dcr Type ")) {
                error_dt.setText("Select DCR Type");
                dcr_spinner.setPrompt("Select DCR Type");
            } else if (dcr_spinner.getSelectedItem().toString().equals("Select Dcr Type ")) {
                error_dt.setText("Select DCR Type");
                dcr_spinner.setPrompt("Select DCR Type");
            } else {
                Thread server = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        JSONParser jsonParser = new JSONParser();
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("ORD_NO", ord_no));
                        params.add(new BasicNameValuePair("MPO_CODE", userName));
                        params.add(new BasicNameValuePair("VISITOR_CODE", visitorcode.getText().toString()));
                        params.add(new BasicNameValuePair("TOUR_NATURE", loc_code));
                        params.add(new BasicNameValuePair("VISIT_DATE", ded.getText().toString()));
                        params.add(new BasicNameValuePair("DCR_TYPE", dt_code));
                        params.add(new BasicNameValuePair("AM_PM", shift_spinner.getSelectedItem().toString()));
                        params.add(new BasicNameValuePair("DATE", ded.getText().toString()));
                        params.add(new BasicNameValuePair("DOC_CODE", doc_code));
                        params.add(new BasicNameValuePair("Start_Time", s_time.getText().toString()));
                        params.add(new BasicNameValuePair("End_Time", e_time.getText().toString()));
                        params.add(new BasicNameValuePair("REMARKS", remarks.getText().toString()));
                        params.add(new BasicNameValuePair("COMPETITOR_ANALYSIS", comp_ana.getText().toString()));
                        params.add(new BasicNameValuePair("SHIFT", shift_status));
                        params.add(new BasicNameValuePair("VISIT_WITH", spinner.getSelectedItem().toString()));
                        params.add(new BasicNameValuePair("yes_no_val", yes_no_val));
                        JSONObject json = jsonParser.makeHttpRequest(submit_url, "POST", params);

                        try {
                            success = json.getInt(TAG_SUCCESS);
                            message = json.getString(TAG_MESSAGE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Intent in = getIntent();
                        Intent inten = getIntent();
                        Bundle bundle = in.getExtras();
                        inten.getExtras();
                        String MPO_CODE = bundle.getString("MPO_CODE");
                        userName = bundle.getString("UserName");
                        UserName_2 = bundle.getString("UserName_2");
                        Intent sameint = new Intent(Dcr.this, Dcr.class);
                        sameint.putExtra("Ord_NO", ord_no);
                        sameint.putExtra("UserName", userName);
                        sameint.putExtra("UserName_2", UserName_2);
                        startActivity(sameint);
                    }
                });
                server.start();
            }
        });

        next.setOnClickListener(v -> {
            Bundle f = getIntent().getExtras();
            userName = f.getString("UserName");
            String str = ded.getText().toString();
            String date_1 = str.replaceAll("[^\\d.-]", "");
            final String ord_no = userName + "-" + date_1;
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
            int myNum = Integer.parseInt(get_ext_dt);
            int totalday_valid2 = cmonth_day + cDay - myNum;
            int total_back_day = Integer.parseInt(get_ext_dt);
            int rcYear = c.get(Calendar.YEAR);
            int rcMonth = c.get(Calendar.MONTH) + 1;
            int rcDay = c.get(Calendar.DAY_OF_MONTH);
            int rcYear_day = rcYear * 365;
            int rcMonth_day = rcMonth * 30;
            int rctotal_day_today = rcYear_day + rcMonth_day + rcDay;
            int total_valid_back_day = rctotal_day_today - total_back_day;
            int rgyear = myCalendar.get(Calendar.YEAR);
            int rgmonth = myCalendar.get(Calendar.MONTH) + 1;
            int rgday = myCalendar.get(Calendar.DAY_OF_MONTH);
            int rgyear_day = rgyear * 365;
            int rgmonth_day = rgmonth * 30;
            int rgtotal_day_given = rgyear_day + rgmonth_day + rgday;

            if ((ded.getText().toString().trim().equals("")) || (ded.getText().toString().trim().equals("Reference Date")) || (ded.getText().toString().trim().equals("Please Select date"))) {
                ded.setTextSize(14);
                ded.setText("Please Select date");
                ded.setTextColor(Color.RED);
            } else if ((actv.getText().toString().trim().equals("")) || (actv.getText().toString().trim().equals("Input Customer (eg. dh..)"))) {
                actv.setError("Doctor  not Assigned !");
                actv.setText("Please insert  Doctor Name ");
                actv.setTextColor(Color.RED);
            } else if (rgtotal_day_given > rctotal_day_today) {
                error_dt.setText("Delivery Date  is not greater than current date!");
            } else if (rgtotal_day_given < total_valid_back_day) {
                ded.setError("Delivery Date  is not less " + total_back_day + "  than days from current date");
                error_dt.setText("Delivery Date  is not less " + total_back_day + " than  days from current date");
            } else if ((comp_ana.getText().toString().equals("Competitors activity analysis"))) {
                comp_ana.getText().toString().equals("");
                com_ana_val = "";
            } else {
                final Spinner nameSpinner =  findViewById(R.id.customer);
                final String selected_cust = actv.getText().toString();
                com_ana_val = comp_ana.getText().toString();
                Bundle b = getIntent().getExtras();
                final String userName = b.getString("UserName");
                String UserName_1 = b.getString("UserName_1");

                Thread next = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //Intent in = new Intent(Dcr.this, ProductOrder.class);
                        Intent in = new Intent(Dcr.this, GiftOrder.class);
                        Bundle extras = new Bundle();
                        String str = ded.getText().toString();
                        String date_1 = str.replaceAll("[^\\d.-]", "");
                        final String generated_ord_no = userName + "-" + date_1;
                        extras.putString("MPO_CODE", userName);
                        extras.putString("CUST_CODE", userName);
                        extras.putString("AM_PM", shift_spinner.getSelectedItem().toString());
                        extras.putString("ORDER_DELEVERY_DATE", ded.getText().toString());
                        extras.putString("ORDER_REFERANCE_NO", ref.getText().toString());
                        extras.putString("ord_no", generated_ord_no);
                        extras.putString("doc_code", doc_code);
                        extras.putString("end_time", e_time.getText().toString());
                        extras.putString("start_time", s_time.getText().toString());
                        extras.putString("Type", dt_code);
                        extras.putString("location code", loc_code);
                        extras.putString("VISITOR_CODE", visitorcode.getText().toString());
                        extras.putString("VISIT_DATE", ded.getText().toString());
                        extras.putString("REMARKS", remarks.getText().toString());
                        extras.putString("COMPETITOR_ANALYSIS", com_ana_val);
                        extras.putString("VISIT_WITH", spinner.getSelectedItem().toString());
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
        });
    }

    public void postDcrCheck() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Patient>> call = apiInterface.postDcrCheck(Dashboard.globalmpocode,ded.getText().toString() );
        Log.e("postDcrCheck-->", Dashboard.globalmpocode+"--"+ded.getText().toString());

        call.enqueue(new Callback<List<Patient>>() {
            @Override
            public void onResponse(@NonNull Call<List<Patient>> call, @NonNull retrofit2.Response<List<Patient>> response) {
                List<Patient> giftitemCount = response.body();
                assert giftitemCount != null;
                Log.e("getFlagValue-->", String.valueOf( giftitemCount.get(0).getFirst_name()));
                int dcr_flag = Integer.parseInt(giftitemCount.get(0).getFirst_name());
                if (response.isSuccessful()) {
                    if (dcr_flag == 0) {
                        location.setVisibility(View.VISIBLE);
                    } else {
                        location.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(Dcr.this, "Server error! Please try moments later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Patient>> call, Throwable t) {
                //pDialog.dismiss();
                //Toast.makeText(PrescriptionFollowup2.this, "Processing Prescription Count", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initCalender() {
        cal = Calendar.getInstance();
        df = new SimpleDateFormat("dd/MM/yyyy ");
        date_str = df.format(cal.getTime());
        ded.setText(date_str);
        myCalendar = Calendar.getInstance();

        date = new DatePickerDialog.OnDateSetListener() {
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
                ded.setTextColor(Color.BLACK);
                ded.setText(sdf.format(myCalendar.getTime()));
                postDcrCheck();
            }
        };
        ded.setOnClickListener(v -> {
            new DatePickerDialog(Dcr.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            //location.setVisibility(View.VISIBLE);
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void docCustomerVisitorAutocomplete() {
        actv = findViewById(R.id.autoCompleteTextView1);
        actv2 = findViewById(R.id.autoCompleteTextView2);
        actv3 = findViewById(R.id.autoCompleteTextView3);

        actv.setOnClickListener(v -> {
            if (!actv.getText().toString().equals("")) {
                String selectedcustomer = actv.getText().toString();
                cust.setTag(selectedcustomer);
            }
        });
        actv.addTextChangedListener(new TextWatcher() {
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

                    if (inputorder.contains("-")) {
                        Log.e("SelectedDoctor ==>", inputorder);
                        doc_code = inputorder.substring(inputorder.indexOf("-") + 1);
                        Log.e("Selecteddoc_code ==>", doc_code);
                        String[] first_split = inputorder.split("-");
                        doc_name = first_split[0].trim();
                        actv.setText(doc_name);
                        hideKeyboard(Dcr.this);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        actv.setOnTouchListener((v, event) -> {
            actv.showDropDown();
            return false;
        });
        actv2.setOnClickListener(v -> {
            if (actv2.getText().toString() != "") {
                String selectedcustomer2 = actv2.getText().toString();
                visitor.setTag(selectedcustomer2);
            }
        });
        actv2.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //actv.setTextColor(Color.BLACK);
            }

            @SuppressLint("CutPasteId")
            @Override
            public void afterTextChanged(final Editable s) {
                try {
                    final String inputorder = s.toString();
                    int total_string = inputorder.length();
                    if (inputorder.contains(":")) {
                        String cust_type = inputorder.substring(inputorder.indexOf(":") + 1);
                        String cust_type_with_note = inputorder.substring(inputorder.indexOf(":"));
                        String arr[] = cust_type_with_note.split(":");
                        cust_type_with_note = arr[1].trim();
                        String arr1[] = cust_type_with_note.split("///");
                        cashcredit = findViewById(R.id.cashcredit);

                        List list = new ArrayList();
                        for (int i = 1; i < arr1.length; i++) {
                            list.add(arr1[i].trim());
                        }
                        ArrayAdapter dataAdapter = new ArrayAdapter(Dcr.this, android.R.layout.simple_spinner_item, list);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        cashcredit.setAdapter(dataAdapter);

                        credit =  findViewById(R.id.credit);
                        List list_credit = new ArrayList();
                        for (int j = 1; j < arr1.length; j++) {
                            list_credit.add(arr1[j].trim());
                        }
                        ArrayAdapter dataAdapter_credit = new ArrayAdapter(Dcr.this, android.R.layout.simple_spinner_item, list);
                        dataAdapter_credit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        credit.setAdapter(dataAdapter);

                        int cust_type_with_note_length = cust_type_with_note.length();
                        String cust_address = s.toString();
                        String customer_address = cust_address.substring(0, total_string - cust_type_with_note_length);
                        String doc[] = customer_address.split("//");
                        String emp_name = doc[0].trim();
                        String emp_code = doc[1].trim();
                        String market[] = emp_code.split("-");
                        String emp_code1 = market[0].trim();
                        String mar_name = market[1].trim();
                        String mar[] = mar_name.split(":");
                        String mar_name1 = mar[0].trim();
                        actv2.setText(emp_name); //doctorname
                        visitorcode.setText(emp_code1);
                        cust_type = arr1[0];

                        if (cust_type.equals("CASH")) {
                            cash_party = 1;
                            credit_party = 0;
                            credit =  findViewById(R.id.credit);
                            cashcredit =  findViewById(R.id.cashcredit);
                            View credit_view =  findViewById(R.id.credit);
                            View cashcredit_view =  findViewById(R.id.cashcredit);
                            credit_view.setVisibility(View.GONE);
                            cashcredit_view.setVisibility(View.GONE);
                            s_time.setFocusable(true);
                            s_time.setFocusableInTouchMode(true);
                            s_time.requestFocus();
                        } else {
                            credit =  findViewById(R.id.credit);
                            cashcredit =  findViewById(R.id.cashcredit);
                            View credit_view =  findViewById(R.id.credit);
                            View cashcredit_view =  findViewById(R.id.cashcredit);
                            cashcredit_view.setVisibility(View.GONE);
                            credit_view.setVisibility(View.VISIBLE);
                            credit_view.setVisibility(View.GONE);
                            cash_party = 0;
                            credit_party = 1;
                            s_time.setFocusable(true);
                            s_time.setFocusableInTouchMode(true);
                            s_time.requestFocus();
                        }
                    }  //ded.setText("Select Date");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        actv2.setOnTouchListener((v, event) -> {
            actv2.showDropDown();
            return false;
        });
        actv3.setOnClickListener(v -> {
            if (actv3.getText().toString() != "") {
                String selectedchemist = actv3.getText().toString();
                chemist.setTag(selectedchemist);
            }
        });
        actv3.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //actv.setTextColor(Color.BLACK);
            }

            @Override
            public void afterTextChanged(final Editable s) {
                try {
                    //actv.setError("");
                    final String inputorder = s.toString();
                    int total_string = inputorder.length();

                    if (inputorder.contains(":")) {
                        String cust_type = inputorder.substring(inputorder.indexOf(":") + 1);
                        String cust_type_with_note = inputorder.substring(inputorder.indexOf(":"));
                        String arr[] = cust_type_with_note.split(":");
                        cust_type_with_note = arr[1].trim();
                        String chemist_first_index = arr[0].trim();
                        String arr1[] = cust_type_with_note.split("///");
                        int cust_type_with_note_length = cust_type_with_note.length();
                        String cust_address = s.toString();
                        String customer_address = cust_address.substring(0, total_string - cust_type_with_note_length);
                        String doc[] = customer_address.split("//");
                        String doc_name = doc[0].trim();
                        String doc_code = doc[1].trim();
                        String market[] = doc_code.split("-");
                        String doc_code1 = market[0].trim();
                        String mar_name = market[1].trim();
                        String mar[] = mar_name.split(":");
                        String mar_name1 = mar[0].trim();
                        actv3.setText(doc_name); //doctorname
                        doccode.setText(doc_code1); //doctorcode
                        marketcode.setText(mar_name1); //marketname
                        cust_type = arr1[0];
                        actv2.setFocusable(true);
                        actv2.setFocusableInTouchMode(true);
                        actv2.requestFocus();
                    }  //ded.setText("Select Date");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        actv3.setOnTouchListener((v, event) -> {
            actv3.showDropDown();
            return false;
        });
    }

    private void multiSpinnerinitViews() {
        spinner = findViewById(R.id.input1);
        List<String> list = new ArrayList<String>();
        list.add("GMSM");
        list.add("SM");
        list.add("DSM");
        list.add("ASM");
        list.add("RM");
        list.add("AM");
        spinner.setItems(list);
    }

    @SuppressLint({"SetTextI18n", "CutPasteId"})
    private void initViews() {
        fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        logout =  findViewById(R.id.logout);
        logout.setTypeface(fontFamily);
        logout.setText("\uf08b"); //&#xf08b
        user_show = findViewById(R.id.user_show);
        newversion = findViewById(R.id.newversion);
        setTitle("DCR Entry");
        next = findViewById(R.id.next);
        dcr_submit = findViewById(R.id.offline);
        chemist_ppm = findViewById(R.id.chemist_ppm);
        next.setTypeface(fontFamily);
        next.setText("\uf061");
        chemist_ppm.setTypeface(fontFamily);
        chemist_ppm.setText("\uf061");
        dcr_submit.setTypeface(fontFamily);
        dcr_submit.setText("\uf1d8");
        back =  findViewById(R.id.back);
        back.setTypeface(fontFamily);
        back.setText("\uf060 ");
        db = new DatabaseHandler(this);
        dcrdaterange = new ArrayList<>();
        error_dt = findViewById(R.id.errordt);
        error_payment = findViewById(R.id.errorpayment);
        op =  findViewById(R.id.orderpage);
        cust =  findViewById(R.id.customer);
        cust.setPrompt("Select Doctor");
        chemist =  findViewById(R.id.chemist);
        chemist.setPrompt("Select Chemist");
        ref =  findViewById(R.id.reference);
        ded = findViewById(R.id.deliverydate);
        today = new Date();
        remarks = findViewById(R.id.remarks);
        comp_ana = findViewById(R.id.comp_ana);
        s_time = findViewById(R.id.starttime);
        e_time = findViewById(R.id.endtime);
        ordno = findViewById(R.id.ordno);
        succ_msg = findViewById(R.id.succ_msg);
        date_ext = findViewById(R.id.date_extend);
        note = findViewById(R.id.note);
        mainlayout = findViewById(R.id.successmsg);
        dcrdatelist = new ArrayList<>();
        myTextView = findViewById(R.id.user_show);
        newversion = findViewById(R.id.newversion);

        Bundle b = getIntent().getExtras();
        name = b.getString("UserName_1");
        ename = b.getString("UserName_2");
        newversion_text = b.getString("new_version");
        newversion.setText("DCR Entry");

        if (b.isEmpty()) {
            String userName = "";
            myTextView.setText(userName);
        } else {
            userName = b.getString("UserName");
            UserName_2 = b.getString("UserName_2");
            ordernumber = b.getString("Ord_NO");
            myTextView.setText(UserName_2 + "(" + userName + ")");

            if (ordernumber == null) {
                mainlayout.setVisibility(LinearLayout.GONE);
            } else {
                //myTextView.setText(userName);
                UserName_3 = b.getString("UserName_2");
                if (UserName_3.equals("DCR Submitted")) {
                    ordno.setText(ordernumber);
                    ordno.setTextSize(12);
                }
                ordno.setText("DCR Sl No." + ordernumber);
                ordno.setTextSize(12);
                succ_msg.setText("Submitted");

                String[] mpo = ordernumber.split("-");
                //myTextView.setText(mpo[0])
                if (UserName_2.equals("DCR Submitted")) {
                    myTextView.setText(mpo[0]);
                    myTextView.setTextSize(12);
                } else {
                    myTextView.setText(mpo[0]);
                    myTextView.setTextSize(12);
                }
            }
        }
    }

    private void spinnerViews() {
        dcr_spinner = findViewById(R.id.dcrtype);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dcr_spinner.setPrompt("Select Dcr Type");
        dcr_spinner.setAdapter(adapter);
        dcr_spinner.setFocusable(true);
        dcr_spinner.setFocusableInTouchMode(true);
        dcr_spinner.requestFocus();
        dcr_spinner.setOnItemSelectedListener(this);
        chemordoc =  findViewById(R.id.chemordoc);
        ArrayAdapter<CharSequence> adapter_chem_doc = ArrayAdapter.createFromResource(this, R.array.cord, android.R.layout.simple_spinner_item);
        adapter_chem_doc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chemordoc.setPrompt("Visit to ");
        chemordoc.setAdapter(adapter_chem_doc);
        chemordoc.setOnItemSelectedListener(this);
        visitor = findViewById(R.id.visitor);
        visitor.setPrompt("Select Visitor");
        visitor.setOnItemSelectedListener(this);
        shiftlist = new ArrayList<Customer>();
        shift_spinner = findViewById(R.id.shift_spinner);
        ArrayAdapter<CharSequence> adapter_shift_spinner = ArrayAdapter.createFromResource(this, R.array.shift, android.R.layout.simple_spinner_item);
        adapter_shift_spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shift_spinner.setPrompt("Select Shift");
        shift_spinner.setAdapter(adapter_shift_spinner);
        shift_spinner.setOnItemSelectedListener(this);
        location = findViewById(R.id.location);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.location, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        location.setPrompt("Select Location");
        location.setAdapter(adapter1);
        location.setOnItemSelectedListener(this);
        location.setVisibility(View.VISIBLE);
        ampmspin =  findViewById(R.id.ampm);
        ArrayAdapter<CharSequence> ampm_adapter = ArrayAdapter.createFromResource(this, R.array.am_pm, android.R.layout.simple_spinner_item);
        yes_no = findViewById(R.id.ppm_type);
        ArrayAdapter<CharSequence> adapter0 = ArrayAdapter.createFromResource(this, R.array.yes_no, android.R.layout.simple_spinner_item);
        adapter0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yes_no.setPrompt("Visited with Promotional Material? ");
        yes_no.setAdapter(adapter0);
        yes_no.setVisibility(View.GONE);
        yes_no.setOnItemSelectedListener(this);
        cashcredit = findViewById(R.id.cashcredit);
        credit = findViewById(R.id.credit);
        cashcredit.setVisibility(View.GONE);
        credit.setVisibility(View.GONE);
    }

    private void autoCompleteTextViews() {
        doccode = findViewById(R.id.doccode);
        visitorcode = findViewById(R.id.visitorcode);
        marketcode = findViewById(R.id.marketcode);
        doccode.setCursorVisible(false);
        doccode.setPressed(false);
        doccode.setClickable(false);
        doccode.setTag(doccode.getKeyListener());
        doccode.setKeyListener(null);
        marketcode.setCursorVisible(false);
        marketcode.setPressed(false);
        marketcode.setClickable(false);
        marketcode.setTag(marketcode.getKeyListener());
        marketcode.setKeyListener(null);
    }

    private void btnVisibleInit() {
        back.setPressed(true);
        back.setClickable(true);
        back.setVisibility(View.VISIBLE);
        next.setClickable(false);
        next.setPressed(false);
        next.setEnabled(false);
        dcr_submit.setEnabled(false);
        chemist_ppm.setVisibility(View.GONE);
        dcr_submit.setClickable(false);
        dcr_submit.setPressed(false);
        customerlist = new ArrayList<Customer>();
        cust.setOnItemSelectedListener(this);
        visitorlist = new ArrayList<Customer>();
        visitor.setOnItemSelectedListener(this);
        chemistlist = new ArrayList<Customer>();
        chemist.setOnItemSelectedListener(this);
        dateextendlist = new ArrayList<Customer>();
    }

    private void populateSpinner() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < customerlist.size(); i++) {
            lables.add(customerlist.get(i).getName());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, lables);
        cust.setAdapter(spinnerAdapter);
        String[] customer = lables.toArray(new String[lables.size()]);
        // ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item, customer);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, customer);
        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        actv.setThreshold(2);
        actv.setAdapter(Adapter);
        actv.setTextColor(Color.parseColor("#006199"));
    }

    private void populateSpinner2() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < visitorlist.size(); i++) {
            lables.add(visitorlist.get(i).getName());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, lables);
        visitor.setAdapter(spinnerAdapter);

        String[] customer = lables.toArray(new String[lables.size()]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, customer);
        AutoCompleteTextView actv2 = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView2);
        actv2.setThreshold(2);
        actv2.setAdapter(Adapter);
        actv2.setTextColor(Color.parseColor("#006199"));
    }

    private void populateSpinnerShift() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < shiftlist.size(); i++) {
            lables.add(shiftlist.get(i).getName());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, lables);
        shift_spinner.setAdapter(spinnerAdapter);
    }

    private void populateSpinner3() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < chemistlist.size(); i++) {
            lables.add(chemistlist.get(i).getName());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, lables);
        chemist.setAdapter(spinnerAdapter);
        String[] customer = lables.toArray(new String[lables.size()]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, customer);
        AutoCompleteTextView actv3 = findViewById(R.id.autoCompleteTextView3);
        actv3.setThreshold(2);
        actv3.setAdapter(Adapter);
        actv3.setTextColor(Color.parseColor("#006199"));
    }

    class GetCategories extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Dcr.this);
            pDialog.setMessage("Fetching Doctors..");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... arg0) {
            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");

            String id = userName;
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", id));
            params.add(new BasicNameValuePair("mpo_lat",Dashboard.track_lat));
            params.add(new BasicNameValuePair("mpo_lang",Dashboard.track_lang));
            params.add(new BasicNameValuePair("shift", shift_spinner.getSelectedItem().toString()));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_CUSOTMER, ServiceHandler.POST, params);
            Log.e("json-->",json);
            customerlist.clear();

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
            populateSpinner();
        }
    }

    class GetEmp extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog2 = new ProgressDialog(Dcr.this);
            pDialog2.setMessage("Fetching Employees..");
            pDialog2.setCancelable(false);
            pDialog2.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            String id = userName;

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", id));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_EMP, ServiceHandler.POST, params);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    JSONArray customer = jsonObj.getJSONArray("customer");
                    for (int i = 0; i < customer.length(); i++) {
                        JSONObject catObj = (JSONObject) customer.get(i);
                        Customer custo = new Customer(catObj.getInt("id"), catObj.getString("name"));
                        visitorlist.add(custo);
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
            pDialog2.dismiss();
            populateSpinner2();
        }
    }

    private class GeTDateExtend extends AsyncTask<Void, Void, Void> {
        String json;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Dcr.this);
            pDialog.setTitle("MPO Base and Share");
            pDialog.setMessage("Loading Please Wait..");
            pDialog.setCancelable(true);
            //pDialog.show();
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            final JSONParser jsonParser = new JSONParser();
            final List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", Dashboard.globalmpocode));

            Thread server = new Thread(new Runnable() {
                @Override
                public void run() {
                    JSONObject json = jsonParser.makeHttpRequest(date_range_permission, "POST", params);
                    try {
                        success = json.getInt("success");
                        if (success == 1) {
                            get_ext_dt = json.getString("date_range");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.e("daterange===>", String.valueOf(get_ext_dt));
                                    int myNum = Integer.parseInt(get_ext_dt);
                                    if (myNum > 0) {
                                        ded.setEnabled(true);
                                    } else {
                                        ded.setEnabled(false);
                                        location.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            //pDialog.dismiss();
            //pDialog.cancel();
            server.start();
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            //mpo_base.setText(base);
            //mpo_share.setText(unit_share);
            //pDialog.dismiss();
        }
    }

    class GeTShift extends AsyncTask<Void, Void, Void> {
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
            //String json=jsonParser.makeServiceCall(URL_CUSOTMER, ServiceHandler.POST, params);
            String json = jsonParser.makeServiceCall(URL_SHIFT, ServiceHandler.POST, params);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray customer = jsonObj.getJSONArray("customer");
                        for (int i = 0; i < customer.length(); i++) {
                            JSONObject catObj = (JSONObject) customer.get(i);

                            Customer custo = new Customer(catObj.getInt("id"), catObj.getString("name"));
                            shiftlist.add(custo);
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
            //pDialog2.dismiss();
            populateSpinnerShift();
        }
    }

    class GetChemist extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Dcr.this);
            pDialog.setMessage("Fetching Chemist ...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... arg0) {
            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            String id = userName;
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", id));
            params.add(new BasicNameValuePair("shift", shift_spinner.getSelectedItem().toString()));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_CHEMIST, ServiceHandler.POST, params);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    JSONArray customer = jsonObj.getJSONArray("customer");
                    for (int i = 0; i < customer.length(); i++) {
                        JSONObject catObj = (JSONObject) customer.get(i);
                        Customer custo = new Customer(catObj.getInt("id"), catObj.getString("name"));
                        chemistlist.add(custo);
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
            populateSpinner3();
        }
    }

    class GetDCCChemist extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Dcr.this);
            pDialog.setMessage("Fetching DCC ...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... arg0) {
            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            String id = userName;
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", id));
            params.add(new BasicNameValuePair("shift", shift_spinner.getSelectedItem().toString()));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_DCCCHEMIST, ServiceHandler.POST, params);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    JSONArray customer = jsonObj.getJSONArray("customer");
                    for (int i = 0; i < customer.length(); i++) {
                        JSONObject catObj = (JSONObject) customer.get(i);
                        Customer custo = new Customer(catObj.getInt("id"), catObj.getString("name"));
                        chemistlist.add(custo);
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
            populateSpinner3();
        }
    }

    private class GetDcrDateOffline extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Bundle b = getIntent().getExtras();
            String id = b.getString("UserName");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", id));
            ServiceHandler jsonParser = new ServiceHandler();
            String jsonStr2 = jsonParser.makeServiceCall(get_dcr_date, ServiceHandler.POST, params);

            if (jsonStr2 != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr2);
                    JSONArray customers = jsonObj.getJSONArray("customer");
                    db.deleteDcrdata();
                    for (int i = 0; i < customers.length(); i++) {
                        JSONObject c = customers.getJSONObject(i);
                        String cust_id = c.getString("id");
                        String cust_name = c.getString("name");
                        String cust = c.getString("cust");
                        String mpo = c.getString("mpo");
                        HashMap<String, String> customer = new HashMap<>();
                        customer.put("id", cust_id);
                        customer.put("name", cust_name);
                        customer.put("cust", cust);
                        customer.put("mpo", mpo);
                        dcrdatelist.add(customer);
                        db.addDcrDate(new DcrDate(cust, cust_name));
                    }
                } catch (final JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Toast.makeText(getApplicationContext(), "Customer Updated" + e.getMessage() , Toast.LENGTH_LONG).show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Couldn't get json from server. Check LogCat for possible errors!", Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {}

    private void logoutUser() {
        //session.removeAttribute();
        //Intent intent = new Intent(Dcr.this, Login.class);
        //startActivity(intent);
        //finishActivity(BIND_ABOVE_CLIENT);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}
}
