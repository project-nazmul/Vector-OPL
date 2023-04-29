package com.opl.pharmavector;

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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Date;
import java.util.Objects;

import static android.content.ContentValues.TAG;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import com.opl.pharmavector.amdashboard.VacantList;
import com.opl.pharmavector.amdashboard.VacantModel;
import com.opl.pharmavector.giftfeedback.GiftFeedbackEntry;
import com.opl.pharmavector.model.Patient;
import com.opl.pharmavector.mpodcr.GiftOrder;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;

public class AmDcr extends Activity implements OnItemSelectedListener {
    private Spinner spinner1, spinner2, cashcredit, cashcredit_test, credit;
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_MESSAGE_1 = "message_1";
    public static final String TAG_invoice = "invoice";
    public static final String TAG_target = "target";
    public static final String TAG_achivement = "achivement";
    public static final String TAG_MESSAGE_new_version = "new_version";
    public String vcheckloccode;
    public String yes_no_val = "";
    private Button logout, achivbtn;
    private TextView user_show, newversion, visit_with_value, visit_with_value_1, visit_with_value_2, visit_with_value_3, visit_with_value_4;
    private SessionManager session;
    public Button next, back, dcr_submit, chemist_ppm, rx_page;
    public static String name = null, newversion_text = null, ename = null;
    public int credit_party = 0, cash_party = 0, vacantStatus = 1;
    Editor editor;
    public EditText osi, op, od, dateResult, ref, date_ext;
    //private ListView cust;
    private ArrayList<com.opl.pharmavector.AmCustomer> customerlist;
    private ArrayList<com.opl.pharmavector.AmCustomer> visitorlist;
    private ArrayList<com.opl.pharmavector.AmCustomer> chemistlist;
    private ArrayList<com.opl.pharmavector.AmCustomer> shiftlist;
    private ArrayList<com.opl.pharmavector.AmCustomer> dateextendlist;
    public Array pay_cash;
    public Array pay_cradit;
    private com.opl.pharmavector.AmDatabaseHandler db;
    private String f_name, s_name;
    private Button mOffline;
    private ListView lv;
    private com.opl.pharmavector.Contact dataModel;
    Toast toast;
    Toast toast2;
    Toast toast3;
    boolean status;
    RadioButton unregisterTerritory;
    public String dcr_code = "";
    public String dt_code;
    public String com_ana_val;
    public String pay_cash1, userName, userName_1, userName_2;
    public stirng pay_credit1;
    public String location_code;
    public String loc_code;
    public String v_location_code;
    public String v_loc_code;
    private Spinner cust, visitor, chemist, shift_spinner, dcr_date_extend, v_location;
    ProgressDialog pDialog, pDialog2;
    EditText mEdit;
    TextView date2, ded, note, s_time, e_time, remarks, comp_ana, ratio_of_opso;
    EditText no_of_rx, no_of_prod, prod_of_opsonin;
    public int success;
    public String message, ord_no, invoice, target_data, achivement, searchString, select_party;
    ArrayList<HashMap<String, String>> dcrdatelist;
    ArrayList<HashMap<String, String>> dcrdaterange;
    public String shift_status = "Morning";
    public String CHEM_FLAG = " ";
    public String get_ext_dt;
    private final String URL_CUSOTMER = BASE_URL + "area_manager_api/amdcr/get_doctor.php";
    private final String URL_EMP = BASE_URL + "area_manager_api/amdcr/getemp.php";
    private final String URL_CHEMIST = BASE_URL + "area_manager_api/amdcr/get_chemist.php";
    private final String submit_doctor_url = BASE_URL + "area_manager_api/amdcr/dcrsubmit_doctor.php";
    private final String get_dcr_date = BASE_URL + "area_manager_api/amdcr/get_dcr.php";
    private final String URL_SHIFT = BASE_URL + "area_manager_api/amdcr/getshift.php";
    private final String date_range_permission = BASE_URL + "area_manager_api/amdcr/date_range_permission.php";
    protected Handler handler;
    DialogMultipleChoice mDialogMultipleChoice;
    Typeface fontFamily;
    LinearLayout mainlayout, territoryLayout;
    TextView error_dt, error_payment;
    Date today;
    TextView ordno, succ_msg;
    Spinner dcr_spinner, location, ampmspin, chemordoc, yes_no;
    AutoCompleteTextView doccode, visitorcode, marketcode, vacantMpo;
    String dat_val_ext, vacantMpoCode = "0000";
    List<VacantList> vacantLists;

