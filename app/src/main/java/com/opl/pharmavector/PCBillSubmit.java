package com.opl.pharmavector;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

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
import com.opl.pharmavector.pcconference.PcProposalDoc;
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

public class PCBillSubmit extends Activity implements AdapterView.OnItemSelectedListener {

    private Toolbar toolbar;
    private EditText mpo_name, territory_name, mpo_code, venue_charge, food, miscell, miscell_bdt, impact,
            fd_bdt, venue_charge_iou, miscell_bdt_iou, food_iou, venue_voucher_head, food_voucher_head, miscell_voucher_head, iou_no;

    private EditText proposed_pc_rmp, no_of_pc_rmp, proposed_dcc, no_of_dcc, proposed_inhouse, no_of_inhouse;
    public static final String TAG_SUCCESS_JSON = "get_edit_data";
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPassword;
    private TextView user_show;
    private Button btnSignUp;
    private EditText pc_rmp, doc_chemist, in_house;
    private ArrayList<Customer> customerlist;
    private ArrayList<Customer> brandlist;
    private ArrayList<Customer> dateextendlist;
    private ArrayList<Customer> dateextendlist2;
    private ArrayList<Customer> dateextedsetupdata;
    private ArrayList<Customer> checkdatelist;
    private ArrayList<Customer> checkflag;
    private EditText market_name, venue_name, total_participent, arranged_venue_name, total_participent_new;
    EditText ded, venue_voucher_date, miscell_voucher_date, food_voucher_date, arranged_conference_date;
    public int pc_rmp_val = 0;
    public int result_participant = 0;
    public int doc_val = 0;
    public int inhouse_val = 0;
    public int food_val = 0;
    public MultiSelectionSpinner2 spinner;
    private Spinner cust;
    //private MaterialSpinner cust;
    public String proposed_conference_date;
    ProgressDialog pDialog;
    public String market_code, m_name, food_allowance, v_charge, miscell_bdt_val, miscell_val;
    public String conf_type_val;

    public String userName, UserName_2, pc_conference_serial, user_flag, pc_conference_iou;
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_MESSAGE_1 = "message_1";
    public static final String TAG_invoice = "invoice";
    public static final String TAG_target = "target";
    public static final String TAG_achivement = "achivement";
    public static final String TAG_MESSAGE_new_version = "new_version";
    public int success;
    public String message, ord_no, invoice, target_data, achivement, searchString, select_party;
    public String get_ext_dt, date_flag, check_flag, get_ext_dt2, get_pc_conference_setup_data;

    public String budget_limit, person_limit, total_conference;
    public String food_budget_limit, venue_budget_limit, miscell_budget_limit, participant_limit;

    public int get_success, tag_get_success;
    public String get_pc, get_dcc, get_inhouse, get_vcharge, get_misc, get_fbudget,get_impact_text,get_venue_name,get_misc_text,get_conference_date;
    //public String tag_get_pc,tag_get_dcc,tag_get_inhouse,tag_get_vcharge,tag_get_misc,tag_get_fbudget;

