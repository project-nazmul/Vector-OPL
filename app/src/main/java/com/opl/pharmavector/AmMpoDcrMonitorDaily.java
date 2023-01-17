package com.opl.pharmavector;



import static com.opl.pharmavector.remote.ApiClient.BASE_URL_AM;

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


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;



public class AmMpoDcrMonitorDaily extends Activity implements OnClickListener {
    private static Activity parent;
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    // array list for spinner adapter
    private ArrayList<com.opl.pharmavector.AmCategory3> categoriesList;
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
    JSONParser jsonParser;
    List<NameValuePair> params;
    //public String CurrenCustomer="";
    //public AutoCompleteTextView actv;
    public static ArrayList<String> sl;
    public static ArrayList<String> p_ids;
    public static ArrayList<Integer> p_quanty;
    public static ArrayList<String> PROD_RATE;
    public static ArrayList<String> PROD_VAT;
    public static ArrayList<String> PROD_VAT_2;
    public static ArrayList<String> PROD_VAT_3;
    public static ArrayList<String> PROD_VAT_4;
    private android.widget.Spinner count_dcr;
    private ArrayList<com.opl.pharmavector.AmCustomer> dateextendlist;

    private ArrayList<com.opl.pharmavector.AmCustomer> mpodonedcr;

    private ArrayList<com.opl.pharmavector.AmCustomer> mporeqdcr;

    public String get_ext_dt;


    public String get_ext_dt2;

    public String get_ext_dt3;


    private ArrayList<AmCustomer> mpodcrlist;

    private ArrayList<String> array_sort = new ArrayList<String>();
    private final String URL_PRODUCT_VIEW =BASE_URL_AM+"/MpoDcrMonitorDaily.php";
    private final String COUNT_MPO_DCR =BASE_URL_AM+"/COUNT_MPO_DCR.php";
    private final String COUNT_MPO =BASE_URL_AM+"/COUNT_MPO.php";
    private final String REQ_MPO =BASE_URL_AM+"/REQ_MPO.php";
    private final String Submit_DCR_MPO =BASE_URL_AM+"/Submit_DCR_MPO.php";
    private final String Not_Submit_DCR_MPO =BASE_URL_AM+"/Not_Submit_DCR_MPO.php";

