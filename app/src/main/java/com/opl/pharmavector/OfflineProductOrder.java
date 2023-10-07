package com.opl.pharmavector;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.zip.Inflater;
public class OfflineProductOrder extends Activity implements View.OnClickListener {
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_SUCCESS1 = "success_1";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_ord_no = "ord_no";
    public String userName_1, userName;
    // array list for spinner adapter
    static ArrayList<Category> categoriesList;
    static ArrayList<test> arrayList;
    static ArrayList<Product> productList;
    ProgressDialog pDialog;
    static ListView productListView;
    OfflineProductListAdapter adapter2;
    Button submit;
    // private EditText current_qnty;
    public static EditText qnty, searchview;
    public int success, success_1;
    public String message, ord_no, target, searchString;
    OfflineProductListAdapter adapter;
    JSONParser jsonParser;
    List<NameValuePair> params;
    static TextView totalsellquantity;
    static TextView totalsellvalue;
    public LinearLayout totalshow;
    public Button calc;
    public static ArrayList<String> p_code;
    public static ArrayList<Integer> p_quanty;
    public static ArrayList<String> PROD_RATE;
    public static ArrayList<String> PROD_VAT;
    ArrayList<String> lables;
    ArrayList<Integer> quanty;
    HashMap<Integer, String> mapQuantity;
    HashMap<Integer, String> productquantity;

    static HashMap<String, Integer> nameSerialPair;
    ArrayList<String> sl;
    /*- Initializing*/
    String last_quantity = "1";
    int last_position = 1;
    String quantity = "1";
    ArrayList<Category> arraylist = new ArrayList<Category>();
    private final int REQ_CODE_SPEECH_INPUT = 100;
    public static String URL_NEW_CATEGORY = BASE_URL+"put_products.php";

    private DatabaseHandler db;

