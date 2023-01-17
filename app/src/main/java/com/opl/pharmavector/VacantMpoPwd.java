package com.opl.pharmavector;

import static com.nativecss.enums.RemoteContentRefreshPeriod.Never;
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

import com.nativecss.NativeCSS;

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
import android.widget.TextView;
import android.widget.Toast;

public class VacantMpoPwd extends Activity implements OnClickListener {

    private static Activity parent;
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    private ArrayList<Category3> categoriesList;
    public ProgressDialog pDialog;
    ListView productListView;
    Button back_btn;
    EditText qnty;
    Boolean result;
    EditText inputOne, inputtwo;
    public int success;
    public String message, ord_no;
    TextView  fromdate, todate;
    public TextView totqty, totval,head;
    public String userName_1, userName, active_string, act_desiredString,user_flag;
    public String from_date, to_date;
    JSONParser jsonParser;
    List<NameValuePair> params;
    public static ArrayList<String> sl;
    public static ArrayList<String> p_ids;
    public static ArrayList<Integer> p_quanty;
    public static ArrayList<String> PROD_RATE;
    public static ArrayList<String> PROD_VAT;
    public static ArrayList<String> PROD_VAT_2;
    public static ArrayList<String> PROD_VAT_3;
    public static ArrayList<String> PROD_VAT_4;
    public String mpo_code;


    private String URL_PRODUCT_VIEW = BASE_URL+"VacantMpoPwd.php";


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.vacantmpo);
        initViews();
        p_ids = new ArrayList<String>();
        p_quanty = new ArrayList<Integer>();
        PROD_RATE = new ArrayList<String>();
        categoriesList = new ArrayList<Category3>();
        new GetCategories().execute();

        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                String brand_code1 = (String) productListView.getAdapter().getItem(arg2);
                String arr1[] = brand_code1.split("#");
                String mpo_pwd = arr1[1].trim();
                String mpo_code1 = arr1[0].trim();
                Intent i = new Intent(VacantMpoPwd.this, MPOChangepassword.class);
                i.putExtra("mpo_pwd", mpo_pwd);
                i.putExtra("mpo_code", mpo_code1);
                i.putExtra("UserName", userName);
                startActivity(i);


            }

        });


        back_btn.setOnClickListener(new OnClickListener() {
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

    private void initViews() {

        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        productListView = (ListView) findViewById(R.id.pListView);
        head = findViewById(R.id.head);
        back_btn =  findViewById(R.id.backbt);
        back_btn.setTypeface(fontFamily);
        back_btn.setText("\uf060 ");

        Bundle b = getIntent().getExtras();
        user_flag = b.getString("user_flag");

        switch (user_flag) {
            case "ASM":
                mpo_code = AssistantManagerDashboard.globalASMCode;
                break;
            case "SM":
                mpo_code = SalesManagerDashboard.globalSMCode;

                break;
            case "AM":
                mpo_code = AmDashboard.globalFMCode;
                break;
            case "RM":
                mpo_code = RmDashboard.globalRMCode;
                break;
            case "AD":
                head.setText("Master Code");
                mpo_code = "PE11351";
                break;


        }




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
            ArrayList<String> value8 = new ArrayList<String>();
            int quantity = 0;
            float prod_rate, prod_vat, sellvalue;
            String prod_rate_1, prod_vat_1, prod_vat_2, prod_vat_3, prod_vat_4, prod_vat_5, sellvalue_2, sellvalue_3;


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

                prod_vat_1 = categoriesList.get(i).getPROD_VAT();
                value4.add(prod_vat_1);


                prod_vat_2 = categoriesList.get(i).getPROD_VAT_2();
                value5.add(prod_vat_2);


                prod_vat_3 = categoriesList.get(i).getPROD_VAT_3();
                value6.add(prod_vat_3);

                prod_vat_4 = categoriesList.get(i).getPROD_VAT_4();
                value7.add(prod_vat_4);


                prod_vat_5 = categoriesList.get(i).getPROD_VAT_5();
                value8.add(prod_vat_5);


            }


            // DcrreportvalueProductShowAdapter adapter = new DcrreportvalueProductShowAdapter(MPODccStock.this,sl,lables, quanty, value,value4,value5,value6,value7);
//VacantMpoPwdAdapter
            //  MPODccStockAdapter adapter = new MPODccStockAdapter(VacantMpoPwd.this,sl,lables, quanty, value,value4,value5,value6,value7,value8);

            VacantMpoPwdAdapter adapter = new VacantMpoPwdAdapter(VacantMpoPwd.this, sl, lables, quanty, value, value4, value5, value6, value7, value8);

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


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(VacantMpoPwd.this);
            pDialog.setTitle("Data Loading !");
            pDialog.setMessage("Loading Please Wait..");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", mpo_code));
            params.add(new BasicNameValuePair("user_flag", user_flag));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_PRODUCT_VIEW, ServiceHandler.POST, params);
            Log.e("Response: ", "> " + json);
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
                                    catObj.getString("PROD_VAT_4"),
                                    catObj.getString("PROD_VAT_5")
                            );
                            categoriesList.add(cat3);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
                Toast.makeText(VacantMpoPwd.this, "Nothing To Disply", Toast.LENGTH_SHORT).show();
                Toast.makeText(VacantMpoPwd.this, "Please make a order first !", Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            VacantMpoPwd.Spinner sp = new VacantMpoPwd.Spinner();
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


    @Override
    public void onClick(View v) {
    }

    protected void onPostExecute() {
    }


    private void view() {
        Intent i = new Intent(VacantMpoPwd.this, Report.class);
        startActivity(i);
        finish();

    }

}