    @SuppressLint({"CutPasteId", "ClickableViewAccessibility", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.amdcr);

        initViews();
        new GetDcrDateOffline().execute();
        findViewById(R.id.show_multiple_dialog).setOnClickListener(view -> mDialogMultipleChoice.show(s_time));

        new GeTShift().execute();
        new GeTDateExtend().execute();
        new GetEmp().execute();
        getVacantMpoList();

        dat_val_ext = date_ext.getText().toString().trim();
        final MultiSelectionSpinner spinner = findViewById(R.id.input1);
        List<String> list = new ArrayList<String>();
        list.add("GMSM");
        list.add("SM");
        list.add("DSM");
        list.add("ASM");
        list.add("RM");
        list.add("MPO");
        //list.add("AM as MPO");
        spinner.setItems(list);

        final AutoCompleteTextView actv = findViewById(R.id.autoCompleteTextView1);
        final AutoCompleteTextView actv2 = findViewById(R.id.autoCompleteTextView2);
        final AutoCompleteTextView actv3 = findViewById(R.id.autoCompleteTextView3);
        final AutoCompleteTextView actv4 = findViewById(R.id.autoCompleteTextView4);

        status = unregisterTerritory.isChecked();
        unregisterTerritory.setOnClickListener(v -> {
            if (status) {
                vacantStatus = 1;
                unregisterTerritory.setChecked(false);
                status = false;
                territoryLayout.setVisibility(View.GONE);
            } else {
                vacantStatus = 2;
                unregisterTerritory.setChecked(true);
                status = true;
                territoryLayout.setVisibility(View.VISIBLE);
                vacantMpo.setTag("Select Vacant Territory (Type Mpo name)");
                populateVacantMpo();
            }
            Log.d("AmDcr", "unregisterTerritory clicked : " + status);
        });
        actv.setOnClickListener(v -> {
            if (!actv.getText().toString().equals("")) {
                String selectedcustomer = actv.getText().toString();
                System.out.println("Selectedcustomer = " + selectedcustomer);
                cust.setTag(selectedcustomer);
            }
        });
        vacantMpo.setOnTouchListener((v, event) -> {
            vacantMpo.showDropDown();
            return false;
        });
        actv.setOnTouchListener((v, event) -> {
            actv.showDropDown();
            return false;
        });
        actv2.setOnTouchListener((v, event) -> {
            actv2.showDropDown();
            return false;
        });
        actv3.setOnTouchListener((v, event) -> {
            actv3.showDropDown();
            return false;
        });
        actv4.setOnTouchListener((v, event) -> {
            actv4.showDropDown();
            return false;
        });
        actv4.setOnClickListener(v -> {
            if (!actv.getText().toString().equals("")) {
                String selectedcustomer = actv.getText().toString();
                System.out.println("Selectedcustomer = " + selectedcustomer);
                cust.setTag(selectedcustomer);
            }
        });
        actv2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!actv2.getText().toString().equals("")) {
                    String selectedcustomer2 = actv2.getText().toString();
                    System.out.println("Selectedcustomer = " + selectedcustomer2);
                    visitor.setTag(selectedcustomer2);
                }
            }
        });
        actv3.setOnClickListener(v -> {
            if (!actv3.getText().toString().equals("")) {
                String selectedchemist = actv3.getText().toString();
                Toast.makeText(getBaseContext(), "selected chemist " + selectedchemist, Toast.LENGTH_LONG).show();
                System.out.println("Selectedcustomer = " + selectedchemist);
                chemist.setTag(selectedchemist);
            }
        });
        vacantMpo.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(final Editable s) {
                try {
                    final String inputorder = s.toString();

                    if (inputorder.contains("-")) {
                        String[] arr = inputorder.split("//");
                        String cust_type_with_note = arr[0].trim();
                        vacantMpo.setText(arr[1].trim());

                        String[] first_split = cust_type_with_note.split("-");
                        if (first_split.length > 0) {
                            vacantMpoCode = first_split[first_split.length-1];
                        }
                        Log.d("vacantCode", vacantMpoCode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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

                    if (inputorder.contains(":")) {
                        String cust_type = inputorder.substring(inputorder.indexOf(":") + 1);
                        String cust_type_with_note = inputorder.substring(inputorder.indexOf(":"));
                        String[] arr = cust_type_with_note.split(":");
                        cust_type_with_note = arr[1].trim();

                        String[] first_split = inputorder.split(":");
                        String split1 = first_split[0].trim();
                        String market_code = first_split[1].trim();
                        Log.e("split1", split1);
                        marketcode.setText(market_code);

                        String[] second_split = split1.split("//");
                        String split2 = second_split[0].trim();
                        String doc_code = second_split[1].trim();
                        doccode.setText(doc_code); //doctorcode

                        String[] third_split = split2.split("-");
                        String doc_name = third_split[0].trim();
                        String mpocodedoc = third_split[1].trim();
                        doccode.setText(doc_code); //doctorcode
                        actv.setText(doc_name);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            private void length() {}
        });
        actv4.addTextChangedListener(new TextWatcher() {
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
                        String[] arr = cust_type_with_note.split(":");
                        cust_type_with_note = arr[1].trim();
                        Log.e("amdoctor: ", "> " + cust_type_with_note);
                        Log.w("amdoctor: ", "> " + cust_type_with_note);
                        String[] arr1 = cust_type_with_note.split("///");
                        cashcredit = findViewById(R.id.cashcredit);

                        List list = new ArrayList();
                        for (int i = 1; i < arr1.length; i++) {
                            list.add(arr1[i].trim());
                        }
                        ArrayAdapter dataAdapter = new ArrayAdapter(AmDcr.this, android.R.layout.simple_spinner_item, list);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        cashcredit.setAdapter(dataAdapter);
                        credit = findViewById(R.id.credit);
                        List list_credit = new ArrayList();
                        for (int j = 1; j < arr1.length; j++) {
                            list_credit.add(arr1[j].trim());
                        }
                        ArrayAdapter dataAdapter_credit = new ArrayAdapter(AmDcr.this, android.R.layout.simple_spinner_item, list);
                        dataAdapter_credit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        credit.setAdapter(dataAdapter);

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

                        actv4.setText(doc_name);   ///doctorname
                        doccode.setText(doc_code1); //doctorcode
                        marketcode.setText(mar_name1); //marketname

                        cust_type = arr1[0];

                        if (cust_type.equals("CASH")) {
                            cash_party = 1;
                            credit_party = 0;
                            credit = findViewById(R.id.credit);
                            cashcredit = findViewById(R.id.cashcredit);
                            View credit_view = findViewById(R.id.credit);
                            View cashcredit_view = findViewById(R.id.cashcredit);
                            credit_view.setVisibility(View.GONE);
                            cashcredit_view.setVisibility(View.GONE);
                            // cashcredit_view.setVisibility(View.VISIBLE);
                            Log.e("cashcredit_view: ", "> " + cashcredit_view);


                            findViewById(R.id.show_multiple_dialog).setFocusableInTouchMode(true);
                            findViewById(R.id.show_multiple_dialog).setFocusable(true);
                            findViewById(R.id.show_multiple_dialog).setFocusableInTouchMode(true);
                            findViewById(R.id.show_multiple_dialog).requestFocus();


                            spinner.setFocusable(true);
                            spinner.setFocusableInTouchMode(true);
                            spinner.requestFocus();


                        } else {

                            findViewById(R.id.show_multiple_dialog).setFocusableInTouchMode(true);
                            findViewById(R.id.show_multiple_dialog).setFocusable(true);
                            findViewById(R.id.show_multiple_dialog).setFocusableInTouchMode(true);
                            findViewById(R.id.show_multiple_dialog).requestFocus();

                            spinner.setFocusable(true);
                            spinner.setFocusableInTouchMode(true);
                            spinner.requestFocus();

                            credit = findViewById(R.id.credit);
                            cashcredit = findViewById(R.id.cashcredit);
                            View credit_view = findViewById(R.id.credit);
                            View cashcredit_view = findViewById(R.id.cashcredit);
                            cashcredit_view.setVisibility(View.GONE);
                            credit_view.setVisibility(View.VISIBLE);
                            credit_view.setVisibility(View.GONE);
                            cash_party = 0;
                            credit_party = 1;

                        }

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
        actv2.addTextChangedListener(new TextWatcher() {

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
                    if (inputorder.indexOf(":") != -1) {
                        String cust_type = inputorder.substring(inputorder.indexOf(":") + 1);
                        String cust_type_with_note = inputorder.substring(inputorder.indexOf(":") + 0);


                        String arr[] = cust_type_with_note.split(":");
                        cust_type_with_note = arr[1].trim();
                        String arr1[] = cust_type_with_note.split("///");


                        cashcredit = findViewById(R.id.cashcredit);

                        List list = new ArrayList();
                        for (int i = 1; i < arr1.length; i++) {
                            list.add(arr1[i].trim());
                        }


                        ArrayAdapter dataAdapter = new ArrayAdapter(AmDcr.this, android.R.layout.simple_spinner_item, list);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        cashcredit.setAdapter(dataAdapter);


                        credit = findViewById(R.id.credit);
                        List list_credit = new ArrayList();
                        for (int j = 1; j < arr1.length; j++) {
                            list_credit.add(arr1[j].trim());
                        }
                        ArrayAdapter dataAdapter_credit = new ArrayAdapter(AmDcr.this, android.R.layout.simple_spinner_item, list);
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
                        actv2.setText(emp_name);   ///doctorname


                        visitorcode.setText(emp_code1);


                        Log.e("emp code: ", "> " + emp_code1);

                        cust_type = arr1[0];

                        if (cust_type.equals("CASH")) {
                            cash_party = 1;
                            credit_party = 0;
                            credit = findViewById(R.id.credit);
                            cashcredit = findViewById(R.id.cashcredit);
                            View credit_view = findViewById(R.id.credit);
                            View cashcredit_view = findViewById(R.id.cashcredit);
                            credit_view.setVisibility(View.GONE);
                            cashcredit_view.setVisibility(View.GONE);
                            // cashcredit_view.setVisibility(View.VISIBLE);
                            Log.e("cashcredit_view: ", "> " + cashcredit_view);


                            s_time.setFocusable(true);
                            s_time.setFocusableInTouchMode(true);
                            s_time.requestFocus();

                        } else {

                            credit = findViewById(R.id.credit);
                            cashcredit = findViewById(R.id.cashcredit);
                            View credit_view = findViewById(R.id.credit);
                            View cashcredit_view = findViewById(R.id.cashcredit);
                            cashcredit_view.setVisibility(View.GONE);
                            credit_view.setVisibility(View.VISIBLE);
                            credit_view.setVisibility(View.GONE);
                            cash_party = 0;
                            credit_party = 1;

                            s_time.setFocusable(true);
                            s_time.setFocusableInTouchMode(true);
                            s_time.requestFocus();

                        }

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
        actv3.addTextChangedListener(new TextWatcher() {

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

                    if (inputorder.contains(":")) {

                        String cust_type = inputorder.substring(inputorder.indexOf(":") + 1);
                        String cust_type_with_note = inputorder.substring(inputorder.indexOf(":") + 0);


                        String arr[] = cust_type_with_note.split(":");
                        cust_type_with_note = arr[1].trim();

                        String chemist_first_index = arr[0].trim();

                        String arr1[] = cust_type_with_note.split("///");

                        Log.w("Selected date", "> " + cust_type + chemist_first_index + cust_type_with_note);


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


                        actv3.setText(doc_name);   ///doctorname
                        doccode.setText(doc_code1); //doctorcode
                        marketcode.setText(mar_name1); //marketname
                        CHEM_FLAG = "Y";

                        cust_type = arr1[0];

                        actv2.setFocusable(true);
                        actv2.setFocusableInTouchMode(true);
                        actv2.requestFocus();


                    } else {
                        //ded.setText("Select Date");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            private void length() {}
        });
        remarks.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!actv2.getText().toString().equals("")) {
                    final String remarksvalue = remarks.getText().toString();
                }
            }
        });
        comp_ana.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actv2.getText().toString() != "") {
                    final String comp_anavalue = comp_ana.getText().toString();
                }
            }
        });
        final TextView myTextView = findViewById(R.id.user_show);
        final TextView newversion = findViewById(R.id.newversion);
        Bundle b = getIntent().getExtras();
        name = b.getString("UserName_1");
        ename = b.getString("UserName_2");
        newversion_text = b.getString("new_version");
        newversion.setText("DCR Entry");

        if (b.isEmpty()) {
            String userName = "";
            myTextView.setText(userName);
        } else {
            String userName = b.getString("UserName");
            String UserName_2 = b.getString("UserName_2");
            String ordernumber = b.getString("Ord_NO");
            myTextView.setText(UserName_2 + "(" + userName + ")");

            if (ordernumber == null) {
                mainlayout.setVisibility(LinearLayout.GONE);
            } else {
                String UserName_3 = b.getString("UserName_2");
                if (UserName_3.equals("DCR Submitted")) {
                    ordno.setText(ordernumber);
                    ordno.setTextSize(10);
                }
                ordno.setText("DCR Sl No." + ordernumber);
                ordno.setTextSize(10);
                succ_msg.setText("Submitted");
                String[] mpo = ordernumber.split("-");

                if (UserName_2.equals("DCR Submitted")) {
                    myTextView.setText(mpo[0]);
                    myTextView.setTextSize(10);
                } else {
                    myTextView.setText(mpo[0]);
                    myTextView.setTextSize(10);
                }
            }
        }

        final Calendar cal = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("dd/MM/yyyy ");
        String date_str = df.format(cal.getTime());
        ded.setText(date_str);

        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {
                //String myFormat = "dd/MM/yyyy";
                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                error_dt.setText("");
                //note.setText("" );
                //ded.setTextSize(14);
                ded.setTextColor(Color.BLACK);
                ded.setText(sdf.format(myCalendar.getTime()));
                Log.w("Selected date", "> " + ded.getText().toString());
            }
        };
        ded.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AmDcr.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                location.setVisibility(View.VISIBLE);
            }
        });
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
                    actv4.setVisibility(view.GONE);
                    yes_no.setVisibility(View.GONE);
                    chemordoc.setVisibility(View.VISIBLE);
                    chemordoc.setEnabled(true);
                    chemordoc.setClickable(true);
                    v_location.setVisibility(View.GONE);

                    no_of_rx.setVisibility(View.GONE);
                    no_of_prod.setVisibility(View.GONE);
                    prod_of_opsonin.setVisibility(View.GONE);

                    rx_page.setClickable(true);
                    rx_page.setPressed(true);
                    rx_page.setEnabled(true);

                    spinner.setVisibility(View.VISIBLE);
                    s_time.setVisibility(View.VISIBLE);
                    e_time.setVisibility(View.VISIBLE);


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


                    actv4.setVisibility(view.GONE);
                    v_location.setVisibility(View.GONE);
                    no_of_rx.setVisibility(View.GONE);
                    no_of_prod.setVisibility(View.GONE);
                    prod_of_opsonin.setVisibility(View.GONE);

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
                    rx_page.setClickable(true);
                    rx_page.setPressed(true);
                    rx_page.setEnabled(true);


                    spinner.setVisibility(View.VISIBLE);
                    s_time.setVisibility(View.VISIBLE);
                    e_time.setVisibility(View.VISIBLE);

                } else if (dcr_code.equals("RX")) {

                    dt_code = "X";

                    new RXGetDoctors().execute();
                    actv4.setVisibility(view.VISIBLE);
                    actv4.setEnabled(true);
                    actv4.setClickable(true);
                    actv4.setFocusable(true);


                    if (db.checkOrdNo()) {

                        location.setVisibility(View.GONE);
                        actv4.requestFocus();

                    } else {
                        location.setVisibility(View.VISIBLE);

                        location.setFocusable(true);
                        location.setFocusableInTouchMode(true);
                        location.requestFocus();
                    }


                    chemordoc.setVisibility(View.GONE);
                    yes_no.setVisibility(View.GONE);
                    actv.setVisibility(view.GONE);
                    actv3.setVisibility(view.GONE);


                    v_location.setVisibility(View.VISIBLE);
                    no_of_rx.setVisibility(View.VISIBLE);
                    no_of_prod.setVisibility(View.VISIBLE);
                    prod_of_opsonin.setVisibility(View.VISIBLE);


                    spinner.setVisibility(View.GONE);
                    s_time.setVisibility(View.GONE);
                    e_time.setVisibility(View.GONE);

                    dcr_submit.setEnabled(false);
                    dcr_submit.setPressed(false);
                    dcr_submit.setClickable(false);


                    rx_page.setClickable(true);
                    rx_page.setPressed(true);
                    rx_page.setEnabled(true);


                }


            }

            public void onNothingSelected(AdapterView<?> adapterView) {

                return;
            }
        });


        location.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                location_code = location.getSelectedItem().toString();

                loc_code = location_code.substring(0, 1).toString();

                Log.e("loccode", "> " + loc_code);
                final String check = String.valueOf(loc_code);
                Log.e("checkloccode", "> " + check);

                if (dt_code.equals("X")) {

                    no_of_rx.setFocusable(true);
                    no_of_rx.setFocusableInTouchMode(true);
                    no_of_rx.requestFocus();

                } else {


                }


            }

            public void onNothingSelected(AdapterView<?> adapterView) {

                return;
            }
        });


        v_location.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                v_location_code = v_location.getSelectedItem().toString();

                v_loc_code = v_location_code.substring(0, 1).toString();

                Log.e("vloccode", "> " + v_loc_code);
                vcheckloccode = String.valueOf(v_loc_code);
                Log.e("vcheckloccode", "> " + vcheckloccode);

                no_of_rx.setFocusable(true);
                no_of_rx.setFocusableInTouchMode(true);
                no_of_rx.requestFocus();


            }

            public void onNothingSelected(AdapterView<?> adapterView) {

                return;
            }
        });


        s_time.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;

                mTimePicker = new TimePickerDialog(AmDcr.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        s_time.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();


                e_time.setFocusable(true);
                e_time.setFocusableInTouchMode(true);
                e_time.requestFocus();


            }
        });


        e_time.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AmDcr.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        e_time.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

                //  location.setFocusable(true);
                //  location.setFocusableInTouchMode(true);
                //  location.requestFocus();
                Log.w(" am_pm_check ", "> " + s_time.getText().toString());

                String check_shift = s_time.getText().toString();

                String arr[] = check_shift.split(":");
                String day_shift = arr[0].trim();
                int shift_check = Integer.parseInt(day_shift);
                Log.w("day_shift", "> " + day_shift);


            }
        });

        session = new SessionManager(getApplicationContext());


        shift_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                shift_status = shift_spinner.getSelectedItem().toString();

                if (dcr_code.equals("Regular")) {


                    Log.e("Regular", "> " + dt_code);


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
                    } else {

                        Log.e("else is Regular", "> " + shift_spinner.getSelectedItem().toString());

                    }


                } else if (dcr_code.equals("Journey") || dcr_code.equals("Conference") || dcr_code.equals("Training") || dcr_code.equals("Others") || dcr_code.equals("Meeting")
                        || dcr_code.equals("Holiday") || dcr_code.equals("Leave")
                ) {

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
                Toast.makeText(AmDcr.this, "Please Select Shift !!", Toast.LENGTH_LONG).show();
                return;

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
                    yes_no.setClickable(true);
                    yes_no.setFocusableInTouchMode(true);
                    yes_no.requestFocus();
                    actv.setVisibility(View.VISIBLE);
                    actv3.setVisibility(View.GONE);
                } else if (chemordoc.getSelectedItem().toString().equals("Chemist")) {
                    actv3.setFocusable(true);
                    actv3.setFocusableInTouchMode(true);
                    actv3.requestFocus();
                    new GetChemist().execute();
                    yes_no.setVisibility(View.VISIBLE);
                    yes_no.setFocusable(true);
                    yes_no.setClickable(true);
                    yes_no.setFocusableInTouchMode(true);
                    yes_no.requestFocus();
                    next.setVisibility(View.GONE);
                    actv.setVisibility(View.GONE);
                    actv3.setVisibility(View.VISIBLE);
                    chemist_ppm.setVisibility(View.VISIBLE);
                    chemist_ppm.setEnabled(true);
                    chemist_ppm.setPressed(true);
                    chemist_ppm.setClickable(true);
                }
            }
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        yes_no.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                yes_no_val = yes_no.getSelectedItem().toString();
                if (yes_no.getSelectedItem().toString().equals("Yes")) {
                    next.setVisibility(View.VISIBLE);
                    dcr_submit.setPressed(false);
                    dcr_submit.setClickable(false);
                    next.setEnabled(true);
                    next.setPressed(true);
                    next.setClickable(true);
                    actv.setFocusable(true);
                    actv.setFocusableInTouchMode(true);
                    actv.requestFocus();
                } else if (yes_no.getSelectedItem().toString().equals("No")) {
                    next.setVisibility(View.GONE);
                    dcr_submit.setVisibility(View.VISIBLE);
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
                    Log.v("value", "> " + yes_no.getSelectedItem().toString());
                }
            }
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread mysells = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                });
                mysells.start();
            }
        });

        logout.setOnClickListener(new OnClickListener() {
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

        chemist_ppm.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Bundle f = getIntent().getExtras();
                userName = f.getString("UserName");
                Log.w("Mpo Code", "> " + userName);
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
                int total_back_day = Integer.parseInt(date_ext.getText().toString());

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

                final Spinner nameSpinner = findViewById(R.id.customer);
                final String selected_cust = actv.getText().toString();
                Bundle b = getIntent().getExtras();
                final String userName = b.getString("UserName");
                String UserName_1 = b.getString("UserName_1");
                Thread next = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent in = new Intent(AmDcr.this, AmChemistGiftOrder.class);
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
                        extras.putString("doc_code", doccode.getText().toString());
                        extras.putString("end_time", e_time.getText().toString());
                        extras.putString("start_time", s_time.getText().toString());
                        extras.putString("Type", dt_code);
                        extras.putString("location code", loc_code);
                        extras.putString("VISITOR_CODE", visitorcode.getText().toString());
                        extras.putString("VISIT_DATE", ded.getText().toString());
                        extras.putString("CHEM_CODE", doccode.getText().toString());
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


        rx_page.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {


                Bundle f = getIntent().getExtras();
                userName = f.getString("UserName");
                Log.w("Mpo Code", "> " + userName);
                String str = ded.getText().toString();
                String date_1 = str.replaceAll("[^\\d.-]", "");
                final String ord_no = userName + "-" + date_1;


                Calendar c = Calendar.getInstance();
                int cYear = c.get(Calendar.YEAR);
                int cMonth = c.get(Calendar.MONTH) + 1;
                int cDay = c.get(Calendar.DAY_OF_MONTH);

                int gyear = myCalendar.get(Calendar.YEAR);

                //  int max_date=cDay+2;
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


                //------------------------rez
                int total_back_day = Integer.parseInt(date_ext.getText().toString());

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


                if ((dcr_code.equals("Regular"))) {

                    error_dt.setTextColor(Color.RED);
                    error_dt.setText("Select DCR Type 'RX' .Click on Dcr Type to select 'RX' ");

                } else if (dcr_code.equals("Journey") || dcr_code.equals("Conference") || dcr_code.equals("Training") || dcr_code.equals("Others") || dcr_code.equals("Meeting")
                        || dcr_code.equals("Holiday") || dcr_code.equals("Leave")) {

                    error_dt.setTextColor(Color.RED);
                    error_dt.setText("Select DCR Type 'RX' .Click on Dcr Type to select 'RX' ");


                } else if (no_of_rx.getText().toString().trim().equals("") || (no_of_rx.getText().toString().trim().equals("No Of Rx"))) {

                    no_of_rx.setTextSize(14);
                    no_of_rx.setText("Please Select No of Rx");
                    no_of_rx.setTextColor(Color.RED);

                } else if (no_of_prod.getText().toString().trim().equals("") || (no_of_prod.getText().toString().trim().equals("No Of Products") ||

                        (no_of_prod.getText().toString().trim().equals("Please Select No of Products"))
                )) {

                    no_of_prod.setTextSize(14);
                    no_of_prod.setText("Please Select No of Products");
                    no_of_prod.setTextColor(Color.RED);

                } else if (prod_of_opsonin.getText().toString().trim().equals("") || (prod_of_opsonin.getText().toString().trim().equals("No Of Opsonin Products") ||

                        (prod_of_opsonin.getText().toString().trim().equals("Please Select No of Opsonin Products")
                        ))) {

                    prod_of_opsonin.setTextSize(14);
                    prod_of_opsonin.setText("Please Select No of Opsonin Products");
                    prod_of_opsonin.setTextColor(Color.RED);

                } else if (rgtotal_day_given > rctotal_day_today) {

                    error_dt.setText("Delivery Date  is not greater than current date!");

                } else if (rgtotal_day_given < total_valid_back_day) {


                    ded.setError("Delivery Date  is not less " + total_back_day + "  than days from current date");
                    error_dt.setText("Delivery Date  is not less " + total_back_day + " than  days from current date");
                } else {
                    final Spinner nameSpinner = findViewById(R.id.customer);
                    final String selected_cust = actv.getText().toString();


                    // final String select_party1 = select_party.toString();
                    Bundle b = getIntent().getExtras();
                    final String userName = b.getString("UserName");
                    String UserName_1 = b.getString("UserName_1");

                    Thread next = new Thread(new Runnable() {
                        @Override
                        public void run() {

                            Intent in = new Intent(AmDcr.this, AmRxCompany.class);
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
                            extras.putString("doc_code", doccode.getText().toString());
                            extras.putString("end_time", e_time.getText().toString());
                            extras.putString("start_time", s_time.getText().toString());
                            extras.putString("Type", dt_code);
                            extras.putString("location code", loc_code);
                            extras.putString("VISITOR_CODE", visitorcode.getText().toString());
                            extras.putString("VISIT_DATE", ded.getText().toString());
                            extras.putString("vcheckloccode", vcheckloccode);
                            extras.putString("no_rx", no_of_rx.getText().toString());
                            extras.putString("no_prod", no_of_prod.getText().toString());
                            extras.putString("prod_of_opsonin", prod_of_opsonin.getText().toString());
                            Bundle b = getIntent().getExtras();
                            String userName = b.getString("UserName");
                            String UserName_1 = b.getString("UserName_1");
                            extras.putString("MPO_CODE", userName);
                            extras.putString("UserName_1", UserName_1);
                            //extras.putString("MPO_CODE",user_show.getText().toString() );
                            in.putExtras(extras);
                            startActivity(in);
                        }
                    });
                    next.start();
                }
            }
        });

        dcr_submit.setOnClickListener(v -> {
            Bundle b1 = getIntent().getExtras();
            userName = b1.getString("UserName");
            String str = ded.getText().toString();
            String date_1 = str.replaceAll("[^\\d.-]", "");
            final String ord_no = userName + "-" + date_1;
            Log.w("dcr_submit order no", "> " + ord_no);

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

            int myNum = Integer.parseInt(date_ext.getText().toString());

            int totalday_valid2 = cmonth_day + cDay - myNum;

            //------------------------rez
            int total_back_day = Integer.parseInt(date_ext.getText().toString());

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

            //-----------------------
            if (vacantStatus == 1) {
                if ((ded.getText().toString().trim().equals("")) || (ded.getText().toString().trim().equals("Reference Date")) || (ded.getText().toString().trim().equals("Please Select date"))) {
                    ded.setTextSize(14);
                    ded.setText("Please Select date");
                    ded.setTextColor(Color.RED);
                } else if (dcr_code.equals("RX")) {
                    error_dt.setText("Click  'Other Company Products' Button");
                } else if (dcr_spinner.getSelectedItem().toString().equals("Select Dcr Type ")) {
                    error_dt.setText("Select DCR Type");
                    dcr_spinner.setPrompt("Select DCR Type");
                } else if (rgtotal_day_given > rctotal_day_today) {
                    //ded.setError( "Delivery Date  is not more than 6 days" );
                    error_dt.setText("Delivery Date  is not greater than current date!");
                } //else if (totalday_given < totalday_valid2) {
                else if (rgtotal_day_given < total_valid_back_day) {
                    error_dt.setText("Previous date can not be less than " + myNum + " days from current date.. ");
                } else if (dcr_spinner.getSelectedItem().toString().equals("Select Dcr Type ")) {
                    error_dt.setText("Select DCR Type");
                    dcr_spinner.setPrompt("Select DCR Type");
                } else {
                    Thread server = new Thread(() -> {
                        JSONParser jsonParser = new JSONParser();
                        List<NameValuePair> params = new ArrayList<>();
                        params.add(new BasicNameValuePair("ORD_NO", ord_no));
                        params.add(new BasicNameValuePair("MPO_CODE", userName));
                        params.add(new BasicNameValuePair("VISITOR_CODE", visitorcode.getText().toString()));
                        params.add(new BasicNameValuePair("TOUR_NATURE", loc_code));
                        params.add(new BasicNameValuePair("VISIT_DATE", ded.getText().toString()));
                        params.add(new BasicNameValuePair("DCR_TYPE", dt_code));
                        //params.add(new BasicNameValuePair("AM_PM", shift_status));
                        params.add(new BasicNameValuePair("AM_PM", shift_spinner.getSelectedItem().toString()));
                        params.add(new BasicNameValuePair("DATE", ded.getText().toString()));
                        params.add(new BasicNameValuePair("DOC_CODE", doccode.getText().toString()));
                        params.add(new BasicNameValuePair("Start_Time", s_time.getText().toString()));
                        params.add(new BasicNameValuePair("End_Time", e_time.getText().toString()));
                        params.add(new BasicNameValuePair("REMARKS", remarks.getText().toString()));
                        params.add(new BasicNameValuePair("COMPETITOR_ANALYSIS", comp_ana.getText().toString()));
                        params.add(new BasicNameValuePair("SHIFT", shift_status));
                        params.add(new BasicNameValuePair("VISIT_WITH", spinner.getSelectedItem().toString()));
                        params.add(new BasicNameValuePair("yes_no_val", yes_no_val));
                        params.add(new BasicNameValuePair("CHEM_FLAG", CHEM_FLAG));

                        JSONObject json = jsonParser.makeHttpRequest(submit_doctor_url, "POST", params);
                        try {
                            success = json.getInt(TAG_SUCCESS);
                            message = json.getString(TAG_MESSAGE);
                            Log.w("please wait ...." + message, json.toString());
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
                        Intent sameint = new Intent(AmDcr.this, AmDcr.class);
                        sameint.putExtra("Ord_NO", ord_no);
                        sameint.putExtra("UserName", userName);
                        sameint.putExtra("UserName_2", UserName_2);
                        startActivity(sameint);
                        Log.w("Passed in DCR TO DCR", ord_no + "UserName" + userName + "UserName_2" + UserName_2);
                    });
                    server.start();
                }
            }
            else if (vacantStatus == 2) {
                if (!vacantMpoCode.equals("0000")) {
                    Log.d("vacantMpoCode", "2");
                    Thread server = new Thread(() -> {
                        JSONParser jsonParser = new JSONParser();
                        List<NameValuePair> params = new ArrayList<>();
                        params.add(new BasicNameValuePair("ORD_NO", ord_no));
                        params.add(new BasicNameValuePair("MPO_CODE", vacantMpoCode));
                        params.add(new BasicNameValuePair("VISITOR_CODE", visitorcode.getText().toString()));
                        params.add(new BasicNameValuePair("TOUR_NATURE", loc_code));
                        params.add(new BasicNameValuePair("VISIT_DATE", ded.getText().toString()));
                        params.add(new BasicNameValuePair("DCR_TYPE", dt_code));
                        //params.add(new BasicNameValuePair("AM_PM", shift_status));
                        params.add(new BasicNameValuePair("AM_PM", shift_spinner.getSelectedItem().toString()));
                        params.add(new BasicNameValuePair("DATE", ded.getText().toString()));
                        params.add(new BasicNameValuePair("DOC_CODE", doccode.getText().toString()));
                        params.add(new BasicNameValuePair("Start_Time", s_time.getText().toString()));
                        params.add(new BasicNameValuePair("End_Time", e_time.getText().toString()));
                        params.add(new BasicNameValuePair("REMARKS", remarks.getText().toString()));
                        params.add(new BasicNameValuePair("COMPETITOR_ANALYSIS", comp_ana.getText().toString()));
                        params.add(new BasicNameValuePair("SHIFT", shift_status));
                        //params.add(new BasicNameValuePair("VISIT_WITH", spinner.getSelectedItem().toString()));
                        params.add(new BasicNameValuePair("VISIT_WITH", "AM as MPO"));
                        params.add(new BasicNameValuePair("yes_no_val", yes_no_val));
                        params.add(new BasicNameValuePair("CHEM_FLAG", CHEM_FLAG));

                        JSONObject json = jsonParser.makeHttpRequest(submit_doctor_url, "POST", params);
                        try {
                            success = json.getInt(TAG_SUCCESS);
                            message = json.getString(TAG_MESSAGE);
                            Log.w("please wait ...." + message, json.toString());
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
                        Intent sameint = new Intent(AmDcr.this, AmDcr.class);
                        sameint.putExtra("Ord_NO", ord_no);
                        sameint.putExtra("UserName", userName);
                        sameint.putExtra("UserName_2", UserName_2);
                        startActivity(sameint);
                        Log.w("Passed in DCR TO DCR", ord_no + "UserName" + userName + "UserName_2" + UserName_2);
                    });
                    server.start();
                }
                else {
                    Log.d("vacantMpoCode", "2");
                    vacantMpo.setText("Please Select Mpo");
                    vacantMpo.setTextColor(Color.RED);
                }
            }
        });

/*
        next.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Bundle f = getIntent().getExtras();
                userName = f.getString("UserName");
                Log.w("Mpo Code", "> " + userName);
                String str = ded.getText().toString();
                String date_1 = str.replaceAll("[^\\d.-]", "");
                final String ord_no = userName + "-" + date_1;

                Calendar c = Calendar.getInstance();
                int cYear = c.get(Calendar.YEAR);
                int cMonth = c.get(Calendar.MONTH) + 1;
                int cDay = c.get(Calendar.DAY_OF_MONTH);

                int gyear = myCalendar.get(Calendar.YEAR);

                //  int max_date=cDay+2;
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

                int myNum = Integer.parseInt(date_ext.getText().toString());

                int totalday_valid2 = cmonth_day + cDay - myNum;


                //------------------------rez
                int total_back_day = Integer.parseInt(date_ext.getText().toString());

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
                } else if (totalday_given < totalday_valid2) {
                    ded.setError("Delivery Date  is not less " + myNum + "  than days");
                    error_dt.setText("Delivery Date  is not less " + myNum + " than  days ");
                } else if ((actv.getText().toString().trim().equals("")) || (actv.getText().toString().trim().equals("Input Customer (eg. dh..)"))) {
                    actv.setError("Doctor  not Assigned !");
                    actv.setText("Please insert  Doctor Name ");
                    actv.setTextColor(Color.RED);

                }else if ((comp_ana.getText().toString().equals("Competitors activity analysis"))) {

                    comp_ana.getText().toString().equals("");
                    com_ana_val = "";

                } else {

                        final Spinner nameSpinner = findViewById(R.id.customer);
                        final String selected_cust = actv.getText().toString();

                        com_ana_val = comp_ana.getText().toString();
                        Bundle b = getIntent().getExtras();
                        final String userName = b.getString("UserName");
                        String UserName_1 = b.getString("UserName_1");
                        Thread next = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Intent in = new Intent(AmDcr.this, AmGiftOrder.class);
                                Bundle extras = new Bundle();
                                String str = ded.getText().toString();
                                String date_1 = str.replaceAll("[^\\d.-]", "");

                                final String generated_ord_no = userName + "-" + date_1;

                                extras.putString("MPO_CODE", userName);
                                extras.putString("CUST_CODE", userName);
                                extras.putString("AM_PM", shift_spinner.getSelectedItem().toString());
                                //extras.putString("cash_credit", select_party.toString());
                                extras.putString("ORDER_DELEVERY_DATE", ded.getText().toString());
                                extras.putString("ORDER_REFERANCE_NO", ref.getText().toString());
                                extras.putString("ord_no", generated_ord_no);
                                extras.putString("doc_code", doccode.getText().toString());
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
                                //extras.putString("MPO_CODE",user_show.getText().toString() );
                                in.putExtras(extras);
                                startActivity(in);
                            }
                        });
                        next.start();
                }
            }
        });
*/
    }

    private void initViews() {
        fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        logout = findViewById(R.id.logout);
        logout.setTypeface(fontFamily);
        logout.setText("\uf08b"); //&#xf08b
        user_show = findViewById(R.id.user_show);
        newversion = findViewById(R.id.newversion);
        setTitle("DCR Entry");
        next = findViewById(R.id.next);
        next.setTypeface(fontFamily);
        chemist_ppm = findViewById(R.id.chemist_ppm);
        dcr_submit = findViewById(R.id.offline);
        dcr_submit.setTypeface(fontFamily);
        next.setText("\uf061");

        chemist_ppm.setTypeface(fontFamily);
        chemist_ppm.setText("\uf061");

        dcr_submit.setText("\uf1d8");

        db = new com.opl.pharmavector.AmDatabaseHandler(this);
        dcrdaterange = new ArrayList<>();
        error_dt = findViewById(R.id.errordt);
        error_payment = findViewById(R.id.errorpayment);
        op = findViewById(R.id.orderpage);
        cust = findViewById(R.id.customer);
        cust.setPrompt("Select Doctor");
        chemist = findViewById(R.id.chemist);
        chemist.setPrompt("Select Chemist");
        ref = (EditText) findViewById(R.id.reference);
        vacantMpo = findViewById(R.id.autoUnregisTerritory);
        unregisterTerritory = findViewById(R.id.radioTerritory);
        territoryLayout = findViewById(R.id.territoryLayout);
        ded = findViewById(R.id.deliverydate);
        v_location = findViewById(R.id.v_location);
        no_of_rx = (EditText) findViewById(R.id.no_of_rx);
        no_of_prod = (EditText) findViewById(R.id.no_of_prod);
        prod_of_opsonin = (EditText) findViewById(R.id.prod_of_opsonin);
        ratio_of_opso = findViewById(R.id.ratio_of_opso);
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

        dcr_spinner = findViewById(R.id.dcrtype);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dcr_spinner.setPrompt("Select Dcr Type");
        dcr_spinner.setAdapter(adapter);
        dcr_spinner.setFocusable(true);
        dcr_spinner.setFocusableInTouchMode(true);
        dcr_spinner.requestFocus();
        dcr_spinner.setOnItemSelectedListener(this);
        visitor = findViewById(R.id.visitor);
        visitor.setPrompt("Select Visitor");
        //visitor.setSelected(false);  //must
        //visitor.setSelection(0, true);  //must
        visitor.setOnItemSelectedListener(this);
        shift_spinner = findViewById(R.id.shift_spinner);
        shift_spinner.setPrompt("Select Shift");
        //shift_spinner.setSelected(false);  //must
        //shift_spinner.setSelection(0, true);  //must
        shift_spinner.setOnItemSelectedListener(this);
        mDialogMultipleChoice = new DialogMultipleChoice(this);
        cashcredit = findViewById(R.id.cashcredit);
        credit = findViewById(R.id.credit);
        cashcredit.setVisibility(View.GONE);
        credit.setVisibility(View.GONE);
        back = findViewById(R.id.back);

        back.setTypeface(fontFamily);
        back.setText("\uf060"); //&#xf060
        rx_page = findViewById(R.id.rx_page);
        doccode = findViewById(R.id.doccode);
        visitorcode = findViewById(R.id.visitorcode);
        marketcode = findViewById(R.id.marketcode);

        location = findViewById(R.id.location);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.location, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        location.setPrompt("Select Location");
        location.setAdapter(adapter1);
        location.setOnItemSelectedListener(this);
        location.setVisibility(View.VISIBLE);


        v_location = findViewById(R.id.v_location);
        ArrayAdapter<CharSequence> adapter_v_location = ArrayAdapter.createFromResource(this, R.array.rx_location, android.R.layout.simple_spinner_item);
        adapter_v_location.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        v_location.setPrompt("Select Visit Location");
        v_location.setAdapter(adapter_v_location);
        v_location.setOnItemSelectedListener(this);
        v_location.setVisibility(View.VISIBLE);


        ampmspin = findViewById(R.id.ampm);
        ArrayAdapter<CharSequence> ampm_adapter = ArrayAdapter.createFromResource(this, R.array.am_pm, android.R.layout.simple_spinner_item);
        ampm_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ampmspin.setPrompt("Select Shift");
        ampmspin.setAdapter(ampm_adapter);
        ampmspin.setOnItemSelectedListener(this);


        chemordoc = findViewById(R.id.chemordoc);
        ArrayAdapter<CharSequence> adapter_chem_doc = ArrayAdapter.createFromResource(this, R.array.cord, android.R.layout.simple_spinner_item);
        adapter_chem_doc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chemordoc.setPrompt("Visit to ");
        chemordoc.setAdapter(adapter_chem_doc);
        chemordoc.setOnItemSelectedListener(this);


        yes_no = findViewById(R.id.yes_no);
        ArrayAdapter<CharSequence> adapter0 = ArrayAdapter.createFromResource(this, R.array.yes_no_am, android.R.layout.simple_spinner_item);
        adapter0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yes_no.setPrompt("Visited with PM? ");
        yes_no.setAdapter(adapter0);
        yes_no.setOnItemSelectedListener(this);

        customerlist = new ArrayList<com.opl.pharmavector.AmCustomer>();
        cust.setOnItemSelectedListener(this);
        visitorlist = new ArrayList<com.opl.pharmavector.AmCustomer>();
        visitor.setOnItemSelectedListener(this);
        chemistlist = new ArrayList<com.opl.pharmavector.AmCustomer>();
        chemist.setOnItemSelectedListener(this);
        dateextendlist = new ArrayList<com.opl.pharmavector.AmCustomer>();
        shiftlist = new ArrayList<com.opl.pharmavector.AmCustomer>();
        shift_spinner.setOnItemSelectedListener(this);
        shift_spinner.setOnItemSelectedListener(this);


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
        next.setClickable(false);
        next.setPressed(false);
        next.setEnabled(false);
        dcr_submit.setEnabled(false);
        chemist_ppm.setVisibility(View.GONE);
        dcr_submit.setClickable(false);
        dcr_submit.setPressed(false);
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
        AutoCompleteTextView actv = findViewById(R.id.autoCompleteTextView1);
        actv.setThreshold(2);
        actv.setAdapter(Adapter);
        actv.setTextColor(Color.parseColor("#006199"));
    }

    private void populateVacantMpo() {
        if (vacantLists.size() > 0) {
            List<String> vacant = new ArrayList<String>();
            for (int i = 0; i < vacantLists.size(); i++) {
                vacant.add(vacantLists.get(i).getName());
            }
            String[] customer = vacant.toArray(new String[0]);
            ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, customer);
            AutoCompleteTextView actv = findViewById(R.id.autoUnregisTerritory);
            actv.setThreshold(2);
            actv.setAdapter(Adapter);
            actv.setTextColor(Color.parseColor("#006199"));
        }
    }

    private void rxpopulateSpinner() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < customerlist.size(); i++) {
            lables.add(customerlist.get(i).getName());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, lables);
        cust.setAdapter(spinnerAdapter);
        String[] customer = lables.toArray(new String[lables.size()]);
        //ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item, customer);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, customer);
        AutoCompleteTextView actv4 = findViewById(R.id.autoCompleteTextView4);
        actv4.setThreshold(2);
        actv4.setAdapter(Adapter);
        actv4.setTextColor(Color.parseColor("#006199"));
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
        AutoCompleteTextView actv2 = findViewById(R.id.autoCompleteTextView2);
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

    private void populatedcr_date_extend() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < dateextendlist.size(); i++) {
            get_ext_dt = dateextendlist.get(i).getName();
        }
        date_ext.setText(get_ext_dt);
        int myNum = Integer.parseInt(date_ext.getText().toString());

        if (myNum > 0) {
            ded.setEnabled(true);
        } else {
            ded.setEnabled(false);
        }
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
            pDialog = new ProgressDialog(AmDcr.this);
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
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_CUSOTMER, ServiceHandler.POST, params);
            Log.e("Response: ", "> " + json);

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
        }
    }

    class RXGetDoctors extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AmDcr.this);
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
            params.add(new BasicNameValuePair("shift", shift_spinner.getSelectedItem().toString()));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_CUSOTMER, ServiceHandler.POST, params);

            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray customer = jsonObj.getJSONArray("customer");
                        for (int i = 0; i < customer.length(); i++) {
                            JSONObject catObj = (JSONObject) customer.get(i);
                            com.opl.pharmavector.AmCustomer custo = new com.opl.pharmavector.AmCustomer(catObj.getInt("id"), catObj.getString("name"));
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
            rxpopulateSpinner();
        }

    }


    class GetEmp extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog2 = new ProgressDialog(AmDcr.this);
            pDialog2.setMessage("Fetching Employees..");
            pDialog2.setCancelable(false);
            pDialog2.show();

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
            //String json=jsonParser.makeServiceCall(URL_CUSOTMER, ServiceHandler.POST, params);
            String json = jsonParser.makeServiceCall(URL_EMP, ServiceHandler.POST, params);
            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    JSONArray customer = jsonObj.getJSONArray("customer");
                    for (int i = 0; i < customer.length(); i++) {
                        JSONObject catObj = (JSONObject) customer.get(i);
                        AmCustomer custo = new AmCustomer(catObj.getInt("id"), catObj.getString("name"));
                        visitorlist.add(custo);
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
            pDialog2.dismiss();
            populateSpinner2();
        }
    }

    class GeTDateExtend extends AsyncTask<Void, Void, Void> {
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
            String json = jsonParser.makeServiceCall(date_range_permission, ServiceHandler.POST, params);
            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    JSONArray customer = jsonObj.getJSONArray("customer");
                    for (int i = 0; i < 1; i++) {
                        JSONObject catObj = (JSONObject) customer.get(i);
                        AmCustomer custo = new AmCustomer(catObj.getInt("id"), catObj.getString("name"));
                        dateextendlist.add(custo);
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
            populatedcr_date_extend();
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
            String json = jsonParser.makeServiceCall(URL_SHIFT, ServiceHandler.POST, params);
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
            //pDialog2.dismiss();
            populateSpinnerShift();
        }
    }

    class GetChemist extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AmDcr.this);
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
                        AmCustomer custo = new AmCustomer(catObj.getInt("id"), catObj.getString("name"));
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
                    Log.e(TAG, "Json parsing error: " + e.getMessage());

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
            //Dismiss the progress dialog
            //if (pDialog.isShowing())
            //pDialog.dismiss();
        }
    }

    public void getVacantMpoList() {
        ProgressDialog pDialog;
        pDialog = new ProgressDialog(AmDcr.this);
        pDialog.setTitle("Please wait !");
        pDialog.setMessage("Loading Vacant Mpo List..");
        pDialog.setCancelable(true);
        pDialog.show();

        Bundle b = getIntent().getExtras();
        String userName = b.getString("UserName");
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<VacantModel> call = apiInterface.getVacantMpoList(userName);

        call.enqueue(new Callback<VacantModel>() {
            @Override
            public void onResponse(@NotNull Call<VacantModel> call, @NotNull retrofit2.Response<VacantModel> response) {
                vacantLists = Objects.requireNonNull(response.body()).getVacantLists();
                Log.d("vacant=>","response==>"+ vacantLists.toString());

                if (response.code() == 200) {
                    pDialog.dismiss();
                    //Log.e("patientdetail==>", String.valueOf(giftitemCount.size()));
                } else {
                    pDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<VacantModel> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(AmDcr.this, "Network error! Please wait while we are reconnecting", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {}

    private void logoutUser() {
        session.setLogin(false);
        //session.removeAttribute();
        session.invalidate();
        Intent intent = new Intent(AmDcr.this, Login.class);
        startActivity(intent);
        finishActivity(BIND_ABOVE_CLIENT);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}
}
