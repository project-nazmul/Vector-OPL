package com.opl.pharmavector;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import java.net.MalformedURLException;
import java.net.URL;
import java.lang.Runnable;

import java.text.DecimalFormat;
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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.opl.pharmavector.adapter.RmTargetvalueProductShowAdapter;

public class RmTargetvalue extends Activity implements OnClickListener {
    private static Activity parent;
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    private ArrayList<com.opl.pharmavector.Category3> categoriesList;
    public ProgressDialog pDialog;
    ListView productListView;
    Button submitBtn;
    public int success;
    public String message, ord_no;
    TextView date2, ded, fromdate, todate;
    int textlength = 0;
    public TextView totqty, totval;
    public String userName_1, userName, active_string, act_desiredString;
    public String from_date, to_date;
    com.opl.pharmavector.JSONParser jsonParser;
    List<NameValuePair> params;
    public static ArrayList<String> p_ids;
    public static ArrayList<Integer> p_quanty;
    public static ArrayList<String> PROD_RATE;
    public static ArrayList<String> PROD_VAT;
    public static ArrayList<String> PROD_VAT_2;
    private ArrayList<String> array_sort = new ArrayList<String>();
    private String URL_PRODUCT_VIEW = BASE_URL+"regional_manager_api/sales_reports/Targetvaluegrowth.php";
    Button back_btn, view_btn;
    LinearLayout ln;
    int listsize;
    Calendar c_todate, c_fromdate;
    SimpleDateFormat dftodate, dffromdate;
    String current_todate, current_fromdate;
    Calendar myCalendar, myCalendar1;
    DatePickerDialog.OnDateSetListener date_form, date_to;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.rmtargetvalue);
        initViews();
        initCalender();
        new GetCategories().execute();


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

                            Intent i = new Intent(RmTargetvalue.this, RmSalesReportDashboard.class);
                            i.putExtra("UserName", userName);
                            i.putExtra("new_version", userName);
                            i.putExtra("UserName_1", UserName_1);
                            i.putExtra("UserName_2", UserName_2);

                            i.putExtra("userName", userName);
                            i.putExtra("new_version", userName);
                            i.putExtra("userName_1", UserName_1);
                            i.putExtra("userName_2", UserName_2);

                            Log.d("userName", "UserName" + userName);
                            Log.d("UserName_1", "UserName_1" + UserName_1);
                            Log.d("UserName_2", "UserName_2" + UserName_2);
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
                    String fromdate1 = fromdate.getText().toString();
                    String todate1 = todate.getText().toString();
                    if (fromdate1.isEmpty() || (fromdate1.equals("From Date")) || (fromdate1.equals("From Date is required"))) {
                        fromdate.setText("From Date is required");
                        fromdate.setTextColor(Color.RED);
                    } else if (todate1.isEmpty() || (todate1.equals("To Date")) || (todate1.equals("To Date is required"))) {

                        todate.setText("To Date is required");
                        todate.setTextColor(Color.RED);

                    } else {


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

    private void initCalender() {
        myCalendar = Calendar.getInstance();
        date_form = new DatePickerDialog.OnDateSetListener() {
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

                new DatePickerDialog(RmTargetvalue.this, date_form, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        myCalendar1 = Calendar.getInstance();
        date_to = new DatePickerDialog.OnDateSetListener() {
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
                todate.setTextColor(Color.BLACK);
                todate.setText("");
                todate.setText(sdf.format(myCalendar.getTime()));
            }

        };

        todate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(RmTargetvalue.this, date_to, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void initViews() {
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        productListView = (ListView) findViewById(R.id.pListView);
        back_btn = findViewById(R.id.backbt);
        view_btn = findViewById(R.id.view);
        submitBtn = findViewById(R.id.submitBtn);
        fromdate = findViewById(R.id.fromdate);
        todate = findViewById(R.id.todate);

        back_btn.setTypeface(fontFamily);
        back_btn.setText("\uf060 ");// &#xf060

        ln = findViewById(R.id.totalshow);
        totqty = (TextView) findViewById(R.id.totalsellquantity);
        totval = (TextView) findViewById(R.id.totalsellvalue);
        listsize = productListView.getChildCount();
        p_ids = new ArrayList<String>();
        p_quanty = new ArrayList<Integer>();
        PROD_RATE = new ArrayList<String>();
        PROD_VAT = new ArrayList<String>();
        PROD_VAT_2 = new ArrayList<String>();
        categoriesList = new ArrayList<com.opl.pharmavector.Category3>();

        Bundle b = getIntent().getExtras();
        userName = b.getString("UserName");
        c_todate = Calendar.getInstance();
        dftodate = new SimpleDateFormat("dd/MM/yyyy");
        current_todate = dftodate.format(c_todate.getTime());
        todate.setText(current_todate);
        c_fromdate = Calendar.getInstance();
        dffromdate = new SimpleDateFormat("01/MM/yyyy");
        current_fromdate = dffromdate.format(c_fromdate.getTime());
        fromdate.setText(current_fromdate);
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
            ArrayList<String> lables = new ArrayList<String>();
            ArrayList<String> quanty = new ArrayList<String>();
            ArrayList<String> value = new ArrayList<String>();
            ArrayList<Float> achv = new ArrayList<>();
            ArrayList<Float> growth = new ArrayList<>();
            int quantity = 0;
            float prod_rate, prod_vat, sellvalue, prod_vat_2;
            for (int i = 0; i < categoriesList.size(); i++) {
                lables.add(categoriesList.get(i).getName());
                p_ids.add(categoriesList.get(i).getId());
                quanty.add(categoriesList.get(i).getQuantity());
                //quantity = categoriesList.get(i).getQuantity();
                prod_rate = Float.parseFloat(categoriesList.get(i).getPROD_RATE());
                prod_vat = Float.parseFloat(categoriesList.get(i).getPROD_VAT());
                prod_vat_2 = Float.parseFloat(categoriesList.get(i).getPROD_VAT_2());
                //sellvalue = quantity * prod_rate;
                sellvalue = prod_rate;
                String sellvalue1 = String.format("%.02f", sellvalue);
                value.add(sellvalue1);


                achv.add(prod_vat);
                growth.add(prod_vat_2);
            }

            int totalqty = 0;
            float totalvlu = 0F;
            float qnty = 0F;
            for (int i = 0; i < quanty.size(); i++) {
                qnty += Float.parseFloat(quanty.get(i));
                totalvlu += Float.parseFloat(value.get(i)); //(value.get(i));
            }
            TotalQ = String.valueOf(qnty);
            float Totalsellvalue1 = totalvlu;
            String total_value = String.format("%.02f", Totalsellvalue1);
            TotalV = String.valueOf(total_value);
            String sale_velue = TotalV;
            //String number = "100000000";
            double sale_velue1 = Double.parseDouble(sale_velue);
            DecimalFormat formatter = new DecimalFormat("#,##,###.00");
            String sale_velue2 = formatter.format(sale_velue1);
            String target_value = TotalQ;
            double target_value2 = Double.parseDouble(target_value);
            DecimalFormat formatter2 = new DecimalFormat("#,##,###.00");
            String target_value3 = formatter2.format(target_value2);
            totqty.setText("Target value=" + target_value3);
            totval.setText("Sale value=" + sale_velue2);
            RmTargetvalueProductShowAdapter adapter = new RmTargetvalueProductShowAdapter(RmTargetvalue.this, lables,
                    quanty, value, achv, growth);


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

        String fromdate1 = fromdate.getText().toString();
        String todate1 = todate.getText().toString();
        Bundle b = getIntent().getExtras();
        String userName = b.getString("UserName");

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RmTargetvalue.this);
            pDialog.setTitle("Sales Loading !");
            pDialog.setMessage("Loading Please Wait..");
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
            params.add(new BasicNameValuePair("to_date", todate1));
            params.add(new BasicNameValuePair("from_date", fromdate1));

            com.opl.pharmavector.ServiceHandler jsonParser = new com.opl.pharmavector.ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_PRODUCT_VIEW, com.opl.pharmavector.ServiceHandler.POST, params);
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj.getJSONArray("categories");
                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            com.opl.pharmavector.Category3 cat3 = new com.opl.pharmavector.Category3(
                                    catObj.getString("sl"),
                                    catObj.getString("id"),
                                    catObj.getString("name"),
                                    catObj.getString("quantity"),
                                    catObj.getString("PROD_RATE"),
                                    catObj.getString("PROD_VAT"),
                                    catObj.getString("PROD_VAT_2")
                            );
                            categoriesList.add(cat3);
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
            RmTargetvalue.Spinner sp = new RmTargetvalue.Spinner();
            sp.populateSpinner();
            popSpinner();
        }
    }

    @Override
    public void onClick(View v) {
    }

    protected void onPostExecute() {
    }

    private void view() {
        Intent i = new Intent(RmTargetvalue.this, com.opl.pharmavector.AmSalesReportDashboard.class);
        startActivity(i);
        finish();

    }

}

