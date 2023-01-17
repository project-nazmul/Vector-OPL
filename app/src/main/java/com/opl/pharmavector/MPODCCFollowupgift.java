
package com.opl.pharmavector;

import static com.nativecss.enums.RemoteContentRefreshPeriod.Never;
import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import java.net.MalformedURLException;
import java.net.URL;
import java.lang.Runnable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.nativecss.NativeCSS;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;

public class MPODCCFollowupgift extends Activity implements OnClickListener {
    private static Activity parent;
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    private ArrayList<Category6> categoriesList2;

    public ProgressDialog pDialog;
    ListView productListView;
    ListView productListView2, productListView3, productListView4, productListView5, productListView6, productListView7, productListView8,
            productListView9, productListView10, productListView11, productListView12;
    Button submit, submitBtn;
    EditText qnty;
    Boolean result;
    EditText inputOne, inputtwo;
    public int success;
    public String message, ord_no;
    TextView date2, ded, fromdate, todate, detail_head;
    int textlength = 0;
    public TextView totqty, totval;
    //public android.widget.Spinner ordspin;
    public String userName_1, userName, active_string, act_desiredString;
    public String from_date, to_date;
    JSONParser jsonParser;
    List<NameValuePair> params;
    public String test;
    Button detail;
    public static ArrayList<String> sl;
    public static ArrayList<String> p_ids;
    public static ArrayList<Integer> p_quanty;
    public static ArrayList<String> PROD_RATE;
    public static ArrayList<String> PROD_VAT;
    public static ArrayList<String> PROD_VAT_2;
    public static ArrayList<String> PROD_VAT_3;
    public static ArrayList<String> PROD_VAT_4;
    public static ArrayList<String> PROD_VAT_5;
    public static ArrayList<String> PROD_VAT_6;
    public static ArrayList<String> PROD_VAT_7;

    public static ArrayList<String> PROD_VAT_8;

    private ArrayList<String> array_sort = new ArrayList<String>();

