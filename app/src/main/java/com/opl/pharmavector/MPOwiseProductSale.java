//MPOwiseProductSale



package com.opl.pharmavector;



import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import java.net.MalformedURLException;
import java.net.URL;
import java.lang.Runnable;

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


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MPOwiseProductSale extends Activity implements OnClickListener, AdapterView.OnItemSelectedListener {
    private static Activity parent;
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    // array list for spinner adapter
    private ArrayList<com.opl.pharmavector.Category> categoriesList;
    public ProgressDialog pDialog;
    ListView productListView;
    Button submit,submitBtn;
    // private EditText current_qnty;
    EditText qnty;
    Boolean result;
    EditText inputOne, inputtwo;
    public int success;
    public String message, ord_no;
    TextView date2, ded,fromdate,todate;
    int textlength = 0;
    public TextView totqty, totval;
    //public android.widget.Spinner ordspin;
    public String userName_1,userName,active_string,act_desiredString;
    public String from_date,to_date;
    com.opl.pharmavector.JSONParser jsonParser;
    List<NameValuePair> params;
    //public String CurrenCustomer="";
    //public AutoCompleteTextView actv;
    public static ArrayList<String> p_ids;
    public static ArrayList<Integer> p_quanty;
    public static ArrayList<String> PROD_RATE;
    public static ArrayList<String> PROD_VAT;
    private ArrayList<Customer> mpodcrlist;
    private ArrayList<String> array_sort = new ArrayList<String>();
    //private String URL_PRODUCT_VIEW ="http://opsonin.com.bd/dept_order_android_v2/ViewbyDate.php";

    private final String URL_PRODUCT_VIEW =BASE_URL+"MPOwiseProductSale1.php";
    private final String URL_DCR = BASE_URL+"get_product_followup.php";
    private android.widget.Spinner cust;
    public String p_code;
    private ArrayList<Customer> customerlist;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.amtargetquantity);

        setContentView(R.layout.mpowiseproductsale);

        Typeface fontFamily = Typeface.createFromAsset(getAssets(),"fonts/fontawesome.ttf");
        productListView = (ListView) findViewById(R.id.pListView);
        Button back_btn = (Button) findViewById(R.id.backbt);
        Button view_btn = (Button) findViewById(R.id.view);
        Button submitBtn = (Button) findViewById(R.id.submitBtn);
        fromdate = (TextView) findViewById(R.id.fromdate);
        todate = (TextView) findViewById(R.id.todate);


        cust = (android.widget.Spinner) findViewById(R.id.dcrlist);

        mpodcrlist = new ArrayList<Customer>();
        cust.setOnItemSelectedListener(this);

        final AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);

        back_btn.setTypeface(fontFamily);
        back_btn.setText("\uf060 ");// &#xf060
        final LinearLayout ln = (LinearLayout) findViewById(R.id.totalshow);
        totqty = (TextView) findViewById(R.id.totalsellquantity);
        totval = (TextView) findViewById(R.id.totalsellvalue);
        //totqty.setText("Quantity");
        //totval.setText("Sellvalue");
        int listsize = productListView.getChildCount();
        Log.i("Size of ProductLIstview", "ProductLIstView SIZE: " + listsize);
        p_ids = new ArrayList<String>();
        p_quanty = new ArrayList<Integer>();
        PROD_RATE = new ArrayList<String>();
        PROD_VAT = new ArrayList<String>();
        categoriesList = new ArrayList<com.opl.pharmavector.Category>();
        Bundle b = getIntent().getExtras();
        String userName = b.getString("UserName");
        submitBtn.setTextSize(10);
        Toast.makeText(MPOwiseProductSale.this, userName, Toast.LENGTH_LONG).show();


        Calendar c_todate = Calendar .getInstance();
        //System.out.println("Current time => "+c.getTime());
        SimpleDateFormat dftodate = new SimpleDateFormat("dd/MM/yyyy");
        String current_todate = dftodate.format(c_todate.getTime());
        todate.setText(current_todate);


        Calendar c_fromdate = Calendar .getInstance();
        //System.out.println("Current time => "+c.getTime());
        SimpleDateFormat dffromdate = new SimpleDateFormat("01/MM/yyyy");
        String current_fromdate = dffromdate.format(c_fromdate.getTime());
        fromdate.setText(current_fromdate);

        customerlist = new ArrayList<Customer>();
        cust.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        actv.setOnClickListener(new View.OnClickListener() {
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

                    if(inputorder.indexOf("//") != -1){

                        Log.w("product_code", "> " +"product_code : ->  "+ inputorder);


                        String arr[] = inputorder.split("//");
                        String product_name= arr[0].trim();
                        String product_code=arr[1].trim();




                        p_code=product_code;

                        Log.w("p_code", "> " +"product_code : ->  "+ p_code);

                        Log.w("p_code", "> " +"product_code : ->  "+ p_code);


                         actv.setText(product_name);

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



        new LoadProduct().execute();

        //new ViewbyDate.GetCategories().execute();
        /*------------------------------------------------------*/
        /*--------------------------from-date range----------------------------*/
        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date_form = new DatePickerDialog.OnDateSetListener() {
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

                fromdate.setTextColor(Color.BLACK);
                fromdate.setText("");
                fromdate.setText(sdf.format(myCalendar.getTime()));
            }

        };

        fromdate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(MPOwiseProductSale.this, date_form, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        /*---------------------------from date range-----------------end-----------*/





        /*--------------------------to-date range----------------------------*/

        final Calendar myCalendar1 = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date_to = new DatePickerDialog.OnDateSetListener() {
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
                todate.setTextColor(Color.BLACK);
                todate.setText("");
                todate.setText(sdf.format(myCalendar.getTime()));
            }

        };

        todate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(MPOwiseProductSale.this, date_to, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        /*---------------------------to date range-----------------end-----------*/

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

                            Intent i = new Intent(MPOwiseProductSale.this,AmSalesReportDashboard.class);
                            i.putExtra("UserName", userName);
                            i.putExtra("new_version", userName);
                            i.putExtra("UserName_1", UserName_1);
                            i.putExtra("UserName_2", UserName_2);

                            i.putExtra("userName", userName);
                            i.putExtra("new_version", userName);
                            i.putExtra("userName_1", UserName_1);
                            i.putExtra("userName_2", UserName_2);

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
















        submitBtn.setOnClickListener(new OnClickListener() {


            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            @Override
            public void onClick(final View v) {
                try {
                    String fromdate1=fromdate.getText().toString();
                    String todate1=todate.getText().toString();
                    System.out.println("else  fromdate1 "+fromdate1);
                    System.out.println("else todate1"+todate1);
                    if (fromdate1.isEmpty()||(fromdate1.equals("From Date"))||(fromdate1.equals("From Date is required"))) {
                        //fromdate.setError( "From Date is required!" );
                        fromdate.setText("From Date is required");
                        fromdate.setTextColor(Color.RED);
                    }
                    else if (todate1.isEmpty()||(todate1.equals("To Date"))||(todate1.equals("To Date is required"))) {
                        //todate.setError( "To Date is required!" );

                        todate.setText("To Date is required");
                        todate.setTextColor(Color.RED);

                    }
                    else {
                        System.out.println("after text change elsfromdate1eeeeeeeee"+fromdate1);
                        System.out.println("elsetodate1 "+todate1);

                        categoriesList.clear();
                        new GetCategories().execute();


                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ln.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

            }
        });

    }



    private void producpopulatespinner() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i <customerlist.size(); i++) {
            lables.add(customerlist.get(i).getName());


        }


        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, lables);
        cust.setAdapter(spinnerAdapter);
        String[] customer = lables.toArray(new String[lables.size()]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item, customer);
        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);

        actv.setAdapter(Adapter);
        actv.setTextColor(Color.BLUE);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    class LoadProduct extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(  MPOwiseProductSale.this);
            pDialog.setMessage("Loading Products ...");
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
            producpopulatespinner();

        }

    }





















    private void popSpinner() {
        List<String> description = new ArrayList<String>();
        for (int i = 0; i < categoriesList.size(); i++) {
            Log.d("Changepa---ssword",categoriesList.get(i).getId());
            description.add(categoriesList.get(i).getId());
            Log.d("Changep---assword","Login"+categoriesList.get(i).getId());
        }


    }



    public void finishActivity(View v) {
        finish();
    }



    class Spinner {
        private String TotalQ;
        private String TotalV;
        private void populateSpinner() {
            ArrayList<String> lables = new ArrayList<String>();
            ArrayList<Integer> quanty = new ArrayList<Integer>();
            ArrayList<String> value = new ArrayList<String>();
            ArrayList<String> achv = new ArrayList<String>();
            ArrayList<String> mpo_code = new ArrayList<String>();

            int quantity = 0;
            float  achievment;
            String prod_rate, prod_vat,sellvalue;
            String mpo;

            for (int i = 0; i < categoriesList.size(); i++) {

                lables.add(categoriesList.get(i).getName());
                p_ids.add(categoriesList.get(i).getId());
                quanty.add(categoriesList.get(i).getQuantity());
                quantity = categoriesList.get(i).getQuantity();


                prod_rate = String.valueOf((categoriesList.get(i).getPROD_RATE()));
                prod_vat = String.valueOf((categoriesList.get(i).getPROD_VAT()));
                mpo = String.valueOf(categoriesList.get(i).getPPM_CODE());

                Log.i("OPSONIN", "mpo_code"+categoriesList.get(i).getPPM_CODE());


                Log.i("OPSONIN", "mpo_code"+prod_vat);
                Log.i("OPSONIN", "mpo_code"+prod_rate);

                value.add(prod_rate);

                achv.add(prod_vat);
                mpo_code.add(mpo);
            }





           MPOwiseProductSaleShowAdapter adapter = new MPOwiseProductSaleShowAdapter(MPOwiseProductSale.this,lables, quanty,value,achv,mpo_code);


            productListView.setAdapter(adapter);
        }

        private float round(float x, int i) {
            // TODO Auto-generated method stub
            return 0;
        }
        public String getTotalQ() {
            return TotalQ;
        }
        public String getTotalV() {
            return TotalV;
        }
    }
    private class GetCategories extends AsyncTask<Void, Void, Void> {

        String fromdate1=fromdate.getText().toString();
        String todate1=todate.getText().toString();
        Bundle b = getIntent().getExtras();
        String userName = b.getString("UserName");
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MPOwiseProductSale.this);
            pDialog.setTitle("Data Loading !");
            pDialog.setMessage("Please Wait..");
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            Log.e("Response: ", ">  yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy---------------------------y");

            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            String UserName = b.getString("UserName");
            String id = userName;
            Log.e(" todate:  yyyyyyy", ">  yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy"+todate1);
            Log.e(" fromdate:  yyyyyyy", ">  yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy"+fromdate1);
            Log.e(" id:  yyyyyyy", id);
            Log.e(" UserName:  yyyyyyy", UserName);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", id));
            params.add(new BasicNameValuePair("to_date", todate1));
            params.add(new BasicNameValuePair("p_code",p_code));
            params.add(new BasicNameValuePair("from_date", fromdate1));

            com.opl.pharmavector.ServiceHandler jsonParser = new com.opl.pharmavector.ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_PRODUCT_VIEW, com.opl.pharmavector.ServiceHandler.POST, params);
            Log.e("Response: ", "> " + json);
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj.getJSONArray("categories");
                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            com.opl.pharmavector.Category cat = new com.opl.pharmavector.Category(
                                    catObj.getString("sl"),
                                    catObj.getString("id"),
                                    catObj.getString("name"),
                                    catObj.getInt("quantity"),
                                    catObj.getString("PROD_RATE"),
                                    catObj.getString("PROD_VAT"),
                                    catObj.getString("PPM_CODE")
                            );
                            categoriesList.add(cat);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
                Toast.makeText(MPOwiseProductSale.this, "Nothing To Disply",Toast.LENGTH_SHORT).show();
                Toast.makeText(MPOwiseProductSale.this, "Please make a order first !",Toast.LENGTH_LONG).show();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            MPOwiseProductSale.Spinner sp = new MPOwiseProductSale.Spinner();
            sp.populateSpinner();
            popSpinner();
            totqty.setText("");
            totval.setText("");

            //totqty.setText("Total target quantity="+sp.getTotalQ());
            //totval.setText("Total Sales quantity="+sp.getTotalV());

        }
    }
    /*------------- list items on click event----------------*/























    @Override
    public void onClick(View v) {
    }
    protected void onPostExecute() {
    }
    private void view() {
        Intent i = new Intent(MPOwiseProductSale.this, com.opl.pharmavector.Report.class);
        startActivity(i);
        finish();

    }

}

