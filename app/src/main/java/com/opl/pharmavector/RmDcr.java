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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Date;

import static android.content.ContentValues.TAG;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import com.opl.pharmavector.mpodcr.GiftOrder;

public class RmDcr extends Activity implements OnItemSelectedListener {

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
    private com.opl.pharmavector.SessionManager session;
    public Button next, back, dcr_submit, chemist_ppm, rx_page;
    public static String name = null, newversion_text = null, ename = null;
    public int credit_party = 0, cash_party = 0;
    Editor editor;
    public EditText osi, op, od, dateResult, ref, date_ext;
    // private ListView cust;
    private ArrayList<com.opl.pharmavector.RmCustomer> customerlist;
    private ArrayList<com.opl.pharmavector.RmCustomer> visitorlist;
    private ArrayList<com.opl.pharmavector.RmCustomer> chemistlist;
    private ArrayList<com.opl.pharmavector.RmCustomer> shiftlist;
    private ArrayList<com.opl.pharmavector.RmCustomer> dateextendlist;
    public Array pay_cash;
    public Array pay_cradit;

    private com.opl.pharmavector.RmDatabaseHandler db;
    private String f_name, s_name;
    private Button mOffline;
    private ListView lv;
    private com.opl.pharmavector.Contact dataModel;
    Toast toast;
    Toast toast2;
    Toast toast3;
    public String dcr_code = "";

    public String dt_code;
    public String com_ana_val;
    public String pay_cash1, userName, userName_1, userName_2;

    public com.opl.pharmavector.stirng pay_credit1;
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

    private String URL_CUSOTMER = BASE_URL+"regional_manager_api/rmdcr/get_doctor.php";
    private String URL_EMP = BASE_URL+"regional_manager_api/rmdcr/getemp.php";
    private String URL_CHEMIST = BASE_URL+"regional_manager_api/rmdcr/get_chemist.php";
    private String submit_doctor_url = BASE_URL+"regional_manager_api/rmdcr/dcrsubmit_doctor.php";
    private String get_dcr_date = BASE_URL+"regional_manager_api/rmdcr/get_dcr.php";
    private String URL_SHIFT = BASE_URL+"regional_manager_api/rmdcr/getshift.php";
    private String date_range_permission = BASE_URL+"regional_manager_api/rmdcr/date_range_permission.php";

    protected Handler handler;
    com.opl.pharmavector.DialogMultipleChoice mDialogMultipleChoice;

