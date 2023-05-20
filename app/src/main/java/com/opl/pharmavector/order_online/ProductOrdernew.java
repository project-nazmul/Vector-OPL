package com.opl.pharmavector.order_online;

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
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.opl.pharmavector.AmCustomer;
import com.opl.pharmavector.Category;
import com.opl.pharmavector.JSONParser;
import com.opl.pharmavector.ServiceHandler;
import com.opl.pharmavector.util.OfferDialoge;
import com.opl.pharmavector.R;

public class ProductOrdernew extends FragmentActivity implements OnClickListener {
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_SUCCESS1 = "success_1";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_MESSAGE_1 = "message_1";
    public static final String TAG_MESSAGE_2 = "message_2";
    public static final String TAG_ord_no = "ord_no";
    public static final String TAG_invoice = "invoice";
    public static final String TAG_target = "target";
    public static final String TAG_achivement = "achivement";
    public String userName_1, userName;
    static ArrayList<Category> categoriesList;
    ProgressDialog pDialog;
    static ListView productListView;
    public String get_ext_dt3;
    public String get_ext_dt4;
    public String brand_name, product_flag1;
    public int brand_quant, product_min, product_flag = 0;
    public String onik, sale_prod;
    private TableLayout table2;
    Button submit;
    public static EditText qnty, searchview;
    public int success, success_1, ordsl;
    public String message, ord_no, invoice, target, achivement, searchString, message_1, message_2;
    ProductListAdapter2 adapter;
    JSONParser jsonParser;
    List<NameValuePair> params;
    static TextView totalsellquantity, gen_sum, gal_sum, ver_sum, dar_sum, aspiron_sum, plexus_sum, dynamos_sum;
    static TextView totalsellvalue;
    public LinearLayout totalshow;
    public Button calc;
    public String brand;
    public static ArrayList<String> p_ids;
    public static ArrayList<Integer> p_quanty;
    public static ArrayList<String> PROD_RATE;
    public static ArrayList<String> PROD_VAT;
    public static ArrayList<String> PPM_CODE;
    public static ArrayList<String> SHIFT_CODE;
    public static ArrayList<String> P_CODE;
    public static ArrayList<String> PACK_CODE;
    ArrayList<String> lables;
    ArrayList<Integer> quanty;
    HashMap<Integer, String> mapQuantity;
    static HashMap<String, Integer> nameSerialPair;
    ArrayList<String> sl;
    String last_quantity = "1";
    int last_position = 1;
    String quantity = "1";
    public int brand_total = 0;
    Toast toast1, toast2, toast3;
    ArrayList<Category> arraylist = new ArrayList<Category>();
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private ArrayList<AmCustomer> mposingleflag;

    private ArrayList<AmCustomer> mporeqdcr;
    private ArrayList<AmCustomer> brand_info;
    Typeface fontFamily;
    TextView showorders, offer, search, clickme;
    Button back_btn;

    public static String URL_NEW_CATEGORY    = BASE_URL+"order_online/submit_order.php";
    private final String SALE_FALG           = BASE_URL+"order_online/sale_flag.php";
    private final String BRAND_FALG          = BASE_URL+"order_online/brand_flag.php";
    private final String SINGLE_SALE_FALG    = BASE_URL+"order_online/single_sale_flag.php";
    private final String URL_CATEGORIES      = BASE_URL+"order_online/get_product.php";
    private final String campaign_credit     = BASE_URL+"order_online/get_campaign_products.php";
    Spinner am_pm, cash_credit, credit;

