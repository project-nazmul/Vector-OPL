package com.opl.pharmavector;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.opl.pharmavector.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
//import android.content.ActivityNotFoundException;
//import android.content.Intent;
//import android.os.Bundle;
//import android.speech.RecognizerIntent;

public class MPODCCSplit extends Activity implements OnClickListener {

    public static final String TAG_SUCCESS = "success";
    public static final String TAG_SUCCESS1 = "success_1";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_MESSAGE_1 = "message_1";
    public static final String TAG_MESSAGE_2 = "message_2";
    public static final String TAG_ord_no = "ord_no";
    public static final String TAG_invoice = "invoice";
    public static final String TAG_target = "target";
    public static final String TAG_achivement = "achivement";
    public String userName_1,userName;
    // array list for spinner adapter
    static ArrayList<Category> categoriesList;

    public String get_ext_dt3;
    public String public_rest_quant;

    public int p_rest_quant;

    private ArrayList<Customer> brandlist;
    private Spinner cust;

    ProgressDialog pDialog;
    static ListView productListView;
    DCCSplitAdapter adapter2;
    Button submit;
    // private EditText current_qnty;
    public static EditText qnty, searchview;
    EditText inputOne, inputtwo;
    public int success,success_1;
    public String message, ord_no,invoice,target,achivement,searchString,message_1,message_2;
    int textlength = 0;
    DCCSplitAdapter adapter;
    JSONParser jsonParser;
    List<NameValuePair> params;
    static TextView totalsellquantity;
    static TextView totalsellvalue;
    static TextView heading;


    public TextView  hqnty1;

    public String split_prod;
    public LinearLayout totalshow;
    public Button calc;
    public static ArrayList<String> p_ids;
    public static ArrayList<Integer> p_quanty;
    public static ArrayList<String> PROD_RATE;
    public static ArrayList<String> PROD_VAT;
    ArrayList<String> lables;
    ArrayList<Integer> quanty;
    HashMap<Integer, String> mapQuantity;
    static HashMap<String, Integer> nameSerialPair;
    ArrayList<String> sl;
    /*- Initializing*/
    String last_quantity = "1";
    int last_position = 1;
    String quantity = "1";
    public String mpo_code;
    Toast toast1;
    ArrayList<Category> arraylist = new ArrayList<Category>();
    private final int REQ_CODE_SPEECH_INPUT = 100;
    public static String URL_NEW_CATEGORY = BASE_URL+"update_dcc_product.php";
    private final String URL_CATEGORIES = BASE_URL+"dcc_split_product.php";
    private final String campaign_credit = BASE_URL+"get_campaign_products.php";
    private final String REQ_MPO =BASE_URL+"dcc_split_validation.php";




    private ArrayList<com.opl.pharmavector.AmCustomer> mporeqdcr;

