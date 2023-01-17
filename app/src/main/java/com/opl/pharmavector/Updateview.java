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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.nativecss.NativeCSS;
import com.opl.pharmavector.R;
import com.opl.pharmavector.order_online.ReadComments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class Updateview extends Activity implements OnItemSelectedListener {
    private Button logout;
    private Spinner spinner1, spinner2,cashcredit,cashcredit_test,credit;
    private TextView user_show;
    private SessionManager session;
    public Button next, back;
    Editor editor;
    public EditText osi, op, od, dateResult, ref;
    // private ListView cust;
    private ArrayList<Customer> customerlist;
    private Spinner cust;
    ProgressDialog pDialog;
    EditText mEdit;
    TextView date2, ded;
    public static String date=null,test;
    public int credit_party=0,cash_party=0;

    public String userName_1,userName,UserName,deli_tdate,deli_time,select_party;
    private String URL_CUSOTMER = BASE_URL+"get_ordno.php";
    private String URL_DATE_TIME = BASE_URL+"get_datetime.php";
    public String CUST_CODE1,pay_mode;
    TextView cash_credt;
    protected Handler handler;
    public String ord_date,Ord_NO;



    @SuppressLint("CutPasteId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updateview);
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        logout = (Button) findViewById(R.id.logout);
        logout.setTypeface(fontFamily);
        logout.setText("\uf08b"); //&#xf08b

        user_show = (TextView) findViewById(R.id.user_show);
        Button vieworders = (Button) findViewById(R.id.view);
        Button updateorders = (Button) findViewById(R.id.update);
        vieworders.setTypeface(fontFamily);
        vieworders.setText("\uf06e"); //&#xf06e
        next = (Button) findViewById(R.id.next);
        next.setTypeface(fontFamily);
        next.setText("\uf061");  //&#xf061
        Button back_btn = (Button) findViewById(R.id.backbt);



        back_btn.setTypeface(fontFamily);
        back_btn.setText("\uf060 ");// &#xf060
        final TextView error_dt=(TextView)findViewById(R.id.errordt);
        final TextView error_payment=(TextView)findViewById(R.id.errorpayment);
        op = (EditText) findViewById(R.id.orderpage);
        cust = (Spinner) findViewById(R.id.customer);
        cust.setPrompt("Select Customer");
        ref = (EditText) findViewById(R.id.reference);
        ded = (TextView) findViewById(R.id.deliverydate);
        cash_credt = (TextView) findViewById(R.id.cash_credt);
        final TextView ordno=(TextView) findViewById(R.id.ordno);
        TextView succ_msg=(TextView) findViewById(R.id.succ_msg);
        LinearLayout mainlayout = (LinearLayout)findViewById(R.id.successmsg);


        //final Spinner ampmspin=(Spinner)findViewById(R.id.ampm);
        //final Spinner cashcredit=(Spinner)findViewById(R.id.cashcredit);
        //final Spinner credit=(Spinner)findViewById(R.id.credit);

        final Spinner ampmspin=(Spinner)findViewById(R.id.ampm);
        cashcredit=(Spinner)findViewById(R.id.cashcredit);
        credit=(Spinner)findViewById(R.id.credit);


        cashcredit.setVisibility(View.GONE);
        credit.setVisibility(View.GONE);


        /*---------- getting CSS form user inputs-----------*/
     

        customerlist = new ArrayList<Customer>();
        cust.setOnItemSelectedListener(this);

        /*----------------get customer-----------*/

        new GetCategories().execute();

        /*-------------------customer end-----------*/


        final AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);

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

//-------------------------------
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
/*
            public void afterTextChanged(final Editable s) {
                // TODO Auto-generated method stub
                try {
                    final String inputorder = s.toString();
                    int total_string=inputorder.length();

                    if(inputorder.indexOf(":") != -1){

                        Log.e("inputorder", "> " + inputorder);
                        String result2 = inputorder.substring(inputorder.indexOf(":") + 1);

                        String pay_mode = inputorder.substring(inputorder.indexOf("//") +1);

                        Log.e("result2", "> " + result2);

                        Log.e("pay_mode", "> " + pay_mode);
                        int pay_mode_length=pay_mode.length();
                        int minus_length=pay_mode_length+13;


                        String asubstring = result2.substring(0, 8);

                        ded.setText(asubstring);
                        String sss = s.toString();
                        String ss = sss.substring(0,total_string-minus_length);
                        actv.setText(ss);
                        ded.setText(asubstring);


                        Log.e("pay_mode---", "> " + pay_mode);
                        Log.e("asubstring---", "> " + asubstring);
                        Log.e("ss---", "> " + ss);


                        String arr1[] = pay_mode.split("///");

                        cashcredit = (Spinner) findViewById(R.id.cashcredit);
                        List list = new ArrayList();
                        for(int i = 1; i < arr1.length; i++){
                            list.add(arr1[i].trim());
                        }
                        ArrayAdapter dataAdapter = new ArrayAdapter(Updateview.this,android.R.layout.simple_spinner_item, list);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        cashcredit.setAdapter(dataAdapter);


                        credit = (Spinner) findViewById(R.id.credit);
                        List list_credit = new ArrayList();
                        for(int j = 1; j < arr1.length; j++){
                            list_credit.add(arr1[j].trim());
                        }
                        ArrayAdapter dataAdapter_credit = new ArrayAdapter(Updateview.this,android.R.layout.simple_spinner_item, list);
                        dataAdapter_credit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        credit.setAdapter(dataAdapter);



                        pay_mode=arr1[0];
                        Log.e("pay_mode---", "> " + pay_mode);

                        if (!pay_mode.equals("/CREDIT")) {
                            cash_party=1;
                            credit_party=0;



                            //final Spinner credit=(Spinner)findViewById(R.id.credit);
                            // final Spinner cashcredit=(Spinner)findViewById(R.id.cashcredit);


                            credit=(Spinner)findViewById(R.id.credit);
                            cashcredit=(Spinner)findViewById(R.id.cashcredit);


                            View credit_view=(Spinner)findViewById(R.id.credit);
                            View cashcredit_view=(Spinner)findViewById(R.id.cashcredit);
                            credit_view.setVisibility(View.GONE);
                            cashcredit_view.setVisibility(View.VISIBLE);

                        }else{

                            // final Spinner credit=(Spinner)findViewById(R.id.credit);
                            // final Spinner cashcredit=(Spinner)findViewById(R.id.cashcredit);

                            credit=(Spinner)findViewById(R.id.credit);
                            cashcredit=(Spinner)findViewById(R.id.cashcredit);
                            View credit_view=(Spinner)findViewById(R.id.credit);
                            View cashcredit_view=(Spinner)findViewById(R.id.cashcredit);
                            cashcredit_view.setVisibility(View.GONE);
                            credit_view.setVisibility(View.VISIBLE);
                            cash_party=0;
                            credit_party=1;


                        }


                    }else{
                        ded.setText("Select Date");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
*/

            public void afterTextChanged(final Editable s) {
                // TODO Auto-generated method stub
                try {
                    final String inputorder = s.toString();
                    int total_string=inputorder.length();


                    Log.w("inputorder",inputorder );

                    Log.w("totalstring", String.valueOf(total_string));

                    if(total_string > 0){


                        String split1[] = inputorder.split("//");
                        String Ord_dtl =split1[1];


                        Ord_NO=split1[0];

                        String split2[] = Ord_dtl.split("/");


                        pay_mode=split2[1];


                        String Cust_detail=  split2[0];


                        String split3[] = Cust_detail.split("#");

                        String CUST_CODE=split3[0];


                        CUST_CODE1=CUST_CODE;


                        ord_date=split3[1];



                       // SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
                       // String date2 = DATE_FORMAT.format(date);




                        actv.setText(Ord_NO);
                        ded.setText(ord_date);
                        cash_credt.setText(pay_mode);





                    }else{
                        ded.setText("Select Date");
                    }



                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


            private void length() {
                // TODO Auto-generated method stub

            }


        });
        //--------------------------
        //------------------------------
        System.out.println("selected_cust" + " = " + actv);

        /*----------User show------------------------*/
        final TextView myTextView = (TextView) findViewById(R.id.user_show);
        Bundle b = getIntent().getExtras();
        String UserName=b.getString("UserName");
        String UserName_1=b.getString("userName_1");
        String UserName_2=b.getString("userName_2");
        //Log.e("userName", userName);
        System.out.println("userName in updateview" + UserName);
        System.out.println("userName in updateview" + UserName_1);

        myTextView.setText(UserName_1);
        myTextView.setText(UserName_2+"("+UserName+")");


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
                ded.setTextSize(18);
                ded.setTextColor(Color.BLACK);
                ded.setText(sdf.format(myCalendar.getTime()));
            }

        };

        ded.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(Updateview.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        /*---------------End Date view-----------------------------*/

        /*---------------Veiw Sells----------*/

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
                        String UserName = b.getString("UserName");
                        //Intent i = new Intent(Updateview.this, Sellview.class);
                        Intent i = new Intent(Updateview.this, Report.class);
                        String user=myTextView.getText().toString();
                        i.putExtra("UserName", UserName);
                        startActivity(i);
                    }
                });
                mysells.start();

            }
        });










        back_btn.setOnClickListener(new OnClickListener() {
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
                            Log.d("New_pass2", "New_pass2");

                            Bundle b = getIntent().getExtras();
                            String userName = b.getString("UserName");
                            String UserName_1 = b.getString("userName_1");
                            String UserName_2 = b.getString("userName_2");

                            Intent i = new Intent(Updateview.this,  ReadComments.class);
                            i.putExtra("UserName", userName);
                            i.putExtra("new_version", userName);
                            i.putExtra("UserName_1", UserName_1);
                            i.putExtra("UserName_2", UserName_2);

                            Log.d("userName","UserName"+userName);
                            Log.d("UserName_1","UserName_1"+UserName_1);
                            Log.d("UserName_2","UserName_2"+UserName_2);

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



        /*----------------------------------------*/




        /*---------------Update Sells----------*/

        updateorders.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Thread mysells = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        //Intent sellview=new Intent(ReadComments.this, Sellview.class);
                        Bundle b = getIntent().getExtras();
                        String UserName = b.getString("UserName");
                        Intent i = new Intent(Updateview.this, Updateview.class);
                        String user=myTextView.getText().toString();
                        i.putExtra("UserName", UserName);
                        startActivity(i);


                    }
                });
                mysells.start();

            }
        });
        /*----------------------------------------*/


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

        /*---------------Next Button Event click------------------*/
        next.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {


                if ((ded.getText().toString().trim().equals(""))|| (ded.getText().toString().trim().equals("Delivery Date"))|| (ded.getText().toString().trim().equals("Please Select date"))){
                    //ded.setError( "Delivery Date  is required!" );

                    //ded.setError( "Delivery Date  is required!" );
                    ded.setTextSize(14);
                    ded.setText("Please Select date");
                    ded.setTextColor(Color.RED);

                }else if((actv.getText().toString().trim().equals(""))||(actv.getText().toString().trim().equals("Input orderno (eg. 2015..)")))
                {

                    actv.setError("Please Select Orderno !");

                }




                else
                {

                    if(cash_party==1){
                        select_party=cashcredit.getSelectedItem().toString();
                    }else{
                        select_party=credit.getSelectedItem().toString();
                    }

                    if (select_party.equals("Select payment mode")) {
                        error_dt.setText("Please Select payment mode by click! " );
                        error_payment.setError( "Please Select payment mode by click!" );

                    }else{

                        final Spinner nameSpinner = (Spinner) findViewById(R.id.customer);
                        final String selected_cust=	actv.getText().toString();


                        Thread next = new Thread(new Runnable() {

                            @Override
                            public void run() {
                                Intent in = new Intent(Updateview.this,	ProductupdateOrder.class);
                                Bundle extras = new Bundle();

                                extras.putString("CUST_CODE", CUST_CODE1);
                                extras.putString("AM_PM", ampmspin.getSelectedItem().toString());
                                extras.putString("ORDER_DELEVERY_DATE", ded.getText().toString());
                                extras.putString("cash_credit", pay_mode);
                                extras.putString("ORD_NO", Ord_NO);


                                Bundle b = getIntent().getExtras();
                                String UserName = b.getString("UserName");
                                extras.putString("MPO_CODE",UserName);




                                in.putExtras(extras);
                                startActivity(in);



                            }
                        });

                        next.start();



                    }/*---------------end else with in else----------------------*/
                }
            }
        });








    }
    /*-------------- Extra Menus--------------*/

	/*
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menus, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.setting:
			startActivity(new Intent(this, setting.class));
			return true;
		case R.id.help:
			startActivity(new Intent(this, Help.class));
			return true;


		case R.id.home:
			//Intent i = new Intent(this, Login.class);
			//i.putExtra("UserName_1",userName_1);
			//i.putExtra("UserName",userName);
			//startActivity(i);
			//return true;
			finish();
		default:
			return super.onOptionsItemSelected(item);





		}
	}
*/
    /*------------Extra menus end-------------------*/

    /*----------Spinner view (Drop down view)-------------------*/

    private void Log_i() {
        // TODO Auto-generated method stub

    }


    private void populateSpinner() {
        List<String> lables = new ArrayList<String>();

        // txtCategory.setText("");

        for (int i = 0; i < customerlist.size(); i++) {
            lables.add(customerlist.get(i).getName());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);
        cust.setAdapter(spinnerAdapter);

        String[] customer = lables.toArray(new String[lables.size()]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item, customer);
        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        actv.setThreshold(2);//-----------------


        actv.setAdapter(Adapter);
        actv.setTextColor(Color.BLUE);
        System.out.println("customer" + " = " + actv.getText().toString());
        //Log.e(actv, actv);
        System.out.println("Adapter" + " = " + actv.getText().toString());

    }

    class GetCategories extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Updateview.this);
            pDialog.setMessage("Loading Orders..");
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
            String json=jsonParser.makeServiceCall(URL_CUSOTMER, ServiceHandler.POST, params);
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
        }

    }

    public class MySpinnerSelectedListener implements OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            Toast.makeText(getApplicationContext(),parent.getItemAtPosition(position).toString() + " Selected",Toast.LENGTH_LONG).show();
            String str = parent.getItemAtPosition(position).toString();
            Toast.makeText(Updateview.this, "Spin selected " + str,	Toast.LENGTH_LONG).show();

            System.out.println("str" + " = " + str);
            //-------------


            test=str;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // TODO Auto-generated method stub

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    /*-----------------End of Spinner view---------------------------*/

    private void logoutUser() {
        session.setLogin(false);
        // session.removeAttribute();
        session.invalidate();
        Intent intent = new Intent(Updateview.this, Login.class);
        startActivity(intent);
        finishActivity(BIND_ABOVE_CLIENT);
        finish();

    }

    private void next() {

        Intent i = new Intent(Updateview.this, ProductupdateOrder.class);
        startActivity(i);

    }

    private void back() {
        Intent i = new Intent(Updateview.this, Login.class);
        startActivity(i);
        finish();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        // TODO Auto-generated method stub

    }

}
