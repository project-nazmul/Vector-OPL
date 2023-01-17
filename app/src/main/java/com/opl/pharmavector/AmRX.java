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

import static com.opl.pharmavector.remote.ApiClient.BASE_URL_AM;

import com.opl.pharmavector.mpodcr.GiftOrder;

public class AmRX extends Activity implements OnItemSelectedListener {

    private Spinner spinner1, spinner2,cashcredit,cashcredit_test,credit;
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_MESSAGE_1 = "message_1";
    public static final String TAG_invoice = "invoice";
    public static final String TAG_target = "target";
    public static final String TAG_achivement = "achivement";
    public static final String TAG_MESSAGE_new_version = "new_version";
    public   String vcheckloccode;
    public String yes_no_val ="";
    private Button logout,achivbtn;
    private TextView user_show,newversion,visit_with_value,visit_with_value_1,visit_with_value_2,visit_with_value_3,visit_with_value_4;
    private SessionManager session;
    public Button next, back,dcr_submit,chemist_ppm,rx_page;
    public static String name=null,newversion_text=null,ename=null;
    public int credit_party=0,cash_party=0;
    Editor editor;
    public EditText osi, op, od, dateResult, ref,date_ext;
    // private ListView cust;
    private ArrayList<com.opl.pharmavector.AmCustomer> customerlist;
    private ArrayList<com.opl.pharmavector.AmCustomer> visitorlist;
    private ArrayList<com.opl.pharmavector.AmCustomer> chemistlist;
    private ArrayList<com.opl.pharmavector.AmCustomer> shiftlist;
    private ArrayList<com.opl.pharmavector.AmCustomer> dateextendlist;
    public Array pay_cash;
    public Array pay_cradit;

    private com.opl.pharmavector.DatabaseHandler db;
    private String f_name,s_name;
    private Button mOffline;
    private ListView lv;
    private com.opl.pharmavector.Contact dataModel;
    Toast toast;
    Toast toast2;
    Toast toast3;
    public String dcr_code="";

    public String dt_code;
    public String com_ana_val;
    public String pay_cash1,userName,userName_1,userName_2;

    public stirng pay_credit1;
    public String  location_code;
    public String   loc_code;

    public String  v_location_code;
    public String   v_loc_code;
    private Spinner cust,visitor,chemist,shift_spinner,dcr_date_extend,v_location;
    ProgressDialog pDialog,pDialog2;
    EditText mEdit;
    TextView date2, ded,note,s_time,e_time,remarks,comp_ana,ratio_of_opso;
    EditText no_of_rx,no_of_prod,prod_of_opsonin;
    public int success;
    public String message, ord_no,invoice,target_data,achivement,searchString,select_party;
    ArrayList<HashMap<String, String>> dcrdatelist;
    ArrayList<HashMap<String, String>> dcrdaterange;
    public String shift_status ="Morning";
    public String CHEM_FLAG =" ";
    public String get_ext_dt;
    private final String URL_CUSOTMER = BASE_URL_AM+"get_doctor.php";
    private final String URL_EMP = BASE_URL_AM+"getemp.php";
    private final String URL_CHEMIST = BASE_URL_AM+"get_chemist.php";
    private final String submit_doctor_url = BASE_URL_AM+"dcrsubmit_doctor.php";
    private final String get_dcr_date = BASE_URL_AM+"get_dcr.php";
    private final String URL_SHIFT = BASE_URL_AM+"getshift.php";
    private final String date_range_permission = BASE_URL_AM+"date_range_permission.php";

    protected Handler handler;
    DialogMultipleChoice mDialogMultipleChoice;
    @SuppressLint("CutPasteId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.amrxpage);
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        logout = (Button) findViewById(R.id.logout);
        logout.setTypeface(fontFamily);
        logout.setText("\uf08b"); //&#xf08b
        user_show = (TextView) findViewById(R.id.user_show);
        newversion = (TextView) findViewById(R.id.newversion);
        setTitle("DCR Entry");
        next = (Button) findViewById(R.id.next);

        chemist_ppm = (Button) findViewById(R.id.chemist_ppm);

        dcr_submit = (Button) findViewById(R.id.offline);



        db=new com.opl.pharmavector.DatabaseHandler(this);


        dcrdaterange= new ArrayList<>();
        // new GetDcrDateRange().execute();

        final TextView error_dt=(TextView)findViewById(R.id.errordt);
        final TextView error_payment=(TextView)findViewById(R.id.errorpayment);



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



        Date today = new Date();
        System.out.println("today: "+today.toString());
        Log.w("date",today.toString());



        remarks = (TextView) findViewById(R.id.remarks);
        comp_ana = (TextView) findViewById(R.id.comp_ana);

        s_time = (TextView) findViewById(R.id.starttime);
        e_time = (TextView) findViewById(R.id.endtime);

        TextView ordno=(TextView) findViewById(R.id.ordno);
        TextView succ_msg=(TextView) findViewById(R.id.succ_msg);

        date_ext=(EditText) findViewById(R.id.date_extend);


        note=(TextView) findViewById(R.id.note);
        LinearLayout mainlayout = (LinearLayout)findViewById(R.id.successmsg);




        dcrdatelist= new ArrayList<>();
        new GetDcrDateOffline().execute();




        final AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        final AutoCompleteTextView actv2 = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView2);
        final AutoCompleteTextView actv3 = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView3);
        final AutoCompleteTextView actv4 = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView4);


        actv4.setVisibility(View.VISIBLE);
        actv4.setFocusable(true);
        actv4.setFocusableInTouchMode(true);
        actv4.requestFocus();



        /*==================================================== dcr spinner ===================================*/



        final   Spinner dcr_spinner = (Spinner) findViewById(R.id.dcrtype);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.rxval,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dcr_spinner.setPrompt("Select Dcr Type");
        dcr_spinner.setAdapter(adapter);

        // dcr_spinner.setAdapter(new NothingSelectedSpinnerAdapter(adapter, R.layout.contact_spinner_row_nothing_selected, this));

        dcr_spinner.setFocusable(true);
        dcr_spinner.setFocusableInTouchMode(true);
      //  dcr_spinner.requestFocus();
        dcr_spinner.setOnItemSelectedListener(this);

        /*====================================================     dcr spinner end =======================================================*/



        visitor = (Spinner) findViewById(R.id.visitor);
        visitor.setPrompt("Select Visitor");
        visitor.setSelected(false);  // must
        visitor.setSelection(0,true);  //must
        visitor.setOnItemSelectedListener(this);


        /*====================================================  location spinner =======================================================*/



        shift_spinner = (Spinner) findViewById(R.id.shift_spinner);
        shift_spinner.setPrompt("Select Shift");
        shift_spinner.setSelected(false);  // must
        shift_spinner.setSelection(0,true);  //must
        shift_spinner.setOnItemSelectedListener(this);



        /*====================================================  test spinner =======================================================*/


        mDialogMultipleChoice = new DialogMultipleChoice(this);

        findViewById(R.id.show_multiple_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialogMultipleChoice.show(s_time);
            }
        });


        /*==================================================== end test spinner =======================================================*/




        final Spinner location = (Spinner) findViewById(R.id.location);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.location, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        location.setPrompt("Select Location");

        location.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapter1, R.layout.location_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));
        location.setSelected(false);  // must
        location.setSelection(0,true);  //must
        location.setOnItemSelectedListener(this);
        location.setVisibility(View.VISIBLE);


        /*====================================================  location spinner =======================================================*/

        /*====================================================  visit location spinner =======================================================*/


        final Spinner v_location = (Spinner) findViewById(R.id.v_location);
        ArrayAdapter<CharSequence> adapter_v_location = ArrayAdapter.createFromResource(this, R.array.rx_location, android.R.layout.simple_spinner_item);
        adapter_v_location.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        v_location.setPrompt("Select Visit Location");

        v_location.setVisibility(View.GONE);