    public EditText date_ext,val_mpo,submit_mpo,req_mpo;
    public ProgressBar bar_1,bar_2,bar_3;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ammpodcrmonitordaily);
        Typeface fontFamily = Typeface.createFromAsset(getAssets(),"fonts/fontawesome.ttf");
        productListView = (ListView) findViewById(R.id.pListView);
        Button back_btn = (Button) findViewById(R.id.backbt);
        Button view_btn = (Button) findViewById(R.id.view);
        Button submitBtn = (Button) findViewById(R.id.submitBtn);
        Button submitBtn_2 = (Button) findViewById(R.id.submitBtn_2);
        Button btn_2 = (Button) findViewById(R.id.btn_2);
        Button btn_3 = (Button) findViewById(R.id.btn_3);
        Button btn_1 = (Button) findViewById(R.id.btn_1);

        fromdate = (TextView) findViewById(R.id.fromdate);
        todate = (TextView) findViewById(R.id.todate);
        bar_1 = (ProgressBar) findViewById(R.id.bar_1);
        bar_2 = (ProgressBar) findViewById(R.id.bar_2);
        bar_3 = (ProgressBar) findViewById(R.id.bar_3);

        TextView namesmpo = (TextView) findViewById(R.id.namesmpo);
        TextView dcrbympo = (TextView) findViewById(R.id.dcrbympo);
        TextView codeofmpo = (TextView) findViewById(R.id.codeofmpo);
        TextView dcrreqbympo = (TextView) findViewById(R.id.dcrreqbympo);

        final TextView header_1 = (TextView) findViewById(R.id.header_1);
        final TextView header_2 = (TextView) findViewById(R.id.header_2);
        final TextView header_3 = (TextView) findViewById(R.id.header_3);
        val_mpo=(EditText) findViewById(R.id.val_mpo);
        submit_mpo =(EditText) findViewById(R.id.submit_mpo);
        req_mpo =(EditText) findViewById(R.id.req_mpo);
        date_ext=(EditText) findViewById(R.id.date_ex);
        back_btn.setTypeface(fontFamily);
        back_btn.setText("\uf060 ");// &#xf060
        final LinearLayout ln = (LinearLayout) findViewById(R.id.totalshow);
        totqty = (TextView) findViewById(R.id.totalsellquantity);
        totval = (TextView) findViewById(R.id.totalsellvalue);
        int listsize = productListView.getChildCount();
        Log.i("Size of ProductLIstview", "ProductLIstView SIZE: " + listsize);
        p_ids = new ArrayList<String>();
        p_quanty = new ArrayList<Integer>();
        PROD_RATE = new ArrayList<String>();
        categoriesList = new ArrayList<com.opl.pharmavector.AmCategory3>();


        Bundle b = getIntent().getExtras();
        String userName = b.getString("UserName");


        //DcrReport

        Calendar c_todate = Calendar .getInstance();
        SimpleDateFormat dftodate = new SimpleDateFormat("dd/MM/yyyy");
        String current_todate = dftodate.format(c_todate.getTime());
        todate.setText(current_todate);


        Calendar c_fromdate = Calendar .getInstance();
        SimpleDateFormat dffromdate = new SimpleDateFormat("01/MM/yyyy");
        String current_fromdate = dffromdate.format(c_fromdate.getTime());
        fromdate.setText(current_fromdate);


        mpodcrlist = new ArrayList<AmCustomer>();
        dateextendlist = new ArrayList<com.opl.pharmavector.AmCustomer>();
        mpodonedcr = new ArrayList<com.opl.pharmavector.AmCustomer>();
        mporeqdcr = new ArrayList<com.opl.pharmavector.AmCustomer>();

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
                new DatePickerDialog(AmMpoDcrMonitorDaily.this, date_form, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



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

                new DatePickerDialog(AmMpoDcrMonitorDaily.this, date_to, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        new CountMPO().execute();
        new CountMPODCR().execute();
        new REQ_MPO().execute();
        new GetCategories().execute();


        back_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            Intent i = new Intent(AmMpoDcrMonitorDaily.this,  AmDashboard.class);
                            i.putExtra("UserName", AmDashboard.globalFMCode);
                            i.putExtra("new_version", AmDashboard.new_version);
                            i.putExtra("UserName_2", AmDashboard.globalAreaCode);
                            i.putExtra("message_3", AmDashboard.message_3);
                            i.putExtra("password", AmDashboard.password);
                            i.putExtra("ff_type", AmDashboard.ff_type);
                            i.putExtra("vector_version", R.string.vector_version);
                            i.putExtra("emp_code", AmDashboard.globalempCode);
                            i.putExtra("emp_name", AmDashboard.globalempName);
                            startActivity(i);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });

                backthred.start();


            }
        });
        submitBtn_2.setOnClickListener(new OnClickListener() {

            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            @Override
            public void onClick(final View v) {
                try {
                    String fromdate1=fromdate.getText().toString();
                    String todate1=todate.getText().toString();
                    header_3.setVisibility(View.GONE);
                    header_1.setVisibility(View.VISIBLE);
                    header_2.setVisibility(View.GONE);


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
                        new CountMPODCR().execute();
                        new CountMPO().execute();
                        new REQ_MPO().execute();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btn_1.setOnClickListener(new OnClickListener() {


            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            @Override
            public void onClick(final View v) {
                try {
                    String fromdate1=fromdate.getText().toString();
                    String todate1=todate.getText().toString();
                    System.out.println("else  fromdate1 "+fromdate1);
                    System.out.println("else todate1"+todate1);


                    header_3.setVisibility(View.GONE);
                    header_1.setVisibility(View.VISIBLE);
                    header_2.setVisibility(View.GONE);


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
                        new CountMPODCR().execute();
                        new CountMPO().execute();
                        new REQ_MPO().execute();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        bar_1.setOnClickListener(new OnClickListener() {


            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            @Override
            public void onClick(final View v) {
                try {
                    String fromdate1=fromdate.getText().toString();
                    String todate1=todate.getText().toString();
                    System.out.println("else  fromdate1 "+fromdate1);
                    System.out.println("else todate1"+todate1);


                    header_3.setVisibility(View.GONE);
                    header_1.setVisibility(View.VISIBLE);
                    header_2.setVisibility(View.GONE);


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
                        new CountMPODCR().execute();
                        new CountMPO().execute();
                        new REQ_MPO().execute();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btn_2.setOnClickListener(new OnClickListener() {


            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            @Override
            public void onClick(final View v) {
                try {
                    String fromdate1=fromdate.getText().toString();
                    String todate1=todate.getText().toString();
                    System.out.println("else  fromdate1 "+fromdate1);
                    System.out.println("else todate1"+todate1);

                  //  Toast.makeText(getApplicationContext(), "Customer Updated" +"submitted by mpo button is clicked" , Toast.LENGTH_LONG).show();
                    header_1.setVisibility(View.GONE);
                    header_2.setVisibility(View.VISIBLE);
                    header_3.setVisibility(View.GONE);


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
                        new Submit_DCR_MPO().execute();
                        new CountMPODCR().execute();
                        new CountMPO().execute();
                        new REQ_MPO().execute();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        bar_2.setOnClickListener(new OnClickListener() {


            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            @Override
            public void onClick(final View v) {
                try {
                    String fromdate1=fromdate.getText().toString();
                    String todate1=todate.getText().toString();
                    System.out.println("else  fromdate1 "+fromdate1);
                    System.out.println("else todate1"+todate1);

                    //  Toast.makeText(getApplicationContext(), "Customer Updated" +"submitted by mpo button is clicked" , Toast.LENGTH_LONG).show();
                    header_1.setVisibility(View.GONE);
                    header_2.setVisibility(View.VISIBLE);
                    header_3.setVisibility(View.GONE);


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
                        new Submit_DCR_MPO().execute();
                        new CountMPODCR().execute();
                        new CountMPO().execute();
                        new REQ_MPO().execute();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btn_3.setOnClickListener(new OnClickListener() {


            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            @Override
            public void onClick(final View v) {
                try {
                    String fromdate1=fromdate.getText().toString();
                    String todate1=todate.getText().toString();
                    System.out.println("else  fromdate1 "+fromdate1);
                    System.out.println("else todate1"+todate1);



                    header_1.setVisibility(View.GONE);
                    header_3.setVisibility(View.VISIBLE);
                    header_2.setVisibility(View.GONE);



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
                        new Not_Submit_DCR_MPO().execute();
                        new CountMPODCR().execute();
                        new CountMPO().execute();
                        new REQ_MPO().execute();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        bar_3.setOnClickListener(new OnClickListener() {


            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            @Override
            public void onClick(final View v) {
                try {
                    String fromdate1=fromdate.getText().toString();
                    String todate1=todate.getText().toString();
                    System.out.println("else  fromdate1 "+fromdate1);
                    System.out.println("else todate1"+todate1);



                    header_1.setVisibility(View.GONE);
                    header_3.setVisibility(View.VISIBLE);
                    header_2.setVisibility(View.GONE);



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
                        new Not_Submit_DCR_MPO().execute();
                        new CountMPODCR().execute();
                        new CountMPO().execute();
                        new REQ_MPO().execute();

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



    private void popSpinner() {
        List<String> description = new ArrayList<String>();
        for (int i = 0; i < categoriesList.size(); i++) {
            description.add(categoriesList.get(i).getId());
        }


    }

    public void finishActivity(View v) {
        finish();
    }

    class Spinner {
        private String TotalQ;
        private String TotalV;
        private void populateSpinner() {
            ArrayList<String> sl = new ArrayList<String>();
            ArrayList<String> lables = new ArrayList<String>();
            ArrayList<String> quanty = new ArrayList<String>();
            ArrayList<String> value = new ArrayList<String>();
            ArrayList<String> value4 = new ArrayList<String>();
            ArrayList<String> value5 = new ArrayList<String>();
            ArrayList<String> value6 = new ArrayList<String>();
            ArrayList<String> value7 = new ArrayList<String>();

            int quantity = 0;
            float prod_rate, prod_vat, sellvalue;
            String prod_rate_1, prod_vat_1,prod_vat_2,prod_vat_3,prod_vat_4, sellvalue_2,sellvalue_3;



            for (int i = 0; i < categoriesList.size(); i++) {
                Log.i("OPSONIN", " P_ID " + categoriesList.get(i).getId());
                Log.i("OPSONIN--", " P_ID " + categoriesList.get(i).getsl());

                sl.add(categoriesList.get(i).getsl());

                lables.add(categoriesList.get(i).getName());
                p_ids.add(categoriesList.get(i).getId());
                quanty.add(categoriesList.get(i).getQuantity());

                //quantity = categoriesList.get(i).getQuantity();
                prod_rate_1 = categoriesList.get(i).getPROD_RATE();
                sellvalue_2 = prod_rate_1;
                value.add(sellvalue_2);

                prod_vat_1= categoriesList.get(i).getPROD_VAT();
                value4.add(prod_vat_1);


                prod_vat_2= categoriesList.get(i).getPROD_VAT_2();
                value5.add(prod_vat_2);


                prod_vat_3= categoriesList.get(i).getPROD_VAT_3();
                value6.add(prod_vat_3);

                prod_vat_4= categoriesList.get(i).getPROD_VAT_4();
                value7.add(prod_vat_4);


            }

            AmMpoDailyMonitorShowAdapter adapter = new AmMpoDailyMonitorShowAdapter(AmMpoDcrMonitorDaily.this,sl,lables, quanty, value,value4,value5,value6,value7);

           // DcrreportvalueProductShowAdapter adapter = new DcrreportvalueProductShowAdapter(MpoDcrMonitorDaily.this,sl,lables, quanty, value,value4,value5,value6,value7);
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



        String str = todate.getText().toString();
        String date_1 = str.replaceAll("[^\\d.-]", "");
        final String ord_no = userName + "-" + date_1;




        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AmMpoDcrMonitorDaily.this);
            pDialog.setTitle("Data Loading !");
            pDialog.setMessage("Loading Please Wait..");
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            Log.e("Response: ", ">  ");


            Log.e(" todate: ", ">  todate == "+todate1);
            Log.e(" fromdate: ", ">  fromdate ==  "+fromdate1);
            Log.e(" dcr_no: ", ">  dcr_no ==  "+ord_no);

            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            String id = userName;


            Log.e(" id ", ">  id ==  "+id);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", id));
            params.add(new BasicNameValuePair("to_date", todate1));
            params.add(new BasicNameValuePair("from_date", fromdate1));
            params.add(new BasicNameValuePair("ord_no", ord_no));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_PRODUCT_VIEW,ServiceHandler.POST, params);
            Log.e("Response: ", "> " + json);
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj.getJSONArray("categories");
                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            com.opl.pharmavector.AmCategory3 cat3 = new com.opl.pharmavector.AmCategory3(
                                    catObj.getString("sl"),
                                    catObj.getString("id"),
                                    catObj.getString("name"),
                                    catObj.getString("quantity"),
                                    catObj.getString("PROD_RATE"),
                                    catObj.getString("PROD_VAT"),
                                    catObj.getString("PROD_VAT_2"),
                                    catObj.getString("PROD_VAT_3"),
                                    catObj.getString("PROD_VAT_4"));
                            categoriesList.add(cat3);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
                Toast.makeText(AmMpoDcrMonitorDaily.this, "Nothing To Disply",Toast.LENGTH_SHORT).show();
                Toast.makeText(AmMpoDcrMonitorDaily.this, "Please make a order first !",Toast.LENGTH_LONG).show();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            AmMpoDcrMonitorDaily.Spinner sp = new AmMpoDcrMonitorDaily.Spinner();
            sp.populateSpinner();
            popSpinner();


/*

			String sale_velue = sp.getTotalV();
			//String number = "100000000";
			double sale_velue1 = Double.parseDouble(sale_velue);
			DecimalFormat formatter = new DecimalFormat("#,##,###.00");
			String sale_velue2 = formatter.format(sale_velue1);





			String  target_value= sp.getTotalQ();
			//String number = "100000000";
			double target_value2 = Double.parseDouble(target_value);
			DecimalFormat formatter2 = new DecimalFormat("#,##,###.00");
			String target_value3 = formatter2.format(target_value2);


			totqty.setText("Target value="+target_value3);
			totval.setText("Sale value="+sale_velue2);


*/

            //totqty.setText(sp.getTotalQ());
            //totval.setText(sp.getTotalV());

        }
    }


    private class Submit_DCR_MPO extends AsyncTask<Void, Void, Void> {

        String fromdate1=fromdate.getText().toString();
        String todate1=todate.getText().toString();
        Bundle b = getIntent().getExtras();
        String userName = b.getString("UserName");



        String str = todate.getText().toString();
        String date_1 = str.replaceAll("[^\\d.-]", "");
        final String ord_no = userName + "-" + date_1;




        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AmMpoDcrMonitorDaily.this);
            pDialog.setTitle("Data Loading !");
            pDialog.setMessage("Loading Please Wait..");
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            Log.e("Response: ", ">  ");


            Log.e(" todate: ", ">  todate == "+todate1);
            Log.e(" fromdate: ", ">  fromdate ==  "+fromdate1);
            Log.e(" dcr_no: ", ">  dcr_no ==  "+ord_no);

            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            String id = userName;


            Log.e(" id ", ">  id ==  "+id);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", id));
            params.add(new BasicNameValuePair("to_date", todate1));
            params.add(new BasicNameValuePair("from_date", fromdate1));
            params.add(new BasicNameValuePair("ord_no", ord_no));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(Submit_DCR_MPO,ServiceHandler.POST, params);
            Log.e("Response: ", "> " + json);
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj.getJSONArray("categories");
                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            com.opl.pharmavector.AmCategory3 cat3 = new com.opl.pharmavector.AmCategory3(
                                    catObj.getString("sl"),
                                    catObj.getString("id"),
                                    catObj.getString("name"),
                                    catObj.getString("quantity"),
                                    catObj.getString("PROD_RATE"),
                                    catObj.getString("PROD_VAT"),
                                    catObj.getString("PROD_VAT_2"),
                                    catObj.getString("PROD_VAT_3"),
                                    catObj.getString("PROD_VAT_4"));
                            categoriesList.add(cat3);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
                Toast.makeText(AmMpoDcrMonitorDaily.this, "Nothing To Disply",Toast.LENGTH_SHORT).show();
                Toast.makeText(AmMpoDcrMonitorDaily.this, "Please make a order first !",Toast.LENGTH_LONG).show();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            AmMpoDcrMonitorDaily.Spinner sp = new AmMpoDcrMonitorDaily.Spinner();
            sp.populateSpinner();
            popSpinner();


/*

			String sale_velue = sp.getTotalV();
			//String number = "100000000";
			double sale_velue1 = Double.parseDouble(sale_velue);
			DecimalFormat formatter = new DecimalFormat("#,##,###.00");
			String sale_velue2 = formatter.format(sale_velue1);





			String  target_value= sp.getTotalQ();
			//String number = "100000000";
			double target_value2 = Double.parseDouble(target_value);
			DecimalFormat formatter2 = new DecimalFormat("#,##,###.00");
			String target_value3 = formatter2.format(target_value2);


			totqty.setText("Target value="+target_value3);
			totval.setText("Sale value="+sale_velue2);


*/

            //totqty.setText(sp.getTotalQ());
            //totval.setText(sp.getTotalV());

        }
    }



    private class Not_Submit_DCR_MPO extends AsyncTask<Void, Void, Void> {

        String fromdate1=fromdate.getText().toString();
        String todate1=todate.getText().toString();
        Bundle b = getIntent().getExtras();
        String userName = b.getString("UserName");



        String str = todate.getText().toString();
        String date_1 = str.replaceAll("[^\\d.-]", "");
        final String ord_no = userName + "-" + date_1;




        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AmMpoDcrMonitorDaily.this);
            pDialog.setTitle("Data Loading !");
            pDialog.setMessage("Loading Please Wait..");
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            Log.e("Response: ", ">  ");


            Log.e(" todate: ", ">  todate == "+todate1);
            Log.e(" fromdate: ", ">  fromdate ==  "+fromdate1);
            Log.e(" dcr_no: ", ">  dcr_no ==  "+ord_no);

            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            String id = userName;


            Log.e(" id ", ">  id ==  "+id);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", id));
            params.add(new BasicNameValuePair("to_date", todate1));
            params.add(new BasicNameValuePair("from_date", fromdate1));
            params.add(new BasicNameValuePair("ord_no", ord_no));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(Not_Submit_DCR_MPO,ServiceHandler.POST, params);
            Log.e("Response: ", "> " + json);
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj.getJSONArray("categories");
                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            com.opl.pharmavector.AmCategory3 cat3 = new com.opl.pharmavector.AmCategory3(
                                    catObj.getString("sl"),
                                    catObj.getString("id"),
                                    catObj.getString("name"),
                                    catObj.getString("quantity"),
                                    catObj.getString("PROD_RATE"),
                                    catObj.getString("PROD_VAT"),
                                    catObj.getString("PROD_VAT_2"),
                                    catObj.getString("PROD_VAT_3"),
                                    catObj.getString("PROD_VAT_4"));
                            categoriesList.add(cat3);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
                Toast.makeText(AmMpoDcrMonitorDaily.this, "Nothing To Disply",Toast.LENGTH_SHORT).show();
                Toast.makeText(AmMpoDcrMonitorDaily.this, "Please make a order first !",Toast.LENGTH_LONG).show();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            AmMpoDcrMonitorDaily.Spinner sp = new AmMpoDcrMonitorDaily.Spinner();
            sp.populateSpinner();
            popSpinner();


/*

			String sale_velue = sp.getTotalV();
			//String number = "100000000";
			double sale_velue1 = Double.parseDouble(sale_velue);
			DecimalFormat formatter = new DecimalFormat("#,##,###.00");
			String sale_velue2 = formatter.format(sale_velue1);





			String  target_value= sp.getTotalQ();
			//String number = "100000000";
			double target_value2 = Double.parseDouble(target_value);
			DecimalFormat formatter2 = new DecimalFormat("#,##,###.00");
			String target_value3 = formatter2.format(target_value2);


			totqty.setText("Target value="+target_value3);
			totval.setText("Sale value="+sale_velue2);


*/

            //totqty.setText(sp.getTotalQ());
            //totval.setText(sp.getTotalV());

        }
    }


    private void  populatecountmpo() {
        List<String> lables = new ArrayList<String>();

        for (int i = 0; i <dateextendlist.size(); i++) {
            get_ext_dt= dateextendlist.get(i).getName();
        }

        val_mpo.setText(get_ext_dt);

        bar_1.setProgress(Integer.parseInt(get_ext_dt));
        bar_1.setMax(Integer.parseInt(get_ext_dt));
        bar_2.setMax(Integer.parseInt(get_ext_dt));
        bar_3.setMax(Integer.parseInt(get_ext_dt));
    }


    private void  populatedcrdonebympo() {
        List<String> lables = new ArrayList<String>();

        for (int i = 0; i <mpodonedcr.size(); i++) {
            get_ext_dt2= mpodonedcr.get(i).getName();
        }


        submit_mpo.setText(get_ext_dt2);
        bar_2.setProgress(Integer.parseInt(get_ext_dt2));

    }

    private void  populatreqmpo() {
        List<String> lables = new ArrayList<String>();

        for (int i = 0; i <mporeqdcr.size(); i++) {
            get_ext_dt3= mporeqdcr.get(i).getName();
        }

        req_mpo.setText(get_ext_dt3);
        bar_3.setProgress(Integer.parseInt(get_ext_dt3));
    }





    private class CountMPO extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            @SuppressLint("WrongThread") String str = todate.getText().toString();
            String id=userName;



            List<NameValuePair>params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id",id));
            params.add(new BasicNameValuePair("str",str));



            Log.e(" id: ", ">  id == "+id);
            Log.e(" str: ", ">  str ==  "+str);


            ServiceHandler jsonParser=new ServiceHandler();


            String json=jsonParser.makeServiceCall(COUNT_MPO, ServiceHandler.POST, params);

            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray customer = jsonObj.getJSONArray("customer");
                        for (int i = 0; i <= customer.length(); i++) {
                            JSONObject catObj = (JSONObject) customer.get(0);
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

            populatecountmpo();
        }

    }



    private class CountMPODCR extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            @SuppressLint("WrongThread") String str = todate.getText().toString();
            String id=userName;



            List<NameValuePair>params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id",id));
            params.add(new BasicNameValuePair("str",str));


            ServiceHandler jsonParser=new ServiceHandler();


            String json=jsonParser.makeServiceCall(COUNT_MPO_DCR, ServiceHandler.POST, params);

            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray customer = jsonObj.getJSONArray("customer");
                        for (int i = 0; i <= customer.length(); i++) {
                            JSONObject catObj = (JSONObject) customer.get(0);
                            com.opl.pharmavector.AmCustomer custo = new com.opl.pharmavector.AmCustomer(catObj.getInt("id"),catObj.getString("name"));
                            mpodonedcr.add(custo);

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
            populatedcrdonebympo();
        }

    }


    private class REQ_MPO extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            @SuppressLint("WrongThread") String str = todate.getText().toString();
            String id=userName;



            List<NameValuePair>params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id",id));
            params.add(new BasicNameValuePair("str",str));



            Log.e(" id: ", ">  id == "+id);
            Log.e(" str: ", ">  str ==  "+str);


            ServiceHandler jsonParser=new ServiceHandler();


            String json=jsonParser.makeServiceCall(REQ_MPO, ServiceHandler.POST, params);

            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray customer = jsonObj.getJSONArray("customer");
                        for (int i = 0; i <= customer.length(); i++) {
                            JSONObject catObj = (JSONObject) customer.get(0);
                            com.opl.pharmavector.AmCustomer custo = new com.opl.pharmavector.AmCustomer(catObj.getInt("id"),catObj.getString("name"));
                            mporeqdcr.add(custo);

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

            populatreqmpo();
        }

    }


    /*------------- list items on click event----------------*/
    @Override
    public void onClick(View v) {
    }

    protected void onPostExecute() {
    }


    private void view() {
        Intent i = new Intent(AmMpoDcrMonitorDaily.this, AmReport.class);
        startActivity(i);
        finish();

    }

}
