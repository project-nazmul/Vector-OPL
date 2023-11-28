package com.opl.pharmavector.pcconference;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;


import com.google.android.material.textfield.TextInputLayout;
import com.opl.pharmavector.Customer;
import com.opl.pharmavector.JSONParser;
import com.opl.pharmavector.MultiSelectionSpinner2;
import com.opl.pharmavector.PCDashboard;
import com.opl.pharmavector.ServiceHandler;
import com.opl.pharmavector.R;
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

public class PcConferenceEdit extends Activity implements AdapterView.OnItemSelectedListener {
    private Toolbar toolbar;
    private EditText mpo_name, territory_name, mpo_code, venue_charge, food, miscell, miscell_bdt, impact, fd_bdt;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPassword;
    private TextView user_show;
    private Button btnSignUp;
    private EditText pc_rmp, doc_chemist, in_house;
    private ArrayList<Customer> customerlist;
    private ArrayList<Customer> brandlist;
    private ArrayList<Customer> dateextendlist;
    private ArrayList<Customer> dateextendlist2;
    private ArrayList<Customer> dateextendlist3;
    private ArrayList<Customer> dateextedsetupdata;
    private ArrayList<Customer> checkdatelist;
    private ArrayList<Customer> checkflag;
    private EditText market_name, venue_name, total_participent;
    EditText ded;
    public int pc_rmp_val = 0;
    public int result_participant = 0;
    public int doc_val = 0;
    public int inhouse_val = 0;
    public int food_val = 0;
    public MultiSelectionSpinner2 spinner;
    private Spinner cust;
    public String proposed_conference_date;
    ProgressDialog pDialog;
    public String market_code, m_name, food_allowance, v_charge, miscell_bdt_val, miscell_val;
    public String userName, UserName_2, pc_conference_serial, user_flag;
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_MESSAGE_1 = "message_1";
    public static final String TAG_invoice = "invoice";
    public static final String TAG_target = "target";
    public static final String TAG_achivement = "achivement";
    public static final String TAG_MESSAGE_new_version = "new_version";
    public int success;
    public String message, ord_no, invoice, target_data, achivement, searchString, select_party;
    public String get_ext_dt, date_flag, check_flag, get_ext_dt2, get_pc_conference_setup_data, get_ext_dt3;
    public String conf_type_val;
    public String budget_limit, person_limit, total_conference;
    private final String URL_CUSOTMER = BASE_URL+"pcconference/pc_conf_edit/pc_mkt_restructure_vw.php";
    private final String URL_PC_PRODUCT = BASE_URL+"pcconference/pc_conf_edit/pc_productlist.php";
    private final String pc_conference_range_permission = BASE_URL+"pcconference/pc_conf_edit/pc_conference_range_permission.php";
    private final String pc_conference_date_check = BASE_URL+"pcconference/pc_conf_edit/pc_conference_date_check.php";
    private final String pc_conference_check_flag = BASE_URL+"pcconference/pc_conf_edit/pc_conference_check_flag.php";
    private final String pc_conference_get_data_edit = BASE_URL+"pcconference/pc_conf_edit/pc_conference_get_data_edit.php";
    private final String pc_conference_get_data_edit2 = BASE_URL+"pcconference/pc_conf_edit/pc_conference_get_data_edit2.php";
    private final String pc_conference_get_data_edit3 = BASE_URL+"pcconference/pc_conf_edit/pc_conference_get_data_edit3.php";
    private final String pc_conference_submit = BASE_URL+"pcconference/pc_conf_edit/pc_conference_submit_data_edit.php";
    TextView error_dt, succ_msg, ordno;
    Button back, logback;
    LinearLayout mainlayout;
    AutoCompleteTextView actv;
    String new_version, ordernumber;
    Typeface fontFamily;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pc_conference_proposal_edit);
        initViews();

        new GeTPcConferenceEditData().execute();
        new GeTPcConferenceEditData2().execute();
        new GeTPcConferenceEditData3().execute();
        new GetMarket().execute();
        new GeTPcConferenceSetupData().execute();
        participantCalc();
        foodvalCalc();

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
                ded.setText(sdf.format(myCalendar.getTime()));
                proposed_conference_date = ded.getText().toString().trim();
                new GeTPcConferenceDate().execute();
                new GeTPcConferenceFlag().execute();
            }
        };
        ded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(PcConferenceEdit.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                hideKeyboard(v);
            }
        });
        cust.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String text = cust.getSelectedItem().toString();
                String[] second_split = text.split("-");
                m_name = second_split[0];
                market_code = second_split[1];
                actv.setText(m_name);
            }
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        logback.setOnClickListener(new View.OnClickListener() {
            Bundle b = getIntent().getExtras();
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Intent i = new Intent(PcConferenceEdit.this, PCDashboard.class);
                            i.putExtra("UserName", userName);
                            i.putExtra("new_version", userName);
                            i.putExtra("UserName_1", UserName_2);
                            i.putExtra("UserName_2", UserName_2);
                            i.putExtra("userName", userName);
                            i.putExtra("new_version", userName);
                            i.putExtra("userName_1", UserName_2);
                            i.putExtra("userName_2", UserName_2);
                            i.putExtra("user_flag", user_flag);
                            startActivity(i);
                            //finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                backthred.start();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            Bundle b = getIntent().getExtras();
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Intent i = new Intent(PcConferenceEdit.this, PCDashboard.class);
                            i.putExtra("UserName", userName);
                            i.putExtra("new_version", userName);
                            i.putExtra("UserName_1", UserName_2);
                            i.putExtra("UserName_2", UserName_2);
                            i.putExtra("userName", userName);
                            i.putExtra("new_version", userName);
                            i.putExtra("userName_1", UserName_2);
                            i.putExtra("userName_2", UserName_2);
                            i.putExtra("user_flag", user_flag);
                            startActivity(i);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                backthred.start();
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
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
                error_dt.setText(" ");
                Bundle b = getIntent().getExtras();
                userName = b.getString("UserName");

                if ((ded.getText().toString().trim().equals("")) || (ded.getText().toString().trim().equals("Delivery Date")) || (ded.getText().toString().trim().equals("Please Select date"))) {
                    ded.setTextSize(14);
                    ded.setText("Please Select date");
                    error_dt.setText("Please Select date");
                } else if (totalday_given < totalday_valid1) {
                    ded.setError("Back date can not be proposed for  PC Conference");
                    error_dt.setText("Back date can not be proposed for  PC Conference ");
                    error_dt.setTextColor(Color.RED);
                } else if ((actv.getText().toString().trim().equals("")) || (actv.getText().toString().isEmpty())) {
                    actv.setError("Market not Assigned");
                    error_dt.setText("Market is not Properly selected");
                } else if ((venue_name.getText().toString().trim().equals("")) || (venue_name.getText().toString().isEmpty())) {
                    venue_name.setError("Please enter Venue name");
                    error_dt.setText("Venue name is not given");
                } else if (total_participent.getText().toString().trim().equals("") || total_participent.getText().toString().trim().equals(0)) {
                    pc_rmp.setError("Enter Participants");
                    error_dt.setText("Please enter participants");
                } else if (conf_type_val.equals("R") && ((Integer.parseInt(total_participent.getText().toString().trim()) - inhouse_val) > Integer.parseInt(person_limit))) {
                    pc_rmp.setError("Doctor Participant  limit is exceed");
                    error_dt.setText("Doctor Participant  is not more than " + person_limit);
                } else if (food.getText().toString().trim().equals("") || food.getText().toString().trim().equals(0)) {
                    food.setError("Enter Food cost");
                    error_dt.setText("Food cost is not given");
                } else if (conf_type_val.equals("R") && (Integer.parseInt(food.getText().toString().trim()) > Integer.parseInt(budget_limit))) {
                    food.setError("Food cost limit is exceed");
                    error_dt.setText("Food cost is not more than BDT. " + budget_limit + " Tk ");
                } else if ((impact.getText().toString().trim().equals("")) || (impact.getText().toString().isEmpty())) {
                    impact.setError("Please Describe Impact of the Conference");
                    error_dt.setText("Conference impact is not described");
                } else {
                    if (venue_charge.getText().toString().trim().equals("")) {
                        v_charge = "0";
                    } else {
                        v_charge = venue_charge.getText().toString();
                    }
                    if ((miscell_bdt.getText().toString().trim().equals("")) || (miscell_bdt.getText().toString().isEmpty())) {
                        miscell_bdt_val = "0";
                    } else {
                        miscell_bdt_val = miscell_bdt.getText().toString();
                    }
                    if ((miscell.getText().toString().trim().equals("")) || (miscell.getText().toString().isEmpty())) {
                        miscell_val = "0";
                    } else {
                        miscell_val = miscell.getText().toString();
                    }
                    Thread next = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            JSONParser jsonParser = new JSONParser();
                            List<NameValuePair> params = new ArrayList<NameValuePair>();
                            params.add(new BasicNameValuePair("MPO_CODE", userName));
                            params.add(new BasicNameValuePair("conference_date", ded.getText().toString()));
                            params.add(new BasicNameValuePair("market_code", market_code));
                            params.add(new BasicNameValuePair("market_name", actv.getText().toString().trim()));
                            params.add(new BasicNameValuePair("venue_name", venue_name.getText().toString().trim()));
                            params.add(new BasicNameValuePair("pc_rmp_val", String.valueOf(pc_rmp_val)));
                            params.add(new BasicNameValuePair("doc_val", String.valueOf(doc_val)));
                            params.add(new BasicNameValuePair("inhouse_val", String.valueOf(inhouse_val)));
                            params.add(new BasicNameValuePair("total_participant", total_participent.getText().toString().trim()));
                            params.add(new BasicNameValuePair("venue_charge", v_charge));
                            params.add(new BasicNameValuePair("food_per_person", food.getText().toString().trim()));
                            params.add(new BasicNameValuePair("food_total_bdt", fd_bdt.getText().toString().trim()));
                            params.add(new BasicNameValuePair("miscell", miscell_val));
                            params.add(new BasicNameValuePair("miscell_bdt", miscell_bdt_val));
                            params.add(new BasicNameValuePair("impact", impact.getText().toString().trim()));
                            params.add(new BasicNameValuePair("pc_conference_serial", pc_conference_serial));
                            params.add(new BasicNameValuePair("user_flag", user_flag));
                            Log.e("marketcode-->",market_code);

                            JSONObject json = jsonParser.makeHttpRequest(pc_conference_submit, "POST", params);
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
                            String pc_sl_no = message;
                            Intent sameint = new Intent(PcConferenceEdit.this, PcConferenceFollowup.class);
                            sameint.putExtra("UserName", userName);
                            sameint.putExtra("UserName_2", UserName_2);
                            sameint.putExtra("Ord_NO", message);
                            sameint.putExtra("user_flag", user_flag);
                            startActivity(sameint);
                        }
                    });
                    next.start();
                }
            }
        });
    }

    private void foodvalCalc() {
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (total_participent.getText().toString().trim().equals("") || total_participent.getText().toString().trim().equals(0)) {}
            }
        });
        food.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (total_participent.getText().toString().trim().equals("") || total_participent.getText().toString().trim().equals(0)) {}
                }
            }
        });
        food.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (total_participent.getText().toString().trim().equals("") || total_participent.getText().toString().trim().equals(0)) {}
                else if (food.getText().toString().trim().equals("") || food.getText().toString().trim().equals(" ")) {
                    food.setText("0");
                    food.setSelection(food.getText().length());
                    fd_bdt.setText("0");
                } else {
                    int a = Integer.parseInt(total_participent.getText().toString().trim());
                    int b = Integer.parseInt(food.getText().toString().trim());
                    food_allowance = String.valueOf(a * b);
                    fd_bdt.setText(food_allowance);
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void afterTextChanged(Editable s) {}
        });
    }

    private void participantCalc() {
        pc_rmp.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (pc_rmp.getText().toString().trim().equals("")) {
                    if (doc_chemist.getText().toString().trim().equals("")) {
                        doc_val = 0;
                    } else {
                        doc_val = Integer.parseInt(doc_chemist.getText().toString().trim());
                    }
                    if (in_house.getText().toString().trim().equals("")) {
                        inhouse_val = 0;
                    } else {
                        inhouse_val = Integer.parseInt(in_house.getText().toString().trim());
                    }
                    if (food.getText().toString().trim().equals("")) {
                        food_val = 0;
                    } else {
                        food_val = Integer.parseInt(food.getText().toString().trim());
                    }
                    pc_rmp_val = 0;
                    result_participant = pc_rmp_val + doc_val + inhouse_val;
                    String total2 = Double.toString(result_participant);
                    total_participent.setText(String.valueOf(result_participant));

                    int food_val_temp = result_participant * food_val;
                    food_allowance = String.valueOf(food_val_temp);
                    fd_bdt.setText(String.valueOf(food_val_temp));
                } else {
                    if (doc_chemist.getText().toString().trim().equals("")) {
                        doc_val = 0;
                    } else {
                        doc_val = Integer.parseInt(doc_chemist.getText().toString().trim());
                    }
                    if (in_house.getText().toString().trim().equals("")) {
                        inhouse_val = 0;
                    } else {
                        inhouse_val = Integer.parseInt(in_house.getText().toString().trim());
                    }
                    pc_rmp_val = Integer.parseInt(pc_rmp.getText().toString().trim());
                    double result = pc_rmp_val + doc_val + inhouse_val;
                    String total2 = Double.toString(result);
                    total_participent.setText(total2);

                    if (food.getText().toString().trim().equals("")) {
                        food_val = 0;
                    } else {
                        food_val = Integer.parseInt(food.getText().toString().trim());
                    }
                    int food_val_temp = result_participant * food_val;
                    fd_bdt.setText(String.valueOf(food_val_temp));
                    food_allowance = String.valueOf(food_val_temp);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void afterTextChanged(Editable s) {
                if (pc_rmp.getText().toString().trim().equals("")) {
                    if (doc_chemist.getText().toString().trim().equals("")) {
                        doc_val = 0;
                    } else {
                        doc_val = Integer.parseInt(doc_chemist.getText().toString().trim());
                    }
                    if (in_house.getText().toString().trim().equals("")) {
                        inhouse_val = 0;
                    } else {
                        inhouse_val = Integer.parseInt(in_house.getText().toString().trim());
                    }
                    pc_rmp_val = 0;
                    result_participant = pc_rmp_val + doc_val + inhouse_val;
                    String total2 = Double.toString(result_participant);
                    total_participent.setText(String.valueOf(result_participant));

                    if (food.getText().toString().trim().equals("")) {
                        food_val = 0;
                    } else {
                        food_val = Integer.parseInt(food.getText().toString().trim());
                    }
                    int food_val_temp = result_participant * food_val;
                    fd_bdt.setText(String.valueOf(food_val_temp));
                    food_allowance = String.valueOf(food_val_temp);
                } else {
                    if (doc_chemist.getText().toString().trim().equals("")) {
                        doc_val = 0;
                    } else {
                        doc_val = Integer.parseInt(doc_chemist.getText().toString().trim());
                    }
                    if (in_house.getText().toString().trim().equals("")) {
                        inhouse_val = 0;
                    } else {
                        inhouse_val = Integer.parseInt(in_house.getText().toString().trim());
                    }
                    pc_rmp_val = Integer.parseInt(pc_rmp.getText().toString().trim());
                    int result = pc_rmp_val + doc_val + inhouse_val;
                    String total2 = Integer.toString(result);
                    total_participent.setText(total2);

                    if (food.getText().toString().trim().equals("")) {
                        food_val = 0;

                    } else {

                        food_val = Integer.parseInt(food.getText().toString().trim());

                    }
                    int food_val_temp = result * food_val;
                    fd_bdt.setText(String.valueOf(food_val_temp));

                    food_allowance = String.valueOf(food_val_temp);


                }


            }
        });
        doc_chemist.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {


                if (doc_chemist.getText().toString().trim().equals("")) {

                    if (pc_rmp.getText().toString().trim().equals("")) {

                        pc_rmp_val = 0;
                    } else {
                        pc_rmp_val = Integer.parseInt(pc_rmp.getText().toString().trim());

                    }

                    if (in_house.getText().toString().trim().equals("")) {
                        inhouse_val = 0;

                    } else {

                        inhouse_val = Integer.parseInt(in_house.getText().toString().trim());

                    }


                    doc_val = 0;
                    int result = pc_rmp_val + doc_val + inhouse_val;
                    String total2 = Integer.toString(result);
                    total_participent.setText(total2);


                    if (food.getText().toString().trim().equals("")) {
                        food_val = 0;

                    } else {

                        food_val = Integer.parseInt(food.getText().toString().trim());

                    }


                    int food_val_temp = result * food_val;
                    food_allowance = String.valueOf(food_val_temp);
                    fd_bdt.setText(String.valueOf(food_val_temp));


                } else {

                    if (doc_chemist.getText().toString().trim().equals("")) {

                        doc_val = 0;
                    } else {
                        doc_val = Integer.parseInt(doc_chemist.getText().toString().trim());

                    }


                    if (in_house.getText().toString().trim().equals("")) {
                        inhouse_val = 0;

                    } else {

                        inhouse_val = Integer.parseInt(in_house.getText().toString().trim());

                    }


                    if (pc_rmp.getText().toString().trim().equals("")) {
                        pc_rmp_val = 0;

                    } else {

                        pc_rmp_val = Integer.parseInt(pc_rmp.getText().toString().trim());

                    }


                    int result = pc_rmp_val + doc_val + inhouse_val;
                    String total2 = Integer.toString(result);
                    total_participent.setText(total2);


                    if (food.getText().toString().trim().equals("")) {
                        food_val = 0;

                    } else {

                        food_val = Integer.parseInt(food.getText().toString().trim());

                    }


                    int food_val_temp = result * food_val;
                    food_allowance = String.valueOf(food_val_temp);
                    fd_bdt.setText(String.valueOf(food_val_temp));


                }


            }


            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {


                if (doc_chemist.getText().toString().trim().equals("")) {


                    if (pc_rmp.getText().toString().trim().equals("")) {

                        pc_rmp_val = 0;
                    } else {
                        pc_rmp_val = Integer.parseInt(pc_rmp.getText().toString().trim());

                    }

                    if (in_house.getText().toString().trim().equals("")) {
                        inhouse_val = 0;

                    } else {

                        inhouse_val = Integer.parseInt(in_house.getText().toString().trim());

                    }


                    doc_val = 0;
                    int result = pc_rmp_val + doc_val + inhouse_val;
                    String total2 = Integer.toString(result);
                    total_participent.setText(total2);

                    if (food.getText().toString().trim().equals("")) {
                        food_val = 0;

                    } else {

                        food_val = Integer.parseInt(food.getText().toString().trim());

                    }

                    int food_val_temp = result * food_val;
                    food_allowance = String.valueOf(food_val_temp);
                    fd_bdt.setText(String.valueOf(food_val_temp));


                } else {

                    if (doc_chemist.getText().toString().trim().equals("")) {

                        doc_val = 0;
                    } else {
                        doc_val = Integer.parseInt(doc_chemist.getText().toString().trim());

                    }


                    if (in_house.getText().toString().trim().equals("")) {
                        inhouse_val = 0;

                    } else {

                        inhouse_val = Integer.parseInt(in_house.getText().toString().trim());

                    }

                    if (pc_rmp.getText().toString().trim().equals("")) {
                        pc_rmp_val = 0;

                    } else {

                        pc_rmp_val = Integer.parseInt(pc_rmp.getText().toString().trim());

                    }

                    int result = pc_rmp_val + doc_val + inhouse_val;
                    String total2 = Integer.toString(result);
                    total_participent.setText(total2);

                    if (food.getText().toString().trim().equals("")) {
                        food_val = 0;

                    } else {

                        food_val = Integer.parseInt(food.getText().toString().trim());

                    }


                    int food_val_temp = result * food_val;
                    food_allowance = String.valueOf(food_val_temp);
                    fd_bdt.setText(String.valueOf(food_val_temp));


                }


            }
        });
        in_house.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {


                if (in_house.getText().toString().trim().equals("")) {


                    if (pc_rmp.getText().toString().trim().equals("")) {

                        pc_rmp_val = 0;
                    } else {
                        pc_rmp_val = Integer.parseInt(pc_rmp.getText().toString().trim());

                    }

                    if (doc_chemist.getText().toString().trim().equals("")) {
                        doc_val = 0;

                    } else {

                        doc_val = Integer.parseInt(doc_chemist.getText().toString().trim());

                    }


                    if (food.getText().toString().trim().equals("")) {
                        food_val = 0;

                    } else {

                        food_val = Integer.parseInt(food.getText().toString().trim());

                    }


                    inhouse_val = 0;
                    int result = pc_rmp_val + doc_val + inhouse_val;
                    String total2 = Integer.toString(result);
                    total_participent.setText(total2);


                    int food_val_temp = result * food_val;
                    food_allowance = String.valueOf(food_val_temp);
                    fd_bdt.setText(String.valueOf(food_val_temp));


                } else {

                    if (doc_chemist.getText().toString().trim().equals("")) {

                        doc_val = 0;
                    } else {
                        doc_val = Integer.parseInt(doc_chemist.getText().toString().trim());

                    }

                    if (pc_rmp.getText().toString().trim().equals("")) {

                        pc_rmp_val = 0;
                    } else {
                        pc_rmp_val = Integer.parseInt(pc_rmp.getText().toString().trim());

                    }

                    if (food.getText().toString().trim().equals("")) {
                        food_val = 0;

                    } else {

                        food_val = Integer.parseInt(food.getText().toString().trim());

                    }

                    inhouse_val = Integer.parseInt(in_house.getText().toString().trim());
                    int result = pc_rmp_val + doc_val + inhouse_val;
                    String total2 = Integer.toString(result);
                    total_participent.setText(total2);


                    int food_val_temp = result * food_val;
                    food_allowance = String.valueOf(food_val_temp);
                    fd_bdt.setText(String.valueOf(food_val_temp));


                }


            }


            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {


                if (in_house.getText().toString().trim().equals("")) {


                    if (pc_rmp.getText().toString().trim().equals("")) {
                        pc_rmp_val = 0;
                    } else {
                        pc_rmp_val = Integer.parseInt(pc_rmp.getText().toString().trim());

                    }

                    if (doc_chemist.getText().toString().trim().equals("")) {
                        doc_val = 0;

                    } else {

                        doc_val = Integer.parseInt(doc_chemist.getText().toString().trim());

                    }


                    inhouse_val = 0;
                    int result = pc_rmp_val + doc_val + inhouse_val;
                    String total2 = Integer.toString(result);
                    total_participent.setText(total2);

                    if (food.getText().toString().trim().equals("")) {
                        food_val = 0;

                    } else {

                        food_val = Integer.parseInt(food.getText().toString().trim());

                    }
                    int food_val_temp = result * food_val;
                    food_allowance = String.valueOf(food_val_temp);
                    fd_bdt.setText(String.valueOf(food_val_temp));

                } else {

                    if (doc_chemist.getText().toString().trim().equals("")) {

                        doc_val = 0;
                    } else {
                        doc_val = Integer.parseInt(doc_chemist.getText().toString().trim());

                    }

                    if (pc_rmp.getText().toString().trim().equals("")) {

                        pc_rmp_val = 0;
                    } else {
                        pc_rmp_val = Integer.parseInt(pc_rmp.getText().toString().trim());

                    }


                    inhouse_val = Integer.parseInt(in_house.getText().toString().trim());
                    int result = pc_rmp_val + doc_val + inhouse_val;
                    String total2 = Integer.toString(result);
                    total_participent.setText(total2);


                    if (food.getText().toString().trim().equals("")) {
                        food_val = 0;

                    } else {

                        food_val = Integer.parseInt(food.getText().toString().trim());

                    }
                    int food_val_temp = result * food_val;
                    food_allowance = String.valueOf(food_val_temp);
                    fd_bdt.setText(String.valueOf(food_val_temp));


                }


            }
        });
    }

    private void initViews() {

        inputLayoutName = findViewById(R.id.input_layout_name);
        inputLayoutEmail = findViewById(R.id.input_layout_email);
        error_dt = findViewById(R.id.errordt);
        user_show = findViewById(R.id.user_show);
        btnSignUp = findViewById(R.id.submit);
        back = findViewById(R.id.back);
        logback = findViewById(R.id.logback);

        ded = findViewById(R.id.conference_date);
        venue_charge = findViewById(R.id.venue_charge);
        food = findViewById(R.id.food);
        fd_bdt = findViewById(R.id.fd_bdt);
        miscell = findViewById(R.id.miscell);
        miscell.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        miscell_bdt = findViewById(R.id.miscell_bdt);
        impact = findViewById(R.id.impact);
        impact.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_FLAG_MULTI_LINE |
                InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        pc_rmp = findViewById(R.id.pc_rmp);
        doc_chemist = findViewById(R.id.doc_chemist);
        in_house = findViewById(R.id.in_house);
        venue_name = findViewById(R.id.venue_name);
        venue_name.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        market_name = findViewById(R.id.market_name);
        market_name.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        spinner = findViewById(R.id.input1);
        spinner.setVisibility(View.GONE);
        total_participent = findViewById(R.id.total_participent);
        cust = findViewById(R.id.customer);
        customerlist = new ArrayList<Customer>();
        brandlist = new ArrayList<Customer>();
        dateextendlist = new ArrayList<Customer>();
        dateextendlist2 = new ArrayList<Customer>();
        dateextendlist3 = new ArrayList<Customer>();
        dateextedsetupdata = new ArrayList<Customer>();
        checkdatelist = new ArrayList<Customer>();
        checkflag = new ArrayList<Customer>();
        fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        cust.setOnItemSelectedListener(this);
        mainlayout = findViewById(R.id.successmsg);
        succ_msg = findViewById(R.id.succ_msg);
        ordno = findViewById(R.id.ordno);
        ded.setFocusableInTouchMode(true);
        ded.setFocusable(true);
        ded.requestFocus();
        v_charge = "0";
        miscell_bdt_val = "0";
        miscell_val = "0";
        actv = findViewById(R.id.autoCompleteTextView1);
        Bundle[] b = {getIntent().getExtras()};
        userName = b[0].getString("UserName");
        UserName_2 = b[0].getString("UserName_2");
        user_flag = b[0].getString("user_flag");
        pc_conference_serial = b[0].getString("pc_conference_serial");
        new_version = b[0].getString("new_version");
        ordernumber = b[0].getString("Ord_NO");
        user_show.setText(userName + " " + UserName_2 + " ");
        back.setTypeface(fontFamily);
        back.setText("\uf060 ");
        logback.setTypeface(fontFamily);
        logback.setText("\uf08b");

        if (ordernumber == null) {
            mainlayout.setVisibility(LinearLayout.GONE);
        } else {

            succ_msg.setTextSize(12);
            succ_msg.setText("Submitted.");
            succ_msg.setTextColor(Color.BLACK);
            ordno.setText("PC Serial no." + ordernumber);
            ordno.setTextSize(12);
            ordno.setTextColor(Color.BLACK);
        }
    }


    private void pc_conference_edit_data() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < dateextendlist.size(); i++) {
            get_ext_dt = dateextendlist.get(i).getName();
        }

        String split1[] = get_ext_dt.split("%");
        String get_val1 = split1[0].trim();
        String get_misc = split1[1].trim();


        String split2[] = get_val1.split("@");
        String get_val2 = split2[0].trim();
        String get_vcharge = split2[1].trim();


        String split3[] = get_val2.split("#");
        String get_val3 = split3[0].trim();
        String get_fbudget = split3[1].trim();


        String split4[] = get_val3.split("/");
        String get_val4 = split4[0].trim();
        String get_inhouse = split4[1].trim();


        String split5[] = get_val4.split("-");
        String get_pc = split5[0].trim();
        String get_dcc = split5[1].trim();

        venue_charge.setText(get_vcharge);
        miscell_bdt.setText(get_misc);
        food.setText(get_fbudget);


        pc_rmp.setText(get_pc);
        doc_chemist.setText(get_dcc);
        in_house.setText(get_inhouse);


        v_charge = get_vcharge;


        pc_rmp_val = Integer.parseInt(get_pc);
        doc_val = Integer.parseInt(get_dcc);
        inhouse_val = Integer.parseInt(get_inhouse);

        int total_par = Integer.parseInt(get_pc) + Integer.parseInt(get_dcc) + Integer.parseInt(get_inhouse);

    }
    class GeTPcConferenceEditData extends AsyncTask<Void, Void, Void> {

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
            params.add(new BasicNameValuePair("id", pc_conference_serial));
            ServiceHandler jsonParser = new ServiceHandler();


            String json = jsonParser.makeServiceCall(pc_conference_get_data_edit, ServiceHandler.POST, params);


            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray customer = jsonObj.getJSONArray("customer");
                        for (int i = 0; i < 1; i++) {
                            JSONObject catObj = (JSONObject) customer.get(i);
                            Customer custo = new Customer(catObj.getInt("id"), catObj.getString("name"));
                            dateextendlist.add(custo);

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
            //    pDialog2.dismiss();
            pc_conference_edit_data();
        }

    }


    private void pc_conference_edit_data2() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < dateextendlist2.size(); i++) {
            get_ext_dt2 = dateextendlist2.get(i).getName();
        }

        String split12[] = get_ext_dt2.split("@");
        String get_val12 = split12[0].trim();
        String get_misc_text = split12[1].trim();


        String split22[] = get_val12.split("/");
        String get_val22 = split22[0].trim();
        String get_impact_text = split22[1].trim();


        String split32[] = get_val22.split("#");
        String get_conference_date = split32[0].trim();
        String get_venue_name = split32[1].trim();


        impact.setText(get_impact_text);
        venue_name.setText(get_venue_name);
        miscell.setText(get_misc_text);


        get_conference_date = get_conference_date.replace("-", "/");
        ded.setText(get_conference_date);


    }
    class GeTPcConferenceEditData2 extends AsyncTask<Void, Void, Void> {

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
            params.add(new BasicNameValuePair("id", pc_conference_serial));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(pc_conference_get_data_edit2, ServiceHandler.POST, params);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray customer = jsonObj.getJSONArray("customer");
                        for (int i = 0; i < 1; i++) {
                            JSONObject catObj = (JSONObject) customer.get(i);
                            Customer custo = new Customer(catObj.getInt("id"), catObj.getString("name"));
                            dateextendlist2.add(custo);

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

            pc_conference_edit_data2();
        }

    }


    private void pc_conference_edit_data3() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < dateextendlist3.size(); i++) {
            get_ext_dt3 = dateextendlist3.get(i).getName();
        }

        conf_type_val = get_ext_dt3;

    }
    class GeTPcConferenceEditData3 extends AsyncTask<Void, Void, Void> {

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
            params.add(new BasicNameValuePair("id", pc_conference_serial));
            ServiceHandler jsonParser = new ServiceHandler();


            String json = jsonParser.makeServiceCall(pc_conference_get_data_edit3, ServiceHandler.POST, params);


            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray customer = jsonObj.getJSONArray("customer");
                        for (int i = 0; i < 1; i++) {
                            JSONObject catObj = (JSONObject) customer.get(i);
                            Customer custo = new Customer(catObj.getInt("id"), catObj.getString("name"));
                            dateextendlist3.add(custo);

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

            pc_conference_edit_data3();
        }

    }


    private void pc_conference_flag() {
        check_flag = "0";
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < checkflag.size(); i++) {
            check_flag = checkflag.get(i).getName();
        }


    }
    class GeTPcConferenceFlag extends AsyncTask<Void, Void, Void> {

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
            params.add(new BasicNameValuePair("proposed_conference_date", proposed_conference_date));
            ServiceHandler jsonParser = new ServiceHandler();
            // String json=jsonParser.makeServiceCall(pc_conference_date_check, ServiceHandler.POST, params);
            String json = jsonParser.makeServiceCall(pc_conference_check_flag, ServiceHandler.POST, params);


            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray customer = jsonObj.getJSONArray("customer");
                        for (int i = 0; i < 1; i++) {
                            JSONObject catObj = (JSONObject) customer.get(i);
                            Customer custo = new Customer(catObj.getInt("id"), catObj.getString("name"));
                            // checkdatelist.add(custo);
                            checkflag.add(custo);


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

            pc_conference_flag();
        }

    }


    private void pc_conference_date_check() {
        date_flag = "0";
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < checkdatelist.size(); i++) {
            date_flag = checkdatelist.get(i).getName();
        }


    }
    class GeTPcConferenceDate extends AsyncTask<Void, Void, Void> {

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
            params.add(new BasicNameValuePair("proposed_conference_date", proposed_conference_date));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(pc_conference_date_check, ServiceHandler.POST, params);


            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray customer = jsonObj.getJSONArray("customer");
                        for (int i = 0; i < 1; i++) {
                            JSONObject catObj = (JSONObject) customer.get(i);
                            Customer custo = new Customer(catObj.getInt("id"), catObj.getString("name"));
                            checkdatelist.add(custo);

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
            //    pDialog2.dismiss();
            pc_conference_date_check();
        }

    }



    private void pc_conference_setup_data() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < dateextedsetupdata.size(); i++) {
            get_pc_conference_setup_data = dateextedsetupdata.get(i).getName();
        }


        String split[] = get_pc_conference_setup_data.split("/");
        String get_val = split[0].trim();
        total_conference = split[1].trim();
        String split2[] = get_val.split("-");

        budget_limit = split2[0].trim();
        person_limit = split2[1].trim();


    }
    class GeTPcConferenceSetupData extends AsyncTask<Void, Void, Void> {

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
            params.add(new BasicNameValuePair("id", proposed_conference_date));
            ServiceHandler jsonParser = new ServiceHandler();


            String json = jsonParser.makeServiceCall(pc_conference_range_permission, ServiceHandler.POST, params);


            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray customer = jsonObj.getJSONArray("customer");
                        for (int i = 0; i < 1; i++) {
                            JSONObject catObj = (JSONObject) customer.get(i);
                            Customer custo = new Customer(catObj.getInt("id"), catObj.getString("name"));
                            dateextedsetupdata.add(custo);

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
            //    pDialog2.dismiss();
            pc_conference_setup_data();
        }

    }


    private void populateSpinner2() {
        List<String> list2 = new ArrayList<String>();
        for (int i = 0; i < brandlist.size(); i++) {
            list2.add(brandlist.get(i).getName());
        }

        spinner.setItems(list2);


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


        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        actv.setAdapter(Adapter);
        actv.setTextColor(Color.BLUE);


    }

    class GetMarket extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PcConferenceEdit.this);
            pDialog.setMessage("Loading Market name..");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0){

            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            String id = userName;
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", id));
            params.add(new BasicNameValuePair("pc_conference_serial", pc_conference_serial));


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

    class GetPCBrand extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PcConferenceEdit.this);
            pDialog.setMessage("Loading PC Brand .. .. ..");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {


            // ServiceHandler jsonParser = new ServiceHandler();
            // String json = jsonParser.makeServiceCall(URL_CUSOTMER,ServiceHandler.GET);

            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            String id = userName;

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", proposed_conference_date));


            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_PC_PRODUCT, ServiceHandler.POST, params);


            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray customer = jsonObj.getJSONArray("customer");
                        for (int i = 0; i < customer.length(); i++) {
                            JSONObject catObj = (JSONObject) customer.get(i);


                            Customer custo = new Customer(catObj.getInt("id"), catObj.getString("name"));
                            brandlist.add(custo);
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
            populateSpinner2();
        }

    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}