/*
        v_location.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapter_v_location, R.layout.visit_location_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));
        v_location.setSelected(false);  // must
        v_location.setSelection(0,true);  //must
        v_location.setOnItemSelectedListener(this);
        v_location.setVisibility(View.VISIBLE);

*/







        /*====================================================  visit location spinner =======================================================*/





        final Spinner ampmspin=(Spinner)findViewById(R.id.ampm);
        ArrayAdapter<CharSequence> ampm_adapter = ArrayAdapter.createFromResource(this, R.array.am_pm, android.R.layout.simple_spinner_item);


        ampm_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ampmspin.setPrompt("Select Shift");

        ampmspin.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        ampm_adapter, R.layout.shift_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));



        ampmspin.setSelected(false);  // must
        ampmspin.setSelection(0,true);  //must
        ampmspin.setOnItemSelectedListener(this);


        /*====================================================    visit to spinner  =======================================================*/





        /*==================================================SPINNER VALUE FROM DATABASE===============================================================*/




        final Spinner chemordoc=(Spinner)findViewById(R.id.chemordoc);
        ArrayAdapter<CharSequence> adapter_chem_doc = ArrayAdapter.createFromResource(this, R.array.cord, android.R.layout.simple_spinner_item);

        adapter_chem_doc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chemordoc.setPrompt("Visit to ");

        chemordoc.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapter_chem_doc,
                        R.layout.visit_to_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));



        chemordoc.setSelected(false);  // must
        chemordoc.setSelection(0,true);  //must
        chemordoc.setOnItemSelectedListener(this);




        /*====================================================     visit to spinner  end =======================================================*/



        final Spinner yes_no=(Spinner)findViewById(R.id.ppm_type);
        ArrayAdapter<CharSequence> adapter0 = ArrayAdapter.createFromResource(this, R.array.yes_no, android.R.layout.simple_spinner_item);

        adapter0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yes_no.setPrompt("Visited with PM? ");

        yes_no.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapter0,
                        R.layout.yes_no_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));



        yes_no.setVisibility(View.GONE);





        yes_no.setSelected(false);  // must
        yes_no.setSelection(0,true);  //must
        yes_no.setOnItemSelectedListener(this);


        cashcredit=(Spinner)findViewById(R.id.cashcredit);
        credit=(Spinner)findViewById(R.id.credit);
        cashcredit.setVisibility(View.GONE);
        credit.setVisibility(View.GONE);

        Button back= (Button) findViewById(R.id.back);
        final Button rx_page= (Button) findViewById(R.id.rx_page);

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




        /*========================================================================hide button on create==============================*/




        next.setClickable(false);
        next.setPressed(false);
        next.setEnabled(false);



        dcr_submit.setEnabled(false);


        chemist_ppm.setVisibility(View.GONE);

        dcr_submit.setClickable(false);
        dcr_submit.setPressed(false);





        /*======================================================================== end hide button on create==============================*/







        /*---------- getting CSS form user inputs-----------*/
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }

        customerlist = new ArrayList<com.opl.pharmavector.AmCustomer>();
        cust.setOnItemSelectedListener(this);

        visitorlist = new ArrayList<com.opl.pharmavector.AmCustomer>();
        visitor.setOnItemSelectedListener(this);




        chemistlist = new ArrayList<com.opl.pharmavector.AmCustomer>();
        chemist.setOnItemSelectedListener(this);

        dateextendlist = new ArrayList<com.opl.pharmavector.AmCustomer>();


        shiftlist = new ArrayList<com.opl.pharmavector.AmCustomer>();
        shift_spinner.setOnItemSelectedListener(this);

        shift_spinner.setSelected(false);  // must
        shift_spinner.setSelection(0,true);  //must
        shift_spinner.setOnItemSelectedListener(this);

        /*================================================================get customer========================================*/



        new GeTShift().execute();
        new GeTDateExtend().execute();
        new GetEmp().execute();

        final String dat_val_ext = date_ext.getText().toString().trim();

        Log.w("akon",dat_val_ext);




        final  MultiSelectionSpinner spinner = (MultiSelectionSpinner)findViewById(R.id.input1);

        List<String> list = new ArrayList<String>();
        list.add("GMSM");
        list.add("SM");
        list.add("DSM");
        list.add("ASM");
        list.add("RM");
        list.add("MPO");
        spinner.setItems(list);


        /*===========================================================customer end===============================================*/



        actv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (actv.getText().toString() != "") {
                    String selectedcustomer = actv.getText().toString();
                    System.out.println("Selectedcustomer = "+selectedcustomer);
                    cust.setTag(selectedcustomer);
                }

            }
        });




        actv4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (actv.getText().toString() != "") {
                    String selectedcustomer = actv.getText().toString();
                    System.out.println("Selectedcustomer = "+selectedcustomer);
                    cust.setTag(selectedcustomer);
                }

            }
        });



        /*-------------------- Autocomplete Textview 2 =====  Visit with Employee --------------------------*/


        actv2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (actv2.getText().toString() != "") {

                    String selectedcustomer2 = actv2.getText().toString();
                    System.out.println("Selectedcustomer = "+ selectedcustomer2);
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

                    Toast.makeText(getBaseContext(), "selected chemist "+selectedchemist , Toast.LENGTH_LONG).show();

                    System.out.println("Selectedcustomer = "+selectedchemist);
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
                    int total_string=inputorder.length();
                    if(inputorder.indexOf(":") != -1){
                        String cust_type = inputorder.substring(inputorder.indexOf(":") + 1);
                        String cust_type_with_note = inputorder.substring(inputorder.indexOf(":") + 0);


                        String arr[] = cust_type_with_note.split(":");
                        cust_type_with_note=arr[1].trim();

                        Log.e("amdoctor: ", "> " + cust_type_with_note);
                        Log.w("amdoctor: ", "> " + cust_type_with_note);
                        String arr1[] = cust_type_with_note.split("///");




                        cashcredit = (Spinner) findViewById(R.id.cashcredit);

                        List list = new ArrayList();
                        for(int i = 1; i < arr1.length; i++){
                            list.add(arr1[i].trim());
                        }
                       // RX

                        ArrayAdapter dataAdapter = new ArrayAdapter(  AmRX.this,android.R.layout.simple_spinner_item, list);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        cashcredit.setAdapter(dataAdapter);



                        credit = (Spinner) findViewById(R.id.credit);
                        List list_credit = new ArrayList();
                        for(int j = 1; j < arr1.length; j++){
                            list_credit.add(arr1[j].trim());
                        }
                        ArrayAdapter dataAdapter_credit = new ArrayAdapter(  AmRX.this,android.R.layout.simple_spinner_item, list);
                        dataAdapter_credit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        credit.setAdapter(dataAdapter);





                        int cust_type_with_note_length=cust_type_with_note.length();
                        String cust_address = s.toString();
                        String customer_address = cust_address.substring(0,total_string-cust_type_with_note_length);

                        String doc[] = customer_address.split("//");
                        String doc_name=doc[0].trim();
                        String doc_code = doc[1].trim();

                        String market[] = doc_code .split("-");
                        String doc_code1=market[0].trim();
                        String mar_name = market[1].trim();

                        String mar[] = mar_name.split(":");
                        String mar_name1=mar[0].trim();



                        actv.setText(doc_name);   ///doctorname
                        doccode.setText(doc_code1); //doctorcode
                        marketcode.setText(mar_name1); //marketname




                        cust_type=arr1[0];

                        if (cust_type.equals("CASH")) {
                            cash_party=1;
                            credit_party=0;
                            credit=(Spinner)findViewById(R.id.credit);
                            cashcredit=(Spinner)findViewById(R.id.cashcredit);
                            View credit_view=(Spinner)findViewById(R.id.credit);
                            View cashcredit_view=(Spinner)findViewById(R.id.cashcredit);
                            credit_view.setVisibility(View.GONE);
                            cashcredit_view.setVisibility(View.GONE);
                            // cashcredit_view.setVisibility(View.VISIBLE);
                            Log.e("cashcredit_view: ", "> " + cashcredit_view);


                          //  findViewById(R.id.show_multiple_dialog).setFocusableInTouchMode(true);
                          //  findViewById(R.id.show_multiple_dialog).setFocusable(true);
                         //   findViewById(R.id.show_multiple_dialog).setFocusableInTouchMode(true);
                         //   findViewById(R.id.show_multiple_dialog).requestFocus();

                            // actv2.setFocusable(true);
                            //  actv2.setFocusableInTouchMode(true);
                            //  actv2.requestFocus();


                            //spinner.setFocusable(true);
                           // spinner.setFocusableInTouchMode(true);
                           // spinner.requestFocus();

/*
                            ampmspin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                public void onFocusChange(View v, boolean hasFocus) {
                                    if(hasFocus) {
                                        s_time.requestFocus();
                                    }
                                }
                            });
*/



                        }else{

                          //  findViewById(R.id.show_multiple_dialog).setFocusableInTouchMode(true);
                          //  findViewById(R.id.show_multiple_dialog).setFocusable(true);
                          //  findViewById(R.id.show_multiple_dialog).setFocusableInTouchMode(true);
                          //  findViewById(R.id.show_multiple_dialog).requestFocus();



                            // actv2.setFocusable(true);
                            //  actv2.setFocusableInTouchMode(true);
                            // actv2.requestFocus();

                       //     spinner.setFocusable(true);
                         //   spinner.setFocusableInTouchMode(true);
                         //   spinner.requestFocus();
/*
                            ampmspin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                public void onFocusChange(View v, boolean hasFocus) {
                                    if(hasFocus) {
                                        s_time.requestFocus();
                                    }
                                }
                            });
*/

                            credit=(Spinner)findViewById(R.id.credit);
                            cashcredit=(Spinner)findViewById(R.id.cashcredit);
                            View credit_view=(Spinner)findViewById(R.id.credit);
                            View cashcredit_view=(Spinner)findViewById(R.id.cashcredit);
                            cashcredit_view.setVisibility(View.GONE);
                            credit_view.setVisibility(View.VISIBLE);
                            credit_view.setVisibility(View.GONE);
                            cash_party=0;
                            credit_party=1;

                        }

                    }else{
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


        actv4.addTextChangedListener(new TextWatcher() {

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
                    int total_string=inputorder.length();
                    if(inputorder.indexOf(":") != -1){
                        String cust_type = inputorder.substring(inputorder.indexOf(":") + 1);
                        String cust_type_with_note = inputorder.substring(inputorder.indexOf(":") + 0);


                        String arr[] = cust_type_with_note.split(":");
                        cust_type_with_note=arr[1].trim();

                        Log.e("amdoctor: ", "> " + cust_type_with_note);
                        Log.w("amdoctor: ", "> " + cust_type_with_note);
                        String arr1[] = cust_type_with_note.split("///");




                        cashcredit = (Spinner) findViewById(R.id.cashcredit);

                        List list = new ArrayList();
                        for(int i = 1; i < arr1.length; i++){
                            list.add(arr1[i].trim());
                        }


                        ArrayAdapter dataAdapter = new ArrayAdapter(  AmRX.this,android.R.layout.simple_spinner_item, list);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        cashcredit.setAdapter(dataAdapter);



                        credit = (Spinner) findViewById(R.id.credit);
                        List list_credit = new ArrayList();
                        for(int j = 1; j < arr1.length; j++){
                            list_credit.add(arr1[j].trim());
                        }
                        ArrayAdapter dataAdapter_credit = new ArrayAdapter(  AmRX.this,android.R.layout.simple_spinner_item, list);
                        dataAdapter_credit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        credit.setAdapter(dataAdapter);





                        int cust_type_with_note_length=cust_type_with_note.length();
                        String cust_address = s.toString();
                        String customer_address = cust_address.substring(0,total_string-cust_type_with_note_length);

                        String doc[] = customer_address.split("//");
                        String doc_name=doc[0].trim();
                        String doc_code = doc[1].trim();

                        String market[] = doc_code .split("-");
                        String doc_code1=market[0].trim();
                        String mar_name = market[1].trim();

                        String mar[] = mar_name.split(":");
                        String mar_name1=mar[0].trim();



                        actv4.setText(doc_name);   ///doctorname
                        doccode.setText(doc_code1); //doctorcode
                        marketcode.setText(mar_name1); //marketname




                        cust_type=arr1[0];

                        if (cust_type.equals("CASH")) {
                            cash_party=1;
                            credit_party=0;
                            credit=(Spinner)findViewById(R.id.credit);
                            cashcredit=(Spinner)findViewById(R.id.cashcredit);
                            View credit_view=(Spinner)findViewById(R.id.credit);
                            View cashcredit_view=(Spinner)findViewById(R.id.cashcredit);
                            credit_view.setVisibility(View.GONE);
                            cashcredit_view.setVisibility(View.GONE);
                            // cashcredit_view.setVisibility(View.VISIBLE);
                            Log.e("cashcredit_view: ", "> " + cashcredit_view);


                         //   findViewById(R.id.show_multiple_dialog).setFocusableInTouchMode(true);
                          //  findViewById(R.id.show_multiple_dialog).setFocusable(true);
                         //   findViewById(R.id.show_multiple_dialog).setFocusableInTouchMode(true);
                          //  findViewById(R.id.show_multiple_dialog).requestFocus();

                            // actv2.setFocusable(true);
                            //  actv2.setFocusableInTouchMode(true);
                            //  actv2.requestFocus();


                          //  spinner.setFocusable(true);
                          //  spinner.setFocusableInTouchMode(true);
                           // spinner.requestFocus();

/*
                            ampmspin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                public void onFocusChange(View v, boolean hasFocus) {
                                    if(hasFocus) {
                                        s_time.requestFocus();
                                    }
                                }
                            });
*/



                        }else{

                          //  findViewById(R.id.show_multiple_dialog).setFocusableInTouchMode(true);
                          //  findViewById(R.id.show_multiple_dialog).setFocusable(true);
                          //  findViewById(R.id.show_multiple_dialog).setFocusableInTouchMode(true);
                          //  findViewById(R.id.show_multiple_dialog).requestFocus();



                            // actv2.setFocusable(true);
                            //  actv2.setFocusableInTouchMode(true);
                            // actv2.requestFocus();

                           // spinner.setFocusable(true);
                          //  spinner.setFocusableInTouchMode(true);
                          //  spinner.requestFocus();
/*
                            ampmspin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                public void onFocusChange(View v, boolean hasFocus) {
                                    if(hasFocus) {
                                        s_time.requestFocus();
                                    }
                                }
                            });
*/

                            credit=(Spinner)findViewById(R.id.credit);
                            cashcredit=(Spinner)findViewById(R.id.cashcredit);
                            View credit_view=(Spinner)findViewById(R.id.credit);
                            View cashcredit_view=(Spinner)findViewById(R.id.cashcredit);
                            cashcredit_view.setVisibility(View.GONE);
                            credit_view.setVisibility(View.VISIBLE);
                            credit_view.setVisibility(View.GONE);
                            cash_party=0;
                            credit_party=1;

                        }

                    }else{
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
                    int total_string=inputorder.length();
                    if(inputorder.indexOf(":") != -1){
                        String cust_type = inputorder.substring(inputorder.indexOf(":") + 1);
                        String cust_type_with_note = inputorder.substring(inputorder.indexOf(":") + 0);


                        String arr[] = cust_type_with_note.split(":");
                        cust_type_with_note=arr[1].trim();
                        String arr1[] = cust_type_with_note.split("///");




                        cashcredit = (Spinner) findViewById(R.id.cashcredit);

                        List list = new ArrayList();
                        for(int i = 1; i < arr1.length; i++){
                            list.add(arr1[i].trim());
                        }


                        ArrayAdapter dataAdapter = new ArrayAdapter(  AmRX.this,android.R.layout.simple_spinner_item, list);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        cashcredit.setAdapter(dataAdapter);



                        credit = (Spinner) findViewById(R.id.credit);
                        List list_credit = new ArrayList();
                        for(int j = 1; j < arr1.length; j++){
                            list_credit.add(arr1[j].trim());
                        }
                        ArrayAdapter dataAdapter_credit = new ArrayAdapter(  AmRX.this,android.R.layout.simple_spinner_item, list);
                        dataAdapter_credit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        credit.setAdapter(dataAdapter);





                        int cust_type_with_note_length=cust_type_with_note.length();
                        String cust_address = s.toString();
                        String customer_address = cust_address.substring(0,total_string-cust_type_with_note_length);

                        String doc[] = customer_address.split("//");
                        String emp_name=doc[0].trim();
                        String emp_code = doc[1].trim();

                        String market[] = emp_code .split("-");
                        String emp_code1=market[0].trim();
                        String mar_name = market[1].trim();

                        String mar[] = mar_name.split(":");
                        String mar_name1=mar[0].trim();
                        actv2.setText(emp_name);   ///doctorname


                        visitorcode.setText(emp_code1);




                        Log.e("emp code: ", "> " +emp_code1);

                        cust_type=arr1[0];

                        if (cust_type.equals("CASH")) {
                            cash_party=1;
                            credit_party=0;
                            credit=(Spinner)findViewById(R.id.credit);
                            cashcredit=(Spinner)findViewById(R.id.cashcredit);
                            View credit_view=(Spinner)findViewById(R.id.credit);
                            View cashcredit_view=(Spinner)findViewById(R.id.cashcredit);
                            credit_view.setVisibility(View.GONE);
                            cashcredit_view.setVisibility(View.GONE);
                            // cashcredit_view.setVisibility(View.VISIBLE);
                            Log.e("cashcredit_view: ", "> " + cashcredit_view);





                           // s_time.setFocusable(true);
                           // s_time.setFocusableInTouchMode(true);
                           // s_time.requestFocus();

                        }else{

                            credit=(Spinner)findViewById(R.id.credit);
                            cashcredit=(Spinner)findViewById(R.id.cashcredit);
                            View credit_view=(Spinner)findViewById(R.id.credit);
                            View cashcredit_view=(Spinner)findViewById(R.id.cashcredit);
                            cashcredit_view.setVisibility(View.GONE);
                            credit_view.setVisibility(View.VISIBLE);
                            credit_view.setVisibility(View.GONE);
                            cash_party=0;
                            credit_party=1;

                           // s_time.setFocusable(true);
                           // s_time.setFocusableInTouchMode(true);
                           // s_time.requestFocus();

                        }

                    }else{
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
                    int total_string=inputorder.length();

                    if(inputorder.indexOf(":") != -1){

                        String cust_type = inputorder.substring(inputorder.indexOf(":") + 1);
                        String cust_type_with_note = inputorder.substring(inputorder.indexOf(":") + 0);


                        String arr[] = cust_type_with_note.split(":");
                        cust_type_with_note=arr[1].trim();

                        String chemist_first_index=arr[0].trim();

                        String arr1[] = cust_type_with_note.split("///");

                        Log.w("Selected date", "> " +cust_type+ chemist_first_index + cust_type_with_note );




                        int cust_type_with_note_length=cust_type_with_note.length();
                        String cust_address = s.toString();
                        String customer_address = cust_address.substring(0,total_string-cust_type_with_note_length);

                        String doc[] = customer_address.split("//");
                        String doc_name=doc[0].trim();
                        String doc_code = doc[1].trim();

                        String market[] = doc_code .split("-");
                        String doc_code1=market[0].trim();
                        String mar_name = market[1].trim();

                        String mar[] = mar_name.split(":");
                        String mar_name1=mar[0].trim();


                        actv3.setText(doc_name);   ///doctorname
                        doccode.setText(doc_code1); //doctorcode
                        marketcode.setText(mar_name1); //marketname
                        CHEM_FLAG="Y";

                        cust_type=arr1[0];

                       // actv2.setFocusable(true);
                       // actv2.setFocusableInTouchMode(true);
                       // actv2.requestFocus();


                    }else{
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

                    final   String remarksvalue = remarks.getText().toString();



                }

            }
        });



        comp_ana.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (actv2.getText().toString() != "") {

                    final   String comp_anavalue = comp_ana.getText().toString();


                }

            }
        });





        /*===============================================User show===================================================*/
        final TextView myTextView = (TextView) findViewById(R.id.user_show);

        final TextView newversion = (TextView) findViewById(R.id.newversion);
        Bundle b = getIntent().getExtras();
        //String ordernumber=b.getString("Ord_NO");
        name= b.getString("UserName_1");
        ename= b.getString("UserName_2");
        newversion_text= b.getString("new_version");

        newversion.setText("RX Entry");

        //DatabaseHelper db = new DatabaseHelper(this);
        //db=new DatabaseHandler(  Dcr.this);
        //db.addTerritory(ename);

        //   db=new DatabaseHandler(this);

        if(b.isEmpty()){

            String userName="";
            myTextView.setText(userName);

        }
        else {


            String userName = b.getString("UserName");
            String UserName_2 = b.getString("UserName_2");


            String ordernumber = b.getString("Ord_NO");

            myTextView.setText(UserName_2 + "(" + userName + ")");

            //	myTextView.setText(userName);


            if (ordernumber == null) {
                mainlayout.setVisibility(LinearLayout.GONE);
            } else {
                //myTextView.setText(userName);
                String UserName_3 = b.getString("UserName_2");


                if (UserName_3.equals("RX Submitted")) {

                    ordno.setText( ordernumber);
                    ordno.setTextSize(14);

                }


                ordno.setText("RX Sl No." + ordernumber);
                ordno.setTextSize(20);

                succ_msg.setText("Submitted");

                String mpo[] = ordernumber.split("-");

                // myTextView.setText(mpo[0])

                if (UserName_2.equals("RX Submitted")) {
                    myTextView.setText(mpo[0]);
                    myTextView.setTextSize(16);

                } else {
                    myTextView.setText(  mpo[0]  );
                    myTextView.setTextSize(12);
                }



            }
        }


        /*




         */

        final  Calendar cal = Calendar.getInstance();
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
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat,Locale.getDefault());
                error_dt.setText("" );
                //note.setText("" );
                //  ded.setTextSize(14);
                ded.setTextColor(Color.BLACK);
                ded.setText(sdf.format(myCalendar.getTime()));
                Log.w("Selected date", "> " +  ded.getText().toString());



            }

        };

        ded.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(  AmRX.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();



            }
        });






        dcr_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Log.v("Type---------------", "> " + dcr_spinner.getSelectedItem().toString());


                dcr_code = dcr_spinner.getSelectedItem().toString();
                dt_code=dcr_code.substring(0, 1);

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

                if (dcr_code.equals("Regular")){


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
                       // shift_spinner.requestFocus();

                    }
                    else{

                        location.setVisibility(View.VISIBLE);
                        location.setFocusable(true);
                        location.setFocusableInTouchMode(true);
                       // location.requestFocus();

                    }
                    actv.setEnabled(true);
                    error_dt.setText("");
                    actv.setVisibility(View.VISIBLE);
                    actv3.setVisibility(View.VISIBLE);

                }

                else if(dcr_code.equals("Journey") || dcr_code.equals("Conference") || dcr_code.equals("Training") || dcr_code.equals("Others") || dcr_code.equals("Meeting")
                        || dcr_code.equals("Holiday") || dcr_code.equals("Leave"))  {





                    if (db.checkOrdNo()) {

                        location.setVisibility(View.GONE);
                        shift_spinner.setFocusable(true);
                        shift_spinner.setFocusableInTouchMode(true);
                        shift_spinner.requestFocus();

                    }
                    else{
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

                }



                else if (dcr_code.equals("RX")){

                    dt_code="X";

                    new RXGetDoctors().execute();
                    actv4.setVisibility(view.VISIBLE);
                    actv4.setEnabled(true);
                    actv4.setClickable(true);
                    actv4.setFocusable(true);



                    actv4.setFocusable(true);
                    actv4.setFocusableInTouchMode(true);
                    actv4.requestFocus();





                    if (db.checkOrdNo()) {

                        location.setVisibility(View.GONE);
                        actv4.setVisibility(view.VISIBLE);
                        actv4.setEnabled(true);
                        actv4.setClickable(true);
                        actv4.setFocusable(true);



                        actv4.setFocusable(true);
                        actv4.setFocusableInTouchMode(true);
                        actv4.requestFocus();

                    }
                    else{
                        actv4.setVisibility(view.VISIBLE);
                        actv4.setEnabled(true);
                        actv4.setClickable(true);
                        actv4.setFocusable(true);



                        actv4.setFocusable(true);
                        actv4.setFocusableInTouchMode(true);
                        actv4.requestFocus();
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

                Log.w("location----", "> " + location.getSelectedItem().toString());


                location_code = location.getSelectedItem().toString();

                loc_code = location_code.substring(0, 1).toString();

                Log.e("loccode", "> " + loc_code);
                final String check =String.valueOf(loc_code);
                Log.e("checkloccode", "> " + check);

                if(dt_code.equals("X")){

                    no_of_rx.setFocusable(true);
                    no_of_rx.setFocusableInTouchMode(true);
                    no_of_rx.requestFocus();

                }


                else{


                }






            }

            public void onNothingSelected(AdapterView<?> adapterView) {

                return;
            }
        });


