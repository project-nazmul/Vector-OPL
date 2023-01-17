//package com.opl.pharmavector;
 //ChemistGiftCampaign


package com.opl.pharmavector;



import static com.opl.pharmavector.remote.ApiClient.BASE_URL_RM;

import java.lang.Runnable;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
public class ChemistGiftCampaign extends Activity implements OnClickListener {
    private static Activity parent;
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    // array list for spinner adapter
    private ArrayList<Category3> categoriesList;

    private ArrayList<Category3> categoriesList2;

    private ArrayList<Category3> categoriesList3;
    public ProgressDialog pDialog;
    ListView productListView;
    ListView productListView2;
    ListView productListView3;

    Button submit,submitBtn;


    // private EditText current_qnty;
    EditText qnty;
    Boolean result;
    EditText inputOne, inputtwo;
    public int success;
    public String message, ord_no;
    TextView date2, ded,fromdate,todate,detail_head,detail_head3;
    int textlength = 0;
    public TextView totqty, totval;
    //public android.widget.Spinner ordspin;
    public String userName_1,userName,active_string,act_desiredString;
    public String from_date,to_date;
    JSONParser jsonParser;
    List<NameValuePair> params;
    public  String test,test1,test2;
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


    private ArrayList<String> array_sort = new ArrayList<String>();

