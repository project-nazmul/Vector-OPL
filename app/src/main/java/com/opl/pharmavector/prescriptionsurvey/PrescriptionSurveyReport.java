package com.opl.pharmavector.prescriptionsurvey;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.opl.pharmavector.Category3;
import com.opl.pharmavector.ChemistGiftCampaign;
import com.opl.pharmavector.ChemistGiftCampaignAdapter;
import com.opl.pharmavector.Dashboard;
import com.opl.pharmavector.JSONParser;
import com.opl.pharmavector.Login;
import com.opl.pharmavector.R;
import com.opl.pharmavector.ServiceHandler;
import com.opl.pharmavector.Targetquantity;

public class PrescriptionSurveyReport extends Activity implements OnClickListener {
    private static Activity parent;
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    // array list for spinner adapter
    private ArrayList<Category3> categoriesList;
    private ArrayList<Category3> categoriesList2;
    private ArrayList<Category3> categoriesList3;
    public ProgressDialog pDialog, pDialog1;
    ListView productListView;
    ListView productListView2;
    ListView productListView3;
    Button submit, submitBtn;
    EditText qnty;
    Boolean result;
    EditText inputOne, inputtwo;
    public int success;
    public String message, ord_no;
    TextView date2, ded, todate, detail_head, detail_head3;
    int textlength = 0;
    public TextView totqty, totval, mpo_base, mpo_share;
    public String mpocode, userName_1, userName, active_string, act_desiredString;
    public String from_date, to_date;
    List<NameValuePair> params;
    public String test, test1, test2;
    public static ArrayList<String> sl;
    public static ArrayList<String> p_ids;
    public static ArrayList<Integer> p_quanty;
    public static ArrayList<String> PROD_RATE;
    public static ArrayList<String> PROD_VAT;
    public static ArrayList<String> PROD_VAT_2;
    public static ArrayList<String> PROD_VAT_3;
    public static ArrayList<String> PROD_VAT_4;
    private ArrayList<String> array_sort = new ArrayList<String>();
    TextView fromdate;
    public String fromdate1;
    private String URL_PRODUCT_VIEW = BASE_URL+"prescription_survey/mrd_survey_report/getcompabyshare.php";
    private String URL_DETAIL_VIEW = BASE_URL+"prescription_survey/mrd_survey_report/getmpowiseDoctor.php";
    private String URL_DETAIL_VIEW3 = BASE_URL+"prescription_survey/mrd_survey_report/getDoctorwisePrescription.php";

    private String MRD_MPO_DETAIL = BASE_URL+"prescription_survey/mrd_survey_report/mrd_mpo_summary.php";
    private String FOURP_MPO_DETAIL = BASE_URL+"prescription_survey/4p_survey_report/fourp_mpo_summary.php";


