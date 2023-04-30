package com.opl.pharmavector.mpodcr;

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

import com.opl.pharmavector.Category;
import com.opl.pharmavector.JSONParser;
import com.opl.pharmavector.R;
import com.opl.pharmavector.ServiceHandler;
import com.opl.pharmavector.mpodcr.Dcr;
import com.opl.pharmavector.util.NetInfo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
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

public class GiftOrder extends Activity implements OnClickListener {
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
    //array list for spinner adapter
    public static ArrayList<Category> categoriesList;
    ProgressDialog pDialog;
    static ListView productListView;
    GiftListAdapter adapter2;
    Button submit;
    //private EditText current_qnty;
    public static EditText qnty, searchview;
    EditText inputOne, inputtwo;
    public int success,success_1;
    public String message, ord_no,invoice,target,achivement,searchString,message_1,message_2;
    int textlength = 0;
    GiftListAdapter adapter;
    JSONParser jsonParser;
    List<NameValuePair> params;
    public static TextView totalsellquantity;
    public static TextView totalsellvalue;
    public LinearLayout totalshow;
    public Button calc;
    public static ArrayList<String> p_ids;
    public static ArrayList<Integer> p_quanty;
    public static ArrayList<String> PROD_RATE;
    public static ArrayList<String> PROD_VAT;
    public static ArrayList<String> PPM_CODE;
    public static ArrayList<String> P_CODE;
    public static ArrayList<String> SHIFT_CODE;
    String get_mpo_code,ORDER_DELEVERY_DATE;
    ArrayList<String> lables;
    ArrayList<Integer> quanty;
    HashMap<Integer, String> mapQuantity;
    public static HashMap<String, Integer> nameSerialPair;
    //Map<String,List<Integer>> index = new HashMap<String,List<Integer>>();
    ArrayList<String> sl;
    /*-Initializing*/
    String last_quantity = "1";
    int last_position = 1;
    String quantity = "1";
    Toast toast1;
    ArrayList<Category> arraylist = new ArrayList<Category>();
    private final int REQ_CODE_SPEECH_INPUT = 100;
    public static String URL_NEW_CATEGORY = "http://opsonin.com.bd/vector_opl_v1/mpodcr/submit_doc_dcr.php";
    //private String URL_CATEGORIES = "http://opsonin.com.bd/vector_opl_v1/mpodcr/get_gift_new.php";
    private String URL_CATEGORIES = "http://opsonin.com.bd/vector_opl_v1/mpodcr/get_gift_list.php";