    @SuppressLint("CutPasteId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rmdcr);


        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        logout = (Button) findViewById(R.id.logout);
        logout.setTypeface(fontFamily);
        logout.setText("\uf08b"); //&#xf08b
        user_show = (TextView) findViewById(R.id.user_show);
        newversion = (TextView) findViewById(R.id.newversion);
        setTitle("DCR Entry");
        next = (Button) findViewById(R.id.next);
        next.setTypeface(fontFamily);
        next.setText("\uf1d8");

        chemist_ppm = (Button) findViewById(R.id.chemist_ppm);
        dcr_submit = (Button) findViewById(R.id.offline);


        dcr_submit.setTypeface(fontFamily);
        dcr_submit.setText("\uf1d8");


        Button back = (Button) findViewById(R.id.back);

        final Button rx_page = (Button) findViewById(R.id.rx_page);

        back.setTypeface(fontFamily);
        back.setText("\uf060 ");

        db = new com.opl.pharmavector.RmDatabaseHandler(this);

        dcrdaterange = new ArrayList<>();
        final TextView error_dt = (TextView) findViewById(R.id.errordt);
        final TextView error_payment = (TextView) findViewById(R.id.errorpayment);


        op = (EditText) findViewById(R.id.orderpage);

        cust = (Spinner) findViewById(R.id.customer);
        cust.setPrompt("Select Doctor");


        chemist = (Spinner) findViewById(R.id.chemist);
        chemist.setPrompt("Select Chemist");
        ref = (EditText) findViewById(R.id.reference);
        ded = (TextView) findViewById(R.id.deliverydate);


        v_location = (Spinner) findViewById(R.id.v_location);


        no_of_rx = (EditText) findViewById(R.id.no_of_rx);
        no_of_prod = (EditText) findViewById(R.id.no_of_prod);
        prod_of_opsonin = (EditText) findViewById(R.id.prod_of_opsonin);
        ratio_of_opso = (TextView) findViewById(R.id.ratio_of_opso);
        ratio_of_opso.setVisibility(View.GONE);
        prod_of_opsonin.setVisibility(View.GONE);


        Date today = new Date();
        remarks = (TextView) findViewById(R.id.remarks);
        comp_ana = (TextView) findViewById(R.id.comp_ana);

        s_time = (TextView) findViewById(R.id.starttime);
        e_time = (TextView) findViewById(R.id.endtime);


        TextView ordno = (TextView) findViewById(R.id.ordno);
        TextView succ_msg = (TextView) findViewById(R.id.succ_msg);

        date_ext = (EditText) findViewById(R.id.date_extend);
        note = (TextView) findViewById(R.id.note);
        LinearLayout mainlayout = (LinearLayout) findViewById(R.id.successmsg);


        dcrdatelist = new ArrayList<>();
        new GetDcrDateOffline().execute();




        /*==================================================== dcr spinner ===================================*/


        final Spinner dcr_spinner = (Spinner) findViewById(R.id.dcrtype);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dcr_spinner.setPrompt("Select Dcr Type");
        dcr_spinner.setAdapter(adapter);

        // dcr_spinner.setAdapter(new NothingSelectedSpinnerAdapter(adapter, R.layout.contact_spinner_row_nothing_selected, this));

        dcr_spinner.setFocusable(true);
        dcr_spinner.setFocusableInTouchMode(true);
        dcr_spinner.requestFocus();
        dcr_spinner.setOnItemSelectedListener(this);

        /*====================================================     dcr spinner end =======================================================*/


        visitor = (Spinner) findViewById(R.id.visitor);
        visitor.setPrompt("Select Visitor");
        visitor.setOnItemSelectedListener(this);


        /*====================================================  location spinner =======================================================*/


        shift_spinner = (Spinner) findViewById(R.id.shift_spinner);
        shift_spinner.setPrompt("Select Shift");
        shift_spinner.setOnItemSelectedListener(this);



        /*====================================================  test spinner =======================================================*/


        mDialogMultipleChoice = new com.opl.pharmavector.DialogMultipleChoice(this);

        findViewById(R.id.show_multiple_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialogMultipleChoice.show(s_time);
            }
        });




        final Spinner location = (Spinner) findViewById(R.id.location);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.location, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        location.setPrompt("Select Location");
        location.setAdapter(adapter1);
        location.setOnItemSelectedListener(this);
        location.setVisibility(View.VISIBLE);



        final Spinner v_location = (Spinner) findViewById(R.id.v_location);
        ArrayAdapter<CharSequence> adapter_v_location = ArrayAdapter.createFromResource(this, R.array.rx_location, android.R.layout.simple_spinner_item);
        adapter_v_location.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        v_location.setPrompt("Select Visit Location");
        v_location.setAdapter(adapter_v_location);
        v_location.setOnItemSelectedListener(this);
        v_location.setVisibility(View.VISIBLE);





        final Spinner ampmspin = (Spinner) findViewById(R.id.ampm);
        ArrayAdapter<CharSequence> ampm_adapter = ArrayAdapter.createFromResource(this, R.array.am_pm, android.R.layout.simple_spinner_item);


        ampm_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ampmspin.setPrompt("Select Shift");
        ampmspin.setAdapter(ampm_adapter);

        ampmspin.setOnItemSelectedListener(this);


        /*====================================================    visit to spinner  =======================================================*/





        /*==================================================SPINNER VALUE FROM DATABASE===============================================================*/


        final Spinner chemordoc = (Spinner) findViewById(R.id.chemordoc);
        ArrayAdapter<CharSequence> adapter_chem_doc = ArrayAdapter.createFromResource(this, R.array.cord, android.R.layout.simple_spinner_item);

        adapter_chem_doc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chemordoc.setPrompt("Visit to ");
        chemordoc.setAdapter(adapter_chem_doc);
        chemordoc.setOnItemSelectedListener(this);




        /*====================================================     visit to spinner  end =======================================================*/


        final Spinner yes_no = (Spinner) findViewById(R.id.ppm_type);
        ArrayAdapter<CharSequence> adapter0 = ArrayAdapter.createFromResource(this, R.array.yes_no_am, android.R.layout.simple_spinner_item);

        adapter0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yes_no.setPrompt("Visited with PM? ");
        yes_no.setAdapter(adapter0);
        yes_no.setVisibility(View.GONE);
        yes_no.setOnItemSelectedListener(this);


        cashcredit = (Spinner) findViewById(R.id.cashcredit);
        credit = (Spinner) findViewById(R.id.credit);
        cashcredit.setVisibility(View.GONE);
        credit.setVisibility(View.GONE);



        final AutoCompleteTextView doccode = (AutoCompleteTextView) findViewById(R.id.doccode);

        final AutoCompleteTextView visitorcode = (AutoCompleteTextView) findViewById(R.id.visitorcode);
        final AutoCompleteTextView marketcode = (AutoCompleteTextView) findViewById(R.id.marketcode);

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
        dcr_submit.setEnabled(true);
        dcr_submit.setClickable(true);
        dcr_submit.setPressed(true);

        chemist_ppm.setVisibility(View.GONE);




        customerlist = new ArrayList<com.opl.pharmavector.RmCustomer>();
        cust.setOnItemSelectedListener(this);

        visitorlist = new ArrayList<com.opl.pharmavector.RmCustomer>();
        visitor.setOnItemSelectedListener(this);


        chemistlist = new ArrayList<com.opl.pharmavector.RmCustomer>();
        chemist.setOnItemSelectedListener(this);

        dateextendlist = new ArrayList<com.opl.pharmavector.RmCustomer>();


        shiftlist = new ArrayList<com.opl.pharmavector.RmCustomer>();


        new GeTShift().execute();
        new GeTDateExtend().execute();
        new GetEmp().execute();


        final String dat_val_ext = date_ext.getText().toString().trim();
        final com.opl.pharmavector.MultiSelectionSpinner spinner = (com.opl.pharmavector.MultiSelectionSpinner) findViewById(R.id.input1);

        List<String> list = new ArrayList<String>();
        list.add("GMSM");
        list.add("SM");
        list.add("DSM");
        list.add("ASM");
        list.add("AM");
        list.add("MPO");
        spinner.setItems(list);



        final AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        final AutoCompleteTextView actv2 = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView2);
        final AutoCompleteTextView actv3 = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView3);

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

        actv2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (actv2.getText().toString() != "") {

                    String selectedcustomer2 = actv2.getText().toString();
                    System.out.println("Selectedcustomer = " + selectedcustomer2);
                    visitor.setTag(selectedcustomer2);
                }

            }
        });


        actv3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (actv3.getText().toString() != "") {

                    String selectedchemist = actv3.getText().toString();
                    Toast.makeText(getBaseContext(), "selected chemist " + selectedchemist, Toast.LENGTH_LONG).show();
                    chemist.setTag(selectedchemist);
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
                        String cust_type = inputorder.substring(inputorder.indexOf(":") + 1);
                        String cust_type_with_note = inputorder.substring(inputorder.indexOf(":") + 0);
                        String arr[] = cust_type_with_note.split(":");
                        cust_type_with_note = arr[1].trim();

                        String first_split[] = inputorder.split(":");
                        String split1 = first_split[0].trim();
                        String market_code = first_split[1].trim();
                        Log.e("split1",split1);
                        marketcode.setText(market_code);

                        String second_split[] = split1.split("//");
                        String split2 = second_split[0].trim();
                        String doc_code = second_split[1].trim();
                        doccode.setText(doc_code); //doctorcode

                        String third_split[] = split2.split("-");
                        String doc_name = third_split[0].trim();
                        String mpocodedoc = third_split[1].trim();
                        doccode.setText(doc_code); //doctorcode
                        actv.setText(doc_name);

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


                        cashcredit = (Spinner) findViewById(R.id.cashcredit);

                        List list = new ArrayList();
                        for (int i = 1; i < arr1.length; i++) {
                            list.add(arr1[i].trim());
                        }


                        ArrayAdapter dataAdapter = new ArrayAdapter(RmDcr.this, android.R.layout.simple_spinner_item, list);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        cashcredit.setAdapter(dataAdapter);


                        credit = (Spinner) findViewById(R.id.credit);
                        List list_credit = new ArrayList();
                        for (int j = 1; j < arr1.length; j++) {
                            list_credit.add(arr1[j].trim());
                        }
                        ArrayAdapter dataAdapter_credit = new ArrayAdapter(RmDcr.this, android.R.layout.simple_spinner_item, list);
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
                            credit = (Spinner) findViewById(R.id.credit);
                            cashcredit = (Spinner) findViewById(R.id.cashcredit);
                            View credit_view = (Spinner) findViewById(R.id.credit);
                            View cashcredit_view = (Spinner) findViewById(R.id.cashcredit);
                            credit_view.setVisibility(View.GONE);
                            cashcredit_view.setVisibility(View.GONE);
                            // cashcredit_view.setVisibility(View.VISIBLE);
                            Log.e("cashcredit_view: ", "> " + cashcredit_view);


                            s_time.setFocusable(true);
                            s_time.setFocusableInTouchMode(true);
                            s_time.requestFocus();

                        } else {

                            credit = (Spinner) findViewById(R.id.credit);
                            cashcredit = (Spinner) findViewById(R.id.cashcredit);
                            View credit_view = (Spinner) findViewById(R.id.credit);
                            View cashcredit_view = (Spinner) findViewById(R.id.cashcredit);
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

                    if (inputorder.indexOf(":") != -1) {

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

            private void length() {
                // TODO Auto-generated method stub

            }


        });


        remarks.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (actv2.getText().toString() != "") {

                    final String remarksvalue = remarks.getText().toString();


                }

            }
        });


        comp_ana.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (actv2.getText().toString() != "") {

                    final String comp_anavalue = comp_ana.getText().toString();


                }

            }
        });



        final TextView myTextView = (TextView) findViewById(R.id.user_show);

        final TextView newversion = (TextView) findViewById(R.id.newversion);
        Bundle b = getIntent().getExtras();
        //String ordernumber=b.getString("Ord_NO");
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
                //myTextView.setText(userName);
                String UserName_3 = b.getString("UserName_2");
                if (UserName_3.equals("DCR Submitted")) {
                    ordno.setText(ordernumber);
                    ordno.setTextSize(12);
                }

                ordno.setText("DCR Sl No." + ordernumber);
                ordno.setTextSize(12);
                succ_msg.setText("Submitted");
                String mpo[] = ordernumber.split("-");
                if (UserName_2.equals("DCR Submitted")) {
                    myTextView.setText(mpo[0]);
                    myTextView.setTextSize(12);

                } else {
                    myTextView.setText(mpo[0]);
                    myTextView.setTextSize(12);
                }


            }
        }




        final Calendar cal = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy ");
        String date_str = df.format(cal.getTime());
        ded.setText(date_str);


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
                error_dt.setText("");
                //note.setText("" );
                //  ded.setTextSize(14);
                ded.setTextColor(Color.BLACK);
                ded.setText(sdf.format(myCalendar.getTime()));
                Log.w("Selected date", "> " + ded.getText().toString());


            }

        };

        ded.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(RmDcr.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
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

                    yes_no.setVisibility(View.GONE);
                    chemordoc.setVisibility(View.VISIBLE);
                    v_location.setVisibility(View.GONE);

                    no_of_rx.setVisibility(View.GONE);
                    no_of_prod.setVisibility(View.GONE);
                    prod_of_opsonin.setVisibility(View.GONE);
                    ratio_of_opso.setVisibility(view.GONE);
                    s_time.setVisibility(View.VISIBLE);
                    e_time.setVisibility(View.VISIBLE);

                    chemordoc.setEnabled(true);
                    chemordoc.setClickable(true);

                    actv.setVisibility(View.VISIBLE);
                    actv.setFocusable(true);
                    actv.setEnabled(true);
                    actv.setClickable(true);


                    spinner.setVisibility(View.VISIBLE);

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


                    v_location.setVisibility(View.GONE);
                    no_of_rx.setVisibility(View.GONE);
                    no_of_prod.setVisibility(View.GONE);
                    prod_of_opsonin.setVisibility(View.GONE);
                    ratio_of_opso.setVisibility(View.GONE);

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

                } else if (dcr_code.equals("RX")) {

                    dt_code = "X";
                    new GetCategories().execute();

                    chemordoc.setVisibility(View.GONE);
                    yes_no.setVisibility(View.GONE);
                    actv3.setVisibility(View.GONE);

                    v_location.setVisibility(View.VISIBLE);
                    no_of_rx.setVisibility(View.VISIBLE);
                    no_of_prod.setVisibility(View.VISIBLE);
                    prod_of_opsonin.setVisibility(View.VISIBLE);
                    ratio_of_opso.setVisibility(View.VISIBLE);


                    actv.setVisibility(View.VISIBLE);
                    actv.setFocusable(true);
                    actv.setFocusableInTouchMode(true);
                    actv.requestFocus();
                    actv.setVisibility(View.VISIBLE);
                    actv.setEnabled(true);
                    actv.setPressed(true);
                    actv.setClickable(true);


                    rx_page.setClickable(true);
                    rx_page.setPressed(true);
                    rx_page.setEnabled(true);

                    spinner.setVisibility(View.GONE);
                    s_time.setVisibility(View.GONE);
                    e_time.setVisibility(View.GONE);


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
                final String check = String.valueOf(loc_code);
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
                vcheckloccode = String.valueOf(v_loc_code);
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

                mTimePicker = new TimePickerDialog(RmDcr.this, new TimePickerDialog.OnTimeSetListener() {
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
                mTimePicker = new TimePickerDialog(RmDcr.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        e_time.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
                String check_shift = s_time.getText().toString();

                String arr[] = check_shift.split(":");
                String day_shift = arr[0].trim();
                int shift_check = Integer.parseInt(day_shift);
             


            }
        });


        /*=============================================Update Sells=============================================*/

        session = new com.opl.pharmavector.SessionManager(getApplicationContext());


        shift_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                shift_status = shift_spinner.getSelectedItem().toString();
                if (dcr_code.equals("Regular")) {
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
                Toast.makeText(RmDcr.this, "Please Select Shift !!", Toast.LENGTH_LONG).show();
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
                    //  shift_spinner.setEnabled(false);
                    next.setVisibility(View.GONE);
                    next.setEnabled(false);
                    next.setPressed(false);
                    next.setClickable(false);

                    yes_no.setVisibility(View.GONE);
                    actv.setVisibility(View.VISIBLE);
                    actv.setFocusable(true);
                    actv.setFocusableInTouchMode(true);
                    actv.requestFocus();
                    actv.setVisibility(View.VISIBLE);
                    actv3.setVisibility(View.GONE);


                } else if (chemordoc.getSelectedItem().toString().equals("Chemist")) {

                    actv3.setVisibility(View.VISIBLE);
                    actv3.setFocusable(true);
                    actv3.setFocusableInTouchMode(true);
                    actv3.requestFocus();
                    // shift_spinner.setEnabled(false);

                    new GetChemist().execute();
                    Log.v("Visit to =>>>", "> " + chemordoc.getSelectedItem().toString());

                    yes_no.setVisibility(View.GONE);
                    actv3.setFocusable(true);
                    actv3.setFocusableInTouchMode(true);
                    actv3.requestFocus();

                    actv.setVisibility(View.GONE);


                    chemist_ppm.setVisibility(View.GONE);
                    chemist_ppm.setEnabled(false);
                    chemist_ppm.setPressed(false);
                    chemist_ppm.setClickable(false);

                }


            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });


        yes_no.setOnItemSelectedListener(new OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.v("Visit with PM?", "> " + yes_no.getSelectedItem().toString());

                yes_no_val = yes_no.getSelectedItem().toString();


                if (yes_no.getSelectedItem().toString().equals("Yes")) {


                    Log.v("value", "> " + yes_no.getSelectedItem().toString());


                } else if (yes_no.getSelectedItem().toString().equals("No")) {


                    Log.v("value", "> " + yes_no.getSelectedItem().toString());
                }


            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });


        /*=============================================Log out Button Event click===================================================================*/
        back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Thread mysells = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Intent i = new Intent(RmDcr.this,  RmDashboard.class);
                        i.putExtra("UserName", RmDashboard.globalRMCode);
                        i.putExtra("new_version", RmDashboard.new_version);
                        i.putExtra("UserName_2", RmDashboard.globalRegionalCode);
                        i.putExtra("message_3", RmDashboard.message_3);
                        i.putExtra("password", RmDashboard.password);
                        i.putExtra("ff_type", RmDashboard.ff_type);
                        i.putExtra("vector_version", R.string.vector_version);
                        i.putExtra("emp_code", RmDashboard.globalempCode);
                        i.putExtra("emp_name", RmDashboard.globalempName);
                        startActivity(i);

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
                        com.opl.pharmavector.JSONParser jsonParser = new com.opl.pharmavector.JSONParser();
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("logout", "logout"));
                        JSONObject json = jsonParser.makeHttpRequest(com.opl.pharmavector.Login.LOGIN_URL, "POST", params);

                    }
                });

                server.start();
                logoutUser();
            }
        });


        prod_of_opsonin.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

                if (prod_of_opsonin.getText().toString().trim().equals("")) {
                    prod_of_opsonin.setText("0");


                } else {


                    String a = prod_of_opsonin.getText().toString().trim();
                    Float f = Float.parseFloat(a);
                    String b = no_of_prod.getText().toString().trim();
                    Float g = Float.parseFloat(b);
                    Float result = (f / g) * 100;
                    int result1 = (int) Math.round(result);
                    String total2 = String.valueOf(result1);


                    ratio_of_opso.setText(" Ratio of Opsonin products are  " + total2 + " %");
                }


            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {

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

                Toast.makeText(getApplicationContext(), "HEMISTbutton is clicked", Toast.LENGTH_LONG).show();

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


                //------------------------rez-------------------------
                int  total_back_day = Integer.parseInt(date_ext.getText().toString());

                int rcYear = c.get(Calendar.YEAR);
                int rcMonth = c.get(Calendar.MONTH) + 1;
                int rcDay = c.get(Calendar.DAY_OF_MONTH);

                int rcYear_day=rcYear*365;
                int rcMonth_day=rcMonth*30;
                int rctotal_day_today=rcYear_day+rcMonth_day+rcDay;

                int total_valid_back_day=rctotal_day_today-total_back_day;




                int rgyear = myCalendar.get(Calendar.YEAR);
                int rgmonth = myCalendar.get(Calendar.MONTH) + 1;
                int rgday = myCalendar.get(Calendar.DAY_OF_MONTH);


                int rgyear_day=rgyear*365;
                int rgmonth_day=rgmonth*30;
                int rgtotal_day_given=rgyear_day+rgmonth_day+rgday;

                int day_diff=rctotal_day_today-rgtotal_day_given;

                Log.e("rgyear---", "> " + rgyear);
                Log.e("rgmonth---", "> " + rgmonth);
                Log.e("rgday---", "> " + rgday);
                Log.e(" ---------;---", "> " +  "------------------");

                Log.e(" rcYear;---", "> " +  rcYear);
                Log.e("rcMonth---", "> " + rcMonth);
                Log.e("rcDay---", "> " + rcDay);



                Log.e("rgtotal_day_given---", "> " + rgtotal_day_given);
                Log.e("rctotal_day_today---", "> " + rctotal_day_today);
                Log.e("total_back_day---", "> " + total_back_day);

                Log.e("day_diff---", "> " + day_diff);

                //-----------------------rez------------------------








                if ((ded.getText().toString().trim().equals("")) || (ded.getText().toString().trim().equals("Reference Date")) || (ded.getText().toString().trim().equals("Please Select date"))) {

                    ded.setTextSize(14);
                    ded.setText("Please Select date");
                    ded.setTextColor(Color.RED);
                } else if ((actv.getText().toString().trim().equals("")) || (actv.getText().toString().trim().equals("Input Customer (eg. dh..)"))) {


                    actv.setError("Doctor  not Assigned !");
                    actv.setText("Please insert  Doctor Name ");
                    actv.setTextColor(Color.RED);

                }


                /*
                else if (totalday_given > totalday_valid) {

                    error_dt.setText("Delivery Date  is not greater then current date!");

                } else if (totalday_given < totalday_valid2) {


                    error_dt.setText("Previous date can not be more then 7 days.. ");
                }

*/

                else if (rgtotal_day_given > rctotal_day_today) {

                    error_dt.setText("Delivery Date  is not greater than current date!");

                }

                else if(rgtotal_day_given < total_valid_back_day){


                    ded.setError( "Delivery Date  is not less " + total_back_day  + "  than days from current date" );
                    error_dt.setText("Delivery Date  is not less " + total_back_day  +" than  days from current date" );
                }

















                final Spinner nameSpinner = (Spinner) findViewById(R.id.customer);
                final String selected_cust = actv.getText().toString();


                // final String select_party1 = select_party.toString();
                Bundle b = getIntent().getExtras();
                final String userName = b.getString("UserName");
                String UserName_1 = b.getString("UserName_1");


                Log.e("mpo_code", "> " + actv.getText().toString());
                Log.e("Mpo Code", "> " + userName);
                Log.e("doc code", "> " + doccode.getText().toString());
                Log.e("doctor name", "> " + actv.getText().toString());
                Log.e("doc code", "> " + doccode.getText().toString());
                Log.e("end time ", "> " + e_time.getText().toString());
                Log.e("start time", "> " + s_time.getText().toString());
                Log.e("shift", "> " + shift_status);
                Log.v("Type", "> " + dt_code);
                Log.w("location code", "> " + loc_code);
                Log.e("DATE", "> " + ded.getText().toString());
                Log.e("ORDER_REFERANCE_NO ", "> " + ref.getText().toString());
                Log.e("MPO_CODE ---", "> " + userName);
                Log.e("VISITOR_CODE ---", "> " + visitorcode.getText().toString());
                Log.e("VISIT_DATE ---", "> " + ded.getText().toString());
                Log.e("Chemist Code", "> " + doccode.getText().toString());


                //   autoCompleteTextView3


                Thread next = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //   Intent in = new Intent(  Dcr.this, ProductOrder.class);


                        Intent in = new Intent(RmDcr.this, ChemistGiftOrder.class);

                        Bundle extras = new Bundle();


                        String str = ded.getText().toString();
                        String date_1 = str.replaceAll("[^\\d.-]", "");

                        final String generated_ord_no = userName + "-" + date_1;

                        extras.putString("MPO_CODE", userName);
                        extras.putString("CUST_CODE", userName);

                        //  extras.putString("AM_PM", shift_status);
                        extras.putString("AM_PM", shift_spinner.getSelectedItem().toString());

                        //  extras.putString("AM_PM", ampmspin.getSelectedItem().toString());
                        //  extras.putString("cash_credit", select_party.toString());
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
//     Log.e("Chemist Code", "> " +doccode.getText().toString());
                        extras.putString("CHEM_CODE", doccode.getText().toString());

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

                //  }


                //    }


                //  }

            }   // end else //
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



                //------------------------rez-------------------------
                int  total_back_day = Integer.parseInt(date_ext.getText().toString());

                int rcYear = c.get(Calendar.YEAR);
                int rcMonth = c.get(Calendar.MONTH) + 1;
                int rcDay = c.get(Calendar.DAY_OF_MONTH);

                int rcYear_day=rcYear*365;
                int rcMonth_day=rcMonth*30;
                int rctotal_day_today=rcYear_day+rcMonth_day+rcDay;

                int total_valid_back_day=rctotal_day_today-total_back_day;




                int rgyear = myCalendar.get(Calendar.YEAR);
                int rgmonth = myCalendar.get(Calendar.MONTH) + 1;
                int rgday = myCalendar.get(Calendar.DAY_OF_MONTH);


                int rgyear_day=rgyear*365;
                int rgmonth_day=rgmonth*30;
                int rgtotal_day_given=rgyear_day+rgmonth_day+rgday;

                int day_diff=rctotal_day_today-rgtotal_day_given;

                Log.e("rgyear---", "> " + rgyear);
                Log.e("rgmonth---", "> " + rgmonth);
                Log.e("rgday---", "> " + rgday);
                Log.e(" ---------;---", "> " +  "------------------");

                Log.e(" rcYear;---", "> " +  rcYear);
                Log.e("rcMonth---", "> " + rcMonth);
                Log.e("rcDay---", "> " + rcDay);



                Log.e("rgtotal_day_given---", "> " + rgtotal_day_given);
                Log.e("rctotal_day_today---", "> " + rctotal_day_today);
                Log.e("total_back_day---", "> " + total_back_day);

                Log.e("day_diff---", "> " + day_diff);

                //-----------------------rez------------------------






                if (dcr_spinner.getSelectedItem().toString().equals("Regular")) {

                    error_dt.setText("Select DCR TYPE 'RX' instead of 'Regular'. Click ON 'Regular' to change DCR TYPE");

                }
