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
import com.opl.pharmavector.doctorList.DoctorListActivity;
import com.opl.pharmavector.doctorList.model.DoctorFFList;
import com.opl.pharmavector.doctorList.model.DoctorFFModel;
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

public class PersonalExpenses extends Activity implements AdapterView.OnItemSelectedListener {
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
    public static String name = null, newversion_text = null, ename = null;
    public int credit_party = 0, cash_party = 0;
    SharedPreferences.Editor editor;
    public EditText osi, op, od, dateResult, ref;
    //private ListView cust;
    private ArrayList<Customer> customerlist;
    private ArrayList<Customer> mpodcrlist;
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
    public stirng pay_credit1;
    public String jour_code = null;
    private Spinner cust,ampmspin;
    private Spinner dcrlist;
    ProgressDialog pDialog, qDialog;
    EditText mEdit;
    TextView date2, ded, note, s_time, e_time, da, ta, tournature, dcr_nummber, othertaval, particul;
    public int success;
    public String message, ord_no, invoice, target_data, achivement, searchString, select_party;
    private String URL_DCR = BASE_URL+"mpodcr/mpo_pexpense/get_mpo_dcr.php";
    private String URL_JOURMODE = BASE_URL+"/mpodcr/mpo_pexpense/get_jour_mode.php";
    private String submit_dcr_expense = BASE_URL+"/mpodcr/mpo_pexpense/submit_dcr_expense.php";
    private ArrayList<MotorCycleRate> motorCycleRates = new ArrayList<>();
    LinearLayout mainlayout;
    protected Handler handler;
    EditText diskm;
    TextView error_dt,error_payment,ordno,succ_msg;