    @SuppressLint("DefaultLocale")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.giftorder);

        Typeface fontFamily = Typeface.createFromAsset(getAssets(),"fonts/fontawesome.ttf");
        submit = findViewById(R.id.submitBtn);
        submit.setTypeface(fontFamily);
        submit.setText("\uf1d8"); //&#xf1d8
        productListView = (ListView) findViewById(R.id.pListView);
        productListView.setDescendantFocusability(ListView.FOCUS_AFTER_DESCENDANTS);
        TextView showorders=findViewById(R.id.showorders);
        showorders.setTypeface(fontFamily);
        showorders.setText("\uf055"); //&#xf055
        Button back_btn =  findViewById(R.id.backBtn);
        back_btn.setTypeface(fontFamily);
        back_btn.setText("\uf060"); //&#xf060
        calc = findViewById(R.id.calc);
        calc.setTypeface(fontFamily);
        calc.setText("\uf1ec"); //&#xf01e &#xf1ec
        calc.setOnClickListener(this);
        searchview = (EditText) findViewById(R.id.p_search);
        TextView search = findViewById(R.id.search);
        search.setTypeface(fontFamily);
        search.setText("\uf056");
        toast1 = Toast.makeText(GiftOrder.this, "Please select Order Quantity  !!!.", Toast.LENGTH_LONG);

        search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    searchview.requestFocus();
                    searchview.setText("");
                }
                catch (Exception e) {
                    // GiftListAdapter.qnty.c
                }
            }
        });
        totalsellquantity =  findViewById(R.id.totalsellquantity);
        totalsellquantity.setVisibility(View.GONE);
        totalsellvalue =  findViewById(R.id.totalsellvalue);
        Spinner am_pm =  findViewById(R.id.ampm);
        Spinner cash_credit =  findViewById(R.id.cashcredit);
        Spinner credit =  findViewById(R.id.credit);
        totalshow =  findViewById(R.id.totalshow);
        GiftListAdapter.qnty = null;
        GiftListAdapter.qntyID.clear();
        GiftListAdapter.qntyVal.clear();
        p_ids = new ArrayList<String>();
        p_quanty = new ArrayList<Integer>();
        mapQuantity = new HashMap<Integer, String>();
        nameSerialPair = new HashMap<String, Integer>();
        PROD_RATE = new ArrayList<String>();
        PROD_VAT = new ArrayList<String>();
        PPM_CODE = new ArrayList<String>();
        P_CODE = new ArrayList<String>();
        SHIFT_CODE = new ArrayList<String>();
        categoriesList = new ArrayList<Category>();
        Intent in = getIntent();
        Intent inten = getIntent();
        Bundle bundle = in.getExtras();
        Bundle extra = inten.getExtras();
        final String MPO_CODE = extra.getString("MPO_CODE");
        extra.getString("CUST_CODE");
        bundle.getString("ORDER_DELEVERY_DATE");
        bundle.getString("ORDER_REFERANCE_NO");

        submit.setOnClickListener(this);
        back_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Inflater inf = new Inflater();
                inf.end();
                finish();
            }
        });
        showorders.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                searchString="1";
                adapter.getFilter().filter(searchString);
            }});
        searchview.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                searchview.setFocusable(true);
                searchview.setFocusableInTouchMode(true);
                searchview.setClickable(true);
                searchview.setText("");
            }
        });
        searchview.addTextChangedListener(new TextWatcher() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<String> resList = new ArrayList<String>();
                ArrayList<Integer> resList2 = new ArrayList<Integer>();
                String searchString = s.toString().toLowerCase();
                if(searchString!=null && adapter!=null){
                    adapter.getFilter().filter(searchString);
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                String text = searchview.getText().toString().toLowerCase(Locale.getDefault());
            }
        });
        new GetCategories().execute();
        TextView clickme =  findViewById(R.id.clickme);

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
                    // listview=findViewById(R.id.listview);
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
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    searchview.setText(result.get(0));
                }
                break;
            }
        }
    }

    public void swapfocus(View v){
        if (GiftListAdapter.focusvalue==0) {
            v.clearFocus();
            searchview.setFocusable(true);
        }
        else {
            v.clearFocus();
            searchview.setFocusable(false);
        }
    }

    public void finishActivity(View v) {
        finish();
    }

    @Override
    protected void onResume() {
        // System.out.println("EditTxtID " + GiftListAdapter.editTxtID.size());
        super.onResume();

    }

    private void populateSpinner() {
        lables = new ArrayList<String>();
        quanty = new ArrayList<Integer>();
        sl = new ArrayList<String>();
        for (int i = 0; i < categoriesList.size(); i++) {

            lables.add(categoriesList.get(i).getName());
            sl.add(categoriesList.get(i).getsl());
            int o = Integer.parseInt(categoriesList.get(i).getsl());
            p_ids.add(categoriesList.get(i).getId());
            PROD_RATE.add(categoriesList.get(i).getPROD_RATE());
            PROD_VAT.add(categoriesList.get(i).getPROD_VAT());
            PPM_CODE.add(categoriesList.get(i).getPPM_CODE());
            SHIFT_CODE.add(categoriesList.get(i).getSHIFT_CODE());
            P_CODE.add(categoriesList.get(i).getP_CODE());
            nameSerialPair.put(categoriesList.get(i).getName(), Integer.parseInt(categoriesList.get(i).getsl()));
            int p_serial = Integer.parseInt(categoriesList.get(i).getsl());
            quanty.add(categoriesList.get(i).getQuantity());
            mapQuantity.put(o, String.valueOf(categoriesList.get(i).getQuantity()));
        }
        adapter = new GiftListAdapter(GiftOrder.this, sl, lables, mapQuantity,PROD_RATE,PROD_VAT,PROD_RATE,SHIFT_CODE);
        productListView.setAdapter(adapter);
    }


    private class GetCategories extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(GiftOrder.this);
            pDialog.setTitle("Please wait !");
            pDialog.setMessage("Loading Products..");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Intent inten = getIntent();
            Bundle extra = inten.getExtras();
            get_mpo_code = extra.getString("CUST_CODE");
            ORDER_DELEVERY_DATE = extra.getString("ORDER_DELEVERY_DATE");
            List<NameValuePair>params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("MPO_CODE", get_mpo_code));
            params.add(new BasicNameValuePair("ORDER_DELEVERY_DATE", ORDER_DELEVERY_DATE));
            ServiceHandler jsonParser=new ServiceHandler();
            String json=jsonParser.makeServiceCall(URL_CATEGORIES, ServiceHandler.POST, params);
            Log.e("giftList-->",json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj.getJSONArray("categories");
                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            Category cat = new Category(catObj.getString("sl"),
                                    catObj.getString("id"),
                                    catObj.getString("name"),
                                    catObj.getInt("quantity"),
                                    catObj.getString("PROD_RATE"),
                                    catObj.getString("PROD_VAT"),
                                    catObj.getString("PPM_CODE"),
                                    catObj.getString("P_CODE"),
                                    catObj.getString("SHIFT_CODE"),
                                    catObj.getString("PACK_CODE"),
                                    catObj.getString("TOTAL_CODE")
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

    @Override
    public void onClick(View v) {
        if (v.getId() == submit.getId()) {
            if (!NetInfo.isOnline(getBaseContext())) {
                Toast.makeText(getBaseContext(), "Internet conection failed", Toast.LENGTH_SHORT).show();
                final ProgressDialog progress = ProgressDialog.show(this, "Saving Data Offline", "Please Wait..", true);
                return;
            }
            jsonParser = new JSONParser();
            params = new ArrayList<NameValuePair>();
            Intent in = getIntent();
            Intent inten = getIntent();
            Bundle bundle = in.getExtras();
            Bundle extra = inten.getExtras();
            String CUST_CODE = extra.getString("CUST_CODE");
            final String message_1 = extra.getString("message_1");
            final  String AM_PM = extra.getString("AM_PM");
            String cash_credit = extra.getString("cash_credit");
            String MPO_CODE = extra.getString("MPO_CODE");
            String ORDER_DELEVERY_DATE = bundle.getString("ORDER_DELEVERY_DATE");
            final   String get_mpo_code = extra.getString("CUST_CODE");
            final   String ord_no_1 = extra.getString("ord_no");
            final   String doc_code = extra.getString("doc_code");
            final   String end_time = extra.getString("end_time");
            final   String start_time = extra.getString("start_time");
            final   String Type = extra.getString("Type");
            final   String location_code = extra.getString("location code");
            final   String Visitor_Code = extra.getString("VISITOR_CODE");
            final   String VISIT_DATE = extra.getString("VISIT_DATE");
            final   String REMARKS = extra.getString("REMARKS");
            final   String VISIT_WITH = extra.getString("VISIT_WITH");
            final   String COMPETITOR_ANALYSIS = extra.getString("COMPETITOR_ANALYSIS");
            String last_quantity = GiftListAdapter.qnty;
            int last_position = GiftListAdapter.last_position;
            if (last_quantity != null) {
                GiftListAdapter.dataSet.put(last_position, last_quantity);
                GiftListAdapter.qntyID.add(last_position);
                GiftListAdapter.qntyVal.add(last_quantity);
                GiftListAdapter.set2.add(last_position);
            }
            if (GiftListAdapter.qntyID.size() < 1) {
                Toast.makeText(this, "No item inserted", Toast.LENGTH_SHORT).show();
            }
            else {
                String qnty;
                ArrayList<Integer> position = new ArrayList<Integer>();
                for (int j : GiftListAdapter.set2) {
                    position.add(j);
                }
                int k=0;
                int submit_value=0;
                for (int i = 1; i < GiftListAdapter.p_quanty.size(); i++) {
                    int value = Integer.parseInt(GiftListAdapter.p_quanty.get(i));
                    if (value>0) {
                        submit_value=submit_value+value;
                        k=k+1;
                        params.add(new BasicNameValuePair("total", String.valueOf(k)));
                        params.add(new BasicNameValuePair("id"+String.valueOf(k), p_ids.get(i - 1)));
                        params.add(new BasicNameValuePair("ORD_QNTY"+ String.valueOf(k),GiftListAdapter.p_quanty.get(i)));
                        params.add(new BasicNameValuePair("PROD_RATE"+ String.valueOf(k), ord_no_1));
                        params.add(new BasicNameValuePair("PROD_VAT"+ String.valueOf(k), doc_code));
                        params.add(new BasicNameValuePair("PPM_CODE"+ String.valueOf(k), PPM_CODE.get(i - 1)));
                        params.add(new BasicNameValuePair("P_CODE"+ String.valueOf(k), P_CODE.get(i - 1)));
                        params.add(new BasicNameValuePair("SHIFT_CODE"+ String.valueOf(k), AM_PM));
                    }
                }
                params.add(new BasicNameValuePair("ORD_NO", ord_no_1));
                params.add(new BasicNameValuePair("MPO_CODE", get_mpo_code));
                params.add(new BasicNameValuePair("CUST_CODE", doc_code)); // doctor code
                params.add(new BasicNameValuePair("End_Time", end_time));
                params.add(new BasicNameValuePair("Start_Time", start_time));
                params.add(new BasicNameValuePair("DCR_TYPE", Type));
                params.add(new BasicNameValuePair("AM_PM", AM_PM));
                params.add(new BasicNameValuePair("TOUR_NATURE", location_code));
                params.add(new BasicNameValuePair("VISITOR_CODE",Visitor_Code));
                params.add(new BasicNameValuePair("VISIT_DATE",VISIT_DATE));
                params.add(new BasicNameValuePair("REMARKS",REMARKS));
                params.add(new BasicNameValuePair("VISIT_WITH",VISIT_WITH));
                params.add(new BasicNameValuePair("COMPETITOR_ANALYSIS",COMPETITOR_ANALYSIS));

                if(submit_value>0) {
                    final ProgressDialog progress = ProgressDialog.show(this, "Saving Data", "Please Wait..", true);
                    Thread server = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            JSONObject json = jsonParser.makeHttpRequest(URL_NEW_CATEGORY, "POST", params);
                            progress.dismiss();

                            try {
                                success = json.getInt(TAG_SUCCESS);
                                success_1 = json.getInt(TAG_SUCCESS1);
                                message = json.getString(TAG_MESSAGE);
                                ord_no = json.getString(TAG_ord_no);
                                Log.w("please wait ...." + message, json.toString());
                                if (success_1 == 1) {
                                    GiftListAdapter.qnty = null;
                                    GiftListAdapter.qntyID.clear();
                                    GiftListAdapter.qntyVal.clear();
                                    GiftListAdapter.set2.clear();
                                    GiftListAdapter.dataSet.clear();
                                    GiftListAdapter.p_quanty.clear();
                                    GiftListAdapter.mProductSerialList.clear();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Intent in = getIntent();
                            Intent inten = getIntent();
                            Bundle bundle = in.getExtras();
                            inten.getExtras();
                            String MPO_CODE = bundle.getString("MPO_CODE");
                            Intent sameint = new Intent(GiftOrder.this, Dcr.class);
                            sameint.putExtra("UserName", MPO_CODE);
                            sameint.putExtra("Ord_NO", message);
                            sameint.putExtra("UserName_1", "Dcr Submitted");
                            sameint.putExtra("UserName_2", "Dcr Submitted");
                            startActivity(sameint);
                        }
                    });
                    server.start();
                } else {
                    toast1.show();
                }
            }
        }

        if (v.getId() == calc.getId()) {
            float sum = 0f;
            int qnty1 = 0;
            float rate = 0f;
            float product_value = 0f;
            int total_item = GiftListAdapter.p_quanty.size();
            for (int i = 1; i < total_item; i++) {
                int value = Integer.parseInt(GiftListAdapter.p_quanty.get(i));
                if (value > 0) {
                    qnty1 = Integer.parseInt(GiftListAdapter.p_quanty.get(i));
                    rate = Float.parseFloat(PROD_RATE.get(i - 1));
                    product_value = qnty1 * rate;
                    sum = sum + product_value;
                }
            }
            totalsellquantity.setVisibility(v.VISIBLE);
            String test=String.valueOf(sum );
            String total_value = String.format("%.02f", sum);
            totalsellquantity.setText("" + total_value);
        }
    }

    protected void onPostExecute() {

    }
}
