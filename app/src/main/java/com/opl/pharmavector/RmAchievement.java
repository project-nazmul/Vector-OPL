//package com.opl.pharmavector;

// RmAchievement


//AmAchievment
package com.opl.pharmavector;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.opl.pharmavector.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class RmAchievement extends Activity implements OnItemSelectedListener {
    private Button logout,achivbtn;

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
    public String userName_1,userName;
    private String URL_CUSOTMER = BASE_URL+"regional_manager_api/sales_reports/Achievement1.php";
    protected Handler handler;
    public ProgressBar bar_1,bar_2,bar_3,bar_4;
    public EditText date_ext,val_mpo,submit_mpo,req_mpo,val_growth;


    @SuppressLint("CutPasteId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.rmachievment);
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");

        logout = (Button) findViewById(R.id.logout);
        logout.setTypeface(fontFamily);
        logout.setText("\uf08b"); //&#xf08b


        user_show = (TextView) findViewById(R.id.user_show);
        Button btn_1 = (Button) findViewById(R.id.btn_1);
        Button btn_2 = (Button) findViewById(R.id.btn_2);
        Button btn_3 = (Button) findViewById(R.id.btn_3);
        Button btn_4 = (Button) findViewById(R.id.btn_4);


        val_mpo=(EditText) findViewById(R.id.val_mpo);
        submit_mpo =(EditText) findViewById(R.id.submit_mpo);
        req_mpo =(EditText) findViewById(R.id.req_mpo);
        val_growth =(EditText) findViewById(R.id.val_growth);

        bar_1 = (ProgressBar) findViewById(R.id.bar_1);
        bar_2 = (ProgressBar) findViewById(R.id.bar_2);
        bar_3 = (ProgressBar) findViewById(R.id.bar_3);
        bar_4 = (ProgressBar) findViewById(R.id.bar_4);


        back = (Button) findViewById(R.id.logout);
        back.setTypeface(fontFamily);
        back.setText("\uf08b");// &#xf060

        customerlist = new ArrayList<Customer>();


        //new GetCategories().execute();


        final TextView myTextView = (TextView) findViewById(R.id.user_show1);
        Bundle b = getIntent().getExtras();

        if(b.isEmpty()){
            String userName="";


            myTextView.setText(userName);

        }
        else {

            userName = b.getString("UserName");
            userName_1= b.getString("userName_1");
            String UserName_2=b.getString("UserName_2");
            //  String ordernumber=b.getString("Ord_NO");
            String invoice=b.getString("target");
            String tar=b.getString("invoice");
            String achivement=b.getString("achivement");
            String growth=b.getString("growth");

            String msg=b.getString("UserName_2");
            String Ord_NO=b.getString("Ord_NO");
            myTextView.setText(Ord_NO);
            double d=Double.parseDouble(tar);
            int i=(int)d;
            double e=Double.parseDouble(invoice);
            int j=(int)e;
            String number =tar;
            //String number = "100000000";
            double amount = Double.parseDouble(number);
            DecimalFormat formatter = new DecimalFormat("#,##,###.00");
            String TARGET_VALUE = formatter.format(amount);
            String number2 =invoice;
            //String number = "100000000";
            double amount2 = Double.parseDouble(number2);
            DecimalFormat INVOICE_VALUE = new DecimalFormat("#,##,###.00");
            String INVOICE = INVOICE_VALUE.format(amount2);
            val_mpo.setText(TARGET_VALUE);




            bar_1.setMax(j);
            bar_1.setProgress(i);




            submit_mpo.setText(INVOICE);


            req_mpo.setText(achivement);

            val_growth.setText(growth);

            bar_3.setMax(j);
            bar_3.setProgress(i);
            bar_3.setRotation(180);

            bar_4.setMax(100);
            bar_4.setProgress(100);
            bar_4.setRotation(180);

        }
        session = new SessionManager(getApplicationContext());
        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                logoutUser();
            }
        });



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
        //	cust.setAdapter(spinnerAdapter);

        String[] customer = lables.toArray(new String[lables.size()]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, customer);

        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);

    }

    class GetCategories extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RmAchievement.this);
            pDialog.setMessage("Fetching Customer..");
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
                            Customer custo = new Customer(catObj.getInt("id"),
                                    catObj.getString("name"));
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



    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    /*-----------------End of Spinner view---------------------------*/

    private void logoutUser() {
        session.setLogin(false);
        session.invalidate();
        Intent intent = new Intent(RmAchievement.this, Login.class);
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
