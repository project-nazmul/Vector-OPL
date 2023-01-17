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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.opl.pharmavector.order_online.ReadComments;


public class NoticeBoard extends Activity implements OnClickListener {

    public static final String TAG_SUCCESS = "success";
    public static final String TAG_SUCCESS1 = "success_1";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_MESSAGE_1 = "message_1";
    public static final String TAG_MESSAGE_2 = "message_2";
    public static final String TAG_ord_no = "ord_no";
    public static final String TAG_invoice = "invoice";
    public static final String TAG_target = "target";
    public static final String TAG_achivement = "achivement";
    public String userName_1, userName="xx";
    // array list for spinner adapter
    static ArrayList<Category> categoriesList;
    ProgressDialog pDialog;
    static ListView productListView;

    public String get_ext_dt3;

    public String brand_name, product_flag1;
    public int brand_quant, product_min, product_flag = 0;

    public String onik;


    Button submit;
    // private EditText current_qnty;
    public static EditText qnty, searchview;
    EditText inputOne, inputtwo,notice_search;
    public int success, success_1, ordsl;
    public String message, ord_no, invoice, target, achivement, searchString, message_1, message_2;
    int textlength = 0;
    NoticeBoardAdapter adapter;
    NoticeAdapter adapter2;
    JSONParser jsonParser;
    List<NameValuePair> params;
    static TextView totalsellquantity;
    static TextView totalsellvalue;
    public LinearLayout totalshow;
    public Button calc;
    public String brand;
    public static ArrayList<String> p_ids;
    public static ArrayList<Integer> p_quanty;
    public static ArrayList<String> PROD_RATE;
    public static ArrayList<String> PROD_VAT;
    public static ArrayList<String> PPM_CODE;
    public static ArrayList<String> P_CODE;
    ArrayList<String> lables;
    ArrayList<Integer> quanty;
    HashMap<Integer, String> mapQuantity;
    static HashMap<String, Integer> nameSerialPair;
    ArrayList<String> sl;
    /*- Initializing*/
    String last_quantity = "1";
    int last_position = 1;
    String quantity = "1";
    public int brand_total = 0;
    Toast toast1, toast2;
    ArrayList<Category> arraylist = new ArrayList<Category>();
    private final int REQ_CODE_SPEECH_INPUT = 100;
    public static String URL_NEW_CATEGORY = BASE_URL+"put_products.php";
    private String URL_CATEGORIES = BASE_URL+"get_products_2.php";
    private String campaign_credit = BASE_URL+"notification/get_notification.php";
    private ArrayList<com.opl.pharmavector.AmCustomer> mporeqdcr;
    private ArrayList<com.opl.pharmavector.AmCustomer> brand_info;
    private String SALE_FALG = BASE_URL+"sale_flag.php";
    private String BRAND_FALG = BASE_URL+"brand_flag.php";


