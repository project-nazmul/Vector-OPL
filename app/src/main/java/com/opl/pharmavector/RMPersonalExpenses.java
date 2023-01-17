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
public class  RMPersonalExpenses  extends Activity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner1, spinner2,cashcredit,cashcredit_test,credit;
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_MESSAGE_1 = "message_1";
    public static final String TAG_invoice = "invoice";
    public static final String TAG_target = "target";
    public static final String TAG_achivement = "achivement";
    public static final String TAG_MESSAGE_new_version = "new_version";
    private Button logout,achivbtn;
    private TextView user_show,newversion;
    private SessionManager session;
    public Button next, back;
    public static String name=null,newversion_text=null,ename=null;
    public int credit_party=0,cash_party=0;
    SharedPreferences.Editor editor;
    public EditText osi, op, od, dateResult, ref;
    // private ListView cust;
    private ArrayList<Customer> customerlist;
    private ArrayList<Customer> mpodcrlist;

    private ArrayList<Customer> shiftlist;

    public Array pay_cash;
    public Array pay_cradit;
    public String  saved_da_val;
    private DatabaseHandler db;
    private String f_name,s_name;
    private Button mOffline;
    private ListView lv;
    private Contact dataModel;
    Toast toast;
    Toast toast2;
    Toast toast3;
    public  String Tour_nature_code;
    public String dcr_sl_no;
    public String pay_cash1,userName,userName_1,userName_2;
    private Spinner shift_spinner;
    public stirng pay_credit1;
    public  String jour_code=null;
    public  String pass_dcr_date;
    private Spinner cust;
    private Spinner dcrlist;
    ProgressDialog pDialog;
    EditText mEdit,hotel_fare,ta;
    TextView date2, ded,note,s_time,e_time,da,tournature,dcr_nummber,othertaval,particul,car;
    public int success;
    public String message, ord_no,invoice,target_data,achivement,searchString,select_party;

    private String URL_DCR = BASE_URL+"regional_manager_api/personal_expense/get_rm_dcr.php";
    private String URL_JOURMODE = BASE_URL+"regional_manager_api/personal_expense/get_jour_mode.php";
    private String submit_dcr_expense = BASE_URL+"regional_manager_api/personal_expense/submit_dcr_expense.php";
    private String  URL_PASS_DCR_DATE = BASE_URL+"regional_manager_api/personal_expense/URL_PASS_DCR_DATE.php";
    protected Handler handler;
    EditText diskm;
    @SuppressLint("CutPasteId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rmpersonalexpenses);
        setTitle("Personal Expenses");
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        logout = (Button) findViewById(R.id.logout);
        logout.setTypeface(fontFamily);
        logout.setText("\uf08b"); //&#xf08b
        user_show = (TextView) findViewById(R.id.user_show);
        newversion = (TextView) findViewById(R.id.newversion);

        setTitle("Personal Expense");

        next = (Button) findViewById(R.id.next);

        next.setTypeface(fontFamily);
        next.setText("\uf1d8");

        back = (Button) findViewById(R.id.view);

        back.setTypeface(fontFamily);
        back.setText("\uf060 ");

        final TextView error_dt=(TextView)findViewById(R.id.errordt);
        final TextView error_payment=(TextView)findViewById(R.id.errorpayment);
        op = (EditText) findViewById(R.id.orderpage);

        cust = (Spinner) findViewById(R.id.customer);
        dcrlist = (Spinner) findViewById(R.id.dcrlist);


        da = (TextView) findViewById(R.id.da);

        othertaval = (EditText) findViewById(R.id.othertaval);
        particul = (EditText) findViewById(R.id.particul);

        car = (TextView) findViewById(R.id.car);

        particul.setFilters(new InputFilter[] {
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start,
                                               int end, Spanned spanned, int dStart, int dEnd) {
                        // TODO Auto-generated method stub
                        if(cs.equals("")){ // for backspace
                            return cs;
                        }
                        if(cs.toString().matches("[a-zA-Z ]+")){
                            return cs;
                        }
                        return "";
                    }
                }
        });



        diskm =  findViewById(R.id.diskm);
        ta =  findViewById(R.id.ta);

        hotel_fare = (EditText) findViewById(R.id.hotel_fare);

        cust.setPrompt("Select Doctor");
        dcrlist.setPrompt("List of Dcr");


        ref = (EditText) findViewById(R.id.reference);
        ded = (TextView) findViewById(R.id.tourdate);

        tournature = (TextView) findViewById(R.id.tournature);

        dcr_nummber = (TextView) findViewById(R.id.dcr_nummber);


        s_time = (TextView) findViewById(R.id.starttime);
        e_time = (TextView) findViewById(R.id.endtime);

        TextView ordno=(TextView) findViewById(R.id.ordno);
        TextView succ_msg=(TextView) findViewById(R.id.succ_msg);

        note=(TextView) findViewById(R.id.note);
        LinearLayout mainlayout = (LinearLayout)findViewById(R.id.successmsg);
        final Spinner ampmspin=(Spinner)findViewById(R.id.ampm);


        cashcredit=(Spinner)findViewById(R.id.cashcredit);
        credit=(Spinner)findViewById(R.id.credit);
        cashcredit.setVisibility(View.GONE);
        credit.setVisibility(View.GONE);


        customerlist = new ArrayList<Customer>();
        cust.setOnItemSelectedListener(this);

        mpodcrlist = new ArrayList<Customer>();
        dcrlist.setOnItemSelectedListener(this);


        shiftlist = new ArrayList<Customer>();
        shift_spinner = (Spinner) findViewById(R.id.shift_spinner);
        /*================================================================get customer========================================*/
        new GetCategories().execute();
        new GetJourmode().execute();
        /*===========================================================customer end===============================================*/


        final   Spinner visitstatus = (Spinner) findViewById(R.id.visitstatus);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.visitstatus,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        visitstatus.setPrompt("Work Status");
        visitstatus.setAdapter(adapter);
        visitstatus.setOnItemSelectedListener(this);



        visitstatus.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String da_value_on_change = saved_da_val;
                if(visitstatus.getSelectedItem().toString().equals("Half Day")) {
                    da.setText("");
                    int da_val_cov = Integer.parseInt(da_value_on_change.toString());
                    int on_change_work_stat = da_val_cov/2;
                    String aString = Integer.toString(on_change_work_stat);
                    da.setText(aString);
                }

                else if(visitstatus.getSelectedItem().toString().equals("Full Day")) {
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


        actv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // hideKeyBoard();
                actv.showDropDown();
                return false;
            }
        });


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
        /*---------------------23.06.2016-----start--------------------------*/


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
                    int total_string=inputorder.length();

                    if(inputorder.indexOf(":") != -1){


                        //  String cust_type = inputorder.substring(inputorder.indexOf(":") + 1);
                        //  String cust_type_with_note = inputorder.substring(inputorder.indexOf(":") + 0);

                        String arr[] = inputorder.split(":");
                        String dcr_date_info_split= arr[0].trim();
                        String cust_type_with_note=arr[1].trim();
                        String arr1[] = cust_type_with_note.split("//");
                        Tour_nature_code= arr1[0].trim();
                        String dcr_expense_info_val=arr1[1].trim();


                        String arr0[] = dcr_date_info_split.split("//");
                        String dcr_date= arr0[0].trim();
                        String dcr_location_split=arr0[1].trim();



                        String arr3[] = dcr_location_split.split("/");
                        dcr_sl_no= arr3[0].trim();
                        pass_dcr_date=arr3[0].trim();
                        String dcr_location_name=arr3[1].trim();


                        actv.setText(dcr_date);
                        new GetDcrType().execute();

                        tournature.setTextColor(Color.BLUE);
                        tournature.setText(dcr_location_name);
                        dcr_nummber.setText(dcr_sl_no);




                        String arr4[] = dcr_expense_info_val.split("##");
                        String ta_value = arr4[0].trim();
                        String da_val= arr4[1].trim(); //DAVALUE


                        String get_ta[] = ta_value.split("/");
                        String gettavalue = get_ta[1].trim();




                        ta.setTextColor(Color.BLUE);
                        ta.setText(gettavalue);
                        String car0[] = da_val.split("&#");
                        String davalue= car0[0].trim();
                        da.setTextColor(Color.BLUE);
                        da.setText(davalue);
                        saved_da_val = car0[0].trim();

                        String car_stat= car0[1].trim(); //DAVALUE
                        car.setTextColor(Color.GREEN);
                        car.setText(car_stat);

                        if (Tour_nature_code.toString().trim().equals("1")  && car_stat.toString().equals("Y") ){

                            actv1.setText("CAR");
                            actv1.setEnabled(false);
                            actv1.setClickable(false);
                            jour_code="CR";
                            diskm.setEnabled(false);
                            ta.setEnabled(false);
                            othertaval.setVisibility(View.GONE);
                            particul.setVisibility(View.GONE);
                            othertaval.setEnabled(false);
                            particul.setEnabled(false);
                            hotel_fare.setVisibility(View.GONE);
                        }


                        else if (Tour_nature_code.toString().trim().equals("1")  && car_stat.toString().equals("N") ) {
                            actv1.setFocusable(true);
                            actv1.setFocusableInTouchMode(true);
                            actv1.requestFocus();
                            actv1.setEnabled(true);
                            actv1.setClickable(true);
                            diskm.setEnabled(false);
                            ta.setEnabled(false);
                            othertaval.setVisibility(View.GONE);
                            particul.setVisibility(View.GONE);
                            othertaval.setEnabled(false);
                            particul.setEnabled(false);
                            hotel_fare.setVisibility(View.GONE);

                        }


                        else if (Tour_nature_code.toString().trim().equals("2")  && car_stat.toString().equals("Y") ) {
                            actv1.setText("CAR");
                            actv1.setEnabled(false);
                            actv1.setClickable(false);
                            jour_code="CR";
                            diskm.setEnabled(false);
                            ta.setEnabled(false);
                            othertaval.setVisibility(View.VISIBLE);
                            particul.setVisibility(View.VISIBLE);
                            othertaval.setEnabled(true);
                            particul.setEnabled(true);
                            othertaval.setFocusable(true);
                            othertaval.setFocusableInTouchMode(true);
                            othertaval.requestFocus();
                            hotel_fare.setVisibility(View.GONE);

                        }

                        else if (Tour_nature_code.toString().trim().equals("2")  && car_stat.toString().equals("N") ) {

                            actv1.setFocusable(true);
                            actv1.setFocusableInTouchMode(true);
                            actv1.requestFocus();
                            actv1.setEnabled(true);
                            actv1.setClickable(true);
                            diskm.setEnabled(true);
                            ta.setEnabled(true);
                            othertaval.setVisibility(View.VISIBLE);
                            particul.setVisibility(View.VISIBLE);
                            othertaval.setEnabled(true);
                            particul.setEnabled(true);
                            hotel_fare.setVisibility(View.GONE);

                        }

                        else if (Tour_nature_code.toString().trim().equals("3")  && car_stat.toString().equals("Y") ) {

                            actv1.setText("CAR");
                            actv1.setEnabled(false);
                            actv1.setClickable(false);
                            jour_code="CR";
                            diskm.setEnabled(false);
                            ta.setEnabled(false);
                            othertaval.setVisibility(View.VISIBLE);
                            particul.setVisibility(View.VISIBLE);
                            othertaval.setEnabled(true);
                            particul.setEnabled(true);
                            othertaval.setFocusable(true);
                            othertaval.setFocusableInTouchMode(true);
                            othertaval.requestFocus();
                            hotel_fare.setVisibility(View.VISIBLE);
                            hotel_fare.setEnabled(true);
                        }


                        else if (Tour_nature_code.toString().trim().equals("3")  && car_stat.toString().equals("N") ) {

                            actv1.setFocusable(true);
                            actv1.setFocusableInTouchMode(true);
                            actv1.requestFocus();
                            actv1.setEnabled(true);
                            actv1.setClickable(true);

                            diskm.setEnabled(true);
                            ta.setEnabled(true);
                            othertaval.setVisibility(View.VISIBLE);
                            particul.setVisibility(View.VISIBLE);
                            othertaval.setEnabled(true);
                            particul.setEnabled(true);
                            hotel_fare.setVisibility(View.VISIBLE);
                            hotel_fare.setEnabled(true);
                        }


                        if(visitstatus.getSelectedItem().toString().equals("Half Day")) {

                            int da_val_cov = Integer.parseInt(davalue.toString());
                            int half_day_da = da_val_cov/2;
                            da.setTextColor(Color.BLUE);
                            da.setText(half_day_da);

                        }

                        else if(visitstatus.getSelectedItem().toString().equals("Full Day")) {
                            da.setTextColor(Color.BLUE);
                            da.setText(davalue);
                        }






                        cashcredit = (Spinner) findViewById(R.id.cashcredit);
                        credit = (Spinner) findViewById(R.id.credit);


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
                //actv.setTextColor(Color.BLACK);
            }

            @Override
            public void afterTextChanged(final Editable s) {
                // TODO Auto-generated method stub
                try {

                    final String inputorder = s.toString();
                    int total_string=inputorder.length();

                    if(inputorder.indexOf(":") != -1){


                        String cust_type = inputorder.substring(inputorder.indexOf(":") + 1);

                        // String dcr_info = inputorder.substring(inputorder.indexOf(":") + 0);
                        String cust_type_with_note = inputorder.substring(inputorder.indexOf(":") + 0);


                        String arr[] = cust_type_with_note.split(":");
                        cust_type_with_note=arr[1].trim();


                        String arr1[] = cust_type_with_note.split("///");
                        String jour_mode_split= arr1[0].trim();
                        // String dcr_expense_info_val=arr1[1].trim();


                        String jour_code_split[]= jour_mode_split.split("-");
                        String jour_name=jour_code_split[0].trim();
                        jour_code=jour_code_split[1].trim();
                        actv1.setText(jour_name);



                        if (( Tour_nature_code.toString().trim().equals("1") ) ){
                            diskm.setEnabled(false);
                        }

                        if (( Tour_nature_code.toString().trim().equals("2") ) || ( Tour_nature_code.toString().trim().equals("3") ) ){
                            diskm.setEnabled(true);
                            othertaval.setEnabled(true);
                            othertaval.setFocusable(true);
                            othertaval.setFocusableInTouchMode(true);
                            othertaval.requestFocus();
                            particul.setEnabled(true);
                        }



                        if (jour_code.toString().trim().equals("MC") || ( Tour_nature_code.toString().trim().equals("1"))){
                            ta.setEnabled(false);
                            next.setFocusable(true);
                            next.setFocusableInTouchMode(true);
                            next.requestFocus();

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


        final TextView myTextView = (TextView) findViewById(R.id.user_show);
        final TextView newversion = (TextView) findViewById(R.id.newversion);
        Bundle b = getIntent().getExtras();
        //String ordernumber=b.getString("Ord_NO");
        name= b.getString("UserName_1");
        ename= b.getString("UserName_2");
        newversion_text= b.getString("new_version");

        newversion.setText("Personal Expense Entry ");


        if(b.isEmpty()){

            String userName="";
            myTextView.setText(userName);

        }
        else {


            String userName = b.getString("UserName");
            String UserName_2= b.getString("UserName_2");




            myTextView.setText(UserName_2 + "(" + userName + ")");

            //	myTextView.setText(userName);
            String ordernumber=b.getString("Ord_NO");
            String invoice=b.getString("target");
            String tar=b.getString("invoice");
            String achivement=b.getString("achivement");
            if(ordernumber==null){
                mainlayout.setVisibility(LinearLayout.GONE);
            }
            else{


                ordno.setText("Ord No."+ordernumber);
                ordno.setTextSize(10);

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
                //String myFormat = "dd/MM/yyyy";
                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                error_dt.setText("" );
                //note.setText("" );
                //  ded.setTextSize(14);
                ded.setTextColor(Color.BLACK);
                ded.setText(sdf.format(myCalendar.getTime()));
            }

        };

        ded.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(  RMPersonalExpenses.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });







        /*====================================== distance in KM=========================================*/



        diskm.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

                if (diskm.getText().toString().trim().equals("")){
                    diskm.setText("0");
                }

                if (jour_code.toString().trim().equals("MC")){

                    int a = Integer.parseInt(diskm.getText().toString().trim());
                    double result = a * 2.5;
                    String total2 = Double.toString(result);
                    ta.setText(total2);
                }else{
                    ta.setEnabled(true);
                }


            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {

            }
        });

        /*====================================== distance in KM=========================================*/












        /*=============================================Update Sells=============================================*/

        session = new SessionManager(getApplicationContext());


        /*=============================================Log out Button Event click===================================================================*/


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



        /*  ====================================================================================== */

        back.setOnClickListener(new OnClickListener() {
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
                            Intent i = new Intent(RMPersonalExpenses.this,  RmDashboard.class);
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

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });

                backthred.start();
            }
        });

        /*  ====================================================================================== */




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

                if ((ded.getText().toString().trim().equals("")) || (ded.getText().toString().trim().equals("Reference Date")) || (ded.getText().toString().trim().equals("Please Select date"))) {

                    ded.setTextSize(14);
                    ded.setText("Please Select date");
                    ded.setTextColor(Color.RED);
                } else if (totalday_given > totalday_valid) {

                    error_dt.setText("Select Todays Date");

                }
                else if ( ( Tour_nature_code.toString().trim().equals("3")  &&  hotel_fare.getText().toString().trim().equals(""))) {
                    error_dt.setText("For Outstation Select Hotel Fare, Enter '0' if no Hotel fare");
                    hotel_fare.setFocusable(true);
                    hotel_fare.setFocusableInTouchMode(true);
                    hotel_fare.requestFocus();
                }


                else {

                    Thread server = new Thread(new Runnable() {

                        @Override
                        public void run() {

                            JSONParser jsonParser = new JSONParser();
                            List<NameValuePair> params = new ArrayList<NameValuePair>();

                            params.add(new BasicNameValuePair("ORD_NO", dcr_sl_no));
                            params.add(new BasicNameValuePair("MPO_CODE", userName));
                            params.add(new BasicNameValuePair("JOUR_CODE", jour_code));
                            params.add(new BasicNameValuePair("AM_PM", visitstatus.getSelectedItem().toString()  ));
                            params.add(new BasicNameValuePair("DISKM", diskm.getText().toString()  ));
                            params.add(new BasicNameValuePair("TA", ta.getText().toString()  ));
                            params.add(new BasicNameValuePair("DA", da.getText().toString()  ));
                            params.add(new BasicNameValuePair("Tour_nature_code", Tour_nature_code  ));
                            params.add(new BasicNameValuePair("PARTICUL", particul.getText().toString()  ));
                            params.add(new BasicNameValuePair("OTHERVAL", othertaval.getText().toString()  ));
                            params.add(new BasicNameValuePair("HOTELFARE", hotel_fare.getText().toString()  ));

                            params.add(new BasicNameValuePair("dcr_type", shift_spinner.getSelectedItem().toString()  ));


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
                            String MPO_CODE = bundle.getString("MPO_CODE");
                            String userName = bundle.getString("UserName");
                            String UserName_2 = bundle.getString("UserName_2");
                            Intent sameint = new Intent(RMPersonalExpenses.this, RMPersonalExpenses.class);
                            sameint.putExtra("Ord_NO", dcr_sl_no);
                            sameint.putExtra("UserName", userName);
                            sameint.putExtra("UserName_2", UserName_2);
                            startActivity(sameint);



                        }
                    });


                    server.start();

                }
                //  }


            }
        });








        /*============================================= End  Next Button Event click==========================================================================*/

    }

    /*============================================= End  on create==========================================================================*/


    private void populateSpinner() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i <customerlist.size(); i++) {
            lables.add(customerlist.get(i).getName());

            //System.out.println("i="+i);

        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,R.layout.spinner_text_view, lables);
        cust.setAdapter(spinnerAdapter);
        String[] customer = lables.toArray(new String[lables.size()]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this,R.layout.spinner_text_view, customer);
        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        actv.setAdapter(Adapter);
        actv.setTextColor(Color.parseColor("#006199"));
    }



    private void populateSpinner2() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i <customerlist.size(); i++) {
            lables.add(customerlist.get(i).getName());
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,R.layout.spinner_text_view, lables);
        cust.setAdapter(spinnerAdapter);
        String[] customer = lables.toArray(new String[lables.size()]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this,R.layout.spinner_text_view, customer);
        AutoCompleteTextView actv1 = (AutoCompleteTextView) findViewById(R.id.JourneyMode);
        actv1.setAdapter(Adapter);
        actv1.setTextColor(Color.RED);
    }




    private void  populateSpinnerShift() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i <shiftlist.size(); i++) {
            lables.add(shiftlist.get(i).getName());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, lables);
        shift_spinner.setAdapter(spinnerAdapter);

    }








    class GetCategories extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(  RMPersonalExpenses.this);
            pDialog.setMessage("Fetching your DCR Date ...");
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

            List<NameValuePair>params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id",id));
            ServiceHandler jsonParser=new ServiceHandler();
            String json=jsonParser.makeServiceCall(URL_DCR, ServiceHandler.POST, params);

            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray customer = jsonObj.getJSONArray("customer");
                        for (int i = 0; i < customer.length(); i++) {
                            JSONObject catObj = (JSONObject) customer.get(i);
                            Customer custo = new Customer(catObj.getInt("id"),catObj.getString("name"));
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
            //  populateSpinner2();
        }

    }



    class GetJourmode extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //  pDialog = new ProgressDialog(  RMPersonalExpenses.this);
            //  pDialog.setMessage("Fetching Customer..");
            // pDialog.setCancelable(false);
            // pDialog.show();

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
            String json=jsonParser.makeServiceCall(URL_JOURMODE, ServiceHandler.POST, params);

            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray customer = jsonObj.getJSONArray("customer");
                        for (int i = 0; i < customer.length(); i++) {
                            JSONObject catObj = (JSONObject) customer.get(i);
                            Customer custo = new Customer(catObj.getInt("id"),catObj.getString("name"));
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
            populateSpinner2();
            //  populateSpinner2();
        }

    }



    class GetDcrType extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

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
            params.add(new BasicNameValuePair("pass_dcr_date",pass_dcr_date));

            ServiceHandler jsonParser=new ServiceHandler();
            String json=jsonParser.makeServiceCall(URL_PASS_DCR_DATE, ServiceHandler.POST, params);

            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray customer = jsonObj.getJSONArray("customer");
                        for (int i = 0; i < customer.length(); i++) {
                            JSONObject catObj = (JSONObject) customer.get(i);
                            Customer custo = new Customer(catObj.getInt("id"),catObj.getString("name"));
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
            if (pDialog.isShowing())
                pDialog.dismiss();
            populateSpinnerShift();

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
        Intent intent = new Intent(  RMPersonalExpenses.this, Login.class);
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