    private final String URL_DETAIL_VIEW = BASE_URL+"mpo_dcc_campaign/mpo_dcc_master.php";

    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.amdccfollowup);
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");


        productListView = (ListView) findViewById(R.id.pListView1);
        productListView2 = (ListView) findViewById(R.id.pListView2);
        productListView3 = (ListView) findViewById(R.id.pListView3);
        productListView4 = (ListView) findViewById(R.id.pListView4);
        productListView4 = (ListView) findViewById(R.id.pListView4);
        productListView5 = (ListView) findViewById(R.id.pListView5);


        LinearLayout l_dec = (LinearLayout) findViewById(R.id.dec);
        View v_dec = (View) findViewById(R.id.v_dec);
        NestedScrollView n_dec = findViewById(R.id.n_dec);


        LinearLayout l_nov = (LinearLayout) findViewById(R.id.nov);
        View v_nov = (View) findViewById(R.id.v_nov);
        NestedScrollView n_nov = findViewById(R.id.n_nov);


        LinearLayout l_oct = (LinearLayout) findViewById(R.id.oct);
        View v_oct = (View) findViewById(R.id.v_oct);
        NestedScrollView n_oct = findViewById(R.id.n_oct);


        LinearLayout l_sep = (LinearLayout) findViewById(R.id.sep);
        View v_sep = (View) findViewById(R.id.v_sep);
        NestedScrollView n_sep = findViewById(R.id.n_sep);


        LinearLayout l_aug = (LinearLayout) findViewById(R.id.aug);
        View v_aug = (View) findViewById(R.id.v_aug);
        NestedScrollView n_aug = findViewById(R.id.n_aug);

        LinearLayout l_jul = (LinearLayout) findViewById(R.id.jul);
        View v_jul = (View) findViewById(R.id.v_jul);
        NestedScrollView n_jul = findViewById(R.id.n_jul);


        LinearLayout l_jun = (LinearLayout) findViewById(R.id.jun);
        View v_jun = (View) findViewById(R.id.v_jun);
        NestedScrollView n_jun = findViewById(R.id.n_jun);


        LinearLayout l_may = (LinearLayout) findViewById(R.id.may);
        View v_may = (View) findViewById(R.id.v_may);
        NestedScrollView n_may = findViewById(R.id.n_may);


        LinearLayout l_apr = (LinearLayout) findViewById(R.id.apr);
        View v_apr = (View) findViewById(R.id.v_apr);
        NestedScrollView n_apr = findViewById(R.id.n_apr);


        LinearLayout l_mar = (LinearLayout) findViewById(R.id.mar);
        View v_mar = (View) findViewById(R.id.v_mar);
        NestedScrollView n_mar = findViewById(R.id.n_mar);

        LinearLayout l_feb = (LinearLayout) findViewById(R.id.feb);
        View v_feb = (View) findViewById(R.id.v_feb);
        NestedScrollView n_feb = findViewById(R.id.n_feb);


        productListView6 = (ListView) findViewById(R.id.pListView6);
        productListView7 = (ListView) findViewById(R.id.pListView7);
        productListView8 = (ListView) findViewById(R.id.pListView8);
        productListView9 = (ListView) findViewById(R.id.pListView9);
        productListView10 = (ListView) findViewById(R.id.pListView10);
        productListView11 = (ListView) findViewById(R.id.pListView11);
        productListView12 = (ListView) findViewById(R.id.pListView12);


        Button back_btn = (Button) findViewById(R.id.backbt);
        Button detail = (Button) findViewById(R.id.detail);
        detail.setTypeface(fontFamily);
        // detail.setText("\uf060 ");//
        back_btn.setTypeface(fontFamily);
        // back_btn.setText("\uf060 ");// &#xf060


        final LinearLayout ln = (LinearLayout) findViewById(R.id.totalshow);
        totqty = (TextView) findViewById(R.id.totalsellquantity);
        totval = (TextView) findViewById(R.id.totalsellvalue);
        detail_head = (TextView) findViewById(R.id.detail_head);
        int listsize = productListView.getChildCount();
        int listsize2 = productListView2.getChildCount();

        p_ids = new ArrayList<String>();
        p_quanty = new ArrayList<Integer>();
        PROD_RATE = new ArrayList<String>();
        categoriesList2 = new ArrayList<Category6>();

        Bundle b = getIntent().getExtras();
        String userName = b.getString("UserName");
        String UserName_2 = b.getString("UserName_2");


        Calendar now = Calendar.getInstance();
        int current_month = (now.get(Calendar.MONTH) + 1);


        l_aug.setVisibility(View.GONE);
        v_aug.setVisibility(View.GONE);
        n_aug.setVisibility(View.GONE);

        l_nov.setVisibility(View.GONE);
        v_nov.setVisibility(View.GONE);
        n_nov.setVisibility(View.GONE);

        l_dec.setVisibility(View.GONE);
        v_dec.setVisibility(View.GONE);
        n_dec.setVisibility(View.GONE);

        l_sep.setVisibility(View.GONE);
        v_sep.setVisibility(View.GONE);
        n_sep.setVisibility(View.GONE);

        l_oct.setVisibility(View.GONE);
        v_oct.setVisibility(View.GONE);
        n_oct.setVisibility(View.GONE);

        l_jul.setVisibility(View.GONE);
        v_jul.setVisibility(View.GONE);
        n_jul.setVisibility(View.GONE);

        l_jun.setVisibility(View.GONE);
        v_jun.setVisibility(View.GONE);
        n_jun.setVisibility(View.GONE);

        l_may.setVisibility(View.GONE);
        v_may.setVisibility(View.GONE);
        n_may.setVisibility(View.GONE);

        l_apr.setVisibility(View.GONE);
        v_apr.setVisibility(View.GONE);
        n_apr.setVisibility(View.GONE);

        l_mar.setVisibility(View.GONE);
        v_mar.setVisibility(View.GONE);
        n_mar.setVisibility(View.GONE);

        l_feb.setVisibility(View.GONE);
        v_feb.setVisibility(View.GONE);
        n_feb.setVisibility(View.GONE);


        new January().execute();


        test = userName;

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


        detail.setOnClickListener(new OnClickListener() {
            Bundle b = getIntent().getExtras();
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                pDialog = new ProgressDialog(MPODCCFollowupgift.this);
                pDialog.setMessage("Preparing DCC Campaign data ");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            Bundle b = getIntent().getExtras();
                            String userName = b.getString("UserName");
                            String UserName_1 = b.getString("userName_1");
                            String UserName_2 = b.getString("userName_2");
                            Intent i = new Intent(MPODCCFollowupgift.this, MPODCCFollowupnew.class);
                            i.putExtra("UserName", userName);
                            i.putExtra("new_version", userName);
                            i.putExtra("UserName_1", UserName_1);
                            i.putExtra("UserName_2", UserName_2);

                            i.putExtra("userName", userName);
                            i.putExtra("new_version", userName);
                            i.putExtra("userName_1", UserName_1);
                            i.putExtra("userName_2", UserName_2);
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

        pDialog.dismiss();
    }


    private void popSpinner2() {
        List<String> description2 = new ArrayList<String>();
        for (int i = 0; i < categoriesList2.size(); i++) {
            description2.add(categoriesList2.get(i).getId());
        }


    }

    public void finishActivity(View v) {
        finish();
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


            ArrayList<String> value8 = new ArrayList<String>();
            ArrayList<String> value9 = new ArrayList<String>();
            ArrayList<String> value10 = new ArrayList<String>();
            ArrayList<String> value11 = new ArrayList<String>();
            ArrayList<String> value12 = new ArrayList<String>();
            int quantity = 0;
            float prod_rate, prod_vat, sellvalue;
            String prod_rate_1, prod_vat_1, prod_vat_2, prod_vat_3, prod_vat_4, prod_vat_5, prod_vat_6, prod_vat_7, prod_vat_8, prod_vat_9, sellvalue_2, sellvalue_3;


            for (int i = 0; i < categoriesList2.size(); i++) {
                Log.i("OPSONIN", " P_ID " + categoriesList2.get(i).getId());
                Log.i("OPSONIN--", " P_ID " + categoriesList2.get(i).getsl());

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


                prod_vat_5 = categoriesList2.get(i).getPROD_VAT_5();
                value8.add(prod_vat_5);


                prod_vat_6 = categoriesList2.get(i).getPROD_VAT_6();
                value9.add(prod_vat_6);


                prod_vat_7 = categoriesList2.get(i).getPROD_VAT_7();
                value10.add(prod_vat_7);

                prod_vat_8 = categoriesList2.get(i).getPROD_VAT_8();
                value11.add(prod_vat_8);

                prod_vat_9 = categoriesList2.get(i).getPROD_VAT_9();
                value12.add(prod_vat_9);

            }


            DccCampAdapter adapter2 = new DccCampAdapter(MPODCCFollowupgift.this, sl, lables, quanty, value, value4, value5, value6, value7, value8, value9, value10, value11, value12);
            productListView.setAdapter(adapter2);


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


    private class January extends AsyncTask<Void, Void, Void> {

        Bundle b = getIntent().getExtras();
        String userName = b.getString("UserName");


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MPODCCFollowupgift.this);
            pDialog.setTitle("Data Loading !");
            pDialog.setMessage("Loading Please Wait..");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Log.e("Response: ", ">  ");


            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            String id = userName;//userName;


            Log.e(" id ", ">  test ==  " + userName);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", userName));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_DETAIL_VIEW, ServiceHandler.POST, params);
            Log.e("Response: ", "> " + json);
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj.getJSONArray("categories");
                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            Category6 cat6 = new Category6(
                                    catObj.getString("sl"),
                                    catObj.getString("id"),
                                    catObj.getString("name"),
                                    catObj.getString("quantity"),
                                    catObj.getString("PROD_RATE"),
                                    catObj.getString("PROD_VAT"),
                                    catObj.getString("PROD_VAT_2"),
                                    catObj.getString("PROD_VAT_3"),
                                    catObj.getString("PROD_VAT_4"),
                                    catObj.getString("PROD_VAT_5"),
                                    catObj.getString("PROD_VAT_6"),
                                    catObj.getString("PROD_VAT_7"),
                                    catObj.getString("PROD_VAT_8"),
                                    catObj.getString("PROD_VAT_9"),
                                    catObj.getString("PROD_VAT_10"),
                                    catObj.getString("PROD_VAT_11")

                            );
                            categoriesList2.add(cat6);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
                Toast.makeText(MPODCCFollowupgift.this, "Nothing To Disply", Toast.LENGTH_SHORT).show();
                Toast.makeText(MPODCCFollowupgift.this, "Please make a order first !", Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();

            Spinner2 sp = new Spinner2();

            sp.populateSpinner2();
            popSpinner2();


        }
    }


    @Override
    public void onClick(View v) {
    }

    protected void onPostExecute() {
    }


    private void view() {
        Intent i = new Intent(MPODCCFollowupgift.this, Report.class);
        startActivity(i);
        finish();

    }

}