/*

        v_location.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Log.w("vlocation----", "> " + v_location.getSelectedItem().toString());


                v_location_code = v_location.getSelectedItem().toString();

                v_loc_code = v_location_code.substring(0, 1).toString();

                Log.e("vloccode", "> " + v_loc_code);
                vcheckloccode =String.valueOf(v_loc_code);
                Log.e("vcheckloccode", "> " + vcheckloccode);


                actv4.setFocusable(true);

                actv4.setFocusable(true);
                actv4.setFocusableInTouchMode(true);
                actv4.requestFocus();

             //   no_of_rx.setFocusable(true);
              //  no_of_rx.setFocusableInTouchMode(true);
              //  no_of_rx.requestFocus();



            }

            public void onNothingSelected(AdapterView<?> adapterView) {

                return;
            }
        });

*/

        s_time.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;

                mTimePicker = new TimePickerDialog(AmRX.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        s_time.setText( selectedHour + ":" + selectedMinute);
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
                mTimePicker = new TimePickerDialog(AmRX.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        e_time.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

                //  location.setFocusable(true);
                //  location.setFocusableInTouchMode(true);
                //  location.requestFocus();
                Log.w(" am_pm_check ", "> " +s_time.getText().toString());

                String check_shift = s_time.getText().toString();

                String arr[] = check_shift.split(":");
                String  day_shift =arr[0].trim();
                int shift_check = Integer.parseInt(day_shift);
                Log.w("day_shift", "> " +day_shift);


            }
        });


        /*=============================================Update Sells=============================================*/

        session = new SessionManager(getApplicationContext());



        shift_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                shift_status = shift_spinner.getSelectedItem().toString();

                if (dcr_code.equals("Regular")  ){


                    Log.e("Regular", "> " + dt_code);


                    if (shift_spinner.getSelectedItem().toString().equals("AM") || shift_spinner.getSelectedItem().toString().equals("PM") ) {

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


                    else{

                        Log.e("else is Regular", "> " + shift_spinner.getSelectedItem().toString());

                    }


                }
                else if(dcr_code.equals("Journey") || dcr_code.equals("Conference") || dcr_code.equals("Training") || dcr_code.equals("Others") || dcr_code.equals("Meeting")
                        || dcr_code.equals("Holiday") || dcr_code.equals("Leave")
                        )  {

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
                Toast.makeText(AmRX.this,"Please Select Shift !!", Toast.LENGTH_LONG).show();
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
                // Log.v("Visit to", "> " + chemordoc.getSelectedItem().toString());



                if (chemordoc.getSelectedItem().toString().equals("Doctor")){



                    Log.v("Visit to =>>>", "> " + chemordoc.getSelectedItem().toString());


                    new GetCategories().execute();



                    chemist_ppm.setVisibility(View.GONE);
                    //  shift_spinner.setEnabled(false);
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



                }

                else if (chemordoc.getSelectedItem().toString().equals("Chemist")) {

                    actv3.setFocusable(true);
                    actv3.setFocusableInTouchMode(true);
                    actv3.requestFocus();
                    // shift_spinner.setEnabled(false);

                    new GetChemist().execute();
                    Log.v("Visit to =>>>", "> " + chemordoc.getSelectedItem().toString());

                    yes_no.setVisibility(View.VISIBLE);
                    yes_no.setFocusable(true);
                    yes_no.setClickable(true);
                    yes_no.setFocusableInTouchMode(true);
                    yes_no.requestFocus();

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
                Log.v("Visit with PM?", "> " + yes_no.getSelectedItem().toString());

                yes_no_val = yes_no.getSelectedItem().toString();


                if (yes_no.getSelectedItem().toString().equals("Yes")){



                    Log.v("value", "> " + yes_no.getSelectedItem().toString());

                    dcr_submit.setPressed(false);
                    dcr_submit.setClickable(false);


                    next.setEnabled(true);
                    next.setPressed(true);
                    next.setClickable(true);

                    actv.setFocusable(true);
                    actv.setFocusableInTouchMode(true);
                    actv.requestFocus();

                }

                else if (yes_no.getSelectedItem().toString().equals("No")) {



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


        /*=============================================Log out Button Event click===================================================================*/
        back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Thread mysells = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub


                        // toast.show();
                        Bundle b = getIntent().getExtras();
                        String userName = b.getString("UserName");
                        Intent i = new Intent(AmRX.this, com.opl.pharmavector.AmDashboard.class);
                        String user=myTextView.getText().toString();
                        i.putExtra("UserName", userName);

                        startActivity(i);



                    }
                });
                mysells.start();

            }
        });


        /*=============================================Log out Button Event click===================================================================*/


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
        /*============================================= End Log out Button Event click===================================================================*/



