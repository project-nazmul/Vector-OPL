//ASMFollowupReport


package com.opl.pharmavector;
import static com.nativecss.enums.RemoteContentRefreshPeriod.Never;
import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import java.net.MalformedURLException;
import java.net.URL;
import java.lang.Runnable;

import java.util.ArrayList;
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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ASMFollowupReport extends Activity implements OnClickListener {
    private static Activity parent;
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    // array list for spinner adapter
    private ArrayList<com.opl.pharmavector.Category3> categoriesList;

    private ArrayList<com.opl.pharmavector.Category6> categoriesList2;

    public ProgressDialog pDialog;
    ListView productListView;
    Button submit,submitBtn;
    // private EditText current_qnty;
    EditText qnty;
    Boolean result;
    EditText inputOne, inputtwo;
    public int success;
    public String message, ord_no;
    TextView date2, ded,fromdate,todate,rname;
    int textlength = 0;
    public TextView totqty, totval;
    //public android.widget.Spinner ordspin;
    public String userName_1,userName,UserName_2,active_string,act_desiredString,user,sm_flag,sm_code,admin_flag;
    public String from_date,to_date;
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


    public static ArrayList<String> PROD_VAT_5;
    public static ArrayList<String> PROD_VAT_6;
    public static ArrayList<String> PROD_VAT_7;


    public static ArrayList<String> PROD_VAT_8;
    public static ArrayList<String> PROD_VAT_9;
    public static ArrayList<String> PROD_VAT_10;


    public static ArrayList<String> PROD_VAT_11;
    public static ArrayList<String> PROD_VAT_12;
    public static ArrayList<String> PROD_VAT_13;


    private android.widget.Spinner count_dcr;
    private ArrayList<com.opl.pharmavector.Customer> dateextendlist;
    private ArrayList<com.opl.pharmavector.Customer> mpodonedcr;

    private ArrayList<com.opl.pharmavector.Customer> mporeqdcr;

    public String get_ext_dt;


    private ArrayList<Customer> mpodcrlist;

    private ArrayList<String> array_sort = new ArrayList<String>();
    private final String URL_PRODUCT_VIEW =BASE_URL+"ASMFollowupReport.php";



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.followupreport);


        Typeface fontFamily = Typeface.createFromAsset(getAssets(),"fonts/fontawesome.ttf");
        productListView = (ListView) findViewById(R.id.pListView);
        Button back_btn = (Button) findViewById(R.id.backbt);
        back_btn.setTypeface(fontFamily);
        back_btn.setText("\uf060 ");
        int listsize = productListView.getChildCount();
        Log.i("Size of ProductLIstview", "ProductLIstView SIZE: " + listsize);
        p_ids = new ArrayList<String>();
        p_quanty = new ArrayList<Integer>();
        PROD_RATE = new ArrayList<String>();
        categoriesList = new ArrayList<Category3>();


        categoriesList2 = new ArrayList<Category6>();


        Bundle b = getIntent().getExtras();
        userName = b.getString("UserName");
        user = b.getString("UserName_2");
        sm_flag = b.getString("sm_flag");
        sm_code = b.getString("sm_code");
        admin_flag = b.getString("admin_flag");

        Log.e("getValues==>",sm_flag+sm_code+admin_flag);
        todate =  findViewById(R.id.todate);
        rname =  findViewById(R.id.rm_code);

        todate.setText("Assistant Manager followup report");

        rname.setText("Zone");

        mpodcrlist = new ArrayList<Customer>();
        dateextendlist = new ArrayList<com.opl.pharmavector.Customer>();
        mpodonedcr = new ArrayList<com.opl.pharmavector.Customer>();
        mporeqdcr = new ArrayList<com.opl.pharmavector.Customer>();



        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {


                Log.i("Selected Item in list", arg0.toString());
                String am_code = (String) productListView.getAdapter().getItem(arg2);
                Log.i("Selected Item in list", am_code);
                Intent i = new Intent(ASMFollowupReport.this, FollowupReport.class);
                i.putExtra("am_code", am_code);
                i.putExtra("UserName", am_code);
                i.putExtra("UserName_2", user);
                i.putExtra("sm_flag", sm_flag);
                i.putExtra("sm_code", sm_code);
                i.putExtra("admin_flag", admin_flag);
                startActivity(i);


            }
        });


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
                            if(admin_flag.equals("N") ) {
                                Intent i = new Intent(ASMFollowupReport.this,  SalesManagerDashboard.class);
                                i.putExtra("UserName", SalesManagerDashboard.globalSMCode);
                                i.putExtra("new_version", SalesManagerDashboard.new_version);
                                i.putExtra("UserName_2", SalesManagerDashboard.globalDivisionCode);
                                i.putExtra("message_3", SalesManagerDashboard.message_3);
                                i.putExtra("password", SalesManagerDashboard.password);
                                i.putExtra("ff_type", SalesManagerDashboard.ff_type);
                                i.putExtra("vector_version", R.string.vector_version);
                                i.putExtra("emp_code", SalesManagerDashboard.globalempCode);
                                i.putExtra("emp_name", SalesManagerDashboard.globalempName);
                                startActivity(i);
                            }else if( admin_flag.equals("Y"))
                            {
                                Intent i = new Intent(ASMFollowupReport.this,  GMDashboard.class);
                                i.putExtra("UserName", GMDashboard1.globalAdmin);
                                i.putExtra("new_version", GMDashboard1.new_version);
                                i.putExtra("UserName_2", GMDashboard1.globalAdminDtl);
                                i.putExtra("message_3", GMDashboard1.message_3);
                                i.putExtra("password", GMDashboard1.password);
                                i.putExtra("ff_type", GMDashboard1.ff_type);
                                i.putExtra("vector_version", R.string.vector_version);
                                i.putExtra("emp_code", GMDashboard1.globalempCode);
                                i.putExtra("emp_name", GMDashboard1.globalempName);
                                startActivity(i);

                            }




                            //finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });

                backthred.start();
            

            }
        });



    }



    private void popSpinner() {
        List<String> description = new ArrayList<String>();
        for (int i = 0; i < categoriesList2.size(); i++) {
            Log.d("Changepa---ssword",categoriesList2.get(i).getId());
            description.add(categoriesList2.get(i).getId());
            Log.d("Changep---assword","Login"+categoriesList2.get(i).getId());
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
            ArrayList<String> value9 = new ArrayList<String>();
            ArrayList<String> value10 = new ArrayList<String>();
            ArrayList<String> value11 = new ArrayList<String>();



            ArrayList<String> value12 = new ArrayList<String>();
            ArrayList<String> value13 = new ArrayList<String>();
            ArrayList<String> value14 = new ArrayList<String>();
            ArrayList<String> value15 = new ArrayList<String>();
            ArrayList<String> value16 = new ArrayList<String>();



            ArrayList<String> value17 = new ArrayList<String>();
            ArrayList<String> value18 = new ArrayList<String>();
            ArrayList<String> value19 = new ArrayList<String>();
            ArrayList<String> value20 = new ArrayList<String>();




            int quantity = 0;
            float prod_rate, prod_vat, sellvalue;
            String prod_rate_1, prod_vat_1,prod_vat_2,prod_vat_3,prod_vat_4,prod_vat_5,prod_vat_6,prod_vat_7,prod_vat_8,prod_vat_9,prod_vat_10,prod_vat_11,prod_vat_12,prod_vat_13,
                    prod_vat_14,prod_vat_15,
                    sellvalue_2,sellvalue_3;



            for (int i = 0; i < categoriesList2.size(); i++) {
                Log.i("OPSONIN", " P_ID " + categoriesList2.get(i).getId());
                Log.i("OPSONIN--", " P_ID " + categoriesList2.get(i).getsl());

                sl.add(categoriesList2.get(i).getsl());

                lables.add(categoriesList2.get(i).getName());

                p_ids.add(categoriesList2.get(i).getId());

                quanty.add(categoriesList2.get(i).getQuantity());




                prod_rate_1 = categoriesList2.get(i).getPROD_RATE();
                value.add(prod_rate_1);


                prod_vat_1= categoriesList2.get(i).getPROD_VAT();
                value4.add(prod_vat_1);




                prod_vat_2= categoriesList2.get(i).getPROD_VAT_2();
                value5.add(prod_vat_2);




                prod_vat_3= categoriesList2.get(i).getPROD_VAT_3();
                value6.add(prod_vat_3);



                prod_vat_4= categoriesList2.get(i).getPROD_VAT_4();
                value7.add(prod_vat_4);




                prod_vat_5= categoriesList2.get(i).getPROD_VAT_5();
                value8.add(prod_vat_5);





                prod_vat_6= categoriesList2.get(i).getPROD_VAT_6();
                value9.add(prod_vat_6);



                prod_vat_7= categoriesList2.get(i).getPROD_VAT_7();
                value10.add(prod_vat_7);





                prod_vat_8= categoriesList2.get(i).getPROD_VAT_8();
                value11.add(prod_vat_8);



                prod_vat_9= categoriesList2.get(i).getPROD_VAT_9();
                value12.add(prod_vat_9);



                prod_vat_10= categoriesList2.get(i).getPROD_VAT_10();
                value13.add(prod_vat_10);
                Log.w("FOLLOWUPvalue14",prod_vat_10);



                prod_vat_11= categoriesList2.get(i).getPROD_VAT_11();
                value14.add(prod_vat_11);

                Log.w("FOLLOWUPvalue15",prod_vat_11);

                prod_vat_12= categoriesList2.get(i).getPROD_VAT_12();
                value15.add(prod_vat_12);
                Log.w("FOLLOWUPvalue16",prod_vat_12);


                prod_vat_13= categoriesList2.get(i).getPROD_VAT_13();
                value16.add(prod_vat_13);
                Log.w("FOLLOWUPvalue17",prod_vat_13);


            }

            //  MpoDailyMonitorShowAdapter adapter = new MpoDailyMonitorShowAdapter(ASMFollowupReport.this,sl,lables, quanty, value,value4,value5,value6,value7);

            // RmDcrFollowupAdapter adapter = new RmDcrFollowupAdapter(ASMFollowupReport.this,  sl,lables, quanty, value,value4,value5,value6,value7);

            //  RmDcrFollowupAdapter adapter = new RmDcrFollowupAdapter(ASMFollowupReport.this,  sl,lables, quanty, value,value4,value5,value6,value7,value8,value9,value10,value11);


            //  RmDcrFollowupAdapter adapter = new RmDcrFollowupAdapter(ASMFollowupReport.this,  sl,lables, quanty, value,value4,value5,value6,value7,value8,value9,value10,value11,value12,value13,
            //       value14,value15);


            RmDcrFollowupAdapter adapter = new RmDcrFollowupAdapter(ASMFollowupReport.this,  sl,lables, quanty, value,value4,value5,value6,value7,value8,value9,value10,value11,value12,value13,
                    value14,value15,value16,value17);



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
            pDialog = new ProgressDialog(ASMFollowupReport.this);
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
            String id = sm_code;
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", id));
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


                            com.opl.pharmavector.Category6 cat3 = new com.opl.pharmavector.Category6(
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
                                    catObj.getString("PROD_VAT_11"),
                                    catObj.getString("PROD_VAT_12"),
                                    catObj.getString("PROD_VAT_13")
                            );
                            categoriesList2.add(cat3);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
                Toast.makeText(ASMFollowupReport.this, "Nothing To Disply",Toast.LENGTH_SHORT).show();
                Toast.makeText(ASMFollowupReport.this, "Please make a order first !",Toast.LENGTH_LONG).show();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            ASMFollowupReport.Spinner sp = new ASMFollowupReport.Spinner();
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
        Intent i = new Intent(ASMFollowupReport.this, Report.class);
        startActivity(i);
        finish();

    }

}

