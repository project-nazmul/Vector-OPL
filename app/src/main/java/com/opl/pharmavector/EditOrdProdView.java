package com.opl.pharmavector;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;

import org.apache.http.NameValuePair;

import com.opl.pharmavector.R;
import com.opl.pharmavector.R.layout;
import com.opl.pharmavector.database.DatabaseQueryHelper;
import com.opl.pharmavector.util.SharedPreferencesHelper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EditOrdProdView extends Activity implements OnClickListener {

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

    // array list for spinner adapter
    static ArrayList<Category> categoriesList;
    static ArrayList<test> arrayList;
    static ArrayList<test1> arrayList2;

    ProgressDialog pDialog;
    static ListView productListView;
    EditOfflineProdListAdapter adapter2;
    Button submit;
    // private EditText current_qnty;
    EditText qnty, searchview;
    EditText inputOne, inputtwo;
    public int success, success_1;
    public String message, ord_no, invoice, target, achivement, searchString, message_1, message_2;
    int textlength = 0;
    EditOfflineProdListAdapter adapter;

    JSONParser jsonParser;
    List<NameValuePair> params;
    static TextView totalsellquantity;

    static TextView totalsellvalue;
    public LinearLayout totalshow;
    public Button calc;

    public static ArrayList<String> p_ids;
    public static ArrayList<Integer> p_quanty;

    public static ArrayList<String> PROD_RATE;
    public static ArrayList<String> PROD_VAT;
    ArrayList<String> lables;
    ArrayList<Integer> quanty;
    Toast toast1;

    ArrayList<String> s_pcode;
    ArrayList<String> s_q;


    HashMap<Integer, String> mapQuantity;

    static HashMap<String, Integer> nameSerialPair;
    ArrayList<String> sl;
    /*- Initializing*/
    String last_quantity = "1";
    int last_position = 1;
    String quantity = "1";

    ArrayList<Category> arraylist = new ArrayList<Category>();


    public static ArrayList<String> p_code;
    private DatabaseHandler db;

    public static String URL_NEW_CATEGORY = BASE_URL+"offline_put_products.php";

    private String URL_CATEGORIES = BASE_URL+"get_updateproducts.php";
    private String campaign_credit = BASE_URL+"get_campaign_updateproducts.php";

    @SuppressLint("DefaultLocale")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.editordprodview);
        Typeface fontFamily = Typeface.createFromAsset(getAssets(),
                "fonts/fontawesome.ttf");
        submit = (Button) findViewById(R.id.submitBtn);
        submit.setTypeface(fontFamily);
        submit.setText("\uf1d8"); // &#xf1d8
        productListView = (ListView) findViewById(R.id.pListView);
        Button back_btn = (Button) findViewById(R.id.backBtn);
        TextView showorders = (TextView) findViewById(R.id.showorders);

        showorders.setTypeface(fontFamily);
        showorders.setText("\uf055");//&#xf055
        back_btn.setTypeface(fontFamily);
        back_btn.setText("\uf060 ");// &#xf060
        calc = (Button) findViewById(R.id.calc);
        calc.setTypeface(fontFamily);
        calc.setText("\uf1ec"); // &#xf01e &#xf1ec
        calc.setOnClickListener(this);
        searchview = (EditText) findViewById(R.id.p_search);

        TextView search = (TextView) findViewById(R.id.search);
        search.setTypeface(fontFamily);
        search.setText("\uf056"); // &#xf002 , &#xf010// [&#xf056;]
        search.setOnClickListener(new OnClickListener() {


            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                searchview.setText("");

            }
        });

        totalsellquantity = (TextView) findViewById(R.id.totalsellquantity);
        totalsellquantity.setVisibility(View.GONE);

        totalsellvalue = (TextView) findViewById(R.id.totalsellvalue);
        Spinner am_pm = (Spinner) findViewById(R.id.ampm);
        Spinner cash_credit = (Spinner) findViewById(R.id.cashcredit);


        totalshow = (LinearLayout) findViewById(R.id.totalshow);

        EditOfflineProdListAdapter.qnty = "10";
        EditOfflineProdListAdapter.qntyID.clear();
        EditOfflineProdListAdapter.qntyVal.clear();
        p_code = new ArrayList<String>();
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
        extra.getString("CUST_CODE");
        bundle.getString("ORDER_DELEVERY_DATE");
        bundle.getString("ORDER_REFERANCE_NO");
        submit.setOnClickListener(this);

        back_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Inflater inf = new Inflater();
                inf.end();
                finish();


            }
        });
        showorders.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                searchString = "1";

                adapter.getFilter().filter(searchString);

                Log.d("Clicked on search view", "1");
            }
        });


        searchview.addTextChangedListener(new TextWatcher() {

            @SuppressLint("DefaultLocale")
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

                ArrayList<String> resList = new ArrayList<String>();
                ArrayList<Integer> resList2 = new ArrayList<Integer>();

                if (s.length() < 1) {
                    searchString = null;
                } else {
                    searchString = s.toString().toLowerCase();
                }

                searchString = s.toString().toLowerCase();
                Log.i("Onik text changed----", " searchString " + searchString + " adapter " + adapter);

                if (searchString != null && adapter != null) {
                    adapter.getFilter().filter(searchString);
                }


            }

            private boolean isNumeric(int test) {
                // TODO Auto-generated method stub
                return false;
            }

            private boolean isNumeric(String string) {
                // TODO Auto-generated method stub
                return false;
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

        populateSpinner();

        // new GetCategories().execute();


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

    /*-------------- Extra Menus--------------*/
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menus, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                startActivity(new Intent(this, setting.class));
                return true;
            case R.id.help:
                startActivity(new Intent(this, Help.class));
                return true;


            case R.id.home:
                //Intent i = new Intent(this, Login.class);
                //i.putExtra("UserName_1",userName_1);
                //i.putExtra("UserName",userName);
                //startActivity(i);
                //return true;
                finish();
            default:
                return super.onOptionsItemSelected(item);


        }
    }

    /*------------Extra menus end-------------------*/


    public void finishActivity(View v) {
        finish();
    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        // System.out.println("EditTxtID " +   EditOfflineProdListAdapter .editTxtID.size());
        super.onResume();
    }

    private void populateSpinner() {

        Intent in = getIntent();
        Intent inten = getIntent();
        Bundle bundle = in.getExtras();
        Bundle extra = inten.getExtras();
        String CUST_CODE = extra.getString("CUST_CODE");

        final String message_1 = extra.getString("message_1");
        String AM_PM = extra.getString("AM_PM");

        String cash_credit = extra.getString("cash_credit");


        String ord_no = extra.getString("selected_ord");


        lables = new ArrayList<String>();
        quanty = new ArrayList<Integer>();
        sl = new ArrayList<String>();
        s_q = new ArrayList<String>();
        s_pcode = new ArrayList<String>();
        db = new DatabaseHandler(EditOrdProdView.this);
        arrayList2 = db.getsavedProductdetails(ord_no);


        if (arrayList2.size() != 0) {
            for (int i = 0; i < arrayList2.size(); i++) {
                sl.add(arrayList2.get(i).getproductsl());
                lables.add(arrayList2.get(i).getproductname());
                p_code.add(arrayList2.get(i).getproductcode());
                PROD_RATE.add(arrayList2.get(i).getproductrate());
                PROD_VAT.add(arrayList2.get(i).getproductvat());
                nameSerialPair.put(arrayList2.get(i).getproductname(), Integer.parseInt(arrayList2.get(i).getproductsl()));
                int p_serial = Integer.parseInt(arrayList2.get(i).getproductsl());
                mapQuantity.put(p_serial, String.valueOf(arrayList2.get(i).getproductquant()));


            }
        }

        adapter = new EditOfflineProdListAdapter(EditOrdProdView.this, sl, lables, mapQuantity);
        productListView.setAdapter(adapter);

    }


    @Override
    public void onClick(View v) {

        if (v.getId() == submit.getId()) {


            Toast.makeText(getBaseContext(), "Clicked to Update Offline Order", Toast.LENGTH_LONG).show();

            Intent in = getIntent();
            Intent inten = getIntent();
            Bundle bundle = in.getExtras();
            Bundle extra = inten.getExtras();
            String CUST_CODE = extra.getString("CUST_CODE");
            final String message_1 = extra.getString("message_1");
            String AM_PM = extra.getString("AM_PM");

            final String selectrd_ord = extra.getString("selected_ord");
            String cash_credit = extra.getString("cash_credit");


            String MPO_CODE = extra.getString("MPO_CODE");
            String ORDER_DELEVERY_DATE = bundle.getString("ORDER_DELEVERY_DATE");
            String last_quantity = EditOfflineProdListAdapter.qnty;
            int last_position = EditOfflineProdListAdapter.last_position;

            //  Toast.makeText(getBaseContext(), "Values in edit offline order activity"+"->"+ MPO_CODE+ "==>"+selectrd_ord +"CUST CODE"+CUST_CODE, Toast.LENGTH_LONG).show();


            if (last_quantity != null) {
                EditOfflineProdListAdapter.dataSet.put(last_position, last_quantity);
                EditOfflineProdListAdapter.qntyID.add(last_position);
                EditOfflineProdListAdapter.qntyVal.add(last_quantity);
                EditOfflineProdListAdapter.set2.add(last_position);
            }


            if (EditOfflineProdListAdapter.qntyID.size() < 1) {
                Toast.makeText(this, "No item inserted", Toast.LENGTH_SHORT).show();
            } else {
                String qnty;
                ArrayList<Integer> position = new ArrayList<Integer>();
                for (int j : EditOfflineProdListAdapter.set2) {
                    position.add(j);
                }
                int k = 0;

                int submit_value = 0;

                db.deleteOfflineorderitem(selectrd_ord);

                for (int i = 1; i < EditOfflineProdListAdapter.p_quanty.size(); i++) {

                    int value = Integer.parseInt(EditOfflineProdListAdapter.p_quanty.get(i));

                    if (value > 0) {
                        submit_value = submit_value + value;
                        k = k + 1;
                        int qnty1 = Integer.parseInt(EditOfflineProdListAdapter.p_quanty.get(i));

                        String pro_code = p_code.get(i - 1);
                        String ORD_QNTY = EditOfflineProdListAdapter.p_quanty.get(i);
                        String pro_r = PROD_RATE.get(i - 1);
                        String pro_v = PROD_VAT.get(i - 1);
                        Toast.makeText(this, "Edited offline Products " + pro_code + "===" + ORD_QNTY + "===" + pro_r + "====" + pro_v, Toast.LENGTH_SHORT).show();


                        db.updateOrditem(new Orditem(selectrd_ord, pro_code, pro_r, ORD_QNTY, pro_v));


                    }

                }


                if (submit_value > 0) {

                    //   selectrd_ord

                    final ProgressDialog progress = ProgressDialog.show(this, "Updating offline  Order", "Please Wait..", true);
                    Toast.makeText(this, "Updated Order number ===  " + selectrd_ord, Toast.LENGTH_SHORT).show();

                    Thread server = new Thread(new Runnable() {
                        @Override
                        public void run() {


                            //  Toast.makeText(this, "Edited offline Products " +pro_code+"==="+ORD_QNTY+"==="+pro_r+"===="+pro_v, Toast.LENGTH_SHORT).show();
                            Intent in = getIntent();
                            Intent inten = getIntent();
                            Bundle bundle = in.getExtras();
                            inten.getExtras();
                            String MPO_CODE = bundle.getString("MPO_CODE");

                            Intent sameint = new Intent(EditOrdProdView.this, Offlinereadcomments.class);


                            sameint.putExtra("UserName", MPO_CODE);
                            sameint.putExtra("Ord_NO", selectrd_ord);


                            sameint.putExtra("UserName_1", message_1);
                            sameint.putExtra("UserName_2", message_2);
                            startActivity(sameint);

                            // db.deleteOfflineordermain(selectrd_ord);
                            // db.deleteOfflineorderitem(selectrd_ord);

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
            int total_item = EditOfflineProdListAdapter.p_quanty.size();
            // System.out.println("total_item " + total_item);
            for (int i = 1; i < total_item; i++) {
                int value = Integer
                        .parseInt(EditOfflineProdListAdapter.p_quanty.get(i));
                // System.out.println("value " + value);

                if (value > 0) {

                    qnty1 = Integer.parseInt(EditOfflineProdListAdapter.p_quanty.get(i));
                    rate = Float.parseFloat(PROD_RATE.get(i - 1));
                    System.out.println("qnty1 " + qnty1);
                    System.out.println("rate " + rate);
                    product_value = qnty1 * rate;
                    sum = sum + product_value;

                }

            }

            totalsellquantity.setVisibility(v.VISIBLE);
            String total_value = String.format("%.02f", sum);
            totalsellquantity.setText("" + total_value);

        }

    }

    @SuppressLint("SimpleDateFormat")
    private void SaveToDataBase() {
        for (int i = 1; i < EditOfflineProdListAdapter.p_quanty.size(); i++) {
            int value = Integer.parseInt(EditOfflineProdListAdapter.p_quanty.get(i));
            Intent in = getIntent();
            int max = SharedPreferencesHelper.get_max(getBaseContext());

            Intent inten = getIntent();
            Bundle bundle = in.getExtras();
            Bundle extra = inten.getExtras();
            String CUST_CODE = extra.getString("CUST_CODE");
            String AM_PM = extra.getString("AM_PM");
            String MPO_CODE = extra.getString("MPO_CODE");
            String ORDER_DELEVERY_DATE = bundle
                    .getString("ORDER_DELEVERY_DATE");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String currentDateandTime = sdf.format(new Date());
            if (value > 0) {
                DatabaseQueryHelper.SaveSingleOrderDatabase(p_code.get(i - 1),
                        value, CUST_CODE, ORDER_DELEVERY_DATE,
                        "" + max, ""
                                + currentDateandTime, MPO_CODE, AM_PM);
                SharedPreferencesHelper.set_max(getBaseContext(), max + 1);
            }


        }
    }

    protected void onPostExecute() {

    }

}