    private final String URL_PRODUCT_VIEW =BASE_URL_RM+"ChemistGiftCampaign.php";
    private final String URL_DETAIL_VIEW =BASE_URL_RM+"ChemistGiftCampaignDetail.php";
    private final String URL_DETAIL_VIEW3 =BASE_URL_RM+"ChemistGiftCampaignDetailterritory.php";

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.chemistgiftcampaignfollowup);

        Typeface fontFamily = Typeface.createFromAsset(getAssets(),"fonts/fontawesome.ttf");
        productListView = (ListView) findViewById(R.id.pListView);
        productListView2 = (ListView) findViewById(R.id.pListView2);
        productListView3 = (ListView) findViewById(R.id.pListView3);
        Button back_btn = (Button) findViewById(R.id.backbt);
        back_btn.setTypeface(fontFamily);
        back_btn.setText("\uf060 ");// &#xf060

        final LinearLayout ln = (LinearLayout) findViewById(R.id.totalshow);
        totqty = (TextView) findViewById(R.id.totalsellquantity);
        totval = (TextView) findViewById(R.id.totalsellvalue);
        detail_head = (TextView) findViewById(R.id.detail_head);

        detail_head3 = (TextView) findViewById(R.id.detail_head3);

        int listsize = productListView.getChildCount();
        Log.i("Size of ProductLIstview", "ProductLIstView SIZE: " + listsize);

        int listsize2 = productListView2.getChildCount();
        Log.i("SizeofProductLIstview2", "ProductLIstView2 SIZE: " + listsize2);

        p_ids = new ArrayList<String>();
        p_quanty = new ArrayList<Integer>();
        PROD_RATE = new ArrayList<String>();
        categoriesList = new ArrayList<Category3>();


        categoriesList2 = new ArrayList<Category3>();
        categoriesList3 = new ArrayList<Category3>();

        Bundle b = getIntent().getExtras();
        String userName = b.getString("UserName");
        Toast.makeText(ChemistGiftCampaign.this, userName, Toast.LENGTH_LONG).show();

        new GetCategories().execute();








        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                productListView2.setAdapter(null);

                test = (String) productListView.getAdapter().getItem(arg2);
                String i=String.valueOf(arg3);
                Integer result = Integer.valueOf(i);

                categoriesList2.clear();
                categoriesList3.clear();
                new GetCategories2().execute();
                detail_head.setText("Area wise information of item - "+ test);

                detail_head3.setText("Territory wise promotional item information " );
                productListView3.setAdapter(null);


            }
        });


        productListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                productListView3.setAdapter(null);

                test1 = (String) productListView2.getAdapter().getItem(arg2);
                Log.i("Selected Item in list", test1);

                Log.i("Selected Item in list", String.valueOf(arg1));
                Log.i("Selected Item in list", String.valueOf(arg3));

                String i=String.valueOf(arg3);
                Integer result = Integer.valueOf(i);

                categoriesList3.clear();
                new GetCategories3().execute();
                detail_head3.setText("Territory wise promotional item information of - "+  test1 );

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

                            Intent i = new Intent(ChemistGiftCampaign.this,  RmDashboard.class);
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



    }



    private void popSpinner() {
        List<String> description = new ArrayList<String>();
        for (int i = 0; i < categoriesList.size(); i++) {
            Log.d("Changepa---ssword",categoriesList.get(i).getId());
            description.add(categoriesList.get(i).getId());
            Log.d("Changep---assword","Login"+categoriesList.get(i).getId());
        }


    }

    private void popSpinner2() {
        List<String> description2 = new ArrayList<String>();
        for (int i = 0; i < categoriesList2.size(); i++) {
            description2.add(categoriesList2.get(i).getId());
        }

    }

    private void popSpinner3() {
        List<String> description3 = new ArrayList<String>();
        for (int i = 0; i < categoriesList3.size(); i++) {
            description3.add(categoriesList3.get(i).getId());

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



            //   MPODccFollowupAdapter adapter = new MPODccFollowupAdapter(ChemistGiftCampaign.this,sl,lables, quanty, value,value4,value5,value6,value7);


           // AMDccFollowupAdapter adapter = new AMDccFollowupAdapter(ChemistGiftCampaign.this,sl,lables, quanty, value,value4,value5,value6,value7);


            ChemistGiftCampaignAdapter adapter = new ChemistGiftCampaignAdapter(ChemistGiftCampaign.this,sl,lables, quanty, value,value4,value5,value6,value7);

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
            String prod_rate_1, prod_vat_1,prod_vat_2,prod_vat_3,prod_vat_4, sellvalue_2,sellvalue_3;



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

                prod_vat_1= categoriesList2.get(i).getPROD_VAT();
                value4.add(prod_vat_1);


                prod_vat_2= categoriesList2.get(i).getPROD_VAT_2();
                value5.add(prod_vat_2);


                prod_vat_3= categoriesList2.get(i).getPROD_VAT_3();
                value6.add(prod_vat_3);

                prod_vat_4= categoriesList2.get(i).getPROD_VAT_4();
                value7.add(prod_vat_4);

            }






            ChemistGiftCampaignAdapter adapter2 = new ChemistGiftCampaignAdapter(ChemistGiftCampaign.this,sl,lables, quanty, value,value4,value5,value6,value7);


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
            String prod_rate_1, prod_vat_1,prod_vat_2,prod_vat_3,prod_vat_4, sellvalue_2,sellvalue_3;



            for (int i = 0; i < categoriesList3.size(); i++) {
                Log.i("OPSONIN", " P_ID " + categoriesList3.get(i).getId());
                Log.i("OPSONIN--", " P_ID " + categoriesList3.get(i).getsl());

                sl.add(categoriesList3.get(i).getsl());

                lables.add(categoriesList3.get(i).getName());
                p_ids.add(categoriesList3.get(i).getId());
                quanty.add(categoriesList3.get(i).getQuantity());

                //quantity = categoriesList.get(i).getQuantity();
                prod_rate_1 = categoriesList3.get(i).getPROD_RATE();
                sellvalue_2 = prod_rate_1;
                value.add(sellvalue_2);

                prod_vat_1= categoriesList3.get(i).getPROD_VAT();
                value4.add(prod_vat_1);


                prod_vat_2= categoriesList3.get(i).getPROD_VAT_2();
                value5.add(prod_vat_2);


                prod_vat_3= categoriesList3.get(i).getPROD_VAT_3();
                value6.add(prod_vat_3);

                prod_vat_4= categoriesList3.get(i).getPROD_VAT_4();
                value7.add(prod_vat_4);

            }






            ChemistGiftCampaignAdapter adapter2 = new ChemistGiftCampaignAdapter(ChemistGiftCampaign.this,sl,lables, quanty, value,value4,value5,value6,value7);


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


    private class GetCategories extends AsyncTask<Void, Void, Void> {
        Bundle b = getIntent().getExtras();
        String userName = b.getString("UserName");
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ChemistGiftCampaign.this);
            pDialog.setTitle("Data Loading !");
            pDialog.setMessage("Loading Please Wait..");
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            Log.e("Response: ", ">  ");



            Log.e(" dcr_no: ", ">  dcr_no ==  "+ord_no);

            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            String id = userName;


            Log.e(" id ", ">  id ==  "+id);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", id));
            //  params.add(new BasicNameValuePair("ord_no", ord_no));
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
                Log.e("JSON Data", "Didn't receive any data from server!");
                Toast.makeText(ChemistGiftCampaign.this, "Nothing To Disply",Toast.LENGTH_SHORT).show();
                Toast.makeText(ChemistGiftCampaign.this, "Please make a order first !",Toast.LENGTH_LONG).show();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            Spinner sp = new Spinner();
            sp.populateSpinner();
            popSpinner();


        }
    }


    private class GetCategories2 extends AsyncTask<Void, Void, Void> {
        String userName = test;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ChemistGiftCampaign.this);
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
            String id = userName;


            Log.e(" id ", ">  test ==  "+test);

            Log.e(" id2 ", ">  id2 ==  "+id);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", test));
            params.add(new BasicNameValuePair("id2", id));

            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_DETAIL_VIEW,ServiceHandler.POST, params);
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
                                    catObj.getString("PROD_VAT_4"));
                            categoriesList2.add(cat3);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
                Toast.makeText(ChemistGiftCampaign.this, "Nothing To Disply",Toast.LENGTH_SHORT).show();
                Toast.makeText(ChemistGiftCampaign.this, "Please make a order first !",Toast.LENGTH_LONG).show();
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
    /*------------- list items on click event----------------*/

    private class GetCategories3 extends AsyncTask<Void, Void, Void> {


        String userName = test;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ChemistGiftCampaign.this);
            pDialog.setTitle("Data Loading !");
            pDialog.setMessage("Territory wise promotional item information..");
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            Log.e("Response: ", ">  ");


            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            String id = userName;


            Log.e(" id ", ">  test ==  "+test);
            Log.e(" id ", ">  test1 ==  "+test1);
            Log.e(" id2 ", ">  id2 ==  "+id);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", test));
            params.add(new BasicNameValuePair("id2", id));
            params.add(new BasicNameValuePair("id3", test1));

            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_DETAIL_VIEW3,ServiceHandler.POST, params);
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
                                    catObj.getString("PROD_VAT_4"));
                            categoriesList3.add(cat3);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
                Toast.makeText(ChemistGiftCampaign.this, "Nothing To Disply",Toast.LENGTH_SHORT).show();
                Toast.makeText(ChemistGiftCampaign.this, "Please make a order first !",Toast.LENGTH_LONG).show();
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


    @Override
    public void onClick(View v) {
    }

    protected void onPostExecute() {
    }


    private void view() {
        Intent i = new Intent(ChemistGiftCampaign.this, Report.class);
        startActivity(i);
        finish();

    }

}