    private String FOURP_COMPANY_SHARE = BASE_URL+"prescription_survey/4p_survey_report/getcompabyshare.php";
    private String FOURP_DOCTOR_LIST = BASE_URL+"prescription_survey/4p_survey_report/getmpowiseDoctor.php";
    private String FOURP_DOCTOR_DETAILS = BASE_URL+"prescription_survey/4p_survey_report/getDoctorwisePrescription.php";
    private TabLayout tab;
    String Tab_Flag = "MRD";
    public String unit_share, base;
    Typeface fontFamily;
    Calendar c_fromdate;
    SimpleDateFormat dffromdate, com_share;
    String current_fromdate, com_share_date;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date_form;
    Button back_btn;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.prescription_shareactivity_main);

        intiViews();

        new GetMPOSummary().execute();

        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        Tab_Flag = "MRD";
                        productListView.setAdapter(null);
                        productListView2.setAdapter(null);
                        productListView3.setAdapter(null);
                        categoriesList.clear();
                        categoriesList2.clear();
                        categoriesList3.clear();

                        new GetMPOSummary().execute();
                        new GetCategories().execute();
                        break;
                    case 1:
                        Tab_Flag = "4P";
                        productListView.setAdapter(null);
                        productListView2.setAdapter(null);
                        productListView3.setAdapter(null);
                        categoriesList.clear();
                        categoriesList2.clear();
                        categoriesList3.clear();

                        new GetMPOSummary().execute();
                        new GetCategories().execute();
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        submitBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                try {
                    fromdate1 = fromdate.getText().toString().trim();
                    if (fromdate1.isEmpty() || (fromdate1.equals("Select a Date")) || (fromdate1.equals("Date is required"))) {
                        fromdate.setText("Date is required");
                        fromdate.setTextColor(Color.RED);
                    } else {

                        productListView.setAdapter(null);
                        productListView2.setAdapter(null);
                        productListView3.setAdapter(null);
                        categoriesList.clear();
                        categoriesList2.clear();
                        categoriesList3.clear();
                        new GetCategories().execute();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        productListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                productListView3.setAdapter(null);
                test1 = (String) productListView2.getAdapter().getItem(arg2);
                categoriesList3.clear();
                new GetCategories3().execute();
                detail_head3.setText("Doctor Code:\t" + test1);

            }
        });
        back_btn.setOnClickListener(new OnClickListener() {
            Bundle b = getIntent().getExtras();

            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                backthred.start();
            }
        });

    }

    private void intiViews() {


        fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        productListView = (ListView) findViewById(R.id.pListView);
        productListView2 = (ListView) findViewById(R.id.pListView2);
        productListView3 = (ListView) findViewById(R.id.pListView3);
        fromdate = findViewById(R.id.fromdate);
        mpo_base = findViewById(R.id.mpo_base);
        mpo_share = findViewById(R.id.mpo_share);
        detail_head3 = findViewById(R.id.detail_head3);
        c_fromdate = Calendar.getInstance();
        dffromdate = new SimpleDateFormat("01/MM/yyyy");
        current_fromdate = dffromdate.format(c_fromdate.getTime());
        fromdate.setText(current_fromdate);
        com_share = new SimpleDateFormat("01/MM/yyyy");
        com_share_date = com_share.format(c_fromdate.getTime());
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
                new DatePickerDialog(PrescriptionSurveyReport.this, date_form, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });


        back_btn = (Button) findViewById(R.id.backbt);
        back_btn.setTypeface(fontFamily);
        back_btn.setText("\uf060 ");


        p_ids = new ArrayList<String>();
        p_quanty = new ArrayList<Integer>();
        PROD_RATE = new ArrayList<String>();
        categoriesList = new ArrayList<Category3>();
        categoriesList2 = new ArrayList<Category3>();
        categoriesList3 = new ArrayList<Category3>();
        mpocode = Dashboard.globalmpocode;

        submitBtn = findViewById(R.id.submitBtn);

        tab = findViewById(R.id.tablayout);

    }

    private class GetMPOSummary extends AsyncTask<Void, Void, Void> {

        String json;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PrescriptionSurveyReport.this);
            pDialog.setTitle("MPO Base and Share");
            pDialog.setMessage("Loading Please Wait..");
            pDialog.setCancelable(true);
            // pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            final JSONParser jsonParser = new JSONParser();
            final List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("mpocode", Dashboard.globalmpocode));
            params.add(new BasicNameValuePair("fromdate", fromdate.getText().toString().trim()));
            params.add(new BasicNameValuePair("tabflag", Tab_Flag));
            Log.e("senddata==>", Dashboard.globalmpocode + "-->" + fromdate.getText().toString().trim() + "" + Tab_Flag);
            Thread server = new Thread(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    JSONObject json = jsonParser.makeHttpRequest(MRD_MPO_DETAIL, "POST", params);
                    try {
                        Log.e("d===>", String.valueOf(success));
                            unit_share = json.getString("mpo_share");
                            base = json.getString("mpo_base");
                            mpo_base.setText(base);
                            mpo_share.setText(unit_share);



                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }

            });
            //pDialog.dismiss();
            // pDialog.cancel();
            server.start();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            //mpo_base.setText(base);
            // mpo_share.setText(unit_share);
            //pDialog.dismiss();
        }
    }


    private class GetCategories extends AsyncTask<Void, Void, Void> {

        String json;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PrescriptionSurveyReport.this);
            pDialog.setTitle("Company Share");
            pDialog.setMessage("Loading Please Wait..");
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("mpocode", Dashboard.globalmpocode));
            params.add(new BasicNameValuePair("fromdate", fromdate.getText().toString().trim()));


            ServiceHandler jsonParser = new ServiceHandler();
            if (Tab_Flag.equals("MRD")) {
                json = jsonParser.makeServiceCall(URL_PRODUCT_VIEW, ServiceHandler.POST, params);
            } else {
                Log.e("4p-->",Tab_Flag);
                json = jsonParser.makeServiceCall(FOURP_COMPANY_SHARE, ServiceHandler.POST, params);
            }

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj.getJSONArray("categories");
                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            Category3 cat3 = new Category3(
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


            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Spinner sp = new Spinner();
            sp.populateSpinner();
            popSpinner();
            pDialog.dismiss();
            new GetCategories2().execute();

        }
    }

    private void popSpinner() {
        List<String> description = new ArrayList<String>();
        for (int i = 0; i < categoriesList.size(); i++) {
            description.add(categoriesList.get(i).getId());
        }

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
            String prod_rate_1, prod_vat_1, prod_vat_2, prod_vat_3, prod_vat_4, sellvalue_2, sellvalue_3;

            for (int i = 0; i < categoriesList.size(); i++) {
                sl.add(categoriesList.get(i).getsl());
                lables.add(categoriesList.get(i).getName());
                p_ids.add(categoriesList.get(i).getId());
                quanty.add(categoriesList.get(i).getQuantity());
                //quantity = categoriesList.get(i).getQuantity();
                prod_rate_1 = categoriesList.get(i).getPROD_RATE();
                sellvalue_2 = prod_rate_1;
                value.add(sellvalue_2);
                prod_vat_1 = categoriesList.get(i).getPROD_VAT();
                value4.add(prod_vat_1);
                prod_vat_2 = categoriesList.get(i).getPROD_VAT_2();
                value5.add(prod_vat_2);
                prod_vat_3 = categoriesList.get(i).getPROD_VAT_3();
                value6.add(prod_vat_3);
                prod_vat_4 = categoriesList.get(i).getPROD_VAT_4();
                value7.add(prod_vat_4);

            }

            PrescriptionSurveyAdapter adapter = new PrescriptionSurveyAdapter(PrescriptionSurveyReport.this, sl, lables, quanty, value, value4, value5, value6, value7);
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


    private class GetCategories2 extends AsyncTask<Void, Void, Void> {

        String json;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PrescriptionSurveyReport.this);
            pDialog.setTitle("Doctors List");
            pDialog.setMessage("Loading Please Wait..");
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("mpocode", mpocode));
            params.add(new BasicNameValuePair("fromdate", fromdate.getText().toString().trim()));
            ServiceHandler jsonParser = new ServiceHandler();

            if (Tab_Flag == "MRD") {
                json = jsonParser.makeServiceCall(URL_DETAIL_VIEW, ServiceHandler.POST, params);
            } else {
                json = jsonParser.makeServiceCall(FOURP_DOCTOR_LIST, ServiceHandler.POST, params);
            }

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj.getJSONArray("categories");
                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            Category3 cat3 = new Category3(
                                    catObj.getString("sl"),
                                    catObj.getString("id"),
                                    catObj.getString("name"),
                                    catObj.getString("quantity"),
                                    catObj.getString("PROD_RATE"),
                                    catObj.getString("PROD_VAT"),
                                    catObj.getString("PROD_VAT_2"),
                                    catObj.getString("PROD_VAT_3"),
                                    catObj.getString("PROD_VAT_4"));
                            categoriesList2.add(cat3);
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
            Spinner2 sp = new Spinner2();
            sp.populateSpinner2();
            popSpinner2();
            pDialog.dismiss();

        }
    }

    class Spinner2 {
        private String TotalQ;
        private String TotalV;

        private void populateSpinner2() {
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
            String prod_rate_1, prod_vat_1, prod_vat_2, prod_vat_3, prod_vat_4, sellvalue_2, sellvalue_3;

            for (int i = 0; i < categoriesList2.size(); i++) {

                sl.add(categoriesList2.get(i).getsl());
                lables.add(categoriesList2.get(i).getName());
                p_ids.add(categoriesList2.get(i).getId());
                quanty.add(categoriesList2.get(i).getQuantity());
                //quantity = categoriesList.get(i).getQuantity();
                prod_rate_1 = categoriesList2.get(i).getPROD_RATE();
                sellvalue_2 = prod_rate_1;
                value.add(sellvalue_2);
                prod_vat_1 = categoriesList2.get(i).getPROD_VAT();
                value4.add(prod_vat_1);
                prod_vat_2 = categoriesList2.get(i).getPROD_VAT_2();
                value5.add(prod_vat_2);
                prod_vat_3 = categoriesList2.get(i).getPROD_VAT_3();
                value6.add(prod_vat_3);
                prod_vat_4 = categoriesList2.get(i).getPROD_VAT_4();
                value7.add(prod_vat_4);

            }

            //PrescriptionSurveyAdapter adapter2 = new PrescriptionSurveyAdapter(PrescriptionSurveyReport.this,sl,lables, quanty, value,value4,value5,value6,value7);
            PrescriptionSurveyAdapter2 adapter2 = new PrescriptionSurveyAdapter2(PrescriptionSurveyReport.this, sl, lables, quanty, value, value4, value5, value6, value7);

            productListView2.setAdapter(adapter2);

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

    private void popSpinner2() {
        List<String> description2 = new ArrayList<String>();
        for (int i = 0; i < categoriesList2.size(); i++) {
            description2.add(categoriesList2.get(i).getId());
        }


        if (categoriesList2.size() < 1) {
            Toast.makeText(PrescriptionSurveyReport.this,
                    "Doctors data is not Available for this Month ! !", Toast.LENGTH_SHORT).show();
        }
    }


    private class GetCategories3 extends AsyncTask<Void, Void, Void> {

        String json;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PrescriptionSurveyReport.this);
            pDialog.setTitle("Data Loading !");
            pDialog.setMessage("Doctors Prescription Detail");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            //  params.add(new BasicNameValuePair("id", test));
            params.add(new BasicNameValuePair("mpocode", mpocode));
            params.add(new BasicNameValuePair("fromdate", fromdate.getText().toString().trim()));
            params.add(new BasicNameValuePair("doccode", test1.trim()));
            ServiceHandler jsonParser = new ServiceHandler();

            if (Tab_Flag == "MRD") {
                json = jsonParser.makeServiceCall(URL_DETAIL_VIEW3, ServiceHandler.POST, params);
            } else if (Tab_Flag == "4P") {
                json = jsonParser.makeServiceCall(FOURP_DOCTOR_DETAILS, ServiceHandler.POST, params);
            }

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj.getJSONArray("categories");
                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            Category3 cat3 = new Category3(
                                    catObj.getString("sl"),
                                    catObj.getString("id"),
                                    catObj.getString("name"),
                                    catObj.getString("quantity"),
                                    catObj.getString("PROD_RATE"),
                                    catObj.getString("PROD_VAT"),
                                    catObj.getString("PROD_VAT_2"),
                                    catObj.getString("PROD_VAT_3"),
                                    catObj.getString("PROD_VAT_4"));
                            categoriesList3.add(cat3);
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

            Spinner3 sp3 = new Spinner3();
            sp3.populateSpinner3();
            popSpinner3();
        }
    }

    class Spinner3 {
        private String TotalQ;
        private String TotalV;

        private void populateSpinner3() {
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
            String prod_rate_1, prod_vat_1, prod_vat_2, prod_vat_3, prod_vat_4, sellvalue_2, sellvalue_3;

            for (int i = 0; i < categoriesList3.size(); i++) {

                sl.add(categoriesList3.get(i).getsl());
                lables.add(categoriesList3.get(i).getName());
                p_ids.add(categoriesList3.get(i).getId());
                quanty.add(categoriesList3.get(i).getQuantity());

                //quantity = categoriesList.get(i).getQuantity();
                prod_rate_1 = categoriesList3.get(i).getPROD_RATE();
                sellvalue_2 = prod_rate_1;
                value.add(sellvalue_2);

                prod_vat_1 = categoriesList3.get(i).getPROD_VAT();
                value4.add(prod_vat_1);


                prod_vat_2 = categoriesList3.get(i).getPROD_VAT_2();
                value5.add(prod_vat_2);


                prod_vat_3 = categoriesList3.get(i).getPROD_VAT_3();
                value6.add(prod_vat_3);

                prod_vat_4 = categoriesList3.get(i).getPROD_VAT_4();
                value7.add(prod_vat_4);

            }


            PrescriptionSurveyAdapter3 adapter2 = new PrescriptionSurveyAdapter3(PrescriptionSurveyReport.this, sl, lables, quanty, value, value4, value5, value6, value7);
            productListView3.setAdapter(adapter2);


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

    private void popSpinner3() {
        List<String> description3 = new ArrayList<String>();
        for (int i = 0; i < categoriesList3.size(); i++) {
            description3.add(categoriesList3.get(i).getId());
        }
        if (categoriesList3.size() < 1) {
            Toast.makeText(PrescriptionSurveyReport.this,
                    "No Data Available for this Doctor ! !", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
    }

    protected void onPostExecute() {
    }


}