    private ArrayList<AmCustomer> mpodcrlist;
    @SuppressLint("DefaultLocale")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.productorder);
        Typeface fontFamily = Typeface.createFromAsset(getAssets(),"fonts/fontawesome.ttf");
        submit = (Button) findViewById(R.id.submitBtn);
        submit.setTypeface(fontFamily);
        submit.setText("\uf1d8"); // &#xf1d8
        productListView = (ListView) findViewById(R.id.pListView);
        productListView.setDescendantFocusability(ListView.FOCUS_AFTER_DESCENDANTS);
        TextView showorders=(TextView)findViewById(R.id.showorders);
        showorders.setTypeface(fontFamily);
        showorders.setText("\uf055");//&#xf055
        Button back_btn = (Button) findViewById(R.id.backBtn);
        back_btn.setTypeface(fontFamily);
        back_btn.setText("\uf060 ");// &#xf060
        calc = (Button) findViewById(R.id.calc);
        calc.setTypeface(fontFamily);
        calc.setText("\uf1ec"); // &#xf01e &#xf1ec
        calc.setOnClickListener(this);

        calc.setVisibility(View.GONE);

        searchview = (EditText) findViewById(R.id.p_search);
        TextView search = (TextView) findViewById(R.id.search);

        heading = (TextView) findViewById(R.id.heading);

        hqnty1 = (TextView) findViewById(R.id.hqnty1);


        heading.setText("DCC Quantity in hand to split");

        hqnty1.setText("Quantity");


        mpodcrlist = new ArrayList<AmCustomer>();
        mporeqdcr = new ArrayList<com.opl.pharmavector.AmCustomer>();





        search.setTypeface(fontFamily);
        search.setText("\uf056"); // &#xf002 , &#xf010
        TextView mic=(TextView)findViewById(R.id.mic);
        mic.setTypeface(fontFamily);
        mic.setText("\uf130");
        toast1 = Toast.makeText(MPODCCSplit.this, "Order quantity must be equal to your remaining allocated DCC", Toast.LENGTH_LONG);

        search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    searchview.setText("");
                    //qnty.clearFocus();
                    searchview.requestFocus();
                }
                catch (Exception e) {
                    // TODO: handle exception
                    //DCCSplitAdapter.qnty.c
                }

            }
        });

        totalsellquantity = (TextView) findViewById(R.id.totalsellquantity);
        totalsellquantity.setVisibility(View.GONE);

        totalsellvalue = (TextView) findViewById(R.id.totalsellvalue);
        Spinner am_pm = (Spinner) findViewById(R.id.ampm);
        Spinner cash_credit = (Spinner) findViewById(R.id.cashcredit);
        Spinner credit = (Spinner) findViewById(R.id.credit);

        totalshow = (LinearLayout) findViewById(R.id.totalshow);
        // totalshow.setOnClickListener(this);
        // Button print=(Button)findViewById(R.id.print);
        DCCSplitAdapter.qnty = null;
        DCCSplitAdapter.qntyID.clear();
        DCCSplitAdapter.qntyVal.clear();
        p_ids = new ArrayList<String>();
        p_quanty = new ArrayList<Integer>();
        mapQuantity = new HashMap<Integer, String>();
        nameSerialPair = new HashMap<String, Integer>();
        PROD_RATE = new ArrayList<String>();
        PROD_VAT = new ArrayList<String>();
        categoriesList = new ArrayList<Category>();
        Intent in = getIntent();
        Intent inten = getIntent();
        Bundle bundle = in.getExtras();


        // 5. get status value from bundle
        Bundle extra = inten.getExtras();
        final String MPO_CODE = extra.getString("MPO_CODE");
        Bundle b = getIntent().getExtras();
        final String userName = b.getString("UserName");
        final String brand_code=b.getString("brand_code");
        final String rest_quant=b.getString("rest_quant");
        mpo_code=userName;
        split_prod=b.getString("p_code");

        Log.w("dccsplitsctivity", brand_code + "---" + userName + "---------reaminquant -----" + rest_quant + "---splitflag-----"+split_prod+"mpo_code"+mpo_code  );




        Toast.makeText(MPODCCSplit.this, userName, Toast.LENGTH_LONG).show();

        new REQ_MPO().execute();

        submit.setOnClickListener(this);


     //   totalsellvalue.setText(rest_quant);



        brandlist = new ArrayList<Customer>();
        //	cust.setOnItemSelectedListener(this);


        back_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Inflater inf = new Inflater();
                inf.end();
                finish();
			/*	Intent intent = new Intent(MPODCCSplit.this,
						ReadComments.class);
				intent.putExtra("UserName", MPO_CODE);
				finishActivity(v);
				startActivity(intent);*/

            }
        });
        showorders.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                searchString="1";
                //	searchview.setText(null);
                //	searchview.setVisibility(LinearLayout.GONE);;
                adapter.getFilter().filter(searchString);
            }});
        searchview.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Search Feild clicked");
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(searchview, InputMethodManager.SHOW_IMPLICIT);

                searchview.setFocusable(true);
                searchview.setFocusableInTouchMode(true);
                searchview.setClickable(true);
                searchview.setText("");
            }
        });


        searchview.addTextChangedListener(new TextWatcher() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                ArrayList<String> resList = new ArrayList<String>();
                ArrayList<Integer> resList2 = new ArrayList<Integer>();
                String searchString = s.toString().toLowerCase();
                //searchview.requestFocus();
                Log.i("HIRA"," searchString "+searchString+" adapter "+adapter);
                if(searchString!=null && adapter!=null){
                    adapter.getFilter().filter(searchString);
                }
                //adapter.getFilter().filter(s);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                String text = searchview.getText().toString().toLowerCase(Locale.getDefault());

            }
        });



       new GetCategories().execute();



        TextView clickme = (TextView) findViewById(R.id.clickme);
        clickme.setOnTouchListener(new View.OnTouchListener() {

            private Handler mHandler;
            private long mInitialDelay = 100;
            private long mRepeatDelay = 80;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (mHandler != null)
                            return true;
                        mHandler = new Handler();
                        mHandler.postDelayed(mAction, mInitialDelay);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (mHandler == null)
                            return true;
                        mHandler.removeCallbacks(mAction);
                        mHandler = null;
                        break;
                }
                return false;
            }

            Runnable mAction = new Runnable() {
                @Override
                public void run() {
                    // LinearLayout
                    // listview=(LinearLayout)findViewById(R.id.listview);
                    productListView.scrollTo(
                            (int) productListView.getScrollX(),
                            (int) productListView.getScrollY() + 11);
                    mHandler.postDelayed(mAction, mRepeatDelay);
                }
            };

        });



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    searchview.setText(result.get(0));
                }
                break;
            }

        }
    }
    /*------------Extra menus end-------------------*/
    public void swapfocus(View v){
        //	DCCSplitAdapter.focusvalue=0;
        if (DCCSplitAdapter.focusvalue==0) {
            v.clearFocus();
            searchview.setFocusable(true);

            Log.e("FocusVal", ""+DCCSplitAdapter.focusvalue);

        }
        else {
            Log.e("FocusVal", ""+DCCSplitAdapter.focusvalue);
            v.clearFocus();
            searchview.setFocusable(false);


        }
    }
    public void finishActivity(View v) {
        finish();
    }

    /**
     * Adding spinner data
     * */

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        // System.out.println("EditTxtID " + DCCSplitAdapter.editTxtID.size());
        super.onResume();

    }

    private void populateSpinner() {

        lables = new ArrayList<String>();
        quanty = new ArrayList<Integer>();
        sl = new ArrayList<String>();

        for (int i = 0; i < categoriesList.size(); i++) {
            lables.add(categoriesList.get(i).getName());
            sl.add(categoriesList.get(i).getsl());
            // p_ids.add(categoriesList.get(i).getId());

            p_ids.add(categoriesList.get(i).getId());
            PROD_RATE.add(categoriesList.get(i).getPROD_RATE());
            PROD_VAT.add(categoriesList.get(i).getPROD_VAT());
            //   PROD_VAT_2.add(categoriesList.get(i).getPROD_VAT2());

            nameSerialPair.put(categoriesList.get(i).getName(),
                    Integer.parseInt(categoriesList.get(i).getsl()));

            // p_quanty.add(categoriesList.get(i).getQuantity());

            // Log.i("OPSONIN", " P_ID " + categoriesList.get(i).getId());

            int p_serial = Integer.parseInt(categoriesList.get(i).getsl());
            // quanty.add(Integer.parseInt(categoriesList.get(i).getQuantity()));
            quanty.add(categoriesList.get(i).getQuantity());
            mapQuantity.put(p_serial, String.valueOf(categoriesList.get(i).getQuantity()));

            // quanty.add(10.0f);
        }
        Log.e("TotalProducts",""+categoriesList.size());

        adapter = new DCCSplitAdapter(MPODCCSplit.this, sl, lables, mapQuantity);

        // productListView.setSelection(300);
        productListView.setAdapter(adapter);

    }



    private class GetCategories extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MPODCCSplit.this);
            pDialog.setTitle("Please wait !");
            pDialog.setMessage("Loading Products..");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {


            Intent in = getIntent();
            Intent inten = getIntent();
            Bundle bundle = in.getExtras();


            // 5. get status value from bundle
            Bundle extra = inten.getExtras();
             String MPO_CODE = extra.getString("MPO_CODE");

            Bundle b = getIntent().getExtras();
            final String userName = b.getString("UserName");
            final String brand_code=b.getString("brand_code");
            final String rest_quant=b.getString("rest_quant");
            final String mpo_code=b.getString("mpo_code");

            public_rest_quant=b.getString("rest_quant");

          //  p_rest_quant = Integer.valueOf(public_rest_quant);


                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("MPO_CODE", userName));
                params.add(new BasicNameValuePair("BRAND_CODE", brand_code));
                Log.e("mpo_code: ", "> " + userName);

                Log.e("brand_code-----------: ", "> " + brand_code);
                ServiceHandler jsonParser = new ServiceHandler();
                String json = jsonParser.makeServiceCall(URL_CATEGORIES,ServiceHandler.GET,params);
                Log.e("mpo_code-------- ", "> " + mpo_code);
                Log.e("Response: ", "> " + json);

                if (json != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(json);
                        if (jsonObj != null) {
                            JSONArray categories = jsonObj
                                    .getJSONArray("categories");
                            for (int i = 0; i < categories.length(); i++) {
                                JSONObject catObj = (JSONObject) categories.get(i);
                                Category cat = new Category(catObj.getString("sl"),
                                        catObj.getString("id"),
                                        catObj.getString("name"),
                                        catObj.getInt("quantity"),
                                        catObj.getString("PROD_RATE"),
                                        catObj.getString("PROD_VAT")


                                );
                                categoriesList.add(cat);
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


/*=================================================================================================================================================*/

    private void  populatreqmpo() {
        List<String> lables = new ArrayList<String>();

        for (int i = 0; i <mporeqdcr.size(); i++) {
            get_ext_dt3= mporeqdcr.get(i).getName();
        }

      //  req_mpo.setText(get_ext_dt3);


        totalsellvalue.setText(get_ext_dt3);

    }














    private class REQ_MPO extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            Bundle b = getIntent().getExtras();
            final String userName = b.getString("UserName");
            final String brand_code=b.getString("brand_code");
            final String rest_quant=b.getString("rest_quant");
            final String mpo_code=b.getString("mpo_code");
            String id=userName;




            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", id));
            params.add(new BasicNameValuePair("brand_code", brand_code));
            Log.e("mpo_code: ", "> " + userName);



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


    /*=============================================================================================================================================*/


    @Override
    public void onClick(View v) {

        if (v.getId() == submit.getId()) {

            jsonParser = new JSONParser();
            params = new ArrayList<NameValuePair>();

            Intent in = getIntent();
            Intent inten = getIntent();
            Bundle bundle = in.getExtras();
            Bundle extra = inten.getExtras();
            String last_quantity = DCCSplitAdapter.qnty;
            int last_position = DCCSplitAdapter.last_position;

            if (last_quantity != null) {
                DCCSplitAdapter.dataSet.put(last_position, last_quantity);
                DCCSplitAdapter.qntyID.add(last_position);
                DCCSplitAdapter.qntyVal.add(last_quantity);
                DCCSplitAdapter.set2.add(last_position);
            }
            if (DCCSplitAdapter.qntyID.size() < 1) {
                Toast.makeText(this, "No item inserted", Toast.LENGTH_SHORT).show();
            }


            else {


                String qnty;

                ArrayList<Integer> position = new ArrayList<Integer>();

                for (int j : DCCSplitAdapter.set2) {
                    position.add(j);
                }


                int k=0;
                int submit_value=0;
                for (int i = 1; i < DCCSplitAdapter.p_quanty.size(); i++) {


                    int value = Integer.parseInt(DCCSplitAdapter.p_quanty.get(i));


                  //  if (value>0) {


                        submit_value=submit_value+value;

                        k=k+1;

                        params.add(new BasicNameValuePair("total", String.valueOf(k)));
                        params.add(new BasicNameValuePair("id"+String.valueOf(k), p_ids.get(i - 1)));
                        params.add(new BasicNameValuePair("ORD_QNTY"+ String.valueOf(k),DCCSplitAdapter.p_quanty.get(i)));
                        params.add(new BasicNameValuePair("PROD_RATE"+ String.valueOf(k), PROD_RATE.get(i - 1)));
                        params.add(new BasicNameValuePair("PROD_VAT"+ String.valueOf(k), PROD_VAT.get(i - 1)));


                        System.out.println("id--k"+String.valueOf(k)+"="+ p_ids.get(k- 1));
                        System.out.println("total"+"="+ String.valueOf(k));

                        System.out.println("id"+String.valueOf(k)+"="+ p_ids.get(i- 1));
                        System.out.println("ORD_QNTY"+String.valueOf(k)+"="+ DCCSplitAdapter.p_quanty.get(i));
                        System.out.println("PROD_RATE"+String.valueOf(k)+"="+ PROD_RATE.get(i- 1));
                        System.out.println("PROD_VAT"+String.valueOf(k)+"="+ PROD_VAT.get(i- 1));


                        Log.e("submittedquantity",String.valueOf(submit_value));

                   // }

                }

                params.add(new BasicNameValuePair("split_product_code", split_prod));
                params.add(new BasicNameValuePair("MPO_CODE", userName));


                if(submit_value== Integer.valueOf(get_ext_dt3) ) {

                    Log.e("validationdcc" + "" , get_ext_dt3);

                    final ProgressDialog progress = ProgressDialog.show(this, "Updating DCC", "Please Wait..", true);

                    Thread server = new Thread(new Runnable() {
                        @Override
                        public void run() {


                            // TODO Auto-generated method stub
                            JSONObject json = jsonParser.makeHttpRequest(URL_NEW_CATEGORY, "POST", params);
                            progress.dismiss();
                            Log.w("brforetry" + message, json.toString());

                            try {
                                success = json.getInt(TAG_SUCCESS);
                                success_1 = json.getInt(TAG_SUCCESS1);
                                message = json.getString(TAG_MESSAGE);
                                message_2 = json.getString(TAG_MESSAGE_2);
                                ord_no = json.getString(TAG_ord_no);


                                Log.w("intryloop" + message, json.toString());
                                if (success_1 == 1) {

                                    // startActivity(i);
                                    DCCSplitAdapter.qnty = null;
                                    DCCSplitAdapter.qntyID.clear();
                                    DCCSplitAdapter.qntyVal.clear();
                                    DCCSplitAdapter.set2.clear();
                                    DCCSplitAdapter.dataSet.clear();
                                    DCCSplitAdapter.p_quanty.clear();
                                    DCCSplitAdapter.mProductSerialList.clear();

                                } else {

                                }

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                            Intent in = getIntent();
                            Intent inten = getIntent();
                            Bundle bundle = in.getExtras();
                            inten.getExtras();
                            String MPO_CODE = bundle.getString("MPO_CODE");





                            Intent i = new Intent(MPODCCSplit.this, MPODccStock.class);
                            i.putExtra("UserName", mpo_code);
                            i.putExtra("UserName_2", mpo_code);
                            i.putExtra("mpo_code", mpo_code);
                            Log.w("Passedtodccstock", mpo_code + "---"+ mpo_code);
                            startActivity(i);



                        }

                    });

                    server.start();
                }else{

                    toast1.show();


                }
            }

        }

        /*----------------------*/
        if (v.getId() == calc.getId()) {
            float sum = 0f;
            int qnty1 = 0;
            float rate = 0f;
            float product_value = 0f;
            int total_item = DCCSplitAdapter.p_quanty.size();
            // System.out.println("total_item " + total_item);
            for (int i = 1; i < total_item; i++) {
                int value = Integer.parseInt(DCCSplitAdapter.p_quanty.get(i));
                // System.out.println("value " + value);
                if (value > 0) {
                    qnty1 = Integer.parseInt(DCCSplitAdapter.p_quanty.get(i));
                    rate = Float.parseFloat(PROD_RATE.get(i - 1));
                    System.out.println("qnty1 " + qnty1);
                    System.out.println("rate " + rate);
                    product_value = qnty1 * rate;
                    sum = sum + product_value;
                }
            }

            totalsellquantity.setVisibility(v.VISIBLE);
            String test=String.valueOf(sum );
            System.out.println("sum " + sum);
            Log.v("sum " , test);
            String total_value = String.format("%.02f", sum);
            totalsellquantity.setText("" + total_value);
            System.out.println("total_value " + total_value);
            Log.v("total_value " , total_value);

        }

    }

    @SuppressLint("SimpleDateFormat")


    protected void onPostExecute() {

    }

}