/*
                else if(dcr_spinner.getSelectedItem().toString().equals("Journey") || dcr_spinner.getSelectedItem().toString().equals("Conference") ||
                        dcr_spinner.getSelectedItem().toString().equals("Training") || dcr_spinner.getSelectedItem().toString().equals("Others") ||
                        dcr_spinner.getSelectedItem().toString().equals("Meeting")
                          ||dcr_spinner.getSelectedItem().toString().equals("Holiday") || dcr_spinner.getSelectedItem().toString().equals("Leave")) {

                    error_dt.setText("Select DCR TYPE 'RX' instead of 'Regular' Type and Click ON 'Regular' to change DCR TYPE");


                  }

*/

                else if (dcr_spinner.getSelectedItem().toString().equals("Journey")) {

                    error_dt.setText("Select DCR TYPE 'RX' instead of 'Journey' Type. Click ON 'Journey' to change DCR TYPE");

                } else if (dcr_spinner.getSelectedItem().toString().equals("Conference")) {

                    error_dt.setText("Select DCR TYPE 'RX' instead of 'Conference' Type. Click ON 'Conference' to change DCR TYPE");

                } else if (dcr_spinner.getSelectedItem().toString().equals("Training")) {

                    error_dt.setText("Select DCR TYPE 'RX' instead of 'Training' Type. Click ON 'Training' to change DCR TYPE");

                } else if (dcr_spinner.getSelectedItem().toString().equals("Conference")) {

                    error_dt.setText("Select DCR TYPE 'RX' instead of 'Conference' Type. Click ON 'Conference' to change DCR TYPE");

                } else if (dcr_spinner.getSelectedItem().toString().equals("Others")) {

                    error_dt.setText("Select DCR TYPE 'RX' instead of 'Others' Type. Click ON 'Others' to change DCR TYPE");

                } else if (dcr_spinner.getSelectedItem().toString().equals("Meeting")) {

                    error_dt.setText("Select DCR TYPE 'RX' instead of 'Meeting' Type. Click ON 'Meeting' to change DCR TYPE");

                } else if (dcr_spinner.getSelectedItem().toString().equals("Holiday")) {

                    error_dt.setText("Select DCR TYPE 'RX' instead of 'Holiday' Type. Click ON 'Holiday' to change DCR TYPE");

                } else if (dcr_spinner.getSelectedItem().toString().equals("Leave")) {

                    error_dt.setText("Select DCR TYPE 'RX' instead of 'Leave' Type. Click ON 'Leave' to change DCR TYPE");

                } else if (actv.getText().toString().trim().equals("")) {


                    error_dt.setText("Please Select Doctor");

                } else if (no_of_rx.getText().toString().trim().equals("") || (no_of_rx.getText().toString().trim().equals("No Of Rx"))) {


                    error_dt.setText("Please Select No of Rx");

                } else if (no_of_prod.getText().toString().trim().equals("") || (no_of_prod.getText().toString().trim().equals("No Of Products"))) {


                    error_dt.setText("Please Select No of Products");
                } else if (prod_of_opsonin.getText().toString().trim().equals("") || (prod_of_opsonin.getText().toString().trim().equals("No Of Opsonin Products"))) {

                    error_dt.setText("Please Select No of Opsonin Products Products");
                }

                else if (v_location.getSelectedItem().toString().trim().equals("")) {

                    error_dt.setText("Please Select Visit Location (EX: Outdoor, Indoor or GP");
                }