    @SuppressLint("DefaultLocale")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.notice_board);
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        submit = (Button) findViewById(R.id.submitBtn);
        submit.setTypeface(fontFamily);
        submit.setText("\uf1d8"); // &#xf1d8
        submit.setVisibility(View.GONE);
        productListView = (ListView) findViewById(R.id.pListView);
        productListView.setDescendantFocusability(ListView.FOCUS_AFTER_DESCENDANTS);
        TextView showorders = (TextView) findViewById(R.id.showorders);
        showorders.setTypeface(fontFamily);
        showorders.setText("\uf055");//&#xf055
        Button back_btn = (Button) findViewById(R.id.backBtn);
        back_btn.setTypeface(fontFamily);
        back_btn.setText("\uf060 ");// &#xf060
        calc = (Button) findViewById(R.id.calc);
        calc.setTypeface(fontFamily);
        calc.setText("\uf1ec"); // &#xf01e &#xf1ec
        calc.setOnClickListener(this);
        searchview = (EditText) findViewById(R.id.p_search);
        notice_search = (EditText) findViewById(R.id.notice_search);
        TextView search = (TextView) findViewById(R.id.search);
        search.setTypeface(fontFamily);
        search.setText("\uf056"); // &#xf002 , &#xf010
        TextView mic = (TextView) findViewById(R.id.mic);
        mic.setTypeface(fontFamily);
        mic.setText("\uf130");
        toast1 = Toast.makeText(NoticeBoard.this, "Please select Order Quantity  !!!.", Toast.LENGTH_LONG);
        toast2 = Toast.makeText(NoticeBoard.this, "Please select Order Quantity for acer 5 or more then 5 !!!.", Toast.LENGTH_LONG);
        Bundle b = getIntent().getExtras();
        userName = b.getString("UserName");
        final String UserName_2 = b.getString("UserName_2");
        final String new_version = b.getString("new_version");



        search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    searchview.setText("");
                    //qnty.clearFocus();
                    searchview.setFocusable(true);
                    searchview.setFocusableInTouchMode(true);
                    searchview.requestFocus();
                } catch (Exception e) {
                    // TODO: handle exception

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
        NoticeBoardAdapter.qnty = null;
        NoticeBoardAdapter.qntyID.clear();
        NoticeBoardAdapter.qntyVal.clear();
        p_ids = new ArrayList<String>();
        p_quanty = new ArrayList<Integer>();
        mapQuantity = new HashMap<Integer, String>();
        nameSerialPair = new HashMap<String, Integer>();
        mporeqdcr = new ArrayList<com.opl.pharmavector.AmCustomer>();
        brand_info = new ArrayList<com.opl.pharmavector.AmCustomer>();
        PROD_RATE = new ArrayList<String>();
        PROD_VAT = new ArrayList<String>();
        PPM_CODE = new ArrayList<String>();
        P_CODE = new ArrayList<String>();
        categoriesList = new ArrayList<Category>();
        submit.setOnClickListener(this);


        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                String notification_id = (String) productListView.getAdapter().getItem(arg2);
                Intent i = new Intent(NoticeBoard.this, NoticeBoardDetails.class);
                i.putExtra("UserName", userName);
                i.putExtra("UserName_2", UserName_2);
                i.putExtra("notification_id", notification_id);
                startActivity(i);

            }
        });


        mic.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                getActionBar().hide();
                promptSpeechInput();
            }

            private void promptSpeechInput() {
                // TODO Auto-generated method stub
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                        getString(R.string.speech_prompt));
                try {
                    startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.speech_not_supported),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        back_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();


            }
        });
        showorders.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                searchString = "1";
                adapter.getFilter().filter(searchString);
            }
        });
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
                if (searchString != null && adapter != null) {
                    adapter.getFilter().filter(searchString);
                }

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

        notice_search.addTextChangedListener(new TextWatcher() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                String searchString = s.toString().toLowerCase();
                if (searchString != null && adapter2 != null) {
                    adapter2.getFilter().filter(searchString);
                }

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

        try {


        } catch (Exception e) {
            e.printStackTrace();

        }

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


    public void finishActivity(View v) {
        finish();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        // System.out.println("EditTxtID " + NoticeBoardAdapter.editTxtID.size());
        super.onResume();

    }


    private void populateSpinner() {

        lables = new ArrayList<String>();
        quanty = new ArrayList<Integer>();
        sl = new ArrayList<String>();
        for (int i = 0; i < categoriesList.size(); i++) {
            Category catdata = new Category(null,null,null,null,null,null);
            lables.add(categoriesList.get(i).getName());
            sl.add(categoriesList.get(i).getsl());
            int o = Integer.parseInt(categoriesList.get(i).getsl());
            p_ids.add(categoriesList.get(i).getId());
            PROD_RATE.add(categoriesList.get(i).getPROD_RATE());
            PROD_VAT.add(categoriesList.get(i).getPROD_VAT());
            PPM_CODE.add(categoriesList.get(i).getPPM_CODE());
            P_CODE.add(categoriesList.get(i).getP_CODE());
            nameSerialPair.put(categoriesList.get(i).getName(), Integer.parseInt(categoriesList.get(i).getsl()));
            int p_serial = Integer.parseInt(categoriesList.get(i).getsl());
            quanty.add(categoriesList.get(i).getQuantity());
            mapQuantity.put(o, String.valueOf(categoriesList.get(i).getQuantity()));
        }
        adapter = new NoticeBoardAdapter(NoticeBoard.this, sl, lables, mapQuantity, PPM_CODE, P_CODE);
        productListView.setAdapter(adapter);

    }


    private class GetCategories extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(NoticeBoard.this);
            pDialog.setTitle("Please wait !");
            pDialog.setMessage("Loading Notifications ... ... ");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("MPO_CODE", userName));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(campaign_credit, ServiceHandler.POST, params);


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
                                    catObj.getString("P_CODE"));
                            categoriesList.add(cat);
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
            //populateSpinner();
            adapter2 = new NoticeAdapter(NoticeBoard.this,categoriesList);
            productListView.setAdapter(adapter2);
        }

    }


    @Override
    public void onClick(View v) {


        /*----------------------*/
        if (v.getId() == submit.getId()) {

            jsonParser = new JSONParser();
            params = new ArrayList<NameValuePair>();
            Intent in = getIntent();
            Intent inten = getIntent();
            Bundle bundle = in.getExtras();
            Bundle extra = inten.getExtras();
            String CUST_CODE = extra.getString("CUST_CODE");
            final String message_1 = extra.getString("message_1");
            String AM_PM = extra.getString("AM_PM");
            String cash_credit = extra.getString("cash_credit");
            String MPO_CODE = extra.getString("MPO_CODE");
            String ORDER_DELEVERY_DATE = bundle.getString("ORDER_DELEVERY_DATE");
            String last_quantity = NoticeBoardAdapter.qnty;
            int last_position = NoticeBoardAdapter.last_position;


            if (last_quantity != null) {
                NoticeBoardAdapter.dataSet.put(last_position, last_quantity);
                NoticeBoardAdapter.qntyID.add(last_position);
                NoticeBoardAdapter.qntyVal.add(last_quantity);
                NoticeBoardAdapter.set2.add(last_position);
            }


            if (NoticeBoardAdapter.qntyID.size() < 1) {
                Toast.makeText(this, "No item inserted", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "No item inserted" + PPM_CODE, Toast.LENGTH_SHORT).show();

            } else {

                String qnty;
                ArrayList<Integer> position = new ArrayList<Integer>();
                for (int j : NoticeBoardAdapter.set2) {
                    position.add(j);
                }
                int k = 0;
                int submit_value = 0;

                product_flag = 0;
                for (int m = 1; m < NoticeBoardAdapter.p_quanty.size(); m++) {

                    brand = P_CODE.get(m - 1);
                    String testbrand = p_ids.get(m - 1);


                    String p_min = PPM_CODE.get(m - 1);
                    int my_quant = Integer.parseInt(p_min);

                    if (my_quant > 0) {
                        product_flag = product_flag + 1;
                    }
                }

                product_min = 0;

                for (int i = 1; i < NoticeBoardAdapter.p_quanty.size(); i++) {
                    int value = Integer.parseInt(NoticeBoardAdapter.p_quanty.get(i));
                    brand = P_CODE.get(i - 1);
                    String testbrand = p_ids.get(i - 1);


                    if (value > 0) {
                        if (brand.equals(brand_name)) {
                            brand_total = brand_total + value;

                        }

                        submit_value = submit_value + value;
                        k = k + 1;
                        Log.w("", cash_credit);
                        params.add(new BasicNameValuePair("total", String.valueOf(k)));
                        params.add(new BasicNameValuePair("id" + String.valueOf(k), p_ids.get(i - 1)));
                        params.add(new BasicNameValuePair("ORD_QNTY" + String.valueOf(k), NoticeBoardAdapter.p_quanty.get(i)));
                        params.add(new BasicNameValuePair("PROD_RATE" + String.valueOf(k), PROD_RATE.get(i - 1)));
                        params.add(new BasicNameValuePair("PROD_VAT" + String.valueOf(k), PROD_VAT.get(i - 1)));
                        String p_min = PPM_CODE.get(i - 1);
                        int my_quant = Integer.parseInt(p_min);
                        if (my_quant > 0) {
                            product_min = product_min + 1;

                        }
                    }
                }
                params.add(new BasicNameValuePair("MPO_CODE", MPO_CODE));
                params.add(new BasicNameValuePair("CUST_CODE", CUST_CODE));
                params.add(new BasicNameValuePair("AM_PM", AM_PM));
                params.add(new BasicNameValuePair("PAY_MODE", cash_credit));
                params.add(new BasicNameValuePair("ORDER_DELEVERY_DATE", ORDER_DELEVERY_DATE));
                if (submit_value > 0) {
                    if (product_min != product_flag && get_ext_dt3.equals("0")) {
                        Toast.makeText(this, "Enter all the mandatory product to submit first credit order", Toast.LENGTH_LONG).show();
                        for (int m = 1; m < NoticeBoardAdapter.p_quanty.size(); m++) {
                            brand = P_CODE.get(m - 1);
                            String testbrand = p_ids.get(m - 1);
                            String p_min = PPM_CODE.get(m - 1);
                            int my_quant = Integer.parseInt(p_min);
                            if (my_quant > 0) {
                                int value = Integer.parseInt(NoticeBoardAdapter.p_quanty.get(m));
                                if (value < my_quant) {
                                    Toast.makeText(this, "Minimum Quntity for **" + testbrand + "** is '" + my_quant + "' to order first credit party of this month", Toast.LENGTH_LONG).show();
                                }
                            }
                        }

                    } else if (brand_total < brand_quant && get_ext_dt3.equals("0")) {
                        Toast.makeText(this, "Minimum Quntity for  brand **" + brand_name + "** is '" + brand_quant + "' to submit first credit party of this month", Toast.LENGTH_LONG).show();
                    } else {
                        final ProgressDialog progress = ProgressDialog.show(this, "Saving Data", "Please Wait..", true);
                        Thread server = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                JSONObject json = jsonParser.makeHttpRequest(URL_NEW_CATEGORY, "POST", params);
                                progress.dismiss();

                                try {
                                    success = json.getInt(TAG_SUCCESS);
                                    success_1 = json.getInt(TAG_SUCCESS1);
                                    message = json.getString(TAG_MESSAGE);
                                    message_2 = json.getString(TAG_MESSAGE_2);
                                    ord_no = json.getString(TAG_ord_no);
                                    if (success_1 == 1) {

                                        // startActivity(i);
                                        NoticeBoardAdapter.qnty = null;
                                        NoticeBoardAdapter.qntyID.clear();
                                        NoticeBoardAdapter.qntyVal.clear();
                                        NoticeBoardAdapter.set2.clear();
                                        NoticeBoardAdapter.dataSet.clear();
                                        NoticeBoardAdapter.p_quanty.clear();
                                        NoticeBoardAdapter.mProductSerialList.clear();

                                    } else {
                                        //SaveToDataBase();
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
                                Intent sameint = new Intent(NoticeBoard.this, ReadComments.class);
                                sameint.putExtra("UserName", MPO_CODE);
                                sameint.putExtra("Ord_NO", message);
                                sameint.putExtra("UserName_1", message_1);
                                sameint.putExtra("UserName_2", message_2);
                                startActivity(sameint);
                            }
                        });

                        server.start();
                    }
                } else {
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
            int total_item = NoticeBoardAdapter.p_quanty.size();
            // System.out.println("total_item " + total_item);
            for (int i = 1; i < total_item; i++) {
                int value = Integer.parseInt(NoticeBoardAdapter.p_quanty.get(i));
                brand = P_CODE.get(i - 1);
                //   Log.w("brand_code",brand);
                if (value > 0) {
                    qnty1 = Integer.parseInt(NoticeBoardAdapter.p_quanty.get(i));
                    rate = Float.parseFloat(PROD_RATE.get(i - 1));
                    product_value = qnty1 * rate;
                    sum = sum + product_value;
                }
            }
            totalsellquantity.setVisibility(v.VISIBLE);
            String test = String.valueOf(sum);
            String total_value = String.format("%.02f", sum);
            totalsellquantity.setText("" + total_value);
        }
    }

    protected void onPostExecute() {

    }

}