/*
        prod_of_opsonin.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

                if (prod_of_opsonin.getText().toString().trim().equals("")){

                    prod_of_opsonin.setText("0");


                }

                else{

                    int a = Integer.parseInt(prod_of_opsonin.getText().toString().trim());
                    int b = Integer.parseInt(no_of_prod.getText().toString().trim());
                    double result = a / b;
                    String total2 = Double.toString(result);
                    ratio_of_opso.setText(total2);
                }


            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {

            }
        });

*/













        /*============================================  Chemist ppm next button   ================================================*/



        chemist_ppm.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {


                Bundle f = getIntent().getExtras();
                userName = f.getString("UserName");
                Log.w("Mpo Code", "> " + userName);
                String str = ded.getText().toString();
                String date_1 = str.replaceAll("[^\\d.-]", "");
                final String ord_no = userName + "-" + date_1;

                Toast.makeText(getApplicationContext(), "HEMISTbutton is clicked"  , Toast.LENGTH_LONG).show();

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

                if ((ded.getText().toString().trim().equals("")) || (ded.getText().toString().trim().equals("Reference Date")) || (ded.getText().toString().trim().equals("Please Select date"))) {

                    ded.setTextSize(14);
                    ded.setText("Please Select date");
                    ded.setTextColor(Color.RED);
                } else if ((actv.getText().toString().trim().equals("")) || (actv.getText().toString().trim().equals("Input Customer (eg. dh..)"))) {


                    actv.setError("Doctor  not Assigned !");
                    actv.setText("Please insert  Doctor Name ");
                    actv.setTextColor(Color.RED);

                } else if (totalday_given > totalday_valid) {

                    error_dt.setText("Delivery Date  is not greater then current date!");

                } else if (totalday_given < totalday_valid2) {


                    error_dt.setText("Previous date can not be more then 7 days.. ");
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
                Log.e("VISIT_DATE ---", "> " +ded.getText().toString());
                Log.e("Chemist Code", "> " +doccode.getText().toString());


                //   autoCompleteTextView3



                Thread next = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //   Intent in = new Intent(  Dcr.this, ProductOrder.class);


                        Intent in = new Intent(AmRX.this, ChemistGiftOrder.class);

                        Bundle extras = new Bundle();


                        String str = ded.getText().toString();
                        String date_1 = str.replaceAll("[^\\d.-]", "");

                        final String generated_ord_no = userName + "-" + date_1;

                        extras.putString("MPO_CODE",  userName);
                        extras.putString("CUST_CODE", userName);

                        //  extras.putString("AM_PM", shift_status);
                        extras.putString("AM_PM",  shift_spinner.getSelectedItem().toString());

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
                        extras.putString("VISIT_DATE",ded.getText().toString());
//     Log.e("Chemist Code", "> " +doccode.getText().toString());
                        extras.putString("CHEM_CODE",doccode.getText().toString());

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



        /*============================================        Chemist ppm next button       ================================================*/




        /*============================================        rx_page button       ================================================*/



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

                if ( (dcr_code.equals("Regular"))  ) {

                    error_dt.setTextColor(Color.RED);
                    error_dt.setText("Select DCR Type 'RX' .Click on Dcr Type to select 'RX' ");

                }
               else if ( (actv4.getText().toString().equals("Select Doctor for RX (Type 00 or doc name)")) ||  (actv4.getText().toString().equals(""))     ) {

                    error_dt.setTextColor(Color.RED);
                    error_dt.setText("Please Select Doctor Name ");

                }

                else if(dcr_code.equals("Journey") || dcr_code.equals("Conference") || dcr_code.equals("Training") || dcr_code.equals("Others") || dcr_code.equals("Meeting")
                        || dcr_code.equals("Holiday") || dcr_code.equals("Leave")){

                    error_dt.setTextColor(Color.RED);
                    error_dt.setText("Select DCR Type 'RX' .Click on Dcr Type to select 'RX' ");




                }




                else if ( no_of_rx.getText().toString().trim().equals("") || (no_of_rx.getText().toString().trim().equals("No Of Rx"))   )  {

                    no_of_rx.setTextSize(14);
                    no_of_rx.setText("Please Select No of Rx");
                    no_of_rx.setTextColor(Color.RED);

                }

                else if ( no_of_prod.getText().toString().trim().equals("") || (no_of_prod.getText().toString().trim().equals("No Of Products")  ||

                        (no_of_prod.getText().toString().trim().equals("Please Select No of Products"))
                )   )  {

                    no_of_prod.setTextSize(14);
                    no_of_prod.setText("Please Select No of Products");
                    no_of_prod.setTextColor(Color.RED);

                }


                else if ( prod_of_opsonin.getText().toString().trim().equals("") || (prod_of_opsonin.getText().toString().trim().equals("No Of Opsonin Products") ||

                        (prod_of_opsonin.getText().toString().trim().equals("Please Select No of Opsonin Products")
                        ) )  )  {

                    prod_of_opsonin.setTextSize(14);
                    prod_of_opsonin.setText("Please Select No of Opsonin Products");
                    prod_of_opsonin.setTextColor(Color.RED);

                }






                else if (totalday_given > totalday_valid) {

                    error_dt.setText("Delivery Date  is not greater then current date!");

                } else if (totalday_given < totalday_valid2) {


                    error_dt.setText("Previous date can not be more then 7 days.. ");
                }

                else{
                    final Spinner nameSpinner = (Spinner) findViewById(R.id.customer);
                    final String selected_cust = actv.getText().toString();


                    // final String select_party1 = select_party.toString();
                    Bundle b = getIntent().getExtras();
                    final String userName = b.getString("UserName");
                    String UserName_1 = b.getString("UserName_1");


                    Log.e("mpo_code", "> " + actv.getText().toString());
                    Log.e("Mpo Code", "> " + userName);
                    Log.e("doc code", "> " + doccode.getText().toString());

                    Log.e("VISIT_DATE ---", "> " +ded.getText().toString());

                    Log.e("$vcheckloccode ---", "> " +vcheckloccode);
                    Log.e("no_of_rx ---", "> " +no_of_rx.getText().toString());
                    Log.e("no_of_prod ---", "> " +no_of_prod.getText().toString());
                    Log.e("prod_of_opsonin ---", "> " +prod_of_opsonin.getText().toString());



                    Thread next = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //   Intent in = new Intent(  Dcr.this, ProductOrder.class);


                            Intent in = new Intent(AmRX.this, AmRxCompany.class);

                            Bundle extras = new Bundle();


                            String str = ded.getText().toString();
                            String date_1 = str.replaceAll("[^\\d.-]", "");

                            final String generated_ord_no = userName + "-" + date_1;

                            extras.putString("MPO_CODE",  userName);
                            extras.putString("CUST_CODE", userName);

                            //  extras.putString("AM_PM", shift_status);
                            extras.putString("AM_PM",  shift_spinner.getSelectedItem().toString());

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
                            extras.putString("VISIT_DATE",ded.getText().toString());
                            extras.putString("vcheckloccode",vcheckloccode);

                            extras.putString("no_rx", no_of_rx.getText().toString());
                            extras.putString("no_prod", no_of_prod.getText().toString());
                            extras.putString("prod_of_opsonin",prod_of_opsonin.getText().toString());


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






        /*============================================       rx_page button         ================================================*/























        /*=============================================   start  dcr submit button   ===================================================================*/

        dcr_submit.setOnClickListener(new OnClickListener() {


            public void onClick(View v) {

                Bundle b = getIntent().getExtras();
                userName = b.getString("UserName");
                Log.w("Mpo Code", "> " + userName);
                String str = ded.getText().toString();
                String date_1 = str.replaceAll("[^\\d.-]", "");
                final String ord_no = userName + "-" + date_1;


                Log.w("dcr_submit order no", "> " + ord_no);
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



                if ((ded.getText().toString().trim().equals("")) || (ded.getText().toString().trim().equals("Reference Date")) || (ded.getText().toString().trim().equals("Please Select date"))) {

                    ded.setTextSize(14);
                    ded.setText("Please Select date");
                    ded.setTextColor(Color.RED);
                } else if (totalday_given > totalday_valid) {

                    error_dt.setText("Select Todays Date");

                }

                else if (dcr_code.equals("RX")) {

                    error_dt.setText("Click  'Other Company Products' Button");

                }



                else if (totalday_given < totalday_valid2) {
                    error_dt.setText("Select Todays Date");
                }

                else if(dcr_spinner.getSelectedItem().toString().equals("Select Dcr Type ")){
                    error_dt.setText("Select DCR Type");
                    dcr_spinner.setPrompt("Select DCR Type");

                }



                else if(dcr_spinner.getSelectedItem().toString().equals("Select Dcr Type ")){
                    error_dt.setText("Select DCR Type");
                    dcr_spinner.setPrompt("Select DCR Type");

                }


                else {

                    Thread server = new Thread(new Runnable() {

                        @Override
                        public void run() {


                            JSONParser jsonParser = new JSONParser();
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
                            Log.e("VISIT_DATE", "> " +ded.getText().toString());
                            Log.e("visit with ", "> " +spinner.getSelectedItem().toString());
                            Log.e("COMPETITOR_ANALYSIS",comp_ana.getText().toString());
                            Log.e("remarks_value",remarks.getText().toString() );
                            Log.e("yes_no_val",yes_no_val );
                            Log.e("shift", "> " + shift_spinner.getSelectedItem().toString());
                            Log.e("CHEM_FLAG", "> " +CHEM_FLAG);

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
                            Intent sameint = new Intent(AmRX.this, AmRX.class);
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

                int  myNum = Integer.parseInt(date_ext.getText().toString());

                int totalday_valid2 = cmonth_day + cDay - myNum;



                if ((ded.getText().toString().trim().equals("")) || (ded.getText().toString().trim().equals("Reference Date")) || (ded.getText().toString().trim().equals("Please Select date"))) {

                    ded.setTextSize(14);
                    ded.setText("Please Select date");
                    ded.setTextColor(Color.RED);
                }

                else if(totalday_given < totalday_valid2){
                    ded.setError( "Delivery Date  is not less " + myNum  + "  than days" );
                    error_dt.setText("Delivery Date  is not less " + myNum  +" than  days " );
                }

                else if ((actv.getText().toString().trim().equals("")) || (actv.getText().toString().trim().equals("Input Customer (eg. dh..)"))) {


                    actv.setError("Doctor  not Assigned !");
                    actv.setText("Please insert  Doctor Name ");
                    actv.setTextColor(Color.RED);

                } else if (totalday_given > totalday_valid) {


                    // ded.setError( "Delivery Date  is not more than 6 days" );
                    error_dt.setText("Delivery Date  is not greater then current date!");

                } else if (totalday_given < totalday_valid2) {


                    error_dt.setText("Previous date can not be more then 7 days.. ");
                }


                else if ((comp_ana.getText().toString().equals("Competitors activity analysis"))) {

                    comp_ana.getText().toString().equals("");
                    com_ana_val = "";

                }


                else {

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

                        com_ana_val= comp_ana.getText().toString();

                        final String select_party1 = select_party.toString();
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
                        Log.e("VISITOR_CODE", "> " + visitorcode.getText().toString());
                        Log.e("VISIT_DATE ", "> " +ded.getText().toString());

                        //  Log.e("COMPETITOR_ANALYSIS", "> " + com_ana_val);
                        Log.e("REMARKS ", "> " +remarks.getText().toString());

                        Log.e("VISIT_WITH ", "> " +spinner.getSelectedItem().toString());

                        //   params.add(new BasicNameValuePair("VISIT_DATE", ded.getText().toString()));
                        Thread next = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                //   Intent in = new Intent(  Dcr.this, ProductOrder.class);


                                Intent in = new Intent(AmRX.this, GiftOrder.class);

                                Bundle extras = new Bundle();


                                String str = ded.getText().toString();
                                String date_1 = str.replaceAll("[^\\d.-]", "");

                                final String generated_ord_no = userName + "-" + date_1;

                                extras.putString("MPO_CODE",  userName);
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
                                extras.putString("VISIT_DATE",ded.getText().toString());
                                extras.putString("REMARKS",remarks.getText().toString());
                                extras.putString("COMPETITOR_ANALYSIS",com_ana_val);
                                extras.putString("VISIT_WITH",spinner.getSelectedItem().toString());

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


                //  }

            }   // end else //
        });

        /*============================================= End  Next Button for doctors ppm ==========================================================================*/

    }























    /*============================================= Populate Spinner for Doctors==========================================================================*/



    private void populateSpinner() {


        List<String> lables = new ArrayList<String>();
        for (int i = 0; i <customerlist.size(); i++) {
            lables.add(customerlist.get(i).getName());

        }

        // Creating adapter for spinner
        //   ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, lables);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, lables);
        cust.setAdapter(spinnerAdapter);

        String[] customer = lables.toArray(new String[lables.size()]);
        // ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item, customer);

        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, customer);

        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);




        actv.setThreshold(2);
        actv.setAdapter(Adapter);
        actv.setTextColor(Color.BLUE);
    }


    /*============================================= End  Populate Spinner for Doctors==========================================================================*/


    private void rxpopulateSpinner() {


        List<String> lables = new ArrayList<String>();
        for (int i = 0; i <customerlist.size(); i++) {
            lables.add(customerlist.get(i).getName());

        }

        // Creating adapter for spinner
        //   ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, lables);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, lables);
        cust.setAdapter(spinnerAdapter);

        String[] customer = lables.toArray(new String[lables.size()]);
        // ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item, customer);

        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, customer);

        AutoCompleteTextView actv4 = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView4);




        actv4.setThreshold(2);
        actv4.setAdapter(Adapter);
        actv4.setTextColor(Color.BLUE);
    }




    /*============================================= Populate Spinner for Visitors ==========================================================================*/


    private void  populateSpinner2() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i <visitorlist.size(); i++) {
            lables.add(visitorlist.get(i).getName());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, lables);
        visitor.setAdapter(spinnerAdapter);


        String[] customer = lables.toArray(new String[lables.size()]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, customer);
        AutoCompleteTextView actv2 = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView2);
        actv2.setThreshold(2);
        actv2.setAdapter(Adapter);
        actv2.setTextColor(Color.BLUE);

    }









    private void  populateSpinnerShift() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i <shiftlist.size(); i++) {
            lables.add(shiftlist.get(i).getName());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, lables);
        shift_spinner.setAdapter(spinnerAdapter);



    }





    private void  populatedcr_date_extend() {
        List<String> lables = new ArrayList<String>();

        for (int i = 0; i <dateextendlist.size(); i++) {
            get_ext_dt= dateextendlist.get(i).getName();
        }





        date_ext.setText(get_ext_dt);

        int  myNum = Integer.parseInt(date_ext.getText().toString());

        Log.w("showvalue2", String.valueOf(myNum));

        if(myNum>0 ){

            ded.setEnabled(true);
        }

        else{
            ded.setEnabled(false);

        }
    }














    /*=============================================     End Populate Spinner for Visitors ==========================================================================*/



    private void  populateSpinner3() {


        List<String> lables = new ArrayList<String>();
        for (int i = 0; i <chemistlist.size(); i++) {
            lables.add(chemistlist.get(i).getName());

        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, lables);
        chemist.setAdapter(spinnerAdapter);

        String[] customer = lables.toArray(new String[lables.size()]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, customer);
        AutoCompleteTextView actv3 = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView3);




        actv3.setThreshold(2);
        actv3.setAdapter(Adapter);
        actv3.setTextColor(Color.RED);
    }








    /*=============================================        Asnyc Task FOR DOCTORS         ==========================================================================*/



    class GetCategories extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(  AmRX.this);
            pDialog.setMessage("Fetching Doctors..");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {


            // ServiceHandler jsonParser = new ServiceHandler();
            // String json = jsonParser.makeServiceCall(URL_CUSOTMER,ServiceHandler.GET);

            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");

            String id=userName;

            //  Log.v("postid: ", "> " + id);
            //  Log.v("postshift: ", "> " + shift_spinner.getSelectedItem().toString());

            List<NameValuePair>params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id",id));
            params.add(new BasicNameValuePair("shift",shift_spinner.getSelectedItem().toString()));
            ServiceHandler jsonParser=new ServiceHandler();
            String json=jsonParser.makeServiceCall(URL_CUSOTMER, ServiceHandler.POST, params);

            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray customer = jsonObj.getJSONArray("customer");
                        for (int i = 0; i < customer.length(); i++) {
                            JSONObject catObj = (JSONObject) customer.get(i);



                            com.opl.pharmavector.AmCustomer custo = new com.opl.pharmavector.AmCustomer(catObj.getInt("id"),catObj.getString("name"));
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


    class RXGetDoctors extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(  AmRX.this);
            pDialog.setMessage("Fetching Doctors..");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {


            // ServiceHandler jsonParser = new ServiceHandler();
            // String json = jsonParser.makeServiceCall(URL_CUSOTMER,ServiceHandler.GET);

            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");

            String id=userName;

            //  Log.v("postid: ", "> " + id);
            //  Log.v("postshift: ", "> " + shift_spinner.getSelectedItem().toString());

            List<NameValuePair>params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id",id));
            params.add(new BasicNameValuePair("shift",shift_spinner.getSelectedItem().toString()));
            ServiceHandler jsonParser=new ServiceHandler();
            String json=jsonParser.makeServiceCall(URL_CUSOTMER, ServiceHandler.POST, params);

            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray customer = jsonObj.getJSONArray("customer");
                        for (int i = 0; i < customer.length(); i++) {
                            JSONObject catObj = (JSONObject) customer.get(i);



                            com.opl.pharmavector.AmCustomer custo = new com.opl.pharmavector.AmCustomer(catObj.getInt("id"),catObj.getString("name"));
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
    /*=============================================        Asnyc Task FOR Visitors      ==========================================================================*/




    class GetEmp extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog2 = new ProgressDialog(AmRX.this);
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
            String id=userName;

            List<NameValuePair>params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id",id));
            ServiceHandler jsonParser=new ServiceHandler();
            // String json=jsonParser.makeServiceCall(URL_CUSOTMER, ServiceHandler.POST, params);

            String json=jsonParser.makeServiceCall(URL_EMP, ServiceHandler.POST, params);

            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray customer = jsonObj.getJSONArray("customer");
                        for (int i = 0; i < customer.length(); i++) {
                            JSONObject catObj = (JSONObject) customer.get(i);
                            com.opl.pharmavector.AmCustomer custo = new com.opl.pharmavector.AmCustomer(catObj.getInt("id"),catObj.getString("name"));
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
            String id=userName;

            List<NameValuePair>params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id",id));
            ServiceHandler jsonParser=new ServiceHandler();


            String json=jsonParser.makeServiceCall(date_range_permission, ServiceHandler.POST, params);

            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray customer = jsonObj.getJSONArray("customer");
                        for (int i = 0; i <= customer.length(); i++) {
                            JSONObject catObj = (JSONObject) customer.get(i);
                            com.opl.pharmavector.AmCustomer custo = new com.opl.pharmavector.AmCustomer(catObj.getInt("id"),catObj.getString("name"));

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
            String id=userName;

            List<NameValuePair>params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id",id));
            ServiceHandler jsonParser=new ServiceHandler();
            // String json=jsonParser.makeServiceCall(URL_CUSOTMER, ServiceHandler.POST, params);

            String json=jsonParser.makeServiceCall(URL_SHIFT, ServiceHandler.POST, params);

            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray customer = jsonObj.getJSONArray("customer");
                        for (int i = 0; i <= customer.length(); i++) {
                            JSONObject catObj = (JSONObject) customer.get(i);
                            com.opl.pharmavector.AmCustomer custo = new com.opl.pharmavector.AmCustomer(catObj.getInt("id"),catObj.getString("name"));
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
            pDialog = new ProgressDialog(AmRX.this);
            pDialog.setMessage("Fetching Chemist ...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {


            // ServiceHandler jsonParser = new ServiceHandler();
            // String json = jsonParser.makeServiceCall(URL_CUSOTMER,ServiceHandler.GET);

            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            String id=userName;
            //   Log.v("postshift: ", "> " + shift_spinner.getSelectedItem().toString());

            List<NameValuePair>params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id",id));
            params.add(new BasicNameValuePair("shift",shift_spinner.getSelectedItem().toString()));
            ServiceHandler jsonParser=new ServiceHandler();
            String json=jsonParser.makeServiceCall(URL_CHEMIST, ServiceHandler.POST, params);

            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray customer = jsonObj.getJSONArray("customer");
                        for (int i = 0; i < customer.length(); i++) {
                            JSONObject catObj = (JSONObject) customer.get(i);
                            com.opl.pharmavector.AmCustomer custo = new com.opl.pharmavector.AmCustomer(catObj.getInt("id"),catObj.getString("name"));
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



            List<NameValuePair>params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id",id));




            ServiceHandler jsonParser=new ServiceHandler();
            String jsonStr2=jsonParser.makeServiceCall(get_dcr_date, ServiceHandler.POST, params);

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
                        String  mpo = c.getString("mpo");

                        HashMap<String, String> customer = new HashMap<>();



                        customer.put("id", cust_id );
                        customer.put("name", cust_name);
                        customer.put("cust", cust);
                        customer.put("mpo", mpo);


                        dcrdatelist.add(customer);
                        db.addDcrDate(new DcrDate(cust,cust_name));



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
        Intent intent = new Intent(  AmRX.this, Login.class);
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