/*
                else if (totalday_given > totalday_valid) {

                    error_dt.setText("Delivery Date  is not greater then current date!");

                } else if (totalday_given < totalday_valid2) {


                    error_dt.setText("Previous date can not be more then 7 days.. ");
                }
*/


                else if (rgtotal_day_given > rctotal_day_today) {

                    error_dt.setText("Delivery Date  is not greater than current date!");

                }

                else if(rgtotal_day_given < total_valid_back_day){


                    ded.setError( "Delivery Date  is not less " + total_back_day  + "  than days from current date" );
                    error_dt.setText("Delivery Date  is not less " + total_back_day  +" than  days from current date" );
                }










                else {
                    final Spinner nameSpinner = (Spinner) findViewById(R.id.customer);
                    final String selected_cust = actv.getText().toString();


                    // final String select_party1 = select_party.toString();
                    Bundle b = getIntent().getExtras();
                    final String userName = b.getString("UserName");
                    String UserName_1 = b.getString("UserName_1");


                    Log.e("mpo_code", "> " + actv.getText().toString());
                    Log.e("Mpo Code", "> " + userName);
                    Log.e("doc code", "> " + doccode.getText().toString());

                    Log.e("VISIT_DATE ---", "> " + ded.getText().toString());

                    Log.e("$vcheckloccode ---", "> " + vcheckloccode);
                    Log.e("no_of_rx ---", "> " + no_of_rx.getText().toString());
                    Log.e("no_of_prod ---", "> " + no_of_prod.getText().toString());
                    Log.e("prod_of_opsonin ---", "> " + prod_of_opsonin.getText().toString());


                    Thread next = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //   Intent in = new Intent(  Dcr.this, ProductOrder.class);


                            Intent in = new Intent(RmDcr.this, com.opl.pharmavector.AmRxCompany.class);

                            Bundle extras = new Bundle();


                            String str = ded.getText().toString();
                            String date_1 = str.replaceAll("[^\\d.-]", "");

                            final String generated_ord_no = userName + "-" + date_1;

                            extras.putString("MPO_CODE", userName);
                            extras.putString("CUST_CODE", userName);

                            //  extras.putString("AM_PM", shift_status);
                            extras.putString("AM_PM", shift_spinner.getSelectedItem().toString());

                            //  extras.putString("AM_PM", ampmspin.getSelectedItem().toString());
                            //  extras.putString("cash_credit", select_party.toString());
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

                    //  }


                    //    }


                }

            }   // end else //
        });






        dcr_submit.setOnClickListener(new OnClickListener() {


            public void onClick(View v) {

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



                //------------------------rez-------------------------
                int  total_back_day = Integer.parseInt(date_ext.getText().toString());

                int rcYear = c.get(Calendar.YEAR);
                int rcMonth = c.get(Calendar.MONTH) + 1;
                int rcDay = c.get(Calendar.DAY_OF_MONTH);

                int rcYear_day=rcYear*365;
                int rcMonth_day=rcMonth*30;
                int rctotal_day_today=rcYear_day+rcMonth_day+rcDay;

                int total_valid_back_day=rctotal_day_today-total_back_day;




                int rgyear = myCalendar.get(Calendar.YEAR);
                int rgmonth = myCalendar.get(Calendar.MONTH) + 1;
                int rgday = myCalendar.get(Calendar.DAY_OF_MONTH);


                int rgyear_day=rgyear*365;
                int rgmonth_day=rgmonth*30;
                int rgtotal_day_given=rgyear_day+rgmonth_day+rgday;

                int day_diff=rctotal_day_today-rgtotal_day_given;

                Log.e("rgyear---", "> " + rgyear);
                Log.e("rgmonth---", "> " + rgmonth);
                Log.e("rgday---", "> " + rgday);
                Log.e(" ---------;---", "> " +  "------------------");

                Log.e(" rcYear;---", "> " +  rcYear);
                Log.e("rcMonth---", "> " + rcMonth);
                Log.e("rcDay---", "> " + rcDay);



                Log.e("rgtotal_day_given---", "> " + rgtotal_day_given);
                Log.e("rctotal_day_today---", "> " + rctotal_day_today);
                Log.e("total_back_day---", "> " + total_back_day);

                Log.e("day_diff---", "> " + day_diff);

                //-----------------------rez------------------------







                if (dcr_code.equals("RX")) {


                    error_dt.setText("Click Other Company Products Button ");


                } else if ((ded.getText().toString().trim().equals("")) || (ded.getText().toString().trim().equals("Reference Date")) || (ded.getText().toString().trim().equals("Please Select date"))) {

                    ded.setTextSize(14);
                    ded.setText("Please Select date");
                    ded.setTextColor(Color.RED);
                }
                /*
                else if (totalday_given > totalday_valid) {

                    error_dt.setText("Select Todays Date");

                }
                */






                else if (rgtotal_day_given > rctotal_day_today) {

                    error_dt.setText("Delivery Date  is not greater than current date!");

                } //else if (totalday_given < totalday_valid2) {

                   /*
                    else if(rgtotal_day_given < total_valid_back_day){


                    error_dt.setText("Previous date can not be more then 7 days.. ");
                }
                */


                // else if(totalday_given < totalday_valid2){
                else if(rgtotal_day_given < total_valid_back_day){


                    ded.setError( "Delivery Date  is not less " + total_back_day  + "  than days from current date" );
                    error_dt.setText("Delivery Date  is not less " + total_back_day  +" than  days from current date" );
                }










                else if (dcr_code.equals("RX")) {

                    error_dt.setText("Click  'Other Company Products' Button");

                }

               /*
                else if (totalday_given < totalday_valid2) {
                    error_dt.setText("Select Todays Date");
                }

                */


                else if (dcr_spinner.getSelectedItem().toString().equals("Select Dcr Type ")) {
                    error_dt.setText("Select DCR Type");
                    dcr_spinner.setPrompt("Select DCR Type");

                } else if (dcr_spinner.getSelectedItem().toString().equals("Select Dcr Type ")) {
                    error_dt.setText("Select DCR Type");
                    dcr_spinner.setPrompt("Select DCR Type");

                } else {

                    Thread server = new Thread(new Runnable() {

                        @Override
                        public void run() {


                            com.opl.pharmavector.JSONParser jsonParser = new com.opl.pharmavector.JSONParser();
                            List<NameValuePair> params = new ArrayList<NameValuePair>();

                            Log.e("ORD_NO", "> " + ord_no);
                            Log.e("MPO_CODE", "> " + userName);
                            Log.e("VISITOR_CODE", "> " + visitorcode.getText().toString());
                            Log.e("doc code", "> " + doccode.getText().toString());
                            Log.e("doctor name", "> " + actv.getText().toString());
                            Log.e("doc code", "> " + doccode.getText().toString());
                            Log.e("end time ", "> " + e_time.getText().toString());
                            Log.e("start time", "> " + s_time.getText().toString());
                            Log.e("shift", "> " + shift_status);
                            Log.e("Type", "> " + dt_code);
                            Log.e("TOUR_NATURE", "> " + loc_code);
                            Log.e("DATE", "> " + ded.getText().toString());
                            Log.e("ORDER_REFERANCE_NO ", "> " + ref.getText().toString());
                            Log.e("MPO_CODE", "> " + userName);
                            Log.e("VISIT_DATE", "> " + ded.getText().toString());
                            Log.e("visit with ", "> " + spinner.getSelectedItem().toString());
                            Log.e("COMPETITOR_ANALYSIS", comp_ana.getText().toString());
                            Log.e("remarks_value", remarks.getText().toString());
                            Log.e("yes_no_val", yes_no_val);
                            Log.e("shift", "> " + shift_spinner.getSelectedItem().toString());
                            Log.e("CHEM_FLAG", "> " + CHEM_FLAG);

                            params.add(new BasicNameValuePair("ORD_NO", ord_no));
                            params.add(new BasicNameValuePair("MPO_CODE", userName));
                            params.add(new BasicNameValuePair("VISITOR_CODE", visitorcode.getText().toString()));
                            params.add(new BasicNameValuePair("TOUR_NATURE", loc_code));
                            params.add(new BasicNameValuePair("VISIT_DATE", ded.getText().toString()));
                            params.add(new BasicNameValuePair("DCR_TYPE", dt_code));
                            //  params.add(new BasicNameValuePair("AM_PM", shift_status));
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
                                // TODO Auto-generated catch block
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
                            Intent sameint = new Intent(RmDcr.this, RmDcr.class);
                            sameint.putExtra("Ord_NO", ord_no);
                            sameint.putExtra("UserName", userName);
                            sameint.putExtra("UserName_2", UserName_2);
                            startActivity(sameint);
                            Log.w("Passed in DCR TO DCR", ord_no + "UserName" + userName + "UserName_2" + UserName_2);


                        }
                    });


                    server.start();

                }
                //  }


            }
        });



        /*================================================       end dcr submit button     ===================================================================*/




        /*=============================================    start next button for doctors ppm   ================================================================*/


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

                } else if (totalday_given > totalday_valid) {


                    // ded.setError( "Delivery Date  is not more than 6 days" );
                    error_dt.setText("Delivery Date  is not greater then current date!");

                } else if (totalday_given < totalday_valid2) {


                    error_dt.setText("Previous date can not be more then 7 days.. ");
                } else if ((comp_ana.getText().toString().equals("Competitors activity analysis"))) {

                    comp_ana.getText().toString().equals("");
                    com_ana_val = "";

                } else {

                    if (cash_party == 1) {
                        select_party = cashcredit.getSelectedItem().toString();
                    } else {
                        select_party = credit.getSelectedItem().toString();
                    }

                    if (select_party.equals("Select payment mode")) {
                        error_dt.setText("Please Select payment mode by click! ");
                        error_payment.setError("Please Select payment mode by click!");
                    } else {

                        final Spinner nameSpinner = (Spinner) findViewById(R.id.customer);
                        final String selected_cust = actv.getText().toString();
                        com_ana_val = comp_ana.getText().toString();
                        final String select_party1 = select_party.toString();
                        Bundle b = getIntent().getExtras();
                        final String userName = b.getString("UserName");
                        String UserName_1 = b.getString("UserName_1");
                        Thread next = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Intent in = new Intent(RmDcr.this, GiftOrder.class);
                                Bundle extras = new Bundle();
                                String str = ded.getText().toString();
                                String date_1 = str.replaceAll("[^\\d.-]", "");
                                final String generated_ord_no = userName + "-" + date_1;
                                extras.putString("MPO_CODE", userName);
                                extras.putString("CUST_CODE", userName);
                                //  extras.putString("AM_PM", shift_status);
                                extras.putString("AM_PM", shift_spinner.getSelectedItem().toString());
                                //  params.add(new BasicNameValuePair("AM_PM", shift_spinner.getSelectedItem().toString()));


                                extras.putString("cash_credit", select_party.toString());
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


        actv.setThreshold(2);
        actv.setAdapter(Adapter);
        actv.setTextColor(Color.BLUE);
    }



    private void populateSpinner2() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < visitorlist.size(); i++) {
            lables.add(visitorlist.get(i).getName());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, lables);
        visitor.setAdapter(spinnerAdapter);
        String[] customer = lables.toArray(new String[lables.size()]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, customer);
        AutoCompleteTextView actv2 = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView2);
        actv2.setThreshold(2);
        actv2.setAdapter(Adapter);
        actv2.setTextColor(Color.BLUE);

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

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, lables);
        chemist.setAdapter(spinnerAdapter);

        String[] customer = lables.toArray(new String[lables.size()]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, customer);
        AutoCompleteTextView actv3 = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView3);


        actv3.setThreshold(2);
        actv3.setAdapter(Adapter);
        actv3.setTextColor(Color.RED);
    }



    class GetCategories extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RmDcr.this);
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
            params.add(new BasicNameValuePair("id", RmDashboard.globalRMCode));

            Log.e("rmcode",RmDashboard.globalRegionalCode);
            com.opl.pharmavector.ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_CUSOTMER, ServiceHandler.POST, params);
            Log.e("rmjson-->",json);
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray customer = jsonObj.getJSONArray("customer");
                        for (int i = 0; i < customer.length(); i++) {
                            JSONObject catObj = (JSONObject) customer.get(i);

                            RmCustomer custo = new RmCustomer(catObj.getInt("id"), catObj.getString("name"));
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


    /*===================================================   End   Asnyc Task FOR DOCTORS ==========================================================================*/



    /*=============================================        Asnyc Task FOR Visitors      ==========================================================================*/


    class GetEmp extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog2 = new ProgressDialog(RmDcr.this);
            pDialog2.setMessage("Fetching Employees..");
            pDialog2.setCancelable(false);
            pDialog2.show();

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
            com.opl.pharmavector.ServiceHandler jsonParser = new com.opl.pharmavector.ServiceHandler();
            // String json=jsonParser.makeServiceCall(URL_CUSOTMER, ServiceHandler.POST, params);

            String json = jsonParser.makeServiceCall(URL_EMP, com.opl.pharmavector.ServiceHandler.POST, params);

            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray customer = jsonObj.getJSONArray("customer");
                        for (int i = 0; i < customer.length(); i++) {
                            JSONObject catObj = (JSONObject) customer.get(i);
                            com.opl.pharmavector.RmCustomer custo = new com.opl.pharmavector.RmCustomer(catObj.getInt("id"), catObj.getString("name"));
                            visitorlist.add(custo);
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
            pDialog2.dismiss();
            populateSpinner2();
        }

    }


    /*===================================================   End   Asnyc Task FOR Visitors  =========================================================================*/


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
            com.opl.pharmavector.ServiceHandler jsonParser = new com.opl.pharmavector.ServiceHandler();


            String json = jsonParser.makeServiceCall(date_range_permission, com.opl.pharmavector.ServiceHandler.POST, params);

            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray customer = jsonObj.getJSONArray("customer");
                        for (int i = 0; i < customer.length(); i++) {
                            JSONObject catObj = (JSONObject) customer.get(i);
                            com.opl.pharmavector.RmCustomer custo = new com.opl.pharmavector.RmCustomer(catObj.getInt("id"), catObj.getString("name"));

                            dateextendlist.add(custo);

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
            //    pDialog2.dismiss();
            populatedcr_date_extend();
        }

    }


    class GeTShift extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //  pDialog2 = new ProgressDialog(Dcr.this);
            //  pDialog2.setMessage("Fetching Shift..");
            //  pDialog2.setCancelable(false);
            //  pDialog2.show();

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
            com.opl.pharmavector.ServiceHandler jsonParser = new com.opl.pharmavector.ServiceHandler();
            // String json=jsonParser.makeServiceCall(URL_CUSOTMER, ServiceHandler.POST, params);

            String json = jsonParser.makeServiceCall(URL_SHIFT, com.opl.pharmavector.ServiceHandler.POST, params);

            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray customer = jsonObj.getJSONArray("customer");
                        for (int i = 0; i < customer.length(); i++) {
                            JSONObject catObj = (JSONObject) customer.get(i);
                            com.opl.pharmavector.RmCustomer custo = new com.opl.pharmavector.RmCustomer(catObj.getInt("id"), catObj.getString("name"));
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
            //    pDialog2.dismiss();
            populateSpinnerShift();
        }

    }


    class GetChemist extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RmDcr.this);
            pDialog.setMessage("Fetching Chemist ...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... arg0) {


            // ServiceHandler jsonParser = new ServiceHandler();
            // String json = jsonParser.makeServiceCall(URL_CUSOTMER,ServiceHandler.GET);

            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            String id = userName;
            //   Log.v("postshift: ", "> " + shift_spinner.getSelectedItem().toString());

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", id));
            params.add(new BasicNameValuePair("shift", shift_spinner.getSelectedItem().toString()));
            com.opl.pharmavector.ServiceHandler jsonParser = new com.opl.pharmavector.ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_CHEMIST, com.opl.pharmavector.ServiceHandler.POST, params);

            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray customer = jsonObj.getJSONArray("customer");
                        for (int i = 0; i < customer.length(); i++) {
                            JSONObject catObj = (JSONObject) customer.get(i);
                            com.opl.pharmavector.RmCustomer custo = new com.opl.pharmavector.RmCustomer(catObj.getInt("id"), catObj.getString("name"));
                            chemistlist.add(custo);
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
            populateSpinner3();
        }

    }


    /*========================================================  Get submitted dcr date offline     ======================================================================*/

    private class GetDcrDateOffline extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected Void doInBackground(Void... arg0) {

            Bundle b = getIntent().getExtras();
            String id = b.getString("UserName");

            // pDialog.show();


            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", id));


            com.opl.pharmavector.ServiceHandler jsonParser = new com.opl.pharmavector.ServiceHandler();
            String jsonStr2 = jsonParser.makeServiceCall(get_dcr_date, com.opl.pharmavector.ServiceHandler.POST, params);

            Log.e(TAG, "Response from url: " + jsonStr2);

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
                        db.addDcrDate(new com.opl.pharmavector.DcrDate(cust, cust_name));


                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //     Toast.makeText(getApplicationContext(), "Customer Updated" + e.getMessage() , Toast.LENGTH_LONG).show();


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
            // Dismiss the progress dialog
            //  if (pDialog.isShowing())
            //   pDialog.dismiss();


        }

    }

    /*========================================================  Get submitted dcr date offline     ======================================================================*/



    /*====================================================== Getcurrentdateonlogin=======================================================*/


    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }


    private void logoutUser() {
        session.setLogin(false);
        // session.removeAttribute();
        session.invalidate();
        Intent intent = new Intent(RmDcr.this, com.opl.pharmavector.Login.class);
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