    @SuppressLint({"CutPasteId", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personalexpenses);

        motorCycleRateInfo();
        setTitle("Personal Expenses");
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        logout =  findViewById(R.id.logout);
        logout.setTypeface(fontFamily);
        logout.setText("\uf08b"); //&#xf08b
        user_show =  findViewById(R.id.user_show);
        newversion =  findViewById(R.id.newversion);
        next =  findViewById(R.id.next);
        next.setTypeface(fontFamily);
        next.setText("\uf1d8");
        back =  findViewById(R.id.view);
        back.setTypeface(fontFamily);
        back.setText("\uf060 ");
        error_dt =  findViewById(R.id.errordt);
        error_payment =  findViewById(R.id.errorpayment);
        op =  findViewById(R.id.orderpage);
        cust =  findViewById(R.id.customer);
        dcrlist =  findViewById(R.id.dcrlist);
        da =  findViewById(R.id.da);
        othertaval =  findViewById(R.id.othertaval);
        particul =  findViewById(R.id.particul);

        particul.setFilters(new InputFilter[] {
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
        diskm =  findViewById(R.id.diskm);
        ta =  findViewById(R.id.ta);
        cust.setPrompt("Select Doctor");
        dcrlist.setPrompt("List of Dcr");
        ref =  findViewById(R.id.reference);
        ded =  findViewById(R.id.tourdate);
        tournature =  findViewById(R.id.tournature);
        dcr_nummber =  findViewById(R.id.dcr_nummber);
        s_time =  findViewById(R.id.starttime);
        e_time =  findViewById(R.id.endtime);
         ordno =  findViewById(R.id.ordno);
        note =  findViewById(R.id.note);
         mainlayout =  findViewById(R.id.successmsg);
         succ_msg =  findViewById(R.id.succ_msg);
          ampmspin =  findViewById(R.id.ampm);
        cashcredit =  findViewById(R.id.cashcredit);
        credit =  findViewById(R.id.credit);
        cashcredit.setVisibility(View.GONE);
        credit.setVisibility(View.GONE);
        customerlist = new ArrayList<Customer>();
        cust.setOnItemSelectedListener(this);
        mpodcrlist = new ArrayList<Customer>();
        dcrlist.setOnItemSelectedListener(this);

        /*================================================================get customer========================================*/
        new GetCategories().execute();
        new GetJourmode().execute();
        /*===========================================================customer end===============================================*/

        final Spinner visitstatus =  findViewById(R.id.visitstatus);
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
                    int da_val_cov = Integer.parseInt(da_value_on_change.toString());
                    int on_change_work_stat = da_val_cov / 2;
                    String aString = Integer.toString(on_change_work_stat);
                    da.setText(aString);
                } else if (visitstatus.getSelectedItem().toString().equals("Full Day")) {
                    da.setTextColor(Color.BLUE);
                    da.setText(saved_da_val);
                }


            }

            public void onNothingSelected(AdapterView<?> adapterView) {

                return;
            }
        });


        final AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        final AutoCompleteTextView actv1 = (AutoCompleteTextView) findViewById(R.id.JourneyMode);
        diskm.setEnabled(false);

        actv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (actv.getText().toString() != "") {
                    String selectedcustomer = actv.getText().toString();
                    cust.setTag(selectedcustomer);
                }

            }
        });

        actv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (actv1.getText().toString() != "") {
                    String jourmode = actv1.getText().toString();
                    cust.setTag(jourmode);
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

                    if (inputorder.indexOf(":") != -1) {
                        String arr[] = inputorder.split(":");
                        String dcr_date_info_split = arr[0].trim();
                        String cust_type_with_note = arr[1].trim();
                        String arr1[] = cust_type_with_note.split("//");
                        Tour_nature_code = arr1[0].trim();
                        String dcr_expense_info_val = arr1[1].trim();
                        if (Tour_nature_code.toString().trim().equals("1")) {

                            othertaval.setVisibility(View.GONE);
                            particul.setVisibility(View.GONE);
                            othertaval.setEnabled(false);
                            particul.setEnabled(false);
                            diskm.setEnabled(false);
                            ta.setEnabled(false);
                        } else {


                            othertaval.setVisibility(View.VISIBLE);
                            particul.setVisibility(View.VISIBLE);
                            othertaval.setEnabled(true);
                            particul.setEnabled(true);
                            ta.setEnabled(true);
                        }
                        String arr0[] = dcr_date_info_split.split("//");
                        String dcr_date = arr0[0].trim();
                        String dcr_location_split = arr0[1].trim();
                        String arr3[] = dcr_location_split.split("/");
                        dcr_sl_no = arr3[0].trim();
                        String dcr_location_name = arr3[1].trim();
                        actv.setText(dcr_date);
                        tournature.setTextColor(Color.BLUE);
                        tournature.setText(dcr_location_name);
                        dcr_nummber.setText(dcr_sl_no);
                        String arr4[] = dcr_expense_info_val.split("##");
                        String da_val = arr4[1].trim(); //DAVALUE
                        saved_da_val = arr4[1].trim();
                        da.setTextColor(Color.BLUE);
                        da.setText(da_val);
                        String hq_ta_val_arr = arr4[0].trim();
                        String arr2[] = hq_ta_val_arr.split("/");
                        String hq_ta_val = arr2[1].trim();
                        ta.setTextColor(Color.BLUE);
                        ta.setText(hq_ta_val);

                        if (visitstatus.getSelectedItem().toString().equals("Half Day")) {

                            int da_val_cov = Integer.parseInt(da_val.toString());
                            int half_day_da = da_val_cov / 2;
                            da.setTextColor(Color.BLUE);
                            da.setText(half_day_da);

                        } else if (visitstatus.getSelectedItem().toString().equals("Full Day")) {
                            da.setTextColor(Color.BLUE);
                            da.setText(da_val);
                        }
                        actv1.setFocusable(true);
                        actv1.setFocusableInTouchMode(true);
                        actv1.requestFocus();
                        cashcredit =  findViewById(R.id.cashcredit);
                        credit =  findViewById(R.id.credit);


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

        actv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                actv.showDropDown();
                return false;
            }
        });

        actv1.addTextChangedListener(new TextWatcher() {

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

                        // String dcr_info = inputorder.substring(inputorder.indexOf(":") + 0);
                        String cust_type_with_note = inputorder.substring(inputorder.indexOf(":") + 0);


                        String arr[] = cust_type_with_note.split(":");
                        cust_type_with_note = arr[1].trim();


                        String arr1[] = cust_type_with_note.split("///");
                        String jour_mode_split = arr1[0].trim();
                        // String dcr_expense_info_val=arr1[1].trim();


                        String jour_code_split[] = jour_mode_split.split("-");
                        String jour_name = jour_code_split[0].trim();
                        jour_code = jour_code_split[1].trim();
                        actv1.setText(jour_name);

                        //  next.setEnabled(true);
                        //  next.setClickable(true);


                        if ((Tour_nature_code.toString().trim().equals("1"))) {
                            diskm.setEnabled(false);
                        }

                        if ((Tour_nature_code.toString().trim().equals("2")) || (Tour_nature_code.toString().trim().equals("3"))) {
                            diskm.setEnabled(true);
                            othertaval.setEnabled(true);
                            particul.setEnabled(true);
                        }


                        if (jour_code.toString().trim().equals("MC")) {
                            ta.setEnabled(false);

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


        actv1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                actv1.showDropDown();
                return false;
            }
        });

        final TextView myTextView =  findViewById(R.id.user_show);
        final TextView newversion =  findViewById(R.id.newversion);
        Bundle b = getIntent().getExtras();
        //String ordernumber=b.getString("Ord_NO");
        name = b.getString("UserName_1");
        ename = b.getString("UserName_2");
        newversion_text = b.getString("new_version");

        newversion.setText("Personal Expense Entry");


        if (b.isEmpty()) {

            String userName = "";
            myTextView.setText(userName);

        } else {


            String userName = b.getString("UserName");
            String UserName_2 = b.getString("UserName_2");
            myTextView.setText(UserName_2 +"\n" + "(" + userName + ")");
            String ordernumber = b.getString("Ord_NO");
            String invoice = b.getString("target");
            String tar = b.getString("invoice");
            String achivement = b.getString("achivement");
            if (ordernumber == null) {
                mainlayout.setVisibility(LinearLayout.GONE);
            } else {
                //myTextView.setText(userName);

                ordno.setText("Ord No." + ordernumber);
                ordno.setTextSize(8);

                succ_msg.setText("Submitted.");
                myTextView.setText(userName);
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
                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                error_dt.setText("");
                ded.setTextColor(Color.BLACK);
                ded.setText(sdf.format(myCalendar.getTime()));
            }

        };

        ded.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(PersonalExpenses.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        diskm.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (diskm.getText().toString().trim().equals("")) {
                    diskm.setText("0");
                }
                if (jour_code.trim().equals("MC")) {
                    int a = Integer.parseInt(diskm.getText().toString().trim());
                    if (motorCycleRates.size() > 0) {
                        double motorRate = Double.parseDouble(motorCycleRates.get(0).getUnit_exp());
                        double result = a * motorRate;
                        String total2 = Double.toString(result);
                        ta.setText(total2);
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {

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


        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            Intent i = new Intent(PersonalExpenses.this, Dashboard.class);
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
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });

                backthred.start();
            }
        });

        next.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

                Bundle b = getIntent().getExtras();
                userName = b.getString("UserName");
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
                int totalday_valid2 = cmonth_day + cDay - 0;

                if ((actv.getText().toString().trim().equals("")) || (actv.getText().toString().trim().equals("DCR Date (Type date like : 01  or 11 )"))) {
                    error_dt.setText("1.Select DCR DATE ");
                } else if (actv1.getText().toString().trim().equals("")) {
                    error_dt.setText(" 2.Select  Journey Mode");
                } else if ((Tour_nature_code.toString().trim().equals("2")) && jour_code.toString().trim().equals("MC") && (diskm.getText().toString().trim().equals(""))) {
                    error_dt.setText("3.Select Distance in KM");
                } else if ((Tour_nature_code.toString().trim().equals("3")) && jour_code.toString().trim().equals("MC") && (diskm.getText().toString().trim().equals(""))) {
                    error_dt.setText("4.OUTSTATION---Select Distance in KM");
                } else if ((Tour_nature_code.toString().trim().equals("2")) && !othertaval.getText().toString().isEmpty() && particul.getText().toString().equals("")) {
                    error_dt.setText("Enter Other T/A Particul (Ex:where did you spend the TA) ");
                } else if ((Tour_nature_code.toString().trim().equals("3")) && !othertaval.getText().toString().isEmpty() && particul.getText().toString().equals("")) {
                    error_dt.setText("Enter Other T/A Particul (Ex:where did you spend the TA) ");
                } else {
                    Thread server = new Thread(new Runnable() {
                        @Override
                        public void run() {
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
                            JSONObject json = jsonParser.makeHttpRequest(submit_dcr_expense, "POST", params);

                            try {
                                success = json.getInt(TAG_SUCCESS);
                                message = json.getString(TAG_MESSAGE);
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            Intent in = getIntent();
                            Intent inten = getIntent();
                            Bundle bundle = in.getExtras();
                            inten.getExtras();
                            Intent sameint = new Intent(PersonalExpenses.this, PersonalExpenses.class);
                            sameint.putExtra("Ord_NO", dcr_sl_no);
                            sameint.putExtra("UserName", Dashboard.globalmpocode);
                            sameint.putExtra("UserName_2", Dashboard.globalterritorycode);
                            startActivity(sameint);
                        }
                    });

                    server.start();
                }

            }
        });

    }

    private void populateSpinner() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < mpodcrlist.size(); i++) {
            lables.add(mpodcrlist.get(i).getName());
        }

       if(mpodcrlist.size()>0) {
           ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, lables);
           cust.setAdapter(spinnerAdapter);
           String[] customer = lables.toArray(new String[lables.size()]);
           ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, customer);
           AutoCompleteTextView actv =  findViewById(R.id.autoCompleteTextView1);
           actv.setAdapter(Adapter);
           actv.setTextColor(Color.BLUE);
       } else {
           AutoCompleteTextView actv =  findViewById(R.id.autoCompleteTextView1);
           actv.setAdapter(null);
           actv.setKeyListener(null);
       }
    }

    public void motorCycleRateInfo() {
        qDialog = new ProgressDialog(PersonalExpenses.this);
        qDialog = new ProgressDialog(PersonalExpenses.this);
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
                    Toast.makeText(PersonalExpenses.this, "No data Available", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MotorCycleModel> call, @NonNull Throwable t) {
                qDialog.dismiss();
                motorCycleRateInfo();
            }
        });
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
        actv1.setTextColor(Color.RED);
    }


    class GetCategories extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PersonalExpenses.this);
            pDialog.setMessage("Loading Data for Personal Expenses ...");
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
                            mpodcrlist.add(custo);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {

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


    class GetJourmode extends AsyncTask<Void, Void, Void> {

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
            String json = jsonParser.makeServiceCall(URL_JOURMODE, ServiceHandler.POST, params);

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

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            populateSpinner2();
            //  populateSpinner2();
        }

    }


    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    /*-----------------  End of Spinner view---------------------------*/

    private void logoutUser() {
        session.setLogin(false);
        // session.removeAttribute();
        session.invalidate();
        Intent intent = new Intent(PersonalExpenses.this, Login.class);
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