    public static final String tag_get_pc = "pc";
    public static final String tag_get_dcc = "dcc";
    public static final String tag_get_inhouse = "inhouse";
    public static final String tag_get_vcharge = "vcharge";
    public static final String tag_get_misc = "misc";
    public static final String tag_get_fbudget = "fbudget";
    public static final String tag_conf_outcome = "conf_outcome";
    public static final String tag_conf_venue_name = "conf_venue_name";
    public static final String tag_miscellaneous = "miscellaneous";
    public static final String tag_conf_proposed_date = "conf_proposed_date";
    private String pc_conference_bill_submit = BASE_URL+"pc_conference_bill_submit.php";
    private String URL_CUSOTMER = BASE_URL+"pc_mkt_restructure_vw.php";
    private String pc_conference_range_permission = BASE_URL+"pc_conference_range_permission.php";
    private String pc_conference_get_data_edit = BASE_URL+"pcconference/pc_conference_get_data_edit.php";




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pc_conference_bill_submit);
        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        final TextView error_dt = (TextView) findViewById(R.id.errordt);
        user_show = (TextView) findViewById(R.id.user_show);
        btnSignUp = (Button) findViewById(R.id.submit);
        final Button back = (Button) findViewById(R.id.back);
        final Button logback = (Button) findViewById(R.id.logback);

        ded = (EditText) findViewById(R.id.conference_date);

        food_budget_limit = "150";
        venue_budget_limit = "500";
        miscell_budget_limit = "300";
        participant_limit = "35";


        ded.setFocusable(false);
        ded.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
        ded.setClickable(false);


        arranged_conference_date = (EditText) findViewById(R.id.arranged_conference_date);

        venue_voucher_date = (EditText) findViewById(R.id.venue_voucher_date);
        miscell_voucher_date = (EditText) findViewById(R.id.miscell_voucher_date);
        food_voucher_date = (EditText) findViewById(R.id.food_voucher_date);


        venue_voucher_head = (EditText) findViewById(R.id.venue_voucher_head);

        venue_voucher_head.setFocusableInTouchMode(true);
        venue_voucher_head.setFocusable(true);
        venue_voucher_head.requestFocus();


        miscell_voucher_head = (EditText) findViewById(R.id.miscell_voucher_head);

        miscell_voucher_head.setFocusable(true);
        miscell_voucher_head.setFocusableInTouchMode(true); // user touches widget on phone with touch screen
        miscell_voucher_head.setClickable(true);

        iou_no = (EditText) findViewById(R.id.iou_no);

        iou_no.setFocusable(false);
        iou_no.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
        iou_no.setClickable(false);

        food_voucher_head = (EditText) findViewById(R.id.food_voucher_head);


        food_voucher_head.setFocusable(true);
        food_voucher_head.setFocusableInTouchMode(true); // user touches widget on phone with touch screen
        food_voucher_head.setClickable(true);


        venue_charge = (EditText) findViewById(R.id.venue_charge);
        venue_charge.setFocusable(false);
        venue_charge.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
        venue_charge.setClickable(false);


        venue_charge_iou = (EditText) findViewById(R.id.venue_charge_iou);
        venue_charge_iou.setSelection(venue_charge_iou.getText().length());


        food = (EditText) findViewById(R.id.food);

        food.setFocusable(false);
        food.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
        food.setClickable(false);

        food_voucher_head = (EditText) findViewById(R.id.food_voucher_head);

        food_iou = (EditText) findViewById(R.id.food_iou);
        food_iou.setSelection(food_iou.getText().length());


        fd_bdt = (EditText) findViewById(R.id.fd_bdt);


        miscell = (EditText) findViewById(R.id.miscell);
        miscell.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        miscell_bdt = (EditText) findViewById(R.id.miscell_bdt);
        miscell_bdt.setFocusable(false);
        miscell_bdt.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
        miscell_bdt.setClickable(false);


        miscell_bdt_iou = (EditText) findViewById(R.id.miscell_bdt_iou);
        miscell_bdt_iou.setSelection(miscell_bdt_iou.getText().length());


        impact = (EditText) findViewById(R.id.impact);
        impact.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_FLAG_MULTI_LINE |
                InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        pc_rmp = (EditText) findViewById(R.id.pc_rmp);
        doc_chemist = (EditText) findViewById(R.id.doc_chemist);
        in_house = (EditText) findViewById(R.id.in_house);


        venue_name = (EditText) findViewById(R.id.venue_name);
        venue_name.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        venue_name.setFocusable(false);
        venue_name.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
        venue_name.setClickable(false);


        arranged_venue_name = (EditText) findViewById(R.id.arranged_venue_name);
        arranged_venue_name.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        arranged_venue_name.setFocusable(true);
        arranged_venue_name.setFocusableInTouchMode(true); // user touches widget on phone with touch screen
        arranged_venue_name.setClickable(true);


        no_of_inhouse = (EditText) findViewById(R.id.no_of_inhouse);
        no_of_pc_rmp = (EditText) findViewById(R.id.no_of_pc_rmp);
        no_of_dcc = (EditText) findViewById(R.id.no_of_dcc);


        no_of_pc_rmp.setFocusable(true);
        no_of_pc_rmp.setFocusableInTouchMode(true); // user touches widget on phone with touch screen
        no_of_pc_rmp.setClickable(true);


        no_of_dcc.setFocusable(true);
        no_of_dcc.setFocusableInTouchMode(true); // user touches widget on phone with touch screen
        no_of_dcc.setClickable(true);


        proposed_pc_rmp = (EditText) findViewById(R.id.proposed_pc_rmp);
        proposed_pc_rmp.setFocusable(false);
        proposed_pc_rmp.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
        proposed_pc_rmp.setClickable(false);


        proposed_dcc = (EditText) findViewById(R.id.proposed_dcc);
        proposed_dcc.setFocusable(false);
        proposed_dcc.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
        proposed_dcc.setClickable(false);

        proposed_inhouse = (EditText) findViewById(R.id.proposed_inhouse);
        proposed_inhouse.setFocusable(false);
        proposed_inhouse.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
        proposed_inhouse.setClickable(false);


        market_name = (EditText) findViewById(R.id.market_name);
        market_name.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        spinner = (MultiSelectionSpinner2) findViewById(R.id.input1);
        spinner.setVisibility(View.GONE);
        total_participent = (EditText) findViewById(R.id.total_participent);
        total_participent.setFocusable(false);
        total_participent.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
        total_participent.setClickable(false);


        cust = (Spinner) findViewById(R.id.customer);

        cust.setFocusable(false);
        cust.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
        cust.setClickable(false);

        customerlist = new ArrayList<Customer>();
        brandlist = new ArrayList<Customer>();
        dateextendlist = new ArrayList<Customer>();
        dateextendlist2 = new ArrayList<Customer>();
        dateextedsetupdata = new ArrayList<Customer>();
        checkdatelist = new ArrayList<Customer>();
        checkflag = new ArrayList<Customer>();
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        cust.setOnItemSelectedListener(this);
        LinearLayout mainlayout = (LinearLayout) findViewById(R.id.successmsg);
        TextView succ_msg = (TextView) findViewById(R.id.succ_msg);
        TextView ordno = (TextView) findViewById(R.id.ordno);


        v_charge = "0";
        miscell_bdt_val = "0";
        miscell_val = "0";


        final AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        Bundle b = getIntent().getExtras();

        userName = b.getString("UserName");
        UserName_2 = b.getString("UserName_2");
        user_flag = b.getString("user_flag");


        // final String participent = b[0].getString("participent");
        pc_conference_serial = b.getString("pc_conference_serial");
        pc_conference_iou = b.getString("pc_conference_iou");
        iou_no.setText(pc_conference_iou);
        conf_type_val = b.getString("conf_type_val");
        final String new_version = b.getString("new_version");
        final String ordernumber = b.getString("Ord_NO");


        Log.e("pc_conference_serial", pc_conference_serial + "---" + pc_conference_iou + "--" + conf_type_val);

        user_show.setText(userName + " " + UserName_2 + " ");
        back.setTypeface(fontFamily);
        back.setText("\uf060 ");
        logback.setTypeface(fontFamily);
        logback.setText("\uf08b");


        new GeTPcConferenceEditData().execute();
      //  new GeTPcConferenceEditData2().execute();

        new GetMarket().execute();
        new GeTPcConferenceSetupData().execute();
        


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


        logback.setOnClickListener(new View.OnClickListener() {
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
                        // TODO Auto-generated method stub

                        try {
                            Intent i = new Intent(PCBillSubmit.this, PCDashboard.class);
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

            //UserName = b.getString("UserName");
            //UserName_1= b.getString("UserName_1");
            //UserName_2= b.getString("UserName_2");
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub

                        try {
                            Intent i = new Intent(PCBillSubmit.this, PCDashboard.class);
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
                venue_voucher_date.setText(sdf.format(myCalendar.getTime()));
            }

        };

        venue_voucher_date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                hideKeyboard(v);

                new DatePickerDialog(PCBillSubmit.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();


            }
        });


        final Calendar myCalendar4 = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date4 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar4.set(Calendar.YEAR, year);
                myCalendar4.set(Calendar.MONTH, monthOfYear);
                myCalendar4.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel4();
            }

            private void updateLabel4() {
                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                arranged_conference_date.setText(sdf.format(myCalendar4.getTime()));
            }

        };


        arranged_conference_date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                hideKeyboard(v);

                new DatePickerDialog(PCBillSubmit.this, date4, myCalendar4
                        .get(Calendar.YEAR), myCalendar4.get(Calendar.MONTH),
                        myCalendar4.get(Calendar.DAY_OF_MONTH)).show();


            }
        });


        final Calendar myCalendar2 = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar2.set(Calendar.YEAR, year);
                myCalendar2.set(Calendar.MONTH, monthOfYear);
                myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel2();
            }

            private void updateLabel2() {
                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                miscell_voucher_date.setText(sdf.format(myCalendar2.getTime()));
            }

        };

        miscell_voucher_date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                hideKeyboard(v);

                new DatePickerDialog(PCBillSubmit.this, date2, myCalendar2
                        .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                        myCalendar2.get(Calendar.DAY_OF_MONTH)).show();


            }
        });


        final Calendar myCalendar3 = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date3 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar3.set(Calendar.YEAR, year);
                myCalendar3.set(Calendar.MONTH, monthOfYear);
                myCalendar3.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabe3();
            }

            private void updateLabe3() {
                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                food_voucher_date.setText(sdf.format(myCalendar3.getTime()));
            }

        };

        food_voucher_date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                hideKeyboard(v);

                new DatePickerDialog(PCBillSubmit.this, date3, myCalendar3
                        .get(Calendar.YEAR), myCalendar3.get(Calendar.MONTH),
                        myCalendar3.get(Calendar.DAY_OF_MONTH)).show();


            }
        });

        cust.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {


                String text = cust.getSelectedItem().toString();

                String[] second_split = text.split("-");
                m_name = second_split[0];
                market_code = second_split[1];
                actv.setText(m_name);

            }

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });








        /*====================================== PC/RMP  participant change  ========================================*/

        pc_rmp.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {


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


                    int food_val_temp = result_participant * food_val;
                    food_allowance = String.valueOf(food_val_temp);
                    Log.e("temp_food_val", String.valueOf(food_val_temp));


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

                    if (food.getText().toString().trim().equals("")) {
                        food_val = 0;

                    } else {

                        food_val = Integer.parseInt(food.getText().toString().trim());

                    }
                    int food_val_temp = result_participant * food_val;
                    food_allowance = String.valueOf(food_val_temp);


                }


            }


            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

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


                    if (food.getText().toString().trim().equals("")) {
                        food_val = 0;

                    } else {

                        food_val = Integer.parseInt(food.getText().toString().trim());

                    }
                    int food_val_temp = result_participant * food_val;
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


                    if (food.getText().toString().trim().equals("")) {
                        food_val = 0;

                    } else {

                        food_val = Integer.parseInt(food.getText().toString().trim());

                    }
                    int food_val_temp = result * food_val;
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

        /*======================================================================= submit button click============================================================================*/

        btnSignUp.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {


                Bundle b = getIntent().getExtras();
                userName = b.getString("UserName");


                String miscell_value_str = miscell_bdt_iou.getText().toString().trim();
                int miscell_value = Integer.parseInt(miscell_bdt.getText().toString());
                int miscell_value_iou = Integer.parseInt(miscell_bdt_iou.getText().toString());

                // int food_value_iou = Integer.parseInt(food_iou.getText().toString());
                double food_value_iou = Double.parseDouble(food_iou.getText().toString());


                int doc_count = Integer.parseInt(no_of_pc_rmp.getText().toString());
                int dcc_count = Integer.parseInt(no_of_dcc.getText().toString());
                int inhouse_count = Integer.parseInt(no_of_inhouse.getText().toString());


                int total_doc = doc_count + dcc_count;
                int total_participant_conf = doc_count + dcc_count + inhouse_count;


                if (venue_voucher_head.getText().toString().trim().equals("") || venue_voucher_head.getText().toString().trim().equals(0)) {

                    new AlertDialog.Builder(PCBillSubmit.this)
                            .setTitle("Venue Voucher head not given not Selected")
                            .setMessage("Please enter Valid Venue Voucher head ")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    venue_voucher_head.setFocusableInTouchMode(true);
                                    venue_voucher_head.setFocusable(true);
                                    venue_voucher_head.requestFocus();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setCancelable(true)
                            .show();

                } else if (food_value_iou > 0 && food_voucher_head.getText().toString().trim().equals("")) {

                    new AlertDialog.Builder(PCBillSubmit.this)
                            .setTitle("Food Voucher head not given not Selected")
                            .setMessage("Please enter a Valid Food Voucher head ")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    food_voucher_head.setFocusableInTouchMode(true);
                                    food_voucher_head.setFocusable(true);
                                    food_voucher_head.requestFocus();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setCancelable(true)
                            .show();


                } else if (miscell_value_iou > 0 && miscell_voucher_head.getText().toString().trim().equals("")) {
                    new AlertDialog.Builder(PCBillSubmit.this)
                            .setTitle("Miscelleneous Voucher head not given ")
                            .setMessage("Please enter Miscelleneous Voucher head ")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    miscell_voucher_head.setFocusableInTouchMode(true);
                                    miscell_voucher_head.setFocusable(true);
                                    miscell_voucher_head.requestFocus();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setCancelable(true)
                            .show();

                } else if (venue_voucher_date.getText().toString().trim().equals("") || venue_voucher_date.getText().toString().trim().equals(null)) {
                    new AlertDialog.Builder(PCBillSubmit.this)
                            .setTitle("Venue Voucher Date not given ")
                            .setMessage("Please enter Venue Voucher date ")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    venue_voucher_date.requestFocus();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setCancelable(true)
                            .show();

                } else if (food_voucher_date.getText().toString().trim().equals("") || food_voucher_date.getText().toString().trim().equals(null)) {
                    new AlertDialog.Builder(PCBillSubmit.this)
                            .setTitle("Food Voucher Date not given ")
                            .setMessage("Please enter Food Voucher date ")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    food_voucher_date.requestFocus();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setCancelable(true)
                            .show();

                } else if (miscell_value_iou > 0 && miscell_voucher_date.getText().toString().trim().equals("")) {
                    new AlertDialog.Builder(PCBillSubmit.this)
                            .setTitle("Miscelleneous Voucher Date not given ")
                            .setMessage("Please enter Miscelleneous Voucher Date not given ")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    miscell_voucher_date.requestFocus();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setCancelable(true)
                            .show();

                } else if (conf_type_val.equals("R") && Double.parseDouble(food_iou.getText().toString().trim()) > Double.parseDouble(food_budget_limit)) {
                    food.setError("Food cost limit is exceed");
                    error_dt.setText("Food cost is not more then   BDT. " + food_budget_limit + " Tk ");
                } else if (conf_type_val.equals("R") && Integer.parseInt(venue_charge_iou.getText().toString().trim()) > Integer.parseInt(venue_budget_limit)) {
                    food.setError("Venue Charge  limit is exceed");
                    error_dt.setText("Venue Charge is not more then   BDT. " + venue_budget_limit + " Tk ");
                } else if (conf_type_val.equals("R") && Integer.parseInt(miscell_bdt_iou.getText().toString().trim()) > Integer.parseInt(miscell_budget_limit)) {
                    food.setError("Budeget  limit is exceed");
                    error_dt.setText("Miscellaneous is not more then   BDT. " + miscell_budget_limit + " Tk ");
                } else if (conf_type_val.equals("R") && ((total_doc > 30))) {
                    error_dt.setText("Total Doctor participant limit is exceed");
                } else if (conf_type_val.equals("R") && ((total_participant_conf > 35))) {

                    error_dt.setText("Total participant limit is exceed");
                } else {

                    Thread next = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            JSONParser jsonParser = new JSONParser();
                            List<NameValuePair> params = new ArrayList<NameValuePair>();

                            int doc_count=Integer.parseInt(no_of_pc_rmp.getText().toString());
                            int dcc_count=Integer.parseInt(no_of_dcc.getText().toString());
                            int inhouse_count=Integer.parseInt(no_of_inhouse.getText().toString());
                            int total_participent = doc_count + dcc_count + inhouse_count;



                            params.add(new BasicNameValuePair("MPO_CODE", userName));
                            params.add(new BasicNameValuePair("conference_date", ded.getText().toString()));
                            params.add(new BasicNameValuePair("total_participent", String.valueOf(total_participent)));
                            params.add(new BasicNameValuePair("pc_conference_iou", pc_conference_iou));
                            params.add(new BasicNameValuePair("pc_conference_serial", pc_conference_serial));
                            params.add(new BasicNameValuePair("venue_voucher_head", venue_voucher_head.getText().toString().trim()));
                            params.add(new BasicNameValuePair("venue_charge_iou", venue_charge_iou.getText().toString().trim()));
                            params.add(new BasicNameValuePair("venue_charge_unit", venue_charge.getText().toString().trim()));
                            params.add(new BasicNameValuePair("food_voucher_head", food_voucher_head.getText().toString().trim()));
                            params.add(new BasicNameValuePair("food_iou", food_iou.getText().toString().trim()));
                            params.add(new BasicNameValuePair("food_unit_bdt", food.getText().toString().trim()));
                            params.add(new BasicNameValuePair("miscell_voucher_head", miscell_voucher_head.getText().toString().trim()));
                            params.add(new BasicNameValuePair("miscell_bdt_iou", miscell_bdt_iou.getText().toString().trim()));
                            params.add(new BasicNameValuePair("miscell_bdt", miscell_bdt.getText().toString().trim()));
                            params.add(new BasicNameValuePair("miscell_voucher_date", miscell_voucher_date.getText().toString().trim()));
                            params.add(new BasicNameValuePair("food_voucher_date", food_voucher_date.getText().toString().trim()));
                            params.add(new BasicNameValuePair("venue_voucher_date", venue_voucher_date.getText().toString().trim()));
                            params.add(new BasicNameValuePair("arranged_conference_date", arranged_conference_date.getText().toString().trim()));
                            params.add(new BasicNameValuePair("arranged_venue_name", arranged_venue_name.getText().toString().trim()));
                            params.add(new BasicNameValuePair("no_of_pc_rmp", no_of_pc_rmp.getText().toString().trim()));
                            params.add(new BasicNameValuePair("no_of_dcc", no_of_dcc.getText().toString().trim()));
                            params.add(new BasicNameValuePair("no_of_inhouse", no_of_inhouse.getText().toString().trim()));

                           JSONObject json = jsonParser.makeHttpRequest(pc_conference_bill_submit, "POST", params);



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
                            String MPO_CODE = bundle.getString("MPO_CODE");
                            String pc_bill_no = message;
                            Intent sameint = new Intent(PCBillSubmit.this, PCBill.class);
                            sameint.putExtra("UserName", userName);
                            sameint.putExtra("UserName_2", UserName_2);
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





        /*======================================================================= next button click============================================================================*/


    }


    class GeTPcConferenceEditData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            final JSONParser jsonParser = new JSONParser();
            final List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", pc_conference_serial));

            JSONObject json = jsonParser.makeHttpRequest(pc_conference_get_data_edit, "POST", params);
            try {
                success = json.getInt(TAG_SUCCESS);
                get_pc = json.getString(tag_get_pc);
                get_dcc = json.getString(tag_get_dcc);
                get_inhouse = json.getString(tag_get_inhouse);
                get_vcharge = json.getString(tag_get_vcharge);
                get_misc = json.getString(tag_get_misc);
                get_fbudget = json.getString(tag_get_fbudget);
                get_impact_text= json.getString(tag_conf_outcome);
                get_venue_name= json.getString(tag_conf_venue_name);
                get_misc_text=json.getString(tag_miscellaneous);
                get_conference_date=json.getString(tag_conf_proposed_date);
                Log.w("please wait ...." + message, json.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pc_conference_edit_data();
        }

    }

    private void pc_conference_edit_data() {

        proposed_pc_rmp.setText(get_pc);
        no_of_pc_rmp.setText(get_pc);
        proposed_dcc.setText(get_dcc);
        no_of_dcc.setText(get_dcc);
        proposed_inhouse.setText(get_inhouse);
        no_of_inhouse.setText(get_inhouse);
        venue_charge.setText(get_vcharge);
        venue_charge_iou.setText(get_vcharge);
        miscell_bdt.setText(get_misc);
        miscell_bdt_iou.setText(get_misc);
        food.setText(get_fbudget);
        food_iou.setText(get_fbudget);
        v_charge = get_vcharge;
        pc_rmp_val = Integer.parseInt(get_pc);
        doc_val = Integer.parseInt(get_dcc);
        inhouse_val = Integer.parseInt(get_inhouse);
        int total_par = Integer.parseInt(get_pc) + Integer.parseInt(get_dcc) + Integer.parseInt(get_inhouse);
        String parti = String.valueOf(total_par);
        total_participent.setText(parti);

        impact.setText(get_impact_text);
        venue_name.setText(get_venue_name);
        arranged_venue_name.setText(get_venue_name);
        miscell.setText(get_misc_text);
        get_conference_date = get_conference_date.replace("-", "/");
        ded.setText(get_conference_date);
        arranged_conference_date.setText(get_conference_date);



    }







    /*=========================================== pc conference permission check================================================*/

    private void pc_conference_flag() {
        check_flag = "0";
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < checkflag.size(); i++) {
            check_flag = checkflag.get(i).getName();
        }
    }

    /*======================================================================================================================*/

    /*=========================================== pc conference setupdata check================================================*/


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
            //  pc_conference_setup_data();
        }

    }



    /*======================================================================================================================*/


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
            pDialog = new ProgressDialog(PCBillSubmit.this);
            pDialog.setMessage("Loading Market name..");
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