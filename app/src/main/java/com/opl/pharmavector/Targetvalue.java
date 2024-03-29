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
import android.widget.TextView;
import android.widget.Toast;

public class Targetvalue extends Activity implements OnClickListener {
    private static Activity parent;
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    //array list for spinner adapter
    private ArrayList<com.opl.pharmavector.Category> categoriesList;
    public ProgressDialog pDialog;
    ListView productListView;
    Button submit, submitBtn;
    //private EditText current_qnty;
    EditText qnty;
    Boolean result;
    EditText inputOne, inputtwo;
    public int success;
    public String message, ord_no;
    TextView date2, ded, fromdate, todate;
    int textlength = 0;
    public TextView totqty, totval;
    //public android.widget.Spinner ordspin;
    public String userName_1, userName, active_string, act_desiredString;
    public String from_date, to_date;
    com.opl.pharmavector.JSONParser jsonParser;
    List<NameValuePair> params;
    //public String CurrenCustomer="";
    //public AutoCompleteTextView actv;
    public static ArrayList<String> p_ids;
    public static ArrayList<Integer> p_quanty;
    public static ArrayList<String> PROD_RATE;
    public static ArrayList<String> PROD_VAT;
    public static ArrayList<String> PROD_VAT_2;
    private ArrayList<String> array_sort = new ArrayList<String>();
    private String URL_PRODUCT_VIEW = BASE_URL+"mposalesreports/report2.php";
    Button back_btn;
    LinearLayout ln;
    Calendar c_todate, c_fromdate;
    SimpleDateFormat dftodate, dffromdate;
    String current_todate, current_fromdate;
    Calendar myCalendar, myCalendar1;
    DatePickerDialog.OnDateSetListener date_form, date_to;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.amtargetvalue);

        initViews();
        calenClickEvents();
        new GetCategories().execute();
        back_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                });

                backthred.start();

            }
        });
        submitBtn.setOnClickListener(new OnClickListener() {
            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");

            @SuppressLint("SetTextI18n")
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
    }

    private void calenClickEvents() {
        date_form = new DatePickerDialog.OnDateSetListener() {
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
                fromdate.setTextColor(Color.BLACK);
                fromdate.setText("");
                fromdate.setText(sdf.format(myCalendar.getTime()));
            }
        };

        fromdate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Targetvalue.this, date_form, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        date_to = new DatePickerDialog.OnDateSetListener() {
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
                todate.setTextColor(Color.BLACK);
                todate.setText("");
                todate.setText(sdf.format(myCalendar.getTime()));
            }
        };

        todate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Targetvalue.this, date_to, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    @SuppressLint("SimpleDateFormat")
    private void initViews() {
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        productListView = findViewById(R.id.pListView);
        back_btn = findViewById(R.id.backbt);
        submitBtn = findViewById(R.id.submitBtn);
        fromdate = findViewById(R.id.fromdate);
        todate = findViewById(R.id.todate);
        back_btn.setTypeface(fontFamily);
        back_btn.setText("\uf060 ");
        ln = findViewById(R.id.totalshow);
        totqty = findViewById(R.id.totalsellquantity);
        totval = findViewById(R.id.totalsellvalue);
        p_ids = new ArrayList<String>();
        p_quanty = new ArrayList<Integer>();
        PROD_RATE = new ArrayList<String>();
        PROD_VAT = new ArrayList<String>();
        PROD_VAT_2 = new ArrayList<String>();
        categoriesList = new ArrayList<com.opl.pharmavector.Category>();
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
        myCalendar = Calendar.getInstance();
        myCalendar1 = Calendar.getInstance();
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

   /* class Spinner {
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
                prod_rate = Float.parseFloat(categoriesList.get(i).getPROD_RATE());
                prod_vat = Float.parseFloat(categoriesList.get(i).getPROD_VAT());
                prod_vat_2 = Float.parseFloat(categoriesList.get(i).getPROD_VAT_2());
                sellvalue = prod_rate;
                String sellvalue1 = String.format("%.02f", sellvalue);
                //Float sellvalue2= Float.parseFloat(sellvalue1);
                value.add(sellvalue1);
                achv.add(prod_vat);
                growth.add(prod_vat_2);
            }
            int totalqty = 0;
            float totalvlu = 0F;
            float qnty = 0F;

            for (int i = 0; i < quanty.size(); i++) {
                //totalqty += (quanty.get(i));
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
            //String number = "100000000";
            double target_value2 = Double.parseDouble(target_value);
            DecimalFormat formatter2 = new DecimalFormat("#,##,###.00");
            String target_value3 = formatter2.format(target_value2);
            totqty.setText("Target value =" + target_value3);
            totval.setText("Sale value = " + sale_velue2);
            AmTargetvalueProductShowAdapter adapter = new AmTargetvalueProductShowAdapter(Targetvalue.this, lables, quanty, value, achv, growth);
            productListView.setAdapter(adapter);
        }

        private float round(float x, int i) {
            return 0;
        }

        public String getTotalQ() {
            return TotalQ;
        }

        public String getTotalV() {
            return TotalV;
        }
    } */

    class Spinner {
        private String TotalQ;
        private String TotalV;

        @SuppressLint("SetTextI18n")
        private void populateSpinner() {
            ArrayList<String> lables = new ArrayList<String>();
            ArrayList<Integer> quanty = new ArrayList<Integer>();
            ArrayList<Float> value = new ArrayList<Float>();
            ArrayList<Float> achv = new ArrayList<Float>();
            ArrayList<Float> growth = new ArrayList<Float>();
            ArrayList<String> segment = new ArrayList<String>();
            int quantity = 0;
            float prod_rate, prod_vat, sellvalue, achievment, ppm_code;
            String segment_code;

            for (int i = 0; i < categoriesList.size(); i++) {
                lables.add(categoriesList.get(i).getName());
                p_ids.add(categoriesList.get(i).getId());
                quanty.add(categoriesList.get(i).getQuantity());
                quantity = categoriesList.get(i).getQuantity();
                prod_rate = Float.parseFloat(categoriesList.get(i).getPROD_RATE());
                prod_vat = Float.parseFloat(categoriesList.get(i).getPROD_VAT());
                ppm_code = Float.parseFloat(categoriesList.get(i).getPPM_CODE());
                segment_code = categoriesList.get(i).getP_CODE();
                Log.e("segment_Code", categoriesList.get(i).getP_CODE());
                sellvalue = prod_rate;
                @SuppressLint("DefaultLocale") String sellvalue1 = String.format("%.02f", sellvalue);
                Float sellvalue2 = Float.parseFloat(sellvalue1);
                value.add(prod_rate);
                achv.add(prod_vat);
                growth.add(ppm_code);
                segment.add(segment_code);
            }
            int totalqty = 0;
            float totalvlu = 0F;

            for (int i = 0; i < quanty.size(); i++) {
                totalqty += (quanty.get(i));
                totalvlu += (value.get(i));
            }
            float Totalsellvalue1 = totalvlu;
            float Totalsellquant1 = Float.parseFloat(String.valueOf(totalqty));
            @SuppressLint("DefaultLocale") String total_value = String.format("%.02f", Totalsellvalue1);
            @SuppressLint("DefaultLocale") String total_quant = String.format("%.02f", Totalsellquant1);
            TotalQ = String.valueOf(total_quant);
            TotalV = String.valueOf(total_value);
            totqty.setText("Target value = " + TotalQ);
            totval.setText("Sale value = " + TotalV);
            AmTargetquantityProductShowAdapter adapter = new AmTargetquantityProductShowAdapter(Targetvalue.this, lables, quanty, value, achv, growth,segment);
            productListView.setAdapter(adapter);
        }

        private float round(float x, int i) {
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
            pDialog = new ProgressDialog(Targetvalue.this);
            pDialog.setTitle("Calculating Target, Sale, Achievement and Growth Values");
            pDialog.setMessage("Please Wait..");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", userName));
            params.add(new BasicNameValuePair("to_date", todate1));
            params.add(new BasicNameValuePair("from_date", fromdate1));
            com.opl.pharmavector.ServiceHandler jsonParser = new com.opl.pharmavector.ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_PRODUCT_VIEW, com.opl.pharmavector.ServiceHandler.POST, params);
            Log.e("josnload==>",json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    JSONArray categories = jsonObj.getJSONArray("categories");

                    for (int i = 0; i < categories.length(); i++) {
                        JSONObject catObj = (JSONObject) categories.get(i);
                        Category cat = new Category(
                                catObj.getString("sl"),
                                catObj.getString("id"),
                                catObj.getString("name"),
                                catObj.getInt("quantity"),
                                catObj.getString("PROD_RATE"),
                                catObj.getString("PROD_VAT"),
                                catObj.getString("PPM_CODE"),
                                catObj.getString("P_CODE")
                        );
                        categoriesList.add(cat);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(Targetvalue.this, "Nothing To Display", Toast.LENGTH_SHORT).show();
                Toast.makeText(Targetvalue.this, "Please make a order first !", Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            Targetvalue.Spinner sp = new Targetvalue.Spinner();
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
        Intent i = new Intent(Targetvalue.this, com.opl.pharmavector.AmSalesReportDashboard.class);
        startActivity(i);
        finish();
    }
}