    @SuppressLint("DefaultLocale")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offlineproductorder);
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        submit = (Button) findViewById(R.id.submitBtn);
        submit.setTypeface(fontFamily);
        submit.setText("\uf1d8"); // &#xf1d8
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
        TextView search = (TextView) findViewById(R.id.search);
        search.setTypeface(fontFamily);
        search.setText("\uf056"); // &#xf002 , &#xf010

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    searchview.setText("");
                    searchview.requestFocus();
                } catch (Exception e) {
                    // TODO: handle exception

                }

            }
        });
        totalsellquantity = (TextView) findViewById(R.id.totalsellquantity);
        totalsellquantity.setVisibility(View.GONE);
        totalsellvalue = (TextView) findViewById(R.id.totalsellvalue);
        totalshow = (LinearLayout) findViewById(R.id.totalshow);
        OfflineProductListAdapter.qnty = null;
        OfflineProductListAdapter.qntyID.clear();
        OfflineProductListAdapter.qntyVal.clear();
        p_code = new ArrayList<String>();
        p_quanty = new ArrayList<Integer>();
        mapQuantity = new HashMap<Integer, String>();
        productquantity = new HashMap<Integer, String>();
        nameSerialPair = new HashMap<String, Integer>();
        PROD_RATE = new ArrayList<String>();
        PROD_VAT = new ArrayList<String>();
        categoriesList = new ArrayList<Category>();
        arrayList = new ArrayList<test>();
        productList = new ArrayList<Product>();
        Intent in = getIntent();
        Intent inten = getIntent();
        Bundle bundle = in.getExtras();
        Bundle extra = inten.getExtras();
        extra.getString("CUST_CODE");
        bundle.getString("ORDER_DELEVERY_DATE");
        bundle.getString("ORDER_REFERANCE_NO");
        submit.setOnClickListener(this);


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Inflater inf = new Inflater();
                inf.end();
                finish();


            }
        });

        showorders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                searchString = "1";
                adapter.getFilter().filter(searchString);
            }
        });
        searchview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        populateSpinner();
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
                finish();
            default:
                return super.onOptionsItemSelected(item);
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
        super.onResume();
    }
    private void populateSpinner() {
        lables = new ArrayList<String>();
        quanty = new ArrayList<Integer>();
        sl = new ArrayList<String>();
        db = new DatabaseHandler(OfflineProductOrder.this);
        arrayList = db.getProductdetails();
        if (arrayList.size() != 0) {
            for (int i = 0; i < arrayList.size(); i++) {
                sl.add(arrayList.get(i).getproductsl());
                lables.add(arrayList.get(i).getproductname());
                p_code.add(arrayList.get(i).getproductcode());
                PROD_RATE.add(arrayList.get(i).getproductrate());
                PROD_VAT.add(arrayList.get(i).getproductvat());
                nameSerialPair.put(arrayList.get(i).getproductname(), Integer.parseInt(arrayList.get(i).getproductsl()));
                int p_serial = Integer.parseInt(arrayList.get(i).getproductsl());
                mapQuantity.put(p_serial, String.valueOf(arrayList.get(i).getproductquant()));
            }
        }

        adapter = new OfflineProductListAdapter(OfflineProductOrder.this, sl, lables, mapQuantity);
        productListView.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == submit.getId()) {
            Intent in = getIntent();
            Intent inten = getIntent();
            Bundle bundle = in.getExtras();
            Bundle extra = inten.getExtras();
            extra.getString("CUST_CODE");
            extra.getString("AM_PM");
            extra.getString("cash_credit");
            bundle.getString("ORDER_DELEVERY_DATE");
            bundle.getString("ORDER_REFERANCE_NO");
            String MPO_CODE = bundle.getString("MPO_CODE");
            String ORDER_DELEVERY_DATE = bundle.getString("ORDER_DELEVERY_DATE");
            String CUST_CODE = bundle.getString("CUST_CODE");
            SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyyMMddHHmmssSS");
            Date myDate = new Date();
            String enterdate = timeStampFormat.format(myDate);
            String s = enterdate.substring(0, 16);
            String Ordno = MPO_CODE + "-" + s;
            String AM_PM = bundle.getString("AM_PM");
            String cash_credit = bundle.getString("cash_credit");
            String last_quantity = OfflineProductListAdapter.qnty;
            int last_position = OfflineProductListAdapter.last_position;
            if (last_quantity != null) {
                OfflineProductListAdapter.dataSet.put(last_position, last_quantity);
                OfflineProductListAdapter.qntyID.add(last_position);
                OfflineProductListAdapter.qntyVal.add(last_quantity);
                OfflineProductListAdapter.set2.add(last_position);
            }

            if (OfflineProductListAdapter.qntyID.size() < 1) {
                Toast.makeText(this, "No item inserted", Toast.LENGTH_SHORT).show();
            } else {
                db.Ordmain(new Ordmain(Ordno, CUST_CODE, MPO_CODE, enterdate, ORDER_DELEVERY_DATE, AM_PM, AM_PM, cash_credit));
                ArrayList<Integer> position = new ArrayList<Integer>();
                for (int j : OfflineProductListAdapter.set2) {
                    position.add(j);
                }

                int k = 0;
                for (int i = 1; i < OfflineProductListAdapter.p_quanty.size(); i++) {
                    int value = Integer.parseInt(OfflineProductListAdapter.p_quanty.get(i));
                    if (value > 0) {
                        k = k + 1;
                        String code = p_code.get(i - 1);
                        String rate = PROD_RATE.get(i - 1);
                        String prod_quant = OfflineProductListAdapter.p_quanty.get(i);
                        String vat = PROD_VAT.get(i - 1);
                        db.Orditem(new Orditem(Ordno, code, rate, prod_quant, vat));
                    }
                }
            }
            Intent sameint = new Intent(OfflineProductOrder.this, Offlinereadcomments.class);
            sameint.putExtra("UserName", MPO_CODE);
            sameint.putExtra("Ord_NO", Ordno);
            startActivity(sameint);
        }
        if (v.getId() == calc.getId()) {
            float sum = 0f;
            int qnty1 = 0;
            float rate = 0f;
            float product_value = 0f;
            int total_item = OfflineProductListAdapter.p_quanty.size();
            // System.out.println("total_item " + total_item);
            for (int i = 1; i < total_item; i++) {
                int value = Integer.parseInt(OfflineProductListAdapter.p_quanty.get(i));
                if (value > 0) {
                    qnty1 = Integer.parseInt(OfflineProductListAdapter.p_quanty.get(i));
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


    @SuppressLint("SimpleDateFormat")


    protected void onPostExecute() {

    }

}