    @SuppressLint({"DefaultLocale", "ClickableViewAccessibility"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productorder);

        initViews();
        search.setOnClickListener(v -> {
            try {
                searchview.setText("");
                searchview.requestFocus();
            } catch (Exception e) {}
        });
        new REQ_MPO().execute();
        new REQ_BRAND().execute();
        new SINGLE_FLAG().execute();

        back_btn.setOnClickListener(v -> {
            Inflater inf = new Inflater();
            inf.end();
            finish();
        });
        showorders.setOnClickListener(v -> {
            searchString = "1";
            adapter.getFilter().filter(searchString);
        });
        searchview.setOnClickListener(v -> {
            searchview.setFocusable(true);
            searchview.setFocusableInTouchMode(true);
            searchview.setClickable(true);
            searchview.setText("");
        });
        offer.setOnClickListener(v -> new OfferDialoge().show(getSupportFragmentManager(), "Dialog"));
        searchview.addTextChangedListener(new TextWatcher() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<String> resList = new ArrayList<String>();
                ArrayList<Integer> resList2 = new ArrayList<Integer>();
                String searchString = s.toString().toLowerCase();
                if (adapter != null) {
                    adapter.getFilter().filter(searchString);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {
                String text = searchview.getText().toString().toLowerCase(Locale.getDefault());
            }
        });
        new GetCategories().execute();
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
                    //LinearLayout
                    //listview=(LinearLayout)findViewById(R.id.listview);
                    productListView.scrollTo(
                            (int) productListView.getScrollX(),
                            (int) productListView.getScrollY() + 11);
                    mHandler.postDelayed(mAction, mRepeatDelay);
                }
            };
        });
    }

    private void initViews() {
        fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        submit = findViewById(R.id.submitBtn);
        submit.setTypeface(fontFamily);
        submit.setText("\uf1d8"); // &#xf1d8
        productListView = findViewById(R.id.pListView);
        productListView.setDescendantFocusability(ListView.FOCUS_AFTER_DESCENDANTS);

        showorders = findViewById(R.id.showorders);
        showorders.setTypeface(fontFamily);
        showorders.setText("\uf055");
        offer = findViewById(R.id.offer);
        offer.setTypeface(fontFamily);
        back_btn = findViewById(R.id.backBtn);
        back_btn.setTypeface(fontFamily);
        back_btn.setText("\uf060 ");
        calc = findViewById(R.id.calc);
        calc.setTypeface(fontFamily);
        calc.setText("\uf1ec");
        calc.setOnClickListener(this);
        searchview = findViewById(R.id.p_search);
        search = findViewById(R.id.search);
        search.setTypeface(fontFamily);
        search.setText("\uf056");

        searchview.requestFocus();
        toast1 = Toast.makeText(ProductOrdernew.this, "Please select Order Quantity  !!!.", Toast.LENGTH_LONG);
        toast2 = Toast.makeText(ProductOrdernew.this, "Please select Order Quantity for acer 5 or more then 5 !!!.", Toast.LENGTH_LONG);
        toast3 = Toast.makeText(ProductOrdernew.this, "As you Selectd Easium 10 mg Injection, 10 other product " +
                "will be included in your invoice", Toast.LENGTH_LONG);

        totalsellquantity = findViewById(R.id.totalsellquantity);
        totalsellquantity.setVisibility(View.GONE);
        table2 = findViewById(R.id.table2);
        table2.setVisibility(View.GONE);
        gen_sum = findViewById(R.id.gen_sum);
        gen_sum.setVisibility(View.GONE);
        ver_sum = findViewById(R.id.ver_sum);
        ver_sum.setVisibility(View.GONE);
        gal_sum = findViewById(R.id.gal_sum);
        gal_sum.setVisibility(View.GONE);
        dar_sum = findViewById(R.id.dar_sum);
        dar_sum.setVisibility(View.GONE);
        aspiron_sum = findViewById(R.id.aspiron_sum);
        aspiron_sum.setVisibility(View.GONE);
        plexus_sum = findViewById(R.id.plexus_sum);
        plexus_sum.setVisibility(View.GONE);
        dynamos_sum = findViewById(R.id.dynamos_sum);
        dynamos_sum.setVisibility(View.GONE);
        totalsellvalue = findViewById(R.id.totalsellvalue);
        am_pm = findViewById(R.id.ampm);
        cash_credit = findViewById(R.id.cashcredit);
        credit = findViewById(R.id.credit);
        clickme = findViewById(R.id.clickme);
        totalshow =  findViewById(R.id.totalshow);
        ProductListAdapter2.qnty = null;
        ProductListAdapter2.qntyID.clear();
        ProductListAdapter2.qntyVal.clear();
        p_ids = new ArrayList<String>();
        p_quanty = new ArrayList<Integer>();
        mapQuantity = new HashMap<Integer, String>();
        nameSerialPair = new HashMap<String, Integer>();
        mporeqdcr = new ArrayList<AmCustomer>();
        brand_info = new ArrayList<AmCustomer>();
        mposingleflag = new ArrayList<AmCustomer>();
        PROD_RATE = new ArrayList<String>();
        PROD_VAT = new ArrayList<String>();
        PPM_CODE = new ArrayList<String>();
        SHIFT_CODE = new ArrayList<String>();
        P_CODE = new ArrayList<String>();
        PACK_CODE = new ArrayList<String>();
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

    public void swapfocus(View v) {
        //ProductListAdapter2.focusvalue=0;
        if (ProductListAdapter2.focusvalue == 0) {
            v.clearFocus();
            searchview.setFocusable(true);
        } else {
            v.clearFocus();
            searchview.setFocusable(false);
        }
    }

    public void finishActivity(View v) {
        finish();
    }

    @Override
    protected void onResume() {
        //System.out.println("EditTxtID " + ProductListAdapter2.editTxtID.size());
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
            PACK_CODE.add(categoriesList.get(i).getPACK_CODE());
            nameSerialPair.put(categoriesList.get(i).getName(), Integer.parseInt(categoriesList.get(i).getsl()));
            int p_serial = Integer.parseInt(categoriesList.get(i).getsl());
            quanty.add(categoriesList.get(i).getQuantity());
            mapQuantity.put(o, String.valueOf(categoriesList.get(i).getQuantity()));
        }
        adapter = new ProductListAdapter2(ProductOrdernew.this, sl, lables, mapQuantity, PPM_CODE, P_CODE, SHIFT_CODE, PACK_CODE);
        productListView.setAdapter(adapter);
    }

    private class GetCategories extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ProductOrdernew.this);
            pDialog.setTitle("Please wait !");
            pDialog.setMessage("Loading Products..");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        private void dismissProgressDialog() {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Intent inten = getIntent();
            Bundle extra = inten.getExtras();
            String cash_credit = extra.getString("cash_credit");
            String mpo_code = extra.getString("MPO_CODE");
            String CUST_CODE = extra.getString("CUST_CODE");
            String ORDER_DELEVERY_DATE = extra.getString("ORDER_DELEVERY_DATE");

            if (cash_credit.equals("Campaign Credit")) {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("MPO_CODE", mpo_code));
                params.add(new BasicNameValuePair("CUST_CODE", CUST_CODE));
                params.add(new BasicNameValuePair("cash_credit", cash_credit));
                ServiceHandler jsonParser = new ServiceHandler();
                String json = jsonParser.makeServiceCall(campaign_credit, ServiceHandler.GET, params);

                Log.e("Response: ", "> " + json);
                Log.d("Param: ", cash_credit + " : " + mpo_code + " : " + CUST_CODE);

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
                                        catObj.getString("PACK_CODE")
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
            } else {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("MPO_CODE", mpo_code));
                params.add(new BasicNameValuePair("CUST_CODE", CUST_CODE));
                params.add(new BasicNameValuePair("cash_credit", cash_credit));
                params.add(new BasicNameValuePair("ORDER_DELEVERY_DATE", ORDER_DELEVERY_DATE));
                Log.d("Param: ", cash_credit + " : " + mpo_code + " : " + CUST_CODE + " : " + ORDER_DELEVERY_DATE);

                ServiceHandler jsonParser = new ServiceHandler();
                String json = jsonParser.makeServiceCall(URL_CATEGORIES, ServiceHandler.GET, params);

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
                                        catObj.getString("PACK_CODE")
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
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            dismissProgressDialog();
            populateSpinner();
        }
    }

    private void populatreqmpo() {
        List<String> lables = new ArrayList<String>();

        for (int i = 0; i < mporeqdcr.size(); i++) {
            get_ext_dt3 = mporeqdcr.get(i).getName();
        }
    }

    private void brand_info() {
        List<String> lables = new ArrayList<String>();
        for (int i = 1; i < brand_info.size(); i++) {
            brand_name = brand_info.get(i).getName();
            brand_quant = brand_info.get(i).getId();
        }
    }

    private class REQ_MPO extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Intent inten = getIntent();
            Bundle extra = inten.getExtras();
            String cash_credit = extra.getString("cash_credit");
            String mpo_code = extra.getString("MPO_CODE");
            String CUST_CODE = extra.getString("CUST_CODE");

            String ORDER_DELEVERY_DATE = extra.getString("ORDER_DELEVERY_DATE");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("cash_credit", cash_credit));
            params.add(new BasicNameValuePair("mpo_code", mpo_code));
            params.add(new BasicNameValuePair("cust_code", CUST_CODE));
            params.add(new BasicNameValuePair("id", mpo_code));
            params.add(new BasicNameValuePair("str", CUST_CODE));
            params.add(new BasicNameValuePair("ORDER_DELEVERY_DATE", ORDER_DELEVERY_DATE));

            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(SALE_FALG, ServiceHandler.POST, params);
            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray customer = jsonObj.getJSONArray("customer");
                        for (int i = 0; i < customer.length(); i++) {
                            JSONObject catObj = (JSONObject) customer.get(0);
                            AmCustomer custo = new AmCustomer(catObj.getInt("id"), catObj.getString("name"));
                            mporeqdcr.add(custo);
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
            populatreqmpo();
        }
    }


    private class REQ_BRAND extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Intent inten = getIntent();
            Bundle extra = inten.getExtras();
            String cash_credit = extra.getString("cash_credit");
            String mpo_code = extra.getString("MPO_CODE");
            String CUST_CODE = extra.getString("CUST_CODE");
            String ORDER_DELEVERY_DATE = extra.getString("ORDER_DELEVERY_DATE");

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("cash_credit", cash_credit));
            params.add(new BasicNameValuePair("mpo_code", mpo_code));
            params.add(new BasicNameValuePair("cust_code", CUST_CODE));
            params.add(new BasicNameValuePair("id", mpo_code));
            params.add(new BasicNameValuePair("str", CUST_CODE));
            params.add(new BasicNameValuePair("ORDER_DELEVERY_DATE", ORDER_DELEVERY_DATE));
            ServiceHandler jsonParser = new ServiceHandler();

            String json = jsonParser.makeServiceCall(BRAND_FALG, ServiceHandler.POST, params);
            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray customer = jsonObj.getJSONArray("customer");
                        for (int i = 0; i < customer.length(); i++) {
                            JSONObject catObj = (JSONObject) customer.get(0);
                            AmCustomer custo = new AmCustomer(catObj.getInt("id"), catObj.getString("name"));
                            brand_info.add(custo);
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
            brand_info();
        }
    }

    private void populatesingleflag() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < mposingleflag.size(); i++) {
            get_ext_dt4 = mposingleflag.get(i).getName();
            Log.e("mposingleflag ", ">  get_ext_dt4 == " + get_ext_dt4);
        }
    }

    private class SINGLE_FLAG extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Intent inten = getIntent();
            Bundle extra = inten.getExtras();
            String cash_credit = extra.getString("cash_credit");
            String mpo_code = extra.getString("MPO_CODE");
            String CUST_CODE = extra.getString("CUST_CODE");
            String ORDER_DELEVERY_DATE = extra.getString("ORDER_DELEVERY_DATE");

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("cash_credit", cash_credit));
            params.add(new BasicNameValuePair("mpo_code", mpo_code));
            params.add(new BasicNameValuePair("cust_code", CUST_CODE));
            params.add(new BasicNameValuePair("id", mpo_code));
            params.add(new BasicNameValuePair("str", CUST_CODE));
            params.add(new BasicNameValuePair("ORDER_DELEVERY_DATE", ORDER_DELEVERY_DATE));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(SINGLE_SALE_FALG, ServiceHandler.POST, params);
            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray customer = jsonObj.getJSONArray("customer");
                        for (int i = 0; i < customer.length(); i++) {
                            JSONObject catObj = (JSONObject) customer.get(0);
                            AmCustomer custo = new AmCustomer(catObj.getInt("id"), catObj.getString("name"));
                            mposingleflag.add(custo);
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
            populatesingleflag();
        }
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        if (v.getId() == submit.getId()) {
            jsonParser = new JSONParser();
            params = new ArrayList<>();
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
            System.out.println("ORDER_DELEVERY_DATE " + "ORD_QNTY" + ORDER_DELEVERY_DATE);
            String last_quantity = ProductListAdapter2.qnty;
            int last_position = ProductListAdapter2.last_position;

            if (last_quantity != null) {
                ProductListAdapter2.dataSet.put(last_position, last_quantity);
                ProductListAdapter2.qntyID.add(last_position);
                ProductListAdapter2.qntyVal.add(last_quantity);
                ProductListAdapter2.set2.add(last_position);
            }

            if (ProductListAdapter2.qntyID.size() < 1) {
                Toast.makeText(this, "No item inserted", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "No item inserted" + PPM_CODE, Toast.LENGTH_SHORT).show();

            } else {
                String qnty;
                ArrayList<Integer> position = new ArrayList<Integer>();
                for (int j : ProductListAdapter2.set2) {
                    position.add(j);
                }
                int k = 0;
                int submit_value = 0;
                product_flag = 0;

                for (int m = 1; m < ProductListAdapter2.p_quanty.size(); m++) {
                    brand = P_CODE.get(m - 1);
                    String testbrand = p_ids.get(m - 1);
                    Log.w("brand_code", brand);
                    sale_prod = p_ids.get(m - 1);
                    Log.w("sale_prod", sale_prod);
                    String p_min = PPM_CODE.get(m - 1);
                    int my_quant = Integer.parseInt(p_min);

                    if (my_quant > 0) {
                        product_flag = product_flag + 1;
                    }
                }
                product_min = 0;

                for (int i = 1; i < ProductListAdapter2.p_quanty.size(); i++) {
                    int value = Integer.parseInt(ProductListAdapter2.p_quanty.get(i));
                    brand = P_CODE.get(i - 1);

                    String testbrand = p_ids.get(i - 1);
                    Log.w("brand_code", brand);
                    Log.w("testbrand", testbrand);

                    if (value > 0) {
                        if (brand.equals(brand_name)) {
                            brand_total = brand_total + value;
                            Log.w("brand_t", String.valueOf(brand_total));
                        }

                        if (get_ext_dt4.equals(1) && testbrand.equals("ESI5")) {
                            toast3.setGravity(Gravity.CENTER, 0, 0);
                            toast3.setDuration(Toast.LENGTH_LONG);
                            toast3.show();
                        }
                        submit_value = submit_value + value;
                        k = k + 1;
                        Log.w("", cash_credit);
                        params.add(new BasicNameValuePair("total", String.valueOf(k)));
                        params.add(new BasicNameValuePair("id" + String.valueOf(k), p_ids.get(i - 1)));
                        params.add(new BasicNameValuePair("ORD_QNTY" + String.valueOf(k), ProductListAdapter2.p_quanty.get(i)));
                        params.add(new BasicNameValuePair("PROD_RATE" + String.valueOf(k), PROD_RATE.get(i - 1)));
                        params.add(new BasicNameValuePair("PROD_VAT" + String.valueOf(k), PROD_VAT.get(i - 1)));

                        System.out.println("id--k" + String.valueOf(k) + "=" + p_ids.get(k - 1));
                        System.out.println("total" + "=" + String.valueOf(k));
                        System.out.println("id" + String.valueOf(k) + "=" + p_ids.get(i - 1));
                        System.out.println("ORD_QNTY" + String.valueOf(k) + "=" + ProductListAdapter2.p_quanty.get(i));
                        System.out.println("PROD_RATE" + String.valueOf(k) + "=" + PROD_RATE.get(i - 1));
                        System.out.println("PROD_VAT" + String.valueOf(k) + "=" + PROD_VAT.get(i - 1));

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

                        for (int m = 1; m < ProductListAdapter2.p_quanty.size(); m++) {
                            brand = P_CODE.get(m - 1);
                            String testbrand = p_ids.get(m - 1);
                            Log.w("brand_code", brand);
                            String p_min = PPM_CODE.get(m - 1);
                            int my_quant = Integer.parseInt(p_min);

                            if (my_quant > 0) {
                                int value = Integer.parseInt(ProductListAdapter2.p_quanty.get(m));
                                if (value < my_quant) {
                                    Toast.makeText(this, "Minimum Quantity for **" + testbrand + "** is '" + my_quant + "' to order first credit party of this month", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    } else if (brand_total < brand_quant && get_ext_dt3.equals("0")) {
                        Toast.makeText(this, "Minimum Quantity for  brand **" + brand_name + "** is '" + brand_quant + "' to submit first credit party of this month", Toast.LENGTH_LONG).show();
                    } else {
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
                                    message_2 = json.getString(TAG_MESSAGE_2);
                                    ord_no = json.getString(TAG_ord_no);
                                    if (success_1 == 1) {
                                        ProductListAdapter2.qnty = null;
                                        ProductListAdapter2.qntyID.clear();
                                        ProductListAdapter2.qntyVal.clear();
                                        ProductListAdapter2.set2.clear();
                                        ProductListAdapter2.dataSet.clear();
                                        ProductListAdapter2.p_quanty.clear();
                                        ProductListAdapter2.mProductSerialList.clear();
                                    } else {
                                        //SaveToDataBase();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Intent in = getIntent();
                                Intent inten = getIntent();
                                Bundle bundle = in.getExtras();
                                inten.getExtras();
                                String MPO_CODE = bundle.getString("MPO_CODE");
                                Intent sameint = new Intent(ProductOrdernew.this, ReadComments.class);
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

        if (v.getId() == calc.getId()) {
            float sum = 0f;
            int qnty1 = 0;
            float rate = 0f;
            float product_value = 0f;

            float sum_general = 0f;
            int qnty_general = 0;
            float rate_general = 0f;
            float product_value_general = 0f;

            float sum_vergence = 0f;
            int qnty_vergence = 0;
            float rate_vergence = 0f;
            float product_value_vergence = 0f;
            float sum_gallant = 0f;
            int qnty_gallant = 0;
            float rate_gallant = 0f;
            float product_value_gallant = 0f;
            float sum_darma = 0f;
            int qnty_darma = 0;
            float rate_darma = 0f;
            float product_value_darma = 0f;
            float sum_dynamos = 0f;
            int qnty_dynamos = 0;
            float rate_dynamos = 0f;
            float product_value_dynamos = 0f;
            float sum_aspiron = 0f;
            int qnty_aspiron = 0;
            float rate_aspiron = 0f;
            float product_value_aspiron = 0f;
            float sum_plexus = 0f;
            int qnty_plexus = 0;
            float rate_plexus = 0f;
            float product_value_plexus = 0f;
            int total_item = ProductListAdapter2.p_quanty.size();

            for (int i = 1; i < total_item; i++) {
                int value = Integer.parseInt(ProductListAdapter2.p_quanty.get(i));
                brand = P_CODE.get(i - 1);
                if (value > 0) {
                    qnty1 = Integer.parseInt(ProductListAdapter2.p_quanty.get(i));
                    rate = Float.parseFloat(PROD_RATE.get(i - 1));
                    rate += Float.parseFloat(PROD_VAT.get(i - 1));
                    product_value = qnty1 * rate;
                    sum = sum + product_value;
                }
            }

            for (int i = 1; i < total_item; i++) {
                int value = Integer.parseInt(ProductListAdapter2.p_quanty.get(i));
                brand = P_CODE.get(i - 1);
                String brand_name = SHIFT_CODE.get(i - 1);
                //if (brand_name.equals("TITAN")) {
                if (brand_name.equals("Gen Team B")) {
                    if (value > 0) {
                        qnty_general = Integer.parseInt(ProductListAdapter2.p_quanty.get(i));
                        rate_general =  Float.parseFloat(PROD_RATE.get(i - 1));
                        rate_general += Float.parseFloat(PROD_VAT.get(i - 1));
                        product_value_general = qnty_general * rate_general;
                        sum_general = sum_general + product_value_general;
                    }
                }
            }

            for (int i = 1; i < total_item; i++) {
                int value = Integer.parseInt(ProductListAdapter2.p_quanty.get(i));
                brand = P_CODE.get(i - 1);
                String brand_name = SHIFT_CODE.get(i - 1);
                //if (brand_name.equals("VERGENCE")) {
                if (brand_name.equals("Special_E")) {
                    if (value > 0) {
                        qnty_vergence = Integer.parseInt(ProductListAdapter2.p_quanty.get(i));
                        rate_vergence = Float.parseFloat(PROD_RATE.get(i - 1));
                        rate_vergence += Float.parseFloat(PROD_VAT.get(i - 1));
                        product_value_vergence = qnty_vergence * rate_vergence;
                        sum_vergence = sum_vergence + product_value_vergence;
                    }
                }
            }

            for (int i = 1; i < total_item; i++) {
                int value = Integer.parseInt(ProductListAdapter2.p_quanty.get(i));
                brand = P_CODE.get(i - 1);
                String brand_name = SHIFT_CODE.get(i - 1);
                //if (brand_name.equals("GALLANT")) {
                if (brand_name.equals("Special_C")) {
                    if (value > 0) {
                        qnty_gallant = Integer.parseInt(ProductListAdapter2.p_quanty.get(i));
                        rate_gallant = Float.parseFloat(PROD_RATE.get(i - 1));
                        rate_gallant += Float.parseFloat(PROD_VAT.get(i - 1));
                        product_value_gallant = qnty_gallant * rate_gallant;
                        sum_gallant = sum_gallant + product_value_gallant;
                    }
                }
            }

            for (int i = 1; i < total_item; i++) {
                int value = Integer.parseInt(ProductListAdapter2.p_quanty.get(i));
                brand = P_CODE.get(i - 1);
                String brand_name = SHIFT_CODE.get(i - 1);
                //if (brand_name.equals("DYNAMOS")) {
                if (brand_name.equals("Gen Team A")) {
                    if (value > 0) {
                        qnty_dynamos = Integer.parseInt(ProductListAdapter2.p_quanty.get(i));
                        rate_dynamos = Float.parseFloat(PROD_RATE.get(i - 1));
                        rate_dynamos += Float.parseFloat(PROD_VAT.get(i - 1));
                        product_value_dynamos = qnty_dynamos * rate_dynamos;
                        sum_dynamos = sum_dynamos + product_value_dynamos;
                    }
                }
            }

            for (int i = 1; i < total_item; i++) {
                int value = Integer.parseInt(ProductListAdapter2.p_quanty.get(i));
                brand = P_CODE.get(i - 1);
                String brand_name = SHIFT_CODE.get(i - 1);
                //if (brand_name.equals("EXCELON")) {
                if (brand_name.equals("Special_D")) {
                    if (value > 0) {
                        qnty_darma = Integer.parseInt(ProductListAdapter2.p_quanty.get(i));
                        rate_darma = Float.parseFloat(PROD_RATE.get(i - 1));
                        rate_darma += Float.parseFloat(PROD_VAT.get(i - 1));
                        product_value_darma = qnty_darma * rate_darma;
                        sum_darma = sum_darma + product_value_darma;
                    }
                }
            }
            table2.setVisibility(View.VISIBLE);
            totalsellquantity.setVisibility(View.VISIBLE);
            String test = String.valueOf(sum);

            @SuppressLint("DefaultLocale") String total_value = String.format("%.02f", sum);
            totalsellquantity.setText("" + total_value);
            System.out.println("total_value " + total_value);

            gen_sum.setVisibility(View.VISIBLE);
            @SuppressLint("DefaultLocale") String total_value_general = String.format("%.02f", sum_general);
            gen_sum.setText("Team B" + "\n" + total_value_general);
            //gen_sum.setText("Titan" + "\n" + total_value_general);

            ver_sum.setVisibility(View.VISIBLE);
            @SuppressLint("DefaultLocale") String total_value_ver = String.format("%.02f", sum_vergence);
            ver_sum.setText("Special E" + "\n" + total_value_ver);
            //ver_sum.setText("Vergence" + "\n" + total_value_ver);

            gal_sum.setVisibility(View.VISIBLE);
            @SuppressLint("DefaultLocale") String total_value_gal = String.format("%.02f", sum_gallant);
            gal_sum.setText("Special C" + "\n" + total_value_gal);
            //gal_sum.setText("Gallant" + "\n" + total_value_gal);

            dar_sum.setVisibility(View.VISIBLE);
            @SuppressLint("DefaultLocale") String total_value_dar = String.format("%.02f", sum_darma);
            dar_sum.setText("Special D" + "\n" + total_value_dar);
            //dar_sum.setText("Excelon" + "\n" + total_value_dar);

            aspiron_sum.setVisibility(View.GONE);
            @SuppressLint("DefaultLocale") String total_value_asp = String.format("%.02f", sum_aspiron);
            aspiron_sum.setText("Aspiron" + "\n" + total_value_asp);

            plexus_sum.setVisibility(View.GONE);
            @SuppressLint("DefaultLocale") String total_value_plexus = String.format("%.02f", sum_plexus);
            plexus_sum.setText("Plexus" + "\n" + total_value_plexus);

            dynamos_sum.setVisibility(View.VISIBLE);
            @SuppressLint("DefaultLocale") String total_value_dynamos = String.format("%.02f", sum_dynamos);
            dynamos_sum.setText("Team A" + "\n" + total_value_dynamos);
            //dynamos_sum.setText("Dynamos" + "\n" + total_value_dynamos);
        }
    }

    protected void onPostExecute() {}
}
